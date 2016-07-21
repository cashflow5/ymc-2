package com.yougou.api.validator;

import java.util.Map;

import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.ValidationException;

public abstract class RequiredFieldValidator implements FieldValidator {

	private String fieldName;
	private String fieldDataType;
    private String messagePattern;
    private String messageKey;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldDataType() {
		return fieldDataType;
	}

	public void setFieldDataType(String fieldDataType) {
		this.fieldDataType = fieldDataType;
	}

	public String getMessagePattern() {
		return messagePattern;
	}

	public void setMessagePattern(String messagePattern) {
		this.messagePattern = messagePattern;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}
	
	@Override
	public void validate(Map<String, Object> parameters) throws ValidationException {
		Object fieldValue = parameters.get(fieldName);
		if (fieldValue == null) {
			throw new ValidationException(YOPBusinessCode.PARAM_NOT_REQUIRED, fieldName + " is required");
		}
		
		fieldValueValidate(fieldValue);
	}
	
}
