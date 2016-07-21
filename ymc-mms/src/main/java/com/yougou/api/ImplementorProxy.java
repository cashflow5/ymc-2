package com.yougou.api;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;

import com.yougou.api.cfg.ImplementorMapping;
import com.yougou.api.exception.ServiceException;
import com.yougou.api.interceptor.Interceptor;
import com.yougou.api.validator.Validator;

/**
 * 实现者代理
 * 
 * @author 杨梦清 
 * @date Apr 11, 2012 11:54:59 AM
 */
public interface ImplementorProxy {

	/**
	 * 获取实现者所绑定的调用者
	 * 
	 * @return ImplementorInvocation
	 */
	ImplementorInvocation getImplementorInvocation();
	
	/**
	 * 获取实现者
	 * 
	 * @return Implementor
	 */
	Implementor getImplementor();
	
	/**
	 * 获取实现者配置信息
	 * 
	 * @return ImplementorMapping
	 */
	ImplementorMapping getImplementorMapping();
	
	/**
	 * 获取实现者调用方法
	 * 
	 * @return Method
	 */
	Method getImplementMethod();
	
	/**
	 * 实现者校验器链
	 * 
	 * @return List
	 */
	List<Validator> getValidators();
	
	/**
	 * 实现者拦截器链
	 * 
	 * @return List
	 */
	List<Interceptor> getInterceptors();
	
	/**
	 * 执行实现者前置准备
	 * 
	 * @param mapping
	 * @param beanFactory
	 * @throws Exception
	 */
	void prepare(ImplementorMapping mapping, BeanFactory beanFactory) throws ServiceException;
	
	/**
	 * 实现者开始执行
	 * 
	 * @return Object
	 * @throws Exception
	 */
	Object execute() throws ServiceException;
}
