package com.yougou.kaidian.commodity.beans;

import java.util.Date;
import java.util.List;

import com.yougou.pc.model.commodityinfo.CommodityProp;
import com.yougou.pc.model.picture.Picture;
import com.yougou.pc.model.product.Product;

/**
 * 商品包装类(Use by 商品系统)
 * 
 * @author yang.mq
 *
 */
public class CommodityWrapper extends com.yougou.pc.model.commodityinfo.Commodity {

	private static final long serialVersionUID = 8461964840458662624L;
	
	private com.yougou.pc.model.commodityinfo.Commodity commodity;
	private int numbers;
	private int uploadStatus;// 0 未上传 1部分上传 2已上传

	public CommodityWrapper(
			com.yougou.pc.model.commodityinfo.Commodity commodity, int numbers,
			int uploadStatus) {
		this.commodity = commodity;
		this.numbers = numbers;
		this.uploadStatus = uploadStatus;
	}

	@Override
	public String getAliasName() {
		return commodity.getAliasName();
	}

	@Override
	public Integer getAllStrock() {
		return commodity.getAllStrock();
	}

	@Override
	public String getBrandName() {
		return commodity.getBrandName();
	}

	@Override
	public String getBrandNo() {
		return commodity.getBrandNo();
	}

	@Override
	public String getCatName() {
		return commodity.getCatName();
	}

	@Override
	public String getCatNo() {
		return commodity.getCatNo();
	}

	@Override
	public String getCatStructName() {
		return commodity.getCatStructName();
	}

	@Override
	public String getColorName() {
		return commodity.getColorName();
	}

	@Override
	public String getColorNo() {
		return commodity.getColorNo();
	}

	@Override
	public List<CommodityProp> getCommdoityProps() {
		return commodity.getCommdoityProps();
	}

	@Override
	public String getCommodityDesc() {
		return commodity.getCommodityDesc();
	}

	@Override
	public String getCommodityName() {
		return commodity.getCommodityName();
	}

	@Override
	public String getCommodityNo() {
		return commodity.getCommodityNo();
	}

	@Override
	public Double getCostPrice() {
		return commodity.getCostPrice();
	}

	@Override
	public Double getCostPrice2() {
		return commodity.getCostPrice2();
	}

	@Override
	public Date getCreateDate() {
		return commodity.getCreateDate();
	}

	@Override
	public String getDefalutPic() {
		return commodity.getDefalutPic();
	}

	@Override
	public Integer getDescNo() {
		return commodity.getDescNo();
	}

	@Override
	public Date getDownDate() {
		return commodity.getDownDate();
	}

	@Override
	public Date getFirstSellDate() {
		return commodity.getFirstSellDate();
	}

	@Override
	public String getId() {
		return commodity.getId();
	}

	@Override
	public Integer getIsAudit() {
		return commodity.getIsAudit();
	}

	@Override
	public Short getIsGenImage() {
		return commodity.getIsGenImage();
	}

	@Override
	public Double getMarkPrice() {
		return commodity.getMarkPrice();
	}

	@Override
	public String getMerchantCode() {
		return commodity.getMerchantCode();
	}

	@Override
	public Integer getOrderDistributionSide() {
		return commodity.getOrderDistributionSide();
	}

	@Override
	public Short getPicFlag() {
		return commodity.getPicFlag();
	}

	@Override
	public String getPicSmall() {
		return commodity.getPicSmall();
	}

	@Override
	public List<Picture> getPictures() {
		return commodity.getPictures();
	}

	@Override
	public Integer getPriceChangeTotal() {
		return commodity.getPriceChangeTotal();
	}

	@Override
	public List<Product> getProducts() {
		return commodity.getProducts();
	}

	@Override
	public Date getSellDate() {
		return commodity.getSellDate();
	}

	@Override
	public Double getSellPrice() {
		return commodity.getSellPrice();
	}

	@Override
	public Integer getSizeChartId() {
		return commodity.getSizeChartId();
	}

	@Override
	public Integer getStatus() {
		return commodity.getStatus();
	}

	@Override
	public String getStyleNo() {
		return commodity.getStyleNo();
	}

	@Override
	public String getSupplierCode() {
		return commodity.getSupplierCode();
	}

	@Override
	public Date getUpdateDate() {
		return commodity.getUpdateDate();
	}

	@Override
	public Long getUpdateTimestamp() {
		return commodity.getUpdateTimestamp();
	}

	@Override
	public String getYears() {
		return commodity.getYears();
	}

	@Override
	public Integer getYgStrock() {
		return commodity.getYgStrock();
	}

	@Override
	public void setAliasName(String aliasName) {
		commodity.setAliasName(aliasName);
	}

	@Override
	public void setAllStrock(Integer allStrock) {
		commodity.setAllStrock(allStrock);
	}

	@Override
	public void setBrandName(String brandName) {
		commodity.setBrandName(brandName);
	}

	@Override
	public void setBrandNo(String brandNo) {
		commodity.setBrandNo(brandNo);
	}

	@Override
	public void setCatName(String catName) {
		commodity.setCatName(catName);
	}

	@Override
	public void setCatNo(String catNo) {
		commodity.setCatNo(catNo);
	}

	@Override
	public void setCatStructName(String catStructName) {
		commodity.setCatStructName(catStructName);
	}

	@Override
	public void setColorName(String colorName) {
		commodity.setColorName(colorName);
	}

	@Override
	public void setColorNo(String colorNo) {
		commodity.setColorNo(colorNo);
	}

	@Override
	public void setCommdoityProps(List<CommodityProp> commdoityProps) {
		commodity.setCommdoityProps(commdoityProps);
	}

	@Override
	public void setCommodityDesc(String commodityDesc) {
		commodity.setCommodityDesc(commodityDesc);
	}

	@Override
	public void setCommodityName(String commodityName) {
		commodity.setCommodityName(commodityName);
	}

	@Override
	public void setCommodityNo(String commodityNo) {
		commodity.setCommodityNo(commodityNo);
	}

	@Override
	public void setCostPrice(Double costPrice) {
		commodity.setCostPrice(costPrice);
	}

	@Override
	public void setCostPrice2(Double costPrice2) {
		commodity.setCostPrice2(costPrice2);
	}

	@Override
	public void setCreateDate(Date createDate) {
		commodity.setCreateDate(createDate);
	}

	@Override
	public void setDefalutPic(String defalutPic) {
		commodity.setDefalutPic(defalutPic);
	}

	@Override
	public void setDescNo(Integer descNo) {
		commodity.setDescNo(descNo);
	}

	@Override
	public void setDownDate(Date downDate) {
		commodity.setDownDate(downDate);
	}

	@Override
	public void setFirstSellDate(Date firstSellDate) {
		commodity.setFirstSellDate(firstSellDate);
	}

	@Override
	public void setId(String id) {
		commodity.setId(id);
	}

	@Override
	public void setIsAudit(Integer isAudit) {
		commodity.setIsAudit(isAudit);
	}

	@Override
	public void setIsGenImage(Short isGenImage) {
		commodity.setIsGenImage(isGenImage);
	}

	@Override
	public void setMarkPrice(Double markPrice) {
		commodity.setMarkPrice(markPrice);
	}

	@Override
	public void setMerchantCode(String merchantCode) {
		commodity.setMerchantCode(merchantCode);
	}

	@Override
	public void setOrderDistributionSide(Integer orderDistributionSide) {
		commodity.setOrderDistributionSide(orderDistributionSide);
	}

	@Override
	public void setPicFlag(Short picFlag) {
		commodity.setPicFlag(picFlag);
	}

	@Override
	public void setPicSmall(String picSmall) {
		commodity.setPicSmall(picSmall);
	}

	@Override
	public void setPictures(List<Picture> pictures) {
		commodity.setPictures(pictures);
	}

	@Override
	public void setPriceChangeTotal(Integer priceChangeTotal) {
		commodity.setPriceChangeTotal(priceChangeTotal);
	}

	@Override
	public void setProducts(List<Product> products) {
		commodity.setProducts(products);
	}

	@Override
	public void setSellDate(Date sellDate) {
		commodity.setSellDate(sellDate);
	}

	@Override
	public void setSellPrice(Double sellPrice) {
		commodity.setSellPrice(sellPrice);
	}

	@Override
	public void setSizeChartId(Integer sizeChartId) {
		commodity.setSizeChartId(sizeChartId);
	}

	@Override
	public void setStatus(Integer status) {
		commodity.setStatus(status);
	}

	@Override
	public void setStyleNo(String styleNo) {
		commodity.setStyleNo(styleNo);
	}

	@Override
	public void setSupplierCode(String supplierCode) {
		commodity.setSupplierCode(supplierCode);
	}

	@Override
	public void setUpdateDate(Date updateDate) {
		commodity.setUpdateDate(updateDate);
	}

	@Override
	public void setUpdateTimestamp(Long updateTimestamp) {
		commodity.setUpdateTimestamp(updateTimestamp);
	}

	@Override
	public void setYears(String years) {
		commodity.setYears(years);
	}

	@Override
	public void setYgStrock(Integer ygStrock) {
		commodity.setYgStrock(ygStrock);
	}

	public int getNumbers() {
		return numbers;
	}

	public int getUploadStatus() {
		return uploadStatus;
	}

}
