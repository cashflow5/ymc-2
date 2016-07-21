package com.yougou.api.validator.impl;

import java.text.MessageFormat;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;

import com.yougou.api.constant.Constants;
import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.ValidationException;
import com.yougou.api.validator.RequiredFieldValidator;

/**
 * 数据类型校验器
 * 
 * @author 杨梦清 
 * @date Apr 13, 2012 1:47:50 PM
 */
public class DataTypeFieldValidator extends RequiredFieldValidator {

	@Override
	public void fieldValueValidate(Object fieldValue) throws ValidationException {
		String fieldDataType = getFieldDataType();
		String strFieldValue = fieldValue.toString().trim();
		
		// 生成异常信息
		String message = MessageFormat.format(getMessagePattern(), getFieldName(), fieldDataType);
		
		// 是否为字符类型
		if ("String".equalsIgnoreCase(fieldDataType) && !String.class.isInstance(fieldValue)) {
			throw new ValidationException(YOPBusinessCode.PARAM_TYPE_MISMATCH, message);
		}
		// 是否为数字类型
		else if ("Number".equalsIgnoreCase(fieldDataType) && !NumberUtils.isNumber(strFieldValue)) {
			throw new ValidationException(YOPBusinessCode.PARAM_TYPE_MISMATCH, message);
		}
		// 是否为日期类型
		else if ("Date".equalsIgnoreCase(fieldDataType)) {
			try {
				DateUtils.parseDate(strFieldValue, Constants.DATE_PATTERNS);
			} catch (Exception e) {
				throw new ValidationException(YOPBusinessCode.PARAM_TYPE_MISMATCH, message);
			}
		}
		// 是否为数组类型
		else if ("Array".equalsIgnoreCase(fieldDataType) && !fieldValue.getClass().isArray()) {
			throw new ValidationException(YOPBusinessCode.PARAM_TYPE_MISMATCH, message);
		}
		// 是否为布尔类型
		else if ("Boolean".equalsIgnoreCase(getFieldDataType()) && !("true".equalsIgnoreCase(strFieldValue) || "false".equalsIgnoreCase(strFieldValue))) {
			throw new ValidationException(YOPBusinessCode.PARAM_TYPE_MISMATCH, message);
		}
	}
}
