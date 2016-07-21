package com.yougou.kaidian.taobao.model.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 商品属性模板
 * @author luo.hl
 *
 */
public class ItemTemplate {
	private String id;
	private String cateNo;
	private String title;
	private String merchantCode;
	private String operated;
	private String propNames;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCateNo() {
		return cateNo;
	}
	public void setCateNo(String cateNo) {
		this.cateNo = cateNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getOperated() {
		return operated;
	}
	public void setOperated(String operated) {
		this.operated = operated;
	}
	public String getPropNames() {
		return propNames;
	}
	public void setPropNames(String propNames) {
		this.propNames = propNames;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
