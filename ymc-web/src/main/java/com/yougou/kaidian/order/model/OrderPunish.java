package com.yougou.kaidian.order.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class OrderPunish implements Serializable {

	private static final long serialVersionUID = -4634618838623161002L;
	
	private String id;

	/** 商品编号 */
	private String orderNo;

	/** 下单时间 */
	private Timestamp orderTime;

	/** 订单金额 */
	private Double orderPrice;

	/** 外部订单号 */
	private String thirdOrderNo;

	/** 扣款状态,1:是,0:否 */
	private String isSettle;

	/** 结算单号 */
	private String settleOrderNo;

	/** 结算周期 */
	private String settleCycle;

	/** 处罚金额 */
	private Double punishPrice;

	/** 处罚类型,1:超时效,0:缺货 */
	private String punishType;

	/** 下单时间-开始 */
	private Date orderTimeStart;

	/** 下单时间-结束 */
	private Date orderTimeEnd;
	
	/** 发货状态 */
	private String shipmentStatus;
	
	/** 发货时间 */
	private Timestamp shipmentTime;
	
	/** 创建时间 */
	private Timestamp createTime;
	
	/** 更新时间 */
	private Timestamp updateTime;
	
	/** 商家编码 */
	private String merchantCode;
	
	/** 违规订单状态*/
	private String punishOrderStatus;
	
	/** 第三级订单来源 */
	private String orderSourceNo;
	
	/** 店铺来源*/
	private String outShopName;
	
	/** 货品条码*/
	private String insideCode;
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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getPunishOrderStatus() {
		return punishOrderStatus;
	}

	public void setPunishOrderStatus(String punishOrderStatus) {
		this.punishOrderStatus = punishOrderStatus;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getShipmentTime() {
		return shipmentTime;
	}

	public void setShipmentTime(Timestamp shipmentTime) {
		this.shipmentTime = shipmentTime;
	}

	public String getShipmentStatus() {
		return shipmentStatus;
	}

	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}

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

	public String getIsSettle() {
		return isSettle;
	}

	public void setIsSettle(String isSettle) {
		this.isSettle = isSettle;
	}

	public String getSettleOrderNo() {
		return settleOrderNo;
	}

	public void setSettleOrderNo(String settleOrderNo) {
		this.settleOrderNo = settleOrderNo;
	}

	public String getSettleCycle() {
		return settleCycle;
	}

	public void setSettleCycle(String settleCycle) {
		this.settleCycle = settleCycle;
	}

	public Double getPunishPrice() {
		return punishPrice;
	}

	public void setPunishPrice(Double punishPrice) {
		this.punishPrice = punishPrice;
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

	public String getInsideCode() {
		return insideCode;
	}

	public void setInsideCode(String insideCode) {
		this.insideCode = insideCode;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
//		return "OrderPunish [id=" + id + ", orderNo=" + orderNo + ", orderTime=" + orderTime + ", orderPrice=" + orderPrice + ", thirdOrderNo="
//				+ thirdOrderNo + ", isSettle=" + isSettle + ", settleOrderNo=" + settleOrderNo + ", settleCycle=" + settleCycle + ", punishPrice="
//				+ punishPrice + ", punishType=" + punishType + ", orderTimeStart=" + orderTimeStart + ", orderTimeEnd=" + orderTimeEnd
//				+ ", shipmentStatus=" + shipmentStatus + ", shipmentTime=" + shipmentTime + "]";
	}
	
	
}