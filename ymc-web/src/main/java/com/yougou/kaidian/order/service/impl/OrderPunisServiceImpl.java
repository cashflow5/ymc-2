package com.yougou.kaidian.order.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.order.dao.OrderPunishMapper;
import com.yougou.kaidian.order.model.OrderPunish;
import com.yougou.kaidian.order.model.OrderPunishCommodity;
import com.yougou.kaidian.order.model.OrderPunishRule;
import com.yougou.kaidian.order.service.IOrderPunishService;
import com.yougou.ordercenter.vo.merchant.input.QueryOutOfStockInputDto;
import com.yougou.ordercenter.vo.merchant.output.QueryOutOfStockOutputDto;

/**
 * 违规订单
 * 
 * @author he.wc
 * 
 */
@Service
public class OrderPunisServiceImpl implements IOrderPunishService {

	@Resource
	private OrderPunishMapper orderPunishMapper;

	/**
	 * 违规订单查询
	 */
	public PageFinder<OrderPunish> queryOrderPunishList(OrderPunish orderPunish, Query query) {
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<OrderPunish> orderPunishorList = orderPunishMapper.queryOrderPunishList(orderPunish, rowBounds);
		int count = orderPunishMapper.queryOrderPunishCount(orderPunish);
		PageFinder<OrderPunish> pageFinder = new PageFinder<OrderPunish>(query.getPage(), query.getPageSize(), count, orderPunishorList);
		return pageFinder;
	}
	
	/**
	 * 根据订单号查询违规订单
	 */
	public List<OrderPunish> queryOrderPunishList(String orderNo) {
		return orderPunishMapper.queryOrderPunishByOrderNo(orderNo);
	}
	
	/**
	 *  根据订单号列表查询违规订单
	 */
	public List<OrderPunish> queryOrderPunishList(String[] orderNos) {
		return orderPunishMapper.queryOrderPunishByOrderNos(orderNos);
	}

	/**
	 * 保存违规订单信息
	 */
	@Transactional
	public void saveOrderPunish(OrderPunish orderPunish) {
		orderPunishMapper.saveOrderPunish(orderPunish);
	}

	/**
	 * 得到该商家处罚规则
	 */
	public OrderPunishRule getOrderPunishRule(String merchantCode) {
		List<OrderPunishRule> list = orderPunishMapper.queryOrderPunishRuleList(merchantCode);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 得到该分销商的处罚规则列表
	 */
	@Transactional
	public void updateOrderPunsih(String id, String punishType, Timestamp updateTime,Double punishPrice) {
		orderPunishMapper.updateOrderPunish(id, punishType, updateTime,punishPrice);
	}

	@Override
	public PageFinder<OrderPunishCommodity> queryOrderPunishCommodityList(
			Map<String, Object> paraMap, Query query) {
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<OrderPunishCommodity> orderPunishorList = orderPunishMapper.queryOrderPunishCommodityList(paraMap, rowBounds);
		int count = orderPunishMapper.queryOrderPunishCommodityListCount(paraMap);
		PageFinder<OrderPunishCommodity> pageFinder = new PageFinder<OrderPunishCommodity>(query.getPage(), query.getPageSize(), count, orderPunishorList);
		return pageFinder;
	}
	
	/** 
	 * @see com.yougou.kaidian.order.service.IOrderPunishService#getPunishValidStockList(com.yougou.ordercenter.vo.merchant.input.QueryOutOfStockInputDto, com.yougou.kaidian.common.base.Query) 
	 */
	@Override
	public com.yougou.ordercenter.common.PageFinder<QueryOutOfStockOutputDto> getPunishValidStockList(
			QueryOutOfStockInputDto queryOutOfStockInputDto, Query query) {
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<QueryOutOfStockOutputDto> orderPunishorList = orderPunishMapper.getPunishValidStockList(queryOutOfStockInputDto, rowBounds);
		int count = orderPunishMapper.getPunishValidStockListCount(queryOutOfStockInputDto);
		com.yougou.ordercenter.common.PageFinder<QueryOutOfStockOutputDto> pageFinder = 
				new com.yougou.ordercenter.common.PageFinder<QueryOutOfStockOutputDto>
		(query.getPage(), query.getPageSize(), count, orderPunishorList);
		return pageFinder;
	}

}
