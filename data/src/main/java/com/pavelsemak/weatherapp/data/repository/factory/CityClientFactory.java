package com.pavelsemak.weatherapp.data.repository.factory;

import com.pavelsemak.weatherapp.data.client.CurrentCityClient;
import com.pavelsemak.weatherapp.data.client.SavedCityClient;
import com.pavelsemak.weatherapp.data.client.db.CityDBClient;
import com.pavelsemak.weatherapp.data.client.location.LocationCityClient;

import javax.inject.Inject;

public class CityClientFactory {

    private LocationCityClient locationCityClient;
    private CityDBClient cityDBClient;

    @Inject
    CityClientFactory(LocationCityClient locationCityClient, CityDBClient cityDBClient) {
        this.locationCityClient = locationCityClient;
        this.cityDBClient = cityDBClient;
    }

    public CurrentCityClient createCurrentCityClient() {
        return locationCityClient;
    }

    public SavedCityClient createSavedCityClient() {
        return cityDBClient;
    }
}
