package com.yougou.kaidian.taobao.model;

import org.apache.commons.lang.builder.ToStringBuilder;


public class TaobaoAuthinfoDto extends TaobaoAuthinfo {
	private Long cid;
	private String appSecret;
	private Long nickId;

	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Long getNickId() {
		return nickId;
	}
	public void setNickId(Long nickId) {
		this.nickId = nickId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}