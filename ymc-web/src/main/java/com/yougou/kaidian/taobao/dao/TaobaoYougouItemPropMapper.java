package com.yougou.kaidian.taobao.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.taobao.model.TaobaoYougouItemProp;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemPropValue;

/**
 * 淘宝优购分类属性绑定
 * 
 * @author luo.hl
 * @date 2014-7-25 上午11:06:03
 * @version 0.1.0
 * @copyright yougou.com
 */
public interface TaobaoYougouItemPropMapper {

	/**
	 * 批量插入属性
	 * 
	 * @param list
	 */
	public void insertTaobaoYougouItemPropBatch(List<TaobaoYougouItemProp> list);
	
	/**
	 * 批量插入属性值
	 * 
	 * @param list
	 */
	public void insertTaobaoYougouItemPropValueBatch(
			List<TaobaoYougouItemPropValue> list);

	/**
	 * 更新属性
	 * 
	 * @param prop
	 */
	public void updateTaobaoYougouItemProp(TaobaoYougouItemProp prop);

	/**
	 * 更新属性值
	 * 
	 * @param value
	 */
	public void updateTaobaoYougouItemPropValue(TaobaoYougouItemPropValue value);

	/**
	 * 查询分类绑定id对应的所有属性
	 * 
	 * @param id
	 * @param merchantCode
	 * @return
	 */
	public List<String> selectPropIdsByCatBindId(@Param("id") String id,
			@Param("merchantCode") String merchantCode);

	/**
	 * 查询分类绑定id对应的所有属性值
	 * @param id
	 * @param merchantCode
	 * 
	 * @return
	 */
	public List<String> selectPropValueIdsByCatBindId(@Param("id") String id,
			@Param("merchantCode") String merchantCode);

	/**
	 * 批量删除所有属性
	 * 
	 * @param list
	 * @param merchantCode
	 */
	public void deletePropBatch(@Param("list") List<String> list,
			@Param("merchantCode") String merchantCode);

	/**
	 * 批量删除所有属性值
	 * 
	 * @param list
	 * @param merchantCode
	 */
	public void deletePropValueBatch(@Param("list") List<String> list,
			@Param("merchantCode") String merchantCode);
	
	
	
	public List<TaobaoYougouItemProp> selectTaobaoYougouItemPropList(
			@Param("params") Map<String, Object> params);

	/**
	 * 更具条件查询绑定分类属性值
	 * 
	 * @param params
	 * @return
	 */
	public List<TaobaoYougouItemPropValue> selectTaobaoYougouItemPropValueList(
			@Param("params") Map<String, Object> params);
	
	
	public void deletePropByCid(@Param("taobaoCid") Long taobaoCid,@Param("merchantCode") String merchantCode);
	
	public void deletePropValueByCid(@Param("taobaoCid") Long taobaoCid,@Param("merchantCode") String merchantCode);

}
