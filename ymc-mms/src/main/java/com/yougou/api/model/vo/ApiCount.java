package com.yougou.api.model.vo;

public class ApiCount  {
	
	/**
	 * api名字
	 */
	private String apiName;
	
	/**
	 * 调用次数
	 */
	private Long callCount;
	
	
	/**
	 * 失败次数
	 */
	private Long failCount;

	/**
	 * 调用时间
	 */
	private Long exTime;

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}



	public Long getCallCount() {
		return callCount;
	}

	public void setCallCount(Long callCount) {
		this.callCount = callCount;
	}

	public Long getFailCount() {
		return failCount;
	}

	public void setFailCount(Long failCount) {
		this.failCount = failCount;
	}

	public Long getExTime() {
		return exTime;
	}

	public void setExTime(Long exTime) {
		this.exTime = exTime;
	}

	@Override
	public String toString() {
		return "ApiCount [apiName=" + apiName + ", callCount=" + callCount + ", failCount=" + failCount + ", exTime="
				+ exTime + "]";
	}
	
}
