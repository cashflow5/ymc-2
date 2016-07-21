package com.belle.yitiansystem.systemmgmt.util;

import org.apache.commons.lang.StringUtils;

import com.belle.yitiansystem.systemmgmt.model.pojo.SystemConfig;
import com.belle.yitiansystem.systemmgmt.service.ISystemConfigService;
import com.belle.yitiansystem.systemmgmt.service.impl.SystemConfigServiceImpl;
import com.yougou.tools.common.utils.ServiceLocator;

/**
 *<P>
 * 2011-6-25下午05:04:45
 *<P>
 * @author liudi
 */
public class ConfigUtil {

	public static String getConfigValueByKey(String key){
		if(StringUtils.isEmpty(key)){
			return "";
		}
		String value = "";
		ServiceLocator serviceLocator = ServiceLocator.getInstance();
		ISystemConfigService configService = serviceLocator.getBeanFactory().getBean(SystemConfigServiceImpl.class);
		try {
			SystemConfig config = configService.getCacheSystemConfigByKey(key);
			
			if(config!=null){
				return config.getValue();
			}else{
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 为空情况，返回默认值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getConfigValueByKey(String key,String defaultValue){
		if(StringUtils.isEmpty(key)){
			return "";
		}
		String value = "";
		ServiceLocator serviceLocator = ServiceLocator.getInstance();
		ISystemConfigService configService = serviceLocator.getBeanFactory().getBean(SystemConfigServiceImpl.class);
		try {
			SystemConfig config = configService.getCacheSystemConfigByKey(key);
			if(config!=null){
				value = config.getValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return StringUtils.isEmpty(value)?defaultValue:value;
	}
	
	/**
	 * 无缓存查询config配置项 addby wuyang 2012-4-10
	 * @param key
	 * @return
	 */
	public static String getConfigValueByKeyWithNoCache(String key){
		if(StringUtils.isEmpty(key)){
			return "";
		}
		String value = "";
		ServiceLocator serviceLocator = ServiceLocator.getInstance();
		ISystemConfigService configService = serviceLocator.getBeanFactory().getBean(SystemConfigServiceImpl.class);
		try {
			SystemConfig config = configService.getConfigValueByKeyWithNoCache(key);
			if(config != null){
				value = config.getValue();
			}else{
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return value;
	}
	
	/**
	 * 保存配置项key对应值 addby wuyang 2012-4-10
	 * @param key
	 * @return
	 */
	public static int saveConfigValueByKeyWithNoCache(String key,String value){
		ServiceLocator serviceLocator = ServiceLocator.getInstance();
		ISystemConfigService configService = serviceLocator.getBeanFactory().getBean(SystemConfigServiceImpl.class);
		try {
			configService.saveConfigValueByKey(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 获取Boolean型配置值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getConfigBoolean(String key, boolean defaultValue){
		String value = getConfigValueByKey(key,String.valueOf(defaultValue));
		return Boolean.valueOf(value);
	}
}
