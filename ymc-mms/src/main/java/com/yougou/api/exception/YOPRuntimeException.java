package com.yougou.api.exception;

import com.belle.infrastructure.schema.ErrorCode;

/**
 * API系统异常类<br>
 * 
 * @author 杨梦清
 * 
 */
public class YOPRuntimeException extends RuntimeException implements ErrorCode {

	private static final long serialVersionUID = -1657560845224431422L;

	private String errorCode;
	

	public YOPRuntimeException(String errorCode) {
		this(errorCode, null);
	}

	public YOPRuntimeException(String errorCode, String errorMessage) {
		
		this(errorCode, errorMessage, null);
	}

	public YOPRuntimeException(String errorCode, String errorMessage, Throwable errorCause) {
		super(errorMessage, errorCause);
		this.errorCode = errorCode;
		//logger.info(errorMessage);
	
	}

	@Override
	public String getErrorCode() {
		return this.errorCode;
	}

	@Override
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}



	
}
