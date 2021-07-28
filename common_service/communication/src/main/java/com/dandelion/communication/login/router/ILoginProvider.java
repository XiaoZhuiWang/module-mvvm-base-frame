package com.dandelion.communication.login.router;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.dandelion.communication.login.bean.UserInfoBean;

/**
 * 登录组件暴露服务
 * Created by lin.wang on 2021/6/24.
 */
public interface ILoginProvider extends IProvider {
    /**
     * 获取用户信息
     * @return 用户信息
     */
    UserInfoBean getUserInfo();

    void saveUserInfo(UserInfoBean userInfoBean);

    /**
     * 退出登录
     */
    void loginOut();

    /**
     * 是否登录
     * @return
     */
    boolean isLogin();
}
