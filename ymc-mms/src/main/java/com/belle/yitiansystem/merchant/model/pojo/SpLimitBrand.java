package com.belle.yitiansystem.merchant.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 招商系统 商品品牌权限表
 * @author wang.m
 * @Date 2012-03-05
 *
 */
@Entity
@Table(name = "tbl_sp_limit_brand")
public class SpLimitBrand {
	private String id;
	
	private String supplyId;//供应商ID
	
	private String brandNo;//品牌编号
	
	public SpLimitBrand(String id, String supplyId, String brandNo) {
		super();
		this.id = id;
		this.supplyId = supplyId;
		this.brandNo = brandNo;
	}



	public SpLimitBrand() {
		super();
	}



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
	@Column(name = "supply_id", length = 20)
	public String getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(String supplyId) {
		this.supplyId = supplyId;
	}
	
	@Column(name = "brand_no", length = 20)
	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}
	
}
