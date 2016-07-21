package com.yougou.api.exception.alarm;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.belle.infrastructure.schema.ErrorCode;
import com.yougou.api.Notifier;
import com.yougou.api.Receiver;
import com.yougou.api.cfg.AbnormalAlarmMapping;

/**
 * 
 * @author 杨梦清
 * 
 */
public abstract class CyclerAbnormalAlarm implements Receiver {

	private static final String PREVIOUS_TIME_MILLIS = "previousTimeMillis";

	private static final String IGNORE_NUMBERS = "ignoreNumbers";
	
	/** 异常报警配置映射 **/
	protected AbnormalAlarmMapping mapping;

	public CyclerAbnormalAlarm(AbnormalAlarmMapping mapping) {
		this.mapping = mapping;
	}

	@Override
	public void receive(Notifier notifier, Object message) {
		// 检查当前消息是否可处理
		if (!mapping.getAlarmCauserClass().isInstance(message)) {
			return;
		}
		String errorCode = ErrorCode.class.isInstance(message) ? ((ErrorCode) message).getErrorCode() : "*";
		if (!"*".equals(errorCode) && !mapping.getAlarmCauserCode().equals(errorCode)) {
			return;
		}
		
		// 是否进行处理
		boolean isProcess = true;
		// 生成消息索引键
		String key = mapping.getAlarmCauserClass().getName() + errorCode;
		// 共享数据容器
		Map sharedMap = MapUtils.getMap(notifier.getNotifyContext(), mapping, new HashMap());
		// 独占数据容器
		Map unsharedMap = MapUtils.getMap(sharedMap, key, new HashMap());
		// 已忽略报警次数
		int ignoreNumbers = MapUtils.getIntValue(unsharedMap, IGNORE_NUMBERS, 0);
		// 上次报警时间戳
		long previousTimeMillis = MapUtils.getLongValue(unsharedMap, PREVIOUS_TIME_MILLIS, 0L);
		// 通知报警时间戳
		long notifyTimestamp = notifier.getNotifyTimestamp();
		// 差额报警时间戳
		long balanceTimeMillis = notifyTimestamp - previousTimeMillis;

		if (balanceTimeMillis == notifyTimestamp) {// 初次报警处理
			previousTimeMillis = notifyTimestamp;
			ignoreNumbers = 1;
		} else if (mapping.getCycleTimeline() != null) {
			// 判断系统设置的周期时间已小于差额报警时间戳
			// 小于：则重置相关变量
			// 大于等于：则累加已忽略报警次数
			if (mapping.getCycleTimeline() < balanceTimeMillis) {
				previousTimeMillis = notifyTimestamp;
				ignoreNumbers = 1;
			} else {
				ignoreNumbers++;
			}
			// 计算已忽略报警次数是否已超出系统忽略报警次数
			isProcess = Math.ceil(ignoreNumbers / 2D) > mapping.getIgnoreNumbers();
		} else {// 默认累加操作用于平衡次数(作用为：(EMAIL忽略报警次数 + SMS忽略报警次数) / 2 == 系统忽略报警次数)
			ignoreNumbers++;
		}

		// 将相关变量重新加入通者上下文
		unsharedMap.put(PREVIOUS_TIME_MILLIS, previousTimeMillis);
		unsharedMap.put(IGNORE_NUMBERS, ignoreNumbers);
		sharedMap.put(key, unsharedMap);
		notifier.getNotifyContext().put(mapping, sharedMap);
		
		// 处理报警消息
		if (isProcess && (mapping.getAlarmCauserWeight() & getWeight()) == getWeight()) {
			processMessage((Throwable) message, notifyTimestamp);
		}
	}

	/**
	 * 获取可处理的异常报警类型
	 * 
	 * @return
	 */
	protected abstract Long getWeight();

	/**
	 * 处理异常报警信息
	 * 
	 * @param message
	 * @param timestamp
	 */
	protected abstract void processMessage(Throwable message, long timestamp);
}
