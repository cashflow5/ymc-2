/*
 * 类名 com.yougou.merchant.api.supplier.vo.ContractTradeMarkSub
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
package com.belle.yitiansystem.merchant.model.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ContractTradeMarkSub implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 887585358838L;
    private String id;

    private String contractId;

    private String trademarkId;

    private String beAuthorizer;

    private Integer level;

    private Date authorizStartdate;

    private Date authorizEnddate;

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

    public String getTrademarkId() {
        return trademarkId;
    }

    public void setTrademarkId(String trademarkId) {
        this.trademarkId = trademarkId == null ? null : trademarkId.trim();
    }

    public String getBeAuthorizer() {
        return beAuthorizer;
    }

    public void setBeAuthorizer(String beAuthorizer) {
        this.beAuthorizer = beAuthorizer == null ? null : beAuthorizer.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getAuthorizStartdate() {
        return authorizStartdate;
    }

    public void setAuthorizStartdate(Date authorizStartdate) {
        this.authorizStartdate = authorizStartdate;
    }

    public Date getAuthorizEnddate() {
        return authorizEnddate;
    }

    public void setAuthorizEnddate(Date authorizEnddate) {
        this.authorizEnddate = authorizEnddate;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}