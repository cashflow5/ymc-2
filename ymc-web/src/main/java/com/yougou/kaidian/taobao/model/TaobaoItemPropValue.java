package com.yougou.kaidian.taobao.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TaobaoItemPropValue implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long vid;
	private String name;
	private Boolean isParent;
	private String operater;
	private String operated;

	public Long getVid() {
		return vid;
	}

	public void setVid(Long vid) {
		this.vid = vid;
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
