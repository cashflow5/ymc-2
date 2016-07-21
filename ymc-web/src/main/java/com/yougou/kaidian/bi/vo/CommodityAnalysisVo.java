package com.yougou.kaidian.bi.vo;  

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: CommodityAnalysisVo
 * Desc: 首页的商品分析报表
 * date: 2015-7-27 下午4:23:30
 * @author li.n1 
 * @since JDK 1.6
 */
public class CommodityAnalysisVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "CommodityAnalysisVo [merchantCode=" + merchantCode
				+ ", brandNo=" + brandNo + ", rootCategory=" + rootCategory
				+ ", secondCategory=" + secondCategory + ", threeCategory="
				+ threeCategory + ", commodityNo=" + commodityNo
				+ ", keywords=" + keywords + ", dateCondition=" + dateCondition
				+ ", dimensions=" + dimensions + ", sortBy=" + sortBy
				+ ", sortDirection=" + sortDirection + ", queryStartDate="
				+ queryStartDate + ", queryEndDate=" + queryEndDate + "]";
	}
	/**
	 * 商家编码
	 */
	private String merchantCode;
	
	/**
	 * 品牌编码
	 */
	private String brandNo;
	/**
	 * 一级分类编码
	 */
	private String rootCategory;
	/**
	 * 二级分类编码
	 */
	private String secondCategory;
	/**
	 * 三级分类编码
	 */
	private String threeCategory;
	/**
	 * 商品编码
	 */
	private String commodityNo;
	/**
	 * 搜索框输入的关键词
	 */
	private String keywords;
	/**
	 * 时间跨度  1昨天  	2最近7天 	3最近30天
	 */
	private int dateCondition = 1;
	/**
	 * 维度查询，1 商品维度   2 品类维度
	 */
	private int dimensions = 1;
	/**
	 * 根据具体哪个指标排序
	 */
	private String sortBy; 
	/**
	 * 排序方向（默认降序） 0 降序  1升序
	 */
	private int sortDirection = 0;
	/**
	 * 查询开始时间
	 */
	private Date queryStartDate;
	
	/**
	 * 查询结束时间
	 */
	private Date queryEndDate;
	
	public String getCommodityNo() {
		return commodityNo;
	}
	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public Date getQueryStartDate() {
		return queryStartDate;
	}
	public void setQueryStartDate(Date queryStartDate) {
		this.queryStartDate = queryStartDate;
	}
	public Date getQueryEndDate() {
		return queryEndDate;
	}
	public void setQueryEndDate(Date queryEndDate) {
		this.queryEndDate = queryEndDate;
	}
	public String getSecondCategory() {
		return secondCategory;
	}
	public void setSecondCategory(String secondCategory) {
		this.secondCategory = secondCategory;
	}
	public String getRootCategory() {
		return rootCategory;
	}
	public void setRootCategory(String rootCategory) {
		this.rootCategory = rootCategory;
	}
	public String getThreeCategory() {
		return threeCategory;
	}
	public void setThreeCategory(String threeCategory) {
		this.threeCategory = threeCategory;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public int getDateCondition() {
		return dateCondition;
	}
	public void setDateCondition(int dateCondition) {
		this.dateCondition = dateCondition;
	}
	public int getDimensions() {
		return dimensions;
	}
	public void setDimensions(int dimensions) {
		this.dimensions = dimensions;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public int getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(int sortDirection) {
		this.sortDirection = sortDirection;
	}
	public String getBrandNo() {
		return brandNo;
	}
	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}
}
