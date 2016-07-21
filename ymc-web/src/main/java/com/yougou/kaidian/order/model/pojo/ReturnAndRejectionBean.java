package com.yougou.kaidian.order.model.pojo;

import java.util.List;

public class ReturnAndRejectionBean {

	//序号
	private int index;
	//id
	private String id;
	//申请单号
	private String applyNo;
	//订单号
	private String orderSubNo;
	//快递公司ID
	private String expressId;
	//快递公司名称
	private String expressName;
	//快递费用
	private double expressCharges;
	//快递编号
	private String expressCode;
	//商品质检信息
	private List<ReturnAndRejectionDetailBean> returnAndRejectionDetailList;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getOrderSubNo() {
		return orderSubNo;
	}
	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}
	public String getExpressId() {
		return expressId;
	}
	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public double getExpressCharges() {
		return expressCharges;
	}
	public void setExpressCharges(double expressCharges) {
		this.expressCharges = expressCharges;
	}
	public String getExpressCode() {
		return expressCode;
	}
	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}
	public List<ReturnAndRejectionDetailBean> getReturnAndRejectionDetailList() {
		return returnAndRejectionDetailList;
	}
	public void setReturnAndRejectionDetailList(
			List<ReturnAndRejectionDetailBean> returnAndRejectionDetailList) {
		this.returnAndRejectionDetailList = returnAndRejectionDetailList;
	}
	
}
