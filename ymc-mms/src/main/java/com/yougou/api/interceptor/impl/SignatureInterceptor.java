package com.yougou.api.interceptor.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.data.redis.core.RedisTemplate;

import com.yougou.api.ImplementorInvocation;
import com.yougou.api.annotation.BeanPropertyAutowiring;
import com.yougou.api.beans.AppType;
import com.yougou.api.constant.Constants;
import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.dao.IApiKeyDao;
import com.yougou.api.exception.InterceptionException;
import com.yougou.api.interceptor.AbstractInterceptor;
import com.yougou.api.service.BusinessLogger;
import com.yougou.api.util.MD5Encryptor;
import com.yougou.api.util.SHA1Encryptor;

/**
 * 参数签名认证拦截器
 * 
 * @author 杨梦清
 *
 */
@BeanPropertyAutowiring
public class SignatureInterceptor extends AbstractInterceptor {
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	private static final String API_KEYMETADATA_REDIS_KEY = "api_keymetadata_redis_key";
	
	@Resource
	private IApiKeyDao apiKeyDao;
	
	@Resource
	private BusinessLogger businessLogger;
	
	@Override
	public Object intercept(ImplementorInvocation invocation) throws InterceptionException {
		Map<String, Object> parameters = invocation.getImplementorContext().getParameters();
		String appKey = MapUtils.getString(parameters, "app_key");
		String sign = MapUtils.getString(parameters, "sign");
		String signMethod = MapUtils.getString(parameters, "sign_method");
		AppType appType = invocation.getImplementorProxy().getImplementorMapping().getOwnership();
		
		//ApiKeyMetadata apiKeyMetadata = apiKeyDao.getApiKeyMetadata(appKey, appType);
		Map<String, String> map = this.getApikeyMetadataById(appKey, appType.name());
		if (MapUtils.isEmpty(map)) {
			businessLogger.log("signature", YOPBusinessCode.API_APP_KEY_IS_INVALID, "app_key不正确", appKey);
			return new InterceptionException(YOPBusinessCode.API_APP_KEY_IS_INVALID, "您提供的[app_key]未能被优购识别,请确认[app_key]是否正确.");
		}
		if ("0".equals(MapUtils.getString(map, "status"))) {
			businessLogger.log("signature", YOPBusinessCode.API_APP_KEY_IS_CLOSED, "app_key已关闭", appKey);
			return new InterceptionException(YOPBusinessCode.API_APP_KEY_IS_CLOSED, "您的[app_key]已经关闭,请联系优购招商人员.");
		}
		if (signMethod != null && !Constants.MD5.equalsIgnoreCase(signMethod) && !Constants.SHA1.equalsIgnoreCase(signMethod)) {
			businessLogger.log("signature", YOPBusinessCode.API_SIGN_IS_INVALID, "签名加密方法错误", appKey);
			return new  InterceptionException(YOPBusinessCode.API_SIGN_IS_INVALID, "输入参数签名加密方法只支持[md5,sha-1].");
		}
		if (!sign.equals(toEncryptKey(parameters, MapUtils.getString(map, "app_secret"), signMethod))) {
			businessLogger.log("signature", YOPBusinessCode.API_SIGN_IS_INVALID, "签名错误", appKey);
			return new InterceptionException(YOPBusinessCode.API_SIGN_IS_INVALID, "输入参数签名加密错误,请联系优购技术支持.");
		}
		
		// 注入商家编码
		parameters.put("merchant_code", MapUtils.getString(map, "metadata_val"));
		
		return invocation.invoke();
	}
	
	/**
	 * 签名加密
	 * 
	 * @param context
	 * @param token
	 * @param signMethod
	 * @return String
	 */
	private String toEncryptKey(Map<String, Object> context, String token, String signMethod) {
		int paramIndex = 0;
		String[] params = new String[context.size() - 1];
		for (Map.Entry<String, Object> entry : context.entrySet()) {
			if ("sign".equalsIgnoreCase(entry.getKey())) {
				continue;
			}
			params[paramIndex++] = entry.getKey() + entry.getValue();
		}
		
		Arrays.sort(params);
		token += StringUtils.join(params, "");
		return Constants.SHA1.equals(signMethod) ? SHA1Encryptor.encrypt(token) : MD5Encryptor.encrypt(token);
	}
	
	private Map<String, String> getApikeyMetadataById(String appKey, String appType) {
		List<Map<String, String>> list = null;
		try {
			list = (List<Map<String, String>>) redisTemplate.opsForValue().get(API_KEYMETADATA_REDIS_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (CollectionUtils.isEmpty(list)) {
			list = this.queryApiKeyMetadata();
			redisTemplate.opsForValue().set(API_KEYMETADATA_REDIS_KEY, list, 5, TimeUnit.MINUTES);
			businessLogger.log(MessageFormat.format("重新查询API_KEYMETADATA_REDIS_KEY加载到缓存！总共：{0}。", CollectionUtils.isNotEmpty(list) ? list.size() : 0));
		}
		
		if (CollectionUtils.isNotEmpty(list)) {
			return this.getApikeyMetadataById(list, appKey, appType);
		}
		
		return null;
	}
	
	private Map<String, String> getApikeyMetadataById(List<Map<String, String>> objs, String appKey, String appType) {
		if (CollectionUtils.isNotEmpty(objs)) {
			for (Map<String, String> apiKeyMetadata : objs) {
				if (appKey.equals(MapUtils.getString(apiKeyMetadata, "app_key"))) {
					//如果归属是全部，则不需要跟metadata_key对比
					if(appType.equals(AppType.ALL.toString()) || appType.equals(MapUtils.getString(apiKeyMetadata, "metadata_key")) ){
						return apiKeyMetadata; 
					}
				}
			}
		}
		
		return null;
	}
	
	private List<Map<String, String>> queryApiKeyMetadata() {
		Session session = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			// 校验商家是否已获得访问API的授权
			session = apiKeyDao.getHibernateSession();
			SQLQuery query = (SQLQuery) session
					.createSQLQuery(
							"SELECT t1.app_key, t1.app_secret, t1.status, t2.id, t2.key_id, t2.metadata_key, t2.metadata_val FROM tbl_merchant_api_key t1 INNER JOIN tbl_merchant_api_key_metadata t2 ON (t1.id = t2.key_id)")
					.addScalar("app_key", Hibernate.STRING).addScalar("app_secret", Hibernate.STRING).addScalar("metadata_key", Hibernate.STRING)
					.addScalar("status", Hibernate.STRING).addScalar("metadata_val", Hibernate.STRING).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<?> objs = query.list();
			for (Object object : objs) {
				list.add((Map<String, String>)object);
			}
		} finally {
			apiKeyDao.releaseHibernateSession(session);
		}
		
		return list;
	}
}
