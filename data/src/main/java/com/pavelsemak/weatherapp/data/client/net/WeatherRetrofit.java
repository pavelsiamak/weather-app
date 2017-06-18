package com.pavelsemak.weatherapp.data.client.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.pavelsemak.weatherapp.data.utils.Constant;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRetrofit {

    private Retrofit retrofit;

    @Inject
    WeatherRetrofit() {
        this.createClient();
    }

    private void createClient() {
        Gson gson = new GsonBuilder().create();

        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.create();

        OkHttpClient client = new OkHttpClient.Builder().build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
