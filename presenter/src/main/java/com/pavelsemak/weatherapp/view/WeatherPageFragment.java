package com.pavelsemak.weatherapp.view;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pavelsemak.weatherapp.R;
import com.pavelsemak.weatherapp.model.City;
import com.pavelsemak.weatherapp.model.Weather;
import com.pavelsemak.weatherapp.viewmodel.WeatherViewModel;
import com.pavelsemak.weatherapp.wrapper.DataWrapper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherPageFragment extends LifecycleFragment {

    public static final String CITY_PARAM = "city_param";

    private City city;

    @BindView(R.id.weather_temperature_text)
    TextView textView;

    @BindView(R.id.weather_city_name)
    TextView cityTextView;

    @Inject
    WeatherViewModel weatherViewModel;

    @BindView(R.id.progress_weather_page)
    ViewGroup progressBar;

    public static WeatherPageFragment newInstance(City city) {
        WeatherPageFragment myFragment = new WeatherPageFragment();

        Bundle args = new Bundle();
        args.putParcelable(CITY_PARAM, city);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        city = getArguments().getParcelable(CITY_PARAM);
        ((MainActivity) getActivity()).getWeatherComponent().inject(this);
        ((MainActivity) getActivity()).getWeatherComponent().inject(weatherViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_page_layout, container, false);
        ButterKnife.bind(this, view);
        cityTextView.setText(city.getName());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        weatherViewModel.getWeather(city).observe(this, new Observer<DataWrapper<Weather>>() {
            @Override
            public void onChanged(@Nullable DataWrapper<Weather> weatherDataWrapper) {
                if (weatherDataWrapper != null && weatherDataWrapper.getData() != null
                        && weatherDataWrapper.getError() == null) {
                    textView.setText(String.valueOf(weatherDataWrapper.getData().getTemperature()));
                    hideProgress();
                } else {
                    // TODO weather not loaded
                }
            }
        });
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
