package com.yougou.kaidian.bi.web;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.kaidian.bi.service.IReportRealTimeStatisticsService;
import com.yougou.kaidian.bi.vo.CommoditySaleRank;
import com.yougou.kaidian.bi.vo.RealTimeStatisticsVo;
import com.yougou.kaidian.commodity.component.CommodityComponent;
import com.yougou.kaidian.commodity.service.ICommodityService;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.commodity.pojo.Cat;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.tools.common.utils.DateUtil;

/**
 * 数据智慧-实时数据
 * @author li.j1
 */

@Controller
public class ReportRealTimeStatisticsController {
	
	@Resource
	private ICommodityService commodityService;
	
	@Resource
	private IReportRealTimeStatisticsService reportRealTimeStatisticsService;
	
	@Resource
	private CommodityComponent commodityComponet;
	
	/**
	 * 
	 * @param realTimeStatisticsVo
	 * @param modelMap ModelMap
	 * @param request HttpServletRequest
	 * @return ModelAndView 
	 * @throws Exception 所有异常
	 */
	@RequestMapping("report/reportRealTimeStatistics")
	public ModelAndView querySummaryOfOperations(RealTimeStatisticsVo realTimeStatisticsVo, ModelMap modelMap, Query query, HttpServletRequest request) throws Exception {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		//List<Cat> lstCat = commodityService.queryCatList(merchantCode, "");
		//realTimeStatisticsVo.setStructName(getCatStructName(realTimeStatisticsVo));
		
		//当前时间
		realTimeStatisticsVo.setCurrentTime(DateUtil.getDateTime(new Date(System.currentTimeMillis())));
		
		//设置当前支付总数据
		initCurrentPayedInfo(realTimeStatisticsVo);
		commodityComponet.initBrand(modelMap, request);
		//modelMap.put("lstCat", lstCat);
		modelMap.put("realTimeStatisticsVo", realTimeStatisticsVo);
		return new ModelAndView("manage/report/report_real_time_statistics", modelMap);
	}
	
	@RequestMapping("report/loadCommoditySaleRankPage")
	public ModelAndView loadCommoditySaleRankPage(RealTimeStatisticsVo realTimeStatisticsVo, ModelMap modelMap, Query query, HttpServletRequest request) throws Exception {
		//初始化
		query.setPageSize(5);
		PageFinder<CommoditySaleRank> pageFinder = reportRealTimeStatisticsService.querycommoditySaleRanks(realTimeStatisticsVo, query);
		modelMap.put("pageFinder", pageFinder);
		//从起始时间开始，直到结束日期，最近7天含当天
		String[] lastServenDays = new String[7];
		Date date = new Date();
		for(int i=1;i<=lastServenDays.length;i++){
			lastServenDays[lastServenDays.length-i]=DateUtil.formatDate(DateUtil.addDate(date, -(i-1)),"yyyy-MM-dd");
		}
		JSONArray json = JSONArray.fromObject(lastServenDays);
		String jsonStr = json.toString();
		modelMap.put("lastServenDays", jsonStr);
		return new ModelAndView("manage/report/commodity_sale_rank", modelMap);
	}

	/**
	 * 设置当前支付总数据
	 * @param realTimeStatisticsVo 实时数据对象
	 */
	private void initCurrentPayedInfo(RealTimeStatisticsVo realTimeStatisticsVo) throws Exception{
		
		reportRealTimeStatisticsService.initOrderInfo(realTimeStatisticsVo);
		
	}
	
	
}
