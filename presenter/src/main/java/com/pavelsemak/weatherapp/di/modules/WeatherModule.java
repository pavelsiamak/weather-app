package com.pavelsemak.weatherapp.di.modules;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

import com.pavelsemak.weatherapp.di.PerActivity;
import com.pavelsemak.weatherapp.viewmodel.CityViewModel;
import com.pavelsemak.weatherapp.viewmodel.WeatherViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherModule {
    public WeatherModule() {}

    @Provides
    @PerActivity
    CityViewModel provideCityViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(CityViewModel.class);
    }

    @Provides
    @PerActivity
    WeatherViewModel provideWeatherViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(WeatherViewModel.class);
    }

}
