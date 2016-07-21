package com.yougou.kaidian.bi.beans;


/**
 * 经营概况走势枚举
 * 
 * @author yang.mq
 *
 */
public enum SummaryOfOperationsTendency {
	
	PURCHASE_AMOUNT("下单金额", 0, 1),
	PURCHASE_QUANTITY("下单量", 0, 0),
	PURCHASE_CUSTOMER_NUM("下单客户数", 0, 0),
	PURCHASE_COMMODITY_NUM("下单商品件数", 0, 0),
	;
	
	private String description;
	private int xAxis;
	private int yAxis;

	private SummaryOfOperationsTendency(String description, int xAxis, int yAxis) {
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
