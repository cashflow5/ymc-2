package com.belle.yitiansystem.taobao.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.belle.yitiansystem.taobao.model.TaobaoBrand;
import com.yougou.merchant.api.common.PageFinder;
import com.yougou.merchant.api.common.Query;

public interface ITaobaoBrandService {
	
	public void addBrand(TaobaoBrand brand,String userName,HttpServletRequest request) throws BusinessException;
	
	public PageFinder<TaobaoBrand> findTaoaoBrandList(Map<String, Object> map, Query query);
	
	public List<TaobaoBrand> findTaobaoBrandList(Map<String,Object> map);
	
	public void deleteTaobaoBrand(String bid,HttpServletRequest request)throws BusinessException;
	
	public void updateBrand(TaobaoBrand brand,HttpServletRequest requests);
}
