package com.pavelsemak.weatherapp.data.db;

import java.util.Calendar;

import javax.inject.Inject;

public class DBHelper {

    private CityDatabase cityDatabase;

    @Inject
    DBHelper(CityDatabase cityDatabase) {
        this.cityDatabase = cityDatabase;
    }

    public void updateCurrentCity(CityData cityData) {
        CityDao cityDao = cityDatabase.cityDao();
        if (cityDao.getCitiesCount() == 0) {
            cityData.setTimestamp(Calendar.getInstance().getTimeInMillis());
            cityDao.insertCity(cityData);
        } else {
            CityData firstItem = cityDao.getFirstCity();
            cityData.setId(firstItem.getId());
            cityDao.updateCity(cityData);
        }
    }
}
