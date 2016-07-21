package com.yougou.api.exception;

import org.apache.log4j.Logger;

import com.yougou.api.constant.YOPBusinessCode;


/**
 * 业务异常
 * @author he.wc
 *
 */
public class BusinessServiceException extends YOPRuntimeException {

	private static final long serialVersionUID = 5422338573226076148L;
	
	//private static final Logger logger = Logger.getLogger("openapiBusiness");
	
	
	public BusinessServiceException(String errorCode, String errorMessage, Throwable errorCause) {
		super(errorCode, errorMessage, errorCause);
		//logger.info(errorMessage);
	}

	public BusinessServiceException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
		//logger.info(errorMessage);
	}

	public BusinessServiceException(String errorMessage) {
		super(YOPBusinessCode.RUNTIME_ERROR,errorMessage);
		//logger.info(errorMessage);
	}
	
}

