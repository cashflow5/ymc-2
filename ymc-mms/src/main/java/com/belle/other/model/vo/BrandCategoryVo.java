/*
 * Copyright 2011 Belle.com All right reserved. This software is the
 * confidential and proprietary information of Belle.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Belle.com.
 */
package com.belle.other.model.vo;

import java.io.Serializable;

/**
 * 类描述：TODO 类实现描述
 * 
 * @author zhongwen
 * @date 2011-5-13 上午11:10:28
 * @email zw1004@163.com
 */
public class BrandCategoryVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String category1No;
	private String category2No;
	private String category3No;
	private String brandNo;

	private String productNo;
	private String productName;

	private String insideCode;

	private String styleNo;
	private String supplierCode;
	private String warehouseId;
	private String warehouseCode;
	private String goodsType;

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getInsideCode() {
		return insideCode;
	}

	public void setInsideCode(String insideCode) {
		this.insideCode = insideCode;
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

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

}
