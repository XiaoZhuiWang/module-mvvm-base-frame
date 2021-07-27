package com.dandelion.network.service;


import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 公共网络请求
 * Created by lin.wang on 2021/6/30
 */
public interface CommonService {
    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, Object> map);

    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap Map<String, Object> map);

    @Multipart
    @POST()
    Observable<String> uploadOneFile(@Url String url, @Part List<MultipartBody.Part> partList);

}
