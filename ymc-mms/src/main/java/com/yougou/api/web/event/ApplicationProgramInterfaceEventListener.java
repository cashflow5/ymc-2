package com.yougou.api.web.event;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.yougou.api.Notifier;
import com.yougou.api.annotation.Documented;
import com.yougou.api.cfg.ConfigurationManager;
import com.yougou.api.model.pojo.ApiLog;
import com.yougou.api.mongodb.GenericMongoDao;
import com.yougou.api.util.RedisKeyUtils;

/**
 * API 事件监听器
 * 
 * @author yang.mq
 *
 */
@Component
public class ApplicationProgramInterfaceEventListener implements ApplicationListener<ApplicationProgramInterfaceEvent> {

	private static final Logger LOGGER = Logger.getLogger(ApplicationProgramInterfaceEventListener.class);
	
	@Resource
	private GenericMongoDao genericMongoDao;

	@Resource
	private Notifier notifier;
	
	@Resource(name = "stringRedisTemplate")
	private ValueOperations<String, String> valueOperations;
	
	@Resource(name = "stringRedisTemplate")
	private HashOperations<String, String, String> hashOperations;
	
	@Resource(name = "stringRedisTemplate")
	private ZSetOperations<String, String> zSetOperations;
	
	@Resource(name = "stringRedisTemplate")
	private SetOperations<String, String> setOperations;
	
	@Resource
	private ConfigurationManager configurationManager;
	
	@Override
	public void onApplicationEvent(final ApplicationProgramInterfaceEvent event) {
		try {
			// 异步处理事件
			new Thread(new Runnable() {
				@Override
				public void run() {
					if (event instanceof ApplicationProgramInterfaceThrowableEvent) {
						notifier.notifyAllReceiver(event.getSource());
					} else if (event instanceof ApplicationProgramInterfaceLogEvent) {
						DBObject dbOject = (DBObject) event.getSource();
						BasicDBObject base = (BasicDBObject)dbOject.get("operationParameters");
						String api = base.getString("method");
						String appkey = base.getString("app_key");
						Boolean isCallSucess =  (Boolean)dbOject.get("isCallSucess");
						Long exTime = (Long)dbOject.get("exTime");
						String clientIp = (String)dbOject.get("clientIp");
						genericMongoDao.insert(ApiLog.class.getAnnotation(Documented.class).name(), dbOject);						
						
						if(configurationManager.checkUseRedis()){
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
						
					}
				}
			}).start();
		} catch (Exception ex) {
			LOGGER.error("Publish application program interface event error：" + event.getEventDescription());
		}
	}
}
