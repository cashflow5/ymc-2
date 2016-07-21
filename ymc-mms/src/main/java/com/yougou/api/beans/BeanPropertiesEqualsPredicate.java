package com.yougou.api.beans;

import java.util.Map;

/**
 * 类多属性相等断言
 * 
 * @author yang.mq
 *
 */
public class BeanPropertiesEqualsPredicate extends CustomPredicate {

	private Map<String, Object> properties;
	
	public BeanPropertiesEqualsPredicate(Map<String, Object> properties) {
		this.properties = properties;
	}

	@Override
	public boolean evaluate(Object bean) {
		try {
			int equalsCount = 0;
			for (Map.Entry<String, Object> entry : properties.entrySet()) {
				equalsCount += new BeanPropertyEqualsPredicate(entry.getKey(), entry.getValue()).evaluate(bean) ? 1 : 0;
			}
			return (equalsCount == properties.size());
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}
}
