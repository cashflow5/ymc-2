/*
 * ���� com.yougou.kaidian.order.model.MerchantActiveSignup
 * 
 * ����  Tue Oct 13 09:44:01 CST 2015
 * 
 * ��Ȩ����Copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
package com.yougou.kaidian.active.vo;

import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MerchantActiveSignup {
    private String id;

    private String merchantCode;

    private String merchantName;

    private String activeId;

    private String activeName;

    private Short status;

    private String auditRemark;

    private String creator;

    private Date createTime;

    private Date updateTime;
    
    private Short activeType;
    
    private Date signUpStartTime;
    
    private Date signUpEndTime;
    
    private Date startTime;

    private Date endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getActiveId() {
        return activeId;
    }

    public void setActiveId(String activeId) {
        this.activeId = activeId == null ? null : activeId.trim();
    }

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName == null ? null : activeName.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark == null ? null : auditRemark.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

	public Short getActiveType() {
		return activeType;
	}

	public void setActiveType(Short activeType) {
		this.activeType = activeType;
	}

	public Date getSignUpStartTime() {
		return signUpStartTime;
	}

	public void setSignUpStartTime(Date signUpStartTime) {
		this.signUpStartTime = signUpStartTime;
	}

	public Date getSignUpEndTime() {
		return signUpEndTime;
	}

	public void setSignUpEndTime(Date signUpEndTime) {
		this.signUpEndTime = signUpEndTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
    
}