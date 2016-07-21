package com.yougou.kaidian.order.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class HttpServletRequestUtils {

	@SuppressWarnings("unchecked")
	public static String getQueryString(HttpServletRequest request) {
		StringBuilder qsBuilder = new StringBuilder();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String[] values = request.getParameterValues(name);
			for (int i = 0; i < values.length; i++) {
				qsBuilder.append("&").append(name).append("=").append(values[i]);
			}
		}
		
		if (qsBuilder.length() > 0) {
			qsBuilder.deleteCharAt(0);
		}
		
		return qsBuilder.toString();
	}
	
	public static String getClientIp(HttpServletRequest request) {
		String clientIp = request.getHeader("X-Forwarded-For");
		if (StringUtils.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = request.getHeader("Proxy-Client-Ip");
		}
		if (StringUtils.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = request.getHeader("WL-Proxy-Client-Ip");
		}
		if (StringUtils.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = request.getRemoteAddr();
		}
		if (StringUtils.isNotBlank(clientIp) && !"unknown".equalsIgnoreCase(clientIp)) {
			String[] clientIps = StringUtils.split(clientIp, ",");
			clientIp = clientIps.length > 0 ? clientIps[0] : clientIp;
		}
		return StringUtils.trimToNull(clientIp);
	}
	
	public static String getBasePath(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(request.getScheme());
		builder.append("://");
		builder.append(request.getServerName());
		builder.append(":");
		builder.append(request.getServerPort());
		builder.append(request.getContextPath());
		return builder.toString();
	}
}