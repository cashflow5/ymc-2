package com.belle.yitiansystem.taobao.model;

import java.io.Serializable;

public class TaobaoItemCatPropDto extends TaobaoItemCatProp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
}
