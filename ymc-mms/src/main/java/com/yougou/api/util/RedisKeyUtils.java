package com.yougou.api.util;

import org.apache.commons.lang.StringUtils;

public class RedisKeyUtils {
	
	public static final String COUNT_SET_KEY = "api:count.key.set";
	
	public static final String FUNC_COUNT = "api:count";
	
	public static final String FUNC_COUNT_ALL = "api:count.all";
	
	public static final String FUNC_API_COUNT = "api:api.count";
	
	public static final String FUNC_APPKEY_COUNT = "api:appkey.count";
	
	public static final String APPKEY_METADATA_HASH = "api:metadata.hash";
	/**
	 * 调用次数
	 */
	public static final String CALL_COUNT = "call";
	/**
	 * 耗时
	 */
	public static final String EX_TIME = "ex.time";
	
	/**
	 * 失败次数
	 */
	public static final String FAIL_CALL_COUNT = "fail.call";
	
	public static final String API_APPKEY_HASH = "api.appkey.hash";
	
	/**
	 * api频率预警
	 */
	public static final String API_FREQUENCY_EARLY_WARN = "api.frequency.early.warn";
	
	public static String getApiKey(String api,String appkey,String func){
		return StringUtils.join(new Object[]{FUNC_COUNT,api,appkey,func}, ":");
	}
	
	public static String getCountALlKey(String func){
		return StringUtils.join(new Object[]{FUNC_COUNT_ALL,func},":");
	};
	
	public static String getApiKeyALlKey(String api,String appkey,String func){
		return StringUtils.join(new Object[]{FUNC_COUNT_ALL,api,appkey,func},":");
	};
	
	public static String getApiKey(String apiAndappkey,String func){
		return StringUtils.join(new Object[]{FUNC_COUNT,apiAndappkey,func}, ":");
	}
	
	public static String getZsetCountKey(String api,String appkey,String func){
		return String.format("api:count.zet:api:%s:appkey:%s:%s", api,appkey,func);
	}
	public static String getApiCountKey(String api,String func){
		return StringUtils.join(new Object[]{FUNC_API_COUNT,api,func}, ":");
	}
	public static String getApiFreqKey(String api){
		return String.format("api:api.freq:%s", api);
	}
	public static String getAppkeyCountKey(String appkey,String func){
		return StringUtils.join(new Object[]{FUNC_APPKEY_COUNT,appkey,func}, ":");
	}
	public static String getAppkeyFreqKey(String appkey){
		return String.format("api:appkey.freq:%s", appkey);
	}
	

}
