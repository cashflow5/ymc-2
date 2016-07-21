package com.yougou.api.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.belle.infrastructure.util.DateUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.yougou.api.annotation.Documented;
import com.yougou.api.model.pojo.ApiLog;
import com.yougou.api.mongodb.GenericMongoDao;
import com.yougou.api.util.RedisKeyUtils;

@Service
public class ApiLogReceive {
	
	private static final Logger LOGGER = Logger.getLogger(ApiLogReceive.class);
	
	
	@Resource
	private GenericMongoDao genericMongoDao;
	
	@Resource(name = "stringRedisTemplate")
	private ValueOperations<String, String> valueOperations;
	
	@Resource(name = "stringRedisTemplate")
	private SetOperations<String, String> setOperations;
	
	
	public void handleMessage(Map<String,Object> msg){
		try {
		
			String api = MapUtils.getString(msg, "api");
			String appkey = MapUtils.getString(msg, "appKey");
			Boolean isCallSucess =   MapUtils.getBoolean(msg,"isCallSucess");
			Long exTime =  MapUtils.getLong(msg,"exTime");
			String clientIp =  MapUtils.getString(msg, "clientIp");
			
			if(StringUtils.isNotBlank(api) && StringUtils.isNotBlank(appkey) ){
				
				BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
				builder.append("className", MapUtils.getString(msg, "className"));
				builder.append("clientIp", clientIp);
				builder.append("operationParameters", new BasicDBObject(MapUtils.getMap(msg, "operationParameters")));
				builder.append("operationResult", MapUtils.getString(msg, "operationResult"));
				Timestamp ts = new Timestamp(MapUtils.getLong(msg, "operated"));  
				Date operated = new Date();
				operated = ts;
				builder.append("operated",  operated);
				builder.append("isCallSucess",isCallSucess);
				builder.append("exTime",exTime);
				genericMongoDao.insert(ApiLog.class.getAnnotation(Documented.class).name(), builder.get());	
				
				//api appkey 总调用情况
				String apikeyCountKey = RedisKeyUtils.getApiKeyALlKey(api, appkey, RedisKeyUtils.CALL_COUNT);
				String apikeyFailCountKey = RedisKeyUtils.getApiKeyALlKey(api, appkey, RedisKeyUtils.FAIL_CALL_COUNT);
				String apikeyExTimeKey = RedisKeyUtils.getApiKeyALlKey(api, appkey, RedisKeyUtils.EX_TIME);

				//总调用情况
				String countALlKey = RedisKeyUtils.getCountALlKey(RedisKeyUtils.CALL_COUNT);
				String exTimeALLkey = RedisKeyUtils.getCountALlKey(RedisKeyUtils.EX_TIME);
				String failAllKey = RedisKeyUtils.getCountALlKey(RedisKeyUtils.FAIL_CALL_COUNT);							
				
				valueOperations.increment(countALlKey, 1);
				valueOperations.increment(exTimeALLkey, exTime);
				
				valueOperations.increment(apikeyCountKey, 1);
				valueOperations.increment(apikeyExTimeKey, exTime);
				
				if(!isCallSucess){
					valueOperations.increment(failAllKey, 1);
					//valueOperations.increment(failCallKey, 1);
					valueOperations.increment(apikeyFailCountKey, 1);
				}
				
				setOperations.add(RedisKeyUtils.COUNT_SET_KEY,StringUtils.join(new Object[]{api,appkey,clientIp},":"));
			}
			
			
			
	
		} catch (Exception e) {
			LOGGER.error("MQ 信息:" + msg+ " error :"+e.getMessage());
		}
		
	}

}
