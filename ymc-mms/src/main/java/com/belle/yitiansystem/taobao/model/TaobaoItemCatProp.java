package com.belle.yitiansystem.taobao.model;

import java.io.Serializable;

public class TaobaoItemCatProp implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long cid;
	private Long pid;
	private String name;
	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
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

}
