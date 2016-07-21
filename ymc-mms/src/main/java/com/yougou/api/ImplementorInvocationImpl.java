package com.yougou.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.belle.infrastructure.schema.ErrorCode;
import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.ServiceException;
import com.yougou.api.exception.YOPRuntimeException;
import com.yougou.api.interceptor.Interceptor;

/**
 * 调用者实现
 * 
 * @author 杨梦清
 * @date Apr 11, 2012 11:59:19 AM
 */
public class ImplementorInvocationImpl implements ImplementorInvocation {

	private static final String ERR_MSG_PATTERN = "Internal server error.\nPlease contact the server administrator \'support@yougou.com\' and report the bug info.\n{0}";
	
	private static final Logger LOGGER = Logger.getLogger(ImplementorInvocation.class);

	private ImplementorProxy proxy;
	private ImplementorContext context;
	private Iterator<Interceptor> interceptors;

	public ImplementorInvocationImpl(ImplementorContext context) {
		this.context = context;
	}

	@Override
	public ImplementorProxy getImplementorProxy() {
		return this.proxy;
	}

	@Override
	public ImplementorContext getImplementorContext() {
		return this.context;
	}

	@Override
	public void init(ImplementorProxy proxy) throws ServiceException {
		this.proxy = proxy;
		this.interceptors = proxy.getInterceptors().iterator();
	}

	@Override
	public Object invoke() throws ServiceException {
		Object result;

		if (interceptors.hasNext()) {
			// 处理拦截业务请求
			Interceptor interceptor = interceptors.next();
			LOGGER.info("at: " + System.currentTimeMillis() + " intercepting: " + interceptor);
			interceptor.init();
			result = interceptor.intercept(this);
			interceptor.destroy();
			LOGGER.info("at: " + System.currentTimeMillis() + " complete the intercept: " + interceptor);
		} else {
			try {
				// 过滤前拷贝商家编码信息
				Object merchantCode = context.getParameters().get("merchant_code");
				
				// 过滤实现者输入参数(过滤规则为非配置的输入参数)
				Map<String, String> legalInputParams = proxy.getImplementorMapping().getInputParams();
				Map<String, Object> inputParamMap = new HashMap<String, Object>(context.getParameters());
				inputParamMap.keySet().retainAll(legalInputParams.keySet());
				
				// 注入基础参数
				if (inputParamMap.put("merchant_code", merchantCode) != null) {
					throw new YOPRuntimeException(YOPBusinessCode.PARAM_NAME_DUPLICATE, "Cannot register system parameter ''merchant_code'' for invoker, It already registered.");
				}
				if (inputParamMap.put("base_path", context.getHttpBasePath()) != null) {
					throw new YOPRuntimeException(YOPBusinessCode.PARAM_NAME_DUPLICATE, "Cannot register system parameter ''base_path'' for invoker, It already registered.");
				}
				if (inputParamMap.put("context_path", context.getHttpContextPath()) != null) {
					throw new YOPRuntimeException(YOPBusinessCode.PARAM_NAME_DUPLICATE, "Cannot register system parameter ''context_path'' for invoker, It already registered.");
				}
				
				// 调用业务实现者处理用户请求业务
				Class<?>[] argTypes = proxy.getImplementMethod().getParameterTypes();
				Object[] args = ArrayUtils.isEmpty(argTypes) ? argTypes : new Object[] { inputParamMap };
				result = proxy.getImplementMethod().invoke(proxy.getImplementor(), args);
			} catch (Exception ex) {
				throw convertImplementorAccessException(ex);
			}
		}

		return result;
	}
	
	/**
	 * 转换接口异常
	 * 
	 * @param ex
	 * @return YOPRuntimeException
	 */
	private YOPRuntimeException convertImplementorAccessException(Throwable ex) {
		for (Throwable cause = ex; cause != null; cause = cause.getCause()) {
			if (YOPRuntimeException.class.isInstance(cause)) {
				return (YOPRuntimeException) cause;
			} else if (ErrorCode.class.isInstance(cause)) {
				String errorCode = StringUtils.defaultIfEmpty(((ErrorCode) cause).getErrorCode(), YOPBusinessCode.UNDEFINITION_ERROR);
				return new ServiceException(errorCode, cause.getMessage(), ex);
			} else if (RuntimeException.class.isInstance(cause)) {
				return new ServiceException(YOPBusinessCode.RUNTIME_ERROR, cause.getMessage(), ex);
			} else if (IOException.class.isInstance(cause)) {
				return new ServiceException(YOPBusinessCode.IO_ERROR, cause.getMessage(), ex);
			}
		}
		
		return new ServiceException(YOPBusinessCode.ERROR, MessageFormat.format(ERR_MSG_PATTERN, StringUtils.defaultIfEmpty(ex.getMessage(), "null")), ex);
	}
}
