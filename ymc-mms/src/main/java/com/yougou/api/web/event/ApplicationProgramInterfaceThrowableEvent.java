package com.yougou.api.web.event;

/**
 * API 异常事件
 * 
 * @author yang.mq
 * 
 */
public class ApplicationProgramInterfaceThrowableEvent extends ApplicationProgramInterfaceEvent {

	private static final long serialVersionUID = -5294007238067637380L;

	public ApplicationProgramInterfaceThrowableEvent(Throwable source) {
		super(source);
	}

	@Override
	protected String getEventDescription() {
		return "Throwable event [" + source + "]";
	}
}
