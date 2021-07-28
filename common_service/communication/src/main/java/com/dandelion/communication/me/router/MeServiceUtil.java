package com.dandelion.communication.me.router;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 我的组件服务工具类
 * 其他组件使用此类，即可跳转我的组件相关页面、调用故事组件服务
 * Created by lin.wang on 2021/6/24.
 */
public class MeServiceUtil {
    public static Fragment navigateMePage(){
       return (Fragment) ARouter.getInstance()
                .build(MeRouterTable.PATH_PAGE_ME)
                .navigation();
    }
}
