package com.belle.yitiansystem.merchant.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 商家权限列表类
 * @author wang.m
 *
 */
@Entity
@Table(name = "tbl_merchant_authority")
public class MerchantsAuthority {

	private String id;
	
	private String parentId;
	
	private String authrityName;//权限名称
	
	private String authrityURL;//url
	
	private Integer authrityModule;//所属模块  0 根模块 1 商品模块  2 订单模3 库存 4结算 4 设置
	
	
	private Integer sortNo;//排序号
	
	private String createTime;//添加时间
	
	private String remark;//备注
	
	private String parentAuthrityName;
	

	

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

	@Column(name = "authrity_name", length = 50)
	public String getAuthrityName() {
		return authrityName;
	}

	public void setAuthrityName(String authrityName) {
		this.authrityName = authrityName;
	}
	
	@Column(name = "parent_id", length = 32)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "authrity_url", length = 100)
	public String getAuthrityURL() {
		return authrityURL;
	}

	public void setAuthrityURL(String authrityURL) {
		this.authrityURL = authrityURL;
	}
	@Column(name = "authrity_module", length = 100)
	public Integer getAuthrityModule() {
		return authrityModule;
	}

	public void setAuthrityModule(Integer authrityModule) {
		this.authrityModule = authrityModule;
	}
	@Column(name = "sort_no", length = 10)
	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	@Column(name = "create_time", length = 30)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "remark", length = 50)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public MerchantsAuthority() {
		super();
	}
	
	@Transient
	public String getParentAuthrityName() {
		return parentAuthrityName;
	}

	public void setParentAuthrityName(String parentAuthrityName) {
		this.parentAuthrityName = parentAuthrityName;
	}
	
}
