package com.pavelsemak.weatherapp.wrapper;

public class DataWrapper<T> {

    private T data;
    private Throwable error;

    public DataWrapper(T data, Throwable error) {
        this.data = data;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
