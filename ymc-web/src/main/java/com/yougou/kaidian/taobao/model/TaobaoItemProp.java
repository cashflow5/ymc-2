package com.yougou.kaidian.taobao.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 淘宝商品属性
 * 
 * @author zhuang.rb
 *
 */
public class TaobaoItemProp implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long pid;
	private String name;
	private Boolean must;
	private Boolean multi;
	private String status;
	private Long sortOrder;
	private Long parentPid;
	private Long parentVid;
	private Boolean isKeyProp;
	private Boolean isSaleProp;
	private Boolean isColorProp;
	private Boolean isEnumProp;
	private Boolean isItemProp;
	private Boolean isAllowAlias;
	private Boolean isInputProp;
	private String operater;
	private String operated;

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getMust() {
		return must;
	}

	public void setMust(Boolean must) {
		this.must = must;
	}

	public Boolean getMulti() {
		return multi;
	}

	public void setMulti(Boolean multi) {
		this.multi = multi;
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

	public Long getParentPid() {
		return parentPid;
	}

	public void setParentPid(Long parentPid) {
		this.parentPid = parentPid;
	}

	public Long getParentVid() {
		return parentVid;
	}

	public void setParentVid(Long parentVid) {
		this.parentVid = parentVid;
	}

	public Boolean getIsKeyProp() {
		return isKeyProp;
	}

	public void setIsKeyProp(Boolean isKeyProp) {
		this.isKeyProp = isKeyProp;
	}

	public Boolean getIsSaleProp() {
		return isSaleProp;
	}

	public void setIsSaleProp(Boolean isSaleProp) {
		this.isSaleProp = isSaleProp;
	}

	public Boolean getIsColorProp() {
		return isColorProp;
	}

	public void setIsColorProp(Boolean isColorProp) {
		this.isColorProp = isColorProp;
	}

	public Boolean getIsEnumProp() {
		return isEnumProp;
	}

	public void setIsEnumProp(Boolean isEnumProp) {
		this.isEnumProp = isEnumProp;
	}

	public Boolean getIsItemProp() {
		return isItemProp;
	}

	public void setIsItemProp(Boolean isItemProp) {
		this.isItemProp = isItemProp;
	}

	public Boolean getIsAllowAlias() {
		return isAllowAlias;
	}

	public void setIsAllowAlias(Boolean isAllowAlias) {
		this.isAllowAlias = isAllowAlias;
	}

	public Boolean getIsInputProp() {
		return isInputProp;
	}

	public void setIsInputProp(Boolean isInputProp) {
		this.isInputProp = isInputProp;
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
