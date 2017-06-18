package com.pavelsemak.weatherapp.data.mapper;

import com.pavelsemak.weatherapp.data.db.CityData;
import com.pavelsemak.weatherapp.domain.model.CityModel;

import javax.inject.Inject;

public class CityModelMapper {

    @Inject
    CityModelMapper() {}

    public CityData transform(CityModel cityModel) {
        CityData cityData = new CityData();

        cityData.setName(cityModel.getName());
        cityData.setLatitude(cityModel.getLatitude());
        cityData.setLongitude(cityModel.getLongitude());

        return cityData;
    }
}
