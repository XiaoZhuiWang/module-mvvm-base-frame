package com.dandelion.network.constant;


import com.dandelion.utils.MMKVHelper;

/**
 * 当前服务器环境。
 * Created by lin.wang on 2021.06.30
 */
public class EnvHelper {
    private static final String MAIN_ADDRESS = "http_base_url_main";  //主http地址
    private static final String STANDBY_ADDRESS = "http_base_url_standby";//备用http地址

    /**
     * 持久化main address
     *
     * @param address
     */
    public static void saveMainAddress(String address) {
        MMKVHelper.getMMKV().encode(MAIN_ADDRESS, address);
    }

    /**
     * 从本地获取main address
     *
     * @return
     */
    public static String getMainAddress() {
        return MMKVHelper.getMMKV().decodeString(MAIN_ADDRESS,
                ServerAddress.RELEASE.getMainHost());
    }

    public static void saveStandbyAddress(String address) {
        MMKVHelper.getMMKV().encode(STANDBY_ADDRESS, address);
    }


    public static String getStandbyAddress() {
        return MMKVHelper.getMMKV().decodeString(STANDBY_ADDRESS,
                ServerAddress.RELEASE.getStandbyHost());
    }

}
