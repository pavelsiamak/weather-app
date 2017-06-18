package com.pavelsemak.weatherapp.data.client.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.pavelsemak.weatherapp.data.client.CurrentCityClient;
import com.pavelsemak.weatherapp.data.db.CityData;
import com.pavelsemak.weatherapp.data.db.DBHelper;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Emitter;
import io.reactivex.Observable;

public class LocationCityClient implements CurrentCityClient {

    private Context context;
    private DBHelper dbHelper;

    @Inject
    LocationCityClient(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    @Override
    public Observable<Boolean> updateCurrentCity() {
        return Observable.create(this::getLastLocation);
    }

    private void getLastLocation(Emitter<Boolean> emitter) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            emitter.onNext(false);
            return;
        }

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        CountDownLatch latch = new CountDownLatch(1);
        WaitableLocationListener waitableLocationListener = new WaitableLocationListener(latch);
        fusedLocationClient.getLastLocation().addOnSuccessListener(waitableLocationListener);
        try {
            if (latch.await(3000, TimeUnit.MILLISECONDS)) {
                Location location = waitableLocationListener.getLocation();
                if (location != null) {
                    getCityByLocation(emitter, location);
                } else {
                    requestLocationUpdates(fusedLocationClient, emitter);
                }
            } else {
                emitter.onNext(false);
            }
        } catch (InterruptedException e) {
            emitter.onNext(false);
        }
    }

    private void requestLocationUpdates(FusedLocationProviderClient fusedLocationClient, Emitter<Boolean> emitter) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            emitter.onNext(false);
            return;
        }

        LocationRequest locationRequest = createLocationRequest();
        Looper looper = createLooper();

        CountDownLatch latch = new CountDownLatch(1);
        WaitableLocationCallback waitableLocationCallback = new WaitableLocationCallback(latch);
        fusedLocationClient.requestLocationUpdates(locationRequest, waitableLocationCallback, looper);
        try {
            if (latch.await(3000, TimeUnit.MILLISECONDS)) {
                Location location = waitableLocationCallback.getLocation();
                if (location != null) {
                    getCityByLocation(emitter, location);
                } else {
                    emitter.onNext(false);
                }
            } else {
                emitter.onNext(false);
            }
        } catch (InterruptedException e) {
            emitter.onNext(false);
        }
    }

    private void getCityByLocation(Emitter<Boolean> emitter, Location location) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && addresses.size() >= 1) {
                Address address = addresses.get(0);

                CityData cityData = new CityData();

                cityData.setName(address.getLocality());
                cityData.setLatitude(location.getLatitude());
                cityData.setLongitude(location.getLongitude());

                dbHelper.updateCurrentCity(cityData);

                emitter.onNext(true);
                emitter.onComplete();
            } else {
                emitter.onNext(false);
            }
        } catch (IOException e) {
            emitter.onNext(false);
        }
    }

    private LocationRequest createLocationRequest() {
        return LocationRequest.create()
                .setExpirationDuration(3000)
                .setNumUpdates(1)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private Looper createLooper() {
        HandlerThread handlerThread = new HandlerThread("LocationHandlerThread");
        handlerThread.start();
        return handlerThread.getLooper();
    }
}
