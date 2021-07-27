package com.dandelion.utils;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * 日志打印工具类
 */
public class LogUtil {

    /**
     * 利用这些TAG（而不是在代码中设置各种日志Debug），配合Android Studio的LogCat日志过滤器，可以清晰简洁的看到各种Log
     */

    public static final String TAG = "Boulevard";

    // Socket TAG
    public static final String SOCKET_REQUEST_JYONLINE_TAG = "Socket请求";
    public static final String SOCKET_RESPOND_JYONLINE_TAG = "Socket回复";
    public static final String SOCKET_EXCEPTION_JYONLINE_TAG = "Socket异常";
    //Http TAG
    public static final String HTTP_REQUEST_TAG = "HTTP请求";
    public static final String HTTP_RESPOND_TAG = "HTTP回复";
    public static final String HTTP_EXCEPTION_TAG = "HTTP异常";

    public static void init(boolean showLog) {
        Logger.init(TAG)
                .logLevel(showLog ? LogLevel.FULL : LogLevel.NONE)
                .methodCount(1)
                .methodOffset(1);
    }

    public static void v(String tag, String msg) {
        Logger.t(tag).v(msg);
    }

    public static void v(String msg) {
        Logger.v(msg);
    }

    public static void d(String tag, String msg) {
        Logger.t(tag).d(msg);
    }

    public static void d(String msg) {
        Logger.d(msg);
    }

    public static void i(String tag, String msg) {
        Logger.t(tag).i(msg);
    }

    public static void i(String msg) {
        Logger.i(msg);
    }

    public static void w(String tag, String msg) {
        Logger.t(tag).w(msg);
    }

    public static void w(String msg) {
        Logger.w(msg);
    }

    public static void e(String tag, String msg) {
        Logger.t(tag).e(msg);
    }

    public static void e(String msg) {
        Logger.e(msg);
    }

    public static void json(String tag, String msg) {
        Logger.t(tag).json(msg);
    }

    public static void json(String msg) {
        Logger.json(msg);
    }
}
