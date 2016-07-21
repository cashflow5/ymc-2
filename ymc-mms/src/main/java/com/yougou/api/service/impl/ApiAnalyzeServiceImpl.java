package com.yougou.api.service.impl;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.belle.infrastructure.util.JDBCUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.yougou.api.model.vo.ApiAllCount;
import com.yougou.api.model.vo.ApiAnalyzeDetailVo;
import com.yougou.api.model.vo.ApiCount;
import com.yougou.api.model.vo.ApiMetadata;
import com.yougou.api.model.vo.AppKeyCount;
import com.yougou.api.model.vo.AppKeyMetadata;
import com.yougou.api.mongodb.GenericMongoDao;
import com.yougou.api.service.IApiAnalyzeService;
import com.yougou.api.util.RedisKeyUtils;
import com.yougou.dms.api.ApiDistributorService;
import com.yougou.merchant.api.common.Constant;
import com.yougou.merchant.api.monitor.service.IApiMonitorService;
import com.yougou.merchant.api.monitor.vo.ApiMonitorParameterVo;
import com.yougou.merchant.api.monitor.vo.MonitorAppKeyDetailVo;
import com.yougou.merchant.api.monitor.vo.MonitorAppKeyTemplate;
import com.yougou.merchant.api.monitor.vo.MonitorAppKeyVo;
import com.yougou.merchant.api.monitor.vo.MonitorConfig;
import com.yougou.merchant.api.monitor.vo.MonitorRateWarnDetail;
import com.yougou.merchant.api.monitor.vo.MonitorTemplateDetail;
import com.yougou.merchant.api.supplier.service.ISupplierService;
import com.yougou.merchant.api.supplier.vo.SupplierVo;


@Service
public class ApiAnalyzeServiceImpl implements IApiAnalyzeService {
	
	@Resource(name = "stringRedisTemplate")
	private ValueOperations<String, String> valueOperations;
	
	@Resource(name = "stringRedisTemplate")
	private HashOperations<String, String, String> hashOperations;
	
	@Resource(name = "stringRedisTemplate")
	private SetOperations<String, String> setOperations;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private ISupplierService supplierService;
	
	@Resource
	private ApiDistributorService apiDistributorService;
	
	@Resource
	private GenericMongoDao genericMongoDao;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	private Logger logger = Logger.getLogger(ApiAnalyzeServiceImpl.class);
	
    public static final String API_CONFIG_REDIS_KEY = "api_config_redis_key";
    public static final String APPKEY_CONFIG_REDIS_KEY = "appkey_config_redis_key";
    
    private static final String SQL_FINDCOUNTGROUPBYAPPKEY = "SELECT t.app_key appkey,SUM(t.call_count) callCount FROM tbl_merchant_api_analyze_detail t WHERE t.time_quantum >= ? AND t.time_quantum <= ? group by t.app_key";
    
    @Resource
    private IApiMonitorService apiMonitorService;

	public List<AppKeyCount> findAppKeyCountByApiId(String apiId) throws Exception {
		List<AppKeyCount> list = new ArrayList<AppKeyCount>();
		List<Map<String, Object>> mapList = this.getAppKeyCountByApiId(apiId);
		Set<String> set = setOperations.members(RedisKeyUtils.COUNT_SET_KEY);
		
		for(Map<String, Object> map:mapList){
			String appkey = MapUtils.getString(map, "app_key");
			String api = MapUtils.getString(map, "api_method");
			Long count = 0L;
			Long exTime = 0L;
			Long failCount = 0L;
			for(String str:set){
				String apiStr = str.split(":")[0];
				String appkeyStr = str.split(":")[1];
				if(StringUtils.equals(apiStr, api) && StringUtils.equals(appkeyStr, appkey)){
					String apikeyCountKey = RedisKeyUtils.getApiKeyALlKey(apiStr, appkeyStr, RedisKeyUtils.CALL_COUNT);
					String apikeyFailCountKey = RedisKeyUtils.getApiKeyALlKey(apiStr, appkeyStr, RedisKeyUtils.FAIL_CALL_COUNT);
					String apikeyExTimeKey = RedisKeyUtils.getApiKeyALlKey(apiStr, appkeyStr, RedisKeyUtils.EX_TIME);
					count = count + Long.valueOf(valueOperations.get(apikeyCountKey)==null?"0":valueOperations.get(apikeyCountKey));
					exTime = exTime + Long.valueOf(valueOperations.get(apikeyExTimeKey)==null?"0":valueOperations.get(apikeyExTimeKey));
					failCount = failCount + Long.valueOf(valueOperations.get(apikeyFailCountKey)==null?"0":valueOperations.get(apikeyFailCountKey));
				}
			}
			if(count!=0){
				AppKeyCount appkeyCount = new AppKeyCount();
				appkeyCount.setAppkey(appkey);
				appkeyCount.setAppkeyUser(this.getKeyUserByAppkey(appkey));
				appkeyCount.setCallCount(count);
				appkeyCount.setExTime(exTime);
				appkeyCount.setFailCount(failCount);
				list.add(appkeyCount);
			}
		}
		return list;
	}

	public List<ApiCount> findApiCountByAppKeyOwner(String appKeyOwner) {
		List<ApiCount> list = new ArrayList<ApiCount>();
		List<Map<String, Object>> mapList = this.getApiCountByApiId(appKeyOwner);
		Set<String> set = setOperations.members(RedisKeyUtils.COUNT_SET_KEY);
		for(Map<String, Object> map:mapList){
			String api = MapUtils.getString(map, "api_method");
			Long count = 0L;
			Long exTime = 0L;
			Long failCount = 0L;
			for(String str:set){
				String apiStr = str.split(":")[0];
				String appkeyStr = str.split(":")[1];
				if(StringUtils.equals(apiStr, api) && StringUtils.equals(appkeyStr, appKeyOwner)){
					String apikeyCountKey = RedisKeyUtils.getApiKeyALlKey(apiStr, appkeyStr, RedisKeyUtils.CALL_COUNT);
					String apikeyFailCountKey = RedisKeyUtils.getApiKeyALlKey(apiStr, appkeyStr, RedisKeyUtils.FAIL_CALL_COUNT);
					String apikeyExTimeKey = RedisKeyUtils.getApiKeyALlKey(apiStr, appkeyStr, RedisKeyUtils.EX_TIME);
					count = count + Long.valueOf(valueOperations.get(apikeyCountKey)==null?"0":valueOperations.get(apikeyCountKey));
					exTime = exTime + Long.valueOf(valueOperations.get(apikeyExTimeKey)==null?"0":valueOperations.get(apikeyExTimeKey));
					failCount = failCount + Long.valueOf(valueOperations.get(apikeyFailCountKey)==null?"0":valueOperations.get(apikeyFailCountKey));
				}
			}
			
			if(count!=0){
				ApiCount apiCount = new ApiCount();
				String apiName = MapUtils.getString(map, "api_name");
				String apiMethod = MapUtils.getString(map, "api_method");
				apiCount.setApiName(apiName+apiMethod);
				apiCount.setCallCount(count);
				apiCount.setFailCount(Long.valueOf(failCount));
				apiCount.setExTime(failCount);
				list.add(apiCount);
			}
		}
		return list;
	}
	
	public ApiAllCount findApiAllCount() {
		ApiAllCount apiAllCount = new ApiAllCount();
		String callAllKey = RedisKeyUtils.getCountALlKey(RedisKeyUtils.CALL_COUNT);
		Long callCount = Long.valueOf(valueOperations.get(callAllKey));
		apiAllCount.setCallCount(callCount);
		String failCallKey = RedisKeyUtils.getCountALlKey(RedisKeyUtils.FAIL_CALL_COUNT);
		Long failCount = Long.valueOf(valueOperations.get(failCallKey)==null?"0":valueOperations.get(failCallKey));
		apiAllCount.setFailCount(failCount);
		String exTimeKey = RedisKeyUtils.getCountALlKey(RedisKeyUtils.EX_TIME);
		Long exTime = Long.valueOf(valueOperations.get(exTimeKey));
		apiAllCount.setExTime(Long.valueOf(exTime));
		return apiAllCount;
	}

	public String findApiIdByName(String apiMethod) {
		String sql = "SELECT t.id FROM tbl_merchant_api t WHERE t.api_method = ? ";
		String id = (String)JDBCUtils.getInstance().uniqueResult(sql, new Object[]{apiMethod});
		return id;
	}

	public List<Map<String, Object>> getAppKeyCountByApiId(String apiId) {
		if (StringUtils.isBlank(apiId)) {
			return Collections.emptyList();
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT t3.app_key,t4.metadata_val,t4.metadata_key,t2.api_method  FROM tbl_merchant_api_license t1 ");
		sb.append(" INNER JOIN tbl_merchant_api t2 on t2.id = t1.api_id ");
		sb.append(" INNER JOIN tbl_merchant_api_key t3 on t3.id = t1.key_id ");
		sb.append(" INNER JOIN tbl_merchant_api_key_metadata t4 on t4.key_id = t3.id ");
		sb.append(" WHERE t2.id = ? ");
		
		Object[] values = { apiId };
		return JDBCUtils.getInstance().listResultMap(sb.toString(), values);
	}
	
	public List<Map<String, Object>> getApiCountByApiId(String appkey) {
		if (StringUtils.isBlank(appkey)) {
			return Collections.emptyList();
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT t2.api_name,t2.api_method FROM tbl_merchant_api_license t1  ");
		sb.append(" INNER JOIN tbl_merchant_api t2 on t2.id = t1.api_id ");
		sb.append(" INNER JOIN tbl_merchant_api_key t3 on t3.id = t1.key_id ");
		sb.append(" INNER JOIN tbl_merchant_api_key_metadata t4 on t4.key_id = t3.id ");
		sb.append(" WHERE t3.app_key = ? ");
		
		Object[] values = { appkey };
		return JDBCUtils.getInstance().listResultMap(sb.toString(), values);
	}

	public Map<String, Object> getApiCountSum(String apiId, String timeQuantum) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT SUM(t.call_count) AS call_count,SUM(t.fail_call_count) as fail_call_count, ");
		sb.append(" SUM(t.sucess_call_count) AS sucess_call_count,SUM(t.avg_ex_time) as avg_ex_time ");
		sb.append(" FROM tbl_merchant_api_analyze_detail t  ");
		sb.append(" WHERE t.api_id = ? ");
		sb.append(" AND t.time_quantum  like ? ");
		Object[] values = { apiId,timeQuantum+"%" };
		List<Map<String, Object>> list = JDBCUtils.getInstance().listResultMap(sb.toString(), values);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	public Map<String, Object> getAppKeyCountSum(String appKey, String timeQuantum) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT SUM(t.call_count) AS call_count,SUM(t.fail_call_count) as fail_call_count, ");
		sb.append(" SUM(t.sucess_call_count) AS sucess_call_count,SUM(t.avg_ex_time) as avg_ex_time ");
		sb.append(" FROM tbl_merchant_api_analyze_detail t  ");
		sb.append(" WHERE t.app_key = ? ");
		sb.append(" AND t.time_quantum  like  ? ");
		Object[] values = { appKey,timeQuantum+"%" };
		List<Map<String, Object>> list = JDBCUtils.getInstance().listResultMap(sb.toString(), values);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	public List<Map<String, Object>> getApiKeyMetadata() {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT t2.app_key,t1.metadata_key,t1.metadata_val ");
		sb.append(" FROM tbl_merchant_api_key_metadata t1 ");
		sb.append(" INNER JOIN tbl_merchant_api_key t2 ON t2.id = t1.key_id ");
		List<Map<String, Object>> list = JDBCUtils.getInstance().listResultMap(sb.toString());
		return list;
	}

	public void createAppKeyUserCache() throws Exception {
		String appKey = null;
		String metadataKey = null;
		String metadataVal = null;
		String appKeyUser = null;
		List<Map<String, Object>> list = this.getApiKeyMetadata();
		for(Map<String, Object> map:list){
			appKey = MapUtils.getString(map, "app_key");
			metadataKey = MapUtils.getString(map, "metadata_key");
			metadataVal = MapUtils.getString(map, "metadata_val");
			if(StringUtils.equals(metadataKey, "MERCHANTS")){
				try {
					SupplierVo vo = supplierService.getSupplierByMerchantCode(metadataVal);
					appKeyUser = vo.getSupplier();
				} catch (Exception e) {
					//logger.info("调用 mct api失败："+metadataVal);
					//System.out.println(metadataVal);
					e.printStackTrace();
				}
				
			}else{
				try {
					appKeyUser = apiDistributorService.queryAllDistributor().get(metadataVal);
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error("分销dubbo查询分销商接口出现异常！");
				}
			}
			if(StringUtils.isNotEmpty(appKeyUser)){
				hashOperations.put(RedisKeyUtils.API_APPKEY_HASH, appKey, appKeyUser+ "("+metadataVal+")");
			}
			
		}
		
	}

	public String getKeyUserByAppkey(String appKey) throws Exception  {
		if(StringUtils.isNotEmpty(appKey)){
			return hashOperations.get(RedisKeyUtils.API_APPKEY_HASH, appKey);
		}else{
			return "";
		}
	}

	public List<AppKeyMetadata> getAppkeyByUser(String appKeyUser) {
		Map<String,String> map = hashOperations.entries(RedisKeyUtils.API_APPKEY_HASH);
		List<AppKeyMetadata> list = new ArrayList<AppKeyMetadata>();
		Set<Entry<String, String>> set =  map.entrySet();
		for(Entry<String, String> entity:set){
			String key = entity.getKey();
			String value = entity.getValue();
			if(StringUtils.contains(value, appKeyUser)){
				list.add(new AppKeyMetadata(key,value));
			}
		}
		return list;
	}

	public List<ApiMetadata> getApiMetadataByApiName(String apiName) {
		List<ApiMetadata> list = new ArrayList<ApiMetadata>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT t.id,t.api_name FROM tbl_merchant_api t");
		sb.append(" WHERE t.api_name LIKE ? ");
		Object[] values = { "%"+apiName+"%" };
		List<Map<String, Object>> mapList = JDBCUtils.getInstance().listResultMap(sb.toString(),values);
		if(CollectionUtils.isNotEmpty(mapList)){
			for(Map<String, Object> map:mapList){
				String id = MapUtils.getString(map, "id");
				String name = MapUtils.getString(map, "api_name");
				list.add(new ApiMetadata(id, name));
			}
		}
		return list;
	}

	@Override
	public List<ApiAnalyzeDetailVo> getApiAnalyzeDetailVoList(Date startTime, Date endTime) {
	
		BasicDBObject key = new BasicDBObject();
		BasicDBObject cond = new BasicDBObject();
		BasicDBObject conditionGroup = new BasicDBObject();
		BasicDBObject initial = new BasicDBObject();
		StringBuffer reduce = new StringBuffer();
		key.put("operationParameters.app_key", true);
		key.put("operationParameters.method", true);
		if (startTime != null) {
			conditionGroup.put("$gte", startTime);
		}
		if (endTime != null) {
			conditionGroup.put("$lte", endTime);
		}
		cond.put("operated", conditionGroup);
		initial.put("total", 0);
		initial.put("totalExTime", 0);
		initial.put("faleTotal", 0);
		
		reduce.append("function(curr,result){");
		reduce.append("result.totalExTime += curr.exTime;");
		reduce.append("result.total++;");
		reduce.append("if(curr.isCallSucess == false){");
		reduce.append("result.faleTotal++}}");
		
		DBObject dbObject = genericMongoDao.getDBObjectByGroup("tbl_merchant_api_log", key, cond, initial, reduce.toString());
		Gson gson = new Gson();
		Type type = new TypeToken<List<ApiAnalyzeDetailVo>>(){}.getType();
		//Gson 不支持xx.xx的格式，所以采用字符串替换的方法取别名
		String jsonStr = dbObject.toString().replace("operationParameters.method", "api").replace("operationParameters.app_key", "appKey");
		List<ApiAnalyzeDetailVo> list = gson.fromJson(jsonStr,type);
		return list;
		
	}

	@Override
	public String getMerchantCodeByappKey(String appKey) {
		String sql = "select m.metadata_val as merchant_code from tbl_merchant_api_key_metadata m, tbl_merchant_api_key k where m.key_id = k.id and m.metadata_key = 'MERCHANTS' AND k.app_key = ? ";
		Object[] values = { appKey };
		return (String) JDBCUtils.getInstance().uniqueResult(sql, values);
	}
	
	@Override
	public Integer getAppkeyCallCount(Date startDate, Date endDate, String appKey) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT SUM(t.call_count) ");
		sb.append(" FROM tbl_merchant_api_analyze_detail t ");
		sb.append(" WHERE t.time_quantum >= '"+ df.format(startDate) +"' AND t.time_quantum < '"+df.format(endDate)+"'");
		sb.append(" AND t.app_key = '"+appKey+"'");
		Object result =JDBCUtils.getInstance().uniqueResult(sb.toString());
		if(result == null){
			return 0;
		}else{
			Integer count = Integer.valueOf(result.toString());
			return count;
		}
		
	}

	@Override
	public Date getLastCallDate(Date startDate, Date endDate, String appKey) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT t.create_time FROM tbl_merchant_api_analyze_detail  t ");
		sb.append(" WHERE t.time_quantum >= '"+ df.format(startDate) +"' AND t.time_quantum < '"+df.format(endDate)+"'");
		sb.append(" AND t.app_key = '"+appKey+"'");
		sb.append(" ORDER BY t.create_time DESC LIMIT 1 ");
		Object object = JDBCUtils.getInstance().uniqueResult(sb.toString());
		if(object == null){
			return new Date();
		}else{
			Date date = (Date)JDBCUtils.getInstance().uniqueResult(sb.toString());
			return date; 
		}
		
	}

	@Override
	public String getApiMethodById(String apiId) {
		StringBuffer sql = new StringBuffer("select t.api_method from tbl_merchant_api t where t.id = '");
		sql.append(apiId);
		sql.append("'");
		Object object = JDBCUtils.getInstance().uniqueResult(sql.toString());
		return (String) (object != null ? object : null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MonitorRateWarnDetail> getMonitorRateWarnByAppKey(String appKey) {
		Map<String, List<MonitorRateWarnDetail>> warnMaps = (Map<String, List<MonitorRateWarnDetail>>) this.redisTemplate.opsForValue().get(RedisKeyUtils.API_FREQUENCY_EARLY_WARN);
		if (MapUtils.isNotEmpty(warnMaps)) {
			return warnMaps.get(appKey);
		}
		
		return null;
	}
	
    /**
     * 按照appkey查询该appkey全局的配置参数和每个接口的配置明细
     * 
     * @param MonitorAppKeyVo
     * @return
     */
    public MonitorAppKeyVo queryMonitorAppKeyVo(String appkey) throws Exception {
        // 根据AppKey获得AppKeyId
        String appkeyId = apiMonitorService.queryAppKeyIdByAppKey(appkey);
        MonitorAppKeyVo vo = new MonitorAppKeyVo();
        Map<String, MonitorAppKeyDetailVo> detailmap = new HashMap<String, MonitorAppKeyDetailVo>();
        Map<String, Map<String, String>> map = (Map<String, Map<String, String>>) redisTemplate.opsForValue().get(APPKEY_CONFIG_REDIS_KEY);
        String apiId = "";
        String detailKey = "";
        String[] detail;
        // 如果没有该appkey对应的模板，那么取默认的模板
        if (!map.containsKey(appkeyId)) {
            appkeyId = "default";
        }
        for (String key : map.get(appkeyId).keySet()) {
            if ("dataFlow".equals(key)) {
                vo.setDataFlow(Integer.valueOf(map.get(appkeyId).get(key).toString()));
            } else if ("dataFlowEarlyWarn".equals(key)) {
                vo.setDataFlowEarlyWarn(Integer.valueOf(map.get(appkeyId).get(key).toString()));
            } else if ("successRate".equals(key)) {
                vo.setSuccessRate(Integer.valueOf(map.get(appkeyId).get(key).toString()));
            } else if ("invalidAppKeyRequest".equals(key)) {
                vo.setInvalidAppKeyRequest(Integer.valueOf(map.get(appkeyId).get(key).toString()));
            } else {
                detail = key.split("_");
                if (detail.length > 0) {
                    apiId = detail[0];
                    detailKey = detail[1];
                    if (!detailmap.containsKey(apiId)) {
                        detailmap.put(apiId, new MonitorAppKeyDetailVo());
                    } else if ("frequency".equals(detailKey)) {
                        detailmap.get(apiId).setFrequency(Integer.valueOf(map.get(appkeyId).get(key).toString()));
                    } else if ("frequencyUnit".equals(detailKey)) {
                        detailmap.get(apiId).setFrequencyUnit(Integer.valueOf(map.get(appkeyId).get(key).toString()));
                    } else if ("callNum".equals(detailKey)) {
                        detailmap.get(apiId).setCallNum(Integer.valueOf(map.get(appkeyId).get(key).toString()));
                    } else if ("dataFlowLimit".equals(detailKey)) {
                        detailmap.get(apiId).setDataFlowLimit(Integer.valueOf(map.get(appkeyId).get(key).toString()));
                    } else if ("frequencyLimit".equals(detailKey)) {
                        detailmap.get(apiId).setFrequencyLimit(Integer.valueOf(map.get(appkeyId).get(key).toString()));
                    } else if ("lockTime".equals(detailKey)) {
                        detailmap.get(apiId).setLockTime(Float.valueOf(map.get(appkeyId).get(key).toString()));
                    } else if ("dataFlowEarlyWarn".equals(detailKey)) {
                        detailmap.get(apiId).setDataFlowEarlyWarn(Integer.valueOf(map.get(appkeyId).get(key).toString()));
                    } else if ("frequencyEarlyWarn".equals(detailKey)) {
                        detailmap.get(apiId).setFrequencyEarlyWarn(Integer.valueOf(map.get(appkeyId).get(key).toString()));
                    }
                }
            }
        }
        vo.setMonitorAppKeyDetailVo(detailmap);
        return vo;
    }

    /**
     * 获取全局的API监控参数设置
     * 
     * @param ApiMonitorParameterVo
     * @return
     */
    public ApiMonitorParameterVo getApiMonitorConfig() {
        ApiMonitorParameterVo vo = new ApiMonitorParameterVo();
        Map<String, String> map = (Map<String, String>) redisTemplate.opsForValue().get(API_CONFIG_REDIS_KEY);
        if (null != map) {
            vo.setDataFlowRate(map.get(Constant.DATA_FLOW_RATE));
            vo.setFrequencyRate(map.get(Constant.FREQUENCY_RATE));
            vo.setFrequencyOutLockTime(map.get(Constant.FREQUENCY_OUTLOCK_TIME));
            vo.setSimpleImplOneDayRate(map.get(Constant.SIMPLEIMPL_ONEDAY_RATE));
            vo.setSimpleImplFrequencyRate(map.get(Constant.SIMPLEIMPL_FREQUENCY_RATE));
            vo.setSuccessRate(map.get(Constant.SUCCESS_RATE));
            vo.setAppKeyCallFrequencyRate(map.get(Constant.APPKEY_CALLFREQUENCY_RATE));
            vo.setInvalidAppKeyRequest(map.get(Constant.INVALID_APPKEY_REQUEST));
        } else {
            List<MonitorConfig> configList = null;
            try {
                configList = apiMonitorService.queryMonitorConfigList();
            } catch (Exception e) {
                logger.error("获取API监控参数时发生异常！");
            }
            if (null != configList && configList.size() > 0) {
                for (MonitorConfig config : configList) {
                    if (config.getConfigKey().equals(Constant.KEY_DATA_FLOW_RATE)) {
                        vo.setDataFlowRate(config.getConfigValue());
                    } else if (config.getConfigKey().equals(Constant.KEY_FREQUENCY_RATE)) {
                        vo.setFrequencyRate(config.getConfigValue());
                    } else if (config.getConfigKey().equals(Constant.KEY_FREQUENCY_OUTLOCK_TIME)) {
                        vo.setFrequencyOutLockTime(config.getConfigValue());
                    } else if (config.getConfigKey().equals(Constant.KEY_SIMPLEIMPL_ONEDAY_RATE)) {
                        vo.setSimpleImplOneDayRate(config.getConfigValue());
                    } else if (config.getConfigKey().equals(Constant.KEY_SIMPLEIMPL_FREQUENCY_RATE)) {
                        vo.setSimpleImplFrequencyRate(config.getConfigValue());
                    } else if (config.getConfigKey().equals(Constant.KEY_SUCCESS_RATE)) {
                        vo.setSuccessRate(config.getConfigValue());
                    } else if (config.getConfigKey().equals(Constant.KEY_APPKEY_CALLFREQUENCY_RATE)) {
                        vo.setAppKeyCallFrequencyRate(config.getConfigValue());
                    } else if (config.getConfigKey().equals(Constant.KEY_INVALID_APPKEY_REQUEST)) {
                        vo.setInvalidAppKeyRequest(config.getConfigValue());
                    }
                }
            } else {
                // 单接口日调用超限次数
                vo.setDataFlowRate("120");
                // 单接口超限频率
                vo.setFrequencyRate("120");
                // 锁定接口小时数
                vo.setFrequencyOutLockTime("0");
                // 单接口日调用次数
                vo.setSimpleImplOneDayRate("120");
                // 单接口频率预警
                vo.setSimpleImplFrequencyRate("120");
                // 调用成功率预警
                vo.setSuccessRate("80");
                // AppKey日调用次数预警
                vo.setAppKeyCallFrequencyRate("120");
                // 无效AppKey发送次数，则该IP被封
                vo.setInvalidAppKeyRequest("1000");
            }
        }
        return vo;
    }

    /**
     * 更新redis缓存里的每个appkey对应的模板配置
     * 
     * @return list
     */
    public List<String> updateRedisCasheForMonitorTemplate() {
        ApiMonitorParameterVo monitor = getApiMonitorConfig();
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
        List<String> list = new ArrayList<String>();
        for (MonitorAppKeyTemplate temp : apiMonitorService.queryAppKeyTemplate()) {
            list.add(temp.getAppkeyId());
            map.put(temp.getAppkeyId(), getApiMonitorMap(temp.getTemplateNo(), monitor));
        }
        // 根据默认模板设定默认的参数配置
        String default_template_no = apiMonitorService.queryDefaultTemplate().get(0);
        list.add("default");
        map.put("default", getApiMonitorMap(default_template_no, monitor));
        redisTemplate.opsForValue().set(APPKEY_CONFIG_REDIS_KEY, map);
        return list;
    }


    private Map<String, String> getApiMonitorMap(String template_no, ApiMonitorParameterVo monitor) {
        Map<String, String> appkey_map = new HashMap<String, String>();
        appkey_map.put("successRate", monitor.getSuccessRate());
        appkey_map.put("invalidAppKeyRequest", monitor.getInvalidAppKeyRequest());
        int count = 0;
        for (MonitorTemplateDetail detail : apiMonitorService.queryTemplateDetailList(template_no)) {
            if (detail.getIsCallNum() == 1) {
                count = count + detail.getCallNum();
            }
            appkey_map.put(detail.getApiId() + "_frequency", (detail.getIsFrequency() == 1 ? String.valueOf(detail.getFrequency()) : "-1"));
            appkey_map.put(detail.getApiId() + "_frequencyUnit", String.valueOf(detail.getFrequencyUnit()));
            appkey_map.put(detail.getApiId() + "_callNum", (detail.getIsCallNum() == 1 ? String.valueOf(detail.getCallNum()) : "-1"));
            appkey_map.put(detail.getApiId() + "_dataFlowLimit",
                    (detail.getIsCallNum() == 1 ? String.valueOf(detail.getIsCallNum() * Integer.valueOf(monitor.getDataFlowRate().trim()) + detail.getCallNum()) : "-1"));
            appkey_map.put(detail.getApiId() + "_frequencyLimit",
                    (detail.getIsFrequency() == 1 ? String.valueOf(detail.getFrequency() * Integer.valueOf(monitor.getFrequencyRate().trim()) + detail.getFrequency()) : "-1"));
            appkey_map.put(detail.getApiId() + "_lockTime", monitor.getFrequencyOutLockTime());
            appkey_map.put(detail.getApiId() + "_dataFlowEarlyWarn",
                    (detail.getIsCallNum() == 1 ? String.valueOf(detail.getCallNum() * Integer.valueOf(monitor.getSimpleImplOneDayRate().trim())) : "-1"));
            appkey_map.put(detail.getApiId() + "_frequencyEarlyWarn",
                    (detail.getIsFrequency() == 1 ? String.valueOf(detail.getFrequency() * Integer.valueOf(monitor.getSimpleImplFrequencyRate().trim())) : "-1"));
        }
        appkey_map.put("dataFlow", String.valueOf(count));
        appkey_map.put("dataFlowEarlyWarn", String.valueOf(count * Integer.valueOf(monitor.getAppKeyCallFrequencyRate().trim())));
        return appkey_map;
    }
    
	@Override
	public List<Map<String, Object>> findAppKeyByDate(Date startDate, Date endDate) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT t.app_key FROM tbl_merchant_api_analyze_ip t  ");
		sb.append(" WHERE t.time_quantum >= ? AND t.time_quantum < ? ");
		
		Object[] values = { df.format(startDate),df.format(endDate) };
		return JDBCUtils.getInstance().listResultMap(sb.toString(),values);
	}
	
	@Override
	public Integer findCallCountGroupByKey(Date startDate, Date endDate,String appKey) {
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT SUM(t.call_count) as call_count from tbl_merchant_api_analyze_detail t  ");
		sb.append(" WHERE t.create_time >= ? AND t.create_time < ? ");
		sb.append(" AND t.app_key = ? ");
		
		Object[] values = { startDate,endDate,appKey };
		Object object =  JDBCUtils.getInstance().uniqueResult(sb.toString(), values);
		if(object == null){
			return 0;
		}else{
			return  Integer.valueOf(object.toString());
		}
	}
	
	@Override
	public List<Map<String, Object>> findCallCountGroupByApi(Date startDate, Date endDate,String appKey) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT t.api_id,SUM(t.call_count) as call_count,SUM(t.sucess_call_count) as sucess_call_count");
		sb.append(" from tbl_merchant_api_analyze_detail t ");
		sb.append(" WHERE t.create_time >= ? AND t.create_time < ? ");
		sb.append(" AND t.app_key = ? ");
		sb.append(" GROUP BY t.api_id ");
		
		Object[] values = { startDate,endDate,appKey };
		return JDBCUtils.getInstance().listResultMap(sb.toString(),values);
	}

	@Override
	public List<Map<String, Object>> findCallCountGroupByApiAppKey(Date startDate, Date endDate) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT t.api_id,t.app_key,t.call_count ");
		sb.append(" FROM tbl_merchant_api_analyze_detail t  ");
		sb.append(" WHERE t.create_time >= ? AND t.create_time < ? ");
		sb.append(" GROUP BY t.api_id,t.app_key ");
		
		Object[] values = { startDate,endDate };
		return JDBCUtils.getInstance().listResultMap(sb.toString(),values);
	}

	@Override
	public Integer findCallCountByDateAndAppKey(Date startDate, Date endDate, String appKey) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT SUM(t.call_count)  ");
		sql.append(" FROM tbl_merchant_api_analyze_detail t  ");
		sql.append(" WHERE t.time_quantum >= ? AND t.time_quantum <= ? ");
		sql.append(" AND t.app_key = ? ");
		final List<Integer> list  = jdbcTemplate.query(sql.toString(), new Object[]{startDate,endDate,appKey}, new RowMapper<Integer>(){
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(1);
			}
			
		});
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return 0;
	}
	
	@Override
	public List<Map<String,Object>> findCountGroupByAppkey(Date startDate, Date endDate) {
		final List<Map<String,Object>> list  = jdbcTemplate.queryForList(SQL_FINDCOUNTGROUPBYAPPKEY, new Object[]{startDate,endDate});
		if(CollectionUtils.isNotEmpty(list)){
			return list;
		}
		return null;
	}

	@Override
	public List<String> findAppKeyForCountByDate(Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT t.app_key  ");
		sql.append(" FROM tbl_merchant_api_analyze_detail t  ");
		sql.append(" WHERE t.time_quantum >= ? AND t.time_quantum <= ? ");
		final List<String> list  = jdbcTemplate.query(sql.toString(), new Object[]{startDate,endDate}, new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
			
		});
		return list;
	}
	
	
	
	
	
}
