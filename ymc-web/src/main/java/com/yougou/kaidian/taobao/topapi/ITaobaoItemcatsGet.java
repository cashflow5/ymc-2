package com.yougou.kaidian.taobao.topapi;

import java.util.List;
import java.util.Map;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.ItemCat;
import com.yougou.kaidian.taobao.model.TaobaoApiReturnData;

public interface ITaobaoItemcatsGet {
	
	public TaobaoApiReturnData<List<ItemCat>> getSubItemcatsGet(TaobaoClient taobaoClient, Map<String, String> mapParamData, long cid) throws IllegalAccessException, ApiException;
	
	public TaobaoApiReturnData<List<ItemCat>> getItemcatGet(TaobaoClient taobaoClient, Map<String, String> mapParamData, String cids) throws IllegalAccessException, ApiException;
	
}
