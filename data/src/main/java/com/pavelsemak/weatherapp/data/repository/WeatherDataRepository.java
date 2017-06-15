package com.pavelsemak.weatherapp.data.repository;

import android.util.Log;

import com.pavelsemak.weatherapp.data.repository.datasource.WeatherDataStore;
import com.pavelsemak.weatherapp.data.repository.datasource.WeatherDataStoreFactory;
import com.pavelsemak.weatherapp.data.repository.model.WeatherResponse;
import com.pavelsemak.weatherapp.domain.WeatherEntity;
import com.pavelsemak.weatherapp.domain.repository.WeatherRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class WeatherDataRepository implements WeatherRepository {

    @Inject
    WeatherDataStoreFactory weatherDataStoreFactory;

    @Inject
    WeatherDataRepository() {}

    @Override
    public Observable<List<WeatherEntity>> getWeatherList() {
        WeatherDataStore weatherDataStore = weatherDataStoreFactory.createCloudDataStore();
        return weatherDataStore.getWeather().map(new Function<WeatherResponse, List<WeatherEntity>>() {
            @Override
            public List<WeatherEntity> apply(@NonNull WeatherResponse weatherResponse) throws Exception {
                return new ArrayList<WeatherEntity>();
            }
        });
    }
}
