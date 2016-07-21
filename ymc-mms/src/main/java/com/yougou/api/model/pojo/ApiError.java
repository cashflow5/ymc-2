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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_merchant_api_error")
public class ApiError implements java.io.Serializable {

	private static final long serialVersionUID = 5177040827923393971L;
	private String id;
	private String errorCode;
	private String errorDescription;
	private String errorSolution;
	private Integer orderNo;
	private String creator;
	private Date created;
	private String modifier;
	private Date modified;
	
	private Set<ApiErrorMapper> apiErrorMappers;

	public ApiError() {
	}
	
	public ApiError(String id) {
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

	@Column(name = "error_code", nullable = false, length = 32)
	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Column(name = "error_description", nullable = false, length = 256)
	public String getErrorDescription() {
		return this.errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	@Column(name = "error_solution", nullable = false, length = 1024)
	public String getErrorSolution() {
		return this.errorSolution;
	}

	public void setErrorSolution(String errorSolution) {
		this.errorSolution = errorSolution;
	}

	@Column(name = "order_no")
	public Integer getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "apiError")
	public Set<ApiErrorMapper> getApiErrorMappers() {
		return apiErrorMappers;
	}

	public void setApiErrorMappers(Set<ApiErrorMapper> apiErrorMappers) {
		this.apiErrorMappers = apiErrorMappers;
	}
}
