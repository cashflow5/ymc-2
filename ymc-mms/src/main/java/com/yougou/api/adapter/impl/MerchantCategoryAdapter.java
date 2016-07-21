package com.yougou.api.adapter.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yougou.api.adapter.MerchantCategoryTarget;
import com.yougou.yop.api.IMerchantApiCategoryService;

/**
 * 
 * @author 杨梦清
 * 
 */
@Component
public class MerchantCategoryAdapter implements MerchantCategoryTarget {
	
	@Resource
	private IMerchantApiCategoryService merchantApiCategory;
	
	@Override
	public Object queryBrand(Map<String, Object> parameterMap) throws Exception {
		return merchantApiCategory.queryBrand(parameterMap);
	}

	@Override
	public Object queryCat(Map<String, Object> parameterMap) throws Exception {
		return merchantApiCategory.queryCat(parameterMap);
	}

}

