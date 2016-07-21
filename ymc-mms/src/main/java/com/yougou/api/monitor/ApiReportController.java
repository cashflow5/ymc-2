/**
 * 
 */
package com.yougou.api.monitor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.util.DateUtil;
import com.belle.infrastructure.util.StringUtil;
import com.yougou.api.model.vo.AppKeyMetadata;
import com.yougou.api.service.IApiAnalyzeService;
import com.yougou.merchant.api.monitor.service.IApiReportService;
import com.yougou.merchant.api.monitor.vo.AnalyzeAppkeyDay;
import com.yougou.merchant.api.monitor.vo.AnalyzeDetail;
import com.yougou.merchant.api.monitor.vo.AnalyzeInterface;
import com.yougou.merchant.api.monitor.vo.AnalyzeInterfaceDay;

/**
 * API统计报表管理
 * 
 * @author huang.tao
 *
 */
@Controller
@RequestMapping("/api/report")
public class ApiReportController {
	//private static final Logger logger = Logger.getLogger(MonitorManageController.class);
	
	@Resource
	private IApiReportService apiReportService;

	@Resource
	private IApiAnalyzeService apiAnalyzeService;
		
	/**
	 * API概况
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/to_api_info_report")
	public ModelAndView toApiInfoReport(ModelMap model, String startTime, String endTime, Integer flag, String mark, String isExport, HttpServletRequest request) throws Exception {
		if (flag == null) flag = 1; 
		
		Map<String, Object> resultMap = null;
		if (StringUtils.isBlank(mark) && (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime))) {
			mark = "3";
			resultMap = this.toFixedTimeQuantum(mark);
		} else {
			resultMap = new HashMap<String, Object>();
			resultMap.put("startTime", startTime);
			resultMap.put("endTime", endTime);
		}
		int hours = this.diffDateTimeForhours(MapUtils.getString(resultMap, "startTime"), MapUtils.getString(resultMap, "endTime"));
		
		List<AnalyzeInterfaceDay> list = apiReportService.queryApiIntegerGeneral(MapUtils.getString(resultMap, "startTime"), MapUtils.getString(resultMap, "endTime"));
		List<AnalyzeInterfaceDay> todaylist = apiReportService.queryApiIntegerGeneralForToday();
		
		//是否需要查询当天的数据(当天的数据从原表中查询 只能当前1小时)
		if (MapUtils.getString(resultMap, "endTime").equals(DateUtil.format1(new Date()))) {
			list = this.mergeCollection(list, todaylist);
		}
		
		if (CollectionUtils.isNotEmpty(list)) {
			for (AnalyzeInterfaceDay _obj : list) {
				_obj.setAvgFrequency(hours == 0 ? 0 : _obj.getCallCount()/hours);
				_obj.setSuccessRate((double) ((_obj.getCallCount() == null || _obj.getCallCount() == 0) ? 0 : _obj.getSucessCallCount()/(double)_obj.getCallCount()));
				_obj.setSuccessRate(_obj.getSuccessRate()*100);
				_obj.setAvgExTime(_obj.getAvgExTime() == null ? 0 : _obj.getAvgExTime() / _obj.getCallCount());
			}
		}
		
		//进行排名计算
		list = (List<AnalyzeInterfaceDay>) this.sortObjectByField(list, AnalyzeInterfaceDay.class, "maxFrequency", "rankingFrequency");
		list = (List<AnalyzeInterfaceDay>) this.sortObjectByField(list, AnalyzeInterfaceDay.class, "callCount", "rankingCall");
		
		//判断是否导出
		if (StringUtils.isNotBlank(isExport) && "1".equals(isExport)) {
			String[] headers = new String[]{"API接口", "总次数", "成功次数", "失败次数", "成功率", "总次数排名", "平均频率(次/时)", 
					"最高频率(次/时)", "最高频率排名", "平均接口执行时间(ms)", "最大AppKey并发数"};
			String[] cloumns = new String[] {"apiName", "callCount", "sucessCallCount", "failCallCount", "successRate", "rankingCall", "avgFrequency", "maxFrequency", "rankingFrequency", "avgExTime", "maxAppkeyNums"};
			Boolean[] percents = new Boolean[]{false, false, false, false, true, false, false, false, false, false, false};
			model.put("list", list);
			model.put("clazz", AnalyzeInterfaceDay.class);
			model.put("headers", headers);
			model.put("cloumns", cloumns);
			model.put("percents", percents);
			model.put("title", "API概况");
			model.put("fileName", MessageFormat.format("Api监控报表(API概况{0}至{1})", new Object[]{MapUtils.getString(resultMap, "startTime"), MapUtils.getString(resultMap, "endTime")}));
			return new ModelAndView(new ApiReportExcelView(), model);
		} else {
			model.put("list", list);
			model.put("startTime", MapUtils.getString(resultMap, "startTime"));
			model.put("endTime", MapUtils.getString(resultMap, "endTime"));
			model.put("flag", flag);
			model.put("mark", mark);
			return new ModelAndView("/yitiansystem/merchant/apimonitor/api_info_report", model);
		}
	}
	
	/**
	 * 
	 * AppKey概况
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/to_appkey_info_report")
	public ModelAndView toAppKeyInfoReport(ModelMap model, String startTime, String endTime, String mark, Integer flag, String isExport, HttpServletRequest request) throws Exception {
		if (flag == null) flag = 2;
		
		Map<String, Object> resultMap = null;
		if (StringUtils.isBlank(mark)) {
			mark = "3";
			resultMap = this.toFixedTimeQuantum(mark);
		} else {
			resultMap = new HashMap<String, Object>();
			resultMap.put("startTime", startTime);
			resultMap.put("endTime", endTime);
		}
		int hours = this.diffDateTimeForhours(MapUtils.getString(resultMap, "startTime"), MapUtils.getString(resultMap, "endTime"));
		List<AnalyzeAppkeyDay> list = apiReportService.queryAppKeyGeneral(MapUtils.getString(resultMap, "startTime"), MapUtils.getString(resultMap, "endTime"));
		List<AnalyzeAppkeyDay> todaylist = apiReportService.queryAppKeyGeneralForToday();
		
		//是否需要查询当天的数据(当天的数据从原表中查询 只能当前1小时)
		if (MapUtils.getString(resultMap, "endTime").equals(DateUtil.format1(new Date()))) {
			list = this.mergeCollection2(list, todaylist);
		}
		
		if (CollectionUtils.isNotEmpty(list)) {
			for (AnalyzeAppkeyDay _obj : list) {
				_obj.setAvgFrequency(hours == 0 ? 0 : _obj.getCallCount()/hours);
				_obj.setSuccessRate((double) ((_obj.getCallCount() == null || _obj.getCallCount() == 0) ? 0 : _obj.getSucessCallCount()/(double)_obj.getCallCount()));
				_obj.setSuccessRate(_obj.getSuccessRate()*100);
				_obj.setAppKeyHolder(apiAnalyzeService.getKeyUserByAppkey(_obj.getAppKey()));
			}
		}
		
		//进行排名计算
		list = (List<AnalyzeAppkeyDay>) this.sortObjectByField(list, AnalyzeAppkeyDay.class, "orderQty", "rankingOrderQty");
		list = (List<AnalyzeAppkeyDay>) this.sortObjectByField(list, AnalyzeAppkeyDay.class, "orderMoney", "rankingOrderMoney");
		list = (List<AnalyzeAppkeyDay>) this.sortObjectByField(list, AnalyzeAppkeyDay.class, "maxFrequency", "rankingFrequency");
		list = (List<AnalyzeAppkeyDay>) this.sortObjectByField(list, AnalyzeAppkeyDay.class, "callCount", "rankingCall");
		
		//判断是否导出
		if (StringUtils.isNotBlank(isExport) && "1".equals(isExport)) {
			String[] headers = new String[]{"AppKey持有者", "总次数", "成功次数", "失败次数", "成功率", "总次数排名", "平均频率(次/时)", 
					"最高频率(次/时)", "最高频率排名", "IP连接数", "下单量", "下单量排名", "下单金额", "下单金额排名"};
			String[] cloumns = new String[] {"appKeyHolder", "callCount", "sucessCallCount", "failCallCount", "successRate", "rankingCall", "avgFrequency", "maxFrequency", "rankingFrequency", "ipNums", "orderQty", "rankingOrderQty", "orderMoney", "rankingOrderMoney"};
			Boolean[] percents = new Boolean[]{false, false, false, false, true, false, false, false, false, false, false, false, false, false};
			model.put("list", list);
			model.put("clazz", AnalyzeAppkeyDay.class);
			model.put("headers", headers);
			model.put("cloumns", cloumns);
			model.put("percents", percents);
			model.put("title", "AppKey概况");
			model.put("fileName", MessageFormat.format("Api监控报表(AppKey概况{0}至{1})", new Object[]{MapUtils.getString(resultMap, "startTime"), MapUtils.getString(resultMap, "endTime")}));
			return new ModelAndView(new ApiReportExcelView(), model);
		} else {
			model.put("list", list);
			model.put("startTime", MapUtils.getString(resultMap, "startTime"));
			model.put("endTime", MapUtils.getString(resultMap, "endTime"));
			model.put("flag", flag);
			model.put("mark", mark);
			return new ModelAndView("/yitiansystem/merchant/apimonitor/api_info_report", model);
		}
	}
	
	/**
	 * 
	 * 趋势统计分析
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/to_report_chart")
	public ModelAndView toReportChart(ModelMap model, String startTime, String endTime, String mark, Integer flag, String isExport, String svg, HttpServletRequest request) throws Exception {
		if (flag == null) flag = 3;
				
		Map<String, Object> resultMap = null;
		if (StringUtils.isBlank(mark)) {
			mark = "3";
			resultMap = this.toFixedTimeQuantum(mark);
		} else {
			resultMap = new HashMap<String, Object>();
			resultMap.put("startTime", startTime);
			resultMap.put("endTime", endTime);
		}
		int hours = this.diffDateTimeForhours(MapUtils.getString(resultMap, "startTime"), MapUtils.getString(resultMap, "endTime"));
		
		//如果是一天内,则获取该天内的走势分析列表
		//1:按天统计 0:按小时统计
		Integer sign = startTime.equals(endTime) ? 0 : 1;
		Map<String, String> timequantums = this.structureTimeQuantum(startTime, endTime);
		List<AnalyzeInterface> list = new ArrayList<AnalyzeInterface>();
		
		List<AnalyzeInterface> _list = apiReportService.queryTrendAnalysis(MapUtils.getString(resultMap, "startTime"), MapUtils.getString(resultMap, "endTime"), sign);
		List<AnalyzeInterface> todaylist = apiReportService.queryTrendAnalysisForToday(sign);
		//是否需要查询当天的数据(当天的数据从原表中查询 只能当前1小时)
		if (MapUtils.getString(resultMap, "endTime").equals(DateUtil.format1(new Date()))) {
			_list = this.mergeCollection3(_list, todaylist);
		}
		
		Map<String, AnalyzeInterface> _tempMap = new HashMap<String, AnalyzeInterface>();
		if (CollectionUtils.isNotEmpty(_list)) {
			for (AnalyzeInterface _obj : _list) {
				_obj.setAvgFrequency(hours == 0 ? 0 : _obj.getCallCount()/hours);
				_obj.setSuccessRate((double) ((_obj.getCallCount() == null || _obj.getCallCount() == 0) ? 0 : _obj.getSucessCallCount()/(double)_obj.getCallCount()));
				_obj.setSuccessRate(_obj.getSuccessRate()*100);
				_tempMap.put(_obj.getTimeQuantum(), _obj);
			}
		}
		AnalyzeInterface _temp = null;
		for (String timequan : timequantums.keySet()) {
			if (_tempMap.containsKey(timequan)) {
				list.add(_tempMap.get(timequan));
				continue;
			}
			
			_temp = new AnalyzeInterface();
			_temp.setTimeQuantum(timequan);
			_temp.setCallCount(0);
			_temp.setFailCallCount(0);
			_temp.setSuccessRate(0.0);
			_temp.setSucessCallCount(0);
			_temp.setAvgFrequency(0);
			_temp.setMaxAppkeyNums(0);
			_temp.setMaxFrequency(0);
			
			list.add(_temp);
		}
		
		//判断是否导出
		if (StringUtils.isNotBlank(isExport) && "1".equals(isExport)) {
			/*if (StringUtils.isNotBlank(svg)) {
				byte[] bytes = SVGUtil.convert2ByteBySVG(svg);
				if (null != bytes) model.put("bytes", bytes);
			}*/
			String[] headers = new String[]{"时间段", "总次数", "成功次数", "失败次数", "成功率", "AppKey平均频率(次/时)", 
					"AppKey最高频率(次/时)", "最大AppKey并发数"};
			String[] cloumns = new String[] {"timeQuantum", "callCount", "sucessCallCount", "failCallCount", "successRate", "avgFrequency", "maxFrequency", "maxAppkeyNums"};
			Boolean[] percents = new Boolean[]{false, false, false, false, true, false, false, false};
			model.put("list", list);
			model.put("clazz", AnalyzeInterface.class);
			model.put("headers", headers);
			model.put("cloumns", cloumns);
			model.put("percents", percents);
			model.put("title", "总体趋势分析");
			model.put("fileName", MessageFormat.format("Api监控报表(总体趋势分析{0}至{1})", new Object[]{MapUtils.getString(resultMap, "startTime"), MapUtils.getString(resultMap, "endTime")}));
			return new ModelAndView(new ApiReportExcelView(), model);
		} else {
			model.put("list", list);
			model.put("startTime", MapUtils.getString(resultMap, "startTime"));
			model.put("endTime", MapUtils.getString(resultMap, "endTime"));
			model.put("flag", flag);
			model.put("sign", sign);
			model.put("mark", mark);
			return new ModelAndView("/yitiansystem/merchant/apimonitor/api_info_report", model);
		}
	}
	
	/**
	 * API接口调用明细报表
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/to_analyze_detail_api_report")
	public ModelAndView toAnalyzeDetailByApiReport(ModelMap model, String apiId, String apiName, String appKeyHolder, String startTime, String endTime, Integer flag, String isExport, String svg, HttpServletRequest request) throws Exception {
		if (flag == null) flag = 1;
		
		apiName = StringUtil.unescape(apiName);
		//通过appKeyHolder找到appKeys列表
		List<AppKeyMetadata> appKeyMetas = apiAnalyzeService.getAppkeyByUser(appKeyHolder);
		//模糊搜索AppKey持有者
		List<String> appKeys = this.extractAppKeys(appKeyMetas);
		
		//1:按天统计  0:按小时统计
		Integer sign = startTime.equals(endTime) ? 0 : 1;
		if (flag == 1) {
			int hours = this.diffDateTimeForhours(startTime, endTime);
			List<AnalyzeDetail> list = apiReportService.queryAnalyzeDetailByApi(apiId, appKeys, startTime, endTime);
			if (CollectionUtils.isNotEmpty(list)) {
				for (AnalyzeDetail _obj : list) {
					_obj.setAvgFrequency(hours == 0 ? 0 : _obj.getCallCount()/hours);
					_obj.setSuccessRate((double) ((_obj.getCallCount() == null || _obj.getCallCount() == 0) ? 0 : _obj.getSucessCallCount()/(double)_obj.getCallCount()));
					_obj.setSuccessRate(_obj.getSuccessRate()*100);
					_obj.setAppKeyHolder(apiAnalyzeService.getKeyUserByAppkey(_obj.getAppKey()));
				}
			}
			//排名计算
			list = (List<AnalyzeDetail>) this.sortObjectByField(list, AnalyzeDetail.class, "maxFrequency", "rankingFrequency");
			list = (List<AnalyzeDetail>) this.sortObjectByField(list, AnalyzeDetail.class, "callCount", "rankingCall");
			
			model.put("list", list);
		} else {//趋势分析
			Map<String, String> timequantums = this.structureTimeQuantum(startTime, endTime);
			List<AnalyzeInterface> list = new ArrayList<AnalyzeInterface>();
			List<AnalyzeInterface> _list = apiReportService.queryTrendAnalysisByApi(apiId, appKeys, startTime, endTime, sign);
			Map<String, AnalyzeInterface> _tempMap = new HashMap<String, AnalyzeInterface>();
			if (CollectionUtils.isNotEmpty(_list)) {
				for (AnalyzeInterface _obj : _list) {
					_obj.setSuccessRate((double) ((_obj.getCallCount() == null || _obj.getCallCount() == 0) ? 0 : _obj.getSucessCallCount()/(double)_obj.getCallCount()));
					_obj.setSuccessRate(_obj.getSuccessRate()*100);
					_tempMap.put(_obj.getTimeQuantum(), _obj);
				}
			}
			AnalyzeInterface _temp = null;
			for (String timequan : timequantums.keySet()) {
				if (_tempMap.containsKey(timequan)) {
					list.add(_tempMap.get(timequan));
					continue;
				}
				
				_temp = new AnalyzeInterface();
				_temp.setTimeQuantum(timequan);
				_temp.setCallCount(0);
				_temp.setFailCallCount(0);
				_temp.setSuccessRate(0.0);
				_temp.setSucessCallCount(0);
				_temp.setAvgFrequency(0);
				_temp.setMaxAppkeyNums(0);
				_temp.setMaxFrequency(0);
				
				list.add(_temp);
			}
			model.put("list", list);
		}
		
		//判断是否导出
		if (StringUtils.isNotBlank(isExport) && "1".equals(isExport)) {
			if (flag == 1) {
				String[] headers = new String[]{"AppKey持有者", "总次数", "成功次数", "失败次数", "成功率", "总调用次数排名", "平均频率(次/时)", 
						"最高频率(次/时)", "最高频率排名"};
				String[] cloumns = new String[] {"appKeyHolder", "callCount", "sucessCallCount", "failCallCount", "successRate", "rankingCall", "avgFrequency", "maxFrequency", "rankingFrequency"};
				Boolean[] percents = new Boolean[]{false, false, false, false, true, false, false, false, false};
				model.put("clazz", AnalyzeDetail.class);
				model.put("headers", headers);
				model.put("cloumns", cloumns);
				model.put("percents", percents);
				model.put("title", "单API明细");
				model.put("fileName", MessageFormat.format("Api监控报表(单API明细{0}至{1})", new Object[]{startTime, endTime}));
			} else {
				/*if (StringUtils.isNotBlank(svg)) {
					byte[] bytes = SVGUtil.convert2ByteBySVG(svg);
					if (null != bytes) model.put("bytes", bytes);
				}*/
				String[] headers = new String[]{"时间段", "总次数", "成功次数", "失败次数", "成功率", "AppKey平均频率(次/时)", 
						"AppKey最高频率(次/时)", "最大AppKey并发数"};
				String[] cloumns = new String[] {"timeQuantum", "callCount", "sucessCallCount", "failCallCount", "successRate", "avgFrequency", "maxFrequency", "maxAppkeyNums"};
				Boolean[] percents = new Boolean[]{false, false, false, false, true, false, false, false};
				model.put("clazz", AnalyzeInterface.class);
				model.put("headers", headers);
				model.put("cloumns", cloumns);
				model.put("percents", percents);
				model.put("title", "单API趋势分析");
				model.put("fileName", MessageFormat.format("Api监控报表(单API趋势分析{0}至{1})", new Object[]{startTime, endTime}));
			}
			
			return new ModelAndView(new ApiReportExcelView(), model);
		} else {
			model.put("startTime", startTime);
			model.put("endTime", endTime);
			model.put("flag", flag);
			model.put("apiName", apiName);
			model.put("appKeyHolder", appKeyHolder);
			model.put("apiId", apiId);
			model.put("sign", sign);
			return new ModelAndView("/yitiansystem/merchant/apimonitor/api_detail_report", model);
		}
	}
	
	/**
	 * AppKey下各接口(APi)的调用明细
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/to_analyze_detail_appkey_report")
	public ModelAndView toAnalyzeDetailByAppKeyReport(ModelMap model, String appKey, String apiId, String appKeyHolder, String startTime, String endTime, Integer flag, String isExport, String svg, HttpServletRequest request) throws Exception {
		if (flag == null) flag = 1;
		
		appKeyHolder = StringUtil.unescape(appKeyHolder);
		List<Map<String, String>> apiList = new ArrayList<Map<String, String>>();
		Map<String, String> tempApi = null;
		//1:按天统计 0:按小时统计
		Integer sign = startTime.equals(endTime) ? 0 : 1;
		if (flag == 1) {
			int hours = this.diffDateTimeForhours(startTime, endTime);
			List<AnalyzeDetail> list = apiReportService.queryAnalyzeDetailByAppKey(appKey, apiId, startTime, endTime);
			if (CollectionUtils.isNotEmpty(list)) {
				for (AnalyzeDetail _obj : list) {
					_obj.setAvgFrequency(hours == 0 ? 0 : _obj.getCallCount()/hours);
					_obj.setSuccessRate((double) ((_obj.getCallCount() == null || _obj.getCallCount() == 0) ? 0 : _obj.getSucessCallCount()/(double)_obj.getCallCount()));
					_obj.setSuccessRate(_obj.getSuccessRate()*100);
					
					//接口列表
					tempApi = new HashMap<String, String>();
					tempApi.put("apiId", _obj.getApiId());
					tempApi.put("apiName", _obj.getApiName());
					apiList.add(tempApi);
				}
			}
			//排名计算
			list = (List<AnalyzeDetail>) this.sortObjectByField(list, AnalyzeDetail.class, "maxFrequency", "rankingFrequency");
			list = (List<AnalyzeDetail>) this.sortObjectByField(list, AnalyzeDetail.class, "callCount", "rankingCall");
			
			
			model.put("list", list);
		} else {//趋势分析
			Map<String, String> timequantums = this.structureTimeQuantum(startTime, endTime);
			List<AnalyzeInterface> list = new ArrayList<AnalyzeInterface>();
			List<AnalyzeInterface> _list = apiReportService.queryTrendAnalysisByAppKey(appKey, apiId, startTime, endTime, sign);
			Map<String, AnalyzeInterface> _tempMap = new HashMap<String, AnalyzeInterface>();
			if (CollectionUtils.isNotEmpty(_list)) {
				for (AnalyzeInterface _obj : _list) {
					_obj.setSuccessRate((double) ((_obj.getCallCount() == null || _obj.getCallCount() == 0) ? 0 : _obj.getSucessCallCount()/(double)_obj.getCallCount()));
					_obj.setSuccessRate(_obj.getSuccessRate()*100);
					_obj.setAvgExTime((double) ((_obj.getCallCount() == null || _obj.getCallCount() == 0 || _obj.getAvgExTime() == null) ? 0 : _obj.getAvgExTime()/(double)_obj.getCallCount()));
					_tempMap.put(_obj.getTimeQuantum(), _obj);
				}
			}
			AnalyzeInterface _temp = null;
			for (String timequan : timequantums.keySet()) {
				if (_tempMap.containsKey(timequan)) {
					list.add(_tempMap.get(timequan));
					continue;
				}
				
				_temp = new AnalyzeInterface();
				_temp.setTimeQuantum(timequan);
				_temp.setCallCount(0);
				_temp.setFailCallCount(0);
				_temp.setSuccessRate(0.0);
				_temp.setSucessCallCount(0);
				_temp.setAvgFrequency(0);
				_temp.setMaxAppkeyNums(0);
				_temp.setMaxFrequency(0);
				_temp.setAvgExTime(0.0);
				
				list.add(_temp);
			}
			model.put("list", list);
		}
		
		//判断是否导出
		if (StringUtils.isNotBlank(isExport) && "1".equals(isExport)) {
			if (flag == 1) {
				String[] headers = new String[]{"API接口", "总次数", "成功次数", "失败次数", "成功率", "总调用次数排名", "平均频率(次/时)", 
						"最高频率(次/时)", "最高频率排名", "平均接口执行时间(ms)"};
				String[] cloumns = new String[] {"apiName", "callCount", "sucessCallCount", "failCallCount", "successRate", "rankingCall", "avgFrequency", "maxFrequency", "rankingFrequency", "avgExTime"};
				Boolean[] percents = new Boolean[]{false, false, false, false, true, false, false, false, false, false};
				model.put("clazz", AnalyzeDetail.class);
				model.put("headers", headers);
				model.put("cloumns", cloumns);
				model.put("percents", percents);
				model.put("title", "单AppKey明细");
				model.put("fileName", MessageFormat.format("Api监控报表(单AppKey明细{0}至{1})", new Object[]{startTime, endTime}));
			} else {
				/*if (StringUtils.isNotBlank(svg)) {
					byte[] bytes = SVGUtil.convert2ByteBySVG(svg);
					if (null != bytes) model.put("bytes", bytes);
				}*/
				String[] headers = new String[]{"时间段", "总次数", "成功次数", "失败次数", "成功率", "AppKey平均频率(次/时)", 
						"AppKey最高频率(次/时)", "最大AppKey并发数"};
				String[] cloumns = new String[] {"timeQuantum", "callCount", "sucessCallCount", "failCallCount", "successRate", "avgFrequency", "maxFrequency", "maxAppkeyNums"};
				Boolean[] percents = new Boolean[]{false, false, false, false, true, false, false, false};
				model.put("clazz", AnalyzeInterface.class);
				model.put("headers", headers);
				model.put("cloumns", cloumns);
				model.put("percents", percents);
				model.put("title", "单AppKey趋势分析");
				model.put("fileName", MessageFormat.format("Api监控报表(单AppKey趋势分析{0}至{1})", new Object[]{startTime, endTime}));
			}
			
			return new ModelAndView(new ApiReportExcelView(), model);
		} else {
			model.put("startTime", startTime);
			model.put("endTime", endTime);
			model.put("flag", flag);
			model.put("appKeyHolder", appKeyHolder);
			model.put("appKey", appKey);
			model.put("sign", sign);
			model.put("apiId", apiId);
			model.put("apiList", apiList);
			return new ModelAndView("/yitiansystem/merchant/apimonitor/appkey_detail_report", model);
		}
	}
	
	/**
	 * 获取时间间隔内的小时差
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	private int diffDateTimeForhours(String startTime, String endTime) throws Exception {
		int hours = 0;
		Long seconds = 0L;
		if (StringUtils.isBlank(startTime)) return hours;
		if (StringUtils.isBlank(endTime)) endTime = DateUtil.format1(new Date());
		
		if (endTime.equals(DateUtil.format1(new Date()))) {
			seconds = DateUtil.diffDateTime(new Date(), DateUtil.getdate1(startTime + " 00:00:00"));
		} else {
			seconds = DateUtil.diffDateTime(DateUtil.getdate1(endTime + " 23:59:59"), DateUtil.getdate1(startTime + " 00:00:00"));
		}
		
		hours = (int) (seconds/(60*60));
		
		return hours;
	}
	
	/**
	 * 设置固定查询时间段
	 * 
	 * @return
	 */
	private Map<String, Object> toFixedTimeQuantum(String mark) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		//mark 标记日期选择  1：今天 2：昨天 3：一周内  -1：半个月内
		Date nowDate = new Date();
		String startTime = null;
		String endTime = null;
		if ("1".equals(mark)) {// 今天
			startTime = DateUtil.format1(nowDate);
			endTime = DateUtil.format1(nowDate);
		} else if ("2".equals(mark)) {// 昨天
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1);
			startTime = DateUtil.format1(cal.getTime());
			endTime = DateUtil.format1(cal.getTime());
		} else if ("3".equals(mark)) {// 一周内
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -7);
			startTime = DateUtil.format1(cal.getTime());
			endTime = DateUtil.format1(nowDate);
		} else if ("4".equals(mark)) {// 半个月
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -15);
			startTime = DateUtil.format1(cal.getTime());
			endTime = DateUtil.format1(nowDate);
		} else if ("5".equals(mark)) {// 半个月
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			startTime = DateUtil.format1(cal.getTime());
			endTime = DateUtil.format1(nowDate);
		} 
		
		result.put("startTime", startTime);
		result.put("endTime", endTime);
		
		return result;
	}
	
	/**
	 * 设置固定时间段
	 * <b>标记日期选择  1：今天 2：昨天 3：一周内 4：半个月内 5:1个月内</b>
	 * 
	 * @param mark
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/set_fixed_time_quantum")
	public String setFixedTimeQuantum(String mark) throws Exception {
		Map<String, Object> result = this.toFixedTimeQuantum(mark);
		
		JSONArray jsonArray = JSONArray.fromObject(result);
		return jsonArray.toString();
	}
	
	/**
	 * 构造时间段
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<String, String> structureTimeQuantum(String startTime, String endTime) {
		Map<String, String> timeQuantums = new TreeMap<String, String>();
		if (startTime.equals(endTime)) { //按天
			for (int i = 0; i < 24; i++) {
				timeQuantums.put(startTime + ((i < 10) ? " 0" : " ") + i + ":00:00" , ((i < 10) ? " 0" : "") + i + ":00~" + ((i < 9) ? " 0" : "") + (i + 1) + ":00");
			}
		} else {
			try {
				while(!startTime.equals(endTime)) {
					Date start = DateUtil.getdate(startTime);
					timeQuantums.put(startTime, startTime);
					 
					Calendar cal = Calendar.getInstance();
					cal.setTime(start);
					cal.add(Calendar.DAY_OF_MONTH, 1);
					startTime = DateUtil.format1(cal.getTime());
				}
				timeQuantums.put(endTime, endTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
 		
		return timeQuantums;
	}
	
	private List<AnalyzeInterfaceDay> mergeCollection(List<AnalyzeInterfaceDay> list, List<AnalyzeInterfaceDay> todaylist) {
		if (CollectionUtils.isEmpty(list)) return todaylist;
		
		if (CollectionUtils.isEmpty(todaylist)) return list;
		
		Set<String> apiIdSet = new HashSet<String>();
		
		Map<String, AnalyzeInterfaceDay> todayMap = new HashMap<String, AnalyzeInterfaceDay>();
		for (AnalyzeInterfaceDay _todayObj : todaylist) {
			todayMap.put(_todayObj.getApiId(), _todayObj);
			apiIdSet.add(_todayObj.getApiId());
		}
		
		Map<String, AnalyzeInterfaceDay> map = new HashMap<String, AnalyzeInterfaceDay>();
		for (AnalyzeInterfaceDay _obj : list) {
			map.put(_obj.getApiId(), _obj);
			apiIdSet.add(_obj.getApiId());
		}
		
		List<AnalyzeInterfaceDay> _list = new ArrayList<AnalyzeInterfaceDay>();
		
		for (String apiId : apiIdSet) {
			if (todayMap.containsKey(apiId) && map.containsKey(apiId)) {
				AnalyzeInterfaceDay _temp_today = todayMap.get(apiId);
				AnalyzeInterfaceDay _temp = map.get(apiId);
				_temp.setCallCount((_temp.getCallCount() == null ? 0 : _temp.getCallCount()) + (_temp_today.getCallCount() == null ? 0 : _temp_today.getCallCount()));
				_temp.setFailCallCount((_temp.getFailCallCount() == null ? 0 : _temp.getFailCallCount()) + (_temp_today.getFailCallCount() == null ? 0 : _temp_today.getFailCallCount()));
				_temp.setSucessCallCount((_temp.getSucessCallCount() == null ? 0 : _temp.getSucessCallCount()) + (_temp_today.getSucessCallCount() == null ? 0 : _temp_today.getSucessCallCount()));
				_temp.setMaxAppkeyNums(0);
				_temp.setMaxFrequency(_temp.getMaxFrequency() > _temp_today.getMaxFrequency() ? _temp.getMaxFrequency() : _temp_today.getMaxFrequency());
				_temp.setAvgExTime((_temp.getAvgExTime() == null ? 0.0 : _temp.getAvgExTime()) + (_temp_today.getAvgExTime() == null ? 0.0 : _temp_today.getAvgExTime()));
				
				_list.add(_temp);
				continue;
			} 
			
			if (todayMap.containsKey(apiId)) {
				_list.add(todayMap.get(apiId));
				continue;
			}
			
			if (map.containsKey(apiId)) {
				_list.add(map.get(apiId));
			}
			
		}
		
		return _list;
	}
	
	private List<AnalyzeAppkeyDay> mergeCollection2(List<AnalyzeAppkeyDay> list, List<AnalyzeAppkeyDay> todaylist) {
		if (CollectionUtils.isEmpty(list)) return todaylist;
		
		if (CollectionUtils.isEmpty(todaylist)) return list;
		
		Set<String> appKeySet = new HashSet<String>();
		
		Map<String, AnalyzeAppkeyDay> todayMap = new HashMap<String, AnalyzeAppkeyDay>();
		for (AnalyzeAppkeyDay _todayObj : todaylist) {
			todayMap.put(_todayObj.getAppKey(), _todayObj);
			appKeySet.add(_todayObj.getAppKey());
		}
		
		Map<String, AnalyzeAppkeyDay> map = new HashMap<String, AnalyzeAppkeyDay>();
		for (AnalyzeAppkeyDay _obj : list) {
			map.put(_obj.getAppKey(), _obj);
			appKeySet.add(_obj.getAppKey());
		}
		
		List<AnalyzeAppkeyDay> _list = new ArrayList<AnalyzeAppkeyDay>();
		
		for (String appkey : appKeySet) {
			if (todayMap.containsKey(appkey) && map.containsKey(appkey)) {
				AnalyzeAppkeyDay _temp_today = todayMap.get(appkey);
				AnalyzeAppkeyDay _temp = map.get(appkey);
				_temp.setCallCount((_temp.getCallCount() == null ? 0 : _temp.getCallCount()) + (_temp_today.getCallCount() == null ? 0 : _temp_today.getCallCount()));
				_temp.setFailCallCount((_temp.getFailCallCount() == null ? 0 : _temp.getFailCallCount()) + (_temp_today.getFailCallCount() == null ? 0 : _temp_today.getFailCallCount()));
				_temp.setSucessCallCount((_temp.getSucessCallCount() == null ? 0 : _temp.getSucessCallCount()) + (_temp_today.getSucessCallCount() == null ? 0 : _temp_today.getSucessCallCount()));
				_temp.setMaxFrequency(_temp.getMaxFrequency() > _temp_today.getMaxFrequency() ? _temp.getMaxFrequency() : _temp_today.getMaxFrequency());
				_temp.setOrderMoney((_temp.getOrderMoney() == null ? 0 : _temp.getOrderMoney()) + (_temp_today.getOrderMoney() == null ? 0 : _temp_today.getOrderMoney()));
				_temp.setOrderQty((_temp.getOrderQty() == null ? 0 : _temp.getOrderQty()) + (_temp_today.getOrderQty() == null ? 0 : _temp_today.getOrderQty()));
				
				_list.add(_temp);
				continue;
			} 
			
			if (todayMap.containsKey(appkey)) {
				_list.add(todayMap.get(appkey));
				continue;
			}
			
			if (map.containsKey(appkey)) {
				_list.add(map.get(appkey));
			}
			
		}
		
		return _list;
	}
	
	private List<AnalyzeInterface> mergeCollection3(List<AnalyzeInterface> list, List<AnalyzeInterface> todaylist) {
		if (CollectionUtils.isEmpty(list)) return todaylist;
		
		if (CollectionUtils.isEmpty(todaylist)) return list;
		
		List<AnalyzeInterface> _list = new ArrayList<AnalyzeInterface>();
		_list.addAll(list);
		_list.addAll(todaylist);
		
		return _list;
	}
	
	/**
	 * 对象排序，并填充序列号
	 * 
	 * @param list
	 * @param clazz
	 * @param x 排序字段
	 * @param y 序号字段
	 * @return
	 * @throws Exception
	 */
	private List<?> sortObjectByField(List<?> list, Class<?> clazz, String x, String y) throws Exception {
		if (CollectionUtils.isEmpty(list)) return list;
		
		Field field = clazz.getDeclaredField(x);
		
		Object[] _a = new Object[list.size()];
		Object[] a = list.toArray(_a);
		
		PropertyDescriptor pd = new PropertyDescriptor(x, clazz);
        Method method = pd.getReadMethod();
        Integer result1 = 0;
        Integer result=0;
        Double dResult1 = 0.0;
        Double dResult = 0.0;
		for (int i = 0; i < a.length; i++) { 
			for	(int j = a.length - 1; j > 0; j--) { 
				boolean compResult = false;
				if ("java.lang.Integer".equals(field.getType().getCanonicalName())) {
					if(null!=method.invoke(a[j-1])){
						result = (Integer)method.invoke(a[j-1]);
					}
					if(null!=method.invoke(a[j])){
						result1 = (Integer)method.invoke(a[j]);
					}
					compResult = result1 > result; 
				} else if ("java.lang.Double".equals(field.getType().getCanonicalName())) {
					if(null!=method.invoke(a[j-1])){
						dResult = (Double)method.invoke(a[j-1]);
					}
					if(null!=method.invoke(a[j])){
						dResult1 = (Double)method.invoke(a[j]);
					}
					compResult = dResult1 > dResult;
				}
				
				if (compResult) { //比较交换相邻元素
					Object temp; 
					temp = a[j]; a[j] = a[j-1]; a[j-1] = temp; 
				}
			}
		}
		
		PropertyDescriptor _pd = new PropertyDescriptor(y, clazz);
		Method _method2 = _pd.getReadMethod();
        Method _method = _pd.getWriteMethod();
        
		for (int i = 0; i < a.length; i++) {
			if (i == 0) {
				_method.invoke(a[i], 1);
				continue;
			}
			if ("java.lang.Integer".equals(field.getType().getCanonicalName())) {
				if(null!=method.invoke(a[i-1])){
					result = (Integer)method.invoke(a[i-1]);
				}
				if(null!=method.invoke(a[i])){
					result1 = (Integer)method.invoke(a[i]);
				}
				if (result1 > result) {
					_method.invoke(a[i], _method2.invoke(a[i-1]));
					continue;
				}
				if (result1.equals(result)) {
					_method.invoke(a[i], _method2.invoke(a[i-1]));
					continue;
				}
				if (result1 < result) {
					Integer n = (Integer) _method2.invoke(a[i-1]);
					if (n == null) n = 1;
					_method.invoke(a[i], n + 1);
					continue;
				}
			} else if ("java.lang.Double".equals(field.getType().getCanonicalName())) {
				if(null!=method.invoke(a[i-1])){
					dResult = (Double)method.invoke(a[i-1]);
				}
				if(null!=method.invoke(a[i])){
					dResult1 = (Double)method.invoke(a[i]);
				}
				if (dResult1 > dResult) {
					_method.invoke(a[i], _method2.invoke(a[i-1]));
					continue;
				}
				if (dResult1.equals(dResult)) {
					_method.invoke(a[i], _method2.invoke(a[i-1]));
					continue;
				}
				if (dResult1 < dResult) {
					Integer n = (Integer) _method2.invoke(a[i-1]);
					if (n == null) n = 1;
					_method.invoke(a[i], n + 1);
					continue;
				}
			}
		}
		
		return Arrays.asList(a);
	}
	
	private List<String> extractAppKeys(List<AppKeyMetadata> objs) {
		if (CollectionUtils.isEmpty(objs)) return null;
		
		List<String> appKeys = new ArrayList<String>();
		for (AppKeyMetadata obj : objs) {
			appKeys.add(obj.getAppKey());
		}
		
		return appKeys;
	}
	
}
