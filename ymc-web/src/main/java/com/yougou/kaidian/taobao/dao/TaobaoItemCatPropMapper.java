package com.yougou.kaidian.taobao.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.taobao.model.TaobaoItemCatPropDto;

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
			@Param("catBindId") String catBindId,
			@Param("merchantCode") String merchantCode);
}
