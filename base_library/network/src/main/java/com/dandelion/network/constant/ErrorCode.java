package com.dandelion.network.constant;

/**
 * 服务端错误码
 * 错误信息最好由服务端返回，客户端只负责显示！！！
 * Created by lin.wang on 2021/6/30
 */
public enum ErrorCode {
    DEFAULT_ERROR_CODE(-10000, "默认错误");

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
    }
