/**
 * 
 */
package com.yougou.kaidian.framework.exception;

/**
 * 
 * 商家中心业务异常
 * 
 * @author huang.tao
 *
 */
public class YMCException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	private String errorCode;
	
	public YMCException(String message, String errorCode) {
		this(message, errorCode, null);
	}
	
	public YMCException(String message, String errorCode, Throwable cause) {
		super(cause);
		this.message = message;
		this.errorCode = errorCode;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
