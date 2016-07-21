package com.yougou.api.cfg.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Component;

import com.yougou.api.cfg.AbnormalAlarmMapping;
import com.yougou.api.cfg.AbnormalAlarmMapping.AbnormalAlarmMappingBuilder;
import com.yougou.api.cfg.AbstractConfigurationManager;
import com.yougou.api.cfg.FilterMapping;
import com.yougou.api.cfg.FilterMapping.FilterMappingBuilder;
import com.yougou.api.cfg.ImplementorMapping;
import com.yougou.api.cfg.ImplementorMapping.ImplementorMappingBuilder;
import com.yougou.api.cfg.ImplementorMapping.InterceptorMapping;
import com.yougou.api.cfg.ImplementorMapping.InterceptorMappingBuilder;
import com.yougou.api.cfg.ImplementorMapping.ValidatorMapping;
import com.yougou.api.cfg.ImplementorMapping.ValidatorMappingBuilder;
import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiAbnormalAlarm;
import com.yougou.api.model.pojo.ApiFilter;
import com.yougou.api.model.pojo.ApiInputParamMetadata;
import com.yougou.api.model.pojo.ApiInterceptor;
import com.yougou.api.model.pojo.ApiValidator;
import com.yougou.api.model.pojo.InputParam;
import com.yougou.api.model.pojo.InterceptorMapper;
import com.yougou.api.model.pojo.OutputParam;
import com.yougou.merchant.api.common.PageFinder;
import com.yougou.merchant.api.common.Query;
import com.yougou.merchant.api.monitor.service.IApiMonitorService;
import com.yougou.merchant.api.monitor.vo.MonitorIpBlackList;

/**
 * 持久化配置管理员
 * 
 * @author 杨梦清 
 * @date Apr 11, 2012 3:01:15 PM
 */
@Component
public class StoreConfigurationManager extends AbstractConfigurationManager {

	@Resource
	private SessionFactory sessionFactory;
	
	@Resource(name = "ignoreClassNotFound")
	private Boolean ignoreClassNotFound;
	
	@Resource(name = "ignoreNoSuchMethod")
	private Boolean ignoreNoSuchMethod;
	
	@Resource
	private IApiMonitorService apiMonitorService;
	
	public StoreConfigurationManager() {
		super(true);
	}
	
	@Override
	protected List<AbnormalAlarmMapping> loadAbnormalAlarmMappings() throws Exception {
		List<AbnormalAlarmMapping> mappings = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionFactoryUtils.getSession(sessionFactory, true);
			List<ApiAbnormalAlarm> apiAbnormalAlarms = session.createCriteria(ApiAbnormalAlarm.class).list();
			mappings = loadAbnormalAlarmMappings(apiAbnormalAlarms);
		} finally {
			SessionFactoryUtils.releaseSession(session, sessionFactory);
		}
		
		return mappings;
	}

	@Override
	protected List<FilterMapping> loadFilterMappings() throws Exception {
		List<FilterMapping> mappings = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionFactoryUtils.getSession(sessionFactory, true);
			List<ApiFilter> apiFilters = session.createCriteria(ApiFilter.class).addOrder(Order.asc("orderNo")).list();
			mappings = loadFilterMappings(apiFilters);
		} finally {
			SessionFactoryUtils.releaseSession(session, sessionFactory);
		}
		
		return mappings;
	}

	@Override
	protected List<ImplementorMapping> loadImplementorMappings() throws Exception {
		List<ImplementorMapping> mappings = Collections.emptyList();
		Session session = null;
		
		try {
			session = SessionFactoryUtils.getSession(sessionFactory, true);
			List<Api> apis = session.createCriteria(Api.class).list();
			mappings = loadImplementorMappings(apis);
		} finally {
			SessionFactoryUtils.releaseSession(session, sessionFactory);
		}
		
		return mappings;
	}
	
	/**
	 * 加载异常报警器
	 * 
	 * @param apiAbnormalAlarms
	 * @return List
	 * @throws Exception
	 */
	private List<AbnormalAlarmMapping> loadAbnormalAlarmMappings(List<ApiAbnormalAlarm> apiAbnormalAlarms) throws Exception {
		List<AbnormalAlarmMapping> mappings = new ArrayList<AbnormalAlarmMapping>();
		
		for (ApiAbnormalAlarm apiAbnormalAlarm : apiAbnormalAlarms) {
			AbnormalAlarmMappingBuilder builder = new AbnormalAlarmMapping.AbnormalAlarmMappingBuilder(loadClass(apiAbnormalAlarm.getAlarmCauserClass()), apiAbnormalAlarm.getAlarmCauserWeight());
			builder.alarmCauserCode(apiAbnormalAlarm.getAlarmCauserCode());
			builder.ignoreNumbers(apiAbnormalAlarm.getIgnoreNumbers());
			builder.cycleTimeline(apiAbnormalAlarm.getCycleTimeline());
			builder.alarmReceiverEmail(apiAbnormalAlarm.getAlarmReceiverEmail());
			builder.alarmReceiverPhone(apiAbnormalAlarm.getAlarmReceiverPhone());
			mappings.add(builder.build());
		}
		
		return mappings;
	}
	
	/**
	 * 加载过滤器
	 * 
	 * @param apiFilters
	 * @return List
	 * @throws Exception
	 */
	private List<FilterMapping> loadFilterMappings(List<ApiFilter> apiFilters) throws Exception {
		List<FilterMapping> mappings = new ArrayList<FilterMapping>();
		
		for (ApiFilter apiFilter : apiFilters) {
			FilterMappingBuilder builder = new FilterMapping.FilterMappingBuilder(loadClass(apiFilter.getFilterClass()), apiFilter.getFilterRule());
			builder.identifier(apiFilter.getIdentifier());
			mappings.add(builder.build());
		}
		
		return mappings;
	}

	/**
	 * 加载实现者
	 * 
	 * @param apis
	 * @return List
	 * @throws Exception
	 */
	private List<ImplementorMapping> loadImplementorMappings(Collection<Api> apis) throws Exception {
		List<ImplementorMapping> mappings = new ArrayList<ImplementorMapping>();
		
		for (Api api : apis) {
			try {
				Class<?> implementorClass = loadClass(api.getApiImplementor().getImplementorClass());
				Method implementorClassMethod = loadClassMethod(implementorClass, api.getApiMethod(), api.getApiMethodParamTypes());
				ImplementorMappingBuilder builder = new ImplementorMapping.ImplementorMappingBuilder(api.getId(), implementorClassMethod, api.getApiVersion().getVersionNo(), api.getApiCategory().getOwnership(), api.getApiWeight());
				builder.identifier(api.getApiImplementor().getIdentifier());
				builder.implementorClass(implementorClass);
				builder.isSpringManagedInstance(api.getApiImplementor().getIsSpringManagedInstance());
				builder.addInputParam(loadInputParams(api.getApiInputParams()));
				builder.addOutputParam(loadOutputParams(api.getApiOutputParams()));
				builder.addValidatorMapping(loadValidatorMappings(api.getApiVersion().getApiVersionInputParams()));
				builder.addValidatorMapping(loadValidatorMappings(api.getApiInputParams()));
				builder.addInterceptorMapping(loadInterceptorMappings(api.getApiVersion().getApiVersionInterceptorMappers()));
				builder.addInterceptorMapping(loadInterceptorMappings(api.getApiInterceptorMappers()));
				mappings.add(builder.build());
			} catch (Exception ex) {
				if (ignoreClassNotFound && ClassNotFoundException.class.isInstance(ex)) {
					logger.info("Ignore 'ClassNotFoundException' because variable ignoreClassNotFound is 'true' for the current environment.", ex);
					continue;
				}
				if (ignoreNoSuchMethod && NoSuchMethodException.class.isInstance(ex)) {
					logger.info("Ignore 'NoSuchMethodException' because variable ignoreNoSuchMethod is 'true' for the current environment.", ex);
					continue;
				}
				throw ex;
			}
		}
		
		return mappings;
	}
	
	/**
	 * 加载验证器
	 * 
	 * @param apiInputParams
	 * @return List
	 * @throws Exception
	 */
	private List<ValidatorMapping> loadValidatorMappings(Set<? extends InputParam> inputParams) throws Exception {
		List<ValidatorMapping> mappings = new ArrayList<ValidatorMapping>();
		
		for (InputParam inputParam : inputParams) {
			Set<ApiInputParamMetadata> apiInputParamMetadatas = inputParam.getApiInputParamMetadatas();
			for (ApiInputParamMetadata apiInputParamMetadata : apiInputParamMetadatas) {
				ApiValidator apiValidator = apiInputParamMetadata.getApiValidator();
				ValidatorMappingBuilder builder = new ImplementorMapping.ValidatorMappingBuilder(apiValidator.getIdentifier(), loadClass(apiValidator.getValidatorClass()), inputParam.getParamName(), inputParam.getParamDataType(), apiValidator.getMessagePattern());
				builder.messageKey(apiValidator.getMessageKey());
				builder.trim(apiInputParamMetadata.getTrim());
				builder.expression(apiInputParamMetadata.getExpression());
				builder.caseSensitive(apiInputParamMetadata.getCaseSensitive());
				builder.minValue(apiInputParamMetadata.getMinValue());
				builder.maxValue(apiInputParamMetadata.getMaxValue());
				builder.minLength(apiInputParamMetadata.getMinLength());
				builder.maxLength(apiInputParamMetadata.getMaxLength());
				mappings.add(builder.build());
			}
		}
		
		return mappings;
	}
	
	/**
	 * 加载拦截器
	 * 
	 * @param apiInterceptorMappers
	 * @return List
	 * @throws Exception
	 */
	private List<InterceptorMapping> loadInterceptorMappings(Set<? extends InterceptorMapper> interceptorMappers) throws Exception {
		List<InterceptorMapping> mappings = new ArrayList<InterceptorMapping>();
		
		for (InterceptorMapper interceptorMapper : interceptorMappers) {
			ApiInterceptor apiInterceptor = interceptorMapper.getApiInterceptor();
			InterceptorMappingBuilder builder = new ImplementorMapping.InterceptorMappingBuilder(apiInterceptor.getIdentifier(), loadClass(apiInterceptor.getInterceptorClass()));
			mappings.add(builder.build());
		}
		
		return mappings;
	}
	
	/**
	 * 加载输入参数名称
	 * 
	 * @param inputParams
	 * @return Map
	 * @throws Exception
	 */
	private Map<String, String> loadInputParams(Set<? extends InputParam> inputParams) throws Exception {
		Map<String, String> inputParamMaps = new HashMap<String, String>();
		
		for (InputParam inputParam : inputParams) {
			inputParamMaps.put(inputParam.getParamName(), inputParam.getParamDefaultData());
		}
		
		return inputParamMaps;
	}
	
	/**
	 * 加载输出参数名称
	 * 
	 * @param outputParams
	 * @return Set
	 * @throws Exception
	 */
	private Set<String> loadOutputParams(Set<? extends OutputParam> outputParams) throws Exception {
		Set<String> outputParamNames = new HashSet<String>();
		
		for (OutputParam outputParam : outputParams) {
			outputParamNames.add(outputParam.getParamName());
		}
		
		return outputParamNames;
	}

	/**
	 * 加载IP黑名单
	 */
	protected List<String> loadIpBlackList() throws Exception {
		PageFinder<MonitorIpBlackList> page = apiMonitorService.queryMonitorIpBlacklist("", null, null, new Query(100));
		List<String> list = new ArrayList<String>();
	    if(page !=null && page.getData()!=null && page.getData().size() > 0){
	    	for(MonitorIpBlackList ip:page.getData()){
	    		logger.info("拦载IP访问："+ip.getIp());
	    		list.add(ip.getIp());
	    	}
	    }
		
		return list;
	}



	
	
}
