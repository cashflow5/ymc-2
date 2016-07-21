package com.belle.yitiansystem.merchant.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.belle.finance.merchants.deliveryfine.model.vo.MerchantDeliveryFineVo;
import com.belle.infrastructure.orm.basedao.CritMap;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.DateUtil;
import com.belle.other.service.ISqlService;
import com.belle.yitiansystem.asm.model.vo.SaleTraceVo;
import com.belle.yitiansystem.merchant.constant.PunishConstant;
import com.belle.yitiansystem.merchant.dao.IStockPunishRuleDetailDao;
import com.belle.yitiansystem.merchant.dao.PunishOrderAgentDao;
import com.belle.yitiansystem.merchant.dao.PunishOrderDao;
import com.belle.yitiansystem.merchant.dao.PunishRuleDao;
import com.belle.yitiansystem.merchant.dao.mapper.PunishOrderMapper;
import com.belle.yitiansystem.merchant.model.pojo.PunishOrder;
import com.belle.yitiansystem.merchant.model.pojo.PunishOrderAgent;
import com.belle.yitiansystem.merchant.model.pojo.PunishRule;
import com.belle.yitiansystem.merchant.model.pojo.StockPunishRuleDetail;
import com.belle.yitiansystem.merchant.model.vo.PunishOrderVo;
import com.belle.yitiansystem.merchant.service.PunishService;
import com.yougou.component.email.api.IEmailApi;
import com.yougou.component.email.model.MailSenderInfo;
import com.yougou.merchant.api.supplier.service.ISupplierService;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
import com.yougou.ordercenter.api.order.IOrderForMerchantApi;
import com.yougou.ordercenter.common.OrderStatusEnum;
import com.yougou.ordercenter.model.order.OrderDetail4sub;
import com.yougou.ordercenter.model.order.OrderSub;
import com.yougou.ordercenter.vo.order.MerchantMessageVo;

/**
 * 商家处罚管理
 * 
 * @author he.wc
 * 
 */
@Service
public class PunishServiceImpl implements PunishService {

	private static final Logger logger = LoggerFactory.getLogger(PunishServiceImpl.class);

	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Resource
	private PunishRuleDao punishRuleDao;
	
	@Autowired
	private IStockPunishRuleDetailDao detailDao;

	@Resource
	private PunishOrderDao punishOrderDao;

	@Resource
	private ISqlService sqlService;

	@Resource
	private RabbitTemplate rabbitMerchantsTemplate;

	@Resource
	private ISupplierService supplierService;

	@Resource
	private PunishOrderAgentDao punishOrderAgentDao;

	@Resource
	private IEmailApi emailApi;

	@Resource
	private IOrderForMerchantApi orderForMerchantApi;
	
	@Resource
	private com.yougou.ordercenter.api.order.IOrderApi orderApi;
	
	@Resource
	private PunishOrderMapper punishOrderMapper;
	
	/**
	 * 通过商家编号得到商家处罚规则
	 */
	public PunishRule getPunishRuleByMerchantsCode(String merchantsCode) {
		CritMap critMap = new CritMap();
		if (StringUtils.isNotBlank(merchantsCode)) {
			critMap.addEqual("merchantCode", merchantsCode);
			List<PunishRule> list = punishRuleDao.findByCritMap(critMap);
			if (!list.isEmpty()) {
				return list.get(0);
			}
		}
		return null;
	}

	/**
	 * 保存商家处罚规则
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveOrUpdatePunishRule(PunishRule punishRule) {
		String id = punishRule.getId();
		Timestamp curDate = new Timestamp(new Date().getTime());
		if (StringUtils.isNotBlank(id)) {
			PunishRule entity = punishRuleDao.getById(id);
			BeanUtils.copyProperties(punishRule, entity, new String[] { "id", "merchantCode", "createTime" });
			entity.setUpdateTime(curDate);
			punishRuleDao.getTemplate().update(entity);
			logger.info("update punishRule " + entity);
		} else {
			punishRule.setCreateTime(curDate);
			punishRule.setUpdateTime(curDate);
			punishRuleDao.getTemplate().save(punishRule);
		}

	}

	/**
	 * 保存处罚订单
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void savePunishOrder(PunishOrder punishOrder) {
		punishRuleDao.getTemplate().save(punishOrder);
	}

	/**
	 * 审核或取消处罚订单
	 * 
	 * @param id
	 * @param vaildStatus
	 * @param validPerson
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void vaildPunishOrder(String id, String validStatus, String validPerson) throws Exception {
		Timestamp curDate = new Timestamp(new Date().getTime());
		PunishOrder entity = punishOrderDao.getById(id);
		entity.setValidTime(curDate);
		entity.setValidPerson(validPerson);
		entity.setPunishOrderStatus(validStatus);
		entity.setUpdateTime(curDate);
		punishRuleDao.getTemplate().update(entity);
		/*
		if("1".equals(entity.getPunishOrderStatus())){
			entity.setValidTime(curDate);
			entity.setValidPerson(validPerson);
			entity.setPunishOrderStatus(validStatus);
			entity.setUpdateTime(curDate);
			punishRuleDao.getTemplate().update(entity);
			if (validStatus.equals("2")) {
				this.vaildPunishMQ(entity);
			}
		}
		*/
	}

	/**
	 * 审核订单后发送MQ
	 * 
	 * @param id
	 * @throws Exception 
	 */
	public void vaildPunishMQ(PunishOrder entity) throws Exception {
		MerchantDeliveryFineVo deliveryFineVo = new MerchantDeliveryFineVo();
		deliveryFineVo.setOrderNo(entity.getOrderNo());
		deliveryFineVo.setSpOrderNo(entity.getThirdOrderNo());
		deliveryFineVo.setOrderDate(entity.getOrderTime());
		deliveryFineVo.setCheckDate(entity.getValidTime());
		String merchantsCode = entity.getMerchantCode();
		String merchantsName = new String();
		if (StringUtils.isNotBlank(merchantsCode)) {
			SupplierVo vo = supplierService.getSupplierByMerchantCode(merchantsCode);
			if (null != vo) {
				merchantsName = vo.getSupplier();
			}
		}

		deliveryFineVo.setSupplierName(merchantsName);
		deliveryFineVo.setSupplierCode(merchantsCode);
		deliveryFineVo.setGoodStatus(Integer.valueOf(entity.getShipmentStatus()));
		deliveryFineVo.setOrderAmount(entity.getOrderPrice());
		deliveryFineVo.setDeductAmount(entity.getPunishPrice());
		Long hour;
		if (entity.getShipmentStatus().equals(PunishConstant.SHIPMENT_YES)) {
			hour = (entity.getShipmentTime().getTime() - entity.getOrderTime().getTime()) / 3600000;
		} else {
			hour = (new Date().getTime() - entity.getOrderTime().getTime()) / 3600000;
		}
		deliveryFineVo.setOverTime(Double.valueOf(hour.toString()));
		deliveryFineVo.setOrderSourceNo(entity.getOrderSourceNo());
		deliveryFineVo.setOutShopName(entity.getOutShopName());
		deliveryFineVo.setViolateType(Integer.valueOf(entity.getPunishType()));
		// rabbitMerchantsTemplate.convertAndSend("merchants.punish.topic","merchants.punish.queue",deliveryFineVo);
		rabbitMerchantsTemplate.convertAndSend(deliveryFineVo);
		logger.info("审核订单后发送MQ,发送" + deliveryFineVo);

	}
	
	@Override
	public PageFinder<Map<String, Object>> queryPunishOrderList(PunishOrderVo punishOrderVo, Query query) {
		Map<String,Object> params = new HashMap<String,Object>();
		String brandNo = punishOrderVo.getBrandNo();
		if(brandNo !=null && !brandNo.trim().equals("")){
			String[]brandNos = brandNo.split(",");
			params.put("brandNos", brandNos);
		}
		//实际为商家编码
		String codeStr = punishOrderVo.getMerchantName();
		if(codeStr !=null && !codeStr.trim().equals("")){
			String[] merchantCodes = codeStr.split(",");
			params.put("merchantCodes", merchantCodes);
		}
		params.put("punishOrderVo", punishOrderVo);
		params.put("punishOrderVo", punishOrderVo);
		if(query != null){
			params.put("start", (query.getPage() -1) * query.getPageSize());
			params.put("pageSize", query.getPageSize());
		}
		List<Map<String,Object>> punishOrderList = punishOrderMapper.queryPunishOrderList(params);
		int punishOrderCount = punishOrderMapper.queryPunishOrderCount(params);		
		PageFinder<Map<String, Object>> pageFider = new PageFinder<Map<String,Object>>(query.getPage(), query.getPageSize(), punishOrderCount, punishOrderList);
		return pageFider;
	}

	/*@Override
	public PageFinder<Map<String, Object>> queryPunishOrderList(PunishOrderVo punishOrderVo, Query query) {
		List<Object> params = new ArrayList<Object>();
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sqlbuffer = new StringBuffer();
		sqlbuffer.append(" SELECT DISTINCT t1.id,t1.punish_order_status,t1.order_no,t1.third_order_no,t1.order_time,t1.valid_time, ");
		sqlbuffer.append(" sp.supplier,t1.merchant_code,t1.punish_type,t1.shipment_status,t1.shipment_time,");
		sqlbuffer.append(" t1.order_price, t1.is_settle,t1.is_submit,t1.settle_order_no,t1.punish_price,   ");
		sqlbuffer.append(" pu.shipment_hour,pu.timeout_punish_type,pu.timeout_punish_money,pu.timeout_punish_rate ");
		//sqlbuffer.append(" pu.stock_punish_type,pu.stock_punish_money,pu.stock_punish_rate ");
		sqlbuffer.append(", (SELECT COUNT(1) FROM tbl_merchant_shipment_day_setting sd ");
		sqlbuffer.append(" where sd.is_shipment_day = 0 ");
		sqlbuffer.append(" and sd.date between DATE_FORMAT(t1.order_time,'%Y-%m-%d') and DATE_FORMAT(ifnull(t1.shipment_time,now()),'%Y-%m-%d') ) noshipDays ");
		sqlbuffer.append(" FROM tbl_sp_supplier_punish_order t1 ");
		sqlbuffer.append(" INNER JOIN tbl_sp_supplier sp on sp.supplier_code = t1.merchant_code");
		sqlbuffer.append(" INNER JOIN tbl_sp_supplier_punish_rule pu on pu.merchant_code = t1.merchant_code ");
		sqlbuffer.append(" inner join tbl_order_sub os on os.order_sub_no = t1.order_no ");
		sqlbuffer.append(" inner join tbl_order_detail4sub od on od.order_sub_id = os.id ");
		sqlbuffer.append(" inner join tbl_commodity_style  cs on cs.no = od.commodity_no ");
		sqlbuffer.append(" WHERE 1=1 and t1.punish_order_status!=0 ");
		StringBuffer wherebuffer = new StringBuffer();
		if (StringUtils.isNotBlank(punishOrderVo.getPunishOrderStatus())) {
			wherebuffer.append(" AND  t1.punish_order_status = ? ");
			params.add(punishOrderVo.getPunishOrderStatus());
		}
		if (StringUtils.isNotBlank(punishOrderVo.getOrderNo())) {
			wherebuffer.append(" AND  t1.order_no = ? ");
			params.add(punishOrderVo.getOrderNo());
		}
		
		if(StringUtils.isNotBlank(punishOrderVo.getMerchantCode())){
			wherebuffer.append(" AND t1.merchant_code = ? ");
			params.add(punishOrderVo.getMerchantCode());
		}
		
		if (StringUtils.isNotBlank(punishOrderVo.getMerchantName())) {
			String codeStr = punishOrderVo.getMerchantName();
			codeStr = codeStr.replace(",", "','");
			wherebuffer.append(" AND t1.merchant_code in ('"+codeStr+"') ");
		}
		
		if (StringUtils.isNotBlank(punishOrderVo.getShipmentStatus())) {
			wherebuffer.append(" AND t1.shipment_status = ? ");
			params.add(punishOrderVo.getShipmentStatus());
		}
		if (StringUtils.isNotBlank(punishOrderVo.getPunishType())) {
			wherebuffer.append(" AND t1.punish_type = ? ");
			params.add(punishOrderVo.getPunishType());
		}
		if (punishOrderVo.getOrderTimeStart() != null) {
			wherebuffer.append(" AND t1.order_time >= '" + dateTimeFormat.format(punishOrderVo.getOrderTimeStart())
					+ "'");
		}
		if (punishOrderVo.getOrderTimeEnd() != null) {
			wherebuffer
					.append(" AND t1.order_time <= '" + dateTimeFormat.format(punishOrderVo.getOrderTimeEnd()) + "'");
		}
		if (!StringUtils.isEmpty(punishOrderVo.getSupplierYgContacts())) {
			wherebuffer
					.append(" AND t1.merchant_code in(select t.merchant_code from tbl_merchant_supplier_expand t inner join tbl_supplier_yg_contact s on t.yg_contact_user_id = s.user_id where s.user_name like '%"
							+ punishOrderVo.getSupplierYgContacts() + "%')");
		}
		if(StringUtils.isNotBlank(punishOrderVo.getBrandNo())){
			String brandNo = punishOrderVo.getBrandNo();
			brandNo = brandNo.replace(",", "','");
			wherebuffer.append(" AND cs.brand_no in ('"+brandNo+"') ");
		}
		
		if(StringUtils.isNotBlank(punishOrderVo.getIsSettle())){
			if("0".equals(punishOrderVo.getIsSettle())){
				wherebuffer.append(" AND (t1.is_settle = ? or t1.is_settle is null)");
			}else{
				wherebuffer.append(" AND t1.is_settle = ? ");
			}
			params.add(punishOrderVo.getIsSettle());
		}
		
		if(StringUtils.isNotBlank(punishOrderVo.getIsSubmit())){
			wherebuffer.append(" AND t1.is_submit = ? ");
			params.add(punishOrderVo.getIsSubmit());
		}
		
		if(StringUtils.isNotBlank(punishOrderVo.getCategory())){
			wherebuffer.append(" AND cs.cat_structname like '"+punishOrderVo.getCategory()+"%' ");
		}
		
		String orderStr = "t1.order_time desc,t1.create_time desc";

		// 拼接查询条件
		PageFinder<Map<String, Object>> pageFinder = sqlService.getDISTINCTObjectsBySql(sqlbuffer.toString(), query,
				wherebuffer, params, orderStr);
		return pageFinder;
	}*/

	/**
	 * 通过ids查询违规订单明细
	 */
	public List<Map<String, Object>> queryPunishOrderList(List<String> ids) {
		StringBuffer sqlbuffer = new StringBuffer();
		sqlbuffer.append(" SELECT po.id,po.order_no,po.third_order_no,po.order_time,po.valid_time, ");
		sqlbuffer.append(" sp.supplier,po.merchant_code,po.punish_type,po.shipment_status,po.shipment_time,");
		sqlbuffer.append(" po.order_price, po.is_settle,po.settle_order_no,po.settle_cycle,po.punish_price,   ");
		sqlbuffer.append(" pu.shipment_hour,pu.timeout_punish_type,pu.timeout_punish_money,pu.timeout_punish_rate, ");
		//sqlbuffer.append(" pu.stock_punish_type,pu.stock_punish_money,pu.stock_punish_rate ");
		sqlbuffer.append(" FROM tbl_sp_supplier_punish_order po ");
		sqlbuffer.append(" INNER JOIN tbl_sp_supplier sp on sp.supplier_code = po.merchant_code");
		sqlbuffer.append(" INNER JOIN tbl_sp_supplier_punish_rule pu on pu.merchant_code = po.merchant_code ");
		sqlbuffer.append(" WHERE po.id in ( ");
		for (String id : ids) {
			sqlbuffer.append("'" + id + "',");
		}
		String sql = sqlbuffer.toString().substring(0, sqlbuffer.length() - 1) + ")";

		return sqlService.getDatasBySql(sql);
	}
	
	/**
	 * 得到时间段的非发货日天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getNoShipmentDay(String startDate, String endDate){
		StringBuffer sqlbuffer = new StringBuffer();
		sqlbuffer.append(" SELECT COUNT(1) days FROM tbl_merchant_shipment_day_setting sd  ");
		sqlbuffer.append(" WHERE sd.date between '"+ startDate +"' AND '"+ endDate +"' ");
		sqlbuffer.append(" AND sd.is_shipment_day = 0  ");
		Long result = (Long) sqlService.getDataBySql(sqlbuffer.toString()).get("days");
		return result;
		
	}

	/**
	 * 通过订单号获处罚订单信息
	 * 一个订单号可能存在多个缺货商品
	 * 单纯靠订单号查询有可能不是唯一的
	 */
	public List<PunishOrder> getPunishOrderByOrderNo(String orderNo) {
		CritMap critMap = new CritMap();
		if (StringUtils.isNotBlank(orderNo)) {
			critMap.addEqual("orderNo", orderNo);
			critMap.addIN("punishOrderStatus", new String[] { PunishConstant.ORDER_STATUS_NORMAL,
					PunishConstant.ORDER_STATUS_VALID });
			return punishOrderDao.findByCritMap(critMap);
		}
		return null;
	}
	
	@Override
	public List<PunishOrder> getPunishOutOfStockByOrderNo(String orderNo) {
		if (StringUtils.isNotBlank(orderNo)) {
			List<Map<String,Object>> mapList = 
					sqlService.getDatasBySql("select e.order_sub_no,ed.level_code from tbl_order_handle_exception e " +
							"inner join tbl_order_handle_exception_detail ed on e.id = ed.order_handle_id " +
							"where e.order_sub_no = '"+orderNo+"' and e.exception_type in (10,12,14)");
			if(mapList!=null && mapList.size()>0){
				List<PunishOrder> temp = new ArrayList<PunishOrder>();
				PunishOrder order = null;
				for(Map<String,Object> map : mapList){
					order = new PunishOrder();
					order.setInsideCode(MapUtils.getString(map, "level_code"));
					order.setOrderNo(orderNo);
					temp.add(order);
				}
				return temp;
			}
		}
		return null;
	}

	/**
	 * 更新处罚订单价格
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updatePunishOrderPrice(String id, Double price) {
		PunishOrder entity = punishOrderDao.getById(id);
		entity.setPunishPrice(price);
		punishOrderDao.getTemplate().update(entity);
	}

	/**
	 * 生成超时订单
	 * 
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveSyncPunishOrder(PunishOrderAgent orderAgent) throws Exception {
		Calendar cal = Calendar.getInstance();
		Date cruDate = cal.getTime();
		
		String merchantsCode = orderAgent.getMerchantCode();
		PunishRule rule = this.getPunishRuleByMerchantsCode(merchantsCode);
		if (rule != null) {
			Long shipmentHour = rule.getShipmentHour();
			Long overHour = new Long(0);
			Long differHour = new Long(0);
			
			Timestamp shipmentTime = orderAgent.getShipmentTime();
			Timestamp orderTime = orderAgent.getOrderTime();
			if (shipmentTime != null) {
				differHour = this.getNoShipmentDay(df.format(orderTime), df.format(shipmentTime)) * 24;
				overHour = (shipmentTime.getTime() - orderTime.getTime()) / 3600000 - differHour;
			} else {
				differHour = this.getNoShipmentDay(df.format(orderTime), df.format(cruDate)) * 24;
				overHour = (cruDate.getTime() - orderTime.getTime()) / 3600000 - differHour;
			}

			Timestamp curDate = new Timestamp(new Date().getTime());
			if (shipmentHour < overHour) {
				PunishOrder punishOrder = new PunishOrder();
				String type = rule.getTimeoutPunishType();
				String orderNo = orderAgent.getOrderNo();
				List<PunishOrder> orderEntity = this.getPunishOrderByOrderNo(orderNo);
				if (orderEntity == null || (orderEntity!=null && orderEntity.size()==0)) {
					BeanUtils.copyProperties(orderAgent, punishOrder);
					punishOrder.setPunishType(PunishConstant.PUNISH_TYPE_TIMEOUT);
					punishOrder.setPunishOrderStatus(PunishConstant.ORDER_STATUS_NORMAL);
					punishOrder.setCreateTime(curDate);
					punishOrder.setUpdateTime(curDate);
					punishOrder.setIsSubmit("0");
					Double punishPrice = rule.getTimeoutPunishMoney() == null ? 0 : rule.getTimeoutPunishMoney();
					Double punishRate = rule.getTimeoutPunishRate() == null ? 0 : rule.getTimeoutPunishRate(); 
					Double orderPrice = orderAgent.getOrderPrice() == null ? 0 : orderAgent.getOrderPrice(); 
					if (StringUtils.equals(type, PunishConstant.PUNISH_PRICE_TYPE_DISCOUNT)) {
						punishPrice = orderPrice * punishRate / 100;
					}
					punishOrder.setPunishPrice(punishPrice);
					punishOrderDao.getTemplate().save(punishOrder);

					logger.debug("JOB生成违规订单，订单状态[已超时],操作[新建],违规订单信息[{}]", punishOrder);
				}
				orderAgent.setIsDelete(PunishConstant.ORDER_CANCEL_YES);
				punishOrderAgentDao.getTemplate().update(orderAgent);
			}// 已发货
			else if (orderAgent.getShipmentTime() != null) {
				orderAgent.setIsDelete(PunishConstant.ORDER_CANCEL_YES);
				punishOrderAgentDao.getTemplate().save(orderAgent);
				logger.info("JOB生成违规订单,订单状态[正常发货],操作[标志删除],订单中间表信息[{}]", orderAgent);
			} else {
				logger.debug("JOB生成违规订单，订单状态[未发货未超时],操作[需要再同步],订单中间表信息[{}]", orderAgent);
			}
		}

	}


	/**
	 * 得到处罚订单中间列表
	 * 
	 * @return
	 */
	public List<PunishOrderAgent> getPunishOrderAgentList() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -12);
		Timestamp date = new Timestamp(cal.getTimeInMillis());
		List<PunishOrderAgent> list = new ArrayList<PunishOrderAgent>();
		CritMap critMap = new CritMap();
		critMap.addEqual("isDelete", PunishConstant.ORDER_CANCEL_NO);
		critMap.addLessAndEq("orderTime", date);
		list = punishOrderAgentDao.findByCritMap(critMap);
		if (list != null) {
			logger.info("JOB将同步订单的数量{}", list.size());
		}
		return list;
	}

	/**
	 * 向商家发提醒邮件
	 * @throws Exception 
	 */
	public void sendPunishOrderEmail() {
		List<PunishRule> punishRuleList = this.getPunishRuleForOverTime();
		Iterator<PunishRule> it = punishRuleList.iterator();
		while (it.hasNext()) {
			PunishRule punishRule = it.next();
			String merchantsCode = punishRule.getMerchantCode();
			List<PunishOrder> punishOrderList = this.getPunishOrderListByMerchantsCode(merchantsCode);

			// 邮件通知为启用，且有处罚订单信息
			if (punishOrderList != null && punishOrderList.size() > 0) {
				int size = punishOrderList.size();
				List<String> orderNos = new ArrayList<String>();
				for (int i = 0; i < size; i++) {
					PunishOrder punishOrder = punishOrderList.get(i);
					OrderSub orderSub = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(
							punishOrder.getOrderNo(), merchantsCode);

					if (orderSub == null) {
						logger.debug(String.format("调用订单接口返回NULL，订单号[%s],商家编码[%s],", punishOrder.getOrderNo(),
								merchantsCode));
						continue;
					}
				
					//订单状态为已确认，配送状态为拣货中就发送邮件
					if(orderSub.getOrderStatus() == OrderStatusEnum.WAREHOUSE_NOTICED.getValue() || orderSub.getOrderStatus() == OrderStatusEnum.CONFIRMED.getValue()){
						orderNos.add(punishOrder.getOrderNo());
					}

				}
				String emails = punishRule.getEmails();
				if (emails != null && CollectionUtils.isNotEmpty(orderNos)) {
					SupplierVo sp = new SupplierVo();
					try {
						sp = supplierService.getSupplierByMerchantCode(merchantsCode);
					} catch (Exception e) {
						e.printStackTrace();
					}
					String merchant = sp.getSupplier();
					String orderNoMsg = StringUtils.join(orderNos, "、");
					StringBuffer sb = new StringBuffer();
					sb.append("<p>您好，"+merchant+":</p>");
					sb.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;您的订单"+orderNoMsg);
					sb.append("已经超过"+punishRule.getShipmentHour()+"小时未发货，请及时处理，以免给顾客带来不好的购物体验，谢谢合作。</p>");
					sb.append("<p style='text-align:right'>优购商城</p>");
					
					MailSenderInfo mailInfo = new MailSenderInfo();
					mailInfo.setContent(sb.toString());
					mailInfo.setTitle("发货提醒-优购商城");
					mailInfo.setToAddress(emails); // 接受方邮箱
					emailApi.sendByOperation(mailInfo);
					logger.info(String.format("商家[%s],超时订单[%s]提醒邮件[%s]发送成功,", merchantsCode, orderNoMsg, emails));
				}
			}

		}

	}

	/**
	 * 根据商家编号查询超时效订单列表
	 * 
	 * @param merchantsCode
	 * @return
	 */
	public List<PunishOrder> getPunishOrderListByMerchantsCode(String merchantsCode) {
		CritMap critMap = new CritMap();
		if (StringUtils.isNotBlank(merchantsCode)) {
			critMap.addEqual("merchantCode", merchantsCode);
			critMap.addIN("punishOrderStatus", new Object[] { PunishConstant.ORDER_STATUS_NORMAL,
					PunishConstant.ORDER_STATUS_VALID });
			critMap.addEqual("punishType", PunishConstant.PUNISH_TYPE_TIMEOUT);
			critMap.addAsc("merchantCode");
			List<PunishOrder> list = punishOrderDao.findByCritMap(critMap);
			if (!list.isEmpty()) {
				return list;
			}
		}
		return null;
	}

	/**
	 * 得到处罚商品规则列表
	 * 
	 * @return
	 */
	public List<PunishRule> getPunishRule() {
		return punishRuleDao.find("from PunishRule");
	}

	/**
	 * 得到发邮件规则
	 * 
	 * @return
	 */
	public List<PunishRule> getPunishRuleForOverTime() {
		CritMap critMap = new CritMap();
		critMap.addEqual("isNotification", PunishConstant.EMAIL_NOTIFICATION_YES);
		critMap.addIsNoNull("emails");
		return punishRuleDao.findByCritMap(critMap);
	}

	/**
	 * 更新处罚订单信息
	 */
	public void updatePunishOrder(PunishOrder punishOrder) {
		punishOrderDao.getTemplate().update(punishOrder);
	}

	/**
	 * 订单MQ消息处理-发货
	 * 
	 * @param merchantMessageVo
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveOrderMessage(MerchantMessageVo merchantMessageVo) throws Exception {

		String orderNo = merchantMessageVo.getOrderSubNo();
		String merchantCode = merchantMessageVo.getMerchantCode();
		if (StringUtils.isBlank(orderNo)) {
			throw new RuntimeException("订单号不能为空！");
		}

		if (merchantMessageVo.getOrderCreateTime() == null) {
			throw new RuntimeException("订单创建时间不能为空！");
		}

		PunishOrderAgent orderAgent = punishOrderAgentDao.getById(orderNo);
		Timestamp curDate = new Timestamp(new Date().getTime());
		Timestamp orderTime = new Timestamp(merchantMessageVo.getOrderCreateTime().getTime());
		PunishRule punishRule = this.getPunishRuleByMerchantsCode(merchantCode);
		if (punishRule == null) {
			logger.debug(String.format("该商家[{}]没有配置违规订单，舍弃->无效订单MQ信息[{}]", merchantCode, merchantMessageVo));
			return;
		}
		if (orderAgent == null) {
			if (merchantMessageVo.getAction().equals(MerchantMessageVo.ACTION_CHECK)) {
				String orderSubNo = merchantMessageVo.getOrderSubNo();
				String[] noSpilt = StringUtils.split(orderSubNo, "_");
				if (noSpilt != null && noSpilt.length > 1 && noSpilt[1].length() > 2) {
					logger.info("处理订单MQ,该订单为换货单，舍弃订单MQ信息[{}]", merchantMessageVo);
					return;
				}
				/* 订单创建 */
				PunishOrderAgent entity = new PunishOrderAgent();
				entity.setThirdOrderNo(merchantMessageVo.getThirdOrderNo());
				entity.setMerchantCode(merchantMessageVo.getMerchantCode());
				entity.setOrderNo(orderSubNo);
				entity.setOrderTime(orderTime);
				entity.setOrderSourceNo(merchantMessageVo.getOrderSurceNo());
				entity.setOutShopName(merchantMessageVo.getOutShopName());
				entity.setShipmentStatus(PunishConstant.SHIPMENT_NO);
				entity.setSynCreateTime(curDate);
				entity.setSynUpdateTime(curDate);
				entity.setPunishCreateTime(orderTime);
				entity.setOrderPrice(merchantMessageVo.getOrderPrice());
				entity.setIsDelete(PunishConstant.ORDER_CANCEL_NO);
				punishOrderAgentDao.getTemplate().save(entity);
				logger.info("处理订单MQ,订单状态[新建],新建订单中间表[{}],订单MQ信息[{}]", entity, merchantMessageVo);
			} else {
				logger.error("处理订单MQ,订单状态[新建],订单中间表没该订单信息，舍弃订单MQ信息[{}]", merchantMessageVo);
			}
		} else if (merchantMessageVo.getAction().equals(MerchantMessageVo.ACTION_OUTSTORE)) {
			Timestamp shipmentTime = new Timestamp(0);
			if (merchantMessageVo.getShipmentTime() != null) {
				shipmentTime = new Timestamp(merchantMessageVo.getShipmentTime().getTime());
				orderAgent.setShipmentTime(shipmentTime);
				orderAgent.setShipmentStatus(PunishConstant.SHIPMENT_YES);
				orderAgent.setSynUpdateTime(curDate);
				orderAgent.setPunishCreateTime(orderTime);
				punishOrderAgentDao.getTemplate().update(orderAgent);
				logger.info("处理订单MQ,订单状态 [发货],更新订单中间表[{}],订单MQ信息[{}]", orderAgent, merchantMessageVo);

				List<PunishOrder> punishOrderList = this.getPunishOrderByOrderNo(orderNo);
				if (punishOrderList != null && punishOrderList.size()==1) {
					PunishOrder punishOrder = punishOrderList.get(0);
					punishOrder.setShipmentStatus(PunishConstant.SHIPMENT_YES);
					punishOrder.setShipmentTime(shipmentTime);
					punishOrderDao.getTemplate().update(punishOrder);
					logger.info("处理订单MQ,订单状态 [发货],更新违规订单表[{}],订单MQ信息[{}]", punishOrder, merchantMessageVo);
				}
			} else {
				throw new RuntimeException("发货时间不能为空");
			}

		} else if (merchantMessageVo.getAction().equals(MerchantMessageVo.ACTION_CANCEL)) {
			PunishRule rule = this.getPunishRuleByMerchantsCode(merchantCode);
			Long shipmentHour = rule.getShipmentHour();
			Long overHour = new Long(0);
			if (orderAgent.getShipmentTime() != null) {
				overHour = (orderAgent.getShipmentTime().getTime() - orderAgent.getOrderTime().getTime()) / 3600000;
			} else {
				overHour = (new Date().getTime() - orderAgent.getOrderTime().getTime()) / 3600000;
			}
			if (shipmentHour > overHour) {
				orderAgent.setIsDelete(PunishConstant.ORDER_CANCEL_YES);
				orderAgent.setSynUpdateTime(curDate);
				orderAgent.setPunishCreateTime(curDate);
				punishOrderAgentDao.getTemplate().update(orderAgent);
				logger.info("处理订单MQ,订单状态 [取消],更新订单中间表信息[{}],订单MQ信息[{}]", orderAgent, merchantMessageVo);
			}
		}
	}

    /**
     * 向商家发提醒邮件
     * 
     * @throws Exception
     */
    public void sendOrderSaleTraceEmail(SaleTraceVo saleTraceVo) throws Exception {
        OrderSub orderSub= orderApi.getOrderSubByOrderSubNo(saleTraceVo.getOrderSubNo(), 1);
        String merchantCode= orderSub.getOrderDetail4subs().get(0).getMerchantCode();
        PunishRule punishRule=getPunishRuleByMerchantsCode(merchantCode);
        SupplierVo supplierVo=supplierService.getSupplierByMerchantCode(merchantCode);
        StringBuffer sb = new StringBuffer();
        sb.append("<p>您好，"+supplierVo.getSupplier()+":</p>");
        sb.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您有一条待处理工单已经生成，请在24小时内处理并在商家中心工单处理界面回复，工单具体内容如下：</p>");
        sb.append("<li style=\"text-indent:2em;\">订单号:"+saleTraceVo.getOrderSubNo()+"</li>");
        sb.append("<li style=\"text-indent:2em;\">工单号:"+saleTraceVo.getOrderTraceNo()+"</li>");
        sb.append("<li style=\"text-indent:2em;\">问题归属:"+saleTraceVo.getSecondProblem()+"</li>");
        sb.append("<li style=\"text-indent:2em;\">工单内容:"+saleTraceVo.getIssueDescription()+"</li>");
        sb.append("<p style='text-align:right'>优购商城</p>");
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setContent(sb.toString());//邮件内容
        mailInfo.setTitle("优购工单处理通知邮件");
        mailInfo.setToAddress(punishRule.getEmails()); // 接受方邮箱
        emailApi.sendByOperation(mailInfo);
        logger.info(String.format("商家[%s],工单[%s]处理提醒邮件发送成功,", merchantCode, saleTraceVo.getOrderTraceNo()));
    }

	/** 
	 * @see com.belle.yitiansystem.merchant.service.PunishService#getStockPunishRuleDetail(java.lang.String) 
	 */
	@Override
	public List<StockPunishRuleDetail> getStockPunishRuleDetail(String ruleId) {
		return detailDao.getStockPunishRuleDetail(ruleId);
	}
	
	/** 
	 * @see com.belle.yitiansystem.merchant.service.PunishService#savePunishRuleDetail(java.util.List) 
	 */
	@Override
	public boolean savePunishRuleDetail(String ruleId,List<StockPunishRuleDetail> detailList) {
		boolean flag = false;
		try{
			detailDao.savePunishRuleDetail(ruleId,detailList);
			flag = true;
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}
	
	@Override
	public List<Map<String, Object>> queryPunishOrderList(
			PunishOrderVo punishOrderVo) {
		Map<String,Object> params = new HashMap<String,Object>();
		String brandNo = punishOrderVo.getBrandNo();
		if(brandNo !=null && !brandNo.trim().equals("")){
			String[]brandNos = brandNo.split(",");
			params.put("brandNos", brandNos);
		}
		//实际为商家编码
		String codeStr = punishOrderVo.getMerchantName();
		if(codeStr !=null && !codeStr.trim().equals("")){
			String[] merchantCodes = codeStr.split(",");
			params.put("merchantCodes", merchantCodes);
		}
		params.put("punishOrderVo", punishOrderVo);
		params.put("punishOrderVo", punishOrderVo);
		
		List<Map<String,Object>> punishOrderList = punishOrderMapper.queryPunishOrderList(params);
		return punishOrderList;
	}
	/** 
	 * @see com.belle.yitiansystem.merchant.service.PunishService#queryPunishOrderList(com.belle.yitiansystem.merchant.model.vo.PunishOrderVo) 
	 */
	/*@Override
	public List<Map<String, Object>> queryPunishOrderList(
			PunishOrderVo punishOrderVo) {
		List<Object> params = new ArrayList<Object>();
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sqlbuffer = new StringBuffer();
		sqlbuffer.append(" SELECT DISTINCT t1.id,t1.punish_order_status,t1.order_no,t1.third_order_no,t1.order_time,t1.valid_time, ");
		sqlbuffer.append(" sp.supplier,t1.merchant_code,t1.punish_type,t1.shipment_status,t1.shipment_time,");
		sqlbuffer.append(" t1.order_price, t1.is_settle,t1.settle_order_no,t1.settle_cycle,t1.punish_price,   ");
		sqlbuffer.append(" pu.shipment_hour,pu.timeout_punish_type,pu.timeout_punish_money,pu.timeout_punish_rate ");
		//sqlbuffer.append(" pu.stock_punish_type,pu.stock_punish_money,pu.stock_punish_rate ");
		sqlbuffer.append(", (SELECT COUNT(1) FROM tbl_merchant_shipment_day_setting sd ");
		sqlbuffer.append(" where sd.is_shipment_day = 0 ");
		sqlbuffer.append(" and sd.date between DATE_FORMAT(t1.order_time,'%Y-%m-%d') and DATE_FORMAT(ifnull(t1.shipment_time,now()),'%Y-%m-%d') ) noshipDays ");
		sqlbuffer.append(" FROM tbl_sp_supplier_punish_order t1 ");
		sqlbuffer.append(" INNER JOIN tbl_sp_supplier sp on sp.supplier_code = t1.merchant_code");
		sqlbuffer.append(" INNER JOIN tbl_sp_supplier_punish_rule pu on pu.merchant_code = t1.merchant_code ");
		sqlbuffer.append(" inner join tbl_order_sub os on os.order_sub_no = t1.order_no ");
		sqlbuffer.append(" inner join tbl_order_detail4sub od on od.order_sub_id = os.id ");
		sqlbuffer.append(" inner join tbl_commodity_style  cs on cs.no = od.commodity_no ");
		sqlbuffer.append(" WHERE 1=1  ");
		StringBuffer wherebuffer = new StringBuffer();
		if (StringUtils.isNotBlank(punishOrderVo.getPunishOrderStatus())) {
			wherebuffer.append(" AND  t1.punish_order_status = ? ");
			params.add(punishOrderVo.getPunishOrderStatus());
		}
		if (StringUtils.isNotBlank(punishOrderVo.getOrderNo())) {
			wherebuffer.append(" AND  t1.order_no = ? ");
			params.add(punishOrderVo.getOrderNo());
		}
		
		if(StringUtils.isNotBlank(punishOrderVo.getMerchantCode())){
			wherebuffer.append(" AND t1.merchant_code = ? ");
			params.add(punishOrderVo.getMerchantCode());
		}
		
		if (StringUtils.isNotBlank(punishOrderVo.getMerchantName())) {
			String codeStr = punishOrderVo.getMerchantName();
			codeStr = codeStr.replace(",", "','");
			wherebuffer.append(" AND t1.merchant_code in ('"+codeStr+"') ");
		}
		
		if (StringUtils.isNotBlank(punishOrderVo.getShipmentStatus())) {
			wherebuffer.append(" AND t1.shipment_status = ? ");
			params.add(punishOrderVo.getShipmentStatus());
		}
		if (StringUtils.isNotBlank(punishOrderVo.getPunishType())) {
			wherebuffer.append(" AND t1.punish_type = ? ");
			params.add(punishOrderVo.getPunishType());
		}
		if (punishOrderVo.getOrderTimeStart() != null) {
			wherebuffer.append(" AND t1.order_time >= '" + dateTimeFormat.format(punishOrderVo.getOrderTimeStart())
					+ "'");
		}
		if (punishOrderVo.getOrderTimeEnd() != null) {
			wherebuffer
					.append(" AND t1.order_time <= '" + dateTimeFormat.format(punishOrderVo.getOrderTimeEnd()) + "'");
		}
		if (!StringUtils.isEmpty(punishOrderVo.getSupplierYgContacts())) {
			wherebuffer
					.append(" AND t1.merchant_code in(select t.merchant_code from tbl_merchant_supplier_expand t inner join tbl_supplier_yg_contact s on t.yg_contact_user_id = s.user_id where s.user_name like '%"
							+ punishOrderVo.getSupplierYgContacts() + "%')");
		}
		if(StringUtils.isNotBlank(punishOrderVo.getBrandNo())){
			String brandNo = punishOrderVo.getBrandNo();
			brandNo = brandNo.replace(",", "','");
			wherebuffer.append(" AND cs.brand_no in ('"+brandNo+"') ");
		}
		String orderStr = "t1.create_time desc";
		// 拼接查询条件
		List<Map<String, Object>> list = sqlService.getDatasBySql(sqlbuffer.toString(), wherebuffer, params, orderStr);
		return list;
	}*/

	/** 
	 * @see com.belle.yitiansystem.merchant.service.PunishService#getOrderNo(java.lang.String) 
	 */
	@Override
	public Map<String, Object> getOrderNo(String id) {
		String sql = "select order_no from tbl_sp_supplier_punish_order where id = '"+id+"'";
		Map<String, Object>  map = sqlService.getDataBySql(sql);
		return map;
	}

	/** 
	 * @see com.belle.yitiansystem.merchant.service.PunishService#countAmount(java.lang.String, java.lang.String, java.lang.String) 
	 */
	@Override
	public Map<String,Object> countAmount(String startDate, String endDate,
			String merchantName,String punishType) {
		//超时效订单
		String sql = "select sum(punish_price) as countamount,count(id) as countrow from tbl_sp_supplier_punish_order where order_time between '"+startDate+"' and " +
				"'"+endDate+"' and merchant_code = '"+merchantName+"' and punish_order_status = '2' and punish_type = '"+punishType+"' and (is_submit is null or is_submit='0')";
		return sqlService.getDataBySql(sql);
	}
	
	/** 
	 * @see com.belle.yitiansystem.merchant.service.PunishService#getPunishOrderByOrderNo(java.lang.String, boolean) 
	 */
	@Override
	public List<PunishOrder> getPunishOrderByOrderNo(String orderNo, boolean flag) {
		if(flag){
			return getPunishOrderByOrderNo(orderNo);
		}else{
			CritMap critMap = new CritMap();
			if (StringUtils.isNotBlank(orderNo)) {
				critMap.addEqual("orderNo", orderNo);
				critMap.addIN("punishOrderStatus", new String[] { PunishConstant.ORDER_STATUS_NORMAL,
						PunishConstant.ORDER_STATUS_VALID });
				critMap.addEqual("punishType", "0");
				return punishOrderDao.findByCritMap(critMap);
			}
			return null;
		}
	}

	/** 
	 * @see com.belle.yitiansystem.merchant.service.PunishService#updatePunishOrderStatus(java.lang.String, java.lang.String, java.lang.String, java.lang.String) 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean updatePunishOrderStatus(String settleStart, String settleEnd,
			String supplierCode, String punishType, String settleId) throws Exception {
		String sql = "update tbl_sp_supplier_punish_order set is_submit='1',settle_id=? where order_time between ? and ? and " +
				"merchant_code = ? and punish_order_status = '2' and " +
						"punish_type = ? and (is_submit is null or is_submit ='0')";
		return sqlService.updateObject(sql, new Object[]{settleId,settleStart,settleEnd,supplierCode,punishType});
	}
	
	/** 
	 * @see com.belle.yitiansystem.merchant.service.PunishService#getPunishStockList(com.belle.yitiansystem.merchant.model.vo.PunishOrderVo, com.belle.infrastructure.orm.basedao.Query) 
	 */
	@Override
	public PageFinder<Map<String, Object>> getPunishStockList(
			PunishOrderVo punishOrderVo, Query query) {
		List<Object> params = new ArrayList<Object>();
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sqlbuffer = new StringBuffer();
		sqlbuffer.append("select d.id as detail_id,su.supplier_code,su.supplier,p.brand_name,d.level_code,e.exception_time,e.order_sub_no,");
		sqlbuffer.append("s.create_time,(d.prod_total_amt+d.postage_cost) as totalamt,o.id,ed.lack_num ");
		sqlbuffer.append("from tbl_order_handle_exception e inner join tbl_order_handle_exception_detail ed on ed.order_handle_id = e.id  ");
		sqlbuffer.append("inner join tbl_order_sub s on s.order_sub_no = e.order_sub_no inner join ");
		sqlbuffer.append("tbl_order_sub_expand se on se.order_sub_id = s.id inner join tbl_order_detail4sub d "); 
		sqlbuffer.append("on (d.order_sub_id = s.id and d.level_code = ed.level_code) inner join tbl_commodity_product p "); 
		sqlbuffer.append("on d.prod_no = p.product_no inner JOIN tbl_sp_supplier su on su.supplier_code = se.merchant_code " );
		sqlbuffer.append("left join tbl_sp_supplier_punish_order o on (o.order_no = e.order_sub_no and o.inside_code = d.level_code) ");
		StringBuffer wherebuffer = new StringBuffer();
		wherebuffer.append("where e.exception_type in (10,12,14) and (o.id is null or o.punish_order_status='1') ");
		if (StringUtils.isNotBlank(punishOrderVo.getOrderNo())) {
			wherebuffer.append(" AND e.order_sub_no = ? ");
			params.add(punishOrderVo.getOrderNo());
		}
		
		if(StringUtils.isNotBlank(punishOrderVo.getMerchantCode())){
			wherebuffer.append(" AND su.supplier_code = ? ");
			params.add(punishOrderVo.getMerchantCode());
		}
		
		if (StringUtils.isNotBlank(punishOrderVo.getMerchantName())) {
			String codeStr = punishOrderVo.getMerchantName();
			codeStr = codeStr.replace(",", "','");
			wherebuffer.append(" AND su.supplier_code in ('"+codeStr+"') ");
		}
		
		if (punishOrderVo.getOrderTimeStart() != null) {
			wherebuffer.append(" AND s.create_time >= '" + dateTimeFormat.format(punishOrderVo.getOrderTimeStart())
					+ "'");
		}
		if (punishOrderVo.getOrderTimeEnd() != null) {
			wherebuffer
					.append(" AND s.create_time <= '" + dateTimeFormat.format(punishOrderVo.getOrderTimeEnd()) + "'");
		}
		if(punishOrderVo.getLackTimeStart()!=null){
			wherebuffer.append(" AND e.exception_time >= '" + dateTimeFormat.format(punishOrderVo.getLackTimeStart())
					+ "'");
		}
		if(punishOrderVo.getLackTimeEnd()!=null){
			wherebuffer
			.append(" AND e.exception_time <= '" + dateTimeFormat.format(punishOrderVo.getLackTimeEnd()) + "'");
		}
		if (!StringUtils.isEmpty(punishOrderVo.getSupplierYgContacts())) {
			wherebuffer
					.append(" AND su.supplier_code in(select t.merchant_code from tbl_merchant_supplier_expand t inner join tbl_supplier_yg_contact s on t.yg_contact_user_id = s.user_id where s.user_name like '%"
							+ punishOrderVo.getSupplierYgContacts() + "%')");
		}
		if(StringUtils.isNotBlank(punishOrderVo.getBrandNo())){
			String brandNo = punishOrderVo.getBrandNo();
			brandNo = brandNo.replace(",", "','");
			wherebuffer.append(" AND p.brand_no in ('"+brandNo+"') ");
		}
		if(StringUtils.isNotBlank(punishOrderVo.getInsideCode())){
			wherebuffer.append(" AND d.level_code = ? ");
			params.add(punishOrderVo.getInsideCode());
		}
		String orderStr = " s.create_time desc,e.exception_time desc ";
		// 拼接查询条件
		PageFinder<Map<String, Object>> pageFinder = sqlService.getObjectsBySql(sqlbuffer.toString(), query, wherebuffer, params, orderStr);
		return pageFinder;
	}
	
	/** 
	 * @see com.belle.yitiansystem.merchant.service.PunishService#getPunishOutOfStockList(com.belle.yitiansystem.merchant.model.vo.PunishOrderVo) 
	 */
	@Override
	public List<Map<String, Object>> getPunishOutOfStockList(
			PunishOrderVo punishOrderVo) {
		List<Object> params = new ArrayList<Object>();
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sqlbuffer = new StringBuffer();
		sqlbuffer.append("select d.id as detail_id,su.supplier_code,su.supplier,p.brand_name,d.level_code,e.exception_time,e.order_sub_no,");
		sqlbuffer.append("s.create_time,(d.prod_total_amt+d.postage_cost) as totalamt,o.id,d.prod_name,ed.lack_num,st.supplier_code as scode,st.style_no ");
		sqlbuffer.append("from tbl_order_handle_exception e inner join tbl_order_handle_exception_detail ed on ed.order_handle_id = e.id ");
		sqlbuffer.append("inner join tbl_order_sub s on s.order_sub_no = e.order_sub_no inner join ");
		sqlbuffer.append("tbl_order_sub_expand se on se.order_sub_id = s.id inner join "); 
		sqlbuffer.append("tbl_order_detail4sub d on (d.order_sub_id = s.id and d.level_code = ed.level_code) inner join tbl_commodity_product p "); 
		sqlbuffer.append("on d.prod_no = p.product_no inner join  tbl_commodity_style st on st.id = p.commodity_id" );
		sqlbuffer.append(" inner JOIN tbl_sp_supplier su on su.supplier_code = se.merchant_code "); 
		sqlbuffer.append("left join tbl_sp_supplier_punish_order o on (o.order_no = e.order_sub_no and o.inside_code = d.level_code) ");
		StringBuffer wherebuffer = new StringBuffer();
		wherebuffer.append("where e.exception_type in (10,12,14) and (o.id is null or o.punish_order_status='1') ");
		if (StringUtils.isNotBlank(punishOrderVo.getOrderNo())) {
			wherebuffer.append(" AND e.order_sub_no = ? ");
			params.add(punishOrderVo.getOrderNo());
		}
		
		if(StringUtils.isNotBlank(punishOrderVo.getMerchantCode())){
			wherebuffer.append(" AND su.supplier_code = ? ");
			params.add(punishOrderVo.getMerchantCode());
		}
		
		if (StringUtils.isNotBlank(punishOrderVo.getMerchantName())) {
			String codeStr = punishOrderVo.getMerchantName();
			codeStr = codeStr.replace(",", "','");
			wherebuffer.append(" AND su.supplier_code in ('"+codeStr+"') ");
		}
		
		if (punishOrderVo.getOrderTimeStart() != null) {
			wherebuffer.append(" AND s.create_time >= '" + dateTimeFormat.format(punishOrderVo.getOrderTimeStart())
					+ "'");
		}
		if (punishOrderVo.getOrderTimeEnd() != null) {
			wherebuffer
					.append(" AND s.create_time <= '" + dateTimeFormat.format(punishOrderVo.getOrderTimeEnd()) + "'");
		}
		if(punishOrderVo.getLackTimeStart()!=null){
			wherebuffer.append(" AND e.exception_time >= '" + dateTimeFormat.format(punishOrderVo.getLackTimeStart())
					+ "'");
		}
		if(punishOrderVo.getLackTimeEnd()!=null){
			wherebuffer
			.append(" AND e.exception_time <= '" + dateTimeFormat.format(punishOrderVo.getLackTimeEnd()) + "'");
		}
		if (!StringUtils.isEmpty(punishOrderVo.getSupplierYgContacts())) {
			wherebuffer
					.append(" AND su.supplier_code in(select t.merchant_code from tbl_merchant_supplier_expand t inner join tbl_supplier_yg_contact s on t.yg_contact_user_id = s.user_id where s.user_name like '%"
							+ punishOrderVo.getSupplierYgContacts() + "%')");
		}
		if(StringUtils.isNotBlank(punishOrderVo.getBrandNo())){
			String brandNo = punishOrderVo.getBrandNo();
			brandNo = brandNo.replace(",", "','");
			wherebuffer.append(" AND p.brand_no in ('"+brandNo+"') ");
		}
		if(StringUtils.isNotBlank(punishOrderVo.getInsideCode())){
			wherebuffer.append(" AND d.level_code = ? ");
			params.add(punishOrderVo.getInsideCode());
		}
		String orderStr = " s.create_time desc,e.exception_time desc ";
		// 拼接查询条件
		return sqlService.getDatasBySql(sqlbuffer.toString(), wherebuffer, params, orderStr);
	}
	
	/** 
	 * @see com.belle.yitiansystem.merchant.service.PunishService#getValidPunishOutOfStockList(com.belle.yitiansystem.merchant.model.vo.PunishOrderVo) 
	 */
	@Override
	public List<Map<String, Object>> getValidPunishOutOfStockList(
			PunishOrderVo punishOrderVo) {
		List<Object> params = new ArrayList<Object>();
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sqlbuffer = new StringBuffer();
		sqlbuffer.append("select su.supplier_code,su.supplier,p.brand_name,d.level_code,e.exception_time,e.order_sub_no,");
		sqlbuffer.append("s.create_time,(d.prod_total_amt+d.postage_cost) as totalamt,o.punish_price,ed.lack_num,o.is_settle,");
		sqlbuffer.append("(s.total_price+s.actual_postage) as order_price from tbl_order_handle_exception e ");
		sqlbuffer.append("inner join tbl_order_handle_exception_detail ed on ed.order_handle_id = e.id inner join tbl_order_sub s on s.order_sub_no = e.order_sub_no inner join ");
		sqlbuffer.append("tbl_order_sub_expand se on se.order_sub_id = s.id inner join "); 
		sqlbuffer.append("tbl_order_detail4sub d on (d.order_sub_id = s.id and d.level_code = ed.level_code) inner join tbl_commodity_product p "); 
		sqlbuffer.append("on d.prod_no = p.product_no inner join  tbl_commodity_style st on st.id = p.commodity_id" );
		sqlbuffer.append(" inner JOIN tbl_sp_supplier su on su.supplier_code = se.merchant_code "); 
		sqlbuffer.append("inner join tbl_sp_supplier_punish_order o on ");
		sqlbuffer.append("(o.order_no = e.order_sub_no and o.inside_code = d.level_code) ");
		StringBuffer wherebuffer = new StringBuffer();
		wherebuffer.append(" where e.exception_type in (10,12,14) and o.punish_order_status = '2'");
		if (StringUtils.isNotBlank(punishOrderVo.getOrderNo())) {
			wherebuffer.append(" AND  e.order_sub_no = ? ");
			params.add(punishOrderVo.getOrderNo());
		}
		
		if(StringUtils.isNotBlank(punishOrderVo.getMerchantCode())){
			wherebuffer.append(" AND su.supplier_code = ? ");
			params.add(punishOrderVo.getMerchantCode());
		}
		
		if (StringUtils.isNotBlank(punishOrderVo.getMerchantName())) {
			String codeStr = punishOrderVo.getMerchantName();
			codeStr = codeStr.replace(",", "','");
			wherebuffer.append(" AND su.supplier_code in ('"+codeStr+"') ");
		}
		
		if (punishOrderVo.getOrderTimeStart() != null) {
			wherebuffer.append(" AND s.create_time >= '" + dateTimeFormat.format(punishOrderVo.getOrderTimeStart())
					+ "'");
		}
		if (punishOrderVo.getOrderTimeEnd() != null) {
			wherebuffer
					.append(" AND s.create_time <= '" + dateTimeFormat.format(punishOrderVo.getOrderTimeEnd()) + "'");
		}
		if(punishOrderVo.getLackTimeStart()!=null){
			wherebuffer.append(" AND e.exception_time >= '" + dateTimeFormat.format(punishOrderVo.getLackTimeStart())
					+ "'");
		}
		if(punishOrderVo.getLackTimeEnd()!=null){
			wherebuffer
			.append(" AND e.exception_time <= '" + dateTimeFormat.format(punishOrderVo.getLackTimeEnd()) + "'");
		}
		if (!StringUtils.isEmpty(punishOrderVo.getSupplierYgContacts())) {
			wherebuffer
					.append(" AND su.supplier_code in(select t.merchant_code from tbl_merchant_supplier_expand t inner join tbl_supplier_yg_contact s on t.yg_contact_user_id = s.user_id where s.user_name like '%"
							+ punishOrderVo.getSupplierYgContacts() + "%')");
		}
		if(StringUtils.isNotBlank(punishOrderVo.getBrandNo())){
			String brandNo = punishOrderVo.getBrandNo();
			brandNo = brandNo.replace(",", "','");
			wherebuffer.append(" AND p.brand_no in ('"+brandNo+"') ");
		}
		if(StringUtils.isNotBlank(punishOrderVo.getInsideCode())){
			wherebuffer.append(" AND d.level_code = ? ");
			params.add(punishOrderVo.getInsideCode());
		}
		if(StringUtils.isNotBlank(punishOrderVo.getIsSettle())){
			wherebuffer.append(" AND o.is_settle = ? ");
			params.add(punishOrderVo.getIsSettle());
		}
		if(StringUtils.isNotBlank(punishOrderVo.getIsSubmit())){
			wherebuffer.append(" AND o.is_submit = ? ");
			params.add(punishOrderVo.getIsSubmit());
		}
		
		String orderStr = " s.create_time desc ";
		// 拼接查询条件
		return sqlService.getDatasBySql(sqlbuffer.toString(), wherebuffer, params, orderStr);
	}
	
	/** 
	 * @see com.belle.yitiansystem.merchant.service.PunishService#getPunishValidStockList(com.belle.yitiansystem.merchant.model.vo.PunishOrderVo, com.belle.infrastructure.orm.basedao.Query) 
	 */
	@Override
	public PageFinder<Map<String, Object>> getPunishValidStockList(
			PunishOrderVo punishOrderVo, Query query) {
		List<Object> params = new ArrayList<Object>();
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sqlbuffer = new StringBuffer();
		sqlbuffer.append("select su.supplier_code,su.supplier,p.brand_name,d.level_code,e.exception_time,e.order_sub_no,");
		sqlbuffer.append("o.order_time,(d.prod_total_amt+d.postage_cost) as totalamt,o.id,o.is_settle,o.is_submit,ed.lack_num,o.punish_price ");
		sqlbuffer.append("from tbl_order_handle_exception e inner join tbl_order_handle_exception_detail ed on ed.order_handle_id = e.id ");
		sqlbuffer.append("inner join tbl_order_sub s on s.order_sub_no = e.order_sub_no inner join ");
		sqlbuffer.append("tbl_order_sub_expand se on se.order_sub_id = s.id inner join "); 
		sqlbuffer.append("tbl_order_detail4sub d on (d.order_sub_id = s.id and d.level_code = ed.level_code) inner join tbl_commodity_product p "); 
		sqlbuffer.append("on d.prod_no = p.product_no inner JOIN tbl_sp_supplier su on su.supplier_code = se.merchant_code "); 
		sqlbuffer.append("inner join tbl_sp_supplier_punish_order o on ");
		sqlbuffer.append("(o.order_no = e.order_sub_no and o.inside_code = d.level_code) ");
		StringBuffer wherebuffer = new StringBuffer();
		wherebuffer.append(" where e.exception_type in (10,12,14) and o.punish_order_status = '2'");
		if (StringUtils.isNotBlank(punishOrderVo.getOrderNo())) {
			wherebuffer.append(" AND  e.order_sub_no = ? ");
			params.add(punishOrderVo.getOrderNo());
		}
		
		if(StringUtils.isNotBlank(punishOrderVo.getMerchantCode())){
			wherebuffer.append(" AND su.supplier_code = ? ");
			params.add(punishOrderVo.getMerchantCode());
		}
		
		if (StringUtils.isNotBlank(punishOrderVo.getMerchantName())) {
			String codeStr = punishOrderVo.getMerchantName();
			codeStr = codeStr.replace(",", "','");
			wherebuffer.append(" AND su.supplier_code in ('"+codeStr+"') ");
		}
		
		if (punishOrderVo.getOrderTimeStart() != null) {
			wherebuffer.append(" AND o.order_time >= '" + dateTimeFormat.format(punishOrderVo.getOrderTimeStart())
					+ "'");
		}
		if (punishOrderVo.getOrderTimeEnd() != null) {
			wherebuffer
					.append(" AND o.order_time <= '" + dateTimeFormat.format(punishOrderVo.getOrderTimeEnd()) + "'");
		}
		if(punishOrderVo.getLackTimeStart()!=null){
			wherebuffer.append(" AND e.exception_time >= '" + dateTimeFormat.format(punishOrderVo.getLackTimeStart())
					+ "'");
		}
		if(punishOrderVo.getLackTimeEnd()!=null){
			wherebuffer
			.append(" AND e.exception_time <= '" + dateTimeFormat.format(punishOrderVo.getLackTimeEnd()) + "'");
		}
		if (!StringUtils.isEmpty(punishOrderVo.getSupplierYgContacts())) {
			wherebuffer
					.append(" AND su.supplier_code in(select t.merchant_code from tbl_merchant_supplier_expand t inner join tbl_supplier_yg_contact s on t.yg_contact_user_id = s.user_id where s.user_name like '%"
							+ punishOrderVo.getSupplierYgContacts() + "%')");
		}
		if(StringUtils.isNotBlank(punishOrderVo.getBrandNo())){
			String brandNo = punishOrderVo.getBrandNo();
			brandNo = brandNo.replace(",", "','");
			wherebuffer.append(" AND p.brand_no in ('"+brandNo+"') ");
		}
		if(StringUtils.isNotBlank(punishOrderVo.getInsideCode())){
			wherebuffer.append(" AND d.level_code = ? ");
			params.add(punishOrderVo.getInsideCode());
		}
		if(StringUtils.isNotBlank(punishOrderVo.getIsSettle())){
			wherebuffer.append(" AND o.is_settle = ? ");
			params.add(punishOrderVo.getIsSettle());
		}
		if(StringUtils.isNotBlank(punishOrderVo.getIsSubmit())){
			wherebuffer.append(" AND o.is_submit = ? ");
			params.add(punishOrderVo.getIsSubmit());
		}
		
		String orderStr = " s.create_time desc ";
		// 拼接查询条件
		PageFinder<Map<String, Object>> pageFinder = sqlService.getObjectsBySql(sqlbuffer.toString(), query, wherebuffer, params, orderStr);
		return pageFinder;
	}
	
	/** 
	 * @see com.belle.yitiansystem.merchant.service.PunishService#vaildStockPunishOrder(java.lang.String[], java.lang.String, java.lang.String) 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public String vaildStockPunishOrder(String detailId, String validStatus,
			String validPerson, String punishId,String merchantCode) {
		Timestamp curDate = new Timestamp(new Date().getTime());
		PunishOrder entity = null;
		List<String> punishIdList = new ArrayList<String>();
		//判断是否存在PunishOrder，否则会插入重复的数据
		if(StringUtils.isNotBlank(punishId)){
			entity = this.getPunishOrderByPunishId(punishId);
			entity.setUpdateTime(curDate);
			entity.setValidTime(curDate);
			entity.setValidPerson(validPerson);
			entity.setPunishOrderStatus(validStatus);
			//entity.setInsideCode(idArr[1]);
			punishRuleDao.getTemplate().update(entity);
			punishIdList.add(entity.getId());
		}else{
			entity = new PunishOrder();
			entity.setCreateTime(curDate);
			entity.setUpdateTime(curDate);
			entity.setIsSettle("0");
			entity.setIsSubmit("0");
			OrderDetail4sub detail4sub = orderApi.getOrderDetail4subById(detailId);
			OrderSub orderSub = orderForMerchantApi.findOrderSubByOrderSubIdAndMerchantCode(detail4sub.getOrderSubId(), merchantCode);
			entity.setMerchantCode(merchantCode);
			entity.setOrderNo(orderSub.getOrderSubNo());
			entity.setOrderTime(new Timestamp(orderSub.getCreateTime().getTime()));
			entity.setValidTime(curDate);
			entity.setValidPerson(validPerson);
			entity.setPunishOrderStatus(validStatus);
			entity.setOrderPrice(detail4sub.getProdTotalAmt()+detail4sub.getPostageCost());
			entity.setPunishType("0");
			entity.setOrderSourceNo(orderSub.getOrderSourceNo());
			entity.setOutShopName(orderSub.getOutShopName());
			entity.setThirdOrderNo(orderSub.getOutOrderId());
			entity.setInsideCode(detail4sub.getLevelCode());
			punishRuleDao.getTemplate().save(entity);
			punishIdList.add(entity.getId());
		}
		return entity.getId();
	}
	
	/** 
	 * @see com.belle.yitiansystem.merchant.service.PunishService#getPunishOrderByPunishId(java.lang.String) 
	 */
	@Override
	public PunishOrder getPunishOrderByPunishId(String punishId) {
		CritMap critMap = new CritMap();
		if (StringUtils.isNotBlank(punishId)) {
			critMap.addEqual("id", punishId);
			critMap.addIN("punishOrderStatus", new String[] { PunishConstant.ORDER_STATUS_NORMAL,
					PunishConstant.ORDER_STATUS_VALID });
			List<PunishOrder> list = punishOrderDao.findByCritMap(critMap);
			if (!list.isEmpty()) {
				return list.get(0);
			}
		}
		return null;
	}
	
	/** 
	 * @see com.belle.yitiansystem.merchant.service.PunishService#getOutStockRate(java.util.Date, java.util.Date, java.lang.String) 
	 */
	@Override
	public double getOutStockRate(Date orderTimeStart, Date orderTimeEnd,
			String merchantCode) {
		//自己写查数据库
		//orderForMerchantApi
		//orderForMerchantApi.queryOutOfStockForPageFinder(null);
		//查找已审核的缺货率
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sqlbuffer = new StringBuffer();
		//sqlbuffer.append("select count(1) from tbl_sp_supplier_punish_order o where o.punish_order_status = '2'");
		sqlbuffer.append("select sum(ed.lack_num) from tbl_sp_supplier_punish_order o inner join tbl_order_handle_exception e on o.order_no = e.order_sub_no");
		sqlbuffer.append(" inner join tbl_order_handle_exception_detail ed on ed.order_handle_id = e.id  where o.punish_order_status = '2' and o.punish_type = '0'");
		if (orderTimeStart != null) {
			sqlbuffer.append(" AND o.order_time >= '" + dateTimeFormat.format(orderTimeStart) + "'");
		}
		if (orderTimeEnd != null) {
			sqlbuffer.append(" AND o.order_time <= '" + dateTimeFormat.format(orderTimeEnd) + "'");
		}
		if(StringUtils.isNotBlank(merchantCode)){
			sqlbuffer.append(" AND o.merchant_code = '"+merchantCode+"'");
		}
		//已审核的缺货商品数量
		long lackCount = sqlService.getCountBySql(sqlbuffer.toString(), null, null);
		//已发货的订单数
		sqlbuffer.setLength(0);
		//判断是否招商的订单！！se.merchant_code不为空则为商家订单
		//sqlbuffer.append("select count(1) from tbl_order_sub s INNER JOIN ");		
		sqlbuffer.append("select sum(d.commodity_num) from tbl_order_sub s INNER JOIN ");
		sqlbuffer.append("tbl_order_detail4sub d on s.id = d.order_sub_id INNER JOIN ");
		sqlbuffer.append("tbl_order_sub_expand se on se.order_sub_id = s.id and s.order_status = '13' ");
		if (orderTimeStart != null) {
			sqlbuffer.append(" and s.create_time>= '" + dateTimeFormat.format(orderTimeStart) + "'");
		}
		if (orderTimeEnd != null) {
			sqlbuffer.append(" AND s.create_time <= '" + dateTimeFormat.format(orderTimeEnd) + "'");
		}
		if(StringUtils.isNotBlank(merchantCode)){
			sqlbuffer.append(" AND se.merchant_code = '"+merchantCode+"'");
		}
		long sendCount = sqlService.getCountBySql(sqlbuffer.toString(), null, null);
		
		
		if(lackCount+sendCount<=0)
			return 0.0;
		logger.info("已审核的缺货商品数量："+lackCount+"----已发货商品数量："+sendCount+"----缺货率："+(lackCount/(lackCount+sendCount)));
		return lackCount*100*0.01/(lackCount+sendCount);
	}
	
	/** 
	 * @see com.belle.yitiansystem.merchant.service.PunishService#getPunishValidAndNoSubmitStockList(com.belle.yitiansystem.merchant.model.vo.PunishOrderVo, double) 
	 */
	@Override
	public Map<String, Object> getPunishValidAndNoSubmitStockList(
			PunishOrderVo vo, double que) {
		StringBuilder sb = new StringBuilder();
		sb.append("select sum((d.prod_total_amt+d.postage_cost)*(d.commodity_num)*"+que*0.01+") as countamount ");
		StringBuilder fromSb = new StringBuilder();
		fromSb.append("from tbl_sp_supplier_punish_order o inner join tbl_order_sub s ");
		fromSb.append("on s.order_sub_no = o.order_no ");		 
		fromSb.append("inner join tbl_order_detail4sub d on (d.order_sub_id = s.id and d.level_code = o.inside_code) ");	
		fromSb.append("where o.order_time between '"+DateUtil.format(vo.getOrderTimeStart(), "yyyy-MM-dd HH:mm:ss")+"' ");		 
		fromSb.append("and '"+DateUtil.format(vo.getOrderTimeEnd(), "yyyy-MM-dd HH:mm:ss")+"' and o.merchant_code = '"+vo.getMerchantCode()+"' and ");		  
		fromSb.append("o.punish_order_status = '2' and o.punish_type = '0' ");		 
		fromSb.append("and (o.is_submit is null or o.is_submit='0') ");
		StringBuilder otherSb = new StringBuilder();
		otherSb.append("and (o.punish_price is null or o.punish_price!=0)");
		Map<String, Object> punishMap =  sqlService.getDataBySql(sb.toString()+fromSb.toString()+otherSb.toString());
		sb.setLength(0);
		sb.append("select count(o.id) as countrow ");
		punishMap.putAll(sqlService.getDataBySql(sb.toString()+fromSb.toString()));
		return punishMap;
	}
	
	@Override
	public boolean cancelPunishOrder(String registNum){
		boolean flag = false;
		try{
			String sql = "update tbl_sp_supplier_punish_order set is_submit='0' and regist_num = null where regist_num = ?";
			return sqlService.updateObject(sql, new Object[]{registNum});
		}catch(Exception e){
			logger.error("取消违规结算发生服务器错误！",e);
		}
		return flag;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public boolean cleanPunishOrder() {
		//封装查询参数
		List<Object> params = new ArrayList<Object>();
		
		Long count = getTotalCount(params);
		
		int totalPage = 0;// 总页数
		int pageSize = 1000; // 每页数量
		if(count>pageSize){
			totalPage = (int) (count % pageSize ==0 ? count / pageSize : count / pageSize +1);   
		}else {
			totalPage = 1;
		}
		
		//查询违规订单表中超时违规，无发货时间，发货状态 的订单列表
		StringBuffer list_sql = new StringBuffer();		
		list_sql.append("select t.order_no,t.order_time,t.merchant_code from tbl_sp_supplier_punish_order t  ");
		list_sql.append("where  (t.shipment_status = '0' or t.shipment_status is null) ");
		list_sql.append("and t.shipment_time is null ");
		list_sql.append("and t.punish_type = '1' and t.punish_order_status !='0'  ");
		//list_sql.append("and t.order_time >= ? ");
		//list_sql.append("and t.order_time <=  ?  ");
		list_sql.append("order by t.order_time limit ? ,? ");
		
		//需要更新发货时间，发货状态 的超时违规订单
		List<Object[]> batUpParams = new ArrayList<Object[]> ();		
		//需要逻辑删除的的超时违规订单
		List<Object[]> batDelParams = new ArrayList<Object[]> ();
		
		// 分页查询数据,90天内下的订单
		for(int i = 0;i<totalPage;i++){
			// 当前时间-90天
			//params.add(DateUtil.formatDate((DateUtil.diffDate(new Date(), 90))));
			// 当前时间+1天
			//params.add(DateUtil.formatDate(DateUtil.addDate(new Date(), 1)));
			// 起始位置
			params.add(i*pageSize);
			// 每次查询1000条记录
			params.add(pageSize);
			List<Map<String,Object>> resultList = sqlService.getDatasBySql(list_sql.toString(), null, params);
			// 清除参数
			params.clear();
					
			if(resultList!=null && resultList.size()>0){
				setBatParams(batUpParams, batDelParams, resultList);
			}		
		}
		//logger.error("cleanPunishOrder --batUpParams:"+batUpParams.toArray().toString());
		//logger.error("cleanPunishOrder --batDelParams:"+batDelParams.toArray().toString());
		batUpdatePunishOrder(batUpParams, batDelParams);	
		
		return true;
	}
	
	/**
	 * 批量处理违规超时订单，超时的：更新发货时间，发货状态；未超时的：更新发货时间，发货状态，订单状态变为取消
	 * 更新订单中间表发货时间，发货状态
	 * @param batUpParams
	 * @param batDelParams
	 */
	private void batUpdatePunishOrder(List<Object[]> batUpParams,
			List<Object[]> batDelParams) {
		//批量修改已超时违规订单表
		StringBuffer sbf = new StringBuffer();
		sbf.append("update tbl_sp_supplier_punish_order t set t.shipment_time = ? , t.shipment_status = ? ,t.update_time = ?");
		sbf.append(" where t.order_no = ?");
		sbf.append(" and t.merchant_code = ?");
		
		try {
			sqlService.insertObjects(sbf.toString(), batUpParams);
		} catch (Exception e) {
			logger.error("cleanPunishOrder 批量更新违规订单发货时间，发货状态异常："+e.getMessage());
		}
		
		//批量逻辑删除未超时违规订单表punish_order_status = '0'
		StringBuffer sql_s = new StringBuffer();
		sql_s.append("update tbl_sp_supplier_punish_order t set t.update_time = ?, t.punish_order_status = '0' ");
		sql_s.append(" where t.order_no = ?");
		sql_s.append(" and t.merchant_code = ?");
		
		try {
			sqlService.insertObjects(sql_s.toString(), batDelParams);
		} catch (Exception e) {
			logger.error("cleanPunishOrder 批量逻辑删除未超时违规订单表异常："+e.getMessage());
		}
		
		//批量修改超时违规订单中间表
		StringBuffer sql_sbf = new StringBuffer();
		sql_sbf.append("update tbl_sp_supplier_order t set t.shipment_time = ? , t.shipment_status = ?,t.syn_update_time = ? ");
		sql_sbf.append(" where t.order_no = ?");
		sql_sbf.append(" and t.merchant_code = ?");
		try {
			sqlService.insertObjects(sql_sbf.toString(), batUpParams); // 超时
			//sqlService.insertObjects(sql_sbf.toString(), batDelParams); // 未超时
		} catch (Exception e) {
			logger.error("cleanPunishOrder 批量更新违规订单中间表发货时间，发货状态异常："+e.getMessage());
		}
	}
	
	/**
	 * 迭代无发货时间，发货状态超时违规订单，调用订单接口再次判断订单是否真的超时，封装需要更新发货状态，发货时间，违规订单状态的 超时违规订单，以便做批量修改处理
	 * @param batUpParams
	 * @param batDelParams
	 * @param resultList
	 */
	private void setBatParams(List<Object[]> batUpParams,
			List<Object[]> batDelParams, List<Map<String, Object>> resultList) {
		// 迭代查询出的最近3个月内的无发货状态，发货时间的超时违规订单，调用订单接口判断是否真的超时；将没有超时的错误数据进行逻辑删除，更新发货时间发货状态
		for(Map<String,Object> map : resultList){
			
			String subOrderNo = (String) map.get("order_no"); //订单号
			String merchantsCode = (String) map.get("merchant_code"); // 商家编码
			Date orderTime = (Date) map.get("order_time"); // 下单时间
			//根据商家编码获取商家处罚规则 
			PunishRule rule = this.getPunishRuleByMerchantsCode(merchantsCode); 						
			if(rule !=null){
				Long shipmentHour = rule.getShipmentHour(); // 发货时效
				Long overHour = new Long(0); // 发货时间-下单时间-非发货时间（部分日期不发货）
				Long differHour = new Long(0); // 发货时间至下单时间 ，期间非发货时长
				// 调用订单接口获取订单基本信息
				OrderSub orderSub = orderApi.getOrderSubByOrderSubNo(subOrderNo, null);
				if(orderSub!=null){
					int orderStatus = orderSub.getOrderStatus();
					Date shipmentTime = orderSub.getShipTime();
					
					// 订单已经发货,发货时间不为空
					if(orderStatus >=OrderStatusEnum.DELIVERED.getValue() && orderStatus <=OrderStatusEnum.REJECT_TRADED.getValue() && shipmentTime != null){								
						differHour = this.getNoShipmentDay(df.format(orderTime), df.format(shipmentTime)) * 24;
						overHour = (shipmentTime.getTime() - orderTime.getTime()) / 3600000 - differHour;
					}else{
						Calendar cal = Calendar.getInstance();
						Date cruDate = cal.getTime();
						differHour = this.getNoShipmentDay(df.format(orderTime), df.format(cruDate)) * 24;
						overHour = (cruDate.getTime() - orderTime.getTime()) / 3600000 - differHour;
					}
					
					//订单状态为已取消，更新此超时违规订单状态为取消
					if(orderStatus==OrderStatusEnum.CANCELLED.getValue()){
						batDelParams.add(new Object[]{DateUtil.getDateTime(new Date()),subOrderNo,merchantsCode});
					}else{
						// 发货时效<发货间隔时间，订单超时,封装订单号，商家编码以便批量修改发货时间发货状态
						if (shipmentHour < overHour) {
							//订单状态已发货，发货时间不为空，将发货时间，发货状态封装
							if(orderStatus >=OrderStatusEnum.DELIVERED.getValue() && orderStatus <=OrderStatusEnum.REJECT_TRADED.getValue() && shipmentTime!=null){
								setUpdateParams(batUpParams, subOrderNo, merchantsCode, DateUtil.getDateTime(shipmentTime));
							}
						// 订单未超时，更新违规订单状态为取消即可	
						}else{
							batDelParams.add(new Object[]{DateUtil.getDateTime(new Date()),subOrderNo,merchantsCode});
							
						}
					}
				
				}else{
					logger.error("cleanPunishOrder 调用订单API没有获取到订单基本信息,订单号:"+subOrderNo);
				}
			}
		}
	}
	
	/**
	 * 获取90天内下的单，订单无发货状态，发货时间 的总数
	 * @param params
	 * @return
	 */
	private Long getTotalCount(List<Object> params) {
		//查询违规订单表中超时违规，无发货时间，发货状态 的订单总记录数，90天内下的订单
		StringBuffer count_sql = new StringBuffer();
		count_sql.append("select count(1) from tbl_sp_supplier_punish_order t  ");
		count_sql.append("where  (t.shipment_status = '0' or t.shipment_status is null) ");
		count_sql.append("and t.shipment_time is null ");
		count_sql.append("and t.punish_type = '1' and t.punish_order_status !='0' ");
		//count_sql.append("and t.order_time >= ? ");
		//count_sql.append("and t.order_time <=  ?  ");
		
		// 当前时间-90天
		//params.add(DateUtil.formatDate((DateUtil.diffDate(new Date(), 90))));
		// 当前时间+1天
		//params.add(DateUtil.formatDate(DateUtil.addDate(new Date(), 1)));
		//获取总记录数
		Long count = sqlService.getCountBySql(count_sql.toString(), null,params);
		params.clear(); // 清除参数，以备后用
		return count;
	}
	
	/**
	 * 封装批量修改参数：发货时间，发货状态等
	 * @param batParams
	 * @param subOrderNo
	 * @param merchantsCode
	 * @param shipmentTime
	 */
	private void setUpdateParams(List<Object[]> batParams, String subOrderNo,
			String merchantsCode, String shipmentTime) {
		
			Object[] param = new Object[5];
			param[0] = shipmentTime;
			param[1] = PunishConstant.SHIPMENT_YES;	
			param[2] = DateUtil.getDateTime(new Date());	
			param[3] = subOrderNo;
			param[4] = merchantsCode;	
			
			batParams.add(param);
		
	}
}