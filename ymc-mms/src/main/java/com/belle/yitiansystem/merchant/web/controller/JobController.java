package com.belle.yitiansystem.merchant.web.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.util.FileUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.belle.yitiansystem.active.service.MerchantOfficialActivityService;
import com.belle.yitiansystem.merchant.model.pojo.PunishOrderAgent;
import com.belle.yitiansystem.merchant.model.pojo.ShipmentDaySetting;
import com.belle.yitiansystem.merchant.service.IMerchantOperatorApi;
import com.belle.yitiansystem.merchant.service.PunishService;
import com.belle.yitiansystem.merchant.service.ShipmentDaySettingService;
import com.belle.yitiansystem.merchant.service.impl.SupplierContractService;
import com.belle.yitiansystem.merchant.util.MerchantUtil;
import com.belle.yitiansystem.systemmgmt.util.SysconfigProperties;
import com.google.gson.Gson;
import com.yougou.api.model.vo.ApiCount;
import com.yougou.api.model.vo.AppKeyCount;
import com.yougou.api.service.IApiAbnormalAlarmService;
import com.yougou.api.service.IApiAnalyzeService;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.DateUtil2;

/**
 * 招商JOB controller
 * @author he.wc
 *
 */
@Controller
@RequestMapping("/job")
public class JobController {
	
	private Logger logger = Logger.getLogger(JobController.class);
	
	@Resource
	private IApiAbnormalAlarmService apiAbnormalAlarmService;
	
	@Resource
	private PunishService punishService;
	@Resource
	private ShipmentDaySettingService shipmentDaySettingService;
	@Resource
	private IApiAnalyzeService apiAnalyzeService;
	@Resource
	private SupplierContractService supplierContractService;
	@Resource
	private SysconfigProperties sysconfigProperties;
	
	@Resource
	private IMerchantOperatorApi merchantOperatorApi ;
	@Resource
	private RedisTemplate<String, Object> redisTemplateForSupplierCreate;
	
	@Resource
	private MerchantOfficialActivityService officialActiveService;
	
	private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	// 数据绑定
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateTimeFormat, true));
	}
	
	/**
	 * 生成违规订单JOB
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/PunishOrderCreateJob")
	public String punishOrderCreateJob() throws Exception {
		logger.info("生成违规订单JOB开始...");
		
		Calendar cal = Calendar.getInstance();
		ShipmentDaySetting daySetting = shipmentDaySettingService.findShipmentDaySettingByDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH) + 1 ,cal.get(Calendar.DAY_OF_MONTH));
		if(daySetting == null || StringUtils.equals(daySetting.getIsShipmentDay(), "0")){
			logger.info("JOB生成违规订单，今天是非发货日，无须生成超时订单");
			return "JOB生成违规订单，今天是非发货日，无须生成超时订单";
		}
		
		List<PunishOrderAgent> list = punishService.getPunishOrderAgentList();
		
		Iterator<PunishOrderAgent> it = list.iterator();
		while (it.hasNext()) {
			PunishOrderAgent entity = (PunishOrderAgent)it.next();
			punishService.saveSyncPunishOrder(entity);
		}
		logger.info("生成违规订单JOB结束...");
		
		return "true";
	}
	
	/**
	 * 违规订单发送邮件JOB
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/SendPunishOrderEmailJob")
	public String sendPunishOrderEmail() {
		logger.info("违规订单发送邮件JOB开始...");
		punishService.sendPunishOrderEmail();
		return "true";
	}
	
	@ResponseBody
	@RequestMapping("/ApiAbnormalAlarmEmailJob")
	public String triggeringApiAbnormalAlarmEmail() {
		apiAbnormalAlarmService.triggeringApiAbnormalAlarmEmail();
		return "true";
	}
	
	@ResponseBody
	@RequestMapping("/ApiAbnormalAlarmSmsJob")
	public String triggeringApiAbnormalAlarmSms() {
		apiAbnormalAlarmService.triggeringApiAbnormalAlarmSms();
		return "true";
	}
	
	/**
	 * openapi 日志测试
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ApiErrorTest")
	public String apiErrorTest() {
		
		Timer timer = new Timer();  
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				HttpClient client = new HttpClient();
				HttpMethod method = new GetMethod(
						"http://10.0.30.163:8080/mms/api.sc?sign=9af608542d94aa2eb9bdf0903073d792&sign_method=MD5&timestamp=2013-8-12+19%3A11%3A12&category=ee3ece8cc4f511e19c4f5cf3fc0c2d70&submit=POST&update_type=&quantity=1&app_key=1&method=yougou.inventory.update&format=XML&third_party_code=1");
				// 使用POST方法
				// HttpMethod method = new PostMethod("http://java.sun.com";);
				try {
					client.executeMethod(method);
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}, 1000, 1000);
		return "true";
	}
	
	/**
	 * 初始化发货日设置
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initialShipmentSay")
	public String initialShipmentDay(Date startDate,Date endDate){
		if(startDate == null || endDate == null || startDate.compareTo(endDate) > 0 ){
			return "请输入正确的startDate和endDate";
		}
		Date date = startDate;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		Integer startYear = cal.get(Calendar.YEAR);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(endDate);
		Integer endYear = calEnd.get(Calendar.YEAR);
		
		//先删除数据
		shipmentDaySettingService.deleteShipmentDaySetting(startYear, endYear);
		
		while(!date.equals(endDate)){
			int week = cal.get(Calendar.DAY_OF_WEEK);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DATE);
			logger.info("day:  "+day);
			logger.info("week: "+week);
			logger.info("date: "+dateTimeFormat.format(date));
			//每个月1号前补位，补到当个星期日为止。
			if(day == 1){
				int size = week;
				for(int i= 1; i < size; i++){
					ShipmentDaySetting entity = new ShipmentDaySetting();
					entity.setYear(year);
					entity.setMonth(month);
					entity.setDay(i-size);//为负数
					entity.setCreateTime(new Date());
					entity.setUpdateTime(new Date());
					shipmentDaySettingService.save(entity);
				}
			}
			
			ShipmentDaySetting entity = new ShipmentDaySetting();
			entity.setDate(date);
			entity.setYear(year);
			entity.setMonth(month);
			entity.setDay(day);
			entity.setCreateTime(new Date());
			entity.setUpdateTime(new Date());
			if(week == 1 || week == 7){//双休日非发货日
				entity.setIsShipmentDay("0");
			}else{
				entity.setIsShipmentDay("1");
			}
			shipmentDaySettingService.save(entity);
			
			//后补位置直到35
			int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			if(max == day){
				for(int i = max ; i< 35; i++){
					ShipmentDaySetting afterEntity = new ShipmentDaySetting();
					Integer afterDay = i+1;
					afterEntity.setYear(year);
					afterEntity.setMonth(month);
					afterEntity.setDay(afterDay);//最后天数+1
					afterEntity.setCreateTime(new Date());
					afterEntity.setUpdateTime(new Date());
					//ShipmentDaySetting setting =  shipmentDaySettingService.findShipmentDaySettingByDate(year, month, afterDay);
					//if(setting == null){
					shipmentDaySettingService.save(afterEntity);
					//}
				}
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
			date = cal.getTime();
		}
		
		return "true";
	}
	
	
	/**
	 * 临时测试用
	 * api实时监控
	 * http://localhost:8080/mms/job/apiCount.sc?apiId=8a8094e93f233365013f2338c52a0002
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
	 * http://localhost:8080/mms/job/appkeyCount.sc?appkey=308c6608_13de3f418e5__7fef
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
	 * 更新资质到期时间  ，每天的 凌晨2点执行
	 */
	@ResponseBody
	@RequestMapping("/updateDateInfo")
	
	public void updateDateInfo(){
		supplierContractService.updateDateInfo();
	}
	
	/**
	 * 将到期资质的商家清除库存  ，每天4点执行
	 */
	@ResponseBody
	@RequestMapping("/cleanMerchantWms")
	
	public void cleanMerchantWms(){
		merchantOperatorApi.checkOverDaysMerchant();
	}
	
	/**
	 * @author luo.q1
	 * @subject 1 清理培训中心上传用临时文件路径：${springMVC.file.path.temp} —— 每天job
	 * @subject 2  清理商家创建的草稿，7天内未编辑的草稿 —— 每天job
	 */
	@ResponseBody
	@RequestMapping("/cleanTempFilePath")
	public void cleanTempFilePath(){
		// step1: 清理培训中心上传用临时文件路径：${springMVC.file.path.temp}
		String dir = sysconfigProperties.getSpringMVCFilePathTemp();
		if( StringUtils.isEmpty(dir) ){
			dir = "/data/tmpUpload/";
		}
		File dirFile = new File(dir);
		if( dirFile.exists() ){
			FileUtil.deleteContents(dirFile);
		}
		//  step2: 清理商家创建的草稿，7天内未编辑的草稿
		cleanRedisForSupplierDraft();
	}
	
	
	/**
	 * @author zhang.f
	 * @subject 避免订单系统MQ消息漏发，导致订单中间表缺失发货时间，发货状态，从而影响到生成的超时违规订单数据不正确或者超时时间不正确
	 */
	@ResponseBody
	@RequestMapping("/cleanPunishOrder")
	public void cleanPunishOrderJob(){
		logger.error("cleanPunishOrderJob,超时违规订单定时清理JOB START...");
		punishService.cleanPunishOrder();
		logger.error("cleanPunishOrderJob,超时违规订单定时清理JOB END...");
	}
	

	/**
	 * 1  合同到期最後15天發郵件通知商家
	 */
	
	@ResponseBody
	@RequestMapping("/overDaySendMessage")
	public void overDaySendMessage(){
		 merchantOperatorApi.overDaySendMessage();
	}
	/**
	 * @author
	  
	 *2 是否已經到新合同時間，是的話，系統自動啟用新合同，否則，否則服務期限合同到期時間
	 *
	 */
	@ResponseBody
	@RequestMapping("/useNewContract")
	public void useNewContract(){
		merchantOperatorApi.useNewContract();
	}
	
	/**
	 * 1  合同到期超过60天关闭商家部分权限
	 */
	
	@ResponseBody
	@RequestMapping("/overDay60CloseOperation")
	public void overDay60CloseOperation(String date){
		Date targetDay = DateUtil2.getCurrentDate();
		if( StringUtils.isNotEmpty(date) ){
			date = date.trim();
			try {
				targetDay = DateUtil2.getdate( date );//eg:"2015-11-04"
			} catch (Exception e) {
				targetDay = DateUtil2.getCurrentDate();
			}
		}	
		 merchantOperatorApi.overDay60CloseOperation(sysconfigProperties.getBaseRoleIdArray(),targetDay);
	}
	/**
	 * 超过90天，关闭商家
	 */
	@ResponseBody
	@RequestMapping("/closeMerchantOver90Days")
	public void closeMerchantOver90Days(){
		 merchantOperatorApi.overDay90CloseOperation();
	}
	
	/**
	 * 初始化expand表，轻易不要执行
	 */
	@ResponseBody
	@RequestMapping("/initExpand")
	public void initExpand(){
		 merchantOperatorApi.initExpand();
	}
	
	/**
	 * 6天不操作，则自动清理商家草稿(深夜 执行一次)
	 */
	private void cleanRedisForSupplierDraft(){
    	Set<Object> keysSet = redisTemplateForSupplierCreate.opsForHash().keys( CacheConstant.C_MERCHANT_NAME_KEY );
    	String daysNum = sysconfigProperties.getAutoCleanDaysForDraft();
    	int days = 30;
    	if( StringUtils.isNotEmpty(daysNum) ){
    		try {
				days = Integer.parseInt( daysNum );
			} catch (NumberFormatException e) {
				logger.error("配置文件中有错： 自动清理商家草稿天数必须为数字");
				days = 30;
			}
    	}
    	if(null!=keysSet && keysSet.size()>0){
    		for(Object hashKey :keysSet){
    			HashMap<String,Object> map = (HashMap<String,Object>)redisTemplateForSupplierCreate.opsForHash().get(CacheConstant.C_MERCHANT_NAME_KEY, (String)hashKey);
    			if( null!=map && (map.get(CacheConstant.KEY_UPDATE_TIME))!=null ){
    				Date updateDate = (Date)map.get(CacheConstant.KEY_UPDATE_TIME);
    				if(DateUtil2.diffDate(new Date(), updateDate)>=days){
    					logger.debug("删除"+days+"天未操作的商家草稿："+(String)hashKey );
    					MerchantUtil.removeFromSupplierSegmentCache(CacheConstant.C_MERCHANT_NAME_KEY,(String)hashKey, redisTemplateForSupplierCreate);
    				}
    			}
    		}
    	}
	}
	
	/**
	 * 活动审核时间过后，未审核通过的商家报名更新成报名结束状态
	 */
	@ResponseBody
	@RequestMapping("/updateOfficialActiveStatus")
	public void updateOfficialActiveStatus(){
		officialActiveService.updateOfficialActiveStatusOver();
	}
	
}
