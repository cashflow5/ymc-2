package com.yougou.kaidian.order.model;

import java.util.Date;

public class OrderPunishCommodity {

	private String orderSubId;
	private Date backorderDate;
	private String orderSubNo;
	private String commodityNo;
	private String commodityURL;
	private String prodName;
	private String levelCode;
	private String styleNo;
	private String commoditySupplierCode;
	private String picSmall;
	private int commodityCount;
	
	public String getOrderSubId() {
		return orderSubId;
	}
	public void setOrderSubId(String orderSubId) {
		this.orderSubId = orderSubId;
	}
	public Date getBackorderDate() {
		return backorderDate;
	}
	public void setBackorderDate(Date backorderDate) {
		this.backorderDate = backorderDate;
	}
	public String getOrderSubNo() {
		return orderSubNo;
	}
	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}
	public String getCommodityNo() {
		return commodityNo;
	}
	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	public String getStyleNo() {
		return styleNo;
	}
	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}
	public String getCommoditySupplierCode() {
		return commoditySupplierCode;
	}
	public void setCommoditySupplierCode(String commoditySupplierCode) {
		this.commoditySupplierCode = commoditySupplierCode;
	}
	public String getPicSmall() {
		return picSmall;
	}
	public void setPicSmall(String picSmall) {
		this.picSmall = picSmall;
	}
	public int getCommodityCount() {
		return commodityCount;
	}
	public void setCommodityCount(int commodityCount) {
		this.commodityCount = commodityCount;
	}
	public String getCommodityURL() {
		return commodityURL;
	}
	public void setCommodityURL(String commodityURL) {
		this.commodityURL = commodityURL;
	}
}
