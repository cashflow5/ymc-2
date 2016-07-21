package com.belle.other.model.vo;

import java.util.List;


public class OtherOutStoreWmsVo {
	
	private String otherOutStoreCode;
	private Integer outStorageType;
	private String creator;
	private String createorName;
	private String modifier;
	private String modifierName;
	private String operator;
	private String operatorName;
	private Integer status;
	private String createDate;
	private String modifyDate;
	private String operateDate;
	private String fromVoucher;
	private String fromVoucherId;
	private String toVoucher;
	private String toVoucherId;
	private String companyId;
	private String storehouseId;
	private String srcStorehouseId;
	private String remark;
	private String outId;
	private String  dealer;
	private Long  timestampvalue;
	private List<OtherOutStoreDetailWmsVo> detail;
	public OtherOutStoreWmsVo() {
		super();
	}
	
	public String getOtherOutStoreCode() {
		return otherOutStoreCode;
	}

	public void setOtherOutStoreCode(String otherOutStoreCode) {
		this.otherOutStoreCode = otherOutStoreCode;
	}

	public Integer getOutStorageType() {
		return outStorageType;
	}
	public void setOutStorageType(Integer outStorageType) {
		this.outStorageType = outStorageType;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateorName() {
		return createorName;
	}
	public void setCreateorName(String createorName) {
		this.createorName = createorName;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public String getModifierName() {
		return modifierName;
	}
	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getFromVoucher() {
		return fromVoucher;
	}
	public void setFromVoucher(String fromVoucher) {
		this.fromVoucher = fromVoucher;
	}
	public String getFromVoucherId() {
		return fromVoucherId;
	}
	public void setFromVoucherId(String fromVoucherId) {
		this.fromVoucherId = fromVoucherId;
	}
	public String getToVoucher() {
		return toVoucher;
	}
	public void setToVoucher(String toVoucher) {
		this.toVoucher = toVoucher;
	}
	
	public String getToVoucherId() {
		return toVoucherId;
	}

	public void setToVoucherId(String toVoucherId) {
		this.toVoucherId = toVoucherId;
	}

	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getStorehouseId() {
		return storehouseId;
	}
	public void setStorehouseId(String storehouseId) {
		this.storehouseId = storehouseId;
	}
	public String getSrcStorehouseId() {
		return srcStorehouseId;
	}
	public void setSrcStorehouseId(String srcStorehouseId) {
		this.srcStorehouseId = srcStorehouseId;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOutId() {
		return outId;
	}
	public void setOutId(String outId) {
		this.outId = outId;
	}
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	public Long getTimestampvalue() {
		return timestampvalue;
	}
	public void setTimestampvalue(Long timestampvalue) {
		this.timestampvalue = timestampvalue;
	}

	public List<OtherOutStoreDetailWmsVo> getDetail() {
		return detail;
	}

	public void setDetail(List<OtherOutStoreDetailWmsVo> detail) {
		this.detail = detail;
	}
	
	

}
