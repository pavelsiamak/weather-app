package com.pavelsemak.weatherapp.data.client.location;

import android.location.Location;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.util.concurrent.CountDownLatch;

public class WaitableLocationCallback extends LocationCallback {

    private final CountDownLatch latch;

    private Location location;

    public WaitableLocationCallback(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        super.onLocationResult(locationResult);
        location = locationResult.getLastLocation();
        latch.countDown();
    }

    public Location getLocation() {
        return location;
    }
}
