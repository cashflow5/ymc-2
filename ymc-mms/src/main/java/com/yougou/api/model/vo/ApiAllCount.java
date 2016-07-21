package com.yougou.api.model.vo;

/**
 * API 系统总调用情况
 * @author he.wc
 *
 */
public class ApiAllCount  {
	

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
		return "ApiAllCount [callCount=" + callCount + ", failCount=" + failCount + ", exTime=" + exTime + "]";
	}
	
	
	
}
