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

    public static final int PERCENT = 100;

    @Inject
    WeatherModelMapper() {}

    public Weather transform(WeatherModel weatherModel) {
        Weather weather = new Weather();

        weather.setLatitude(weatherModel.getLatitude());
        weather.setLongitude(weatherModel.getLongitude());
        weather.setOffset(weatherModel.getOffset());

        WeatherItemModel currentItemModel = weatherModel.getCurrently();
        weather.setTime(currentItemModel.getTime());
        weather.setSummary(currentItemModel.getSummary());
        weather.setIcon(currentItemModel.getIcon());
        weather.setTemperature((int) currentItemModel.getTemperature());
        weather.setPrecipProbability((int) (currentItemModel.getPrecipProbability() * PERCENT));
        weather.setHumidity((int) (currentItemModel.getHumidity() * PERCENT));
        weather.setWindSpeed((int) currentItemModel.getWindSpeed());
        weather.setApparentTemperature((int) currentItemModel.getApparentTemperature());
        weather.setPrecipIntensity((int) currentItemModel.getPrecipIntensity());
        weather.setPressure((int) currentItemModel.getPressure());
        weather.setUvIndex((int) currentItemModel.getUvIndex());

        if (weatherModel.getDaily() != null && weatherModel.getDaily().size() != 0) {
            WeatherItemModel todayItemModel = weatherModel.getDaily().get(0);
            weather.setTemperatureMin((int) todayItemModel.getTemperatureMin());
            weather.setTemperatureMax((int) todayItemModel.getTemperatureMax());
            weather.setSunriseTime(todayItemModel.getSunriseTime());
            weather.setSunsetTime(todayItemModel.getSunsetTime());
        }

        List<WeatherItemHour> weatherItemHourList = new ArrayList<>();
        for (WeatherItemModel hourItemModel: weatherModel.getHourly()) {
            WeatherItemHour weatherItemHour = new WeatherItemHour();
            weatherItemHour.setTime(hourItemModel.getTime());
            weatherItemHour.setIcon(hourItemModel.getIcon());
            weatherItemHour.setHumidity((int) (hourItemModel.getHumidity() * PERCENT));
            weatherItemHour.setTemperature((int) hourItemModel.getTemperature());
            weatherItemHourList.add(weatherItemHour);
        }
        weather.setHourly(weatherItemHourList);

        List<WeatherItemDay> weatherItemDayList = new ArrayList<>();
        for (WeatherItemModel dayItemModel: weatherModel.getDaily()) {
            WeatherItemDay weatherItemDay = new WeatherItemDay();
            weatherItemDay.setTime(dayItemModel.getTime());
            weatherItemDay.setIcon(dayItemModel.getIcon());
            weatherItemDay.setTemperatureMin((int) dayItemModel.getTemperatureMin());
            weatherItemDay.setTemperatureMax((int) dayItemModel.getTemperatureMax());
            weatherItemDay.setSunrise(dayItemModel.getSunriseTime());
            weatherItemDay.setSunset(dayItemModel.getSunsetTime());
            weatherItemDayList.add(weatherItemDay);
        }
        weather.setDaily(weatherItemDayList);

        return weather;
    }
}
