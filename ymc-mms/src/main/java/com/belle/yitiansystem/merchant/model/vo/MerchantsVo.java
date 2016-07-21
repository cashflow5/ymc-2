package com.belle.yitiansystem.merchant.model.vo;


/**
 * 供应商查询数据封装vo类
 * @author wang.m
 * @Date 2010-03-06
 *
 */
public class MerchantsVo {

    private String supplier;//商家名称
	private String supplierCode;//供应商编码
	private Integer type;//类型
	private String contact;//姓名
	private Integer isValid ;//状态 1正常  2锁定 -1 关闭
	private String telePhone;//电话
	private String mobilePhone;//手机
	private String email;//邮箱
	private String address;//详细地址
	private String fax;//传真
	private String contractNo;//合同编号
	private Integer clearingForm;//结算方式
	private String effectiveDate;//生效时间
	private String failureDate;//失效时间
	private String updateTime;//更新时间
	private String fileName;//附件名称
	private String brandName;//品牌名称
	private Integer isInputYougouWarehouse;//仓库类型
	private String brandNo;//品牌编码
	private String supplierYgContacts;
	private String orderBy ;//按哪列排序
	private String sequence;//排序的次序
	private String flagForReminds;// 查询用：只查资质提醒的数据用的字段  默认true
	private String isNewContract; //是新合同还是续签合同
	private String isNeedRenew;  //是否需要续签
	private String updateUser; //更新人
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public String getTelePhone() {
		return telePhone;
	}
	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Integer getClearingForm() {
		return clearingForm;
	}
	public void setClearingForm(Integer clearingForm) {
		this.clearingForm = clearingForm;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getFailureDate() {
		return failureDate;
	}
	public void setFailureDate(String failureDate) {
		this.failureDate = failureDate;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public MerchantsVo(String supplier, String supplierCode, Integer type,
			String contact, Integer isValid, String telePhone,
			String mobilePhone, String email, String fax, String contractNo,
			Integer clearingForm, String effectiveDate, String failureDate,
			String updateTime) {
		super();
		this.supplier = supplier;
		this.supplierCode = supplierCode;
		this.type = type;
		this.contact = contact;
		this.isValid = isValid;
		this.telePhone = telePhone;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.fax = fax;
		this.contractNo = contractNo;
		this.clearingForm = clearingForm;
		this.effectiveDate = effectiveDate;
		this.failureDate = failureDate;
		this.updateTime = updateTime;
	}
	public MerchantsVo() {
		super();
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Integer getIsInputYougouWarehouse() {
		return isInputYougouWarehouse;
	}
	public void setIsInputYougouWarehouse(Integer isInputYougouWarehouse) {
		this.isInputYougouWarehouse = isInputYougouWarehouse;
	}
	public String getBrandNo() {
		return brandNo;
	}
	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}
	public String getSupplierYgContacts() {
		return supplierYgContacts;
	}
	public void setSupplierYgContacts(String supplierYgContacts) {
		this.supplierYgContacts = supplierYgContacts;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getFlagForReminds() {
		return flagForReminds;
	}
	public void setFlagForReminds(String flagForReminds) {
		this.flagForReminds = flagForReminds;
	}
	public String getIsNewContract() {
		return isNewContract;
	}
	public void setIsNewContract(String isNewContract) {
		this.isNewContract = isNewContract;
	}
	public String getIsNeedRenew() {
		return isNeedRenew;
	}
	public void setIsNeedRenew(String isNeedRenew) {
		this.isNeedRenew = isNeedRenew;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
}
