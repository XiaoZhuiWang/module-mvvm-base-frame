package com.dandelion.me;

import android.view.LayoutInflater;
import android.view.View;

import com.dandelion.common.base_ui.activity.BaseActivity;
import com.dandelion.me.databinding.MeActivityMeBinding;

/**
 * Module 独立运行时，包裹Fragment的Activity
 * Created by lin.wang on 2021/6/23.
 */
public class RunModuleActivity extends BaseActivity {

    protected MeActivityMeBinding mBinding;

    @Override
    protected void objectInject() {

    }

    @Override
    protected View initBinding(LayoutInflater layoutInflater) {
        mBinding = MeActivityMeBinding.inflate(layoutInflater);
        return mBinding.getRoot();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void addListener() {

    }


}