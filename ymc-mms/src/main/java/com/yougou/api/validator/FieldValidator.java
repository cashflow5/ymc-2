package com.yougou.api.validator;

import com.yougou.api.exception.ValidationException;


public interface FieldValidator extends Validator {

	void fieldValueValidate(Object fieldValue) throws ValidationException;
}
