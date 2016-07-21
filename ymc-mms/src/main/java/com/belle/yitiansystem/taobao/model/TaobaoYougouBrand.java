package com.belle.yitiansystem.taobao.model;

import java.io.Serializable;

public class TaobaoYougouBrand implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String taobaoBid;
	private String taobaoBrandName;
	private String yougouBrandNo;
	private String yougouBrandName;
	private String operater;
	private String operated;
	private String vid;
	
	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaobaoBid() {
		return taobaoBid;
	}

	public void setTaobaoBid(String taobaoBid) {
		this.taobaoBid = taobaoBid;
	}

	public String getYougouBrandNo() {
		return yougouBrandNo;
	}

	public void setYougouBrandNo(String yougouBrandNo) {
		this.yougouBrandNo = yougouBrandNo;
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

	public String getTaobaoBrandName() {
		return taobaoBrandName;
	}

	public void setTaobaoBrandName(String taobaoBrandName) {
		this.taobaoBrandName = taobaoBrandName;
	}

	public String getYougouBrandName() {
		return yougouBrandName;
	}

	public void setYougouBrandName(String yougouBrandName) {
		this.yougouBrandName = yougouBrandName;
	}

}
