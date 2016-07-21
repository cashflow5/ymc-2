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

import com.belle.yitiansystem.merchant.model.entity.ContractAttachment;
import com.belle.yitiansystem.merchant.model.entity.ContractAttachmentQuery;
 

public interface ContractAttachmentMapper {
    int countByQuery(ContractAttachmentQuery example);

    int deleteByQuery(ContractAttachmentQuery example);

    int deleteByPrimaryKey(String id);

    int insert(ContractAttachment record);

    int insertSelective(ContractAttachment record);

    List<ContractAttachment> selectByQuery(ContractAttachmentQuery example);

    ContractAttachment selectByPrimaryKey(String id);

    int updateByQuerySelective(@Param("record") ContractAttachment record, @Param("example") ContractAttachmentQuery example);

    int updateByQuery(@Param("record") ContractAttachment record, @Param("example") ContractAttachmentQuery example);

    int updateByPrimaryKeySelective(ContractAttachment record);

    int updateByPrimaryKey(ContractAttachment record);
}