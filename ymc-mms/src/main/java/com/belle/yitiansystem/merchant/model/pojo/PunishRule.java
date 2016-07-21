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
@Table(name = "tbl_sp_supplier_punish_rule")
public class PunishRule  implements java.io.Serializable {
	
	private static final long serialVersionUID = -9037076717725970849L;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	
	/** 供应商编号 */
	@Column(name = "merchant_code")
	private String merchantCode;
	
	/** 发货时效  **/
	@Column(name = "shipment_hour")
	private Long shipmentHour;
	
	/** 超时效邮件通知商家,1:是,0:否 */
	@Column(name = "is_notification")
	private String isNotification;
	
	/** 邮件地址,多个邮箱地址,用;隔开  */
	@Column(name = "emails")
	private String emails;
	
	/** 延时发货处罚,1:按订单,0:按订单金额 */
	@Column(name = "timeout_punish_type")
	private String timeoutPunishType;
	
	/** 延时发货处罚金额 */
	@Column(name = "timeout_punish_money")
	private Double timeoutPunishMoney;
	
	/** 延时发货处罚比率 */
	@Column(name = "timeout_punish_rate")
	private Double timeoutPunishRate;
	
	/** 缺货处罚,1:按订单,0:按订单金额*/
	//@Column(name = "stock_punish_type")
	//private String stockPunishType;
	
	/** 缺货处罚金额 */
	//@Column(name = "stock_punish_money")
	//private Double stockPunishMoney;
	
	/** 缺货处罚比率  */
	//@Column(name = "stock_punish_rate")
	//private Double stockPunishRate;
	
	/**创建时间*/
	@Column(name = "create_time")
	private Timestamp createTime;

	/**更新时间*/
	@Column(name = "update_time")
	private Timestamp updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public Long getShipmentHour() {
		return shipmentHour;
	}

	public void setShipmentHour(Long shipmentHour) {
		this.shipmentHour = shipmentHour;
	}

	public String getIsNotification() {
		return isNotification;
	}

	public void setIsNotification(String isNotification) {
		this.isNotification = isNotification;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public String getTimeoutPunishType() {
		return timeoutPunishType;
	}

	public void setTimeoutPunishType(String timeoutPunishType) {
		this.timeoutPunishType = timeoutPunishType;
	}

	public Double getTimeoutPunishMoney() {
		return timeoutPunishMoney;
	}

	public void setTimeoutPunishMoney(Double timeoutPunishMoney) {
		this.timeoutPunishMoney = timeoutPunishMoney;
	}

	public Double getTimeoutPunishRate() {
		return timeoutPunishRate;
	}

	public void setTimeoutPunishRate(Double timeoutPunishRate) {
		this.timeoutPunishRate = timeoutPunishRate;
	}

	/**
	public String getStockPunishType() {
		return stockPunishType;
	}
	public void setStockPunishType(String stockPunishType) {
		this.stockPunishType = stockPunishType;
	}

	public Double getStockPunishMoney() {
		return stockPunishMoney;
	}

	public void setStockPunishMoney(Double stockPunishMoney) {
		this.stockPunishMoney = stockPunishMoney;
	}

	public Double getStockPunishRate() {
		return stockPunishRate;
	}

	public void setStockPunishRate(Double stockPunishRate) {
		this.stockPunishRate = stockPunishRate;
	}
	**/
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

	@Override
	public String toString() {
		return "PunishRule [id=" + id + ", merchantCode=" + merchantCode + ", shipmentHour=" + shipmentHour
				+ ", isNotification=" + isNotification + ", emails=" + emails + ", timeoutPunishType="
				+ timeoutPunishType + ", timeoutPunishMoney=" + timeoutPunishMoney + ", timeoutPunishRate="
				+ timeoutPunishRate + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}
	

}
