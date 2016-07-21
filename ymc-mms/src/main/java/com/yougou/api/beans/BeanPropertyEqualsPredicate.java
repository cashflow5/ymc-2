package com.yougou.api.beans;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 类属性相等断言
 * 
 * @author yang.mq
 *
 */
public class BeanPropertyEqualsPredicate extends CustomPredicate {

	private String propertyName;
	private Object propertyValue;
	
	public BeanPropertyEqualsPredicate(String propertyName, Object propertyValue) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}

	@Override
	public boolean evaluate(Object bean) {
		try {
			Object another = PropertyUtils.getProperty(bean, propertyName);
			return propertyValue == null ? another == null : propertyValue.equals(another);
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}
}
