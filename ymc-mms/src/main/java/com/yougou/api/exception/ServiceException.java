package com.yougou.api.exception;

/**
 * 服务异常
 * 
 * @author 杨梦清
 * 
 */
public class ServiceException extends YOPRuntimeException {

	private static final long serialVersionUID = 5422338573226076148L;

	public ServiceException(String errorCode, String errorMessage, Throwable errorCause) {
		super(errorCode, errorMessage, errorCause);
	}

	public ServiceException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public ServiceException(String errorCode) {
		super(errorCode);
	}
}

