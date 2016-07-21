package com.belle.yitiansystem.merchant.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.poi.ExportUtil;
import com.yougou.bi.api.IBIForMerchantApi;
import com.yougou.bi.api.StockoutMerchantsVo;
import com.yougou.ordercenter.api.order.IOrderForMerchantApi;
import com.belle.yitiansystem.systemmgmt.web.controller.BaseController;

/**
 * 
 * @author li.m1
 *
 */
@Controller
@RequestMapping("/yitiansystem/merchant/order")
public class OrderController extends BaseController{

	private static Logger logger = Logger.getLogger(OrderController.class);
	@Resource
	private IBIForMerchantApi biForMerchantApi;
	
	@RequestMapping("/punishOrder_merchant")
	public String punishOrderBymerchant(HttpServletRequest request,
			ModelMap map, Query query) throws Exception {
		Map<String, Object> params = this.builderParams(request, false);
		
		//排序方式
		if(params.get("orderType")!=null){
			if("1".equals(params.get("orderType").toString())){
				params.put("orderBy", 1);
				params.put("sortBy", 1);
			}else if("2".equals(params.get("orderType").toString())){
				params.put("orderBy", 1);
				params.put("sortBy", 2);
			}else if("3".equals(params.get("orderType").toString())){
				params.put("orderBy", 2);
				params.put("sortBy", 1);
			}else if("4".equals(params.get("orderType").toString())){
				params.put("orderBy", 2);
				params.put("sortBy", 2);
			}
		}

		DateTime dateTime = new DateTime();
		if(params.get("stockoutDateStart")==null||"".equals(params.get("stockoutDateStart"))){
			params.put("stockoutDateStart", dateTime.plusDays(-30).toString("yyyy-MM-dd HH:mm:ss"));
		}
		if(params.get("stockoutDateEnd")==null||"".equals(params.get("stockoutDateEnd"))){
			params.put("stockoutDateEnd", dateTime.toString("yyyy-MM-dd HH:mm:ss"));
		}
		params.put("pageIndex", query.getPage());
		params.put("pageSize", query.getPageSize());
		Map<String, Object> result=biForMerchantApi.getStockoutMerchants(params);
		if(result!=null&&Integer.valueOf(result.get("code").toString())==0){
			int count=result.get("totalCount")==null?0:(Integer)result.get("totalCount");
			PageFinder<StockoutMerchantsVo> pageFinder=new PageFinder(query.getPage(),query.getPageSize(),count);
			pageFinder.setData((List<StockoutMerchantsVo>)result.get("data"));
			map.addAttribute("pageFinder", pageFinder);
		}else{
			logger.error("调用BI接口查询缺货率产生异常!");
		}
		map.put("params", params);
		return "yitiansystem/order/punish_order_merchant";
	}
	
	@RequestMapping("/punishOrder_brand")
	public String punishOrderByBrand(HttpServletRequest request,
			ModelMap map, Query query) throws Exception {
		Map<String, Object> params = this.builderParams(request, false);
		
		//排序方式
		if(params.get("orderType")!=null){
			if("1".equals(params.get("orderType").toString())){
				params.put("orderBy", 1);
				params.put("sortBy", 1);
			}else if("2".equals(params.get("orderType").toString())){
				params.put("orderBy", 1);
				params.put("sortBy", 2);
			}else if("3".equals(params.get("orderType").toString())){
				params.put("orderBy", 2);
				params.put("sortBy", 1);
			}else if("4".equals(params.get("orderType").toString())){
				params.put("orderBy", 2);
				params.put("sortBy", 2);
			}
		}

		DateTime dateTime = new DateTime();
		if(params.get("stockoutDateStart")==null||"".equals(params.get("stockoutDateStart"))){
			params.put("stockoutDateStart", dateTime.plusDays(-30).toString("yyyy-MM-dd HH:mm:ss"));
		}
		if(params.get("stockoutDateEnd")==null||"".equals(params.get("stockoutDateEnd"))){
			params.put("stockoutDateEnd", dateTime.toString("yyyy-MM-dd HH:mm:ss"));
		}
		params.put("pageIndex", query.getPage());
		params.put("pageSize", query.getPageSize());
		Map<String, Object> result=biForMerchantApi.getStockoutMerchantBrand(params);
		if(result!=null&&Integer.valueOf(result.get("code").toString())==0){
			int count=result.get("totalCount")==null?0:(Integer)result.get("totalCount");
			PageFinder<StockoutMerchantsVo> pageFinder=new PageFinder(query.getPage(),query.getPageSize(),count);
			pageFinder.setData((List<StockoutMerchantsVo>)result.get("data"));
			map.addAttribute("pageFinder", pageFinder);
		}else{
			logger.error("调用BI接口查询缺货率产生异常(品牌)!");
		}
		map.put("params", params);
		return "yitiansystem/order/punish_order_brand";
	}
	
	@RequestMapping("exportPunishCommodity")
	public void exportPunishCommodity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> params = this.builderParams(request, false);
		//merchantCode=merchantCode(SP20140103487301)
		//stockoutDateStart=stockoutDateStart(2014-06-24 17:13:29)
		//stockoutDateEnd=stockoutDateEnd(2014-09-21 17:13:29)
		//System.out.println("merchantCode=="+params.get("merchantCode"));
		//params.put("merchantCode", null);
		//System.out.println("merchantCode=="+params.get("merchantCode"));
		Map<String, Object> result=biForMerchantApi.getStockoutMerchantCommdity(params);
		if(result!=null&&Integer.valueOf(result.get("code").toString())==0){
			List<StockoutMerchantsVo> datas= (List<StockoutMerchantsVo>)result.get("data");
			String[] headers = new String[]{"商品名称","商家名称", "品牌","一级分类","订单号","置缺时间", "下单日期", "款色编码", "货品条码","订单金额","单品成交价"};
			//String[] headers = new String[]{"商品名称","商家名称", "品牌","一级分类","订单号","置缺时间", "下单日期", "款色编码", "货品条码","订单金额"};
			String[] heddenAndUpdateColumns = new String[] {"NORMAL", "NORMAL", "NORMAL", "NORMAL", "NORMAL", "NORMAL", "NORMAL", "NORMAL","NORMAL", "NORMAL","NORMAL"};
			//String[] heddenAndUpdateColumns = new String[] {"NORMAL", "NORMAL", "NORMAL", "NORMAL", "NORMAL", "NORMAL", "NORMAL", "NORMAL","NORMAL", "NORMAL"};
			Boolean[] amounts = new Boolean[]{false, false, false, false, false, false, false, false,false, true,true};
			//Boolean[] amounts = new Boolean[]{false, false, false, false, false, false, false, false,false, true,false};
			String[] cloumns = new String[] {"commodityName", "merchantName", "brandName", "catNameOne","orderSubNo" , "stockoutTime", "orderDate", "supplierCode", "insideCode","orderPrice","commodityPrice"};
			//String[] cloumns = new String[] {"commodityName", "merchantName", "brandName", "catNameOne","orderSubNo" , "stockoutTime", "orderDate", "supplierCode", "insideCode","orderPrice"};
			Boolean[] percents = new Boolean[]{false, false, false, false, false, false, false, false,false, false,false};
			//Boolean[] percents = new Boolean[]{false, false, false, false, false, false, false, false,false, false};
			String brandName=MapUtils.getString(params,"brandName");
			String fileName=MapUtils.getString(params,"merchantCode")+(StringUtils.isBlank(brandName)?"":"-"+brandName)+"-"+(new DateTime()).toString("yyyyMMdd");
			ExportUtil.exportEexcel(response, request, fileName, "缺货商品明细", headers, datas, heddenAndUpdateColumns, amounts, StockoutMerchantsVo.class, cloumns, percents);
		}else{
			logger.error("调用BI接口查询缺货率产生异常(导出)!");
		}
	}
}
