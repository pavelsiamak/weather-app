package com.pavelsemak.weatherapp.data.repository.factory;

import com.pavelsemak.weatherapp.data.client.CurrentCityClient;
import com.pavelsemak.weatherapp.data.client.WeatherClient;
import com.pavelsemak.weatherapp.data.location.LocationCityClient;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CityClientFactory {

    private LocationCityClient locationCityClient;

    @Inject
    CityClientFactory(LocationCityClient locationCityClient) {
        this.locationCityClient = locationCityClient;
    }

    public CurrentCityClient createCurrentCityClient() {
        return locationCityClient;
    }
}
