package com.yougou.api;

import com.yougou.api.exception.ServiceException;

/**
 * 调用者
 * 
 * @author 杨梦清 
 * @date Apr 11, 2012 11:59:04 AM
 */
public interface ImplementorInvocation {

	/**
	 * 执行调用者前置准备
	 * 
	 * @param proxy
	 * @throws ServiceException
	 */
	void init(ImplementorProxy proxy) throws ServiceException;
	
	/**
	 * 调用实现者业务方法
	 * 
	 * @return Object
	 * @throws ServiceException
	 */
	Object invoke() throws ServiceException;
	
	/**
	 * 获取调用者所绑定的实现者
	 * 
	 * @return ImplementorProxy
	 */
	ImplementorProxy getImplementorProxy();
	
	/**
	 * 获取实现者上下文
	 * 
	 * @return ImplementorContext
	 */
	ImplementorContext getImplementorContext();
}
