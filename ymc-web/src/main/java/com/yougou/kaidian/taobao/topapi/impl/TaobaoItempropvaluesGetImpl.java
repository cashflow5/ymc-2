package com.yougou.kaidian.taobao.topapi.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.PropValue;
import com.taobao.api.request.ItempropvaluesGetRequest;
import com.taobao.api.response.ItempropvaluesGetResponse;
import com.yougou.kaidian.taobao.annotation.TaobaoApiMethod;
import com.yougou.kaidian.taobao.model.TaobaoApiReturnData;
import com.yougou.kaidian.taobao.topapi.ITaobaoItempropvaluesGet;
@Service
public class TaobaoItempropvaluesGetImpl implements ITaobaoItempropvaluesGet {
	
	private static final Logger logger = LoggerFactory.getLogger(TaobaoItempropvaluesGetImpl.class);
	
	/**
	 * 获取淘宝属性值
	 * @param taobaoClient
	 * @param mapParamData
	 * @param cid
	 * @param pid
	 * @return
	 * @throws IllegalAccessException
	 * @throws ApiException
	 */
	@TaobaoApiMethod(name="taobao.itempropvalues.get")
	public TaobaoApiReturnData<List<PropValue>> getItempropvaluesGet(TaobaoClient taobaoClient, Map<String, String> mapParamData, long cid, long pid) throws IllegalAccessException, ApiException {
		if(taobaoClient == null) {
			taobaoClient = YougouTaobaoClient.getYougouTaobaoClient();
		}
		TaobaoApiReturnData<List<PropValue>> taobaoApiReturnData = new TaobaoApiReturnData<List<PropValue>>();
		ItempropvaluesGetRequest request = new ItempropvaluesGetRequest();
		request.setFields("cid,pid,prop_name,vid,name,name_alias,status,sort_order");
		request.setCid(cid);
		request.setPvs(""+pid);
//		request.setType(1L);
		logger.info("[淘宝导入]获取分类属性值，cid:{},pvs:{}", cid, pid);
		ItempropvaluesGetResponse response = taobaoClient.execute(request);
		logger.info("[淘宝导入]获取分类属性值，返回数据:{}", response);
		if(response.getErrorCode() != null){
			logger.error("[淘宝导入]获取分类属性值淘宝API出现问题{},消息为{}", response.getErrorCode(), response.getMsg());
		} else {
			taobaoApiReturnData.setBody(response.getBody());
			taobaoApiReturnData.setReturnData(response.getPropValues());
		}
		return taobaoApiReturnData;
	}

}
