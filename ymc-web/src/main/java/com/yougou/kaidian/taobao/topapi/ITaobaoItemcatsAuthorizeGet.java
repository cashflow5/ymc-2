package com.yougou.kaidian.taobao.topapi;

import java.util.Map;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.SellerAuthorize;
import com.yougou.kaidian.taobao.model.TaobaoApiReturnData;

public interface ITaobaoItemcatsAuthorizeGet {
	
	public TaobaoApiReturnData<SellerAuthorize> getItemcatsAuthorizeGet(
			TaobaoClient taobaoClient, Map<String, String> mapParamData)
			throws IllegalAccessException, ApiException;
	
}
