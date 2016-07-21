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
public class ApiVersionInputParam extends InputParam {

	private static final long serialVersionUID = 3372222325916051479L;

	private ApiVersion apiVersion;

	public ApiVersionInputParam() {
		super();
	}

	public ApiVersionInputParam(String id) {
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

