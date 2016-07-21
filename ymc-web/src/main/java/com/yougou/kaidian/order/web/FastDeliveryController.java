package com.yougou.kaidian.order.web;

import java.awt.Color;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.yougou.kaidian.commodity.convert.ExcelToDataModelConverter;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.framework.beans.CooperationModel;
import com.yougou.kaidian.framework.constant.Constant;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.order.model.MerchantOrderPrintInputDto;
import com.yougou.kaidian.order.model.MerchantQueryOrderPrintOutputDto;
import com.yougou.kaidian.order.model.OrderSubExpand;
import com.yougou.kaidian.order.service.IOrderForMerchantService;
import com.yougou.kaidian.order.util.OrderUtil;
import com.yougou.kaidian.user.constant.UserConstant;
import com.yougou.kaidian.user.model.pojo.CommonUseLogisticsCompany;
import com.yougou.kaidian.user.model.pojo.MerchantCenterOperationLog;
import com.yougou.kaidian.user.service.IMerchantCenterOperationLogService;
import com.yougou.kaidian.user.service.IMerchantUsers;
import com.yougou.ordercenter.api.order.IOrderForMerchantApi;
import com.yougou.ordercenter.common.OrderStatusEnum;
import com.yougou.ordercenter.constant.OrderConstant;
import com.yougou.ordercenter.model.order.OrderConsigneeInfo;
import com.yougou.ordercenter.model.order.OrderSub;
import com.yougou.ordercenter.vo.merchant.output.CancelOrModifyOrderForMerchantVo;
import com.yougou.ordercenter.vo.merchant.output.QueryOrderPickOutputDto;
import com.yougou.ordercenter.vo.merchant.output.QueryOrderPrintOutputDto;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.product.Product;
import com.yougou.wms.wpi.orderdeliverystatus.service.IOrderDeliveryStatusDomainService;
import com.yougou.yop.api.IMerchantApiOrderService;

@Controller
@RequestMapping("order/fastdelivery")
public class FastDeliveryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FastDeliveryController.class);

	/** 未导出 **/
	private static final String FTL_NOT_EXPORTED = "/manage/order/notexported_of_fast_delivery";

	/** 已导出未发货 **/
	private static final String FTL_NOT_DELIVERY = "/manage/order/notdelivery_of_fast_delivery";

	/** 已完成 **/
	private static final String FTL_DELIVERED = "/manage/order/delivered_of_fast_delivery";

	/** 缺货订单 **/
	private static final String FTL_OUT_OF_ORDERS = "/manage/order/outoforders_of_fast_delivery";

	/** 全部 **/
	private static final String FTL_ALL_ORDERS = "/manage/order/allorders_of_fast_delivery";

	/** 快捷发货 **/
	private static final String FTL_FAST_DELIVERY = "/manage_unless/order/fast_delivery_of_order";
	
	/** 常用快递 **/
	private static final String FTL_COMMON_USE_DELIVERY = "/manage_unless/order/commonuse_delivery_of_merchant";

	/** 全并发货 **/
	private static final String FTL_MERGER_DELIVERY = "/manage_unless/order/merger_delivery_of_order";
    /** 默认查询的订单天数间隔时间30天 **/
    private static final int QUERY_DATE_RANGE = 29;
	//MJF
	@Resource
	private IOrderForMerchantApi orderForMerchantApi;

	@Resource
	private IMerchantUsers merchantUsers;

	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
    @Resource
    private IOrderForMerchantService orderForMerchantService;
    
    @Resource
    private IMerchantApiOrderService merchantOrderApi;
    
    @Resource
    private IMerchantCenterOperationLogService operationLogService;

    @Resource
    private IOrderDeliveryStatusDomainService orderDeliveryStatusDomainService;
    
    @Resource
    private Properties configProperties;
    
	@RequestMapping("/notexported")
	public ModelAndView queryNotExportedOrder(OrderSubExpand orderSubExpand, Query query, HttpServletRequest request,
			ModelMap modelMap) throws Exception {
		// 获得当前登录的商家
		Map<String, Object> unionUser = SessionUtil.getUnionUser(request);////
		if (MapUtils.isEmpty(unionUser)) {
			throw new Exception("请先登录！");
		}
		
		String merchantCode = MapUtils.getString(unionUser,"supplier_code");
		if(StringUtils.isBlank(merchantCode)){
			throw new Exception("商家编号不能为空！");
		}

		// 商家编码
		/**************** 由于业务部门的要求 这里要加入对申请拦截和客服取消的订单处理 *************/
			orderForMerchantApi.updateProcessInterceptAndCancelOrder(merchantCode);
		/**************** 由于业务部门的要求 这里要加入对申请拦截和客服取消的订单处理 *************/

		// 合作模式为“不入优购库，商家发货”才处理订单同步状态
		CooperationModel cooperationModel = CooperationModel.forIdentifier(MapUtils.getInteger(unionUser,"is_input_yougou_warehouse"));
		if (CooperationModel.NON_ENTER_YOUGOU_WAREHOUSE_MERCHANT_DELIVERY.equals(cooperationModel)) {
			/**************** 由于业务部门的要求 这里要加入对当前商家订单的同步处理 *************/
			orderForMerchantApi.updateSynchronousOrder(merchantCode,MapUtils.getString(unionUser, "supplier", ""));
			/**************** 由于业务部门的要求 这里要加入对当前商家订单的同步处理 *************/
		}

		// 设置查询订单基本状态【已确认】
		orderSubExpand.setOrderStatus(OrderStatusEnum.WAREHOUSE_NOTICED.getValue());
		// 设置查询订单导出状态【未导出】
		orderSubExpand.setOrderExportedStatus(NumberUtils.INTEGER_ZERO);
		// 设置排序
		orderSubExpand.setOrderBy(1);
		
		modelMap = this.getOrderModelMap(orderSubExpand, modelMap, query,request);
		modelMap.addAttribute("orderSubExpand", orderSubExpand);
		modelMap.addAttribute("areaList", merchantUsers.getAreaList());// 获取省市区第一级结果集数据
		modelMap.addAttribute("tagTab", "deliver-all");// 标识进入订单清单
		
		//增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_READ,
        		UserConstant.MENU_KJFH, "未导出", "", "");

		return new ModelAndView(FTL_NOT_EXPORTED, modelMap);
	}

	@RequestMapping("/notdelivery")
	public ModelAndView queryExportedAndNotDeliveryOrder(OrderSubExpand orderSubExpand, Query query,
			HttpServletRequest request, ModelMap modelMap) throws Exception {
	
		// 设置查询订单基本状态
		orderSubExpand.setOrderStatus(OrderStatusEnum.WAREHOUSE_NOTICED.getValue());
		// 设置查询订单导出状态【已导出】
		orderSubExpand.setOrderExportedStatus(NumberUtils.INTEGER_ONE);
		// 设置排序
		orderSubExpand.setOrderBy(1);

		modelMap = this.getOrderModelMap(orderSubExpand, modelMap, query,request);
		modelMap.addAttribute("orderSubExpand", orderSubExpand);
		modelMap.addAttribute("logisticscompanList", merchantUsers.getLogisticscompanList());
		modelMap.addAttribute("tagTab", "deliver-all");// 标识进入订单清单
		
		//增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_READ,
        		UserConstant.MENU_KJFH, "已导出未发货", "", "");
		return new ModelAndView(FTL_NOT_DELIVERY, modelMap);
	}

	@RequestMapping("/delivered")
	public ModelAndView queryDeliveredOrder(OrderSubExpand orderSubExpand, Query query, HttpServletRequest request,
			ModelMap modelMap) throws Exception {
		
		// 设置查询订单基本状态-已发货
		orderSubExpand.setOrderStatus(OrderStatusEnum.DELIVERED.getValue());
		// 配送状态 未发货【备货中】
		orderSubExpand.setDeliveryStatus(OrderConstant.DELIVERY_SEND);
		// 设置排序
		orderSubExpand.setOrderBy(2);

		modelMap = this.getOrderModelMap(orderSubExpand, modelMap, query,request);
		modelMap.addAttribute("orderSubExpand", orderSubExpand);
		modelMap.addAttribute("logisticscompanList", merchantUsers.getLogisticscompanList());
		modelMap.addAttribute("tagTab", "deliver-all");// 标识进入订单清单

		//增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_READ,
        		UserConstant.MENU_KJFH, "已发货", "", "");
		return new ModelAndView(FTL_DELIVERED, modelMap);
	}

	@RequestMapping("/outoforders")
	public ModelAndView queryOutOfOrders(OrderSubExpand orderSubExpand, Query query, HttpServletRequest request,
			ModelMap modelMap) throws Exception {
		// 设置查询订单基本状态【已挂起】
		orderSubExpand.setOrderStatus(OrderStatusEnum.SERVICE_NOTICED.getValue());
		// 设置排序
		orderSubExpand.setOrderBy(3);

		modelMap = this.getOrderModelMap(orderSubExpand, modelMap, query,request);
		modelMap.addAttribute("orderSubExpand", orderSubExpand);
		modelMap.addAttribute("areaList", merchantUsers.getAreaList());// 获取省市区第一级结果集数据
		modelMap.addAttribute("tagTab", "deliver-all");// 标识进入订单清单

		//增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_READ,
        		UserConstant.MENU_KJFH, "缺货订单", "", "");
		return new ModelAndView(FTL_OUT_OF_ORDERS, modelMap);
	}
	
	/**
	 * 得到订单Model
	 * @param orderSubExpand
	 * @param modelMap
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public ModelMap getOrderModelMap(OrderSubExpand orderSubExpand,ModelMap modelMap, Query query,HttpServletRequest request) throws Exception{
		MerchantOrderPrintInputDto dto = new MerchantOrderPrintInputDto();
		
		Map<String, Object> unionUser = SessionUtil.getUnionUser(request);////
		String merchantCode = MapUtils.getString(unionUser, "supplier_code");
		String warehouseCode = MapUtils.getString(unionUser, "warehouse_code");
		if(StringUtils.isBlank(merchantCode)){
			throw new Exception("商家编号不能为空！");
		}
		if(StringUtils.isBlank(warehouseCode)){
			throw new Exception("商家仓库编号不能为空！");
		}
		orderSubExpand.setSupplierCode(merchantCode);
		orderSubExpand.setMerchantCode(merchantCode);
		orderSubExpand.setWarehouseCode(warehouseCode);
		
		
		//应订单组要求,当订单时间不为空时，要增加15天以内的下单时间查询条件。
		//如果页面的下单时间不输入，默认查询15天以内的数据,但是如果订单号有输入则忽略下单时间（无论是否输入了下单时间）
		if(!StringUtils.isBlank(orderSubExpand.getTimeStart()) && StringUtils.isBlank(orderSubExpand.getTimeEnd()) 
				&& StringUtils.isBlank(orderSubExpand.getOrderSubNo())){
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			Date  startdate = format1.parse(orderSubExpand.getTimeStart());
			Date  enddate  =DateUtils.addDays(startdate, QUERY_DATE_RANGE);
			orderSubExpand.setTimeEnd(format1.format(enddate));
			//throw new Exception("当下单开始时间不为空，下单结束时间也不能为空！");
		}else if(StringUtils.isNotBlank(orderSubExpand.getTimeEnd())&&StringUtils.isNotBlank(orderSubExpand.getOrderSubNo())){
			//如果同时填写了订单号和时间进行查询，则查询后，时间清空
			orderSubExpand.setTimeEnd(null);
		}
		if(StringUtils.isBlank(orderSubExpand.getTimeStart()) && !StringUtils.isBlank(orderSubExpand.getTimeEnd()) 
				&& StringUtils.isBlank(orderSubExpand.getOrderSubNo())){
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			Date  enddate = format1.parse(orderSubExpand.getTimeEnd());
			Date  startdate  =DateUtils.addDays(enddate, -QUERY_DATE_RANGE);
			orderSubExpand.setTimeStart(format1.format(startdate));
			//throw new Exception("当下单结束时间不为空，下单开始时间也不能为空！");
		}else if(StringUtils.isNotBlank(orderSubExpand.getTimeStart())&&StringUtils.isNotBlank(orderSubExpand.getOrderSubNo())){
			//如果同时填写了订单号和时间进行查询，则查询后，时间清空
			orderSubExpand.setTimeStart(null);
		}
		if(!StringUtils.isBlank(orderSubExpand.getTimeStart()) && !StringUtils.isBlank(orderSubExpand.getTimeEnd())){
			Date timeStart = DateUtil2.getdate(orderSubExpand.getTimeStart());
			Date timeEnd = DateUtil2.getdate(orderSubExpand.getTimeEnd());
			long intervalMilli = timeEnd.getTime() - timeStart.getTime();
			int day = (int)( intervalMilli / (24 * 60 * 60 * 1000));
			if(day > QUERY_DATE_RANGE){
				throw new Exception("下单时间隔必须小于等于30天");
			}
		}
		
		BeanUtils.copyProperties(orderSubExpand, dto);
		//如果页面的下单时间不输入，默认查询15天以内的数据
		if(StringUtils.isBlank(dto.getTimeStart()) && StringUtils.isBlank(dto.getTimeEnd()) && StringUtils.isBlank(dto.getOrderSubNo())){
			Calendar calStart = Calendar.getInstance();
			calStart.add(Calendar.DAY_OF_YEAR, -QUERY_DATE_RANGE);
			dto.setTimeStart(DateUtil2.format1(calStart.getTime()));
			dto.setTimeEnd(DateUtil2.format1(new Date()));
		}
        //加入客服申请拦截的订单
		
		if(null!=orderSubExpand.getBaseStatus()&&null!=orderSubExpand.getDeliveryStatus()
				&&OrderConstant.BASE_CONFIRM==orderSubExpand.getBaseStatus()
				&&OrderConstant.DELIVERY_PREPARE_REAl==orderSubExpand.getDeliveryStatus()){
			dto.setWithApplyInterruptForMerchant(Boolean.TRUE);
		}
		dto.setPageNo(query.getPage());
		dto.setPageSize(query.getPageSize());
		
		LOGGER.info("调用订单系统单据打印接口-输入参数 ：{}",dto);
		
		com.yougou.ordercenter.common.PageFinder<MerchantQueryOrderPrintOutputDto> pageFinder = orderForMerchantService.queryPrintList(dto);
		if (pageFinder != null && pageFinder.getData() != null && pageFinder.getData().size() > 0) {
			Map<String,Object> map=new HashMap<String,Object>();
			for(MerchantQueryOrderPrintOutputDto order:pageFinder.getData()){
				
				Object result=this.redisTemplate.opsForHash().get(CacheConstant.C_ORDER_INTERCEPT_NO_KEY, "orderSubNo_"+order.getOrderSubNo());
				map.put(order.getOrderSubNo(), orderForMerchantApi.getModifiedOrCanceledOrder(order.getOrderSubNo())!= null && result == null ? true : false);
				//数据加密 li.j1 2015-5-25
				String[] encryptResult = OrderUtil.encryptMobileAndAddressByDateAndStatus(order.getConsigneeAddress(), 
    					order.getConsigneeMobile(),order.getOrderStatus(), order.getCreateTime() );
    			order.setConsigneeAddress(encryptResult[0]);
    			order.setConsigneeMobile(encryptResult[1]);
			}
			
			LOGGER.info("调用订单系统单据打印接口-返回结果 {}条 ",pageFinder.getData().size());
			modelMap.put("pageFinder", pageFinder);
			modelMap.put("orderintercept", map);
		}
		return modelMap;
	}

	@RequestMapping("/allorders")
	public ModelAndView queryAllOrders(OrderSubExpand orderSubExpand, Query query, HttpServletRequest request,
			ModelMap modelMap) throws Exception {
		// 设置排序
		orderSubExpand.setOrderBy(4);

		modelMap = this.getOrderModelMap(orderSubExpand, modelMap, query, request);;
		modelMap.addAttribute("orderSubExpand", orderSubExpand);
		modelMap.addAttribute("logisticscompanList", merchantUsers.getLogisticscompanList());
		modelMap.addAttribute("orderStatusMap", OrderUtil.getNewOrderStatus());
		modelMap.addAttribute("tagTab", "deliver-all");// 标识进入订单清单

		//增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_READ,
        		UserConstant.MENU_KJFH, "全部", "", "");
		return new ModelAndView(FTL_ALL_ORDERS, modelMap);
	}

	/**
	 * 开始合并发货
	 * 
	 * @param orderSubNo
	 * @param request
	 * @param modelMap
	 * @return ModelAndView
	 */
	@RequestMapping("/mergerDelivering")
	public ModelAndView mergerDeliveringOfOrder(String orderSubNos, HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();

		if (StringUtils.isBlank(orderSubNos)) {
			throw new IllegalStateException("订单号不能为空");
		}

		String[] orderSubNoList = orderSubNos.substring(0, orderSubNos.length() - 1).split(",");
		//用于判断是否全部为韩国首尔直发订单
		int koreaCount = 0;
		if (orderSubNoList == null || orderSubNoList.length > 0) {
			for (String orderSubNo : orderSubNoList) {
				OrderSub orderSub = new OrderSub();
				try {
					orderSub = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(orderSubNo, merchantCode);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (orderSub == null) {
					throw new IllegalStateException("订单系统中没有查找到商家发货订单:" + orderSubNo);
				} else {
					//信息加密
					OrderConsigneeInfo consigneeInfo = orderSub.getOrderConsigneeInfo();
					if(null != consigneeInfo){
						String[] encryptResult = OrderUtil.encryptMobileAndAddressByDateAndStatus(orderSub.getOrderConsigneeInfo().getConsigneeAddress(), 
								orderSub.getOrderConsigneeInfo().getMobilePhone(),orderSub.getOrderStatus(), orderSub.getCreateTime());
						orderSub.getOrderConsigneeInfo().setConsigneeAddress(encryptResult[0]);
					}
					modelMap.put("orderSub", orderSub);
					// 判断是否韩国站首尔直发订单start，added by zhangfeng 2015-12-07
					boolean isKorea = false;
					isKorea = isKoreaOrder(orderSubNo, orderSub, isKorea);					
					if(isKorea){
						koreaCount ++;
					}					
					// 判断是否韩国站首尔直发订单end 
				}
			}
		}
		// 获取韩国直发订单指定物流公司信息
		boolean isKorea = false;
		if(koreaCount == orderSubNoList.length){
			isKorea = true;
			List<Map<String,Object>>koreaLogistics = getKoreaOrderLogisticComPany();			
			modelMap.put("logisticscompanList",koreaLogistics);
		}
		modelMap.put("isKorea", String.valueOf(isKorea));
		
		modelMap.put("orderSubNos", orderSubNos);
		if(!isKorea){
			modelMap.put("logisticscompanList", merchantUsers.getLogisticscompanList());
		}
		//增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_OPERATE,
        		UserConstant.MENU_KJFH, "合并发货", "", orderSubNoList.length+"");
    	
		return new ModelAndView(FTL_MERGER_DELIVERY, modelMap);
	}

	@RequestMapping("/delivering")
	public ModelAndView deliveringOfOrder(String orderSubNo, HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();

		OrderSub orderSub = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(orderSubNo, merchantCode);
		if (orderSub == null) {
			throw new IllegalStateException("订单系统中没有查找到商家发货订单:" + orderSubNo);
		}
		modelMap.put("orderSub", orderSub);
		
		// 判断是否韩国站首尔直发订单start，added by zhangfeng 2015-12-07
		boolean isKorea = false;
		isKorea = isKoreaOrder(orderSubNo, orderSub, isKorea);
		modelMap.put("isKorea", String.valueOf(isKorea));
		
		//  判断是否韩国站首尔直发订单end 
		//if(!isKorea){
			boolean flag = merchantUsers.checkHasCommonUseLogistics(merchantCode);
			if(flag){
				modelMap.put("frequentUseCompanyList", merchantUsers.getfrequentUseCompanyList(merchantCode));
			}else{
				if(isKorea){
					//getKoreaOrderLogisticComPany(isKorea,orderSubNo, modelMap);
					List<Map<String,Object>>koreaLogistics = getKoreaOrderLogisticComPany();			
					modelMap.put("logisticscompanList",koreaLogistics);
				}else{
					modelMap.put("logisticscompanList", merchantUsers.getLogisticscompanList());
				}
			}
			modelMap.put("resultFlag", Boolean.toString(flag));
		//}
		
		
		//信息加密
		OrderConsigneeInfo consigneeInfo = orderSub.getOrderConsigneeInfo();
		if(null != consigneeInfo){
			String[] encryptResult = OrderUtil.encryptMobileAndAddressByDateAndStatus(orderSub.getOrderConsigneeInfo().getConsigneeAddress(), 
					orderSub.getOrderConsigneeInfo().getMobilePhone(),orderSub.getOrderStatus(), orderSub.getCreateTime());
			orderSub.getOrderConsigneeInfo().setConsigneeAddress(encryptResult[0]);
		}
		
		//增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_OPERATE,
        		UserConstant.MENU_KJFH, "发货", orderSub.getOrderSubNo(), "");
        
		
		return new ModelAndView(FTL_FAST_DELIVERY, modelMap);
	}
	
	/**
	 * 判断是否韩国首尔直发订单 added by zhangfeng 2015-12-07 
	 * @param orderSubNo
	 * @param orderSub
	 * @param isKorea
	 * @return
	 */
	private boolean isKoreaOrder(String orderSubNo, OrderSub orderSub,
			boolean isKorea) {
		try{
			isKorea = orderSub.getOrderSourceNo().startsWith(Constant.KOREA_ORDER_SOURCE_NO_START);
		}catch (Exception e) {
			LOGGER.error( MessageFormat.format("订单系统中未返回订单来源编码：{0}",orderSubNo),e );
			throw new IllegalStateException("订单系统中未返回订单来源编码：:" + orderSubNo);
		}
		return isKorea;
	}
	
	/**
	 * 获取首尔直发订单指定的物流公司信息
	 * @return
	 * @throws Exception
	 */
	private List <Map<String,Object>> getKoreaOrderLogisticComPany() throws Exception {		

		//存放首尔直发物流公司
		List <Map<String,Object>> koreaLogistics = new ArrayList<Map<String,Object>>();
		//获取所有物流公司信息，
		List<Map<String,Object>> logisticscompanList = merchantUsers.getLogisticscompanList();
		//获取配置项中首尔直发物流公司编码
		String code = (String) configProperties.get(Constant.KOREA_ORDER_LOGISTIC_COMPANY_CODE);
		String[] codes = code.split(",");
		//进行过滤判断，保留首尔直发物流公司
		if(logisticscompanList !=null && logisticscompanList.size()>0){
			for(Map<String,Object> logisticCom : logisticscompanList){
				String logisticCompanyCode = (String) logisticCom.get("logistic_company_code");					
				if(StringUtils.isNotBlank(code)){						
					for(String codeStr : codes){
						if(codeStr.equalsIgnoreCase(logisticCompanyCode)){
							koreaLogistics.add(logisticCom);
							break;
						}
					}
					
				}
			}
		}else{
			LOGGER.error("getKoreaOrderLogisticComPany WMS系统中未返回物流公司信息");
		}
		return koreaLogistics;

	}

	/**
	 * 完成发货
	 * 
	 * @param orderSubNo
	 * @param request
	 * @param modelMap
	 * @return ModelAndView
	 */
	@ResponseBody
	@RequestMapping("/dispatched")
	public String dispatchedOfOrder(String orderSubNo, String logisticsCode, String expressCode,String commodityWeight,
			HttpServletRequest request, ModelMap modelMap) throws Exception {
        expressCode = expressCode.trim();
		if (StringUtils.isBlank(orderSubNo)) {
			throw new IllegalArgumentException("发货订单号为空!");
		} else if (StringUtils.isBlank(logisticsCode)) {
			throw new IllegalArgumentException("发货快递公司为空!");
		} else if (StringUtils.isBlank(expressCode)) {
			throw new IllegalArgumentException("发货快递单号为空!");
		}
		List<OrderSub> orderSubList = new ArrayList<OrderSub>();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		Date outShopDate = new Date();
		orderSubList = orderForMerchantApi.findOrderSubByMerchantCodeAndExpressOrderId(merchantCode,expressCode);
		OrderSub newOrderSub = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(orderSubNo,merchantCode);
		//如果订单为换货单，则校验不能与原订单使用相同的快递单
		if (StringUtils.isNotBlank(newOrderSub.getOriginalOrderNo())) {
			OrderSub _originalOrder = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(newOrderSub.getOriginalOrderNo(), merchantCode);
            if (null != _originalOrder && null != _originalOrder.getExpressOrderId() && _originalOrder.getExpressOrderId().equals(expressCode)) {
                return "warn";
            }
		}
		
		if (orderSubList != null && orderSubList.size() > 0) {
			OrderSub oldOrderSub = orderSubList.get(0);
			String oldExpressInfo = this.getConsigneeInfoStr(oldOrderSub);
			String newExpressInfo = this.getConsigneeInfoStr(newOrderSub);
			if (!StringUtils.equals(oldExpressInfo, newExpressInfo)) {
				//throw new IllegalArgumentException("您录入的快递单号已存在，且两个订单的收货人信息不一致，请重新录入。");
				return "warn";
			}
			/** 以合并之前的发货时间为准  
			if(oldOrderSub.getShipTime()!=null){
				outShopDate = oldOrderSub.getShipTime();
			}
			*/
		}
		 Map<String, Object> params = new HashMap<String , Object>();
         params.put("orderSubNo", orderSubNo);
         params.put("logisticsCompanyCode", logisticsCode.trim());
         params.put("expressCode",  expressCode.trim());
         params.put("outStoreDate", outShopDate);
         //首尔直发订单才有包裹重量  （2016 4月6日需求取消包裹重量）
        /* if(StringUtils.isNotBlank(commodityWeight)){
        	 params.put("weight", Double.parseDouble(commodityWeight.trim()));
         }*/
         params.put("merchantCode", merchantCode);
        boolean isSuccess = merchantOrderApi.newOrderOutStoreForMerchant(params);
        if(isSuccess){
        	try{
        		orderForMerchantService.updateOutStoreStatus(merchantCode,orderSubNo,outShopDate);
        	}catch(Exception e){
        		LOGGER.error(MessageFormat.format("商家{0}订单{1}发货更新招商库数据出错：",new Object[]{merchantCode,orderSubNo})
        		,e);
        	}
        }
		LOGGER.info(MessageFormat.format("调用订单系统出库接口'{'输入参数:'{'订单号:{0},快递公司:{1},快递单号:{2}'}', 输出参数:{3}'}'",
					orderSubNo, logisticsCode, expressCode, isSuccess));
		return Boolean.toString(isSuccess);
	}

	/**
	 * 合并发货-完成发货
	 * 
	 * @param orderSubNo
	 * @param request
	 * @param modelMap
	 * @return ModelAndView
	 */
	@ResponseBody
	@RequestMapping("/mergerDispatched")
	public String mergerDispatchedOfOrder(String orderSubNos, String logisticsCode, String expressCode,String commodityWeight,
			HttpServletRequest request, ModelMap modelMap) throws Exception {
		if (StringUtils.isBlank(orderSubNos)) {
			throw new IllegalArgumentException("发货订单号为空!");
		} else if (StringUtils.isBlank(logisticsCode)) {
			throw new IllegalArgumentException("发货快递公司为空!");
		} else if (StringUtils.isBlank(expressCode)) {
			throw new IllegalArgumentException("发货快递单号为空!");
		} else {
			expressCode=expressCode.trim();
			List<OrderSub> orderSubList = new ArrayList<OrderSub>();
			String[] orderSubNoList = orderSubNos.substring(0, orderSubNos.length() - 1).split(",");
			String merchantCode = YmcThreadLocalHolder.getMerchantCode();

			Set<String> set = new HashSet<String>();
			Date outShopDate = new Date();
			for (String orderSubNo : orderSubNoList) {
				OrderSub orderSub = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(orderSubNo,
						merchantCode);
				set.add(this.getConsigneeInfoStr(orderSub));
			}
			if (set.size() > 1) {
				throw new IllegalArgumentException("各订单的收货信息不一致!");
			}

			orderSubList = orderForMerchantApi.findOrderSubByMerchantCodeAndExpressOrderId(merchantCode,expressCode);
			if (orderSubList != null && orderSubList.size() > 0) {
				OrderSub orderSub = orderSubList.get(0);
				String expressInfo = this.getConsigneeInfoStr(orderSub);
				if (set.contains(expressInfo)) {
					Date date = orderSub.getShipTime();
					if(date!=null){
						Date now = new Date();
						long time = now.getTime()-date.getTime();
						long h = time/(60*60*1000);
						//System.out.println("相隔小时=="+h);
						if(h>=24){
							LOGGER.warn("该快递单号已使用，合并发货时效不能超过24个小时！");
							throw new IllegalArgumentException("该快递单号已使用，合并发货时效不能超过24个小时！");
						}
					}
				}
				/** 以合并之前的发货时间为准  
				if(orderSub.getShipTime()!=null){
					outShopDate = orderSub.getShipTime();
				}*/
			}
			 Map<String, Object> params = new HashMap<String , Object>();        
	         params.put("logisticsCompanyCode", logisticsCode.trim());
	         params.put("expressCode",  expressCode.trim());
	         params.put("outStoreDate", outShopDate);
	         //首尔直发订单才有包裹重量(2016.4.6 首尔直发支持云物流，取消包裹重量)
	        /* if(StringUtils.isNotBlank(commodityWeight)){
	        	 params.put("weight", Double.parseDouble(commodityWeight.trim()));
	         }*/
	         params.put("merchantCode", merchantCode);
	        
			for (String orderSubNo : orderSubNoList) {
				params.put("orderSubNo", orderSubNo);
                //boolean isSuccess = merchantOrderApi.orderOutStoreForMerchant(merchantCode, orderSubNo, logisticsCode, expressCode, outShopDate);
				boolean isSuccess = merchantOrderApi.newOrderOutStoreForMerchant(params);
                if(isSuccess){
                	try{
                		orderForMerchantService.updateOutStoreStatus(merchantCode, orderSubNo, outShopDate);
                	}catch(Exception e){
                		LOGGER.error(MessageFormat.format("商家{0}合并发货订单{1}修改招商库订单状态失败！",new Object[]{merchantCode,orderSubNo})
                				    ,e);
                	}
                }
				LOGGER.info(MessageFormat.format("合并发货，调用订单系统出库接口'{'输入参数:'{'订单号:{0},快递公司:{1},快递单号:{2}'}', 输出参数:{3}'}'",
						orderSubNo, logisticsCode, expressCode, isSuccess));
				if (isSuccess == false) {
					return orderSubNo;
				}
			}
			return Boolean.toString(true);
		}
	}

	/**
	 * 拼接收货人信息
	 * 
	 * @param orderSub
	 * @return
	 */
	private String getConsigneeInfoStr(OrderSub orderSub) {
		OrderConsigneeInfo orderConsigneeInfo = orderSub.getOrderConsigneeInfo();
		String consigneeInfo = StringUtils.join(new String[] { orderConsigneeInfo.getUserName(),
				orderConsigneeInfo.getMobilePhone(), orderConsigneeInfo.getProvinceName(),
				orderConsigneeInfo.getCityName(), orderConsigneeInfo.getAreaName(),
				orderConsigneeInfo.getConsigneeAddress() });
		return consigneeInfo;
	}

	/**
	 * 检查快递单号合法性 ajax
	 * 
	 * @param request
	 * @param expressCode
	 * @param expressInfoString
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkExpressCode")
	public String checkExpressCode(HttpServletRequest request, String expressCode, String expressInfoStr) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		List<OrderSub> orderSubList = new ArrayList<OrderSub>();
		//Set<String> set = new HashSet<String>();
		try {
			orderSubList = orderForMerchantApi.findOrderSubByMerchantCodeAndExpressOrderId(merchantCode,expressCode);
		} catch (Exception e) {
			throw new IllegalArgumentException("调用订单API失败。");
		}
		
		if (orderSubList == null) {
			return Boolean.toString(false);
		}else{
			OrderSub orderSub = orderSubList.get(0);
			String expressInfo = this.getConsigneeInfoStr(orderSub);
			if (StringUtils.equals(expressInfoStr, expressInfo)) {
				return Boolean.toString(true);
			}
		}
		return Boolean.toString(false);
	}
	

	/**
	 * 导出拣货清单
	 * 
	 * @param orderSubIds
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportbog")
	public void exportBillOfGoods(String[] orderSubIds, String[] orderSubNos, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<String> orderSubNoList = new ArrayList<String>();
		
		if (orderSubNos == null) {
			throw new Exception("请选择要导出订单！");
		}
		
		// 获得当前登录的商家
		Map<String, Object> unionUser = SessionUtil.getUnionUser(request);////
		if (MapUtils.isEmpty(unionUser)) {
			throw new Exception("请先登录！");
		}
		
		String merchantCode = MapUtils.getString(unionUser, "supplier_code");
		String warehouseCode = MapUtils.getString(unionUser, "warehouse_code");
		String loginName =   MapUtils.getString(unionUser, "login_name");
		if(StringUtils.isBlank(merchantCode)){
			throw new Exception("商家编号不能为空！");
		}
		if(StringUtils.isBlank(warehouseCode)){
			throw new Exception("商家仓库编号不能为空！");
		}
	
	
		// 查询导出数据
		for (int i = 0; i < orderSubNos.length; i++) {
			orderSubNoList.add(orderSubNos[i]);
		}
		LOGGER.info(MessageFormat.format("调用订单系统拣货清单接口,输入参数:[商家编号:{0},仓库编号:{1},订单列表：{2}]",merchantCode,warehouseCode,orderSubNoList));
		List<QueryOrderPickOutputDto> orderPickList =  orderForMerchantService.queryPcikingList(merchantCode, warehouseCode, orderSubNoList);
		
		if (CollectionUtils.isEmpty(orderPickList)) {
			throw new Exception("没有数据可导出");
		}

		LOGGER.info(MessageFormat.format("商家[{0}]拣货订单数量:{1}.", new Object[] { merchantCode, orderPickList.size() }));

		int rowIndex = 0, cellIndex = 0;
		XSSFWorkbook excel = new XSSFWorkbook();
		XSSFSheet sheet = excel.createSheet();
		XSSFRow row = sheet.createRow(rowIndex++);
		XSSFDrawing drawing = sheet.createDrawingPatriarch();
		ExcelToDataModelConverter.createCell(row, cellIndex++, "销售金额", null, drawing, "所有商品销售价格*购买数量之和。");
		ExcelToDataModelConverter.createCell(row, cellIndex++, "订单备注");
		ExcelToDataModelConverter.createCell(row, cellIndex++, "商品名称");
		ExcelToDataModelConverter.createCell(row, cellIndex++, "商家款色编码");
		ExcelToDataModelConverter.createCell(row, cellIndex++, "商家货品条码");
		ExcelToDataModelConverter.createCell(row, cellIndex++, "货品条码");
		ExcelToDataModelConverter.createCell(row, cellIndex++, "商品品牌");
		ExcelToDataModelConverter.createCell(row, cellIndex++, "商品规格");
		ExcelToDataModelConverter.createCell(row, cellIndex++, "拣货数量");

		Set<String> orderSubNosSet = new HashSet<String>();
		for(QueryOrderPickOutputDto dto:orderPickList){
			cellIndex = 0;
			row = sheet.createRow(rowIndex++);
			ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(dto.getTotalPrice()));
			ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(dto.getMessage()));
			ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(dto.getProdName()));
			//ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(dto.getSupplierCode()));			
			//ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(dto.getThirdPartyCode()));
			cellIndex = setCellComment(cellIndex, row, drawing, dto.getSupplierCode());
			cellIndex = setCellComment(cellIndex, row, drawing, dto.getThirdPartyCode());
			ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(dto.getInsideCode()));
			//ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(dto.getBrandName()));
			cellIndex = setCellComment(cellIndex, row, drawing, dto.getBrandName());
			ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(dto.getCommoditySpecificationStr()));
			ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(dto.getCommodityNum()));
			CollectionUtils.addIgnoreNull(orderSubNosSet, dto.getOrderSubNo());
		}

		OutputStream os = null;
		try {
			// 下载生成的模板
			response.reset();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader(
					"Content-Disposition",
					MessageFormat.format("attachment; filename=bill_of_goods_{0}.xlsx",
							DateFormatUtils.format(new Date(), "yyyyMMdd")));
			os = response.getOutputStream();
			excel.write(os);
		} finally {
			IOUtils.closeQuietly(os);
		}
		Date now = new Date();
		if (!orderForMerchantApi.updateExport(merchantCode, new ArrayList<String>(orderSubNosSet), now, loginName)) {
			LOGGER.error(MessageFormat.format("商家[{0}]导出拣货清单(订单号：{1})并更新状态异常.", new Object[] { merchantCode,
					orderSubNos }));
			throw new IllegalStateException("更新导出拣货清单信息异常");
		}else{
			//解决延迟问题，修改招商库数据
			orderForMerchantService.updateExportStatus(merchantCode,new ArrayList<String>(orderSubNosSet),now);
		}
		
		 //增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_OPERATE,
        		UserConstant.MENU_KJFH, "导出拣货清单", "", (null == orderPickList ? 0 : orderPickList.size())+"");
	}
	
	/**
	 * 导出拣货单时如果货品条码有更新，商品的品牌名称，商家货品条码，供应商款色编码 信息为空，在这些为空的列中添加提示信息 
	 * @param cellIndex
	 * @param row
	 * @param drawing
	 * @param cellValue
	 * @return
	 */
	private int setCellComment(int cellIndex, XSSFRow row, XSSFDrawing drawing,
			String cellValue) {
		
		if(cellValue==null || "".equals(cellValue.trim())){
			ExcelToDataModelConverter.createCell(row, cellIndex++, "该条码的商品信息无法找到，可能条码已更换", null, drawing, "该条码的商品信息无法找到，可能条码已更换。");
		}else{
			ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(cellValue));
		}
		return cellIndex;
	}

	/**
	 * 导出缺货订单
	 * 
	 * @param orderSubIds
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportooo")
	public void exportOutOfOrders(String[] orderSubNos, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 获得当前登录的商家
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		if (StringUtils.isBlank(merchantCode)) { throw new Exception("请先登录！"); }
		if (ArrayUtils.isEmpty(orderSubNos)) { throw new Exception("没有数据可导出"); }
		/**
		String joinedOrderSubIds = ArrayUtils.isEmpty(orderSubIds) ? ""
				: ("'" + StringUtils.join(orderSubIds, "','") + "'");
		
		OrderSubExpand orderSubExpand = new OrderSubExpand();
		// 子订单ID列表
		orderSubExpand.setOrderSubIds(joinedOrderSubIds);
		// 商家编码
		orderSubExpand.setSupplierCode(MapUtils.getString(unionUser, "supplier_code"));
		orderSubExpand.setWarehouseCode(MapUtils.getString(unionUser, "warehouse_code"));
		// 设置查询订单基本状态 已挂起
		orderSubExpand.setBaseStatus(OrderConstant.BASE_SUSPEND);
		// 设置挂起状态 缺货挂起
		orderSubExpand.setSuspendType(OrderConstant.SUSPEND_TYPE_LACK);
		// 设置排序
		orderSubExpand.setOrderBy(3);
		*/
		// 设置查询缺货时间是当前一个月内的
//		if (StringUtils.isBlank(orderSubExpand.getTimeStartOutStock())) {
//			orderSubExpand.setTimeStartOutStock(DateFormatUtils.ISO_DATE_FORMAT.format(DateUtils.addMonths(new Date(),
//					-1)));
//		}
		List<String> _orderSubNos = Arrays.asList(orderSubNos);
		MerchantOrderPrintInputDto dto = new MerchantOrderPrintInputDto();
		dto.setOrderSubNos(_orderSubNos);
		dto.setMerchantCode(merchantCode);
		dto.setWarehouseCode(SessionUtil.getWarehouseCodeFromSession(request));
		
		com.yougou.ordercenter.common.PageFinder<MerchantQueryOrderPrintOutputDto> pageFinder = orderForMerchantService.queryPrintList(dto);
		//List<OrderSubExpand> orderSubExpandList = expandService.queryOutStockOrderExport(orderSubExpand);

		int rowIndex = 0, cellIndex = 0;
		XSSFWorkbook excel = new XSSFWorkbook();
		XSSFSheet sheet = excel.createSheet("缺货清单");
		XSSFRow row = sheet.createRow(rowIndex++);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "下单时间");
		ExcelToDataModelConverter.createCell(row, cellIndex++, "订单号");
		ExcelToDataModelConverter.createCell(row, cellIndex++, "订单金额");
		ExcelToDataModelConverter.createCell(row, cellIndex++, "优惠金额");
		ExcelToDataModelConverter.createCell(row, cellIndex++, "运费金额");
		ExcelToDataModelConverter.createCell(row, cellIndex++, "缺货时间");
		ExcelToDataModelConverter.createCell(row, cellIndex++, "订单状态");
		
		for (QueryOrderPrintOutputDto _dto : pageFinder.getData()) {
			cellIndex = 0;
			row = sheet.createRow(rowIndex++);
			ExcelToDataModelConverter.createCell(row, cellIndex++, DateUtil2.getDateTime(_dto.getCreateTime()));
			ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(_dto.getOrderSubNo()));
			ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(_dto.getTotalPrice()));
			ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(_dto.getDiscountAmount()));
			ExcelToDataModelConverter.createCell(row, cellIndex++, ObjectUtils.toString(_dto.getActualPostage()));
			ExcelToDataModelConverter.createCell(row, cellIndex++, DateUtil2.getDateTime(_dto.getBackorderDate()));
			ExcelToDataModelConverter.createCell(row, cellIndex++, _dto.getBaseStatusDesc());
		}

		OutputStream os = null;
		try {
			// 下载生成的模板
			response.reset();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader(
					"Content-Disposition",
					MessageFormat.format("attachment; filename=out_of_orders_{0}.xlsx",
							DateFormatUtils.format(new Date(), "yyyyMMdd")));
			os = response.getOutputStream();
			excel.write(os);
		} finally {
			IOUtils.closeQuietly(os);
		}
		LOGGER.info(MessageFormat.format("商家[{0}]导出缺货订单数量：{1} .", new Object[] { merchantCode, pageFinder.getData().size() }));
		
		//增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_OPERATE,
        		UserConstant.MENU_KJFH, "导出缺货订单", "", pageFinder.getData().size()+"");
	}

	/**
	 * 导出订单明细
	 * 
	 * @param orderSubNos
	 *            订单No [多个英文逗号隔开]
	 * @param request
	 */
	@RequestMapping("doExportOrderdetail")
	public void doExportOrderdetail(OrderSubExpand orderSubExpand, String orderSubNos, int flag,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		//excel数据列数
		 int colCount = 20;
		// 获得当前登录的商家
		Map<String, Object> unionUser = SessionUtil.getUnionUser(request);////
		if (MapUtils.isEmpty(unionUser)) {
			throw new Exception("请先登录！");
		}

		String merchantCode = MapUtils.getString(unionUser, "supplier_code");
		// 组装数据
		if (StringUtils.isBlank(orderSubNos)) {
			// 商家编码
			orderSubExpand.setSupplierCode(merchantCode );
			orderSubExpand.setWarehouseCode(MapUtils.getString(unionUser, "warehouse_code"));
			if (flag == 1) { // 未导出
				// 设置查询订单基本状态【已确认】
				orderSubExpand.setBaseStatus(OrderConstant.BASE_CONFIRM);
				// 设置查询订单导出状态【未导出】
				orderSubExpand.setOrderExportedStatus(NumberUtils.INTEGER_ZERO);
				// 配送状态 未发货【备货中】
				orderSubExpand.setDeliveryStatus(OrderConstant.DELIVERY_PREPARE_REAl);
				// 设置排序
				orderSubExpand.setOrderBy(1);
			} else if (flag == 2) { // 导出未发货
				// 设置查询订单基本状态【已确认】
				orderSubExpand.setBaseStatus(OrderConstant.BASE_CONFIRM);
				// 设置查询订单导出状态【已导出】
				orderSubExpand.setOrderExportedStatus(NumberUtils.INTEGER_ONE);
				// 配送状态 未发货【备货中】
				orderSubExpand.setDeliveryStatus(OrderConstant.DELIVERY_PREPARE_REAl);
				// 设置排序
				orderSubExpand.setOrderBy(1);
			}
		}
		DecimalFormat df =new DecimalFormat("0.0");
		List<Object[]> rowDataList = new ArrayList<Object[]>();
		if (StringUtils.isNotBlank(orderSubNos)) {
			String[] orderNos = orderSubNos.split(",");
			for (String orderNo : orderNos) {
				OrderSub order = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(orderNo, merchantCode);
				if (null == order)
					continue;
				
				 //信息加密
	            OrderConsigneeInfo consigneeInfo = order.getOrderConsigneeInfo();
	            if(null != consigneeInfo){
	            	String[] encryptResult = OrderUtil.encryptMobileAndAddressByDateAndStatus(consigneeInfo.getConsigneeAddress(),
	            			consigneeInfo.getMobilePhone(), order.getOrderStatus(),order.getCreateTime());
	            	consigneeInfo.setConsigneeAddress(encryptResult[0]);
	            	consigneeInfo.setMobilePhone(encryptResult[1]);
	            }				
	            
	            // 判断是否韩国站首尔直发订单start，added by zhangfeng 2015-12-07
				boolean isKorea = false;
				isKorea = isKoreaOrder(orderNo, order, isKorea);	
				if(isKorea){
					colCount=21; // 首尔直发订单导出多收货人一列身份证号码信息
				}	
				// 判断是否韩国站首尔直发订单end
				List<com.yougou.ordercenter.model.order.OrderDetail4sub> details = order.getOrderDetail4subs();
				if (CollectionUtils.isNotEmpty(details)) {
					for (com.yougou.ordercenter.model.order.OrderDetail4sub orderDetail4sub : details) {
						String productNo = orderDetail4sub.getProdNo();
						Product product = commodityBaseApiService.getProductByNo(productNo, true);
						if (null == product) {
							LOGGER.error(MessageFormat.format("导出订单明细并未查询货号[{0}]的相关信息", productNo));
							continue;
						}
						int index = 0;
						Object[] rowData = new Object[colCount];
						//下单时间
						rowData[index++] = DateUtil2.format(order.getCreateTime(), "yyyy/MM/dd HH:mm:ss");
						rowData[index++] = orderNo;
						rowData[index++] = product.getCommodity().getCommodityName();
						rowData[index++] = product.getInsideCode();
						rowData[index++] = product.getCommodity().getStyleNo();
						rowData[index++] = product.getCommodity().getColorName();
						rowData[index++] = product.getSizeName();
						rowData[index++] = orderDetail4sub.getCommodityNum();
						//优购价
						rowData[index++] = df.format(orderDetail4sub.getProdUnitPrice());
						rowData[index++] = orderDetail4sub.getProdTotalAmt() + orderDetail4sub.getPostageCost();
						//促销活动优惠
						rowData[index++] = orderDetail4sub.getProdDiscountAmount()-orderDetail4sub.getCouponPrefAmount();
						//礼品卡
						rowData[index++] = orderDetail4sub.getCouponPrefAmount5();
						//优惠券
						rowData[index++] = orderDetail4sub.getCouponPrefAmount();
						//收货人姓名
						rowData[index++] = order.getOrderConsigneeInfo().getUserName();
						// 收货人身份证号码 added by zhangfeng 2015-12-07 
						if(isKorea){
							rowData[index++] = order.getOrderConsigneeInfo().getIdCardNo();
						}
//						rowData[index++] = order.getOrderConsigneeInfo().getProvinceName() + order.getOrderConsigneeInfo().getCityName() + order.getOrderConsigneeInfo().getAreaName();
						rowData[index++] = order.getOrderConsigneeInfo().getProvinceName();
						rowData[index++] = order.getOrderConsigneeInfo().getCityName();
						rowData[index++] = order.getOrderConsigneeInfo().getAreaName();
						rowData[index++] = order.getOrderConsigneeInfo().getConsigneeAddress();
						rowData[index++] = order.getOrderConsigneeInfo().getZipCode();
						rowData[index++] = order.getOrderConsigneeInfo().getMobilePhone();
						rowDataList.add(rowData);
					}
				}
			}
		}

		if (CollectionUtils.isEmpty(rowDataList)) {
			throw new Exception("没有数据可导出");
		}

		int rowIndex = 0, cellIndex = 0;
		XSSFWorkbook excel = new XSSFWorkbook();
		// 创建样式
		XSSFCellStyle cellStyle = excel.createCellStyle();
		XSSFFont font = excel.createFont();
		font.setBold(true);
		font.setColor(new XSSFColor(Color.RED));
		cellStyle.setFont(font);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		
		XSSFCellStyle bgCellStyle = excel.createCellStyle();
		XSSFFont bgfont = excel.createFont();
		bgfont.setBold(true);
		bgCellStyle.setFont(bgfont);
		bgCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		bgCellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);  
		

		XSSFSheet sheet = excel.createSheet();
		XSSFRow row = sheet.createRow(rowIndex++);
		XSSFDrawing drawing = sheet.createDrawingPatriarch();
		ExcelToDataModelConverter.createCell(row, cellIndex++,"下单时间", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++,"订单号", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "商品名称", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "商家货品条码", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "商品款号", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "商品颜色", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "商品尺码", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "商品数量", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "优购价", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "销售金额", cellStyle, drawing, "实际支付金额");
		ExcelToDataModelConverter.createCell(row, cellIndex++, "促销活动优惠", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "礼品卡", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "优惠券", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "收货人姓名", bgCellStyle);
		// 收货人身份证号码 added by zhangfeng 2015-12-07 
		if(colCount > 20){
			ExcelToDataModelConverter.createCell(row, cellIndex++, "身份证号码", bgCellStyle);
		}	
//		ExcelToDataModelConverter.createCell(row, cellIndex++, "收货人地区", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "省", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "市", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "区", bgCellStyle);
	    ExcelToDataModelConverter.createCell(row, cellIndex++, "收货人地址", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "收货人邮编", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "收货人电话", bgCellStyle);

		for (Object[] rowData : rowDataList) {
			row = sheet.createRow(rowIndex++);
			//CellStyle cellStyle
			for(int i = 0; i < colCount; i ++) {
				ExcelToDataModelConverter.createCell(row, i, ObjectUtils.toString(rowData[i]));
			}
		}
		int columnIndex = 0;
		sheet.autoSizeColumn(columnIndex++);
		sheet.autoSizeColumn(columnIndex++);
		sheet.autoSizeColumn(columnIndex++);
		sheet.autoSizeColumn(columnIndex++);
		sheet.autoSizeColumn(columnIndex++);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 6*2*256);
		// 大于最初设定的20列，则为首尔直发订单，多出一列身份证号码，相关的列设置需依次移动
		if(colCount > 20){
			sheet.setColumnWidth(columnIndex++, 15*2*256); // 身份证号码
		}	
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 20*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 15*2*256);
		
		OutputStream os = null;
		try {
			// 下载生成的模板
			response.reset();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader(
					"Content-Disposition",
					MessageFormat.format("attachment; filename=order_detail_{0}.xlsx",
							DateFormatUtils.format(new Date(), "yyyyMMdd")));
			os = response.getOutputStream();
			excel.write(os);
		} finally {
			IOUtils.closeQuietly(os);
		}
		LOGGER.info(MessageFormat.format("商家[{0}]导出订单明细数量：{1} .", new Object[] { merchantCode, rowDataList.size() }));
		
		//增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_OPERATE,
        		UserConstant.MENU_KJFH, "导出订单明细", "", (null == rowDataList ? 0 : rowDataList.size())+"");
	}
	/**
	 * 导出订单明细
	 * 
	 * @param orderSubNos
	 *            订单No [多个英文逗号隔开]
	 * @param request
	 */
	@RequestMapping("exportOrderdetail")
	public void exportOrderdetail(OrderSubExpand orderSubExpand, String orderSubNos, int flag,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		//excel数据列数
		 int colCount = 20;
		 // 判断是否韩国站首尔直发订单start，added 
		boolean isKorea = false;
		// 获得当前登录的商家
		Map<String, Object> unionUser = SessionUtil.getUnionUser(request);////
		if (MapUtils.isEmpty(unionUser)) {
			throw new Exception("请先登录！");
		}

		String merchantCode = MapUtils.getString(unionUser, "supplier_code");
		// 组装数据
		if (StringUtils.isBlank(orderSubNos)) {
			// 商家编码
			orderSubExpand.setSupplierCode(merchantCode );
			orderSubExpand.setWarehouseCode(MapUtils.getString(unionUser, "warehouse_code"));
			if (flag == 1) { // 未导出
				// 设置查询订单基本状态【已确认】
				orderSubExpand.setBaseStatus(OrderConstant.BASE_CONFIRM);
				// 设置查询订单导出状态【未导出】
				orderSubExpand.setOrderExportedStatus(NumberUtils.INTEGER_ZERO);
				// 配送状态 未发货【备货中】
				orderSubExpand.setDeliveryStatus(OrderConstant.DELIVERY_PREPARE_REAl);
				// 设置排序
				orderSubExpand.setOrderBy(1);
			} else if (flag == 2) { // 导出未发货
				// 设置查询订单基本状态【已确认】
				orderSubExpand.setBaseStatus(OrderConstant.BASE_CONFIRM);
				// 设置查询订单导出状态【已导出】
				orderSubExpand.setOrderExportedStatus(NumberUtils.INTEGER_ONE);
				// 配送状态 未发货【备货中】
				orderSubExpand.setDeliveryStatus(OrderConstant.DELIVERY_PREPARE_REAl);
				// 设置排序
				orderSubExpand.setOrderBy(1);
			}
		}
		DecimalFormat df =new DecimalFormat("0.0");
		List<Object[]> rowDataList = new ArrayList<Object[]>();
		if (StringUtils.isNotBlank(orderSubNos)) {
			String[] orderNos = orderSubNos.split(",");
			for (String orderNo : orderNos) {
				OrderSub order = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(orderNo, merchantCode);
				
				
				if (null == order)
					continue;
				
				 //信息加密
	            OrderConsigneeInfo consigneeInfo = order.getOrderConsigneeInfo();
	            if(null != consigneeInfo){
	            	String[] encryptResult = OrderUtil.encryptMobileAndAddressByDateAndStatus(consigneeInfo.getConsigneeAddress(),
	            			consigneeInfo.getMobilePhone(), order.getOrderStatus(),order.getCreateTime());
	            	consigneeInfo.setConsigneeAddress(encryptResult[0]);
	            	consigneeInfo.setMobilePhone(encryptResult[1]);
	            }				
	            List<String>  expressList=new ArrayList<String>();
				expressList.add(order.getExpressOrderId());
				Map<Object, Object> orderDeliveryWeigh=orderDeliveryStatusDomainService.queryOrderDeliveryWeightByExpressCode(expressList);
	           
				isKorea = isKoreaOrder(orderNo, order, isKorea);	
				if(isKorea){
					colCount=21; // 首尔直发订单导出多收货人一列身份证号码信息
				}	
				// 判断是否韩国站首尔直发订单end
				List<com.yougou.ordercenter.model.order.OrderDetail4sub> details = order.getOrderDetail4subs();
				if (CollectionUtils.isNotEmpty(details)) {
					
					for (com.yougou.ordercenter.model.order.OrderDetail4sub orderDetail4sub : details) {
						String productNo = orderDetail4sub.getProdNo();
						Product product = commodityBaseApiService.getProductByNo(productNo, true);
						if (null == product) {
							LOGGER.error(MessageFormat.format("导出订单明细并未查询货号[{0}]的相关信息", productNo));
							continue;
						}
						int index = 0;
						Object[] rowData = new Object[colCount];
						//下单时间
						rowData[index++] = DateUtil2.format(order.getShipTime(), "yyyy/MM/dd HH:mm:ss");
						rowData[index++] = order.getExpressOrderId();
						//首尔直发多出包裹重量
						if(isKorea){
							rowData[index++] = orderDeliveryWeigh.get(order.getExpressOrderId());
						}
						rowData[index++] = orderNo;
						rowData[index++] = product.getCommodity().getCommodityName();
						
						rowData[index++] = product.getCommodity().getColorName();
						rowData[index++] = product.getSizeName();
						rowData[index++] = orderDetail4sub.getCommodityNum();
						
						
						rowData[index++] = orderDetail4sub.getProdTotalAmt() + orderDetail4sub.getPostageCost();
						
						
						//收货人姓名
						rowData[index++] = order.getOrderConsigneeInfo().getUserName();
						// 收货人身份证号码 added by zhangfeng 2015-12-07 
						if(isKorea){
							rowData[index++] = order.getOrderConsigneeInfo().getIdCardNo();
						}
//						rowData[index++] = order.getOrderConsigneeInfo().getProvinceName() + order.getOrderConsigneeInfo().getCityName() + order.getOrderConsigneeInfo().getAreaName();
						rowData[index++] = order.getOrderConsigneeInfo().getProvinceName();
						rowData[index++] = order.getOrderConsigneeInfo().getCityName();
						rowData[index++] = order.getOrderConsigneeInfo().getAreaName();
						rowData[index++] = order.getOrderConsigneeInfo().getConsigneeAddress();
						rowData[index++] = order.getOrderConsigneeInfo().getZipCode();
						rowData[index++] = order.getOrderConsigneeInfo().getMobilePhone();
						rowDataList.add(rowData);
					}
				}
			}
		}

		if (CollectionUtils.isEmpty(rowDataList)) {
			throw new Exception("没有数据可导出");
		}

		int rowIndex = 0, cellIndex = 0;
		XSSFWorkbook excel = new XSSFWorkbook();
		// 创建样式
		XSSFCellStyle cellStyle = excel.createCellStyle();
		XSSFFont font = excel.createFont();
		font.setBold(true);
		font.setColor(new XSSFColor(Color.RED));
		cellStyle.setFont(font);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		
		XSSFCellStyle bgCellStyle = excel.createCellStyle();
		XSSFFont bgfont = excel.createFont();
		bgfont.setBold(true);
		bgCellStyle.setFont(bgfont);
		bgCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		bgCellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);  
		

		XSSFSheet sheet = excel.createSheet();
		XSSFRow row = sheet.createRow(rowIndex++);
		XSSFDrawing drawing = sheet.createDrawingPatriarch();
		ExcelToDataModelConverter.createCell(row, cellIndex++,"发货时间", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++,"快递单号", bgCellStyle);
		if(isKorea){
			ExcelToDataModelConverter.createCell(row, cellIndex++,"包裹总量", bgCellStyle);
		}
		ExcelToDataModelConverter.createCell(row, cellIndex++,"订单号", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "商品名称", bgCellStyle);
		
		ExcelToDataModelConverter.createCell(row, cellIndex++, "商品颜色", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "商品尺码", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "商品数量", bgCellStyle);
		
		ExcelToDataModelConverter.createCell(row, cellIndex++, "销售金额", cellStyle, drawing, "实际支付金额");
		
		ExcelToDataModelConverter.createCell(row, cellIndex++, "收货人姓名", bgCellStyle);
		// 收货人身份证号码 added by zhangfeng 2015-12-07 
		if(colCount > 20){
			ExcelToDataModelConverter.createCell(row, cellIndex++, "身份证号码", bgCellStyle);
		}	
//		ExcelToDataModelConverter.createCell(row, cellIndex++, "收货人地区", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "省", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "市", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "区", bgCellStyle);
	    ExcelToDataModelConverter.createCell(row, cellIndex++, "收货人地址", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "收货人邮编", bgCellStyle);
		ExcelToDataModelConverter.createCell(row, cellIndex++, "收货人电话", bgCellStyle);

		for (Object[] rowData : rowDataList) {
			row = sheet.createRow(rowIndex++);
			//CellStyle cellStyle
			for(int i = 0; i < colCount; i ++) {
				ExcelToDataModelConverter.createCell(row, i, ObjectUtils.toString(rowData[i]));
			}
		}
		int columnIndex = 0;
		sheet.autoSizeColumn(columnIndex++);
		sheet.autoSizeColumn(columnIndex++);
		sheet.autoSizeColumn(columnIndex++);
		sheet.autoSizeColumn(columnIndex++);
		sheet.autoSizeColumn(columnIndex++);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 6*2*256);
		// 大于最初设定的20列，则为首尔直发订单，多出一列身份证号码，相关的列设置需依次移动
		if(colCount > 20){
			sheet.setColumnWidth(columnIndex++, 15*2*256); // 身份证号码
		}	
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 20*2*256);
		sheet.setColumnWidth(columnIndex++, 5*2*256);
		sheet.setColumnWidth(columnIndex++, 15*2*256);
		
		OutputStream os = null;
		try {
			// 下载生成的模板
			response.reset();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader(
					"Content-Disposition",
					MessageFormat.format("attachment; filename=order_detail_{0}.xlsx",
							DateFormatUtils.format(new Date(), "yyyyMMdd")));
			os = response.getOutputStream();
			excel.write(os);
		} finally {
			IOUtils.closeQuietly(os);
		}
		LOGGER.info(MessageFormat.format("商家[{0}]导出订单明细数量：{1} .", new Object[] { merchantCode, rowDataList.size() }));
		
		//增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_OPERATE,
        		UserConstant.MENU_KJFH, "导出订单明细", "", (null == rowDataList ? 0 : rowDataList.size())+"");
	}
	/**
	 * 获取客服申请订单信息
	 * 
	 * @param orderSubNos
	 *            订单No
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("getOrderIntercept")
	public String getOrderIntercept(String orderSubNo,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CancelOrModifyOrderForMerchantVo dto = orderForMerchantApi.getModifiedOrCanceledOrder(orderSubNo);
		JSONObject jsonObject = new JSONObject();
		Object result=this.redisTemplate.opsForHash().get(CacheConstant.C_ORDER_INTERCEPT_NO_KEY, "orderSubNo_"+orderSubNo);
		boolean flag = (dto != null ? true : false);
		jsonObject.put("flag", flag);
		if(result == null){
			if (flag) {
				jsonObject.put("result", "success");
				if(dto.getInterceptDate()==null){
					jsonObject.put("time", "");
				}else{
					jsonObject.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getInterceptDate()));
				}
				jsonObject.put("reason", "");
			} else {
				jsonObject.put("result", "intercepted_yes");
			}
		}else{
			jsonObject.put("result", "intercepted_no");
		}
		return jsonObject.toString();
	}
	
	/**
	 * 客服申请订单拦截回调接口
	 * 
	 * @param orderSubNos
	 *            订单No
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("doOrderIntercept")
	public String doOrderIntercept(String orderSubNo, int flag,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		List<String> orderSubNoList=new ArrayList<String>();
		orderSubNoList.add(orderSubNo);
		if(1==flag){
			List<String> orderFailure=orderForMerchantApi.orderNoDelivery(orderSubNoList);
			if(orderFailure==null||orderFailure.size()==0){
				jsonObject.put("result", "success");
			}else{
				jsonObject.put("result", "failure");
			}
		}/*else{
			try {
				this.redisTemplate.opsForHash().put(CacheConstant.C_ORDER_INTERCEPT_NO_KEY, "orderSubNo_"+orderSubNo, "no");
				this.redisTemplate.expire(CacheConstant.C_ORDER_INTERCEPT_NO_KEY, 15, TimeUnit.DAYS);
				jsonObject.put("result", "success");
			} catch (Exception e) {
				jsonObject.put("result", "failure");
			}
		}*/
	    return jsonObject.toString();
	}
	
	@RequestMapping("/selectExpress")
	public String selectExpress(HttpServletRequest request,ModelMap modelMap) throws Exception{
		String isKorea = request.getParameter("isKorea");
		
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		List<Map<String, Object>> freLogistics = merchantUsers.getfrequentUseCompanyList(merchantCode);
		modelMap.put("frequentUseCompanyList", freLogistics);
		List<Map<String, Object>> allLogistics = null;
		if("true".equals(isKorea)){
			allLogistics = getKoreaOrderLogisticComPany();
		}else{
			allLogistics = merchantUsers.getLogisticscompanList();
		}
		Map<String,Object> tempMap = null;
		Map<String,Object> tempfMap = null;
		for(Iterator<Map<String,Object>> it = allLogistics.iterator();it.hasNext();){
			tempMap = it.next();
			for(Iterator<Map<String,Object>> fit = freLogistics.iterator();fit.hasNext();){
				tempfMap = fit.next();
				if(MapUtils.getString(tempMap, "logistic_company_code")
						.equals(MapUtils.getString(tempfMap, "logistic_company_code"))){
					it.remove();
				}
			}
		}
		modelMap.put("logisticscompanList", allLogistics);
		return FTL_COMMON_USE_DELIVERY;
	}
	
	@ResponseBody
	@RequestMapping("/addcomexpress")
	public String addCommonUseExpress(HttpServletRequest request,
			String[] codeArr,String[] nameArr){
		JSONObject jsonObject = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		//先删除
		boolean flag = merchantUsers.deleteCommonUseExpress(merchantCode);
		LOGGER.warn("删除商家常用快递公司：{}",flag);
		//再增加
		if(flag && (null!=codeArr) && (null!=nameArr)){
			CommonUseLogisticsCompany com = null;
			for(int i=0;i<codeArr.length;i++){
				com = new CommonUseLogisticsCompany();
				com.setLogisticCompanyCode(codeArr[i]);
				com.setLogisticsCompanyName(nameArr[i]);
				com.setId(UUIDGenerator.getUUID());
				com.setMerchantCode(merchantCode);
				com.setSortNo(i);
				merchantUsers.saveCommonUseExpress(com);
			}
		}
		jsonObject.put("result", "success");
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping("/moreExpress")
	public String moreExpress(HttpServletRequest request,ModelMap modelMap) throws Exception{
		String isKorea = request.getParameter("isKorea");
		List<Map<String, Object>> allLogistics = null;
		if("true".equals(isKorea)){
			allLogistics = getKoreaOrderLogisticComPany();
		}else{
			allLogistics = merchantUsers.getLogisticscompanList();
		}
		//return JSONArray.fromObject(merchantUsers.getLogisticscompanList()).toString();
		return JSONArray.fromObject(allLogistics).toString();
	}
	
	/**
     * 保存操作日志
     * @param request
     * @param operateType
     * @param menuName
     * @param note
     * @param operateOrder
     * @param operateNum
     */
    private void saveOperationLog(HttpServletRequest request,String operateType,
			String menuName, String note, String operateOrder, String operateNum) {
    	// updated by zhangfeng 2016.5.22 直接异步线程处理，不用每次创建单个线程池处理
    	/*ExecutorService threadPool = Executors.newFixedThreadPool(1);		
    		//增加日志       
    	final MerchantCenterOperationLog operationLog = new MerchantCenterOperationLog(request,
    				menuName,menuName+"-"+note,	operateNum ,operateOrder,operateType);
    	
    	threadPool.execute(new Runnable(){

			@Override
			public void run() {
				if(!(operationLogService.insertOperationLog(operationLog))){
					LOGGER.error("商家中心-快捷发货：记录操作日志报错！日志详情：{}",operationLog);
				}
				
			}
			
		});*/
    	final MerchantCenterOperationLog operationLog = new MerchantCenterOperationLog(request,
				menuName,menuName+"-"+note,	operateNum ,operateOrder,operateType);
    	Runnable run = new Runnable(){
			@Override
			public void run() {
				LOGGER.warn("线程{},开始保存快捷发货操作日志...", Thread.currentThread().getName());
				if(!(operationLogService.insertOperationLog(operationLog))){
					LOGGER.error("商家中心-快捷发货：记录操作日志报错！日志详情：{}",operationLog);
				}
				LOGGER.warn("线程{},结束保存快捷发货操作日志...", Thread.currentThread().getName());
			}
    	};	
    	Thread thread = new Thread(run);
		thread.setName("saveOperationLog 快捷发货保存操作日志");
		thread.start();
    	
	}
}
