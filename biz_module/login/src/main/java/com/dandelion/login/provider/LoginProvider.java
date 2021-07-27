package com.dandelion.login.provider;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dandelion.login.UserInfoManager;
import com.dandelion.communication.login.router.ILoginProvider;
import com.dandelion.communication.login.router.LoginRouterTable;
import com.dandelion.communication.login.bean.UserInfoBean;

/**
 * 登录组件暴露服务实现
 *
 * Created by lin.wang on 2021/6/24.
 */
@Route(path = LoginRouterTable.PATH_SERVICE_LOGIN)
public class LoginProvider implements ILoginProvider {
    @Override
    public UserInfoBean getUserInfo() {
        return UserInfoManager.getUserInfo();
    }

    @Override
    public void saveUserInfo(UserInfoBean userInfoBean) {
        UserInfoManager.saveUserInfo(userInfoBean);
    }

    @Override
    public void loginOut() {
        UserInfoManager.clearUserInfo();
    }

    @Override
    public boolean isLogin() {
        return UserInfoManager.isLogin();
    }

    @Override
    public void init(Context context) {
        //初始化工作，服务注入时会调用，可忽略
    }
}
