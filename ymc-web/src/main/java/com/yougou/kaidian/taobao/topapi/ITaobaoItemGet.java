package com.yougou.kaidian.taobao.topapi;

import java.util.Map;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.yougou.kaidian.taobao.model.TaobaoApiReturnData;

public interface ITaobaoItemGet {
	public TaobaoApiReturnData<Item> getItemGet(TaobaoClient taobaoClient, Map<String, String> mapParamData, long numIid) throws IllegalAccessException, ApiException;
}
