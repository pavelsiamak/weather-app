package com.pavelsemak.weatherapp.view;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pavelsemak.weatherapp.R;
import com.pavelsemak.weatherapp.adapter.DailyAdapter;
import com.pavelsemak.weatherapp.adapter.HourlyAdapter;
import com.pavelsemak.weatherapp.adapter.InfoAdapter;
import com.pavelsemak.weatherapp.model.City;
import com.pavelsemak.weatherapp.model.Weather;
import com.pavelsemak.weatherapp.utils.Utils;
import com.pavelsemak.weatherapp.viewmodel.WeatherViewModel;
import com.pavelsemak.weatherapp.wrapper.DataWrapper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherPageFragment extends LifecycleFragment {

    public static final String CITY_PARAM = "city_param";

    private City city;

    @BindView(R.id.progress_weather_page)
    ViewGroup progressBar;

    @BindView(R.id.weather_loading_error)
    TextView loadingError;

    @BindView(R.id.weather_city_name)
    TextView cityTextView;

    @BindView(R.id.weather_summary)
    TextView summary;

    @BindView(R.id.weather_temperature_text)
    TextView temperature;

    @BindView(R.id.weather_day)
    TextView dayOfWeek;

    @BindView(R.id.weather_max)
    TextView temperatureMax;

    @BindView(R.id.weather_min)
    TextView temperatureMin;

    @BindView(R.id.weather_hourly_recycler)
    RecyclerView hourlyRecycler;

    @BindView(R.id.weather_daily_recycler)
    RecyclerView dailyRecycler;

    @BindView(R.id.weather_summary_large)
    TextView summaryLarge;

    @BindView(R.id.weather_info_recycler)
    RecyclerView infoRecycler;

    @Inject
    WeatherViewModel weatherViewModel;

    private HourlyAdapter hourlyAdapter;
    private DailyAdapter dailyAdapter;
    private InfoAdapter infoAdapter;

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
        setRetainInstance(true);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManagerHour = new LinearLayoutManager(getContext());
        layoutManagerHour.setOrientation(LinearLayoutManager.HORIZONTAL);
        hourlyRecycler.setLayoutManager(layoutManagerHour);

        hourlyAdapter = new HourlyAdapter(getResources());
        hourlyRecycler.setAdapter(hourlyAdapter);

        LinearLayoutManager layoutManagerDay = new LinearLayoutManager(getContext());
        dailyRecycler.setLayoutManager(layoutManagerDay);

        dailyAdapter = new DailyAdapter();
        dailyRecycler.setAdapter(dailyAdapter);

        LinearLayoutManager layoutManagerInfo = new LinearLayoutManager(getContext());
        infoRecycler.setLayoutManager(layoutManagerInfo);

        infoAdapter = new InfoAdapter(getResources().getStringArray(R.array.info_labels),
                getResources().getStringArray(R.array.info_units));
        infoRecycler.setAdapter(infoAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        weatherViewModel.getWeather(city).observe(this, new Observer<DataWrapper<Weather>>() {
            @Override
            public void onChanged(@Nullable DataWrapper<Weather> weatherDataWrapper) {
                if (weatherDataWrapper != null && weatherDataWrapper.getData() != null
                        && weatherDataWrapper.getError() == null) {
                    onWeatherLoaded(weatherDataWrapper.getData());
                    hideProgress();
                } else {
                    showLoadingError();
                }
            }
        });
    }

    public void onWeatherLoaded(Weather weather) {
        temperature.setText(String.valueOf(weather.getTemperature()));
        summary.setText(weather.getSummary());
        hourlyAdapter.setData(weather.getHourly());
        dailyAdapter.setData(weather.getDaily());
        infoAdapter.setData(weather.getInfoList());
        summaryLarge.setText(String.format(getString(R.string.summary_large), weather.getSummary(),
                weather.getTemperature(), weather.getTemperatureMax(), weather.getTemperatureMin()));
        dayOfWeek.setText(Utils.dayOfWeekFromTime(weather.getTime()));
        temperatureMax.setText(String.valueOf(weather.getTemperatureMax()));
        temperatureMin.setText(String.valueOf(weather.getTemperatureMin()));
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        loadingError.setVisibility(View.GONE);
    }

    public void showLoadingError() {
        loadingError.setVisibility(View.VISIBLE);
        hideProgress();
    }

}
