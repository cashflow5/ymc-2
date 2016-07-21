package com.yougou.api.util;

import org.apache.log4j.Logger;

/**
 * 系统配置持有者
 * 
 * @author yang.mq
 * 
 */
public class SystemConfigHolder {
	
	private static final Logger LOGGER = Logger.getLogger(SystemConfigHolder.class);

	private static final String TARGET_CLASS = "com.belle.yitiansystem.systemmgmt.util.ConfigUtil";
	
	private static final String TARGET_METHOD = "getConfigValueByKey";

	/**
	 * 按KEY获取系统配置值
	 * 
	 * @param key
	 * @return String
	 */
	public static String getConfigValueByKey(String key) {
		return SystemConfigHolder.getConfigValueByKey(key, "");
	}
	
	/**
	 * 按KEY获取系统配置值(如为空或空字符串返回默认值)
	 * 
	 * @param key
	 * @param defaultValue
	 * @return String
	 */
	public static String getConfigValueByKey(String key, String defaultValue) {
		try {
			return (String) Class.forName(TARGET_CLASS).getMethod(TARGET_METHOD, String.class, String.class).invoke(null, key, defaultValue);
		} catch (Exception ex) {
			LOGGER.error("get system config info error", ex);
			return defaultValue;
		}
	}
}
