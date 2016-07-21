package com.yougou.kaidian.bi.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.MMSRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.yougou.kaidian.bi.beans.AnalysisIndex;
import com.yougou.kaidian.bi.service.IReportManagementSurveyService;
import com.yougou.kaidian.bi.service.IReportRealTimeStatisticsService;
import com.yougou.kaidian.bi.service.IReportTemplateService;
import com.yougou.kaidian.bi.vo.CommodityAnalysisVo;
import com.yougou.kaidian.bi.vo.RealTimeStatisticsVo;
import com.yougou.kaidian.commodity.service.IImageService;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.common.util.FileFtpUtil;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.tools.common.utils.DateUtil;
import com.yougou.tools.common.utils.StringUtil;

/**
 * 商家中心数据报表首页：经营概况
 * @author zhang.f1
 *
 */
@Controller
@RequestMapping("report")
public class ReportManagementSurveyController {
	
	@Resource
	IReportTemplateService reportTemplateService;
	
	@Resource
	IReportRealTimeStatisticsService reportRealTimeStatisticsService;
	
	@Resource
	IReportManagementSurveyService reportManagementSurveyService ;
	
	@Resource
	private CommoditySettings commoditySettings;
	@Resource
	private IImageService imageService;
	private static NumberFormat numberFormat = NumberFormat.getPercentInstance();
	private static DecimalFormat df = new DecimalFormat("#.##");
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	private Logger logger = LoggerFactory.getLogger(ReportManagementSurveyController.class);
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/**
	 * 商家中心数据报表-经营概况（首页）
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/gotoReportManagementSurvey")
	public String reportManagementSurvey(ModelMap modelMap, HttpServletRequest request) throws Exception {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		//加载实时概况数据
		RealTimeStatisticsVo realTimeStatisticsVo = new RealTimeStatisticsVo();
		reportRealTimeStatisticsService.queryRealTimeIndex(realTimeStatisticsVo);		
		//当前时间
		realTimeStatisticsVo.setCurrentTime(DateUtil.getDateTime(new Date(System.currentTimeMillis())));
		modelMap.put("realTimeStatisticsVo", realTimeStatisticsVo);
		
		//默认查询7天内的汇总数据，时间查询条件带入到页面，以便页面异步查询
		Date date = new Date();
		modelMap.put("startDate", DateUtil.formatDate(DateUtil.addDate(date, -7), "yyyy-MM-dd"));
		modelMap.put("endDate", DateUtil.formatDate(DateUtil.addDate(date, -1), "yyyy-MM-dd"));
		
		//查询经营概况指标
		List<Map> surveyIndexList =  reportManagementSurveyService.queryManageSurveyIndex();
		modelMap.put("surveyIndexList", surveyIndexList);
		//商品导出功能相关代码判断
		Map<String,Object> hashMap = (Map<String,Object>)redisTemplate.opsForValue().get(CacheConstant.C_MERCHANT_EXPORT_BI_PRODANALYSE+":"+merchantCode);
    	String isExportData = (String)redisTemplate.opsForValue().get(CacheConstant.C_USER_ENTER_METHOD+":createReportAnalysis:"+merchantCode);
    	Double progress = (Double)redisTemplate.opsForValue().get(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+merchantCode);
    	if(isExportData!=null){
    		//执行过导出excel
    		if(hashMap!=null){
    			modelMap.put("exportResult", hashMap.get("url"));
    			//缓存清除
        		redisTemplate.delete(CacheConstant.C_MERCHANT_EXPORT_BI_PRODANALYSE+":"+merchantCode);
        		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":createReportAnalysis:"+merchantCode);
        		redisTemplate.delete(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+merchantCode);
        		modelMap.put("isExportData", "loaded");
    		}else{
    			//正在执行导出
    			modelMap.put("isExportData", "loading");
    			modelMap.put("progress", progress);
    		}
    	}else{
    		//未执行导出
			modelMap.put("isExportData", "noenter");
			if(progress!=null){
				redisTemplate.delete(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+merchantCode);
			}
    	}
		
		return "/manage/report/report_management_survey";
	}
	
	/**
	 * 加载汇总指标数据
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadTotalIndexData")
	@ResponseBody
	public String loadTotalIndexData(ModelMap modelMap, HttpServletRequest request) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("merchantCode",YmcThreadLocalHolder.getMerchantCode());
		params.put("startDate",request.getParameter("startDate"));
		params.put("endDate",request.getParameter("endDate"));
		String[] index = request.getParameterValues("manageServeyIndex");
		params.put("manageServeyIndex", Arrays.asList(index));
		String isNew = request.getParameter("isNew");
		Map result = null;
		if(!StringUtils.isBlank(isNew) && "Y".equals(isNew)){
			result = reportManagementSurveyService.newCalculateSupplierTotalIndex(params);
		}else{
			result = reportManagementSurveyService.calculateSupplierTotalIndex(params);
		}
		JSONObject json = JSONObject.fromObject(result);
		return json.toString();
	}
	
	/**
	 * 加载汇总指标下折线图所需数据，即：每天或者每小时指标数据 ，以便进行折线图展示
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadEveryDayOrHourIndexData")
	@ResponseBody
	public String loadEveryDayOrHourIndexData(ModelMap modelMap, HttpServletRequest request) throws Exception {
		Map<String,Object> params = new HashMap<String,Object> ();
		params.put("startDate", request.getParameter("startDate"));
		params.put("endDate", request.getParameter("endDate"));
		params.put("merchantCode",YmcThreadLocalHolder.getMerchantCode());
		params.put("firstIndexName", request.getParameter("firstIndexName"));
		params.put("secondIndexName", request.getParameter("secondIndexName"));
		params.put("groupType", request.getParameter("groupType"));
		String isNew = request.getParameter("isNew");
		Map result = null;
		if(!StringUtils.isBlank(isNew) && "Y".equals(isNew)){
			result = reportManagementSurveyService.newQueryEveryDayOrHourIndex(params);
		}else{
			result = reportManagementSurveyService.queryEveryDayOrHourIndex(params);
		}
		JSONObject json = JSONObject.fromObject(result);
		String jsonStr = json.toString();
		return json.toString();
	}
	
	
	/*@RequestMapping("/insertSupplierServeyIndex")
	@ResponseBody
	public void insertSupplierServeyIndex(ModelMap modelMap, HttpServletRequest request) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		String startDateStr = request.getParameter("startDate");
		String endDateStr = request.getParameter("endDate");
		if(!StringUtils.isBlank(startDateStr) && !StringUtils.isBlank(endDateStr)){
			Date startDate = DateUtil.parseDate(startDateStr, "yyyy-MM-dd");
			Date endDate = DateUtil.parseDate(endDateStr, "yyyy-MM-dd");
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		reportManagementSurveyService.insertSupplierServeyIndex(params);

	}*/
	
	
	
	
	/**
	 * 商家中心数据报表-经营概况（首页）商品分析列表查询
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/prodAnalyseList")
	public String prodAnalyseList(ModelMap modelMap, HttpServletRequest request, Query query, CommodityAnalysisVo vo) throws Exception {
		try {
			if(StringUtils.isNotBlank(vo.getKeywords()) 
					&& "请输入商品名称或商品编码".equals(vo.getKeywords())){
				vo.setKeywords(null);
			}
			String merchantCode = YmcThreadLocalHolder.getMerchantCode();
			String loginName = YmcThreadLocalHolder.getOperater();
			vo.setMerchantCode(merchantCode);
			if(vo.getQueryStartDate()==null || vo.getQueryEndDate()==null){
				//按页面已有的时间跨度查询
				setQueryDate(vo);
			}else{
				vo.setQueryEndDate(DateUtil.getdate1(DateUtil.format1(vo.getQueryEndDate())+" 23:59:59"));
			}
			//查询该商家账号的展示的默认指标
			List<Map<String, Object>>  indexList = this.loadCommodityIndex(merchantCode,loginName);
			boolean flag = true,inIndexFlag = false;
			int tempDimension = 0;
			String indexName = null;
			//页面展示的列指标，如遇到非该维度下的指标，切换成系统默认指标
			//判断页面展示的排序列是否在该商家账号展示的默认指标里，如没有，排序列默认第一个
			for(Map<String,Object> indexMap : indexList){
				tempDimension = Integer.parseInt((String)indexMap.get("dimension"));
				indexName = (String)indexMap.get("en_name");
				if(tempDimension!=0 && tempDimension!=vo.getDimensions()){
					inIndexFlag = false;
					List<Map<String, Object>>  templateMap = reportTemplateService.queryDefaultTemplate();
					modelMap.put("columnIndex",templateMap);
					for(Map<String,Object> defalutMap : templateMap){
						indexName = (String)defalutMap.get("en_name");
						if(vo.getSortBy()!=null && 
								vo.getSortBy().toLowerCase().equals(indexName.toLowerCase())){
							//排序列在展示指标中
							inIndexFlag = true;
						}
					}
					flag = false;
					break;
				}
				if(vo.getSortBy()!=null && 
						vo.getSortBy().toLowerCase().equals(indexName.toLowerCase())){
					//排序列在展示指标中
					inIndexFlag = true;
				}
			}
			if(flag){
				modelMap.put("columnIndex",this.loadCommodityIndex(merchantCode,loginName));
			}
			if(!inIndexFlag){
				vo.setSortBy((String)((List<Map<String, Object>>)modelMap.get("columnIndex")).get(0).get("en_name"));
			}
			Map<String,Object> sumMap = reportManagementSurveyService.querySumOfCommodityAnalysis(vo);
			PageFinder<Map<String,Object>> pageFinder = 
					reportManagementSurveyService.queryCommodityAnalysisList(query,vo);
			modelMap.put("sumData", sumMap);
			modelMap.put("pageFinder", pageFinder);
		} catch (Exception e) {
			logger.error("商家编码：{},商品分析商品维度查询发生系统错误",YmcThreadLocalHolder.getMerchantCode(),e);
		}
		modelMap.put("loadUrl","/report/prodAnalyseList.sc");
		modelMap.put("commodityAnalysisVo", vo);
		modelMap.put("class",request.getParameter("class"));
		return "/manage/report/prodAnalyseList";
	}
	

	/**
	 * 商家中心数据报表-经营概况（首页）品类分析列表查询
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/catagoryAnalyseList")
	public String catagoryAnalyseList(Query query,ModelMap modelMap, HttpServletRequest request,
			CommodityAnalysisVo vo) throws Exception {
		try {
			if(StringUtils.isNotBlank(vo.getKeywords()) 
					&& "请输入分类名称关键词".equals(vo.getKeywords())){
				vo.setKeywords(null);
			}
			String merchantCode = YmcThreadLocalHolder.getMerchantCode();
			String loginName = YmcThreadLocalHolder.getOperater();
			vo.setMerchantCode(merchantCode);
			if(vo.getQueryStartDate()==null || vo.getQueryEndDate()==null){
				//按页面已有的时间跨度查询
				setQueryDate(vo);
			}else{
				vo.setQueryEndDate(DateUtil.getdate1(DateUtil.format1(vo.getQueryEndDate())+" 23:59:59"));
			}
			//页面展示的列指标，如遇到非该维度下的指标，切换成系统默认指标
			List<Map<String, Object>>  indexList = this.loadCommodityIndex(merchantCode,loginName);
			boolean flag = true,inIndexFlag = false;;
			int tempDimension = 0;
			String indexName = null;
			for(Map<String,Object> indexMap : indexList){
				tempDimension = Integer.parseInt((String)indexMap.get("dimension"));
				indexName = (String)indexMap.get("en_name");
				if(tempDimension!=0 && tempDimension!=vo.getDimensions()){
					List<Map<String, Object>>  templateMap = reportTemplateService.queryDefaultTemplate();
					modelMap.put("columnIndex",templateMap);
					inIndexFlag = false;
					for(Map<String,Object> defalutMap : templateMap){
						indexName = (String)defalutMap.get("en_name");
						if(vo.getSortBy()!=null && 
								vo.getSortBy().toLowerCase().equals(indexName.toLowerCase())){
							//排序列在展示指标中
							inIndexFlag = true;
						}
					}
					flag = false;
					break;
				}
				if(vo.getSortBy()!=null && 
						vo.getSortBy().toLowerCase().equals(indexName.toLowerCase())){
					//排序列在展示指标中
					inIndexFlag = true;
				}
			}
			if(flag){
				modelMap.put("columnIndex",this.loadCommodityIndex(merchantCode,loginName));
			}
			if(!inIndexFlag){
				vo.setSortBy((String)((List<Map<String, Object>>)modelMap.get("columnIndex")).get(0).get("en_name"));
			}
			Map<String,Object> sumMap = reportManagementSurveyService.querySumOfCatagoryAnalysis(vo);
			PageFinder<Map<String,Object>> pageFinder = 
				reportManagementSurveyService.queryCategoryAnalysisList(query,vo);
			modelMap.put("sumData", sumMap);
			modelMap.put("pageFinder", pageFinder);
		} catch (Exception e) {
			logger.error("商家编码：{},商品分析分类维度查询发生系统错误",YmcThreadLocalHolder.getMerchantCode(),e);
		}
		modelMap.put("loadUrl","/report/catagoryAnalyseList.sc");
		modelMap.put("commodityAnalysisVo", vo);
		modelMap.put("class",request.getParameter("class"));
		return "/manage/report/catagoryAnalyseList";
	}
	
	/**
	 * 商家中心数据报表-经营概况（首页）品类分析子列表查询
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/catagoryAnalyseSubList")
	@ResponseBody
	public String catagoryAnalyseSubList(CommodityAnalysisVo vo,Query query) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String loginName = YmcThreadLocalHolder.getOperater();
		vo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		if(vo.getQueryStartDate()==null || vo.getQueryEndDate()==null){
			//按页面已有的时间跨度查询
			setQueryDate(vo);
		}
		vo.setSecondCategory(vo.getSecondCategory().substring(vo.getSecondCategory().indexOf("_")+1));
		List<Map<String,Object>> catagoryAnalyseSubList = 
				reportManagementSurveyService.queryCategoryAnalysisListByCategoryStruct(vo);
		try {
			//页面展示的列指标，如遇到非该维度下的指标，切换成系统默认指标
			List<Map<String, Object>>  indexList = this.loadCommodityIndex(merchantCode,loginName);
			boolean flag = true;
			int tempDimension = 0;
			for(Map<String,Object> indexMap : indexList){
				tempDimension = Integer.parseInt((String)indexMap.get("dimension"));
				if(tempDimension!=0 && tempDimension!=vo.getDimensions()){
					map.put("columnIndex",reportTemplateService.queryDefaultTemplate());
					flag = false;
					break;
				}
			}
			if(flag){
				map.put("columnIndex",this.loadCommodityIndex(merchantCode,loginName));
			}
		} catch (Exception e) {
			logger.error("商家编码：{},商品分析分类维度二级分类查询发生系统错误",YmcThreadLocalHolder.getMerchantCode(),e);
		}
		// 首页经营概况-商品分析子列表
		map.put("id", vo.getSecondCategory());
		map.put("child",catagoryAnalyseSubList);
		return JSONObject.fromObject(map).toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/createReportAnalysis",method = RequestMethod.POST)
	public String createReportAnalysis(final HttpServletRequest request,
			final HttpServletResponse response,final CommodityAnalysisVo vo){
		final String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		final double progress = 5.0d;
		Map<String,Object> hashMap = (Map<String,Object>)redisTemplate.opsForValue().get(CacheConstant.C_MERCHANT_EXPORT_BI_PRODANALYSE+":"+merchantCode);
    	String isExportData = (String)redisTemplate.opsForValue().get(CacheConstant.C_USER_ENTER_METHOD+":createReportAnalysis:"+merchantCode);
		if(isExportData!=null){
    		//执行过导出excel
    		if(hashMap==null){
    			//未执行完
    			return "loading";
    		}
		}
		vo.setMerchantCode(merchantCode);
		if(vo.getQueryStartDate()==null || vo.getQueryEndDate()==null){
			//按页面已有的时间跨度查询
			setQueryDate(vo);
		}
		ExecutorService threadPool = null;
		try{
			// updated by zhangfeng 2016.5.22 直接异步线程处理，不用每次创建单个线程池处理	
			/*threadPool = Executors.newFixedThreadPool(1);// 线程池
			threadPool.execute(new Runnable() {
				Map<String,Object> hashMap = null;
				//进度值默认起始值
				@Override
				public void run() {
					try {
						hashMap = exportReportAnalysis(request,response,vo,progress);
						redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+merchantCode, 100.0d);
					} catch (Exception e) {
						hashMap = null;
						logger.error("商家编码：{},导出数据报表之商品分析出现错误：",YmcThreadLocalHolder.getMerchantCode(),e);
						//删除缓存，不再进行处理
						redisTemplate.delete(CacheConstant.C_MERCHANT_EXPORT_BI_PRODANALYSE+":"+merchantCode);
			    		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":createReportAnalysis:"+merchantCode);
						redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+merchantCode, -1.0d);
					}finally{
						redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_EXPORT_BI_PRODANALYSE+":"+merchantCode, hashMap);
					}
				}
			});*/
			Runnable run = new Runnable() {
				Map<String,Object> hashMap = null;
				//进度值默认起始值
				@Override
				public void run() {
					try {
						logger.warn("线程{}，开始处理数据报表商品或品类分析导出....",Thread.currentThread().getName());
						hashMap = exportReportAnalysis(request,response,vo,progress);
						redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+merchantCode, 100.0d);
						logger.warn("线程{}，结束处理数据报表商品或品类分析导出....",Thread.currentThread().getName());
					} catch (Exception e) {
						hashMap = null;
						logger.error("商家编码：{},导出数据报表之商品分析出现错误：",YmcThreadLocalHolder.getMerchantCode(),e);
						//删除缓存，不再进行处理
						redisTemplate.delete(CacheConstant.C_MERCHANT_EXPORT_BI_PRODANALYSE+":"+merchantCode);
			    		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":createReportAnalysis:"+merchantCode);
						redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+merchantCode, -1.0d);
					}finally{
						redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_EXPORT_BI_PRODANALYSE+":"+merchantCode, hashMap);
					}
				}
			};
			Thread thread = new Thread(run);
			thread.setName("createReportAnalysis 数据报表首页商品或品类分析列表导出");
			thread.start();
			//缓存进度值
			redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+merchantCode, progress);
			//表示已进入该方法，设置过安全库存
		    redisTemplate.opsForValue().set(CacheConstant.C_USER_ENTER_METHOD+":createReportAnalysis:"+merchantCode,"enter");
		    redisTemplate.expire(CacheConstant.C_USER_ENTER_METHOD+":createReportAnalysis:"+merchantCode, 1, TimeUnit.HOURS);
		    //60分钟之内没有下载完，就失效
		    redisTemplate.expire(CacheConstant.C_MERCHANT_EXPORT_BI_PRODANALYSE+":"+merchantCode, 1, TimeUnit.HOURS);
		    redisTemplate.expire(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+merchantCode, 1, TimeUnit.HOURS);
		}catch(Exception e){
			logger.error("商家编码：{},导出报表发生错误！",YmcThreadLocalHolder.getMerchantCode(),e);
		}finally{
			if (threadPool != null) {
				threadPool.shutdown();
				threadPool = null;
			}
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("/getExportResult")
	public String getExportResult(){
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		Map<String,Object> hashMap = (Map<String,Object>)redisTemplate.opsForValue().get(CacheConstant.C_MERCHANT_EXPORT_BI_PRODANALYSE+":"+merchantCode);
		Double progress = (Double)redisTemplate.opsForValue().get(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+merchantCode);
		if(hashMap!=null){
			//已经生成excel，可供下载
			hashMap.put("result", "true");
			//删除缓存
			redisTemplate.delete(CacheConstant.C_MERCHANT_EXPORT_BI_PRODANALYSE+":"+merchantCode);
    		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":createReportAnalysis:"+merchantCode);
    		redisTemplate.delete(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+merchantCode);
		}else{
			hashMap = new HashMap<String,Object>();
			hashMap.put("result", "false");
		}
		hashMap.put("progress", progress);
		return JSONObject.fromObject(hashMap).toString();
	}
	
	
	@RequestMapping("/reportAnalysisDownload")
	public void reportAnalysisDownload(@RequestParam String name, 
			HttpServletRequest request, HttpServletResponse response){
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		OutputStream outputStream = null;
		try{
			response.reset();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", MessageFormat.format("attachment; filename={0}", encodeDownloadAttachmentFilename(request, "数据报表_"+name)));
			outputStream = response.getOutputStream();
			FileFtpUtil ftpUtil = new FileFtpUtil(commoditySettings.imageFtpServer,commoditySettings.imageFtpPort, 
					commoditySettings.imageFtpUsername, commoditySettings.imageFtpPassword);
			ftpUtil.login();
			ftpUtil.downRemoteFile("/merchantpics/exceltemp/"+merchantCode+"/reportAnalysis_"+name,outputStream);
			outputStream.flush();
			ftpUtil.logout();
		}catch(Exception e){
			logger.error("商家编码：{},下载失败！",YmcThreadLocalHolder.getMerchantCode(),e);
		}finally{
			if(outputStream!=null){
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error("商家编码：{},IO流关闭异常！",YmcThreadLocalHolder.getMerchantCode(),e);
				}
			}
		}
	}
	
	/**
	 * loadAnalysisIndex:根据维度价值商品分析指标 与用户的模板
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-8-25 下午6:09:08
	 */
	
	@RequestMapping("/loadAnalysisIndex")
	public String loadAnalysisIndex(String dimension,ModelMap map){
		//查询商品分析的所有指标
		List<AnalysisIndex> voList = reportTemplateService.loadAnalysisIndex();
		List<Map<String, Object>> templateIndexList = null;
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String loginName = YmcThreadLocalHolder.getOperater();
		List<Map<String,Object>> templateList = null;
		try {
			//查询该商家的默认指标，需要勾选上
			templateIndexList = loadCommodityIndex(merchantCode,loginName);
			//查询商家账号所有的模板列表
			templateList = reportTemplateService.queryReportTemplate(merchantCode, loginName);
			map.put("templateList",templateList);
			map.put("templateIndexList", templateIndexList);
			map.put("dimension", dimension);
		} catch (Exception e) {
			logger.error("商家编码：{},获取用户的指标发生系统错误",YmcThreadLocalHolder.getMerchantCode(),e);
		}
		map.put("analysisIndexList", voList);
		return "/manage_unless/report/template_index";
	}
	
	/**
	 * 编码中文文件名称(解决下载文件弹出窗体中文乱码问题)
	 * 
	 * @param request
	 * @param filename
	 * @return String
	 * @throws Exception
	 */
	private String encodeDownloadAttachmentFilename(HttpServletRequest request, String filename) throws Exception {
		return StringUtils.indexOf(request.getHeader("User-Agent"), "MSIE") != -1 ? URLEncoder.encode(filename, "UTF-8") : new String(filename.getBytes("UTF-8"), "ISO-8859-1");
	}
	
	/**
	 * 导出数据报表
	 * @param request
	 * @param response
	 * @param commodityQueryVo
	 * @param progress 进度值
	 * @throws Exception
	 */
	private Map<String,Object> exportReportAnalysis(HttpServletRequest request, 
			HttpServletResponse response, 
			CommodityAnalysisVo vo,
			double progress) throws Exception {
		if(StringUtils.isNotBlank(vo.getKeywords()) 
				&& ("请输入分类名称关键词".equals(vo.getKeywords()) 
						|| "请输入商品名称或商品编码".equals(vo.getKeywords()))){
			vo.setKeywords(null);
		}
		List<Map<String,Object>> resultMapList = null;
		SXSSFWorkbook workbook = new SXSSFWorkbook(500);
		Sheet sheet = workbook.createSheet("分析列表");
		int rowInd = 0, colInd = 0;
		Row row = sheet.createRow(rowInd++);
		numberFormat.setMaximumIntegerDigits(3);
		numberFormat.setMaximumFractionDigits(2);
		progress = progress +5.0d;
		redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+vo.getMerchantCode(), progress);
		if(vo.getDimensions()==1){
			//商品分析导出
			resultMapList = reportManagementSurveyService.queryExportCommodityAnalysisList(vo);
			progress = progress +5.0d;
			redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+vo.getMerchantCode(), progress);
		}else{
			//品类分析导出
			resultMapList = reportManagementSurveyService.queryExportCategoryAnalysisList(vo);
			progress = progress +5.0d;
			redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+vo.getMerchantCode(), progress);
		}
		toExcel(resultMapList, row, vo.getDimensions(), colInd, sheet, rowInd, progress, vo.getMerchantCode(),workbook);
		//缓存进度值
		String dateStr = DateUtil2.getCurrentDateTimeToStr();
		File file = new File(this.getAbsoluteFilepath(vo.getMerchantCode()));
		if(!file.exists()){
			file.mkdirs();
		}
		FileOutputStream outputStream = null;
		try{
			outputStream = new FileOutputStream(this.getAbsoluteFilepath(vo.getMerchantCode())+
					"reportAnalysis_"+dateStr+".xlsx");
			workbook.write(outputStream);
			outputStream.flush();
			progress = progress+10.0d;
			redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+vo.getMerchantCode(), progress);
		}catch(Exception e){
			logger.error("商家编码：{},IO异常，导出失败！",YmcThreadLocalHolder.getMerchantCode(),e);
		}finally{
			if(outputStream!=null){
				try {
					outputStream.close();
				}catch(Exception e){
					logger.error("商家编码：{},IO异常，导出失败！",YmcThreadLocalHolder.getMerchantCode(),e);
				}
			}
		}
		Map<String,Object> hashMap = new HashMap<String,Object>();
		File excelFile = new File(this.getAbsoluteFilepath(vo.getMerchantCode())+"reportAnalysis_"+dateStr+".xlsx");
		boolean result = imageService.ftpUpload(excelFile,"/merchantpics/exceltemp/"+vo.getMerchantCode());
    	hashMap.put("result", result);
    	hashMap.put("url",dateStr+".xlsx");
    	progress = progress+5.0;
    	redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+vo.getMerchantCode(), progress);
    	return hashMap;
	}
	
	/** 
	 * getAbsoluteFilepath:得到绝对路径
	 * @author li.n1 
	 * @param merchantCode
	 * @return 
	 * @since JDK 1.6 
	 */  
	private String getAbsoluteFilepath(String merchantCode) {
		return new StringBuffer(commoditySettings.picDir)
		.append(File.separator)
		.append("exceltemp")
		.append(File.separator)
		.append(merchantCode)
		.append(File.separator)
		.toString();
	}
	
	public static void main(String[] args) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.addDate(date, -5));
		System.out.println(cal.getTimeInMillis());
		cal.setTime(DateUtil.addDate(date, -4));
		System.out.println(cal.getTimeInMillis());
		cal.setTime(DateUtil.addDate(date, -3));
		System.out.println(cal.getTimeInMillis());
		cal.setTime(DateUtil.addDate(date, -2));
		System.out.println(cal.getTimeInMillis());
		cal.setTime(DateUtil.addDate(date, -1));
		System.out.println(cal.getTimeInMillis());

		System.out.println(cal.getTimeInMillis());
	}
	
	private void setQueryDate(CommodityAnalysisVo vo){
		if(vo.getDateCondition()==2){
			//最近7天
			vo.setQueryStartDate(DateUtil.getdate1(DateUtil.format1(DateUtil.addDaysToDate(new Date(),-7))));
			vo.setQueryEndDate(DateUtil.getdate1(DateUtil.format1(DateUtil.addDaysToDate(new Date(),-1))+" 23:59:59"));
		}else if(vo.getDateCondition()==3){
			//最近30天
			vo.setQueryStartDate(DateUtil.getdate1(DateUtil.format1(DateUtil.addDaysToDate(new Date(),-30))));
			vo.setQueryEndDate(DateUtil.getdate1(DateUtil.format1(DateUtil.addDaysToDate(new Date(),-1))+" 23:59:59"));
		}else{
			//昨天
			vo.setQueryStartDate(DateUtil.getdate1(DateUtil.format1(DateUtil.addDaysToDate(new Date(),-1))));
			vo.setQueryEndDate(DateUtil.getdate1(DateUtil.format1(DateUtil.addDaysToDate(new Date(),-1))+" 23:59:59"));
			vo.setDateCondition(1);
		}
	}
	
	/**
	 * toExcel:封装导出 
	 * @author li.n1 
	 * @param resultMapList 数据
	 * @param row
	 * @param dimensions
	 * @param colInd
	 * @param sheet
	 * @param rowInd
	 * @param progress 进度值
	 * @since JDK 1.6 
	 * @date 2015-8-18 下午6:05:57
	 */
	private void toExcel(List<Map<String,Object>> resultMapList, 
			Row row, 
			int dimensions, 
			int colInd,
			Sheet sheet,
			int rowInd,
			double progress,
			String merchantCode,
			SXSSFWorkbook workbook) throws Exception {
		double tempProgress = 0;
		if(dimensions==1){
			row.createCell(colInd++).setCellValue("商品编码");
			row.createCell(colInd++).setCellValue("商品名称");
			row.createCell(colInd++).setCellValue("商品访次");
			row.createCell(colInd++).setCellValue("商品浏览量");
			row.createCell(colInd++).setCellValue("单品转化率");
			row.createCell(colInd++).setCellValue("收订件数");
			row.createCell(colInd++).setCellValue("收订金额");
			row.createCell(colInd++).setCellValue("支付件数");
			row.createCell(colInd++).setCellValue("支付金额");
			row.createCell(colInd++).setCellValue("发货件数");
			row.createCell(colInd++).setCellValue("发货金额");
			row.createCell(colInd++).setCellValue("发货率");
			row.createCell(colInd++).setCellValue("可售库存");
			row.createCell(colInd++).setCellValue("收订均价");
			row.createCell(colInd++).setCellValue("支付均价");
			row.createCell(colInd++).setCellValue("发货均价");
			row.createCell(colInd++).setCellValue("评论次数");
			row.createCell(colInd++).setCellValue("加车次数");
			row.createCell(colInd++).setCellValue("退货拒收数");
			row.createCell(colInd++).setCellValue("退货拒收额");
			row.createCell(colInd++).setCellValue("退货拒收率");
			row.createCell(colInd++).setCellValue("持续零收订天数");
			row.createCell(colInd++).setCellValue("上架天数");
			
			for (Map<String,Object> map : resultMapList) {
					colInd = 0;
					tempProgress = new BigDecimal((rowInd*65.0/resultMapList.size()))
										.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					row = sheet.createRow(rowInd);
					//商品编码
					row.createCell(colInd++).setCellValue(MapUtils.getString(map, "COMMODITY_NO", ""));
					//商品名称
					row.createCell(colInd++).setCellValue(MapUtils.getString(map, "COMMODITY_NAME", ""));
					//商品访次
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "visit_count", 0));
					//商品浏览量
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "page_view", 0));
					//单品转化率
					row.createCell(colInd++).setCellValue(numberFormat.format(MapUtils.getDoubleValue(map, "converse_rate", 0)));
					//收订件数
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "sale_commodity_num", 0));
					//收订金额
					row.createCell(colInd++).setCellValue(df.format(MapUtils.getDoubleValue(map, "sale_total_amount", 0)));
					//支付件数
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "commodity_Num", 0));
					//支付金额
					row.createCell(colInd++).setCellValue(df.format(MapUtils.getDoubleValue(map, "prod_total_amt", 0)));
					//发货件数
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "send_commodity_num", 0));
					//发货金额
					row.createCell(colInd++).setCellValue(df.format(MapUtils.getDoubleValue(map, "send_total_amount", 0)));
					//发货率
					row.createCell(colInd++).setCellValue(numberFormat.format(MapUtils.getDoubleValue(map, "send_rate", 0)));
					//库存
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "store_num", 0));
					//收订均价
					row.createCell(colInd++).setCellValue(df.format(MapUtils.getDoubleValue(map, "sale_commodity_avg", 0)));
					//支付均价
					row.createCell(colInd++).setCellValue(df.format(MapUtils.getDoubleValue(map, "pay_commodity_avg", 0)));
					//发货均价
					row.createCell(colInd++).setCellValue(df.format(MapUtils.getDoubleValue(map, "send_commodity_avg", 0)));
					//评论次数
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "commodity_times", 0));
					//加车次数
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "colected_num", 0));
					//退货拒收数
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "reject_commodity_num", 0));
					//退货拒收额
					row.createCell(colInd++).setCellValue(df.format(MapUtils.getDoubleValue(map, "reject_commodity_amt", 0)));
					//退货拒收率
					row.createCell(colInd++).setCellValue(numberFormat.format(MapUtils.getDoubleValue(map, "reject_rate", 0)));
					//持续零收订天数
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "no_sale_day", 0));
					//上架天数
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "online_day", 0));
					rowInd++;
					redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+merchantCode, 
							new BigDecimal(progress+tempProgress).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
			}
		}else{
			row.createCell(colInd++).setCellValue("商品分类");
			row.createCell(colInd++).setCellValue("商品访次");
			row.createCell(colInd++).setCellValue("商品浏览量");
			row.createCell(colInd++).setCellValue("品类转化率");
			row.createCell(colInd++).setCellValue("收订件数");
			row.createCell(colInd++).setCellValue("收订金额");
			row.createCell(colInd++).setCellValue("支付件数");
			row.createCell(colInd++).setCellValue("支付金额");
			row.createCell(colInd++).setCellValue("发货件数");
			row.createCell(colInd++).setCellValue("发货金额");
			row.createCell(colInd++).setCellValue("发货率");
			row.createCell(colInd++).setCellValue("可售库存");
			row.createCell(colInd++).setCellValue("评论次数");
			row.createCell(colInd++).setCellValue("加车次数");
			row.createCell(colInd++).setCellValue("退货拒收数");
			row.createCell(colInd++).setCellValue("退货拒收额");
			row.createCell(colInd++).setCellValue("退货拒收率");
			row.createCell(colInd++).setCellValue("新上架商品数");
			for (Map<String,Object> map : resultMapList) {
					colInd = 0;
					tempProgress = new BigDecimal((rowInd*65.0/resultMapList.size()))
										.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					row = sheet.createRow(rowInd++);
					//商品分类
					row.createCell(colInd++).setCellValue(MapUtils.getString(map, "CATNAME", ""));
					//商品访次
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "visit_count", 0));
					//商品浏览量
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "page_view", 0));
					//品类转化率
					row.createCell(colInd++).setCellValue(numberFormat.format(MapUtils.getDoubleValue(map, "converse_rate", 0)));
					//收订件数
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "sale_commodity_num", 0));
					//收订金额
					row.createCell(colInd++).setCellValue(df.format(MapUtils.getDoubleValue(map, "sale_total_amount", 0)));
					//支付件数
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "commodity_num", 0));
					//支付金额
					row.createCell(colInd++).setCellValue(df.format(MapUtils.getDoubleValue(map, "prod_total_amt", 0)));
					//发货件数
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "send_commodity_num", 0));
					//发货金额
					row.createCell(colInd++).setCellValue(df.format(MapUtils.getDoubleValue(map, "send_total_amount", 0)));
					//发货率
					row.createCell(colInd++).setCellValue(numberFormat.format(MapUtils.getDoubleValue(map, "send_rate", 0)));
					//可售库存
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "store_num", 0));
					//评论次数
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "commodity_times", 0));
					//加车次数
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "colected_num", 0));
					//退货拒收数
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "reject_commodity_num", 0));
					//退货拒收额
					row.createCell(colInd++).setCellValue(df.format(MapUtils.getDoubleValue(map, "reject_commodity_amt", 0)));
					//退货拒收率
					row.createCell(colInd++).setCellValue(numberFormat.format(MapUtils.getDoubleValue(map, "reject_rate", 0)));
					//新上架商品数
					row.createCell(colInd++).setCellValue(MapUtils.getLongValue(map, "new_commodity_count", 0));
					
					redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_EXPORT_BI_PROGRESS_BAR_KEY+":"+merchantCode, 
							new BigDecimal(progress+tempProgress).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
			}
		}
	}
	
	private List<Map<String,Object>> loadCommodityIndex(String merchantCode,String loginName) throws Exception{
		//加载用户模板指标
		//查询用户设置的预置模板，如未设置，使用系统自带的预置模板指标
		//先查询缓存，没有则查数据库
		//redisTemplate.delete(CacheConstant.C_MERCHANT_BI_USER_DEFAULT_TEMPLATE_INDEX+":"+merchantCode+":"+loginName);
		List<Map<String,Object>> defaultReportList = 
				(List<Map<String,Object>>)redisTemplate.opsForValue()
					.get(CacheConstant.C_MERCHANT_BI_USER_DEFAULT_TEMPLATE_INDEX+":"+merchantCode+":"+loginName);
		if(CollectionUtils.isEmpty(defaultReportList)){
			defaultReportList = reportTemplateService
					.queryUserDefaultReportTemplate(merchantCode, loginName);
			//用户尚未新增模板、或者设置的是系统自带的默认指标
			if(CollectionUtils.isEmpty(defaultReportList)){
				defaultReportList = reportTemplateService.queryDefaultTemplate();
			}
			redisTemplate.opsForValue()
			.set(CacheConstant.C_MERCHANT_BI_USER_DEFAULT_TEMPLATE_INDEX+":"+merchantCode+":"+loginName, 
					defaultReportList, 10, TimeUnit.MINUTES);
		}
		
		return defaultReportList;
	}
	
	@ResponseBody
	@RequestMapping("/saveTemplate")
	public String saveAnalysisTemplate(String name,String ids){
		Map<String,Object> params = new HashMap<String,Object>();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String loginName = YmcThreadLocalHolder.getOperater();
		String uuid = UUIDGenerator.getUUID();
		params.put("template_id",uuid);
		params.put("name", name);
		params.put("merchant_code", merchantCode);
		params.put("login_name", loginName);
		List<String> indexList = new ArrayList<String>();
		String[] idArr = ids.split(",");
		for(String id : idArr){
			indexList.add(id);
		}
		params.put("indexList", indexList);
		boolean flag = true;
		try {
			flag = reportTemplateService.addReportTemplate(params);
		} catch (Exception e) {
			logger.error("商家编码：{},新增模板报错：",YmcThreadLocalHolder.getMerchantCode(),e);
			flag = false;
		}
		JSONObject object = new JSONObject();
		if(!flag){
			object.put("result", "fail");
			return object.toString();
		}
		object.put("templateId", uuid);
		object.put("result", "success");
		return object.toString();
	}
	
	
	/**
	 * updateAnalysisTemplate:更新模板
	 * @author li.n1 
	 * @param id 模板id
	 * @param ids 指标ids
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-8-31 下午2:26:21
	 */
	@ResponseBody
	@RequestMapping("/updateTemplate")
	public String updateAnalysisTemplate(String id,String ids){
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String loginName = YmcThreadLocalHolder.getOperater();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("template_id", id);
		List<String> indexList = new ArrayList<String>();
		String[] idArr = ids.split(",");
		for(String idStr : idArr){
			indexList.add(idStr);
		}
		params.put("indexList", indexList);
		boolean flag = true;
		try {
			flag = reportTemplateService.updateReportTemplate(params);
		} catch (Exception e) {
			flag = false;
			logger.error("商家编码：{},更新模板报错：",YmcThreadLocalHolder.getMerchantCode(),e);
		}
		if(!flag){
			return "fail";
		}
		//删除默认指标的redis缓存
		redisTemplate.delete(CacheConstant.C_MERCHANT_BI_USER_DEFAULT_TEMPLATE_INDEX+":"+merchantCode+":"+loginName);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("/setDefaultTemplate")
	public String setDefaultTemplate(String id){
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String loginName = YmcThreadLocalHolder.getOperater();
		boolean flag = true;
		try {
			flag = reportTemplateService.setDefaultTemplate(merchantCode,loginName,id);
		} catch (Exception e) {
			logger.error("商家编码：{},设置用户默认模板发生错误",YmcThreadLocalHolder.getMerchantCode(),e);
			flag = false;
		}
		if(!flag){
			return "fail";
		}
		//删除默认指标的redis缓存
		redisTemplate.delete(CacheConstant.C_MERCHANT_BI_USER_DEFAULT_TEMPLATE_INDEX+":"+merchantCode+":"+loginName);
		return "success";
	}
	
	/**
	 * delAnalysisTemplate:删除模板 
	 * @author li.n1 
	 * @param id 模板id
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-8-31 下午2:26:33
	 */
	@ResponseBody
	@RequestMapping("/delTemplate")
	public String delAnalysisTemplate(String id){
		boolean flag = true;
		try {
			flag = reportTemplateService.deleteReportTemplate(id);
		} catch (Exception e) {
			logger.error("商家编码：{},删除模板报错：",YmcThreadLocalHolder.getMerchantCode(),e);
			flag = false;
		}
		if(!flag){
			return "fail";
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("/loadTemplateIndex")
	public String loadTemplateIndex(String id){
		List<Map<String,Object>> indexList = null;
		JSONObject object = new JSONObject();
		//是0  查找系统默认模板
		if(!("0".equals(id))){
			indexList = reportTemplateService.loadIndexByTemplateId(id);
		}else{
			indexList = reportTemplateService.queryDefaultTemplate();
		}
		object.put("indexList", indexList);
		return object.toString();
	}

}
