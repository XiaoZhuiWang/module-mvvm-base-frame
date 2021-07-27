package com.dandelion.network.constant;

/**
 * 服务端错误码
 * 错误信息最好由服务端返回，客户端只负责显示！！！
 * Created by lin.wang on 2021/6/30
 */
public enum ErrorCode {
    DEFAULT_ERROR_CODE(-10000, "默认错误"),
    NORMAL(200, "正常"),
    LACK_PARAM(201, "缺少参数"),
    DATA_NOT_FOUND(202, "找不到对应数据"),
    DATA_ALREADY_EXISTS(203, "已存在对应数据"),
    OPERATION_FAIL(205, "操作失败，请重试"),
    ILLEGAL_OPERATION(206, "非法操作，操作不属于自己的信息"),
    INCOMPLETE_PERSONAL_INFORMATION(301, "未完善个人信息"),
    AGE_IS_NOT(302, "年龄不符"),
    MARITAL_STATUS_DISCREPANCY(303, "婚姻状态不符"),
    GENDER_DISCREPANCIES(304, "性别不符"),
    TOKEN_NOT_ENOUGH(401, "代币不足"),
    YOU_CAN_NOT_REWARD_YOURSELF(402, "无法打赏自己"),
    TOPIC_NEED_TOKEN(403, "主题还需*代币开启");

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
