package com.dandelion.common.base_ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.dandelion.common.R;
import com.dandelion.common.base_vm.PullBaseVM;
import com.dandelion.network.constant.LoadStatusCode;
import com.dandelion.widget.load_sir_callback.LoadSirStatusCode;
import com.dandelion.widget.smart_refresh_layout.AppRefreshFooter;
import com.dandelion.widget.smart_refresh_layout.AppRefreshHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;


/**
 * @author: Lyn Wang
 * @descrption: 支持上下拉加载数据的使用Mvp模式的Activity,
 * @date: 2018/3/23.
 */

public abstract class PullBaseActivity<T extends PullBaseVM> extends LoadBaseActivity<T> {

    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRefresh();
    }

    /**
     * 初始化ViewModel
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    protected void addLoadStatusObserver() {
        // 观察加载状态
        viewModel.getLoadStatusLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer) {
                    case LoadStatusCode.LOAD_STATUS_DEFAULT_LOADING:
                        if (isDialogLoading()) {
                            showProgressDialog();
                        }else {
                            showLoadSirLayout(LoadSirStatusCode.LOAD_ING);
                        }
                        break;
                    case LoadStatusCode.LOAD_STATUS_DEFAULT_SUCCESS:
                        showLoadSirLayout(LoadSirStatusCode.LOAD_SUCCESS);
                        if (isDialogLoading()) {
                            showProgressDialog();
                        }
                        break;
                    case LoadStatusCode.LOAD_STATUS_DEFAULT_FAIL:
                        showLoadSirLayout(LoadSirStatusCode.LOAD_ERROR);
                        if (isDialogLoading()) {
                            showProgressDialog();
                        }
                        break;
                    case LoadStatusCode.LOAD_STATUS_DEFAULT_NO_DATA:
                        showLoadSirLayout(LoadSirStatusCode.LOAD_NO_DATA);
                        if (isDialogLoading()) {
                            showProgressDialog();
                        }
                        break;
                    case LoadStatusCode.LOAD_STATUS_REFRESH_SUCCESS:
                        finishRefresh(true);
                        break;
                    case LoadStatusCode.LOAD_STATUS_REFRESH_FAIL:
                        finishRefresh(false);
                        break;
                    case LoadStatusCode.LOAD_STATUS_MORE_SUCCESS:
                        finishLoadMore(true, true);
                        break;
                    case LoadStatusCode.LOAD_STATUS_MORE_FAIL:
                        finishLoadMore(false, false);
                        break;
                    case LoadStatusCode.LOAD_STATUS_MORE_NO_MORE_DATA:
                        finishLoadMore(true, false);
                        break;
                }
            }
        });

    }

    private void initRefresh() {
        refreshLayout = onAttachRefreshLayout();
        if (refreshLayout != null) {
            //配置刷新
            AppRefreshHeader refreshHeader = new AppRefreshHeader(this);
            refreshHeader.setArrowResource(R.drawable.common_smart_refresh_arrow_down);
            refreshLayout.setRefreshHeader(refreshHeader); //设置刷新头部
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NotNull RefreshLayout refreshlayout) {
                    viewModel.onRefresh();
                }
            });

            //配置加载更多
            refreshLayout.setRefreshFooter(new AppRefreshFooter(this));   //设置刷新尾部
            refreshLayout.setEnableLoadMoreWhenContentNotFull(false);//列表不满一页时，不允许开启上拉加载功能
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NotNull RefreshLayout refreshLayout) {
                    viewModel.onLoadMore();
                }
            });
        }
    }

    protected void finishRefresh(boolean success) {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh(success);
        }
    }

    protected void finishLoadMore(boolean success, boolean hasMore) {
        if (refreshLayout != null) {
            if (success) {
                if (hasMore) {
                    refreshLayout.finishLoadMore(true);
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            } else {
                refreshLayout.finishLoadMore(false);
            }
        }
    }

    /**
     * 子类覆盖此方法返回刷新布局，启用刷新功能
     *
     * @return
     */
    protected SmartRefreshLayout onAttachRefreshLayout() {
        return null;
    }
}
