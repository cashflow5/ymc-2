package com.yougou.api.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.DateUtil;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.infrastructure.util.UUIDUtil;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.yougou.api.beans.AppType;
import com.yougou.api.model.pojo.ApiKey;
import com.yougou.api.model.pojo.ApiKey.ApiKeyStatus;
import com.yougou.api.model.pojo.ApiKeyMetadata;
import com.yougou.api.service.IApiKeyService;
import com.yougou.api.util.MD5Encryptor;
import com.yougou.dms.api.ApiDistributorService;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.merchant.api.monitor.service.IApiMonitorService;
import com.yougou.merchant.api.supplier.service.IMerchantsApi;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog.OperationType;

@Controller
@RequestMapping("/openapimgt/apikey")
public class ApiKeyController {
	
	private static final Logger LOGGER = Logger.getLogger(ApiKeyController.class);

	@Resource
	private IApiKeyService apiKeyService;
	
	@Resource
	private ApiDistributorService apiDistributorService;
	
    @Resource
    private IApiMonitorService apiMonitorService;
    
	@Resource
	private IMerchantsApi merchantOperationLogService;
	@Resource(name = "stringRedisTemplate")
	private SetOperations<String, String> setOperations;
	
	private final static String TYPE_MERCHANT = "招商";
	private final static String TYPE_DISTRIBUTOR = "分销";
	
	/**
	 * 商家API密钥信息列表
	 * 
	 * @param apiKeyMetadata
	 * @param query
	 * @param tabNum tab页  1：招商AppKey管理 2:分销AppKey管理
	 * @return ModelAndView
	 */
	@RequestMapping("/queryApiKey")
	public ModelAndView queryApiKey(ApiKeyMetadata apiKeyMetadata, Query query) throws Exception {
		PageFinder<ApiKey> pageFinder = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
		Map<String,String> distributorMap = apiDistributorService.queryAllDistributor();
		resultMap.put("distributorMap", distributorMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("分销dubbo查询分销商接口出现异常！");
		}

		pageFinder = apiKeyService.queryApiKey(apiKeyMetadata, query);
		resultMap.put("pageFinder", pageFinder);
		resultMap.put("apiKeyMetadata", apiKeyMetadata);
		resultMap.put("appTypes", AppType.values());
		resultMap.put("apiKeyStatuses", ApiKeyStatus.values());
		
		return new ModelAndView("yitiansystem/merchant/apimgt/list_api_key", resultMap);
	}

	/**
	 * 生成API密钥信息
	 * 
	 * @param modelMap
	 * @param request
	 * @return String
	 */
	@RequestMapping("/generateApiKey")
	public void generateApiKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 try{
			SystemmgtUser user = GetSessionUtil.getSystemUser(request);
			// 生成密匙
			String appKey = new java.rmi.server.UID().toString().replaceAll("(:|-)", "_");
			// 生成密匙口令
			List<String> appSecretSections = Arrays.asList(appKey.split(""));
			Collections.shuffle(appSecretSections);
			String appSecret = MD5Encryptor.encrypt(appSecretSections.toString());
			SystemmgtUser systemmgtUser = GetSessionUtil.getSystemUser(request);
			ApiKey apiKey = new ApiKey();
			apiKey.setAppKey(appKey);
			apiKey.setAppSecret(appSecret);
			apiKey.setStatus(ApiKeyStatus.DISABLE);
			apiKey.setUpdateTime(DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
			apiKey.setUpdateUser(systemmgtUser.getUsername());
			apiKeyService.saveApiKey(apiKey);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[] { "apiKeyMetadatas" });
			IOUtils.write(JSONObject.fromObject(apiKey, jsonConfig).toString(), response.getWriter());
			
			MerchantOperationLog operationLog=new MerchantOperationLog();
			operationLog.setId(UUIDUtil.getUUID());
			operationLog.setMerchantCode(appKey);
			operationLog.setOperated(new Date());
			operationLog.setOperationNotes("新建appkey");
			operationLog.setOperationType(OperationType.AppKey_ADD);
			operationLog.setOperator(user.getUsername());
			merchantOperationLogService.saveMerchantOperationLog(operationLog);
		} catch (Exception ex) {
			LOGGER.error(ex);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			IOUtils.write("生成AppKey异常", response.getWriter());
		} 
	}
	
	/**
	 * 更新API密钥状态
	 * 
	 * @return apiKey
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/updateApiKeyStatus")
	public void updateApiKeyStatus(ApiKey apiKey,HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			SystemmgtUser user = GetSessionUtil.getSystemUser(request);
			MerchantOperationLog operationLog=new MerchantOperationLog();
			operationLog.setId(UUIDUtil.getUUID());
			
			ApiKey another = apiKeyService.getApiKeyById(apiKey.getId());
			if (ApiKeyStatus.DISABLE.equals(another.getStatus())) {
				another.setStatus(ApiKeyStatus.ENABLE);
				operationLog.setOperationNotes("启用appkey");
				operationLog.setOperationType(OperationType.APPKEY_ENABLED);
				if(setOperations.isMember(CacheConstant.APPKEY_YOUGOU_STATUS, another.getAppKey())){
					setOperations.remove(CacheConstant.APPKEY_YOUGOU_STATUS, another.getAppKey());
				}
			} else {
				another.setStatus(ApiKeyStatus.DISABLE);
				operationLog.setOperationNotes("禁用appkey");
				operationLog.setOperationType(OperationType.APPKEY_DISABLE);
				setOperations.add(CacheConstant.APPKEY_YOUGOU_STATUS, another.getAppKey());
			}
			apiKeyService.saveApiKey(another);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[] { "apiKeyMetadatas" });
			IOUtils.write(JSONObject.fromObject(another, jsonConfig).toString(), response.getWriter());
			
			operationLog.setMerchantCode(another.getAppKey());
			operationLog.setOperated(new Date());
			operationLog.setOperator(user.getUsername());
			merchantOperationLogService.saveMerchantOperationLog(operationLog);
		} catch (Exception ex) {
			LOGGER.error(ex);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			IOUtils.write("更新AppKey状态异常", response.getWriter());
		}
	}
	
	/**
	 * 跳转到到授权AppKey使用API页面
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/preAuthorizeApi")
	public ModelAndView preAuthorizeApi(String apiKeyId) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(apiKeyId)) {
			// 分组未获得授权API列表
			Map<String, List<Map<String, Object>>> unownedListMap = new HashMap<String, List<Map<String,Object>>>();
			List<Map<String, Object>> unownedList = apiKeyService.queryApiKeyUnownedApiList(apiKeyId);
			for (Map<String, Object> element : unownedList) {
				String key = MapUtils.getString(element, "category_name");
				List<Map<String, Object>> list = unownedListMap.get(key);
				if (list == null) {
					list = new ArrayList<Map<String,Object>>();
					unownedListMap.put(key, list);
				}
				list.add(element);
			}
			// 分组已获得授权API列表
			Map<String, List<Map<String, Object>>> ownedListMap = new HashMap<String, List<Map<String,Object>>>();
			List<Map<String, Object>> ownedList = apiKeyService.queryApiKeyOwnedApiList(apiKeyId);
			for (Map<String, Object> element : ownedList) {
				String key = MapUtils.getString(element, "category_name");
				List<Map<String, Object>> list = ownedListMap.get(key);
				if (list == null) {
					list = new ArrayList<Map<String,Object>>();
					ownedListMap.put(key, list);
				}
				list.add(element);
			}
			resultMap.put("unownedListMap", unownedListMap);
			resultMap.put("ownedListMap", ownedListMap);
			resultMap.put("ownedList", ownedList);
			resultMap.put("apiKeyId", apiKeyId);
			resultMap.put("templateList", apiMonitorService.queryTemplateList());
		}
		return new ModelAndView("yitiansystem/merchant/apimgt/authorize_api", resultMap);
	}

	/**
	 * 给商家授权API
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/authorizeApi")
	public String authorizeApi(String apiIds, String apiKeyId, HttpServletRequest request) throws Exception {
		boolean result = false;
		if (StringUtils.isNotBlank(apiKeyId)) {
			HttpSession session = request.getSession();
			SystemmgtUser systemmgtUser = (SystemmgtUser) session.getAttribute(Constant.LOGIN_SYSTEM_USER);
			ApiKey appkey = apiKeyService.getApiKeyById(apiKeyId);
			result = apiKeyService.authorizeApi(StringUtils.split(apiIds, "_"), apiKeyId, systemmgtUser,appkey.getAppKey(),null);
		}
		return Boolean.toString(result);
	}
	
	@RequestMapping("/preBindingApiKey")
	public ModelAndView preBindingApiKeyPotentialCustomers(String apiKeyId) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//DataSourceSwitcher.setMaster();
		if (StringUtils.isNotBlank(apiKeyId)) {
			// 查询已绑定的AppKey消费者
			List<ApiKeyMetadata> apiKeyCustomers = apiKeyService.queryApiKeyCustomers(apiKeyId);
			// 查询分销已绑定的AppKey消费者
			List<ApiKeyMetadata> apiKeyCustomersChain = apiKeyService.queryApiKeyByType("CHAIN");
			// 查询分销已绑定的指定AppKey消费者
			List<ApiKeyMetadata> apiKeyCustomersApiChain = apiKeyService.queryApiKeyByapiKeyIdAndType(apiKeyId,"CHAIN");
			// 查询未绑定的AppKey潜在消费者
			List<ApiKeyMetadata> apiKeyPotentialCustomers = apiKeyService.queryApiKeyPotentialCustomers();
			// 分组消费者
			Map<String, Collection<ApiKeyMetadata>> bindedListMap = new HashMap<String, Collection<ApiKeyMetadata>>();
			Map<String, Collection<ApiKeyMetadata>> unbindedListMap = new HashMap<String, Collection<ApiKeyMetadata>>();
			bindedListMap.put(TYPE_MERCHANT, apiKeyCustomers);
			unbindedListMap.put(TYPE_MERCHANT, apiKeyPotentialCustomers);
			try {
				//分销
				Map<String,String> distributorMap = apiDistributorService.queryAllDistributor();
				bindedListMap.put(TYPE_DISTRIBUTOR, apiKeyCustomersApiChain);
				resultMap.put("distributorMap", distributorMap);
				List<ApiKeyMetadata> apiKeyDistributorPotential = new ArrayList<ApiKeyMetadata>();
				//分销未绑定的AppKey潜在消费者
				for(Entry<String, String> entry:distributorMap.entrySet()){
					ApiKeyMetadata apiKeyMetadata = new ApiKeyMetadata();
					apiKeyMetadata.setMetadataKey(AppType.CHAIN);
					apiKeyMetadata.setMetadataVal(entry.getKey());
					apiKeyMetadata.setMetadataTag(entry.getValue());
					boolean potential = true;
					for(ApiKeyMetadata key:apiKeyCustomersChain){
						if(StringUtils.equals(apiKeyMetadata.getMetadataVal(), key.getMetadataVal())){
							potential = false;
							break;
						}
					}
					if(potential){
						apiKeyDistributorPotential.add(apiKeyMetadata);
					}
				}
				unbindedListMap.put(TYPE_DISTRIBUTOR, apiKeyDistributorPotential);
			} catch (Exception ex) {
				ex.printStackTrace();
				LOGGER.error("分销dubbo查询分销商接口出现异常！");
			}
			resultMap.put("bindedListMap", bindedListMap);
			resultMap.put("unbindedListMap", unbindedListMap);
			resultMap.put("apiKeyId", apiKeyId);
		}
		return new ModelAndView("yitiansystem/merchant/apimgt/list_api_key_metadata", resultMap);
	}
	
	/**
	 * 查询API密匙潜在消费客户
	 * 
	 * @return ModelAndView
	 */
	@ResponseBody
	@RequestMapping("/bindingApiKey")
	public String bindingApiKeyCustomers(String[] customers, AppType[] appTypes, String apiKeyId,HttpServletRequest request) throws Exception {
		boolean result = false;
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		if (StringUtils.isNotBlank(apiKeyId)) {
			ApiKey appkey = apiKeyService.getApiKeyById(apiKeyId);
			String userName = user==null?"":user.getUsername();
			result = apiKeyService.bindingApiKeyCustomers(customers, appTypes, apiKeyId,userName,appkey.getAppKey());
		}
		return Boolean.toString(result);
	}
}
