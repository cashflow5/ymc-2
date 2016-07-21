package com.belle.yitiansystem.merchant.model.vo;

import java.io.Serializable;

public class TaobaoAppkeyVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	// 淘宝APP名称
	private String topAppName;

	// 淘宝APPKEY
	private String topAppkey;

	// 淘宝SECRET
	private String topSecret;

	// 是否可用(0:否，1:是）
	private Integer isUseble;

	// 操作员
	private String operater;

	// 操作时间
	private String operated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTopAppName() {
		return topAppName;
	}

	public void setTopAppName(String topAppName) {
		this.topAppName = topAppName;
	}

	public String getTopAppkey() {
		return topAppkey;
	}

	public void setTopAppkey(String topAppkey) {
		this.topAppkey = topAppkey;
	}

	public String getTopSecret() {
		return topSecret;
	}

	public void setTopSecret(String topSecret) {
		this.topSecret = topSecret;
	}

	public Integer getIsUseble() {
		return isUseble;
	}

	public void setIsUseble(Integer isUseble) {
		this.isUseble = isUseble;
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

}
