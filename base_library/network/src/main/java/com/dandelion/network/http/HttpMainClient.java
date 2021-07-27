package com.dandelion.network.http;


import com.dandelion.network.constant.EnvHelper;

/**
 * 主ip的HttpClient
 * Created by lin.wang on 2021.06.30
 */
public class HttpMainClient extends HttpBaseClient {

    private HttpMainClient() {
        super(EnvHelper.getMainAddress());
    }

    private static class SingletonHolder {
        private static final HttpMainClient INSTANCE = new HttpMainClient();
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
