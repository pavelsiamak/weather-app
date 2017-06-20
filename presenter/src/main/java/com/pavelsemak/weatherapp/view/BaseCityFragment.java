package com.pavelsemak.weatherapp.view;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.pavelsemak.weatherapp.model.City;
import com.pavelsemak.weatherapp.viewmodel.CityViewModel;
import com.pavelsemak.weatherapp.wrapper.DataWrapper;

import java.util.List;

import javax.inject.Inject;

public abstract class BaseCityFragment extends LifecycleFragment {

    public static final int PLACES_REQUEST_CODE = 1;
    public static final int LOCATION_PERMISSIONS_REQUEST = 2;

    @Inject
    CityViewModel cityViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSIONS_REQUEST);
            return;
        } else {
            startObservingCities();
        }
    }

    protected void openPlaceSearch() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(getActivity());
            startActivityForResult(intent, PLACES_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            onErrorDetectingCity();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACES_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                showProgress();
                cityViewModel.onNewPlaceReceived(place);
            } else {
                onErrorDetectingCity();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSIONS_REQUEST) {
            startObservingCities();
        }
    }

    private void startObservingCities() {
        cityViewModel.getCities().observe(this, new Observer<DataWrapper<List<City>>>() {
            @Override
            public void onChanged(@Nullable DataWrapper<List<City>> dataWrapper) {
                if (dataWrapper != null && dataWrapper.getData() != null
                        && dataWrapper.getError() == null) {
                    hideProgress();
                    onCitiesReceived(dataWrapper.getData());
                } else {
                    openPlaceSearch();
                }
            }
        });
    }

    public abstract void showProgress();

    public abstract void hideProgress();

    public abstract void onErrorDetectingCity();

    public void goBack() {
        getActivity().onBackPressed();
    }

    public abstract void onCitiesReceived(List<City> cityList);

}
