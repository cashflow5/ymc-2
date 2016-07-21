package com.yougou.api.adapter.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yougou.api.adapter.MerchantLogisticsTarget;
import com.yougou.yop.api.IMerchantApiLogisticsService;

/**
 * 商家物流适配器
 * 
 * @author 杨梦清
 * 
 */
@Component
public class MerchantLogisticsAdapter implements MerchantLogisticsTarget {

	@Resource
	private IMerchantApiLogisticsService merchantLogisticsApi;
	
	@Override
	public Object getLogisticscompany() throws Exception {
		return merchantLogisticsApi.getLogisticscompany();
	}

	@Override
	public Object getExpresscompany() throws Exception {
		return merchantLogisticsApi.getExpresscompany();
	}

	@Override
	public Object importDeliveryDetail(Map<String, Object> parameterMap) throws Exception {
		// TODO Auto-generated method stub		
		Boolean result = merchantLogisticsApi.importDeliveryDetail(parameterMap);
		return result;
	}
	

	@Override
	public Object getStorageNo(Map<String, Object> parameterMap)
			throws Exception {
		// TODO Auto-generated method stub
		List<String> storageNos = merchantLogisticsApi.getStorageNo(parameterMap);
		return storageNos;
	}

}

