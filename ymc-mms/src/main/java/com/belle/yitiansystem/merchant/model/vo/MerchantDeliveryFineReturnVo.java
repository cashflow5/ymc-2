package com.belle.yitiansystem.merchant.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *创建人:ZhangZaiYun
 *创建时间:2013-6-13
 *商家处罚返回Vo
 *
 */
public class MerchantDeliveryFineReturnVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 结算单号
	 */
	private String balanceBillNumber;

	/**
	 * 结算周期(开始时间)
	 */
	private Date balanceStartDate;
	/**
	 * 结算周期(结束时间)
	 */
	private Date balanceEndDate;
	/**
	 * 扣款状态
	 */
	private Integer deductStatus;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBalanceBillNumber() {
		return balanceBillNumber;
	}

	public void setBalanceBillNumber(String balanceBillNumber) {
		this.balanceBillNumber = balanceBillNumber;
	}

	public Date getBalanceStartDate() {
		return balanceStartDate;
	}

	public void setBalanceStartDate(Date balanceStartDate) {
		this.balanceStartDate = balanceStartDate;
	}

	public Date getBalanceEndDate() {
		return balanceEndDate;
	}

	public void setBalanceEndDate(Date balanceEndDate) {
		this.balanceEndDate = balanceEndDate;
	}

	public Integer getDeductStatus() {
		return deductStatus;
	}

	public void setDeductStatus(Integer deductStatus) {
		this.deductStatus = deductStatus;
	}

	@Override
	public String toString() {
		return "deliveryFineReturnVo.orderNo:"+orderNo+
				"deliveryFineReturnVo.balanceBillNumber:"+balanceBillNumber+
				"deliveryFineReturnVo.balanceStartDate:"+balanceStartDate+
				"deliveryFineReturnVo.balanceEndDate:"+balanceEndDate+
				"deliveryFineReturnVo.deductStatus:"+deductStatus;
	}
	
}
