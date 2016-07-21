package com.yougou.kaidian.taobao.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TaobaoYougouBrand implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String taobaoBid;
	private String yougouBrandNo;
	private String yougouBrandName;
	private Long nickId;
	private String merchantCode;
	private String operater;
	private String operated;

	private String nickName;

	private String taobaoBrandName;

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

	public Long getNickId() {
		return nickId;
	}

	public void setNickId(Long nickId) {
		this.nickId = nickId;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
