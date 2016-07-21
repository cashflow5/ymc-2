package com.belle.yitiansystem.merchant.exception;

/**
 * 招商系统异常类<br>
 * 
 * @author 杨梦清
 * 
 */
public class MerchantSystemException extends RuntimeException {

	private static final long serialVersionUID = -1657560845224431422L;

	private String errorCode;

	public MerchantSystemException(String errorCode) {
		
		this(errorCode, null);
	}

	public MerchantSystemException(String errorCode, String errorMessage) {
		
		this(errorCode, errorMessage, null);
	}

	public MerchantSystemException(String errorCode, String errorMessage, Throwable errorCause) {
		
		super(errorMessage, errorCause);
		
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		
		return this.errorCode;
	}
}
