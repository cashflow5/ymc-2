package com.yougou.kaidian.bi.beans;

/**
 * 售后总览走势枚举
 * 
 * @author yang.mq
 *
 */
public enum AftersaleStatisticsTendency {
	
	RETURNS_NUM("退货数量", 0, 0),
	RETURNS_AMOUNT("退货金额", 0, 1),
	EXCHANGE_NUM("换货数量", 0, 0),
	EXCHANGE_AMOUNT("换货金额", 0, 1)
	;
	
	private String description;
	private int xAxis;
	private int yAxis;

	private AftersaleStatisticsTendency(String description, int xAxis, int yAxis) {
		this.description = description;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}

	public String getDescription() {
		return description;
	}

	public int getxAxis() {
		return xAxis;
	}

	public int getyAxis() {
		return yAxis;
	}
}
