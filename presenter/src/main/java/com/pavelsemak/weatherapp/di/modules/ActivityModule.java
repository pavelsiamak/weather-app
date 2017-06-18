package com.pavelsemak.weatherapp.di.modules;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

import com.pavelsemak.weatherapp.di.PerActivity;
import com.pavelsemak.weatherapp.viewmodel.CityViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final FragmentActivity activity;

    public ActivityModule(FragmentActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    FragmentActivity provideFragmentActivity() {
        return this.activity;
    }
}
