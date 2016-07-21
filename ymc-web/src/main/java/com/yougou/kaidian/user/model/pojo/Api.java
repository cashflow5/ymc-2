package com.yougou.kaidian.user.model.pojo;  

import java.io.Serializable;
import java.util.Date;

public class Api implements Serializable{

	private static final long serialVersionUID = 1526635767697623810L;
	
	private String id;
	private String apiCode;
	private String apiName;
	private String apiMethod;
	private String apiMethodParamTypes; 
	private String apiDescription;
	private Long apiWeight;
	private String creator;
	private Date created;
	private String modifier;
	private Date modified;
	private String isEnable;
	private String isSaveResult;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApiCode() {
		return apiCode;
	}
	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getApiMethod() {
		return apiMethod;
	}
	public void setApiMethod(String apiMethod) {
		this.apiMethod = apiMethod;
	}
	public String getApiMethodParamTypes() {
		return apiMethodParamTypes;
	}
	public void setApiMethodParamTypes(String apiMethodParamTypes) {
		this.apiMethodParamTypes = apiMethodParamTypes;
	}
	public String getApiDescription() {
		return apiDescription;
	}
	public void setApiDescription(String apiDescription) {
		this.apiDescription = apiDescription;
	}
	public Long getApiWeight() {
		return apiWeight;
	}
	public void setApiWeight(Long apiWeight) {
		this.apiWeight = apiWeight;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public String getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
	public String getIsSaveResult() {
		return isSaveResult;
	}
	public void setIsSaveResult(String isSaveResult) {
		this.isSaveResult = isSaveResult;
	}
}
