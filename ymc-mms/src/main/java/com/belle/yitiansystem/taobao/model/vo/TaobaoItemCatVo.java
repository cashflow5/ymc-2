package com.belle.yitiansystem.taobao.model.vo;  

import java.io.Serializable;

public class TaobaoItemCatVo implements Serializable{

	/** 
	 * serialVersionUID:序列号 
	 * @since JDK 1.6 
	 */ 
	private static final long serialVersionUID = 8089033803195244357L;
	private String cid;//分类编码
	private String name;//分类名称
	
	private String rootCatCode;	//一级分类编码
	private String secondCatCode;	//二级分类编码
	
	public String getRootCatCode() {
		return rootCatCode;
	}
	public void setRootCatCode(String rootCatCode) {
		this.rootCatCode = rootCatCode;
	}
	public String getSecondCatCode() {
		return secondCatCode;
	}
	public void setSecondCatCode(String secondCatCode) {
		this.secondCatCode = secondCatCode;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
