package com.belle.yitiansystem.systemmgmt.model.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 调度任务实体
 * @author zhuangruibo
 * @create 2012-2-6 下午03:22:07 
 * @history
 */

@Entity
@Table(name = "tbl_systemmgt_job")
public class SystemmgtJob implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 调度ID
	 */
	private String id;

	/**
	 * 调度名称
	 */
	private String jobName;

	/**
	 * 调度组
	 */
	private String groupName;

	/**
	 * 调度时间配置
	 */
	private String cronExpression;

	/**
	 * 调度类全路径(包括包路径),且此类要实现org.quartz.Job接口
	 */
	private String jobClass;

	/**
	 * 指定调度运行IP
	 */
	private String runningIp;

	/**
	 * 状态。RUNNING:运行中,STOP:停止,WAITTING:等待
	 */
	private String status;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 创建人
	 */
	private String createBy;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 更新人
	 */
	private String updateBy;

	/**
	 * 更新时间
	 */
	private String updateTime;

	/**
	 * 是否启用
	 */
	private String enabledFlag;
	
	/**
	 * 最后执行时间
	 */
	private String lastRuntime;
	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "job_name", length = 200)
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Column(name = "group_name", length = 200)
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "cron_expression", length = 200)
	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	@Column(name = "job_class", length = 400)
	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	@Column(name = "running_ip", length = 16)
	public String getRunningIp() {
		return runningIp;
	}

	public void setRunningIp(String runningIp) {
		this.runningIp = runningIp;
	}

	@Column(name = "status", length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "create_by", length = 32)
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "create_time", length = 20)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_by", length = 32)
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Column(name = "update_time", length = 20)
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "enabled_flag", length = 1)
	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	@Column(name = "last_runtime", length = 30)
	public String getLastRuntime() {
		return lastRuntime;
	}

	public void setLastRuntime(String lastRuntime) {
		this.lastRuntime = lastRuntime;
	}
}
