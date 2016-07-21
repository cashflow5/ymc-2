package com.yougou.api.model.vo;

import com.google.gson.annotations.SerializedName;

/**
 * api信息
 * @author he.wc
 *
 */
public class ApiMetadata  {
	
	@SerializedName("key")
	private String apiId;
	@SerializedName("value")
	private String apiName;
	
	public ApiMetadata(String apiId, String apiName) {
		super();
		this.apiId = apiId;
		this.apiName = apiName;
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	@Override
	public String toString() {
		return "ApiMetadata [apiId=" + apiId + ", apiName=" + apiName + "]";
	}
	
	

	
}
