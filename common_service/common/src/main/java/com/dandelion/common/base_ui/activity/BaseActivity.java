package com.dandelion.common.base_ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dandelion.common.R;
import com.dandelion.utils.ActivityManager;
import com.dandelion.widget.TitleBar;
import com.dandelion.widget.swipe_back_layout.SwipeBackLayout;
import com.dandelion.widget.swipe_back_layout.Utils;
import com.dandelion.widget.swipe_back_layout.app.SwipeBackActivityBase;
import com.dandelion.widget.swipe_back_layout.app.SwipeBackActivityHelper;


/**
 * Activity基类
 * Created by lin.wang on 2021/6/23.
 */

public abstract class BaseActivity extends SupportActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    protected TitleBar titleBar;
    protected Activity mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityManager.getInstance().addActivity(this);

        if (canSwipeBack()) {
            mHelper = new SwipeBackActivityHelper(this);
            mHelper.onActivityCreate();
        }

        setStatusBarStyle(appointStatusBarColor(), isFillStatusBar());
        setStatusBarFontColor(appointStatusBarColor());

        ARouter.getInstance().inject(this);
        objectInject();
        onAfterInject();

        setContentView();
        initView();
        addListener();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mHelper != null) {
            mHelper.onPostCreate();
        }
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper == null ? null : mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        if (getSwipeBackLayout() != null) {
            getSwipeBackLayout().setEnableGesture(enable);
        }
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        if (getSwipeBackLayout() != null) {
            getSwipeBackLayout().scrollToFinishActivity();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this);
    }

    private void setContentView() {
        View contentView;
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        if (!hasTitleBar()) {
            contentView = initBinding(layoutInflater);
        } else {
            // 给界面添加TitleBar
            contentView = layoutInflater.inflate(R.layout.common_layout_title_bar_containor, null);
            titleBar = contentView.findViewById(R.id.title_bar);
            titleBar.setNavigationOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    onBackPressedSupport();
                }
            });
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((LinearLayout) contentView).addView(initBinding(layoutInflater), layoutParams);
        }
        setContentView(contentView);
    }

    /**
     * 设置状态栏风格
     * <p>
     * View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN: Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，
     * Activity顶端布局部分会被状态栏遮住
     * View.SYSTEM_UI_FLAG_VISIBLE：显示状态栏，Activity不全屏显示
     * View.INVISIBLE：隐藏状态栏，同时Activity会伸展全屏显示。
     * View.SYSTEM_UI_FLAG_LAYOUT_STABLE:稳定布局，主要是在全屏和非全屏切换时，布局不要有大的变化
     *
     * @param color 用于确定状态栏字体颜色的颜色，如果在布局不延伸到状态栏情况下还为状态栏颜色。
     * @param fill  布局是否延伸到状态栏
     */
    public void setStatusBarStyle(@ColorInt int color, boolean fill) {
        Window window = getWindow();
        int visibility;
        if (fill) { //将布局延伸到状态栏
            window.setStatusBarColor(Color.TRANSPARENT);
            visibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        } else { //沉浸式状态栏
            window.setStatusBarColor(color);
            visibility = View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        }
        window.getDecorView().setSystemUiVisibility(visibility);
    }

    /**
     * 设置状态栏字体颜色
     *
     * @param color
     */
    public void setStatusBarFontColor(@ColorInt int color) {
        View decorView = getWindow().getDecorView();
        int visibility = decorView.getSystemUiVisibility();
        // 如果状态栏背景颜色为亮色，设置状态栏文字为黑色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ColorUtils.calculateLuminance(color) >= 0.5) {
                visibility = visibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                visibility = visibility & (~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
        decorView.setSystemUiVisibility(visibility);
    }


    /**
     * 指定状态栏或延伸到状态栏的颜色。
     * 子类覆盖此方法，实现自定义颜色
     *
     * @return
     */
    protected int appointStatusBarColor() {
        return ContextCompat.getColor(this, R.color.widget_bg_white_FFFFFF);
    }

    /**
     * 是否填将视图延伸到状态栏，默认为false
     *
     * @return true：布局延伸到状态栏；false：布局不延伸到状态栏，并给状态栏设置颜色
     */
    protected boolean isFillStatusBar() {
        return false;
    }


    /**
     * 在此方法中创建，界面创建时需要用到的对象
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
     * 是否添加TitleBar
     *
     * @return 默认为添加
     */
    protected boolean hasTitleBar() {
        return false;
    }

    /**
     * 初始化视图控件
     */
    protected abstract void initView();

    /**
     * View事件监听，LiveData订阅等观察者模式的监听都放这里
     */
    protected abstract void addListener();


    /**
     * 子类重写此方法，控制能否侧滑返回
     *
     * @return 默认能侧滑返回
     */
    protected boolean canSwipeBack() {
        return true;
    }


}
