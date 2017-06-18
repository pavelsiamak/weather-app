package com.pavelsemak.weatherapp.data.client;

import com.pavelsemak.weatherapp.data.db.CityData;

import java.util.List;

import io.reactivex.Observable;

public interface SavedCityClient {
    Observable<List<CityData>> getSavedCities();

    Observable<Boolean> addCity(CityData cityData);
}
