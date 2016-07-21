package com.yougou.api.cfg;

import java.util.Collection;
import java.util.Enumeration;

/**
 * 配置管理员
 * 
 * @author 杨梦清 
 * @date Apr 11, 2012 3:00:18 PM
 */
public interface ConfigurationManager {

	/**
	 * 初始化容器上下文
	 * 
	 * @throws Exception
	 */
	void configure() throws Exception;
	
	/**
	 * 过滤器列表
	 * 
	 * @return Enumeration
	 */
	Enumeration<FilterMapping> getFilterMappings();
	
	/**
	 * 异常报警器列表
	 * 
	 * @return Enumeration
	 */
	Enumeration<AbnormalAlarmMapping> getAbnormalAlarmMappings();
	
	/**
	 * 向容器中注册实现者
	 * 
	 * @param mapping
	 */
	void addImplementorMapping(ImplementorMapping mapping);
	
	/**
	 * 向容器中注册实现者
	 * 
	 * @param mappings
	 */
	void addImplementorMapping(Collection<ImplementorMapping> mappings);
	
	/**
	 * 从容器中获取一个实现者(默认获取 1.0 版本号实现者)
	 * 
	 * @param method
	 * @return ImplementorMapping
	 */
	ImplementorMapping getImplementorMapping(String method);
	
	/**
	 * 从容器中获取一个实现者
	 * 
	 * @param method
	 * @param version
	 * @return ImplementorMapping
	 */
	ImplementorMapping getImplementorMapping(String method, String version);
	
	/**
	 * 检查IP是否有效
	 * @param ip
	 * @return
	 */
	boolean checkIpInvaild(String ip);
	
	/**
	 * 是否全用redis
	 * @return
	 */
	boolean checkUseRedis();
	
	/**
	 * 设置是否redis
	 * @return
	 */
	void disableUseRedis(boolean useRedis);
}
