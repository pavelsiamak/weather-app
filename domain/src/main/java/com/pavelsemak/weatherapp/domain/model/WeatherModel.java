package com.pavelsemak.weatherapp.domain.model;

import java.util.List;

public class WeatherModel {
    private float latitude;
    private float longitude;

    private WeatherItemModel currently;
    private List<WeatherItemModel> hourly;
    private List<WeatherItemModel> daily;

    public WeatherItemModel getCurrently() {
        return currently;
    }

    public void setCurrently(WeatherItemModel currently) {
        this.currently = currently;
    }

    public List<WeatherItemModel> getHourly() {
        return hourly;
    }

    public void setHourly(List<WeatherItemModel> hourly) {
        this.hourly = hourly;
    }

    public List<WeatherItemModel> getDaily() {
        return daily;
    }

    public void setDaily(List<WeatherItemModel> daily) {
        this.daily = daily;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
