package com.cc.oem.modules.agent.constants;

public class RedisKeyConstant {

    /**
     * key项目前缀
     */
    public final static String CACHE_PREFIX = "eo:";

    /**
     * key模块前缀
     */
    public final static String CACHE_VERSION = "balance:";

    /**
     * 空号检测余额key
     */
    public final static String EMPTY_BALANCE_KEY = CACHE_PREFIX + CACHE_VERSION + "empty:";
    /**
     * 实时检测余额key
     */
    public final static String REALTIME_BALANCE_KEY = CACHE_PREFIX + CACHE_VERSION + "realtime:";
    /**
	 * 国际检测余额key
	 */
	public final static String INTERNATIONAL_BALANCE_KEY = CACHE_PREFIX + CACHE_VERSION + "international:";
	
	/**
	 * 定向通用检测余额key
	 */
	public final static String DIRECT_COMMON_BALANCE_KEY = CACHE_PREFIX + CACHE_VERSION + "directCommon:";
	
	/**
	 * line定向检测余额key
	 */
	public final static String LINE_DIRECT_BALANCE_KEY = CACHE_PREFIX + CACHE_VERSION + "lineDirect:";

}
