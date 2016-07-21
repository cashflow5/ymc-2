package com.yougou.kaidian.order.model;

import com.yougou.ordercenter.vo.merchant.output.QueryOrderPrintOutputDto;

public class MerchantQueryOrderPrintOutputDto extends QueryOrderPrintOutputDto{

	private static final long serialVersionUID = 1L;
	
	private int orderStatus;//新订单状态
	private int isPaid;//新支付状态
	// Add on 2015-08-24
	private int isException;// 是否异常，何种异常(异常解释见常量类：OrderConstant.java)
	// 商家备注颜色
	private String markColor;
	// 商家备注
	private String markNote;
	
	public int getIsException() {
		return isException;
	}
	public void setIsException(int isException) {
		this.isException = isException;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public int getIsPaid() {
		return isPaid;
	}
	public void setIsPaid(int isPaid) {
		this.isPaid = isPaid;
	}
	public String getMarkColor() {
		return markColor;
	}
	public String getMarkNote() {
		return markNote;
	}
	public void setMarkColor(String markColor) {
		this.markColor = markColor;
	}
	public void setMarkNote(String markNote) {
		this.markNote = markNote;
	}
}
