package com.pavelsemak.weatherapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pavelsemak.weatherapp.model.City;
import com.pavelsemak.weatherapp.view.WeatherPageFragment;

import java.util.ArrayList;
import java.util.List;

public class WeatherPagerAdapter extends FragmentStatePagerAdapter {
    private List<City> cityList = new ArrayList<>();

    public WeatherPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return WeatherPageFragment.newInstance(cityList.get(position));
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    public void setData(List<City> cityList) {
        this.cityList = cityList;
        notifyDataSetChanged();
    }
}
