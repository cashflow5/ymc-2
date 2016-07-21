package com.yougou.kaidian.bi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.bi.vo.CommodityAnalysisVo;

public interface ReportManagementSurveyMapper {
	
	/**
	 * 根据商家编码查询时间段内的汇总指标数据
	 * @param params
	 * @return
	 */
	Map queryTotalIndexByParams(Map params);
	
	/**
	 * 按小时或者按天统计商品指标，以便做折线图展示
	 * @param params
	 * @return
	 */
	List<Map> queryEveryDayOrHourIndex(Map params);
	
	/**
	 * 统计商家收订订单数据
	 * @param params
	 * @return
	 */
	//Map queryOrderByParams(Map params);
	
	/**
	 * 统计商家支付商品件数，支付金额
	 * @param params
	 * @return
	 */
	Map queryPayedCommodityByParams(Map params);
	
	/**
	 * 统计商家支付订单数，订单金额，订单均价
	 * @param params
	 * @return
	 */
	Map queryPayedOrderByParams(Map params);
	
	/**
	 * 查询指定日期内商家每天支付的订单数，订单金额，定均价，以便做折线图展示
	 * @param params
	 * @return
	 */
	List<Map> queryEveryDayPayedOrderByParams(Map params);
	
	/**
	 * 查询商家指定时间范围内每天的支付商品数据，金额，均价 ，以便做折线图展示
	 * @param params
	 * @return
	 */
	List<Map> queryEveryDayPayedCommodityByParams(Map params);
	
	/**
	 * 查询商家指定时间范围内每天的新上架商品数  ，以便做折线图展示
	 * @param params
	 * @return
	 */
	List<Map> queryEveryDayNewSaleCommodityByParams(Map params);
	
	/**
	 * 查询商家指定时间范围内每天收订的订单数据  ，以便做折线图展示
	 * @param params
	 * @return
	 */
	//List<Map> queryEveryDayOrderByParams(Map params);
	
	
	/**
	 * queryCommodityAnalysisList:商品分析 
	 * @author li.n1 
	 * @param vo
	 * @param rowBounds
	 * @return 
	 * @since JDK 1.6
	 */
	public List<Map<String, Object>> queryCommodityAnalysisList(
			CommodityAnalysisVo vo, RowBounds rowBounds);

	public Integer queryCommodityAnalysisListCount(CommodityAnalysisVo vo);
	
	/**
	 * querySumOfCommodityAnalysis:商品分析第一行汇总数据 
	 * @author li.n1 
	 * @param vo
	 * @return 
	 * @since JDK 1.6
	 */
	public Map<String,Object> querySumOfCommodityAnalysis(CommodityAnalysisVo vo);

	/**
	 * queryCategoryAnalysisList:品类分析 
	 * @author li.n1 
	 * @param vo
	 * @param rowBounds
	 * @return 
	 * @since JDK 1.6
	 */
	public List<Map<String, Object>> queryCategoryAnalysisList(
			CommodityAnalysisVo vo, RowBounds rowBounds);

	public Integer queryCategoryAnalysisListCount(CommodityAnalysisVo vo);
	/**
	 * querySumOfCategoryAnalysis:品类分析第一行汇总数据  
	 * @author li.n1 
	 * @param vo
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-8-4 上午10:09:42
	 */
	public Map<String, Object> querySumOfCategoryAnalysis(CommodityAnalysisVo vo);
	/**
	 * queryCategoryAnalysisList:获取第三级分类的品类分析数据  
	 * @author li.n1 
	 * @param secondStructCode
	 * @param merchantCode
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-8-4 下午2:29:50
	 */
	public List<Map<String, Object>> queryCategoryAnalysisListByStructCode(
			CommodityAnalysisVo vo);

	public List<Map<String, Object>> queryExportCategoryAnalysisList(
			CommodityAnalysisVo vo);

	public List<Map<String, Object>> queryExportCommodityAnalysisList(
			CommodityAnalysisVo vo);

	int queryExportCommodityAnalysisListCount(CommodityAnalysisVo vo);
	
	/**
	 * 统计新上架商品数
	 * @param params
	 * @return
	 */
	int queryNewSaleCommodityNumByParams(Map params);
	
	/**
	 * 统计更新商品分析Analysis 表商品每天支付件数，支付金额
	 */
	void updateAnalysisCommodityNumAndAmt();
	
	
	/**
	 * 查询商家指定日期的商品分析指标
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> querySupplierCommodityAnalysisIndex(@Param("queryDate")String queryDate);
	
	/**
	 * 查询商家指定日期的支付订单指标
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> querySupplierPayedOrderIndex(@Param("queryDate")String queryDate);
	
	/**
	 * 新增商家经营概况指标
	 */
	void insertSupplierSurveyIndex(@Param("listParams")List<Map<String,Object>> listParams);
	
	/**
	 * 查询商家经营概况指标（新：从提前计算的经营概况指标表中获取数据）
	 * @param params
	 * @return
	 */
	Map<String,Object> selectSupplierSurveyIndex(Map params);
	
	/**
	 * 查询商家每天经营概况指标
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> selectSupplierEveryDaySurveyIndex(Map params);

	/**
	 * job 更新商家经营概况指标中的新上架商品数 added by zhangfeng 2015-12-11
	 */
	void updateNewSaleCommodityNum();

}
