package com.yougou.kaidian.stock.model.vo;

public class InventoryVo {
	private String warehouseName;// 仓库名称
	private String goodsName;// 货品名称
	private String productNo;// 货品编号
	private String specification;// 规格
	private String unit;// 单位
	private Integer quantity;// 库存数量
	private Integer preQuantity;// 预占数量
	private Integer saleQuantity;// 可售数量
	private String thirdPartyCode;// 供应商货品编码
	private Double costPrice;//成本价
	private Double qtyCost;//库存金额 costPrice*quantity
	private String supplierCode;//款色编码
	private String brandName;//品牌名称
	private String catStructName;//分类结构名
	private String picSmall;
	private String prodUrl;
	
	public InventoryVo() {
		super();
	}

	public InventoryVo(String warehouseName, String goodsName, String productNo, String specification, String unit, Integer quantity,
			Integer preQuantity, Integer saleQuantity,String thirdPartyCode,Double costPrice,Double qtyCost,String supplierCode,String brandName) {
		super();
		this.warehouseName = warehouseName;
		this.goodsName = goodsName;
		this.productNo = productNo;
		this.specification = specification;
		this.unit = unit;
		this.quantity = quantity;
		this.preQuantity = preQuantity;
		this.saleQuantity = saleQuantity;
		this.thirdPartyCode=thirdPartyCode;
		this.costPrice=costPrice;
		this.qtyCost=qtyCost;
		this.supplierCode=supplierCode;
		this.brandName=brandName;
	}
	
	public InventoryVo(String warehouseName, String goodsName, String productNo, String specification, String unit, Integer quantity,
			Integer preQuantity, Integer saleQuantity) {
		super();
		this.warehouseName = warehouseName;
		this.goodsName = goodsName;
		this.productNo = productNo;
		this.specification = specification;
		this.unit = unit;
		this.quantity = quantity;
		this.preQuantity = preQuantity;
		this.saleQuantity = saleQuantity;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPreQuantity() {
		return preQuantity;
	}

	public void setPreQuantity(Integer preQuantity) {
		this.preQuantity = preQuantity;
	}

	public Integer getSaleQuantity() {
		return saleQuantity;
	}

	public void setSaleQuantity(Integer saleQuantity) {
		this.saleQuantity = saleQuantity;
	}

	public String getThirdPartyCode() {
		return thirdPartyCode;
	}

	public void setThirdPartyCode(String thirdPartyCode) {
		this.thirdPartyCode = thirdPartyCode;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Double getQtyCost() {
		return qtyCost;
	}

	public void setQtyCost(Double qtyCost) {
		this.qtyCost = qtyCost;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
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

	public String getPicSmall() {
		return picSmall;
	}

	public void setPicSmall(String picSmall) {
		this.picSmall = picSmall;
	}

	public String getProdUrl() {
		return prodUrl;
	}

	public void setProdUrl(String prodUrl) {
		this.prodUrl = prodUrl;
	}
	
}
