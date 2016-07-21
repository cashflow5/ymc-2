package com.yougou.kaidian.bi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.yougou.kaidian.bi.dao.MMSDBReportQueryMapper;
import com.yougou.kaidian.bi.dao.ReportSingleAnalysisMapper;
import com.yougou.kaidian.bi.service.IReportSingleAnalysisService;
import com.yougou.kaidian.bi.vo.SingleAnalysisVo;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.tools.common.utils.DateUtil;

/**
 * 数据报表-单品分析服务层实现类
 * @author zhang.f1
 *
 */
@Service
public class ReportSingleAnalysisServiceImpl implements
		IReportSingleAnalysisService {
	
	@Resource
	private ReportSingleAnalysisMapper reportSingleAnalysisMapper;
	
	@Resource
	private MMSDBReportQueryMapper mmsdbReportQueryMapper;

	@Override
	public SingleAnalysisVo queryCommodityInfo(String merchantCode,
			String commodityNo) {
		// TODO Auto-generated method stub
		SingleAnalysisVo singleAnalysisVo= reportSingleAnalysisMapper.queryCommodityInfo(merchantCode, commodityNo);
		List<String> commodityNos = new ArrayList<String>();
		commodityNos.add(commodityNo);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("merchantCode", YmcThreadLocalHolder.getMerchantCode());
		params.put("loginName", YmcThreadLocalHolder.getOperater());
		params.put("commodityNos", commodityNos);
		//查询商品归类数量
		List<Map<String,Object>> resultList = mmsdbReportQueryMapper.queryFavoriteClassifyByCommodityNos(params);
		if(resultList !=null && resultList.size() > 0){
			int fcount = (Integer) resultList.get(0).get("fcount");
			singleAnalysisVo.setClassifyCount(fcount);
		}
		return singleAnalysisVo;
	}

	@Override
	public List<Map<String, Object>> queryCommodityLoginfoList(
			String merchantCode, String commodityNo) {
		// TODO Auto-generated method stub
		return reportSingleAnalysisMapper.queryCommodityLoginfoList(merchantCode, commodityNo);
	}

	@Override
	public List<Map<String, Object>> queryCommoditySizeList(
			String merchantCode, String commodityNo) {
		// TODO Auto-generated method stub
		return reportSingleAnalysisMapper.queryCommoditySizeList(merchantCode, commodityNo);
	}

	@Override
	public List<Map<String, Object>> querySingleAnalysisIndex() {
		// TODO Auto-generated method stub
		return mmsdbReportQueryMapper.querySingleAnalysisIndex();
	}

	@Override
	public Map<String, Object> queryCommodityEveryDayIndex(
			Map<String, Object> params) {
		//申明返回对象
		Map<String,Object> result = new HashMap<String,Object>();
		String firstIndexName = (String) params.get("firstIndexName"); //第一个指标名称，非空
		String secondIndexName = (String) params.get("secondIndexName"); // 第二个指标名称  非空
		
		//取出查询的起止时间，计算出时间间隔天数
		String startDate = (String) params.get("startDate");
		String endDate =  (String) params.get("endDate");
		//没有查询起止时间，默认查询最近7天的数据
		if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){
			Date date = new Date();
			startDate = DateUtil.format(DateUtil.addDate(date, -7),"yyyy-MM-dd");
			endDate = DateUtil.format(DateUtil.addDate(date, -1),"yyyy-MM-dd");
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		}
		Date start = DateUtil.parseDate(startDate, "yyyy-MM-dd");
		Date end = DateUtil.parseDate(endDate, "yyyy-MM-dd");
		int diff = DateUtil.diffDate( end,start);
		//从起始时间开始，直到结束日期，得到每天的具体日期，以便折线图进行每天数据展示
		Date[] dateArr = new Date[diff+1];
		for(int i=0;i<dateArr.length;i++){
			dateArr[i]=DateUtil.addDate(start, i);
		}
		
		List<Map<String,Object>> everyDayIndex = reportSingleAnalysisMapper.queryCommodityEveryDayIndex(params);
		List<Object[]> firstIndexList = new ArrayList<Object[]>();
		List<Object[]> secondIndexList = new ArrayList<Object[]>();
		
		//循环获取每天的指标数据
		for(Date date : dateArr){
			//日期及其对应指标值的数组对象
			Object[] firstIndexArray = new Object[2];
			Object[] secondIndexArray = new Object[2];
			//获取日期的毫秒数,页面展示时日期显示会自动往前一天，所以此处将日期加1天，使其与查询日期相同显示
			long dateMil = DateUtil.getMillis(DateUtil.addDate(date, 1));	
			firstIndexArray[0] = dateMil;
			secondIndexArray[0] = dateMil;
			Object firstIndexVal = null;
			Object secondIndexVal = null;
			if(everyDayIndex !=null && everyDayIndex.size() > 0){
				for(Map<String,Object> map : everyDayIndex){
					String dateStr = DateUtil.formatDate(date, "yyyy-MM-dd");
					String reportDate = (String) map.get("reportDate");
					if(dateStr.equals(reportDate)){
						firstIndexVal = map.get(firstIndexName);
						secondIndexVal = map.get(secondIndexName);
						break;
					}
				}
			}
			firstIndexArray[1] = firstIndexVal == null ? 0 : firstIndexVal;
			secondIndexArray[1] = secondIndexVal == null ? 0 : secondIndexVal;			
			firstIndexList.add(firstIndexArray);
			secondIndexList.add(secondIndexArray);
			
		}
		result.put("firstIndex", firstIndexList);
		result.put("secondIndex", secondIndexList);
		return result;
	}

	@Override
	public int queryFavoriteCountByCommodityNo(String commodityNo) {
		// TODO Auto-generated method stub
		return mmsdbReportQueryMapper.queryFavoriteCountByCommodityNo(commodityNo);
	}
	
	

}
