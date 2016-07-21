package com.yougou.kaidian.order.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.yougou.ordercenter.vo.merchant.input.OrderPrintInputDto;

public class MerchantOrderPrintInputDto extends OrderPrintInputDto{

	private static final long serialVersionUID = 1L;
	
	private Integer orderStatus;
	/**
	 * 收货人手机
	 * 
	 * @return
	 */
	private String consigneeMobile;
	
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getConsigneeMobile() {
		return consigneeMobile;
	}
	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
