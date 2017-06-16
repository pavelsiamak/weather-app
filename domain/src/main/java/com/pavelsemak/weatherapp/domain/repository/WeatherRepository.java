package com.pavelsemak.weatherapp.domain.repository;

import com.pavelsemak.weatherapp.domain.model.WeatherModel;

import io.reactivex.Observable;

public interface WeatherRepository {

    Observable<WeatherModel> getWeather(String locationString);
}
