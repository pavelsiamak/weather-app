package com.pavelsemak.weatherapp.data.repository;

import com.pavelsemak.weatherapp.data.net.WeatherClient;
import com.pavelsemak.weatherapp.data.repository.factory.WeatherClientFactory;
import com.pavelsemak.weatherapp.data.mapper.WeatherResponseMapper;
import com.pavelsemak.weatherapp.domain.model.WeatherModel;
import com.pavelsemak.weatherapp.domain.repository.WeatherRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WeatherDataRepository implements WeatherRepository {

    private WeatherClientFactory weatherClientFactory;
    private WeatherResponseMapper userEntityDataMapper;

    @Inject
    WeatherDataRepository(WeatherResponseMapper userEntityDataMapper, WeatherClientFactory weatherClientFactory) {
        this.userEntityDataMapper = userEntityDataMapper;
        this.weatherClientFactory = weatherClientFactory;

    }

    @Override
    public Observable<WeatherModel> getWeather(String locationString) {
        WeatherClient weatherClient = weatherClientFactory.createCloudDataStore();
        return weatherClient.getWeather(locationString).map(userEntityDataMapper::transform);
    }
}
