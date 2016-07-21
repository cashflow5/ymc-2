package com.yougou.api.model.pojo;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_merchant_api")
public class Api implements java.io.Serializable {

	private static final long serialVersionUID = -7726859743296401196L;
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

	private ApiCategory apiCategory;
	private ApiVersion apiVersion;
	private ApiImplementor apiImplementor;

	private Set<ApiExample> apiExamples;
	private Set<ApiInputParam> apiInputParams;
	private Set<ApiOutputParam> apiOutputParams;
	private Set<ApiInterceptorMapper> apiInterceptorMappers;
	private Set<ApiErrorMapper> apiErrorMappers;

	public Api() {
	}

	public Api(String id) {
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

	@Column(name = "api_code", nullable = false, length = 32)
	public String getApiCode() {
		return this.apiCode;
	}

	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}

	@Column(name = "api_name", nullable = false, length = 128)
	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	@Column(name = "api_method", nullable = false, length = 128)
	public String getApiMethod() {
		return this.apiMethod;
	}

	public void setApiMethod(String apiMethod) {
		this.apiMethod = apiMethod;
	}

	@Column(name = "api_method_param_types", nullable = false, length = 512)
	public String getApiMethodParamTypes() {
		return apiMethodParamTypes;
	}

	public void setApiMethodParamTypes(String apiMethodParamTypes) {
		this.apiMethodParamTypes = apiMethodParamTypes;
	}

	@Column(name = "api_description", nullable = false, length = 256)
	public String getApiDescription() {
		return this.apiDescription;
	}

	public void setApiDescription(String apiDescription) {
		this.apiDescription = apiDescription;
	}

	@Column(name = "api_weight")
	public Long getApiWeight() {
		return apiWeight;
	}

	public void setApiWeight(Long apiWeight) {
		this.apiWeight = apiWeight;
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
	
	@Column(name = "is_enable", length = 1)
	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name = "is_save_result", length = 1)
	public String getIsSaveResult() {
		return isSaveResult;
	}

	public void setIsSaveResult(String isSaveResult) {
		this.isSaveResult = isSaveResult;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	public ApiCategory getApiCategory() {
		return apiCategory;
	}

	public void setApiCategory(ApiCategory apiCategory) {
		this.apiCategory = apiCategory;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "version_id")
	public ApiVersion getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(ApiVersion apiVersion) {
		this.apiVersion = apiVersion;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "implementor_id")
	public ApiImplementor getApiImplementor() {
		return apiImplementor;
	}

	public void setApiImplementor(ApiImplementor apiImplementor) {
		this.apiImplementor = apiImplementor;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "api")
	@OrderBy("orderNo")
	public Set<ApiInterceptorMapper> getApiInterceptorMappers() {
		return apiInterceptorMappers;
	}

	public void setApiInterceptorMappers(Set<ApiInterceptorMapper> apiInterceptorMappers) {
		this.apiInterceptorMappers = apiInterceptorMappers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "api")
	public Set<ApiErrorMapper> getApiErrorMappers() {
		return apiErrorMappers;
	}

	public void setApiErrorMappers(Set<ApiErrorMapper> apiErrorMappers) {
		this.apiErrorMappers = apiErrorMappers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "api")
	@OrderBy("exampleCategory")
	public Set<ApiExample> getApiExamples() {
		return apiExamples;
	}

	public void setApiExamples(Set<ApiExample> apiExamples) {
		this.apiExamples = apiExamples;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "api")
	@OrderBy("orderNo")
	public Set<ApiInputParam> getApiInputParams() {
		return apiInputParams;
	}

	public void setApiInputParams(Set<ApiInputParam> apiInputParams) {
		this.apiInputParams = apiInputParams;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "api")
	@OrderBy("orderNo")
	public Set<ApiOutputParam> getApiOutputParams() {
		return apiOutputParams;
	}

	public void setApiOutputParams(Set<ApiOutputParam> apiOutputParams) {
		this.apiOutputParams = apiOutputParams;
	}
}
