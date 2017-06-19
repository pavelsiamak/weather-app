package com.pavelsemak.weatherapp.adapter;

import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pavelsemak.weatherapp.R;
import com.pavelsemak.weatherapp.model.City;
import com.pavelsemak.weatherapp.model.Weather;

import java.util.ArrayList;
import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {
    private List<City> cityList = new ArrayList<>();

    private OnCityItemClickListener onCityItemClickListener;
    private WeatherLiveDataProvider weatherLiveDataProvider;

    public CitiesAdapter(WeatherLiveDataProvider weatherLiveDataProvider) {
        this.weatherLiveDataProvider = weatherLiveDataProvider;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        City city = cityList.get(position);
        holder.cityNameTextView.setText(city.getName());

        weatherLiveDataProvider.provideLiveWeather(city, new WeatherLiveDataListener() {
            @Override
            public void onWeatherReceived(Weather weather) {
                holder.temperature.setText(String.valueOf(weather.getTemperature()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public void setData(List<City> cityList) {
        this.cityList = cityList;
        notifyDataSetChanged();
    }

    private void onItemClicked(int position) {
        if (onCityItemClickListener != null) {
            onCityItemClickListener.onCityItemClick(position);
        }
    }

    public void setOnCityItemClickListener(OnCityItemClickListener onCityItemClickListener) {
        this.onCityItemClickListener = onCityItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cityNameTextView;
        public TextView temperature;
        public ViewHolder(View view) {
            super(view);
            cityNameTextView = view.findViewById(R.id.item_city_name);
            temperature = view.findViewById(R.id.temperature_city_item);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClicked(getAdapterPosition());
                }
            });
        }
    }

    public interface WeatherLiveDataProvider {
        void provideLiveWeather(City city, WeatherLiveDataListener weatherLiveDataListener);
    }

    public interface OnCityItemClickListener {
        void onCityItemClick(int position);
    }

    public interface WeatherLiveDataListener {
        void onWeatherReceived(Weather weather);
    }
}
