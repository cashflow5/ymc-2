package com.yougou.api.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.yougou.api.Notifier;
//import com.yougou.api.cfg.AbnormalAlarmMapping;
import com.yougou.api.cfg.ConfigurationManager;
import com.yougou.api.cfg.FilterMapping;
//import com.yougou.api.exception.alarm.AbnormalAlarmEmail;
//import com.yougou.api.exception.alarm.AbnormalAlarmSms;
import com.yougou.api.model.pojo.ApiLog;
//import com.yougou.api.mongodb.GenericMongoDao;
import com.yougou.api.result.ResultHandlerFactory;
import com.yougou.api.web.event.ApplicationProgramInterfaceLogEvent;
import com.yougou.api.web.event.ApplicationProgramInterfaceThrowableEvent;

/**
 * API 过滤器
 * 
 * @author yang.mq
 *
 */
public class ApplicationProgramInterfaceFilter extends AbstractFilter {

	@Resource
	private ConfigurationManager configurationManager;

//	@Resource
//	private GenericMongoDao genericMongoDao;

	@Resource
	private Notifier notifier;
	
	@Override
	public void onInitFilterSet() throws Exception {
		// 初始化系统配置
		configurationManager.configure();
		// 注册异常消息接收者
		notifier.removeAllReceiver();
		//去除EMAIL、SMS发送预警modify by zhuang.rb at 2013-08-07 13:00 
//		Enumeration<AbnormalAlarmMapping> mappings = configurationManager.getAbnormalAlarmMappings();
//		while (mappings.hasMoreElements()) {
//			AbnormalAlarmMapping mapping = mappings.nextElement();
//			notifier.addReceiver(new AbnormalAlarmEmail(mapping, genericMongoDao));
//			notifier.addReceiver(new AbnormalAlarmSms(mapping, genericMongoDao));
//		}
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		// 包装请求与响应对象
		ApplicationProgramInterfaceHttpServletRequest request = new ApplicationProgramInterfaceHttpServletRequest(servletRequest);
		ApplicationProgramInterfaceHttpServletResponse response = new ApplicationProgramInterfaceHttpServletResponse(servletResponse);
		Long callTimeMills = System.currentTimeMillis();
		Boolean isCallSucess = true;
		
		try {
			// 过滤器参与者(前置)
			List<Filter> filterParticipators = new ArrayList<Filter>();
			Enumeration<FilterMapping> filterMappings = configurationManager.getFilterMappings();
			while (filterMappings.hasMoreElements()) {
				FilterMapping filterMapping = filterMappings.nextElement();
				Filter filterParticipator = (Filter) filterMapping.getFilterClass().newInstance();
				BeanUtils.copyProperties(filterParticipator, filterMapping);
				filterParticipators.add(filterParticipator);
			}
			
			// 过滤器输出者(后置)
			Filter filterExporter = new ApplicationProgramInterfaceFilterExporter();
			
			// 创建过滤器链条
			FilterChain filterChain = new ApplicationProgramInterfaceFilterChain(filterExporter, filterParticipators, filterConfig);
			
			// 执行过滤器链条
			filterChain.doFilter(request, response);
		} catch (Throwable ex) {
			LOGGER.error("Can not be completed execute. request parameter map: " + getParameterMap(request), ex);
			isCallSucess = false;
			// 异常预警通知
			applicationContext.publishEvent(new ApplicationProgramInterfaceThrowableEvent(ex));
			// 输出异常结果
			ResultHandlerFactory.newInstance(request, response).writeResult(ex);
		} finally {
			// 记录操作日志
			BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
			Map<String,Object> parameterMap =  getParameterMap(request);
			builder.append("className", ApiLog.class.getName());
			builder.append("clientIp", getClientIp(request));
			builder.append("operationParameters", new BasicDBObject(parameterMap));
			builder.append("operationResult", response.getResponseContent());
			builder.append("operated", new Date());
			builder.append("isCallSucess",isCallSucess);
			builder.append("exTime",System.currentTimeMillis() - callTimeMills);
			
			applicationContext.publishEvent(new ApplicationProgramInterfaceLogEvent(builder.get()));
			
		}
	}
}
