package com.dandelion.me.data.remote;

import com.dandelion.me.data.bean.PersonalInfoBean;
import com.dandelion.network.bean.ResponseBean;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MeApi {

    @GET("personalinfo")
    Observable<ResponseBean<PersonalInfoBean>> queryPersonInfo(@Query("uid") String uid);
}
