package com.yougou.kaidian.taobao.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TaobaoItemPropImg implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String propId;
	private Long numIid;
	private Long id;
	private String url;
	private String properties;
	private Long position;

	public String getPropId() {
		return propId;
	}

	public void setPropId(String propId) {
		this.propId = propId;
	}

	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
