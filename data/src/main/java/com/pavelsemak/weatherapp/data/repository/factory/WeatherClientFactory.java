package com.pavelsemak.weatherapp.data.repository.factory;

import com.pavelsemak.weatherapp.data.client.WeatherClient;
import com.pavelsemak.weatherapp.data.net.WeatherRetrofit;

import javax.inject.Inject;

public class WeatherClientFactory {

    private WeatherRetrofit weatherRetrofit;

    @Inject
    WeatherClientFactory(WeatherRetrofit weatherRetrofit) {
        this.weatherRetrofit = weatherRetrofit;
    }

    public WeatherClient createCloudDataStore() {
        return weatherRetrofit.getRetrofit().create(WeatherClient.class);
    }

}
