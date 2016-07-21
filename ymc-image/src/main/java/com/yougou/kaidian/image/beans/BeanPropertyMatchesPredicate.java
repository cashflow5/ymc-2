package com.yougou.kaidian.image.beans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;

/**
 * 类属性正则表达式断言
 * 
 * @author yang.mq
 *
 */
public class BeanPropertyMatchesPredicate extends CustomPredicate {

	private String propertyName;
	private Pattern propertyValuePattern;
	private boolean isGlobalMatches;

	public BeanPropertyMatchesPredicate(String propertyName, Pattern propertyValuePattern) {
		this(propertyName, propertyValuePattern, true);
	}

	public BeanPropertyMatchesPredicate(String propertyName, Pattern propertyValuePattern, boolean isGlobalMatches) {
		this.propertyName = propertyName;
		this.propertyValuePattern = propertyValuePattern;
		this.isGlobalMatches = isGlobalMatches;
	}

	@Override
	public boolean evaluate(Object bean) {
		try {
			String propertyValue = ObjectUtils.toString(PropertyUtils.getProperty(bean, propertyName));
			Matcher matcher = propertyValuePattern.matcher(propertyValue);
			return isGlobalMatches ? matcher.matches() : matcher.find();
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}
}
