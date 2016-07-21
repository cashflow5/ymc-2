package com.belle.yitiansystem.merchant.dao.mapper;

import java.util.List;
import java.util.Map;

/**
 * 违规处罚订单Mapper映射接口
 * @author zhang.f1
 *
 */
public interface PunishOrderMapper {
	/**
	 * 查询违规订单列表
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryPunishOrderList(Map<String,Object> params);
	
	/**
	 * 查询违规订单列表总数
	 * @param params
	 * @return
	 */
	int queryPunishOrderCount(Map<String,Object> params);
}
