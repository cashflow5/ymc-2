package com.yougou.kaidian.user.dao;  

import com.yougou.kaidian.user.model.pojo.Message;

/**
 * ClassName: MessageMapper
 * Desc: 信息（短信、邮件）
 * date: 2015-10-12 下午2:29:55
 * @author li.n1 
 * @since JDK 1.6
 */
public interface MessageMapper {
	/**
	 * saveMessage:新增一条信息记录 
	 * @author li.n1 
	 * @param message 
	 * @since JDK 1.6 
	 * @date 2015-10-12 下午2:47:28
	 */
	void saveMessage(Message message);
}
