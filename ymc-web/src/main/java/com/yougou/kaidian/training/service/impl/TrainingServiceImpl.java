package com.yougou.kaidian.training.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.order.model.TrainingInfoDto;
import com.yougou.kaidian.training.dao.TrainingMapper;
import com.yougou.kaidian.training.service.ITrainingService;

/**
 * 商家中心培训中心业务逻辑实现类
 * @author zhang.f1
 *
 */
@Service
public class TrainingServiceImpl implements ITrainingService {
	
	@Resource
	private TrainingMapper trainingMapper;
	
	@Override
	public PageFinder<TrainingInfoDto> queryTrainingList(Map params) {
		//设置置顶条件，查询是否有置顶课程
		params.put("on_top", 1);
		int onTopRows = trainingMapper.queryTrainingTotalRows(params);
		//有置顶课程，置顶条件不删除，列表查询按照置顶排序;没有置顶课程，默认发布时间降序排序
		//onTopRows <=0 无置顶课程，修改置顶条件为0
		if(onTopRows<=0){	
			params.put("on_top", 0);
		}

		Query query = (Query) params.get("query");
		List<TrainingInfoDto> list = trainingMapper.queryTrainingList(params);
		PageFinder<TrainingInfoDto> pageFinder = null;
		if(list!=null && list.size()>0){
			//移除置顶查询条件，避免影响总记录数查询
			params.remove("on_top");
			int totalRows = trainingMapper.queryTrainingTotalRows(params);
			pageFinder = new PageFinder<TrainingInfoDto>(query.getPage(),totalRows);
			pageFinder.setData(list);
		}
		
		return 	pageFinder;
	}

	@Override
	public TrainingInfoDto queryTrainingInfoById(TrainingInfoDto trainingDto) {
		// TODO Auto-generated method stub
		return trainingMapper.queryTrainingInfoById(trainingDto);
	}

	@Override
	public void updateTotalClick(Map params) {
		trainingMapper.updateTotalClick(params);
		
	}

}
