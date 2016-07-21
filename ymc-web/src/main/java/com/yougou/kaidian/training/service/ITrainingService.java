package com.yougou.kaidian.training.service;

import java.util.List;
import java.util.Map;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.order.model.TrainingInfoDto;

/**
 * 商家中心培训中心业务逻辑接口类
 * @author zhang.f1
 *
 */
public interface ITrainingService {
	
	/**
	 * 查询所有培训课程，按照发布时间降序排序
	 * @return
	 */
	public PageFinder<TrainingInfoDto> queryTrainingList(Map params);
	
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
