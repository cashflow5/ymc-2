package com.yougou.api;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.yougou.api.cfg.ImplementorMapping;
import com.yougou.api.cfg.ImplementorMapping.InterceptorMapping;
import com.yougou.api.cfg.ImplementorMapping.ValidatorMapping;
import com.yougou.api.constant.BitWeight;
import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.ServiceException;
import com.yougou.api.exception.YOPRuntimeException;
import com.yougou.api.interceptor.Interceptor;
import com.yougou.api.validator.Validator;

/**TransientThreadLocalHolder
 * 实现者代理实现
 * 
 * @author 杨梦清 
 * @date Apr 11, 2012 11:50:28 AM
 */
public class ImplementorProxyImpl implements ImplementorProxy {
	
	private static final Logger LOGGER = Logger.getLogger(ImplementorProxy.class);
	
	private BeanFactory beanFactory;
	private ImplementorMapping mapping;
	private ImplementorInvocation invocation;
	private Implementor implementor;
	private List<Validator> validators;
	private List<Interceptor> interceptors;
	
	public ImplementorProxyImpl(ImplementorInvocation invocation) {
		this.invocation = invocation;
	}

	@Override
	public Implementor getImplementor() {
		return implementor;
	}

	@Override
	public ImplementorMapping getImplementorMapping() {
		return mapping;
	}

	@Override
	public ImplementorInvocation getImplementorInvocation() {
		return invocation;
	}

	@Override
	public Method getImplementMethod() {
		return mapping.getApiMethod();
	}

	@Override
	public List<Validator> getValidators() {
		return validators;
	}

	@Override
	public List<Interceptor> getInterceptors() {
		return interceptors;
	}
	
	@Override
	public void prepare(ImplementorMapping mapping, BeanFactory beanFactory) throws ServiceException {
		this.mapping = mapping;
		this.beanFactory = beanFactory;
		this.validators = new ArrayList<Validator>();
		this.interceptors = new ArrayList<Interceptor>();
		this.resolveImplement();
		this.resolveValidator();
		this.resolveInterceptor();
		this.invocation.init(this);
	}

	@Override
	public Object execute() throws ServiceException {
		return invocation.invoke();
	}
	
	/**
	 * 解析生成实现者
	 * 
	 * @throws Exception
	 */
	private void resolveImplement() {
		try {
			Class<?> implementorClass = mapping.getImplementorClass();
			if (mapping.isSpringManagedInstance()) {
				implementor = (Implementor) beanFactory.getBean(implementorClass);
			} else {
				implementor = (Implementor) implementorClass.newInstance();
				autoWireBean(implementor);
			}
		} catch (Exception ex) {
			throw new YOPRuntimeException(YOPBusinessCode.ERROR, ex.getMessage(), ex);
		}
	}
	
	/**
	 * 解析生成校验器
	 * 
	 * @throws Exception
	 */
	private void resolveValidator() {
		try {
			for (ValidatorMapping validatorMapping : mapping.getValidators()) {
				Validator validator = (Validator) validatorMapping.getValidatorClass().newInstance();
				BeanUtils.copyProperties(validator, validatorMapping);
				autoWireBean(validator);
				validators.add(validator);
			}
		} catch (Exception ex) {
			throw new YOPRuntimeException(YOPBusinessCode.ERROR, ex.getMessage(), ex);
		}
	}
	
	/**
	 * 解析生成拦截器
	 * 
	 * @throws Exception
	 */
	private void resolveInterceptor() {
		try {
			for (InterceptorMapping interceptorMapping  : mapping.getInterceptors()) {
				Interceptor interceptor = (Interceptor) interceptorMapping.getInterceptorClass().newInstance();
				BeanUtils.copyProperties(interceptor, interceptorMapping);
				autoWireBean(interceptor);
				interceptors.add(interceptor);
			}
		} catch (Exception ex) {
			throw new YOPRuntimeException(YOPBusinessCode.ERROR, ex.getMessage(), ex);
		}
	}
	
	/**
	 * 自动装配 Bean 属性
	 * 
	 * @param bean
	 * @throws Exception
	 */
	private <T> T autoWireBean(T bean) throws Exception {
		if (beanFactory == null) {
			LOGGER.warn("BeanFactory is null, skip the autowiring.");
			return bean;
		}
		if (!AutowireCapableBeanFactory.class.isInstance(beanFactory)) {
			LOGGER.warn("BeanFactory instance is not of AutowireCapableBeanFactory, Skip the autowiring.");
			return bean;
		}
		if (!BitWeight.AUTOWIRING.in(mapping.getApiWeight())) {
			LOGGER.warn("Implementor autowiring option is not enabled, Skip the autowiring.");
			return bean;
		}
		AutowireCapableBeanFactory.class.cast(beanFactory).autowireBeanProperties(bean, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Autowire '" + bean + "' by 'AUTOWIRE_NO' strategy.");
		}
		return bean;
	}
}
