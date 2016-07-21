package com.belle.yitiansystem.taobao.model;

import java.io.Serializable;

public class TaobaoItemCatPropValue implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long cid;
	private Long pid;
	private Long vid;
	private String vName;
	private String isArtificialInput;
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

	public Long getVid() {
		return vid;
	}

	public void setVid(Long vid) {
		this.vid = vid;
	}

	public String getvName() {
		return vName;
	}

	public void setvName(String vName) {
		this.vName = vName;
	}

	public String getIsArtificialInput() {
		return isArtificialInput;
	}

	public void setIsArtificialInput(String isArtificialInput) {
		this.isArtificialInput = isArtificialInput;
	}

}
