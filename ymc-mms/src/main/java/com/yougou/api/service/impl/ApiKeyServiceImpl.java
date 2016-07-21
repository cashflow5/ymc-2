package com.yougou.api.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.EnumType;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.DateUtil;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.infrastructure.util.JDBCUtils;
import com.belle.infrastructure.util.UUIDUtil;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.yougou.api.beans.AppType;
import com.yougou.api.constant.Constants;
import com.yougou.api.dao.IApiKeyDao;
import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiKey;
import com.yougou.api.model.pojo.ApiKey.ApiKeyStatus;
import com.yougou.api.model.pojo.ApiKeyMetadata;
import com.yougou.api.service.IApiKeyService;
import com.yougou.api.service.IApiService;
import com.yougou.api.util.MD5Encryptor;
import com.yougou.dms.api.ApiDistributorService;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.merchant.api.supplier.service.IMerchantsApi;
import com.yougou.merchant.api.supplier.service.ISupplierService;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog.OperationType;
import com.yougou.merchant.api.supplier.vo.SupplierVo;

@Service
public class ApiKeyServiceImpl implements IApiKeyService {

	private Logger logger = Logger.getLogger(ApiKeyServiceImpl.class);
	@Resource
	private IApiKeyDao apiKeyDao;
	@Resource
	private IMerchantsApi merchantOperationLogService;
	@Resource
	private ISupplierService supplierService;
	@Resource
	private IApiService apiService;
	@Resource
	private ApiDistributorService apiDistributorService;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	private org.hibernate.type.Type customAppType;
	private org.hibernate.type.Type customApiKeyStatus;
	
	{
		Properties parameters = new Properties();
		parameters.put("enumClass", AppType.class.getName());
		parameters.put("type", java.sql.Types.VARCHAR);
		customAppType = Hibernate.custom(EnumType.class, parameters);
		parameters.put("enumClass", ApiKeyStatus.class.getName());
		parameters.put("type", java.sql.Types.INTEGER);
		customApiKeyStatus = Hibernate.custom(EnumType.class, parameters);
	}
	
	@Override
	public ApiKey getApiKeyById(String id) throws Exception {
		return apiKeyDao.getById(id);
	}

	@Override
	public ApiKey getApiKeyByMetadataVal(String metadataVal) throws Exception {
		Session session = null;
		try {
			session = apiKeyDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiKey.class);
			criteria.add(Restrictions.eq("merchantApiKeyMetadatas.metadataVal", metadataVal));
			return (ApiKey) criteria.uniqueResult();
		} finally {
			apiKeyDao.releaseHibernateSession(session);
		}
	}
	
	@Override
	public PageFinder<ApiKey> queryApiKey(ApiKeyMetadata apiKeyMetadata, Query query) throws Exception {
		StringBuilder sqlPrefixBuilder = new StringBuilder();
		sqlPrefixBuilder.append(" select ");
		sqlPrefixBuilder.append(" t1.id, t1.app_key as appKey, t1.app_secret as appSecret, t1.status, t1.update_user as updateUser, t1.update_time as updateTime ");
		StringBuilder sqlSuffixBuilder = new StringBuilder();
		sqlSuffixBuilder.append(" from ");
		sqlSuffixBuilder.append(" tbl_merchant_api_key t1 ");
		sqlSuffixBuilder.append(" left join ");
		sqlSuffixBuilder.append(" tbl_merchant_api_key_metadata t2 ");
		sqlSuffixBuilder.append(" on(t1.id = t2.key_id) ");
		sqlSuffixBuilder.append(" left join ");
		sqlSuffixBuilder.append(" ( ");
		sqlSuffixBuilder.append(apiKeyDao.getApiKeyPotentialCustomersSqlStatement());
		sqlSuffixBuilder.append(" ) t3 ");
		sqlSuffixBuilder.append(" on (t2.metadata_val = t3.metadata_val) ");
		sqlSuffixBuilder.append(" where 1 = 1 ");
		
		// 拼装查询条件
		Map<String, Object> properties = new HashMap<String, Object>();
		if (apiKeyMetadata != null) {
			if(StringUtils.isNotBlank(apiKeyMetadata.getMetadataTag())) {
				sqlSuffixBuilder.append(" and t3.metadata_tag like :metadata_tag");
				properties.put("metadata_tag", '%' + apiKeyMetadata.getMetadataTag() + '%');
			}
			if (apiKeyMetadata.getMetadataKey() != null) {
				sqlSuffixBuilder.append(" and t2.metadata_key = :metadata_key ");
				properties.put("metadata_key", apiKeyMetadata.getMetadataKey().name());
			}
			if (StringUtils.isNotBlank(apiKeyMetadata.getMetadataVal())) {
				sqlSuffixBuilder.append(" and t2.metadata_val = :metadata_val ");
				properties.put("metadata_val", apiKeyMetadata.getMetadataVal());
			}
			if (apiKeyMetadata.getApiKey() != null) {
				if (apiKeyMetadata.getApiKey().getStatus() != null) {
					sqlSuffixBuilder.append(" and t1.status = :status ");
					properties.put("status", apiKeyMetadata.getApiKey().getStatus().ordinal());
				}
			}
		}
		
		Session session = null;
		PageFinder<ApiKey> pageFinder = null;
		try {
			session = apiKeyDao.getHibernateSession();
			SQLQuery sqlQuery = session.createSQLQuery("select count(1) " + sqlSuffixBuilder.toString());
			sqlQuery.setProperties(properties);
			int rowCount = ((Number) sqlQuery.uniqueResult()).intValue();
			pageFinder = new PageFinder<ApiKey>(query.getPage(), query.getPageSize(), rowCount);
			if (rowCount > 0) {
				// 拼装分组排序
				sqlSuffixBuilder.append(" group by ");
				sqlSuffixBuilder.append(" t1.id, t1.app_key, t1.app_secret, t1.status, t1.update_user, t1.update_time ");
				sqlSuffixBuilder.append(" order by ");
				sqlSuffixBuilder.append(" t1.update_time desc ");
				// 抓取APP密匙
				sqlQuery = session.createSQLQuery(sqlPrefixBuilder.append(sqlSuffixBuilder).toString());
				sqlQuery.setFirstResult(pageFinder.getStartOfPage());
				sqlQuery.setMaxResults(pageFinder.getPageSize());
				sqlQuery.setProperties(properties);
				sqlQuery.addScalar("id", Hibernate.STRING);
				sqlQuery.addScalar("appKey", Hibernate.STRING);
				sqlQuery.addScalar("appSecret", Hibernate.STRING);
				sqlQuery.addScalar("status", customApiKeyStatus);
				sqlQuery.addScalar("updateTime", Hibernate.STRING);
				sqlQuery.addScalar("updateUser", Hibernate.STRING);
				sqlQuery.setResultTransformer(Transformers.aliasToBean(ApiKey.class));
				List<ApiKey> merchantApiKeys = sqlQuery.list();
				// 抓取APP密匙元数据
				sqlPrefixBuilder.setLength(0);
				sqlPrefixBuilder.append(" select ");
				sqlPrefixBuilder.append(" t1.metadata_key as metadataKey, t1.metadata_val as metadataVal, t2.metadata_tag as metadataTag ");
				sqlPrefixBuilder.append(" from ");
				sqlPrefixBuilder.append(" tbl_merchant_api_key_metadata t1 ");
				sqlPrefixBuilder.append(" left join ");
				sqlPrefixBuilder.append(" ( ");
				sqlPrefixBuilder.append(apiKeyDao.getApiKeyPotentialCustomersSqlStatement());
				sqlPrefixBuilder.append(" ) t2 ");
				sqlPrefixBuilder.append(" on(t1.metadata_val = t2.metadata_val) ");
				sqlPrefixBuilder.append(" where ");
				sqlPrefixBuilder.append(" t1.key_id = ? ");
				sqlQuery = session.createSQLQuery(sqlPrefixBuilder.toString());
				sqlQuery.addScalar("metadataKey", customAppType);
				sqlQuery.addScalar("metadataVal", Hibernate.STRING);
				sqlQuery.addScalar("metadataTag", Hibernate.STRING);
				sqlQuery.setResultTransformer(Transformers.aliasToBean(ApiKeyMetadata.class));
				for (ApiKey merchantApiKey : merchantApiKeys) {
					sqlQuery.setParameter(0, merchantApiKey.getId());
					merchantApiKey.setApiKeyMetadatas(new HashSet<ApiKeyMetadata>(sqlQuery.list()));
				}
				pageFinder.setData(merchantApiKeys);
			}
		} finally {
			apiKeyDao.releaseHibernateSession(session);
		}
		return pageFinder;
	}
	
	@Override
	public List<ApiKeyMetadata> queryApiKeyCustomers(String apiKeyId) throws Exception {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" select ");
		sqlBuilder.append(" t1.metadata_key as metadataKey, t1.metadata_val as metadataVal, t1.metadata_tag as metadataTag ");
		sqlBuilder.append(" from ");
		sqlBuilder.append(" ( ");
		sqlBuilder.append(apiKeyDao.getApiKeyPotentialCustomersSqlStatement());
		sqlBuilder.append(" ) t1 ");
		sqlBuilder.append(" inner join ");
		sqlBuilder.append(" tbl_merchant_api_key_metadata t2 ");
		sqlBuilder.append(" on(t1.metadata_val = t2.metadata_val) ");
		sqlBuilder.append(" where ");
		sqlBuilder.append(" t2.key_id = ? ");

		Session session = null;
		try {
			session = apiKeyDao.getHibernateSession();
			SQLQuery sqlQuery = session.createSQLQuery(sqlBuilder.toString());
			sqlQuery.setParameter(0, apiKeyId);
			sqlQuery.addScalar("metadataKey", customAppType);
			sqlQuery.addScalar("metadataVal", Hibernate.STRING);
			sqlQuery.addScalar("metadataTag", Hibernate.STRING);
			sqlQuery.setResultTransformer(Transformers.aliasToBean(ApiKeyMetadata.class));
			return sqlQuery.list();
		} finally {
			apiKeyDao.releaseHibernateSession(session);
		}
	}

	@Override
	public List<ApiKeyMetadata> queryApiKeyPotentialCustomers() throws Exception {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" select ");
		sqlBuilder.append(" t1.metadata_key as metadataKey, t1.metadata_val as metadataVal, t1.metadata_tag as metadataTag ");
		sqlBuilder.append(" from ");
		sqlBuilder.append(" ( ");
		sqlBuilder.append(apiKeyDao.getApiKeyPotentialCustomersSqlStatement());
		sqlBuilder.append(" ) t1 ");
		sqlBuilder.append(" left join ");
		sqlBuilder.append(" tbl_merchant_api_key_metadata t2 ");
		sqlBuilder.append(" on(t1.metadata_val = t2.metadata_val) ");
		sqlBuilder.append(" where ");
		sqlBuilder.append(" t2.id is null ");

		Session session = null;
		try {
			session = apiKeyDao.getHibernateSession();
			SQLQuery sqlQuery = session.createSQLQuery(sqlBuilder.toString());
			sqlQuery.addScalar("metadataKey", customAppType);
			sqlQuery.addScalar("metadataVal", Hibernate.STRING);
			sqlQuery.addScalar("metadataTag", Hibernate.STRING);
			sqlQuery.setResultTransformer(Transformers.aliasToBean(ApiKeyMetadata.class));
			return sqlQuery.list();
		} finally {
			apiKeyDao.releaseHibernateSession(session);
		}
	}
	
	@Override
	public List<ApiKeyMetadata> queryApiKeyByType(String type) throws Exception {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT km.metadata_key as metadataKey,km.metadata_val as metadataVal ");
		sqlBuilder.append(" FROM tbl_merchant_api_key_metadata km ");
		sqlBuilder.append(" WHERE km.metadata_key = ? ");
		
		
		Session session = null;
		try {
			session = apiKeyDao.getHibernateSession();
			SQLQuery sqlQuery = session.createSQLQuery(sqlBuilder.toString());
			sqlQuery.setParameter(0, type);
			sqlQuery.addScalar("metadataKey", customAppType);
			sqlQuery.addScalar("metadataVal", Hibernate.STRING);
			sqlQuery.setResultTransformer(Transformers.aliasToBean(ApiKeyMetadata.class));
			return sqlQuery.list();
		} finally {
			apiKeyDao.releaseHibernateSession(session);
		}
	}
		
	
	@Override
	public List<ApiKeyMetadata> queryApiKeyByapiKeyIdAndType(String apiKeyId, String type) throws Exception {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT km.metadata_key as metadataKey,km.metadata_val as metadataVal ");
		sqlBuilder.append(" FROM tbl_merchant_api_key_metadata km ");
		sqlBuilder.append(" WHERE km.key_id = ? ");
		sqlBuilder.append(" AND km.metadata_key = ? ");
		
		
		Session session = null;
		try {
			session = apiKeyDao.getHibernateSession();
			SQLQuery sqlQuery = session.createSQLQuery(sqlBuilder.toString());
			sqlQuery.setParameter(0, apiKeyId);
			sqlQuery.setParameter(1, type);
			sqlQuery.addScalar("metadataKey", customAppType);
			sqlQuery.addScalar("metadataVal", Hibernate.STRING);
			sqlQuery.setResultTransformer(Transformers.aliasToBean(ApiKeyMetadata.class));
			return sqlQuery.list();
		} finally {
			apiKeyDao.releaseHibernateSession(session);
		}
	}

	@Override
	@Transactional
	public void saveApiKey(ApiKey... apiKeys) throws Exception {
		for(ApiKey apiKey : apiKeys) {
			apiKeyDao.save(apiKey);
		}
	}

	@Override
	public List<Map<String, Object>> queryApiKeyOwnedApiList(String apiKeyId) {
		if (StringUtils.isBlank(apiKeyId)) {
			return Collections.emptyList();
		}
		
		String sql = "select t1.api_id, t2.api_name, t2.api_code, t3.category_name from tbl_merchant_api_license t1 inner join tbl_merchant_api t2 on(t1.api_id = t2.id) inner join tbl_merchant_api_category t3 on(t2.category_id = t3.id) where t1.key_id = ? order by t2.category_id";
		Object[] values = { apiKeyId };
		return JDBCUtils.getInstance().listResultMap(sql, values);
	}
	
	@Override
	public List<Map<String, Object>> queryApiKeyUnownedApiList(String apiKeyId) {
		if (StringUtils.isBlank(apiKeyId)) {
			return Collections.emptyList();
		}
		
		String sql = "select t1.id as api_id, t1.api_name, t2.category_name from tbl_merchant_api t1 inner join tbl_merchant_api_category t2 on(t1.category_id = t2.id) where not exists(select 1 from tbl_merchant_api_license t3 where SUBSTRING(t3.api_id,1,32) = t1.id and t3.key_id = ?) order by t1.category_id";
		Object[] values = { apiKeyId };
		return JDBCUtils.getInstance().listResultMap(sql, values);
	}
	
	@Override
	@Transactional
	public boolean authorizeApi(String[] apiIds, String apiKeyId, SystemmgtUser systemmgtUser,String appkey,String userName) throws Exception {
		// 获取商家编码
		String merchantCode = (String) JDBCUtils.getInstance().uniqueResult("select metadata_val from tbl_merchant_api_key_metadata where metadata_key = 'MERCHANTS' and key_id = ?", new Object[] { apiKeyId });
		if(StringUtils.isEmpty(userName)){
			userName = systemmgtUser.getUsername();
		}
		StringBuilder diffSqlBuilder = new StringBuilder();
		diffSqlBuilder.append(" select ");
		diffSqlBuilder.append(" group_concat(permissions separator ';') ");
		diffSqlBuilder.append(" from ( ");
		diffSqlBuilder.append(" select ");
		diffSqlBuilder.append(" concat(t2.api_name, '(', t3.category_name, ')') as permissions ");
		diffSqlBuilder.append(" from ");
		diffSqlBuilder.append(" tbl_merchant_api_license t1 ");
		diffSqlBuilder.append(" inner join tbl_merchant_api t2 on(t1.api_id = t2.id) ");
		diffSqlBuilder.append(" inner join tbl_merchant_api_category t3 on(t2.category_id = t3.id) ");
		diffSqlBuilder.append(" inner join tbl_merchant_api_key_metadata t4 on(t1.key_id = t4.key_id) ");
		diffSqlBuilder.append(" where ");
		diffSqlBuilder.append(" t4.metadata_val = ? ");
		diffSqlBuilder.append(" group by ");
		diffSqlBuilder.append(" t2.api_name, t3.category_name ");
		diffSqlBuilder.append(" ) tmp ");
		String diffSql = diffSqlBuilder.toString();
		Object ownPermissions = JDBCUtils.getInstance().uniqueResult(diffSql, new Object[] { merchantCode });
		
		// 先删除原始授权API
		String sql = "delete from tbl_merchant_api_license where key_id = ?";
		List<Object[]> sqlParams = new ArrayList<Object[]>();
		sqlParams.add(new Object[] { apiKeyId });
		
		MerchantOperationLog operationLog1=new MerchantOperationLog();
		operationLog1.setId(UUIDUtil.getUUID());
		operationLog1.setMerchantCode(appkey);
		operationLog1.setOperated(new Date());
		operationLog1.setOperationNotes("先删除原始授权API");
		operationLog1.setOperationType(OperationType.APPKEY_AUTH);
		operationLog1.setOperator(userName);
		merchantOperationLogService.saveMerchantOperationLog(operationLog1);
		
		Map<String, List<Object[]>> sqlBatchs = new LinkedHashMap<String, List<Object[]>>();
		sqlBatchs.put(sql, sqlParams);
		String licensedStr="";
		// 再重新授权API
		if (ArrayUtils.isNotEmpty(apiIds)) {
			sql = "insert into tbl_merchant_api_license(id, api_id, key_id, licensor, licensed) values(?, ?, ?, ?, ?)";
			Date licensed = new Date();
			sqlParams = new ArrayList<Object[]>();
			for (int i = 0; i < apiIds.length; i++) {
				Api api=apiService.getApiById(apiIds[i]);
				if(api!=null){
					sqlParams.add(new Object[] { UUIDUtil.getUUID(), apiIds[i], apiKeyId, userName, licensed });
					licensedStr=licensedStr+","+api.getApiCategory().getCategoryName()+"("+api.getApiName()+")";
				}
			}
			sqlBatchs.put(sql, sqlParams);
		}
		
		MerchantOperationLog operationLog2=new MerchantOperationLog();
		operationLog2.setId(UUIDUtil.getUUID());
		operationLog2.setMerchantCode(appkey);
		operationLog2.setOperated(new Date(System.currentTimeMillis()+1000*60*2));
		operationLog2.setOperationNotes("授权API:"+licensedStr);
		operationLog2.setOperationType(OperationType.APPKEY_AUTH);
		operationLog2.setOperator(userName);
		merchantOperationLogService.saveMerchantOperationLog(operationLog2);
		
		boolean result = JDBCUtils.getInstance().executeBatch(sqlBatchs);
		
		if (result) {
			Object finalPermissions = JDBCUtils.getInstance().uniqueResult(diffSql, new Object[] { merchantCode });
			if (!ObjectUtils.equals(ownPermissions, finalPermissions)) {
				/** 添加商家帐户日志 Modifier by yang.mq **/
				MerchantOperationLog operationLog = new MerchantOperationLog();
				operationLog.setId(UUIDUtil.getUUID());
				operationLog.setMerchantCode(merchantCode);
				operationLog.setOperator(userName);
				operationLog.setOperated(new Date());
				operationLog.setOperationType(OperationType.API);
				operationLog.setOperationNotes(MessageFormat.format("API授权列表由【{0}】修改为【{1}】", ownPermissions, finalPermissions));
				merchantOperationLogService.saveMerchantOperationLog(operationLog);
			}
		}
		
		return result;
	}
	
	@Override
	@Transactional
	public boolean bindingApiKeyCustomers(String[] customers, AppType[] appTypes, String apiKeyId,String userName,String appKey) throws Exception {
		
		
		String sqlselect = "select metadata_val,metadata_key from tbl_merchant_api_key_metadata where key_id = ?";
		List<Map<String, Object>> results=JDBCUtils.getInstance().listResultMap(sqlselect, new Object[] { apiKeyId });
		boolean exist=false;
		Map<String, List<Object[]>> sqlBatchs = new LinkedHashMap<String, List<Object[]>>();
		// 先删除原始授权API
		String sql = "delete from tbl_merchant_api_key_metadata where key_id = ? and metadata_val=?";
		List<Object[]> sqlParams=null;
		String sj="";
		for(Map<String, Object> result:results){
			if(ArrayUtils.isNotEmpty(customers)){
				for(String customer:customers){
					if(result.get("metadata_val").toString().equalsIgnoreCase(customer)){
						exist=true;
						break;
					}
				}
			}
				if(!exist){
					sqlParams = new ArrayList<Object[]>();
					sqlParams.add(new Object[] { apiKeyId,result.get("metadata_val").toString()});
					sqlBatchs.put(sql, sqlParams);
					if("MERCHANTS".equalsIgnoreCase(result.get("metadata_key").toString())){
						SupplierVo supplierVo=supplierService.getSupplierByMerchantCode(result.get("metadata_val").toString());
						sj=supplierVo==null?result.get("metadata_val").toString():(supplierVo.getSupplier()+"("+supplierVo.getSupplierCode()+")");
					}else if("CHAIN".equalsIgnoreCase(result.get("metadata_key").toString())){
						Map<String,String> distributorMap =apiDistributorService.queryAllDistributor();
						sj=distributorMap.get(result.get("metadata_val").toString())+"("+result.get("metadata_val").toString()+")";
					}else{
						sj="位置类型";
					}
					
					
					MerchantOperationLog operationLog=new MerchantOperationLog();
					operationLog.setId(UUIDUtil.getUUID());
					operationLog.setMerchantCode(appKey);
					operationLog.setOperated(new Date());
					operationLog.setOperationNotes("解绑appkey，商家:"+sj);
					operationLog.setOperationType(OperationType.APPKEY_UNBUND);
					operationLog.setOperator(userName);
					merchantOperationLogService.saveMerchantOperationLog(operationLog);
				}
			
		}
		String sql2 = "insert into tbl_merchant_api_key_metadata(id, key_id, metadata_key, metadata_val) values(?, ?, ?, ?)";
		// 再重新授权API
		if (ArrayUtils.isNotEmpty(customers)) {
			for(String customer:customers){
				for(Map<String, Object> result:results){
					if(result.get("metadata_val").toString().equalsIgnoreCase(customer)){
						exist=true;
						break;
					}
				}
				if(!exist){
					sqlParams = new ArrayList<Object[]>();
					for (int i = 0; i < customers.length; i++) {
						sqlParams.add(new Object[] { UUIDUtil.getUUID(), apiKeyId, appTypes[i].name(), customers[i] });
						
						if("MERCHANTS".equalsIgnoreCase(appTypes[i].name())){
							SupplierVo supplierVo=supplierService.getSupplierByMerchantCode(customer);
							sj=supplierVo==null?customer:(supplierVo.getSupplier()+"("+supplierVo.getSupplierCode()+")");
						}else if("CHAIN".equalsIgnoreCase(appTypes[i].name())){
							Map<String,String> distributorMap =apiDistributorService.queryAllDistributor();
							sj=distributorMap.get(customer)+"("+customer+")";
						}else{
							sj="位置类型";
						}
						
						MerchantOperationLog operationLog=new MerchantOperationLog();
						operationLog.setId(UUIDUtil.getUUID());
						operationLog.setMerchantCode(appKey);
						operationLog.setOperated(new Date());
						operationLog.setOperationNotes("绑定appkey,商家:"+sj);
						operationLog.setOperationType(OperationType.APPKEY_BINDING);
						operationLog.setOperator(userName);
						merchantOperationLogService.saveMerchantOperationLog(operationLog);
					}
					sqlBatchs.put(sql2, sqlParams);
				}
			}
		}
		
		return JDBCUtils.getInstance().executeBatch(sqlBatchs);
	}

	@Override
	public ApiKey randomSeekTestApiKey() throws Exception {
		Session session = null;
		try {
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append(" select ");
			sqlBuilder.append(" t1.app_key as appKey, t1.app_secret as appSecret ");
			sqlBuilder.append(" from ");
			sqlBuilder.append(" tbl_merchant_api_key t1 ");
			sqlBuilder.append(" inner join ");
			sqlBuilder.append(" tbl_merchant_api_key_metadata t2 ");
			sqlBuilder.append(" on(t1.id = t2.key_id) ");
			sqlBuilder.append(" left join ");
			sqlBuilder.append(" ( ");
			sqlBuilder.append(apiKeyDao.getApiKeyPotentialCustomersSqlStatement());
			sqlBuilder.append(" ) t3 ");
			sqlBuilder.append(" on(t2.metadata_val = t3.metadata_val) ");
			sqlBuilder.append(" where ");
			sqlBuilder.append(" t3.metadata_tag like '%测试%' ");
			session = apiKeyDao.getHibernateSession();
			SQLQuery sqlQuery = session.createSQLQuery(sqlBuilder.toString());
			sqlQuery.addScalar("appKey", Hibernate.STRING);
			sqlQuery.addScalar("appSecret", Hibernate.STRING);
			sqlQuery.setResultTransformer(Transformers.aliasToBean(ApiKey.class));
			List<ApiKey> list = sqlQuery.list();
			return list.size() == 0 ? null : list.get(new Random().nextInt(list.size()));
		} finally {
			apiKeyDao.releaseHibernateSession(session);
		}
	}

	@Override
	@Transactional
	public void updateAppKeyStatus(String appkey, String status) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE tbl_merchant_api_key ");
		sql.append(" SET status = ? ");
		sql.append(" WHERE app_key = ?");
		
		jdbcTemplate.update(sql.toString(),status, appkey);
	}
	
	/**
	 * 备份API
	 * @param appkey
	 * @throws Exception
	 */
	@Override
	public void backupApiLicence(String appkey) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE tbl_merchant_api_license SET api_id = CONCAT(api_id,'_bak') ");
		sql.append(" WHERE	key_id = ? ");
		sql.append(" AND EXISTS (SELECT 1 FROM	tbl_merchant_api t	WHERE ");
		sql.append(" t.id = api_id	AND t.api_code IN ('INVENTORY_QUERY','INVENTORY_UPDATE')) ");
		
		jdbcTemplate.update(sql.toString(), appkey);
	}
	
	/**
	 * 恢复API
	 * @param appkey
	 * @throws Exception
	 */
	@Override
	public void recoverApiLicence(String appkey) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE tbl_merchant_api_license SET api_id = SUBSTRING(api_id,1,32) ");
		sql.append(" WHERE	key_id = ? ");
		sql.append(" AND EXISTS (SELECT 1 FROM	tbl_merchant_api t	WHERE ");
		sql.append(" t.id = SUBSTRING(api_id,1,32) AND t.api_code IN ('INVENTORY_QUERY','INVENTORY_UPDATE')) ");
		
		jdbcTemplate.update(sql.toString(), appkey);
	}
	private ApiKey queryApiKey(String appkey){
		 String hql = "select * from ApiKey where appKey = ? ";
		 
		List<ApiKey> keyList =  this.apiKeyDao.find(hql,new String[] {appkey});
		  
		return CollectionUtils.isEmpty(keyList)?null:keyList.get(0);
	}
	
	/**
	 * 根据metadataVal查询ApiKeyMetadata信息
	 * @param metadataVal
	 * @return
	 * @throws Exception
	 */
	@Override
	public ApiKeyMetadata queryApiKeyByMetadataVal(String metadataVal) throws Exception {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT km.metadata_key as metadataKey,km.metadata_val as metadataVal,km.key_id as id ");
		sqlBuilder.append(" FROM tbl_merchant_api_key_metadata km ");
		sqlBuilder.append(" WHERE km.metadata_key = 'MERCHANTS' ");
		sqlBuilder.append(" AND km.metadata_val = ? ");
		
		
		Session session = null;
		try {
			session = apiKeyDao.getHibernateSession();
			SQLQuery sqlQuery = session.createSQLQuery(sqlBuilder.toString());
			sqlQuery.setParameter(0, metadataVal);
			sqlQuery.addScalar("metadataKey", customAppType);
			sqlQuery.addScalar("metadataVal", Hibernate.STRING);
			sqlQuery.addScalar("id", Hibernate.STRING);
			sqlQuery.setResultTransformer(Transformers.aliasToBean(ApiKeyMetadata.class));
			List<ApiKeyMetadata> list = sqlQuery.list();
			return list.size() == 0 ? null : list.get(0);
		} finally {
			apiKeyDao.releaseHibernateSession(session);
		}
	}

	public String generateApiKey(HttpServletRequest request,String userName,String merchantCode) throws Exception {

		if(StringUtils.isEmpty(userName)){
			
			SystemmgtUser user = GetSessionUtil.getSystemUser(request);
			userName = user.getLoginName();
		}
		 
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "apiKeyMetadatas" });
		// IOUtils.write(JSONObject.fromObject(apiKey, jsonConfig).toString(),
		// response.getWriter());

		ApiKey apiKey  = saveGenerateKey(userName, merchantCode);

		return JSONObject.fromObject(apiKey, jsonConfig).toString();

	}
	
	@Override
	@Transactional
	public ApiKey saveGenerateKey(String userName,String merchantCode){
		// 生成密匙
		String appKey = new java.rmi.server.UID().toString().replaceAll(
				"(:|-)", "_");
		// 生成密匙口令
		List<String> appSecretSections = Arrays.asList(appKey.split(""));
		Collections.shuffle(appSecretSections);
		String appSecret = MD5Encryptor.encrypt(appSecretSections.toString());
		ApiKey apiKey = new ApiKey();
		apiKey.setId( UUIDUtil.getUUID());
		apiKey.setAppKey(appKey);
		apiKey.setAppSecret(appSecret);
		apiKey.setStatus(ApiKeyStatus.DISABLE);
		apiKey.setUpdateTime(DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
		apiKey.setUpdateUser(StringUtils.isEmpty(merchantCode)?userName:merchantCode);
		
		Map<String, List<Object[]>> sqlBatchs = new LinkedHashMap<String, List<Object[]>>();
	 
		String sql = "insert into tbl_merchant_api_key(id, app_key, app_secret,status,update_user,update_time)" +
				" values(?, ?, ?,?, ?, ?)";
		List<Object[]> sqlParams = new ArrayList<Object[]>();
		sqlParams.add(new Object[] {apiKey.getId(),appKey, appSecret,1,StringUtils.isEmpty(merchantCode)?userName:merchantCode,DateUtil.format(new Date(),
				"yyyy-MM-dd hh:mm:ss")});
		sqlBatchs.put(sql, sqlParams);
		
		boolean result = JDBCUtils.getInstance().executeBatch(sqlBatchs);
		if(result){
			
			logger.info("新增 app key 成功 。 appkeyId:"+apiKey.getAppKey());
			MerchantOperationLog operationLog = new MerchantOperationLog();
			operationLog.setId(UUIDUtil.getUUID());
			operationLog.setMerchantCode(appKey);
			operationLog.setOperated(new Date());
			operationLog.setOperationNotes("新建appkey");
			operationLog.setOperationType(OperationType.AppKey_ADD);
			operationLog.setOperator(userName);
			merchantOperationLogService.saveMerchantOperationLog(operationLog);
		}
//		try {
//			//saveApiKey(new ApiKey[]{apiKey});
//			//apiKeyDao.save(apiKey);
//			//System.out.println(a);
//		} catch (Exception e) {
//			logger.error(e);
//		 
//		}
		
		return apiKey;
		
	}
	/**
	 * 生成----绑定 ---授权
	 * 
	 * 备注：  如果初始化的时候 ，数据库里存在appkey ,那么只要将其状态变成可用，刷新缓存即可
	 * 
	 * 否则重新创建。。。。。。。。。
	 * @param merchantCode
	 * @param userName
	 * @return
	 */
	 
	public String initAppKey(String merchantCode,String userName){
		
		ApiKeyMetadata api = null;
		try {
			api = queryApiKeyByMetadataVal(merchantCode);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ApiKey  key = null;
		if(api != null){
			String id = api.getId();
			
			key = this.apiKeyDao.getById(id);
			
			if(key!= null){
				return key.getAppKey();
			}
		}
		
		//1 生成APP KEY 
		 key = saveGenerateKey(userName, merchantCode);
		//绑定
		
		try {
			bindingApiKeyCustomers(new String[]{merchantCode},new AppType[]{ AppType.MERCHANTS}, key.getId(), userName, key.getAppKey());
	//授权
		
			authorizeApi(Constants.APIIDS, key.getId(), null, key.getAppKey(),StringUtils.isEmpty(merchantCode)?userName:merchantCode);
		
			//刷新API授权缓存
			putApiToRedis(key.getAppKey());
			//刷新appkey验证缓存
			String hashKey = key.getAppKey();
			//加入redis缓存
			putAppkeyToRedis(hashKey, key.getAppSecret(), merchantCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return key.getAppKey();
	}
	@Resource(name = "stringRedisTemplate")
	private SetOperations<String, String> setOperations;
	@Resource(name = "stringRedisTemplate")
	private HashOperations<String, String, String> hashOperations;
	
	private void putApiToRedis(String appkey){
		for(String apiId : Constants.APIIDS){
			String value = StringUtils.join(new Object[]{apiId,appkey}, "#");
			setOperations.add(CacheConstant.API_APPKEY_SET_KEY,value);
		}
	}
	
	private void putAppkeyToRedis(String appkey, String appSecret,String merchantCode){
		removeAppkeyToRedis(appkey);
		//刷新appkey验证缓存
		String value = StringUtils.join(new Object[]{appSecret,merchantCode}, "#");
		hashOperations.put(CacheConstant.APPKEY_SECRET_KEY, appkey, value);
	}
	public void removeAppkeyToRedis(String appkey){
		//删除appkey验证缓存
		hashOperations.delete(CacheConstant.APPKEY_SECRET_KEY, appkey);
	}
	/**
	 * 刷新  缓存 
	 * @param merchantCode
	 */
	public void freshRedisCache(String merchantCode,String status){
		try {
			ApiKeyMetadata api = queryApiKeyByMetadataVal(merchantCode);
			String id = api.getId();
			ApiKey  key = this.apiKeyDao.getById(id);
			
			//将状态更改成启用/停用
			updateAppKeyStatus(key.getAppKey(), status);
			
			MerchantOperationLog operationLog=new MerchantOperationLog();
			operationLog.setId(UUIDUtil.getUUID());
			if ("1".equals(status)) {
				operationLog.setOperationNotes("启用appkey");
				operationLog.setOperationType(OperationType.APPKEY_ENABLED);
			} else {
				operationLog.setOperationNotes("禁用appkey");
				operationLog.setOperationType(OperationType.APPKEY_DISABLE);
			}
			operationLog.setMerchantCode(key.getAppKey());
			operationLog.setOperated(new Date());
			operationLog.setOperator("SYSTEM");
			merchantOperationLogService.saveMerchantOperationLog(operationLog);
			
			removeAppkeyToRedis(key.getAppKey());
			putAppkeyToRedis(key.getAppKey(), key.getAppSecret(), merchantCode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
 
	/**
	 * 删除APP KEY 缓存，将数据库置为不可用
	 */
	public  void deleteRedisCache(String merchantCode){
		try {
			ApiKeyMetadata api = queryApiKeyByMetadataVal(merchantCode);
			//将状态置为不可用
			if(api== null)
				return ;
			String id = api.getId();
			
			ApiKey  key = this.apiKeyDao.getById(id);
			
			updateAppKeyStatus(key.getAppKey(), "0");
			//hashOperations.delete(APPKEY_SECRET_KEY, appkey);
			//CritMap critMap=new CritMap();
			//critMap.addEqual("appKey", api.getApiKey().getAppKey());
			//ApiKey key = this.apiKeyDao.getObjectByCritMap(critMap, useCache)
			removeAppkeyToRedis(key.getAppKey());
			//putAppkeyToRedis(api.getApiKey().getAppKey(), api.getApiKey().getAppSecret(), merchantCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
//	final static String CACHE_QUEUE_KEY = "api:api.cache:queue";
//	final static String API_APPKEY_SET_KEY = "api:api.appkey:set";
//	final static String API_HASH_KEY = "api:api:hash";
//	final static String API_IS_SAVE_RESULT_HASH_KEY = "api:api.is.save.result:hash";
//	final static String APPKEY_SECRET_KEY = "api:appkey.secret:hash";
//	final static String API_APPKEY_HASH = "api.appkey.hash";
}
