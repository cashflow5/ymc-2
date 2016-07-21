package com.yougou.kaidian.bi.service;

import java.util.List;
import java.util.Map;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.bi.vo.CommodityAnalysisVo;

/**
 * 商家中心数据报表--经营概况 服务层接口
 * @author zhang.f1
 *
 */
public interface IReportManagementSurveyService {
	
	/**
	 * 计算和统计首页经营概况 商家汇总指标(访次，浏览量，支付订单数，支付金额等等)
	 * @param params
	 * @return
	 */
	Map calculateSupplierTotalIndex(Map params);
	
	/**
	 * 计算和统计首页经营概况 商家汇总指标(访次，浏览量，支付订单数，支付金额等等)
	 * 注：从提前计算好的商家每日经营指标表中取数
	 * @param params
	 * @return
	 */
	Map newCalculateSupplierTotalIndex(Map params);
	
	/**
	 *  按小时或者按天统计商品指标，以便做折线图展示
	 * @param params
	 * @return
	 */
	Map queryEveryDayOrHourIndex(Map params);
	
	/**
	 *  按小时或者按天统计商品指标，以便做折线图展示 
	 *  注：从提前计算好的商家每日经营指标表中取数
	 * @param params
	 * @return
	 */
	Map newQueryEveryDayOrHourIndex(Map params);
	
	/**
	 * queryCommodityAnalysisList:商品分析，按商品维度分析 
	 * @author li.n1 
	 * @param query
	 * @param vo
	 * @return 
	 * @since JDK 1.6
	 */
	PageFinder<Map<String, Object>> queryCommodityAnalysisList(
			Query query, CommodityAnalysisVo vo);
	/**
	 * queryCommodityAnalysisList:商品分析，按分类维度分析 
	 * @author li.n1 
	 * @param query
	 * @param vo
	 * @return 
	 * @since JDK 1.6
	 */
	PageFinder<Map<String, Object>> queryCategoryAnalysisList(
			Query query, CommodityAnalysisVo vo);
	/**
	 * queryCommodityAnalysisList:商品分析，按商品维度分析 ，第一行汇总数据
	 * @author li.n1 
	 * @param vo
	 * @return 
	 * @since JDK 1.6
	 */
	Map<String, Object> querySumOfCommodityAnalysis(CommodityAnalysisVo vo);
	/**
	 * querySumOfCatagoryAnalysis:品类分析，按商品维度分析 ，第一行汇总数据 
	 * @author li.n1 
	 * @param vo
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-8-4 上午10:08:32
	 */
	Map<String, Object> querySumOfCatagoryAnalysis(CommodityAnalysisVo vo);
	/**
	 * queryCategoryAnalysisListByCategoryStruct:获取第三级分类的品类分析数据 
	 * @author li.n1 
	 * @param vo
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-8-4 下午2:26:02
	 */
	List<Map<String, Object>> queryCategoryAnalysisListByCategoryStruct(
			CommodityAnalysisVo vo);
	/**
	 * queryExportCommodityAnalysisList:商品分析导出 
	 * @author li.n1 
	 * @param vo
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-8-6 下午4:36:16
	 */
	List<Map<String, Object>> queryExportCommodityAnalysisList(CommodityAnalysisVo vo);
	/**
	 * queryExportCategoryAnalysisList:品类分析导出
	 * @author li.n1 
	 * @param vo
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-8-6 下午4:36:28
	 */
	List<Map<String, Object>> queryExportCategoryAnalysisList(CommodityAnalysisVo vo);

	int queryCommodityAnalysisListCount(CommodityAnalysisVo vo);
	
	/**
	 * 查询经营概况指标
	 * @return
	 */
	List<Map> queryManageSurveyIndex();
	
	/**
	 * 统计更新商品分析Analysis 表商品每天支付件数，支付金额
	 */
	void updateAnalysisCommodityNumAndAmt();
	
	/**
	 * 每天统计出前一天每个商家所有的经营概况指标，并插入至tbl_merchant_report_suppler_survey_index表
	 */
	void insertSupplierServeyIndex(Map<String,Object> params);
	
	/**
	 * job 更新商家经营概况指标中的新上架商品数 added by zhangfeng 2015-12-11
	 */
	void updateNewSaleCommodityNum();

}
