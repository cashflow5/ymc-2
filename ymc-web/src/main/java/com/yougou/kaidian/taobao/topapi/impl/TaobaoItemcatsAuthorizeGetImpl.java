package com.yougou.kaidian.taobao.topapi.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.SellerAuthorize;
import com.taobao.api.request.ItemcatsAuthorizeGetRequest;
import com.taobao.api.response.ItemcatsAuthorizeGetResponse;
import com.yougou.kaidian.taobao.annotation.TaobaoApiMethod;
import com.yougou.kaidian.taobao.model.TaobaoApiReturnData;
import com.yougou.kaidian.taobao.topapi.ITaobaoItemcatsAuthorizeGet;

/**
 * 淘宝获取商家授权类目、品牌数据
 * @author zhuang.rb
 *
 */
@Service
public class TaobaoItemcatsAuthorizeGetImpl implements ITaobaoItemcatsAuthorizeGet {

	private static final Logger logger = LoggerFactory.getLogger(TaobaoItemcatsAuthorizeGetImpl.class);

	/**
	 * 获取授权类目（分类、品牌）
	 * @param taobaoClient
	 * @param mapParamData
	 * @return
	 * @throws IllegalAccessException
	 * @throws ApiException
	 */
	@TaobaoApiMethod(name="taobao.itemcats.authorize.get")
	public TaobaoApiReturnData<SellerAuthorize> getItemcatsAuthorizeGet(TaobaoClient taobaoClient, Map<String, String> mapParamData) throws IllegalAccessException, ApiException {
		if(taobaoClient == null) {
			taobaoClient = YougouTaobaoClient.getYougouTaobaoClient();
		}
		String sessionKey = null;
		if(mapParamData == null || (sessionKey = mapParamData.get("sessionKey")) == null || "".equals(sessionKey)){
			logger.error("[淘宝导入]获取授权类目（分类、品牌）出现问题,sessionKey为空");
			throw new IllegalAccessException("mapParamData -> key -> sessionKey == null");
		}
		TaobaoApiReturnData<SellerAuthorize> taobaoApiReturnData = new TaobaoApiReturnData<SellerAuthorize>();
		ItemcatsAuthorizeGetRequest request = new ItemcatsAuthorizeGetRequest();
		request.setFields("brand.vid, brand.name, brand.pid, brand.prop_name, item_cat.cid, item_cat.name, item_cat.status,item_cat.sort_order,item_cat.parent_cid,item_cat.is_parent, xinpin_item_cat.cid, xinpin_item_cat.name, xinpin_item_cat.status, xinpin_item_cat.sort_order, xinpin_item_cat.parent_cid, xinpin_item_cat.is_parent");
		logger.info("[淘宝导入]获取授权类目（分类、品牌）API,sessionKey{}" ,sessionKey);
		ItemcatsAuthorizeGetResponse response = taobaoClient.execute(request, sessionKey);
		logger.info("[淘宝导入]获取授权类目（分类、品牌）API,返回数据:{}" ,response);
		if (response.getErrorCode() != null) {
			logger.error("[淘宝导入]获取授权类目（分类、品牌）API出现问题{},消息为{}", response.getErrorCode(), response.getMsg());
		} else {
			taobaoApiReturnData.setBody(response.getBody());
			taobaoApiReturnData.setReturnData(response.getSellerAuthorize());
		}
		return taobaoApiReturnData;
	}

}
