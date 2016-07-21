package com.yougou.kaidian.taobao.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TaobaoItemImg implements Serializable {

	private static final long serialVersionUID = 1L;

	private String imgId;
	private Long numIid;
	private Long id;
	private String url;
	private Long position;
	private String yougouUrl;
	private String yougouUrlThumbnail;
	private String extendId;

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
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

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public String getYougouUrl() {
		return yougouUrl;
	}

	public void setYougouUrl(String yougouUrl) {
		this.yougouUrl = yougouUrl;
	}

	public String getExtendId() {
		return extendId;
	}

	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}

	public String getYougouUrlThumbnail() {
		return yougouUrlThumbnail;
	}

	public void setYougouUrlThumbnail(String yougouUrlThumbnail) {
		this.yougouUrlThumbnail = yougouUrlThumbnail;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
