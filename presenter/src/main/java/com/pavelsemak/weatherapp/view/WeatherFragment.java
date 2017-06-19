package com.pavelsemak.weatherapp.view;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pavelsemak.weatherapp.R;
import com.pavelsemak.weatherapp.adapter.WeatherPagerAdapter;
import com.pavelsemak.weatherapp.model.City;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherFragment extends BaseCityFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.fab_open_cities)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.progress_weather)
    ViewGroup progressBar;

    private WeatherPagerAdapter weatherPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).getWeatherComponent().inject(this);
        ((MainActivity) getActivity()).getWeatherComponent().inject(cityViewModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather_layout, container, false);
        ButterKnife.bind(this, view);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCityListFragment();
            }
        });

        return view;
    }

    public void openCityListFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new CityListFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weatherPagerAdapter = new WeatherPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(weatherPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                cityViewModel.onActiveCityIndexChanged(position);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startObservingActiveCityIndex();
    }

    @Override
    public void onCitiesReceived(List<City> cityList) {
        weatherPagerAdapter.setData(cityList);
    }

    public void startObservingActiveCityIndex() {
        cityViewModel.getActiveCityIndex().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer index) {
                if (index != null) {
                    viewPager.setCurrentItem(index);
                }
            }
        });
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    public void onErrorDetectingCity() {
        Toast.makeText(getContext(), "Unable to detect the city", Toast.LENGTH_LONG).show();
        goBack();
    }
}
