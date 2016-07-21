package com.yougou.api.web.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.data.redis.core.ValueOperations;

import com.yougou.api.ImplementorContext;
import com.yougou.api.ImplementorProxyFactory;
import com.yougou.api.annotation.ServletVariable.Named;
import com.yougou.api.cfg.ConfigurationManager;
import com.yougou.api.cfg.ImplementorMapping;
import com.yougou.api.result.ResultHandlerFactory;

/**
 * API 过滤器输出者
 * 
 * @author yang.mq
 *
 */
public class ApplicationProgramInterfaceFilterExporter extends AbstractFilter {

	@Resource
	private ConfigurationManager configurationManager;
	
	@Resource(name = "redisTemplate")
	private ValueOperations<String,Object> valueOperations;
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		Object result = null;
		
		// 读取请求参数
		Map<String, Object> parameterMap = getParameterMap(servletRequest);
		
		// 实现者执行方法及输出格式
		String implementMethod = (String) parameterMap.get("method");
		String implementFormat = (String) parameterMap.get("format");
		
		String sign = MapUtils.getString(parameterMap, "sign");
		
		// 创建实现者上下文环境
		ImplementorContext implementorContext = new ImplementorContext(implementMethod, implementFormat);
		implementorContext.set(Named.HTTP_REQUEST, request);
		implementorContext.set(Named.HTTP_RESPONSE, response);
		implementorContext.set(Named.HTTP_REQUEST_PARAMETERS, parameterMap);
		implementorContext.set(Named.HTTP_REQUEST_IP, getClientIp(request));
		implementorContext.set(Named.HTTP_LOCALE, request.getLocale());
		implementorContext.set(Named.HTTP_BASE_PATH, getBasePath(request));
		implementorContext.set(Named.HTTP_CONTEXT_PATH, getContextPath(servletRequest));
		
		// 读取实现器映射信息
		ImplementorMapping mapping = configurationManager.getImplementorMapping(implementorContext.getImplementMethod());
		
		
		//String key = "api:sign:"+sign;
		//Boolean hasKey = valueOperations.getOperations().hasKey(key);
		//相同的相同sign调用，走缓存
		//if(hasKey){
		//	result = valueOperations.get(key);
		//	LOGGER.info("相同sign调用,IP: "+ getClientIp(request));
		//}else{
			// 创建并执行实现器代理实例
			result = ImplementorProxyFactory.newInstance(mapping, implementorContext, beanFactory).execute();
			//valueOperations.set(key, result, 6, TimeUnit.MINUTES);
		//}
		
		// 输出响应结果
		ResultHandlerFactory.newInstance(request, response).writeResult(result);
	}
}
