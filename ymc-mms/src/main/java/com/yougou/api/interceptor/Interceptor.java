package com.yougou.api.interceptor;

import com.yougou.api.ImplementorInvocation;
import com.yougou.api.exception.InterceptionException;

/**
 * 拦截器
 * 
 * @author 杨梦清 
 * @date Apr 11, 2012 11:46:21 AM
 */
public interface Interceptor {

	/**
	 * 初始化资源
	 * 
	 * @throws InterceptionException
	 */
	void init() throws InterceptionException;

	/**
	 * 业务拦截点
	 * 
	 * @param invocation
	 * @return Object
	 * @throws InterceptionException
	 */
	Object intercept(ImplementorInvocation invocation) throws InterceptionException;
	
	/**
	 * 销毁资源
	 * 
	 * @throws InterceptionException
	 */
	void destroy() throws InterceptionException;
}
