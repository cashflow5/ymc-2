package com.yougou.kaidian.taobao.exception;


/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-21 下午3:58:34
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class BusinessException extends Exception {
	private static final long serialVersionUID = 2874310081615076500L;

	public BusinessException(String message, Throwable e) {
		super(message, e);
	}

	public BusinessException(String message) {
		super(message);
	}

}
