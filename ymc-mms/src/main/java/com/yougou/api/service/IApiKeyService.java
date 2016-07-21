package com.yougou.api.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.yougou.api.beans.AppType;
import com.yougou.api.model.pojo.ApiKey;
import com.yougou.api.model.pojo.ApiKeyMetadata;

public interface IApiKeyService {

	/**
	 * 按API密匙元数据值查询API密匙
	 * 
	 * @param metadataVal
	 * @return ApiKey
	 * @throws Exception
	 */
	ApiKey getApiKeyByMetadataVal(String metadataVal) throws Exception;
	
	/**
	 * 按ID查询API密匙
	 * 
	 * @param id
	 * @return ApiKey
	 * @throws Exception
	 */
	ApiKey getApiKeyById(String id) throws Exception;
	
	/**
	 * 查询API密匙消费客户
	 * 
	 * @param apiKeyId
	 * @return List
	 * @throws Exception
	 */
	List<ApiKeyMetadata> queryApiKeyCustomers(String apiKeyId) throws Exception;
	
	
	/**
	* 根据类型来查询API密匙元数据
	* @param apiKeyId
	* @return
	* @throws Exception
	*/
	List<ApiKeyMetadata> queryApiKeyByType(String type) throws Exception;

	/**
	 * 根据apikeyId和类型来查询API密匙元数据
	 * @param apiKeyId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	List<ApiKeyMetadata> queryApiKeyByapiKeyIdAndType(String apiKeyId,String type) throws Exception;
	
	/**
	 * 查询API密匙潜在消费客户
	 * 
	 * @return List
	 * @throws Exception
	 */
	List<ApiKeyMetadata> queryApiKeyPotentialCustomers() throws Exception;
	
	/**
	 * 分页查询API密匙
	 * 
	 * @param apiKeyMetadata
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<ApiKey> queryApiKey(ApiKeyMetadata apiKeyMetadata, Query query) throws Exception;

	/**
	 * 保存API密匙
	 * 
	 * @param apiKeys
	 * @throws Exception
	 */
	void saveApiKey(ApiKey... apiKeys) throws Exception;

	/**
	 * 查询ApiKey已获得授权的API列表
	 * 
	 * @param apiKeyId
	 * @return List
	 */
	List<Map<String, Object>> queryApiKeyOwnedApiList(String apiKeyId);

	/**
	 * 查询ApiKey未获得授权的API列表
	 * 
	 * @param apiKeyId
	 * @return List
	 */
	List<Map<String, Object>> queryApiKeyUnownedApiList(String apiKeyId);

	/**
	 * 给ApiKey授权API
	 * 
	 * @param apiIds
	 * @param apiKeyId
	 * @param systemmgtUser
	 * @return boolean
	 */
	boolean authorizeApi(String[] apiIds, String apiKeyId, SystemmgtUser systemmgtUser,String appkey,String userName) throws Exception;
	
	
	/**
	 * 绑定ApiKey消费者
	 * @param customers
	 * @param appTypes
	 * @param apiKeyId
	 * @return boolean
	 */
	boolean bindingApiKeyCustomers(String[] customers, AppType[] appTypes, String apiKeyId,String userName,String appKey)throws Exception;
	
	/**
	 * 随机寻求测试招商API密钥
	 * 
	 * @return ApiKey
	 */
	ApiKey randomSeekTestApiKey() throws Exception;
	
	/**
	 * 更新appkey状态
	 * @param appKey
	 * @param status
	 */
	void updateAppKeyStatus(String appkey,String status) throws Exception;
	
	/**
	 * 备份API
	 * @param appkey
	 * @throws Exception
	 */
	void backupApiLicence(String appkey) throws Exception;
	
	/**
	 * 恢复API
	 * @param appkey
	 * @throws Exception
	 */
	void recoverApiLicence(String appkey) throws Exception;
	
	/**
	 * 根据metadataVal查询ApiKeyMetadata信息
	 * @param metadataVal
	 * @return
	 * @throws Exception
	 */
	ApiKeyMetadata queryApiKeyByMetadataVal(String metadataVal) throws Exception;
	/**
	 *将APP KEY生成方抽离
	 * @return
	 * @throws Exception
	 */
	String  generateApiKey(HttpServletRequest request,String userName,String merchantCode) throws Exception;
	/**
	 * 生成， 绑定，授权 一步到位
	 * @param merchantCode
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	String initAppKey(String merchantCode,String userName)  throws Exception;
	
	 ApiKey saveGenerateKey(String userName,String merchantCode);
	 
	 /**
	  * 刷新app key 缓存
	  * @param merchantCode
	  */
	 void freshRedisCache(String merchantCode,String status);
	 
	 void deleteRedisCache(String merchantCode);
}
