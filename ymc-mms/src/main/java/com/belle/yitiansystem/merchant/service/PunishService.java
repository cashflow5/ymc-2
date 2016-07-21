package com.belle.yitiansystem.merchant.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.asm.model.vo.SaleTraceVo;
import com.belle.yitiansystem.merchant.model.pojo.PunishOrder;
import com.belle.yitiansystem.merchant.model.pojo.PunishOrderAgent;
import com.belle.yitiansystem.merchant.model.pojo.PunishRule;
import com.belle.yitiansystem.merchant.model.pojo.StockPunishRuleDetail;
import com.belle.yitiansystem.merchant.model.vo.PunishOrderVo;
import com.yougou.ordercenter.vo.order.MerchantMessageVo;

/**
 * 商家处罚管理
 * @author he.wc
 *
 */
public interface PunishService {
	
	/**
	 * 通过商家编号得到商家处罚规则
	 * @param merchantsCode
	 * @return
	 */
	public PunishRule getPunishRuleByMerchantsCode(String merchantsCode);
	
	/**
	 * 保存商家处罚规则
	 * @param punishRule
	 */
	public void saveOrUpdatePunishRule(PunishRule punishRule);
	
	/**
	 * 保存处罚订单
	 * @param punishOrder
	 */
	public void savePunishOrder(PunishOrder punishOrder);
	
	/**
	 * 审核或取消处罚订单
	 * @param id
	 * @param vaildStatus
	 * @param validPerson
	 */
	public void vaildPunishOrder(String id, String validStatus, String validPerson) throws Exception;
	
	/**
	 * 查询处罚订单列表
	 * @param punishOrderVo
	 * @param query
	 * @return
	 */
	public PageFinder<Map<String,Object>> queryPunishOrderList(PunishOrderVo punishOrderVo, Query query);
	
	/**
	 * 通过ids查询违规订单明细
	 * @param ids
	 * @return
	 */
	public List<Map<String,Object>> queryPunishOrderList(List<String> ids);
	
	/**
	 * 通过订单号获处罚订单信息
	 * 一个订单号可能存在多个缺货商品
	 * 单纯靠订单号查询有可能不是唯一的
	 * @param id
	 * @return
	 */
	public List<PunishOrder> getPunishOrderByOrderNo(String orderNo);
	/**
	 * getPunishOrderByPunishId:通过处罚Id获处罚订单信息
	 * @author li.n1 
	 * @param punishId
	 * @return 
	 * @since JDK 1.6
	 */
	public PunishOrder getPunishOrderByPunishId(String punishId);
	
	/**
	 * 更新处罚订单价格
	 * @param id
	 * @param price
	 */
	public void updatePunishOrderPrice(String id,Double price);
	
	/**
	 * 更新处罚订单信息
	 * @param punishOrder
	 */
	public void updatePunishOrder(PunishOrder punishOrder);
	
	/**
	 * 生成处罚订单信息
	 */
	public void saveSyncPunishOrder(PunishOrderAgent orderAgent) throws Exception ;
	
	/**
	 *  向商家发提醒发货邮件
	 */
	public void sendPunishOrderEmail();
	
	/**
	 * 保存订单MQ信息
	 * @param merchantMessageVo
	 */
	public void saveOrderMessage(MerchantMessageVo merchantMessageVo) throws Exception;
	
	
	public List<PunishOrderAgent> getPunishOrderAgentList();
	
	/**
     * 给商家发送工单处理MQ信息
     * @param saleTraceVo
     */
    public void sendOrderSaleTraceEmail(SaleTraceVo saleTraceVo) throws Exception;

	/** 
	 * getStockPunishRuleDetail:根据处罚规则Id获得缺货处罚信息
	 * @author li.n1 
	 * @param id
	 * @return 
	 * @since JDK 1.6 
	 */  
	public List<StockPunishRuleDetail> getStockPunishRuleDetail(String id);

	/** 
	 * savePunishRuleDetail:新增缺货处罚明细
	 * @author li.n1 
	 * @param ruleId 
	 * @param detailList
	 * @return 
	 * @since JDK 1.6 
	 */  
	public boolean savePunishRuleDetail(String ruleId, List<StockPunishRuleDetail> detailList);

	/** 
	 * queryPunishOrderList:导出需要，根据查询条件查询列表
	 * @author li.n1 
	 * @param punishOrderVo
	 * @return 
	 * @since JDK 1.6 
	 */  
	public List<Map<String, Object>> queryPunishOrderList(
			PunishOrderVo punishOrderVo);

	/** 
	 * getOrderNo:根据punishId得到订单号
	 * @author li.n1 
	 * @param id
	 * @return 
	 * @since JDK 1.6 
	 */  
	public Map<String, Object> getOrderNo(String id);

	/** 
	 * countAmount:结算总金额 
	 * @author li.n1 
	 * @param startDate
	 * @param endDate
	 * @param merchantName 
	 * @since JDK 1.6 
	 */  
	public Map<String,Object> countAmount(String startDate, String endDate,
			String merchantName,String punishType);

	/** 
	 * getPunishOrderByOrderNo:根据订单号查询罚款订单信息
	 * @author li.n1 
	 * @param subOrderNo
	 * @param b 
	 * @since JDK 1.6 
	 */  
	public List<PunishOrder> getPunishOrderByOrderNo(String subOrderNo, boolean flag);

	/** 
	 * updatePunishOrderStatus:修改状态
	 * @author li.n1 
	 * @param settleStart
	 * @param settleEnd
	 * @param supplierCode
	 * @param punishType 违规类型
	 * @param registNum 登记单号
	 * @since JDK 1.6 
	 */  
	public boolean updatePunishOrderStatus(String settleStart, String settleEnd,
			String supplierCode, String punishType, String registNum) throws Exception;

	/** 
	 * getPunishStockList:得到待审核缺货商品分页列表
	 * @author li.n1 
	 * @param punishOrderVo
	 * @param query
	 * @return 
	 * @since JDK 1.6 
	 */  
	public PageFinder<Map<String, Object>> getPunishStockList(
			PunishOrderVo punishOrderVo, Query query);
	/**
	 * getPunishOutOfStockList:导出所有未审核的缺货商品 
	 * @author li.n1 
	 * @param punishOrderVo
	 * @return 
	 * @since JDK 1.6
	 */
	public List<Map<String,Object>> getPunishOutOfStockList(PunishOrderVo punishOrderVo);
	
	/**
	 * getPunishOutOfStockList:导出所有已审核的缺货商品 
	 * @author li.n1 
	 * @param punishOrderVo
	 * @return 
	 * @since JDK 1.6
	 */
	public List<Map<String,Object>> getValidPunishOutOfStockList(PunishOrderVo punishOrderVo);

	/** 
	 * getPunishValidStockList:得到已审核的缺货商品分页列表
	 * @author li.n1 
	 * @param punishOrderVo
	 * @param query
	 * @return 
	 * @since JDK 1.6 
	 */  
	public PageFinder<Map<String, Object>> getPunishValidStockList(
			PunishOrderVo punishOrderVo, Query query);

	/** 
	 * vaildStockPunishOrder:审核未审核的缺货商品，流程是先判断是否存在，不存在则插入数据库表，再设置已审核，存在就设置已审核
	 * @author li.n1 
	 * @param detailId
	 * @param validStatus
	 * @param validPerson 
	 * @param punishId 
	 * @param merchantCode
	 * @since JDK 1.6 
	 */  
	public String vaildStockPunishOrder(String detailId, String validStatus,
			String validPerson, String punishId,String merchantCode);

	/** 
	 * getOutStockRate:求缺货率
	 * @author li.n1 
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param merchantCode
	 * @return 
	 * @since JDK 1.6 
	 */  
	public double getOutStockRate(Date orderTimeStart, Date orderTimeEnd,
			String merchantCode);

	/** 
	 * getPunishValidAndNoSubmitStockList:缺货计算罚款
	 * @author li.n1 
	 * @param vo
	 * @param que
	 * @return 
	 * @since JDK 1.6 
	 */  
	public Map<String, Object> getPunishValidAndNoSubmitStockList(
			PunishOrderVo vo, double que);

	/** 
	 * cancelPunishOrder:修改违规订单的状态，使之未被提交到违规结算列表 
	 * @author li.n1 
	 * @param registNum 登记单号
	 * @return 
	 * @since JDK 1.6 
	 */  
	boolean cancelPunishOrder(String registNum);
	
	/**
	 * getPunishOutOfStockByOrderNo:到冗余表查询是否存在缺货商品，冗余表的范围更大，包括了招商库的缺货商品 
	 * @author li.n1 
	 * @param orderNo
	 * @return 
	 * @since JDK 1.6
	 */
	public List<PunishOrder> getPunishOutOfStockByOrderNo(String orderNo);
	
	/**
	 * 查询违规订单表中，违规类型为超时，无发货时间，发货状态订单，将这些订单再次主动调用订单接口确认发货状态发货时间，
	 * 如果确认后订单没有违规，将此条违规记录剔除，如果订单确实违规了，更新发货时间，发货状态，避免超时时间计算错误
	 * @author zhang.f
	 * @return boolean
	 * @since JDK 1.6
	 */
	public boolean cleanPunishOrder();
}
