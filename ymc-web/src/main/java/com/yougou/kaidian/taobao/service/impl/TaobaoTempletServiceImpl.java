package com.yougou.kaidian.taobao.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yougou.kaidian.commodity.dao.CommodityPropertyMapper;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.commodity.pojo.Cat;
import com.yougou.kaidian.taobao.dao.TaobaoYougouItemCatTempletMapper;
import com.yougou.kaidian.taobao.model.TaobaoItemCat;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemCatTemplet;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemPropTemplet;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemPropValueTemplet;
import com.yougou.kaidian.taobao.service.ITaobaoTempletService;


@Service
public class TaobaoTempletServiceImpl implements ITaobaoTempletService {
	
	@Resource
	private CommodityPropertyMapper catMapper;
	
	@Resource
	private TaobaoYougouItemCatTempletMapper taobaoYougouItemCatTempletMapper;
	
	public PageFinder<TaobaoYougouItemCatTemplet> findTaobaoYougouItemCatTemplet(Map<String,Object> params,String merchantCode, Query query){
		
		List<Cat> _cats = catMapper.querySupplierCatListByStructName(
				merchantCode, "");
		
		List<TaobaoYougouItemCatTemplet> list = taobaoYougouItemCatTempletMapper.selectTaobaoYougouItemCatTempletByCats(_cats,params,query);
		int count = taobaoYougouItemCatTempletMapper.selectTaobaoYougouItemCatTempletCountByCats(_cats,params);
		PageFinder<TaobaoYougouItemCatTemplet> pageFinder = new PageFinder<TaobaoYougouItemCatTemplet>(
				query.getPage(), query.getPageSize(), count, list);
		return pageFinder;
	}

	@Override
	public List<TaobaoItemCat> selectTaobaoItemCatList(
			Map<String, Object> params) {
		return taobaoYougouItemCatTempletMapper.selectTaobaoItemCatList(params);
	}

	@Override
	public TaobaoYougouItemCatTemplet findTaobaoYougouItemCatById(String id) {
		return taobaoYougouItemCatTempletMapper.selectTaobaoYougouItemCatTempletById(id);
	}

	@Override
	public List<TaobaoYougouItemPropTemplet> findTaobaoYougouItemCatPropTemplet(
			Map<String, Object> params) {
		return taobaoYougouItemCatTempletMapper.selectTaobaoYougouItemCatPropTemplet(params);
	}

	
	
	@Override
	public List<TaobaoYougouItemPropTemplet> findTaobaoYougouItemCatPropTempletAndValues(
			Map<String, Object> params) {
		List<TaobaoYougouItemPropTemplet> propList = taobaoYougouItemCatTempletMapper.selectTaobaoYougouItemCatPropTemplet(params);
		for(TaobaoYougouItemPropTemplet prop:propList){
			prop.setValueList(taobaoYougouItemCatTempletMapper.selectTaobaoYougouItemCatPropValueTempletByTaobaoPid(prop.getTaobaoPid().toString()));
		}
		return propList;
	}

	@Override
	public List<TaobaoYougouItemPropValueTemplet> findTaobaoYougouItemCatPropValueTempletByTaobaoPid(
			String taobaoPid) {
		return taobaoYougouItemCatTempletMapper.selectTaobaoYougouItemCatPropValueTempletByTaobaoPid(taobaoPid);
	}
	
}
