package com.yougou.kaidian.bi.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.kaidian.bi.service.IReportSingleAnalysisService;
import com.yougou.kaidian.bi.vo.CommoditySaleRank;
import com.yougou.kaidian.bi.vo.RealTimeStatisticsVo;
import com.yougou.kaidian.bi.vo.SingleAnalysisVo;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.tools.common.utils.DateUtil;

/**
 * 数据报表单品分析
 * @author zhang.f1
 *
 */
@Controller
public class ReportSingleAnalysisController {
	
	@Resource
	private IReportSingleAnalysisService reportSingleAnalysisService ;
	/**
	 * 数据报表单品分析，加载商品图片，右侧信息以及下方的商品事件列表和商品尺码列表
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("report/singleCommodityAnalysis")
	public ModelAndView loadCommoditySaleRankPage(ModelMap modelMap,HttpServletRequest request) throws Exception {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String commodityNo = request.getParameter("commodityNo");
		SingleAnalysisVo singleAnalysisVo = reportSingleAnalysisService.queryCommodityInfo(merchantCode, commodityNo);
		List<Map<String,Object>> loginfoList = reportSingleAnalysisService.queryCommodityLoginfoList(merchantCode, commodityNo);
		List<Map<String,Object>> sizeList = reportSingleAnalysisService.queryCommoditySizeList(merchantCode, commodityNo);
		List<Map<String,Object>> indexList = reportSingleAnalysisService.querySingleAnalysisIndex();
		int favoriteCount = reportSingleAnalysisService.queryFavoriteCountByCommodityNo(commodityNo);
		singleAnalysisVo.setFavoriteCount(favoriteCount);
		
		//默认查询7天内的数据，时间查询条件带入到页面，以便页面异步查询
		Date date = new Date();
		modelMap.put("startDate", DateUtil.formatDate(DateUtil.addDate(date, -7), "yyyy-MM-dd"));
		modelMap.put("endDate", DateUtil.formatDate(DateUtil.addDate(date, -1), "yyyy-MM-dd"));
		modelMap.put("singleAnalysisVo", singleAnalysisVo);
		modelMap.put("loginfoList", loginfoList);
		modelMap.put("sizeList", sizeList);
		modelMap.put("indexList", indexList);
		return new ModelAndView("manage/report/singleAnalysis", modelMap);
	}
	
	/**
	 * 数据报表单品分析，加载商品图片，右侧信息以及下方的商品事件列表和商品尺码列表
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("report/loadSingleCommodityEveryDayIndex")
	@ResponseBody
	public String loadSingleCommodityEveryDayIndex(ModelMap modelMap,HttpServletRequest request) throws Exception {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String commodityNo = request.getParameter("commodityNo");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String firstIndexName = request.getParameter("firstIndexName");
		String secondIndexName = request.getParameter("secondIndexName");
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("merchantCode", merchantCode);
		params.put("commodityNo", commodityNo);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("firstIndexName", firstIndexName);
		params.put("secondIndexName", secondIndexName);
		
		Map<String,Object> result = reportSingleAnalysisService.queryCommodityEveryDayIndex(params);
		
		return JSONObject.fromObject(result).toString();
	}

}
