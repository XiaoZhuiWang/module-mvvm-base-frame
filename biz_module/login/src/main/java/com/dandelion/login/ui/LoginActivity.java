package com.dandelion.login.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dandelion.common.base_ui.activity.BaseActivity;
import com.dandelion.communication.login.bean.UserInfoBean;
import com.dandelion.communication.login.event.LoginEvent;
import com.dandelion.communication.login.router.LoginRouterTable;
import com.dandelion.communication.login.router.LoginServiceUtil;
import com.dandelion.login.databinding.LoginActivityLoginBinding;
import com.hjq.toast.ToastUtils;
import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * 登录页面
 * Created by lin.wang on 2021/6/23.
 */
@Route(path = LoginRouterTable.PATH_PAGE_LOGIN_ACTIVITY)
public class LoginActivity extends BaseActivity {
    @Autowired(name = "target_path")
    String targetPath;

    protected LoginActivityLoginBinding mBinding;

    public static void start(String targetPath, Bundle bundle){
        ARouter.getInstance()
                .build(LoginRouterTable.PATH_PAGE_LOGIN_ACTIVITY)
                .with(bundle)
                .withString("target_path",targetPath)
                .navigation();
    }

    @Override
    protected void objectInject() {

    }

    @Override
    protected View initBinding(LayoutInflater layoutInflater) {
        mBinding = LoginActivityLoginBinding.inflate(layoutInflater);
        return mBinding.getRoot();
    }

    @Override
    protected void initView() {
        titleBar.setTitle("登录");
    }

    @Override
    protected void addListener() {
        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setUid("11111");
                LoginServiceUtil.saveUserInfo(userInfoBean);
                ToastUtils.show("登录成功");
                //发送登录成功事件
                LiveEventBus.get(LoginEvent.KEY).post(new LoginEvent(LoginEvent.LOGIN_SUCCESS));

                ARouter.getInstance()
                        .build(targetPath)
                        .with(getIntent().getExtras())
                        .navigation();

                finish();
            }
        });
    }

    @Override
    protected boolean hasTitleBar() {
        return true;
    }


}