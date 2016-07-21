package com.yougou.api.model.pojo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("1")
public class ApiInputParam extends InputParam {

	private static final long serialVersionUID = -429767461411166571L;

	private Api api;

	public ApiInputParam() {
		super();
	}

	public ApiInputParam(String id) {
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
