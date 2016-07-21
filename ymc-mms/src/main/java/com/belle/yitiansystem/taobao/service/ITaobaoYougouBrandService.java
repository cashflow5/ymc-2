package com.belle.yitiansystem.taobao.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.belle.yitiansystem.taobao.model.TaobaoBrand;
import com.belle.yitiansystem.taobao.model.TaobaoYougouBrand;
import com.yougou.merchant.api.common.PageFinder;
import com.yougou.merchant.api.common.Query;

public interface ITaobaoYougouBrandService {
	public PageFinder<TaobaoYougouBrand> findTaoaoYougouBrandList(
			Map<String, Object> map, Query query);
	
	public List<TaobaoYougouBrand>findTaoaoYougouBrandList(Map<String, Object> map);
	
	public void deleteBatch(String ids,HttpServletRequest request) throws BusinessException;
	
	public void saveTaobaoYougouBrandBatch(String[] yougouBrandNos, String[] yougouBrandNames, String[] taobaoBrandNos, String[] taobaoBrandNames, String creater) throws BusinessException;
	
	public List<TaobaoBrand>findTaobaoBrandList(String taobaoBrandName);
}
