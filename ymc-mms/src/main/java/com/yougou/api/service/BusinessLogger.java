package com.yougou.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BusinessLogger {
	// 业务日志的logger
	private static Logger businessLogger = LoggerFactory.getLogger("openapiBusiness");

	public void log(String api,String errorCode, String errorMsg, String merchantCode) {
		businessLogger.info("api[{}],errorCode[{}],errorMsg[{}],merchantCode[{}]", new Object[]{api,errorCode,errorMsg,merchantCode});
	}
	
	public void log(String message) {
		businessLogger.info(message);
	}
}
