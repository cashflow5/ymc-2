package com.belle.yitiansystem.merchant.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 商家收货退货地址设置
 * @author wang.m
 * @DATE 2012-05-11
 *
 */
@Entity
@Table(name = "tbl_merchant_rejected_address")
public class MerchantRejectedAddress {
	
	private String id;
	private String supplierName;//商家名称
	private String supplierCode;//商家编号
	private String consigneeName;//收货人姓名
	private String consigneePhone;//收货人电话
	private String consigneeTell;//优购客服电话！！！！
	private String warehousePostcode;//收货仓库邮编
	private String warehouseArea;//收货仓库地区
	private String warehouseAdress;//收货仓库地址
	private String createrTime;//创建时间
	private String createrPerson;//创建人
	private String supplierYgContacts;

    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "supplier_name")
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	@Column(name = "supplier_code")
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	@Column(name = "consignee_name")
	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	@Column(name = "consignee_phone")
	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	@Column(name = "consignee_tell")
	public String getConsigneeTell() {
		return consigneeTell;
	}

	public void setConsigneeTell(String consigneeTell) {
		this.consigneeTell = consigneeTell;
	}
	
	
	@Column(name = "warehouse_area")
	public String getWarehouseArea() {
		return warehouseArea;
	}
	@Column(name = "warehouse_postcode")
	public String getWarehousePostcode() {
		return warehousePostcode;
	}

	public void setWarehousePostcode(String warehousePostcode) {
		this.warehousePostcode = warehousePostcode;
	}

	public void setWarehouseArea(String warehouseArea) {
		this.warehouseArea = warehouseArea;
	}
	@Column(name = "warehouse_adress")
	public String getWarehouseAdress() {
		return warehouseAdress;
	}

	@Transient
	public String getSupplierYgContacts() {
		return supplierYgContacts;
	}

	public void setSupplierYgContacts(String supplierYgContacts) {
		this.supplierYgContacts = supplierYgContacts;
	}

	public void setWarehouseAdress(String warehouseAdress) {
		this.warehouseAdress = warehouseAdress;
	}
	@Column(name = "creater_time")
	public String getCreaterTime() {
		return createrTime;
	}

	public void setCreaterTime(String createrTime) {
		this.createrTime = createrTime;
	}
	@Column(name = "creater_person")
	public String getCreaterPerson() {
		return createrPerson;
	}

	public void setCreaterPerson(String createrPerson) {
		this.createrPerson = createrPerson;
	}

	public MerchantRejectedAddress(String id, String supplierName,
			String supplierCode, String consigneeName, String consigneePhone,
			String consigneeTell, String warehousePostcode, String warehouseArea,
			String warehouseAdress, String createrTime, String createrPerson) {
		super();
		this.id = id;
		this.supplierName = supplierName;
		this.supplierCode = supplierCode;
		this.consigneeName = consigneeName;
		this.consigneePhone = consigneePhone;
		this.consigneeTell = consigneeTell;
		this.warehousePostcode = warehousePostcode;
		this.warehouseArea = warehouseArea;
		this.warehouseAdress = warehouseAdress;
		this.createrTime = createrTime;
		this.createrPerson = createrPerson;
	}

	public MerchantRejectedAddress() {
		super();
	}
	
	
}
