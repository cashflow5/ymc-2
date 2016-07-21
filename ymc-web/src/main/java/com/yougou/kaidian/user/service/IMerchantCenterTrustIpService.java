package com.yougou.kaidian.user.service;  

import com.yougou.kaidian.user.model.pojo.MerchantCenterOperationLog;
import com.yougou.kaidian.user.model.pojo.MerchantCenterTrustIp;

public interface IMerchantCenterTrustIpService {
	
	/**
	 * checkTrustIp:判断此IP是否为可信任ip
	 * @author li.n1 
	 * @param ip 待检验ip
	 * @param loginName 登录名
	 * @return true 是可信任  false 不可信任
	 * @since JDK 1.6 
	 * @date 2015-10-10 下午2:26:01
	 */
	boolean checkTrustIp(String ip, String loginName);
	/**
	 * insert:可信任IP表新增记录
	 * @author li.n1 
	 * @param trustIp bean对象
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-10-10 下午2:26:20
	 */
	boolean insert(MerchantCenterTrustIp trustIp);
	/**
	 * checkTrustIpAndAddr:判断此归属地是否为可信任，不可信任发送短信提示
	 * @author li.n1 
	 * @param operationLog 登录的日志信息
	 * @param loginName  登录名
	 * @paran mobileCode 手机号码
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-10-10 下午2:29:00
	 */
	boolean checkTrustAddrByAddr(MerchantCenterOperationLog operationLog, String loginName,
			String mobileCode);
	
}
