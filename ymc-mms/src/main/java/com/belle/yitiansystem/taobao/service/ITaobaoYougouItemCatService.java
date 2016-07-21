package com.belle.yitiansystem.taobao.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.belle.yitiansystem.taobao.model.TaobaoItemCatPropDto;
import com.belle.yitiansystem.taobao.model.TaobaoItemCatPropValueDto;
import com.belle.yitiansystem.taobao.model.TaobaoYougouItemCat;
import com.belle.yitiansystem.taobao.model.ZTree;
import com.yougou.merchant.api.common.PageFinder;
import com.yougou.merchant.api.common.Query;
import com.yougou.pc.model.prop.PropItem;

public interface ITaobaoYougouItemCatService {
	public PageFinder<TaobaoYougouItemCat> findTaobaoYougouItemCatList(
			Map<String, Object> map, Query query);
	
	/**
	 * 查询优购没绑定的分类
	 * @param structName
	 * @param filterBind TODO
	 * @return
	 */
	public List<ZTree> findNoBindYougouItemCatZTree(String structName, boolean filterBind);
	
	/**
	 * 查询淘宝没绑定分类
	 * @param map
	 * @return
	 */
	public List<ZTree> findNoBindTaobaoItemCatList(Map<String,Object>map);
	
	public String bindYougouItemCate(Map<String, Object> params)
			throws BusinessException;
	

	/**
	 * 通过ID查询分类
	 * @param id
	 * @return
	 */
	public TaobaoYougouItemCat findTaobaoYougouItemCatById(@Param("id") String id);
	
	
	public List<PropItem> getPropMsgByCatIdNew(String catId, boolean showSize);
	
	public List<TaobaoItemCatPropDto> selectItemProWidthBindYougouItemPro(String catBindId);
	
	/**
	 * 查询淘宝属性值
	 * @param pid
	 * @param cid
	 * @param catBindId
	 * @return
	 */
	public List<TaobaoItemCatPropValueDto> selectTaobaoItemProValWidthYouItemProVal(Long pid, Long cid, String catBindId);
	
	
	/**
	 * 绑定分类属性值
	 * @param request
	 * @throws BusinessException
	 */
	public void bindTaobaoYougouItemProAndVal(HttpServletRequest request,String operater)
			throws BusinessException;
	
	/**
	 * 删除分类
	 * @param ids
	 * @throws BusinessException
	 */
	public void deleteYougouTaobaoItemCat(String ids,HttpServletRequest request)throws BusinessException;
}
