package com.yougou.kaidian.user.service;

import java.util.Map;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.user.model.pojo.MerchantCenterOperationLog;

/**
 * 商家中心操作日志服务接口
 * @author zhang.f1
 *
 */
public interface IMerchantCenterOperationLogService {
	
	/**
	 * 保存操作日志
	 * @param log
	 * @return
	 */
	public boolean insertOperationLog(MerchantCenterOperationLog log);
	
	/**
	 * 查询操作日志
	 * @param loginName
	 * @return
	 */
	public PageFinder<MerchantCenterOperationLog> selectByLoginName(Map<String,Object> params);
	/**
	 * cleanLoginLog:清理登录日志中的归属地信息
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-10-14 上午10:48:23
	 */
	public boolean cleanLoginLog(int pageSize) throws Exception;
}
