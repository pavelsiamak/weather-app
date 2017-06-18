package com.pavelsemak.weatherapp.di.components;

import com.pavelsemak.weatherapp.MainActivity;
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
    void inject(MainActivity baseActivity);

    WeatherRepository weatherRepository();
    CityRepository cityRepositoty();

    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
}
