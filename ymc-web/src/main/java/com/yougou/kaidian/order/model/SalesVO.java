package com.yougou.kaidian.order.model;

import java.math.BigDecimal;

/**
 * @directions:销售查询 vo
 * @author： daixiaowei
 * @create： 2012-3-14 下午07:33:47
 * @history：
 * @version:
 */
public class SalesVO {

	/**
	 * 商品id
	 */
	private String commodityId;
	/**
	 * 商家货品条码 等价于 商家货品编码
	 */
	private String thirdPartyCode;
	/**
	 * 优购货品条码
	 */
	private String insideCode;
	/**
	 * 商品编码
	 */
	private String no;
	/**
	 * 商家款色编码
	 */
	private String supplierCode;
	/**
	 * 货品编码
	 */
	private String productNo;
	/**
	 * 商品名称
	 */
	private String commodityName;
	/**
	 * 销售数量
	 */
	private String salesCount;
	/**
	 * 开始时间 下单时间
	 */
	private String timeStart;
	/**
	 * 结束时间 下单时间
	 */
	private String timeEnd;
	/**
	 * 开始发货时间
	 */
	private String shipTimeStart;
	/**
	 * 结束发货时间
	 */
	private String shipTimeEnd;
	/**
	 * 商家编码
	 */
	private String merchantCode;
	/**
	 * 订单号	
	 */
	private String orderSubNo;
	
	/**
	 * 快递单号S
	 */
	private String expressCodes;
	
	/**
	 * 商品规格
	 */
	private String commoditySpecification;
	
	/**
	 * 发货数量
	 */
	private int outQuantity;
	
	/**
	 * 拒收数量
	 */
	private int rejectionQuantity;
	
	/**
	 * 退换货数量
	 */
	private int returnQuantity;
	
	/**
	 * 市场价
	 */
	private double salePrice;
	
	/**
	 * 销售价
	 */
	private double activePrice;
	
	/**
	 * 优惠券方案
	 */
	private String activeName;
	
	/**
	 * 优惠总金额
	 */
	private double prefTotalAmount;
	
	/**
	 * 货款金额
	 */
	private double productAmount;
	
	/**
	 * 活动优惠金额
	 */
	private double activePrefAmount;
	
	/**
	 * 优惠券金额
	 */
	private double couponPrefAmount;
	
	/**
	 * 礼品卡金额
	 */
	private double couponPrefAmount5;
	
	/**
	 * 下单立减金额
	 */
	private double buyReductionPrefAmount;

	/**
	 * 订单ID
	 */
	private String orderSubId;
	
	
	// 查询总金额
	private BigDecimal allPageTotalAmount;
	// 每页总金额
	private BigDecimal onePageTotalAmount;
	

	public String getShipTimeStart() {
		return shipTimeStart;
	}

	public void setShipTimeStart(String shipTimeStart) {
		this.shipTimeStart = shipTimeStart;
	}

	public String getShipTimeEnd() {
		return shipTimeEnd;
	}

	public void setShipTimeEnd(String shipTimeEnd) {
		this.shipTimeEnd = shipTimeEnd;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getInsideCode() {
		return insideCode;
	}

	public void setInsideCode(String insideCode) {
		this.insideCode = insideCode;
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public String getThirdPartyCode() {
		return thirdPartyCode;
	}

	public void setThirdPartyCode(String thirdPartyCode) {
		this.thirdPartyCode = thirdPartyCode;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getSalesCount() {
		return salesCount;
	}

	public void setSalesCount(String salesCount) {
		this.salesCount = salesCount;
	}

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

	public String getExpressCodes() {
		return expressCodes;
	}

	public void setExpressCodes(String expressCodes) {
		this.expressCodes = expressCodes;
	}

	public String getCommoditySpecification() {
		return commoditySpecification;
	}

	public void setCommoditySpecification(String commoditySpecification) {
		this.commoditySpecification = commoditySpecification;
	}

	public int getOutQuantity() {
		return outQuantity;
	}

	public void setOutQuantity(int outQuantity) {
		this.outQuantity = outQuantity;
	}

	public int getRejectionQuantity() {
		return rejectionQuantity;
	}

	public void setRejectionQuantity(int rejectionQuantity) {
		this.rejectionQuantity = rejectionQuantity;
	}

	public int getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(int returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public double getActivePrice() {
		return activePrice;
	}

	public void setActivePrice(double activePrice) {
		this.activePrice = activePrice;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public double getPrefTotalAmount() {
		return prefTotalAmount;
	}

	public void setPrefTotalAmount(double prefTotalAmount) {
		this.prefTotalAmount = prefTotalAmount;
	}

	public double getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(double productAmount) {
		this.productAmount = productAmount;
	}

	public double getActivePrefAmount() {
		return activePrefAmount;
	}

	public void setActivePrefAmount(double activePrefAmount) {
		this.activePrefAmount = activePrefAmount;
	}

	public double getCouponPrefAmount() {
		return couponPrefAmount;
	}

	public void setCouponPrefAmount(double couponPrefAmount) {
		this.couponPrefAmount = couponPrefAmount;
	}

	public double getCouponPrefAmount5() {
		return couponPrefAmount5;
	}

	public void setCouponPrefAmount5(double couponPrefAmount5) {
		this.couponPrefAmount5 = couponPrefAmount5;
	}

	public double getBuyReductionPrefAmount() {
		return buyReductionPrefAmount;
	}

	public void setBuyReductionPrefAmount(double buyReductionPrefAmount) {
		this.buyReductionPrefAmount = buyReductionPrefAmount;
	}

	public String getOrderSubId() {
		return orderSubId;
	}

	public void setOrderSubId(String orderSubId) {
		this.orderSubId = orderSubId;
	}

	public BigDecimal getAllPageTotalAmount() {
		return allPageTotalAmount;
	}

	public void setAllPageTotalAmount(BigDecimal allPageTotalAmount) {
		this.allPageTotalAmount = allPageTotalAmount;
	}

	public BigDecimal getOnePageTotalAmount() {
		return onePageTotalAmount;
	}

	public void setOnePageTotalAmount(BigDecimal onePageTotalAmount) {
		this.onePageTotalAmount = onePageTotalAmount;
	}

}
