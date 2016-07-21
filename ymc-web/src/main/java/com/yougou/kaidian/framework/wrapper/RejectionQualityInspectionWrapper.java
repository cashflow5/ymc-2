package com.yougou.kaidian.framework.wrapper;

import java.util.Date;
import java.util.List;

import com.yougou.wms.wpi.rejectioninspection.domain.RejectionInspectionDetailDomain;
import com.yougou.wms.wpi.rejectioninspection.domain.RejectionInspectionDomain;

public class RejectionQualityInspectionWrapper extends RejectionInspectionDomain {

	private static final long serialVersionUID = -6677352216903007914L;

	public RejectionInspectionDomain rejectionInspectionDomain;

	public RejectionQualityInspectionWrapper(RejectionInspectionDomain rejectionInspectionDomain) {
		this.rejectionInspectionDomain = rejectionInspectionDomain;
	}

	@Override
	public String getApprover() {
		return rejectionInspectionDomain.getApprover();
	}

	@Override
	public Date getApproverDate() {
		return rejectionInspectionDomain.getApproverDate();
	}

	@Override
	public Date getCancelDate() {
		return rejectionInspectionDomain.getCancelDate();
	}

	@Override
	public String getCancelId() {
		return rejectionInspectionDomain.getCancelId();
	}

	@Override
	public String getCancelPerson() {
		return rejectionInspectionDomain.getCancelPerson();
	}

	@Override
	public String getChecker() {
		return rejectionInspectionDomain.getChecker();
	}

	@Override
	public Date getCreateDate() {
		return rejectionInspectionDomain.getCreateDate();
	}

	@Override
	public String getCreator() {
		return rejectionInspectionDomain.getCreator();
	}

	@Override
	public Integer getErrorType() {
		return rejectionInspectionDomain.getErrorType();
	}

	@Override
	public String getExpressCode() {
		return rejectionInspectionDomain.getExpressCode();
	}

	@Override
	public Double getExpressfee() {
		return rejectionInspectionDomain.getExpressfee();
	}

	@Override
	public String getExpressno() {
		return rejectionInspectionDomain.getExpressno();
	}

	@Override
	public String getId() {
		return rejectionInspectionDomain.getId();
	}

	@Override
	public String getInspectionCode() {
		return rejectionInspectionDomain.getInspectionCode();
	}

	@Override
	public Integer getIsBack() {
		return rejectionInspectionDomain.getIsBack();
	}

	@Override
	public Integer getIsCancel() {
		return rejectionInspectionDomain.getIsCancel();
	}

	@Override
	public Integer getIsOffset() {
		return rejectionInspectionDomain.getIsOffset();
	}

	@Override
	public String getOrderCode() {
		return rejectionInspectionDomain.getOrderCode();
	}

	@Override
	public String getOrderSourceNo() {
		return rejectionInspectionDomain.getOrderSourceNo();
	}

	@Override
	public String getOrdersourceid() {
		return rejectionInspectionDomain.getOrdersourceid();
	}

	@Override
	public String getOutorderid() {
		return rejectionInspectionDomain.getOutorderid();
	}

	@Override
	public String getOutshopid() {
		return rejectionInspectionDomain.getOutshopid();
	}

	@Override
	public Integer getPaytype() {
		return rejectionInspectionDomain.getPaytype();
	}

	@Override
	public List<RejectionInspectionDetailDomain> getRejectionQcRegisterdetailDomainVo() {
		return rejectionInspectionDomain.getRejectionQcRegisterdetailDomainVo();
	}

	@Override
	public Integer getRejectionType() {
		return rejectionInspectionDomain.getRejectionType();
	}

	@Override
	public String getRemark() {
		return rejectionInspectionDomain.getRemark();
	}

	@Override
	public Integer getSTATUS() {
		return rejectionInspectionDomain.getSTATUS();
	}

	@Override
	public String getSaleType() {
		return rejectionInspectionDomain.getSaleType();
	}

	@Override
	public Integer getSecConfirmStatus() {
		return rejectionInspectionDomain.getSecConfirmStatus();
	}

	@Override
	public String getWarehouseId() {
		return rejectionInspectionDomain.getWarehouseId();
	}

	@Override
	public void setApprover(String approver) {
		rejectionInspectionDomain.setApprover(approver);
	}

	@Override
	public void setApproverDate(Date approverDate) {
		rejectionInspectionDomain.setApproverDate(approverDate);
	}

	@Override
	public void setCancelDate(Date cancelDate) {
		rejectionInspectionDomain.setCancelDate(cancelDate);
	}

	@Override
	public void setCancelId(String cancelId) {
		rejectionInspectionDomain.setCancelId(cancelId);
	}

	@Override
	public void setCancelPerson(String cancelPerson) {
		rejectionInspectionDomain.setCancelPerson(cancelPerson);
	}

	@Override
	public void setChecker(String checker) {
		rejectionInspectionDomain.setChecker(checker);
	}

	@Override
	public void setCreateDate(Date createDate) {
		rejectionInspectionDomain.setCreateDate(createDate);
	}

	@Override
	public void setCreator(String creator) {
		rejectionInspectionDomain.setCreator(creator);
	}

	@Override
	public void setErrorType(Integer errorType) {
		rejectionInspectionDomain.setErrorType(errorType);
	}

	@Override
	public void setExpressCode(String expressCode) {
		rejectionInspectionDomain.setExpressCode(expressCode);
	}

	@Override
	public void setExpressfee(Double expressfee) {
		rejectionInspectionDomain.setExpressfee(expressfee);
	}

	@Override
	public void setExpressno(String expressno) {
		rejectionInspectionDomain.setExpressno(expressno);
	}

	@Override
	public void setId(String id) {
		rejectionInspectionDomain.setId(id);
	}

	@Override
	public void setInspectionCode(String inspectionCode) {
		rejectionInspectionDomain.setInspectionCode(inspectionCode);
	}

	@Override
	public void setIsBack(Integer isBack) {
		rejectionInspectionDomain.setIsBack(isBack);
	}

	@Override
	public void setIsCancel(Integer isCancel) {
		rejectionInspectionDomain.setIsCancel(isCancel);
	}

	@Override
	public void setIsOffset(Integer isOffset) {
		rejectionInspectionDomain.setIsOffset(isOffset);
	}

	@Override
	public void setOrderCode(String orderCode) {
		rejectionInspectionDomain.setOrderCode(orderCode);
	}

	@Override
	public void setOrderSourceNo(String orderSourceNo) {
		rejectionInspectionDomain.setOrderSourceNo(orderSourceNo);
	}

	@Override
	public void setOrdersourceid(String ordersourceid) {
		rejectionInspectionDomain.setOrdersourceid(ordersourceid);
	}

	@Override
	public void setOutorderid(String outorderid) {
		rejectionInspectionDomain.setOutorderid(outorderid);
	}

	@Override
	public void setOutshopid(String outshopid) {
		rejectionInspectionDomain.setOutshopid(outshopid);
	}

	@Override
	public void setPaytype(Integer paytype) {
		rejectionInspectionDomain.setPaytype(paytype);
	}

	@Override
	public void setRejectionQcRegisterdetailDomainVo(List<RejectionInspectionDetailDomain> rejectionQcRegisterdetailDomainVo) {
		rejectionInspectionDomain.setRejectionQcRegisterdetailDomainVo(rejectionQcRegisterdetailDomainVo);
	}

	@Override
	public void setRejectionType(Integer rejectionType) {
		rejectionInspectionDomain.setRejectionType(rejectionType);
	}

	@Override
	public void setRemark(String remark) {
		rejectionInspectionDomain.setRemark(remark);
	}

	@Override
	public void setSTATUS(Integer sTATUS) {
		rejectionInspectionDomain.setSTATUS(sTATUS);
	}

	@Override
	public void setSaleType(String saleType) {
		rejectionInspectionDomain.setSaleType(saleType);
	}

	@Override
	public void setSecConfirmStatus(Integer secConfirmStatus) {
		rejectionInspectionDomain.setSecConfirmStatus(secConfirmStatus);
	}

	@Override
	public void setWarehouseId(String warehouseId) {
		rejectionInspectionDomain.setWarehouseId(warehouseId);
	}

}
