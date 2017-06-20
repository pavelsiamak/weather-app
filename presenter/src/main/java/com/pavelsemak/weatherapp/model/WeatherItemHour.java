package com.pavelsemak.weatherapp.model;

public class WeatherItemHour {

    private int humidity;
    private long time;
    private String displayTime;
    private int temperature;
    private String icon;
    private boolean isSunset;
    private boolean isSunrise;

    public String getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    public boolean isSunset() {
        return isSunset;
    }

    public void setSunset(boolean sunset) {
        isSunset = sunset;
    }

    public boolean isSunrise() {
        return isSunrise;
    }

    public void setSunrise(boolean sunrise) {
        isSunrise = sunrise;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
