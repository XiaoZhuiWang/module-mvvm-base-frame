package com.dandelion.common.base_ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dandelion.common.R;
import com.dandelion.common.base_ui.activity.BaseActivity;
import com.dandelion.widget.TitleBar;

import org.jetbrains.annotations.NotNull;

/**
 * Fragment基类
 * Created by lin.wang on 2021/6/23.
 */
public abstract class BaseFragment extends SupportFragment {

    @SuppressWarnings("rawtypes")
    protected BaseActivity mContext;
    private Fragment mCurrentFragment;
    protected View contentView;
    protected TitleBar titleBar;

    @SuppressWarnings("rawtypes")
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mContext = (BaseActivity) context;
        } else {
            throw new IllegalStateException("BaseFragment 必须依附在BaseActivity上");
        }

        ARouter.getInstance().inject(this);
        objectInject();
        onAfterInject();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = createContentView(LayoutInflater.from(mContext));
        initView();
        addListener();
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup parent = (ViewGroup) contentView.getParent();
        if (parent != null) { // 防止重复添加
            parent.removeView(contentView);
        }
        return contentView;
    }

    private View createContentView(LayoutInflater layoutInflater) {
        View contentView;
        if (!hasTitleBar()) {
            contentView = initBinding(layoutInflater);
        } else {
            // 给界面添加TitleBar
            contentView = layoutInflater.inflate(R.layout.common_layout_title_bar_containor, null);
            titleBar = contentView.findViewById(R.id.title_bar);
            titleBar.setNavigationOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    mContext.onBackPressedSupport();
                }
            });
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((LinearLayout) contentView).addView(initBinding(layoutInflater), layoutParams);
        }
        return contentView;
    }


    /**
     * 依赖注入
     * 对象的new都放在这个里面：例如Presenter、Adapter、集合等
     */
    protected abstract void objectInject();

    /**
     * 注入之后调用
     */
    protected void onAfterInject() {
    }

    /**
     * 创建ContentView
     *
     * @param layoutInflater
     * @return 布局View
     */
    protected abstract View initBinding(LayoutInflater layoutInflater);

    /**
     * 初始化视图控件
     */
    protected abstract void initView();

    /**
     * View事件监听，LiveData订阅等观察者模式的监听都放这里
     */
    protected abstract void addListener();


    /**
     * 是否添加TitleBar，默认为true
     *
     * @return
     */
    protected boolean hasTitleBar() {
        return false;
    }

    /**
     * 修改状态栏风格
     *
     * @param color
     */
    protected void modifyStatusBarStyle(@ColorInt int color, boolean fill) {
        mContext.setStatusBarStyle(color, fill);
    }

    /**
     * 修改状态栏字体颜色
     * @param color
     */
    protected void  modifyStatusBarFontColor(@ColorInt int color){
        mContext.setStatusBarFontColor(color);
    }
}
