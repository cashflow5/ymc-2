package com.belle.yitiansystem.merchant.model.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.belle.yitiansystem.merchant.model.entity.ContractAttachment;
import com.belle.yitiansystem.merchant.model.entity.ContractTradeMark;
import com.yougou.merchant.api.supplier.vo.MerchantContractUpdateHistory;

public class SupplierContract implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -7439809857901147164L;

	// 合同下面关联的资质附件
	private List<ContractAttachment> contractAttachmentList;
	// 合同下面的商家授权列表
	private List<ContractTradeMark> contractTradeMarkList;
	// 更新历史记录
	private List<MerchantContractUpdateHistory> contractUpdateHistoryList;
	// 附件列表
	private List<SupplierContractAttachment> attachmentList = new ArrayList<SupplierContractAttachment>();
	// 授权资质列表
	private List<SupplierContractTrademark> trademarkList = new ArrayList<SupplierContractTrademark>();
	// 主键ID
	private String id;

	private String contractId;// 供页面保存id使用
	// 供应商ID
	private String supplierId;
	// 供应商名称
	private String supplier;
	// 供应商编码
	private String supplierCode;
	// 合同编号
	private String contractNo;
	// 清算方式
	private String clearingForm;
	// 合同开始时间
	private String effectiveDate;
	// 合同截止时间
	private String failureDate;
	// 合同创建时间
	private String createTime;
	// 最后更新时间
	private String updateTime;
	// 更新人
	private String updateUser;
	// 申报人
	private String declarant;
	// 货品负责人
	private String yccontact;
	// 合同附件数量
	private int contractAttachmentCount = 0;
	// 资质附件数量
	private int naturalAttachmentCount = 0;
	// 授权附件数量
	private int authorityAttachmentCount = 0;
	// 商标附件数量
	private int trademarkAttachmentCount = 0;
	// 商标剩余有效天数
	private Integer markRemainingDays;
	// 合同剩余有效天数
	private Integer contractRemainingDays;
	// 附件
	private String attachment;
	// 是否需要续签
	private String isNeedRenew;
	// 是否使用ERP
	private String isUseERP;
	// 是否需要保证金
	private String isNeedDeposit;
	// 是否需要平台使用费
	private String isNeedUseFee;
	// 保证金
	private BigDecimal deposit;
	// 平台使用费
	private BigDecimal useFee;
	// 备注
	private String remark;
	// 银行开户名
	private String bankOwner;
	// 银行账号
	private String bankAccount;
	// 银行所在地-省
	private String bankProvince;
	// 银行所在地-市
	private String bankCity;
	// 银行所在地-区
	private String bankArea;
	// 开户支行名称
	private String bankName;
	// 上期合同ID
	private String parentContractId;
	// 合同状态
	private String status;
	// 上期保证金
	private BigDecimal preDeposit;
	// 上期平台使用费
	private BigDecimal preUsefee;
	// 上期保证金是否转入本期
	private String isTransferDeposit;
	// 续签标识
	private String renewFlag;
	// 绑定状态
	private String bindStatus;
	// 创建者
	private String createUser;
	// 状态中文名
	private String statusName;
	// 供应商类型
	private String supplierType;
	//是否新供应商
	private String isOld;
	//保证金状态
	private String depositStatus;
	//平台使用费状态
	private String useFeeStatus;
	//是否新合同
	private String isNewContract;
	
	public List<ContractAttachment> getContractAttachmentList() {
		return contractAttachmentList;
	}

	public void setContractAttachmentList(List<ContractAttachment> contractAttachmentList) {
		this.contractAttachmentList = contractAttachmentList;
	}

	public List<ContractTradeMark> getContractTradeMarkList() {
		return contractTradeMarkList;
	}

	public void setContractTradeMarkList(List<ContractTradeMark> contractTradeMarkList) {
		this.contractTradeMarkList = contractTradeMarkList;
	}

	public List<MerchantContractUpdateHistory> getContractUpdateHistoryList() {
		return contractUpdateHistoryList;
	}

	public void setContractUpdateHistoryList(List<MerchantContractUpdateHistory> contractUpdateHistoryList) {
		this.contractUpdateHistoryList = contractUpdateHistoryList;
	}

	public List<SupplierContractAttachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<SupplierContractAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<SupplierContractTrademark> getTrademarkList() {
		return trademarkList;
	}

	public void setTrademarkList(List<SupplierContractTrademark> trademarkList) {
		this.trademarkList = trademarkList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplier() {
		return supplier;
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

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getClearingForm() {
		return clearingForm;
	}

	public void setClearingForm(String clearingForm) {
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getDeclarant() {
		return declarant;
	}

	public void setDeclarant(String declarant) {
		this.declarant = declarant;
	}

	public String getYccontact() {
		return yccontact;
	}

	public void setYccontact(String yccontact) {
		this.yccontact = yccontact;
	}

	public int getContractAttachmentCount() {
		return contractAttachmentCount;
	}

	public void setContractAttachmentCount(int contractAttachmentCount) {
		this.contractAttachmentCount = contractAttachmentCount;
	}

	public int getNaturalAttachmentCount() {
		return naturalAttachmentCount;
	}

	public void setNaturalAttachmentCount(int naturalAttachmentCount) {
		this.naturalAttachmentCount = naturalAttachmentCount;
	}

	public int getAuthorityAttachmentCount() {
		return authorityAttachmentCount;
	}

	public void setAuthorityAttachmentCount(int authorityAttachmentCount) {
		this.authorityAttachmentCount = authorityAttachmentCount;
	}

	public int getTrademarkAttachmentCount() {
		return trademarkAttachmentCount;
	}

	public void setTrademarkAttachmentCount(int trademarkAttachmentCount) {
		this.trademarkAttachmentCount = trademarkAttachmentCount;
	}

	public Integer getMarkRemainingDays() {
		return markRemainingDays;
	}

	public void setMarkRemainingDays(Integer markRemainingDays) {
		this.markRemainingDays = markRemainingDays;
	}

	public Integer getContractRemainingDays() {
		return contractRemainingDays;
	}

	public void setContractRemainingDays(Integer contractRemainingDays) {
		this.contractRemainingDays = contractRemainingDays;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getIsNeedRenew() {
		return isNeedRenew;
	}

	public void setIsNeedRenew(String isNeedRenew) {
		this.isNeedRenew = isNeedRenew;
	}

	public String getIsUseERP() {
		return isUseERP;
	}

	public void setIsUseERP(String isUseERP) {
		this.isUseERP = isUseERP;
	}

	public String getIsNeedDeposit() {
		return isNeedDeposit;
	}

	public void setIsNeedDeposit(String isNeedDeposit) {
		this.isNeedDeposit = isNeedDeposit;
	}

	public String getIsNeedUseFee() {
		return isNeedUseFee;
	}

	public void setIsNeedUseFee(String isNeedUseFee) {
		this.isNeedUseFee = isNeedUseFee;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public BigDecimal getUseFee() {
		return useFee;
	}

	public void setUseFee(BigDecimal useFee) {
		this.useFee = useFee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBankOwner() {
		return bankOwner;
	}

	public void setBankOwner(String bankOwner) {
		this.bankOwner = bankOwner;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankArea() {
		return bankArea;
	}

	public void setBankArea(String bankArea) {
		this.bankArea = bankArea;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getParentContractId() {
		return parentContractId;
	}

	public void setParentContractId(String parentContractId) {
		this.parentContractId = parentContractId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getPreDeposit() {
		return preDeposit;
	}

	public void setPreDeposit(BigDecimal preDeposit) {
		this.preDeposit = preDeposit;
	}

	public BigDecimal getPreUsefee() {
		return preUsefee;
	}

	public void setPreUsefee(BigDecimal preUsefee) {
		this.preUsefee = preUsefee;
	}

	public String getIsTransferDeposit() {
		return isTransferDeposit;
	}

	public void setIsTransferDeposit(String isTransferDeposit) {
		this.isTransferDeposit = isTransferDeposit;
	}

	public String getRenewFlag() {
		return renewFlag;
	}

	public void setRenewFlag(String renewFlag) {
		this.renewFlag = renewFlag;
	}

	public String getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(String bindStatus) {
		this.bindStatus = bindStatus;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	public String getIsOld() {
		return isOld;
	}

	public void setIsOld(String isOld) {
		this.isOld = isOld;
	}

	public String getDepositStatus() {
		return depositStatus;
	}

	public String getUseFeeStatus() {
		return useFeeStatus;
	}

	public void setDepositStatus(String depositStatus) {
		this.depositStatus = depositStatus;
	}

	public void setUseFeeStatus(String useFeeStatus) {
		this.useFeeStatus = useFeeStatus;
	}

	public String getIsNewContract() {
		return isNewContract;
	}

	public void setIsNewContract(String isNewContract) {
		this.isNewContract = isNewContract;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
