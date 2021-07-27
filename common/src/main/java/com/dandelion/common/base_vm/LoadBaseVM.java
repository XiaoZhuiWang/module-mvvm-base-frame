package com.dandelion.common.base_vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.dandelion.network.constant.LoadStatusCode;


public abstract class LoadBaseVM extends BaseVM {

    // 观察加载状态，Integer的取值只能为LoadResultCode中的值
    protected final MutableLiveData<Integer> loadStatusLiveData;

    public LoadBaseVM(@NonNull Application application) {
        super(application);
        loadStatusLiveData = new MutableLiveData<>(LoadStatusCode.LOAD_STATUS_NO_STATUS);
    }

    /**
     * 加载初始数据
     */
    public abstract void onLoadInitData();

    public MutableLiveData<Integer> getLoadStatusLiveData() {
        return loadStatusLiveData;
    }
}
