package com.pavelsemak.weatherapp.domain.executor;

import io.reactivex.Scheduler;

public interface ThreadExecutor {
    Scheduler getScheduler();
}
