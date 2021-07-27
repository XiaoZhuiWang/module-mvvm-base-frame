package com.dandelion.utils;

import com.tencent.mmkv.MMKV;

import java.util.Objects;

/**
 * Created by lin.wang on 2021.06.30
 */
public class MMKVHelper {
    public static MMKV getMMKV(){
        MMKV mmkv = MMKV.defaultMMKV();
        return Objects.requireNonNull(mmkv);
    }


}
