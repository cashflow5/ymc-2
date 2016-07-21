package com.yougou.kaidian.taobao.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TaobaoItemCatPropDto extends TaobaoItemCatProp
		implements
			Serializable {
	private String propBindId;
	private String yougouPropItemNo;
	public String getPropBindId() {
		return propBindId;
	}
	public void setPropBindId(String propBindId) {
		this.propBindId = propBindId;
	}
	public String getYougouPropItemNo() {
		return yougouPropItemNo;
	}
	public void setYougouPropItemNo(String yougouPropItemNo) {
		this.yougouPropItemNo = yougouPropItemNo;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
