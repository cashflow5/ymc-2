package com.yougou.kaidian.order.model;

/**
 * 商家收货退货地址设置  tbl_merchant_rejected_address
 * @author daixiaowei
 *
 */
public class MerchantRejectedAddress {
	
	private String id;
	private String supplierName;//商家名称
	private String supplierCode;//商家编号
	private String consigneeName;//收货人姓名
	private String consigneePhone;//收货人手机(最新修改：更新字段为收货人电话)
	private String consigneeTell;//收货人电话(最新修改：更新字段为优购客服电话)
	private String warehousePostcode;//收货仓库邮编
	private String warehouseArea;//收货仓库地区
	private String warehouseAdress;//收货仓库地址
	private String createrTime;//创建时间
	private String createrPerson;//创建人
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getConsigneePhone() {
		return consigneePhone;
	}
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	public String getConsigneeTell() {
		return consigneeTell;
	}
	public void setConsigneeTell(String consigneeTell) {
		this.consigneeTell = consigneeTell;
	}
	public String getWarehousePostcode() {
		return warehousePostcode;
	}
	public void setWarehousePostcode(String warehousePostcode) {
		this.warehousePostcode = warehousePostcode;
	}
	public String getWarehouseArea() {
		return warehouseArea;
	}
	public void setWarehouseArea(String warehouseArea) {
		this.warehouseArea = warehouseArea;
	}
	public String getWarehouseAdress() {
		return warehouseAdress;
	}
	public void setWarehouseAdress(String warehouseAdress) {
		this.warehouseAdress = warehouseAdress;
	}
	public String getCreaterTime() {
		return createrTime;
	}
	public void setCreaterTime(String createrTime) {
		this.createrTime = createrTime;
	}
	public String getCreaterPerson() {
		return createrPerson;
	}
	public void setCreaterPerson(String createrPerson) {
		this.createrPerson = createrPerson;
	}
	
}
