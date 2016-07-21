package com.belle.yitiansystem.merchant.enums;

public enum SupplierContractAttachmentType {
	CONTRACT_ATTACHMENT_TYPE("1"),NATURAL_ATTACHMENT_TYPE("2"),AUTHORITY_ATTACHMENT_TYPE("3"),
	TRADEMARK_ATTACHMENT_TYPE("3");
	private String type;
	SupplierContractAttachmentType(String type){
		this.type = type;
	}
	public String getType(){
		return type;
	}
}
