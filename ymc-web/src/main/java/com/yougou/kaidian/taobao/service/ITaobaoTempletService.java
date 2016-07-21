package com.yougou.kaidian.taobao.service;

import java.util.List;
import java.util.Map;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.taobao.model.TaobaoItemCat;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemCatTemplet;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemPropTemplet;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemPropValueTemplet;


public interface ITaobaoTempletService {
	PageFinder<TaobaoYougouItemCatTemplet> findTaobaoYougouItemCatTemplet(Map<String,Object> params,String merchantCode, Query query);
	
	List<TaobaoItemCat>selectTaobaoItemCatList(Map<String,Object> params);
	
	TaobaoYougouItemCatTemplet findTaobaoYougouItemCatById(String id);
	
	List<TaobaoYougouItemPropTemplet> findTaobaoYougouItemCatPropTemplet(Map<String,Object> params);
	
	List<TaobaoYougouItemPropTemplet> findTaobaoYougouItemCatPropTempletAndValues(Map<String,Object> params);
	
	List<TaobaoYougouItemPropValueTemplet>findTaobaoYougouItemCatPropValueTempletByTaobaoPid(String taobaoPid);
}

