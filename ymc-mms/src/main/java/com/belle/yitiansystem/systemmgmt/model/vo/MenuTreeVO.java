package com.belle.yitiansystem.systemmgmt.model.vo;


/**
 * 
 *@author yhb
 * 
 * @version 创建时间：2011-3-30 下午03:55:14
 */
public class MenuTreeVO {
	
	private String id;   
	private String name;   //名称
	private String parent; //父级
	private String url; 	//url
	private int order;  //排序
	private String isShow;  //是否显示
	private String remark;  //备注
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


}
