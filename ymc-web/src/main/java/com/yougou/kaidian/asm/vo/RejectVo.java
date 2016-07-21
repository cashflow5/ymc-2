package com.yougou.kaidian.asm.vo;

import java.util.Date;

/**
 * 拒收VO
 * @author huang.qm
 *
 */
public class RejectVo {

	//快递单号
	private String expressOrderId;
	//货品条码
	private String thirdPartyCode;
	//当前质检日期
	private String curDate;
	
	/*************主表信息*****************/
	/** 拒收类型 非拆包拒收 0 拆包拒收 1**/
	private String rejectionType;
	/** 创建人 **/
	private String  creator;
	/** 创建时间 **/
	private Date  createDate;
	private String warehouseId;
	private Double expressfee;
	private String orderSubNo;
	// 1：异常收货；0：正常收货
	private Integer errorType;
	//1：到付；0：非到付
	private Integer paytype=0;
	
	/*************子表信息*****************/
	//货品id
	private String commodityId;
	//货品编码
	private String commodityCode;
	//商品名称
	private String goodsName;
	//单位
	private String units;
	//规格
	private String specification;
	//款色编码
	private String invitemno;
	//问题类型
	private String problemType;
	//问题原因
	private String problemReason;
	//问题归属
	private String problemDue;
	//入库类型
	private String storageType;
	//质检描述
	private String descr;
	//主表id
	private String rejectionId;
	//主表的编码
	private String inspectionCode;
	//错误信息
	private String errorMessage;
	//质检明细ID
	private String rejectDetailId;
	
	private String merchantCode;
	
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getRejectDetailId() {
		return rejectDetailId;
	}

	public void setRejectDetailId(String rejectDetailId) {
		this.rejectDetailId = rejectDetailId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getInspectionCode() {
		return inspectionCode;
	}

	public void setInspectionCode(String inspectionCode) {
		this.inspectionCode = inspectionCode;
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getInvitemno() {
		return invitemno;
	}

	public void setInvitemno(String invitemno) {
		this.invitemno = invitemno;
	}

	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	public String getProblemReason() {
		return problemReason;
	}

	public void setProblemReason(String problemReason) {
		this.problemReason = problemReason;
	}

	public String getProblemDue() {
		return problemDue;
	}

	public void setProblemDue(String problemDue) {
		this.problemDue = problemDue;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getRejectionId() {
		return rejectionId;
	}

	public void setRejectionId(String rejectionId) {
		this.rejectionId = rejectionId;
	}

	public String getRejectionType() {
		return rejectionType;
	}

	public void setRejectionType(String rejectionType) {
		this.rejectionType = rejectionType;
	}

	public String getCurDate() {
		return curDate;
	}

	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}
	
	public String getExpressOrderId() {
		return expressOrderId;
	}

	public void setExpressOrderId(String expressOrderId) {
		this.expressOrderId = expressOrderId;
	}

	public String getThirdPartyCode() {
		return thirdPartyCode;
	}

	public void setThirdPartyCode(String thirdPartyCode) {
		this.thirdPartyCode = thirdPartyCode;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Double getExpressfee() {
		return expressfee;
	}

	public void setExpressfee(Double expressfee) {
		this.expressfee = expressfee;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOrderSubNo() {
		return orderSubNo;
	}

	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}

	public Integer getErrorType() {
		return errorType;
	}

	public void setErrorType(Integer errorType) {
		this.errorType = errorType;
	}

	public Integer getPaytype() {
		return paytype;
	}

	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}
	
	
}
