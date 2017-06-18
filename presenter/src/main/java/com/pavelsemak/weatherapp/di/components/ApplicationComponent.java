package com.pavelsemak.weatherapp.di.components;

import com.pavelsemak.weatherapp.domain.interactor.GetCities;
import com.pavelsemak.weatherapp.domain.interactor.GetWeather;
import com.pavelsemak.weatherapp.view.CityListFragment;
import com.pavelsemak.weatherapp.view.MainActivity;
import com.pavelsemak.weatherapp.di.modules.ApplicationModule;
import com.pavelsemak.weatherapp.domain.executor.PostExecutionThread;
import com.pavelsemak.weatherapp.domain.executor.ThreadExecutor;
import com.pavelsemak.weatherapp.domain.repository.CityRepository;
import com.pavelsemak.weatherapp.domain.repository.WeatherRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);

    WeatherRepository weatherRepository();
    CityRepository cityRepositoty();

    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
}
