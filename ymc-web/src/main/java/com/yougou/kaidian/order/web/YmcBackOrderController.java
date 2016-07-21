package com.yougou.kaidian.order.web;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.order.service.IYmcBackOrderService;
import com.yougou.merchant.api.supplier.vo.BackOrderDetailVo;
import com.yougou.merchant.api.supplier.vo.BackOrderVo;

@Controller
@RequestMapping("/backOrder")
public class YmcBackOrderController {
	
	@Resource
	private IYmcBackOrderService backOrderService;
	
	private static Logger logger = LoggerFactory.getLogger(YmcBackOrderController.class);
	
	/**
	 * 退回单列表查询
	 * @param backOrderVo
	 * @param query
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/backOrderList")
	public ModelAndView queryBackOrderList(BackOrderVo backOrderVo, Query query, HttpServletRequest request,
			ModelMap modelMap) throws Exception {
		//20条分页
		query.setPageSize(20);
		//封装页面查询条件
		String orderSubNo = request.getParameter("orderSubNo");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("orderSubNo", orderSubNo);
		String warehHoseCode = SessionUtil.getWarehouseCodeFromSession(request);
		backOrderVo.setBackWarehHouseCode(warehHoseCode);
		
		PageFinder<BackOrderVo> pageFinder = backOrderService.queryBackOrderList(backOrderVo, query, params);
		modelMap.put("pageFinder", pageFinder);
		modelMap.put("backOrderVo",backOrderVo);
		modelMap.put("params",params);
		return new ModelAndView("/manage/backorder/backOrderList", modelMap);
	}
	
	/**
	 * 退回单详情列表查询
	 * @param backOrderDetailVo
	 * @param query
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/backOrderDetailList")
	public ModelAndView queryBackOrderDetailList(BackOrderDetailVo backOrderDetailVo, Query query, HttpServletRequest request,
			ModelMap modelMap) throws Exception {
		//20条分页
		query.setPageSize(20);
		String mainId = backOrderDetailVo.getMainId();
		BackOrderVo backOrderVo = backOrderService.queryBackOrderInfoById(mainId);
		PageFinder<BackOrderDetailVo> pageFinder = backOrderService.queryBackOrderDetailList(backOrderDetailVo, query);
		modelMap.put("pageFinder", pageFinder);
		modelMap.put("backOrderDetailVo", backOrderDetailVo);
		modelMap.put("backOrderVo", backOrderVo);
		//判断是处理还是查看，query:查看，receive：处理
		String handle = request.getParameter("handle");
		modelMap.put("handle", handle);
		return new ModelAndView("/manage/backorder/backOrderDetailList", modelMap);
	}
	
	/**
	 * 操作单个确认收货，弹出层
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/receiveBackOrder")
	public ModelAndView receiveBackOrder(HttpServletRequest request,ModelMap modelMap) throws Exception {
		String noBackCount = request.getParameter("noBackCount");
		modelMap.put("noBackCount", noBackCount);
		return new ModelAndView("receiveBackOrder", modelMap);
	}
	
	/**
	 * 单个确认收货
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/handleReceive")
	@ResponseBody
	public String handleReceive( HttpServletRequest request,ModelMap modelMap) throws Exception {
		String detailId = request.getParameter("detailId");//退回单明细ID
		String mainId = request.getParameter("mainId"); //退回单ID
		String receiveCountStr = request.getParameter("receiveCount"); //确认收货数量
		int receiveCount = 0;
		if(StringUtils.isNotBlank(receiveCountStr)){
			receiveCount = Integer.parseInt(receiveCountStr);
		}
		String backCode = request.getParameter("backCode"); //退回单号
		//String insideCode = request.getParameter("insideCode"); //货品条码
		//String orderSubNo =request.getParameter("orderSubNo"); //订单号
		String noBackTotalCountStr = request.getParameter("noBackTotalCount"); // 未退回总数
		int noBackTotalCount = 0;
		if(StringUtils.isNotBlank(noBackTotalCountStr)){
			noBackTotalCount = Integer.parseInt(noBackTotalCountStr);
		}
		//判断退回单收货状态
		Integer receiveStatus = null;
		//总未退回数量=确认收货数量，收货状态为1：已确认收货
		if(noBackTotalCount == receiveCount && receiveCount !=0){
			receiveStatus = 1;
		//总未退回数量>确认收货数量，收货状态为2：部分确认收货	
		}else if(noBackTotalCount > receiveCount && receiveCount !=0){
			receiveStatus = 2;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("detailId", detailId);
		params.put("mainId", mainId);
		params.put("receiveCount", receiveCount);
		params.put("backCode", backCode);
		//params.put("insideCode", insideCode);
		//params.put("orderSubNo", orderSubNo);
		params.put("receiveStatus", receiveStatus);
		params.put("receiveOperator", YmcThreadLocalHolder.getOperater());
		backOrderService.handleReceive(params);
		JSONObject json = new JSONObject();
		json.put("msg", "sucess");
		return json.toString();
	}
	
	/**
	 * 批量确认收货
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/batchHandleReceive")
	@ResponseBody
	public String batchHandleReceive( HttpServletRequest request,ModelMap modelMap) throws Exception {
		String detailIds = request.getParameter("detailIds");//退回单明细ID
		String mainId = request.getParameter("mainId"); //退回单ID
		String backCode = request.getParameter("backCode"); //退回单号
		String noBackTotalCountStr = request.getParameter("noBackTotalCount"); // 未退回总数
		int noBackTotalCount = 0;
		if(StringUtils.isNotBlank(noBackTotalCountStr)){
			noBackTotalCount = Integer.parseInt(noBackTotalCountStr);
		}
		List<String> detailIdList = null;
		if(StringUtils.isNotBlank(detailIds)){
			detailIdList = new ArrayList<String>();
			detailIds = detailIds.substring(0, detailIds.length()-1);
			String detailArray[] = detailIds.split(",");
			Collections.addAll(detailIdList,detailArray );
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("detailIdList", detailIdList);
		params.put("mainId", mainId);
		params.put("backCode", backCode);
		params.put("noBackTotalCount", noBackTotalCount);
		params.put("receiveOperator", YmcThreadLocalHolder.getOperater());
		backOrderService.batchHandleReceive(params);
		JSONObject json = new JSONObject();
		json.put("msg", "sucess");
		return json.toString();
	}
	
	/**
	 * 全部确认收货
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/allHandleReceive")
	@ResponseBody
	public String allHandleReceive( HttpServletRequest request,ModelMap modelMap) throws Exception {
		String mainId = request.getParameter("mainId"); //退回单ID
		String backCode = request.getParameter("backCode"); //退回单号
		String noBackTotalCountStr = request.getParameter("noBackTotalCount"); // 未退回总数
		int noBackTotalCount = 0;
		if(StringUtils.isNotBlank(noBackTotalCountStr)){
			noBackTotalCount = Integer.parseInt(noBackTotalCountStr);
		}		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("mainId", mainId);
		params.put("backCode", backCode);
		params.put("noBackTotalCount", noBackTotalCount);
		params.put("receiveOperator", YmcThreadLocalHolder.getOperater());
		backOrderService.allHandleReceive(params);
		JSONObject json = new JSONObject();
		json.put("msg", "sucess");
		return json.toString();
	}
	

}
