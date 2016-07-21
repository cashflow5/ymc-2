package com.yougou.kaidian.bi.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.yougou.kaidian.bi.dao.MMSDBReportQueryMapper;
import com.yougou.kaidian.bi.dao.ReportRealTimeStatisticsMapper;
import com.yougou.kaidian.bi.service.IReportRealTimeStatisticsService;
import com.yougou.kaidian.bi.vo.CommoditySaleRank;
import com.yougou.kaidian.bi.vo.RealTimeStatisticsVo;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.tools.common.utils.DateUtil;

@Service
public class ReportRealTimeStatisticsServiceImpl implements IReportRealTimeStatisticsService{
	
	@Resource
	private ReportRealTimeStatisticsMapper reportRealTimeStatisticsMapper;
	
	@Resource	
	private MMSDBReportQueryMapper mmsdbReportQueryMapper;
	
	@Override
	public void initOrderInfo(RealTimeStatisticsVo realTimeStatisticsVo)
			throws Exception {
		// 查询参数
		Map<String,String> params = new HashMap<String,String>();
		
		String rootCat = realTimeStatisticsVo.getRootCattegory(); // 一级分类
		String secodeCat = realTimeStatisticsVo.getSecondCategory(); // 二级分类
		String threeCat = realTimeStatisticsVo.getThreeCategory(); // 三级分类
		
		String catNo = null;
		// 分类查询条件从大到小
		if(StringUtils.isNotEmpty(threeCat)){
			catNo = threeCat;
		}else{
			if(StringUtils.isNotEmpty(secodeCat)){
				catNo = secodeCat;
			}else{
				catNo = rootCat;
			}
		}
		params.put("catNo",catNo);
		params.put("brandNo",realTimeStatisticsVo.getBrandNo());
		
		Date date = setOrderRealTimeIndex(realTimeStatisticsVo, params);
		
		
		// 封装查询今日每小时订单信息参数
		params.put("startDate",DateUtil.format(date, "yyyy-MM-dd"));
		params.put("endDate", DateUtil.format(DateUtil.addDaysToDate(date,1), "yyyy-MM-dd"));
		List<Map> todayList = reportRealTimeStatisticsMapper.queryOrderInfoByHour(params);
		
		// 封装查询昨日每小时订单信息参数
		params.put("startDate",DateUtil.format(DateUtil.addDaysToDate(date,-1), "yyyy-MM-dd"));
		params.put("endDate", DateUtil.format(date, "yyyy-MM-dd"));
		List<Map> yesterdayList = reportRealTimeStatisticsMapper.queryOrderInfoByHour(params);
		
		
		// 封装查询上周同天每小时订单信息参数
		params.put("startDate",DateUtil.format(DateUtil.addDaysToDate(date,-7), "yyyy-MM-dd"));
		params.put("endDate", DateUtil.format(DateUtil.addDaysToDate(date,-6), "yyyy-MM-dd"));
		List<Map> lastWeekList = reportRealTimeStatisticsMapper.queryOrderInfoByHour(params);
		
		//今日订单数24小时数据集合
		int[] todayHourlyOrder = new int[24];
		//昨日订单数24小时数据集合
		int[] yesterdayHourlyOrder = new int[24];
		//上周同天订单数24小时数据集合
		int[] lastWeekSameDayHourlyOrder = new int[24];
		
		//今日订单金额24小时数据集合
		int[] todayHourlyAmount = new int[24];
		//昨日订单金额24小时数据集合
		int[] yesterdayHourlyAmount = new int[24];
		//上周同天订单金额24小时数据集合
		int[] lastWeekSameDayHourlyAmount = new int[24];
		
		//今日订单平均金额24小时数据集合
		int[] todayHourlyAveragePrice = new int[24];
		//昨日订单平均金额24小时数据集合
		int[] yesterdayHourlyAveragePrice = new int[24];
		//上周同天订单平均金额24小时数据集合
		int[] lastWeekSameDayHourlyAveragePrice = new int[24];
		
		//迭起封装今日24小时订单数，订单金额，订单均价 数据至相关集合
		if(todayList !=null && todayList.size() > 0){
			for(Map map : todayList){
				Integer hour = (Integer)  map.get("orderHour");// 订单所属时段
				Integer orderAmt = (Integer) Integer.parseInt( String.valueOf((map.get("orderAmt") == null ? 0 : map.get("orderAmt"))) ); // 订单金额
				Integer orderNums = (Integer) (map.get("orderNums") == null ? 0 : map.get("orderNums")); // 订单数量
				Integer avgOrderAmt = (Integer) Integer.parseInt( String.valueOf((map.get("avgOrderAmt") == null ? 0 : map.get("avgOrderAmt"))) ); // 订单均价
				
				if(hour != null){
					todayHourlyOrder[hour] = orderNums;
					todayHourlyAmount[hour] = orderAmt;
					todayHourlyAveragePrice[hour] = avgOrderAmt;
				}
			}
		}		

		//迭起封装昨日日24小时订单数，订单金额，订单均价 数据至相关集合
		if(yesterdayList !=null && yesterdayList.size() > 0){
			for(Map map : yesterdayList){
				Integer hour = (Integer)  map.get("orderHour");// 订单所属时段
				Integer orderAmt = (Integer) Integer.parseInt( String.valueOf((map.get("orderAmt") == null ? 0 : map.get("orderAmt"))) ); // 订单金额
				Integer orderNums = (Integer) (map.get("orderNums") == null ? 0 : map.get("orderNums")); // 订单数量
				Integer avgOrderAmt = (Integer)  Integer.parseInt( String.valueOf((map.get("avgOrderAmt") == null ? 0 : map.get("avgOrderAmt"))) ); // 订单均价
				
				if(hour != null){
					yesterdayHourlyOrder[hour] = orderNums;
					yesterdayHourlyAmount[hour] = orderAmt;
					yesterdayHourlyAveragePrice[hour] = avgOrderAmt;
				}
			}
		}
		
		//迭起封装昨日日24小时订单数，订单金额，订单均价 数据至相关集合
		if(lastWeekList !=null && lastWeekList.size() > 0){
			for(Map map : lastWeekList){
				Integer hour = (Integer)  map.get("orderHour");// 订单所属时段
				Integer orderAmt = (Integer) Integer.parseInt( String.valueOf((map.get("orderAmt") == null ? 0 : map.get("orderAmt"))) ); // 订单金额
				Integer orderNums = (Integer) (map.get("orderNums") == null ? 0 : map.get("orderNums")); // 订单数量
				Integer avgOrderAmt = (Integer)  Integer.parseInt( String.valueOf((map.get("avgOrderAmt") == null ? 0 : map.get("avgOrderAmt"))) ); // 订单均价
				
				if(hour != null){
					lastWeekSameDayHourlyOrder[hour] = orderNums;
					lastWeekSameDayHourlyAmount[hour] = orderAmt;
					lastWeekSameDayHourlyAveragePrice[hour] = avgOrderAmt;
				}
			}
		}
		realTimeStatisticsVo.setTodayHourlyOrder(Arrays.toString(todayHourlyOrder));
		realTimeStatisticsVo.setYesterdayHourlyOrder(Arrays.toString(yesterdayHourlyOrder));
		realTimeStatisticsVo.setLastWeekSameDayHourlyOrder(Arrays.toString(lastWeekSameDayHourlyOrder));
		
		realTimeStatisticsVo.setTodayHourlyAmount(Arrays.toString(todayHourlyAmount));
		realTimeStatisticsVo.setYesterdayHourlyAmount(Arrays.toString(yesterdayHourlyAmount));
		realTimeStatisticsVo.setLastWeekSameDayHourlyAmount(Arrays.toString(lastWeekSameDayHourlyAmount));
		
		realTimeStatisticsVo.setTodayHourlyAveragePrice(Arrays.toString(todayHourlyAveragePrice));
		realTimeStatisticsVo.setYesterdayHourlyAveragePrice(Arrays.toString(yesterdayHourlyAveragePrice));
		realTimeStatisticsVo.setLastWeekSameDayHourlyAveragePrice(Arrays.toString(lastWeekSameDayHourlyAveragePrice));		
	}
	
	/**
	 * 计算今天订单实时指标数据
	 * @param realTimeStatisticsVo
	 * @param params
	 * @return
	 */
	private Date setOrderRealTimeIndex(
			RealTimeStatisticsVo realTimeStatisticsVo,
			Map<String, String> params) {
		// 封装查询当前时间订单信息的参数		
		Date date = new Date();
		params.put("merchantCode", YmcThreadLocalHolder.getMerchantCode());
		params.put("startDate",DateUtil.format(date, "yyyy-MM-dd"));
		params.put("endDate", DateUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
		RealTimeStatisticsVo realTimeOrderInfo = reportRealTimeStatisticsMapper.queryRealTimeOrderInfo(params);
		
		// 封装查询昨日当前时间订单信息的参数
		params.put("startDate",DateUtil.format(DateUtil.addDaysToDate(date,-1), "yyyy-MM-dd"));
		params.put("endDate", DateUtil.format(DateUtil.addDaysToDate(date,-1), "yyyy-MM-dd HH:mm:ss"));
		RealTimeStatisticsVo yesdRealTimeOrderInfo = reportRealTimeStatisticsMapper.queryRealTimeOrderInfo(params);
		
		// 封装查询昨日当前时间之后订单信息的参数
		params.put("startDate",DateUtil.format(DateUtil.addDaysToDate(date,-1), "yyyy-MM-dd HH:mm:ss"));
		params.put("endDate", DateUtil.format(date, "yyyy-MM-dd"));
		RealTimeStatisticsVo yesdAfterTimeOrderInfo = reportRealTimeStatisticsMapper.queryRealTimeOrderInfo(params);
		
		// 封装查询昨日全天订单信息的参数
		params.put("startDate",DateUtil.format(DateUtil.addDaysToDate(date,-1), "yyyy-MM-dd"));
		params.put("endDate", DateUtil.format(date, "yyyy-MM-dd"));
		RealTimeStatisticsVo yesdAllDayOrderInfo = reportRealTimeStatisticsMapper.queryRealTimeOrderInfo(params);
		
		// ----------------------------订单数相关计算Start------------------------------------
		//今日支付订单数
		realTimeStatisticsVo.setPayedOrder(realTimeOrderInfo==null ? 0 : realTimeOrderInfo.getPayedOrder());
		//预计今日支付订单数
		realTimeStatisticsVo.setExpectPayedOrder( (realTimeOrderInfo==null ? 0 : realTimeOrderInfo.getPayedOrder()) 
				+ (yesdAfterTimeOrderInfo == null ? 0: yesdAfterTimeOrderInfo.getPayedOrder() ));
		//比昨日此时
		double d = ( (double) (realTimeOrderInfo == null ? 0 : realTimeOrderInfo.getPayedOrder() )
					-(double)(yesdRealTimeOrderInfo ==null ? 0 : yesdRealTimeOrderInfo.getPayedOrder()) ) 
					/ (double) (yesdRealTimeOrderInfo ==null ? 1 : yesdRealTimeOrderInfo.getPayedOrder()); // 分母不能为零，没有数据分母为1

		
		BigDecimal bd = formatToBigDecimal(d*100);
		realTimeStatisticsVo.setCompareYesterdayOrder(bd);
		// 昨日全天
		realTimeStatisticsVo.setYesterdayOrder(yesdAllDayOrderInfo == null ? 0 : yesdAllDayOrderInfo.getPayedOrder());
		//预计比昨日
		double d2 = ( (double) (realTimeStatisticsVo == null ? 0 : realTimeStatisticsVo.getExpectPayedOrder() )
				- (double) (yesdAllDayOrderInfo == null ? 0 : yesdAllDayOrderInfo.getPayedOrder()) ) 
				/ (double) (yesdAllDayOrderInfo == null ? 1 : yesdAllDayOrderInfo.getPayedOrder() ) ; // 分母不能为零，没有数据分母为1
		BigDecimal expBd = formatToBigDecimal(d2*100);
		realTimeStatisticsVo.setExpectCompareYesterdayOrder(expBd);
		// ----------------------------订单数相关计算end------------------------------------
		
		// ----------------------------订单支付金额相关计算Start------------------------------------
		//今日支付订单金额
		realTimeStatisticsVo.setPayedAmount(realTimeOrderInfo==null ? 0 : realTimeOrderInfo.getPayedAmount());
		//预计今日支付订金额
		realTimeStatisticsVo.setExpectPayedAmount( (realTimeOrderInfo==null ? 0 : realTimeOrderInfo.getPayedAmount()) 
				+ (yesdAfterTimeOrderInfo == null ? 0: yesdAfterTimeOrderInfo.getPayedAmount() ));
		//比昨日此时
		double pd = ( (double) (realTimeOrderInfo == null ? 0 : realTimeOrderInfo.getPayedAmount() )
					-(double)(yesdRealTimeOrderInfo ==null ? 0 : yesdRealTimeOrderInfo.getPayedAmount()) ) 
					/ (double) (yesdRealTimeOrderInfo ==null ? 1 : yesdRealTimeOrderInfo.getPayedAmount() == 0 ? 1:yesdRealTimeOrderInfo.getPayedAmount()); // 分母不能为零，没有数据分母为1

		/*double a1 = (double) (realTimeOrderInfo == null ? 0 : realTimeOrderInfo.getPayedAmount());
		double a2 = (double)(yesdRealTimeOrderInfo ==null ? 0 : yesdRealTimeOrderInfo.getPayedAmount())  ;
		double a3 = (double) (yesdRealTimeOrderInfo ==null ? 1 : yesdRealTimeOrderInfo.getPayedAmount() == 0 ? 1:yesdRealTimeOrderInfo.getPayedAmount());
		System.out.println("---------------"+(a1-a2)/a3);*/
		BigDecimal pdec = formatToBigDecimal(pd*100);
		realTimeStatisticsVo.setCompareYesterdayAmount(pdec);
		// 昨日全天
		realTimeStatisticsVo.setYesterdayAmount(yesdAllDayOrderInfo == null ? 0 : yesdAllDayOrderInfo.getPayedAmount());
		//预计比昨日
		double pd2 = ( (double) (realTimeStatisticsVo == null ? 0 : realTimeStatisticsVo.getExpectPayedAmount() )
				- (double) (yesdAllDayOrderInfo == null ? 0 : yesdAllDayOrderInfo.getPayedAmount()) ) 
				/ (double) (yesdAllDayOrderInfo == null ? 1 : yesdAllDayOrderInfo.getPayedAmount() == 0 ? 1 :  yesdAllDayOrderInfo.getPayedAmount() ) ; // 分母不能为零，没有数据分母为1
		BigDecimal exPd = formatToBigDecimal(pd2*100);
		realTimeStatisticsVo.setExpectCompareYesterdayAmount(exPd);
		// ----------------------------订单支付金额相关计算end------------------------------------
		
		// ----------------------------订单均价相关计算Start------------------------------------
		//今日订单均价
		realTimeStatisticsVo.setPayedAveragePrice(realTimeOrderInfo==null ? 0 : realTimeOrderInfo.getPayedAveragePrice());
		//预计今日订单均价 = 预计今日订单金额/预计今日订单数量
		realTimeStatisticsVo.setExpectPayedAveragePrice( realTimeStatisticsVo.getExpectPayedAmount() 
				/ (realTimeStatisticsVo.getExpectPayedOrder()== null || realTimeStatisticsVo.getExpectPayedOrder() == 0 ? 1: realTimeStatisticsVo.getExpectPayedOrder()  ) );
		//比昨日此时
		double pd3 = ( (double) (realTimeOrderInfo == null ? 0 : realTimeOrderInfo.getPayedAveragePrice() )
					-(double)(yesdRealTimeOrderInfo ==null ? 0 : yesdRealTimeOrderInfo.getPayedAveragePrice()) ) 
					/ (double) (yesdRealTimeOrderInfo ==null ? 1 : yesdRealTimeOrderInfo.getPayedAveragePrice() ==0 ? 1 : yesdRealTimeOrderInfo.getPayedAveragePrice()); // 分母不能为零，没有数据分母为1

		
		BigDecimal avgBd = formatToBigDecimal(pd3*100);
		realTimeStatisticsVo.setCompareYesterdayAveragePrice(avgBd);
		// 昨日全天
		realTimeStatisticsVo.setYesterdayAveragePrice(yesdAllDayOrderInfo == null ? 0 : yesdAllDayOrderInfo.getPayedAveragePrice());
		//预计比昨日
		double pd4 = ( (double) (realTimeStatisticsVo == null ? 0 : realTimeStatisticsVo.getExpectPayedAveragePrice() )
				- (double) (yesdAllDayOrderInfo == null ? 0 : yesdAllDayOrderInfo.getPayedAveragePrice()) ) 
				/ (double) (yesdAllDayOrderInfo == null ? 1 : yesdAllDayOrderInfo.getPayedAveragePrice() ==0 ? 1 : yesdAllDayOrderInfo.getPayedAveragePrice() ) ; // 分母不能为零，没有数据分母为1
		BigDecimal avgPd = formatToBigDecimal(pd4*100);
		realTimeStatisticsVo.setExpectCompareYesterdayAveragePrice(avgPd);
		// ----------------------------订单均价相关计算end------------------------------------
		return date;
	}

	/**
	 * 将double 转换为BigDecimal 四舍五入，保留2位小数
	 * @param d
	 * @return
	 */
	private BigDecimal formatToBigDecimal(double d){
		/*DecimalFormat df = new DecimalFormat("0"); // 保留几位小数
		String fd = df.format(d);
		BigDecimal bd = new BigDecimal(fd);*/
		BigDecimal b = new BigDecimal(d);
		double dval = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		long lon = Math.round(dval);		
		return  new BigDecimal(lon);
	}

	@Override
	public PageFinder<CommoditySaleRank> querycommoditySaleRanks(
			RealTimeStatisticsVo realTimeStatisticsVo, Query query)
			throws Exception {
		Map params = new HashMap();
		params.put("realTimeStatisticsVo", realTimeStatisticsVo);
		params.put("query", query);
		String rootCat = realTimeStatisticsVo.getRootCattegory(); // 一级分类
		String secodeCat = realTimeStatisticsVo.getSecondCategory(); // 二级分类
		String threeCat = realTimeStatisticsVo.getThreeCategory(); // 三级分类
		
		String catNo = null;
		// 分类查询条件从大到小
		if(StringUtils.isNotEmpty(threeCat)){
			catNo = threeCat;
		}else{
			if(StringUtils.isNotEmpty(secodeCat)){
				catNo = secodeCat;
			}else{
				catNo = rootCat;
			}
		}
		params.put("catNo",catNo);
		params.put("brandNo",realTimeStatisticsVo.getBrandNo());
		params.put("merchantCode", YmcThreadLocalHolder.getMerchantCode());
		
		List<CommoditySaleRank> commoditySaleRanks = reportRealTimeStatisticsMapper.queryCommodiySaleNumsList(params);
		// 迭代列表集合对象，并封装最近7天内的销量数据
		if (commoditySaleRanks != null && commoditySaleRanks.size() > 0){		
			List<String> commodityNos = new ArrayList<String>();
			for(CommoditySaleRank saleRank : commoditySaleRanks){
				List<Map> list = saleRank.getSaleNums();
				int[] lastSevenDayPayedProduct = new int[7];
				if(list!=null && list.size() > 0){
					for(Map map : list){
						int diffdate = (Integer) map.get("diffdate");//距离当天相差天数
						int salecommoditynum = (Integer) map.get("salecommoditynum"); // 销售量
						//依次倒叙塞入数组内
						lastSevenDayPayedProduct[lastSevenDayPayedProduct.length-1-diffdate] = salecommoditynum;
					}
				}				
				saleRank.setLastSevenDayPayedProduct(Arrays.toString(lastSevenDayPayedProduct));	
				commodityNos.add(saleRank.getProductNo());
			}
			
			Map<String,Object> fparams = new HashMap<String,Object>();
			fparams.put("merchantCode", YmcThreadLocalHolder.getMerchantCode());
			fparams.put("loginName", YmcThreadLocalHolder.getOperater());
			fparams.put("commodityNos", commodityNos);
			//查询商品归类数量
			List<Map<String,Object>> resultList = mmsdbReportQueryMapper.queryFavoriteClassifyByCommodityNos(fparams);
			//将商品归类数量添加到页面列表结果集中，以便判断展示收藏图标
			if(resultList != null && resultList.size() > 0){
				for(CommoditySaleRank saleRank : commoditySaleRanks){
					String commodityNo = saleRank.getProductNo();
					boolean flag = false;
					for(Map<String,Object> fmap : resultList){
						String commodityNof = (String) fmap.get("commodity_no");
						if(commodityNo.equals(commodityNof)){
							flag = true;
							int fcount = (Integer) fmap.get("fcount");
							saleRank.setFcount(fcount);
							break;
						}
					}
					if(!flag){
						saleRank.setFcount(0);
					}
				}
			}
		}
		int count = reportRealTimeStatisticsMapper.queryCommoditySaleNumsTotalRow(params);
		PageFinder<CommoditySaleRank> pageFinder = new PageFinder<CommoditySaleRank>(query.getPage(), query.getPageSize(), count, commoditySaleRanks);
		return pageFinder;
	}
	
	public static void main(String[] args) {
		double   f   =   111231.5585;
		BigDecimal   b   =   new   BigDecimal(f);
		System.out.println(b.setScale(2,   BigDecimal.ROUND_HALF_UP));
		double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println(f1);
		long lon = Math.round(f1);
		System.out.println(lon); 
	}

	@Override
	public void queryRealTimeIndex(RealTimeStatisticsVo realTimeStatisticsVo)
			throws Exception {
		// TODO Auto-generated method stub
		Map params = new HashMap();
		this.setOrderRealTimeIndex(realTimeStatisticsVo, params);
	}

	
}
