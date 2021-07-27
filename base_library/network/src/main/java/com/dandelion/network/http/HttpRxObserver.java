package com.dandelion.network.http;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.dandelion.network.bean.ResponseBean;
import com.dandelion.network.constant.ErrorCode;
import com.dandelion.network.constant.LoadStatusCode;
import com.dandelion.network.constant.LoadType;
import com.dandelion.network.utils.RxExceptionUtil;
import com.dandelion.utils.LogUtil;
import com.hjq.toast.ToastUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 观察以Rx的方式返回的Retrofit请求，并进行统一错误处理
 * Created by lin.wang on 2021/6/30
 */
@SuppressWarnings("ALL")
public abstract class HttpRxObserver<T extends ResponseBean> implements Observer<T> {

    // 观察加载结果
    private MutableLiveData<Integer> mLoadResultLiveData;
    // 加载类型
    private int mLoadType;
    // 没有有更多数据
    private boolean mNoMoreData = false;
    // 没有数据
    private boolean mNoData = false;

    public HttpRxObserver() {
    }

    public HttpRxObserver(MutableLiveData<Integer> loadResultLiveData, @LoadType int loadType) {
        this.mLoadResultLiveData = loadResultLiveData;
        this.mLoadType = loadType;
    }

    @Override
    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
        onStart(d);
        //加载中
        if (mLoadResultLiveData != null && mLoadType == LoadType.LOAD_TYPE_DEFAULT) {
            mLoadResultLiveData.setValue(LoadStatusCode.LOAD_STATUS_DEFAULT_LOADING);
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        if (t.isSuccess()) {
            notifyLoadSuccess(t);
        } else {
            //请求成功，但服务端返回错误码，走错误流程
            notifyLoadFail(t.getCode(), t.getMsg());
        }
    }

    @Override
    public void onError(@NonNull Throwable t) {
        String errorMsg = RxExceptionUtil.exceptionHandler(t); //对常见错误进行统一处理
        notifyLoadFail(ErrorCode.DEFAULT_ERROR_CODE.getCode(), errorMsg);
    }

    @Override
    public void onComplete() {

    }

    private void notifyLoadFail(int code, String errorMsg) {
        LogUtil.d(LogUtil.HTTP_EXCEPTION_TAG, Objects.requireNonNull(errorMsg));
        onFail(code, errorMsg);
        if (mLoadResultLiveData != null) {
            switch (mLoadType) {
                case LoadType.LOAD_TYPE_DEFAULT:
                    mLoadResultLiveData.setValue(LoadStatusCode.LOAD_STATUS_DEFAULT_FAIL);
                    break;
                case LoadType.LOAD_TYPE_REFRESH:
                    mLoadResultLiveData.setValue(LoadStatusCode.LOAD_STATUS_REFRESH_FAIL);
                    break;
                case LoadType.LOAD_TYPE_MORE:
                    mLoadResultLiveData.setValue(LoadStatusCode.LOAD_STATUS_MORE_FAIL);
                    break;
            }
        }

    }

    private void notifyLoadSuccess(T t) {
        onSuccess(t);
        if (mLoadResultLiveData != null) {
            if (mLoadType == LoadType.LOAD_TYPE_DEFAULT && mNoData) { //没有数据
                mLoadResultLiveData.setValue(LoadStatusCode.LOAD_STATUS_DEFAULT_NO_DATA);
            } else if (mLoadType == LoadType.LOAD_TYPE_MORE && mNoMoreData) {//没有更多数据
                mLoadResultLiveData.setValue(LoadStatusCode.LOAD_STATUS_MORE_NO_MORE_DATA);
            } else {//加载到数据
                switch (mLoadType) {
                    case LoadType.LOAD_TYPE_DEFAULT:
                        mLoadResultLiveData.setValue(LoadStatusCode.LOAD_STATUS_DEFAULT_SUCCESS);
                        break;
                    case LoadType.LOAD_TYPE_REFRESH:
                        mLoadResultLiveData.setValue(LoadStatusCode.LOAD_STATUS_REFRESH_SUCCESS);
                        break;
                    case LoadType.LOAD_TYPE_MORE:
                        mLoadResultLiveData.setValue(LoadStatusCode.LOAD_STATUS_MORE_SUCCESS);
                        break;
                }
            }
        }
    }

    /**
     * Observer开始订阅，执行在主线程中
     * @param d
     */
    public abstract void onStart(@NotNull Disposable d);

    /**
     * 请求成功
     *
     * @param response
     */
    public abstract void onSuccess(T response);

    /**
     * 请求错误
     * 有两种情况：1.请求失败；2.请求成功，但服务端返回非成功错误码。
     * 我们可以根据错误码去处理特定的服务端错误。
     * 如果不想某个错误码弹toast直接覆盖该方法即可。
     *
     * @param errorCode
     * @param errorMsg
     */
    public void onFail(int errorCode, String errorMsg) {
        ToastUtils.show(errorMsg);
    }

    /**
     * 设置没有数据，在{@link #onSuccess}里调用才有效
     *
     * @param noData
     */
    protected void setNoData(boolean noData) {
        this.mNoData = noData;
    }

    /**
     * 设置没有更多数据，在{@link #onSuccess}里调用才有效
     *
     * @param noMoreData
     */
    protected void setNoMoreData(boolean noMoreData) {
        this.mNoMoreData = noMoreData;
    }
}