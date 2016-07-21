package com.yougou.api.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

import com.belle.yitiansystem.merchant.service.impl.PunishOrderErrorHandler;

@Service(value="apiErrorHandler")
public class ApiErrorHandler implements ErrorHandler {

	private static Logger logger = Logger.getLogger(PunishOrderErrorHandler.class);
	
	@Override
	public void handleError(Throwable t) {
		t.printStackTrace();
		logger.error("Receive rabbitmq message error。");
	}
}
