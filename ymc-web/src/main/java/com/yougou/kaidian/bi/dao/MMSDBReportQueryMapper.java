package com.yougou.kaidian.bi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 数据报表查询MMSDB库相关操作都在此接口定义，以便AOP进行拦截自动切换数据源
 * @author zhang.f1
 *
 */
public interface MMSDBReportQueryMapper {
	/**
	 * 统计商家收订订单数据
	 * @param params
	 * @return
	 */
	Map queryOrderByParams(Map params);
	
	/**
	 * 查询商家指定时间范围内每天收订的订单数据  ，以便做折线图展示
	 * @param params
	 * @return
	 */
	List<Map> queryEveryDayOrderByParams(Map params);
	
	/**
	 * 统计商家收订订单数据
	 * @param params
	 * @return
	 */
	Map queryDeleveryOrderByParams(Map params);
	
	/**
	 * 查询商家指定时间范围内每天收订的订单数据  ，以便做折线图展示
	 * @param params
	 * @return
	 */
	List<Map> queryEveryDayDeleveryOrderByParams(Map params);
	
	/**
	 * 查询经营概况指标
	 * @return
	 */
	List<Map> queryManageSurveyIndex();
	
	/**
	 * 查询单品分析下拉指标
	 * @return
	 */
	List<Map<String,Object>> querySingleAnalysisIndex();
	
	/**
	 * 根据商品编码查询出收藏人数
	 * @param commodityNo
	 * @return
	 */
	int queryFavoriteCountByCommodityNo(@Param("commodityNo")String commodityNo);
	
	/**
	 * 根据商品编码查询所属归类数量
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryFavoriteClassifyByCommodityNos(Map<String,Object> params);
	
	/**
	 * 查询商家指定日期的收订订单指标
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> querySupplierOrderIndex(@Param("queryDate")String queryDate);
	
	/**
	 * 查询商家指定日期的发货订单指标
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> querySupplierDeliveryOrderIndex(@Param("queryDate")String queryDate);
	
//	/**
//	 * 新增商家经营概况指标
//	 */
//	void insertSupplierSurveyIndex(@Param("listParams")List<Map<String,Object>> listParams);
//	
//	/**
//	 * 查询商家经营概况指标（新：从提前计算的经营概况指标表中获取数据）
//	 * @param params
//	 * @return
//	 */
//	Map<String,Object> selectSupplierSurveyIndex(Map params);
//	
//	/**
//	 * 查询商家每天经营概况指标
//	 * @param params
//	 * @return
//	 */
//	List<Map<String,Object>> selectSupplierEveryDaySurveyIndex(Map params);

}
