package com.yougou.kaidian.taobao.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 淘宝优购绑定类目
 * @author luo.hl
 *
 */
public class TaobaoYougouItemCatTemplet implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private Long taobaoCid;
	private String taobaoCatFullName;
	private String yougouCatNo;
	private String yougouCatStruct;
	private String yougouCatFullName;
	private String operater;
	private String operated;

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
