package com.dandelion.story;

import android.view.LayoutInflater;
import android.view.View;

import com.dandelion.common.base_ui.activity.BaseActivity;
import com.dandelion.story.databinding.StoryActivityStoryBinding;

/**
 * Module 独立运行时，包裹Fragment的Activity
 * Created by lin.wang on 2021/6/23.
 */
public class RunModuleActivity extends BaseActivity {
    protected StoryActivityStoryBinding mBinding;

    @Override
    protected void objectInject() {

    }

    @Override
    protected View initBinding(LayoutInflater layoutInflater) {
        mBinding = StoryActivityStoryBinding.inflate(layoutInflater);
        return mBinding.getRoot();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void addListener() {

    }

}