/**
 * 
 */
package com.yougou.kaidian.order.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.DateUtil;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.order.constant.OrderConstant;
import com.yougou.kaidian.order.dao.MerchantOrderMapper;
import com.yougou.kaidian.order.dao.OrderPunishMapper;
import com.yougou.kaidian.order.model.OrderPunish;
import com.yougou.kaidian.order.model.OrderSubExpand;
import com.yougou.kaidian.order.model.OtherOutStore;
import com.yougou.kaidian.order.service.IOrderService;
import com.yougou.kaidian.order.util.ExportHelper;
import com.yougou.kaidian.order.util.OrderUtil;
import com.yougou.kaidian.stock.service.IStockService;
import com.yougou.kaidian.stock.service.IWarehouseService;
import com.yougou.ordercenter.api.order.IOrderForMerchantApi;
import com.yougou.ordercenter.common.OrderStatusEnum;
import com.yougou.ordercenter.model.order.OrderConsigneeInfo;
import com.yougou.ordercenter.model.order.OrderDetail4sub;
import com.yougou.ordercenter.model.order.OrderSub;
import com.yougou.ordercenter.vo.merchant.MerchantDailyStatisticsVo;
import com.yougou.ordercenter.vo.order.OrderPackVo;
import com.yougou.wms.wpi.inventory.service.IInventoryDomainService;
import com.yougou.wms.wpi.orderoutstore.service.IOrderOutStoreDomainService;
import com.yougou.wms.wpi.warehouse.service.IWarehouseCacheService;

/**
 * 
 * 订单相关逻辑处理(实现类)
 * 
 * @author huang.tao
 *
 */
@Service
public class OrderServiceImpl implements IOrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Resource
	private OrderPunishMapper orderPunishMapper;
	@Resource
	private IOrderForMerchantApi orderForMerchantApi;
	@Resource
	private IInventoryDomainService inventoryDomainService;
	@Resource
	private IWarehouseService warehouseService;
	@Resource
	private IWarehouseCacheService warehouseCacheService;
	@Resource
	private IOrderOutStoreDomainService orderOutStoreDomainService;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private IStockService stockService;
    @Resource
    private MerchantOrderMapper merchantOrderMapper;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> queryShopRemindList(String merchantCode)
			throws Exception {

		Map<String, Integer> tips = null;
		tips = (Map<String, Integer>) redisTemplate.opsForValue().get(
					CacheConstant.C_ORDER_SHOPREMIND_KEY + ":" +merchantCode);

		if (MapUtils.isNotEmpty(tips))
			return tips;

		tips = new HashMap<String, Integer>();

		// 从违规订单中获取
		tips.put("waitSends", merchantOrderMapper.selectOrderCountByStatus(
				merchantCode, OrderStatusEnum.WAREHOUSE_NOTICED.getValue()
						.toString()));
		tips.put("stockOuts", merchantOrderMapper.selectOrderCountByStatus(
				merchantCode, OrderStatusEnum.SERVICE_NOTICED.getValue()
						.toString()));

		// 缺货单=[], 待换货单=[]
		OrderPunish orderPunish = new OrderPunish();
		orderPunish.setPunishType("1");// 超时效
		orderPunish.setShipmentStatus("0");// 未发货
		orderPunish.setMerchantCode(merchantCode);
		List<OrderPunish> orderPunishs = orderPunishMapper
				.queryOrderPunishList(orderPunish, new RowBounds());
		tips.put("timeOutOrders",
				CollectionUtils.isNotEmpty(orderPunishs) ? orderPunishs.size()
						: 0);

		// 加入redisTemplate
		redisTemplate.opsForValue().set(
				CacheConstant.C_ORDER_SHOPREMIND_KEY + ":" +merchantCode, tips);
		redisTemplate.expire(CacheConstant.C_ORDER_SHOPREMIND_KEY + ":" +merchantCode, 5, TimeUnit.MINUTES);
		return tips;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<?>> analyzeSellByNeerWeek(String merchantCode) {
		Map<String, List<?>> tips = null;
		try {
			tips = (Map<String, List<?>>) redisTemplate.opsForHash().get(CacheConstant.C_ORDER_SHOUYE_SELL_KEY, merchantCode);
		} catch (Exception e) {
			logger.error(MessageFormat.format("merchantCode:{0} | 获取订单提醒redis error.",merchantCode)
						, e);
		}
		
		if (MapUtils.isNotEmpty(tips)) return tips;
		
		//未从缓存获取到数据时调用接口获取
		return this.analyzePreSellByNeerWeek(merchantCode);
	}
	
	private Map<String, List<?>> analyzePreSellByNeerWeek(String merchantCode) {
		//默认8填
		int d = 8;
		List<String> dates = DateUtil.getDateStrByNeerWeek(d);
		Map<String, MerchantDailyStatisticsVo> map = null;
		try {
			map = orderForMerchantApi.statisticMerchantData(merchantCode);
		} catch (Exception e) {
			logger.error("获取商家最近一周销量报表异常.", e);
		}
		//下单量
		List<Long> orderNums = new ArrayList<Long>();
		//下单金额
		List<Double> orderMoneys = new ArrayList<Double>();
		for (String date : dates) {
			if (MapUtils.isEmpty(map) || !map.containsKey(date)) {
				orderNums.add(0L);
				orderMoneys.add(0.0);
				continue;
			}
				
			MerchantDailyStatisticsVo _vo = map.get(date);
			if (null == _vo) continue;
					
			orderNums.add(_vo.getOrderNum());
			orderMoneys.add(_vo.getOrderMoney());
		}
		
		Map<String, List<?>> _list = new HashMap<String, List<?>>();
		_list.put("date", dates);
		_list.put("orderNums", orderNums);
		_list.put("orderMoneys", orderMoneys);
		
		//加入redisTemplate
		redisTemplate.opsForHash().put(CacheConstant.C_ORDER_SHOUYE_SELL_KEY, merchantCode, _list);
		redisTemplate.expire(CacheConstant.C_ORDER_SHOUYE_SELL_KEY, 5, TimeUnit.MINUTES);
		logger.info("merchantCode:{} | 订单首页报表查询.", merchantCode);
				
		return _list;
	}

	@Override
	public List<OrderDetail4sub> getOrderDetail4sub(String orderSubId, String merchantCode) {
		List<OrderDetail4sub> orderDetails = new ArrayList<OrderDetail4sub>();
		try {
			OrderSub order = orderForMerchantApi.findOrderSubByOrderSubIdAndMerchantCode(orderSubId, merchantCode);
			if (null != order) {
				List<OrderDetail4sub> _orderDetails = order.getOrderDetail4subs();
				if (CollectionUtils.isNotEmpty(_orderDetails)) orderDetails.addAll(_orderDetails);
			}
		} catch (Exception e) {
			logger.error("获取订单货品信息异常。", e);
		}
		
		return orderDetails;
	}
	
	@Override
	public boolean doExportOrder(List<Object[]> list, String templatePath, String fileName,
			HttpServletResponse response, String sheetName) throws Exception {
		ExportHelper exportHelper = new ExportHelper();
		try {
			Object[] obj = list.get(list.size() - 1);
			// {(开始行),(总列数)}
			int[] paras = { 1, obj.length };
			// 数值列 {0,1,2,3,....}
			int[] numCol = null;
			// "合计"标题 {(开始列),(结束列)}
			int[] amountCol = null;
			// 指定索引值(0,3)....
			Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			for (int i = 0; i < obj.length; i++) {
				indexMap.put(i, i);
			}
			exportHelper.doExport(list, templatePath, fileName, sheetName, null, paras, numCol, amountCol, indexMap,
					false, response);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 置为备货
	 */
	@Override
	@Transactional
	public void updateStocking(String[] orderSubNos, Map<String, Object> unionUser) throws Exception {
		if (ArrayUtils.isEmpty(orderSubNos)) {
			throw new NullPointerException("orderSubIds is null");
		}
		
		Map<String, OtherOutStore> stockUpBillMappedMap = new HashMap<String, OtherOutStore>();
		Map<OtherOutStore, List<String>> incompleteStockUpBillMap = new HashMap<OtherOutStore, List<String>>();
		List<OrderPackVo> vos = new ArrayList<OrderPackVo>();
		
		String loginName = MapUtils.getString(unionUser, "login_name");
		String merchantCode = MapUtils.getString(unionUser, "supplier_code");
		
		for (int i = 0; i < orderSubNos.length; i++) {
			// 生成其它出库信息
			OrderSub orderSub=orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(orderSubNos[i],merchantCode);
			String intoWarehouseId = warehouseCacheService.getWarehouseIdByCode(orderSub.getWarehouseCode());
			if (intoWarehouseId == null) {
				throw new RuntimeException("备货清单获取订单“" + orderSubNos[i] + "”入库仓库为空");
			}
			OtherOutStore otherOutStore = stockUpBillMappedMap.get(intoWarehouseId);
			if (otherOutStore == null) {
				// 生成备货单号
				String stockUpNo = OrderUtil.getCode(OrderConstant.OTHER_OUT_STORE_CODE_HEAD);
				otherOutStore = new OtherOutStore();
				otherOutStore.setId(UUIDGenerator.getUUID());
				otherOutStore.setOtherOutStoreCode(stockUpNo);// 出库单号 OO 其它出库
				otherOutStore.setOperatorId(loginName);
				otherOutStore.setCreator(loginName);
				otherOutStore.setCreateDate(new Date());
				otherOutStore.setOutStoreDate(new Date());
				// 物理仓库ID表示出库仓
				String warehouseId = warehouseCacheService.getWarehouseIdByCode(unionUser.get("inventory_code").toString());
				if (warehouseId == null) {
					throw new RuntimeException("备货清单获取订单“" + orderSubNos[i] + "”出库仓库为空");
				}
				otherOutStore.setWarehouseId(warehouseId);
				// 出库类型
				otherOutStore.setOutStoreType(OrderConstant.OUT_AREA_WAREHOUSE);
				// 状态
				otherOutStore.setStatus(OrderConstant.CHECK_APPLY);
				otherOutStore.setRemark("招商订单导入");
				// 分摊状态
				otherOutStore.setApportionStatus(OrderConstant.DIS_APPORTION);
				// 目标仓库ID
				otherOutStore.setIntoWarehouseId(intoWarehouseId);
				// 根据供应商编码查询目标仓库
				otherOutStore.setUpdateTimestamp(new Date().getTime());
				// 审核人 审核日期
				otherOutStore.setApprover(loginName);
				otherOutStore.setApproverDate(new Date());
				stockUpBillMappedMap.put(intoWarehouseId, otherOutStore);
				incompleteStockUpBillMap.put(otherOutStore, new ArrayList<String>());
			}
			
			incompleteStockUpBillMap.get(otherOutStore).add(orderSubNos[i]);
			
			OrderPackVo vo = new OrderPackVo();
			vo.setOrderCode(orderSubNos[i]);
			vo.setStockUpNo(otherOutStore.getOtherOutStoreCode());
			vo.setOperator(loginName);
			vo.setOperateDate(new Date());
			vos.add(vo);
		}
		
		// 4,生成备货清单
		boolean result = orderForMerchantApi.orderStockingForMerchant(vos, merchantCode);
		logger.info("订单备货（回调）:输入参数 {}-{}-返回结果 -{}",
					new Object[]{JSONArray.fromObject(vos),merchantCode,result} );
		if (!result) {
			throw new Exception("生成备货清单失败！");
		}
		
		List<Map<String,Object>> listOutStore=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> listOutStoreDetail=new ArrayList<Map<String,Object>>();
		Map<String,Object> outStoreMap=null;
		Map<String,Object> outStoreDetailMap=null;
		for (Map.Entry<OtherOutStore, List<String>> entry : incompleteStockUpBillMap.entrySet()) {
			// 主表
			OtherOutStore otherOutStore = entry.getKey();
			outStoreMap=new HashMap<String, Object>();
			outStoreMap.put("id", otherOutStore.getId());
			outStoreMap.put("other_out_store_code", otherOutStore.getOtherOutStoreCode());
			outStoreMap.put("operator_id", otherOutStore.getOperatorId());
			outStoreMap.put("creator", otherOutStore.getCreator());
			outStoreMap.put("create_date", otherOutStore.getCreateDate());
			outStoreMap.put("out_store_date", otherOutStore.getOutStoreDate());
			outStoreMap.put("warehouse_id", otherOutStore.getWarehouseId());
			outStoreMap.put("out_store_type", otherOutStore.getOutStoreType());
			outStoreMap.put("status", otherOutStore.getStatus());
			outStoreMap.put("remark", otherOutStore.getRemark());
			outStoreMap.put("update_timestamp", otherOutStore.getUpdateTimestamp());
			outStoreMap.put("apportion_status", otherOutStore.getApportionStatus());
			outStoreMap.put("into_warehouse_id", otherOutStore.getIntoWarehouseId());
			outStoreMap.put("approver", otherOutStore.getApprover());
			outStoreMap.put("approver_date", otherOutStore.getApproverDate());
			listOutStore.add(outStoreMap);
			
			// 明细表
			for (String orderSubNo : entry.getValue()) {
				com.yougou.ordercenter.model.order.OrderSub orderSub = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(orderSubNo, merchantCode);
				List<com.yougou.ordercenter.model.order.OrderDetail4sub> orderDetail4subs = orderSub.getOrderDetail4subs();
				for (com.yougou.ordercenter.model.order.OrderDetail4sub detail : orderDetail4subs) {
					outStoreDetailMap=new HashMap<String, Object>();
					outStoreDetailMap.put("id", UUIDGenerator.getUUID());
					outStoreDetailMap.put("commodity_code", detail.getProdNo());
					outStoreDetailMap.put("goods_name", detail.getProdName());
					outStoreDetailMap.put("specification", detail.getCommoditySpecificationStr());
					outStoreDetailMap.put("quantity", detail.getCommodityNum());
					outStoreDetailMap.put("other_out_store_id", otherOutStore.getId());
					outStoreDetailMap.put("origin_code", orderSubNo);
					listOutStoreDetail.add(outStoreDetailMap);
				}
			}
		}
		orderOutStoreDomainService.addOtherOrderOutStore(listOutStore, listOutStoreDetail);
	}

	/**
	 * 根据订单号获取订单信息 收货人名字 手机 发货时间 快递公司 快递单号 [售后接口]
	 * 
	 * @return
	 */
	@Override
	public OrderSubExpand getOrderSubExpandByOrderSubNo(String orderSubNo,String merchantCode) throws Exception {
		try {
			OrderSub orderSub=orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(orderSubNo,merchantCode);
			OrderSubExpand orderSubExpand=null;
			if(orderSub!=null){
				orderSubExpand=new OrderSubExpand();
				OrderConsigneeInfo orderConsigneeInfo=orderSub.getOrderConsigneeInfo();
				orderSubExpand.setUserName(orderConsigneeInfo==null?"":orderConsigneeInfo.getUserName());
				orderSubExpand.setConsigneeMobile(orderConsigneeInfo==null?"":orderConsigneeInfo.getMobilePhone());
				orderSubExpand.setShipTime(orderSub.getShipTime());
				orderSubExpand.setLogisticsName(orderSub.getLogisticsName());
				orderSubExpand.setExpressOrderId(orderSub.getExpressOrderId());
				orderSubExpand.setOrderSubNo(orderSub.getOrderSubNo());
				
			}
			return orderSubExpand;
		} catch (Exception e) {
			logger.error(MessageFormat.format("根据订单号{0}获取商家{1}的订单信息 收货人名字 手机 发货时间 快递公司 快递单号 发生异常 ",orderSubNo,merchantCode), e);
			throw new Exception("DATA CURD EXCEPTION!");
		}
	}
	
}
