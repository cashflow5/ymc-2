package com.yougou.kaidian.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.order.dao.YmcBackOrderMapper;
import com.yougou.kaidian.order.service.IYmcBackOrderService;
import com.yougou.merchant.api.supplier.vo.BackOrderDetailVo;
import com.yougou.merchant.api.supplier.vo.BackOrderVo;

/**
 * 商家中心首尔直发二期：退回单确认收货 服务层实现
 * @author zhang.f1
 *
 */
@Service
public class YmcBackOrderServiceImpl implements IYmcBackOrderService {
	
	@Resource
	private YmcBackOrderMapper backOrderMapper;
	
	private static Logger logger = LoggerFactory.getLogger(YmcBackOrderServiceImpl.class);

	@Override
	public PageFinder<BackOrderVo> queryBackOrderList(BackOrderVo backOrderVo,Query query,Map<String,Object> params) {
		// TODO Auto-generated method stub
		int rowCount = backOrderMapper.queryBackOrderCount(backOrderVo, query, params);
		PageFinder<BackOrderVo> pageFinder = null;
		if(rowCount > 0){
			List<BackOrderVo> data = backOrderMapper.queryBackOrderList(backOrderVo, query, params);
			pageFinder = new PageFinder<BackOrderVo>(query.getPage(), query.getPageSize(), rowCount, data);
		}
		return pageFinder;
	}

	@Override
	public PageFinder<BackOrderDetailVo> queryBackOrderDetailList(
			BackOrderDetailVo backOrderDetailVo,Query query) {
		int rowCount = backOrderMapper.queryBackOrderDetailCount(backOrderDetailVo, query);
		PageFinder<BackOrderDetailVo> pageFinder = null;
		if(rowCount > 0){
			List<BackOrderDetailVo> data = backOrderMapper.queryBackOrderDetailList(backOrderDetailVo, query);
			pageFinder = new PageFinder<BackOrderDetailVo>(query.getPage(), query.getPageSize(), rowCount, data);
		}
		return pageFinder;
	}

	@Override
	public BackOrderVo queryBackOrderInfoById(String mainId) {
		// TODO Auto-generated method stub
		return backOrderMapper.queryBackOrderInfoById(mainId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void handleReceive(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		logger.info("商家编码：{}，首尔直发二期单个确认收货handleReceive,params={}",YmcThreadLocalHolder.getMerchantCode(),params);
		String detailId =(String) params.get("detailId");
		BackOrderDetailVo detailVo = backOrderMapper.queryBackOrderDetailInfoById(detailId);
		params.put("orderSubNo", detailVo.getOrderSubNo());
		params.put("commodityName", detailVo.getCommodityName());
		params.put("productNo", detailVo.getProductNo());
		params.put("supplierCode", detailVo.getSupplierCode());
		params.put("merchantCode", YmcThreadLocalHolder.getMerchantCode());
		params.put("merchantName", YmcThreadLocalHolder.getMerchantName());
		backOrderMapper.insertReceiveDetail(params);
		backOrderMapper.updateBackOrderDetail(params);
		backOrderMapper.updateBackOrder(params);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void batchHandleReceive(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		logger.info("商家编码：{}，首尔直发二期批量确认收货batchHandleReceive,params={}",YmcThreadLocalHolder.getMerchantCode(), params);
		String backCode = (String) params.get("backCode");
		Integer noBackTotalCount = (Integer) params.get("noBackTotalCount");
		String receiveOperator = (String) params.get("receiveOperator");
		List<String> detailIdList = (List<String>) params.get("detailIdList");
		
		if(detailIdList != null && detailIdList.size() >0){
			int receiveTotalCount = 0;
			Map<String,Object> receiveParam = null ;
			for(String detailId : detailIdList){
				BackOrderDetailVo detailVo = backOrderMapper.queryBackOrderDetailInfoById(detailId);
				//确认收货数量=计划退回数量-已退回数量
				int receiveCount = detailVo.getPlanBackCount()-detailVo.getAlreadyBackCount();
				//总的已退回数量累加
				receiveTotalCount = receiveTotalCount + receiveCount;
				receiveParam = new HashMap<String,Object>();
				receiveParam.put("detailId", detailId);
				receiveParam.put("backCode", backCode);
				receiveParam.put("orderSubNo", detailVo.getOrderSubNo());
				receiveParam.put("commodityName", detailVo.getCommodityName());
				receiveParam.put("productNo", detailVo.getProductNo());
				receiveParam.put("supplierCode", detailVo.getSupplierCode());
				receiveParam.put("merchantCode", YmcThreadLocalHolder.getMerchantCode());
				receiveParam.put("merchantName", YmcThreadLocalHolder.getMerchantName());
				receiveParam.put("receiveCount", receiveCount);
				receiveParam.put("receiveOperator", receiveOperator);
				//新增确认收货记录
				backOrderMapper.insertReceiveDetail(receiveParam);
				//修改详情确认收货数量
				backOrderMapper.updateBackOrderDetail(receiveParam);
			}
			//判断退回单收货状态
			Integer receiveStatus = null;
			//总未退回数量=确认收货数量，收货状态为1：已确认收货
			if(noBackTotalCount == receiveTotalCount && receiveTotalCount !=0){
				receiveStatus = 1;
			//总未退回数量>确认收货数量，收货状态为2：部分确认收货	
			}else if(noBackTotalCount > receiveTotalCount && receiveTotalCount !=0){
				receiveStatus = 2;
			}
			params.put("receiveCount", receiveTotalCount);
			params.put("receiveStatus", receiveStatus);
			//修改退回单已退回数量及收货状态
			backOrderMapper.updateBackOrder(params);
		}else{
			logger.error("商家编码：{}，首尔直发二期批量确认收货batchHandleReceive 退回单详情ID为空",YmcThreadLocalHolder.getMerchantCode());
		}
	}

	@Override
	public void allHandleReceive(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		logger.info("商家编码：{}，首尔直发二期全部确认收货batchHandleReceive,params={}",YmcThreadLocalHolder.getMerchantCode(), params);
		String mainId = (String) params.get("mainId");
		String backCode = (String) params.get("backCode");
		Integer noBackTotalCount = (Integer) params.get("noBackTotalCount");
		String receiveOperator = (String) params.get("receiveOperator");
		List<BackOrderDetailVo> detailVoList = backOrderMapper.queryBackOrderDetailListByMainId(mainId);
		if(detailVoList != null && detailVoList.size() > 0){
			Map<String,Object> receiveParam = null ;
			int receiveTotalCount = 0;
			for(BackOrderDetailVo detailVo : detailVoList){
				//确认收货数量=计划退回数量-已退回数量
				int receiveCount = detailVo.getPlanBackCount()-detailVo.getAlreadyBackCount();		
				//总的已退回数量累加
				receiveTotalCount = receiveTotalCount + receiveCount;
				receiveParam = new HashMap<String,Object>();
				receiveParam.put("detailId", detailVo.getId());
				receiveParam.put("backCode", backCode);
				receiveParam.put("orderSubNo", detailVo.getOrderSubNo());
				receiveParam.put("commodityName", detailVo.getCommodityName());
				receiveParam.put("productNo", detailVo.getProductNo());
				receiveParam.put("supplierCode", detailVo.getSupplierCode());
				receiveParam.put("merchantCode", YmcThreadLocalHolder.getMerchantCode());
				receiveParam.put("merchantName", YmcThreadLocalHolder.getMerchantName());				
				receiveParam.put("receiveCount", receiveCount);
				receiveParam.put("receiveOperator", receiveOperator);
				//新增确认收货记录
				backOrderMapper.insertReceiveDetail(receiveParam);
				//修改详情确认收货数量
				backOrderMapper.updateBackOrderDetail(receiveParam);	
				
			}
			//判断退回单收货状态
			Integer receiveStatus = null;
			//总未退回数量=确认收货数量，收货状态为1：已确认收货
			if(noBackTotalCount == receiveTotalCount && receiveTotalCount !=0){
				receiveStatus = 1;
				//确认收货商品数=剩余所有未收货商品数
				params.put("receiveCount", noBackTotalCount);
				// 退回单收货状态改为1：已确认收货
				params.put("receiveStatus", receiveStatus);
				//修改退回单已退回数量及收货状态
				backOrderMapper.updateBackOrder(params);
			//总未退回数量>确认收货数量
			}else{
				logger.error("商家编码：{}，首尔直发二期全部确认收货allHandleReceive，详情表剩余确认收回数量与退回单主表待确认收货数量不一致，" +
						"详情表剩余数量：{}，主表待确认收货数量：{}",YmcThreadLocalHolder.getMerchantCode(), receiveTotalCount,noBackTotalCount);
			}
			
		}else{
			logger.error("商家编码：{}，首尔直发二期全部确认收货allHandleReceive 退回单详情为空，退回单ID={}",YmcThreadLocalHolder.getMerchantCode(),mainId);
		}
	}
	
}
