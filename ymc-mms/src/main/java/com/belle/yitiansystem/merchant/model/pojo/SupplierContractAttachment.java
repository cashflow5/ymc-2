package com.belle.yitiansystem.merchant.model.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SupplierContractAttachment implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -7439809857907747164L;

	private String id;
	private String contractId;
	private String attachmentName;
	private String attachmentRealName;
	private String attachmentType;//附件类型 1：合同附件类型 2：资质附件类型 3：授权书附件类型 4:商标注册证附件类型
	private String supplierId;
	private String deleteFlag;// 供页面Form表单使用的删除标志位
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public String getAttachmentRealName() {
		return attachmentRealName;
	}
	public void setAttachmentRealName(String attachmentRealName) {
		this.attachmentRealName = attachmentRealName;
	}
	public String getAttachmentType() {
		return attachmentType;
	}
	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
