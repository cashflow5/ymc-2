package com.belle.yitiansystem.merchant.service.impl;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;
@Service(value="punishOrderErrorHandler")
public class PunishOrderErrorHandler implements ErrorHandler {
	
	private static Logger logger = Logger.getLogger(PunishOrderErrorHandler.class);
	
	@Override
	public void handleError(Throwable t) {
		logger.error("Receive rabbitmq message error。");
	}

}
