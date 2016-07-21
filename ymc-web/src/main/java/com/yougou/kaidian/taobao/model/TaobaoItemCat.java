package com.yougou.kaidian.taobao.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
