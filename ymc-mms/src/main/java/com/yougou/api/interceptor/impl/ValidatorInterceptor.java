package com.yougou.api.interceptor.impl;

import java.util.List;
import java.util.Map;

import com.yougou.api.ImplementorInvocation;
import com.yougou.api.exception.InterceptionException;
import com.yougou.api.interceptor.AbstractInterceptor;
import com.yougou.api.validator.Validator;

/**
 * 参数校验拦截器
 * 
 * @author 杨梦清
 *
 */
public class ValidatorInterceptor extends AbstractInterceptor {

	@Override
	public Object intercept(ImplementorInvocation invocation) throws InterceptionException {
		Map<String, Object> parameters = invocation.getImplementorContext().getParameters();
		List<Validator> validators = invocation.getImplementorProxy().getValidators();
		for (Validator validator : validators) {
			validator.validate(parameters);
		}
		
		return invocation.invoke();
	}
}
