/**
 * 
 */
package com.yougou.kaidian.asm.vo;

/**
 * 新质检登记Vo
 * 
 * @author huang.tao
 *
 */
public class QualitySaveVo {
	
	/** 商家编码 */
	private String merchantCode;
	
	/** 快递单号 */
	private String expressNo;
	
	/** 快递公司Id */
	private String expressEntId;
	
	/** 快递公司 */
	private String expressEnt;
	
	/** 运费 */
	private Double expressCharges;
	
	/** 是否到付 */
	private boolean paytype;
	
	/** 订单号 */
	private String orderNo;
	
	/** 货品条码 */
	private String insideCode;
	
	/** 质检描述 */
	private String qadsc;
	
	/** 关联订单号/关联货品条码 */
	private String qaToOrder;
	
	/** 质检人 */
	private String qaPerson;
	
	/** 错误信息 */
	private String errorMessage;
	
	/** 是否为异常收货 */
	private String isException;

	/** 是否为拒收 */
	private String isRejection;
	
	/** 仓库编码 */
	private String warehouseCode;
	
	/** 是否为异常收货 */
	private String isPassed;
	
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getExpressEntId() {
		return expressEntId;
	}

	public void setExpressEntId(String expressEntId) {
		this.expressEntId = expressEntId;
	}

	public String getExpressEnt() {
		return expressEnt;
	}

	public void setExpressEnt(String expressEnt) {
		this.expressEnt = expressEnt;
	}

	public Double getExpressCharges() {
		return expressCharges;
	}

	public void setExpressCharges(Double expressCharges) {
		this.expressCharges = expressCharges;
	}

	public boolean isPaytype() {
		return paytype;
	}

	public void setPaytype(boolean paytype) {
		this.paytype = paytype;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getInsideCode() {
		return insideCode;
	}

	public void setInsideCode(String insideCode) {
		this.insideCode = insideCode;
	}

	public String getQadsc() {
		return qadsc;
	}

	public void setQadsc(String qadsc) {
		this.qadsc = qadsc;
	}
	
	public String getQaToOrder() {
		return qaToOrder;
	}

	public void setQaToOrder(String qaToOrder) {
		this.qaToOrder = qaToOrder;
	}

	public String getQaPerson() {
		return qaPerson;
	}

	public void setQaPerson(String qaPerson) {
		this.qaPerson = qaPerson;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getIsRejection() {
		return isRejection;
	}

	public void setIsRejection(String isRejection) {
		this.isRejection = isRejection;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getIsException() {
		return isException;
	}

	public void setIsException(String isException) {
		this.isException = isException;
	}

	public String getIsPassed() {
		return isPassed;
	}

	public void setIsPassed(String isPassed) {
		this.isPassed = isPassed;
	}

	
}
