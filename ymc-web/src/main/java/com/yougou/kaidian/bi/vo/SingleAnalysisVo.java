package com.yougou.kaidian.bi.vo;

import java.io.Serializable;

/**
 * 数据报表单品分析VO
 * @author zhang.f1
 *
 */
public class SingleAnalysisVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "SingleAnalysisVo [commodityName=" + commodityName
				+ ", commodityNo=" + commodityNo + ", picUrl=" + picUrl
				+ ", commodityStyleNo=" + commodityStyleNo + ", publicPrice="
				+ publicPrice + ", yougouPrice=" + yougouPrice + ", discount="
				+ discount + ", commodityScore=" + commodityScore
				+ ", commodityTimes=" + commodityTimes
				+ ", commodityFirstSaleDate=" + commodityFirstSaleDate
				+ ", commodityTotalAvailable=" + commodityTotalAvailable
				+ ", pageView=" + pageView + ", visitCount=" + visitCount
				+ ", changePercent=" + changePercent + ", commodityNum="
				+ commodityNum + ", prodTotalAmt=" + prodTotalAmt
				+ ", avgSendAmt=" + avgSendAmt + ", favoriteCount="
				+ favoriteCount + ", classifyCount=" + classifyCount + "]";
	}
	//商品名称
	private String commodityName;
	//商品编码
	private String commodityNo;
	//商品图片
	private String picUrl;
	//款色编码
	private String commodityStyleNo;
	//市场价
	private Double publicPrice;
	//优购价
	private Double yougouPrice;
	//折扣
	private Double discount ;
	//商品评分
	private Double commodityScore;
	//商品评论次数
	private Integer commodityTimes;
	//商品上架时间
	private String commodityFirstSaleDate;
	//商品可售库存
	private Integer commodityTotalAvailable;
	//浏览量
	private Integer pageView;
	//访次
	private Integer visitCount;
	//转化率
	private Double changePercent;	
	//支付件数
	private Integer commodityNum;
	//支付金额
	private Double prodTotalAmt;
	//发货均价
	private Double avgSendAmt;
	//收藏人数
	private int favoriteCount;
	//所属归类数量
	private int classifyCount;
	
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public String getCommodityNo() {
		return commodityNo;
	}
	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getCommodityStyleNo() {
		return commodityStyleNo;
	}
	public void setCommodityStyleNo(String commodityStyleNo) {
		this.commodityStyleNo = commodityStyleNo;
	}
	public Double getPublicPrice() {
		return publicPrice;
	}
	public void setPublicPrice(Double publicPrice) {
		this.publicPrice = publicPrice;
	}
	public Double getYougouPrice() {
		return yougouPrice;
	}
	public void setYougouPrice(Double yougouPrice) {
		this.yougouPrice = yougouPrice;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getCommodityScore() {
		return commodityScore;
	}
	public void setCommodityScore(Double commodityScore) {
		this.commodityScore = commodityScore;
	}
	public Integer getCommodityTimes() {
		return commodityTimes;
	}
	public void setCommodityTimes(Integer commodityTimes) {
		this.commodityTimes = commodityTimes;
	}
	public String getCommodityFirstSaleDate() {
		return commodityFirstSaleDate;
	}
	public void setCommodityFirstSaleDate(String commodityFirstSaleDate) {
		this.commodityFirstSaleDate = commodityFirstSaleDate;
	}
	public Integer getCommodityTotalAvailable() {
		return commodityTotalAvailable;
	}
	public void setCommodityTotalAvailable(Integer commodityTotalAvailable) {
		this.commodityTotalAvailable = commodityTotalAvailable;
	}
	public Integer getPageView() {
		return pageView;
	}
	public void setPageView(Integer pageView) {
		this.pageView = pageView;
	}
	public Integer getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}

	public Double getAvgSendAmt() {
		return avgSendAmt;
	}
	public void setAvgSendAmt(Double avgSendAmt) {
		this.avgSendAmt = avgSendAmt;
	}
	public Integer getCommodityNum() {
		return commodityNum;
	}
	public void setCommodityNum(Integer commodityNum) {
		this.commodityNum = commodityNum;
	}
	public Double getProdTotalAmt() {
		return prodTotalAmt;
	}
	public void setProdTotalAmt(Double prodTotalAmt) {
		this.prodTotalAmt = prodTotalAmt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getFavoriteCount() {
		return favoriteCount;
	}
	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	public int getClassifyCount() {
		return classifyCount;
	}
	public void setClassifyCount(int classifyCount) {
		this.classifyCount = classifyCount;
	}
	public Double getChangePercent() {
		return changePercent;
	}
	public void setChangePercent(Double changePercent) {
		this.changePercent = changePercent;
	}
	

}
