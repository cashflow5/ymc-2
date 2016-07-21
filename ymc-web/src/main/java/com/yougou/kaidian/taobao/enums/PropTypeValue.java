package com.yougou.kaidian.taobao.enums;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-31 下午2:36:45
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public enum PropTypeValue {
	PROPTYPE20000("20000", "品牌");
	private String value;
	private PropTypeValue(String value, String desc) {
		this.value = value;
	}
	public String getValue() {
		return this.value;
	}
}
