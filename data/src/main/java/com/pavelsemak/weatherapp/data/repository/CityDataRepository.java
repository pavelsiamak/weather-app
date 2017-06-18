package com.pavelsemak.weatherapp.data.repository;

import com.pavelsemak.weatherapp.data.client.CurrentCityClient;
import com.pavelsemak.weatherapp.data.db.CityData;
import com.pavelsemak.weatherapp.data.mapper.CityResponseMapper;
import com.pavelsemak.weatherapp.data.repository.factory.CityClientFactory;
import com.pavelsemak.weatherapp.domain.model.CityModel;
import com.pavelsemak.weatherapp.domain.repository.CityRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CityDataRepository implements CityRepository {

    private CityClientFactory cityClientFactory;
    private CityResponseMapper cityResponseMapper;

    @Inject
    CityDataRepository(CityClientFactory cityClientFactory, CityResponseMapper cityResponseMapper) {
        this.cityClientFactory = cityClientFactory;
        this.cityResponseMapper = cityResponseMapper;

    }

    @Override
    public Observable<List<CityModel>> getSavedCities() {
        CurrentCityClient currentCityClient = cityClientFactory.createCurrentCityClient();
        return Observable.just(new ArrayList<CityData>()).map(cityResponseMapper::transform);
    }

    @Override
    public Observable<Boolean> updateCurrentCity() {
        CurrentCityClient currentCityClient = cityClientFactory.createCurrentCityClient();
        return currentCityClient.updateCurrentCity();
    }
}
