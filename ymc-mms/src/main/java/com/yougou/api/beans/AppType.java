package com.yougou.api.beans;

/**
 * App 类型
 * 
 * @author yang.mq
 * 
 */
public enum AppType {
	 ALL("全部"), MERCHANTS("招商"), CHAIN("分销");

	private String description;

	private AppType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
