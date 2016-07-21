/*
 * ���� com.yougou.kaidian.order.dao.mapper.MerchantActiveSignupMapper
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
import com.yougou.kaidian.active.vo.MerchantActiveSignup;
import com.yougou.kaidian.active.vo.MerchantActiveSignupQuery;
import com.yougou.kaidian.common.base.Query;

public interface MerchantActiveSignupMapper {
    int countByQuery(MerchantActiveSignupQuery example);

    int deleteByQuery(MerchantActiveSignupQuery example);

    int deleteByPrimaryKey(String id);

    int insert(MerchantActiveSignup record);

    int insertSelective(MerchantActiveSignup record);

    List<MerchantActiveSignup> selectByQuery(MerchantActiveSignupQuery example);

    MerchantActiveSignup selectByPrimaryKey(String id);
    
    MerchantActiveSignup selectByIdAndMerchantCode(@Param("activeId")String id,@Param("merchantCode")String merchantCode);

    int updateByQuerySelective(@Param("record") MerchantActiveSignup record, @Param("example") MerchantActiveSignupQuery example);

    int updateByQuery(@Param("record") MerchantActiveSignup record, @Param("example") MerchantActiveSignupQuery example);

    int updateByPrimaryKeySelective(MerchantActiveSignup record);

    int updateByPrimaryKey(MerchantActiveSignup record);
    
}