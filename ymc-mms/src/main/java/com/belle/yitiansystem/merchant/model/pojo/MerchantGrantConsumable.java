package com.belle.yitiansystem.merchant.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 发放商家耗材实体类
 * @author wang.m
 * @DATE 2012-04-25
 *
 */
@Entity
@Table(name = "tbl_merchant_grant_consumable")
public class MerchantGrantConsumable {

	private String id;
	private String supplierName;//商家名称
	private String supplierCode;//商家编号
	private String consumableName;//耗材名称
	private String consumableCode;//耗材条码
	private String invoicesNo;//单据号
	private String oldInvoicesNo;//原单据号
	private Integer invoicesType;//单据类型   1为发放调拨  2 剩余登记
	private Integer num;//发放数量
	private String createTime;//创建时间
	private String creater;//创建人
	private String remark;//备注
	

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
	@Column(name = "consumable_name")
	public String getConsumableName() {
		return consumableName;
	}

	public void setConsumableName(String consumableName) {
		this.consumableName = consumableName;
	}
	@Column(name = "consumable_code")
	public String getConsumableCode() {
		return consumableCode;
	}

	public void setConsumableCode(String consumableCode) {
		this.consumableCode = consumableCode;
	}
	@Column(name = "invoices_no")
	public String getInvoicesNo() {
		return invoicesNo;
	}

	public void setInvoicesNo(String invoicesNo) {
		this.invoicesNo = invoicesNo;
	}
	@Column(name = "old_invoices_no")
	public String getOldInvoicesNo() {
		return oldInvoicesNo;
	}

	public void setOldInvoicesNo(String oldInvoicesNo) {
		this.oldInvoicesNo = oldInvoicesNo;
	}
	@Column(name = "invoices_type")
	public Integer getInvoicesType() {
		return invoicesType;
	}

	public void setInvoicesType(Integer invoicesType) {
		this.invoicesType = invoicesType;
	}
	@Column(name = "num")
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	@Column(name = "create_time")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Column(name = "creater")
	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public MerchantGrantConsumable(String id, String supplierName,
			String supplierCode, String consumableName, String consumableCode,
			String invoicesNo, String oldInvoicesNo, Integer invoicesType,
			Integer num, String createTime, String creater, String remark) {
		super();
		this.id = id;
		this.supplierName = supplierName;
		this.supplierCode = supplierCode;
		this.consumableName = consumableName;
		this.consumableCode = consumableCode;
		this.invoicesNo = invoicesNo;
		this.oldInvoicesNo = oldInvoicesNo;
		this.invoicesType = invoicesType;
		this.num = num;
		this.createTime = createTime;
		this.creater = creater;
		this.remark = remark;
	}

	public MerchantGrantConsumable() {
		super();
	}
	
}
