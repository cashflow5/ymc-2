package com.yougou.kaidian.taobao.model;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-8-4 上午10:34:43
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class TaobaoItemExtendYougouPropValue {
	private String id;
	private Long numIid;
	private String extendId;
	private String yougouPropItemNo;
	private String yougouPropItemName;
	private String yougouPropValueNo;
	private String yougouPropValueName;
	private String merchantCode;// 商家编码
	public Long getNumIid() {
		return numIid;
	}
	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}
	public String getYougouPropItemNo() {
		return yougouPropItemNo;
	}
	public void setYougouPropItemNo(String yougouPropItemNo) {
		this.yougouPropItemNo = yougouPropItemNo;
	}
	public String getYougouPropValueNo() {
		return yougouPropValueNo;
	}
	public void setYougouPropValueNo(String yougouPropValueNo) {
		this.yougouPropValueNo = yougouPropValueNo;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getYougouPropItemName() {
		return yougouPropItemName;
	}
	public void setYougouPropItemName(String yougouPropItemName) {
		this.yougouPropItemName = yougouPropItemName;
	}
	public String getYougouPropValueName() {
		return yougouPropValueName;
	}
	public void setYougouPropValueName(String yougouPropValueName) {
		this.yougouPropValueName = yougouPropValueName;
	}
	public String getExtendId() {
		return extendId;
	}
	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
