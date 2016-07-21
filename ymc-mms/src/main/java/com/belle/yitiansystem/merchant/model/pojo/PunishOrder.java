package com.belle.yitiansystem.merchant.model.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 商家处罚规则
 * 
 * @author he.wc
 * 
 */
@Entity
@Table(name = "tbl_sp_supplier_punish_order")
public class PunishOrder  implements java.io.Serializable {
	
	private static final long serialVersionUID = 3176202115816148676L;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	
	/** 商品编号 */
	@Column(name = "order_no")
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
	
	/** 扣款状态,1:是,0:否 */
	@Column(name = "is_settle")
	private String isSettle;
	
	/** 结算单号 */
	@Column(name = "settle_order_no")
	private String settleOrderNo;
	
	/** 结算周期 */
	@Column(name = "settle_cycle")
	private String settleCycle;
	
	/** 发货状态 */
	@Column(name = "shipment_status")
	private String shipmentStatus;
	
	/** 发货时间 */
	@Column(name = "shipment_time")
	private Timestamp shipmentTime;
	
	/** 处罚金额 */
	@Column(name = "punishPrice")
	private Double punishPrice;
	
	/** 处罚类型,1:超时效,0:缺货  */
	@Column(name = "punish_type")
	private String punishType;
	
	/** 处罚订单状态 2:已审核,1:待审核 0:已取消 */
	@Column(name = "punish_order_status")
	private String punishOrderStatus;

	/** 审核时间 */
	@Column(name = "valid_time")
	private Timestamp validTime;
	
	/** 审核人 */
	@Column(name = "valid_person")
	private String validPerson;
	
	/** 备注 */
	@Column(name = "remark")
	private String remark;
	
	/** 创建时间*/
	@Column(name = "create_time")
	private Timestamp createTime;
	
	/** 修改时间 */
	@Column(name = "update_time")
	private Timestamp updateTime;
	
	/** 订单来源*/
	@Column(name = "order_source_no")
	private String orderSourceNo;
	
	/** 店铺名称  */
	@Column(name = "out_shop_name")
	private String outShopName;
	
	
	/** 是否提交到违规结算列表  0否，1是*/
	@Column(name="is_submit")
	private String isSubmit;
	
	/**
	 * 提交到违规结算列表后生成的登记单号
	 * 表明该违规订单所在的登记单号
	 */
	@Column(name="settle_id")
	private String settleId;
	
	/**货品条码*/
	@Column(name="inside_code")
	private String insideCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
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

	public String getPunishType() {
		return punishType;
	}

	public void setPunishType(String punishType) {
		this.punishType = punishType;
	}

	public String getPunishOrderStatus() {
		return punishOrderStatus;
	}

	public void setPunishOrderStatus(String punishOrderStatus) {
		this.punishOrderStatus = punishOrderStatus;
	}

	public Timestamp getValidTime() {
		return validTime;
	}

	public void setValidTime(Timestamp validTime) {
		this.validTime = validTime;
	}

	public String getValidPerson() {
		return validPerson;
	}

	public void setValidPerson(String validPerson) {
		this.validPerson = validPerson;
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

	public Double getPunishPrice() {
		return punishPrice;
	}

	public void setPunishPrice(Double punishPrice) {
		this.punishPrice = punishPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Override
	public String toString() {
		return "PunishOrder [id=" + id + ", orderNo=" + orderNo + ", orderTime=" + orderTime + ", orderPrice="
				+ orderPrice + ", thirdOrderNo=" + thirdOrderNo + ", merchantCode=" + merchantCode + ", isSettle="
				+ isSettle + ", settleOrderNo=" + settleOrderNo + ", settleCycle=" + settleCycle + ", shipmentStatus="
				+ shipmentStatus + ", shipmentTime=" + shipmentTime + ", punishPrice=" + punishPrice + ", punishType="
				+ punishType + ", punishOrderStatus=" + punishOrderStatus + ", validTime=" + validTime
				+ ", validPerson=" + validPerson + ", remark=" + remark + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", orderSourceNo=" + orderSourceNo + ", outShopName=" + outShopName
				+ "]";
	}

	public String getIsSubmit() {
		return isSubmit;
	}

	public void setIsSubmit(String isSubmit) {
		this.isSubmit = isSubmit;
	}

	public String getInsideCode() {
		return insideCode;
	}

	public void setInsideCode(String insideCode) {
		this.insideCode = insideCode;
	}

	public String getSettleId() {
		return settleId;
	}

	public void setSettleId(String settleId) {
		this.settleId = settleId;
	}
	
}
