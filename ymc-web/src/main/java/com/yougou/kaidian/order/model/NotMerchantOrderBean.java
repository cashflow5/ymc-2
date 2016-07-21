package com.yougou.kaidian.order.model;

import java.util.Date;

public class NotMerchantOrderBean {

	private String orderSubID;
	private String orderMainNo;
	private String orderSubNo;
	private String consigneeID;
	private Date modityDate;
	
	public String getOrderSubID() {
		return orderSubID;
	}
	public void setOrderSubID(String orderSubID) {
		this.orderSubID = orderSubID;
	}
	public String getOrderMainNo() {
		return orderMainNo;
	}
	public void setOrderMainNo(String orderMainNo) {
		this.orderMainNo = orderMainNo;
	}
	public String getOrderSubNo() {
		return orderSubNo;
	}
	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}
	public String getConsigneeID() {
		return consigneeID;
	}
	public void setConsigneeID(String consigneeID) {
		this.consigneeID = consigneeID;
	}
	public Date getModityDate() {
		return modityDate;
	}
	public void setModityDate(Date modityDate) {
		this.modityDate = modityDate;
	}
}
