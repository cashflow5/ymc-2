package com.yougou.kaidian.taobao.model;

import org.apache.commons.lang.builder.ToStringBuilder;

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
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
