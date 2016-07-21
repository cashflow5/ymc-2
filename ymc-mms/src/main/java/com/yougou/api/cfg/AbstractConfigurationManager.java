package com.yougou.api.cfg;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.YOPRuntimeException;

public abstract class AbstractConfigurationManager implements ConfigurationManager {

	protected final Logger logger = Logger.getLogger(getClass());

	/** 系统默认版本号 **/
	private static final String DEFAULT_VERSION_NO = "1.0";

	/** 缓存锁等待时间 **/
	private static final long LOCK_WAIT_TIME = 18000L;

	/** 更新缓存锁 **/
	private Lock locker;

	/** 更新缓存锁是否开启 **/
	private boolean isLocked;

	/** 配置实例是否持化 **/
	private boolean isPersistent;

	/** 配置实例是否已配置 **/
	private boolean isConfigured;

	/** 上次更新缓存时间戳 **/
	private long previousTimestamp;

	/** 过滤器映射实例 **/
	private List<FilterMapping> filterMappings;

	/** 异常报警器映射实例 **/
	private List<AbnormalAlarmMapping> abnormalAlarmMappings;

	/** 实现者映射实例 **/
	private Map<String, Map<Method, ImplementorMapping>> implementorMappingContext;

	/** API方法名与方法对象影子映射 **/
	private Map<String, Method> shadowMap;
	
	/** IP 黑名单  */
	private List<String> ipBlackList;
	
	/** 是否使用redis */
	private boolean isUseRedisConfigured = true;
	
	public AbstractConfigurationManager(boolean isPersistent) {
		this.isPersistent = isPersistent;
		this.locker = new ReentrantLock();
	}

	private void reset() {
		this.previousTimestamp = System.currentTimeMillis();
		this.filterMappings = new ArrayList<FilterMapping>();
		this.abnormalAlarmMappings = new ArrayList<AbnormalAlarmMapping>();
		this.implementorMappingContext = new HashMap<String, Map<Method, ImplementorMapping>>();
		this.shadowMap = new HashMap<String, Method>();
		this.ipBlackList = new ArrayList<String>();
	}

	@Override
	public void configure() throws Exception {
		List<FilterMapping> filterMappings = null;
		List<AbnormalAlarmMapping> abnormalAlarmMappings = null;
		Map<String, Map<Method, ImplementorMapping>> implementorMappingContext = null;
		Map<String, Method> shadowMap = null;
		long previousTimestamp = 0L;
		try {
			// 如果是重新配置，备份现有配置(主要预防当前配置异常后回滚上一版本)
			if (isConfigured) {
				filterMappings = new ArrayList<FilterMapping>(this.filterMappings);
				abnormalAlarmMappings = new ArrayList<AbnormalAlarmMapping>(this.abnormalAlarmMappings);
				implementorMappingContext = new HashMap<String, Map<Method, ImplementorMapping>>(this.implementorMappingContext);
				shadowMap = new HashMap<String, Method>(this.shadowMap);
				previousTimestamp = this.previousTimestamp;
				ipBlackList = new ArrayList<String>(this.ipBlackList);
			}
			// 读取锁开始进行配置
			if (isLocked = locker.tryLock(6000L, TimeUnit.MILLISECONDS)) {
				this.reset();
				this.filterMappings = this.loadFilterMappings();
				this.abnormalAlarmMappings = this.loadAbnormalAlarmMappings();
				this.addImplementorMapping(this.loadImplementorMappings());
				this.ipBlackList = this.loadIpBlackList();
				this.isConfigured = true;
			}
		} catch (Exception ex) {
			this.filterMappings = filterMappings;
			this.abnormalAlarmMappings = abnormalAlarmMappings;
			this.implementorMappingContext = implementorMappingContext;
			this.shadowMap = shadowMap;
			this.previousTimestamp = previousTimestamp;
			throw ex;
		} finally {
			if (isLocked) {
				isLocked = false;
				locker.unlock();
			}
		}
	}

	@Override
	public Enumeration<FilterMapping> getFilterMappings() {
		Enumeration<FilterMapping> enumeration;

		try {
			// 如果当前有锁等待1分钟
			for (long i = LOCK_WAIT_TIME, j = 1000L; isLocked && i > 0; i -= j) {
				Thread.currentThread().sleep(j);
			}
			// 如果1分钟后还有锁抛出请求资源超时异常
			if (isLocked) {
				throw new RuntimeException("Request resource timeout.");
			}
			previousTimestamp = System.currentTimeMillis();
			enumeration = Collections.enumeration(filterMappings);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Error getting filter mapping objects.", ex);
		}

		return enumeration;
	}
	

	@Override
	public boolean checkIpInvaild(String ip) {
		return this.ipBlackList.contains(ip);
	}
	
	

	@Override
	public boolean checkUseRedis() {
		return this.isUseRedisConfigured;
	}

	@Override
	public void disableUseRedis(boolean useRedis){
		this.isUseRedisConfigured = useRedis;
		
	}

	public Enumeration<AbnormalAlarmMapping> getAbnormalAlarmMappings() {
		return Collections.enumeration(abnormalAlarmMappings);
	}

	@Override
	public void addImplementorMapping(ImplementorMapping mapping) {
		if (mapping == null) {
			throw new IllegalArgumentException("Mapping object must not be null.");
		}
		if (mapping.getApiVersionNo() == null) {
			throw new IllegalArgumentException("Mapping object api version no must not be null.");
		}
		if (mapping.getApiMethod() == null) {
			throw new IllegalArgumentException("Mapping object api method must not be null.");
		}

		Map<Method, ImplementorMapping> implementorMappings = this.implementorMappingContext.get(mapping.getApiVersionNo());
		if (implementorMappings == null) {
			implementorMappings = new HashMap<Method, ImplementorMapping>();
			implementorMappings.put(mapping.getApiMethod(), mapping);
			this.implementorMappingContext.put(mapping.getApiVersionNo(), implementorMappings);
		} else {
			if (implementorMappings.containsKey(mapping.getApiMethod())) {
				throw new IllegalArgumentException("Error registering mapping object with api method '" + mapping.getApiMethod() + "' the api in version '" + mapping.getApiVersionNo() + "' is already registered.");
			}
			implementorMappings.put(mapping.getApiMethod(), mapping);
		}
	}

	@Override
	public void addImplementorMapping(Collection<ImplementorMapping> mappings) {
		if (mappings == null) {
			throw new IllegalArgumentException("Mapping objects must not be null.");
		}

		for (ImplementorMapping mapping : mappings) {
			addImplementorMapping(mapping);
		}
	}

	@Override
	public ImplementorMapping getImplementorMapping(String method) {
		return getImplementorMapping(method, DEFAULT_VERSION_NO);
	}

	@Override
	public ImplementorMapping getImplementorMapping(String method, String version) {
		if (method == null || method.trim().equals("")) {
			throw new YOPRuntimeException(YOPBusinessCode.PARAM_NOT_REQUIRED, "method is required");
		}

		Map<Method, ImplementorMapping> implementorMappings = this.implementorMappingContext.get(version);
		if (implementorMappings == null) {
			throw new YOPRuntimeException(YOPBusinessCode.API_VERSION_IS_INVALID, "version is invalid");
		}

		Method shadow = shadowMap.get(method);
		if (shadow == null) {
			throw new YOPRuntimeException(YOPBusinessCode.API_METHOD_IS_INVALID, "method is invalid");
		}

		return new ImplementorMapping(implementorMappings.get(shadow));
	}

	/**
	 * 加载类
	 * 
	 * @param className
	 * @return Class
	 * @throws Exception
	 */
	protected Class<?> loadClass(String className) throws Exception {
		return Class.forName(className);
	}

	/**
	 * 加载类方法
	 * 
	 * @param clazz
	 * @param methodName
	 * @param methodParamTypes
	 * @return Method
	 * @throws Exception
	 */
	protected Method loadClassMethod(Class<?> clazz, String methodName, String methodParamTypes) throws Exception {
		String clone = methodName.intern();
		// 组装方法名称
		String[] strings = StringUtils.split(methodName, '.');
		methodName = strings[strings.length - 1];
		for (int i = strings.length - 2; i > 0; i--) {
			methodName += StringUtils.capitalize(strings[i]);
		}
		// 组装方法参数类型列表
		strings = StringUtils.split(methodParamTypes, ',');
		Class<?>[] classes = new Class<?>[strings.length];
		for (int i = 0; i < classes.length; i++) {
			classes[i] = loadClass(strings[i]);
		}
		// 按方法名称与方法参数类型列表加载类方法
		Method method;
		try {
			method = clazz.getMethod(methodName, classes);
		} catch (Exception e) {
			method = clazz.getMethod(methodName);
		}
		// 加入影子映射
		shadowMap.put(clone, method);

		return method;
	}

	/**
	 * 加载实现者映射
	 * 
	 * @return List
	 * @throws Exception
	 */
	protected abstract List<ImplementorMapping> loadImplementorMappings() throws Exception;

	/**
	 * 加载过滤器映射
	 * 
	 * @return List
	 * @throws Exception
	 */
	protected abstract List<FilterMapping> loadFilterMappings() throws Exception;

	/**
	 * 加载异常报警映射
	 * 
	 * @return List
	 * @throws Exception
	 */
	protected abstract List<AbnormalAlarmMapping> loadAbnormalAlarmMappings() throws Exception;
	
	/**
	 * 加载IP黑名单
	 * @return
	 * @throws Exception
	 */
	protected abstract List<String> loadIpBlackList() throws Exception;
}
