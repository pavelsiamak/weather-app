package com.pavelsemak.weatherapp.data.client.location;

import android.location.Location;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.CountDownLatch;

public class WaitableLocationListener implements OnSuccessListener<Location> {

    private final CountDownLatch latch;

    private Location location;

    public WaitableLocationListener(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onSuccess(Location location) {
        this.location = location;

        latch.countDown();
    }

    public Location getLocation() {
        return location;
    }
}
