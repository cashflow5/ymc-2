package com.belle.yitiansystem.systemmgmt.model.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 系统用户操作日志
 * @author zhubin
 * date：2012-2-17 下午1:56:47
 */
@Entity
@Table(name="tbl_systemmgt_user_operate_log")
public class SystemmgtUserOperateLog {

	
	private String id;
	
	private String operateAccount;
	
	private String operateName;
	
	private String operateIp;
	
	private Date operateDate;
	
	private String operateRemark;
	
	private String userId;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "operate_account", length = 32)
	public String getOperateAccount() {
		return operateAccount;
	}

	public void setOperateAccount(String operateAccount) {
		this.operateAccount = operateAccount;
	}
	
	@Column(name = "operate_name", length = 50)
	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	
	@Column(name = "user_id", length = 32)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "operate_ip", length = 50)
	public String getOperateIp() {
		return operateIp;
	}

	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "operate_date")
	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	
	@Column(name = "operate_remark", length = 200)
	public String getOperateRemark() {
		return operateRemark;
	}

	public void setOperateRemark(String operateRemark) {
		this.operateRemark = operateRemark;
	}
	
	
	
	
	
}
