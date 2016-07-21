package com.belle.other.model.vo;

public class ReturnAddrVo {
	private String addr;// 寄回地址
	private String zipCode; // 邮编
	private String receiver;// 收货人
	private String phone;// 电话

	public ReturnAddrVo() {
		super();
	}

	public ReturnAddrVo(String addr, String zipCode, String receiver, String phone) {
		super();
		this.addr = addr;
		this.zipCode = zipCode;
		this.receiver = receiver;
		this.phone = phone;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
