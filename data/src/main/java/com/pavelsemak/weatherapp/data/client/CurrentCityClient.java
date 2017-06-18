package com.pavelsemak.weatherapp.data.client;

import com.pavelsemak.weatherapp.data.db.CityData;

import io.reactivex.Observable;

public interface CurrentCityClient {
    Observable<Boolean> updateCurrentCity();
}
