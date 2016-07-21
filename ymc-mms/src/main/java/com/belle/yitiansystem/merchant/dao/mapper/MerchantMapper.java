/*
 * com.belle.yitiansystem.merchant.dao.mapper.MerchantMapper
 * 
 *  Wed Jun 24 20:43:07 CST 2015
 * 
 * Copyright (C) 2013 YouGou Information Technology Co.,Ltd 
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
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.belle.yitiansystem.merchant.model.entity.SimpleSupplierVo;
import com.yougou.merchant.api.common.Query;
import com.yougou.merchant.api.supplier.vo.SupplierQueryVo;
import com.yougou.merchant.api.supplier.vo.SupplierVo;

public interface MerchantMapper {
	
    int countByQuery(@Param("example")SupplierQueryVo example);
    
    // 查所有的提醒总数
    int countTotalRemind(@Param("example")SupplierQueryVo example);

    int deleteByQuery(SupplierQueryVo example);

    int deleteByPrimaryKey(String id);

    int insert( SupplierVo record);

    int insertSelective(SupplierVo record);

    List<SupplierVo> selectByQuery(@Param("example")SupplierQueryVo example,@Param("query") Query query);

    SupplierVo selectByPrimaryKey(String id);

    int updateByQuerySelective(@Param("record") SupplierVo record, @Param("example") SupplierQueryVo example);

    int updateByQuery(@Param("record") SupplierVo record, @Param("example") SupplierQueryVo example);

    int updateByPrimaryKeySelective(SupplierVo record);

    int updateByPrimaryKey(SupplierVo record);
    
    
    List<SimpleSupplierVo> selectByOverDays();
    
    List<SupplierVo> selectNotExistSupplier();
    
    List<Map<String,Object>>  getMerchantInfo(Map<String,List<String>> map);
    
}