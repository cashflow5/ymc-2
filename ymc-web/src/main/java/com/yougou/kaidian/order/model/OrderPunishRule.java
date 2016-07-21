package com.yougou.kaidian.order.model;

import java.io.Serializable;

public class OrderPunishRule implements Serializable {

	private static final long serialVersionUID = -3976456789821136900L;

	/** 商家编码 */
	private String merchantCode;
	
	/** 缺货处罚类型 */
	private String stockPunishType;
	
	/** 处罚比率 */
	private Double stockPunishRate;
	
	/** 处罚金额 */
	private Double stockPunishMoney;
	
	/** 超时时间 */
	private Integer shipmentHour;

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getStockPunishType() {
		return stockPunishType;
	}

	public void setStockPunishType(String stockPunishType) {
		this.stockPunishType = stockPunishType;
	}

	public Double getStockPunishRate() {
		return stockPunishRate;
	}

	public void setStockPunishRate(Double stockPunishRate) {
		this.stockPunishRate = stockPunishRate;
	}

	public Double getStockPunishMoney() {
		return stockPunishMoney;
	}

	public void setStockPunishMoney(Double stockPunishMoney) {
		this.stockPunishMoney = stockPunishMoney;
	}

	public Integer getShipmentHour() {
		return shipmentHour;
	}

	public void setShipmentHour(Integer shipmentHour) {
		this.shipmentHour = shipmentHour;
	}


	
	

}