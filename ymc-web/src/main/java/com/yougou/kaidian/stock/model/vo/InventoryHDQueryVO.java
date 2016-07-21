package com.yougou.kaidian.stock.model.vo;


public class InventoryHDQueryVO {
	private String virtualId;// 虚拟仓库ID
	private String goodsName;// 货品名称
	private String commodityCode;// 商品编号
	private String productNo;//货品编号
	private String category1No;// 一级分类
	private String category2No;// 二级分类
	private String category3No;// 三级分类
	private String brandNo;// 商品编号
	private String year;// 年份
	private String season; // 季节
	private String styleNo;// 商品款号
	private String supplierCode;// 款色编码
	private String insideCode;// 货品条码
	private String merchantCode; // 商家编号
	private String commodityStatus;// 销售状态 1：下架/不可售商品 ，2：上架 /在售商品
	private int left;
	private int selectType;// 1:正品库存 2：残品库存
	private int isYg; // 商品入优购仓 1:是 2:否
	private String categoryNo;//分类拼接
	private String productList;
	private String warehouseCode;//仓库编码
	private String stock;//实际库存数量
	
	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public InventoryHDQueryVO() {

		super();
	}

	public InventoryHDQueryVO(String virtualId, String goodsName, String commodityCode, String category1No, String category2No, String category3No, String brandNo, String year,
			String season, String styleNo, String supplierCode, String insideCode, int selectType, String merchantCode, String commodityStatus, int left, int isYg) {
		super();
		this.virtualId = virtualId;
		this.goodsName = goodsName;
		this.commodityCode = commodityCode;
		this.category1No = category1No;
		this.category2No = category2No;
		this.category3No = category3No;
		this.brandNo = brandNo;
		this.year = year;
		this.season = season;
		this.styleNo = styleNo;
		this.supplierCode = supplierCode;
		this.insideCode = insideCode;
		this.selectType = selectType;
		this.merchantCode = merchantCode;
		this.commodityStatus = commodityStatus;
		this.left = left;
		this.isYg = isYg;
	}

	public String getVirtualId() {
		return virtualId;
	}

	public void setVirtualId(String virtualId) {
		this.virtualId = virtualId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public String getCategory1No() {
		return category1No;
	}

	public void setCategory1No(String category1No) {
		this.category1No = category1No;
	}

	public String getCategory2No() {
		return category2No;
	}

	public void setCategory2No(String category2No) {
		this.category2No = category2No;
	}

	public String getCategory3No() {
		return category3No;
	}

	public void setCategory3No(String category3No) {
		this.category3No = category3No;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getInsideCode() {
		return insideCode;
	}

	public void setInsideCode(String insideCode) {
		this.insideCode = insideCode;
	}

	public int getSelectType() {
		return selectType;
	}

	public void setSelectType(int selectType) {
		this.selectType = selectType;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getCommodityStatus() {
		return commodityStatus;
	}

	public void setCommodityStatus(String commodityStatus) {
		this.commodityStatus = commodityStatus;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getIsYg() {
		return isYg;
	}

	public void setIsYg(int isYg) {
		this.isYg = isYg;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductList() {
		return productList;
	}

	public void setProductList(String productList) {
		this.productList = productList;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

}
