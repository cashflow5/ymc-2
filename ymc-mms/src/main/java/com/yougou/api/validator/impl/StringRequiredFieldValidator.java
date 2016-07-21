package com.yougou.api.validator.impl;

import java.text.MessageFormat;

import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.ValidationException;
import com.yougou.api.validator.RequiredFieldValidator;

/**
 * 必须提供校验器
 * 
 * @author 杨梦清 
 * @date Apr 13, 2012 12:24:35 PM
 */
public class StringRequiredFieldValidator extends RequiredFieldValidator {

	private boolean trim;
	
	public boolean isTrim() {
		return trim;
	}
	
	public void setTrim(boolean trim) {
		this.trim = trim;
	}

	@Override
	public void fieldValueValidate(Object fieldValue) throws ValidationException {
		if (!String.class.isInstance(fieldValue)) {
			throw new ValidationException(YOPBusinessCode.PARAM_TYPE_MISMATCH, getFieldName() + " is not a string");
		}
		
		String value = fieldValue.toString();
		if (isTrim()) {
			value = value.trim();
		}
		if (value.length() == 0) {
			String message = MessageFormat.format(getMessagePattern(), getFieldName());
			throw new ValidationException(YOPBusinessCode.PARAM_STRING_IS_EMPTY, message);
		}
	}

}
