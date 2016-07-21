package com.yougou.api.model.vo;

import com.google.gson.annotations.SerializedName;

/**
 * appkey信息
 * @author he.wc
 *
 */
public class AppKeyMetadata  {
	
	/**
	 * appKey
	 */
	@SerializedName("key")
	private String appKey;
	@SerializedName("value")
	private String appKeyUser;
	
	

	public AppKeyMetadata(String appKey, String appKeyUser) {
		this.appKey = appKey;
		this.appKeyUser = appKeyUser;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppKeyUser() {
		return appKeyUser;
	}

	public void setAppKeyUser(String appKeyUser) {
		this.appKeyUser = appKeyUser;
	}

	@Override
	public String toString() {
		return "AppKeyMetadata [appKey=" + appKey + ", appKeyUser=" + appKeyUser + "]";
	}
	
	
}
