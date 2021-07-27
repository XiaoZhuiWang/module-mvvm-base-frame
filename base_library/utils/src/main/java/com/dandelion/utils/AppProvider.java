package com.dandelion.utils;

import android.app.Application;

/**
 * Application 对象提供器
 * Created by lin.wang on 2021.06.30
 */
public class AppProvider {

    private static Application application;

    public static void inject(Application app) {
        application = app;
    }

    public static Application provide() {
        return application;
    }

}
