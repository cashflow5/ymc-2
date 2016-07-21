package com.yougou.api.model.vo;

public class AppKeySecretVo  {
	
	private String appKey;
	
	private String secret;
	
	private String metadataVal;
	
	private String metadataKey;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getMetadataVal() {
		return metadataVal;
	}

	public void setMetadataVal(String metadataVal) {
		this.metadataVal = metadataVal;
	}
	
	

	public String getMetadataKey() {
		return metadataKey;
	}

	public void setMetadataKey(String metadataKey) {
		this.metadataKey = metadataKey;
	}

	@Override
	public String toString() {
		return "AppKeySecretVo [appKey=" + appKey + ", secret=" + secret + ", metadataVal=" + metadataVal
				+ ", metadataKey=" + metadataKey + "]";
	}

	
	
	
}
