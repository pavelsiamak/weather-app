package com.pavelsemak.weatherapp.data.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.pavelsemak.weatherapp.data.client.CurrentCityClient;
import com.pavelsemak.weatherapp.data.db.CityData;
import com.pavelsemak.weatherapp.data.db.DBHelper;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
            emitter.onError(new RuntimeException("Location permissions not granted"));
            return;
        }

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                getCityByLocation(emitter, location);
            } else {
                requestLocationUpdates(fusedLocationClient, emitter);
            }
        }).addOnFailureListener(e -> emitter.onError(new RuntimeException("Error receiving location")));

    }

    private void requestLocationUpdates(FusedLocationProviderClient fusedLocationClient, Emitter<Boolean> emitter) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            emitter.onError(new RuntimeException("Location permissions not granted"));
            return;
        }

        LocationRequest locationRequest = createLocationRequest();
        Looper looper = createLooper();

        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                getCityByLocation(emitter, locationResult.getLastLocation());
                fusedLocationClient.removeLocationUpdates(this);
            }
        }, looper);
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
                emitter.onError(new RuntimeException("Address not found"));
            }
        } catch (IOException e) {
            emitter.onError(new RuntimeException("Geocoder error"));
        }
    }

    private LocationRequest createLocationRequest() {
        return LocationRequest.create()
                .setInterval(60000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setFastestInterval(10000);
    }

    private Looper createLooper() {
        HandlerThread handlerThread = new HandlerThread("LocationHandlerThread");
        handlerThread.start();
        return handlerThread.getLooper();
    }
}
