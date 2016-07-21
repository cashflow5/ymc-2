package com.belle.yitiansystem.merchant.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 招商系统 商品分类权限表
 * @author wang.m
 * @Date 2012-03-05
 *
 */
@Entity
@Table(name = "tbl_sp_limit_cat")
public class SpLimitCat {
	private String id;
	
	private String supplyId;//供应商ID
	
	private String catNo;//分类编号
	
	private String structName;//分类结构
	
	
	public SpLimitCat() {
		super();
	}

	public SpLimitCat(String id, String supplyId, String catNo,
			String structName) {
		super();
		this.id = id;
		this.supplyId = supplyId;
		this.catNo = catNo;
		this.structName = structName;
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
	
	@Column(name = "cat_no", length = 20)
	public String getCatNo() {
		return catNo;
	}
	public void setCatNo(String catNo) {
		this.catNo = catNo;
	}
	@Column(name = "struct_name", length = 20)
	public String getStructName() {
		return structName;
	}
	public void setStructName(String structName) {
		this.structName = structName;
	}
}
