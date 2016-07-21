package com.yougou.api.validator.impl;

import java.text.MessageFormat;
import java.util.regex.Pattern;

import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.ValidationException;

/**
 * 正规表达式样验器
 * 
 * @author 杨梦清 
 * @date Apr 13, 2012 12:24:16 PM
 */
public class RegexFieldValidator extends StringRequiredFieldValidator {

	private String expression;
	private boolean caseSensitive;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	@Override
	public void fieldValueValidate(Object fieldValue) throws ValidationException {
		super.fieldValueValidate(fieldValue);
		
		String value = fieldValue.toString();
		if (isTrim()) value = value.trim();
		
		// 正规表达式
		Pattern pattern = caseSensitive ? Pattern.compile(expression) : Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		if (!pattern.matcher(value).matches()) {
			String message = MessageFormat.format(getMessagePattern(), getFieldName(), expression);
			throw new ValidationException(YOPBusinessCode.PARAM_REGULAR_EXPRESSION_NOT_MATCHED, message);
		}
	}
}
