package com.belle.yitiansystem.taobao.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.belle.yitiansystem.taobao.model.TaobaoItemCatPropDto;
import com.belle.yitiansystem.taobao.model.TaobaoItemCatPropValueDto;
import com.belle.yitiansystem.taobao.model.TaobaoItemProp;
import com.belle.yitiansystem.taobao.model.TaobaoItemPropValue;
import com.belle.yitiansystem.taobao.model.TaobaoYougouItemCat;
import com.belle.yitiansystem.taobao.model.TaobaoYougouItemProp;
import com.belle.yitiansystem.taobao.model.TaobaoYougouItemPropValue;

public interface TaobaoItemCatPropMapper {

	/**
	 * 查询淘宝分类对应的所有属性
	 */
	public List<TaobaoItemCatPropDto> selectItemProByCatId(@Param("cid") Long cid);
	
	/**
	 * 查询分类ID对应的淘宝分类属性以及已经绑定的优购分类属性
	 * 
	 * @param catBindId
	 * @param merchantCode
	 * @return
	 */
	public List<TaobaoItemCatPropDto> selectItemProWidthBindYougouItemPro(
			@Param("catBindId") String catBindId);
	
	
	/**
	 * 查询淘宝分类属性值
	 * 
	 * @param pid
	 * @param merchantCode TODO
	 * @param nickId TODO
	 * @return
	 */
	public List<TaobaoItemCatPropValueDto> selectTaobaoItemProValWidthYouItemProVal(
			@Param("pid") Long pid, @Param("cid") Long cid,
			@Param("catBindId") String catBindId);
	
	
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
	 * 批量删除所有属性值
	 * 
	 * @param list
	 * @param merchantCode
	 */
	public void deletePropValueBatch(@Param("list") List<String> list);
	
	
	public void deletePropBatchByCidAndCatNo(@Param("list") List<TaobaoYougouItemCat> list);
	

	public void deletePropValueBatchByCidAndCatNo(@Param("list") List<TaobaoYougouItemCat> list);
	
	public TaobaoItemProp  selectTaobaoItemPropByPid(String pid);
	
	public TaobaoItemPropValue  selectTaobaoItemPropValueByVid(String vid);
}
