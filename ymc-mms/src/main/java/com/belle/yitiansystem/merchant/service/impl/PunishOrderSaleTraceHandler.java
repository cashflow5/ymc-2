package com.belle.yitiansystem.merchant.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.spring.DataSourceSwitcher;
import com.belle.yitiansystem.asm.model.vo.SaleTraceVo;
import com.belle.yitiansystem.merchant.service.PunishService;

/**
 * 商家工单MQ消息处理
 * 
 * @author mei.jf
 * 
 */
@Service(value="punishOrderSaleTraceHandler")
public class PunishOrderSaleTraceHandler {

	@Resource
	private PunishService punishService;

	private static final Logger logger = LoggerFactory.getLogger(PunishOrderSaleTraceHandler.class);


	/**
	 * 商家工单MQ消息处理
	 * 
	 * @param merchantMessageVo
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void handleOrderMessage(SaleTraceVo saleTraceVo) {
		logger.info("商家工单MQ接收开始");
		logger.debug("商家工单MQ接收,MQ信息[{}]", saleTraceVo);
		try {
			DataSourceSwitcher.setMaster();
			punishService.sendOrderSaleTraceEmail(saleTraceVo);
		} catch (Exception e) {
			logger.error("商家工单MQ接收处理失败[{}]，MQ信息[{}]", e.getMessage() + e.getStackTrace(), saleTraceVo);
		}
		logger.info("商家工单MQ接收结束");
	}

	/**
	 * 商家工单MQ消息处理-批量
	 * 
	 * @param merchantMessageVoList
	 */
	public void handleOrderMessage(SaleTraceVo[] saleTraceVoList) {
		logger.info("商家工单[批量]MQ接收处理开始");
		try {
			if (saleTraceVoList == null ) {
				logger.error("商家工单[批量]MQ接收处理失败[{}]，MQ信息为NULL");
			}
			for (SaleTraceVo saleTraceVo : saleTraceVoList) {
				DataSourceSwitcher.setMaster();
				logger.debug("商家工单[批量]MQ接收处理开始,MQ信息[{}]", saleTraceVo);
				punishService.sendOrderSaleTraceEmail(saleTraceVo);
			}
		} catch (Exception e) {
			logger.error("商家工单[批量]MQ接收处理失败[{}]，MQ信息[{}]", e.getMessage() + e.getStackTrace(), saleTraceVoList);
		}
		logger.info("商家工单[批量]MQ接收处理结束");
	}

}