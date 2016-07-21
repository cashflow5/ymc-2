package com.yougou.kaidian.taobao.service;

import java.util.List;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.taobao.exception.BusinessException;
import com.yougou.kaidian.taobao.model.pojo.ItemPropTemplate;
import com.yougou.kaidian.taobao.model.pojo.ItemTemplate;

public interface IItemTemplateService {
	
	/**
	 * 添加模板
	 * @param prams
	 * @throws BusinessException
	 */
	void addItemTemplate(String[] propItemNo,String[]propItemName,String[] propValueNo,String[] propValueName,String commodityName,String cateNo,String merchantCode)throws BusinessException;
	
	/**
	 * 删除模板
	 * @param id
	 * @param merchantCode
	 */
	void deleteItemTemplate(String id,String merchantCode)throws BusinessException;
	
	/**
	 * 查询模板列表
	 * @param merchantCode
	 * @param cateNo
	 * @return
	 */
	PageFinder<ItemTemplate> findItemTemplateList(String merchantCode,String cateNo,String key,Query query);
	
	/**
	 * 查询模板属性值
	 * @param merchantCode
	 * @param templateId
	 * @return
	 */
	List<ItemPropTemplate> findItemPropTemplateList(String merchantCode,String templateId);
	/**
	 * 查询模板属性值个数
	 * selectTemplateCount:(这里用一句话描述这个方法的作用) 
	 * @author li.n1 
	 * @param merchantCode
	 * @param cateNo
	 * @param title
	 * @param key
	 * @return 
	 * @since JDK 1.6
	 */
	int selectTemplateCount(String merchantCode, String cateNo, String title,
			String key);
}
