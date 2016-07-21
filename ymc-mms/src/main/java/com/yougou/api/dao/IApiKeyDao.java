package com.yougou.api.dao;

import com.belle.infrastructure.orm.IHibernateEntityDao;
import com.yougou.api.beans.AppType;
import com.yougou.api.model.pojo.ApiKey;
import com.yougou.api.model.pojo.ApiKeyMetadata;

public interface IApiKeyDao extends IHibernateEntityDao<ApiKey> {

	/**
	 * 获取APP密匙潜在消费客户SQL语句
	 * 
	 * @return String
	 */
	String getApiKeyPotentialCustomersSqlStatement();
	
	/**
	 * 获取APP密匙元数据
	 * 
	 * @param appKey
	 * @param appType
	 * @return ApiKeyMetadata
	 */
	ApiKeyMetadata getApiKeyMetadata(String appKey, AppType appType);
}
