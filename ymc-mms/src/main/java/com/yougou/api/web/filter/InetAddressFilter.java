package com.yougou.api.web.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.yougou.api.cfg.ConfigurationManager;
import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.YOPRuntimeException;
import com.yougou.api.web.servlet.AbstractFilter;


/**
 * 因特网地址过滤器(IP地址)
 * 
 * @author 杨梦清
 *
 */
public class InetAddressFilter extends AbstractFilter {
	
	@Resource
	private ConfigurationManager configurationManager;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (StringUtils.isNotBlank(getClientIp(request)) && configurationManager.checkIpInvaild(getClientIp(request)) ) {
			throw new YOPRuntimeException(YOPBusinessCode.FORBIDDEN, "您的IP已被禁止访问，请联系管理员。");
		}

		chain.doFilter(request, response);
	}
}
