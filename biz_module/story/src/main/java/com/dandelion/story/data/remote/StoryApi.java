package com.dandelion.story.data.remote;

import com.dandelion.network.bean.ResponseBean;
import com.dandelion.story.data.bean.ArticleBean;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface StoryApi {

    @GET("getList")
    Observable<ResponseBean<List<ArticleBean>>> queryArticleList(@Query("uid") String uid,
                                                                 @Query("loadType") int loadType,
                                                                 @Query("type") int columnType,
                                                                 @Query("endId") int endId);
}
