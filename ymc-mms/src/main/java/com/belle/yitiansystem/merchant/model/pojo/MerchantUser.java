package com.belle.yitiansystem.merchant.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 招商系统 商家用户信息表
 * @author wang.m
 * @Date 2012-03-17
 *
 */
@Entity
@Table(name = "tbl_merchant_user")
public class MerchantUser {
	private String id;
	
	private String merchantCode;//商家编号
	
	private String userName;//商家正是姓名
	
	private String loginName;//商家登录名称
	
	private String password;//密码
	
	private String mobileCode;//手机号码
	
	private String creater;//创建人

	private String createTime;//创建时间

	private Integer status;//状态  默认1 启用  0 表示锁定
	
	private String remark;//备注

	private Integer isAdministrator;//是否为管理员 0 不是  1 是
	
	private Integer deleteFlag;//删除标志 0表示已删除  1表示未删除
	
	private Integer isYougouAdmin;//是否优购管理员 0 不是  1 是
	
	private String email;//邮箱
	
	private Integer emailstatus;//邮箱是否激活 0未激活 1激活
	
	public MerchantUser(String id, String merchantCode, String userName,
			String loginName, String password, String mobileCode,
			String createTime, Integer status, String remark) {
		super();
		this.id = id;
		this.merchantCode = merchantCode;
		this.userName = userName;
		this.loginName = loginName;
		this.password = password;
		this.mobileCode = mobileCode;
		this.createTime = createTime;
		this.status = status;
		this.remark = remark;
	}

	public MerchantUser() {
		super();
	}
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
	@Column(name = "merchant_code", length = 20)
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	@Column(name = "is_administrator", length = 11)
	public Integer getIsAdministrator() {
		return isAdministrator;
	}

	public void setIsAdministrator(Integer isAdministrator) {
		this.isAdministrator = isAdministrator;
	}

	@Column(name = "user_name", length = 20)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "login_name", length = 20)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	@Column(name = "password", length = 20)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name = "mobile_code", length = 20)
	public String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}
	
	@Column(name = "creater", length = 50)
	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Column(name = "create_time", length = 20)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Column(name = "status", length = 20)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "remark", length = 20)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "delete_flag", length = 11)
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	@Column(name = "is_yougou_admin", length = 1)
	public Integer getIsYougouAdmin() {
		return isYougouAdmin;
	}

	public void setIsYougouAdmin(Integer isYougouAdmin) {
		this.isYougouAdmin = isYougouAdmin;
	}
	@Column(name = "email", length = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "emailstatus", length = 1)
	public Integer getEmailstatus() {
		return emailstatus;
	}

	public void setEmailstatus(Integer emailstatus) {
		this.emailstatus = emailstatus;
	}
	
}
