package com.yougou.kaidian.taobao.model.pojo;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.StringUtils;

public class ItemPropTemplate {
	private String id;
	private String templateId;
	private String propItemNo;
	private String propItemName;
	private String propValueNo;
	private String propValueName;
	private List<String> propValueNos;
	private String merchantCode;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getPropItemNo() {
		return propItemNo;
	}
	public void setPropItemNo(String propItemNo) {
		this.propItemNo = propItemNo;
	}
	public String getPropValueNo() {
		return propValueNo;
	}
	public void setPropValueNo(String propValueNo) {
		if(!StringUtils.isEmpty(propValueNo)){
			this.propValueNos = Arrays.asList(propValueNo.split(";"));
		}
		this.propValueNo = propValueNo;
	}
	public List<String> getPropValueNos() {
		return propValueNos;
	}
	public void setPropValueNos(List<String> propValueNos) {
		this.propValueNos = propValueNos;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getPropItemName() {
		return propItemName;
	}
	public void setPropItemName(String propItemName) {
		this.propItemName = propItemName;
	}
	public String getPropValueName() {
		return propValueName;
	}
	public void setPropValueName(String propValueName) {
		this.propValueName = propValueName;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
