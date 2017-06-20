package com.pavelsemak.weatherapp.adapter;

import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pavelsemak.weatherapp.R;
import com.pavelsemak.weatherapp.model.City;
import com.pavelsemak.weatherapp.model.Weather;
import com.pavelsemak.weatherapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {
    private List<City> cityList = new ArrayList<>();

    private OnCityItemClickListener onCityItemClickListener;
    private WeatherLiveDataProvider weatherLiveDataProvider;

    private Resources resources;

    public CitiesAdapter(WeatherLiveDataProvider weatherLiveDataProvider, Resources resources) {
        this.weatherLiveDataProvider = weatherLiveDataProvider;
        this.resources = resources;
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
                holder.temperature.setText(String.valueOf(String
                        .format(resources.getString(R.string.temperature), weather.getTemperature())));
                holder.itemTime.setText(Utils.longToTimeString(weather.getTime()));
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
        @BindView(R.id.item_city_name)
        TextView cityNameTextView;
        @BindView(R.id.temperature_city_item)
        TextView temperature;
        @BindView(R.id.item_time)
        TextView itemTime;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
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
