package com.yougou.api.interceptor.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yougou.api.ImplementorInvocation;
import com.yougou.api.constant.Constants;
import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.InterceptionException;
import com.yougou.api.interceptor.AbstractInterceptor;
import com.yougou.api.service.BusinessLogger;

/**
 * 时间戳误差范围拦截器
 * 
 * @author 杨梦清
 * 
 */
public class TimestampInterceptor extends AbstractInterceptor {

	
	private static final Logger logger = LoggerFactory.getLogger("openapiBusiness");
	
	@Resource
	private BusinessLogger businessLogger;
	
	@Override
	public Object intercept(ImplementorInvocation invocation) throws InterceptionException {
		Map<String, Object> parameters = invocation.getImplementorContext().getParameters();
		String timestamp = MapUtils.getString(parameters, "timestamp");
		String appKey = MapUtils.getString(parameters, "app_key");
		Date clientDate = null;
		
		try {
			clientDate = DateUtils.parseDate(timestamp, Constants.DATE_PATTERNS);
		} catch (Exception ex) {
			businessLogger.log("timestamp", YOPBusinessCode.API_TIMESTAMP_IS_INVALID, "时间戳格式无效", appKey);
			return new InterceptionException(YOPBusinessCode.API_TIMESTAMP_IS_INVALID, "时间戳格式无效");
		}
		
		// 允许客户端与服务端时间误差范围6分钟
		if (Math.abs(System.currentTimeMillis() - clientDate.getTime()) > 360000L) {
			businessLogger.log("timestamp", YOPBusinessCode.API_TIMESTAMP_ERROR_RANGE_OVERFLOW, "时间戳误差范围溢出", appKey);
			return new InterceptionException(YOPBusinessCode.API_TIMESTAMP_ERROR_RANGE_OVERFLOW, "时间戳误差范围溢出,有效误差范围为正负6分钟.");
		}
		
		return invocation.invoke();
	}
}

