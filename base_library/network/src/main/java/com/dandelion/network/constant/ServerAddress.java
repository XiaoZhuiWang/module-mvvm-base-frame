package com.dandelion.network.constant;

/**
 * 服务器地址
 * Created by lin.wang on 2021.06.30
 */
public enum ServerAddress {
    // 测试环境
    TEST("https://test.linyinli.com/api/test/",
            "https://test.linyinli.com/api/test/"),

    // beta环境
    BETA("https://test.linyinli.com/api/test/",
            "https://test.linyinli.com/api/test/"),
    // 生产环境
    RELEASE("https://test.linyinli.com/api/test/",
            "https://test.linyinli.com/api/test/");


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
