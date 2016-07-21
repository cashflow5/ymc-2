package com.yougou.kaidian.bi.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommoditySaleRank implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String merchantCode; //商家编码
	private String prodUrl; //商品链接
	private String imageUrl; //图片地址
	private String productNo; //商品编码
	private Integer payedProduct; //支付商品数量
	private BigDecimal payedAveragePrice; //支付均价
	private Integer storge; //库存
	private String lastSevenDayPayedProduct; //
	private List<Map> saleNums = new ArrayList<Map>(); //最近7天每日销量
	private Integer fcount; //被收藏次数
	
	@Override
	public String toString() {
		return "CommoditySaleRank [merchantCode=" + merchantCode + ", prodUrl="
				+ prodUrl + ", imageUrl=" + imageUrl + ", productNo="
				+ productNo + ", payedProduct=" + payedProduct
				+ ", payedAveragePrice=" + payedAveragePrice + ", storge="
				+ storge + ", lastSevenDayPayedProduct="
				+ lastSevenDayPayedProduct + ", saleNums=" + saleNums
				+ ", fcount=" + fcount + "]";
	}
	public String getProdUrl() {
		return prodUrl;
	}	
	public void setProdUrl(String prodUrl) {
		this.prodUrl = prodUrl;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public Integer getPayedProduct() {
		return payedProduct;
	}
	public void setPayedProduct(Integer payedProduct) {
		this.payedProduct = payedProduct;
	}
	public BigDecimal getPayedAveragePrice() {
		return payedAveragePrice;
	}
	public void setPayedAveragePrice(BigDecimal payedAveragePrice) {
		this.payedAveragePrice = payedAveragePrice;
	}
	public Integer getStorge() {
		return storge;
	}
	public void setStorge(Integer storge) {
		this.storge = storge;
	}
	public String getLastSevenDayPayedProduct() {
		return lastSevenDayPayedProduct;
	}
	public void setLastSevenDayPayedProduct(String lastSevenDayPayedProduct) {
		this.lastSevenDayPayedProduct = lastSevenDayPayedProduct;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public List<Map> getSaleNums() {
		return saleNums;
	}
	public void setSaleNums(List<Map> saleNums) {
		this.saleNums = saleNums;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public Integer getFcount() {
		return fcount;
	}
	public void setFcount(Integer fcount) {
		this.fcount = fcount;
	}
	
}
