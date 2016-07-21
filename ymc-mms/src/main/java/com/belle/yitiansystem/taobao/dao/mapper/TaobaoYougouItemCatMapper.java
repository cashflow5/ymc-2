package com.belle.yitiansystem.taobao.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.belle.yitiansystem.taobao.model.TaobaoItemCat;
import com.belle.yitiansystem.taobao.model.TaobaoYougouItemCat;
import com.belle.yitiansystem.taobao.model.YougouCat;

public interface TaobaoYougouItemCatMapper {
	public List<TaobaoYougouItemCat> selectTaobaoYougouItemCatList(@Param("params") Map<String,Object>map,RowBounds rowBounds);
	
	public int selectTaobaoYougouItemCatCount(@Param("params") Map<String,Object>map);
	
	
	public void deleteYougouTaobaoItemCatBatch(@Param("list") List<TaobaoYougouItemCat>list);
	/**
	 * 查询所有分类绑定
	 * @param map
	 * @return
	 */
	public List<TaobaoYougouItemCat> selectTaobaoYougouItemCatList(@Param("params") Map<String,Object>map);
	
	public List<YougouCat> selectYougouCatList(@Param("structName")String structName);
	

	/**
	 * 查询淘宝分类
	 * @param map
	 * @return
	 */
	public List<TaobaoItemCat> selectTaobaoItemCatList(@Param("params") Map<String,Object>map);
	
	/**
	 * 插入分类绑定
	 * @param templet
	 */
	public void insertYougouTaobaoItem(TaobaoYougouItemCat templet);
	
	/**
	 * 通过ID查询分类
	 * @param id
	 * @return
	 */
	public TaobaoYougouItemCat selectTaobaoYougouItemCatById(@Param("id") String id);
}
