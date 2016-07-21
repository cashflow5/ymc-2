package com.yougou.api.model.pojo;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.yougou.api.beans.AppType;

@Entity
@Table(name = "tbl_merchant_api_category")
public class ApiCategory implements java.io.Serializable {

	private static final long serialVersionUID = 7579848379959822114L;
	private String id;
	private String categoryCode;
	private String categoryName;
	private String categoryDescription;
	private AppType ownership;
	private String creator;
	private Date created;
	private String modifier;
	private Date modified;
	
	private Set<Api> apis;

	public ApiCategory() {
	}
	
	public ApiCategory(String id) {
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

	@Column(name = "category_code", unique = true, nullable = false, length = 32)
	public String getCategoryCode() {
		return this.categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	@Column(name = "category_name", nullable = false, length = 32)
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name = "category_description", nullable = false, length = 256)
	public String getCategoryDescription() {
		return this.categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "ownership", nullable = false, length = 32)
	public AppType getOwnership() {
		return ownership;
	}

	public void setOwnership(AppType ownership) {
		this.ownership = ownership;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "apiCategory")
	@OrderBy("created desc")
	public Set<Api> getApis() {
		return apis;
	}

	public void setApis(Set<Api> apis) {
		this.apis = apis;
	}

}
