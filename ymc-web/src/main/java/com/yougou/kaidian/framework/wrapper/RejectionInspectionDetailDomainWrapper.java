package com.yougou.kaidian.framework.wrapper;

import com.yougou.wms.wpi.rejectioninspection.domain.RejectionInspectionDetailDomain;

public class RejectionInspectionDetailDomainWrapper extends RejectionInspectionDetailDomain {

	private static final long serialVersionUID = 6499232548937186865L;

	private RejectionInspectionDetailDomain rejectionInspectionDetailDomain;
	
	private String thirdPartyCode;

	public RejectionInspectionDetailDomainWrapper(RejectionInspectionDetailDomain rejectionInspectionDetailDomain) {
		this.rejectionInspectionDetailDomain = rejectionInspectionDetailDomain;
	}

	@Override
	public String getCommodityCode() {
		return rejectionInspectionDetailDomain.getCommodityCode();
	}

	@Override
	public String getCommodityId() {
		return rejectionInspectionDetailDomain.getCommodityId();
	}

	@Override
	public Integer getDefectiveType() {
		return rejectionInspectionDetailDomain.getDefectiveType();
	}

	@Override
	public String getDescr() {
		return rejectionInspectionDetailDomain.getDescr();
	}

	@Override
	public String getGoodsName() {
		return rejectionInspectionDetailDomain.getGoodsName();
	}

	@Override
	public String getId() {
		return rejectionInspectionDetailDomain.getId();
	}

	@Override
	public String getInInventoryType() {
		return rejectionInspectionDetailDomain.getInInventoryType();
	}

	@Override
	public String getInspectionCode() {
		return rejectionInspectionDetailDomain.getInspectionCode();
	}

	@Override
	public String getInvitemno() {
		return rejectionInspectionDetailDomain.getInvitemno();
	}

	@Override
	public String getOrderSubNo() {
		return rejectionInspectionDetailDomain.getOrderSubNo();
	}

	@Override
	public String getOutInventoryType() {
		return rejectionInspectionDetailDomain.getOutInventoryType();
	}

	@Override
	public String getProblemDue() {
		return rejectionInspectionDetailDomain.getProblemDue();
	}

	@Override
	public String getProblemReason() {
		return rejectionInspectionDetailDomain.getProblemReason();
	}

	@Override
	public String getProblemType() {
		return rejectionInspectionDetailDomain.getProblemType();
	}

	@Override
	public Integer getQuantity() {
		return rejectionInspectionDetailDomain.getQuantity();
	}

	@Override
	public String getRejectionId() {
		return rejectionInspectionDetailDomain.getRejectionId();
	}

	@Override
	public String getSaleType() {
		return rejectionInspectionDetailDomain.getSaleType();
	}

	@Override
	public String getSpecification() {
		return rejectionInspectionDetailDomain.getSpecification();
	}

	@Override
	public String getStorageType() {
		return rejectionInspectionDetailDomain.getStorageType();
	}

	@Override
	public String getSupplierCode() {
		return rejectionInspectionDetailDomain.getSupplierCode();
	}

	@Override
	public String getUnits() {
		return rejectionInspectionDetailDomain.getUnits();
	}

	@Override
	public void setCommodityCode(String commodityCode) {
		rejectionInspectionDetailDomain.setCommodityCode(commodityCode);
	}

	@Override
	public void setCommodityId(String commodityId) {
		rejectionInspectionDetailDomain.setCommodityId(commodityId);
	}

	@Override
	public void setDefectiveType(Integer defectiveType) {
		rejectionInspectionDetailDomain.setDefectiveType(defectiveType);
	}

	@Override
	public void setDescr(String descr) {
		rejectionInspectionDetailDomain.setDescr(descr);
	}

	@Override
	public void setGoodsName(String goodsName) {
		rejectionInspectionDetailDomain.setGoodsName(goodsName);
	}

	@Override
	public void setId(String id) {
		rejectionInspectionDetailDomain.setId(id);
	}

	@Override
	public void setInInventoryType(String inInventoryType) {
		rejectionInspectionDetailDomain.setInInventoryType(inInventoryType);
	}

	@Override
	public void setInspectionCode(String inspectionCode) {
		rejectionInspectionDetailDomain.setInspectionCode(inspectionCode);
	}

	@Override
	public void setInvitemno(String invitemno) {
		rejectionInspectionDetailDomain.setInvitemno(invitemno);
	}

	@Override
	public void setOrderSubNo(String orderSubNo) {
		rejectionInspectionDetailDomain.setOrderSubNo(orderSubNo);
	}

	@Override
	public void setOutInventoryType(String outInventoryType) {
		rejectionInspectionDetailDomain.setOutInventoryType(outInventoryType);
	}

	@Override
	public void setProblemDue(String problemDue) {
		rejectionInspectionDetailDomain.setProblemDue(problemDue);
	}

	@Override
	public void setProblemReason(String problemReason) {
		rejectionInspectionDetailDomain.setProblemReason(problemReason);
	}

	@Override
	public void setProblemType(String problemType) {
		rejectionInspectionDetailDomain.setProblemType(problemType);
	}

	@Override
	public void setQuantity(Integer quantity) {
		rejectionInspectionDetailDomain.setQuantity(quantity);
	}

	@Override
	public void setRejectionId(String rejectionId) {
		rejectionInspectionDetailDomain.setRejectionId(rejectionId);
	}

	@Override
	public void setSaleType(String saleType) {
		rejectionInspectionDetailDomain.setSaleType(saleType);
	}

	@Override
	public void setSpecification(String specification) {
		rejectionInspectionDetailDomain.setSpecification(specification);
	}

	@Override
	public void setStorageType(String storageType) {
		rejectionInspectionDetailDomain.setStorageType(storageType);
	}

	@Override
	public void setSupplierCode(String supplierCode) {
		rejectionInspectionDetailDomain.setSupplierCode(supplierCode);
	}

	@Override
	public void setUnits(String units) {
		rejectionInspectionDetailDomain.setUnits(units);
	}

	public String getThirdPartyCode() {
		return thirdPartyCode;
	}

	public void setThirdPartyCode(String thirdPartyCode) {
		this.thirdPartyCode = thirdPartyCode;
	}

}
