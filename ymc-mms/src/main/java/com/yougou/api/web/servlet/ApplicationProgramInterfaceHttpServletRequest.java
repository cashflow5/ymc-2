package com.yougou.api.web.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;

import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.YOPRuntimeException;

/**
 * HTTP 请求包装者
 * 
 * @author yang.mq
 *
 */
public class ApplicationProgramInterfaceHttpServletRequest extends HttpServletRequestWrapper {
	
	private final HttpServletRequest httpRequest;
	
	private Map<String, Object> parameterMap;
	private String clientIp;
	private String basePath;
	
	public ApplicationProgramInterfaceHttpServletRequest(ServletRequest request) {
		this((HttpServletRequest) request);
	}

	public ApplicationProgramInterfaceHttpServletRequest(HttpServletRequest request) {
		super(request);
		this.httpRequest = request;
	}
	
	/**
	 * 获取请求参数 Map
	 * 
	 * @return Map
	 * @throws IOException
	 */
	protected Map<String, Object> getHttpRequestParameterMap() throws IOException {
		if (parameterMap == null) {
			parameterMap = new HashMap<String, Object>();
			Enumeration<String> parameterNames = httpRequest.getParameterNames();
			if ("post".equalsIgnoreCase(httpRequest.getMethod())) {
				while (parameterNames.hasMoreElements()) {
					String parameterName = parameterNames.nextElement();
					parameterMap.put(parameterName, httpRequest.getParameter(parameterName));
				}
			} else if ("get".equalsIgnoreCase(httpRequest.getMethod())) {
				while (parameterNames.hasMoreElements()) {
					String parameterName = parameterNames.nextElement();
					parameterMap.put(parameterName, new String(httpRequest.getParameter(parameterName).getBytes("ISO-8859-1"), "UTF-8"));
				}
			} else {
				throw new YOPRuntimeException(YOPBusinessCode.METHOD_UNSUPPORTED, "server only support get/post request method.");
			}
		}
		return parameterMap;
	}
	
	/**
	 * 获取请求客户端IP
	 * 
	 * @return String
	 */
	protected String getHttpRequestClientIp() {
		if (clientIp == null) {
			clientIp = httpRequest.getHeader("X-Forwarded-For");
			if (StringUtils.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
				clientIp = httpRequest.getHeader("Proxy-Client-Ip");
			}
			if (StringUtils.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
				clientIp = httpRequest.getHeader("WL-Proxy-Client-Ip");
			}
			if (StringUtils.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
				clientIp = httpRequest.getRemoteAddr();
			}
			if (StringUtils.isNotBlank(clientIp) && !"unknown".equalsIgnoreCase(clientIp)) {
				String[] clientIps = StringUtils.split(clientIp, ",");
				clientIp = clientIps.length > 0 ? clientIps[0] : clientIp;
			}
		}
		return StringUtils.trimToNull(clientIp);
	}
	
	/**
	 * 获取请求基础路径
	 * 
	 * @return String
	 */
	protected String getHttpRequestBasePath() {
		if (basePath == null) {
			StringBuilder builder = new StringBuilder();
			builder.append(httpRequest.getScheme());
			builder.append("://");
			builder.append(httpRequest.getServerName());
			builder.append(":");
			builder.append(httpRequest.getServerPort());
			//builder.append(httpRequest.getContextPath());
			basePath = builder.toString();
		}
		return basePath;
	}
	
	/**
	 * 获取请求上下文路径
	 * 
	 * @return String
	 */
	protected String getHttpRequestContextPath() {
		return httpRequest.getContextPath();
	}
}
