package com.yougou.kaidian.taobao.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TaobaoApiReturnData<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public T getReturnData() {
		return returnData;
	}

	public void setReturnData(T returnData) {
		this.returnData = returnData;
	}

	private String body;
	private T returnData;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
