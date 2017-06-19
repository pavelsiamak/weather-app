package com.pavelsemak.weatherapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.android.gms.location.places.Place;
import com.pavelsemak.weatherapp.di.PerActivity;
import com.pavelsemak.weatherapp.domain.interactor.AddCity;
import com.pavelsemak.weatherapp.domain.interactor.GetCities;
import com.pavelsemak.weatherapp.domain.model.CityModel;
import com.pavelsemak.weatherapp.mapper.CityModelMapper;
import com.pavelsemak.weatherapp.mapper.PlaceMapper;
import com.pavelsemak.weatherapp.model.City;
import com.pavelsemak.weatherapp.wrapper.DataWrapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

@PerActivity
public class CityViewModel extends ViewModel {

    private MutableLiveData<DataWrapper<List<City>>> citiesLiveData;
    private MutableLiveData<Integer> activeCityIndex;

    @Inject
    GetCities getCitiesUseCase;

    @Inject
    CityModelMapper cityModelMapper;

    @Inject
    PlaceMapper placeMapper;

    @Inject
    AddCity addCityUseCase;

    public MutableLiveData<DataWrapper<List<City>>> getCities() {
        if (citiesLiveData == null) {
            citiesLiveData = new MutableLiveData<>();
            loadCities();
        }
        return citiesLiveData;
    }

    public MutableLiveData<Integer> getActiveCityIndex() {
        if (activeCityIndex == null) {
            activeCityIndex = new MutableLiveData<>();
            activeCityIndex.setValue(0);
        }
        return activeCityIndex;
    }

    private void loadCities() {
        getCitiesUseCase.execute(new DisposableObserver<List<CityModel>>() {
            @Override
            public void onNext(@NonNull List<CityModel> cityModels) {
                List<City> cities = cityModelMapper.transform(cityModels);
                if (cities != null && cities.size() != 0) {
                    citiesLiveData.setValue(new DataWrapper<>(cities, null));
                } else {
                    citiesLiveData.setValue(new DataWrapper<>(cities, new Throwable("Empty data")));
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                citiesLiveData.setValue(new DataWrapper<List<City>>(null, e));
            }

            @Override
            public void onComplete() {
            }
        }, null);
    }

    public void onNewPlaceReceived(Place place) {
        addCityUseCase.execute(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(@NonNull Boolean aBoolean) {
                loadCities();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                new RuntimeException(e);
            }

            @Override
            public void onComplete() {
            }
        }, AddCity.Params.forNewCity(placeMapper.transform(place)));
    }

    public void onActiveCityIndexChanged(int index) {
        getActiveCityIndex().setValue(index);
    }
}
