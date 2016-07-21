package com.yougou.kaidian.user.model.pojo;  

import java.io.Serializable;

public class ApiKeyMetadata implements Serializable{
	/** 
	 * @since JDK 1.6 
	 */ 
	private static final long serialVersionUID = 3807539753105755070L;
	
	private String id;
	private String keyId;
	private String metadataKey;
	private String metadataVal;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMetadataKey() {
		return metadataKey;
	}
	public void setMetadataKey(String metadataKey) {
		this.metadataKey = metadataKey;
	}
	public String getMetadataVal() {
		return metadataVal;
	}
	public void setMetadataVal(String metadataVal) {
		this.metadataVal = metadataVal;
	}
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
}
