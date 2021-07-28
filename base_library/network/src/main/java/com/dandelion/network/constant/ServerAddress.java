package com.dandelion.network.constant;

/**
 * 服务器地址
 * Created by lin.wang on 2021.06.30
 */
public enum ServerAddress {
    // 测试环境
    TEST("https://www.baidu.com/",
            "https://www.baidu.com/"),

    // beta环境
    BETA("https://www.baidu.com/",
            "https://www.baidu.com/"),
    // 生产环境
    RELEASE("https://www.baidu.com/",
            "https://www.baidu.com/");


    private String mainHost;
    private String standbyHost;

    ServerAddress(String mainHost, String standbyHost) {
        this.mainHost = mainHost;
        this.standbyHost = standbyHost;
    }

    public String getMainHost() {
        return mainHost;
    }

    public String getStandbyHost() {
        return standbyHost;
    }
}
