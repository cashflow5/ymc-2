package com.yougou.api.adapter.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.yougou.api.adapter.MerchantAsmTarget;
import com.yougou.dto.input.ReturnQualityQueryInputDto;
import com.yougou.yop.api.IMerchantApiAsmService;

/**
 * <p>商家中心售后接口适配器</p>
 * 
 * @author huang.tao
 * @param <ReturnQualityInputDto>
 *
 */
@Component
public class MerchantAsmAdapter<ReturnQualityInputDto> implements MerchantAsmTarget {
	
	@Resource
	private IMerchantApiAsmService afterSaleApi;
	
	@Override
	public Object queryQualityReturn(Map<String, Object> parameterMap) throws Exception {
		ReturnQualityQueryInputDto dto = new ReturnQualityQueryInputDto();
		Map<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.putAll(parameterMap);
		for (String key : parameterMap.keySet()) {
			if ("applyStartTime".equals(key) || "applyEndTime".equals(key)) {
				if (null == parameterMap.get(key) 
						|| StringUtils.isBlank(parameterMap.get(key).toString())) {
					tempMap.remove(key);
				}
			}
		}
		BeanUtils.populate(dto, tempMap);

		return afterSaleApi.queryReturnQualityList(dto);
	}

	@Override
	public Object addQualityReturn(Map<String, Object> parameterMap) throws Exception {
		return afterSaleApi.addQualityReturn(parameterMap);
	}

	@Override
	public Object addQualityRejection(Map<String, Object> parameterMap) throws Exception {
		return afterSaleApi.addQualityRejection(parameterMap);
	}

	@Override
	public Object getReturnWarehouse(Map<String, Object> parameterMap)
			throws Exception {
		// TODO Auto-generated method stub
		return afterSaleApi.getReturnWarehouse(parameterMap.get("order_sub_no").toString());
	}

	
	
}
