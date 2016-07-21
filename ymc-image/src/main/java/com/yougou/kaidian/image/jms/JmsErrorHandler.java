package com.yougou.kaidian.image.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;
/**
 * 
 * @author li.m1
 *接收message异常处理
 */
@Component(value = "jmsErrorHandler")
public class JmsErrorHandler implements ErrorHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(JmsErrorHandler.class);
	
	@Override
	public void handleError(Throwable t) {
		logger.error("接收rabbitmq message error.详细信息：{}",t.getMessage()); // error
	}
}