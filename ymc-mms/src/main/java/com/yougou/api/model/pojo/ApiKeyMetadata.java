package com.yougou.api.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.yougou.api.beans.AppType;

@Entity
@Table(name = "tbl_merchant_api_key_metadata")
public class ApiKeyMetadata {

	private String id;
	private ApiKey apiKey;
	private AppType metadataKey;
	private String metadataVal;
	private String metadataTag;

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
	@JoinColumn(name = "key_id")
	public ApiKey getApiKey() {
		return apiKey;
	}

	public void setApiKey(ApiKey apiKey) {
		this.apiKey = apiKey;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "metadata_key", length = 16)
	public AppType getMetadataKey() {
		return metadataKey;
	}

	public void setMetadataKey(AppType metadataKey) {
		this.metadataKey = metadataKey;
	}

	@Column(name = "metadata_val", length = 16)
	public String getMetadataVal() {
		return metadataVal;
	}

	public void setMetadataVal(String metadataVal) {
		this.metadataVal = metadataVal;
	}

	@Transient
	public String getMetadataTag() {
		return metadataTag;
	}

	public void setMetadataTag(String metadataTag) {
		this.metadataTag = metadataTag;
	}
}
