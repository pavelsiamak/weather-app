package com.pavelsemak.weatherapp.di.modules;

import android.content.Context;

import com.pavelsemak.weatherapp.WeatherApplication;
import com.pavelsemak.weatherapp.data.executor.IOThread;
import com.pavelsemak.weatherapp.data.executor.UIThread;
import com.pavelsemak.weatherapp.data.repository.WeatherDataRepository;
import com.pavelsemak.weatherapp.domain.executor.PostExecutionThread;
import com.pavelsemak.weatherapp.domain.executor.ThreadExecutor;
import com.pavelsemak.weatherapp.domain.repository.WeatherRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final WeatherApplication application;

    public ApplicationModule(WeatherApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(WeatherDataRepository weatherDataRepository) {
        return weatherDataRepository;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(IOThread ioThread) {
        return ioThread;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }
}
