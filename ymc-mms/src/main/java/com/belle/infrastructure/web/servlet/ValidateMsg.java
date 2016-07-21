package com.belle.infrastructure.web.servlet;

import java.io.Serializable;

/**
 * 异步验证消息对象
 *
 * @author 罗正加
 * @history 2011-08-03 新建
 */
public class ValidateMsg implements Serializable {

	/** 序列化 */
	private static final long serialVersionUID = 1L;

	/** 验证结果：失败：0 ； 成功：1 */
	private int result;

	/** 验证失败消息 */
	private String msg;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
