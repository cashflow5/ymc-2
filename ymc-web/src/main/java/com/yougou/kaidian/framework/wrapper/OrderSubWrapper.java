package com.yougou.kaidian.framework.wrapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yougou.ordercenter.model.order.OrderBuyInfo;
import com.yougou.ordercenter.model.order.OrderConsigneeInfo;
import com.yougou.ordercenter.model.order.OrderDetail4sub;
import com.yougou.ordercenter.model.order.OrderSub;
import com.yougou.ordercenter.model.order.OrderSubExpand;

public class OrderSubWrapper extends com.yougou.ordercenter.model.order.OrderSub {

	private static final long serialVersionUID = 77512554798787038L;

	private com.yougou.ordercenter.model.order.OrderSub orderSub;

	public OrderSubWrapper(OrderSub orderSub) {
		this.orderSub = orderSub;
	}

	@Override
	public String getActiveName() {
		return orderSub.getActiveName();
	}

	@Override
	public Double getActivePrefAmount() {
		return orderSub.getActivePrefAmount();
	}

	@Override
	public Double getActualPostage() {
		return orderSub.getActualPostage();
	}

	@Override
	public Integer getApportionStatus() {
		return orderSub.getApportionStatus();
	}

	@Override
	public Map<String, Map<String, List<OrderDetail4sub>>> getAtlsDetails() {
		return orderSub.getAtlsDetails();
	}

	@Override
	public Integer getBaseStatus() {
		return orderSub.getBaseStatus();
	}

	@Override
	public String getBaseStatusName() {
		return orderSub.getBaseStatusName();
	}

	@Override
	public String getBuyId() {
		return orderSub.getBuyId();
	}

	@Override
	public Double getBuyReductionPrefAmount() {
		return orderSub.getBuyReductionPrefAmount();
	}

	@Override
	public String getClientIp() {
		return orderSub.getClientIp();
	}

	@Override
	public String getClientSign() {
		return orderSub.getClientSign();
	}

	@Override
	public String getCod() {
		return orderSub.getCod();
	}

	@Override
	public String getConsigneeId() {
		return orderSub.getConsigneeId();
	}

	@Override
	public String getCouponId() {
		return orderSub.getCouponId();
	}

	@Override
	public Double getCouponPrefAmount() {
		return orderSub.getCouponPrefAmount();
	}

	@Override
	public Double getCouponPrefAmount5() {
		return orderSub.getCouponPrefAmount5();
	}

	@Override
	public Date getCreateTime() {
		return orderSub.getCreateTime();
	}

	@Override
	public Short getDelFlag() {
		return orderSub.getDelFlag();
	}

	@Override
	public Integer getDeliveryStatus() {
		return orderSub.getDeliveryStatus();
	}

	@Override
	public String getDeliveryStatusName() {
		return orderSub.getDeliveryStatusName();
	}

	@Override
	public Double getDiscountAmount() {
		return orderSub.getDiscountAmount();
	}

	@Override
	public String getExpressOrderId() {
		return orderSub.getExpressOrderId();
	}

	@Override
	public Integer getGivingScores() {
		return orderSub.getGivingScores();
	}

	@Override
	public Double getGloblePrefAmount() {
		return orderSub.getGloblePrefAmount();
	}

	@Override
	public Integer getHasRefund() {
		return orderSub.getHasRefund();
	}

	@Override
	public String getId() {
		return orderSub.getId();
	}

	@Override
	public Double getIntegralPrefAmount() {
		return orderSub.getIntegralPrefAmount();
	}

	@Override
	public String getInvoiceId() {
		return orderSub.getInvoiceId();
	}

	@Override
	public Integer getIsBill() {
		return orderSub.getIsBill();
	}

	@Override
	public Integer getIsDerived() {
		return orderSub.getIsDerived();
	}

	@Override
	public Integer getIsException() {
		return orderSub.getIsException();
	}

	@Override
	public Integer getIsPostageFree() {
		return orderSub.getIsPostageFree();
	}

	@Override
	public Short getIsSync() {
		return orderSub.getIsSync();
	}

	@Override
	public List<OrderDetail4sub> getJjgDetails() {
		return orderSub.getJjgDetails();
	}

	@Override
	public String getLockUser() {
		return orderSub.getLockUser();
	}

	@Override
	public String getLogisticsCode() {
		return orderSub.getLogisticsCode();
	}

	@Override
	public String getLogisticsName() {
		return orderSub.getLogisticsName();
	}

	@Override
	public Double getMemberPrefAmount() {
		return orderSub.getMemberPrefAmount();
	}

	@Override
	public String getMessage() {
		return orderSub.getMessage();
	}

	@Override
	public Date getModityDate() {
		return orderSub.getModityDate();
	}

	@Override
	public Long getOffPayTimestamp() {
		return orderSub.getOffPayTimestamp();
	}

	@Override
	public Double getOldAmount() {
		return orderSub.getOldAmount();
	}

	@Override
	public Double getOnlinePayAmount() {
		return orderSub.getOnlinePayAmount();
	}

	@Override
	public String getOnlinePayName() {
		return orderSub.getOnlinePayName();
	}

	@Override
	public Date getOnlinePayTime() {
		return orderSub.getOnlinePayTime();
	}

	@Override
	public String getOnlinePayment() {
		return orderSub.getOnlinePayment();
	}

	@Override
	public OrderBuyInfo getOrderBuyInfo() {
		return orderSub.getOrderBuyInfo();
	}

	@Override
	public OrderConsigneeInfo getOrderConsigneeInfo() {
		return orderSub.getOrderConsigneeInfo();
	}

	@Override
	public List<OrderDetail4sub> getOrderDetail4subs() {
		return orderSub.getOrderDetail4subs();
	}

	@Override
	public Integer getOrderFlag() {
		return orderSub.getOrderFlag();
	}

	@Override
	public String getOrderMainId() {
		return orderSub.getOrderMainId();
	}

	@Override
	public String getOrderMainNo() {
		return orderSub.getOrderMainNo();
	}

	@Override
	public Double getOrderPayTotalAmont() {
		return orderSub.getOrderPayTotalAmont();
	}

	@Override
	public String getOrderSourceNo() {
		return orderSub.getOrderSourceNo();
	}

	@Override
	public OrderSubExpand getOrderSubExpand() {
		return orderSub.getOrderSubExpand();
	}

	@Override
	public String getOrderSubNo() {
		return orderSub.getOrderSubNo();
	}

	@Override
	public Date getOrderSyncTime() {
		return orderSub.getOrderSyncTime();
	}

	@Override
	public Long getOrderTimestamp() {
		return orderSub.getOrderTimestamp();
	}

	@Override
	public String getOriginalOrderNo() {
		return orderSub.getOriginalOrderNo();
	}

	@Override
	public String getOutOrderId() {
		return orderSub.getOutOrderId();
	}

	@Override
	public String getOutShopName() {
		return orderSub.getOutShopName();
	}

	@Override
	public String getPackageNo() {
		return orderSub.getPackageNo();
	}

	@Override
	public Integer getPackageNum() {
		return orderSub.getPackageNum();
	}

	@Override
	public Integer getPayStatus() {
		return orderSub.getPayStatus();
	}

	@Override
	public String getPayStatusName() {
		return orderSub.getPayStatusName();
	}

	@Override
	public Double getPayTotalPrice() {
		return orderSub.getPayTotalPrice();
	}

	@Override
	public String getPayment() {
		return orderSub.getPayment();
	}

	@Override
	public String getPaymentName() {
		return orderSub.getPaymentName();
	}

	@Override
	public Double getPaymentPrefAmount() {
		return orderSub.getPaymentPrefAmount();
	}

	@Override
	public Double getPrintOrderAmount() {
		return orderSub.getPrintOrderAmount();
	}

	@Override
	public Double getPrintPrefAmount() {
		return orderSub.getPrintPrefAmount();
	}

	@Override
	public Integer getProductSendQuantity() {
		return orderSub.getProductSendQuantity();
	}

	@Override
	public Double getProductTotalPrice() {
		return orderSub.getProductTotalPrice();
	}

	@Override
	public Integer getProductTotalQuantity() {
		return orderSub.getProductTotalQuantity();
	}

	@Override
	public Double getProductWeight() {
		return orderSub.getProductWeight();
	}

	@Override
	public Integer getProductWeightUnit() {
		return orderSub.getProductWeightUnit();
	}

	@Override
	public Double getRefundAmount() {
		return orderSub.getRefundAmount();
	}

	@Override
	public Integer getReturnGoodsStatus() {
		return orderSub.getReturnGoodsStatus();
	}

	@Override
	public Long getSendOrPayTimestamp() {
		return orderSub.getSendOrPayTimestamp();
	}

	@Override
	public Short getSettleType() {
		return orderSub.getSettleType();
	}

	@Override
	public Date getShipTime() {
		return orderSub.getShipTime();
	}

	@Override
	public Double getShouldPostage() {
		return orderSub.getShouldPostage();
	}

	@Override
	public Short getSplitFlag() {
		return orderSub.getSplitFlag();
	}

	@Override
	public Integer getSuspendType() {
		return orderSub.getSuspendType();
	}

	@Override
	public String getSuspendTypeName() {
		return orderSub.getSuspendTypeName();
	}

	@Override
	public Double getTempSaleAmount() {
		return orderSub.getTempSaleAmount();
	}

	@Override
	public Double getTotalPrice() {
		return orderSub.getTotalPrice();
	}

	@Override
	public String getUnionTraceCode() {
		return orderSub.getUnionTraceCode();
	}

	@Override
	public Date getVerifyTime() {
		return orderSub.getVerifyTime();
	}

	@Override
	public String getVerifyUser() {
		return orderSub.getVerifyUser();
	}

	@Override
	public String getWarehouseCode() {
		return orderSub.getWarehouseCode();
	}

	@Override
	public String getWarehouseCodeName() {
		return orderSub.getWarehouseCodeName();
	}

	@Override
	public Integer getWarehouseDeliveryStatus() {
		return orderSub.getWarehouseDeliveryStatus();
	}

	@Override
	public void setActiveName(String activeName) {
		orderSub.setActiveName(activeName);
	}

	@Override
	public void setActivePrefAmount(Double activePrefAmount) {
		orderSub.setActivePrefAmount(activePrefAmount);
	}

	@Override
	public void setActualPostage(Double actualPostage) {
		orderSub.setActualPostage(actualPostage);
	}

	@Override
	public void setApportionStatus(Integer apportionStatus) {
		orderSub.setApportionStatus(apportionStatus);
	}

	@Override
	public void setAtlsDetails(Map<String, Map<String, List<OrderDetail4sub>>> atlsDetails) {
		orderSub.setAtlsDetails(atlsDetails);
	}

	@Override
	public void setBaseStatus(Integer baseStatus) {
		orderSub.setBaseStatus(baseStatus);
	}

	@Override
	public void setBaseStatusName(String baseStatusName) {
		orderSub.setBaseStatusName(baseStatusName);
	}

	@Override
	public void setBuyId(String buyId) {
		orderSub.setBuyId(buyId);
	}

	@Override
	public void setBuyReductionPrefAmount(Double buyReductionPrefAmount) {
		orderSub.setBuyReductionPrefAmount(buyReductionPrefAmount);
	}

	@Override
	public void setClientIp(String clientIp) {
		orderSub.setClientIp(clientIp);
	}

	@Override
	public void setClientSign(String clientSign) {
		orderSub.setClientSign(clientSign);
	}

	@Override
	public void setCod(String cod) {
		orderSub.setCod(cod);
	}

	@Override
	public void setConsigneeId(String consigneeId) {
		orderSub.setConsigneeId(consigneeId);
	}

	@Override
	public void setCouponId(String couponId) {
		orderSub.setCouponId(couponId);
	}

	@Override
	public void setCouponPrefAmount(Double couponPrefAmount) {
		orderSub.setCouponPrefAmount(couponPrefAmount);
	}

	@Override
	public void setCouponPrefAmount5(Double couponPrefAmount5) {
		orderSub.setCouponPrefAmount5(couponPrefAmount5);
	}

	@Override
	public void setCreateTime(Date createTime) {
		orderSub.setCreateTime(createTime);
	}

	@Override
	public void setDelFlag(Short delFlag) {
		orderSub.setDelFlag(delFlag);
	}

	@Override
	public void setDeliveryStatus(Integer deliveryStatus) {
		orderSub.setDeliveryStatus(deliveryStatus);
	}

	@Override
	public void setDeliveryStatusName(String deliveryStatusName) {
		orderSub.setDeliveryStatusName(deliveryStatusName);
	}

	@Override
	public void setDiscountAmount(Double discountAmount) {
		orderSub.setDiscountAmount(discountAmount);
	}

	@Override
	public void setExpressOrderId(String expressOrderId) {
		orderSub.setExpressOrderId(expressOrderId);
	}

	@Override
	public void setGivingScores(Integer givingScores) {
		orderSub.setGivingScores(givingScores);
	}

	@Override
	public void setGloblePrefAmount(Double globlePrefAmount) {
		orderSub.setGloblePrefAmount(globlePrefAmount);
	}

	@Override
	public void setHasRefund(Integer hasRefund) {
		orderSub.setHasRefund(hasRefund);
	}

	@Override
	public void setId(String id) {
		orderSub.setId(id);
	}

	@Override
	public void setIntegralPrefAmount(Double integralPrefAmount) {
		orderSub.setIntegralPrefAmount(integralPrefAmount);
	}

	@Override
	public void setInvoiceId(String invoiceId) {
		orderSub.setInvoiceId(invoiceId);
	}

	@Override
	public void setIsBill(Integer isBill) {
		orderSub.setIsBill(isBill);
	}

	@Override
	public void setIsDerived(Integer isDerived) {
		orderSub.setIsDerived(isDerived);
	}

	@Override
	public void setIsException(Integer isException) {
		orderSub.setIsException(isException);
	}

	@Override
	public void setIsPostageFree(Integer isPostageFree) {
		orderSub.setIsPostageFree(isPostageFree);
	}

	@Override
	public void setIsSync(Short isSync) {
		orderSub.setIsSync(isSync);
	}

	@Override
	public void setJjgDetails(List<OrderDetail4sub> jjgDetails) {
		orderSub.setJjgDetails(jjgDetails);
	}

	@Override
	public void setLockUser(String lockUser) {
		orderSub.setLockUser(lockUser);
	}

	@Override
	public void setLogisticsCode(String logisticsCode) {
		orderSub.setLogisticsCode(logisticsCode);
	}

	@Override
	public void setLogisticsName(String logisticsName) {
		orderSub.setLogisticsName(logisticsName);
	}

	@Override
	public void setMemberPrefAmount(Double memberPrefAmount) {
		orderSub.setMemberPrefAmount(memberPrefAmount);
	}

	@Override
	public void setMessage(String message) {
		orderSub.setMessage(message);
	}

	@Override
	public void setModityDate(Date modityDate) {
		orderSub.setModityDate(modityDate);
	}

	@Override
	public void setOffPayTimestamp(Long offPayTimestamp) {
		orderSub.setOffPayTimestamp(offPayTimestamp);
	}

	@Override
	public void setOldAmount(Double oldAmount) {
		orderSub.setOldAmount(oldAmount);
	}

	@Override
	public void setOnlinePayAmount(Double onlinePayAmount) {
		orderSub.setOnlinePayAmount(onlinePayAmount);
	}

	@Override
	public void setOnlinePayName(String onlinePayName) {
		orderSub.setOnlinePayName(onlinePayName);
	}

	@Override
	public void setOnlinePayTime(Date onlinePayTime) {
		orderSub.setOnlinePayTime(onlinePayTime);
	}

	@Override
	public void setOnlinePayment(String onlinePayment) {
		orderSub.setOnlinePayment(onlinePayment);
	}

	@Override
	public void setOrderBuyInfo(OrderBuyInfo orderBuyInfo) {
		orderSub.setOrderBuyInfo(orderBuyInfo);
	}

	@Override
	public void setOrderConsigneeInfo(OrderConsigneeInfo orderConsigneeInfo) {
		orderSub.setOrderConsigneeInfo(orderConsigneeInfo);
	}

	@Override
	public void setOrderDetail4subs(List<OrderDetail4sub> orderDetail4subs) {
		orderSub.setOrderDetail4subs(orderDetail4subs);
	}

	@Override
	public void setOrderFlag(Integer orderFlag) {
		orderSub.setOrderFlag(orderFlag);
	}

	@Override
	public void setOrderMainId(String orderMainId) {
		orderSub.setOrderMainId(orderMainId);
	}

	@Override
	public void setOrderMainNo(String orderMainNo) {
		orderSub.setOrderMainNo(orderMainNo);
	}

	@Override
	public void setOrderPayTotalAmont(Double orderPayTotalAmont) {
		orderSub.setOrderPayTotalAmont(orderPayTotalAmont);
	}

	@Override
	public void setOrderSourceNo(String orderSourceNo) {
		orderSub.setOrderSourceNo(orderSourceNo);
	}

	@Override
	public void setOrderSubExpand(OrderSubExpand orderSubExpand) {
		orderSub.setOrderSubExpand(orderSubExpand);
	}

	@Override
	public void setOrderSubNo(String orderSubNo) {
		orderSub.setOrderSubNo(orderSubNo);
	}

	@Override
	public void setOrderSyncTime(Date orderSyncTime) {
		orderSub.setOrderSyncTime(orderSyncTime);
	}

	@Override
	public void setOrderTimestamp(Long orderTimestamp) {
		orderSub.setOrderTimestamp(orderTimestamp);
	}

	@Override
	public void setOriginalOrderNo(String originalOrderNo) {
		orderSub.setOriginalOrderNo(originalOrderNo);
	}

	@Override
	public void setOutOrderId(String outOrderId) {
		orderSub.setOutOrderId(outOrderId);
	}

	@Override
	public void setOutShopName(String outShopName) {
		orderSub.setOutShopName(outShopName);
	}

	@Override
	public void setPackageNo(String packageNo) {
		orderSub.setPackageNo(packageNo);
	}

	@Override
	public void setPackageNum(Integer packageNum) {
		orderSub.setPackageNum(packageNum);
	}

	@Override
	public void setPayStatus(Integer payStatus) {
		orderSub.setPayStatus(payStatus);
	}

	@Override
	public void setPayStatusName(String payStatusName) {
		orderSub.setPayStatusName(payStatusName);
	}

	@Override
	public void setPayTotalPrice(Double payTotalPrice) {
		orderSub.setPayTotalPrice(payTotalPrice);
	}

	@Override
	public void setPayment(String payment) {
		orderSub.setPayment(payment);
	}

	@Override
	public void setPaymentName(String paymentName) {
		orderSub.setPaymentName(paymentName);
	}

	@Override
	public void setPaymentPrefAmount(Double paymentPrefAmount) {
		orderSub.setPaymentPrefAmount(paymentPrefAmount);
	}

	@Override
	public void setPrintOrderAmount(Double printOrderAmount) {
		orderSub.setPrintOrderAmount(printOrderAmount);
	}

	@Override
	public void setPrintPrefAmount(Double printPrefAmount) {
		orderSub.setPrintPrefAmount(printPrefAmount);
	}

	@Override
	public void setProductSendQuantity(Integer productSendQuantity) {
		orderSub.setProductSendQuantity(productSendQuantity);
	}

	@Override
	public void setProductTotalPrice(Double productTotalPrice) {
		orderSub.setProductTotalPrice(productTotalPrice);
	}

	@Override
	public void setProductTotalQuantity(Integer productTotalQuantity) {
		orderSub.setProductTotalQuantity(productTotalQuantity);
	}

	@Override
	public void setProductWeight(Double productWeight) {
		orderSub.setProductWeight(productWeight);
	}

	@Override
	public void setProductWeightUnit(Integer productWeightUnit) {
		orderSub.setProductWeightUnit(productWeightUnit);
	}

	@Override
	public void setRefundAmount(Double refundAmount) {
		orderSub.setRefundAmount(refundAmount);
	}

	@Override
	public void setReturnGoodsStatus(Integer returnGoodsStatus) {
		orderSub.setReturnGoodsStatus(returnGoodsStatus);
	}

	@Override
	public void setSendOrPayTimestamp(Long sendOrPayTimestamp) {
		orderSub.setSendOrPayTimestamp(sendOrPayTimestamp);
	}

	@Override
	public void setSettleType(Short settleType) {
		orderSub.setSettleType(settleType);
	}

	@Override
	public void setShipTime(Date shipTime) {
		orderSub.setShipTime(shipTime);
	}

	@Override
	public void setShouldPostage(Double shouldPostage) {
		orderSub.setShouldPostage(shouldPostage);
	}

	@Override
	public void setSplitFlag(Short splitFlag) {
		orderSub.setSplitFlag(splitFlag);
	}

	@Override
	public void setSuspendType(Integer suspendType) {
		orderSub.setSuspendType(suspendType);
	}

	@Override
	public void setSuspendTypeName(String suspendTypeName) {
		orderSub.setSuspendTypeName(suspendTypeName);
	}

	@Override
	public void setTempSaleAmount(Double tempSaleAmount) {
		orderSub.setTempSaleAmount(tempSaleAmount);
	}

	@Override
	public void setTotalPrice(Double totalPrice) {
		orderSub.setTotalPrice(totalPrice);
	}

	@Override
	public void setUnionTraceCode(String unionTraceCode) {
		orderSub.setUnionTraceCode(unionTraceCode);
	}

	@Override
	public void setVerifyTime(Date verifyTime) {
		orderSub.setVerifyTime(verifyTime);
	}

	@Override
	public void setVerifyUser(String verifyUser) {
		orderSub.setVerifyUser(verifyUser);
	}

	@Override
	public void setWarehouseCode(String warehouseCode) {
		orderSub.setWarehouseCode(warehouseCode);
	}

	@Override
	public void setWarehouseCodeName(String warehouseCodeName) {
		orderSub.setWarehouseCodeName(warehouseCodeName);
	}

	@Override
	public void setWarehouseDeliveryStatus(Integer warehouseDeliveryStatus) {
		orderSub.setWarehouseDeliveryStatus(warehouseDeliveryStatus);
	}
}
