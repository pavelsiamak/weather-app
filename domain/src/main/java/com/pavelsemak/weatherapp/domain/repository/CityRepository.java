package com.pavelsemak.weatherapp.domain.repository;

import com.pavelsemak.weatherapp.domain.model.CityModel;

import java.util.List;

import io.reactivex.Observable;

public interface CityRepository {
    Observable<List<CityModel>> getSavedCities();

    Observable<Boolean> updateCurrentCity();
}
