package com.yougou.api.validator.impl;

import java.text.MessageFormat;

import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.ValidationException;

/**
 * 字符串长度校验器
 * 
 * @author 杨梦清 
 * @date Apr 13, 2012 12:24:01 PM
 */
public class StringLengthFieldValidator extends StringRequiredFieldValidator {

	private Integer minLength;
	private Integer maxLength;

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	@Override
	public void fieldValueValidate(Object fieldValue) throws ValidationException {
		super.fieldValueValidate(fieldValue);
		
		String value = fieldValue.toString();
		if (isTrim()) value = value.trim();
		
		// 比较最小长度
		if (minLength != null && value.length() < minLength.intValue()) {
			String message = MessageFormat.format(getMessagePattern(), getFieldName(), "less", minLength);
			throw new ValidationException(YOPBusinessCode.PARAM_STRING_MINLENGTH_OVERFLOW, message);
		}
		// 比较最大长度
		if (maxLength != null && value.length() > maxLength.intValue()) {
			String message = MessageFormat.format(getMessagePattern(), getFieldName(), "greater", maxLength);
			throw new ValidationException(YOPBusinessCode.PARAM_STRING_MAXLENGTH_OVERFLOW, message);
		}
	}

}
