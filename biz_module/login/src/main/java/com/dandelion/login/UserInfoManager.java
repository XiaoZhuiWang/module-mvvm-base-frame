package com.dandelion.login;

import android.text.TextUtils;

import com.dandelion.communication.login.bean.UserInfoBean;
import com.dandelion.utils.MMKVHelper;

/**
 * 用户信息管理器。
 * 所有用户信息的增删改查都应该通过此类，不要在其他地方直接通过MMKV去增删改查
 * Created by lin.wang on 2021.06.30
 */
public class UserInfoManager {
    private static final String USER_INFO = "user_info";

    /**
     * 保存用户信息
     * @param userInfoBean
     */
    public static void saveUserInfo(UserInfoBean userInfoBean){
        MMKVHelper.getMMKV().encode(USER_INFO, userInfoBean);
    }

    /**
     * 获取用户信息
     * @return
     */
    public static UserInfoBean getUserInfo(){
        return MMKVHelper.getMMKV().decodeParcelable(USER_INFO, UserInfoBean.class, new UserInfoBean());
    }

    /**
     * 清除用户信息
     */
    public static void clearUserInfo(){
        MMKVHelper.getMMKV().encode(USER_INFO, new UserInfoBean());
    }

    /**
     * 用户是否登录
     * @return
     */
    public static boolean isLogin(){
        return !TextUtils.isEmpty(getUserInfo().getUid());
    }

}
