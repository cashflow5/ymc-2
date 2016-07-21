/**
 * 
 */
package com.yougou.kaidian.asm.vo;

/**
 * @author huang.tao
 *
 */
public class QualityQueryVo {
	
	/** 商家编码 */
	private String merchantCode;
	
	/** 子订单号 */
	private String orderSubNo;
	
	/** 质检状态(已作废'、'已确认'、'待确认') */
	private String statusName;
	
	/** 原始订单号 */
	private String outOrderId;
	/** 快递单号 */
	private String expressCode;
	/** 快递公司 */
	private String expressName;
	/** 货品编码 */
	private String qaProductNo;
	/** 货品条码 */
	private String qaInsideCode;
	/** 款色编码 */
	private String supplierCode;
	/** 货品 名称 */
	private String commodityName;
	
	private String userName;
	/** 用户手机号码 */
	private String mobilePhone;
	/** 质检类型(拒收、退货、换货) */
	private String qualityType;
	
	/** 质检时间 */
	private String qaTimeStart;
	private String qaTimeEnd;
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getOrderSubNo() {
		return orderSubNo;
	}
	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getOutOrderId() {
		return outOrderId;
	}
	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}
	public String getExpressCode() {
		return expressCode;
	}
	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public String getQaProductNo() {
		return qaProductNo;
	}
	public void setQaProductNo(String qaProductNo) {
		this.qaProductNo = qaProductNo;
	}
	public String getQaInsideCode() {
		return qaInsideCode;
	}
	public void setQaInsideCode(String qaInsideCode) {
		this.qaInsideCode = qaInsideCode;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getQualityType() {
		return qualityType;
	}
	public void setQualityType(String qualityType) {
		this.qualityType = qualityType;
	}
	public String getQaTimeStart() {
		return qaTimeStart;
	}
	public void setQaTimeStart(String qaTimeStart) {
		this.qaTimeStart = qaTimeStart;
	}
	public String getQaTimeEnd() {
		return qaTimeEnd;
	}
	public void setQaTimeEnd(String qaTimeEnd) {
		this.qaTimeEnd = qaTimeEnd;
	}
}
