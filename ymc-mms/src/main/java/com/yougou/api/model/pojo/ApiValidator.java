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
@Table(name = "tbl_merchant_api_validator")
public class ApiValidator implements java.io.Serializable {

	private static final long serialVersionUID = -6669602325402489109L;
	private String id;
	private String identifier;
	private String validatorClass;
    private String messagePattern;
    private String messageKey;
    private String description;
	private String creator;
	private Date created;
	private String modifier;
	private Date modified;
	
	private Set<ApiInputParamMetadata> apiInputParamMetadatas;

	public ApiValidator() {
	}
	
	public ApiValidator(String id) {
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

	@Column(name = "identifier", unique = true, nullable = false, length = 128)
	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Column(name = "validator_class", nullable = false, length = 256)
	public String getValidatorClass() {
		return this.validatorClass;
	}

	public void setValidatorClass(String validatorClass) {
		this.validatorClass = validatorClass;
	}

	@Column(name = "message_pattern", length = 128)
	public String getMessagePattern() {
		return messagePattern;
	}

	public void setMessagePattern(String messagePattern) {
		this.messagePattern = messagePattern;
	}

	@Column(name = "message_key", length = 128)
	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	@Column(name = "description", length = 256)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "apiValidator")
	@OrderBy("inputParam")
	public Set<ApiInputParamMetadata> getApiInputParamMetadatas() {
		return apiInputParamMetadatas;
	}

	public void setApiInputParamMetadatas(Set<ApiInputParamMetadata> apiInputParamMetadatas) {
		this.apiInputParamMetadatas = apiInputParamMetadatas;
	}

}
