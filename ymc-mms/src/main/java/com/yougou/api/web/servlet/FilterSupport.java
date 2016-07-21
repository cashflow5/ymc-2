package com.yougou.api.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 过滤器支持
 * 
 * @author yang.mq
 *
 */
public class FilterSupport {

	private String filterRule;

	public void setFilterRule(String filterRule) {
		this.filterRule = filterRule;
	}

	protected String getFilterRuleAsString() {
		return this.filterRule;
	}

	protected int getFilterRuleAsInt() {
		return Integer.parseInt(this.filterRule);
	}

	protected long getFilterRuleAsLong() {
		return Long.parseLong(this.filterRule);
	}

	protected String[] getFilterRuleAsArray() {
		return StringUtils.split(this.filterRule, ",");
	}

	protected Map<String, Object> getParameterMap(ServletRequest servletRequest) throws IOException {
		return ((ApplicationProgramInterfaceHttpServletRequest) servletRequest).getHttpRequestParameterMap();
	}

	protected String getClientIp(ServletRequest servletRequest) {
		return ((ApplicationProgramInterfaceHttpServletRequest) servletRequest).getHttpRequestClientIp();
	}

	protected String getBasePath(ServletRequest servletRequest) {
		return ((ApplicationProgramInterfaceHttpServletRequest) servletRequest).getHttpRequestBasePath();
	}
	
	protected String getContextPath(ServletRequest servletRequest) {
		return ((ApplicationProgramInterfaceHttpServletRequest) servletRequest).getHttpRequestContextPath();
	}
}
