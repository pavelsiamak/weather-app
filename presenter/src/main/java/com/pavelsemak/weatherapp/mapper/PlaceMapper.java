package com.pavelsemak.weatherapp.mapper;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.pavelsemak.weatherapp.di.PerActivity;
import com.pavelsemak.weatherapp.domain.model.CityModel;

import javax.inject.Inject;

@PerActivity
public class PlaceMapper {

    @Inject
    PlaceMapper() {}

    public CityModel transform(Place place) {
        CityModel cityModel = new CityModel();

        cityModel.setName(place.getName().toString());

        LatLng latLng = place.getLatLng();
        cityModel.setLatitude(latLng.latitude);
        cityModel.setLongitude(latLng.longitude);

        return cityModel;
    }
}
