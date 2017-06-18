package com.pavelsemak.weatherapp.view;

import android.Manifest;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.pavelsemak.weatherapp.R;
import com.pavelsemak.weatherapp.WeatherApplication;
import com.pavelsemak.weatherapp.di.components.ApplicationComponent;
import com.pavelsemak.weatherapp.di.components.DaggerWeatherComponent;
import com.pavelsemak.weatherapp.di.components.WeatherComponent;
import com.pavelsemak.weatherapp.di.modules.ActivityModule;
import com.pavelsemak.weatherapp.di.modules.ApplicationModule;
import com.pavelsemak.weatherapp.domain.interactor.GetCities;
import com.pavelsemak.weatherapp.domain.interactor.GetWeather;
import com.pavelsemak.weatherapp.domain.model.CityModel;
import com.pavelsemak.weatherapp.domain.model.WeatherModel;
import com.pavelsemak.weatherapp.model.City;
import com.pavelsemak.weatherapp.viewmodel.CityViewModel;
import com.pavelsemak.weatherapp.viewmodel.WeatherViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

public class MainActivity extends AppCompatActivity {

    //TODO just for testing
    @Inject
    GetWeather getWeatherUseCase;

    //TODO just for testing
    @Inject
    GetCities getCities;

    private WeatherComponent weatherComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
        this.initializeInjector();
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //TODO just for testing
                getWeatherUseCase.execute(new DisposableObserver<WeatherModel>() {
                    @Override
                    public void onNext(@NonNull WeatherModel weatherModel) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }, GetWeather.Params.forWeather(53.9045f, 27.5615f));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent =
                    null;
            try {
                intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
            startActivityForResult(intent, 123);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((WeatherApplication) getApplication()).getApplicationComponent();
    }

    private void initializeInjector() {
        weatherComponent = DaggerWeatherComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    public WeatherComponent getWeatherComponent() {
        return weatherComponent;
    }
}
