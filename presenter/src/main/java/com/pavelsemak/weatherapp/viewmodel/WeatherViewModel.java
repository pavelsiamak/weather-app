package com.pavelsemak.weatherapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.pavelsemak.weatherapp.di.PerActivity;
import com.pavelsemak.weatherapp.domain.interactor.GetWeather;
import com.pavelsemak.weatherapp.domain.model.WeatherModel;
import com.pavelsemak.weatherapp.mapper.WeatherModelMapper;
import com.pavelsemak.weatherapp.model.City;
import com.pavelsemak.weatherapp.model.Weather;
import com.pavelsemak.weatherapp.wrapper.DataWrapper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

@PerActivity
public class WeatherViewModel extends ViewModel {

    private MutableLiveData<DataWrapper<Weather>> weatherLiveData;
    private Map<String, MutableLiveData<DataWrapper<Weather>>> weatherLiveDataMap;

    @Inject
    GetWeather getWeatherUseCase;

    @Inject
    WeatherModelMapper weatherModelMapper;

    public MutableLiveData<DataWrapper<Weather>> getWeather(City city) {
        MutableLiveData<DataWrapper<Weather>> weatherLiveData;
        if (weatherLiveDataMap == null) {
            weatherLiveDataMap = new HashMap<>();
        }
        if (weatherLiveDataMap.containsKey(city.getName())) {
            weatherLiveData = weatherLiveDataMap.get(city.getName());
        } else {
            weatherLiveData = new MutableLiveData<>();
            weatherLiveDataMap.put(city.getName(), weatherLiveData);
            loadWeather(city, weatherLiveData);
        }
        return weatherLiveData;
    }

    private void loadWeather(City city, final MutableLiveData<DataWrapper<Weather>> weatherLiveData) {
        getWeatherUseCase.execute(new DisposableObserver<WeatherModel>() {
            @Override
            public void onNext(@NonNull WeatherModel weatherModel) {
                weatherLiveData.setValue(new DataWrapper<>(weatherModelMapper.transform(weatherModel), null));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                weatherLiveData.setValue(new DataWrapper<Weather>(null, new Throwable("WeatherError")));
            }

            @Override
            public void onComplete() {

            }
        }, GetWeather.Params.forWeather(city.getLatitude(), city.getLongitude()));
    }

}
