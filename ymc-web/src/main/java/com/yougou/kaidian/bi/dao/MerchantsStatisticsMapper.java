package com.yougou.kaidian.bi.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.bi.vo.AfterSaleStatisticsVo;
import com.yougou.kaidian.bi.vo.CommoditySalesDetailsVo;

public interface MerchantsStatisticsMapper {

	/**
	 * 查询经营概况(订单部分)
	 * 
	 * @param start
	 * @param end
	 * @param merchantCode
	 * @return List
	 */
	List<Map<String, Object>> querySummaryOfOperationsOfOrder(@Param("start")Date start, @Param("end")Date end, @Param("merchantCode")String merchantCode);
	
	/**
	 * 查询经营概况(商品部分)
	 * 
	 * @param start
	 * @param end
	 * @param merchantCode
	 * @return List
	 */
	List<Map<String, Object>> querySummaryOfOperationsOfCommodity(@Param("start")Date start, @Param("end")Date end, @Param("merchantCode")String merchantCode);
	
	/**
	 * 查询商品销售明细总计录数
	 * 
	 * @param vo
	 * @return Integer
	 */
	Integer queryCommoditySalesDetailsCount(@Param("vo")CommoditySalesDetailsVo vo);
	
	/**
	 * 查询商品销售明细
	 * 
	 * @param vo
	 * @return List
	 */
	List<Map<String, Object>> queryCommoditySalesDetails(@Param("vo")CommoditySalesDetailsVo vo, RowBounds rowBounds);
	
	/**
	 * 查询没有销售明细的商品总计录数
	 * 
	 * @param vo
	 * @return Integer
	 */
	Integer queryCommodityNoSalesDetailsCount(@Param("vo")CommoditySalesDetailsVo vo);
	
	/**
	 * 查询没有销售明细的商品
	 * 
	 * @param vo
	 * @param rowBounds
	 * @return List
	 */
	List<Map<String, Object>> queryCommodityNoSalesDetails(@Param("vo")CommoditySalesDetailsVo vo, RowBounds rowBounds);
	
	/**
	 * 查询售后明细总计录数
	 * 
	 * @param vo
	 * @return Integer
	 */
	Integer queryAfterSaleStatisticsDetailsCount(@Param("vo")AfterSaleStatisticsVo vo);
	
	/**
	 * 查询售后明细统计
	 * 
	 * @param vo
	 * @param rowBounds
	 * @return List
	 */
	List<Map<String, Object>> queryAfterSaleStatistics(@Param("vo")AfterSaleStatisticsVo vo);
	
	/**
	 * 查询售后明细
	 * 
	 * @param vo
	 * @param rowBounds
	 * @return List
	 */
	List<Map<String, Object>> queryAfterSaleStatisticsDetails(@Param("vo")AfterSaleStatisticsVo vo, RowBounds rowBounds);
}
