package com.belle.yitiansystem.merchant.dao.mapper;

import java.util.List;
import java.util.Map;

import com.belle.yitiansystem.active.vo.MerchantActiveSignup;


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
	public int  queryMerchantCount(Map<String, Object> map) throws Exception;
	
	
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
	public int  queryCommodityCount(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询状态为新建，审核中，审核不通过的报名
	 * @return
	 */
	List<MerchantActiveSignup> selectMerchantActiveSignupNotAudited();
	
	/**
	 * 根据主键更新
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(MerchantActiveSignup record);
	
}
