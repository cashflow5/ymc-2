package com.belle.yitiansystem.merchant.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.belle.yitiansystem.merchant.service.IMerchantApiEarlyWarningService;

@Service
public class MerchantApiEarlyWarningServiceImpl implements IMerchantApiEarlyWarningService {

	@Override
	public void handleMerchantApiEarlyWarning(Exception ex) {
		
		handleMerchantApiEarlyWarning(ex, null);
	}

	@Override
	public void handleMerchantApiEarlyWarning(Exception ex, Map<String, Object> parameterMap) {
		
		if (ex == null) return;
		
		System.out.println("api early warning for : " + ex.getMessage());
	}
}
