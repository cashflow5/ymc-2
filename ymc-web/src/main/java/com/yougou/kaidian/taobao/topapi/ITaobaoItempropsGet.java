package com.yougou.kaidian.taobao.topapi;

import java.util.List;
import java.util.Map;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.ItemProp;
import com.yougou.kaidian.taobao.model.TaobaoApiReturnData;

public interface ITaobaoItempropsGet {
	public TaobaoApiReturnData<List<ItemProp>> getItempropsGet(TaobaoClient taobaoClient, Map<String, String> mapParamData, long cid, String childPath) throws IllegalAccessException, ApiException;
}
