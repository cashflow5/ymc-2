package com.yougou.kaidian.taobao.service;

import java.util.List;
import java.util.Map;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.taobao.model.TaobaoShop;


public interface ITaobaoAuthService {
	
	void insertTaobaoShop(TaobaoShop taobaoShop);
	
	PageFinder<TaobaoShop> getTaobaoShopList(Query query,String merchantCode);
	
	void updateTaobaoShopStatus(Map<String,Object> paraMap);
	
	boolean checkTaobaoShopByName(String taobaoShopName);
	
	TaobaoShop getTaobaoShopByID(String id);
	
	void updateTaobaoShop(TaobaoShop taobaoShop);
	
	boolean authorization(Map<String, Object> params,String merchantCode,String loginUser) throws Exception;
	
	List<TaobaoShop> getAllTaobaoShopListByStatus(String merchantCode,
			String status);
	
	void importTaobaoBasicDataToYougou(Map<String, Object> params,
			String merchantCode, String loginUser);
	 
	String importTaobaoCatPropToYougou(String sessionKey, String merchantCode, String operater, String cids) throws IllegalAccessException;
}
