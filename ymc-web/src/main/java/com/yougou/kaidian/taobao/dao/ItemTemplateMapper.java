package com.yougou.kaidian.taobao.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.taobao.model.pojo.ItemPropTemplate;
import com.yougou.kaidian.taobao.model.pojo.ItemTemplate;

public interface ItemTemplateMapper {
	/**
	 * 插入模板主表
	 * @param itemTemplate
	 */
	public void insertItemTemplate(ItemTemplate itemTemplate);
	
	/**
	 * 插入属性明细
	 * @param list
	 */
	public void  insertItemPropTemplateBatch(@Param("list") List<ItemPropTemplate> list);
	
	/**
	 * 删除模板
	 * @param id
	 * @param merchantCode
	 */
	public void deleteItemTemplate(@Param("id")String id,@Param("merchantCode")String merchantCode);
	
	/**
	 * 删除属性明细
	 * @param id
	 * @param merchantCode
	 */
	public void deleteItemPropTemplate(@Param("templateId")String id,@Param("merchantCode")String merchantCode);
	
	/**
	 * 查询模板
	 * @param merchantCode
	 * @param cateNo
	 * @return
	 */
	public int selectTemplateCount(@Param("merchantCode")String merchantCode,@Param("cateNo")String cateNo,@Param("title")String title,@Param("key")String key);
	
	public List<ItemTemplate> selectTemplate (@Param("merchantCode")String merchantCode,@Param("cateNo")String cateNo,@Param("title")String title,@Param("key")String key,RowBounds rowBounds);
	
	/**
	 * 查询模板属性值
	 * @param merchantCode
	 * @param templateId
	 * @return
	 */
	public List<ItemPropTemplate> selectPropTemplate(@Param("merchantCode")String merchantCode,@Param("templateId")String templateId);
}
