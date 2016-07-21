package com.belle.yitiansystem.systemmgmt.model.vo;

public class SystemmgtJobLogVo {

	private String id;
	private String jobName;
	private String groupName;
	private String jobClass;
	private String status;
	private String startTime;
	private Integer runTime;
	private String error;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Integer getRunTime() {
		return runTime;
	}

	public void setRunTime(Integer runTime) {
		this.runTime = runTime;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}
}
