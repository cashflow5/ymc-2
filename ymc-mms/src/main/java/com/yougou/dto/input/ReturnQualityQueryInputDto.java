package com.yougou.dto.input;

import java.util.Date;


public class ReturnQualityQueryInputDto extends PageableInputDto {

	private static final long serialVersionUID = -7374776236398715735L;
	
	//退货单号
	private String returnId;
	
	/** 售后申请单号 */
	private String applyNo;
	
	/** 申请单状态 */
	private String applyStatus;
	
	/** 订单号 */
	private String orderNo;
	
	/** 外部订单号 */
	private String out_order_id;

	private Date applyStartTime;
	
	private Date applyEndTime;

	public String getReturnId() {
		return returnId;
	}

	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOut_order_id() {
		return out_order_id;
	}

	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}

	public Date getApplyStartTime() {
		return applyStartTime;
	}

	public void setApplyStartTime(Date applyStartTime) {
		this.applyStartTime = applyStartTime;
	}

	public Date getApplyEndTime() {
		return applyEndTime;
	}

	public void setApplyEndTime(Date applyEndTime) {
		this.applyEndTime = applyEndTime;
	}
	
	
}
