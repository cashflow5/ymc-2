package com.yougou.kaidian.user.model.pojo;

import java.util.Date;

public class ApiLicense implements java.io.Serializable {
	/** 
	 * @since JDK 1.6 
	 */ 
	private static final long serialVersionUID = 7376503162103492861L;
	/** 
	 * @since JDK 1.6 
	 */ 
	private String id;
	private String keyId;
	private String licensor;
	private Date licensed;
	private String apiId;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public String getLicensor() {
		return licensor;
	}
	public void setLicensor(String licensor) {
		this.licensor = licensor;
	}
	public Date getLicensed() {
		return licensed;
	}
	public void setLicensed(Date licensed) {
		this.licensed = licensed;
	}
	public String getApiId() {
		return apiId;
	}
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	
}
