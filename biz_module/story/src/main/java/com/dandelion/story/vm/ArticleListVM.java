package com.dandelion.story.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.dandelion.common.base_ui.fragment.SimpleListBaseFragment;
import com.dandelion.communication.login.router.LoginServiceUtil;
import com.dandelion.network.bean.ResponseBean;
import com.dandelion.network.constant.LoadType;
import com.dandelion.network.http.HttpMainClient;
import com.dandelion.network.http.HttpRxObserver;
import com.dandelion.network.scheduler.IoMainScheduler;
import com.dandelion.story.data.bean.ArticleBean;
import com.dandelion.story.data.remote.StoryApi;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public class ArticleListVM extends SimpleListBaseFragment.SimpleListVM<ArticleBean> {
    private final List<ArticleBean> mData = new ArrayList<>();
    private int mColumn; //栏目：最新、推荐...

    public ArticleListVM(@NonNull @NotNull Application application) {
        super(application);
    }

    @Override
    public void onRefresh() {
        queryArticleList(LoadType.LOAD_TYPE_REFRESH, 0);
    }

    @Override
    public void onLoadMore() {
        queryArticleList(LoadType.LOAD_TYPE_MORE, 20);
    }

    @Override
    public void onLoadInitData() {
        queryArticleList(LoadType.LOAD_TYPE_DEFAULT, 0);
    }

    private void queryArticleList(@LoadType int loadType, int endId) {
        HttpMainClient.getService(StoryApi.class)
                .queryArticleList(LoginServiceUtil.getUserInfo().getUid(), loadType, mColumn, endId)
                .compose(new IoMainScheduler<ResponseBean<List<ArticleBean>>>())
                .subscribe(new HttpRxObserver<ResponseBean<List<ArticleBean>>>(loadStatusLiveData, loadType) {
                    @Override
                    public void onStart(@NotNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(ResponseBean<List<ArticleBean>> response) {
                        List<ArticleBean> articleBeans = response.getData();
                        if (articleBeans != null) {
                            switch (loadType) {
                                case LoadType.LOAD_TYPE_DEFAULT:
                                case LoadType.LOAD_TYPE_REFRESH:
                                    mData.clear();
                                    mData.addAll(articleBeans);
                                    break;
                                case LoadType.LOAD_TYPE_MORE:
                                    mData.addAll(articleBeans);
                                    break;
                            }
                            mDataLiveData.setValue(mData);
                        }
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        super.onFail(errorCode, errorMsg);

                        //模拟测试
                        List<ArticleBean> articleBeans = new ArrayList<>();
                        for (int i = 0; i < 20; i++) {
                            articleBeans.add(new ArticleBean("标题" + i, "内容" + i, 123));
                        }
                        switch (loadType) {
                            case LoadType.LOAD_TYPE_DEFAULT:
                            case LoadType.LOAD_TYPE_REFRESH:
                                mData.clear();
                                mData.addAll(articleBeans);
                                break;
                            case LoadType.LOAD_TYPE_MORE:
                                mData.addAll(articleBeans);
                                break;
                        }
                        mDataLiveData.setValue(mData);
                    }
                });
    }

    public void setColumn(int column) {
        this.mColumn = column;
    }
}