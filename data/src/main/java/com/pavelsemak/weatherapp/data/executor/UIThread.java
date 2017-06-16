package com.pavelsemak.weatherapp.data.executor;

import com.pavelsemak.weatherapp.domain.executor.PostExecutionThread;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

@Singleton
public class UIThread implements PostExecutionThread {

    @Inject
    UIThread() {}

    @Override public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}