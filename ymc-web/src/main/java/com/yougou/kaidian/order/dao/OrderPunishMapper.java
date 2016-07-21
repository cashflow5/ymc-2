package com.yougou.kaidian.order.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.order.model.OrderPunish;
import com.yougou.kaidian.order.model.OrderPunishCommodity;
import com.yougou.kaidian.order.model.OrderPunishRule;
import com.yougou.ordercenter.vo.merchant.input.QueryOutOfStockInputDto;
import com.yougou.ordercenter.vo.merchant.output.QueryOutOfStockOutputDto;

/**
 * 违规订单
 * 
 * @author he.wc
 * 
 */
public interface OrderPunishMapper {

	/**
	 * 违规订单列表
	 * 
	 * @param orderPunish
	 * @param rowBounds
	 * @return
	 */
	public List<OrderPunish> queryOrderPunishList(OrderPunish orderPunish, RowBounds rowBounds);
	
	/**
	 * 根据订单号查询违规订单
	 * @param orderNo
	 * @return
	 */
	public List<OrderPunish> queryOrderPunishByOrderNo(@Param("orderNo")String orderNo);
	
	/**
	 * 根据订单号列表查询违规订单
	 * @param orderNo
	 * @return
	 */
	public List<OrderPunish> queryOrderPunishByOrderNos(@Param("orderNos")String[] orderNos);

	/**
	 * 违规订单数量
	 * 
	 * @param salesVo
	 * @return
	 */
	public int queryOrderPunishCount(OrderPunish orderPunish);
	

	/**
	 * 保存违规订单信息
	 * 
	 * @param orderPunish
	 */
	public void saveOrderPunish(OrderPunish orderPunish);
	
	/**
	 * 更新违规订单信息
	 * @param id
	 * @param punishType
	 * @param updateTime
	 */
	public void updateOrderPunish(@Param("id") String id, @Param("punishType") String punishType, @Param("updateTime") Timestamp updateTime, @Param("punishPrice") Double punishPrice);

	/**
	 * 得到该分销商的处罚规则列表
	 * 
	 * @param merchantCode
	 * @return
	 */
	public List<OrderPunishRule> queryOrderPunishRuleList(@Param("merchantCode") String merchantCode);
	
	/**
	 * 查询缺货订单商品
	 * @param paraMap
	 * @return
	 */
	public List<OrderPunishCommodity> queryOrderPunishCommodityList(Map<String,Object> paraMap, RowBounds rowBounds);
	
	public int queryOrderPunishCommodityListCount(Map<String,Object> paraMap);

	/** 
	 * getPunishValidStockList:查询缺货商品
	 * @author li.n1 
	 * @param queryOutOfStockInputDto
	 * @param rowBounds
	 * @return 
	 * @since JDK 1.6 
	 */  
	public List<QueryOutOfStockOutputDto> getPunishValidStockList(
			QueryOutOfStockInputDto queryOutOfStockInputDto, RowBounds rowBounds);

	/** 
	 * getPunishValidStockListCount:查询缺货商品count 
	 * @author li.n1 
	 * @param queryOutOfStockInputDto
	 * @return 
	 * @since JDK 1.6 
	 */  
	public int getPunishValidStockListCount(
			QueryOutOfStockInputDto queryOutOfStockInputDto);
}
