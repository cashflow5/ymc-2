package com.yougou.kaidian.order.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.order.model.OrderPunish;
import com.yougou.kaidian.order.model.OrderPunishCommodity;
import com.yougou.kaidian.order.model.OrderPunishRule;
import com.yougou.ordercenter.vo.merchant.input.QueryOutOfStockInputDto;
import com.yougou.ordercenter.vo.merchant.output.QueryOutOfStockOutputDto;

/**
 * 违规订单Service
 * 
 * @author he.wc
 * 
 */
public interface IOrderPunishService {
	
	/**
	 * 违规订单查询
	 * @param orderPunish
	 * @param query
	 * @return
	 */
	public PageFinder<OrderPunish> queryOrderPunishList(OrderPunish orderPunish, Query query);
	
	/**
	 * 违规订单查询
	 * @param orderPunish
	 * @param query
	 * @return
	 */
	public List<OrderPunish> queryOrderPunishList(String orderNo);
	
	/**
	 * 违规订单查询
	 * @param orderNos
	 * @return
	 */
	public List<OrderPunish> queryOrderPunishList(String[] orderNos);
	
	/**
	 * 保存违规订单信息
	 * @param orderPunish
	 */
	public void saveOrderPunish(OrderPunish orderPunish);
	
	/**
	 * 得到该商家违规订单规则
	 * @param merchantCode
	 * @return
	 */
	public OrderPunishRule getOrderPunishRule(String merchantCode);
	
	/**
	 * 更新违规订单信息
	 * @param id
	 * @param punishType
	 * @param updateTime
	 */
	public void updateOrderPunsih(String id, String punishType, Timestamp updateTime,Double punishPrice);
	
	/**
	 * 查询缺货订单商品
	 * @param paraMap
	 * @return
	 */
	public PageFinder<OrderPunishCommodity> queryOrderPunishCommodityList(Map<String,Object> paraMap, Query query);

	/** 
	 * getPunishValidStockList:查询缺货商品
	 * @author li.n1 
	 * @param queryOutOfStockInputDto
	 * @param query
	 * @return 
	 * @since JDK 1.6 
	 */  
	public com.yougou.ordercenter.common.PageFinder<QueryOutOfStockOutputDto> getPunishValidStockList(
			QueryOutOfStockInputDto queryOutOfStockInputDto, Query query);

}
