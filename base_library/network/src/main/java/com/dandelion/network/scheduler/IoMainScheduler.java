package com.dandelion.network.scheduler;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * io线程执行请求，主线程处理订阅结果的Scheduler
 * Created by lin.wang on 2021/6/30
 */
public class IoMainScheduler<T> extends BaseScheduler<T> {

    public IoMainScheduler() {
        super(Schedulers.io(), AndroidSchedulers.mainThread());
    }
}
