package com.belle.yitiansystem.merchant.model.entity;

import java.io.Serializable;

/**
 * 简单查询时候用到的vo
 * @author le.sm
 *
 */
public class SimpleSupplierVo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6320794915562854515L;
	private String merchantCode;
	/**
	 * 库存
	 */
	private String inventoryCode;
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getInventoryCode() {
		return inventoryCode;
	}
	public void setInventoryCode(String inventoryCode) {
		this.inventoryCode = inventoryCode;
	}
	
	
 
}
