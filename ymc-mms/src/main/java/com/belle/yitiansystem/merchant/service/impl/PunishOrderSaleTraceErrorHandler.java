package com.belle.yitiansystem.merchant.service.impl;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

@Service(value="punishOrderSaleTraceErrorHandler")
public class PunishOrderSaleTraceErrorHandler implements ErrorHandler {
	
	private static Logger logger = Logger.getLogger(PunishOrderSaleTraceErrorHandler.class);
	
	@Override
	public void handleError(Throwable t) {
		logger.error("接收商家工单提醒MQ时发生错误。"); // error
	}

}
