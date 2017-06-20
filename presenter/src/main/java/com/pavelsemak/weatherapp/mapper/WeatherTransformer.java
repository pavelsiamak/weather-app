package com.pavelsemak.weatherapp.mapper;

import android.util.SparseArray;

import com.pavelsemak.weatherapp.model.Weather;
import com.pavelsemak.weatherapp.model.WeatherItemDay;
import com.pavelsemak.weatherapp.model.WeatherItemHour;
import com.pavelsemak.weatherapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class WeatherTransformer {

    @Inject
    WeatherTransformer() {}

    public Weather transform(Weather weather) {
        List<WeatherItemHour> hourly = weather.getHourly();
        List<WeatherItemDay> daily = weather.getDaily();

        float offset = weather.getOffset();
        weather.setTime(Utils.translateTime(weather.getTime(), offset));
        weather.setSunriseTime(Utils.translateTime(weather.getSunriseTime(), offset));
        weather.setSunsetTime(Utils.translateTime(weather.getSunsetTime(), offset));

        for(WeatherItemHour weatherItemHour: weather.getHourly()) {
            weatherItemHour.setTime(Utils.translateTime(weatherItemHour.getTime(), offset));
            weatherItemHour.setDisplayTime(Utils.longToHourString(weatherItemHour.getTime()));
        }

        for (WeatherItemDay weatherItemDay: daily) {
            weatherItemDay.setTime(Utils.translateTime(weatherItemDay.getTime(), offset));
            weatherItemDay.setSunrise(Utils.translateTime(weatherItemDay.getSunrise(), offset));
            weatherItemDay.setSunset(Utils.translateTime(weatherItemDay.getSunset(), offset));
            weatherItemDay.setDayOfWeek(Utils.dayOfWeekFromTime(weatherItemDay.getTime()));
        }

        SparseArray<WeatherItemHour> insertMap = new SparseArray<>();
        for (int i = 0, j = 0; i < hourly.size() - 2 && j < daily.size(); ++i) {
            if (hourly.get(i).getTime() <= daily.get(j).getSunrise()
                    && daily.get(j).getSunrise() <= hourly.get(i + 1).getTime()) {
                WeatherItemHour weatherItemHour = new WeatherItemHour();
                weatherItemHour.setTime(daily.get(j).getSunrise());
                weatherItemHour.setDisplayTime(Utils.longToTimeString(weatherItemHour.getTime()));
                weatherItemHour.setSunrise(true);
                weatherItemHour.setIcon("sunrise");
                insertMap.put(i + 1, weatherItemHour);
                continue;
            }
            if (hourly.get(i).getTime() < daily.get(j).getSunset()
                    && hourly.get(i + 1).getTime() > daily.get(j).getSunset()) {
                WeatherItemHour weatherItemHour = new WeatherItemHour();
                weatherItemHour.setTime(daily.get(j).getSunset());
                weatherItemHour.setDisplayTime(Utils.longToTimeString(weatherItemHour.getTime()));
                weatherItemHour.setSunset(true);
                weatherItemHour.setIcon("sunset");
                insertMap.put(i + 1, weatherItemHour);
                ++j;
            }
        }
        for (int i = hourly.size() - 1; i >= 0; --i) {
            if (insertMap.get(i) != null) {
                hourly.add(i, insertMap.get(i));
            }
        }

        daily.remove(0);

        List<String> infoList = new ArrayList<>();
        infoList.add(Utils.longToTimeString(weather.getSunriseTime()));
        infoList.add(Utils.longToTimeString(weather.getSunsetTime()));

        infoList.add(String.valueOf(weather.getPrecipProbability()));
        infoList.add(String.valueOf(weather.getHumidity()));

        infoList.add(String.valueOf(weather.getWindSpeed()));
        infoList.add(String.valueOf(weather.getApparentTemperature()));

        infoList.add(String.valueOf(weather.getPressure()));
        infoList.add(String.valueOf(weather.getUvIndex()));
        weather.setInfoList(infoList);

        return weather;
    }
}
