package com.yougou.kaidian.taobao.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TaobaoItemCatPropValueDto extends TaobaoItemCatPropValue implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String propValBindId;

	private String yougouPropValueNo;

	public String getPropValBindId() {
		return propValBindId;
	}

	public void setPropValBindId(String propValBindId) {
		this.propValBindId = propValBindId;
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
