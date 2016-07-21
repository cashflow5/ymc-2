package com.belle.yitiansystem.merchant.model.vo;  

import java.io.Serializable;
import java.util.Date;

public class PunishSettleVo implements Serializable {
	/** 
	 * serialVersionUID:序列化
	 * @since JDK 1.6 
	 */ 
	private static final long serialVersionUID = -6407708361042499566L;
	/**登记单号*/
	private String registNum;
	/**状态  1新建  2已审核  3已结算 4申请修改（取消之后的状态） 5财务作废*/
	private String status;
	/**供应商编码*/
	private String supplierCode;
	/**供应商名称*/
	private String supplier;
	/**登记人*/
	private String registrant;
	/**审核人*/
	private String audit;
	/**登记起始时间*/
	private Date registTimeStart;
	/**登记结束时间*/
	private Date registTimeEnd;
	/**结算单号*/
	private String settleNo;
	/**扣款类型*/
	private String deductType;
	/**是否删除 1删除 0正常  暂未有该状态的更新的接口，查询使用到该状态，默认为0*/
	private String deleteFlag;
	
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
	public String getRegistrant() {
		return registrant;
	}
	public void setRegistrant(String registrant) {
		this.registrant = registrant;
	}
	public String getAudit() {
		return audit;
	}
	public void setAudit(String audit) {
		this.audit = audit;
	}
	public Date getRegistTimeStart() {
		return registTimeStart;
	}
	public void setRegistTimeStart(Date registTimeStart) {
		this.registTimeStart = registTimeStart;
	}
	public Date getRegistTimeEnd() {
		return registTimeEnd;
	}
	public void setRegistTimeEnd(Date registTimeEnd) {
		this.registTimeEnd = registTimeEnd;
	}
	public String getSettleNo() {
		return settleNo;
	}
	public void setSettleNo(String settleNo) {
		this.settleNo = settleNo;
	}
	public String getDeductType() {
		return deductType;
	}
	public void setDeductType(String deductType) {
		this.deductType = deductType;
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
