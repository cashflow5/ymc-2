package com.yougou.kaidian.user.service;  

import java.util.List;
import java.util.Map;
import com.yougou.kaidian.user.model.pojo.ApiKey;
import com.yougou.kaidian.user.model.vo.AppKeySecretVo;

public interface IApiKeyService {

	public boolean generateApiKeyAndBandingAndAuthorize(String merchantCode,
			ApiKey apiKey) throws Exception;

	public boolean changeApiStatus(String apiId, Integer status);

	/**
	 * findAppKeyIsExist:根据merchantCode查询APPKEY是否存在 
	 * @author li.n1 
	 * @param merchantCode
	 * @return 
	 * @since JDK 1.6
	 */
	public Map<String,Object> findAppKeyIsExist(String merchantCode);

	public List<AppKeySecretVo> findAppKeySecret();

}
