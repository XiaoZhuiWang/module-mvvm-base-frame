package com.dandelion.common;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dandelion.utils.AppProvider;
import com.dandelion.utils.LogUtil;
import com.dandelion.utils.ProcessUtil;
import com.dandelion.widget.load_sir_callback.ErrorCallback;
import com.dandelion.widget.load_sir_callback.LoadingCallback;
import com.dandelion.widget.load_sir_callback.NoDataCallback;
import com.dandelion.widget.load_sir_callback.NoNetCallback;
import com.hjq.toast.ToastUtils;
import com.hm.lifecycle.api.ApplicationLifecycleManager;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadSir;
import com.tencent.mmkv.MMKV;

/**
 * Application
 * Created by lin.wang on 2021/6/23.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String processName = ProcessUtil.getProcessName(this);
        if (processName != null) {
            if (processName.equals(getPackageName())) {
                // 注册application
                AppProvider.inject(this);
                // 初始化ToastUtils
                ToastUtils.init(this);
                // 初始化LogUtil
                LogUtil.init(BuildConfig.DEBUG);
                //初始化ARouter
                if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
                    ARouter.openLog();     // 打印日志
                    ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
                }
                ARouter.init(this);
                //配置LoadSir
                LoadSir.beginBuilder()
                        .addCallback(new LoadingCallback())
                        .addCallback(new NoNetCallback())
                        .addCallback(new ErrorCallback())
                        .addCallback(new NoDataCallback())
                        .setDefaultCallback(SuccessCallback.class)
                        .commit();
                //初始化MMKV
                String rootDir = MMKV.initialize(this);
                LogUtil.d("mmkv root: " + rootDir);
                //初始化并调用Application生命周期分发管理器相关方法
                ApplicationLifecycleManager.init();
                ApplicationLifecycleManager.onCreate(this);
            }
        }

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ApplicationLifecycleManager.onTerminate();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ApplicationLifecycleManager.onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ApplicationLifecycleManager.onLowMemory();
    }
}
