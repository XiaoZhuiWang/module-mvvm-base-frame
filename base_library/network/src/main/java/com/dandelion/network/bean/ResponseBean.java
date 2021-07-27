package com.dandelion.network.bean;

import androidx.annotation.Nullable;

/**
 * 服务端返回信息Bean
 * Created by lin.wang on 2021/6/30
 */
public class ResponseBean<T> implements IBean{
    //成功码
    public static final int CODE_SUCCESS = 0;

    //错误码
    private int code;
    //错误信息
    private String msg;
    //客户端所需内容
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess(){
        return code == CODE_SUCCESS;
    }
}
