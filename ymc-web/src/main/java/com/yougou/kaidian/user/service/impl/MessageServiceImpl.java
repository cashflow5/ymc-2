package com.yougou.kaidian.user.service.impl;  

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yougou.kaidian.user.dao.MessageMapper;
import com.yougou.kaidian.user.model.pojo.Message;
import com.yougou.kaidian.user.service.IMessageService;

@Service
public class MessageServiceImpl implements IMessageService {
	@Resource
	private MessageMapper messageMapper;
	private static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	@Override
	public void saveMessage(Message message) {
		try{
			messageMapper.saveMessage(message);
		}catch(Exception e){
			logger.error("新增信息记录报错！",e);
		}
	}
	
}
