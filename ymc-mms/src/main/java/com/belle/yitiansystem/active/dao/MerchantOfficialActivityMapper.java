package com.belle.yitiansystem.active.dao;

import java.util.List;
import java.util.Map;


/**
 * 官方活动报名
 * @author zhang.wj
 *
 */
public interface MerchantOfficialActivityMapper {
	/**
	 * 查询商家数据
	 * @param map
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> queryMerchant(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询商家数据数量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>>  queryMerchantCount(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 查询商品数据
	 * @param map
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> queryCommodity(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询商品数据数量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>>  queryCommodityCount(Map<String, Object> map) throws Exception;
	
	
	
}
