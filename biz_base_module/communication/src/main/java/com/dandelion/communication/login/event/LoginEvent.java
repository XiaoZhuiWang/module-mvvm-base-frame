package com.dandelion.communication.login.event;

/**
 * 登录成功事件
 * Created by lin.wang on 2021/7/1
 */
public class LoginEvent {
    public static final String KEY = "login_login";

    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_OUT = 2;

    private int loginStatus;

    public LoginEvent(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    public int getLoginStatus() {
        return loginStatus;
    }
}
