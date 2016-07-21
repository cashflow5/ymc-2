package com.yougou.api.interceptor.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
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
import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.dao.IApiLicenseDao;
import com.yougou.api.exception.InterceptionException;
import com.yougou.api.interceptor.AbstractInterceptor;
import com.yougou.api.service.BusinessLogger;

/**
 * 授权认证拦截器
 * 
 * @author 杨梦清
 * 
 */
public class AuthInterceptor extends AbstractInterceptor {
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private IApiLicenseDao apiLicenseDao;
	
	@Resource
	private BusinessLogger businessLogger;
	
	private static final String API_LICENSE_REDIS_KEY = "api_license_redis_key";
	
	public static final String API_ENABLE_REDIS_KEY = "api_enable_redis_key";
	
	@Override
	public Object intercept(ImplementorInvocation invocation) throws InterceptionException {
		// 读取APIID与商家代码
		String apiId = invocation.getImplementorProxy().getImplementorMapping().getApiId();
		String appKey = MapUtils.getString(invocation.getImplementorContext().getParameters(), "app_key");
		
		// 校验API是否启用
		if (!this.isApiEnable(apiId)) {
			businessLogger.log("auth", YOPBusinessCode.API_IS_UNABLE, "API已被禁用", appKey);
			return  new InterceptionException(YOPBusinessCode.API_IS_UNABLE, "API已被禁用.");
		}
		
		// 校验商家是否已获得访问API的授权
		if (!this.isLicense(apiId, appKey)) {
			businessLogger.log("auth", YOPBusinessCode.API_AUTH_IS_FAILURE, "无权访问该API", appKey);
			return  new InterceptionException(YOPBusinessCode.API_AUTH_IS_FAILURE, "您无权访问该API.");
		}
			
		return invocation.invoke();
	}
	
	/**
	 * 判断API接口是否启用
	 * 
	 * @param apiId Api接口Id
	 * @return true|false
	 */
	private boolean isApiEnable(String apiId) {
		if (StringUtils.isBlank(apiId)) 
			return false;
		
		List<Map<String, String>> list = null;
		try {
			list = (List<Map<String, String>>) redisTemplate.opsForValue().get(API_ENABLE_REDIS_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (CollectionUtils.isEmpty(list)) {
			//查询数据库
			Session session = null;
			list = new ArrayList<Map<String, String>>();
			try {
				// 校验商家是否已获得访问API的授权
				session = apiLicenseDao.getHibernateSession();
				SQLQuery query = (SQLQuery) session.createSQLQuery("select t.id, t.is_enable from tbl_merchant_api t ").addScalar("id", Hibernate.STRING).addScalar("is_enable", Hibernate.STRING).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<?> objs = query.list();
				for (Object object : objs) {
					list.add((Map<String, String>)object);
				}
				redisTemplate.opsForValue().set(API_ENABLE_REDIS_KEY, list, 5, TimeUnit.MINUTES);
				businessLogger.log(MessageFormat.format("重新查询API_ENABLE_REDIS_KEY加载到缓存！总共：{0}。", CollectionUtils.isNotEmpty(list) ? list.size() : 0));
			} finally {
				apiLicenseDao.releaseHibernateSession(session);
			}
		}
		
		return this.isEnableApi(apiId, list);
	}
	
	/**
	 * 判断商家是否已经获得访问API的授权
	 * 
	 * @param apiId
	 * @param appKey
	 * @return
	 */
	private boolean isLicense(String apiId, String appKey) {
		List<Map<String, String>> list = null;
		try {
			list = (List<Map<String, String>>) redisTemplate.opsForValue().get(API_LICENSE_REDIS_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (CollectionUtils.isEmpty(list)) {
			list = this.queryApiLicenseList();
			redisTemplate.opsForValue().set(API_LICENSE_REDIS_KEY, list, 5, TimeUnit.MINUTES);
			businessLogger.log(MessageFormat.format("重新查询API_LICENSE_REDIS_KEY加载到缓存！总共：{0}。", CollectionUtils.isNotEmpty(list) ? list.size() : 0));
		}
		
		return this.containValue(apiId, appKey, list);
	}
	
	private List<Map<String, String>> queryApiLicenseList() {
		Session session = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			// 校验商家是否已获得访问API的授权
			session = apiLicenseDao.getHibernateSession();
			SQLQuery query = (SQLQuery) session.createSQLQuery("select t.api_id, k.app_key from tbl_merchant_api_license t INNER JOIN tbl_merchant_api_key k ON t.key_id = k.id").addScalar("api_id", Hibernate.STRING).addScalar("app_key", Hibernate.STRING).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<?> objs = query.list();
			for (Object object : objs) {
				list.add((Map<String, String>)object);
			}
		} finally {
			apiLicenseDao.releaseHibernateSession(session);
		}
		
		return list;
	}
	
	private boolean containValue(String apiId, String appKey, List<Map<String, String>> list) {
		if (CollectionUtils.isEmpty(list)) {
			return false;
		}
		
		for (Map<String, String> apiLicense : list) {
			if (apiId.equals(MapUtils.getString(apiLicense, "api_id")) && appKey.equals(MapUtils.getString(apiLicense, "app_key"))) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isEnableApi(String apiId, List<Map<String, String>> list) {
		if (CollectionUtils.isEmpty(list))
			return false;
		
		for (Map<String, String> map : list) {
			if (apiId.equals(MapUtils.getString(map, "id"))) {
				String isEnable = MapUtils.getString(map, "is_enable");
				return "0".equals(isEnable) ? true : false;
			}
		}
		
		return true;
	}
}

