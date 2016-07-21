package com.yougou.kaidian.taobao.enums;

public enum BindType {
	HAVEBIND("Y"), NOBIND("N");
	private String type;

	BindType(String type) {

		this.type = type;
	}

	public String getValue() {

		return type;
	}
}
