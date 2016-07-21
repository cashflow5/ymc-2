package com.belle.yitiansystem.taobao.model;

import org.apache.commons.lang3.StringUtils;

/**
 * 淘宝品牌
 * @author zhuang.rb
 *
 */
public class TaobaoBrand implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String bid;
	private Long vid;
	private String name;
	private Long pid;
	private String propName;
	private String operater;
	private String operated;
	private String isArtificialInput;
	private String yougouBrandNo;
	private String haveBind = "0";
	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public Long getVid() {
		return vid;
	}

	public void setVid(Long vid) {
		this.vid = vid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
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

	public String getIsArtificialInput() {
		return isArtificialInput;
	}

	public void setIsArtificialInput(String isArtificialInput) {
		this.isArtificialInput = isArtificialInput;
	}

	public String getYougouBrandNo() {
		return yougouBrandNo;
	}

	public void setYougouBrandNo(String yougouBrandNo) {
		if(StringUtils.isEmpty(yougouBrandNo)){
			this.haveBind = "0";
		}else{
			this.haveBind = "1";
		}
		this.yougouBrandNo = yougouBrandNo;
	}

	public String getHaveBind() {
		return haveBind;
	}

	public void setHaveBind(String haveBind) {
		this.haveBind = haveBind;
	}
}
