package com.pavelsemak.weatherapp.data.client;

import com.pavelsemak.weatherapp.data.model.WeatherResponse;
import com.pavelsemak.weatherapp.data.utils.Constant;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherClient {

    @GET("forecast/" + Constant.FORECAST_SECRET_KEY + "/{location}?units=si")
    Observable<WeatherResponse> getWeather(@Path("location") String locationString);
}
