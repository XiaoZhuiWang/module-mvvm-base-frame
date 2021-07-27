package com.dandelion.network.http;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dandelion.network.interceptor.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;


/**
 * Http请求基类
 * Created by lin.wang on 2021/6/30
 */
class HttpBaseClient {
    private static final int TIMEOUT_READ = 60;
    private static final int TIMEOUT_CONNECTION = 60;

    protected Retrofit mRetrofit;

    HttpBaseClient(String baseUrl) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(TIMEOUT_READ, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)//设置写入超时时间
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .retryOnConnectionFailure(true)//失败重连
                .addInterceptor(new HttpLoggingInterceptor())//添加打印拦截器
                .build();

        //设置json转换器
        FastJsonConverterFactory fastJsonConverterFactory = FastJsonConverterFactory.create();
        SerializerFeature[] features = new SerializerFeature[]{SerializerFeature.WriteNullStringAsEmpty};
        fastJsonConverterFactory.setSerializerFeatures(features);

        mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(fastJsonConverterFactory)
                .build();
    }
}
