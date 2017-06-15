package com.pavelsemak.weatherapp.data.repository.datasource;

import com.pavelsemak.weatherapp.data.repository.model.WeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface WeatherDataStore {

    @GET("weather")
    Observable<WeatherResponse> getWeather();
}
