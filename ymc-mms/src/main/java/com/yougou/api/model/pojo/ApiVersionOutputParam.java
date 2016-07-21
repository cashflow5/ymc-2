package com.yougou.api.model.pojo;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * 
 * @author 杨梦清
 * 
 */
@Entity
@DiscriminatorValue("2")
public class ApiVersionOutputParam extends OutputParam  {

	private static final long serialVersionUID = -2415484054781406759L;
	
	private ApiVersion apiVersion;

	public ApiVersionOutputParam() {
		super();
	}

	public ApiVersionOutputParam(String id) {
		super(id);
	}

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "ref_id")
	public ApiVersion getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(ApiVersion apiVersion) {
		this.apiVersion = apiVersion;
	}
}

