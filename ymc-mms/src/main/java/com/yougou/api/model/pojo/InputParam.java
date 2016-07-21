package com.yougou.api.model.pojo;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "tbl_merchant_api_input_param")
@DiscriminatorColumn(name = "ref_type", discriminatorType = DiscriminatorType.STRING)
public abstract class InputParam implements java.io.Serializable {

	private static final long serialVersionUID = -1995839393213459001L;
	private String id;
	private String paramName;
	private String paramDataType;
	private String isRequired;
	private String paramExampleData;
	private String paramDefaultData;
	private String paramDescription;
	private Integer orderNo;
	private String creator;
	private Date created;
	private String modifier;
	private Date modified;

	private Set<ApiInputParamMetadata> apiInputParamMetadatas;

	public InputParam() {
	}
	
	public InputParam(String id) {
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

	@Column(name = "param_name", nullable = false, length = 32)
	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	@Column(name = "param_data_type", nullable = false, length = 32)
	public String getParamDataType() {
		return this.paramDataType;
	}

	public void setParamDataType(String paramDataType) {
		this.paramDataType = paramDataType;
	}

	@Column(name = "is_required", nullable = false, length = 1)
	public String getIsRequired() {
		return this.isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	@Column(name = "param_example_data", length = 128)
	public String getParamExampleData() {
		return this.paramExampleData;
	}

	public void setParamExampleData(String paramExampleData) {
		this.paramExampleData = paramExampleData;
	}

	@Column(name = "param_default_data", length = 128)
	public String getParamDefaultData() {
		return this.paramDefaultData;
	}

	public void setParamDefaultData(String paramDefaultData) {
		this.paramDefaultData = paramDefaultData;
	}

	@Column(name = "param_description", length = 256)
	public String getParamDescription() {
		return this.paramDescription;
	}

	public void setParamDescription(String paramDescription) {
		this.paramDescription = paramDescription;
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
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "inputParam")
	@OrderBy("orderNo")
	public Set<ApiInputParamMetadata> getApiInputParamMetadatas() {
		return apiInputParamMetadatas;
	}

	public void setApiInputParamMetadatas(Set<ApiInputParamMetadata> apiInputParamMetadatas) {
		this.apiInputParamMetadatas = apiInputParamMetadatas;
	}
	
	@Transient
	public Object getRefer() {
		try {
			Method[] methods = this.getClass().getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				JoinColumn annotation = methods[i].getAnnotation(JoinColumn.class);
				if (annotation != null && "ref_id".equalsIgnoreCase(annotation.name())) {
					return methods[i].invoke(this);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}
	
	@Transient
	@SuppressWarnings("unchecked")
	public <T> T getReferAs(Class<T> clazz) {
		return (T) getRefer();
	}

	@Transient
	public String getReferType() {
		return this.getClass().getAnnotation(DiscriminatorValue.class).value();
	}
}
