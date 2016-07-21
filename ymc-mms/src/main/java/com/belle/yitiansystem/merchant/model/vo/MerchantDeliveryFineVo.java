package com.belle.yitiansystem.merchant.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *创建人:lei.s
 *创建时间:2013-6-4
 *商家处罚Vo
 *
 */
public class MerchantDeliveryFineVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 优购订单号
	 */
	private String orderNo;
	/**
	 * 外部订单号
	 */
	private String spOrderNo;
	/**
	 * 下单时间
	 */
	private Date orderDate;
	/**
	 * 审核时间
	 */
	private Date checkDate;
	/**
	 * 发货时间
	 */
	private Date deliveryDate;
	/**
	 * 供应商名称
	 */
	private String supplierName;
	/**
	 * 供应商编码
	 */
	private String supplierCode;
	/**
	 * 扣款状态
	 */
	private Integer deductStatus;
	/**
	 * 发货状态
	 */
	private Integer goodStatus;
	/**
	 * 订单金额
	 */
	private Double orderAmount;
	/**
	 * 扣除金额
	 */
	private Double deductAmount;
	/**
	 * 违规类型
	 */
	private Integer violateType;
	/**
	 * 超出时间
	 */
	private Double overTime;
	/**
	 * 最后操作时间
	 */
	private Date  lastModifyDate;
	/**
	 * 最后操作人
	 */
	private String lastModifyPerson;
	/**
	 * 三级订单来源id
	 */
	private String orderSourceNo;
	/**
	 * 三级订单来源名称
	 */
	private String outShopName;
	/**
	 * 结算id 
	 */
	private String balanceBillId;
	public String getBalanceBillId() {
		return balanceBillId;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public Double getDeductAmount() {
		return deductAmount;
	}
	public Integer getDeductStatus() {
		return deductStatus;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public Integer getGoodStatus() {
		return goodStatus;
	}
	public String getId() {
		return id;
	}
	public Date getLastModifyDate() {
		return lastModifyDate;
	}
	public String getLastModifyPerson() {
		return lastModifyPerson;
	}
	public Double getOrderAmount() {
		return orderAmount;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public String getOrderSourceNo() {
		return orderSourceNo;
	}
	public String getOutShopName() {
		return outShopName;
	}
	public Double getOverTime() {
		return overTime;
	}
	public String getSpOrderNo() {
		return spOrderNo;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public Integer getViolateType() {
		return violateType;
	}
	public void setBalanceBillId(String balanceBillId) {
		this.balanceBillId = balanceBillId;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public void setDeductAmount(Double deductAmount) {
		this.deductAmount = deductAmount;
	}
	public void setDeductStatus(Integer deductStatus) {
		this.deductStatus = deductStatus;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public void setGoodStatus(Integer goodStatus) {
		this.goodStatus = goodStatus;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public void setLastModifyPerson(String lastModifyPerson) {
		this.lastModifyPerson = lastModifyPerson;
	}
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setOrderSourceNo(String orderSourceNo) {
		this.orderSourceNo = orderSourceNo;
	}
	public void setOutShopName(String outShopName) {
		this.outShopName = outShopName;
	}
	public void setOverTime(Double overTime) {
		this.overTime = overTime;
	}
	public void setSpOrderNo(String spOrderNo) {
		this.spOrderNo = spOrderNo;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	@Override
	public String toString() {
		return "deliveryFineVo.orderNo:"+orderNo+
				"deliveryFineVo.sporderNo:"+spOrderNo+
				"deliveryFineVo.orderDate:"+orderDate+
				"deliveryFineVo.checkDate:"+checkDate+
				"deliveryFineVo.deliveryDate:"+deliveryDate+
				"deliveryFineVo.supplierName:"+supplierName+
				"deliveryFineVo.supplierCode:"+supplierCode+
				"deliveryFineVo.goodStatus:"+goodStatus+
				"deliveryFineVo.orderAmount:"+orderAmount+
				"deliveryFineVo.deductAmount:"+deductAmount+
				"deliveryFineVo.violateType:"+violateType+
				"deliveryFineVo.overTime:"+overTime+
				"deliveryFineVo.orderSourceNo:"+orderSourceNo+
				"deliveryFineVo.outShopName:"+outShopName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setViolateType(Integer violateType) {
		this.violateType = violateType;
	}
	
}
