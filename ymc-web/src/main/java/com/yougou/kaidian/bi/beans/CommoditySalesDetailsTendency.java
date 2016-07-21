package com.yougou.kaidian.bi.beans;

/**
 * 商品销售明细走势枚举
 * 
 * @author yang.mq
 *
 */
public enum CommoditySalesDetailsTendency {

	PURCHASE_QUANTITY("下单量", 0, 0),
	PURCHASE_CUSTOMER_NUM("下单商品件数", 0, 0),
	PURCHASE_AMOUNT("下单金额", 0, 0),
	PURCHASE_TRADED_AMOUNT("成交金额", 0, 0)
	;
	
	private String description;
	private int xAxis;
	private int yAxis;

	private CommoditySalesDetailsTendency(String description, int xAxis, int yAxis) {
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
