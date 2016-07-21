package com.yougou.kaidian.taobao.model;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 淘宝商品对应的优购的属性
 * 
 * @author luo.hl
 * @date 2014-8-4 上午10:34:08
 * @version 0.1.0
 * @copyright yougou.com
 */
public class TaobaoItemExtendYougouProp {
	private String id;
	private Long numIid;
	private String yougouPropItemNo;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
