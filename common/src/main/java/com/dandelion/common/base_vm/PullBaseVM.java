package com.dandelion.common.base_vm;

import android.app.Application;

import androidx.annotation.NonNull;


public abstract class PullBaseVM extends LoadBaseVM {

    public PullBaseVM(@NonNull Application application) {
        super(application);
    }

    /**
     * 刷新数据
     */
    public abstract void onRefresh();

    /**
     * 加载更多数据
     */
    public abstract void onLoadMore();
}
