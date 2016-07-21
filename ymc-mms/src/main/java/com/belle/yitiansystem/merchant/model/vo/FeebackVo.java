package com.belle.yitiansystem.merchant.model.vo;

import java.io.Serializable;
import java.util.Date;



/**
 * 意见反馈VO 
 * @author he.wc
 *
 */
public class FeebackVo implements Serializable {

	private static final long serialVersionUID = -5759294251023763109L;

	private String id;
	
	/**
	 * 商家编码
	 */
	private String merchantCode;
	
	/**
	 * 商家名称 
	 */
	private String merchantName;
	
	/**
	 * 一级意见类型 
	 */
	private String firstCate;
	
	/**
	 * 二级意见类型 
	 */
	private String secondCate;
	
	
	private String email;

	private String phone;
	

	private String isReply;
	
	private String isRead;
	
	private Date startCreateTime;
	
	private Date endCreateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getFirstCate() {
		return firstCate;
	}

	public void setFirstCate(String firstCate) {
		this.firstCate = firstCate;
	}

	public String getSecondCate() {
		return secondCate;
	}

	public void setSecondCate(String secondCate) {
		this.secondCate = secondCate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIsReply() {
		return isReply;
	}

	public void setIsReply(String isReply) {
		this.isReply = isReply;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	
}
