package com.yougou.kaidian.bi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.bi.vo.SingleAnalysisVo;

/**
 * 数据报表-单品分析Dao映射接口
 * @author zhang.f1
 *
 */
public interface ReportSingleAnalysisMapper {
	
	/**
	 * 查询单品分析页面商品基本信息
	 * @param params
	 * @return
	 */
	SingleAnalysisVo queryCommodityInfo(@Param("merchantCode")String merchantCode,@Param("commodityNo")String commodityNo);
	
	/**
	 * 单品分析每天指标数据查询，以便页面折线图展示
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryCommodityEveryDayIndex(Map<String,Object> params);
	
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
	
	
}
