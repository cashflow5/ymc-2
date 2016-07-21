package com.yougou.api.web.event;

import com.mongodb.DBObject;

/**
 * API 日志事件
 * 
 * @author yang.mq
 * 
 */
public class ApplicationProgramInterfaceLogEvent extends ApplicationProgramInterfaceEvent {

	private static final long serialVersionUID = -7515325623193094396L;

	public ApplicationProgramInterfaceLogEvent(DBObject source) {
		super(source);
	}

	@Override
	protected String getEventDescription() {
		return "MongoDB source [" + source + "]";
	}
}
