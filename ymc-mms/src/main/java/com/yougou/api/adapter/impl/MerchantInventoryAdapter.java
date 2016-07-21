package com.yougou.api.adapter.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.yougou.api.adapter.MerchantInventoryTarget;
import com.yougou.dto.input.QueryInventoryInputDto;
import com.yougou.dto.input.UpdateInventoryInputDto;
import com.yougou.yop.api.IMerchantApiInventoryService;

@Component
public class MerchantInventoryAdapter implements MerchantInventoryTarget {
	
	@Resource
	private IMerchantApiInventoryService inventoryApiService;
	
	@Override
	public Object updateInventory(Map<String, Object> parameterMap) throws Exception {
		UpdateInventoryInputDto dto = new UpdateInventoryInputDto();
		BeanUtils.populate(dto, parameterMap);
		return inventoryApiService.updateMerchantInventory(dto);
	}

	@Override
	public Object queryInventory(Map<String, Object> parameterMap) throws Exception {
		QueryInventoryInputDto dto = new QueryInventoryInputDto();
		BeanUtils.populate(dto, parameterMap);
		return inventoryApiService.queryMerchantInventory(dto);
	}


}
