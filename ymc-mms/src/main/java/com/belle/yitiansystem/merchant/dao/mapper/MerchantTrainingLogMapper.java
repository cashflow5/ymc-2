
package com.belle.yitiansystem.merchant.dao.mapper;

import com.belle.yitiansystem.merchant.model.pojo.MerchantTrainingLog;
import java.util.List;
import com.yougou.merchant.api.common.Query;
import org.apache.ibatis.annotations.Param;

public interface MerchantTrainingLogMapper {
   
	int countForQuery(@Param("trainingId") String  trainingId);

    int deleteByPrimaryKey(String id);
    
    int deleteByTrainingId(@Param("trainingId") String  trainingId);

    int insert( MerchantTrainingLog record);

    List<MerchantTrainingLog> selectByQuery(@Param("query") Query query,@Param("trainingId") String  trainingId);

    MerchantTrainingLog selectByPrimaryKey(String id);

    int insertSelective(MerchantTrainingLog record);

    int updateByPrimaryKeySelective(MerchantTrainingLog record);

    int updateByPrimaryKey(MerchantTrainingLog record);
}