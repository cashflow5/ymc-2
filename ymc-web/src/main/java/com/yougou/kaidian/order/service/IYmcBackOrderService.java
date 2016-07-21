package com.yougou.kaidian.order.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.merchant.api.supplier.vo.BackOrderDetailVo;
import com.yougou.merchant.api.supplier.vo.BackOrderVo;

/**
 * 商家中心首尔直发二期：退回单确认收货
 * @author zhang.f1
 *
 */
public interface IYmcBackOrderService {
	
	/**
	 * 商家中心首尔直发二期：确认收货，主退回单列表查询
	 * @param backOrderVo
	 * @return
	 */
	PageFinder<BackOrderVo> queryBackOrderList(BackOrderVo backOrderVo,Query query,Map<String,Object> params);
	
	/**
	 * 商家中心首尔直发二期：主退回单详情列表查询
	 * @param backOrderDetailVo
	 * @return
	 */
	PageFinder<BackOrderDetailVo> queryBackOrderDetailList(BackOrderDetailVo backOrderDetailVo,Query query);
	
	/**
	 * 根据退回单ID查询退回单信息
	 * @param mainId
	 * @return
	 */
	BackOrderVo queryBackOrderInfoById(String mainId);
	
	/**
	 * 单个确认收货
	 * @param params
	 */
	void handleReceive(Map<String,Object> params)throws Exception;
	
	/**
	 * 批量确认收货
	 * @param params
	 * @throws Exception
	 */
	void batchHandleReceive(Map<String,Object> params)throws Exception;
	
	/**
	 * 全部确认收货
	 * @param params
	 * @throws Exception
	 */
	void allHandleReceive(Map<String,Object> params)throws Exception;
}
