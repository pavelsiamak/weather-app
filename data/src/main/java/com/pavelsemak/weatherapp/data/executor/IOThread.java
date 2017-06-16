package com.pavelsemak.weatherapp.data.executor;

import com.pavelsemak.weatherapp.domain.executor.ThreadExecutor;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class IOThread implements ThreadExecutor {

    @Inject
    IOThread() {}

    @Override
    public Scheduler getScheduler() {
        return Schedulers.io();
    }
}
