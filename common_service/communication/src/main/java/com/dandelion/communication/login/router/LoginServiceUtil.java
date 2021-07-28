package com.dandelion.communication.login.router;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dandelion.communication.login.bean.UserInfoBean;

/**
 * 登录组件服务工具类
 * Created by lin.wang on 2021/6/24.
 */
public class LoginServiceUtil {

    /**
     * 启动登录界面
     */
    public static void navigateLoginPage() {
        ARouter.getInstance()
                .build(LoginRouterTable.PATH_PAGE_LOGIN_ACTIVITY)
                .navigation();
    }

    /**
     * 获取服务
     *
     * @return
     */
    public static ILoginProvider getService() {
        return (ILoginProvider) ARouter.getInstance()
                .build(LoginRouterTable.PATH_SERVICE_LOGIN)
                .navigation();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserInfoBean getUserInfo() {
        return getService().getUserInfo();
    }

    public static void saveUserInfo(UserInfoBean userInfoBean) {
        getService().saveUserInfo(userInfoBean);
    }

    /**
     * 退出登录
     */
    public static void loginOut() {
        getService().loginOut();
    }

    /**
     * 是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return getService().isLogin();
    }

}
