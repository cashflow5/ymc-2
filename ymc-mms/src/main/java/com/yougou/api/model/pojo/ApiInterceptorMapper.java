package com.yougou.api.model.pojo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("1")
public class ApiInterceptorMapper extends InterceptorMapper {

	private static final long serialVersionUID = 1554679822830619239L;
	
	private Api api;
	
	public ApiInterceptorMapper() {
		super();
	}

	public ApiInterceptorMapper(String id) {
		super(id);
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ref_id")
	public Api getApi() {
		return api;
	}

	public void setApi(Api api) {
		this.api = api;
	}

}
