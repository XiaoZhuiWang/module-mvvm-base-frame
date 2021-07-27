package com.dandelion.network.constant;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 加载数据类型
 * Created by lin.wang on 2021/7/19
 */
@IntDef({LoadType.LOAD_TYPE_DEFAULT, LoadType.LOAD_TYPE_REFRESH, LoadType.LOAD_TYPE_MORE})
@Retention(RetentionPolicy.SOURCE)
public @interface LoadType {
    int LOAD_TYPE_DEFAULT = 1;//默认加载数据
    int LOAD_TYPE_REFRESH = 2;//刷新
    int LOAD_TYPE_MORE = 3;//加载更多
}