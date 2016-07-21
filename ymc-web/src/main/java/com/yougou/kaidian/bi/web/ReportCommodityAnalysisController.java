package com.yougou.kaidian.bi.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.kaidian.bi.vo.SingleAnalysisVo;
import com.yougou.kaidian.commodity.component.CommodityComponent;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.tools.common.utils.DateUtil;

@Controller
public class ReportCommodityAnalysisController {
	
	@Resource
	private CommodityComponent commodityComponet;
	
	/**
	 * 数据报表-经营分析
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("report/commodityAnalysisList")
	public ModelAndView loadCommoditySaleRankPage(ModelMap modelMap,HttpServletRequest request) throws Exception {
		commodityComponet.initBrand(modelMap, request);
		String dimensions = request.getParameter("dimensions");
		modelMap.put("dimensions", dimensions);
		return new ModelAndView("manage/report/commodityAnalysisList", modelMap);
	}
}
