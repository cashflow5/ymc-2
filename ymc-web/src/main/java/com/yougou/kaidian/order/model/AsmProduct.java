package com.yougou.kaidian.order.model;

/**
 * 质检货品信息
 * @author lim
 *
 */
public class AsmProduct {

	private String commodityProductId;//货品id
	private String productNo;//优购货品编码
	private String commodityName;//商品名称
	private String specification;//颜色,尺寸
	private String supplierCode;//款色编码
	private String orderInsideCode;//自定义货品条码(订单明细)
	private String commodityInsideCode;//自定义货品条码(商品-货品)
	
	public String getCommodityProductId() {
		return commodityProductId;
	}
	public void setCommodityProductId(String commodityProductId) {
		this.commodityProductId = commodityProductId;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getOrderInsideCode() {
		return orderInsideCode;
	}
	public void setOrderInsideCode(String orderInsideCode) {
		this.orderInsideCode = orderInsideCode;
	}
	public String getCommodityInsideCode() {
		return commodityInsideCode;
	}
	public void setCommodityInsideCode(String commodityInsideCode) {
		this.commodityInsideCode = commodityInsideCode;
	}
}
