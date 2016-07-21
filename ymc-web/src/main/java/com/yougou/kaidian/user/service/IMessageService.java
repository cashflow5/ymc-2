package com.yougou.kaidian.user.service;  

import com.yougou.kaidian.user.model.pojo.Message;

public interface IMessageService {
	/**
	 * saveMessage:新增一条发送信息的日志记录
	 * @author li.n1 
	 * @param message 
	 * @since JDK 1.6 
	 * @date 2015-10-12 下午4:02:20
	 */
	void saveMessage(Message message);
	
}
