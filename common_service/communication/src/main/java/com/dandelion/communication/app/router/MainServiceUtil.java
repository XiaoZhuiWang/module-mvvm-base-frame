package com.dandelion.communication.app.router;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * app壳组件工具类
 * Created by lin.wang on 2021/6/24.
 */
public class MainServiceUtil {

    /**
     * MainActivity
     */
    public static void navigateMainPage() {
        ARouter.getInstance()
                .build(MainRouterTable.PATH_PAGE_MAIN)
                .navigation();
    }

}
