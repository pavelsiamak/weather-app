package com.pavelsemak.weatherapp.domain.interactor;

import com.pavelsemak.weatherapp.domain.executor.PostExecutionThread;
import com.pavelsemak.weatherapp.domain.executor.ThreadExecutor;
import com.pavelsemak.weatherapp.domain.model.CityModel;
import com.pavelsemak.weatherapp.domain.repository.CityRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class GetCities extends UseCase<List<CityModel>, Void> {

    private CityRepository cityRepository;

    @Inject
    GetCities(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, CityRepository cityRepository) {
        super(threadExecutor, postExecutionThread);
        this.cityRepository = cityRepository;
    }

    @Override
    Observable<List<CityModel>> buildUseCaseObservable(Void aVoid) {
        return cityRepository.updateCurrentCity().flatMap(new Function<Boolean, ObservableSource<List<CityModel>>>() {
            @Override
            public ObservableSource<List<CityModel>> apply(@NonNull Boolean updated) throws Exception {
                return cityRepository.getSavedCities();
            }
        });
    }

}
