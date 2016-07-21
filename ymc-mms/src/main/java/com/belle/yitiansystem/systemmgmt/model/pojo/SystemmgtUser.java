package com.belle.yitiansystem.systemmgmt.model.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validation.constraints.NotEmpty;

import com.belle.infrastructure.util.DateUtil;

/**
 * TblSystemmgtUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_systemmgt_user")
public class SystemmgtUser implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	@NotEmpty(message = "validator.systemuser.username.notempty")
	private String username;
	private String sex;
	@NotEmpty(message = "validator.systemuser.loginName.notempty")
	private String loginName;
	private String loginPassword;
	private String mobilePhone;
	private String telPhone;
	private String email;
	private String qqNum;
	private String msnNum;
	private String organizName;
	private String organizNo;
	private String state;
	private String category;
	private String no;
	private String level; // level 为0时 为超级管理员

	private String loginIp; // 登录IP地址
	private Set<AuthorityRole> authorityRoles = new HashSet<AuthorityRole>(0);
	private Set<SystemmgtUserGroup> systemmgtUserGroups = new HashSet<SystemmgtUserGroup>(
			0);

	private String supplierCode;// 供应商编码
	private String isSupplier;
	// 仓库编码
	private String warehouseCode;
	// 权限小组
	private UserPermissionGroup permissionGroup;
	// 账户密码更新时间
	private Date pwdUpdateTime = new Date();
	// 礼品卡权限
	private String giftCardPermission = "0";

	private boolean lockFlag;
	 
	// Constructors
 
	public boolean isLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(boolean lockFlag) {
		this.lockFlag = lockFlag;
	}

	@Column(name = "supplier_code", length = 32)
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	/** default constructor */
	public SystemmgtUser() {
	}

	/** full constructor */
	public SystemmgtUser(String username, String sex, String loginName,
			String loginPassword, String mobilePhone, String telPhone,
			String email, String qqNum, String msnNum, String state,
			String category, String no, Set<AuthorityRole> authorityRoles,
			Set<SystemmgtUserGroup> systemmgtUserGroups, String supplierCode) {
		this.username = username;
		this.sex = sex;
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.mobilePhone = mobilePhone;
		this.telPhone = telPhone;
		this.email = email;
		this.qqNum = qqNum;
		this.msnNum = msnNum;
		this.state = state;
		this.category = category;
		this.no = no;
		this.authorityRoles = authorityRoles;
		this.systemmgtUserGroups = systemmgtUserGroups;
		this.supplierCode = supplierCode;
	}

	// 账号剩余的有效天数
	@Transient
	public int getActiveRemainDay() {
		// return 0;
		int num = 90 - DateUtil.diffDate(new Date(), pwdUpdateTime);
		// num = -6;
		if (num < 0) {
			return 0;
		}
		return num;
	}

	// Property accessors
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

	@Column(name = "username", length = 40)
	public String getUsername() {
		return this.username;
	}

	@Column(name = "pwd_update_time")
	public Date getPwdUpdateTime() {
		return pwdUpdateTime;
	}

	public void setPwdUpdateTime(Date pwdUpdateTime) {
		this.pwdUpdateTime = pwdUpdateTime;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "sex", length = 1)
	public String getSex() {
		return this.sex;
	}

	@Column(name = "gift_card_permission", length = 5)
	public String getGiftCardPermission() {
		return giftCardPermission;
	}

	public void setGiftCardPermission(String giftCardPermission) {
		this.giftCardPermission = giftCardPermission;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "login_name", length = 50)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "login_password", length = 50)
	public String getLoginPassword() {
		return this.loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	@Column(name = "mobile_phone", length = 15)
	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "tel_phone", length = 25)
	public String getTelPhone() {
		return this.telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "qq_num", length = 50)
	public String getQqNum() {
		return this.qqNum;
	}

	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}

	@Column(name = "msn_num", length = 50)
	public String getMsnNum() {
		return this.msnNum;
	}

	public void setMsnNum(String msnNum) {
		this.msnNum = msnNum;
	}

	@Column(name = "organiz_name")
	public String getOrganizName() {
		return organizName;
	}

	public void setOrganizName(String organizName) {
		this.organizName = organizName;
	}

	@Column(name = "organiz_no")
	public String getOrganizNo() {
		return organizNo;
	}

	public void setOrganizNo(String organizNo) {
		this.organizNo = organizNo;
	}

	@Column(name = "state", length = 1)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "category", length = 1)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "no", length = 40)
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name = "level", length = 1)
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Transient
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Transient
	public String getIsSupplier() {
		return isSupplier;
	}

	public void setIsSupplier(String isSupplier) {
		this.isSupplier = isSupplier;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_systemmgt_user_role", joinColumns = { @JoinColumn(name = "uid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
	public Set<AuthorityRole> getAuthorityRoles() {
		return authorityRoles;
	}

	public void setAuthorityRoles(Set<AuthorityRole> authorityRoles) {
		this.authorityRoles = authorityRoles;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "systemmgtUser")
	public Set<SystemmgtUserGroup> getSystemmgtUserGroups() {
		return systemmgtUserGroups;
	}

	public void setSystemmgtUserGroups(
			Set<SystemmgtUserGroup> systemmgtUserGroups) {
		this.systemmgtUserGroups = systemmgtUserGroups;
	}

	@Column(name = "warehouse_code")
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_group_id")
	public UserPermissionGroup getPermissionGroup() {
		return permissionGroup;
	}

	public void setPermissionGroup(UserPermissionGroup permissionGroup) {
		this.permissionGroup = permissionGroup;
	}

}