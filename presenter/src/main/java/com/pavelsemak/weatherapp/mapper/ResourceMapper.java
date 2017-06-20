package com.pavelsemak.weatherapp.mapper;

import com.pavelsemak.weatherapp.R;

public class ResourceMapper {

    public static int getResource(String id) {
        switch (id) {
            case "clear-day":
                return R.drawable.clear_day;
            case "clear-night":
                return R.drawable.clear_night;
            case "rain":
                return R.drawable.rain;
            case "snow":
                return R.drawable.snow;
            case "sleet":
                return R.drawable.sleet;
            case "wind":
                return R.drawable.wind;
            case "fog":
                return R.drawable.fog;
            case "cloudy":
                return R.drawable.cloudy;
            case "partly-cloudy-day":
                return R.drawable.partly_cloudy_day;
            case "partly-cloudy-night":
                return R.drawable.partly_cloudy_night;
            case "sunset":
                return R.drawable.sunset;
            case "sunrise":
                return R.drawable.sunrise;
            default:
                return R.drawable.clear_day;
        }
    }

}
