package com.dandelion.network.scheduler;

import org.reactivestreams.Publisher;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.CompletableTransformer;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.core.MaybeTransformer;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.core.SingleTransformer;

/**
 * 针对各种RxJava的场景线程切换
 * Created by lin.wang on 2021/6/30
 */
public class BaseScheduler<T> implements ObservableTransformer<T,T>,
        MaybeTransformer<T, T>,
        FlowableTransformer<T, T>,
        CompletableTransformer,
        SingleTransformer<T,T> {

    private Scheduler subscribeOnScheduler;
    private Scheduler observeOnScheduler;

    protected BaseScheduler(Scheduler subscribeOnScheduler, Scheduler observeOnScheduler) {
        this.subscribeOnScheduler = subscribeOnScheduler;
        this.observeOnScheduler = observeOnScheduler;
    }


    @Override
    public CompletableSource apply(Completable upstream) {
        return upstream.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler);
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler);
    }

    @Override
    public MaybeSource<T> apply(Maybe<T> upstream) {
        return upstream.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler);
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler);
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream) {
        return upstream.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler);
    }
}
