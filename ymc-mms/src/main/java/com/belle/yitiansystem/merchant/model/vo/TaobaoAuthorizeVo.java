package com.belle.yitiansystem.merchant.model.vo;

import java.io.Serializable;
import java.util.Date;

public class TaobaoAuthorizeVo implements Serializable {

	private static final long serialVersionUID = 1L;
	/** id **/
	private String id;
	/** 淘宝店铺昵称ID **/
	private String nid;
	/** 淘宝店铺-主账户名称 **/
	private String nickName;
	/** 淘宝APPKEY **/
	private String topAppkey;
	/** 淘宝店铺名称 **/
	private String taobaoShopName;
	/** 淘宝店铺URL **/
	private String taobaoShopUrl;
	/** 商家编码 **/
	private String merchantCode;
	/** 商家名称 **/
	private String merchantName;
	/** 商家申请淘宝授权状态(0:保存，1:申请APPKEY,2:审核通过,3:已授权,-1:禁用) **/
	private String status;
	private String statusName;
	/** 授权操作人员 **/
	private String operater;
	/** 授权回调创建时间 **/
	private String operated;
	/** 创建时间 **/
	private Date createTime;

	/** 创建开始时间 **/
	private String startTime;

	/** 创建结束时间 **/
	private String endTime;

	public String getStatusName() {
		if (status.equals("0")) {
			statusName = "保存";
		} else if (status.equals("1")) {
			statusName = "申请APPKEY";
		} else if (status.equals("2")) {
			statusName = "审核通过";
		} else if (status.equals("3")) {
			statusName = "已授权";
		} else if (status.equals("-1")) {
			statusName = "禁用";
		}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
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

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
