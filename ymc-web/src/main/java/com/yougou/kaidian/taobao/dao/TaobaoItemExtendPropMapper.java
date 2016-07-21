package com.yougou.kaidian.taobao.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.taobao.model.TaobaoItemExtendYougouPropValue;
import com.yougou.kaidian.taobao.model.TaobaoItemExtendYougouPropValueSize;

public interface TaobaoItemExtendPropMapper {

	// public List<TaobaoItemExtendYougouProp> selectTaobaoItemYougouProp(
	// @Param("params") Map<String, Object> map);

	/**
	 * 批量插入属性
	 * 
	 * @param list
	 */
	// public void insertTaobaoItemYougouPropBatch(
	// @Param("list") List<TaobaoItemExtendYougouProp> list);
	
	/**
	 * 批量删除属性
	 * 
	 * @param numIid
	 * @param merchantCode
	 */
	// public void deleteTaobaoItemYougouPropByNumIid(
	// @Param("numIid") long numIid,
	// @Param("merchantCode") String merchantCode);

	/**
	 * 批量插入属性值
	 * 
	 * @param list
	 */
	public void insertTaobaoItemYougouPropValueBatch(
			@Param("list") List<TaobaoItemExtendYougouPropValue> list);

	/**
	 * 批量删除属性值
	 * 
	 * @param numIid
	 * @param merchantCode
	 */
	public void deleteTaobaoItemYougouPropValueByNumIid(
			@Param("numIid") long numIid,
			@Param("merchantCode") String merchantCode);

	public void deleteTaobaoItemYougouPropValueByExtendId(
			@Param("numIid") long numIid,@Param("extendId") String extendId,
			@Param("merchantCode") String merchantCode);
	
	/**
	 * 查询属性值
	 * 
	 * @param map
	 * @return
	 */
	public List<TaobaoItemExtendYougouPropValue> selectTaobaoItemYougouPropValue(
			@Param("params") Map<String, Object> map);
	
	/**
	 * 查询合并的属性值
	 * @param numIid
	 * @return
	 */
	public List<TaobaoItemExtendYougouPropValue> selectTaobaoItemYougouPropValueByNumIid(long numIid);
	
	public List<TaobaoItemExtendYougouPropValue> selectTaobaoItemYougouPropValueByExtendId(String extendId);

	/**
	 * 尺码属性明细
	 * 
	 * @param map
	 * @return
	 */
	public List<TaobaoItemExtendYougouPropValueSize> selectTaobaoItemYougouPropValueSize(
			@Param("params") Map<String, Object> map);

	public void deleteTaobaoItemYougouPropValueSizeByExtendId(
			@Param("extendId") String extendId,
			@Param("merchantCode") String merchantCode);

	public void insertTaobaoItemYougouPropValueSizeBatch(
			@Param("list") List<TaobaoItemExtendYougouPropValueSize> list);
	
	public void deleteTaobaoItemYougouPropValueSizeByNumIid(@Param("numIid") String numIid);
}
