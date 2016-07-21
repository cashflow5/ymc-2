package com.yougou.api.util;

import org.springframework.beans.BeansException;
import org.springframework.web.context.ContextLoader;

/**
 * Spring自动装配工具
 * 
 * @author yang.mq
 *
 */
public abstract class SpringAutowireUtils {

	/**
	 * 自动装配Bean
	 * 
	 * @param existingBean
	 * @throws BeansException
	 */
	public static void autowireBean(Object existingBean) throws BeansException {
		ContextLoader.getCurrentWebApplicationContext().getAutowireCapableBeanFactory().autowireBean(existingBean);
	}
	
	/**
	 * 创建并自动装配Bean
	 * 
	 * @param beanClass
	 * @param autowireMode
	 * @param dependencyCheck
	 * @return T
	 * @throws BeansException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T autowire(Class<T> beanClass, int autowireMode, boolean dependencyCheck) throws BeansException {
		return (T) ContextLoader.getCurrentWebApplicationContext().getAutowireCapableBeanFactory().autowire(beanClass, autowireMode, dependencyCheck);
	}
	
	/**
	 * 自动装配Bean属性
	 * 
	 * @param existingBean
	 * @param autowireMode
	 * @param dependencyCheck
	 * @throws BeansException
	 */
	public static void autowireBeanProperties(Object existingBean, int autowireMode, boolean dependencyCheck) throws BeansException {
		ContextLoader.getCurrentWebApplicationContext().getAutowireCapableBeanFactory().autowireBeanProperties(existingBean, autowireMode, dependencyCheck);
	}
	
	/**
	 * 自动装配Bean所有属性
	 * 
	 * @param existingBean
	 * @param beanName
	 * @throws BeansException
	 */
	public static void applyBeanPropertyValues(Object existingBean, String beanName) throws BeansException {
		ContextLoader.getCurrentWebApplicationContext().getAutowireCapableBeanFactory().applyBeanPropertyValues(existingBean, beanName);
	}
}
