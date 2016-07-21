package com.belle.yitiansystem.taobao.model;

import java.io.Serializable;

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

}
