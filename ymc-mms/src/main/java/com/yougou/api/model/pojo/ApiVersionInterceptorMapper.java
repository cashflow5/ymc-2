package com.yougou.api.model.pojo;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("2")
public class ApiVersionInterceptorMapper extends InterceptorMapper {

	private static final long serialVersionUID = 1554679822830619239L;
	
	private ApiVersion apiVersion;
	
	public ApiVersionInterceptorMapper() {
		super();
	}

	public ApiVersionInterceptorMapper(String id) {
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
