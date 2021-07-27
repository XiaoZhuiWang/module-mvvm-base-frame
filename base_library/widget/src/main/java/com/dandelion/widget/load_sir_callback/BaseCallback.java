package com.dandelion.widget.load_sir_callback;

import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;

public abstract class BaseCallback extends Callback {

    private OnReloadListener onReloadListener;

    @Override
    public BaseCallback setCallback( Context context, OnReloadListener onReloadListener) {
        super.setCallback(context, onReloadListener);
        this.onReloadListener = onReloadListener;
        return this;
    }

    @Override
    protected void onViewCreate(Context context, View view) {
        if (bindRetryViewId() != 0) {
            view.setOnClickListener(null);
            final View rootView = view;
            view.findViewById(bindRetryViewId()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onReloadEvent(view.getContext(), rootView)) {
                        return;
                    }
                    if (onReloadListener != null) {
                        onReloadListener.onReload(rootView);
                    }
                }
            });
        }
    }

    /** 默认点击整个布局响应Retry事件，也可以指定一个控件来响应 */
    protected int bindRetryViewId() {
        return 0;
    }
}
