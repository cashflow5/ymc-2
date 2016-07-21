package com.yougou.kaidian.user.model.pojo;

/**
 * 用户信息实体�?
 * @author wang.m
 * @Date 2012-03-12
 *
 */
public class MerchantUsers {
    private String id;
	
	private String merchantCode;//商家编号
	
	private String userName;//商家正是姓名
	
	private String loginName;//商家登录名称
	
	private String password;//密码
	
	private String mobileCode;//手机号码

	private String createTime;//时间

	private Integer status;//状态  默认1 可用  0 表示不可用
	
	private String remark;//备注

	private Integer isAdministrator;//是否为管理员 0 不是  1 是
	
	private Integer deleteFlag;//删除标志 0已删除  1未删除
	
	private Integer isYougouAdmin;//是否优购管理员 0 不是  1 是
	
	private String merchantName;//商家编号
	
	private String brands;//商家编号
	
	//1低  2中  3高
	private String strength;	//密码强度
	
	public Integer getIsAdministrator() {
		return isAdministrator;
	}

	public void setIsAdministrator(Integer isAdministrator) {
		this.isAdministrator = isAdministrator;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getIsYougouAdmin() {
		return isYougouAdmin;
	}

	public void setIsYougouAdmin(Integer isYougouAdmin) {
		this.isYougouAdmin = isYougouAdmin;
	}
	
	
	
	public String getBrands() {
		return brands;
	}

	public void setBrands(String brands) {
		this.brands = brands;
	}

	public MerchantUsers(String id, String merchantCode, String userName,
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

	public MerchantUsers() {
		super();
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

}
