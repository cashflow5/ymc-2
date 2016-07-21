package com.yougou.kaidian.taobao.topapi.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.yougou.kaidian.taobao.annotation.TaobaoApiMethod;
import com.yougou.kaidian.taobao.model.TaobaoApiReturnData;
import com.yougou.kaidian.taobao.model.TaobaoItemOnSaleVO;
import com.yougou.kaidian.taobao.topapi.ITaobaoItemsOnsaleGet;

/**
 * 商家获取淘宝店铺在售商品数据
 * @author zhuang.rb
 *
 */
@Service
public class TaobaoItemsOnsaleGetImpl implements ITaobaoItemsOnsaleGet{
	
	private static final Logger logger = LoggerFactory.getLogger(TaobaoItemsOnsaleGetImpl.class);

	/**
	 * 获取在售商品数据接口
	 * @param taobaoClient
	 * @param sessionKey
	 * @param cid
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	 @TaobaoApiMethod(name="taobao.items.onsale.get")
	public TaobaoApiReturnData<TaobaoItemOnSaleVO> getItemsOnsaleGet(TaobaoClient taobaoClient, Map<String, String> mapParamData, Long cid, long pageNo, long pageSize) throws IllegalAccessException, ApiException {
		if(taobaoClient == null) {
			taobaoClient = YougouTaobaoClient.getYougouTaobaoClient();
		}
		String sessionKey = null;
		if(mapParamData == null || (sessionKey = mapParamData.get("sessionKey")) == null || "".equals(sessionKey)){
			logger.error("[淘宝导入]获取商家淘宝店铺在售商品出现问题,sessionKey为空");
			throw new IllegalAccessException("mapParamData -> key -> sessionKey == null");
		}
		 TaobaoApiReturnData<TaobaoItemOnSaleVO> taobaoApiReturnData = new TaobaoApiReturnData<TaobaoItemOnSaleVO>();
		TaobaoItemOnSaleVO taobaoItemOnSaleVO = null;
		ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
		request.setFields("cid,num_iid,title,price,nick");
		if(cid != null) {
			request.setCid(cid);
		}
//		request.setQ("title");//按照商品名称搜索
//		request.setStartModified(startModified);//修改起始时间
//		request.setEndModified(endModified);//修改结束时间
//		request.setIsTaobao(true);
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		request.setOrderBy("list_time:desc");
		logger.info("[淘宝导入]获取商家淘宝店铺在售商品,cid:{},pageNo:{},pageSize:{}", cid, pageNo, pageSize);
		ItemsOnsaleGetResponse response = taobaoClient.execute(request, sessionKey);
		logger.info("[淘宝导入]获取商家淘宝店铺在售商品,返回数据:{}", response);
		if(response.getErrorCode() != null){
			logger.error("[淘宝导入]获取商家淘宝店铺在售商品API出现问题{},消息为{}", response.getErrorCode(), response.getMsg());
		} else {
			taobaoApiReturnData.setBody(response.getBody());
			taobaoItemOnSaleVO = new TaobaoItemOnSaleVO();
			taobaoItemOnSaleVO.setTotalResult(response.getTotalResults());
			taobaoItemOnSaleVO.setLstItem(response.getItems());
			taobaoApiReturnData.setReturnData(taobaoItemOnSaleVO);
		}
		return taobaoApiReturnData;
	}
	
}
