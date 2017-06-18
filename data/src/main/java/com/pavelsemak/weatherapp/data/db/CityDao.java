package com.pavelsemak.weatherapp.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CityDao {

    @Query("SELECT * FROM cityData ORDER BY timestamp ASC")
    List<CityData> getSavedCities();

    @Query("SELECT * FROM cityData ORDER BY timestamp ASC LIMIT 1")
    CityData getFirstCity();

    @Insert
    void insertCity(CityData cityEntity);

    @Delete
    void deleteCity(CityData cityEntity);

    @Query("SELECT count(id) FROM cityData")
    int getCitiesCount();

    @Update
    void updateCity(CityData cityData);
}
