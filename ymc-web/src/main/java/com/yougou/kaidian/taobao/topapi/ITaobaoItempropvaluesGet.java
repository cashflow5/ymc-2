package com.yougou.kaidian.taobao.topapi;

import java.util.List;
import java.util.Map;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.PropValue;
import com.yougou.kaidian.taobao.model.TaobaoApiReturnData;

public interface ITaobaoItempropvaluesGet {
	public TaobaoApiReturnData<List<PropValue>> getItempropvaluesGet(TaobaoClient taobaoClient, Map<String, String> mapParamData, long cid, long pid) throws IllegalAccessException, ApiException;
}
