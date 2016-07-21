package com.yougou.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.vo.AppKeySecretVo;
import com.yougou.api.service.IApiService;
import com.yougou.kaidian.common.constant.CacheConstant;

/**
 * API控制器
 * 
 * @author yang.mq
 * 
 */
@Controller
@RequestMapping("/openapimgt/api")
public class ApiCacheController {

	@Resource
	private IApiService apiService;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Resource(name = "stringRedisTemplate")
	private SetOperations<String, String> setOperations;
	
	@Resource(name = "stringRedisTemplate")
	private HashOperations<String, String, String> hashOperations;
	
	final static String CACHE_QUEUE_KEY = "api:api.cache:queue";
	final static String API_APPKEY_SET_KEY = "api:api.appkey:set";
	final static String API_HASH_KEY = "api:api:hash";
	final static String API_IS_SAVE_RESULT_HASH_KEY = "api:api.is.save.result:hash";
	final static String APPKEY_SECRET_KEY = "api:appkey.secret:hash";
	final static String API_APPKEY_HASH = "api.appkey.hash";

	/**
	 * 刷新API基本配置缓存
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/cahe/updateApiCache")
	public void updateApiCache(HttpServletRequest request, HttpServletResponse response) throws Exception {
		stringRedisTemplate.convertAndSend(CACHE_QUEUE_KEY, "update");

	}

	/**
	 * 刷新API权限缓存
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/cahe/updateAppKeyApiAuthCache")
	@ResponseBody
	public String updateAppKeyApiAuthCache(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Api> list = apiService.findApiIdList();
		stringRedisTemplate.delete(API_APPKEY_SET_KEY);
		stringRedisTemplate.delete(API_HASH_KEY);
		for (Api api : list) {
			String apiId = api.getId();
			//当前有效
			List<String> apiAppkeyList = apiService.findAppKeyByApiId(apiId);
			for(String appKey:apiAppkeyList){
				String value = StringUtils.join(new Object[]{apiId,appKey}, "#");
				setOperations.add(API_APPKEY_SET_KEY,value);
			}
			hashOperations.put(API_HASH_KEY, apiId, api.getIsEnable());
			hashOperations.put(API_IS_SAVE_RESULT_HASH_KEY, api.getApiMethod(), api.getIsSaveResult());
		}
		Long apiCacheSize = setOperations.size(API_APPKEY_SET_KEY);
		return apiCacheSize.toString();
		

	}

	/**
	 * 刷新appKey缓存
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/cahe/updateAppKeyCache")
	@ResponseBody
	public String updateAppKeyCache(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 List<AppKeySecretVo> list = apiService.findAppKeySecret();
		 stringRedisTemplate.delete(APPKEY_SECRET_KEY);
		 Map<String,String> map = new HashMap<String, String>();
		 for(AppKeySecretVo vo:list){
			 String hashKey = vo.getAppKey();
			 String value = StringUtils.join(new Object[]{vo.getSecret(),vo.getMetadataVal()}, "#");
			 map.put(hashKey, value);
		 }
		 hashOperations.putAll(APPKEY_SECRET_KEY, map);
		 Long appkeyCacheSize =  hashOperations.size(APPKEY_SECRET_KEY);
		 
		 
		 return appkeyCacheSize.toString();
	}

	@RequestMapping("/cahe/manage")
	public ModelAndView preListApiLog() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Long appkeyCacheSize =  hashOperations.size(APPKEY_SECRET_KEY);
		Long apiCacheSize = setOperations.size(API_APPKEY_SET_KEY);
		Long apiRelevanceSize = hashOperations.size(API_APPKEY_HASH);//add by LQ.
		resultMap.put("appkeyCacheSize", appkeyCacheSize);
		resultMap.put("appkeyDbSize", apiService.findAppKeyAuthCount());
		resultMap.put("apiCacheSize", apiCacheSize);
		resultMap.put("apiDbSize", apiService.findApiAuthCount());
		resultMap.put("apiRelevanceSize", apiRelevanceSize);//add by LQ.

		return new ModelAndView("yitiansystem/merchant/apimgt/list_api_cache_manage", resultMap);
	}

	
}
