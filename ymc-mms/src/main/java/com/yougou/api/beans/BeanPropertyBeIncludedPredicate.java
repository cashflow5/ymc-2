package com.yougou.api.beans;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;

/**
 * 类属性匹配数组断言
 * 
 * @author yang.mq
 *
 */
public class BeanPropertyBeIncludedPredicate extends CustomPredicate {

	private String propertyName;
	private Object[] propertyValues;
	
	public BeanPropertyBeIncludedPredicate(String propertyName, Object[] propertyValues) {
		this.propertyName = propertyName;
		this.propertyValues = propertyValues;
	}

	@Override
	public boolean evaluate(Object bean) {
		try {
			return ArrayUtils.contains(propertyValues, PropertyUtils.getProperty(bean, propertyName));
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}
}
