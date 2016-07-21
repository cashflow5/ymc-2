/*
 * com.yougou.kaidian.order.model.MerchantOrderExpand
 * 
 * Tue Sep 22 13:49:39 CST 2015
 * 
 * Copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * 招商自建表，存储订单备注
 * 
 */
package com.yougou.kaidian.order.model;

import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MerchantOrderExpand {
   
	private String id;

    private String orderSubId;

    private String merchantCode;

    private Date createTime;

    private Date updateTime;

    private String markColor;

    private String markNote;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderSubId() {
        return orderSubId;
    }

    public void setOrderSubId(String orderSubId) {
        this.orderSubId = orderSubId == null ? null : orderSubId.trim();
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
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

    public String getMarkColor() {
        return markColor;
    }

    public void setMarkColor(String markColor) {
        this.markColor = markColor == null ? null : markColor.trim();
    }

    public String getMarkNote() {
        return markNote;
    }

    public void setMarkNote(String markNote) {
        this.markNote = markNote == null ? null : markNote.trim();
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}