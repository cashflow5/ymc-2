package com.yougou.kaidian.bi.beans;

public enum AnalyzePatttern {

	BY_DAY("按天", 0x1),
	BY_WEEK("按周", 0x2),
	BY_MONTH("按月", 0x4),
	BY_USER_DEFINED("自定义", 0x8)
	;
	
	private String description;
	private int weight;

	private AnalyzePatttern(String description, int weight) {
		this.description = description;
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}
	
	public int getWeight() {
		return weight;
	}
}
