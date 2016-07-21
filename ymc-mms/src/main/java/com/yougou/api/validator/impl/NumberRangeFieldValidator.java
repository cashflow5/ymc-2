package com.yougou.api.validator.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;

import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.ValidationException;

/**
 * 数字大小校验器
 * 
 * @author 杨梦清 
 * @date Apr 13, 2012 12:25:36 PM
 */
public class NumberRangeFieldValidator extends StringRequiredFieldValidator {

	private String minValue;
	private String maxValue;
	
	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	@Override
	public void fieldValueValidate(Object fieldValue) throws ValidationException {
		super.fieldValueValidate(fieldValue);
		
		BigDecimal value = null;
		BigDecimal compareMinValue = null;
		BigDecimal compareMaxValue = null;
		
		try {
			value = new BigDecimal(fieldValue.toString().trim());
		} catch (NumberFormatException e) {
			throw new ValidationException(YOPBusinessCode.PARAM_TYPE_MISMATCH, getFieldName() + " is not a number");
		}
		
		try {
			if (minValue != null) {
				compareMinValue = new BigDecimal(minValue.trim());
			}
		} catch (NumberFormatException e) {
			throw new ValidationException(YOPBusinessCode.PARAM_TYPE_MISMATCH, "compare min value is not a number");
		}
		
		try {
			if (maxValue != null) {
				compareMaxValue = new BigDecimal(maxValue.trim());
			}
		} catch (NumberFormatException e) {
			throw new ValidationException(YOPBusinessCode.PARAM_TYPE_MISMATCH, "compare max value is not a number");
		}

		// 比较最小值
		if (compareMinValue != null && value.compareTo(compareMinValue) < 0) {
			String message = MessageFormat.format(getMessagePattern(), getFieldName(), "less", minValue);
			throw new ValidationException(YOPBusinessCode.PARAM_NUMBER_MINVALUE_OVERFLOW, message);
		}
		// 比较最大值
		if (compareMaxValue != null && value.compareTo(compareMaxValue) > 0) {
			String message = MessageFormat.format(getMessagePattern(), getFieldName(), "greater", maxValue);
			throw new ValidationException(YOPBusinessCode.PARAM_NUMBER_MINVALUE_OVERFLOW, message);
		}
	}

}
