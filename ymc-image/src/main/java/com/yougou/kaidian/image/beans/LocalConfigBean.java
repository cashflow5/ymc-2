package com.yougou.kaidian.image.beans;

/**
 * @author li.m1
 * 从applicationContext.xml读取邮件列表，方便发版调整
 *
 */

public class LocalConfigBean {

	private String[] receiveMailAddress;//切图失败邮件提醒地址

	public String[] getReceiveMailAddress() {
		return receiveMailAddress;
	}

	public void setReceiveMailAddress(String[] receiveMailAddress) {
		this.receiveMailAddress = receiveMailAddress;
	}
}
