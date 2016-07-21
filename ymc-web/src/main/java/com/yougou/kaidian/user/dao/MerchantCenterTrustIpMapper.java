package com.yougou.kaidian.user.dao;  

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.user.model.pojo.MerchantCenterTrustIp;

public interface MerchantCenterTrustIpMapper {
	
	void insert(MerchantCenterTrustIp trustIp);

	int checkTrustIp(@Param("ip") String ip,@Param("loginName") String loginName);

	int findLoginLogCount(@Param("loginName") String loginName,@Param("endTime") Date endTime);

	List<String> findTrustAddrByName(@Param("loginName") String loginName,@Param("endTime") Date endTime);
	
}
