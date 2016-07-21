package com.yougou.kaidian.taobao.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 淘宝店铺
 * @author li.m1
 *
 */
public class TaobaoShop implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private Long nid;
	private String nickName;
	private String topAppkey;
	private String taobaoShopName;
	private String taobaoShopUrl;
	private String merchantCode;
	private Integer status;
	private String operater;
	private String operated;
	private Date createTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getNid() {
		return nid;
	}
	public void setNid(Long nid) {
		this.nid = nid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getTopAppkey() {
		return topAppkey;
	}
	public void setTopAppkey(String topAppkey) {
		this.topAppkey = topAppkey;
	}
	public String getTaobaoShopName() {
		return taobaoShopName;
	}
	public void setTaobaoShopName(String taobaoShopName) {
		this.taobaoShopName = taobaoShopName;
	}
	public String getTaobaoShopUrl() {
		return taobaoShopUrl;
	}
	public void setTaobaoShopUrl(String taobaoShopUrl) {
		this.taobaoShopUrl = taobaoShopUrl;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
