package com.yougou.kaidian.order.model;

import java.util.Date;
import java.util.List;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-6-20 下午1:28:21
 * @version 0.1.0
 * @copyright yougou.com
 */
public class OrderSub4Print {
	private Double discountAmount;
	private Double orderPayTotalAmont;
	private Double payTotalPrice;
	private Double totalPrice;
	private String orderSubNo1;
	private String orderSubNo2;
	private String orderSubNo3;
	private String orderSourceNo;
	private String orderSubNo;
	private String hotLine;
	private Date createTime;
	private Integer productTotalQuantity;
	private List<com.yougou.ordercenter.model.order.OrderDetail4sub> orderDetail4subs;
	private com.yougou.ordercenter.model.order.OrderConsigneeInfo orderConsigneeInfo;
	
	public String getOrderSubNo() {
		return orderSubNo;
	}
	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}
	public String getOrderSourceNo() {
		return orderSourceNo;
	}
	public void setOrderSourceNo(String orderSourceNo) {
		this.orderSourceNo = orderSourceNo;
	}
	
	public String getHotLine() {
		return hotLine;
	}
	public void setHotLine(String hotLine) {
		this.hotLine = hotLine;
	}

	public String getOrderSubNo1() {
		return orderSubNo1;
	}
	public void setOrderSubNo1(String orderSubNo1) {
		this.orderSubNo1 = orderSubNo1;
	}
	public String getOrderSubNo2() {
		return orderSubNo2;
	}
	public void setOrderSubNo2(String orderSubNo2) {
		this.orderSubNo2 = orderSubNo2;
	}
	public String getOrderSubNo3() {
		return orderSubNo3;
	}
	public void setOrderSubNo3(String orderSubNo3) {
		this.orderSubNo3 = orderSubNo3;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getProductTotalQuantity() {
		return productTotalQuantity;
	}
	public void setProductTotalQuantity(Integer productTotalQuantity) {
		this.productTotalQuantity = productTotalQuantity;
	}

	public List<com.yougou.ordercenter.model.order.OrderDetail4sub> getOrderDetail4subs() {
		return orderDetail4subs;
	}
	public void setOrderDetail4subs(
			List<com.yougou.ordercenter.model.order.OrderDetail4sub> orderDetail4subs) {
		this.orderDetail4subs = orderDetail4subs;
	}
	public com.yougou.ordercenter.model.order.OrderConsigneeInfo getOrderConsigneeInfo() {
		return orderConsigneeInfo;
	}
	public void setOrderConsigneeInfo(
			com.yougou.ordercenter.model.order.OrderConsigneeInfo orderConsigneeInfo) {
		this.orderConsigneeInfo = orderConsigneeInfo;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Double getPayTotalPrice() {
		return payTotalPrice;
	}
	public void setPayTotalPrice(Double payTotalPrice) {
		this.payTotalPrice = payTotalPrice;
	}
	public Double getOrderPayTotalAmont() {
		return orderPayTotalAmont;
	}
	public void setOrderPayTotalAmont(Double orderPayTotalAmont) {
		this.orderPayTotalAmont = orderPayTotalAmont;
	}

}
