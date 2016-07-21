package com.yougou.api.interceptor;

import com.yougou.api.exception.InterceptionException;

public abstract class AbstractInterceptor implements Interceptor {

	@Override
	public void init() throws InterceptionException {
		
	}

	@Override
	public void destroy() throws InterceptionException {
		
	}
}
