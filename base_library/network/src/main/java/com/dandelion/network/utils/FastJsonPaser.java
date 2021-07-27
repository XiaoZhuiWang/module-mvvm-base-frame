package com.dandelion.network.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

import java.util.List;

/**
 * 利用fastjson 解析json数据
 *
 * @author zhourr_
 */
public class FastJsonPaser {

    /**
     * 不允许实例
     */
    private FastJsonPaser() {
        throw new AssertionError();

    }

    /**
     * 解析jsonString
     *
     * @param <T>
     * @param jsonString
     * @param clsBean
     * @return
     */
    public static <T> T parse(String jsonString, Class<T> clsBean) {
        try {
            return JSON.parseObject(jsonString, clsBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parse(String jsonString, Class<T> cls, Feature... features) {
        return JSON.parseObject(jsonString, cls, features);
    }


    /**
     * 解析jsonString
     *
     * @param jsonString
     * @param clsBean
     * @retu
     */
    public static <T> List<T> parseList(String jsonString, Class<T> clsBean) {
        try {
            return JSON.parseArray(jsonString, clsBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象转jsonstring
     *
     * @param o
     */
    public static String objectToString(Object o) {
        return JSON.toJSONString(o);
    }
}
