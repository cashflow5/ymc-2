package com.yougou.kaidian.active.service;

import java.util.List;

import com.yougou.kaidian.active.vo.MerchantActiveCommodity;
import com.yougou.kaidian.active.vo.MerchantActiveCommodityQuery;
import com.yougou.kaidian.active.vo.MerchantActiveSignup;
import com.yougou.kaidian.common.base.Query;

public interface IOfficialActiveService {

	/**
	 * 保存活动商品信息
	 * @param commodity
	 * @return
	 */
	int saveActiveCommodity(MerchantActiveCommodity activeCommodity);
	
	/**
	 * 更新活动商品信息
	 * @param activeCommodity
	 * @return
	 */
	int updateActiveCommodity(MerchantActiveCommodity activeCommodity);
	
	/**
	 * 报名活动
	 * @param activeSignup
	 * @return
	 */
	int signupOfficialActive(MerchantActiveSignup activeSignup);
	
	/**
	 * 查询活动报名情况
	 * @param activeId
	 * @param merchantCode
	 * @return
	 */
	MerchantActiveSignup getMerchantActiveSignup(String activeId, String merchantCode);
	
	/**
	 * 根据主键ID查询报名
	 * @param id
	 * @return
	 */
	MerchantActiveSignup getMerchantActiveSignupById(String id);
	
	/**
	 * 查询活动商品列表
	 * @param commodity
	 * @param query
	 * @return
	 */
	List<MerchantActiveCommodity> selectMerchantCommodityList(MerchantActiveCommodity commodity,Query query);
	
	/**
	 * 查询商品数量
	 * @param query
	 * @return
	 */
	int getMerchantCommodityCount(MerchantActiveCommodityQuery query);
	
	/**
	 * 查询商品列表
	 * @param query
	 * @return
	 */
	List<MerchantActiveCommodity> getMerchantCommodityList(MerchantActiveCommodityQuery query);
	
	/**
	 * 查询活动商品数量
	 * @param commodity
	 * @return
	 */
	int selectMerchantCommodityCount(MerchantActiveCommodity commodity);
	
	/**
	 * 删除活动商品
	 * @param commodityNos
	 * @return
	 */
	int deleteMerchantActiveCommodity(String commodityIds);
	
	/**
	 * 修改报名
	 * @param signup
	 * @return
	 */
	int updateOfficialActiveSignup(MerchantActiveSignup signup);
	
	/**
	 * 查询活动报名列表
	 * @param commodity
	 * @param query
	 * @return
	 */
	List<MerchantActiveSignup> selectMerchantActiveSignupList(MerchantActiveSignup signup,Query query);
	
	/**
	 * 查询活动报名数量
	 * @param commodity
	 * @return
	 */
	int selectMerchantActiveSignupCount(MerchantActiveSignup signup);
	
	/**
	 * 根据主键ID获取报名商品
	 * @param commodityId
	 * @return
	 */
	MerchantActiveCommodity getActiveCommodityById(String commodityId);
}
