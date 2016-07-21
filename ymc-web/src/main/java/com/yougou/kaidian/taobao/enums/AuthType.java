package com.yougou.kaidian.taobao.enums;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-31 下午2:36:45
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public enum AuthType {
	STATUS0("0", "保存"), STATUS1("1", "申请APPKEY"), STATUS2("2", "审核通过"), STATUS3(
			"3", "已授权"), STATUS_1("-1", "禁用");
	private String status;
	private AuthType(String status, String desc) {
		this.status = status;
	}
	public String getStatus() {
		return this.status;
	}
}
