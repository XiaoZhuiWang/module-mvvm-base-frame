package com.dandelion.widget.load_sir_callback;

/**
 * 状态码
 */
public enum LoadSirStatusCode {

    LOAD_ING(1, "加载中"),
    LOAD_ERROR(2, "加载错误"),
    LOAD_NO_DATA(3, "没有数据"),
    LOAD_TIMEOUT(4, "加载超时"),
    LOAD_SUCCESS(5, "加载成功"),
    LOAD_NO_NET(6, "没有网络");


    private final int value;
    private final String msg;

//    @SuppressLint("UseSparseArrays")
//    private static final Map<Integer, String> allMsgMap = new HashMap<Integer, String>();
//
//    static {
//        for (LoadSirStatusCode code : LoadSirStatusCode.values()) {
//            allMsgMap.put(code.getValue(), code.getMsg());
//        }
//    }

    private LoadSirStatusCode(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public int getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

//    public static String getMsg(int code) {
//        return allMsgMap.get(code);
//    }
}
