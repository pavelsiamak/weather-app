package com.pavelsemak.weatherapp.di.components;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.pavelsemak.weatherapp.di.PerActivity;
import com.pavelsemak.weatherapp.di.modules.ActivityModule;
import com.pavelsemak.weatherapp.viewmodel.CityViewModel;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
interface ActivityComponent {
    FragmentActivity fragmentActivity();
}
