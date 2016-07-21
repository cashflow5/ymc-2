package com.yougou.kaidian.taobao.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 淘宝优购绑定类目
 * @author zhuang.rb
 *
 */
public class TaobaoYougouItemCat implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private Long taobaoCid;
	private String taobaoCatFullName;
	private String yougouCatNo;
	private String yougouCatStruct;
	private String yougouCatFullName;
	private Long nickId;
	private String merchantCode;
	private String operater;
	private String operated;

	private String nickName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getTaobaoCid() {
		return taobaoCid;
	}

	public void setTaobaoCid(Long taobaoCid) {
		this.taobaoCid = taobaoCid;
	}

	public String getYougouCatNo() {
		return yougouCatNo;
	}

	public void setYougouCatNo(String yougouCatNo) {
		this.yougouCatNo = yougouCatNo;
	}

	public String getYougouCatStruct() {
		return yougouCatStruct;
	}

	public void setYougouCatStruct(String yougouCatStruct) {
		this.yougouCatStruct = yougouCatStruct;
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

	public String getTaobaoCatFullName() {
		return taobaoCatFullName;
	}

	public void setTaobaoCatFullName(String taobaoCatFullName) {
		this.taobaoCatFullName = taobaoCatFullName;
	}

	public String getYougouCatFullName() {
		return yougouCatFullName;
	}

	public void setYougouCatFullName(String yougouCatFullName) {
		this.yougouCatFullName = yougouCatFullName;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
