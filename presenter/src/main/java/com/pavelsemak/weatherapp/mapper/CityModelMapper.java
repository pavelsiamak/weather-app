package com.pavelsemak.weatherapp.mapper;

import com.pavelsemak.weatherapp.di.PerActivity;
import com.pavelsemak.weatherapp.domain.model.CityModel;
import com.pavelsemak.weatherapp.model.City;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class CityModelMapper {

    @Inject
    CityModelMapper() {}

    public List<City> transform(List<CityModel> cityModelIterable) {
        List<City> cityCollection = new ArrayList<>();

        for (CityModel cityModel: cityModelIterable) {
            cityCollection.add(transform(cityModel));
        }

        return cityCollection;
    }

    public City transform(CityModel cityModel) {
        City city = new City();

        city.setName(cityModel.getName());
        city.setLatitude(cityModel.getLatitude());
        city.setLongitude(cityModel.getLongitude());

        return city;
    }
}
