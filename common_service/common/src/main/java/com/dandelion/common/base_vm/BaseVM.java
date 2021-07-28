package com.dandelion.common.base_vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


public class BaseVM extends AndroidViewModel implements LifecycleObserver {

    private CompositeDisposable mCompositeDisposable;

    public BaseVM(@NonNull Application application) {
        super(application);
    }

    protected void addDisposable(Disposable disposable) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny(LifecycleOwner owner, Lifecycle.Event event){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){

    }
}
