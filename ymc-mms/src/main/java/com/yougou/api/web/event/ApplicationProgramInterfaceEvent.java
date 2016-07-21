package com.yougou.api.web.event;

import org.springframework.context.ApplicationEvent;

public abstract class ApplicationProgramInterfaceEvent extends ApplicationEvent {

	private static final long serialVersionUID = -6005115179460993212L;

	public ApplicationProgramInterfaceEvent(Object source) {
		super(source);
	}

	/**
	 * 获取事件描述
	 * 
	 * @return String
	 */
	protected abstract String getEventDescription();
}
