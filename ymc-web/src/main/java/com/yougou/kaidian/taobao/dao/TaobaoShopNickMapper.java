package com.yougou.kaidian.taobao.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.taobao.model.TaobaoShop;

public interface TaobaoShopNickMapper {

	public void insertTaobaoShop(TaobaoShop taobaoShop);
	
	public List<TaobaoShop> getTaobaoShopList(String merchantCode, RowBounds rowBounds);
	
	public int getTaobaoShopListCount(String merchantCode);
	
	public void updateTaobaoShopStatus(Map<String,Object> paraMap);
	
	public int getTaobaoShopByNameCount(String taobaoShopName);
	
	public TaobaoShop getTaobaoShopByID(String id);
	
	public void updateTaobaoShop(TaobaoShop taobaoShop);
	
	public void updateTaobaoShopByNickName(TaobaoShop taobaoShop);
	
	public TaobaoShop getTaobaoShopByNickName(String nickName);
	
	/**
	 * 通过授权状态查询所有店铺
	 * 
	 * @param merchantCode
	 * @param status
	 * @return
	 */
	public List<TaobaoShop> getAllTaobaoShopListByStatus(
			@Param("merchantCode") String merchantCode,
			@Param("status") String status);
}
