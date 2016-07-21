package com.yougou.kaidian.framework.wrapper;

import com.yougou.ordercenter.model.order.OrderDetail4sub;
import com.yougou.ordercenter.model.order.OrderSub;

public class OrderDetail4subWrapper extends com.yougou.ordercenter.model.order.OrderDetail4sub {

	private static final long serialVersionUID = -96040168025478822L;
	
	private com.yougou.ordercenter.model.order.OrderDetail4sub orderDetail4sub;
	
	private String thirdPartyCode;
	private String prodUrl;

	public OrderDetail4subWrapper(OrderDetail4sub orderDetail4sub) {
		this.orderDetail4sub = orderDetail4sub;
	}

	@Override
	public String getActiveId() {
		return orderDetail4sub.getActiveId();
	}

	@Override
	public String getActiveName() {
		return orderDetail4sub.getActiveName();
	}

	@Override
	public Double getActivePrefAmount() {
		return orderDetail4sub.getActivePrefAmount();
	}

	@Override
	public Double getActivePrice() {
		return orderDetail4sub.getActivePrice();
	}

	@Override
	public Short getActiveType() {
		return orderDetail4sub.getActiveType();
	}

	@Override
	public String getApplyRefundNo() {
		return orderDetail4sub.getApplyRefundNo();
	}

	@Override
	public Double getBuyReductionPrefAmount() {
		return orderDetail4sub.getBuyReductionPrefAmount();
	}

	@Override
	public String getCommodityCategoriesPath() {
		return orderDetail4sub.getCommodityCategoriesPath();
	}

	@Override
	public String getCommodityId() {
		return orderDetail4sub.getCommodityId();
	}

	@Override
	public String getCommodityImage() {
		return orderDetail4sub.getCommodityImage();
	}

	@Override
	public String getCommodityNo() {
		return orderDetail4sub.getCommodityNo();
	}

	@Override
	public Integer getCommodityNum() {
		return orderDetail4sub.getCommodityNum();
	}

	@Override
	public String getCommoditySpecificationStr() {
		return orderDetail4sub.getCommoditySpecificationStr();
	}

	@Override
	public Integer getCommodityType() {
		return orderDetail4sub.getCommodityType();
	}
	
	public String getCommodityTypeName() {
		switch (getCommodityType()) {
		case 0:
			return "普通商品";
		case 1:
			return "赠品";
		case 2:
			return "换购";
		case 3:
			return "从商品";
		default:
			return "未知";
		}
	}

	@Override
	public Double getCommodityWeight() {
		return orderDetail4sub.getCommodityWeight();
	}

	@Override
	public Double getCouponPrefAmount() {
		return orderDetail4sub.getCouponPrefAmount();
	}

	@Override
	public Double getCouponPrefAmount5() {
		return orderDetail4sub.getCouponPrefAmount5();
	}

	@Override
	public Short getDelFlag() {
		return orderDetail4sub.getDelFlag();
	}

	@Override
	public String getDiscountNo() {
		return orderDetail4sub.getDiscountNo();
	}

	@Override
	public Integer getDiscountNumber() {
		return orderDetail4sub.getDiscountNumber();
	}

	@Override
	public String getGiftInfo() {
		return orderDetail4sub.getGiftInfo();
	}

	@Override
	public Integer getGivingScores() {
		return orderDetail4sub.getGivingScores();
	}

	@Override
	public Double getGloblePrefAmount() {
		return orderDetail4sub.getGloblePrefAmount();
	}

	@Override
	public String getId() {
		return orderDetail4sub.getId();
	}

	@Override
	public Double getIntegralPrefAmount() {
		return orderDetail4sub.getIntegralPrefAmount();
	}

	@Override
	public String getLevelCode() {
		return orderDetail4sub.getLevelCode();
	}

	@Override
	public Double getMemberPrefAmount() {
		return orderDetail4sub.getMemberPrefAmount();
	}

	@Override
	public String getMerchantCode() {
		return orderDetail4sub.getMerchantCode();
	}

	@Override
	public Integer getOrderDistributionSide() {
		return orderDetail4sub.getOrderDistributionSide();
	}

	@Override
	public String getOrderMainNo() {
		return orderDetail4sub.getOrderMainNo();
	}

	@Override
	public OrderSub getOrderSub() {
		return orderDetail4sub.getOrderSub();
	}

	@Override
	public String getOrderSubId() {
		return orderDetail4sub.getOrderSubId();
	}

	@Override
	public String getPackageNo() {
		return orderDetail4sub.getPackageNo();
	}

	@Override
	public Double getPayAmountByCost() {
		return orderDetail4sub.getPayAmountByCost();
	}

	@Override
	public Double getPaymentPrefAmount() {
		return orderDetail4sub.getPaymentPrefAmount();
	}

	@Override
	public Double getPostageCost() {
		return orderDetail4sub.getPostageCost();
	}

	@Override
	public Double getPrintPrice() {
		return orderDetail4sub.getPrintPrice();
	}

	@Override
	public Double getProdDiscountAmount() {
		return orderDetail4sub.getProdDiscountAmount();
	}

	@Override
	public String getProdName() {
		return orderDetail4sub.getProdName();
	}

	@Override
	public String getProdNo() {
		return orderDetail4sub.getProdNo();
	}

	@Override
	public Integer getProdStatus() {
		return orderDetail4sub.getProdStatus();
	}

	@Override
	public Double getProdTotalAmt() {
		return orderDetail4sub.getProdTotalAmt();
	}

	@Override
	public String getProdType() {
		return orderDetail4sub.getProdType();
	}

	@Override
	public Double getProdUnitPrice() {
		return orderDetail4sub.getProdUnitPrice();
	}

	@Override
	public Boolean getQhflag() {
		return orderDetail4sub.getQhflag();
	}

	@Override
	public Double getSalePrice() {
		return orderDetail4sub.getSalePrice();
	}

	@Override
	public Double getShouldPostage() {
		return orderDetail4sub.getShouldPostage();
	}

	@Override
	public String getSiteNo() {
		return orderDetail4sub.getSiteNo();
	}

	@Override
	public Short getSplitFlag() {
		return orderDetail4sub.getSplitFlag();
	}

	@Override
	public String getSubDetailNo() {
		return orderDetail4sub.getSubDetailNo();
	}

	@Override
	public String getTempCommodityUrl() {
		return orderDetail4sub.getTempCommodityUrl();
	}

	@Override
	public Double getTempSaleAmount() {
		return orderDetail4sub.getTempSaleAmount();
	}

	@Override
	public Integer getUsableNum() {
		return orderDetail4sub.getUsableNum();
	}

	@Override
	public void setActiveId(String activeId) {
		orderDetail4sub.setActiveId(activeId);
	}

	@Override
	public void setActiveName(String activeName) {
		orderDetail4sub.setActiveName(activeName);
	}

	@Override
	public void setActivePrefAmount(Double activePrefAmount) {
		orderDetail4sub.setActivePrefAmount(activePrefAmount);
	}

	@Override
	public void setActivePrice(Double activePrice) {
		orderDetail4sub.setActivePrice(activePrice);
	}

	@Override
	public void setActiveType(Short activeType) {
		orderDetail4sub.setActiveType(activeType);
	}

	@Override
	public void setApplyRefundNo(String applyRefundNo) {
		orderDetail4sub.setApplyRefundNo(applyRefundNo);
	}

	@Override
	public void setBuyReductionPrefAmount(Double buyReductionPrefAmount) {
		orderDetail4sub.setBuyReductionPrefAmount(buyReductionPrefAmount);
	}

	@Override
	public void setCommodityCategoriesPath(String commodityCategoriesPath) {
		orderDetail4sub.setCommodityCategoriesPath(commodityCategoriesPath);
	}

	@Override
	public void setCommodityId(String commodityId) {
		orderDetail4sub.setCommodityId(commodityId);
	}

	@Override
	public void setCommodityImage(String commodityImage) {
		orderDetail4sub.setCommodityImage(commodityImage);
	}

	@Override
	public void setCommodityNo(String commodityNo) {
		orderDetail4sub.setCommodityNo(commodityNo);
	}

	@Override
	public void setCommodityNum(Integer commodityNum) {
		orderDetail4sub.setCommodityNum(commodityNum);
	}

	@Override
	public void setCommoditySpecificationStr(String commoditySpecificationStr) {
		orderDetail4sub.setCommoditySpecificationStr(commoditySpecificationStr);
	}

	@Override
	public void setCommodityType(Integer commodityType) {
		orderDetail4sub.setCommodityType(commodityType);
	}

	@Override
	public void setCommodityWeight(Double commodityWeight) {
		orderDetail4sub.setCommodityWeight(commodityWeight);
	}

	@Override
	public void setCouponPrefAmount(Double couponPrefAmount) {
		orderDetail4sub.setCouponPrefAmount(couponPrefAmount);
	}

	@Override
	public void setCouponPrefAmount5(Double couponPrefAmount5) {
		orderDetail4sub.setCouponPrefAmount5(couponPrefAmount5);
	}

	@Override
	public void setDelFlag(Short delFlag) {
		orderDetail4sub.setDelFlag(delFlag);
	}

	@Override
	public void setDiscountNo(String discountNo) {
		orderDetail4sub.setDiscountNo(discountNo);
	}

	@Override
	public void setDiscountNumber(Integer discountNumber) {
		orderDetail4sub.setDiscountNumber(discountNumber);
	}

	@Override
	public void setGiftInfo(String giftInfo) {
		orderDetail4sub.setGiftInfo(giftInfo);
	}

	@Override
	public void setGivingScores(Integer givingScores) {
		orderDetail4sub.setGivingScores(givingScores);
	}

	@Override
	public void setGloblePrefAmount(Double globlePrefAmount) {
		orderDetail4sub.setGloblePrefAmount(globlePrefAmount);
	}

	@Override
	public void setId(String id) {
		orderDetail4sub.setId(id);
	}

	@Override
	public void setIntegralPrefAmount(Double integralPrefAmount) {
		orderDetail4sub.setIntegralPrefAmount(integralPrefAmount);
	}

	@Override
	public void setLevelCode(String levelCode) {
		orderDetail4sub.setLevelCode(levelCode);
	}

	@Override
	public void setMemberPrefAmount(Double memberPrefAmount) {
		orderDetail4sub.setMemberPrefAmount(memberPrefAmount);
	}

	@Override
	public void setMerchantCode(String merchantCode) {
		orderDetail4sub.setMerchantCode(merchantCode);
	}

	@Override
	public void setOrderDistributionSide(Integer orderDistributionSide) {
		orderDetail4sub.setOrderDistributionSide(orderDistributionSide);
	}

	@Override
	public void setOrderMainNo(String orderMainNo) {
		orderDetail4sub.setOrderMainNo(orderMainNo);
	}

	@Override
	public void setOrderSub(OrderSub orderSub) {
		orderDetail4sub.setOrderSub(orderSub);
	}

	@Override
	public void setOrderSubId(String orderSubId) {
		orderDetail4sub.setOrderSubId(orderSubId);
	}

	@Override
	public void setPackageNo(String packageNo) {
		orderDetail4sub.setPackageNo(packageNo);
	}

	@Override
	public void setPayAmountByCost(Double payAmountByCost) {
		orderDetail4sub.setPayAmountByCost(payAmountByCost);
	}

	@Override
	public void setPaymentPrefAmount(Double paymentPrefAmount) {
		orderDetail4sub.setPaymentPrefAmount(paymentPrefAmount);
	}

	@Override
	public void setPostageCost(Double postageCost) {
		orderDetail4sub.setPostageCost(postageCost);
	}

	@Override
	public void setPrintPrice(Double printPrice) {
		orderDetail4sub.setPrintPrice(printPrice);
	}

	@Override
	public void setProdDiscountAmount(Double prodDiscountAmount) {
		orderDetail4sub.setProdDiscountAmount(prodDiscountAmount);
	}

	@Override
	public void setProdName(String prodName) {
		orderDetail4sub.setProdName(prodName);
	}

	@Override
	public void setProdNo(String prodNo) {
		orderDetail4sub.setProdNo(prodNo);
	}

	@Override
	public void setProdStatus(Integer prodStatus) {
		orderDetail4sub.setProdStatus(prodStatus);
	}

	@Override
	public void setProdTotalAmt(Double prodTotalAmt) {
		orderDetail4sub.setProdTotalAmt(prodTotalAmt);
	}

	@Override
	public void setProdType(String prodType) {
		orderDetail4sub.setProdType(prodType);
	}

	@Override
	public void setProdUnitPrice(Double prodUnitPrice) {
		orderDetail4sub.setProdUnitPrice(prodUnitPrice);
	}

	@Override
	public void setQhflag(Boolean qhflag) {
		orderDetail4sub.setQhflag(qhflag);
	}

	@Override
	public void setSalePrice(Double salePrice) {
		orderDetail4sub.setSalePrice(salePrice);
	}

	@Override
	public void setShouldPostage(Double shouldPostage) {
		orderDetail4sub.setShouldPostage(shouldPostage);
	}

	@Override
	public void setSiteNo(String siteNo) {
		orderDetail4sub.setSiteNo(siteNo);
	}

	@Override
	public void setSplitFlag(Short splitFlag) {
		orderDetail4sub.setSplitFlag(splitFlag);
	}

	@Override
	public void setSubDetailNo(String subDetailNo) {
		orderDetail4sub.setSubDetailNo(subDetailNo);
	}

	@Override
	public void setTempCommodityUrl(String tempCommodityUrl) {
		orderDetail4sub.setTempCommodityUrl(tempCommodityUrl);
	}

	@Override
	public void setTempSaleAmount(Double tempSaleAmount) {
		orderDetail4sub.setTempSaleAmount(tempSaleAmount);
	}

	@Override
	public void setUsableNum(Integer usableNum) {
		orderDetail4sub.setUsableNum(usableNum);
	}

	public String getThirdPartyCode() {
		return thirdPartyCode;
	}

	public void setThirdPartyCode(String thirdPartyCode) {
		this.thirdPartyCode = thirdPartyCode;
	}

	public String getProdUrl() {
		return prodUrl;
	}

	public void setProdUrl(String prodUrl) {
		this.prodUrl = prodUrl;
	}
}
