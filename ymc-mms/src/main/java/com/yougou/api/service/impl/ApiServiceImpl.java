package com.yougou.api.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.yougou.api.annotation.Documented;
import com.yougou.api.dao.IApiCategoryDao;
import com.yougou.api.dao.IApiDao;
import com.yougou.api.dao.IApiImplementorDao;
import com.yougou.api.dao.IApiVersionDao;
import com.yougou.api.interceptor.impl.AuthInterceptor;
import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiCategory;
import com.yougou.api.model.pojo.ApiImplementor;
import com.yougou.api.model.pojo.ApiLog;
import com.yougou.api.model.pojo.ApiVersion;
import com.yougou.api.model.vo.AppKeySecretVo;
import com.yougou.api.mongodb.GenericMongoDao;
import com.yougou.api.service.IApiService;
import com.yougou.dms.api.ApiDistributorService;

@Service
public class ApiServiceImpl implements IApiService {

	@Resource
	private IApiDao apiDao;
	
	@Resource
	private IApiVersionDao apiVersionDao;
	
	@Resource
	private IApiCategoryDao apiCategoryDao;
	
	@Resource
	private IApiImplementorDao apiImplementorDao;

	@Resource
	private GenericMongoDao genericMongoDao;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private ApiDistributorService apiDistributorService;
	
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Resource(name = "stringRedisTemplate")
	private SetOperations<String, String> setOperations;
	
	@Resource(name = "stringRedisTemplate")
	private HashOperations<String, String, String> hashOperations;
	
	final static String API_APPKEY_SET_KEY = "api:api.appkey:set";
	final static String API_HASH_KEY = "api:api:hash";
	final static String API_IS_SAVE_RESULT_HASH_KEY = "api:api.is.save.result:hash";
	
	@Override
	@Transactional
	public void deleteApiById(String id) throws Exception {
		apiDao.removeById(id);
	}

	@Override
	public Api getApiById(String id) throws Exception {
		return apiDao.getById(id);
	}

	@Override
	public List<Api> getAllApi() throws Exception {
		return apiDao.getAll();
	}

	@Override
	@Transactional
	public void saveApi(Api api) throws Exception {
		ApiVersion apiVersion = apiVersionDao.getById(api.getApiVersion().getId());
		if (apiVersion == null) {
			throw new UnsupportedOperationException("未指定API版本!");
		}
		
		ApiCategory apiCategory = apiCategoryDao.getById(api.getApiCategory().getId());
		if (apiCategory == null) {
			throw new UnsupportedOperationException("未指定API分类!");
		}
		
		ApiImplementor apiImplementor = apiImplementorDao.getById(api.getApiImplementor().getId());
		if (apiImplementor == null) {
			throw new UnsupportedOperationException("未指定API实现者!");
		}
		
		Session session = null;
		try {
			session = apiDao.getHibernateSession();
			// 校验API版本与代码是否唯一
			Criteria criteria = session.createCriteria(Api.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("apiVersion.id", apiVersion.getId()));
			criteria.add(Restrictions.eq("apiCode", api.getApiCode()));
			if (((Number) criteria.uniqueResult()).intValue() == 1) {
				throw new UnsupportedOperationException("API版本[" + apiVersion.getVersionNo() + "]中已注册[" + api.getApiCode() + "]代码!");
			}
			// 校验API实现者与方法是否唯一
			criteria = session.createCriteria(Api.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("apiImplementor.id", apiImplementor.getId()));
			criteria.add(Restrictions.eq("apiMethod", api.getApiMethod()));
			if (((Number) criteria.uniqueResult()).intValue() == 1) {
				throw new UnsupportedOperationException("API实现者[" + apiImplementor.getIdentifier() + "]中已注册[" + api.getApiMethod() + "]方法!");
			}
			// 保存API
			api.setApiVersion(apiVersion);
			api.setApiCategory(apiCategory);
			api.setApiImplementor(apiImplementor);
			checkApiMethod(api);
			session.save(api);
		} finally {
			apiDao.releaseHibernateSession(session);
		}
	}

	@Override
	@Transactional
	public void updateApi(Api api) throws Exception {
		ApiVersion apiVersion = apiVersionDao.getById(api.getApiVersion().getId());
		if (apiVersion == null) {
			throw new UnsupportedOperationException("未指定API版本!");
		}
		
		ApiCategory apiCategory = apiCategoryDao.getById(api.getApiCategory().getId());
		if (apiCategory == null) {
			throw new UnsupportedOperationException("未指定API分类!");
		}
		
		ApiImplementor apiImplementor = apiImplementorDao.getById(api.getApiImplementor().getId());
		if (apiImplementor == null) {
			throw new UnsupportedOperationException("未指定API实现者!");
		}
		
		Session session = null;
		try {
			session = apiDao.getHibernateSession();
			// 校验API版本与代码是否唯一
			Criteria criteria = session.createCriteria(Api.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("apiVersion.id", apiVersion.getId()));
			criteria.add(Restrictions.eq("apiCode", api.getApiCode()));
			criteria.add(Restrictions.ne("id", api.getId()));
			if (((Number) criteria.uniqueResult()).intValue() == 1) {
				throw new UnsupportedOperationException("API版本[" + apiVersion.getVersionNo() + "]中已注册[" + api.getApiCode() + "]代码!");
			}
			// 校验API实现者与方法是否唯一
			criteria = session.createCriteria(Api.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("apiImplementor.id", apiImplementor.getId()));
			criteria.add(Restrictions.eq("apiMethod", api.getApiMethod()));
			criteria.add(Restrictions.ne("id", api.getId()));
			if (((Number) criteria.uniqueResult()).intValue() == 1) {
				throw new UnsupportedOperationException("API实现者[" + apiImplementor.getIdentifier() + "]中已注册[" + api.getApiMethod() + "]方法!");
			}
			// 更新API
			Api another = (Api) session.load(Api.class, api.getId(), LockMode.UPGRADE);
			another.setApiVersion(apiVersion);
			another.setApiCategory(apiCategory);
			another.setApiImplementor(apiImplementor);
			another.setModifier(api.getModifier());
			another.setModified(api.getModified());
			another.setApiDescription(api.getApiDescription());
			another.setApiName(api.getApiName());
			another.setApiMethod(api.getApiMethod());
			another.setApiMethodParamTypes(api.getApiMethodParamTypes());
			another.setApiWeight(api.getApiWeight());
			another.setIsEnable(api.getIsEnable());
			another.setIsSaveResult(api.getIsSaveResult());
			checkApiMethod(another);
			session.update(another);
		} finally {
			apiDao.releaseHibernateSession(session);
		}
	}
	
	@Override
	@Transactional
	public void updateApiStatus(String id, String isEnable) throws Exception {
		Session session = null;
		try {
			session = apiDao.getHibernateSession();
			Api another = (Api) session.load(Api.class, id, LockMode.UPGRADE);
			another.setIsEnable(isEnable);
			session.update(another);
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			redisTemplate.opsForValue().set(AuthInterceptor.API_ENABLE_REDIS_KEY, list);
		} finally {
			apiDao.releaseHibernateSession(session);
		}
	}
	
	private void checkApiMethod(Api api) throws Exception {
		Class<?> clazz = Class.forName(api.getApiImplementor().getImplementorClass());
		// 组装方法名称
		String[] strings = StringUtils.split(api.getApiMethod(), '.');
		String methodName = strings[strings.length - 1];
		for (int i = strings.length - 2; i > 0; i--) {
			methodName += StringUtils.capitalize(strings[i]);
		}
		// 组装方法参数类型列表
		try {
			strings = StringUtils.split(api.getApiMethodParamTypes(), ',');
			Class<?>[] classes = new Class<?>[strings.length];
			for (int i = 0; i < classes.length; i++) {
				classes[i] = Class.forName(strings[i]);
			}
			// 按方法名称与方法参数类型列表加载类方法
			try {
				clazz.getMethod(methodName, classes);
			} catch (Exception e) {
				try {
					clazz.getMethod(methodName);
				} catch (Exception e2) {
					throw new UnsupportedOperationException("API实现方法[" + api.getApiMethod() + "]不存在");
				}
			}
		} catch (ClassNotFoundException e) {
			throw new UnsupportedOperationException("API实现方法参数类型[" + api.getApiMethodParamTypes() + "]不存在");
		}
	}

	@Override
	public PageFinder<Api> queryApi(DetachedCriteria criteria, Query query) throws Exception {
		Session session = null;
		try {
			session = apiDao.getHibernateSession();
			Criteria executableCriteria = criteria.getExecutableCriteria(session);
			return apiDao.pagedByCriteria(executableCriteria, query.getPage(), query.getPageSize());
		} finally {
			apiDao.releaseHibernateSession(session);
		}
	}

	@Override
	public PageFinder<DBObject> queryApiLogFromMongoDB(ApiLog apiLog, Date fromOperated, Date toOperated, String apiInputParam, String apiInputParamValue, Query query) throws Exception {
		BasicDBObject condition = new BasicDBObject();
		BasicDBObject conditionGroup = new BasicDBObject();
		
		if(apiLog.getIsCallSucess()!=null){
			condition.put("isCallSucess", apiLog.getIsCallSucess());
		}
		if (StringUtils.isNotBlank(apiLog.getClientIp())) {
			condition.put("clientIp", apiLog.getClientIp());
		}
		if (StringUtils.isNotBlank(apiLog.getOperationResult())) {
			condition.put("operationResult", Pattern.compile(apiLog.getOperationResult().trim()));
		}
		if (StringUtils.isNotBlank(apiLog.getApiMethod())) {
			condition.put("operationParameters.method", apiLog.getApiMethod());
		}
		if (StringUtils.isNotBlank(apiLog.getOperator())) {
			condition.put("operationParameters.app_key", apiLog.getOperator());
		}
		if (StringUtils.isNotBlank(apiInputParam) && StringUtils.isNotBlank(apiInputParamValue)) {
			condition.put("operationParameters." + apiInputParam.trim(), Pattern.compile(apiInputParamValue));
		}
		if (fromOperated != null) {
			conditionGroup.put("$gte", fromOperated);
		}
		if (toOperated != null) {
			conditionGroup.put("$lte", toOperated);
		}
		if (!conditionGroup.isEmpty()) {
			condition.put("operated", conditionGroup);
		}
		
		return genericMongoDao.getDBObject(ApiLog.class.getAnnotation(Documented.class).name(), condition, new BasicDBObject("operated", -1), query);
	}

	
	@Override
	public List<Api> findApiIdList() throws Exception {
		String sql = "SELECT t.id, t.api_name, t.api_method, t.is_enable, t.is_save_result FROM tbl_merchant_api t";
//		String sql = "SELECT t.id FROM tbl_merchant_api t WHERE t.is_enable = '0'";
//		final List<String> list = jdbcTemplate.query(sql, new RowMapper<String>(){
//
//			@Override
//			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return rs.getString("id");
//			}
//			
//		});
		final List<Api> list = jdbcTemplate.query(sql.toString(), new RowMapper<Api>(){

			@Override
			public Api mapRow(ResultSet rs, int rowNum) throws SQLException {
				Api api = new Api();
				api.setId(rs.getString("id"));
				api.setApiName(rs.getString("api_name"));
				api.setApiMethod(rs.getString("api_method"));
				api.setIsEnable(rs.getString("is_enable"));
				api.setIsSaveResult(rs.getString("is_save_result"));
				return api;
			}
			
		});
		return list;
	}

	@Override
	public List<String> findAppKeyByApiId(String apiId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select k.app_key ");
		sql.append(" from tbl_merchant_api_license t ");
		sql.append(" INNER JOIN tbl_merchant_api_key k ON t.key_id = k.id ");
		sql.append(" WHERE t.api_id = ?  AND k.status = 1");
		final List<String> list  = jdbcTemplate.query(sql.toString(), new Object[]{apiId}, new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				return rs.getString("app_key");
			}
			
			
		});
		return list;
	}

	@Override
	public List<AppKeySecretVo> findAppKeySecret() {
	
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT t1.app_key, t1.app_secret, t2.metadata_val ");
		sql.append(" FROM tbl_merchant_api_key t1  ");
		sql.append(" INNER JOIN tbl_merchant_api_key_metadata t2 ON (t1.id = t2.key_id) ");
		sql.append(" WHERE t1.status = '1' ");
		final List<AppKeySecretVo> list = jdbcTemplate.query(sql.toString(), new RowMapper<AppKeySecretVo>(){

			@Override
			public AppKeySecretVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				AppKeySecretVo vo = new AppKeySecretVo();
				vo.setAppKey(rs.getString("app_key"));
				vo.setSecret(rs.getString("app_secret"));
				vo.setMetadataVal(rs.getString("metadata_val"));
				return vo;
			}
			
		});
		return list;
	}
	
	@Override
	public int findApiAuthCount() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT(1) FROM tbl_merchant_api_key t1 ");
		sql.append(" INNER JOIN tbl_merchant_api_license t2 on t2.key_id = t1.id  ");
		sql.append(" INNER JOIN tbl_merchant_api t3 ON t3.id = t2.api_id ");
		sql.append(" WHERE t3.is_enable = 0 AND t1.`status` = 1 ");
		return jdbcTemplate.queryForInt(sql.toString());
	}

	@Override
	public int findAppKeyAuthCount() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT(1) FROM tbl_merchant_api_key t1 ");
		sql.append(" INNER JOIN tbl_merchant_api_key_metadata t2 on t1.id = t2.key_id ");
		sql.append(" WHERE t1.`status` = 1 ");
		return jdbcTemplate.queryForInt(sql.toString());
	}

	@Override
	public AppKeySecretVo findAppKeySecretByAppkey(String appkey) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT t1.app_key, t1.app_secret, t2.metadata_val,t2.metadata_key ");
		sql.append(" FROM tbl_merchant_api_key t1  ");
		sql.append(" INNER JOIN tbl_merchant_api_key_metadata t2 ON (t1.id = t2.key_id) ");
		sql.append(" WHERE t1.app_key = ? ");
		final List<AppKeySecretVo> list = jdbcTemplate.query(sql.toString(),new Object[]{appkey}, new RowMapper<AppKeySecretVo>(){

			@Override
			public AppKeySecretVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				AppKeySecretVo vo = new AppKeySecretVo();
				vo.setAppKey(rs.getString("app_key"));
				vo.setSecret(rs.getString("app_secret"));
				vo.setMetadataVal(rs.getString("metadata_val"));
				vo.setMetadataKey(rs.getString("metadata_key"));
				return vo;
			}
			
		});
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		
		return new AppKeySecretVo();
	}

	@Override
	public String findEmailByAppKey(String appkey) throws Exception {
		AppKeySecretVo vo = this.findAppKeySecretByAppkey(appkey);
		if(StringUtils.equals(vo.getMetadataKey(), "MERCHANTS")){
			String merchantCode = vo.getMetadataVal();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT t.emails FROM tbl_sp_supplier_punish_rule t ");
			sql.append(" WHERE t.merchant_code = ? ");
			final List<String> list = jdbcTemplate.query(sql.toString(),new Object[]{merchantCode}, new RowMapper<String>(){

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString(1);
				}
				
			});
			if(CollectionUtils.isNotEmpty(list)){
				return list.get(0);
			}
		}
		if(StringUtils.equals(vo.getMetadataKey(), "CHAIN")){
			String sellerId = vo.getMetadataVal();
			return apiDistributorService.findDistributorDevEmailsBySellerId(sellerId);
		}
		
		
		return "";
	}
	
	
	
	
	/**
	 * 更新API缓存
	 */
	public void refreshApiCache() throws Exception{
		List<Api> list = findApiIdList();
		stringRedisTemplate.delete(API_APPKEY_SET_KEY);
		stringRedisTemplate.delete(API_HASH_KEY);
		for (Api api : list) {
			String apiId = api.getId();
			//当前有效
			List<String> apiAppkeyList = findAppKeyByApiId(apiId);
			for(String appKey:apiAppkeyList){
				String value = StringUtils.join(new Object[]{apiId,appKey}, "#");
				setOperations.add(API_APPKEY_SET_KEY,value);
			}
			hashOperations.put(API_HASH_KEY, apiId, api.getIsEnable());
			hashOperations.put(API_IS_SAVE_RESULT_HASH_KEY, api.getApiMethod(), api.getIsSaveResult());
		}
	}
	
	

}
