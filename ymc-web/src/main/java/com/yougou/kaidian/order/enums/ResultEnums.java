package com.yougou.kaidian.order.enums;
public enum ResultEnums {
	SUCCESS("success"), FAIL("fail");
	private String resultMsg;

	private ResultEnums(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getResultMsg() {
		return this.resultMsg;
	}
}