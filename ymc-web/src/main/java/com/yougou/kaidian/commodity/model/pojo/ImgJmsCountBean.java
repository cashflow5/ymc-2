package com.yougou.kaidian.commodity.model.pojo;

public class ImgJmsCountBean {

	private String merchantCode;
	private String merchantName;
	private String picType;
	private int status1Count;
	private int status0Count;
	private int count;
	
	public String getPicType() {
		return picType;
	}
	public void setPicType(String picType) {
		this.picType = picType;
	}
	public int getStatus1Count() {
		return status1Count;
	}
	public void setStatus1Count(int status1Count) {
		this.status1Count = status1Count;
	}
	public int getStatus0Count() {
		return status0Count;
	}
	public void setStatus0Count(int status0Count) {
		this.status0Count = status0Count;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
}
