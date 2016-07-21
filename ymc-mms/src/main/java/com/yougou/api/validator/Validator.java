package com.yougou.api.validator;

import java.util.Map;

import com.yougou.api.exception.ValidationException;

public interface Validator {

	void validate(Map<String, Object> parameters) throws ValidationException;
}
