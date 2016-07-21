package com.yougou.kaidian.order.model.pojo;

import java.util.Date;

public class ReturnAndRejectionDetailBean {

	//商品编码
	private String commodityNo;
	//商品名称
	private String commodityName;
	//货品编码
	private String productNo;
	//颜色
	private String specName;
	//商品尺寸
	private String commoditySize;
	//缩略图
	private String prodUrl;
	
	private String picUrl;
	//类型
	private String type;
	//质检时间
	private Date qaDate;
	//原因
	private String reason;
	//状态
	private String status;
	//结果
	private String result;
	//描述
	private String description;
	
	public String getCommodityNo() {
		return commodityNo;
	}
	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	public String getCommoditySize() {
		return commoditySize;
	}
	public void setCommoditySize(String commoditySize) {
		this.commoditySize = commoditySize;
	}
	public String getProdUrl() {
		return prodUrl;
	}
	public void setProdUrl(String prodUrl) {
		this.prodUrl = prodUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getQaDate() {
		return qaDate;
	}
	public void setQaDate(Date qaDate) {
		this.qaDate = qaDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
}
