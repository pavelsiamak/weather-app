package com.pavelsemak.weatherapp.data.model;

import java.util.List;

public class WeatherBlockResponse {
    private List<WeatherItemResponse> data;

    public List<WeatherItemResponse> getData() {
        return data;
    }

    public void setData(List<WeatherItemResponse> data) {
        this.data = data;
    }
}
