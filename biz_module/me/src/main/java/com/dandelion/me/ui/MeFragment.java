package com.dandelion.me.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dandelion.common.base_ui.fragment.BaseFragment;
import com.dandelion.communication.login.event.LoginEvent;
import com.dandelion.communication.login.router.LoginServiceUtil;
import com.dandelion.communication.me.router.MeRouterTable;
import com.dandelion.me.R;
import com.dandelion.me.databinding.MeFragmentMeBinding;
import com.hjq.toast.ToastUtils;
import com.jeremyliao.liveeventbus.LiveEventBus;

import org.jetbrains.annotations.NotNull;

/**
 * 我的模块首页
 * Created by lin.wang on 2021/6/23.
 */
@Route(path = MeRouterTable.PATH_PAGE_ME)
public class MeFragment extends BaseFragment {
    protected MeFragmentMeBinding mBinding;

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        modifyStatusBarFontColor(ContextCompat.getColor(mContext, R.color.widget_bg_white_F5F5F5));
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void objectInject() {

    }

    @Override
    protected View initBinding(LayoutInflater layoutInflater) {
        mBinding = MeFragmentMeBinding.inflate(layoutInflater);
        return mBinding.getRoot();
    }

    @Override
    protected void initView() {
        mBinding.ivHeader.setBackgroundResource(R.drawable.utils_ic_image_load_error);
        if(LoginServiceUtil.isLogin()){
            mBinding.tvNickName.setText("已登录");
        }else {
            mBinding.tvNickName.setText("未登录，点头像测试登录拦截");
        }
    }

    @Override
    protected void addListener() {
        //订阅登录事件
        LiveEventBus.get(LoginEvent.KEY, LoginEvent.class)
                .observe(this, new Observer<LoginEvent>() {
                    @Override
                    public void onChanged(LoginEvent loginEvent) {
                        int loginStatus = loginEvent.getLoginStatus();
                        if(loginStatus == LoginEvent.LOGIN_SUCCESS){
                            mBinding.tvNickName.setText("已登录");
                        }else if(loginStatus == LoginEvent.LOGIN_OUT){
                            mBinding.tvNickName.setText("未登录，点头像测试登录拦截");
                        }
                    }
                });

        mBinding.ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalInfoActivity.start("张阿三");
            }
        });

        mBinding.tvLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginServiceUtil.loginOut();
                //发送登录成功事件
                LiveEventBus.get(LoginEvent.KEY).post(new LoginEvent(LoginEvent.LOGIN_OUT));
                ToastUtils.show("退出登录成功");
            }
        });
    }
}