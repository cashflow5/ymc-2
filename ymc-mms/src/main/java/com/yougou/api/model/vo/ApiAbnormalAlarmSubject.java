package com.yougou.api.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author 杨梦清
 * 
 */
public abstract class ApiAbnormalAlarmSubject implements Serializable {

	private static final long serialVersionUID = -7258501132431696602L;

	protected String code;// 报警代码
	protected String content;// 报警内容
	protected Date abnormalTime;// 异常时间
	protected String notifier;// 报警通知人
	protected Date notified;// 报警通知时间
	protected NotifyStyle notifyStyle;// 报警通知方式
	protected NotifyState notifyState;// 报警通知状态
	protected String receivers;// 接警人列表

	/**
	 * 报警通知方式
	 * 
	 * @author 杨梦清
	 * 
	 */
	public static enum NotifyStyle {
		EMAIL("邮件"), SMS("短信");
		
		private String label;

		private NotifyStyle(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

		public String getValue() {
			return name();
		}
	}

	/**
	 * 报警通知状态
	 * 
	 * @author 杨梦清
	 * 
	 */
	public static enum NotifyState {
		WAITING("待通知"), NOTIFYING("通知中"), COMPLETED("已通知");
		
		private String label;

		private NotifyState(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

		public String getValue() {
			return name();
		}
	}

	protected ApiAbnormalAlarmSubject(NotifyStyle notifyStyle) {
		this.notifyStyle = notifyStyle;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAbnormalTime() {
		return abnormalTime;
	}

	public void setAbnormalTime(Date abnormalTime) {
		this.abnormalTime = abnormalTime;
	}

	public String getNotifier() {
		return notifier;
	}

	public void setNotifier(String notifier) {
		this.notifier = notifier;
	}

	public Date getNotified() {
		return notified;
	}

	public void setNotified(Date notified) {
		this.notified = notified;
	}

	public String getReceivers() {
		return receivers;
	}

	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}

	public NotifyStyle getNotifyStyle() {
		return notifyStyle;
	}

	public NotifyState getNotifyState() {
		return notifyState;
	}

	public void setNotifyState(NotifyState notifyState) {
		this.notifyState = notifyState;
	}

}
