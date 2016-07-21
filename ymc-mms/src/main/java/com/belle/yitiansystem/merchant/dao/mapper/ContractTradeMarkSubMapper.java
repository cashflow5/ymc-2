/*
 * 类名 com.yougou.merchant.api.supplier.dao.ContractTradeMarkSubMapper
 * 
 * 日期  Tue Jun 23 13:25:28 CST 2015
 * 
 * 版权声明Copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
package com.belle.yitiansystem.merchant.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.belle.yitiansystem.merchant.model.entity.ContractTradeMarkSub;
import com.belle.yitiansystem.merchant.model.entity.ContractTradeMarkSubQuery;
 

public interface ContractTradeMarkSubMapper {
    int countByQuery(ContractTradeMarkSubQuery example);

    int deleteByQuery(ContractTradeMarkSubQuery example);

    int deleteByPrimaryKey(String id);

    int insert(ContractTradeMarkSub record);

    int insertSelective(ContractTradeMarkSub record);

    List<ContractTradeMarkSub> selectByQuery(ContractTradeMarkSubQuery example);

    ContractTradeMarkSub selectByPrimaryKey(String id);

    int updateByQuerySelective(@Param("record") ContractTradeMarkSub record, @Param("example") ContractTradeMarkSubQuery example);

    int updateByQuery(@Param("record") ContractTradeMarkSub record, @Param("example") ContractTradeMarkSubQuery example);

    int updateByPrimaryKeySelective(ContractTradeMarkSub record);

    int updateByPrimaryKey(ContractTradeMarkSub record);
}