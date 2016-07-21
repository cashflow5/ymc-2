package com.belle.yitiansystem.merchant.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 处罚订单查询  vo
 * @author he.wc
 *
 */
public class PunishOrderVo implements Serializable {

	private static final long serialVersionUID = -8445178105675542201L;

	/** 优购订单编号 */
	private String orderNo;
	
	/** 外部订单号 */
	private String thirdOrderNo;
	
	/** 商家名称  */
	private String merchantName;
	
	/** 商家编码 */
	private String merchantCode;
	
	/** 发货状态 */
	private String shipmentStatus;
	
	/** 处罚订单状态 */  
	/** 审核状态, 0:已删除  1:待审核  2:已审核 */
	private String punishOrderStatus;
	
	/** 处罚类型,1:超时效,0:缺货  */
	private String punishType;
	
	/** 下单时间-开始 */
	private Date orderTimeStart;
	
	/** 下单时间-结束 */
	private Date orderTimeEnd;
	
	/** 扣款状态,1:是,0:否 */
	private String isSettle;
	
	/** 审核时间  - 开始*/
	private Date validTimeStart;
	
	/** 审核时间  - 结束*/
	private Date validTimeEnd;
	
	/** 备注 */
	private String remark;
	
	/** 处罚金额 */
	private Double punishPrice;
	/** 货品负责人*/
	private String supplierYgContacts;
	
	/** 商品品类 一级*/
	private String category;
	/**商品品类 二级*/
	private String secondCategory;
	/**三级*/
	private String thirdCategory;
	private String structName;
	/** 商品品牌*/
	private String brandNo;
	/**置缺时间 --开始*/
	private Date lackTimeStart;
	/**置缺时间 --结束*/
	private Date lackTimeEnd;
	/**货品条码*/
	private String insideCode;
	/**是否提交到违规结算列表  1:是,0:否*/
	private String isSubmit;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getThirdOrderNo() {
		return thirdOrderNo;
	}

	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getShipmentStatus() {
		return shipmentStatus;
	}

	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}

	public String getPunishType() {
		return punishType;
	}

	public void setPunishType(String punishType) {
		this.punishType = punishType;
	}

	public Date getOrderTimeStart() {
		return orderTimeStart;
	}

	public void setOrderTimeStart(Date orderTimeStart) {
		this.orderTimeStart = orderTimeStart;
	}

	public Date getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(Date orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}

	public Date getValidTimeStart() {
		return validTimeStart;
	}

	public void setValidTimeStart(Date validTimeStart) {
		this.validTimeStart = validTimeStart;
	}

	public Date getValidTimeEnd() {
		return validTimeEnd;
	}

	public void setValidTimeEnd(Date validTimeEnd) {
		this.validTimeEnd = validTimeEnd;
	}

	public String getIsSettle() {
		return isSettle;
	}

	public void setIsSettle(String isSettle) {
		this.isSettle = isSettle;
	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getPunishPrice() {
		return punishPrice;
	}

	public void setPunishPrice(Double punishPrice) {
		this.punishPrice = punishPrice;
	}

	public String getPunishOrderStatus() {
		return punishOrderStatus;
	}

	public void setPunishOrderStatus(String punishOrderStatus) {
		this.punishOrderStatus = punishOrderStatus;
	}

	public String getSupplierYgContacts() {
		return supplierYgContacts;
	}

	public void setSupplierYgContacts(String supplierYgContacts) {
		this.supplierYgContacts = supplierYgContacts;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getSecondCategory() {
		return secondCategory;
	}

	public void setSecondCategory(String secondCategory) {
		this.secondCategory = secondCategory;
	}

	public String getThirdCategory() {
		return thirdCategory;
	}

	public void setThirdCategory(String thirdCategory) {
		this.thirdCategory = thirdCategory;
	}

	public String getStructName() {
		return structName;
	}

	public void setStructName(String structName) {
		this.structName = structName;
	}

	public Date getLackTimeStart() {
		return lackTimeStart;
	}

	public void setLackTimeStart(Date lackTimeStart) {
		this.lackTimeStart = lackTimeStart;
	}

	public Date getLackTimeEnd() {
		return lackTimeEnd;
	}

	public void setLackTimeEnd(Date lackTimeEnd) {
		this.lackTimeEnd = lackTimeEnd;
	}

	public String getInsideCode() {
		return insideCode;
	}

	public void setInsideCode(String insideCode) {
		this.insideCode = insideCode;
	}

	public String getIsSubmit() {
		return isSubmit;
	}

	public void setIsSubmit(String isSubmit) {
		this.isSubmit = isSubmit;
	}
	
}

