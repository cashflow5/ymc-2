package com.yougou.api.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.YOPRuntimeException;
import com.yougou.api.web.servlet.AbstractFilter;

/**
 * 请求参数关键字过滤器
 * 
 * @author 杨梦清
 *
 */
public class KeywordFilter extends AbstractFilter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String[] rules = getFilterRuleAsArray();
		for (Object value : getParameterMap(request).values()) {
			String parameter = (String) value;
			if (StringUtils.isNotBlank(parameter) && ArrayUtils.contains(rules, parameter)) {
				throw new YOPRuntimeException(YOPBusinessCode.FORBIDDEN, "Access forbidden, parameters contains illegal keyword.");
			}
		}
		
		chain.doFilter(request, response);
	}
}
