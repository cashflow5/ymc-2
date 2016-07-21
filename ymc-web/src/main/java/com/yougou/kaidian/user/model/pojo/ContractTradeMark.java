/*
 * 类名 com.yougou.merchant.api.supplier.vo.ContractTradeMark
 * 
 * 日期  Tue Jun 23 13:25:28 CST 2015
 * 
 * 版权声明Copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
package com.yougou.kaidian.user.model.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ContractTradeMark implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 56576646490534088L;
	
	//子商标
	private List<ContractTradeMarkSub> contractTradeMarkSubList ;
    private String id;

    private String contractId;

    private String trademark;

    private String authorizer;

    private String type;

    private String registeredTrademark;

    private Date registeredStartDate;

    private Date registeredEndDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark == null ? null : trademark.trim();
    }

    public String getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(String authorizer) {
        this.authorizer = authorizer == null ? null : authorizer.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRegisteredTrademark() {
        return registeredTrademark;
    }

    public void setRegisteredTrademark(String registeredTrademark) {
        this.registeredTrademark = registeredTrademark == null ? null : registeredTrademark.trim();
    }

    public Date getRegisteredStartDate() {
        return registeredStartDate;
    }

    public void setRegisteredStartDate(Date registeredStartDate) {
        this.registeredStartDate = registeredStartDate;
    }

    public List<ContractTradeMarkSub> getContractTradeMarkSubList() {
		return contractTradeMarkSubList;
	}

	public void setContractTradeMarkSubList(
			List<ContractTradeMarkSub> contractTradeMarkSubList) {
		this.contractTradeMarkSubList = contractTradeMarkSubList;
	}

	public Date getRegisteredEndDate() {
        return registeredEndDate;
    }

    public void setRegisteredEndDate(Date registeredEndDate) {
        this.registeredEndDate = registeredEndDate;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}