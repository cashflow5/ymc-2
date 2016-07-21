package com.yougou.api.model.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_merchant_api_error_mapper")
public class ApiErrorMapper implements Serializable {

	private static final long serialVersionUID = -6785298426110243517L;
	private String id;
	private Api api;//API
	private ApiError apiError;//API错误

	public ApiErrorMapper() {
	}

	public ApiErrorMapper(String id) {
		this.id = id;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@GeneratedValue(generator = "generator")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "api_id")
	public Api getApi() {
		return api;
	}

	public void setApi(Api api) {
		this.api = api;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "error_id")
	public ApiError getApiError() {
		return apiError;
	}

	public void setApiError(ApiError apiError) {
		this.apiError = apiError;
	}
}
