package com.pavelsemak.weatherapp.domain.interactor;

import com.pavelsemak.weatherapp.domain.executor.PostExecutionThread;
import com.pavelsemak.weatherapp.domain.executor.ThreadExecutor;
import com.pavelsemak.weatherapp.domain.model.CityModel;
import com.pavelsemak.weatherapp.domain.repository.CityRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AddCity extends UseCase<Boolean, AddCity.Params> {

    private CityRepository cityRepository;

    @Inject
    AddCity(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, CityRepository cityRepository) {
        super(threadExecutor, postExecutionThread);
        this.cityRepository = cityRepository;
    }

    @Override
    Observable<Boolean> buildUseCaseObservable(Params params) {
        return cityRepository.addCity(params.cityModel);
    }

    public static final class Params {
        private final CityModel cityModel;

        private Params(CityModel cityModel) {
            this.cityModel = cityModel;
        }

        public static AddCity.Params forNewCity(CityModel cityModel) {
            return new AddCity.Params(cityModel);
        }
    }
}
