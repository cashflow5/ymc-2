package com.yougou.api.model.pojo;

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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_merchant_api_key")
public class ApiKey {

	private String id;
	private String appKey;
	private String appSecret;
	private ApiKeyStatus status;
	private String updateUser;
	private String updateTime;
	
	private Set<ApiKeyMetadata> apiKeyMetadatas;

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

	@Column(name = "app_key", length = 32)
	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	@Column(name = "app_secret", length = 100)
	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status", length = 11)
	public ApiKeyStatus getStatus() {
		return status;
	}

	public void setStatus(ApiKeyStatus status) {
		this.status = status;
	}

	@Column(name = "update_user", length = 30)
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Column(name = "update_time", length = 32)
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "apiKey")
	public Set<ApiKeyMetadata> getApiKeyMetadatas() {
		return apiKeyMetadatas;
	}

	public void setApiKeyMetadatas(Set<ApiKeyMetadata> apiKeyMetadatas) {
		this.apiKeyMetadatas = apiKeyMetadatas;
	}
	
	/**
	 * API密匙状况枚举(因数据库字段采用Integer类型,Hibernate映射采用顺序模式)
	 * 
	 * @author yang.mq
	 *
	 */
	public static enum ApiKeyStatus {
		DISABLE("关闭"),
		ENABLE("开启")
		;
		
		private String description;

		private ApiKeyStatus(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}
}
