package com.pavelsemak.weatherapp.domain.interactor;

import com.pavelsemak.weatherapp.domain.executor.PostExecutionThread;
import com.pavelsemak.weatherapp.domain.executor.ThreadExecutor;
import com.pavelsemak.weatherapp.domain.model.CityModel;
import com.pavelsemak.weatherapp.domain.repository.CityRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetCurrentCity extends UseCase<Boolean, Void> {

    private CityRepository cityRepository;

    @Inject
    GetCurrentCity(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, CityRepository cityRepository) {
        super(threadExecutor, postExecutionThread);
        this.cityRepository = cityRepository;
    }

    @Override
    Observable<Boolean> buildUseCaseObservable(Void aVoid) {
        return cityRepository.updateCurrentCity();
    }
}
