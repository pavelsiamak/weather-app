package com.pavelsemak.weatherapp.data.model;

public class WeatherResponse {

    private double latitude;
    private double longitude;

    private float offset;

    private WeatherItemResponse currently;
    private WeatherBlockResponse hourly;
    private WeatherBlockResponse daily;

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
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
