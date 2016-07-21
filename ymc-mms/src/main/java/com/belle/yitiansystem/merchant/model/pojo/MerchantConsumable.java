package com.belle.yitiansystem.merchant.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 耗材实体类
 * @author wang.m
 * @DATE 2012-04-25
 *
 */
@Entity
@Table(name = "tbl_merchant_consumable")
public class MerchantConsumable {

	private String id;
	private String consumableName;//耗材名称
	private String consumableCode;//耗材条码
	private Double price;//耗材价格
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
	@Column(name = "consumable_name", length = 50)
	public String getConsumableName() {
		return consumableName;
	}

	public void setConsumableName(String consumableName) {
		this.consumableName = consumableName;
	}
	@Column(name = "consumable_code", length = 30)
	public String getConsumableCode() {
		return consumableCode;
	}

	public void setConsumableCode(String consumableCode) {
		this.consumableCode = consumableCode;
	}
	@Column(name = "price", length = 20)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	@Column(name = "create_time", length = 20)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Column(name = "creater", length = 20)
	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Column(name = "remark", length = 50)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public MerchantConsumable(String id, String consumableName,
			String consumableCode, Double price, String createTime,
			String remark) {
		super();
		this.id = id;
		this.consumableName = consumableName;
		this.consumableCode = consumableCode;
		this.price = price;
		this.createTime = createTime;
		this.remark = remark;
	}

	public MerchantConsumable() {
		super();
	}

}
