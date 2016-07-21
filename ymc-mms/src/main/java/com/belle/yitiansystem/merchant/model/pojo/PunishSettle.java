package com.belle.yitiansystem.merchant.model.pojo;  

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ClassName: PunishSettle
 * Desc: 商家处罚结算
 * date: 2014-12-26 下午2:27:12
 * @author li.n1 
 * @since JDK 1.6
 */
@Entity
@Table(name="tbl_sp_supplier_punish_settle")
public class PunishSettle implements Serializable {
	/** 
	 * serialVersionUID:序列号
	 * @since JDK 1.6 
	 */ 
	private static final long serialVersionUID = 1L;
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	/**登记单号*/
	@Column(name="regist_num")
	private String registNum;
	/**状态  1新建  2已审核  3已结算 4申请修改 5财务作废*/
	/**
	 * 最新修改为：
	 * 状态  0新建 1已审核  2结算中  3已结算  4已取消 
	 */
	@Column(name="status")
	private String status;
	/**供应商编码*/
	@Column(name="supplier_code")
	private String supplierCode;
	/**供应商名称*/
	@Column(name="supplier")
	private String supplier;
	/**扣款金额*/
	@Column(name="deduct_money")
	private double deductMoney;
	/**扣款类型  1超时效  0缺货*/
	@Column(name="deduct_type")
	private String deductType;
	/**登记人*/
	@Column(name="registrant")
	private String registrant;
	/**登记时间*/
	@Column(name="regist_time")
	private Date registTime;
	/**审核人*/
	@Column(name="audit")
	private String audit;
	/**审核时间*/
	@Column(name="audit_time")
	private Date auditTime;
	/**结算单号*/
	@Column(name="settle_no")
	private String settleNo;
	/**财务结算日期*/
	@Column(name="settle_time")
	private Date settleTime;
	/**结算周期起始*/
	@Column(name="settle_start")
	private Date settleStart;
	/**结算周期结束*/
	@Column(name="settle_end")
	private Date settleEnd;
	/**结算订单数*/
	@Column(name="settle_order_num")
	private int settleOrderNum;
	/**是否删除 1删除 0正常*/
	@Column(name="delete_flag")
	private String deleteFlag;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRegistNum() {
		return registNum;
	}
	public void setRegistNum(String registNum) {
		this.registNum = registNum;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public double getDeductMoney() {
		return deductMoney;
	}
	public void setDeductMoney(double deductMoney) {
		this.deductMoney = deductMoney;
	}
	public String getDeductType() {
		return deductType;
	}
	public void setDeductType(String deductType) {
		this.deductType = deductType;
	}
	public String getRegistrant() {
		return registrant;
	}
	public void setRegistrant(String registrant) {
		this.registrant = registrant;
	}
	public Date getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}
	public String getAudit() {
		return audit;
	}
	public void setAudit(String audit) {
		this.audit = audit;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getSettleNo() {
		return settleNo;
	}
	public void setSettleNo(String settleNo) {
		this.settleNo = settleNo;
	}
	public Date getSettleTime() {
		return settleTime;
	}
	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}
	public Date getSettleStart() {
		return settleStart;
	}
	public void setSettleStart(Date settleStart) {
		this.settleStart = settleStart;
	}
	public Date getSettleEnd() {
		return settleEnd;
	}
	public void setSettleEnd(Date settleEnd) {
		this.settleEnd = settleEnd;
	}
	public int getSettleOrderNum() {
		return settleOrderNum;
	}
	public void setSettleOrderNum(int settleOrderNum) {
		this.settleOrderNum = settleOrderNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
}
