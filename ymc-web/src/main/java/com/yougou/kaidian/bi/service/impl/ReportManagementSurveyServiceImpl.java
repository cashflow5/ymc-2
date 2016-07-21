package com.yougou.kaidian.bi.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.session.RowBounds;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.yougou.kaidian.bi.dao.MMSDBReportQueryMapper;
import com.yougou.kaidian.bi.dao.ReportManagementSurveyMapper;
import com.yougou.kaidian.bi.service.IReportManagementSurveyService;
import com.yougou.kaidian.bi.vo.CommodityAnalysisVo;
import com.yougou.kaidian.bi.web.ReportManagementSurveyController;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.framework.constant.Constant;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.tools.common.utils.DateUtil;

/**
 * 商家中心数据报表--经营概况 服务层业务实现类
 * @author zhang.f1
 *
 */
@Service
public class ReportManagementSurveyServiceImpl implements
		IReportManagementSurveyService {
	
	@Resource
	private ReportManagementSurveyMapper reportManagementSurveyMapper ;
	
	@Resource	
	private MMSDBReportQueryMapper mmsdbReportQueryMapper;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	private Logger logger = LoggerFactory.getLogger(ReportManagementSurveyController.class);
	

	@Override
	public Map calculateSupplierTotalIndex(Map params) {
		// TODO Auto-generated method stub
		// 申明返回结果
		Map<String,Object> result = new HashMap<String,Object>();
		
		//没有查询起止时间，默认查询最近7天的数据
		String startDate = (String) params.get("startDate");
		String endDate = (String)params.get("endDate");
		if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){
			Date date = new Date();
			startDate = DateUtil.format(DateUtil.addDate(date, -7),"yyyy-MM-dd");
			endDate = DateUtil.format(DateUtil.addDate(date, -1),"yyyy-MM-dd");
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		//取出需要统计的指标名称
		List<String> manageServeyIndex = (List<String> ) params.get("manageServeyIndex");
		
		//从单品分析表内统计出商品汇总指标数据，含有访次，浏览量，新上架商品数，发货件数，拒收商品数，拒收金额
		Map commodityIndexMap =  null;
		if(manageServeyIndex.contains(Constant.INDEX_NAME_CHANGE_PERCENT) || manageServeyIndex.contains(Constant.INDEX_NAME_PAGE_VIEW)
				|| manageServeyIndex.contains(Constant.INDEX_NAME_VISIT_COUNT) || manageServeyIndex.contains(Constant.INDEX_NAME_REJECTED_COMMODITY_AMT) 
				|| manageServeyIndex.contains(Constant.INDEX_NAME_REJECTED_COMMODITY_NUM) || manageServeyIndex.contains(Constant.INDEX_NAME_REJECTED_COMMODITY_PERCENT)){
			commodityIndexMap = reportManagementSurveyMapper.queryTotalIndexByParams(params);
		}
		
		//从订单表内统计出收订订单数量，金额
		Map orderMap =  null ;
		if(manageServeyIndex.contains(Constant.INDEX_NAME_ORDER_NUM) || manageServeyIndex.contains(Constant.INDEX_NAME_TOTAL_AMT) 
				|| manageServeyIndex.contains(Constant.INDEX_NAME_CHANGE_PERCENT)){
			orderMap = mmsdbReportQueryMapper.queryOrderByParams(params);
		}
		
		//从实时订单同步表统计出 支付商品数量，支付商品金额,商品均价
		Map payedCommodityMap = null;

		//从实时订单同步表统计出 支付订单数量，支付订单金额，支付订单均价
		Map payedOrderMap =  null;
		if(manageServeyIndex.contains(Constant.INDEX_NAME_PAYED_ORDER_AMT) || manageServeyIndex.contains(Constant.INDEX_NAME_PAYED_ORDER_AVG_AMT)
				|| manageServeyIndex.contains(Constant.INDEX_NAME_PAYED_ORDER_NUM)){
			payedOrderMap = reportManagementSurveyMapper.queryPayedOrderByParams(params);
		}
		//从订单表内统计出发货订单数,发货金额
		Map deliverOorderMap = null;
		if(manageServeyIndex.contains(Constant.INDEX_NAME_DELIVERY_ORDER_AMT) || manageServeyIndex.contains(Constant.INDEX_NAME_DELIVERY_ORDER_NUM)
				|| manageServeyIndex.contains(Constant.INDEX_NAME_DELIVERY_PERCENT)){
			deliverOorderMap = mmsdbReportQueryMapper.queryDeleveryOrderByParams(params);
		}
		
		//新上架商品数
		Integer newSaleCommodityNum = 0;
		if(manageServeyIndex.contains(Constant.INDEX_NAME_NEW_SALE_COMMODITY_NUM)){
			newSaleCommodityNum = reportManagementSurveyMapper.queryNewSaleCommodityNumByParams(params);
		}
		//将指标汇总到一个Map，便于取数计算
		Map totalIndex = new HashMap();
		totalIndex.putAll(commodityIndexMap == null ? new HashMap() : commodityIndexMap);
		totalIndex.putAll( orderMap == null ? new HashMap() : orderMap);
		totalIndex.putAll( payedCommodityMap == null ? new HashMap() : payedCommodityMap);
		totalIndex.putAll( payedOrderMap == null ? new HashMap() : payedOrderMap);
		totalIndex.putAll( deliverOorderMap == null ? new HashMap() : deliverOorderMap);
		totalIndex.put("newSaleCommodityNum",newSaleCommodityNum);
		
		//循环取指标统计的数值
		for(String indexName : manageServeyIndex){
			//非百分比指标数值，无需再加工，直接取数据
			if(indexName.indexOf("Percent") == -1){
				result.put(indexName, totalIndex.get(indexName) == null ? 0 : totalIndex.get(indexName));
			//转化率 = 收订订单数/访次
			}else if(indexName.equals(Constant.INDEX_NAME_CHANGE_PERCENT)){
				//收订订单数
				Integer orderNum = (Integer) (totalIndex.get("orderNum") == null ? 0 : totalIndex.get("orderNum"));
				//访次
				Integer visitCount = (Integer) (totalIndex.get("visitCount") == null ? 0 : totalIndex.get("visitCount"));
				
				if(visitCount ==0){
					result.put(indexName, 0);
				}else{
					double changePercent = 0;
					changePercent= (double)orderNum / visitCount;
					changePercent = handUpHalfDouble(changePercent*100);
					result.put(indexName, changePercent);
				}
			//发货率 = 发货订单数/支付订单数	
			}else if(indexName.equals(Constant.INDEX_NAME_DELIVERY_PERCENT)){
				//发货订单数
				Integer deliveryOrderNum = (Integer) (totalIndex.get("deliveryOrderNum") == null ? 0 : totalIndex.get("deliveryOrderNum"));
				//支付订单数
				Integer payedOrderNum = (Integer) (totalIndex.get("payedOrderNum") == null ? 0 : totalIndex.get("payedOrderNum"));
				
				if(payedOrderNum ==0){
					result.put(indexName, 0);
				}else{
					double deliveryPercent = 0;
					deliveryPercent= (double)deliveryOrderNum / payedOrderNum;
					deliveryPercent = handUpHalfDouble(deliveryPercent*100);
					result.put(indexName, deliveryPercent);
				}
			// 退货拒收率 = 退货拒收商品数/发货商品数	
			}else if(indexName.equals(Constant.INDEX_NAME_REJECTED_COMMODITY_PERCENT)){
				//退货拒收商品数
				Integer rejectedCommodityNum = (Integer) (totalIndex.get("rejectedCommodityNum") == null ? 0 : totalIndex.get("rejectedCommodityNum"));
				//发货商品数
				Integer sendCommodityNum = (Integer) (totalIndex.get("sendCommodityNum") == null ? 0 : totalIndex.get("sendCommodityNum"));
				
				if(sendCommodityNum ==0){
					result.put(indexName, 0);
				}else{
					double rejectedCommodityPercent = 0;
					rejectedCommodityPercent= (double)rejectedCommodityNum / sendCommodityNum;
					rejectedCommodityPercent = handUpHalfDouble(rejectedCommodityPercent*100);
					result.put(indexName, rejectedCommodityPercent);
				}
			}			
		}

		return result;
	}
	
	/**
	 * 四舍五入 double 值，保留两位小数
	 * @param d
	 * @return
	 */
	private static double handUpHalfDouble(double d){
		
		BigDecimal b = new BigDecimal(d);
		double dval = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		return dval;
	}
	

	@Override
	public Map queryEveryDayOrHourIndex(Map params) {
		// TODO Auto-generated method stub
		//申明返回对象
		Map result = new HashMap();
		String firstIndexName = (String) params.get("firstIndexName"); //第一个指标名称，非空
		String secondIndexName = (String) params.get("secondIndexName"); // 第二个指标名称  或空
		
		//取出查询的起止时间，计算出时间间隔天数
		String startDate = (String) params.get("startDate");
		String endDate =  (String) params.get("endDate");
		//没有查询起止时间，默认查询最近7天的数据
		if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){
			Date date = new Date();
			startDate = DateUtil.format(DateUtil.addDate(date, -7),"yyyy-MM-dd");
			endDate = DateUtil.format(DateUtil.addDate(date, -1),"yyyy-MM-dd");
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		Date start = DateUtil.parseDate(startDate, "yyyy-MM-dd");
		Date end = DateUtil.parseDate(endDate, "yyyy-MM-dd");
		int diff = DateUtil.diffDate( end,start);
		//从起始时间开始，直到结束日期，得到每天的具体日期，以便折线图进行每天数据展示
		Date[] dateArr = new Date[diff+1];
		for(int i=0;i<dateArr.length;i++){
			dateArr[i]=DateUtil.addDate(start, i);
		}
		
		List<Map> mapList = null; //每天商品访次，浏览量，发货件数,退货拒收数,退货拒收额
		List<Map> payedOrderList = null; //每天订单量，订单金额，订单均价
		List<Map> newSaleCommodityList = null; //每天新上架商品数
		List<Map> orderList = null; //每天收订订单数
		List<Map> deliveryOrderList = null; //每天发货订单数
		//统计转换率，浏览量，访次 ，退货拒收相关指标 进入下面的查询
		if(params.containsValue(Constant.INDEX_NAME_CHANGE_PERCENT) || params.containsValue(Constant.INDEX_NAME_PAGE_VIEW)
				|| params.containsValue(Constant.INDEX_NAME_VISIT_COUNT) || params.containsValue(Constant.INDEX_NAME_REJECTED_COMMODITY_AMT) 
				|| params.containsValue(Constant.INDEX_NAME_REJECTED_COMMODITY_NUM) || params.containsValue(Constant.INDEX_NAME_REJECTED_COMMODITY_PERCENT)){
			mapList = reportManagementSurveyMapper.queryEveryDayOrHourIndex(params);		
		}
		//统计发货率需要支付订单数，支付订单相关信息	
		if(params.containsValue(Constant.INDEX_NAME_DELIVERY_PERCENT) || params.containsValue(Constant.INDEX_NAME_PAYED_ORDER_AMT) 
				|| params.containsValue(Constant.INDEX_NAME_PAYED_ORDER_AVG_AMT) || params.containsValue(Constant.INDEX_NAME_PAYED_ORDER_NUM)){
			payedOrderList=reportManagementSurveyMapper.queryEveryDayPayedOrderByParams(params);		
		}
		//统计新上架商品数
		if(params.containsValue(Constant.INDEX_NAME_NEW_SALE_COMMODITY_NUM)){
			newSaleCommodityList = reportManagementSurveyMapper.queryEveryDayNewSaleCommodityByParams(params);			
		}
		//统计收订订单数,收订金额 ,计算转化率用到了收订订单数
		if(params.containsValue(Constant.INDEX_NAME_CHANGE_PERCENT) || params.containsValue(Constant.INDEX_NAME_ORDER_NUM) 
				|| params.containsValue(Constant.INDEX_NAME_TOTAL_AMT)){
			orderList = mmsdbReportQueryMapper.queryEveryDayOrderByParams(params);
		}
		//统计每天发货订单数，发货金额
		if(params.containsValue(Constant.INDEX_NAME_DELIVERY_PERCENT) || params.containsValue(Constant.INDEX_NAME_DELIVERY_ORDER_NUM) 
				|| params.containsValue(Constant.INDEX_NAME_DELIVERY_ORDER_AMT)){
			deliveryOrderList = mmsdbReportQueryMapper.queryEveryDayDeleveryOrderByParams(params);
		}
		
		//申明返回数据集合 ，存放每天的指标数据 -- 第一个指标 ；数据格式 [[日期1毫秒数,指标值1],[日期2毫秒数,指标值2]....]
		List<Object> firstList = new ArrayList<Object>();		
	
		//------------------------------------封装第一指标数据start------------------------------------
		//如果第一个指标是访次 或者 浏览量，直接从map 里面取出数据
		setIndexEveryDayVal(firstIndexName, dateArr, mapList,
				payedOrderList, /*payedCommodityList,*/ newSaleCommodityList,
				orderList,deliveryOrderList, firstList);
		//-----------------------------------------封装第一个指标数据end--------------------------------		

		//------------------------------------封装第二指标数据start------------------------------------
		//申明返回数据集合 ，存放每天的指标数据 -- 第二个指标 ，数据格式  [[日期1毫秒数,指标值1],[日期2毫秒数,指标值2]....]
		List<Object> secondList = null;
		if(StringUtils.isNotEmpty(secondIndexName) ){
			secondList = new ArrayList<Object>();				
			setIndexEveryDayVal(secondIndexName, dateArr, mapList,
					payedOrderList, /*payedCommodityList,*/ newSaleCommodityList,
					orderList, deliveryOrderList,secondList);
		}
		//-----------------------------------------封装第二个指标数据end--------------------------------
		
		result.put("firstIndex", firstList);
		result.put("secondIndex", secondList);
		return result;
	}
	
	/**
	 * 设置指定指标每天的值到指定集合中，以便页面曲线图展示
	 * @param firstIndexName
	 * @param dateArr
	 * @param mapList
	 * @param payedOrderList
	 * @param payedCommodityList
	 * @param newSaleCommodityList
	 * @param orderList
	 * @param deliveryOrderList
	 * @param firstList
	 */
	private void setIndexEveryDayVal(String firstIndexName,
			Date[] dateArr, List<Map> mapList, List<Map> payedOrderList,
			/*List<Map> payedCommodityList, */List<Map> newSaleCommodityList,
			List<Map> orderList,List<Map> deliveryOrderList ,List<Object> firstList) {
		//访次，浏览器，退货拒收额，退货拒收数
		if(firstIndexName.equals(Constant.INDEX_NAME_PAGE_VIEW) || firstIndexName.equals(Constant.INDEX_NAME_VISIT_COUNT) 
				|| firstIndexName.equals(Constant.INDEX_NAME_REJECTED_COMMODITY_AMT) || firstIndexName.equals(Constant.INDEX_NAME_REJECTED_COMMODITY_NUM)){					
			setCommdityIndexToArray(dateArr,firstIndexName, mapList, firstList,"reportDate");	
		//支付金额，支付订单数，订单均价	
		}else if(firstIndexName.equals(Constant.INDEX_NAME_PAYED_ORDER_AMT) || firstIndexName.equals(Constant.INDEX_NAME_PAYED_ORDER_AVG_AMT)
				|| firstIndexName.equals(Constant.INDEX_NAME_PAYED_ORDER_NUM)){
			setCommdityIndexToArray(dateArr,firstIndexName, payedOrderList, firstList,"orderDate");	
		}
		//发货订单数，发货金额
		else if(firstIndexName.equals(Constant.INDEX_NAME_DELIVERY_ORDER_NUM) || firstIndexName.equals(Constant.INDEX_NAME_DELIVERY_ORDER_AMT)){
			setCommdityIndexToArray(dateArr,firstIndexName, deliveryOrderList, firstList,"orderDate");	
		}
		//收订订单数，收订金额
		else if(firstIndexName.equals(Constant.INDEX_NAME_ORDER_NUM) || firstIndexName.equals(Constant.INDEX_NAME_TOTAL_AMT)
				|| firstIndexName.equals(Constant.INDEX_NAME_PAYED_ORDER_NUM)){
			setCommdityIndexToArray(dateArr,firstIndexName, orderList, firstList,"orderDate");	
		}
		//新上架商品数
		else if(firstIndexName.equals(Constant.INDEX_NAME_NEW_SALE_COMMODITY_NUM)){
			setCommdityIndexToArray(dateArr,firstIndexName, newSaleCommodityList, firstList,"firstSaleDate");	
		//发货率	
		}else if(firstIndexName.equals(Constant.INDEX_NAME_DELIVERY_PERCENT)){
			setEveryDayDeliveryPercent(dateArr, payedOrderList, deliveryOrderList,
					firstList);
		//转化率	
		}else if(firstIndexName.equals(Constant.INDEX_NAME_CHANGE_PERCENT)){
			setEveryDayChangePercent(dateArr, mapList, orderList, firstList);
		}
		//退货拒收率
		else if(firstIndexName.equals(Constant.INDEX_NAME_REJECTED_COMMODITY_PERCENT)){
			setEveryDayRejectedCommodityPercent(dateArr, mapList, firstList);
		}
	}
	
	/**
	 * 计算每天的退货拒收率，并按照指定格式存入集合对象，以便页面展示曲线图
	 * @param dateArr
	 * @param mapList
	 * @param secondList
	 */
	private void setEveryDayRejectedCommodityPercent(Date[] dateArr, List<Map> mapList, List<Object> secondList){
		for(Date date : dateArr){
			String dateStr = DateUtil.formatDate(date, "yyyy-MM-dd");
			//日期对应指标值的数组对象
			Object[] objArray = new Object[2];
			//获取日期的毫秒数,页面展示时日期显示会自动往前一天，所以此处将日期加1天，使其与查询日期相同显示
			long dateMin = DateUtil.getMillis(DateUtil.addDate(date, 1));	
			objArray[0] = dateMin;

			//发货数
			Integer sendCommodityNum = 0;
			// 退货拒收数
			Integer rejectedCommodityNum = 0;
			//取当前日期的发货数
			if(mapList != null && mapList.size() > 0){
				for(Map map : mapList){
					String reportDate = (String) map.get("reportDate");
					if(dateStr.equals(reportDate)){
						sendCommodityNum = (Integer) map.get("sendCommodityNum");
						rejectedCommodityNum = (Integer) map.get("rejectedCommodityNum");
						break;
					}						
				}
			}
			
			double rejectedCommodityPercent = 0.0;
			if(sendCommodityNum!=null && sendCommodityNum !=0){
				rejectedCommodityPercent = (double)(rejectedCommodityNum == null ? 0 : rejectedCommodityNum) 
										/ (double)(sendCommodityNum);
			}
			rejectedCommodityPercent=handUpHalfDouble(rejectedCommodityPercent*100);
			objArray[1] = rejectedCommodityPercent;
			secondList.add(objArray);
		}
		
	}
	
	/**
	 *  计算每天的转换率，并按照指定格式存入集合对象，以便页面展示曲线图
	 * @param dateArr
	 * @param mapList
	 * @param orderList
	 * @param secondList
	 */
	private void setEveryDayChangePercent(Date[] dateArr, List<Map> mapList,
			List<Map> orderList, List<Object> secondList) {
		for(Date date : dateArr){
			String dateStr = DateUtil.formatDate(date, "yyyy-MM-dd");
			//日期对应指标值的数组对象
			Object[] objArray = new Object[2];
			//获取日期的毫秒数,页面展示时日期显示会自动往前一天，所以此处将日期加1天，使其与查询日期相同显示
			long dateMin = DateUtil.getMillis(DateUtil.addDate(date, 1));	
			objArray[0] = dateMin;
			
			Integer orderNum = 0;
			//取当前日期的收订订单数
			if(orderList != null && orderList.size() > 0){
				for(Map map : orderList){
					String orderDate = (String) map.get("orderDate");
					if(dateStr.equals(orderDate)){
						orderNum = (Integer) map.get("orderNum");
						break;
					}						
				}
			}
			
			Integer visitCount = 0;
			//取当前日期的访次
			if(mapList != null && mapList.size() > 0){
				for(Map map : mapList){
					String reportDate = (String) map.get("reportDate");
					if(dateStr.equals(reportDate)){
						visitCount = (Integer) map.get("visitCount");
						break;
					}						
				}
			}
			
			double changePercent = 0.0;
			if(visitCount!=null && visitCount !=0){
				changePercent = (double)(orderNum == null ? 0 : orderNum) 
										/ (double)( (visitCount == null || visitCount ==0) ? 1 : visitCount);
			}
			changePercent=handUpHalfDouble(changePercent*100);
			objArray[1] = changePercent;
			secondList.add(objArray);
		}
	}
	
	/**
	 * 计算每天的发货率，并按照指定格式存入集合对象，以便页面展示曲线图
	 * @param dateArr
	 * @param mapList
	 * @param payedCommodityList
	 * @param firstList
	 */
	private void setEveryDayDeliveryPercent(Date[] dateArr, List<Map> payedOrderList,
			List<Map> deliveryOrderList, List<Object> firstList) {
		for(Date date : dateArr){
			String dateStr = DateUtil.formatDate(date, "yyyy-MM-dd");
			//日期对应指标值的数组对象
			Object[] objArray = new Object[2];
			//获取日期的毫秒数,页面展示时日期显示会自动往前一天，所以此处将日期加1天，使其与查询日期相同显示
			long dateMin = DateUtil.getMillis(DateUtil.addDate(date, 1));	
			objArray[0] = dateMin;
			
			Integer payedOrderNum = 0;
			//取当前日期的支付订单数
			if(payedOrderList != null && payedOrderList.size() > 0){
				for(Map map : payedOrderList){
					String orderDate = (String) map.get("orderDate");
					if(dateStr.equals(orderDate)){
						payedOrderNum = (Integer) map.get("payedOrderNum");
						break;
					}						
				}
			}
			
			Integer deliveryOrderNum = 0;
			//取当前日期的发货订单数
			if(deliveryOrderList != null && deliveryOrderList.size() > 0){
				for(Map map : deliveryOrderList){
					String reportDate = (String) map.get("orderDate");
					if(dateStr.equals(reportDate)){
						deliveryOrderNum = (Integer) map.get("deliveryOrderNum");
						break;
					}						
				}
			}
			
			//发货率=发货订单数/支付订单数
			double deliveryPercent = 0.0;
			if(payedOrderNum != null && payedOrderNum != 0){
				deliveryPercent = (double)(deliveryOrderNum == null ? 0 : deliveryOrderNum) 
										/ (double)( (payedOrderNum == null || payedOrderNum ==0) ? 1 : payedOrderNum);
			}
			deliveryPercent=handUpHalfDouble(deliveryPercent*100);
			objArray[1] = deliveryPercent;
			firstList.add(objArray);
		}
	}
	
	/**
	 * 迭代商品指标集合，读取和封装指标数据 ，包括：访次，浏览量
	 * @param indexName
	 * @param mapList
	 * @param list
	 */
	private void setCommdityIndexToArray(Date[] dateArr,String indexName,List<Map> mapList, List<Object> list,String keyDate) {
				
		for(Date date : dateArr){
			String dateStr = DateUtil.formatDate(date, "yyyy-MM-dd");
			//日期对应指标值的数组对象
			Object[] objArray = new Object[2];
			//获取日期的毫秒数,页面展示时日期显示会自动往前一天，所以此处将日期加1天，使其与查询日期相同显示
			long dateMin = DateUtil.getMillis(DateUtil.addDate(date, 1));	
			objArray[0] = dateMin;
			//从返回的数据集合中查找当前日期的指标数据
			if(mapList !=null && mapList.size() > 0){	
				boolean flag = false;
				for(Map map : mapList){
					String reportDate = (String) map.get(keyDate);
					if(dateStr.equals(reportDate)){
						//封装指标数据
						Object firstValue = map.get(indexName) == null ? 0 : map.get(indexName);	
						objArray[1] = firstValue;
						flag = true;
						break;
					}					
				}
				if(!flag){
					objArray[1] = 0;
				}
			}else{
				objArray[1] = 0;
			}
			list.add(objArray);
		}
	}
	

	@Override
	public PageFinder<Map<String, Object>> queryCommodityAnalysisList(
			Query query, CommodityAnalysisVo vo) {
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<Map<String, Object>> list = reportManagementSurveyMapper.queryCommodityAnalysisList(vo, rowBounds);
		//迭代结果集，取出商品编码
		if(list != null && list.size() >0){
			List<String> commodityNos = new ArrayList<String>();
			for(Map<String,Object> map : list){
				String commodityNo = (String) map.get("COMMODITY_NO");
				commodityNos.add(commodityNo);
			}
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("merchantCode", YmcThreadLocalHolder.getMerchantCode());
			params.put("loginName", YmcThreadLocalHolder.getOperater());
			params.put("commodityNos", commodityNos);
			//查询商品归类数量
			List<Map<String,Object>> resultList = mmsdbReportQueryMapper.queryFavoriteClassifyByCommodityNos(params);
			//将商品归类数量添加到页面列表结果集中，以便判断展示收藏图标
			if(resultList != null && resultList.size() > 0){
				for(Map<String,Object> map : list){
					String commodityNo = (String) map.get("COMMODITY_NO");
					boolean flag = false;
					for(Map<String,Object> fmap : resultList){
						String commodityNof = (String) fmap.get("commodity_no");
						if(commodityNo.equals(commodityNof)){
							flag = true;
							int fcount = (Integer) fmap.get("fcount");
							map.put("fcount", fcount);
							break;
						}
					}
					if(!flag){
						map.put("fcount", 0);
					}
				}
			}
		}
		Integer count = reportManagementSurveyMapper.queryCommodityAnalysisListCount(vo);
		PageFinder<Map<String, Object>> pageFinder = 
				new PageFinder<Map<String, Object>>(query.getPage(), query.getPageSize(),count);
		pageFinder.setData(list);
		return pageFinder;
	}

	@Override
	public PageFinder<Map<String, Object>> queryCategoryAnalysisList(
			Query query, CommodityAnalysisVo vo) {
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<Map<String, Object>> list = reportManagementSurveyMapper.queryCategoryAnalysisList(vo, rowBounds);
		Integer count = reportManagementSurveyMapper.queryCategoryAnalysisListCount(vo);
		PageFinder<Map<String, Object>> pageFinder = 
				new PageFinder<Map<String, Object>>(query.getPage(), query.getPageSize(),count);
		pageFinder.setData(list);
		return pageFinder;
	}

	@Override
	public Map<String, Object> querySumOfCommodityAnalysis(
			CommodityAnalysisVo vo) {
		return reportManagementSurveyMapper.querySumOfCommodityAnalysis(vo);
	}

	@Override
	public Map<String, Object> querySumOfCatagoryAnalysis(CommodityAnalysisVo vo) {
		return reportManagementSurveyMapper.querySumOfCategoryAnalysis(vo);
	}
	@Override
	public List<Map<String, Object>> queryCategoryAnalysisListByCategoryStruct(
			final CommodityAnalysisVo vo) {
		//该二级分类下的结果缓存5分钟，防止频繁查询
		List<Map<String, Object>> list = 
				(List<Map<String, Object>>)redisTemplate.opsForHash()
				.get(CacheConstant.C_MERCHANT_THREESTRUCT_SUMDATA, vo.getMerchantCode()+"_"+vo.getSecondCategory());
		if(CollectionUtils.isNotEmpty(list)){
			//对list排序，默认升序
			sort(list,vo);
			return list;
		}
		list = reportManagementSurveyMapper.queryCategoryAnalysisListByStructCode(vo);
		redisTemplate.opsForHash().put(CacheConstant.C_MERCHANT_THREESTRUCT_SUMDATA, 
				vo.getMerchantCode()+"_"+vo.getSecondCategory(), list);
		redisTemplate.expire(CacheConstant.C_MERCHANT_THREESTRUCT_SUMDATA, 5, TimeUnit.MINUTES);
		return list;
	}
	
	//对list排序，默认升序
	private void sort(List<Map<String, Object>> list,final CommodityAnalysisVo vo) {
		Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> map1, Map<String, Object> map2) {
            	//以防空指针
            	if(!(map1.containsKey(vo.getSortBy()))){
            		map1.put(vo.getSortBy(), 0);
            	}
            	if(!(map2.containsKey(vo.getSortBy()))){
            		map2.put(vo.getSortBy(), 0);
            	}
            	return NumberUtils.compare(((Number)(map1.get(vo.getSortBy()))).longValue(), 
            			((Number)(map2.get(vo.getSortBy()))).longValue());
            }
        });
		if(vo.getSortDirection()==0){
			Collections.reverse(list);
		}
	}

	@Override
	public List<Map<String, Object>> queryExportCategoryAnalysisList(
			CommodityAnalysisVo vo) {
		return reportManagementSurveyMapper.queryExportCategoryAnalysisList(vo);
	}
	
	@Override
	public List<Map<String, Object>> queryExportCommodityAnalysisList(
			CommodityAnalysisVo vo) {
		return reportManagementSurveyMapper.queryExportCommodityAnalysisList(vo);
	}
	
	@Override
	public int queryCommodityAnalysisListCount(CommodityAnalysisVo vo) {
		return reportManagementSurveyMapper.queryCommodityAnalysisListCount(vo);
	}

	@Override
	public List<Map> queryManageSurveyIndex() {
		// TODO Auto-generated method stub
		return mmsdbReportQueryMapper.queryManageSurveyIndex();
	}

	@Override
	public void updateAnalysisCommodityNumAndAmt() {
		// TODO Auto-generated method stub
		reportManagementSurveyMapper.updateAnalysisCommodityNumAndAmt();
	}

	@Override
	public void insertSupplierServeyIndex(Map<String,Object> params) {
		// TODO Auto-generated method stub
		if(params != null && params.get("startDate") != null && params.get("endDate") != null){
			Date startDate =  (Date) params.get("startDate");
			Date endDate =  (Date) params.get("endDate");
			int diff = DateUtil.diffDate(endDate,startDate);
			if(diff >= 0){
				for(int i=0;i<=diff;i++){
					String queryDate = DateUtil.formatDate(DateUtil.addDate(startDate, i),"yyyy-MM-dd");
					setAndInsertSurveyIndex(queryDate);
				}	
			}
		}else{
			setAndInsertSurveyIndex(null);
		}
	}
	
	/**
	 * 查询封装经营概况指标，并插入到数据库
	 * @param queryDate
	 */
	private void setAndInsertSurveyIndex(String queryDate) {
		//收订订单指标
		List<Map<String,Object>> orderIndexList = mmsdbReportQueryMapper.querySupplierOrderIndex(queryDate);
		//发货订单指标
		List<Map<String,Object>> deliveryOrderIndexList = mmsdbReportQueryMapper.querySupplierDeliveryOrderIndex(queryDate);
		//商品分析指标
		List<Map<String,Object>> commodityAnalysisIndexList = reportManagementSurveyMapper.querySupplierCommodityAnalysisIndex(queryDate);
		//支付订单指标
		List<Map<String,Object>> payedOrderIndexList = reportManagementSurveyMapper.querySupplierPayedOrderIndex(queryDate);
		
		if(commodityAnalysisIndexList != null && commodityAnalysisIndexList.size() > 0){
			for(Map<String,Object> commodityAnalysisIndex:commodityAnalysisIndexList){
				String merchantCode = (String) commodityAnalysisIndex.get("merchantCode");
				//整合收订订单指标至商品分析指标中
				Map<String,Object> orderIndex = getSupplierIndex(orderIndexList, merchantCode);
				if(orderIndex !=null && orderIndex.size() >0){
					commodityAnalysisIndex.putAll(orderIndex);
				}else{
					commodityAnalysisIndex.put("orderNum",0);
					commodityAnalysisIndex.put("totalAmt",0);
				}
				//整合发货订单指标至商品分析指标中
				Map<String,Object> deliveryOrderIndex = getSupplierIndex(deliveryOrderIndexList, merchantCode);
				if(deliveryOrderIndex !=null && deliveryOrderIndex.size() >0){
					commodityAnalysisIndex.putAll(deliveryOrderIndex);
				}else{
					commodityAnalysisIndex.put("deliveryOrderNum",0);
					commodityAnalysisIndex.put("deliveryOrderAmt",0);
				}
				//整合支付订单指标至商品分析指标中
				Map<String,Object> payedOrderIndex = getSupplierIndex(payedOrderIndexList, merchantCode);
				if(payedOrderIndex !=null && payedOrderIndex.size() >0){
					commodityAnalysisIndex.putAll(payedOrderIndex);
				}else{
					commodityAnalysisIndex.put("payedOrderNum",0);
					commodityAnalysisIndex.put("payedOrderAmt",0);						
					commodityAnalysisIndex.put("payedOrderAvgAmt",0);
				}
				
				//转化率=收订订单数/商家所有商品的访次数量
				Integer orderNum = (Integer) (commodityAnalysisIndex.get("orderNum") == null ? 0 : commodityAnalysisIndex.get("orderNum"));
				Integer visitCount = (Integer)(commodityAnalysisIndex.get("visitCount") == null ? 0 : commodityAnalysisIndex.get("visitCount")) ;
				double changePercent = 0; 
				if(visitCount >0){
					changePercent = (double)orderNum / visitCount;					
				}
				changePercent = handUpHalfDouble(changePercent*100);
				commodityAnalysisIndex.put("changePercent",changePercent);
				//发货率=已发货订单件数/已支付订单件数
				Integer deliveryOrderNum = (Integer) (commodityAnalysisIndex.get("deliveryOrderNum") == null ? 0 : commodityAnalysisIndex.get("deliveryOrderNum"));
				Integer payedOrderNum = (Integer)(commodityAnalysisIndex.get("payedOrderNum") == null ? 0 : commodityAnalysisIndex.get("payedOrderNum")) ;
				double deliveryPercent = 0; 
				if(payedOrderNum >0){
					deliveryPercent = (double)deliveryOrderNum / payedOrderNum;					
				}
				deliveryPercent = handUpHalfDouble(deliveryPercent*100);
				commodityAnalysisIndex.put("deliveryPercent",deliveryPercent);
				
			}			
			reportManagementSurveyMapper.insertSupplierSurveyIndex(commodityAnalysisIndexList);
		}
	}
	
	/**
	 * 传入经营概况指标集合，商家编码，返回商家在指标集合中的所有指标Map 对象
	 * @param indexList
	 * @param merchantCode
	 * @return
	 */
	private Map<String,Object> getSupplierIndex(List<Map<String,Object>> indexList,String merchantCode){
		if(indexList != null && indexList.size() > 0){
			for(Map<String,Object> indexMap : indexList){
				String indexMerchantCode = (String) indexMap.get("merchantCode");
				if(!StringUtil.isBlank(indexMerchantCode) && !StringUtil.isBlank(merchantCode) && merchantCode.equals(indexMerchantCode)){
					indexMap.remove("merchantCode");
					return indexMap;
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		Date startDate =  new Date();
		Date endDate =   DateUtil.addDate(new Date(),3);
		int diff = DateUtil.diffDate(endDate,startDate );
		System.out.println(diff+"=="+ DateUtil.formatDate(endDate,"yyyy-MM-dd"));
		int i =45;int j=1899;
		double k = (double)i/j;
		System.out.println(k);
		k = handUpHalfDouble(k);
		System.out.println(k);
	}

	@Override
	public Map newCalculateSupplierTotalIndex(Map params) {
		Map<String,Object> supplierSurveyIndex = reportManagementSurveyMapper.selectSupplierSurveyIndex(params);
		Map<String,Object> supplierDeleveryIndex = mmsdbReportQueryMapper.queryDeleveryOrderByParams(params);
		//将发货订单金额，发货订单数 整合至经营概况指标Map
		if(supplierSurveyIndex !=null && supplierSurveyIndex.size() >0){
			if(supplierDeleveryIndex !=null && supplierDeleveryIndex.size() >0){
				supplierSurveyIndex.putAll(supplierDeleveryIndex);
			}else{
				supplierSurveyIndex.put("deliveryOrderNum", 0);
				supplierSurveyIndex.put("deliveryOrderAmt", 0);
			}
			
			//计算发货率=发货订单数/支付订单数
			Integer payedOrderNum = (Integer) (supplierSurveyIndex.get("payedOrderNum") == null ? 0 : supplierSurveyIndex.get("payedOrderNum"));
			Integer deliveryOrderNum = (Integer) (supplierSurveyIndex.get("deliveryOrderNum") == null ? 0 : supplierSurveyIndex.get("deliveryOrderNum"));
			double deliveryPercent = 0;
			if(payedOrderNum > 0){
				deliveryPercent = (double)deliveryOrderNum / payedOrderNum;
			}
			deliveryPercent= handUpHalfDouble(deliveryPercent*100);
			supplierSurveyIndex.put("deliveryPercent", deliveryPercent);
		//没有查到商家经营概况指标信息，则将页面选择的指标默认为0返回	
		}else{
			//取出需要统计的指标名称
			List<String> manageServeyIndex = (List<String> ) params.get("manageServeyIndex");						
			if(manageServeyIndex !=null && manageServeyIndex.size() > 0){
				supplierSurveyIndex = new HashMap<String,Object>();
				for(String indexName : manageServeyIndex){
					supplierSurveyIndex.put(indexName, 0);
				}
			}else{
				logger.error("商家编码：{},新的方式查询经营概况指标，未指定任何指标！",YmcThreadLocalHolder.getMerchantCode());
			}
		}		
		return supplierSurveyIndex;
	}

	@Override
	public Map newQueryEveryDayOrHourIndex(Map params) {
		// TODO Auto-generated method stub
		//申明返回对象
		Map result = new HashMap();
		String firstIndexName = (String) (params.get("firstIndexName") == null ? Constant.INDEX_NAME_VISIT_COUNT : params.get("firstIndexName")); //第一个指标名称，非空,默认访次
		String secondIndexName = (String) params.get("secondIndexName"); // 第二个指标名称  或空
		
		//取出查询的起止时间，计算出时间间隔天数
		String startDate = (String) params.get("startDate");
		String endDate =  (String) params.get("endDate");
		//没有查询起止时间，默认查询最近7天的数据
		if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){
			Date date = new Date();
			startDate = DateUtil.format(DateUtil.addDate(date, -7),"yyyy-MM-dd");
			endDate = DateUtil.format(DateUtil.addDate(date, -1),"yyyy-MM-dd");
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		
		List<Map<String,Object>> surverIndexList = reportManagementSurveyMapper.selectSupplierEveryDaySurveyIndex(params);
		
		//申明返回数据集合 ，存放每天的指标数据 -- 第一个指标 ；数据格式 [[日期1毫秒数,指标值1],[日期2毫秒数,指标值2]....]
		List<Object> firstIndexList = new ArrayList<Object>();	
		List<Object> secondIndexList = null;
		if(!StringUtil.isBlank(secondIndexName)){
			secondIndexList = new ArrayList<Object>();
		}	
		
		Date start = DateUtil.parseDate(startDate, "yyyy-MM-dd");
		Date end = DateUtil.parseDate(endDate, "yyyy-MM-dd");
		int diff = DateUtil.diffDate( end,start);
		//从起始时间开始，直到结束日期，得到每天的具体日期，以便折线图进行每天数据展示		
		for(int i=0;i<diff+1;i++){
			Date date =DateUtil.addDate(start, i);
			//日期对应指标值的数组对象
			Object[] objArray = new Object[2];
			//获取日期的毫秒数,页面展示时日期显示会自动往前一天，所以此处将日期加1天，使其与查询日期相同显示
			long dateMin = DateUtil.getMillis(DateUtil.addDate(date, 1));	
			objArray[0] = dateMin;
			setSurveyIndexByDate(firstIndexName, surverIndexList, date,objArray);
			//封装第一个经营指标
			firstIndexList.add(objArray);
			//封装第二个经营指标
			if(!StringUtil.isBlank(secondIndexName)){
				Object[] secObjArray = new Object[2];
				secObjArray[0] = dateMin;
				setSurveyIndexByDate(secondIndexName, surverIndexList, date,secObjArray);
				secondIndexList.add(secObjArray);
			}
		}		
		result.put("firstIndex", firstIndexList);
		result.put("secondIndex", secondIndexList);			
		return result;
	}
	
	/**
	 * 根据指定日期获取经营概况指标值，并装入Object[] 
	 * @param firstIndexName
	 * @param surverIndexList
	 * @param date
	 * @param objArray
	 */
	private void setSurveyIndexByDate(String indexName,
			List<Map<String, Object>> surverIndexList, Date date,
			Object[] objArray) {
		String dateStr = DateUtil.formatDate(date, "yyyy-MM-dd");
		if(surverIndexList != null && surverIndexList.size() > 0){
			boolean flag = false;
			for(Map<String,Object> indexMap : surverIndexList){
				String reportDate = (String) indexMap.get("reportDate");
				if(dateStr.equals(reportDate)){
					Object firstIndexVal = indexMap.get(indexName) == null ? 0 : indexMap.get(indexName);
					objArray[1] = firstIndexVal;
					flag = true;
					break;
				}
			}
			//没有取到此天指标值，默认为0
			if(!flag){
				objArray[1] = 0;
			}
		//没有取到此天指标值，默认为0	
		}else{
			objArray[1] = 0;
		}
	}

	@Override
	public void updateNewSaleCommodityNum() {
		// TODO Auto-generated method stub
		reportManagementSurveyMapper.updateNewSaleCommodityNum();
	}
}
