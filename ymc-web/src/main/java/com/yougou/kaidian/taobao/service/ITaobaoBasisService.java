package com.yougou.kaidian.taobao.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.taobao.exception.BusinessException;
import com.yougou.kaidian.taobao.model.TaobaoItemCatPropDto;
import com.yougou.kaidian.taobao.model.TaobaoItemCatPropValueDto;
import com.yougou.kaidian.taobao.model.TaobaoYougouBrand;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemCat;
import com.yougou.kaidian.taobao.model.ZTree;

public interface ITaobaoBasisService {

	public TaobaoYougouItemCat getTaobaoYougouItemCatById(String id);
	
	/**
	 * 查询品牌绑定列表
	 * 
	 * @param params
	 * @return
	 */

	public int findTaobaoYougouBrandCount(Map<String, Object> params);
	/**
	 * 查询品牌绑定列表
	 * 
	 * @param map
	 * @param query
	 * @return
	 */
	public PageFinder<TaobaoYougouBrand> findTaobaoYougouBrandList(
			Map<String, Object> map, Query query);

	/**
	 * 绑定品牌
	 * 
	 * @param dataStr
	 * @param merchantCode
	 * @throws BusinessException
	 */
	public void saveTaobaoYougouBrand(String dataStr, String merchantCode)
			throws BusinessException;

	/**
	 * 删除品牌绑定
	 * 
	 * @param ids
	 * @param merchantCode
	 * @throws BusinessException
	 */
	public void delTaobaoYougouBrand(String ids, String merchantCode)
			throws BusinessException;


	/****************************** 分类 **************************************/

	/**
	 * 查询分类绑定数量
	 * 
	 * @param params
	 * @return
	 */
	public int findTaobaoYougouItemCatCount(Map<String, Object> params);
	/**
	 * 查询分类绑定列表
	 * 
	 * @param map
	 * @param query
	 * @return
	 */
	public PageFinder<TaobaoYougouItemCat> findTaobaoYougouItemCatList(
			Map<String, Object> map, Query query);

	/**
	 * 获取淘宝优购分类树信息
	 * 
	 * @param merchantCode
	 * @param nickId TODO
	 * @return
	 * @throws BusinessException TODO
	 */
	public List<ZTree> findTaobaoYougouItemCatZTree(String merchantCode, String nickId) throws BusinessException;

	/**
	 * 获取优购分类树信息
	 * 
	 * @param merchantCode
	 * @param structName TODO
	 * @return
	 */
	public List<ZTree> findYougouItemCatZTree(String merchantCode, String structName);

	/**
	 * 绑定淘宝优购分类
	 * 
	 * @param params
	 */
	public void bindYougouItemCate(Map<String, Object> params)
			throws BusinessException;

	/**
	 * 删除淘宝分类绑定
	 * 
	 * @param ids
	 * @param merchantCode
	 * @throws BusinessException
	 */
	public void delTaobaoYougouItemCat(String ids, String merchantCode)
			throws BusinessException;

	/********************************** 属性设置 
	 * @param bindId TODO
	 * @param merchantCode TODO********************************************/

	public List<TaobaoItemCatPropDto> selectTaobaoItemPro4Bind(String bindId, String merchantCode);

	/**
	 * 查询淘宝属性值
	 * 
	 * @param pid
	 * @param cid TODO
	 * @param merchantCode TODO
	 * @param nickId TODO
	 * @return
	 */
	public List<TaobaoItemCatPropValueDto> findTaobaoItemProVal(Long pid,
			Long cid, String merchantCode, String catBindId);

	public void bindTaobaoYougouItemProAndVal(HttpServletRequest request)
			throws BusinessException;

	/**
	 * 下载淘宝商品
	 * 
	 * @param itemBindIdStr
	 * @param merchantCode
	 *            TODO
	 * @param operater
	 *            TODO
	 * @return TODO
	 * @throws IllegalAccessException
	 */
	public Map<String, Integer> downloadItem(String itemBindIdStr, String merchantCode,
			String operater) throws BusinessException, IllegalAccessException;

	public List<TaobaoItemCatPropValueDto> findTaobaoItemProVal4Color(Long cid);
}

