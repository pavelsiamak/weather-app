package com.pavelsemak.weatherapp.di.components;

import com.pavelsemak.weatherapp.di.PerActivity;
import com.pavelsemak.weatherapp.di.modules.ActivityModule;
import com.pavelsemak.weatherapp.di.modules.WeatherModule;
import com.pavelsemak.weatherapp.view.CityListFragment;
import com.pavelsemak.weatherapp.view.WeatherFragment;
import com.pavelsemak.weatherapp.viewmodel.CityViewModel;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, WeatherModule.class})
public interface WeatherComponent extends ActivityComponent {
    void inject(WeatherFragment weatherFragment);
    void inject(CityListFragment cityListFragment);
    void inject(CityViewModel cityViewModel);

    CityViewModel cityViewModel();
}
