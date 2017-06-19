package com.pavelsemak.weatherapp.view;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pavelsemak.weatherapp.R;
import com.pavelsemak.weatherapp.adapter.CitiesAdapter;
import com.pavelsemak.weatherapp.model.City;
import com.pavelsemak.weatherapp.model.Weather;
import com.pavelsemak.weatherapp.viewmodel.WeatherViewModel;
import com.pavelsemak.weatherapp.wrapper.DataWrapper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityListFragment extends BaseCityFragment
        implements CitiesAdapter.OnCityItemClickListener, CitiesAdapter.WeatherLiveDataProvider {

    @BindView(R.id.cities_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.fab_add_city)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.progress_city)
    ViewGroup progressBar;

    @Inject
    WeatherViewModel weatherViewModel;

    CitiesAdapter citiesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).getWeatherComponent().inject(this);
        ((MainActivity) getActivity()).getWeatherComponent().inject(cityViewModel);
        ((MainActivity) getActivity()).getWeatherComponent().inject(weatherViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_list_layout, container, false);
        ButterKnife.bind(this, view);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlaceSearch();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());

        recyclerView.addItemDecoration(dividerItemDecoration);

        citiesAdapter = new CitiesAdapter(this);
        citiesAdapter.setOnCityItemClickListener(this);
        recyclerView.setAdapter(citiesAdapter);
    }

    @Override
    public void provideLiveWeather(City city, final CitiesAdapter.WeatherLiveDataListener weatherLiveDataListener) {
        weatherViewModel.getWeather(city).observe(this, new Observer<DataWrapper<Weather>>() {
            @Override
            public void onChanged(@Nullable DataWrapper<Weather> weatherDataWrapper) {
                if (weatherDataWrapper != null && weatherDataWrapper.getData() != null
                        && weatherDataWrapper.getError() == null) {
                    weatherLiveDataListener.onWeatherReceived(weatherDataWrapper.getData());
                }
            }
        });
    }

    @Override
    public void onCitiesReceived(List<City> cityList) {
        citiesAdapter.setData(cityList);
    }

    @Override
    public void onCityItemClick(int position) {
        cityViewModel.onActiveCityIndexChanged(position);
        goBack();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    public void onErrorDetectingCity() {
    }
}
