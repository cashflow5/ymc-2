package com.belle.yitiansystem.active.service;

import java.util.Map;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.yougou.kaidian.common.base.Query;

/**
 * 商家报名官方
 * @author zhang.wj
 *
 */
public interface MerchantOfficialActivityService {
	
	
	/**
	 * 查询商家信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PageFinder<Map<String, Object>> queryMerchant(Map<String,Object> map,Query query)throws Exception;
	
	

	/**
	 * 查询商家信息数量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int queryMerchantCount(String  activityId)throws Exception;
	
	
	
	/**
	 * 查询商品信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PageFinder<Map<String, Object>> queryCommodity(Map<String,Object> map,Query query)throws Exception;
	
	/**
	 * 更新报名状态为报名结束
	 */
	void updateOfficialActiveStatusOver();
}
