package com.dandelion.widget.load_sir_callback;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import com.dandelion.widget.R;

/**
 * 加载中
 * Created by lin.wang on 2021.07.18
 */
public class LoadingCallback  extends BaseCallback {


    private ImageView animateView;

    @Override
    protected int onCreateView() {
        return R.layout.widget_loadsir_loading;
    }

    @Override
    public boolean getSuccessVisible() {
        return super.getSuccessVisible();
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;//加载时覆盖，注册的重新加载监听器，防止点击根布局造成重复加载
    }

    //将Callback添加到当前视图时的回调，View为当前Callback的布局View
    @Override
    public void onAttach(Context context, View view) {
        animateView = view.findViewById(R.id.iv_progress);
//        Animation animation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF,
//                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setDuration(1000);
//        animation.setRepeatCount(Integer.MAX_VALUE);
//        animation.setFillAfter(true);
//        animation.setInterpolator(new LinearInterpolator());
//        animateView.startAnimation(animation);
        AnimationDrawable animation = (AnimationDrawable) animateView.getDrawable();
        animation.start();
    }

    //将Callback从当前视图删除时的回调，View为当前Callback的布局View
    @Override
    public void onDetach() {
        super.onDetach();
        if (animateView != null) {
            animateView.clearAnimation();
        }
    }
}