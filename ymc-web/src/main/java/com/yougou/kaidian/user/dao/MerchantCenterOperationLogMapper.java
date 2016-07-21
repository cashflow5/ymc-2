/*
 * ���� com.yougou.kaidian.user.dao.mapper.MerchantCenterOperationLogMapper
 * 
 * ����  Mon May 25 13:59:25 CST 2015
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
package com.yougou.kaidian.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.user.model.pojo.MerchantCenterOperationLog;

public interface MerchantCenterOperationLogMapper {

    int deleteByPrimaryKey(String id);

    int insert(MerchantCenterOperationLog record);

    int insertSelective(MerchantCenterOperationLog record);

    MerchantCenterOperationLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MerchantCenterOperationLog record);

    int updateByPrimaryKeyWithBLOBs(MerchantCenterOperationLog record);

    int updateByPrimaryKey(MerchantCenterOperationLog record);
    
    /**
     * 登录日志查询
     * @param params
     * @return
     */
    public List<MerchantCenterOperationLog> selectByLoginName(Map<String,Object> params);
    
    /**
     * 登录日志总记录数查询
     * @param params
     * @return
     */
    public int selectCountByLoginName(Map<String,Object> params);
	/**
	 * findAllLoginIPErrorLog:查找所有ipError的登录日志记录
	 * @author li.n1  
	 * @param rowBounds 
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-10-14 上午11:21:58
	 */
	List<String> findAllLoginIPErrorLog(RowBounds rowBounds);
	/**
	 * findAllLoginIPErrorLogCount:查找一个月所有ipError的数据数量
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-10-15 上午11:27:36
	 */
	int findAllLoginIPErrorLogCount();
	/**
	 * updateAddrByNewAddr:根据IP更新归属地 
	 * @author li.n1 
	 * @param ip
	 * @param address 
	 * @since JDK 1.6 
	 * @date 2015-10-28 下午2:31:50
	 */
	void updateAddrByNewAddr(@Param("ip") String ip,@Param("newAddr") String address);
}