package com.yougou.kaidian.taobao.topapi.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.response.ItemGetResponse;
import com.yougou.kaidian.taobao.annotation.TaobaoApiMethod;
import com.yougou.kaidian.taobao.model.TaobaoApiReturnData;
import com.yougou.kaidian.taobao.topapi.ITaobaoItemGet;
/**
 * 获取淘宝商品数据
 * @author zhuang.rb
 *
 */
@Service
public class TaobaoItemGetImpl implements ITaobaoItemGet {
	
	private static final Logger logger = LoggerFactory.getLogger(TaobaoItemGetImpl.class);

	/**
	 * 获取淘宝商品
	 * @param taobaoClient
	 * @param sessionKey
	 * @param numIid
	 * @return
	 */
	@TaobaoApiMethod(name="taobao.item.get")
	public TaobaoApiReturnData<Item> getItemGet(TaobaoClient taobaoClient, Map<String, String> mapParamData, long numIid) throws IllegalAccessException, ApiException {
		if(taobaoClient == null) {
			taobaoClient = YougouTaobaoClient.getYougouTaobaoClient();
		}
		String sessionKey = null;
		if(mapParamData == null || (sessionKey = mapParamData.get("sessionKey")) == null || "".equals(sessionKey)){
			logger.error("[淘宝导入]获取淘宝商品出现问题,sessionKey为空");
			throw new IllegalAccessException("mapParamData -> key -> sessionKey == null");
		}
		TaobaoApiReturnData<Item> taobaoApiReturnData = new TaobaoApiReturnData<Item>();
		ItemGetRequest request=new ItemGetRequest();
		StringBuilder sb = new StringBuilder(512);
		sb.append("cid,num_iid,title,price,nick,desc,props,props_name,input_pids,input_str,created,pic_url,list_time,");
		sb.append("delist_time,modified,violation,detail_url,sell_point,outer_id,wireless_desc,barcode,item_weight,item_size,");
		sb.append("is_xinpin,is_fenxiao,is_timing,is_taobao,approve_status,item_img.url,item_img.position,item_img.id,");
		sb.append("prop_img.id,prop_img.url,prop_img.properties,prop_img.position,");
		sb.append("sku.sku_id,sku.num_iid,sku.outer_id,sku.barcode,sku.properties,sku.properties_name,sku.quantity");
		/**
			item_info = "cid,num_iid,title,price,nick,desc,props,props_name,input_pids,input_str,created,pic_url,list_time,"
				+ "delist_time,modified,violation,detail_url,sell_point,outer_id,wireless_desc,barcode,item_weight,item_size,"
				+ "is_xinpin,is_fenxiao,is_timing,is_taobao,approve_status";
			item_imgs = ",item_img.url,item_img.position,item_img.id";
			prop_imgs = ",prop_img.id,prop_img.url,prop_img.properties,prop_img.position";
			skus = ",sku.sku_id,sku.num_iid,sku.outer_id,sku.barcode,sku.properties,sku.properties_name,sku.quantity";
		 */
		request.setFields(sb.toString());
		request.setNumIid(numIid);
		logger.info("[淘宝导入]获取淘宝商品,numIid:{}",numIid);
		ItemGetResponse response = taobaoClient.execute(request , sessionKey);
		logger.info("[淘宝导入]获取淘宝商品,返回数据:{}",response);
		if (response.getErrorCode() != null) {
			logger.error("[淘宝导入]获取淘宝商品,API出现问题{},消息为{}", response.getErrorCode(), response.getMsg());
		} else {
			taobaoApiReturnData.setBody(response.getBody());
			taobaoApiReturnData.setReturnData(response.getItem());
		}
		return taobaoApiReturnData;
	}
}
