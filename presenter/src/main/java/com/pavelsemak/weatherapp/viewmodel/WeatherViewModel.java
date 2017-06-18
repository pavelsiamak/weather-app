package com.pavelsemak.weatherapp.viewmodel;

import com.pavelsemak.weatherapp.di.PerActivity;
import com.pavelsemak.weatherapp.domain.repository.WeatherRepository;

import javax.inject.Inject;

@PerActivity
public class WeatherViewModel {

    @Inject
    WeatherViewModel(WeatherRepository weatherRepository) {

    }
}
