package com.yougou.kaidian.user.model.pojo;  

import java.io.Serializable;

public class ApiKey implements Serializable {
	/** 
	 * serialVersionUID:序列化 
	 * @since JDK 1.6 
	 */ 
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String appKey;
	private String appSecret;
	/**
	 * '状态（0：关闭，1：开启）'
	 */
	private int status;
	private String updateUser;
	private String updateTime;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	/**
	 * '状态（0：关闭，1：开启）'
	 */
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
