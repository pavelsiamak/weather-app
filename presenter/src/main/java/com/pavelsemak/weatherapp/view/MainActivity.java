package com.pavelsemak.weatherapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.pavelsemak.weatherapp.R;
import com.pavelsemak.weatherapp.WeatherApplication;
import com.pavelsemak.weatherapp.di.components.ApplicationComponent;
import com.pavelsemak.weatherapp.di.components.DaggerWeatherComponent;
import com.pavelsemak.weatherapp.di.components.WeatherComponent;
import com.pavelsemak.weatherapp.di.modules.ActivityModule;

public class MainActivity extends AppCompatActivity {

    private WeatherComponent weatherComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
        this.initializeInjector();
        setContentView(R.layout.activity_main);
        openWeatherFragment();
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

    public void openWeatherFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new WeatherFragment())
                .commitAllowingStateLoss();
    }
}
