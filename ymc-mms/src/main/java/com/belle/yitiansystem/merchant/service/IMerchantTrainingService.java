package com.belle.yitiansystem.merchant.service;

import org.springframework.data.redis.core.RedisTemplate;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.yitiansystem.merchant.model.pojo.MerchantTraining;
import com.belle.yitiansystem.merchant.model.pojo.MerchantTrainingFailLog;
import com.belle.yitiansystem.merchant.model.pojo.MerchantTrainingLog;
import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.yougou.merchant.api.common.Query;

public interface IMerchantTrainingService {
	
	public PageFinder<MerchantTraining> queryTrainingList(Query query, MerchantTraining merchantTraining, RedisTemplate<String, Object> redisTemplate);
	/**成功则返回课程id */
	public String saveTraining(MerchantTraining merchantTraining,String curUser) throws BusinessException;

	public MerchantTraining selectByPrimaryKey(String id);

	public boolean transferFileForView(String filePath, String suffix) ;

	public boolean deleteTraining(String id, String curUser);
	/** 发布课程  */
	public boolean publishTraining(String id, Short flag, String curUser) throws BusinessException;
	/** 置顶课程 */
	public boolean pushTraining(String id,Short flag, String curUser) ;

	public PageFinder<MerchantTrainingLog> queryTrainingOperationLog(
			String trainingId, Query query);
	public void updateByPrimaryKeySelective(MerchantTraining merchantTrainingForUpdate);
	
	public int insertFailLog(MerchantTrainingFailLog record);
}
