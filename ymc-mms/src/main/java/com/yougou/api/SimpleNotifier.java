package com.yougou.api;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

/**
 * 简单消息通知者
 * 
 * @author 杨梦清
 * 
 */
@Component
public class SimpleNotifier implements Notifier {
	
	/**
	 * 接收者列表
	 */
	private List<Receiver> receivers = new CopyOnWriteArrayList<Receiver>();
	
	/**
	 * 通知上下文
	 */
	private Map<Object, Object> notifyContext = new ConcurrentHashMap<Object, Object>();
	
	/**
	 * 锁
	 */
	private Object locker = new Object();
	
	/**
	 * 通时时间戳
	 */
	private long notifyTimestamp = 0L;

	@Override
	public void addReceiver(Receiver receiver) {
		if (!receivers.contains(receiver)) {
			receivers.add(receiver);
		}
	}

	@Override
	public void removeReceiver(Receiver receiver) {
		receivers.remove(receiver);
	}

	@Override
	public void removeAllReceiver() {
		receivers.clear();
	}

	@Override
	public void notifyAllReceiver(Object message) {
		synchronized (locker) {
			notifyTimestamp = System.currentTimeMillis();
			for (Receiver receiver : receivers) {
				receiver.receive(this, message);
			}
		}
	}
	
	@Override
	public long getNotifyTimestamp() {
		return notifyTimestamp;
	}

	@Override
	public Map<Object, Object> getNotifyContext() {
		return notifyContext;
	}
}
