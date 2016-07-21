package com.yougou.api.model.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_merchant_api_license")
public class ApiLicense implements java.io.Serializable {

	private static final long serialVersionUID = 6625038197750917719L;
	private String id;
	private ApiKey apiKey;
	private String licensor;
	private Date licensed;

	private Api api;

	public ApiLicense() {
	}
	
	public ApiLicense(String id) {
		this.id = id;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@GeneratedValue(generator = "generator")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "api_id")
	public Api getApi() {
		return this.api;
	}

	public void setApi(Api api) {
		this.api = api;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "key_id")
	public ApiKey getApiKey() {
		return apiKey;
	}

	public void setApiKey(ApiKey apiKey) {
		this.apiKey = apiKey;
	}

	@Column(name = "licensor", nullable = false, length = 32)
	public String getLicensor() {
		return this.licensor;
	}

	public void setLicensor(String licensor) {
		this.licensor = licensor;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "licensed", nullable = false, length = 19)
	public Date getLicensed() {
		return this.licensed;
	}

	public void setLicensed(Date licensed) {
		this.licensed = licensed;
	}

}
