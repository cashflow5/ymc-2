package com.yougou.kaidian.commodity.model.vo;  

import java.io.Serializable;

public class SensitiveWordVo implements Serializable {
	/** 
	 * @since JDK 1.6 
	 * @date 2015-9-14 下午6:42:55 
	 */ 
	private static final long serialVersionUID = 6485511023174404226L;
	//商品编码
	private String[] commodityNo;
	//敏感词可能存在的地方-商品名称
	private String name;
	//商品卖点
	private String sellingPoint;
	//商品描述
	private String prodDesc;
	//款号
	private String styleNo;
	//款色编码
	private String supplierCode;
	//敏感词
	private String sensiveWord;
	//包含敏感词后的操作:0-取消,1-确定
	private Short followOperate;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSellingPoint() {
		return sellingPoint;
	}
	public void setSellingPoint(String sellingPoint) {
		this.sellingPoint = sellingPoint;
	}
	public String getProdDesc() {
		return prodDesc;
	}
	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}
	public String getSensiveWord() {
		return sensiveWord;
	}
	public void setSensiveWord(String sensiveWord) {
		this.sensiveWord = sensiveWord;
	}
	public Short getFollowOperate() {
		return followOperate;
	}
	public void setFollowOperate(Short followOperate) {
		this.followOperate = followOperate;
	}
	public String[] getCommodityNo() {
		return commodityNo;
	}
	public void setCommodityNo(String[] commodityNo) {
		this.commodityNo = commodityNo;
	}
	
}
