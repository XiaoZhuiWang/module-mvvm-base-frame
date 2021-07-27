package com.dandelion.login;

import android.content.Context;
import android.util.Log;

import com.hm.iou.lifecycle.annotation.AppLifecycle;
import com.hm.lifecycle.api.IApplicationLifecycleCallbacks;

/**
 * 接收Application生命周期回调
 * Created by lin.wang on 2021/6/23.
 */
@AppLifecycle
public class LoginAppCallback implements IApplicationLifecycleCallbacks {
    @Override
    public int getPriority() {
        return NORM_PRIORITY;
    }

    @Override
    public void onCreate(Context context) {
        //可在此处做初始化任务，相当于Application的onCreate方法
        Log.d("app", "login app onCreate");
    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int level) {

    }
}
