package com.yougou.kaidian.user.model.pojo;

import java.io.Serializable;



/**
 * 商家权限列表类
 * @author wang.m
 *
 */
public class MerchantsAuthority implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String authrityName;//权限名称
	
	private String authrityURL;//url
	
	private Integer authrityModule;//所属模块   1 商品模块  2 订单模3 库存 4结算 4 设置
	
	private Integer sortNo;//排序号
	
	private String createTime;//添加时间
	
	private String remark;//备注
	
	private String parentAuthrityId;
	
	private String parentAuthrityName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getAuthrityModule() {
		return authrityModule;
	}

	public void setAuthrityModule(Integer authrityModule) {
		this.authrityModule = authrityModule;
	}

	public String getAuthrityURL() {
		return authrityURL;
	}

	public void setAuthrityURL(String authrityURL) {
		this.authrityURL = authrityURL;
	}
	public String getAuthrityName() {
		return authrityName;
	}

	public void setAuthrityName(String authrityName) {
		this.authrityName = authrityName;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getParentAuthrityId() {
		return parentAuthrityId;
	}

	public void setParentAuthrityId(String parentAuthrityId) {
		this.parentAuthrityId = parentAuthrityId;
	}

	public String getParentAuthrityName() {
		return parentAuthrityName;
	}

	public void setParentAuthrityName(String parentAuthrityName) {
		this.parentAuthrityName = parentAuthrityName;
	}

	public MerchantsAuthority(String id, String authrityName, Integer sortNo,
			String createTime, String remark) {
		super();
		this.id = id;
		this.authrityName = authrityName;
		this.sortNo = sortNo;
		this.createTime = createTime;
		this.remark = remark;
	}

	public MerchantsAuthority() {
		super();
	}
	

	
}
