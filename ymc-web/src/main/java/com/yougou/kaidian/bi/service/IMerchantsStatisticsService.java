package com.yougou.kaidian.bi.service;

import com.yougou.kaidian.bi.vo.AfterSaleStatisticsVo;
import com.yougou.kaidian.bi.vo.AfterSaleStatisticsVo.AfterSaleStatisticsVoDetail;
import com.yougou.kaidian.bi.vo.CommoditySalesDetailsVo;
import com.yougou.kaidian.bi.vo.CommoditySalesDetailsVo.CommoditySalesDetailsVoDetail;
import com.yougou.kaidian.bi.vo.SummaryOfOperationsVo;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;

/**
 * 报表中心业务类
 * 
 * @author yang.mq
 *
 */
public interface IMerchantsStatisticsService {

	/**
	 * 注入经营概况数据
	 * 
	 * @param vo
	 * @throws Exception
	 */
	void injectSummaryOfOperationsInto(SummaryOfOperationsVo vo) throws Exception;
	
	/**
	 * 注入商品销售明细数据
	 * 
	 * @param vo
	 * @throws Exception
	 */
	void injectCommoditySalesDetailsInto(CommoditySalesDetailsVo vo, Query query) throws Exception;
	
	/**
	 * 注入售后明细数据
	 * 
	 * @param vo
	 * @throws Exception
	 */
	void injectAfterSaleStatisticsDetailsInfo(AfterSaleStatisticsVo vo) throws Exception;
	
	/**
	 * 查询商品销售明细数据
	 * 
	 * @param vo
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<CommoditySalesDetailsVoDetail> queryCommoditySalesDetailsInfo(CommoditySalesDetailsVo vo, Query query) throws Exception;
	
	/**
	 * 查询无销售商品明细数据
	 * 
	 * @param vo
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<CommoditySalesDetailsVoDetail> queryCommodityNoSalesDetailsInfo(CommoditySalesDetailsVo vo, Query query) throws Exception;
	
	/**
	 * 查询售后明细
	 * 
	 * @param vo
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<AfterSaleStatisticsVoDetail> queryAfterSaleStatisticsDetailsInfo(AfterSaleStatisticsVo vo, Query query) throws Exception;
}
