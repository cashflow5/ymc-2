package com.yougou.kaidian.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.common.base.Query;
import com.yougou.merchant.api.supplier.vo.BackOrderDetailVo;
import com.yougou.merchant.api.supplier.vo.BackOrderVo;

/**
 * 商家中心首尔直发二期：退回单确认收货 持久层映射接口
 * @author zhang.f1
 *
 */
public interface YmcBackOrderMapper {
	
	/**
	 * 商家中心首尔直发二期：确认收货，主退回单列表查询
	 * @param backOrderVo
	 * @return
	 */
	List<BackOrderVo> queryBackOrderList(@Param("backOrderVo")BackOrderVo backOrderVo,@Param("query")Query query,@Param("params")Map<String,Object> params);
	
	/**
	 * 商家中心首尔直发二期：确认收货，主退回单总数查询
	 * @param backOrderVo
	 * @param query
	 * @param params
	 * @return
	 */
	int queryBackOrderCount (@Param("backOrderVo")BackOrderVo backOrderVo,@Param("query")Query query,@Param("params")Map<String,Object> params);
	
	/**
	 * 商家中心首尔直发二期：主退回单详情列表查询
	 * @param backOrderDetailVo
	 * @return
	 */
	List<BackOrderDetailVo> queryBackOrderDetailList(@Param("backOrderDetailVo")BackOrderDetailVo backOrderDetailVo,@Param("query")Query query);
	
	/**
	 * 商家中心首尔直发二期：主退回单详情总数查询
	 * @param backOrderVo
	 * @param query
	 * @param params
	 * @return
	 */
	int queryBackOrderDetailCount (@Param("backOrderDetailVo")BackOrderDetailVo backOrderDetailVo,@Param("query")Query query);
	
	/**
	 * 根据退回单ID查询退回单信息
	 * @param mainId
	 * @return
	 */
	BackOrderVo queryBackOrderInfoById(@Param("mainId")String mainId);
	
	/**
	 * 根据退回单明细ID 查询详情信息
	 * @param detailId
	 * @return
	 */
	BackOrderDetailVo queryBackOrderDetailInfoById(@Param("detailId")String detailId);
	
	/**
	 * 新增确认收货操作明细记录
	 * @param params
	 */
	void insertReceiveDetail(@Param("receiveDetail")Map<String,Object> params);
	
	/**
	 * 修改退回单详情已退回商品数量
	 * @param params
	 */
	void updateBackOrderDetail(@Param("params")Map<String,Object> params);
	
	/**
	 * 修改退回单已退回商品数量及收货状态
	 * @param params
	 */
	void updateBackOrder(@Param("params")Map<String,Object> params);
	
	/**
	 * 根据退回单ID查询所有未确认收货的详情列表
	 * @param mainId
	 * @return
	 */
	List<BackOrderDetailVo> queryBackOrderDetailListByMainId(@Param("mainId")String mainId);
	
}
