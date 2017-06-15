package com.pavelsemak.weatherapp.domain.repository;

import com.pavelsemak.weatherapp.domain.WeatherEntity;

import java.util.List;

import io.reactivex.Observable;

public interface WeatherRepository {

    Observable<List<WeatherEntity>> getWeatherList();
}
