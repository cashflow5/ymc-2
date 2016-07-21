package com.yougou.api.model.vo;

public class AppKeyCount  {
	
	/**
	 * appKey持有者
	 */
	private String appkey;
	
	/**
	 * appKey持有者名称
	 */
	private String appkeyUser;
	
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


	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
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
		return "AppKeyCount [appkey=" + appkey + ",appkeyUser=" + appkeyUser + ", callCount=" + callCount + ", failCount=" + failCount + ", exTime="
				+ exTime + "]";
	}

	public String getAppkeyUser() {
		return appkeyUser;
	}

	public void setAppkeyUser(String appkeyUser) {
		this.appkeyUser = appkeyUser;
	}
}
