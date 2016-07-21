/*
 * ���� com.yougou.kaidian.order.dao.mapper.MerchantActiveCommodityMapper
 * 
 * ����  Tue Oct 13 09:44:01 CST 2015
 * 
 * ��Ȩ����Copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
package com.yougou.kaidian.active.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.active.vo.MerchantActiveCommodity;
import com.yougou.kaidian.active.vo.MerchantActiveCommodityQuery;
import com.yougou.kaidian.common.base.Query;

public interface MerchantActiveCommodityMapper {
    int countByQuery(MerchantActiveCommodityQuery example);

    int deleteByQuery(MerchantActiveCommodityQuery example);

    int deleteByPrimaryKey(String id);

    int insert(MerchantActiveCommodity record);

    int insertSelective(MerchantActiveCommodity record);

    List<MerchantActiveCommodity> selectByQuery(MerchantActiveCommodityQuery example);
    
    List<MerchantActiveCommodity> selectMerchantCommodityList(@Param("record")MerchantActiveCommodity example, @Param("query") Query query);
    
    int selectMerchantCommodityCount(@Param("record")MerchantActiveCommodity example);

    MerchantActiveCommodity selectByPrimaryKey(String id);

    int updateByQuerySelective(@Param("record") MerchantActiveCommodity record, @Param("example") MerchantActiveCommodityQuery example);

    int updateByQuery(@Param("record") MerchantActiveCommodity record, @Param("example") MerchantActiveCommodityQuery example);

    int updateByPrimaryKeySelective(MerchantActiveCommodity record);

    int updateByPrimaryKey(MerchantActiveCommodity record);
}