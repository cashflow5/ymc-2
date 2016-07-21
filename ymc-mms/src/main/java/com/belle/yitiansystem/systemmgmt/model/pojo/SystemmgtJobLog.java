package com.belle.yitiansystem.systemmgmt.model.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 调度任务日志实体
 * @author zhuangruibo
 * @create 2012-2-6 下午03:22:07 
 * @history
 */

@Entity
@Table(name = "tbl_systemmgt_job_log")
public class SystemmgtJobLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 日志ID
	 */
	private String id;
	
	/**
	 * 调度类全路径(包括包路径),且此类要实现org.quartz.Job接口
	 */
	private String jobClass;

	private String startTime;
	
	private Integer runTime;
	
	private String error;

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
	
	@Column(name = "job_class", length = 100)
	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	@Column(name = "start_time", length = 50)
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(name = "run_time")
	public Integer getRunTime() {
		return runTime;
	}

	public void setRunTime(Integer runTime) {
		this.runTime = runTime;
	}

	@Column(name = "error", length = 30000)
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}


}
