
package com.belle.yitiansystem.merchant.dao.mapper;

import com.belle.yitiansystem.merchant.model.pojo.MerchantTraining;
import com.belle.yitiansystem.merchant.model.pojo.MerchantTrainingQuery;
import com.yougou.merchant.api.common.Query;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface MerchantTrainingMapper {
    int countByQuery(MerchantTrainingQuery example);

    int deleteByQuery(MerchantTrainingQuery example);

    int deleteByPrimaryKey(String id);

    int insert(MerchantTraining record);

    int insertSelective(MerchantTraining record);

    List<MerchantTraining> selectByQuery(MerchantTrainingQuery example);

    MerchantTraining selectByPrimaryKey(String id);

    int updateByQuerySelective(@Param("record") MerchantTraining record, @Param("example") MerchantTrainingQuery example);

    int updateByQuery(@Param("record") MerchantTraining record, @Param("example") MerchantTrainingQuery example);

    int updateByPrimaryKeySelective(MerchantTraining record);

    int updateByPrimaryKey(MerchantTraining record);
    
    public List<MerchantTraining> queryTrainingList(@Param("query")Query query,
			@Param("record")MerchantTraining record) ;

	int countForQueryTrainingList(@Param("record")MerchantTraining record);

}