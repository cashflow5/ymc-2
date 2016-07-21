/*
 * ���� com.yougou.kaidian.order.model.MerchantActiveCommodity
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

public class MerchantActiveCommodity {
    private String id;

    private String signupId;

    private String comodityNo;

    private Integer activePrice;

    private Integer couponAmount;

    private String creator;

    private Date createTime;
    
    private String comodityName;
    
    private String specName;
    
    private Double salePrice;
    
    private String isSupportCoupons;
    
    private String status;
    
    private String activeType;
    
    private String prodUrl;
    
    public String getProdUrl() {
		return prodUrl;
	}

	public void setProdUrl(String prodUrl) {
		this.prodUrl = prodUrl;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSignupId() {
        return signupId;
    }

    public void setSignupId(String signupId) {
        this.signupId = signupId == null ? null : signupId.trim();
    }

    public String getComodityNo() {
        return comodityNo;
    }

    public void setComodityNo(String comodityNo) {
        this.comodityNo = comodityNo == null ? null : comodityNo.trim();
    }

    public Integer getActivePrice() {
        return activePrice;
    }

    public void setActivePrice(Integer activePrice) {
        this.activePrice = activePrice;
    }

    public Integer getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Integer couponAmount) {
        this.couponAmount = couponAmount;
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
    

    public String getComodityName() {
		return comodityName;
	}

	public void setComodityName(String comodityName) {
		this.comodityName = comodityName;
	}

	public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public String getIsSupportCoupons() {
		return isSupportCoupons;
	}

	public void setIsSupportCoupons(String isSupportCoupons) {
		this.isSupportCoupons = isSupportCoupons;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActiveType() {
		return activeType;
	}

	public void setActiveType(String activeType) {
		this.activeType = activeType;
	}
	
}