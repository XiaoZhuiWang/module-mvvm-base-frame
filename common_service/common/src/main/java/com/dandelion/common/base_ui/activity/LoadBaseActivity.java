package com.dandelion.common.base_ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.dandelion.common.base_vm.LoadBaseVM;
import com.dandelion.network.constant.LoadStatusCode;
import com.dandelion.widget.LoadingDialog;
import com.dandelion.widget.load_sir_callback.ErrorCallback;
import com.dandelion.widget.load_sir_callback.LoadSirStatusCode;
import com.dandelion.widget.load_sir_callback.LoadingCallback;
import com.dandelion.widget.load_sir_callback.NoDataCallback;
import com.dandelion.widget.load_sir_callback.NoNetCallback;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.Convertor;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;

import org.jetbrains.annotations.NotNull;


/**
 * @author: Lyn Wang
 * @descrption: 基础数据加载Activity
 * @date: 2018/3/23.
 */

public abstract class LoadBaseActivity<T extends LoadBaseVM> extends BaseActivity {

    private LoadingDialog mLoadingDialog;

    @SuppressWarnings("rawtypes")
    protected LoadService mLoadService;
    protected T viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLoadSir();
        addLoadStatusObserver();
        viewModel.onLoadInitData();
    }

    @Override
    protected void onDestroy() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    protected void onAfterInject() {
        viewModel = initViewModel();
    }

    /**
     * 初始化ViewModel
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
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
                            dismissProgressDialog();
                        }
                        break;
                    case LoadStatusCode.LOAD_STATUS_DEFAULT_FAIL:
                        showLoadSirLayout(LoadSirStatusCode.LOAD_ERROR);
                        if (isDialogLoading()) {
                            dismissProgressDialog();
                        }
                        break;
                    case LoadStatusCode.LOAD_STATUS_DEFAULT_NO_DATA:
                        showLoadSirLayout(LoadSirStatusCode.LOAD_NO_DATA);
                        if (isDialogLoading()) {
                            dismissProgressDialog();
                        }
                        break;
                }
            }
        });
    }

    /**
     * 初始化LoadSir
     */
    private void initLoadSir() {
        View tagView = onAttachLoadSir();
        if (tagView != null) {
            mLoadService = LoadSir.getDefault().register(tagView, new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    viewModel.onLoadInitData();
                }
            }, new Convertor<LoadSirStatusCode>() { //注册的时候加入转换器，可根据返回的数据，适配对应的状态页
                @Override
                public Class<? extends Callback> map(LoadSirStatusCode statusCode) {
                    Class<? extends Callback> resultCode;
                    if (statusCode == LoadSirStatusCode.LOAD_ING) {
                        resultCode = LoadingCallback.class;
                    } else if (statusCode == LoadSirStatusCode.LOAD_ERROR) {
                        resultCode = ErrorCallback.class;
                    } else if (statusCode == LoadSirStatusCode.LOAD_NO_DATA) {
                        resultCode = NoDataCallback.class;
                    } else if (statusCode == LoadSirStatusCode.LOAD_TIMEOUT) {
                        resultCode = ErrorCallback.class;
                    } else if (statusCode == LoadSirStatusCode.LOAD_NO_NET) {
                        resultCode = NoNetCallback.class;
                    } else {
                        resultCode = SuccessCallback.class;
                    }
                    return resultCode;
                }
            });
        }
    }

    /**
     * 动态修改LoadSir的Callback
     *
     * @param callback  要修改的callback，对应在application中全局配置的callback
     * @param transport 修改内容
     */
    protected void modifyLoadSirCallback(Class<? extends Callback> callback, Transport transport) {
        if (mLoadService != null) {
            mLoadService.setCallBack(callback, transport);
        }
    }

    /**
     * 显示弹窗加载框
     */
    protected void showProgressDialog() {
        dismissProgressDialog();
        if (isDestroyed() || isFinishing()) {
            return;
        }
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog.Builder(this)
                    .cancelable(true)
                    .create();
        }
        mLoadingDialog.show();
    }

    /**
     * 隐藏弹窗加载框
     */
    protected void dismissProgressDialog() {
        if (mLoadingDialog != null && !isDestroyed() && !isFinishing()) {
            mLoadingDialog.dismiss();
        }
    }

    @SuppressWarnings("unchecked")
    protected void showLoadSirLayout(LoadSirStatusCode statusCode) {
        if (mLoadService != null) {
            mLoadService.showWithConvertor(statusCode);
        }
    }

    /**
     * 子类覆盖此方法返回要显示加载状态布局，启用加载状态功能
     * note:不要直接返回RecyclerView，应该选择返回它的父View
     *
     * @return 需要显示加载状态布局的View
     */
    protected View onAttachLoadSir() {
        return null;
    }

    /**
     * 是否使用Dialog加载布局
     *
     * @return
     */
    protected boolean isDialogLoading() {
        return false;
    }

    /**
     * 初始化ViewModel
     * 需要加载数据的界面强制使用ViewModel
     */
    @NotNull
    protected abstract T initViewModel();

}
