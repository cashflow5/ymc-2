package com.belle.yitiansystem.merchant.web.controller;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.yougou.api.annotation.Documented;
import com.yougou.api.cfg.ConfigurationManager;
import com.yougou.api.dao.IApiKeyDao;
import com.yougou.api.model.pojo.ApiKey;
import com.yougou.api.model.pojo.ApiLog;
import com.yougou.api.model.vo.ApiAnalyzeDetailVo;
import com.yougou.api.model.vo.ApiCount;
import com.yougou.api.model.vo.AppKeyCount;
import com.yougou.api.model.vo.AppKeySecretVo;
import com.yougou.api.model.vo.AppkeyFlowWarnEmails;
import com.yougou.api.mongodb.GenericMongoDao;
import com.yougou.api.service.IApiAnalyzeService;
import com.yougou.api.service.IApiKeyService;
import com.yougou.api.service.IApiService;
import com.yougou.api.util.RedisKeyUtils;
import com.yougou.component.email.api.IEmailApi;
import com.yougou.component.email.model.MailSenderInfo;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.merchant.api.common.UUIDGenerator;
import com.yougou.merchant.api.monitor.service.IApiMonitorService;
import com.yougou.merchant.api.monitor.service.IApiReportService;
import com.yougou.merchant.api.monitor.vo.AnalyzeAppkey;
import com.yougou.merchant.api.monitor.vo.AnalyzeAppkeyDay;
import com.yougou.merchant.api.monitor.vo.AnalyzeDetail;
import com.yougou.merchant.api.monitor.vo.AnalyzeInterface;
import com.yougou.merchant.api.monitor.vo.AnalyzeIp;
import com.yougou.merchant.api.monitor.vo.MonitorAppKeyDetailVo;
import com.yougou.merchant.api.monitor.vo.MonitorAppKeyVo;
import com.yougou.merchant.api.monitor.vo.MonitorAppkeyWarnDetail;
import com.yougou.merchant.api.monitor.vo.MonitorDayWarnDetail;
import com.yougou.merchant.api.monitor.vo.MonitorEarlyWarnQueryVo;
import com.yougou.merchant.api.monitor.vo.MonitorEarlyWarning;
import com.yougou.merchant.api.monitor.vo.MonitorInvalidIp;
import com.yougou.merchant.api.monitor.vo.MonitorLock;
import com.yougou.merchant.api.monitor.vo.MonitorRateWarnDetail;
import com.yougou.merchant.api.monitor.vo.MonitorSuccRateWarnDetail;
import com.yougou.ordercenter.api.order.IOrderForMerchantApi;
import com.yougou.ordercenter.vo.merchant.MerchantDailyStatisticsVo;
import com.yougou.tools.common.utils.UUIDUtil;
import com.yougou.yop.api.DefaultYougouClient;
import com.yougou.yop.api.YougouClient;
import com.yougou.yop.api.request.ChainOrderSourceRequest;
import com.yougou.yop.api.request.ChainWarehouseQueryallRequest;
import com.yougou.yop.api.response.ChainOrderSourceResponse;
import com.yougou.yop.api.response.ChainWarehouseQueryallResponse;

/**
 * API JOB controller
 * @author he.wc
 *
 */
@Controller
@RequestMapping("/apiJob")
public class ApiJobController {
	
	private Logger logger = LoggerFactory.getLogger(ApiJobController.class);
	
	private static final SimpleDateFormat DF_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final SimpleDateFormat DF_DATE = new SimpleDateFormat("yyyy-MM-dd");
	
    public static final String API_APPKEY_FLOW_MAX_NUM = "api:appkey.flow:max";
    
    public static final String API_APPKEY_FLOW_WARN_NUM = "api:appkey.flow:warn";
    
    public static final String API_APPKEY_FLOW_IS_SEND_EMAIL = "api:appkey.flow:is.send.email";
    
    public static final String API_APPKEY_FLOW_IS_SEND_CLOSE_EMAIL = "api:appkey.flow:is.send.close.email";
    
    final static String APPKEY_SECRET_KEY = "api:appkey.secret:hash";
    
    final static String API_APPKEY_HASH = "api.appkey.hash";
	
	@Resource
	private IApiMonitorService apiMonitorService;
	
	@Resource
	private IApiReportService apiReportService;
	
	@Resource(name = "stringRedisTemplate")
	private ValueOperations<String, String> valueOperations;
	
	@Resource(name = "stringRedisTemplate")
	private SetOperations<String, String> setOperations;
	
	@Resource(name = "stringRedisTemplate")
	private StringRedisTemplate stringRedisTemplate;
	
	@Resource(name = "stringRedisTemplate")
	private HashOperations<String, String, String> hashOperations;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private IApiAnalyzeService apiAnalyzeService;
	
	@Resource
	private IApiKeyDao apiKeyDao;
	
	@Resource
	private GenericMongoDao genericMongoDao;
	
	@Resource
	private ConfigurationManager configurationManager;
	
	@Resource
	private IOrderForMerchantApi orderForMerchantApi;
	
	
	private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");

	private List<MonitorRateWarnDetail> list;
	
	@Resource
	private IEmailApi emailApi;
	
	@Resource
	private AppkeyFlowWarnEmails appkeyFlowWarnEmails;
	
	
	@Resource
	private IApiKeyService apiKeyService;
	
	@Resource
	private IApiService apiService;
	
	// 数据绑定
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateTimeFormat, true));
	}
	
	
	/**
	 * 抽取appkey和api接口来进行统计
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/createApiAnalyzeDetail")
	public String createApiAnalyzeDetail() throws Exception{
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.SECOND, 0);
		Calendar startCal = Calendar.getInstance();
		startCal.add(Calendar.MINUTE, -5);
		List<ApiAnalyzeDetailVo> list = apiAnalyzeService.getApiAnalyzeDetailVoList(startCal.getTime(), new Date());
		List<AnalyzeDetail> details = new ArrayList<AnalyzeDetail>();
		if(CollectionUtils.isNotEmpty(list)){
			for(ApiAnalyzeDetailVo vo:list){
				AnalyzeDetail detail = new AnalyzeDetail();
				String apiId = apiAnalyzeService.findApiIdByName(vo.getApi());
				String appKey = vo.getAppKey();
				if(!StringUtils.isEmpty(appKey) && !StringUtils.isEmpty(apiId)){
					detail.setId(UUIDUtil.getUUID());
					detail.setApiId(apiId);
					detail.setAppKey(appKey);
					detail.setCallCount(vo.getTotal());
					detail.setFailCallCount(vo.getFaleTotal());
					detail.setAvgExTime(Double.valueOf(vo.getTotalExTime()));
					detail.setSucessCallCount(vo.getTotal()-vo.getFaleTotal());
					String timeQuantum = DF_DATETIME.format(cal.getTime());
					detail.setTimeQuantum(timeQuantum);
					detail.setCreateTime(new Date());
					details.add(detail);
				}
			}
			apiReportService.insertAnalyzeDetailBatch(details);
			
		}
	
		
		return "true";
	}
	
	/**
	 * 锁定IP JOB
	 * http://localhost:8080/mms/apiJob/createApiLock.sc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/createApiLock")
	public String createApiLock() throws Exception{
	
		Calendar startCal = Calendar.getInstance();
		startCal.add(Calendar.DAY_OF_MONTH, -1);
		startCal.set(Calendar.HOUR, 0);
		startCal.set(Calendar.MINUTE, 0);
		startCal.set(Calendar.SECOND, 0);
		
		Calendar endCal = Calendar.getInstance();
		endCal.set(Calendar.HOUR, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.SECOND, 0);
		
		Date startDate = startCal.getTime();
		Date endDate = endCal.getTime();
		
		
		List<Map<String, Object>> apiList =  apiAnalyzeService.findCallCountGroupByApiAppKey(startDate, endDate);
		for(Map<String, Object> map:apiList){
			String appKey = MapUtils.getString(map, "app_key");
			String apiId = MapUtils.getString(map, "api_id");
			Integer callCount = MapUtils.getInteger(map, "call_count");
			//按照appkey查询该appkey全局的配置参数和每个接口的配置明细
			MonitorAppKeyVo monitorAppKeyVo  = apiAnalyzeService.queryMonitorAppKeyVo(appKey);
			Map<String, MonitorAppKeyDetailVo> monitorMap = monitorAppKeyVo.getMonitorAppKeyDetailVo();
			MonitorAppKeyDetailVo monitorAppKeyDetail = monitorMap.get(apiId);
			if(monitorAppKeyDetail!=null){
				Integer callCountMax = monitorAppKeyDetail.getCallNum();
				logger.info("锁定IP JOB,最大访问数："+callCountMax +",api 访问数："+callCount+",appkey:"+appKey);
				if(callCountMax.intValue() >0 && callCountMax.intValue() <= callCount.intValue()){
					MonitorLock lock = new MonitorLock();
					lock.setId(UUIDUtil.getUUID());
					lock.setAppKey(appKey);
					lock.setApiId(apiId);
					lock.setLockType(1);
					lock.setLockTime(new Date());
					lock.setLockHour(24);
					lock.setLockStatus(1);
					Calendar unlockCal = Calendar.getInstance();
					startCal.add(Calendar.HOUR_OF_DAY, 24);
					lock.setUpdateTime(new Date());
					lock.setUnlockTime(unlockCal.getTime());
					lock.setUpdateUser("system");
					apiMonitorService.insertMonitorLock(lock);
				}
			}else{
				logger.info("锁定IP JOB, api配置redis为空,apiId:"+apiId);
			}
			
		}
		
		
		
		return "true";
	}
	
	
	/**
	 * 分销orderSourceApiJob，每秒调用一次，用于测试
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/orderSourceApiJob")
	public String orderSourceApiJob() throws Exception{
		String serverUrl = "http://183.62.162.119/bms/api.sc";
		String appKey = "308c6608_13de3f418e5__7fef";
		String appSecret = "4380fbf1fba79ae0e75f57b1638a2111";
		YougouClient client = new DefaultYougouClient(serverUrl, appKey, appSecret);
		ChainOrderSourceRequest request = new ChainOrderSourceRequest();
		ChainOrderSourceResponse response =  client.execute(request);
		return new Gson().toJson(response);
	}
	
	@ResponseBody
	@RequestMapping("/warehouseApiJobq")
	public String orderSourceApiJobq() throws Exception{
		String serverUrl = "http://183.62.162.119/bms/api.sc";
		String appKey = "403727c3_13bd534c154__7fee";
		String appSecret = "03z730CWp943158x2nt6";
		YougouClient client = new DefaultYougouClient(serverUrl, appKey, appSecret);
		ChainWarehouseQueryallRequest request = new ChainWarehouseQueryallRequest();
	
		ChainWarehouseQueryallResponse response =   client.execute(request);
		return new Gson().toJson(response);
	}
	
	/**
	 * API预警数据生成JOB
	 * http://localhost:8080/mms/apiJob/createMonitorAppkeyWarning.sc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/createMonitorAppkeyWarning")
	public String createMonitorAppkeyWarning() throws Exception{
		Calendar startCal = Calendar.getInstance();
		startCal.add(Calendar.DAY_OF_MONTH, -1);
		startCal.set(Calendar.HOUR, 0);
		startCal.set(Calendar.MINUTE, 0);
		startCal.set(Calendar.SECOND, 0);
		
		Calendar endCal = Calendar.getInstance();
		endCal.set(Calendar.HOUR, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.SECOND, 0);
		
		Date startDate = startCal.getTime();
		Date endDate = endCal.getTime();
		
		List<Map<String, Object>> appKeylist = apiAnalyzeService.findAppKeyByDate(startDate, endDate);
		for(Map<String, Object> appKeyMap:appKeylist){
		
			List<MonitorEarlyWarning> monitorList = new ArrayList<MonitorEarlyWarning>();
			String appKey = MapUtils.getString(appKeyMap, "app_key");
			//按照appkey查询该appkey全局的配置参数和每个接口的配置明细
			MonitorAppKeyVo monitorAppKeyVo  = apiAnalyzeService.queryMonitorAppKeyVo(appKey);
			if(monitorAppKeyVo==null){
				logger.info("API预警数据 JOB,redis 为空");
				continue;
			}
			
			Integer appKeyCallCout =  apiAnalyzeService.findCallCountGroupByKey(startDate, endDate,appKey);
			List<Map<String, Object>> apiCallList = apiAnalyzeService.findCallCountGroupByApi(startDate, endDate, appKey);
			List<MonitorRateWarnDetail> rateList = apiAnalyzeService.getMonitorRateWarnByAppKey(appKey);
			
			//每项标准都为0
			if(appKeyCallCout == 0 && (apiCallList == null || apiCallList.size() <= 0) &&  (rateList ==null || rateList.size() <= 0) ){
				continue;
			}
			
			MonitorEarlyWarning monitor = new MonitorEarlyWarning();
			monitor.setId(UUIDUtil.getUUID());
			monitor.setAppKey(appKey);
			monitor.setTimeQuantum(DF_DATE.format(startDate));
			
			//APPKEY日调用次数预警明细
			Integer warmAppkeyCallCount =  monitorAppKeyVo.getDataFlowEarlyWarn();
			logger.info("API预警数据 JOB,预警APPKEY日调用次数："+warmAppkeyCallCount +",api 访问数："+appKeyCallCout+",appkey:"+appKey);
			if(appKeyCallCout > 0 && warmAppkeyCallCount.intValue() <= appKeyCallCout.intValue() ){
				monitor.setWarmAppkeyCallCount(1);
				MonitorAppkeyWarnDetail appKeyDetail = new MonitorAppkeyWarnDetail();
				appKeyDetail.setAppKey(appKey);
				appKeyDetail.setId(UUIDUtil.getUUID());
				appKeyDetail.setAppkeyCallCount(appKeyCallCout);
				appKeyDetail.setTimeQuantum(DF_DATE.format(startDate));
				appKeyDetail.setWarmAppkeyCallCount(warmAppkeyCallCount);
				monitor.setAppKeyDetail(appKeyDetail);
			}else{
				monitor.setWarmAppkeyCallCount(0);
			}
			
			if( apiCallList != null && apiCallList.size() > 0){
			
				List<MonitorDayWarnDetail> dayWarnDetailList = new ArrayList<MonitorDayWarnDetail>();
				List<MonitorSuccRateWarnDetail> succRateDetails = new ArrayList<MonitorSuccRateWarnDetail>();
				Map<String, MonitorAppKeyDetailVo> apiWarmMap =  monitorAppKeyVo.getMonitorAppKeyDetailVo();
				
				for(Map<String,Object> map:apiCallList){
					
					Integer dayCallCount = MapUtils.getInteger(map, "call_count");
					String apiId = MapUtils.getString(map, "api_id");
					//API配置
					MonitorAppKeyDetailVo  monitorAppKeyDetailVo = apiWarmMap.get(apiId);
					Integer callNum = -1;
					if(monitorAppKeyDetailVo != null && monitorAppKeyDetailVo.getCallNum()>0){
						callNum = monitorAppKeyDetailVo.getCallNum();
					}
					logger.info("API预警数据 JOB,预警api日调用次数："+callNum +",api 访问数："+dayCallCount+",appkey:"+appKey+",api:"+apiId);
					//日调用次数预警明细
					if(callNum >0 && (callNum.intValue() <= dayCallCount.intValue())){
						MonitorDayWarnDetail  dayWarnDetail = new MonitorDayWarnDetail();
						dayWarnDetail.setAppKey(appKey);
						dayWarnDetail.setId(UUIDUtil.getUUID());
						dayWarnDetail.setApiId(apiId);
						dayWarnDetail.setDayCallCount(dayCallCount);
						dayWarnDetail.setTimeQuantum(DF_DATE.format(startDate));
						dayWarnDetail.setWarmDayCallCount(callNum);//测试
						dayWarnDetailList.add(dayWarnDetail);
					}
					
					//单接口调用成功率预警
					Integer successCallCount = MapUtils.getInteger(map, "sucess_call_count");
					Integer successRate  = monitorAppKeyVo.getSuccessRate();
					logger.info("API预警数据 JOB,预警api成功率：百分之"+successRate +",api 成功数："+successCallCount+",appkey:"+appKey+",api:"+apiId);
					if(successRate > 0 && (successRate/100*dayCallCount) >= successCallCount.intValue()){
						MonitorSuccRateWarnDetail succRateWarnDetail = new MonitorSuccRateWarnDetail();
						succRateWarnDetail.setId(UUIDUtil.getUUID());
						succRateWarnDetail.setApiId(apiId);
						succRateWarnDetail.setTimeQuantum(DF_DATE.format(startDate));
						succRateWarnDetail.setSuccessCallCount(successCallCount);
						succRateWarnDetail.setFailCallCount(dayCallCount-successCallCount);
						succRateWarnDetail.setAppKey(appKey);
						succRateDetails.add(succRateWarnDetail);
					}
				}
				
				monitor.setWarmDayCallCount(dayWarnDetailList.size());
				monitor.setDayDetails(dayWarnDetailList);
				monitor.setWarmSuccessCount(succRateDetails.size());
				monitor.setSuccRateDetails(succRateDetails);
				
			}
			monitor.setRateWarnDetails(rateList);
			monitor.setWarmRateCount(rateList==null?0:rateList.size());
			
			//4项标准有一项不为0
			if(monitor.getWarmAppkeyCallCount() > 0 || monitor.getWarmDayCallCount() >0 || monitor.getWarmRateCount() > 0 || monitor.getWarmSuccessCount() > 0){
				monitorList.add(monitor);
			}
			
			apiMonitorService.insertMonitorEarlyWarning(monitorList);
			
		}
		
		return "true";
	}
	
	
	@ResponseBody
	@RequestMapping("/createIpAddress")
	public String createIpAddress() throws Exception{
		Set<String> set = setOperations.members(RedisKeyUtils.COUNT_SET_KEY);
		Set<String> appKeyIpSet = new HashSet<String>();
		List<String> keys = new ArrayList<String>();
		for(String str:set){
			String api = str.split(":")[0];
			String appkey = str.split(":")[1];
			String clientIp = str.split(":")[2];
			String appKeyIp = appkey+":"+clientIp;
		
			//api appkey 总调用情况
			String apikeyCountKey = RedisKeyUtils.getApiKeyALlKey(api, appkey, RedisKeyUtils.CALL_COUNT);
			String apikeyFailCountKey = RedisKeyUtils.getApiKeyALlKey(api, appkey, RedisKeyUtils.FAIL_CALL_COUNT);
			String apikeyExTimeKey = RedisKeyUtils.getApiKeyALlKey(api, appkey, RedisKeyUtils.EX_TIME);
		
			keys.add(apikeyCountKey);
			keys.add(apikeyFailCountKey);
			keys.add(apikeyExTimeKey);
			appKeyIpSet.add(appKeyIp);
		}
		
		//总调用情况
		String countALlKey = RedisKeyUtils.getCountALlKey(RedisKeyUtils.CALL_COUNT);
		String exTimeALLkey = RedisKeyUtils.getCountALlKey(RedisKeyUtils.EX_TIME);
		String failAllKey = RedisKeyUtils.getCountALlKey(RedisKeyUtils.FAIL_CALL_COUNT);
		keys.add(countALlKey);
		keys.add(exTimeALLkey);
		keys.add(failAllKey);
		keys.add(RedisKeyUtils.COUNT_SET_KEY);
		stringRedisTemplate.delete(keys);
	
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		List<AnalyzeIp> ipObjs = new ArrayList<AnalyzeIp>();
		for(String str:appKeyIpSet){
			String appkey = str.split(":")[0];
			String clientIp = str.split(":")[1];
			if(StringUtils.isNotEmpty(appkey) && StringUtils.isNotEmpty(clientIp)){
				AnalyzeIp analyzeIp = new AnalyzeIp();
				analyzeIp.setId(UUIDUtil.getUUID());
				analyzeIp.setAppKey(appkey);
				analyzeIp.setIp(clientIp);
				analyzeIp.setTimeQuantum(DF_DATETIME.format(cal.getTime()));
				ipObjs.add(analyzeIp);
			}
			
			List<ApiKey> keyList = apiKeyDao.findBy("appKey", appkey, false);
			int doubtType = -1;
			if(CollectionUtils.isEmpty(keyList)){
				doubtType = 0;
			}else if(keyList.get(0).getStatus().getDescription().equals("关闭") ) {
				doubtType = 1;
			}
			if(doubtType!=-1){
				MonitorInvalidIp invalidIp = new MonitorInvalidIp();
				invalidIp.setId(UUIDUtil.getUUID());
				invalidIp.setDoubtType(doubtType);
				invalidIp.setInvalidCount(apiAnalyzeService.getAppkeyCallCount(cal.getTime(), new Date(), appkey));
				invalidIp.setLastCallTime(apiAnalyzeService.getLastCallDate(cal.getTime(), new Date(), appkey));
				invalidIp.setIp(clientIp);
				invalidIp.setTimeQuantum(DF_DATE.format(cal.getTime()));
				apiReportService.insertMonitorInvalidIp(invalidIp);
			}
		}
		
		apiReportService.insertAnalyzeIpBatch(ipObjs);
	
		return new Gson().toJson(appKeyIpSet);
	}
	
	
	@ResponseBody
	@RequestMapping("/createApiFiveMin")
	public String createApiFiveMin(){
		
		return "true";
	}
	
	/**
	 * 临时测试用
	 * api实时监控
	 * http://localhost:8080/mms/apiJob/apiCount.sc?apiId=8a8094ff4019b609014019baf85b0003
	 * @param apiId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/apiCount")
	public String apiCount(String apiId)throws Exception{
		Gson gson = new Gson();
		List<AppKeyCount> list = apiAnalyzeService.findAppKeyCountByApiId(apiId);
		String result = gson.toJson(list);
		return result;
	}

	
	/**
	 * 临时测试用
	 * appKey 实时监控
	 * http://localhost:8080/mms/apiJob/appkeyCount.sc?appkey=308c6608_13de3f418e5__7fef
	 * @param apiId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appkeyCount")
	public String appkeyCount(String appkey){
		Gson gson = new Gson();
		List<ApiCount> list = apiAnalyzeService.findApiCountByAppKeyOwner(appkey);
		String result = gson.toJson(list);
		return result;
	}
	
	/**
	 * 临时测试用
	 * API 总调用 实时监控
	 * http://localhost:8080/mms/apiJob/apiAllCount.sc
	 * @param apiId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/apiAllCount")
	public String apiAllCount(){
		return new Gson().toJson(apiAnalyzeService.findApiAllCount());
	}
	
	/**
	 * 按小时汇总
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/summary2ApiForhoursJob")
	public String summary2ApiForhoursJob(String startTime, String endTime) throws Exception {
		if (StringUtils.isBlank(startTime)) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1);
			startTime = DF_DATE.format(cal.getTime());
			endTime = DF_DATE.format(cal.getTime());
		}
		
		List<AnalyzeInterface> list = apiReportService.summary2ApiForHoursJob(startTime, endTime);
		if (CollectionUtils.isNotEmpty(list)) {
			for (AnalyzeInterface analyzeInterface : list) {
				analyzeInterface.setId(UUIDGenerator.getUUID());
				analyzeInterface.setCreateTime(new Date());
			}
			
			apiReportService.insertAnalyzeInterfaceBatch(list);
		}
		
		return "true";
	}
	
	@ResponseBody
	@RequestMapping("/summary2AppkeyForhoursJob")
	public String summary2AppkeyForhoursJob(String startTime, String endTime) throws Exception {
		if (StringUtils.isBlank(startTime)) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1);
			startTime = DF_DATE.format(cal.getTime());
			endTime = DF_DATE.format(cal.getTime());
		}
		List<AnalyzeAppkey> list = apiReportService.summary2AppKeyForHoursJob(startTime, endTime);
		if (CollectionUtils.isNotEmpty(list)) {
			for (AnalyzeAppkey analyzeAppkey : list) {
				analyzeAppkey.setId(UUIDGenerator.getUUID());
				analyzeAppkey.setCreateTime(new Date());
			}
			apiReportService.insertAnalyzeAppkeyBatch(list);
		}
		return "true";
	}
	
	/**
	 * 每天汇总
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/summary2AppkeyFordaysJob")
	public String summary2AppkeyFordaysJob(String startTime, String endTime) throws Exception {
		if (StringUtils.isBlank(startTime)) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1);
			startTime = DF_DATE.format(cal.getTime());
			endTime = DF_DATE.format(cal.getTime());
		}
		List<AnalyzeAppkeyDay> list = apiReportService.summary2AppKeyForDaysJob(startTime, endTime);
		if (CollectionUtils.isNotEmpty(list)) {
			for (AnalyzeAppkeyDay analyzeAppkeyDay : list) {
				analyzeAppkeyDay.setId(UUIDGenerator.getUUID());
				analyzeAppkeyDay.setCreateTime(new Date());
				String merchantCode = apiAnalyzeService.getMerchantCodeByappKey(analyzeAppkeyDay.getAppKey());
				if(merchantCode == null || "".equals(merchantCode)) {
					continue ;
				}
				Map<String, MerchantDailyStatisticsVo> map = null;
				try {
					map = orderForMerchantApi.statisticMerchantData(merchantCode);
				} catch (Exception e) {
					logger.error("获取商家当天销量异常.", e);
				}
				
				//获取上一天的销售情况
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, -1);
				String next_day = DF_DATE.format(cal.getTime());
				MerchantDailyStatisticsVo statisVo = MapUtils.isEmpty(map) ? null : map.get(next_day);
				logger.info(MessageFormat.format("Api监控统计| 商家销量情况({0}): 订单量：{1}, 订单金额：{2}", new Object[] {next_day, null == statisVo ? 0 : statisVo.getOrderNum(), null == statisVo ? 0.0 : statisVo.getOrderMoney()}));
				
				//下单量等
				analyzeAppkeyDay.setOrderQty((int) (null == statisVo ? 0 : statisVo.getOrderNum()));
				analyzeAppkeyDay.setOrderMoney(null == statisVo ? 0.0 : statisVo.getOrderMoney());
				
				//IP连接数等
				analyzeAppkeyDay.setIpNums(2);
			}
			apiReportService.insertAnalyzeAppkeyDayBatch(list);
		}
		return "true";
	}
	
	
	@ResponseBody
	@RequestMapping("/mongoTest")
	public String mongoTest(){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		//for(int i=0;i<10000000;i++){
			BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
			Map<String,Object> parameterMap =  new HashMap<String, Object>();
			parameterMap.put("sign", "signValue");
			parameterMap.put("sign_method", "MD5");
			parameterMap.put("timestamp", df.format(new Date()));
			parameterMap.put("update_type", "0");
			parameterMap.put("app_version", "1.0");
			parameterMap.put("quantity", "0");
			parameterMap.put("app_key", "app_key_value");
			parameterMap.put("method", "yougou.inventory.update");
			parameterMap.put("format", "xml");
			parameterMap.put("merchant_code", "SP20120412312125");
			parameterMap.put("third_party_code", "C636071770138");
			
			builder.append("className", ApiLog.class.getName());
			builder.append("clientIp", "127.0.0.1");
			builder.append("operationParameters", new BasicDBObject(parameterMap));
			builder.append("operationResult","<?xml version='1.0' encoding='UTF-8'?><yougou_inventory_update_response><code>501</code><message>您发布的商品无对应货品SP20120412312125</message></yougou_inventory_update_response>");
			builder.append("operated", new Date());
			builder.append("isCallSucess",true);
			builder.append("exTime",12568);
			
			genericMongoDao.insert(ApiLog.class.getAnnotation(Documented.class).name(), builder.get());
		//}
		
		
		return "true";
	}
	
	@ResponseBody
	@RequestMapping("/createAppKeyUserCache")
	public String createAppKeyUserCache() throws Exception{
		apiAnalyzeService.createAppKeyUserCache();
		Long apiRelevanceSize = hashOperations.size(API_APPKEY_HASH);//add by LQ.
		return apiRelevanceSize.toString();
	}
	
	@ResponseBody
	@RequestMapping("/createAppSecretCache")
	public String createAppSecretCache() throws Exception{
		List<ApiKey> list = apiKeyDao.getAll();
		for(ApiKey apiKey:list){
			hashOperations.put("api.appkey.secret.hash",apiKey.getAppKey(),apiKey.getAppSecret());
		}
		return "true";
	}
	
	/**
	 * 更新appkey与metadata的缓存
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/createApiKeyMetadataCache")
	public String createApiKeyMetadataCache() throws Exception{
		List<Map<String, Object>> list = apiAnalyzeService.getApiKeyMetadata();
		for(Map<String, Object> map:list){
			hashOperations.put("api.appkey.metadata.hash",MapUtils.getString(map, "metadata_val"),MapUtils.getString(map, "app_key"));
		}
		return "true";
	}
	
	/**
	 * redis 开关
	 * http://localhost:8080/mms/apiJob/disableUseRedis.sc?useRedis=true
	 * @param useRedis
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/disableUseRedis")
	public String disableUseRedis(boolean useRedis) throws Exception{
		logger.info("设置是否使用redis: "+useRedis);
		configurationManager.disableUseRedis(useRedis);
		return "true";
	}
	
	@ResponseBody
	@RequestMapping("/getKeyUserByAppkey")
	public String getKeyUserByAppkey(String appKey) throws Exception{
		
		return apiAnalyzeService.getKeyUserByAppkey(appKey);
	}
	
	@ResponseBody
	@RequestMapping("/getAppkeyByUser")
	public String getAppkeyByUser(String appKeyUser) throws Exception{
		
		return new Gson().toJson(apiAnalyzeService.getAppkeyByUser(appKeyUser));
	}
	
	@ResponseBody
	@RequestMapping("/getApiMetadataByApiName")
	public String getApiMetadataByApiName(String apiName) throws Exception{
		
		return new Gson().toJson(apiAnalyzeService.getApiMetadataByApiName(apiName));
	}
	
	/**
	 * http://localhost:8080/mms/apiJob/apiMonitorFrequencyWarn.sc
	 * Api监控频率预警 job  （每小时执行）
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/apiMonitorFrequencyWarn")
	public String apiMonitorFrequencyWarn() throws Exception {
		MonitorEarlyWarnQueryVo queryVo = new MonitorEarlyWarnQueryVo();
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 1, 0, 0);
		String compareTime = DF_DATETIME.format(cal.getTime());
		cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY), 0, 0);
		queryVo.setEndTime(DF_DATETIME.format(cal.getTime()));
		cal.add(Calendar.HOUR_OF_DAY, -1);
		queryVo.setStartTime(DF_DATETIME.format(cal.getTime()));
		
		Map<String, List<MonitorRateWarnDetail>> warnMaps = new HashMap<String, List<MonitorRateWarnDetail>>();
		list = apiMonitorService.queryApiFrequencyBynow(queryVo);
		if (CollectionUtils.isNotEmpty(list)) {
			for (MonitorRateWarnDetail detail : list) {
				//监控配置
				MonitorAppKeyVo configVo = apiAnalyzeService.queryMonitorAppKeyVo(detail.getAppKey());
				if (configVo != null) {
					MonitorAppKeyDetailVo config_detail_vo = configVo.getMonitorAppKeyDetailVo().get(detail.getApiId());
					//实际频率大于预警频率时，记录到redis
					if (detail.getRateCallCount() > config_detail_vo.getFrequencyEarlyWarn()) {
						detail.setId(UUIDGenerator.getUUID());
						detail.setWarmRateCallCount(config_detail_vo.getFrequencyEarlyWarn());
						List<MonitorRateWarnDetail> temp_list = warnMaps.get(detail.getAppKey());
						if (CollectionUtils.isEmpty(temp_list)) temp_list = new ArrayList<MonitorRateWarnDetail>();
						temp_list.add(detail);
						warnMaps.put(detail.getAppKey(), temp_list);
					}
				}
			}
		}
		
		
		//第二天凌晨第一次统计计算时，把缓存清空
		if (compareTime.equals(queryVo.getEndTime())) {
			this.redisTemplate.delete(RedisKeyUtils.API_FREQUENCY_EARLY_WARN);
		}
		this.redisTemplate.opsForValue().set(RedisKeyUtils.API_FREQUENCY_EARLY_WARN, warnMaps);
		
		return "true";
	}
    
  /**
   * 更新缓存中的监控参数值
   * appKey 实时监控
   * http://localhost:8080/mms/apiJob/updateMonitorParameter.sc
   * @return
   */
  @ResponseBody
  @RequestMapping("/updateMonitorParameter")
  public String updateMonitorParameter(){
      Gson gson = new Gson();
      List<String> list = apiAnalyzeService.updateRedisCasheForMonitorTemplate();
      String result = gson.toJson(list);
      return result;
  }
  
  private String monitorAppkeyFlow2() throws Exception{
	  Long startMillis = System.currentTimeMillis();
	  Calendar cal = Calendar.getInstance();
	  cal.set(Calendar.HOUR_OF_DAY, 0);
	  cal.set(Calendar.MINUTE, 0);
	  cal.set(Calendar.SECOND, 0);
	  Date startDate = cal.getTime();
	  Date endDate = new Date();
	  List<String> appkeyList = apiAnalyzeService.findAppKeyForCountByDate(startDate, endDate);
	  Integer flowMax = Integer.valueOf(valueOperations.get(API_APPKEY_FLOW_MAX_NUM)==null?"0":valueOperations.get(API_APPKEY_FLOW_MAX_NUM));
	  Integer warnMax = Integer.valueOf(valueOperations.get(API_APPKEY_FLOW_WARN_NUM)==null?"0":valueOperations.get(API_APPKEY_FLOW_WARN_NUM));
	  for(String appkey:appkeyList){
		 Integer count = apiAnalyzeService.findCallCountByDateAndAppKey(startDate, endDate, appkey);
		 String appkeyUser = apiAnalyzeService.getKeyUserByAppkey(appkey);
		 String emails = appkeyFlowWarnEmails.getEmails();
		 //超过警告预警值 且 当天没有发过邮件
		 if(warnMax.intValue() <= count.intValue() && !setOperations.isMember(API_APPKEY_FLOW_IS_SEND_EMAIL, appkey)){
			StringBuffer sb = new StringBuffer();
			sb.append(appkeyUser);
			sb.append("日调用次数已超过"+warnMax.toString()+"次（预警值），");
			sb.append("<span style='color:red'>如果达到"+flowMax+"次则自动锁定接口不可使用，第二天零时解封</span>");
			sb.append("，请知悉。");
			MailSenderInfo mailInfo = new MailSenderInfo();
			mailInfo.setContent(sb.toString());
			mailInfo.setTitle("api流量提醒-优购商城");
			String otherEmail = apiService.findEmailByAppKey(appkey);
			if(StringUtils.isNotBlank(otherEmail)){
				emails = emails +","+ otherEmail;
			}
			mailInfo.setToAddress(emails); // 接受方邮箱
			emailApi.sendByOperation(mailInfo);
			
			setOperations.add(API_APPKEY_FLOW_IS_SEND_EMAIL, appkey);
			logger.info(String.format("appkey [%s], 流量 [%s], 警告邮件 [%s] 发送成功", appkey,count.toString(),emails));
		 }
		 //超过最大值
		 if(flowMax.intValue() <= count.intValue() && !setOperations.isMember(API_APPKEY_FLOW_IS_SEND_CLOSE_EMAIL, appkey)){
			apiKeyService.updateAppKeyStatus(appkey, "0");
			hashOperations.delete(APPKEY_SECRET_KEY, appkey);
			setOperations.add(API_APPKEY_FLOW_IS_SEND_CLOSE_EMAIL, appkey);
			setOperations.add(CacheConstant.APPKEY_YOUGOU_STATUS, appkey);
			
			StringBuffer sb = new StringBuffer();
			sb.append(appkeyUser);
			sb.append("日调用次数已超过上限"+flowMax.toString()+"次，");
			sb.append("<span style='color:red'>已于"+DF_DATETIME.format(new Date())+"锁定全部接口不能使用</span>");
			sb.append("，请知悉。");
			MailSenderInfo mailInfo = new MailSenderInfo();
			mailInfo.setContent(sb.toString());
			mailInfo.setTitle("api流量提醒-优购商城");
			String otherEmail = apiService.findEmailByAppKey(appkey);
			if(StringUtils.isNotBlank(otherEmail)){
				emails = emails +","+ otherEmail;
			}
			mailInfo.setToAddress(emails); // 接受方邮箱
			emailApi.sendByOperation(mailInfo);
			logger.info(String.format("appkey [%s] 流量 [%s] ,超上限，邮件 [%s] 发送成功", appkey,count.toString(),emails));
		 }
		 
	  }
	  logger.info(String.format("监控appkey流量job执行完毕，开始时间[%s],结束时间[%s],耗时[%d]", DF_DATETIME.format(startDate),DF_DATETIME.format(endDate),(System.currentTimeMillis() - startMillis)));
	  return "true";
  }
  
  
  /**
   * 对sql优化，appkey分组查询
   * 监控appkey流量job
   * http://localhost:8080/mms/apiJob/monitorAppkeyFlow.sc
   * 
   * 配合每5分钟的中间表使用  http://localhost:8080/mms/apiJob/createApiAnalyzeDetail.sc
   * @return
 * @throws Exception 
   */
  @ResponseBody
  @RequestMapping("/monitorAppkeyFlow")
  public String monitorAppkeyFlow() throws Exception{
	  Long startMillis = System.currentTimeMillis();
	  Calendar cal = Calendar.getInstance();
	  cal.set(Calendar.HOUR_OF_DAY, 0);
	  cal.set(Calendar.MINUTE, 0);
	  cal.set(Calendar.SECOND, 0);
	  Date startDate = cal.getTime();
	  Date endDate = new Date();
	  //List<String> appkeyList = apiAnalyzeService.findAppKeyForCountByDate(startDate, endDate);
	  List<Map<String,Object>> appkeyList = apiAnalyzeService.findCountGroupByAppkey(startDate, endDate);
	  if(CollectionUtils.isNotEmpty(appkeyList)){
		  Integer flowMax = Integer.valueOf(valueOperations.get(API_APPKEY_FLOW_MAX_NUM)==null?"0":valueOperations.get(API_APPKEY_FLOW_MAX_NUM));
		  Integer warnMax = Integer.valueOf(valueOperations.get(API_APPKEY_FLOW_WARN_NUM)==null?"0":valueOperations.get(API_APPKEY_FLOW_WARN_NUM));
		  for(Map<String,Object> map : appkeyList){
				 int count = NumberUtils.toInt(String.valueOf(map.get("callCount")), 0);
				 String appkey = MapUtils.getString(map, "appkey");
				 String appkeyUser = apiAnalyzeService.getKeyUserByAppkey(appkey);
				 String emails = appkeyFlowWarnEmails.getEmails();
				 //超过警告预警值 且 当天没有发过邮件
				 if(warnMax.intValue() <= count && !setOperations.isMember(API_APPKEY_FLOW_IS_SEND_EMAIL, appkey)){
					StringBuffer sb = new StringBuffer();
					sb.append(appkeyUser);
					sb.append("日调用次数已超过"+warnMax.toString()+"次（预警值），");
					sb.append("<span style='color:red'>如果达到"+flowMax+"次则自动锁定接口不可使用，第二天零时解封</span>");
					sb.append("，请知悉。");
					MailSenderInfo mailInfo = new MailSenderInfo();
					mailInfo.setContent(sb.toString());
					mailInfo.setTitle("api流量提醒-优购商城");
					String otherEmail = apiService.findEmailByAppKey(appkey);
					if(StringUtils.isNotBlank(otherEmail)){
						emails = emails +","+ otherEmail;
					}
					mailInfo.setToAddress(emails); // 接受方邮箱
					emailApi.sendByOperation(mailInfo);
					
					setOperations.add(API_APPKEY_FLOW_IS_SEND_EMAIL, appkey);
					logger.info("appkey [{}], 流量 [{}], 警告邮件 [{}] 发送成功", appkey,count,emails);
				 }
				 //超过最大值
				 if(flowMax.intValue() <= count && !setOperations.isMember(API_APPKEY_FLOW_IS_SEND_CLOSE_EMAIL, appkey)){
					apiKeyService.updateAppKeyStatus(appkey, "0");
					hashOperations.delete(APPKEY_SECRET_KEY, appkey);
					setOperations.add(API_APPKEY_FLOW_IS_SEND_CLOSE_EMAIL, appkey);
					setOperations.add(CacheConstant.APPKEY_YOUGOU_STATUS, appkey);
					
					StringBuffer sb = new StringBuffer();
					sb.append(appkeyUser);
					sb.append("日调用次数已超过上限"+flowMax.toString()+"次，");
					sb.append("<span style='color:red'>已于"+DF_DATETIME.format(new Date())+"锁定全部接口不能使用</span>");
					sb.append("，请知悉。");
					MailSenderInfo mailInfo = new MailSenderInfo();
					mailInfo.setContent(sb.toString());
					mailInfo.setTitle("api流量提醒-优购商城");
					String otherEmail = apiService.findEmailByAppKey(appkey);
					if(StringUtils.isNotBlank(otherEmail)){
						emails = emails +","+ otherEmail;
					}
					mailInfo.setToAddress(emails); // 接受方邮箱
					emailApi.sendByOperation(mailInfo);
					logger.info("appkey [{}] 流量 [{}] ,超上限，邮件 [{}] 发送成功", appkey,count,emails);
				 }
				 
			  }
	  }
	  logger.info(String.format("监控appkey流量job执行完毕，开始时间[%s],结束时间[%s],耗时[%d]", DF_DATETIME.format(startDate),DF_DATETIME.format(endDate),(System.currentTimeMillis() - startMillis)));
	  return "true";
  }
  
  
  /**
   *  监控appkey流量重置job
   *  http://localhost:8080/mms/apiJob/resetAppkeyFlow.sc
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/resetAppkeyFlow")
  public String resetAppkeyFlow() throws Exception{
	 // setOperations.getOperations().delete(key);
	 Set<String> set = setOperations.members(API_APPKEY_FLOW_IS_SEND_CLOSE_EMAIL);
	 Map<String,String> map = new HashMap<String, String>();
	 for(String appkey:set){
		 AppKeySecretVo vo = apiService.findAppKeySecretByAppkey(appkey);
		 String hashKey = vo.getAppKey();
		 if(StringUtils.isNotBlank(hashKey)){
			 String value = StringUtils.join(new Object[]{vo.getSecret(),vo.getMetadataVal()}, "#");
			 map.put(hashKey, value);
			 apiKeyService.updateAppKeyStatus(appkey, "1"); 
			 logger.info(String.format("appkey[%s] 解封", appkey));
		 }
	 }
	 if(map.size() > 0){
		 hashOperations.putAll(APPKEY_SECRET_KEY, map);
	 }
	 List<String> keyList = new ArrayList<String>();
	 keyList.add(API_APPKEY_FLOW_IS_SEND_CLOSE_EMAIL);
	 keyList.add(API_APPKEY_FLOW_IS_SEND_EMAIL);
	 keyList.add(CacheConstant.APPKEY_YOUGOU_STATUS);
	 setOperations.getOperations().delete(keyList);
	 logger.info("重置appkey流量成功");
	 return "true";
  }
}
