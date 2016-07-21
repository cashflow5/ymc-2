package com.belle.yitiansystem.taobao.service;  

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.belle.yitiansystem.taobao.model.TaobaoItemCatPropValue;
import com.belle.yitiansystem.taobao.model.TaobaoItemPropValue;
import com.belle.yitiansystem.taobao.model.vo.TaobaoItemCatParentVo;
import com.belle.yitiansystem.taobao.model.vo.TaobaoItemCatPropVo;
import com.belle.yitiansystem.taobao.model.vo.TaobaoItemCatValueVo;
import com.belle.yitiansystem.taobao.model.vo.TaobaoItemCatVo;
import com.yougou.merchant.api.common.Query;


public interface ITaobaoCatService {
	public PageFinder<Map<String, Object>> findTaobaoCat(TaobaoItemCatVo vo, Query query) ;
	
	/** 
	 * findChildrenById:根据Id查询子节点 
	 * @author li.n1 
	 * @param parentId
	 * @return 
	 * @since JDK 1.6 
	 */  
	public List<Map<String, Object>> findChildrenById(long parentId);
	/**
	 * findFirstTaobaoCat:获得第一级节点 
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6
	 */
	public List<Map<String, Object>> selectAllFirstCat();

	/** 
	 * viewCatPropDetail:淘宝分类属性详
	 * @author li.n1 
	 * @param cid 
	 * @since JDK 1.6 
	 */  
	public Map<TaobaoItemCatPropVo,List<TaobaoItemCatValueVo>> findCatPropDetail(long cid);

	/** 
	 * checkTaobaoCat:检验是否末节点 
	 * @author li.n1 
	 * @param cid
	 * @return 
	 * @since JDK 1.6 
	 */  
	public List<TaobaoItemCatParentVo> checkTaobaoCat(String[] ids);

	/** 
	 * deleteTaobaoCat:删除淘宝分类（逻辑删除，改变status的状态，由normal转delete） 
	 * @author li.n1 
	 * @param split 
	 * @since JDK 1.6 
	 */  
	public void deleteTaobaoCat(String[] split,HttpServletRequest request);

	/**
	 * 根据Cid查询属性
	 * @param cid
	 * @return
	 */
	
	public List<TaobaoItemCatPropValue> saveTaobaoPropValue(HttpServletRequest request,String cid)throws BusinessException;
	
	
	public void delTaobaoProVal(Long cid,Long pid,Long vid,HttpServletRequest request)throws BusinessException;
}
