package com.yougou.kaidian.bi.service;

import com.yougou.kaidian.bi.vo.CommoditySaleRank;
import com.yougou.kaidian.bi.vo.RealTimeStatisticsVo;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;

public interface IReportRealTimeStatisticsService {
	
	/**
	 * 初始化加载订单相关指标信息，包括支付金额，均价，订单数 以及 曲线图 实时数据列表
	 * @param realTimeStatisticsVo
	 * @throws Exception
	 */
	void initOrderInfo(RealTimeStatisticsVo realTimeStatisticsVo) throws Exception;
	
	/**
	 * 单品销售排行
	 * @param realTimeStatisticsVo
	 * @param query
	 * @return
	 * @throws Exception
	 */
	PageFinder<CommoditySaleRank> querycommoditySaleRanks(RealTimeStatisticsVo realTimeStatisticsVo, Query query) throws Exception; 
	
	/**
	 * 查询订单实时指标
	 * @param realTimeStatisticsVo
	 * @throws Exception
	 */
	void queryRealTimeIndex(RealTimeStatisticsVo realTimeStatisticsVo) throws Exception;
}
