package com.cc.oem.common.utils;

/**
 * Redis所有Keys
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-18 19:51
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "oem:sys:config:" + key;
    }
    
    public static String getOrderNoIncrKey(String key) {
    	return "oem_order_incr_"+key;
    }
    
    public static String getUserRepeatCommitKey(Long userId) {
    	return "oem_repeat_commit_token_"+userId;
    }
}
