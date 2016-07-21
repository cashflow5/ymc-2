/**
 * 
 */
package com.yougou.api.monitor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.util.DateUtil;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.google.gson.Gson;
import com.yougou.api.model.vo.ApiMetadata;
import com.yougou.api.model.vo.AppKeyMetadata;
import com.yougou.api.service.IApiAnalyzeService;
import com.yougou.api.util.RedisKeyUtils;
import com.yougou.merchant.api.common.PageFinder;
import com.yougou.merchant.api.common.Query;
import com.yougou.merchant.api.common.UUIDGenerator;
import com.yougou.merchant.api.monitor.service.IApiMonitorService;
import com.yougou.merchant.api.monitor.service.IApiReportService;
import com.yougou.merchant.api.monitor.vo.MonitorAppkeyWarnDetail;
import com.yougou.merchant.api.monitor.vo.MonitorDayWarnDetail;
import com.yougou.merchant.api.monitor.vo.MonitorEarlyWarnQueryVo;
import com.yougou.merchant.api.monitor.vo.MonitorEarlyWarning;
import com.yougou.merchant.api.monitor.vo.MonitorInvalidIp;
import com.yougou.merchant.api.monitor.vo.MonitorIpBlackList;
import com.yougou.merchant.api.monitor.vo.MonitorLock;
import com.yougou.merchant.api.monitor.vo.MonitorRateWarnDetail;
import com.yougou.merchant.api.monitor.vo.MonitorSuccRateWarnDetail;

/**
 * API接口监控管理
 * 
 * @author huang.tao
 *
 */
@Controller
@RequestMapping("/api/monitor/manage")
public class MonitorManageController {
	//private static final Logger logger = Logger.getLogger(MonitorManageController.class);
	
	@Resource
	private IApiReportService apiReportService;
	@Resource
	private IApiMonitorService apiMonitorService;
	@Resource(name = "stringRedisTemplate")
	private ValueOperations<String, String> valueOperations;
	@Resource
	private IApiAnalyzeService apiAnalyzeService;
	
	
	@RequestMapping("/to_invalidIps")
	public ModelAndView toInvalidIpList(ModelMap model, String ip,
			Integer doubtType, String startTime, String endTime, String mark, Integer flag, Integer fixed,
			Query query, HttpServletRequest request) throws Exception {
		if (flag == null) flag = 1;
		//mark 标记日期选择  1：今天 2：昨天 3：一周内  -1：半个月内
		Date nowDate = new Date();
		if (StringUtils.isEmpty(mark)) {
			mark = "3"; fixed = 1;
		}
		
		if (fixed != null && "1".equals(mark)) {// 今天
			startTime = DateUtil.format1(nowDate);
			endTime = DateUtil.format1(nowDate);
		} else if (fixed != null && "2".equals(mark)) {// 昨天
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1);
			startTime = DateUtil.format1(cal.getTime());
			endTime = DateUtil.format1(cal.getTime());
		} else if (fixed != null && "3".equals(mark)) {// 一周内
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -7);
			startTime = DateUtil.format1(cal.getTime());
			endTime = DateUtil.format1(nowDate);
		} else if (fixed != null && "4".equals(mark)) {// 半个月
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -15);
			startTime = DateUtil.format1(cal.getTime());
			endTime = DateUtil.format1(nowDate);
		} else {
			mark = "-1";
			if (fixed != null) {
				startTime = null;
				endTime = null;
			}
		}
		String _endTime = "";
		String _startTime = "";
		if (StringUtils.isNotBlank(startTime)) _startTime = startTime + " 00:00:00";
		if (StringUtils.isNotBlank(endTime)) _endTime = endTime + " 00:00:00";
		
		//doubtType==-1为全选
		if (doubtType != null && -1 == doubtType) doubtType = null;
		PageFinder<MonitorInvalidIp> pageFinder = apiReportService.queryMonitorInvalidIps(ip, doubtType, _startTime, _endTime, query);
		model.put("pageFinder", pageFinder);
		model.put("ip", ip);
		model.put("doubtType", doubtType);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("mark", mark);
		model.put("flag", flag);
		return new ModelAndView("/yitiansystem/merchant/apimonitor/invalid_ip_list", model);
	}
	
	@ResponseBody
	@RequestMapping("add_ip_blacklist")
	public String addIpBlackList(String ips, String reason, HttpServletRequest request) throws Exception {
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		
		List<MonitorIpBlackList> blackList = new ArrayList<MonitorIpBlackList>();
		if (StringUtils.isNotBlank(ips)) {
			String[] _ips = ips.split(",");
			MonitorIpBlackList obj = null;
			for (String ip : _ips) {
				obj = new MonitorIpBlackList();
				obj.setId(UUIDGenerator.getUUID());
				obj.setIp(ip);
				obj.setCreateTime(new Date());
				obj.setOperator(user != null ? user.getUsername() : null);
				obj.setReason(reason);
				obj.setUpdateTime(new Date());
				obj.setDeleteFag(NumberUtils.INTEGER_ONE);
				blackList.add(obj);
			}
		}
		
		return Boolean.toString(apiMonitorService.insertIpBlackLists(blackList));
	}
	
	@RequestMapping("/to_blacklistIps")
	public ModelAndView toBlacklistsIpList(ModelMap model, String ip,
			String startTime, String endTime, Integer flag,
			Query query, HttpServletRequest request) throws Exception {
		if (flag == null) flag = 2;
		
		String _endTime = "";
		String _startTime = "";
		if (StringUtils.isNotBlank(startTime)) _startTime = startTime + " 00:00:00";
		if (StringUtils.isNotBlank(endTime)) _endTime = endTime + " 23:59:59";
		
		PageFinder<MonitorIpBlackList> pageFinder = apiMonitorService.queryMonitorIpBlacklist(ip, _startTime, _endTime, query);
		model.put("pageFinder", pageFinder);
		model.put("ip", ip);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("flag", flag);
		return new ModelAndView("/yitiansystem/merchant/apimonitor/invalid_ip_list", model);
	}
	
	@ResponseBody
	@RequestMapping("remove_ip_blacklist")
	public String removeIpBlackList(String id, HttpServletRequest request) throws Exception {
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		
		if (StringUtils.isNotBlank(id)) {
			MonitorIpBlackList obj = new MonitorIpBlackList();
			obj.setId(id);
			obj.setOperator(user != null ? user.getUsername() : null);
			//obj.setReason(reason);
			obj.setUpdateTime(new Date());
			obj.setDeleteFag(NumberUtils.INTEGER_ZERO);
			apiMonitorService.updateIpBlackList(obj);
		}
		
		return Boolean.toString(true);
	}
	
	@RequestMapping("/to_apilocklists")
	public ModelAndView toApiLocklists(ModelMap model, MonitorLock monitorLock,
			Query query, HttpServletRequest request) throws Exception {
		if (monitorLock.getLockStatus() != null && monitorLock.getLockStatus() == -1) monitorLock.setLockStatus(null);
		if (monitorLock.getLockType() != null && monitorLock.getLockType() == -1) monitorLock.setLockType(null);
		
		String _startTime = "";
		String _endTime = "";
		//处理时间
		if (StringUtils.isNotBlank(monitorLock.getStartTime())) {
			_startTime = monitorLock.getStartTime();
			monitorLock.setStartTime(_startTime + " 00:00:00");
		}
		if (StringUtils.isNotBlank(monitorLock.getEndTime())) {
			_endTime = monitorLock.getEndTime();
			monitorLock.setEndTime(_endTime + " 23:59:59");
		}
		
		PageFinder<MonitorLock> pageFinder = apiMonitorService.queryMonitorLockByObj(monitorLock, query);
		monitorLock.setStartTime(_startTime);
		monitorLock.setEndTime(_endTime);
		
		model.put("pageFinder", pageFinder);
		model.put("monitorLock", monitorLock);
		return new ModelAndView("/yitiansystem/merchant/apimonitor/lock_api_list", model);
	}
	
	@ResponseBody
	@RequestMapping("unlock_api")
	public String unLockApiList(String id, HttpServletRequest request) throws Exception {
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		
		List<MonitorLock> list = new ArrayList<MonitorLock>();
		if (StringUtils.isNotBlank(id)) {
			String[] _ids = id.split(",");
			MonitorLock obj = null;
			for (String _id : _ids) {
				obj = new MonitorLock();
				obj.setId(_id);
				obj.setUnlockTime(new Date());
				obj.setUpdateTime(new Date());
				obj.setLockStatus(NumberUtils.INTEGER_ZERO);
				obj.setUpdateUser(user != null ? user.getUsername() : null);
				list.add(obj);
			}
		}
		
		return Boolean.toString(apiMonitorService.updateMonitorLock(list));
	}
	
	@RequestMapping("/queryApi")
	@ResponseBody
	public String queryApi(String apiText, HttpServletRequest request) throws Exception {
		List<ApiMetadata> apiList=apiAnalyzeService.getApiMetadataByApiName(apiText);
		return new Gson().toJson(apiList);
	}
	
	@RequestMapping("/queryAppKey")
	@ResponseBody
	public String queryAppKey(String appKeyText, HttpServletRequest request) throws Exception {
		List<AppKeyMetadata> appkeys=apiAnalyzeService.getAppkeyByUser(appKeyText);
		return new Gson().toJson(appkeys);
	}
	
	@RequestMapping("/realtime")
	public ModelAndView realtime(ModelMap model, HttpServletRequest request) throws Exception {
		String callAllKey = RedisKeyUtils.getCountALlKey(RedisKeyUtils.CALL_COUNT);
		Long callCount = Long.valueOf(valueOperations.get(callAllKey)==null?"0":valueOperations.get(callAllKey));
		String failCallKey = RedisKeyUtils.getCountALlKey(RedisKeyUtils.FAIL_CALL_COUNT);
		Long failCount = Long.valueOf(valueOperations.get(failCallKey)==null?"0":valueOperations.get(failCallKey));
		model.put("flag", 1);
		model.put("callCount", callCount);
		model.put("failCount", failCount);
		return new ModelAndView("/yitiansystem/merchant/apimonitor/realtime", model);
	}
	
	@RequestMapping("/to_earlywarning_list")
	public ModelAndView toEarlyWarningList(ModelMap model, MonitorEarlyWarnQueryVo queryVo, Query query, Integer isExport, HttpServletRequest request) throws Exception {		
		if (StringUtils.isBlank(queryVo.getStartTime())) {
			Date nowDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -15);
			queryVo.setStartTime(DateUtil.format1(cal.getTime()));
			queryVo.setEndTime(DateUtil.format1(nowDate));
		}
		
		//isExport判断是否进行导出操作
		if (isExport != null && isExport == 1) {
			query.setPageSize(Integer.MAX_VALUE);
			PageFinder<MonitorEarlyWarning> pageFinder = apiMonitorService.queryMonitorEarlyWarning(queryVo, query);
			for (MonitorEarlyWarning warn : pageFinder.getData()) {
				warn.setAppKeyHolder(apiAnalyzeService.getKeyUserByAppkey(warn.getAppKey()));
				warn.setTimeQuantum(queryVo.getStartTime() + "~" + queryVo.getEndTime());
			}
			String[] headers = new String[]{"AppKey持有者", "时间段", "AppKey日调用次数预警(次)", "单接口日调用次数预警(次)", "单接口频率预警(次)", "单接口调用成功率预警(次)"};
			String[] cloumns = new String[] {"appKeyHolder", "timeQuantum", "warmAppkeyCallCount", "warmDayCallCount", "warmRateCount", "warmSuccessCount"};
			model.put("list", pageFinder.getData());
			model.put("clazz", MonitorEarlyWarning.class);
			model.put("headers", headers);
			model.put("cloumns", cloumns);
			model.put("title", "API预警统计");
			model.put("fileName", MessageFormat.format("API预警统计_({0}~{1})", new Object[]{queryVo.getStartTime(), queryVo.getEndTime()}));
			return new ModelAndView(new ApiReportExcelView(), model);
		} else {
			PageFinder<MonitorEarlyWarning> pageFinder = apiMonitorService.queryMonitorEarlyWarning(queryVo, query);
			for (MonitorEarlyWarning warn : pageFinder.getData()) {
				warn.setAppKeyHolder(apiAnalyzeService.getKeyUserByAppkey(warn.getAppKey()));
			}
			model.put("pageFinder", pageFinder);
			model.put("startTime", queryVo.getStartTime());
			model.put("endTime", queryVo.getEndTime());
			return new ModelAndView("/yitiansystem/merchant/apimonitor/api_early_warning", model);
		}
	}
	
	@RequestMapping("/to_appkey_earlywarn_detail")
	public ModelAndView toAppKeyEarlyWarnDetail(ModelMap model, MonitorEarlyWarnQueryVo queryVo, HttpServletRequest request) throws Exception {				
		List<MonitorAppkeyWarnDetail> list = apiMonitorService.queryAppKeyEarlyWarningDetail(queryVo);
		if (CollectionUtils.isNotEmpty(list)) {
			for (MonitorAppkeyWarnDetail detail : list) {
				detail.setRatio((double) ((detail.getAppkeyCallCount()*100.00d)/detail.getWarmAppkeyCallCount()));
			}
		}
		model.put("list", list);
		model.put("startTime", queryVo.getStartTime());
		model.put("endTime", queryVo.getEndTime());
		model.put("appKey", queryVo.getAppKey());
		model.put("appKeyHolder", apiAnalyzeService.getKeyUserByAppkey(queryVo.getAppKey()));
		return new ModelAndView("/yitiansystem/merchant/apimonitor/api_early_warn_appkey_detail", model);
	}
	
	@RequestMapping("/to_api_earlywarn_detail")
	public ModelAndView toApiEarlyWarningDetail(ModelMap model, MonitorEarlyWarnQueryVo queryVo, HttpServletRequest request) throws Exception {		
		List<MonitorDayWarnDetail> list = apiMonitorService.queryApiEarlyWarningDetail(queryVo);
		if (CollectionUtils.isNotEmpty(list)) {
			for (MonitorDayWarnDetail detail : list) {
				detail.setRatio((double) ((detail.getDayCallCount()*100.00d)/detail.getWarmDayCallCount()));
				detail.setApiMethod(apiAnalyzeService.getApiMethodById(detail.getApiId()));
			}
		}
		model.put("list", list);
		model.put("startTime", queryVo.getStartTime());
		model.put("endTime", queryVo.getEndTime());
		model.put("appKey", queryVo.getAppKey());
		model.put("appKeyHolder", apiAnalyzeService.getKeyUserByAppkey(queryVo.getAppKey()));
		return new ModelAndView("/yitiansystem/merchant/apimonitor/api_early_warn_api_detail", model);
	}
	
	@RequestMapping("/to_succrate_earlywarn_detail")
	public ModelAndView toSuccRateEarlyWarnDetail(ModelMap model, MonitorEarlyWarnQueryVo queryVo, HttpServletRequest request) throws Exception {		
		List<MonitorSuccRateWarnDetail> list = apiMonitorService.querySuccRateWarnDetail(queryVo);
		if (CollectionUtils.isNotEmpty(list)) {
			for (MonitorSuccRateWarnDetail detail : list) {
				detail.setRatio((double) ((detail.getSuccessCallCount()*100.00d)/(detail.getSuccessCallCount() + detail.getFailCallCount())));
				detail.setApiMethod(apiAnalyzeService.getApiMethodById(detail.getApiId()));
			}
		}
		model.put("list", list);
		model.put("startTime", queryVo.getStartTime());
		model.put("endTime", queryVo.getEndTime());
		model.put("appKey", queryVo.getAppKey());
		model.put("appKeyHolder", apiAnalyzeService.getKeyUserByAppkey(queryVo.getAppKey()));
		return new ModelAndView("/yitiansystem/merchant/apimonitor/api_succrate_early_warn_detail", model);
	}
	
	@RequestMapping("/to_frequency_rate_earlywarn_detail")
	public ModelAndView toFrequencyRateWarnDetail(ModelMap model, MonitorEarlyWarnQueryVo queryVo, HttpServletRequest request) throws Exception {		
		List<MonitorRateWarnDetail> list = apiMonitorService.queryRateWarnDetail(queryVo);
		if (CollectionUtils.isNotEmpty(list)) {
			for (MonitorRateWarnDetail detail : list) {
				detail.setRatio((double) ((detail.getRateCallCount()*100.00d)/detail.getWarmRateCallCount()));
				detail.setApiMethod(apiAnalyzeService.getApiMethodById(detail.getApiId()));
				//设置预警时间段
				String temp = detail.getTimeQuantum();
				detail.setTimeQuantum(temp.substring(0, 10));
				int hour = Integer.valueOf(temp.substring(11, 13));
				int next_hour = hour + 1;
				String _hours = hour > 10 ? hour + ":00" : "0" + hour + ":00";
				String _next_hours = next_hour > 10 ? next_hour + ":00" : "0" + next_hour + ":00";
				detail.setHourQuantum(_hours + "~" + _next_hours); 
			}
		}
		model.put("list", list);
		model.put("startTime", queryVo.getStartTime());
		model.put("endTime", queryVo.getEndTime());
		model.put("appKey", queryVo.getAppKey());
		model.put("appKeyHolder", apiAnalyzeService.getKeyUserByAppkey(queryVo.getAppKey()));
		return new ModelAndView("/yitiansystem/merchant/apimonitor/api_frequency_rate_early_warn_detail", model);
	}
	
	
}
