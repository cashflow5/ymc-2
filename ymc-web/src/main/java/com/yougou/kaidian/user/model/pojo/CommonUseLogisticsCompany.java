package com.yougou.kaidian.user.model.pojo;  

import java.io.Serializable;
/**
 * ClassName: CommonUseLogisticsCompany
 * Desc: 常用物流公司
 * date: 2015-3-4 上午10:00:25
 * @author li.n1 
 * @since JDK 1.6
 */
public class CommonUseLogisticsCompany implements Serializable{
	/** 
	 * serialVersionUID:序列号 
	 * @since JDK 1.6 
	 */ 
	private static final long serialVersionUID = 1L;
	private String id;
	private String logisticCompanyCode;
	private String logisticsCompanyName;
	private String merchantCode;
	private int sortNo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogisticCompanyCode() {
		return logisticCompanyCode;
	}
	public void setLogisticCompanyCode(String logisticCompanyCode) {
		this.logisticCompanyCode = logisticCompanyCode;
	}
	public String getLogisticsCompanyName() {
		return logisticsCompanyName;
	}
	public void setLogisticsCompanyName(String logisticsCompanyName) {
		this.logisticsCompanyName = logisticsCompanyName;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	
}
