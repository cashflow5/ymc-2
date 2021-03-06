package com.yougou.kaidian.taobao.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-25 上午10:40:55
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class TaobaoYougouItemPropValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private Long taobaoCid;
	private Long taobaoPid;
	private String taobaoVid;
	private String yougouCatNo;
	private String yougouPropItemNo;
	private String yougouPropValueNo;
	private String merchantCode;
	private Long nickId;
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
	public Long getTaobaoPid() {
		return taobaoPid;
	}
	public void setTaobaoPid(Long taobaoPid) {
		this.taobaoPid = taobaoPid;
	}
	public String getYougouCatNo() {
		return yougouCatNo;
	}
	public void setYougouCatNo(String yougouCatNo) {
		this.yougouCatNo = yougouCatNo;
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
	public String getTaobaoVid() {
		return taobaoVid;
	}
	public void setTaobaoVid(String taobaoVid) {
		this.taobaoVid = taobaoVid;
	}
	public String getYougouPropValueNo() {
		return yougouPropValueNo;
	}
	public void setYougouPropValueNo(String yougouPropValueNo) {
		this.yougouPropValueNo = yougouPropValueNo;
	}

	public Long getNickId() {
		return nickId;
	}
	public void setNickId(Long nickId) {
		this.nickId = nickId;
	}
	public String getYougouPropItemNo() {
		return yougouPropItemNo;
	}
	public void setYougouPropItemNo(String yougouPropItemNo) {
		this.yougouPropItemNo = yougouPropItemNo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
