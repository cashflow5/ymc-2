package com.yougou.kaidian.user.dao;  

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.user.model.pojo.ApiKey;
import com.yougou.kaidian.user.model.pojo.ApiKeyMetadata;
import com.yougou.kaidian.user.model.pojo.ApiLicense;
import com.yougou.kaidian.user.model.vo.AppKeySecretVo;

public interface ApiKeyMapper {

	void saveApiKey(ApiKey apiKey);

	void bindingApiKeyToMerchant(ApiKeyMetadata keyMetadata);

	void authorizeApiKeyToMerchant(List<ApiLicense> licenses);

	void changeApiStatus(@Param("apiId") String apiId,@Param("status") Integer status);

	Map<String, Object> findAppKeyIsExist(String merchantCode);

	List<String> findAppKeyByApiId(String apiId);

	List<AppKeySecretVo> findAppKeySecret();
	
}
