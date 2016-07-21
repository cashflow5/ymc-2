package com.yougou.kaidian.taobao.topapi.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.ItemProp;
import com.taobao.api.request.ItempropsGetRequest;
import com.taobao.api.response.ItempropsGetResponse;
import com.yougou.kaidian.taobao.annotation.TaobaoApiMethod;
import com.yougou.kaidian.taobao.model.TaobaoApiReturnData;
import com.yougou.kaidian.taobao.topapi.ITaobaoItempropsGet;

/**
 * 获取淘宝商品类目属性数据
 * @author zhuang.rb
 *
 */
@Service
public class TaobaoItempropsGetImpl implements ITaobaoItempropsGet {
	
	private static final Logger logger = LoggerFactory.getLogger(TaobaoItempropsGetImpl.class);

	/**
	 * 获取分类属性
	 * @param taobaoClient
	 * @param cid
	 * @return
	 */
	@TaobaoApiMethod(name="taobao.itemprops.get")
	public TaobaoApiReturnData<List<ItemProp>> getItempropsGet(TaobaoClient taobaoClient, Map<String, String> mapParamData, long cid, String childPath) throws IllegalAccessException, ApiException {
		if(taobaoClient == null) {
			taobaoClient = YougouTaobaoClient.getYougouTaobaoClient();
		}
		TaobaoApiReturnData<List<ItemProp>> taobaoApiReturnData = new TaobaoApiReturnData<List<ItemProp>>();
		ItempropsGetRequest request = new ItempropsGetRequest();//status(状态。可选值:normal(正常),deleted(删除))
		request.setFields("pid,cid,parent_pid,must,multi,prop_values,status,sort_order,parent_vid,name,parent_vid,is_sale_prop,is_color_prop,is_enum_prop,is_item_prop,features,child_template,is_allow_alias,is_input_prop,is_parent");
		request.setCid(cid);
		if(childPath != null) {
			request.setChildPath(childPath);
		}
		logger.info("[淘宝导入]获取分类属性，cid:{},childPath:{}", cid, childPath);
		ItempropsGetResponse response = taobaoClient.execute(request);
		logger.info("[淘宝导入]获取分类属性，返回数据:{}", response);
		if(response.getErrorCode() != null){
			logger.error("[淘宝导入]获取分类属性淘宝API出现问题{},消息为{}", response.getErrorCode(), response.getMsg());
		} else {
			taobaoApiReturnData.setBody(response.getBody());
			taobaoApiReturnData.setReturnData(response.getItemProps());
		}
		return taobaoApiReturnData;
	}
	
}
