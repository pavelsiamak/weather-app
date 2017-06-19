package com.pavelsemak.weatherapp.mapper;

import com.pavelsemak.weatherapp.di.PerActivity;
import com.pavelsemak.weatherapp.domain.model.WeatherItemModel;
import com.pavelsemak.weatherapp.domain.model.WeatherModel;
import com.pavelsemak.weatherapp.model.Weather;
import com.pavelsemak.weatherapp.model.WeatherItemDay;
import com.pavelsemak.weatherapp.model.WeatherItemHour;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class WeatherModelMapper {

    @Inject
    WeatherModelMapper() {}

    public Weather transform(WeatherModel weatherModel) {
        Weather weather = new Weather();

        weather.setLatitude(weatherModel.getLatitude());
        weather.setLongitude(weatherModel.getLongitude());

        WeatherItemModel currentItemModel = weatherModel.getCurrently();
        weather.setTime(currentItemModel.getTime());
        weather.setSummary(currentItemModel.getSummary());
        weather.setIcon(currentItemModel.getIcon());
        weather.setTemperature(currentItemModel.getTemperature());
        weather.setPrecipProbability(currentItemModel.getPrecipProbability());
        weather.setHumidity(currentItemModel.getHumidity());
        weather.setWindSpeed(currentItemModel.getWindSpeed());
        weather.setApparentTemperature(currentItemModel.getApparentTemperature());
        weather.setPrecipIntensity(currentItemModel.getPrecipIntensity());
        weather.setPressure(currentItemModel.getPressure());
        weather.setUvIndex(currentItemModel.getUvIndex());

        if (weatherModel.getDaily() != null && weatherModel.getDaily().size() != 0) {
            WeatherItemModel todayItemModel = weatherModel.getDaily().get(0);
            weather.setTemperatureMin(todayItemModel.getTemperatureMin());
            weather.setTemperatureMax(todayItemModel.getTemperatureMax());
            weather.setSunriseTime(todayItemModel.getSunriseTime());
            weather.setSunsetTime(todayItemModel.getSunsetTime());
        }

        List<WeatherItemHour> weatherItemHourList = new ArrayList<>();
        for (WeatherItemModel hourItemModel: weatherModel.getHourly()) {
            WeatherItemHour weatherItemHour = new WeatherItemHour();
            weatherItemHour.setTime(hourItemModel.getTime());
            weatherItemHour.setIcon(hourItemModel.getIcon());
            weatherItemHour.setPrecipProbability(hourItemModel.getPrecipProbability());
            weatherItemHour.setTemperature(hourItemModel.getTemperature());
        }
        weather.setHourly(weatherItemHourList);

        List<WeatherItemDay> weatherItemDayList = new ArrayList<>();
        for (WeatherItemModel dayItemModel: weatherModel.getDaily()) {
            WeatherItemDay weatherItemDay = new WeatherItemDay();
            weatherItemDay.setTime(dayItemModel.getTime());
            weatherItemDay.setIcon(dayItemModel.getIcon());
            weatherItemDay.setTemperatureMin(dayItemModel.getTemperatureMin());
            weatherItemDay.setTemperatureMax(dayItemModel.getTemperatureMax());
        }
        weather.setDaily(weatherItemDayList);

        return weather;
    }
}
