/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * $Id: ServletUtils.java 1048 2010-04-17 11:23:09Z calvinxiu $
 */
package com.belle.infrastructure.web.servlet;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.belle.infrastructure.util.EncodeUtils;

/**
 * Http与Servlet工具类.
 *
 * @author calvin
 */
public class ServletUtils {

	//-- Content Type 定义 --//
	public static final String TEXT_TYPE = "text/plain";
	public static final String JSON_TYPE = "application/json";
	public static final String XML_TYPE = "text/xml";
	public static final String HTML_TYPE = "text/html";
	public static final String JS_TYPE = "text/javascript";
	public static final String EXCEL_TYPE = "application/vnd.ms-excel";

	//-- Header 定义 --//
	public static final String AUTHENTICATION_HEADER = "Authorization";

	//-- 常用数值定义 --//
	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

	/**
	 * 设置客户端缓存过期时间 Header.
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		//Http 1.0 header
		response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
		//Http 1.1 header
		response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
	}

	/**
	 * 设置客户端无缓存Header.
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		//Http 1.0 header
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		//Http 1.1 header
		response.setHeader("Cache-Control", "no-cache");
	}

	/**
	 * 设置LastModified Header.
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	/**
	 * 设置Etag Header.
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	/**
	 * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
	 *
	 * 如果无修改, checkIfModify返回false ,设置304 not modify status.
	 *
	 * @param lastModified 内容的最后修改时间.
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
			long lastModified) {
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
	 *
	 * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
	 *
	 * @param etag 内容的ETag.
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", etag);
				return false;
			}
		}
		return true;
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 *
	 * @param fileName 下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {
		try {
			//中文文件名支持
			String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
		}
	}

	/**
	 * 取得带相同前缀的Request Parameters.
	 *
	 * 返回的结果Parameter名已去除前缀.
	 */
	@SuppressWarnings("unchecked")
	public static Map getParametersStartingWith(HttpServletRequest request, String prefix) {
		Enumeration paramNames = request.getParameterNames();
		Map params = new TreeMap();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {//NOSONAR
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	/**
	 * 对Http Basic验证的 Header进行编码.
	 */
	public static String encodeHttpBasic(String userName, String password) {
		String encode = userName + ":" + password;
		return "Basic " + EncodeUtils.base64Encode(encode.getBytes());
	}

	/**
	 * 获取spring mvc Controller类重定向的URL
	 * @param request  Servlet请求
	 * @param path    跳转的相对根的路径
	 * @return
	 */
	public static String getControllerRedirctURL(HttpServletRequest request,String path){
		StringBuffer redirectBuffer = new StringBuffer();
		redirectBuffer.append("redirect:");
		redirectBuffer.append(request.getScheme());
		redirectBuffer.append("://");
		redirectBuffer.append(request.getServerName());
		redirectBuffer.append(":");
		redirectBuffer.append(request.getServerPort());
		redirectBuffer.append(request.getContextPath());
		redirectBuffer.append("/");
		redirectBuffer.append(path);

		StringBuffer reqUrl = request.getRequestURL();
		redirectBuffer.append(reqUrl.substring(reqUrl.lastIndexOf(".")));

		return redirectBuffer.toString();

	}

	/**
	 * 获取UrlRewire重定向的URL(添加.jhtml)
	 * @param request  Servlet请求
	 * @param path    跳转的相对根的路径
	 * @return
	 */
	public static String getUrlRewireRedirctURL(HttpServletRequest request,String path){
		StringBuffer redirectBuffer = new StringBuffer();
		redirectBuffer.append("redirect:");
		redirectBuffer.append(request.getScheme());
		redirectBuffer.append("://");
		redirectBuffer.append(request.getServerName());
		redirectBuffer.append(":");
		redirectBuffer.append(request.getServerPort());
		redirectBuffer.append(request.getContextPath());
		redirectBuffer.append("/");
		redirectBuffer.append(path);

		return redirectBuffer.toString();

	}

	public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
           ip = request.getHeader("Proxy-Client-IP");
       }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
           ip = request.getHeader("WL-Proxy-Client-IP");
        }
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
       }
       return ip;
  }
	
	public static String getPostRequestUrl(HttpServletRequest request){
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(request.getRequestURL()).append("?");
		
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String str = (String) enumeration.nextElement();
			String value = request.getParameter(str);
			if(value != null && !"".equals(value))
				sbuf.append(str).append("=").append(value).append("&");
		}
		sbuf.deleteCharAt(sbuf.length()-1);
		return sbuf.toString();
	}
}
