package com.yougou.api.model.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author 杨梦清
 * 
 */
@Entity
@Table(name = "tbl_merchant_api_abnormal_alarm")
public class ApiAbnormalAlarm implements Serializable {

	private static final long serialVersionUID = 8263560661177608039L;
	private String id;
	private String alarmCauserClass;//异常报警引发者类
	private String alarmCauserCode;//异常报警引发者代码
	private Long alarmCauserWeight;// 事件权重(0=无,1=邮件,2=短信,3=邮件&短信)
	private String alarmReceiverEmail;// 报警接收人邮件(注:多接收人以逗号劈开)
	private String alarmReceiverPhone;// 报警接收人手机(注:多接收人以逗号劈开)
	private Integer ignoreNumbers;// 忽略次数
	private Long cycleTimeline;// 周期时间
	private String description;

	public ApiAbnormalAlarm() {
	}
	
	public ApiAbnormalAlarm(String id) {
		this.id = id;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@GeneratedValue(generator = "generator")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "alarm_causer_class", nullable = false, length = 256)
	public String getAlarmCauserClass() {
		return alarmCauserClass;
	}

	public void setAlarmCauserClass(String alarmCauserClass) {
		this.alarmCauserClass = alarmCauserClass;
	}

	@Column(name = "alarm_causer_code", length = 32)
	public String getAlarmCauserCode() {
		return alarmCauserCode;
	}

	public void setAlarmCauserCode(String alarmCauserCode) {
		this.alarmCauserCode = alarmCauserCode;
	}

	@Column(name = "alarm_causer_weight")
	public Long getAlarmCauserWeight() {
		return alarmCauserWeight;
	}

	public void setAlarmCauserWeight(Long alarmCauserWeight) {
		this.alarmCauserWeight = alarmCauserWeight;
	}

	@Column(name = "alarm_receiver_email", length = 1024)
	public String getAlarmReceiverEmail() {
		return alarmReceiverEmail;
	}

	public void setAlarmReceiverEmail(String alarmReceiverEmail) {
		this.alarmReceiverEmail = alarmReceiverEmail;
	}

	@Column(name = "alarm_receiver_phone", length = 1024)
	public String getAlarmReceiverPhone() {
		return alarmReceiverPhone;
	}

	public void setAlarmReceiverPhone(String alarmReceiverPhone) {
		this.alarmReceiverPhone = alarmReceiverPhone;
	}

	@Column(name = "ignore_numbers")
	public Integer getIgnoreNumbers() {
		return ignoreNumbers;
	}

	public void setIgnoreNumbers(Integer ignoreNumbers) {
		this.ignoreNumbers = ignoreNumbers;
	}

	@Column(name = "cycle_timeline")
	public Long getCycleTimeline() {
		return cycleTimeline;
	}

	public void setCycleTimeline(Long cycleTimeline) {
		this.cycleTimeline = cycleTimeline;
	}
	
	@Column(name = "description", length = 256)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
