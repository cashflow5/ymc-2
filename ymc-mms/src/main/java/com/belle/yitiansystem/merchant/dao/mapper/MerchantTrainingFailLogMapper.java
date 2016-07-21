package com.belle.yitiansystem.merchant.dao.mapper;

import com.belle.yitiansystem.merchant.model.pojo.MerchantTrainingFailLog;
import org.apache.ibatis.annotations.Param;

public interface MerchantTrainingFailLogMapper {

    int deleteByPrimaryKey(String id);

    int insert(MerchantTrainingFailLog record);

    int insertSelective(MerchantTrainingFailLog record);

    MerchantTrainingFailLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MerchantTrainingFailLog record);

    int updateByPrimaryKey(MerchantTrainingFailLog record);
}