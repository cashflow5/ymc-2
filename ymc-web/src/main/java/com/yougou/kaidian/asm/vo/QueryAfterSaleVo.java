package com.yougou.kaidian.asm.vo;

public class QueryAfterSaleVo {
	//订单号
	private String orderSubNo;
	//外部订单号
	private String outOrderId;
	//售后类型(退货、换货)
	private String saleType;
	//售后申请单号
	private String applyNo;
	//退换货寄回快递单号
	private String expressNo;
	/*
	 * 售后申请单状态()
	 * SALE_APPLY-未审核,SALE_COMFIRM -已审核,SALE_REFUSE-拒绝申请,
	 * SALE_NOT_GOODS-未收到货,SALE_RECEIVE_GOODS-收到退货,SALE_CALL_BACK-打回,
	 * PART_SALE_QC-部分质检,SALE_QC-已质检,SALE_SEND_GOODS-已发货,
	 * SALE_REFUND_APPLY-退款申请中,SALE_REFUND_COMFIRM-退款审核通过,
	 * SALE_REFUND_REFUSE-退款拒绝,SALE_REFUND_YES-已退款,SALE_SUPPLY_YES-已补款,
	 * SALE_SUPPLY_FAIL-补款拒绝,SALE_SUCCESS-已完成,SALE_CANCEL-作废 
	 */
	private String status;
	
	//申请时间
	private String applyTimeStart;
	private String applyTimeEnd;
	
	//收货人
	private String consignee;
	//收货人手机
	private String mobilePhone;
	
	//质检时间
	private String qcTimeStart;
	private String qcTimeEnd;
	
	private String merchantCode;
	
	public String getOrderSubNo() {
		return orderSubNo;
	}
	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}
	
	public String getOutOrderId() {
		return outOrderId;
	}
	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApplyTimeStart() {
		return applyTimeStart;
	}
	public void setApplyTimeStart(String applyTimeStart) {
		this.applyTimeStart = applyTimeStart;
	}
	public String getApplyTimeEnd() {
		return applyTimeEnd;
	}
	public void setApplyTimeEnd(String applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getQcTimeStart() {
		return qcTimeStart;
	}
	public void setQcTimeStart(String qcTimeStart) {
		this.qcTimeStart = qcTimeStart;
	}
	public String getQcTimeEnd() {
		return qcTimeEnd;
	}
	public void setQcTimeEnd(String qcTimeEnd) {
		this.qcTimeEnd = qcTimeEnd;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

}
