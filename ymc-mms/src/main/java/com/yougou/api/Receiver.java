package com.yougou.api;

/**
 * 消息接收者
 * 
 * @author 杨梦清
 * 
 */
public interface Receiver {

	/**
	 * 处理接收消息
	 * 
	 * @param notifier
	 * @param message
	 */
	void receive(Notifier notifier, Object message);
}

