package com.pavelsemak.weatherapp.data.mapper;

import com.pavelsemak.weatherapp.data.model.WeatherItemResponse;
import com.pavelsemak.weatherapp.data.model.WeatherResponse;
import com.pavelsemak.weatherapp.domain.model.WeatherItemModel;
import com.pavelsemak.weatherapp.domain.model.WeatherModel;

import java.util.ArrayList;

import javax.inject.Inject;

public class WeatherResponseMapper {

    @Inject
    WeatherResponseMapper() {}

    public WeatherModel transform(WeatherResponse weatherResponse) {
        WeatherModel weatherModel = new WeatherModel();

        weatherModel.setLatitude(weatherResponse.getLatitude());
        weatherModel.setLongitude(weatherResponse.getLongitude());

        weatherModel.setCurrently(transform(weatherResponse.getCurrently()));

        weatherModel.setOffset(weatherResponse.getOffset());

        if (weatherResponse.getHourly() != null) {
            weatherModel.setHourly(new ArrayList<>());
            for (WeatherItemResponse weatherItemResponse : weatherResponse.getHourly().getData()) {
                weatherModel.getHourly().add(transform(weatherItemResponse));
            }
        }

        if (weatherResponse.getDaily() != null) {
            weatherModel.setDaily(new ArrayList<>());
            for (WeatherItemResponse weatherItemResponse : weatherResponse.getDaily().getData()) {
                weatherModel.getDaily().add(transform(weatherItemResponse));
            }
        }

        return weatherModel;
    }

    public WeatherItemModel transform(WeatherItemResponse weatherItemResponse) {
        WeatherItemModel weatherItemModel = new WeatherItemModel();

        weatherItemModel.setTime(weatherItemResponse.getTime());
        weatherItemModel.setSummary(weatherItemResponse.getSummary());
        weatherItemModel.setIcon(weatherItemResponse.getIcon());

        weatherItemModel.setTemperature(weatherItemResponse.getTemperature());
        weatherItemModel.setTemperatureMax(weatherItemResponse.getTemperatureMax());
        weatherItemModel.setTemperatureMin(weatherItemResponse.getTemperatureMin());

        weatherItemModel.setSunriseTime(weatherItemResponse.getSunriseTime());
        weatherItemModel.setSunsetTime(weatherItemResponse.getSunsetTime());

        weatherItemModel.setPrecipProbability(weatherItemResponse.getPrecipProbability());
        weatherItemModel.setHumidity(weatherItemResponse.getHumidity());

        weatherItemModel.setWindSpeed(weatherItemResponse.getWindSpeed());
        weatherItemModel.setApparentTemperature(weatherItemResponse.getApparentTemperature());

        weatherItemModel.setPrecipIntensity(weatherItemResponse.getPrecipIntensity());
        weatherItemModel.setPressure(weatherItemResponse.getPressure());

        weatherItemModel.setVisibility(weatherItemResponse.getVisibility());
        weatherItemModel.setUvIndex(weatherItemResponse.getUvIndex());

        return weatherItemModel;
    }
}
