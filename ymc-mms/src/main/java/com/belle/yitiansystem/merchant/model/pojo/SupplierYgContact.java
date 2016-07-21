package com.belle.yitiansystem.merchant.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-10 下午4:51:17
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@Entity
@Table(name = "tbl_supplier_yg_contact")
public class SupplierYgContact {

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_id", unique = true, nullable = false, length = 32)
	private String userId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "leads")
	private String leads;
	
	@Column(name = "tele_phone")
	private String telePhone;

	@Column(name = "mobile_phone")
	private String mobilePhone;

	@Column(name = "email")
	private String email;

	@Column(name = "remark")
	private String remark;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTelePhone() {
		return telePhone;
	}
	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getLeads() {
		return leads;
	}
	public void setLeads(String leads) {
		this.leads = leads;
	}

}
