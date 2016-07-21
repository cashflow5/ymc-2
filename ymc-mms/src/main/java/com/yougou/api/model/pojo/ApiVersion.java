package com.yougou.api.model.pojo;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_merchant_api_version")
public class ApiVersion implements java.io.Serializable {

	private static final long serialVersionUID = -5156065992614279126L;
	private String id;
	private String versionNo;
	private String creator;
	private Date created;
	private String modifier;
	private Date modified;
	private String description;
	
	private Set<Api> apis;
	private Set<ApiVersionInputParam> apiVersionInputParams;
	private Set<ApiVersionOutputParam> apiVersionOutputParams;
	private Set<ApiVersionInterceptorMapper> apiVersionInterceptorMappers;

	public ApiVersion() {
	}
	
	public ApiVersion(String id) {
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

	@Column(name = "version_no", nullable = false, length = 32)
	public String getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	@Column(name = "creator", nullable = false, length = 32)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 19)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(name = "modifier", length = 32)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", length = 19)
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	@Column(name = "description", length = 256)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "apiVersion")
	@OrderBy("created desc")
	public Set<Api> getApis() {
		return apis;
	}

	public void setApis(Set<Api> apis) {
		this.apis = apis;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "apiVersion")
	@OrderBy("orderNo")
	public Set<ApiVersionInputParam> getApiVersionInputParams() {
		return apiVersionInputParams;
	}

	public void setApiVersionInputParams(Set<ApiVersionInputParam> apiVersionInputParams) {
		this.apiVersionInputParams = apiVersionInputParams;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "apiVersion")
	@OrderBy("orderNo")
	public Set<ApiVersionOutputParam> getApiVersionOutputParams() {
		return apiVersionOutputParams;
	}

	public void setApiVersionOutputParams(Set<ApiVersionOutputParam> apiVersionOutputParams) {
		this.apiVersionOutputParams = apiVersionOutputParams;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "apiVersion")
	@OrderBy("orderNo")
	public Set<ApiVersionInterceptorMapper> getApiVersionInterceptorMappers() {
		return apiVersionInterceptorMappers;
	}

	public void setApiVersionInterceptorMappers(Set<ApiVersionInterceptorMapper> apiVersionInterceptorMappers) {
		this.apiVersionInterceptorMappers = apiVersionInterceptorMappers;
	}

}
