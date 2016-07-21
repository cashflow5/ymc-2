package com.yougou.kaidian.taobao.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TaobaoItemPropValVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String propval;
	private Long pid;
	private String name;
	private String yougouCatNo;
	private String yougouPropItemNo;
	private String yougouPropValueNo;

	public String getPropval() {
		return propval;
	}

	public void setPropval(String propval) {
		this.propval = propval;
	}

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

	public String getYougouCatNo() {
		return yougouCatNo;
	}

	public void setYougouCatNo(String yougouCatNo) {
		this.yougouCatNo = yougouCatNo;
	}

	public String getYougouPropItemNo() {
		return yougouPropItemNo;
	}

	public void setYougouPropItemNo(String yougouPropItemNo) {
		this.yougouPropItemNo = yougouPropItemNo;
	}

	public String getYougouPropValueNo() {
		return yougouPropValueNo;
	}

	public void setYougouPropValueNo(String yougouPropValueNo) {
		this.yougouPropValueNo = yougouPropValueNo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
