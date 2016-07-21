package com.yougou.kaidian.bi.vo;  

import java.io.Serializable;
/**
 * ClassName: CommodityIndexVo
 * Desc: 商品分析指标
 * date: 2015-8-27 上午9:54:12
 * @author li.n1 
 * @since JDK 1.6
 */
public class CommodityIndexVo implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public String toString() {
		return "CommodityIndexVo [merchantCode=" + merchantCode
				+ ", commodityNo=" + commodityNo + ", visitCount=" + visitCount
				+ ", pageView=" + pageView + ", converseRate=" + converseRate
				+ ", saleCommodityNum=" + saleCommodityNum + ", commodityNum="
				+ commodityNum + ", saleTotalAmount=" + saleTotalAmount
				+ ", prodTotalAmt=" + prodTotalAmt + ", sendCommodityNum="
				+ sendCommodityNum + ", sendCommodityAmt=" + sendCommodityAmt
				+ ", sendRate=" + sendRate + ", storeNum=" + storeNum
				+ ", saleAvgAmount=" + saleAvgAmount + ", sendAvgAmount="
				+ sendAvgAmount + ", payAvgAmount=" + payAvgAmount
				+ ", commodityTimes=" + commodityTimes + ", colectedNum="
				+ colectedNum + ", rejectCommodityNum=" + rejectCommodityNum
				+ ", rejectCommodityAmt=" + rejectCommodityAmt
				+ ", rejectRate=" + rejectRate + ", newCommodityCount="
				+ newCommodityCount + ", noSaleDay=" + noSaleDay
				+ ", onLineDay=" + onLineDay + ", commodityLink="
				+ commodityLink + ", commodityPicUrl=" + commodityPicUrl
				+ ", commodityStatus=" + commodityStatus + "]";
	}
	
	/**
	 * 商家编码
	 */
	private String merchantCode;
	/**
	 * 商品编码
	 */
	private String commodityNo;
	/**
	 * 商品访次
	 */
	private int visitCount;
	/**
	 * 商品浏览量
	 */
	private int pageView;
	/**
	 * 转化率
	 * 单品转化率（单品转化率=商品收订件数/商品访次）
	 * （订单转化率=收订订单数/商家所有商品访次求和）
	 */
	private double converseRate;
	/**
	 * 收订件数
	 */
	private int saleCommodityNum;
	/**
	 * 支付件数
	 */
	private int commodityNum;
	/**
	 * 收订金额
	 */
	private double saleTotalAmount;
	/**
	 * 支付金额（商品总金额，排除了优惠，与购物车保持一致）
	 */
	private double prodTotalAmt;
	/**
	 * 发货件数
	 */
	private int sendCommodityNum;
	/**
	 * 发货金额
	 */
	private double sendCommodityAmt;
	/**
	 * 发货率 = 发货件数/支付件数
	 */
	private double sendRate;
	/**
	 * 库存(可售库存)
	 */
	private int storeNum;
	/**
	 * 收订均价=收订金额/收订件数
	 */
	private double saleAvgAmount;
	/**
	 * 发货均价=已发货金额/已发货件数
	 */
	private double sendAvgAmount;
	/**
	 * 支付均价=支付金额/支付件数
	 */
	private double payAvgAmount;
	/**
	 * 评论次数
	 */
	private int commodityTimes;
	/**
	 * 加车次数
	 */
	private int colectedNum;
	/**
	 * 退货拒收数
	 */
	private int rejectCommodityNum;
	/**
	 * 退货拒收额
	 */
	private int rejectCommodityAmt;
	/**
	 * 退货拒收率 = 退货拒收数/发货件数
	 */
	private double rejectRate;
	/**
	 * 新上架商品数
	 */
	private int newCommodityCount;
	/**
	 * 持续零收订天数
	 */
	private int noSaleDay;
	/**
	 * 上架天数
	 */
	private int onLineDay;
	/**
	 * 商品单品页链接
	 */
	private String commodityLink;
	/**
	 * 图片链接
	 */
	private String commodityPicUrl;
	/**
	 * 商品状态
	 */
	private int commodityStatus;
	
	
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getCommodityNo() {
		return commodityNo;
	}
	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}
	public int getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
	public int getPageView() {
		return pageView;
	}
	public void setPageView(int pageView) {
		this.pageView = pageView;
	}
	public double getConverseRate() {
		return converseRate;
	}
	public void setConverseRate(double converseRate) {
		this.converseRate = converseRate;
	}
	public int getSaleCommodityNum() {
		return saleCommodityNum;
	}
	public void setSaleCommodityNum(int saleCommodityNum) {
		this.saleCommodityNum = saleCommodityNum;
	}
	public int getCommodityNum() {
		return commodityNum;
	}
	public void setCommodityNum(int commodityNum) {
		this.commodityNum = commodityNum;
	}
	public double getSaleTotalAmount() {
		return saleTotalAmount;
	}
	public void setSaleTotalAmount(double saleTotalAmount) {
		this.saleTotalAmount = saleTotalAmount;
	}
	public double getProdTotalAmt() {
		return prodTotalAmt;
	}
	public void setProdTotalAmt(double prodTotalAmt) {
		this.prodTotalAmt = prodTotalAmt;
	}
	public int getStoreNum() {
		return storeNum;
	}
	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}
	public double getSendAvgAmount() {
		return sendAvgAmount;
	}
	public void setSendAvgAmount(double sendAvgAmount) {
		this.sendAvgAmount = sendAvgAmount;
	}
	public String getCommodityLink() {
		return commodityLink;
	}
	public void setCommodityLink(String commodityLink) {
		this.commodityLink = commodityLink;
	}
	public String getCommodityPicUrl() {
		return commodityPicUrl;
	}
	public void setCommodityPicUrl(String commodityPicUrl) {
		this.commodityPicUrl = commodityPicUrl;
	}
	public int getCommodityStatus() {
		return commodityStatus;
	}
	public void setCommodityStatus(int commodityStatus) {
		this.commodityStatus = commodityStatus;
	}
	public int getSendCommodityNum() {
		return sendCommodityNum;
	}
	public void setSendCommodityNum(int sendCommodityNum) {
		this.sendCommodityNum = sendCommodityNum;
	}
	public double getSendCommodityAmt() {
		return sendCommodityAmt;
	}
	public void setSendCommodityAmt(double sendCommodityAmt) {
		this.sendCommodityAmt = sendCommodityAmt;
	}
	public double getSendRate() {
		return sendRate;
	}
	public void setSendRate(double sendRate) {
		this.sendRate = sendRate;
	}
	public double getSaleAvgAmount() {
		return saleAvgAmount;
	}
	public void setSaleAvgAmount(double saleAvgAmount) {
		this.saleAvgAmount = saleAvgAmount;
	}
	public double getPayAvgAmount() {
		return payAvgAmount;
	}
	public void setPayAvgAmount(double payAvgAmount) {
		this.payAvgAmount = payAvgAmount;
	}
	public int getCommodityTimes() {
		return commodityTimes;
	}
	public void setCommodityTimes(int commodityTimes) {
		this.commodityTimes = commodityTimes;
	}
	public int getColectedNum() {
		return colectedNum;
	}
	public void setColectedNum(int colectedNum) {
		this.colectedNum = colectedNum;
	}
	public int getRejectCommodityNum() {
		return rejectCommodityNum;
	}
	public void setRejectCommodityNum(int rejectCommodityNum) {
		this.rejectCommodityNum = rejectCommodityNum;
	}
	public int getRejectCommodityAmt() {
		return rejectCommodityAmt;
	}
	public void setRejectCommodityAmt(int rejectCommodityAmt) {
		this.rejectCommodityAmt = rejectCommodityAmt;
	}
	public double getRejectRate() {
		return rejectRate;
	}
	public void setRejectRate(double rejectRate) {
		this.rejectRate = rejectRate;
	}
	public int getNewCommodityCount() {
		return newCommodityCount;
	}
	public void setNewCommodityCount(int newCommodityCount) {
		this.newCommodityCount = newCommodityCount;
	}
	public int getNoSaleDay() {
		return noSaleDay;
	}
	public void setNoSaleDay(int noSaleDay) {
		this.noSaleDay = noSaleDay;
	}
	public int getOnLineDay() {
		return onLineDay;
	}
	public void setOnLineDay(int onLineDay) {
		this.onLineDay = onLineDay;
	}
}
