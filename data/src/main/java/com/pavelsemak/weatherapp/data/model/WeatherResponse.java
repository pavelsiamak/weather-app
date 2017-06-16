package com.pavelsemak.weatherapp.data.model;

public class WeatherResponse {

    private float latitude;
    private float longitude;

    private WeatherItemResponse currently;
    private WeatherBlockResponse hourly;
    private WeatherBlockResponse daily;

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

    public WeatherItemResponse getCurrently() {
        return currently;
    }

    public void setCurrently(WeatherItemResponse currently) {
        this.currently = currently;
    }

    public WeatherBlockResponse getHourly() {
        return hourly;
    }

    public void setHourly(WeatherBlockResponse hourly) {
        this.hourly = hourly;
    }

    public WeatherBlockResponse getDaily() {
        return daily;
    }

    public void setDaily(WeatherBlockResponse daily) {
        this.daily = daily;
    }
}
