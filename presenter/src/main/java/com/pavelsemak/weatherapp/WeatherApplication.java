package com.pavelsemak.weatherapp;

import android.app.Application;

import com.pavelsemak.weatherapp.di.components.ApplicationComponent;
import com.pavelsemak.weatherapp.di.components.DaggerApplicationComponent;
import com.pavelsemak.weatherapp.di.modules.ApplicationModule;

public class WeatherApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
