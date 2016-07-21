/*
 * 类名 com.yougou.merchant.api.supplier.vo.ContractAttachment
 * 
 * 日期  Tue Jul 07 14:14:46 CST 2015
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

import org.apache.commons.lang.builder.ToStringBuilder;
/*****
 * 商家附件表  资质
 * @author le.sm
 * @date 2015/07/06
 *@version 1.0
 */
public class ContractAttachment implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 56576647190598088L;
	

	private String id;

    private String contractId;

    private String attachmentName;

    private String attachmentRealName;

    private String attachmentType;

    private String supplierId;

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

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName == null ? null : attachmentName.trim();
    }

    public String getAttachmentRealName() {
        return attachmentRealName;
    }

    public void setAttachmentRealName(String attachmentRealName) {
        this.attachmentRealName = attachmentRealName == null ? null : attachmentRealName.trim();
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType == null ? null : attachmentType.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}