package com.pavelsemak.weatherapp.di.components;

import com.pavelsemak.weatherapp.di.PerActivity;
import com.pavelsemak.weatherapp.di.modules.ActivityModule;
import com.pavelsemak.weatherapp.di.modules.WeatherModule;
import com.pavelsemak.weatherapp.view.BaseCityFragment;
import com.pavelsemak.weatherapp.view.CityListFragment;
import com.pavelsemak.weatherapp.view.WeatherFragment;
import com.pavelsemak.weatherapp.view.WeatherPageFragment;
import com.pavelsemak.weatherapp.viewmodel.CityViewModel;
import com.pavelsemak.weatherapp.viewmodel.WeatherViewModel;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, WeatherModule.class})
public interface WeatherComponent extends ActivityComponent {
    void inject(WeatherFragment weatherFragment);
    void inject(CityListFragment cityListFragment);
    void inject(WeatherPageFragment weatherPageFragment);

    void inject(CityViewModel cityViewModel);
    void inject(WeatherViewModel weatherViewModel);

    CityViewModel cityViewModel();
    WeatherViewModel weatherViewModel();
}
