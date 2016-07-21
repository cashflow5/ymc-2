package com.yougou.api.interceptor.impl;

import java.util.Map;

import com.yougou.api.ImplementorInvocation;
import com.yougou.api.exception.InterceptionException;
import com.yougou.api.interceptor.AbstractInterceptor;

/**
 * 默认参数拦截器
 * 
 * @author yang.mq
 *
 */
public class DefaultParameterInterceptor extends AbstractInterceptor {

	@Override
	public Object intercept(ImplementorInvocation invocation) throws InterceptionException {
		Map<String, Object> parameters = invocation.getImplementorContext().getParameters();
		Map<String, String> inputParams = invocation.getImplementorProxy().getImplementorMapping().getInputParams();
		for (Map.Entry<String, String> inputParam : inputParams.entrySet()) {
			Object value = parameters.get(inputParam.getKey());
			if ((value == null || value.equals("")) && inputParam.getValue() != null) {
				parameters.put(inputParam.getKey(), inputParam.getValue());
			}
		}
		return invocation.invoke();
	}
}
