package com.yougou.kaidian.user.model.pojo;

/**
 * 联系人实体类
 * @author wang.m
 */
public class SupplierLinkMan{

	private String id;
	private String supplierId;//供应商ID
	private Integer type;//类型
	private String contact;//内容
	private String telePhone;//电话
	private String mobilePhone;//手机
	private String email;//邮箱
	private String fax;//传真
	private String address;//详细地址

	
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelePhone() {
		return telePhone;
	}

	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public SupplierLinkMan() {
	}

	public SupplierLinkMan(String id, Integer type, String contact,
			String telePhone, String mobilePhone, String email, String fax,
			String address) {
		super();
		this.id = id;
		this.type = type;
		this.contact = contact;
		this.telePhone = telePhone;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.fax = fax;
		this.address = address;
	}


}
