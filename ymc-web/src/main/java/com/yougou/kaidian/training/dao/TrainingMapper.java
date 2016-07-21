package com.yougou.kaidian.training.dao;

import java.util.List;
import java.util.Map;

import com.yougou.kaidian.order.model.TrainingInfoDto;

public interface TrainingMapper {
	
	
	/**
	 * 查询所有培训课程，按照发布时间降序排序
	 * @return
	 */
	public List<TrainingInfoDto> queryTrainingList(Map params);
	
	/**
	 * 查询所有培训课程记录总数
	 * @return
	 */
	public int queryTrainingTotalRows(Map params);
	
	/**
	 * 根据课程ID查询课程详情
	 * @return
	 */
	public TrainingInfoDto queryTrainingInfoById(TrainingInfoDto trainingDto);
	
	/**
	 * 根据课程ID修改课程PV量
	 * @return
	 */
	public void updateTotalClick(Map params);
}
