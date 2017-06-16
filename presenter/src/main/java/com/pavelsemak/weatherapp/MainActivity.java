package com.pavelsemak.weatherapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.pavelsemak.weatherapp.di.components.ApplicationComponent;
import com.pavelsemak.weatherapp.domain.interactor.GetWeather;
import com.pavelsemak.weatherapp.domain.model.WeatherModel;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

public class MainActivity extends AppCompatActivity {

    //TODO just for testing
    @Inject
    GetWeather getWeatherUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getApplicationComponent().inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((WeatherApplication) getApplication()).getApplicationComponent();
    }
}
