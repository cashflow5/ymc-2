package com.yougou.kaidian.bi.service;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.bi.vo.SingleAnalysisVo;

/**
 * 数据报表-单品分析服务层接口
 * @author zhang.f1
 *
 */
public interface IReportSingleAnalysisService {
	
	/**
	 * 查询单品分析页面商品基本信息
	 * @param params
	 * @return
	 */
	SingleAnalysisVo queryCommodityInfo(String merchantCode,String commodityNo);
	
	/**
	 * 查询单品分析页面商品事件列表信息
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryCommodityLoginfoList(@Param("merchantCode")String merchantCode,@Param("commodityNo")String commodityNo);

	/**
	 * 查询单品分析页面商品尺码列表信息
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryCommoditySizeList(@Param("merchantCode")String merchantCode,@Param("commodityNo")String commodityNo);
	
	/**
	 * 查询单品分析下拉指标
	 * @return
	 */
	List<Map<String,Object>> querySingleAnalysisIndex();
	
	/**
	 * 单品分析每天指标数据查询，以便页面折线图展示
	 * @param params
	 * @return
	 */
	Map<String,Object> queryCommodityEveryDayIndex(Map<String,Object> params);
	
	/**
	 * 根据商品编码查询出收藏人数
	 * @param commodityNo
	 * @return
	 */
	int queryFavoriteCountByCommodityNo(String commodityNo);
	
}
