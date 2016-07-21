
package com.belle.yitiansystem.merchant.model.entity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.yougou.merchant.api.supplier.vo.MerchantOperationLog;
import com.yougou.merchant.api.supplier.vo.SupplierContract;

public class SupplierVo {
	// 合同列表
	private List<SupplierContract> supplierContractList;

	// 操作日志
	private List<MerchantOperationLog> logList;
    private String id;

    private String supplierCode;

    private String supplier;

    private String englishName;

    private String simpleName;

    public List<SupplierContract> getSupplierContractList() {
		return supplierContractList;
	}

	public void setSupplierContractList(List<SupplierContract> supplierContractList) {
		this.supplierContractList = supplierContractList;
	}

	public List<MerchantOperationLog> getLogList() {
		return logList;
	}

	public void setLogList(List<MerchantOperationLog> logList) {
		this.logList = logList;
	}

	private String contact;

    private String telePhone;

    private String email;

    private String fax;

    private Long updateTimestamp;

    private String address;

    private String url;

    private String remark;

    private Integer isValid;

    private String supplierType;

    private String bank;

    private String subBank;

    private String dutyCode;

    private String creator;

    private String account;

    private String payType;

    private Integer conTime;

    private String loginAccount;

    private String loginPassword;

    private Integer isConfig;

    private Date updateDate;

    private String updateUser;

    private Double taxRate;

    private Double couponsAllocationProportion;

    private String inventoryCode;

    private String businessLicense;

    private String businessLocal;

    private String businessVilidity;

    private String tallageNo;

    private String institutional;

    private String taxpayer;

    private String bankLocal;

    private Integer isInputYougouWarehouse;

    private String setOfBooksCode;

    private String setOfBooksName;

    private String balanceTraderCode;

    private String balanceTraderName;

    private String posSourceNo;

    private Integer deleteFlag;

    private Integer shipmentType;

    private String tradeCurrency;

    private Integer isUseYougouWms;

    private Short isHongkong;

    private Integer isInvoice;

    private Integer isAddValueInvoice;

    private Integer supplierTypeCode;

    private Integer isDirect;

    private String invoiceName;

    private String invoiceAddress;

    private String invoicePhone;

    private String updateUsername;

    private String creatorname;

    private String factoryNo;

    private Integer taxplayerType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName == null ? null : englishName.trim();
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName == null ? null : simpleName.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone == null ? null : telePhone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public Long getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Long updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType == null ? null : supplierType.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getSubBank() {
        return subBank;
    }

    public void setSubBank(String subBank) {
        this.subBank = subBank == null ? null : subBank.trim();
    }

    public String getDutyCode() {
        return dutyCode;
    }

    public void setDutyCode(String dutyCode) {
        this.dutyCode = dutyCode == null ? null : dutyCode.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public Integer getConTime() {
        return conTime;
    }

    public void setConTime(Integer conTime) {
        this.conTime = conTime;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount == null ? null : loginAccount.trim();
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }

    public Integer getIsConfig() {
        return isConfig;
    }

    public void setIsConfig(Integer isConfig) {
        this.isConfig = isConfig;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getCouponsAllocationProportion() {
        return couponsAllocationProportion;
    }

    public void setCouponsAllocationProportion(Double couponsAllocationProportion) {
        this.couponsAllocationProportion = couponsAllocationProportion;
    }

    public String getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode == null ? null : inventoryCode.trim();
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense == null ? null : businessLicense.trim();
    }

    public String getBusinessLocal() {
        return businessLocal;
    }

    public void setBusinessLocal(String businessLocal) {
        this.businessLocal = businessLocal == null ? null : businessLocal.trim();
    }

    public String getBusinessVilidity() {
        return businessVilidity;
    }

    public void setBusinessVilidity(String businessVilidity) {
        this.businessVilidity = businessVilidity == null ? null : businessVilidity.trim();
    }

    public String getTallageNo() {
        return tallageNo;
    }

    public void setTallageNo(String tallageNo) {
        this.tallageNo = tallageNo == null ? null : tallageNo.trim();
    }

    public String getInstitutional() {
        return institutional;
    }

    public void setInstitutional(String institutional) {
        this.institutional = institutional == null ? null : institutional.trim();
    }

    public String getTaxpayer() {
        return taxpayer;
    }

    public void setTaxpayer(String taxpayer) {
        this.taxpayer = taxpayer == null ? null : taxpayer.trim();
    }

    public String getBankLocal() {
        return bankLocal;
    }

    public void setBankLocal(String bankLocal) {
        this.bankLocal = bankLocal == null ? null : bankLocal.trim();
    }

    public Integer getIsInputYougouWarehouse() {
        return isInputYougouWarehouse;
    }

    public void setIsInputYougouWarehouse(Integer isInputYougouWarehouse) {
        this.isInputYougouWarehouse = isInputYougouWarehouse;
    }

    public String getSetOfBooksCode() {
        return setOfBooksCode;
    }

    public void setSetOfBooksCode(String setOfBooksCode) {
        this.setOfBooksCode = setOfBooksCode == null ? null : setOfBooksCode.trim();
    }

    public String getSetOfBooksName() {
        return setOfBooksName;
    }

    public void setSetOfBooksName(String setOfBooksName) {
        this.setOfBooksName = setOfBooksName == null ? null : setOfBooksName.trim();
    }

    public String getBalanceTraderCode() {
        return balanceTraderCode;
    }

    public void setBalanceTraderCode(String balanceTraderCode) {
        this.balanceTraderCode = balanceTraderCode == null ? null : balanceTraderCode.trim();
    }

    public String getBalanceTraderName() {
        return balanceTraderName;
    }

    public void setBalanceTraderName(String balanceTraderName) {
        this.balanceTraderName = balanceTraderName == null ? null : balanceTraderName.trim();
    }

    public String getPosSourceNo() {
        return posSourceNo;
    }

    public void setPosSourceNo(String posSourceNo) {
        this.posSourceNo = posSourceNo == null ? null : posSourceNo.trim();
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(Integer shipmentType) {
        this.shipmentType = shipmentType;
    }

    public String getTradeCurrency() {
        return tradeCurrency;
    }

    public void setTradeCurrency(String tradeCurrency) {
        this.tradeCurrency = tradeCurrency == null ? null : tradeCurrency.trim();
    }

    public Integer getIsUseYougouWms() {
        return isUseYougouWms;
    }

    public void setIsUseYougouWms(Integer isUseYougouWms) {
        this.isUseYougouWms = isUseYougouWms;
    }

    public Short getIsHongkong() {
        return isHongkong;
    }

    public void setIsHongkong(Short isHongkong) {
        this.isHongkong = isHongkong;
    }

    public Integer getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Integer isInvoice) {
        this.isInvoice = isInvoice;
    }

    public Integer getIsAddValueInvoice() {
        return isAddValueInvoice;
    }

    public void setIsAddValueInvoice(Integer isAddValueInvoice) {
        this.isAddValueInvoice = isAddValueInvoice;
    }

    public Integer getSupplierTypeCode() {
        return supplierTypeCode;
    }

    public void setSupplierTypeCode(Integer supplierTypeCode) {
        this.supplierTypeCode = supplierTypeCode;
    }

    public Integer getIsDirect() {
        return isDirect;
    }

    public void setIsDirect(Integer isDirect) {
        this.isDirect = isDirect;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName == null ? null : invoiceName.trim();
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress == null ? null : invoiceAddress.trim();
    }

    public String getInvoicePhone() {
        return invoicePhone;
    }

    public void setInvoicePhone(String invoicePhone) {
        this.invoicePhone = invoicePhone == null ? null : invoicePhone.trim();
    }

    public String getUpdateUsername() {
        return updateUsername;
    }

    public void setUpdateUsername(String updateUsername) {
        this.updateUsername = updateUsername == null ? null : updateUsername.trim();
    }

    public String getCreatorname() {
        return creatorname;
    }

    public void setCreatorname(String creatorname) {
        this.creatorname = creatorname == null ? null : creatorname.trim();
    }

    public String getFactoryNo() {
        return factoryNo;
    }

    public void setFactoryNo(String factoryNo) {
        this.factoryNo = factoryNo == null ? null : factoryNo.trim();
    }

    public Integer getTaxplayerType() {
        return taxplayerType;
    }

    public void setTaxplayerType(Integer taxplayerType) {
        this.taxplayerType = taxplayerType;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}