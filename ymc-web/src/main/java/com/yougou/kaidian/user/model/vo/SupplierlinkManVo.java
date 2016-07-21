package com.yougou.kaidian.user.model.vo;


/**
 * 联系人实体类
 * @author wang.m
 */
public class SupplierlinkManVo{

	private String newPhone;//新的手机号码
	
	private String oldPhone;//旧的手机号码
	
	private String supplilyId;//商家id
	
	public String getNewPhone() {
		return newPhone;
	}

	public void setNewPhone(String newPhone) {
		this.newPhone = newPhone;
	}

	public String getOldPhone() {
		return oldPhone;
	}

	public void setOldPhone(String oldPhone) {
		this.oldPhone = oldPhone;
	}

	public String getSupplilyId() {
		return supplilyId;
	}

	public void setSupplilyId(String supplilyId) {
		this.supplilyId = supplilyId;
	}
	
	
}
