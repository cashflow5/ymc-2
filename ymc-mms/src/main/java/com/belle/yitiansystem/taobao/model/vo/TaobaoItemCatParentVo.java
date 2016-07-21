package com.belle.yitiansystem.taobao.model.vo;  

import java.io.Serializable;

public class TaobaoItemCatParentVo implements Serializable{
	/** 
	 * serialVersionUID:序列号 
	 * @since JDK 1.6 
	 */ 
	private static final long serialVersionUID = 2226072907734401265L;
	private Long cid;
	private String name;
	private Boolean isParent;
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
