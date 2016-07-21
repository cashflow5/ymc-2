package com.yougou.kaidian.bi.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.kaidian.bi.beans.AftersaleStatisticsTendency;
import com.yougou.kaidian.bi.beans.AnalyzePatttern;
import com.yougou.kaidian.bi.beans.CommoditySalesDetailsTendency;
import com.yougou.kaidian.bi.beans.CommoditySalesQueryState;
import com.yougou.kaidian.bi.beans.IntelligenceCalendar;
import com.yougou.kaidian.bi.beans.SummaryOfOperationsTendency;
import com.yougou.kaidian.bi.service.IMerchantsStatisticsService;
import com.yougou.kaidian.bi.vo.AfterSaleStatisticsVo;
import com.yougou.kaidian.bi.vo.AfterSaleStatisticsVo.AfterSaleStatisticsVoDetail;
import com.yougou.kaidian.bi.vo.CommoditySalesDetailsVo;
import com.yougou.kaidian.bi.vo.CommoditySalesDetailsVo.CommoditySalesDetailsVoDetail;
import com.yougou.kaidian.bi.vo.SummaryOfOperationsVo;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.ordercenter.common.SaleStatusEnum;
import com.yougou.ordercenter.common.SaleTypeEnum;

/**
 * 报表中心控制器
 * 
 * @author yang.mq
 *
 */
@Controller
public class MerchantsStatisticsController {
	
	@Resource
	private IMerchantsStatisticsService merchantsStatisticsService;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}

	@RequestMapping("bi/preSummaryOfOperations")
	public ModelAndView preQuerySummaryOfOperations(ModelMap modelMap, HttpServletRequest request) throws Exception {
		SummaryOfOperationsVo summaryOfOperationsVo = new SummaryOfOperationsVo();
		summaryOfOperationsVo.setAnalyzePatttern(AnalyzePatttern.BY_DAY);
		summaryOfOperationsVo.setStart(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
		summaryOfOperationsVo.setStartText(DateFormatUtils.ISO_DATE_FORMAT.format(summaryOfOperationsVo.getStart()));
		summaryOfOperationsVo.setEnd(DateUtils.addSeconds(summaryOfOperationsVo.getStart(), 86399));
		return querySummaryOfOperations(summaryOfOperationsVo, modelMap, request);
	}
	
	@RequestMapping("bi/preCommoditySaleDetails")
	public ModelAndView preQueryCommoditySalesDetails(ModelMap modelMap, HttpServletRequest request) throws Exception {
		CommoditySalesDetailsVo commoditySaleDetailsVo = new CommoditySalesDetailsVo();
		commoditySaleDetailsVo.setAnalyzePatttern(AnalyzePatttern.BY_DAY);
		commoditySaleDetailsVo.setStart(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
		commoditySaleDetailsVo.setStartText(DateFormatUtils.ISO_DATE_FORMAT.format(commoditySaleDetailsVo.getStart()));
		commoditySaleDetailsVo.setEnd(DateUtils.addSeconds(commoditySaleDetailsVo.getStart(), 86399));
		return queryAllCommoditySalesDetails(commoditySaleDetailsVo, new Query(30), modelMap, request);
	}
	
	@RequestMapping("bi/preAfterSaleStatistics")
	public ModelAndView preQueryAftersaleStatistics(ModelMap modelMap, HttpServletRequest request) throws Exception {
		AfterSaleStatisticsVo afterSaleStatisticsVo = new AfterSaleStatisticsVo();
		afterSaleStatisticsVo.setAnalyzePatttern(AnalyzePatttern.BY_DAY);
		afterSaleStatisticsVo.setStart(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
		afterSaleStatisticsVo.setStartText(DateFormatUtils.ISO_DATE_FORMAT.format(afterSaleStatisticsVo.getStart()));
		afterSaleStatisticsVo.setEnd(DateUtils.addSeconds(afterSaleStatisticsVo.getStart(), 86399));
		return queryAftersaleStatistics(afterSaleStatisticsVo, modelMap, request);
	}
	
	/**
	 * 查询经营概况
	 * 
	 * @param summaryOfOperationsVo
	 * @param modelMap
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("bi/querySummaryOfOperations")
	public ModelAndView querySummaryOfOperations(SummaryOfOperationsVo summaryOfOperationsVo, ModelMap modelMap, HttpServletRequest request) throws Exception {
		summaryOfOperationsVo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		merchantsStatisticsService.injectSummaryOfOperationsInto(summaryOfOperationsVo);
		modelMap.addAttribute("summaryOfOperationsVo", summaryOfOperationsVo);
		modelMap.addAttribute("Tendencys", SummaryOfOperationsTendency.values());
		modelMap.addAttribute("AnalyzePattterns", AnalyzePatttern.values());
		modelMap.addAttribute("tagTab", "bi");
		return new ModelAndView("manage/bi/summary_of_operations", modelMap);
	}
	
	/**
	 * 打印经营概况
	 * 
	 * @param summaryOfOperationsVo
	 * @param modelMap
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("bi/printSummaryOfOperations")
	public ModelAndView printSummaryOfOperations(SummaryOfOperationsVo summaryOfOperationsVo, ModelMap modelMap, HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = querySummaryOfOperations(summaryOfOperationsVo, modelMap, request);
		modelAndView.setViewName("manage/bi/print_summary_of_operations");
		return modelAndView;
	}

	/**
	 * 查询所有商品销售明细
	 * 
	 * @param commoditySaleDetailsVo
	 * @param modelMap
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("bi/queryAllCommoditySalesDetails")
	public ModelAndView queryAllCommoditySalesDetails(CommoditySalesDetailsVo commoditySaleDetailsVo, Query query, ModelMap modelMap, HttpServletRequest request) throws Exception {
		commoditySaleDetailsVo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		merchantsStatisticsService.injectCommoditySalesDetailsInto(commoditySaleDetailsVo, query);
		modelMap.addAttribute("commoditySaleDetailsVo", commoditySaleDetailsVo);
		modelMap.addAttribute("pageFinder", commoditySaleDetailsVo.getPageFinder());
		modelMap.addAttribute("Tendencys", CommoditySalesDetailsTendency.values());
		modelMap.addAttribute("AnalyzePattterns", AnalyzePatttern.values());
		modelMap.addAttribute("CommoditySalesQueryStates", CommoditySalesQueryState.values());
		modelMap.addAttribute("tagTab", "bi");
		return new ModelAndView("manage/bi/commodity_sales_details", modelMap);
	}
	
	/**
	 * 查询商品销售明细
	 * 
	 * @param commoditySaleDetailsVo
	 * @param modelMap
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("bi/queryCommoditySalesDetails")
	public ModelAndView queryCommoditySalesDetails(CommoditySalesDetailsVo commoditySaleDetailsVo, Query query, ModelMap modelMap, HttpServletRequest request) throws Exception {
		commoditySaleDetailsVo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		PageFinder<CommoditySalesDetailsVoDetail> pageFinder = null;
		switch (commoditySaleDetailsVo.getSalesQueryState()) {
		case MORE_SALE://有销售
			pageFinder = merchantsStatisticsService.queryCommoditySalesDetailsInfo(commoditySaleDetailsVo, query);
			break;
		case ZERO_SALE://无销售
			pageFinder = merchantsStatisticsService.queryCommodityNoSalesDetailsInfo(commoditySaleDetailsVo, query);
			break;
		}
		modelMap.addAttribute("commoditySaleDetailsVo", commoditySaleDetailsVo);
		modelMap.addAttribute("pageFinder", pageFinder);
		return new ModelAndView("manage_unless/bi/commodity_sales_details_list", modelMap);
	}
	
	@RequestMapping("bi/printCommoditySalesDetails")
	public ModelAndView printCommoditySalesDetails(CommoditySalesDetailsVo commoditySaleDetailsVo, Query query, ModelMap modelMap, HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = queryCommoditySalesDetails(commoditySaleDetailsVo, query, modelMap, request);
		modelAndView.setViewName("manage/bi/print_commodity_sales_details");
		return modelAndView;
	}
	
	@RequestMapping("bi/queryAftersaleStatistics")
	public ModelAndView queryAftersaleStatistics(AfterSaleStatisticsVo afterSaleStatisticsVo, ModelMap modelMap, HttpServletRequest request) throws Exception {
		afterSaleStatisticsVo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		merchantsStatisticsService.injectAfterSaleStatisticsDetailsInfo(afterSaleStatisticsVo);
		modelMap.addAttribute("afterSaleStatisticsVo", afterSaleStatisticsVo);
		modelMap.addAttribute("AnalyzePattterns", AnalyzePatttern.values());
		modelMap.addAttribute("SaleStatuses", SaleStatusEnum.values());
		modelMap.addAttribute("SaleTypes", new SaleTypeEnum[] { SaleTypeEnum.QUIT_GOODS, SaleTypeEnum.TRADE_GOODS });
		modelMap.addAttribute("Tendencys", AftersaleStatisticsTendency.values());
		modelMap.addAttribute("tagTab", "bi");
		return new ModelAndView("manage/bi/aftersale_statistics", modelMap);
	}
	
	@RequestMapping("bi/queryAftersaleStatisticsDetail")
	public ModelAndView queryAftersaleStatisticsDetail(AfterSaleStatisticsVo afterSaleStatisticsVo, Query query, ModelMap modelMap, HttpServletRequest request) throws Exception {
		afterSaleStatisticsVo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		PageFinder<AfterSaleStatisticsVoDetail> pageFinder = merchantsStatisticsService.queryAfterSaleStatisticsDetailsInfo(afterSaleStatisticsVo, query);
		afterSaleStatisticsVo.setPageFinder(pageFinder);
		modelMap.addAttribute("afterSaleStatisticsVo", afterSaleStatisticsVo);
		modelMap.addAttribute("pageFinder", pageFinder);
		return new ModelAndView("manage_unless/bi/aftersale_statistics_list", modelMap);
	}
	
	@RequestMapping("bi/printAftersaleStatisticsDetail")
	public ModelAndView printAftersaleStatisticsDetail(AfterSaleStatisticsVo afterSaleStatisticsVo, Query query, ModelMap modelMap, HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = queryAftersaleStatisticsDetail(afterSaleStatisticsVo, query, modelMap, request);
		modelAndView.setViewName("manage/bi/print_aftersale_statistics");
		return modelAndView;
	}
	
	/**
	 * 序列化智能日历数据(use by Ajax)
	 * 
	 * @param year
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("bi/serializeIntelligenceCalendar")
	public void serializeIntelligenceCalendar(String year, HttpServletResponse response) throws Exception {
		IntelligenceCalendar ic = new IntelligenceCalendar(Integer.parseInt(year));
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("year", ic.getThisYear());
		jsonObject.accumulate("yearMonths", ic.getThisYearMonths());
		jsonObject.accumulate("yearWeeks", ic.getThisYearWeeks());
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObject.toString());
		response.getWriter().flush();
	}
}
