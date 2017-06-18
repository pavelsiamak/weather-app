package com.pavelsemak.weatherapp.data.client.db;

import com.pavelsemak.weatherapp.data.client.SavedCityClient;
import com.pavelsemak.weatherapp.data.db.CityData;
import com.pavelsemak.weatherapp.data.db.DBHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CityDBClient implements SavedCityClient {

    private DBHelper dbHelper;

    @Inject
    CityDBClient(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }


    @Override
    public Observable<List<CityData>> getSavedCities() {
        return Observable.fromCallable(() -> dbHelper.getSavedCities());
    }

    @Override
    public Observable<Boolean> addCity(CityData cityData) {
        return Observable.fromCallable(() -> dbHelper.addNewCIty(cityData));
    }
}
