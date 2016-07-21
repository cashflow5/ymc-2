package com.yougou.api.exception;


/**
 * 拦截异常
 * 
 * @author 杨梦清
 * 
 */
public class InterceptionException extends YOPRuntimeException {

	private static final long serialVersionUID = -4347801394543977727L;
	
	//private static final Logger logger = Logger.getLogger("openapiBusiness");

	public InterceptionException(String errorCode, String errorMessage, Throwable errorCause) {
		super(errorCode, errorMessage, errorCause);
		//logger.info(errorMessage);
	}

	public InterceptionException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
		//logger.info(errorMessage);
	}

	public InterceptionException(String errorCode) {
		super(errorCode);
	}

}

