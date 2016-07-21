package com.yougou.kaidian.bi.dao;

import java.util.List;
import java.util.Map;

import com.yougou.kaidian.bi.vo.CommoditySaleRank;
import com.yougou.kaidian.bi.vo.RealTimeStatisticsVo;

/**
 * 商家中心数据报表实时概况DAO 持久化映射接口
 * @author zhang.f1
 *
 */
public interface ReportRealTimeStatisticsMapper {
	/**
	 * 查询指定时间段订单实时数据：订单支付金额，订单均价，订单数 
	 * @param params{merchantCode:商家编码,startDate:起始时间,endDate:结束时间,catNo:类别编码}
	 * @return
	 */
	RealTimeStatisticsVo queryRealTimeOrderInfo(Map<String,String> params); 
	
	/**
	 * 查询指定时间段内每小时订单相关数据：订单数，订单金额，订单均价
	 * @param params{merchantCode:商家编码,startDate:起始时间,endDate:结束时间,catNo:类别编码}
	 * @return
	 */
	List<Map> queryOrderInfoByHour(Map<String,String> params);
	
	/**
	 * 单品销售排行商品列表查询
	 * @param params
	 * @return
	 */
	List<CommoditySaleRank> queryCommodiySaleNumsList(Map params);
	
	/**
	 * 
	 * 单品销售排行列表总记录数查询
	 * @return
	 */
	int queryCommoditySaleNumsTotalRow(Map params);
}
