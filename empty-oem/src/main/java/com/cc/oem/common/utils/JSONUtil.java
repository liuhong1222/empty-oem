package com.cc.oem.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Description:
 * User: liutao
 * Date: 2018-05-24
 * Time: 14:21
 */
public class JSONUtil {

    public static String toJSON(Object o){
        return JSONObject.toJSONString(o);
    }

    public static <T> Object parseObject(String o,Class<T> ss){
        return JSONObject.parseObject(o,ss);
    }

    public static <T> List<T> parseArray(String o, Class<T> ss){
        return JSONArray.parseArray(o,ss);
    }
}
