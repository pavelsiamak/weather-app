package com.pavelsemak.weatherapp.data.mapper;

import com.pavelsemak.weatherapp.data.db.CityData;
import com.pavelsemak.weatherapp.domain.model.CityModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CityDataMapper {

    @Inject
    CityDataMapper() {}

    public List<CityModel> transform(List<CityData> cityDataList) {
        List<CityModel> cityModelList = new ArrayList<>();

        for(CityData cityData: cityDataList) {
            cityModelList.add(transform(cityData));
        }

        return cityModelList;
    }

    public CityModel transform(CityData cityData) {
        CityModel cityModel = new CityModel();

        cityModel.setName(cityData.getName());
        cityModel.setLatitude(cityData.getLatitude());
        cityModel.setLongitude(cityData.getLongitude());

        return cityModel;
    }
}
