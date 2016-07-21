package com.yougou.kaidian.commodity.model.vo;  

public class CommodityAndProductExportVo {
	private String id;
	private String catNo;
	private String commodityName;
	private String commodityNo;
	private String updateDate;
	private String createDate;//创建时间
	private String showDate;//上架时间
	private String downDate;//下架时间
	private int isAudit;
	private String picSmall;
	private String salePrice;
	private int saleQuantity;
	private int onSaleQuantity;
	private String merchantCode;
	private Integer orderDistributionSide;//订订单配送方(0.优购、1.商家)
	private String costPrice;
	private String publicPrice;
	private String styleNo;
	private String specName;//颜色
	private String brandName;//品牌
	private String catStructName;
	private String firstLevelCatName;//一级分类
	private String prodUrl;
	private String years;
	/** 款色编码 */
	private String supplierCode;
	
	/** 尺码 */
	private String sizeName;
	
	/** 第三方条码 *//**商家款色编码*/
	private String thirdPartyCode;
	//库存
	private Integer stock;

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	public String getThirdPartyCode() {
		return thirdPartyCode;
	}

	public void setThirdPartyCode(String thirdPartyCode) {
		this.thirdPartyCode = thirdPartyCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCatNo() {
		return catNo;
	}

	public void setCatNo(String catNo) {
		this.catNo = catNo;
	}

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

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getShowDate() {
		return showDate;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	public String getDownDate() {
		return downDate;
	}

	public void setDownDate(String downDate) {
		this.downDate = downDate;
	}

	public int getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(int isAudit) {
		this.isAudit = isAudit;
	}

	public String getPicSmall() {
		return picSmall;
	}

	public void setPicSmall(String picSmall) {
		this.picSmall = picSmall;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public int getSaleQuantity() {
		return saleQuantity;
	}

	public void setSaleQuantity(int saleQuantity) {
		this.saleQuantity = saleQuantity;
	}

	public int getOnSaleQuantity() {
		return onSaleQuantity;
	}

	public void setOnSaleQuantity(int onSaleQuantity) {
		this.onSaleQuantity = onSaleQuantity;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public Integer getOrderDistributionSide() {
		return orderDistributionSide;
	}

	public void setOrderDistributionSide(Integer orderDistributionSide) {
		this.orderDistributionSide = orderDistributionSide;
	}

	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}

	public String getPublicPrice() {
		return publicPrice;
	}

	public void setPublicPrice(String publicPrice) {
		this.publicPrice = publicPrice;
	}

	public String getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCatStructName() {
		return catStructName;
	}

	public void setCatStructName(String catStructName) {
		this.catStructName = catStructName;
	}

	public String getFirstLevelCatName() {
		return firstLevelCatName;
	}

	public void setFirstLevelCatName(String firstLevelCatName) {
		this.firstLevelCatName = firstLevelCatName;
	}

	public String getProdUrl() {
		return prodUrl;
	}

	public void setProdUrl(String prodUrl) {
		this.prodUrl = prodUrl;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
}
