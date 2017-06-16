package com.pavelsemak.weatherapp.domain.interactor;

import com.pavelsemak.weatherapp.domain.executor.PostExecutionThread;
import com.pavelsemak.weatherapp.domain.executor.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public abstract class UseCase<T, Params> {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private final CompositeDisposable disposables;

    UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    public void execute(DisposableObserver<T> observer, Params params) {
       Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(threadExecutor.getScheduler())
                .observeOn(postExecutionThread.getScheduler());
        disposables.add(observable.subscribeWith(observer));
    }

    abstract Observable<T> buildUseCaseObservable(Params params);

    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
