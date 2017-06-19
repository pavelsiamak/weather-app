package com.pavelsemak.weatherapp.domain.interactor;

import com.pavelsemak.weatherapp.domain.executor.PostExecutionThread;
import com.pavelsemak.weatherapp.domain.executor.ThreadExecutor;
import com.pavelsemak.weatherapp.domain.model.WeatherModel;
import com.pavelsemak.weatherapp.domain.repository.WeatherRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetWeather extends UseCase<WeatherModel, GetWeather.Params> {

    private WeatherRepository weatherRepository;

    @Inject
    GetWeather(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
               WeatherRepository weatherRepository) {
        super(threadExecutor, postExecutionThread);
        this.weatherRepository = weatherRepository;
    }


    @Override
    Observable<WeatherModel> buildUseCaseObservable(Params params) {
        String locationString = createLocationString(params);
        return weatherRepository.getWeather(locationString);
    }

    private String createLocationString(Params params) {
        return params.latitude + ", " + params.longitude;
    }

    public static final class Params {
        private final double latitude;
        private final double longitude;

        private Params(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public static Params forWeather(double latitude, double longitude) {
            return new Params(latitude, longitude);
        }
    }
}
