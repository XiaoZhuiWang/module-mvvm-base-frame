package com.dandelion.widget.load_sir_callback;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dandelion.widget.R;

/**
 * 没有数据
 * Created by lin.wang on 2021.07.18
 */
public class NoDataCallback  extends BaseCallback {

    private String hint;

    public NoDataCallback() {
    }

    public NoDataCallback(String hint) {
        this.hint = hint;
    }

    @Override
    protected int onCreateView() {
        return R.layout.widget_loadsir_no_data;
    }

    @Override
    public View getRootView() {
        View view = super.getRootView();
        TextView tvError = (TextView) view.findViewById(R.id.tv_error);
        tvError.setText(hint);
        return view;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;//加载时覆盖注册的重新加载监听器，防止点击根布局造成重复加载
    }

}
