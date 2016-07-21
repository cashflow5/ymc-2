package com.yougou.api.web.servlet;

import javax.servlet.Filter;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;


/**
 * API 调度程序
 * 
 * @author yang.mq
 *
 */
@Component("ApplicationProgramInterfaceDispatcher")
public class ApplicationProgramInterfaceFactoryBean implements FactoryBean<Filter> {
	
	private Filter instance;
	
	@Override
	public Filter getObject() throws Exception {
		if (instance == null) {
			instance = createSingletonInstance();
		}
		return instance;
	}

	@Override
	public Class<?> getObjectType() {
		return Filter.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	/**
	 * 创建单例过滤器
	 * 
	 * @return Filter
	 * @throws Exception
	 */
	private Filter createSingletonInstance() throws Exception {
		return new ApplicationProgramInterfaceFilter();
	}
}
