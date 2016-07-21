package com.yougou.kaidian.bi.beans;

/**
 * 商品销售查询状态
 * 
 * @author yang.mq
 *
 */
public enum CommoditySalesQueryState {

	MORE_SALE("商品销售明细", true),
	ZERO_SALE("零成交商品");
	
	private String description;
	private boolean selected;

	private CommoditySalesQueryState(String description) {
		this(description, false);
	}

	private CommoditySalesQueryState(String description, boolean selected) {
		this.description = description;
		this.selected = selected;
	}

	public String getDescription() {
		return description;
	}

	public boolean isSelected() {
		return selected;
	}
}
