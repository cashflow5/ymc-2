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
@Table(name = "tbl_merchant_api_example")
public class ApiExample implements java.io.Serializable {

	private static final long serialVersionUID = 4960196120912738713L;
	private String id;
	private String exampleCategory;
	private String exampleData;
	private String creator;
	private Date created;
	private String modifier;
	private Date modified;
	
	private Api api;

	public ApiExample() {
	}
	
	public ApiExample(String id) {
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

	@Column(name = "example_category", nullable = false, length = 16)
	public String getExampleCategory() {
		return this.exampleCategory;
	}

	public void setExampleCategory(String exampleCategory) {
		this.exampleCategory = exampleCategory;
	}

	@Column(name = "example_data", nullable = false, length = 65535)
	public String getExampleData() {
		return this.exampleData;
	}

	public void setExampleData(String exampleData) {
		this.exampleData = exampleData;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "api_id")
	public Api getApi() {
		return api;
	}

	public void setApi(Api api) {
		this.api = api;
	}

}
