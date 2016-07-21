package com.yougou.kaidian.taobao.topapi.impl;

/**
 * 淘宝获取类目节点数据
 */
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.ItemCat;
import com.taobao.api.request.ItemcatsGetRequest;
import com.taobao.api.response.ItemcatsGetResponse;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.taobao.annotation.TaobaoApiMethod;
import com.yougou.kaidian.taobao.model.TaobaoApiReturnData;
import com.yougou.kaidian.taobao.topapi.ITaobaoItemcatsGet;
@Service
public class TaobaoItemcatsGetImpl implements ITaobaoItemcatsGet {

	private static final Logger logger = LoggerFactory.getLogger(TaobaoItemcatsGetImpl.class);

	/**
	 * 获取淘宝类目
	 * @param taobaoClient
	 * @param mapParamData
	 * @param cid
	 * @return
	 * @throws IllegalAccessException
	 * @throws ApiException
	 */
	@TaobaoApiMethod(name="taobao.itemcats.get")
	public TaobaoApiReturnData<List<ItemCat>> getSubItemcatsGet(TaobaoClient taobaoClient, Map<String, String> mapParamData, long cid) throws IllegalAccessException, ApiException {
		if(taobaoClient == null) {
			taobaoClient = YougouTaobaoClient.getYougouTaobaoClient();
			YmcThreadLocalHolder.setTaobaoClient(taobaoClient);
		}
		TaobaoApiReturnData<List<ItemCat>> taobaoApiReturnData = new TaobaoApiReturnData<List<ItemCat>>();
		ItemcatsGetRequest request = new ItemcatsGetRequest();
		request.setFields("cid,parent_cid,name,is_parent,sort_order,status");
		request.setParentCid(cid);
		logger.info("[淘宝导入]获取淘宝类目,parentCid:{}", cid);
		ItemcatsGetResponse response = taobaoClient.execute(request);
		logger.info("[淘宝导入]获取淘宝类目,返回数据:{}", response);
		if(response.getErrorCode() != null){
			logger.error("[淘宝导入]获取淘宝类目API出现问题{},消息为{}", response.getErrorCode(), response.getMsg());
		} else {
			taobaoApiReturnData.setBody(response.getBody());
			taobaoApiReturnData.setReturnData(response.getItemCats());
		}
		return taobaoApiReturnData;
	}

	/**
	 * 获取淘宝类目
	 * @param taobaoClient
	 * @param mapParamData
	 * @param cid
	 * @return
	 * @throws IllegalAccessException
	 * @throws ApiException
	 */
	@TaobaoApiMethod(name="taobao.itemcats.get")
	public TaobaoApiReturnData<List<ItemCat>> getItemcatGet(TaobaoClient taobaoClient, Map<String, String> mapParamData, String cids) throws IllegalAccessException, ApiException {
		if(taobaoClient == null) {
			taobaoClient = YougouTaobaoClient.getYougouTaobaoClient();
		}
		TaobaoApiReturnData<List<ItemCat>> taobaoApiReturnData = new TaobaoApiReturnData<List<ItemCat>>();
		ItemcatsGetRequest request = new ItemcatsGetRequest();
		request.setFields("cid,parent_cid,name,is_parent,sort_order,status");
		request.setCids(cids);
		logger.info("[淘宝导入]获取淘宝类目,cid:{}", cids);
		ItemcatsGetResponse response = taobaoClient.execute(request);
		logger.info("[淘宝导入]获取淘宝类目,返回数据:{}", response);
		if(response.getErrorCode() != null){
			logger.error("获取淘宝类目API出现问题{},消息为{}", response.getErrorCode(), response.getMsg());
		} else {
			taobaoApiReturnData.setBody(response.getBody());
			taobaoApiReturnData.setReturnData(response.getItemCats());
		}
		return taobaoApiReturnData;
	}
}
