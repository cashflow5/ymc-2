package com.yougou.api;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yougou.api.annotation.ServletVariable.Named;

/**
 * 实现者上下文
 * 
 * @author 杨梦清
 * @date Apr 11, 2012 11:59:38 AM
 */
@SuppressWarnings("unchecked")
public class ImplementorContext {

	private Map<Object, Object> context = new ConcurrentHashMap<Object, Object>();
	private String implementMethod;
	private String implementFormat;

	public ImplementorContext(String implementMethod, String implementFormat) {
		this.implementMethod = implementMethod;
		this.implementFormat = implementFormat;
	}

	/**
	 * 执行方法
	 * 
	 * @return String
	 */
	public String getImplementMethod() {
		return implementMethod;
	}

	/** 
	 * 执行格式 
	 * 
	 * @return String
	 */
	public String getImplementFormat() {
		return implementFormat;
	}

	/**
	 * 获取请求参数
	 * 
	 * @return Map
	 */
	public Map<String, Object> getParameters() {
		return Map.class.cast(get(Named.HTTP_REQUEST_PARAMETERS));
	}

	/**
	 * 获取时区
	 * 
	 * @return Local
	 */
	public Locale getHttpLocale() {
		return Locale.class.cast(get(Named.HTTP_LOCALE));
	}

	/**
	 * 获取基础路径
	 * 
	 * @return String
	 */
	public String getHttpBasePath() {
		return String.class.cast(get(Named.HTTP_BASE_PATH));
	}
	
	/**
	 * 获取上下文路径
	 * 
	 * @return String
	 */
	public String getHttpContextPath() {
		return String.class.cast(get(Named.HTTP_CONTEXT_PATH));
	}

	/**
	 * 获取请求对象
	 * 
	 * @return HttpServletResponse
	 */
	public HttpServletRequest getHttpServletRequest() {
		return HttpServletRequest.class.cast(get(Named.HTTP_REQUEST));
	}

	/**
	 * 获取响应对象
	 * 
	 * @return HttpServletResponse
	 */
	public HttpServletResponse getHttpServletResponse() {
		return HttpServletResponse.class.cast(get(Named.HTTP_RESPONSE));
	}

	/**
	 * 将对象加入上下文
	 * 
	 * @param key
	 * @param value
	 */
	public void set(Object key, Object value) {
		this.context.put(key, value);
	}

	/**
	 * 从上下文中获取对象
	 * 
	 * @param key
	 * @return Object
	 */
	public Object get(Object key) {
		return this.context.get(key);
	}
}
