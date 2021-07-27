package com.dandelion.widget.load_sir_callback;

import android.content.Context;
import android.view.View;

import com.dandelion.widget.R;

/**
 * 没有网络
 * Created by lin.wang on 2021.07.18
 */
public class NoNetCallback extends BaseCallback {

    @Override
    protected int onCreateView() {
        return R.layout.widget_loadsir_no_net;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return false;
    }

    @Override
    protected int bindRetryViewId() {
        return R.id.tv_reload;
    }
}