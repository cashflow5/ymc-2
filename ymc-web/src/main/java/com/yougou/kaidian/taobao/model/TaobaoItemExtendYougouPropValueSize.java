package com.yougou.kaidian.taobao.model;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-8-4 上午10:34:43
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class TaobaoItemExtendYougouPropValueSize {
	private String id;
	private String extendId;
	private Long numIid;
	private String yougouPropValueNo; // 尺码，对应尺码属性值
	private String yougouPropValueName;
	private Integer stock; // 库存
	private String barcode; // 条码
	private Float weight; // 重量
	private String merchantCode;// 商家编码
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getNumIid() {
		return numIid;
	}
	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}
	public String getYougouPropValueNo() {
		return yougouPropValueNo;
	}
	public void setYougouPropValueNo(String yougouPropValueNo) {
		this.yougouPropValueNo = yougouPropValueNo;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getYougouPropValueName() {
		return yougouPropValueName;
	}
	public void setYougouPropValueName(String yougouPropValueName) {
		this.yougouPropValueName = yougouPropValueName;
	}
	public String getExtendId() {
		return extendId;
	}
	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
