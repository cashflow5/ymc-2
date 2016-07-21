package com.yougou.api.adapter.impl;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.yougou.api.adapter.MerchantToolsTarget;

/**
 * 
 * @author 杨梦清
 *
 */
@Component
public class MerchantToolsAdapter implements MerchantToolsTarget {

	@Override
	public Date getTime() {
		return new Date();
	}
	
}
