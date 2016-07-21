package com.yougou.kaidian.framework.exception;

import com.yougou.kaidian.framework.beans.DubboProvider;

/**
 * Dubbo服务异常
 * 
 * @author yang.mq
 *
 */
public class DubboServiceException extends RuntimeException {

	private static final long serialVersionUID = -189247273304509786L;
	
	/** 服务提供者 **/
	private DubboProvider dubboProvider;

	public DubboServiceException(DubboProvider dubboProvider, String message) {
		this(dubboProvider, message, null);
	}
	
	public DubboServiceException(DubboProvider dubboProvider, Throwable cause) {
		this(dubboProvider, null, cause);
	}
	
	public DubboServiceException(DubboProvider dubboProvider, String message, Throwable cause) {
		super(message, cause);
		this.dubboProvider = dubboProvider;
	}

	public DubboProvider getDubboProvider() {
		return dubboProvider;
	}
}
