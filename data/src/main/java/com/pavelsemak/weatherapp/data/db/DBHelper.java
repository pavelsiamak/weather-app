package com.pavelsemak.weatherapp.data.db;

import android.util.Log;

import java.util.Calendar;
import java.util.List;

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

    public List<CityData> getSavedCities() {
        return cityDatabase.cityDao().getSavedCities();
    }

    public boolean addNewCIty(CityData cityData) {
        try {
            cityData.setTimestamp(Calendar.getInstance().getTimeInMillis());
            cityDatabase.cityDao().insertCity(cityData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
