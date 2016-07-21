package com.yougou.api;

import java.util.Map;


/**
 * 消息通知者
 * 
 * @author 杨梦清
 * 
 */
public interface Notifier {
	
	/**
	 * 添加接收者
	 * 
	 * @param receiver
	 */
	void addReceiver(Receiver receiver);
	
	/**
	 * 删除接收者
	 * 
	 * @param receiver
	 */
	void removeReceiver(Receiver receiver);
	
	/**
	 * 删除所有接收者
	 * 
	 */
	void removeAllReceiver();
	
	/**
	 * 通知所有接收者
	 * 
	 * @param message
	 */
	void notifyAllReceiver(Object message);
	
	/**
	 * 通知时间戳
	 * 
	 * @return long
	 */
	long getNotifyTimestamp();
	
	/**
	 * 通知上下文
	 * 
	 * @return Map
	 */
	Map<Object, Object> getNotifyContext();
}

