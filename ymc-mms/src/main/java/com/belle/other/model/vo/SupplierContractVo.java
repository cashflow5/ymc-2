package com.belle.other.model.vo;

import java.io.Serializable;
import java.util.Date;

public class SupplierContractVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer contractState;
	private String contractNo;
	private String supplierCode;
	private String supplier;
	private String supplierType;
	private Integer clearingForm;
	private Date effectiveDate;
	private Date failureDate;
	private String updateTime;
	private String updateUser;
	private String id;
	/**
	 * 附件名称
	 */
	private String attachment;

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Integer getContractState() {
		return contractState;
	}

	public void setContractState(Integer contractState) {
		this.contractState = contractState;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	public Integer getClearingForm() {
		return clearingForm;
	}

	public void setClearingForm(Integer clearingForm) {
		this.clearingForm = clearingForm;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getFailureDate() {
		return failureDate;
	}

	public void setFailureDate(Date failureDate) {
		this.failureDate = failureDate;
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

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAttachment() {
		return attachment;
	}

}
