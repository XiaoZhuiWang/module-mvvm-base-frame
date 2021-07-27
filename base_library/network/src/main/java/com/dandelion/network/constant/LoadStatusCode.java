package com.dandelion.network.constant;

/**
 * 加载状态码
 * Created by lin.wang on 2021/7/19
 */
public interface LoadStatusCode {
    int LOAD_STATUS_NO_STATUS = 0;//无状态
    int LOAD_STATUS_DEFAULT_LOADING = 1;//默认数据加载中
    int LOAD_STATUS_DEFAULT_SUCCESS = 2;//加载默认数据成功
    int LOAD_STATUS_DEFAULT_FAIL = 3;//加载默认数据数据失败
    int LOAD_STATUS_DEFAULT_NO_DATA = 4;//没有数据
    int LOAD_STATUS_REFRESH_SUCCESS = 5;//刷新成功
    int LOAD_STATUS_REFRESH_FAIL = 6;//刷新失败
    int LOAD_STATUS_MORE_SUCCESS = 7;//加载更多成功
    int LOAD_STATUS_MORE_FAIL = 8;//加载更多失败
    int LOAD_STATUS_MORE_NO_MORE_DATA = 9;//没有更多数据
}
