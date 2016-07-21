package com.yougou.kaidian.order.model;

import java.io.Serializable;
import java.util.Date;

public class OtherOutStore implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 备货单号
	 */
	private String otherOutStoreCode;
	/**
	 * 操作员ID
	 */
	private String operatorId;
	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 审批人
	 */
	private String approver;
	/**
	 * 审批日期
	 */
	private Date approverDate;
	/**
	 * 出库日期
	 */
	private Date outStoreDate;
	/**
	 * 最新修改日期
	 */
	private Date lastModifyDate;
	/**
	 * 出库类型
	 */
	private Integer outStoreType;
	/**
	 * 出库状态
	 */
	private Integer status;
	/**
	 * 分摊状态
	 */
	private Integer apportionStatus;
	/**
	 * 帐套编号
	 */
	private String merchantCode;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 帐套名称
	 */
	private String merchantName;
	/**
	 * 最后修改人
	 */
	private String lastModifyPerson;
	
	/**
	 * 接收时间
	 */
	private Date receiveDate;
	
	/**
	 * 时间戳
	 */
	private Long updateTimestamp;

    /**
     * 物理仓库ID表示出库仓
     */
    private String warehouseId;


    /**
     * 目标仓库ID
     */
    private String intoWarehouseId;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getOtherOutStoreCode() {
		return otherOutStoreCode;
	}


	public void setOtherOutStoreCode(String otherOutStoreCode) {
		this.otherOutStoreCode = otherOutStoreCode;
	}


	public String getOperatorId() {
		return operatorId;
	}


	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}


	public String getCreator() {
		return creator;
	}


	public void setCreator(String creator) {
		this.creator = creator;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getApprover() {
		return approver;
	}


	public void setApprover(String approver) {
		this.approver = approver;
	}


	public Date getApproverDate() {
		return approverDate;
	}


	public void setApproverDate(Date approverDate) {
		this.approverDate = approverDate;
	}


	public Date getOutStoreDate() {
		return outStoreDate;
	}


	public void setOutStoreDate(Date outStoreDate) {
		this.outStoreDate = outStoreDate;
	}


	public Date getLastModifyDate() {
		return lastModifyDate;
	}


	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}


	public Integer getOutStoreType() {
		return outStoreType;
	}


	public void setOutStoreType(Integer outStoreType) {
		this.outStoreType = outStoreType;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Integer getApportionStatus() {
		return apportionStatus;
	}


	public void setApportionStatus(Integer apportionStatus) {
		this.apportionStatus = apportionStatus;
	}


	public String getMerchantCode() {
		return merchantCode;
	}


	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getMerchantName() {
		return merchantName;
	}


	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}


	public String getLastModifyPerson() {
		return lastModifyPerson;
	}


	public void setLastModifyPerson(String lastModifyPerson) {
		this.lastModifyPerson = lastModifyPerson;
	}


	public Date getReceiveDate() {
		return receiveDate;
	}


	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}


	public Long getUpdateTimestamp() {
		return updateTimestamp;
	}


	public void setUpdateTimestamp(Long updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}


	public String getWarehouseId() {
		return warehouseId;
	}


	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}


	public String getIntoWarehouseId() {
		return intoWarehouseId;
	}


	public void setIntoWarehouseId(String intoWarehouseId) {
		this.intoWarehouseId = intoWarehouseId;
	}


}