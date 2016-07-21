package com.yougou.api.exception;

/**
 * 验证异常
 * 
 * @author 杨梦清
 * 
 */
public class ValidationException extends YOPRuntimeException {

	private static final long serialVersionUID = 8692253095433570837L;

	public ValidationException(String errorCode, String errorMessage, Throwable errorCause) {
		super(errorCode, errorMessage, errorCause);
	}

	public ValidationException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public ValidationException(String errorCode) {
		super(errorCode);
	}
}

