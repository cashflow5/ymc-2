/*
 * 类名 com.yougou.merchant.api.supplier.dao.ContractAttachmentMapper
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

import com.belle.yitiansystem.merchant.model.entity.ContractTradeMark;
import com.belle.yitiansystem.merchant.model.entity.ContractTradeMarkQuery;
 

public interface ContractTradeMarkMapper {
    int countByQuery(ContractTradeMarkQuery example);

    int deleteByQuery(ContractTradeMarkQuery example);

    int deleteByPrimaryKey(String id);

    int insert(ContractTradeMark record);

    int insertSelective(ContractTradeMark record);

    List<ContractTradeMark> selectByQuery(ContractTradeMarkQuery example);

    ContractTradeMark selectByPrimaryKey(String id);

    int updateByQuerySelective(@Param("record") ContractTradeMark record, @Param("example") ContractTradeMarkQuery example);

    int updateByQuery(@Param("record") ContractTradeMark record, @Param("example") ContractTradeMarkQuery example);

    int updateByPrimaryKeySelective(ContractTradeMark record);

    int updateByPrimaryKey(ContractTradeMark record);
}