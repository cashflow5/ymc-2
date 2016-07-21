package com.yougou.kaidian.asm.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.yougou.wms.wpi.returnqaproduct.domain.vo.ReturnQaProductDomainVo.CashOnDelivery;
import com.yougou.wms.wpi.returnqaproduct.domain.vo.ReturnQaProductDomainVo.ReturnExceptionType;
import com.yougou.wms.wpi.returnqaproduct.domain.vo.ReturnQaProductDomainVo.ReturnStatus;

/**
 * 售后VO
 * 
 * @author huang.qm
 * 
 */
public class ReturnVo {

	// 订单号
	private String orderNo;
//	// 款色编码
//	private String thirdPartyCode;
	//货品条码
	private String insideCode;
	// 当前质检日期
	private String curDate;
	// 错误信息
	private String errorMessage;
	// 申请号
	private String applyNo;
	
	// 1：异常收货；0：正常收货
	private String errorType;
	//1：到付；0：非到付
	private String paytype;
	//质检明细ID
	private String returnDetailId;
	
	
	/* 主信息* */
	private String expressCode;// 快递单号
	private String expressId;// 快递公司id
	private String expressName;// 快递公司名称
	private BigDecimal expressCharges;// 快递费用
	private CashOnDelivery cashOnDelivery;// 到付
	private String qaPerson;// 质检人
	private Date qaDate;// 质检时间
	private String remark;// 备注
	private ReturnExceptionType isException;// 是否异常完成
	private String warehouseId;// 仓库ID
	private String warehouseCode;// 物流公司编码
	private String warehouseName;// 物流公司名称
	private Date approverDate;// 质检确认时间
	private ReturnStatus returnStatus = ReturnStatus.WAIT_CONFIRM;//移交单状态
	
	
	//子表信息
	private String questionType;//问题类型
	private String questionCause;//问题原因
	private String questionClassification;//问题分类
	private String storageType;//入库类型
	private String qaDescription;//质检描述
	private Date qaApplyDate;// 退货换质检申请时间
	private String returnId;//退货单号ID
	
	private String merchantCode;//商家编码
	
	private String returnType;//退货单类型
	
	private String isPassed;//质检是否通过
	
	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getReturnId() {
		return returnId;
	}

	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuestionCause() {
		return questionCause;
	}

	public void setQuestionCause(String questionCause) {
		this.questionCause = questionCause;
	}

	public String getQuestionClassification() {
		return questionClassification;
	}

	public void setQuestionClassification(String questionClassification) {
		this.questionClassification = questionClassification;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getQaDescription() {
		return qaDescription;
	}

	public void setQaDescription(String qaDescription) {
		this.qaDescription = qaDescription;
	}

	public Date getQaApplyDate() {
		return qaApplyDate;
	}

	public void setQaApplyDate(Date qaApplyDate) {
		this.qaApplyDate = qaApplyDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

//	public String getThirdPartyCode() {
//		return thirdPartyCode;
//	}
//
//	public void setThirdPartyCode(String thirdPartyCode) {
//		this.thirdPartyCode = thirdPartyCode;
//	}

	public String getCurDate() {
		return curDate;
	}

	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}

	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public BigDecimal getExpressCharges() {
		return expressCharges;
	}

	public void setExpressCharges(BigDecimal expressCharges) {
		this.expressCharges = expressCharges;
	}

	public CashOnDelivery getCashOnDelivery() {
		return cashOnDelivery;
	}

	public void setCashOnDelivery(CashOnDelivery cashOnDelivery) {
		this.cashOnDelivery = cashOnDelivery;
	}

	public String getQaPerson() {
		return qaPerson;
	}

	public void setQaPerson(String qaPerson) {
		this.qaPerson = qaPerson;
	}

	public Date getQaDate() {
		return qaDate;
	}

	public void setQaDate(Date qaDate) {
		this.qaDate = qaDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ReturnExceptionType getIsException() {
		return isException;
	}

	public void setIsException(ReturnExceptionType isException) {
		this.isException = isException;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public Date getApproverDate() {
		return approverDate;
	}

	public void setApproverDate(Date approverDate) {
		this.approverDate = approverDate;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public ReturnStatus getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(ReturnStatus returnStatus) {
		this.returnStatus = returnStatus;
	}

	public String getReturnDetailId() {
		return returnDetailId;
	}

	public void setReturnDetailId(String returnDetailId) {
		this.returnDetailId = returnDetailId;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getInsideCode() {
		return insideCode;
	}

	public void setInsideCode(String insideCode) {
		this.insideCode = insideCode;
	}

	public String getIsPassed() {
		return isPassed;
	}

	public void setIsPassed(String isPassed) {
		this.isPassed = isPassed;
	}
}
