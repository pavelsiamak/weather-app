package com.pavelsemak.weatherapp.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {CityData.class}, version = 2)
public abstract class CityDatabase extends RoomDatabase {
    public abstract CityDao cityDao();
}
