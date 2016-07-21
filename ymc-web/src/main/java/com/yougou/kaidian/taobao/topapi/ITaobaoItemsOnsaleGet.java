package com.yougou.kaidian.taobao.topapi;

import java.util.Map;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.yougou.kaidian.taobao.model.TaobaoApiReturnData;
import com.yougou.kaidian.taobao.model.TaobaoItemOnSaleVO;

public interface ITaobaoItemsOnsaleGet {
	public TaobaoApiReturnData<TaobaoItemOnSaleVO> getItemsOnsaleGet(TaobaoClient taobaoClient, Map<String, String> mapParamData, Long cid, long pageNo, long pageSize) throws IllegalAccessException, ApiException;
}
