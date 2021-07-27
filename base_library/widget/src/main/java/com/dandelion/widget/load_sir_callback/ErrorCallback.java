package com.dandelion.widget.load_sir_callback;

import android.content.Context;
import android.view.View;

import com.dandelion.widget.R;

/**
 * 加载错误
 * Created by lin.wang on 2021.07.18
 */
public class ErrorCallback extends BaseCallback {

    @Override
    protected int onCreateView() {
        return R.layout.widget_loadsir_error;
    }

    //当前Callback的点击事件，如果返回true则覆盖注册时的onReload()，如果返回false则两者都执行，先执行onReloadEvent()。
    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return false;
    }

    @Override
    protected int bindRetryViewId() {
        return R.id.tv_reload;
    }
}