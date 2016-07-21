package com.belle.yitiansystem.merchant.model.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商家处罚订单中间表
 * 
 * @author he.wc
 * 
 */
@Entity
@Table(name = "tbl_sp_supplier_order")
public class PunishOrderAgent implements java.io.Serializable {

	private static final long serialVersionUID = -3325728271472498389L;

	/** 商品编号 */
	@Id
	@Column(name = "order_no", unique = true, nullable = false)
	private String orderNo;

	/** 下单时间 */
	@Column(name = "order_time")
	private Timestamp orderTime;

	/** 订单金额 */
	@Column(name = "order_price")
	private Double orderPrice;

	/** 外部订单号 */
	@Column(name = "third_order_no")
	private String thirdOrderNo;

	/** 商家编号 */
	@Column(name = "merchant_code")
	private String merchantCode;

	/** 发货状态 */
	@Column(name = "shipment_status")
	private String shipmentStatus;

	/** 发货时间 */
	@Column(name = "shipment_time")
	private Timestamp shipmentTime;

	/** 是否取消订单 */
	@Column(name = "is_delete")
	private String isDelete;

	/** 订单来源 */
	@Column(name = "order_source_no")
	private String orderSourceNo;

	/** 店铺名称 */
	@Column(name = "out_shop_name")
	private String outShopName;

	/** 生成处罚订单时间 */
	@Column(name = "punish_create_time")
	private Timestamp punishCreateTime;

	/** 同步创建时间 */
	@Column(name = "syn_create_time")
	private Timestamp synCreateTime;

	/** 同步创建时间 */
	@Column(name = "syn_update_time")
	private Timestamp synUpdateTime;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getThirdOrderNo() {
		return thirdOrderNo;
	}

	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
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

	public Timestamp getShipmentTime() {
		return shipmentTime;
	}

	public void setShipmentTime(Timestamp shipmentTime) {
		this.shipmentTime = shipmentTime;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getOrderSourceNo() {
		return orderSourceNo;
	}

	public void setOrderSourceNo(String orderSourceNo) {
		this.orderSourceNo = orderSourceNo;
	}

	public String getOutShopName() {
		return outShopName;
	}

	public void setOutShopName(String outShopName) {
		this.outShopName = outShopName;
	}

	public Timestamp getPunishCreateTime() {
		return punishCreateTime;
	}

	public void setPunishCreateTime(Timestamp punishCreateTime) {
		this.punishCreateTime = punishCreateTime;
	}

	public Timestamp getSynCreateTime() {
		return synCreateTime;
	}

	public void setSynCreateTime(Timestamp synCreateTime) {
		this.synCreateTime = synCreateTime;
	}

	public Timestamp getSynUpdateTime() {
		return synUpdateTime;
	}

	public void setSynUpdateTime(Timestamp synUpdateTime) {
		this.synUpdateTime = synUpdateTime;
	}

	@Override
	public String toString() {
		return "PunishOrderAgent [orderNo=" + orderNo + ", orderTime=" + orderTime + ", orderPrice=" + orderPrice
				+ ", thirdOrderNo=" + thirdOrderNo + ", merchantCode=" + merchantCode + ", shipmentStatus="
				+ shipmentStatus + ", shipmentTime=" + shipmentTime + ", isDelete=" + isDelete + ", orderSourceNo="
				+ orderSourceNo + ", outShopName=" + outShopName + ", punishCreateTime=" + punishCreateTime
				+ ", synCreateTime=" + synCreateTime + ", synUpdateTime=" + synUpdateTime + "]";
	}
	
}
