package com.yougou.kaidian.user.model.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SupplierContractAttachment {
	private String id;
	private String contractId;
	private String attachmentName;
	private String attachmentRealName;
	private String attachmentType;//附件类型 1：合同附件类型 2：资质附件类型 3：授权书附件类型 4:商标注册证附件类型
	private String supplierId;
	
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
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
