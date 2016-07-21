package com.yougou.kaidian.commodity.model.vo;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.yougou.kaidian.commodity.constant.CommodityConstant;

public class CommodityQueryVo {

	private String brandNo;
	private String structName;
	private String commodityName;
	private String commodityNo;
	private Integer status;//下架类型 1，从未上架  2，人工下架 3，销售完毕商品
	private Integer isAudit;//审核状态
	private String auditStatus;  //审核状态(用于程序逻辑判断)
	private String minCreateDate;//创建开始时间
	private String maxCreateDate;//创建结束时间
	private String minShowDate;//创建上架时间
	private String maxShowDate;//创建上架时间
	private String minDownDate;//创建下架时间t
	private String maxDownDate;//创建下架时间
	private String minSubmitAuditDate; //提交审批时间开始
	private String maxSubmitAuditDate; //提交审批时间结束
	
	private String merchantCode;
	private String styleNo;
	private String supplierCode;

	private String minSalePrice;//最小销售价
	private String maxSalePrice;//最高销售价
	private String maxSaleQuantity;//最大销售价
	private String minSaleQuantity;//最小销售价
	private String rootCattegory;//一级分类
	private String secondCategory;//二级分类
	private String threeCategory;//三级分类
	private String commodityStatus;//上下架状态
	private String onSaleQuantiry;//销售数量排序
	private String isNotIn12;//不包含上架和下架的商品
	
	//add by LQ on 20150323
	private String thirdPartyCode;//货品条码
	
	private List<String> thirdPartyCodeList;//add by LQ on 20150323

	private List<String> commodityNoList;
	
	private List<String> supplierCodeList;
	
	private List<String> styleNoList;

	/**
	 * 是否审核通过
	 */
	private Boolean isAuditPass;
	
	private String warehouseCode;//仓库编码
	
	private String deleteFlag;
	
	
	private String minUpdateDate;
	private String maxUpdateDate;
	
	private String recyclebinFlag;//否为回收站商品,0否（默认为0）,1是
	//商品含有的敏感词
	//true 表示只查询含有敏感词的商品，false 表示只查询不含敏感词的商品
	private boolean sensitive = false;
	
	/**
	 * 商品常量对象
	 */
	private static final CommodityConstant commodityConstant = new CommodityConstant();
	
	public String getOnSaleQuantiry() {
		return onSaleQuantiry;
	}

	public void setOnSaleQuantiry(String onSaleQuantiry) {
		this.onSaleQuantiry = onSaleQuantiry;
	}

	public String getCommodityStatus() {
		return commodityStatus;
	}

	public void setCommodityStatus(String commodityStatus) {
		this.commodityStatus = commodityStatus;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getStructName() {
		return structName;
	}

	public void setStructName(String structName) {
		this.structName = structName;
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
		if(!StringUtils.isEmpty(commodityNo)){
			this.commodityNoList = Arrays.asList(commodityNo.split(","));
		}
		this.commodityNo = commodityNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getMaxCreateDate() {
		return maxCreateDate;
	}

	public void setMaxCreateDate(String maxCreateDate) {
		this.maxCreateDate = maxCreateDate;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMinCreateDate() {
		return minCreateDate;
	}

	public void setMinCreateDate(String minCreateDate) {
		this.minCreateDate = minCreateDate;
	}

	public String getMaxSaleQuantity() {
		return maxSaleQuantity;
	}

	public void setMaxSaleQuantity(String maxSaleQuantity) {
		this.maxSaleQuantity = maxSaleQuantity;
	}

	public String getMinSaleQuantity() {
		return minSaleQuantity;
	}

	public void setMinSaleQuantity(String minSaleQuantity) {
		this.minSaleQuantity = minSaleQuantity;
	}

	public String getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(String styleNo) {
		if(!StringUtils.isEmpty(styleNo)){
			this.styleNoList = Arrays.asList(styleNo.split(","));
		}
		this.styleNo = styleNo;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		if(!StringUtils.isEmpty(supplierCode)){
			this.supplierCodeList = Arrays.asList(supplierCode.split(","));
		}
		this.supplierCode = supplierCode;
	}

	public String getMinSalePrice() {
		return minSalePrice;
	}

	public void setMinSalePrice(String minSalePrice) {
		this.minSalePrice = minSalePrice;
	}

	public String getMaxSalePrice() {
		return maxSalePrice;
	}

	public void setMaxSalePrice(String maxSalePrice) {
		this.maxSalePrice = maxSalePrice;
	}

	public String getRootCattegory() {
		return rootCattegory;
	}

	public void setRootCattegory(String rootCattegory) {
		this.rootCattegory = rootCattegory;
	}

	public String getSecondCategory() {
		return secondCategory;
	}

	public void setSecondCategory(String secondCategory) {
		this.secondCategory = secondCategory;
	}

	public String getThreeCategory() {
		return threeCategory;
	}

	public void setThreeCategory(String threeCategory) {
		this.threeCategory = threeCategory;
	}
	public String getMinShowDate() {
		return minShowDate;
	}

	public void setMinShowDate(String minShowDate) {
		this.minShowDate = minShowDate;
	}

	public String getMaxShowDate() {
		return maxShowDate;
	}

	public void setMaxShowDate(String maxShowDate) {
		this.maxShowDate = maxShowDate;
	}

	public String getMinDownDate() {
		return minDownDate;
	}

	public void setMinDownDate(String minDownDate) {
		this.minDownDate = minDownDate;
	}

	public String getMaxDownDate() {
		return maxDownDate;
	}

	public void setMaxDownDate(String maxDownDate) {
		this.maxDownDate = maxDownDate;
	}

	public String getMinSubmitAuditDate() {
		return minSubmitAuditDate;
	}

	public void setMinSubmitAuditDate(String minSubmitAuditDate) {
		this.minSubmitAuditDate = minSubmitAuditDate;
	}

	public String getMaxSubmitAuditDate() {
		return maxSubmitAuditDate;
	}

	public void setMaxSubmitAuditDate(String maxSubmitAuditDate) {
		this.maxSubmitAuditDate = maxSubmitAuditDate;
	}

	public static CommodityConstant getCommodityconstant() {
		return commodityConstant;
	}

	public Boolean getIsAuditPass() {
		return isAuditPass;
	}

	public void setIsAuditPass(Boolean isAuditPass) {
		this.isAuditPass = isAuditPass;
	}

	public String getIsNotIn12() {
		return isNotIn12;
	}

	public void setIsNotIn12(String isNotIn12) {
		this.isNotIn12 = isNotIn12;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public List<String> getCommodityNoList() {
		return commodityNoList;
	}

	public void setCommodityNoList(List<String> commodityNoList) {
		this.commodityNoList = commodityNoList;
	}

	public List<String> getSupplierCodeList() {
		return supplierCodeList;
	}

	public void setSupplierCodeList(List<String> supplierCodeList) {
		this.supplierCodeList = supplierCodeList;
	}

	public List<String> getStyleNoList() {
		return styleNoList;
	}

	public void setStyleNoList(List<String> styleNoList) {
		this.styleNoList = styleNoList;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getMinUpdateDate() {
		return minUpdateDate;
	}

	public void setMinUpdateDate(String minUpdateDate) {
		this.minUpdateDate = minUpdateDate;
	}

	public String getMaxUpdateDate() {
		return maxUpdateDate;
	}

	public void setMaxUpdateDate(String maxUpdateDate) {
		this.maxUpdateDate = maxUpdateDate;
	}

	public String getThirdPartyCode() {
		return thirdPartyCode;
	}

	public void setThirdPartyCode(String thirdPartyCode) {
		if( !StringUtils.isEmpty(thirdPartyCode)){
			thirdPartyCodeList = Arrays.asList(thirdPartyCode.split(","));
		}
		this.thirdPartyCode = thirdPartyCode;
	}

	public List<String> getThirdPartyCodeList() {
		return thirdPartyCodeList;
	}

	public void setThirdPartyCodeList(List<String> thirdPartyCodeList) {
		this.thirdPartyCodeList = thirdPartyCodeList;
	}

	public String getRecyclebinFlag() {
		return recyclebinFlag;
	}

	public void setRecyclebinFlag(String recyclebinFlag) {
		this.recyclebinFlag = recyclebinFlag;
	}

	public boolean isSensitive() {
		return sensitive;
	}

	public void setSensitive(boolean sensitive) {
		this.sensitive = sensitive;
	}
	
}
