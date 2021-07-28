package com.dandelion.network.http;


import com.dandelion.network.constant.EnvHelper;

/**
 * ip2的HttpClient
 * Created by lin.wang on 2021.06.30
 */
public class HttpStandbyClient extends HttpBaseClient {

    private HttpStandbyClient() {
        super(EnvHelper.getStandbyAddress());
    }

    private static class SingletonHolder {
        private static final HttpStandbyClient INSTANCE = new HttpStandbyClient();
    }

    /**
     * 返回Retrofit Service
     *
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getService(Class<T> cls) {
        return SingletonHolder.INSTANCE.mRetrofit.create(cls);
    }
}
