package com.dandelion.utils;

import com.tencent.mmkv.MMKV;

import java.util.Objects;

/**
 * MMKV框架包装
 * Created by lin.wang on 2021.06.30
 */
public class MMKVHelper {
    public static MMKV getMMKV(){
        MMKV mmkv = MMKV.defaultMMKV();
        return Objects.requireNonNull(mmkv);
    }


}
