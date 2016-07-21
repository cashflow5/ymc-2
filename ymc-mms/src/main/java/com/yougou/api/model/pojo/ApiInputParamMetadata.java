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
@Table(name = "tbl_merchant_api_input_param_metadata")
public class ApiInputParamMetadata implements java.io.Serializable {

	private static final long serialVersionUID = -5834349214300862332L;
	private String id;
	private String expression;
	private String caseSensitive;
	private String trim;
	private String minValue;
	private String maxValue;
	private Integer minLength;
	private Integer maxLength;
	private String creator;
	private Date created;
	private String modifier;
	private Date modified;
	private Integer orderNo;

	private InputParam inputParam;
	private ApiValidator apiValidator;

	public ApiInputParamMetadata() {
	}
	
	public ApiInputParamMetadata(String id) {
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
	
	@Column(name = "expression", length = 128)
	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Column(name = "case_sensitive", length = 16)
	public String getCaseSensitive() {
		return this.caseSensitive;
	}

	public void setCaseSensitive(String caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	@Column(name = "trim", length = 16)
	public String getTrim() {
		return this.trim;
	}

	public void setTrim(String trim) {
		this.trim = trim;
	}

	@Column(name = "min_value", length = 32)
	public String getMinValue() {
		return minValue;
	}
	
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	@Column(name = "max_value", length = 32)
	public String getMaxValue() {
		return this.maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	@Column(name = "min_length")
	public Integer getMinLength() {
		return this.minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	@Column(name = "max_length")
	public Integer getMaxLength() {
		return this.maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
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

	@Column(name = "order_no")
	public Integer getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "input_param_id")
	public InputParam getInputParam() {
		return inputParam;
	}

	public void setInputParam(InputParam inputParam) {
		this.inputParam = inputParam;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "validator_id")
	public ApiValidator getApiValidator() {
		return apiValidator;
	}

	public void setApiValidator(ApiValidator apiValidator) {
		this.apiValidator = apiValidator;
	}
	
}
