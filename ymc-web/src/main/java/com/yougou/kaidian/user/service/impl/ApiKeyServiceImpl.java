package com.yougou.kaidian.user.service.impl;  

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.user.constant.UserConstant;
import com.yougou.kaidian.user.dao.ApiKeyMapper;
import com.yougou.kaidian.user.model.pojo.ApiKey;
import com.yougou.kaidian.user.model.pojo.ApiKeyMetadata;
import com.yougou.kaidian.user.model.pojo.ApiLicense;
import com.yougou.kaidian.user.model.vo.AppKeySecretVo;
import com.yougou.kaidian.user.service.IApiKeyService;

@Service
public class ApiKeyServiceImpl implements IApiKeyService {
	@Autowired
	private ApiKeyMapper apiKeyMapper;
	private Logger logger = LoggerFactory.getLogger(ApiKeyServiceImpl.class);
	
	private void saveApiKey(ApiKey apiKey) {
		apiKeyMapper.saveApiKey(apiKey);
	}
	
	private boolean authorizeApiKeyToMerchant(List<ApiLicense> licenses) {
		boolean flag = false;
		try{
			apiKeyMapper.authorizeApiKeyToMerchant(licenses);
			flag = true;
		}catch(Exception e){
			logger.error("商家中心授权商家api，licenses:{}，发生异常：",
					new Object[]{ToStringBuilder.reflectionToString(licenses, ToStringStyle.SHORT_PREFIX_STYLE),
					e});
		}
		return flag;
	}
	
	private boolean bindingApiKeyToMerchant(ApiKeyMetadata keyMetadata) {
		boolean flag = false;
		try{
			apiKeyMapper.bindingApiKeyToMerchant(keyMetadata);
			flag = true;
		}catch(Exception e){
			logger.error("商家中心绑定商家appkey，商家编码：{}，appkeyId：{}，发生异常：",
					new Object[]{keyMetadata.getMetadataVal(),
					keyMetadata.getKeyId(),
					e});
		}
		return flag;
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean generateApiKeyAndBandingAndAuthorize(String merchantCode,
			ApiKey apiKey) throws Exception {
		boolean flag = false;
		try{
			//1 保存appkey
			this.saveApiKey(apiKey);
			//2 绑定appkey
			ApiKeyMetadata metadata = new ApiKeyMetadata();
			metadata.setId(UUIDGenerator.getUUID());
			metadata.setKeyId(apiKey.getId());
			metadata.setMetadataKey("MERCHANTS");
			metadata.setMetadataVal(merchantCode);
			this.bindingApiKeyToMerchant(metadata);
			//3 授权appkey
			List<ApiLicense> licenses = new ArrayList<ApiLicense>();
			ApiLicense license = null;
			for(String apiId : UserConstant.APIIDS){
				license = new ApiLicense();
				license.setId(UUIDGenerator.getUUID());
				license.setApiId(apiId);
				license.setKeyId(apiKey.getId());
				license.setLicensed(new Date());
				license.setLicensor(merchantCode);
				licenses.add(license);
			}
			this.authorizeApiKeyToMerchant(licenses);
			flag = true;
		}catch(Exception e){
			logger.error("商家中心绑定商家：{}，appkey：{}，发生异常：",
					new Object[]{merchantCode,
					ToStringBuilder.reflectionToString(apiKey, ToStringStyle.SHORT_PREFIX_STYLE),
					e});
			throw new Exception("商家中心绑定商家appkey发生异常！",e);
		}
		return flag;
	}

	@Override
	public boolean changeApiStatus(String apiId, Integer status) {
		boolean flag = false;
		try{
			if(status!=1){
				status=0;
			}
			apiKeyMapper.changeApiStatus(apiId,status);
			flag = true;
		}catch(Exception e){
			logger.error("修改api状态失败：",e);
		}
		return flag;
	}
	
	@Override
	public Map<String,Object> findAppKeyIsExist(String merchantCode) {
		return apiKeyMapper.findAppKeyIsExist(merchantCode);
	}
	
	@Override
	public List<AppKeySecretVo> findAppKeySecret() {
		return apiKeyMapper.findAppKeySecret();
	}
	
}
