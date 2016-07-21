package com.yougou.api.model.vo;

import com.google.gson.annotations.SerializedName;


public class ApiAnalyzeDetailVo implements java.io.Serializable {

	private static final long serialVersionUID = -235599478178740669L;

	//OperationParameters operationParameters;
	
	String api;
	
	String appKey;
	
	int total;
	
	int totalExTime;
	
	int faleTotal;



	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalExTime() {
		return totalExTime;
	}

	public void setTotalExTime(int totalExTime) {
		this.totalExTime = totalExTime;
	}

	public int getFaleTotal() {
		return faleTotal;
	}

	public void setFaleTotal(int faleTotal) {
		this.faleTotal = faleTotal;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	@Override
	public String toString() {
		return "ApiAnalyzeDetailVo [api=" + api + ", appKey=" + appKey + ", total=" + total + ", totalExTime="
				+ totalExTime + ", faleTotal=" + faleTotal + "]";
	}

	

	
	
}
