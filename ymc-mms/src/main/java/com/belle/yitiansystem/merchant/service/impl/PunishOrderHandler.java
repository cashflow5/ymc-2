package com.belle.yitiansystem.merchant.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.belle.finance.merchants.deliveryfine.model.vo.MerchantDeliveryFineReturnVo;
import com.belle.infrastructure.spring.DataSourceSwitcher;
import com.belle.yitiansystem.merchant.constant.PunishConstant;
import com.belle.yitiansystem.merchant.dao.PunishOrderAgentDao;
import com.belle.yitiansystem.merchant.dao.PunishOrderDao;
import com.belle.yitiansystem.merchant.model.pojo.PunishOrder;
import com.belle.yitiansystem.merchant.service.PunishService;
import com.yougou.ordercenter.vo.order.MerchantMessageVo;

/**
 * MQ消息处理
 * 
 * @author he.wc
 * 
 */
@Service
public class PunishOrderHandler {

	@Resource
	private PunishService punishService;

	@Resource
	private PunishOrderDao punishOrderDao;

	private static final Logger logger = LoggerFactory.getLogger(PunishOrderHandler.class);

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void handleMessage(MerchantDeliveryFineReturnVo fineReturnVo) {
		try {
			logger.info("接收财务MQ，MerchantDeliveryFineReturnVo = " + fineReturnVo);
			Integer status = fineReturnVo.getDeductStatus();
			String orderNo = fineReturnVo.getOrderNo();
			SimpleDateFormat sdf = new SimpleDateFormat(PunishConstant.DATE_FORMAT_DATE);
			List<PunishOrder> punishOrderList = punishService.getPunishOrderByOrderNo(orderNo);
			if (punishOrderList == null || (punishOrderList!=null && punishOrderList.size()==0)) {
				return;
			}
			PunishOrder punishOrder = null;
			if(punishOrderList.size()==0){
				punishOrder =  punishOrderList.get(0);
			}
			// 处罚订单结算
			if (status != null && status == 1) {
				String settleCycle = "";
				String balanceBillNumber = fineReturnVo.getBalanceBillNumber();
				if (fineReturnVo.getBalanceStartDate() != null && fineReturnVo.getBalanceEndDate() != null) {
					String balanceStartDate = sdf.format(fineReturnVo.getBalanceStartDate());
					String balanceEndDate = sdf.format(fineReturnVo.getBalanceEndDate());
					settleCycle = balanceStartDate + "到" + balanceEndDate;
				}
				punishOrder.setSettleOrderNo(balanceBillNumber);
				punishOrder.setIsSettle(PunishConstant.IS_SETTLE_YES);
				punishOrder.setSettleCycle(settleCycle);
			} else {// 处罚订单打回
				punishOrder.setPunishOrderStatus(PunishConstant.ORDER_STATUS_NORMAL);
			}
			punishOrderDao.getTemplate().update(punishOrderList);
			logger.info("接收财务MQ，更新处罚订单信息 punishOrder = " + punishOrderList);
		} catch (Exception e) {
			logger.error("财务MQ接收处理失败", e);
		}

	}

	/**
	 * 订单MQ消息处理
	 * 
	 * @param merchantMessageVo
	 */
	public void handleOrderMessage(MerchantMessageVo merchantMessageVo) {
		logger.info("招商订单MQ接收开始");
		logger.debug("招商订单MQ接收,MQ信息[{}]", merchantMessageVo);
		try {
			DataSourceSwitcher.setMaster();
			punishService.saveOrderMessage(merchantMessageVo);
		} catch (Exception e) {
			logger.error("招商订单MQ接收处理失败[{}]，MQ信息[{}]", e.getMessage() + e.getStackTrace(), merchantMessageVo);
		}

	}

	/**
	 * 订单MQ消息处理-批量
	 * 
	 * @param merchantMessageVoList
	 */
	public void handleOrderMessage(MerchantMessageVo[] merchantMessageVoList) {
		logger.info("招商订单[批量]MQ接收处理开始");
		try {
			if (merchantMessageVoList == null ) {
				logger.error("招商订单[批量]MQ接收处理失败[{}]，MQ信息为NULL");
			}
			for (MerchantMessageVo merchantMessageVo : merchantMessageVoList) {
				DataSourceSwitcher.setMaster();
				logger.debug("招商订单[批量]MQ接收处理开始,MQ信息[{}]", merchantMessageVo);
				punishService.saveOrderMessage(merchantMessageVo);
			}
		} catch (Exception e) {
			logger.error("招商订单[批量]MQ接收处理失败[{}]，MQ信息[{}]", e.getMessage() + e.getStackTrace(), merchantMessageVoList);
		}
	}
	
	public void handleOrderMessage(Object obj){
		System.out.println("handleOrderMessage:"+obj);
	}

	public void handleMessage(String reslut) {

	}

}