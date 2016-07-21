package com.yougou.api;

import org.springframework.beans.factory.BeanFactory;

import com.yougou.api.cfg.ImplementorMapping;
import com.yougou.api.exception.ServiceException;

/**
 * 实现者工厂
 * 
 * @author 杨梦清 
 * @date Apr 11, 2012 11:50:40 AM
 */
public class ImplementorProxyFactory {

	public static ImplementorProxy newInstance(ImplementorMapping mapping, ImplementorContext context, BeanFactory beanFactory) throws ServiceException {
		ImplementorInvocation invocation = new ImplementorInvocationImpl(context);
		return newInstance(mapping, invocation, beanFactory);
	}

	public static ImplementorProxy newInstance(ImplementorMapping mapping, ImplementorInvocation invocation, BeanFactory beanFactory) throws ServiceException {
		ImplementorProxy proxy = new ImplementorProxyImpl(invocation);
		proxy.prepare(mapping, beanFactory);
		return proxy;
	}
}
