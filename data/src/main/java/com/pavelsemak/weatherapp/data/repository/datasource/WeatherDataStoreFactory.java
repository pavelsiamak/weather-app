package com.pavelsemak.weatherapp.data.repository.datasource;

import android.util.Log;

import com.pavelsemak.weatherapp.data.repository.net.RetrofitClient;

import javax.inject.Inject;

public class WeatherDataStoreFactory {

    @Inject
    RetrofitClient retrofitClient;

    @Inject
    WeatherDataStoreFactory() {

    }

    public WeatherDataStore createCloudDataStore() {
        return retrofitClient.getRetrofit().create(WeatherDataStore.class);
    }

}
