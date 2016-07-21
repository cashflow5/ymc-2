package com.yougou.kaidian.framework.wrapper;

import java.util.Date;

import com.yougou.ordercenter.model.order.OrderSubExpand;

public class OrderSubExpandWrapper extends com.yougou.ordercenter.model.order.OrderSubExpand {

	private static final long serialVersionUID = -5405255443225187007L;

	private com.yougou.ordercenter.model.order.OrderSubExpand orderSubExpand;

	public OrderSubExpandWrapper(OrderSubExpand orderSubExpand) {
		this.orderSubExpand = orderSubExpand;
	}

	@Override
	public Date getBackorderDate() {
		return orderSubExpand.getBackorderDate();
	}

	@Override
	public String getBackupNo() {
		return orderSubExpand.getBackupNo();
	}

	@Override
	public String getCancelReason() {
		return orderSubExpand.getCancelReason();
	}

	@Override
	public Integer getCancelType() {
		return orderSubExpand.getCancelType();
	}

	@Override
	public String getChannelNo() {
		return orderSubExpand.getChannelNo();
	}

	@Override
	public Date getExportedDate() {
		return orderSubExpand.getExportedDate();
	}

	@Override
	public String getId() {
		return orderSubExpand.getId();
	}

	@Override
	public Date getInsertTime() {
		return orderSubExpand.getInsertTime();
	}

	@Override
	public int getIsPartDeliver() {
		return orderSubExpand.getIsPartDeliver();
	}

	@Override
	public String getMerchantCode() {
		return orderSubExpand.getMerchantCode();
	}

	@Override
	public Integer getOrderDistributionSide() {
		return orderSubExpand.getOrderDistributionSide();
	}

	@Override
	public Integer getOrderExportedStatus() {
		return orderSubExpand.getOrderExportedStatus();
	}

	@Override
	public int getOrderPrintedStatus() {
		return orderSubExpand.getOrderPrintedStatus();
	}

	@Override
	public String getOrderSubId() {
		return orderSubExpand.getOrderSubId();
	}

	@Override
	public int getPostageOnDelivery() {
		return orderSubExpand.getPostageOnDelivery();
	}

	@Override
	public int getPrintLogisticslistCount() {
		return orderSubExpand.getPrintLogisticslistCount();
	}

	@Override
	public int getPrintShoppinglistCount() {
		return orderSubExpand.getPrintShoppinglistCount();
	}

	@Override
	public Date getRejectTime() {
		return orderSubExpand.getRejectTime();
	}

	@Override
	public Date getStockingDate() {
		return orderSubExpand.getStockingDate();
	}

	@Override
	public void setBackorderDate(Date backorderDate) {
		orderSubExpand.setBackorderDate(backorderDate);
	}

	@Override
	public void setBackupNo(String backupNo) {
		orderSubExpand.setBackupNo(backupNo);
	}

	@Override
	public void setCancelReason(String cancelReason) {
		orderSubExpand.setCancelReason(cancelReason);
	}

	@Override
	public void setCancelType(Integer cancelType) {
		orderSubExpand.setCancelType(cancelType);
	}

	@Override
	public void setChannelNo(String channelNo) {
		orderSubExpand.setChannelNo(channelNo);
	}

	@Override
	public void setExportedDate(Date exportedDate) {
		orderSubExpand.setExportedDate(exportedDate);
	}

	@Override
	public void setId(String id) {
		orderSubExpand.setId(id);
	}

	@Override
	public void setInsertTime(Date insertTime) {
		orderSubExpand.setInsertTime(insertTime);
	}

	@Override
	public void setIsPartDeliver(int isPartDeliver) {
		orderSubExpand.setIsPartDeliver(isPartDeliver);
	}

	@Override
	public void setMerchantCode(String merchantCode) {
		orderSubExpand.setMerchantCode(merchantCode);
	}

	@Override
	public void setOrderDistributionSide(Integer orderDistributionSide) {
		orderSubExpand.setOrderDistributionSide(orderDistributionSide);
	}

	@Override
	public void setOrderExportedStatus(Integer orderExportedStatus) {
		orderSubExpand.setOrderExportedStatus(orderExportedStatus);
	}

	@Override
	public void setOrderPrintedStatus(int orderPrintedStatus) {
		orderSubExpand.setOrderPrintedStatus(orderPrintedStatus);
	}

	@Override
	public void setOrderSubId(String orderSubId) {
		orderSubExpand.setOrderSubId(orderSubId);
	}

	@Override
	public void setPostageOnDelivery(int postageOnDelivery) {
		orderSubExpand.setPostageOnDelivery(postageOnDelivery);
	}

	@Override
	public void setPrintLogisticslistCount(int printLogisticslistCount) {
		orderSubExpand.setPrintLogisticslistCount(printLogisticslistCount);
	}

	@Override
	public void setPrintShoppinglistCount(int printShoppinglistCount) {
		orderSubExpand.setPrintShoppinglistCount(printShoppinglistCount);
	}

	@Override
	public void setRejectTime(Date rejectTime) {
		orderSubExpand.setRejectTime(rejectTime);
	}

	@Override
	public void setStockingDate(Date stockingDate) {
		orderSubExpand.setStockingDate(stockingDate);
	}
}
