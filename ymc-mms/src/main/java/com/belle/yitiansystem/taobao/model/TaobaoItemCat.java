package com.belle.yitiansystem.taobao.model;

import java.io.Serializable;
import java.util.List;

/**
 * 淘宝类目
 * @author zhuang.rb
 *
 */
public class TaobaoItemCat implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long cid;
	private Long parentCid;
	private String name;
	private Boolean isParent;
	private String status;
	private Long sortOrder;
	private String operater;
	private String operated;
	//父节点名称
	private String parentName;
	//子节点（一级）
	private List<TaobaoItemCat> children;

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Long getParentCid() {
		return parentCid;
	}

	public void setParentCid(Long parentCid) {
		this.parentCid = parentCid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	public String getOperated() {
		return operated;
	}

	public void setOperated(String operated) {
		this.operated = operated;
	}

	public List<TaobaoItemCat> getChildren() {
		return children;
	}

	public void setChildren(List<TaobaoItemCat> children) {
		this.children = children;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}
