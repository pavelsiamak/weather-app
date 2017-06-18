package com.pavelsemak.weatherapp.data.repository;

import android.os.Looper;
import android.util.Log;

import com.pavelsemak.weatherapp.data.client.CurrentCityClient;
import com.pavelsemak.weatherapp.data.client.SavedCityClient;
import com.pavelsemak.weatherapp.data.mapper.CityDataMapper;
import com.pavelsemak.weatherapp.data.mapper.CityModelMapper;
import com.pavelsemak.weatherapp.data.repository.factory.CityClientFactory;
import com.pavelsemak.weatherapp.domain.model.CityModel;
import com.pavelsemak.weatherapp.domain.repository.CityRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CityDataRepository implements CityRepository {

    private CityClientFactory cityClientFactory;
    private CityDataMapper cityDataMapper;
    private CityModelMapper cityModelMapper;

    @Inject
    CityDataRepository(CityClientFactory cityClientFactory, CityDataMapper cityDataMapper, CityModelMapper cityModelMapper) {
        this.cityClientFactory = cityClientFactory;
        this.cityDataMapper = cityDataMapper;
        this.cityModelMapper = cityModelMapper;

    }

    @Override
    public Observable<List<CityModel>> getSavedCities() {
        SavedCityClient savedCityClient = cityClientFactory.createSavedCityClient();
        return savedCityClient.getSavedCities().map(cityDataMapper::transform);
    }

    @Override
    public Observable<Boolean> updateCurrentCity() {
        CurrentCityClient currentCityClient = cityClientFactory.createCurrentCityClient();
        return currentCityClient.updateCurrentCity();
    }

    @Override
    public Observable<Boolean> addCity(CityModel cityModel) {
        SavedCityClient savedCityClient = cityClientFactory.createSavedCityClient();
        return savedCityClient.addCity(cityModelMapper.transform(cityModel));
    }
}
