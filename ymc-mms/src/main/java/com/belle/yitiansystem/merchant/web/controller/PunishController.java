package com.belle.yitiansystem.merchant.web.controller;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.spring.DataSourceSwitcher;
import com.belle.infrastructure.util.DateUtil;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.yitiansystem.merchant.constant.MerchantConstant;
import com.belle.yitiansystem.merchant.constant.PunishConstant;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog.OperationType;
import com.belle.yitiansystem.merchant.model.pojo.PunishOrder;
import com.belle.yitiansystem.merchant.model.pojo.PunishRule;
import com.belle.yitiansystem.merchant.model.pojo.PunishSettle;
import com.belle.yitiansystem.merchant.model.pojo.ShipmentDaySetting;
import com.belle.yitiansystem.merchant.model.pojo.StockPunishRuleDetail;
import com.belle.yitiansystem.merchant.model.vo.PunishOrderVo;
import com.belle.yitiansystem.merchant.service.IMerchantOperationLogService;
import com.belle.yitiansystem.merchant.service.IMerchantServiceNew;
import com.belle.yitiansystem.merchant.service.IPunishSettleService;
import com.belle.yitiansystem.merchant.service.PunishService;
import com.belle.yitiansystem.merchant.service.ShipmentDaySettingService;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.systemmgmt.util.SysconfigProperties;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
import com.yougou.ordercenter.api.asm.IAsmApi;
import com.yougou.ordercenter.api.order.IOrderApi;
import com.yougou.ordercenter.api.order.IOrderForMerchantApi;
import com.yougou.ordercenter.model.asm.Problem;
import com.yougou.ordercenter.model.order.OrderDetail4sub;
import com.yougou.ordercenter.model.order.OrderSub;
import com.yougou.ordercenter.vo.asm.QueryTraceSaleVo;
import com.yougou.ordercenter.vo.asm.TraceSaleQueryResult;
import com.yougou.ordercenter.vo.order.OrderExceptionVo;
import com.yougou.pc.api.ICommodityBaseApiService;

/**
 * 商家处罚
 * 
 * @author he.wc
 * 
 */
@Controller
@RequestMapping("/yitiansystem/merchants/businessorder")
public class PunishController extends BaseController {

	private final String BASE_PATH = "yitiansystem/merchants/punish/";

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PunishController.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat(PunishConstant.DATE_FORMAT_DATETIME);
	private static final SimpleDateFormat sdfDate = new SimpleDateFormat(PunishConstant.DATE_FORMAT_DATE);
	@Resource
	private IMerchantOperationLogService merchantOperationLogService;

	@Resource
	private PunishService punishService;
	
	@Resource
	private IPunishSettleService punishSettleService;

	@Resource
	private IOrderApi orderApi;

	@Autowired
	private SysconfigProperties sysconfigproperties;
	@Resource
	private ShipmentDaySettingService shipmentDaySettingService;
    @Resource
    private IAsmApi asmApiImpl;
    @Resource
	private ICommodityBaseApiService commodityBaseApiService;
    
    @Resource
    private IOrderForMerchantApi orderForMerchantApi;
    @Resource
	private IMerchantServiceNew merchantServiceNew;
    
	// 数据绑定
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(PunishConstant.DATE_FORMAT_DATETIME);
		//SimpleDateFormat dateFormat = new SimpleDateFormat(PunishConstant.DATE_FORMAT_DATE);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateTimeFormat, true));
		//binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 编辑违规规则
	 * 
	 * @param query
	 * @param modelMap
	 * @param merchantsVo
	 * @param isCanAssign
	 * @return
	 */
	@RequestMapping("to_punishRule")
	public String to_punishRule(ModelMap modelMap, @RequestParam(required = true) String merchantsCode) {
		PunishRule punishRule = punishService.getPunishRuleByMerchantsCode(merchantsCode);
		List<StockPunishRuleDetail> detailList = null;
		if(punishRule!=null){
			detailList = punishService.getStockPunishRuleDetail(punishRule.getId());
		}
		modelMap.addAttribute("detail",detailList);
		modelMap.addAttribute("punishRule", punishRule);
		modelMap.addAttribute("merchantsCode", merchantsCode);
		return BASE_PATH + "update_punish_rule";
	}

	/**
	 * 跳转到违规订单待审核的列表
	 * 
	 * @param modelMap
	 * @param supplierId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("to_punishList")
	public String to_punishList(ModelMap modelMap, PunishOrderVo punishOrderVo, 
			Query query) throws Exception {
		//超时效处罚
		punishOrderVo.setPunishType("1");
		punishOrderVo.setPunishOrderStatus("1");
		modelMap = this.getPunishListModel(punishOrderVo, query, modelMap);
		//return BASE_PATH + "merchants_punish_list";
		modelMap.put("categorys",commodityBaseApiService.getCategoryListByLevel((short)1,(short)1));
		modelMap.addAttribute("merchants",commodityBaseApiService.getMerchantList());
		modelMap.addAttribute("brands", commodityBaseApiService.getAllBrands((short)1));
		return BASE_PATH + "merchants_timeout_punish_list";
	}
	
	
	/**
	 * 跳转到违规订单已审核的列表
	 * 
	 * @param modelMap
	 * @param supplierId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("to_validPunishList")
	public String to_validPunishList(ModelMap modelMap, PunishOrderVo punishOrderVo, 
			Query query) throws Exception {
		//超时效处罚
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		c.set(Calendar.MONTH, month-1);
		if(punishOrderVo.getOrderTimeStart()==null||"".equals(punishOrderVo.getOrderTimeStart())){
			c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
			c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
			c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
			c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
			punishOrderVo.setOrderTimeStart(DateUtil.parseDate(DateUtil.getDateTime(c.getTime()),"yyyy-MM-dd HH:mm:ss"));
		}
		if(punishOrderVo.getOrderTimeEnd()==null||"".equals(punishOrderVo.getOrderTimeEnd())){
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			c.set(Calendar.HOUR_OF_DAY, c.getActualMaximum(Calendar.HOUR_OF_DAY));
			c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
			c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND));
			punishOrderVo.setOrderTimeEnd(DateUtil.parseDate(DateUtil.getDateTime(c.getTime()),"yyyy-MM-dd HH:mm:ss"));
		}
		punishOrderVo.setPunishType("1");
		punishOrderVo.setPunishOrderStatus("2");
		modelMap = this.getPunishListModel(punishOrderVo, query, modelMap);
		//return BASE_PATH + "merchants_punish_list";
		modelMap.put("categorys",commodityBaseApiService.getCategoryListByLevel((short)1,(short)1));
		modelMap.addAttribute("merchants",commodityBaseApiService.getMerchantList());
		modelMap.addAttribute("brands", commodityBaseApiService.getAllBrands((short)1));
		return BASE_PATH + "merchants_timeout_valid_punish_list";
	}
	
	
	@ResponseBody
	@RequestMapping("/getYougouItemCat")
	public String getYougouItemCat(String structName){
		JSONObject result = new JSONObject();
		try {
			result.put("yougouCat",commodityBaseApiService.getChildCategoryByStructName(structName));
		} catch (Exception e) {
			logger.error("获取优购分类异常异常信息：", e);
			result.put("resultCode","500");
			result.put("msg", "系统异常!");
		}
		return result.toString();
	}
	
	/**
	 * 跳转到待审核缺货商品订单列表
	 * 
	 * @param modelMap
	 * @param supplierId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("to_punishValidList")
	public String to_punishValidList(ModelMap modelMap, PunishOrderVo punishOrderVo, 
			Query query) throws Exception {
		//缺货处罚
		PageFinder<Map<String, Object>> pageFinder = punishService.getPunishStockList(punishOrderVo,query);
		modelMap.put("pageFinder", pageFinder);
		modelMap.put("categorys",commodityBaseApiService.getCategoryListByLevel((short)1,(short)1));
		modelMap.addAttribute("merchants",commodityBaseApiService.getMerchantList());
		modelMap.addAttribute("brands", commodityBaseApiService.getAllBrands((short)1));
		modelMap.addAttribute("punishOrderVo", punishOrderVo);
		return BASE_PATH + "merchants_stock_punish_list";
	}
	
	/**
	 * 跳转到已审核缺货商品订单列表
	 * 
	 * @param modelMap
	 * @param supplierId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("to_punishStockValidList")
	public String to_punishStockValidList(ModelMap modelMap, PunishOrderVo punishOrderVo, 
			Query query) throws Exception {
		//缺货处罚
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		c.set(Calendar.MONTH, month-1);
		if(punishOrderVo.getOrderTimeStart()==null||"".equals(punishOrderVo.getOrderTimeStart())){
			c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
			c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
			c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
			c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
			punishOrderVo.setOrderTimeStart(DateUtil.parseDate(DateUtil.getDateTime(c.getTime()),"yyyy-MM-dd HH:mm:ss"));
		}
		if(punishOrderVo.getOrderTimeEnd()==null||"".equals(punishOrderVo.getOrderTimeEnd())){
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			c.set(Calendar.HOUR_OF_DAY, c.getActualMaximum(Calendar.HOUR_OF_DAY));
			c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
			c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND));
			punishOrderVo.setOrderTimeEnd(DateUtil.parseDate(DateUtil.getDateTime(c.getTime()),"yyyy-MM-dd HH:mm:ss"));
		}
		punishOrderVo.setPunishOrderStatus(PunishConstant.ORDER_STATUS_VALID);
		PageFinder<Map<String, Object>> pageFinder = punishService.getPunishValidStockList(punishOrderVo,query);
		if(pageFinder!=null){
			DecimalFormat df = new DecimalFormat("#.##");
			PunishRule punishRule = null;
			//商家编码-》缺货率
			Map<String,Double> queRate = new HashMap<String,Double>();
			//商家编码-》处罚比例
			Map<String,Double> punishRateMap = new HashMap<String,Double>();
			double que = 0.0;
			for(Map<String, Object> map : pageFinder.getData()){
				//罚款比例
				int lackNum = 1;
				double punishRate = 0.0;
				if(queRate.containsKey(MapUtils.getString(map, "supplier_code"))){
					que = queRate.get(MapUtils.getString(map, "supplier_code"));
				}else{
					que = this.getOutStockRate(punishOrderVo.getOrderTimeStart(),
							punishOrderVo.getOrderTimeEnd(),
							MapUtils.getString(map, "supplier_code"));
					queRate.put(MapUtils.getString(map, "supplier_code"), que);
				}
				logger.info("商家编码{}，这段时间{}-{}，缺货率为：{}",
						new Object[]{MapUtils.getString(map, "supplier_code"),
						DateUtil.format(punishOrderVo.getOrderTimeStart(), "yyyy-MM-dd HH:mm:ss"),
						DateUtil.format(punishOrderVo.getOrderTimeEnd(), "yyyy-MM-dd HH:mm:ss"),
						que});
				if(map.get("punish_price")==null || 
						((Number)map.get("punish_price")).doubleValue()!=0.0){
					//人为设置罚款金额为0的不修改罚款金额
					//月缺货率：A% - B%，按商品成交价的C%处罚。
					//查看该商家设置的缺货处罚规则，如果未设置处罚规则，则不罚款；
					//设置了就查询月缺货率所在的范围
					if(punishRateMap.containsKey(MapUtils.getString(map, "supplier_code"))){
						punishRate = punishRateMap.get(MapUtils.getString(map, "supplier_code"));
					}else{
						punishRule = punishService.getPunishRuleByMerchantsCode(MapUtils.getString(map, "supplier_code"));
						if(punishRule!=null){//设置处罚规则
							List<StockPunishRuleDetail> detailList = punishService.getStockPunishRuleDetail(punishRule.getId());
							//罚款金额=商品成交金额*C%
							if(detailList!=null&&detailList.size()>0){
								for(StockPunishRuleDetail detail : detailList){
									if((que*100)>detail.getPunishRateBegin()&&
											(que*100)<=detail.getPunishRateEnd()){
										punishRate = detail.getPunishRule();
										break;
									}
								}
								punishRateMap.put(MapUtils.getString(map, "supplier_code"), punishRate);
							}
						}
					}
				}
				if(map.get("lack_num")!=null){
					lackNum = ((Number)map.get("lack_num")).intValue();
				}
				map.put("punish_price",df.format(((Number)map.get("totalamt")).doubleValue() * (punishRate*0.01) * lackNum));
			}
		}
		modelMap.put("pageFinder", pageFinder);
		modelMap.put("categorys",commodityBaseApiService.getCategoryListByLevel((short)1,(short)1));
		modelMap.addAttribute("merchants",commodityBaseApiService.getMerchantList());
		modelMap.addAttribute("brands", commodityBaseApiService.getAllBrands((short)1));
		return BASE_PATH + "merchants_stock_valid_punish_list";
	}

	/** 
	 * getPunishPrice:在时间范围内求该商家的缺货率 
	 * @author li.n1 
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param merchantCode
	 * @return 
	 * @since JDK 1.6 
	 */  
	private double getOutStockRate(Date orderTimeStart, Date orderTimeEnd,
			String merchantCode) {
		return punishService.getOutStockRate(orderTimeStart,orderTimeEnd,merchantCode);
	}

	/**
	 * 违规订单导出
	 * 
	 * @param modelMap
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("to_exportPunishOrder")
	public ModelAndView to_exportPunishOrder(ModelMap modelMap, 
			PunishOrderVo punishOrderVo) throws Exception {
		//超时效处罚
		List<Map<String, Object>> list = punishService.queryPunishOrderList(punishOrderVo);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, String> orderStatusMap = new HashMap<String, String>();
		Map<String, Long> hourMap = new HashMap<String, Long>();
		for (Map<String, Object> map : list) {
			String subOrderNo = (String) map.get("order_no");
			Long hour;
			String shipmentStatus = map.get("shipment_status").toString();
			Date orderDate = sdf.parse(map.get("order_time").toString());
			// 非发货日天数
			Long noshipdays = Long.valueOf(map.get("noshipdays").toString());
			Long differHour = noshipdays *24; // 非发货时长=非发货天数*24
			OrderSub orderSub = orderApi.getOrderSubByOrderSubNo(subOrderNo, null);
			// 订单状态
			if (orderSub != null) {
				//orderStatusMap.put(subOrderNo, orderSub.getBaseStatusName());订单旧状态
				//订单新状态
				orderStatusMap.put(subOrderNo, orderSub.getOrderStatusName());
			}
			// 超时时长：发货时间 – 下单日期 – 非发货日天数 -商家所设定的时效
			if (shipmentStatus.equals(PunishConstant.SHIPMENT_YES) && map.get("shipment_time") != null) {
				Date shipmentDate = sdf.parse(map.get("shipment_time").toString());			
				hour = (shipmentDate.getTime() - orderDate.getTime()) / 3600000 - differHour;
			} else {
				hour = (new Date().getTime() - orderDate.getTime()) / 3600000 - differHour;
			}
			Long shipmentHour = map.get("shipment_hour") == null ? 0 :Long.valueOf(map.get("shipment_hour").toString());
			hourMap.put(subOrderNo, hour -shipmentHour);

		}

		model.put("list", list);
		model.put("orderStatusMap", orderStatusMap);
		model.put("hourMap", hourMap);
		model.put("punishOrderStatus", punishOrderVo.getPunishOrderStatus());
		return new ModelAndView(new PunishOrderExcelView(), model);
	}
	
	/**
	 * <p>查询分类结构</p>
	 * 
	 * @param queryVo 查询vo
	 * @return 分类结构
	 */
	public static String getCatStructName(PunishOrderVo queryVo) {
		if (StringUtils.isNotBlank(queryVo.getThirdCategory())) {
			return queryVo.getThirdCategory();
		} else if (StringUtils.isNotBlank(queryVo.getSecondCategory())) {
			return queryVo.getSecondCategory();
		} else if (StringUtils.isNotBlank(queryVo.getCategory())
				&& !"0".equals(queryVo.getCategory())) {
			return queryVo.getCategory();
		}
		return null;
	}

	/**
	 * 得到违规订单model
	 * 
	 * @param punishOrderVo
	 * @param query
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public ModelMap getPunishListModel(PunishOrderVo punishOrderVo, Query query, ModelMap modelMap) throws Exception {
		//punishOrderVo.setStructName(getCatStructName(punishOrderVo));
		PageFinder<Map<String, Object>> pageFinder = punishService.queryPunishOrderList(punishOrderVo, query);
		if (pageFinder != null && pageFinder.getData() != null && pageFinder.getData().size() > 0) {
			Map<String, String> orderStatusMap = new HashMap<String, String>();
			Map<String, Long> hourMap = new HashMap<String, Long>();
			for (Map<String, Object> map : pageFinder.getData()) {
				String subOrderNo = (String) map.get("order_no");
				Long hour;
				String shipmentStatus = map.get("shipment_status").toString();
				Date orderDate = sdf.parse(map.get("order_time").toString());
				// 非发货日天数
				Long noshipdays = Long.valueOf(map.get("noshipdays").toString());
				Long differHour = noshipdays *24; // 非发货时长=非发货天数*24
				OrderSub orderSub = orderApi.getOrderSubByOrderSubNo(subOrderNo, null);
				// 订单状态
				if (orderSub != null) {
					//orderStatusMap.put(subOrderNo, orderSub.getBaseStatusName());旧订单状态
					//新订单状态
					orderStatusMap.put(subOrderNo, orderSub.getOrderStatusName());
				}
				
				// 超时时长：发货时间 – 下单日期 – 非发货日天数 -商家所设定的时效
				if (shipmentStatus.equals(PunishConstant.SHIPMENT_YES) && map.get("shipment_time") != null) {
					Date shipmentDate = sdf.parse(map.get("shipment_time").toString());
					hour = (shipmentDate.getTime() - orderDate.getTime()) / 3600000 - differHour;
				} else {
					hour = (new Date().getTime() - orderDate.getTime()) / 3600000 - differHour;
				}
				hourMap.put(subOrderNo, hour - Long.valueOf(map.get("shipment_hour").toString()));

			}
			modelMap.addAttribute("orderStatusMap", orderStatusMap);
			modelMap.addAttribute("overHour", hourMap);
			modelMap.addAttribute("pageFinder", pageFinder);
			modelMap.addAttribute("omsHost", sysconfigproperties.getOmshost());
		}
		modelMap.addAttribute("punishOrderVo", punishOrderVo);
		return modelMap;
	}


	/**
	 * 跳转到添加处罚订单页面
	 * 
	 * @param modelMap
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("to_addPunishOrder")
	public String to_addPunishOrder(ModelMap modelMap, String supplierId) {
		return BASE_PATH + "add_timeout_punish_order";
	}
	
	@RequestMapping("to_addStockPunishOrder")
	public String to_addStockPunishOrder(){
		return BASE_PATH + "add_stock_punish_order";
	}

	/**
	 * 保存处罚规则
	 * 
	 * @param modelMap
	 * @param punishRule
	 * @return
	 */
	@RequestMapping("save_punish_rule")
	@ResponseBody
	public String save_punish_rule(ModelMap modelMap, PunishRule punishRule,String merchantCode,
			HttpServletRequest request) {
		try{
			Enumeration<String> enumParam = request.getParameterNames();
			String paramName = null;
			List<StockPunishRuleDetail> detailList = null;
			StockPunishRuleDetail detail = null;
			boolean flag1 = false;
			if(null!=punishRule && null!=punishRule.getId()){
				if(StringUtils.isNotBlank(request.getParameter("begin_0"))){
					detailList = new ArrayList<StockPunishRuleDetail>();
					while(enumParam.hasMoreElements()){
						paramName = enumParam.nextElement();
						if(paramName.startsWith("begin")&&StringUtils.isNotBlank(request.getParameter(paramName))){
							detail = new StockPunishRuleDetail();
							detail.setPunishRateBegin(Double.parseDouble(request.getParameter(paramName)));
							detail.setPunishRateEnd(Double.parseDouble(request.getParameter("end"+paramName.substring(5))));
							detail.setPunishRule(Double.parseDouble(request.getParameter("cj"+paramName.substring(5))));
							detail.setPunishRuleId(punishRule.getId());
							detail.setCreatTime(new Date());
							detailList.add(detail);
						}
					}
				}
			}
			punishService.saveOrUpdatePunishRule(punishRule);
			//logger.info("新增处罚规则uuid="+punishRule.getId());
			if(null!=detailList){
				if(detailList.size()>0){
					flag1 = punishService.savePunishRuleDetail(punishRule.getId(),detailList);
					//logger.info("新增处罚规则明细信息："+flag1);
				}
			}
			
			// 操作日志
			try {
				if( StringUtils.isNotBlank(merchantCode) ){
					SystemmgtUser user = GetSessionUtil.getSystemUser(request);
					com.yougou.merchant.api.supplier.vo.MerchantOperationLog log = new com.yougou.merchant.api.supplier.vo.MerchantOperationLog();
					log.setType( MerchantConstant.LOG_FOR_MERCHANT );
					log.setOperator(user.getUsername());
					log.setContractNo("");
					log.setOperationNotes("编辑处罚规则");
					log.setOperationType( com.yougou.merchant.api.supplier.vo.MerchantOperationLog.OperationType.PUNISH_RULE.getDescription());
					log.setMerchantCode( merchantCode );
					merchantServiceNew.insertMerchantLog(log);
					
				}else{
					logger.error("给编辑处罚规则操作创建操作日志时，未找到该商家"+merchantCode);
				}
			} catch (Exception e) {
				logger.error("给编辑处罚规则操作创建操作日志失败，商家编码为"+merchantCode);
			}
			//logger.info(String.format("新更或修改处罚订单处罚规则,结果[%s]", punishRule));
			return "true";
		}catch(Exception e){
			logger.error("服务器发生异常：", e);
			return "false";
		}
	}
	
	@Deprecated
	@RequestMapping("/to_submitPunishOrder")
	public String to_submitPunishOrder(ModelMap modelMap, PunishOrderVo punishOrderVo,
			HttpServletRequest request){
		try{
			PunishOrder punishOrder = new PunishOrder();
			MerchantOperationLog operationLog = new MerchantOperationLog();
			String validPerson = GetSessionUtil.getSystemUser(request).getUsername();
			Timestamp curDate = new Timestamp(new Date().getTime());
			String orderNo = punishOrderVo.getOrderNo();
			OrderSub orderSub = orderApi.getOrderSubByOrderSubNo(orderNo, 3);

			punishOrder.setMerchantCode(orderSub.getOrderSubExpand().getMerchantCode());
			if (orderSub.getShipTime() != null) {
				punishOrder.setShipmentTime(new Timestamp(orderSub.getShipTime().getTime()));
				punishOrder.setShipmentStatus(PunishConstant.SHIPMENT_YES);
			} else {
				punishOrder.setShipmentStatus(PunishConstant.SHIPMENT_NO);
			}
			punishOrder.setOrderNo(orderNo);
			punishOrder.setThirdOrderNo(orderSub.getOutOrderId());
			punishOrder.setOrderPrice(orderSub.getTotalPrice());
			punishOrder.setOrderTime(new Timestamp(orderSub.getCreateTime().getTime()));
			punishOrder.setOrderSourceNo(orderSub.getOrderSourceNo());
			punishOrder.setOutShopName(orderSub.getOutShopName());
			punishOrder.setPunishType(punishOrderVo.getPunishType());
			punishOrder.setPunishPrice(punishOrderVo.getPunishPrice());
			//punishOrder.setRemark(punishOrderVo.getRemark());
			if(punishOrderVo.getPunishOrderStatus()==null){
				punishOrderVo.setPunishOrderStatus("1");
				operationLog.setOperationNotes("手动添加");
			}else{
				operationLog.setOperationNotes("审核通过");
			}
			punishOrder.setPunishOrderStatus(punishOrderVo.getPunishOrderStatus());
			punishOrder.setCreateTime(curDate);
			punishOrder.setUpdateTime(curDate);
			punishService.savePunishOrder(punishOrder);
			operationLog.setMerchantCode(punishOrder.getId());
			operationLog.setOperator(validPerson);
			operationLog.setOperated(new Date());
			operationLog.setOperationType(OperationType.PUNISHORDER);
			merchantOperationLogService.saveMerchantOperationLog(operationLog);
			logger.info(String.format("新增处罚订单信息,结果[%s]", punishOrder));
			return "true";
		}catch(Exception e){
			logger.error("新增处罚订单信息发送错误：", e);
			return "false";
		}
	}

	/**
	 * 保存缺货处罚订单
	 * 
	 * @param modelMap
	 * @param punishRule
	 * @return
	 */
	@RequestMapping("save_punishOrder")
	@ResponseBody
	public String save_punishOrder(ModelMap modelMap, PunishOrderVo punishOrderVo,
			String levelCodeStr,
			HttpServletRequest request) {
		try{
			if("0".equals(punishOrderVo.getPunishType())){
				//缺货
				String[] levelCode = levelCodeStr.split(",");
				PunishOrder punishOrder = null;
				String validPerson = GetSessionUtil.getSystemUser(request).getUsername();
				String orderNo = punishOrderVo.getOrderNo();
				OrderSub orderSub = orderApi.getOrderSubByOrderSubNo(orderNo, 4);
				for(int i=0;i<levelCode.length;i++){
					punishOrder = new PunishOrder();
					MerchantOperationLog operationLog = new MerchantOperationLog();
					Timestamp curDate = new Timestamp(new Date().getTime());
					punishOrder.setMerchantCode(orderSub.getOrderSubExpand().getMerchantCode());
					if (orderSub.getShipTime() != null) {
						punishOrder.setShipmentTime(new Timestamp(orderSub.getShipTime().getTime()));
						punishOrder.setShipmentStatus(PunishConstant.SHIPMENT_YES);
					} else {
						punishOrder.setShipmentStatus(PunishConstant.SHIPMENT_NO);
					}
					punishOrder.setOrderNo(orderNo);
					punishOrder.setThirdOrderNo(orderSub.getOutOrderId());
					punishOrder.setOrderPrice(orderSub.getTotalPrice());
					punishOrder.setOrderTime(new Timestamp(orderSub.getCreateTime().getTime()));
					punishOrder.setOrderSourceNo(orderSub.getOrderSourceNo());
					punishOrder.setOutShopName(orderSub.getOutShopName());
					punishOrder.setPunishType("0");
					punishOrder.setPunishPrice(punishOrderVo.getPunishPrice());
					punishOrder.setIsSettle("0");
					punishOrder.setIsSubmit("0");
					punishOrder.setInsideCode(levelCode[i]);
					if(punishOrderVo.getPunishOrderStatus()==null){
						punishOrderVo.setPunishOrderStatus("1");
						operationLog.setOperationNotes("手动添加");
					}else{
						operationLog.setOperationNotes("审核通过");
					}
					punishOrder.setPunishOrderStatus(punishOrderVo.getPunishOrderStatus());
					punishOrder.setCreateTime(curDate);
					punishOrder.setUpdateTime(curDate);
					punishService.savePunishOrder(punishOrder);
					operationLog.setMerchantCode(punishOrder.getId());
					operationLog.setOperator(validPerson);
					operationLog.setOperated(new Date());
					operationLog.setOperationType(OperationType.PUNISHORDER);
					merchantOperationLogService.saveMerchantOperationLog(operationLog);
					logger.info(String.format("新增处罚订单信息,结果[%s]", punishOrder));
				}
				//调用订单提供接口
				List<String> detailIds = new ArrayList<String>();
				for(OrderDetail4sub detail : orderSub.getOrderDetail4subs()){
					if(levelCodeStr.contains(detail.getLevelCode())){
						detailIds.add(detail.getId());
					}
				}
		        OrderExceptionVo vo = new OrderExceptionVo();
		        vo.setOperateUser(validPerson);
		        vo.setOrderCode(orderNo);
		        vo.setOrderDetailIds(detailIds);
		        vo.setStatus(com.yougou.ordercenter.constant.OrderConstant.EXCEPTION_WAREHOUSE_ERROR);
				orderForMerchantApi.orderExceptionForMerchant(Arrays.asList(vo), 
								orderSub.getOrderSubExpand().getMerchantCode());
				return "true";
			}else{
				//超时效
				PunishOrder punishOrder = new PunishOrder();
				MerchantOperationLog operationLog = new MerchantOperationLog();
				String validPerson = GetSessionUtil.getSystemUser(request).getUsername();
				Timestamp curDate = new Timestamp(new Date().getTime());
				String orderNo = punishOrderVo.getOrderNo();
				OrderSub orderSub = orderApi.getOrderSubByOrderSubNo(orderNo, 3);

				punishOrder.setMerchantCode(orderSub.getOrderSubExpand().getMerchantCode());
				if (orderSub.getShipTime() != null) {
					punishOrder.setShipmentTime(new Timestamp(orderSub.getShipTime().getTime()));
					punishOrder.setShipmentStatus(PunishConstant.SHIPMENT_YES);
				} else {
					punishOrder.setShipmentStatus(PunishConstant.SHIPMENT_NO);
				}
				punishOrder.setOrderNo(orderNo);
				punishOrder.setThirdOrderNo(orderSub.getOutOrderId());
				punishOrder.setOrderPrice(orderSub.getTotalPrice());
				punishOrder.setOrderTime(new Timestamp(orderSub.getCreateTime().getTime()));
				punishOrder.setOrderSourceNo(orderSub.getOrderSourceNo());
				punishOrder.setOutShopName(orderSub.getOutShopName());
				punishOrder.setPunishType("1");
				punishOrder.setPunishPrice(punishOrderVo.getPunishPrice());
				punishOrder.setIsSettle("0");
				punishOrder.setIsSubmit("0");
				if(punishOrderVo.getPunishOrderStatus()==null){
					punishOrderVo.setPunishOrderStatus("1");
					operationLog.setOperationNotes("手动添加");
				}else{
					operationLog.setOperationNotes("审核通过");
				}
				punishOrder.setPunishOrderStatus(punishOrderVo.getPunishOrderStatus());
				punishOrder.setCreateTime(curDate);
				punishOrder.setUpdateTime(curDate);
				punishService.savePunishOrder(punishOrder);
				operationLog.setMerchantCode(punishOrder.getId());
				operationLog.setOperator(validPerson);
				operationLog.setOperated(new Date());
				operationLog.setOperationType(OperationType.PUNISHORDER);
				merchantOperationLogService.saveMerchantOperationLog(operationLog);
				logger.info(String.format("新增处罚订单信息,结果[%s]", punishOrder));
				return "true";
			}
		}catch(Exception e){
			logger.error("新增处罚订单信息发送错误：", e);
			return "false";
		}
	}
	

	/**
	 * 更新处罚订单价格
	 * 
	 * @param modelMap
	 * @param id
	 * @param price
	 * @return
	 */
	@RequestMapping("update_PunishOrderPrice")
	@ResponseBody
	public String updatePunishOrderPrice(ModelMap modelMap, @RequestParam(required = true) String id,
			@RequestParam(required = true) Double price, String type) {
		if(type!=null&&"0".equals(type)){
			//缺货，只能对0保存
			if(price==0){
				punishService.updatePunishOrderPrice(id, price);
				logger.info(String.format("更新处罚订单价格，处罚订id[%s],修改后价格[%f]", id, price));
			}
		}else{
			punishService.updatePunishOrderPrice(id, price);
			logger.info(String.format("更新处罚订单价格，处罚订id[%s],修改后价格[%f]", id, price));
		}
		return "true";
	}

	/**
	 * 审核处罚订单
	 * 
	 * @param modelMap
	 * @param id
	 * @param validStatus
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("valid_punish_order")
	@ResponseBody
	public String valid_punish_order(ModelMap modelMap, @RequestParam(required = true) String[] ids,
			String validStatus, HttpServletRequest request) throws Exception {
		String validPerson = GetSessionUtil.getSystemUser(request).getUsername();
		try{
			MerchantOperationLog operationLog = null;
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				punishService.vaildPunishOrder(id, validStatus, validPerson);
				operationLog = new MerchantOperationLog();
				operationLog.setMerchantCode(id);
        		operationLog.setOperator(validPerson);
        		operationLog.setOperated(new Date());
				if("1".equals(validStatus)){
					operationLog.setOperationNotes("取消审核通过");
				}else if("2".equals(validStatus)){
	        		operationLog.setOperationNotes("审核通过");
				}
				operationLog.setOperationType(OperationType.PUNISHORDER);
				merchantOperationLogService.saveMerchantOperationLog(operationLog);
				logger.info(String.format("操作处罚订单，处罚订id[%s]，操作[%s]，操作员[%s]", id, validStatus, validPerson));
			}
		}catch(Exception e){
			logger.error("服务器发生异常：", e);
			return "false";
		}
		return "true";
	}
	
	/**
	 * valid_stockpunish_order:审核缺货商品
	 * @author li.n1 
	 * @param modelMap
	 * @param ids
	 * @param validStatus
	 * @param request
	 * @return 
	 * @since JDK 1.6
	 */
	@RequestMapping("/valid_stockpunish_order")
	@ResponseBody
	public String valid_stockpunish_order(ModelMap modelMap, @RequestParam(required = true) String[] ids,
			String validStatus, String[] punishId,String[] merchantCode,HttpServletRequest request){
		String validPerson = GetSessionUtil.getSystemUser(request).getUsername();
		try{
			MerchantOperationLog operationLog = null;
			String tempPunishId = null;
			for (int i = 0; i < ids.length; i++) {
				tempPunishId = punishService.vaildStockPunishOrder(ids[i], validStatus, validPerson, punishId[i],merchantCode[i]);
				operationLog = new MerchantOperationLog();
				operationLog.setMerchantCode(tempPunishId);
        		operationLog.setOperator(validPerson);
        		operationLog.setOperated(new Date());
				if("1".equals(validStatus)){
					operationLog.setOperationNotes("取消审核通过");
				}else if("2".equals(validStatus)){
	        		operationLog.setOperationNotes("审核通过");
				}
				operationLog.setOperationType(OperationType.PUNISHORDER);
				merchantOperationLogService.saveMerchantOperationLog(operationLog);
				logger.info(String.format("操作处罚订单，处罚订单明细id[%s]，操作[%s]，操作员[%s]", ids[i], validStatus, validPerson));
			}
		}catch(Exception e){
			logger.error("服务器发生异常：", e);
			return "false";
		}
		return "true";
	}
	
	@ResponseBody
	@RequestMapping("/submitAmount")
	public String submitAmount(PunishSettle settle,
			HttpServletRequest request){
		JsonObject object = new JsonObject();
		String validPerson = GetSessionUtil.getSystemUser(request).getUsername();
		try{
			if(DateUtil.format(settle.getSettleStart(), "yyyy-MM")
					.equals(DateUtil.format(settle.getSettleEnd(), "yyyy-MM"))){
				if(DateUtil.format(settle.getSettleStart(),"yyyy-MM")
						.equals(DateUtil.format(new Date(),"yyyy-MM"))){
					//不能统计本月的罚款金额
					object.addProperty("result", "error");
					object.addProperty("msg", "本月罚款金额需到下月才可以统计！");
				}else{
					settle.setRegistrant(validPerson);
					settle.setRegistTime(new Date());
					settle.setRegistNum("DJ"+com.yougou.tools.common.utils.DateUtil.getCurrentDay("yyyyMMdd")
							+String.valueOf(System.currentTimeMillis()).substring(9));
					settle.setStatus("0");
					settle.setDeleteFlag("0");
					punishSettleService.addPunishSettle(settle);
					logger.info("提交处罚到违规结算订单列表，结算起始时间:{}，结算结束时间：{}，商家编码：{}，处罚总金额：{}，提交订单数：{}，操作员：{}",
							new Object[]{settle.getSettleStart(),settle.getSettleEnd(),
							settle.getSupplierCode(),settle.getDeductMoney(),
							settle.getSettleOrderNum(),settle.getRegistrant()});
					//设置已提交状态，绑定tbl_sp_supplier_punish_settle表的Id
					boolean flag = punishService.updatePunishOrderStatus(DateUtil.format(settle.getSettleStart(), "yyyy-MM-dd HH:mm:ss"),
							DateUtil.format(settle.getSettleEnd(), "yyyy-MM-dd HH:mm:ss"),
							settle.getSupplierCode(),settle.getDeductType(),settle.getId());
					logger.warn("提交违规订单到列表成功状态：{}",flag);
					object.addProperty("result", "success");
				}
			}else{
				object.addProperty("result", "error");
				object.addProperty("msg", "统计罚款金额需下单时间属于同一个年月份！");
			}
		}catch(Exception e){
			object.addProperty("result", "error");
			object.addProperty("msg", "提交失败，服务器发生错误！");
			logger.error("服务器发生错误：", e);
		}
		return object.toString();
	}
	
	@ResponseBody
	@RequestMapping("/getTotalAmount.sc")
	public String getTotalAmount(PunishOrderVo vo){
		Gson gson = new Gson();
		Map<String,Object> map = null ;
		if("0".equals(vo.getPunishType())){
			//缺货
			//根据条件动态求出该月缺货率
			//如果月份不在同一个月，缺货率算第一个时间所在月的缺货率
			//如果两个时间如：12月1日、12月2日，按一个月的缺货率统计罚款，会有误差，最好是结算日期为月初与月末两个时间
			//第一个时间所在月份的最后一天的时间
			Calendar c = Calendar.getInstance();
			c.setTime(vo.getOrderTimeStart());
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			c.set(Calendar.HOUR_OF_DAY, c.getActualMaximum(Calendar.HOUR_OF_DAY));
			c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
			c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND));
			//第一个时间所在月份的开始一天的时间
			Calendar cs = Calendar.getInstance();
			cs.setTime(vo.getOrderTimeStart());
			cs.set(Calendar.DAY_OF_MONTH, cs.getActualMinimum(Calendar.DAY_OF_MONTH));
			cs.set(Calendar.HOUR_OF_DAY, cs.getActualMinimum(Calendar.HOUR_OF_DAY));
			cs.set(Calendar.MINUTE, cs.getActualMinimum(Calendar.MINUTE));
			cs.set(Calendar.SECOND, cs.getActualMinimum(Calendar.SECOND));
			double que = this.getOutStockRate(
					//vo.getOrderTimeStart(), 
					cs.getTime(),
					//vo.getOrderTimeEnd(), 
					c.getTime(),
					vo.getMerchantCode());
			PunishRule punishRule = punishService.getPunishRuleByMerchantsCode(vo.getMerchantCode());
			double punishRate = 0.0;
			if(punishRule!=null){//设置处罚规则
				List<StockPunishRuleDetail> detailList = punishService.getStockPunishRuleDetail(punishRule.getId());
				//罚款金额=商品成交金额*C%
				if(detailList!=null&&detailList.size()>0){
					for(StockPunishRuleDetail detail : detailList){
						if((que*100)>detail.getPunishRateBegin()&&
								(que*100)<=detail.getPunishRateEnd()){
							punishRate = detail.getPunishRule();
							break;
						}
					}
				}
			}
			//还是按客户端传来的时间去查这段时间内的缺货数量
			map = punishService.getPunishValidAndNoSubmitStockList(vo,punishRate);
			if(map.get("countamount")==null || map.get("countrow")==null){
					map.put("countamount", "0");
					map.put("countrow", "0");
			}
		}else{
			//超时效
			map = punishService.countAmount(
					DateUtil.formatDateByFormat(vo.getOrderTimeStart(), "yyyy-MM-dd HH:mm:ss"),
					DateUtil.formatDateByFormat(vo.getOrderTimeEnd(), "yyyy-MM-dd HH:mm:ss"),
					vo.getMerchantCode(),
					vo.getPunishType());
			if(map.get("countamount")==null || map.get("countrow")==null){
				map.put("countamount", "0");
				map.put("countrow", "0");
			}
		}
		return gson.toJson(map);
	}

	/**
	 * 订单号合法性检查
	 * 
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("check_orderNo")
	@ResponseBody
	public String checkOrderNo(String orderNo,String type) {
		// 检查订单号是否存存，或者是不是商家商品订单
		OrderSub orderSub = new OrderSub();
		try {
			orderSub = orderApi.getOrderSubByOrderSubNo(orderNo, 4);
		} catch (Exception e) {
			logger.error("调用订单API失败", e);
			return "false";
		}

		if (orderSub == null || 
				orderSub.getOrderSubExpand() == null || 
				StringUtils.isBlank(orderSub.getOrderSubExpand().getMerchantCode())) {
			logger.info(String.format("订单号合法性检查,订单号[%s]不是商家订单", orderNo));
			return "false";
		}
		// 该订单号是否在处罚订单上有重复。
		List<PunishOrder> punishOrder = null;
		if("0".equals(type)){
			//如果缺货,到冗余表查询最保险
			punishOrder = punishService.getPunishOutOfStockByOrderNo(orderNo);
			//缺货
			List<String> insideStr = new ArrayList<String>();
			if(punishOrder!=null){
				for(PunishOrder order : punishOrder){
					insideStr.add(order.getInsideCode());
				}
			}
			StringBuilder detailSb = new StringBuilder();
			for(OrderDetail4sub detail : orderSub.getOrderDetail4subs()){
				detailSb.append(detail.getLevelCode()+",");
			}
			String remaining = detailSb.toString();
			for(String str : insideStr){
				if(detailSb.toString().contains(str)){
					remaining = remaining.replaceFirst(str+",", "");
				}
			}
			if(remaining.length()<=0){
				logger.info(String.format("订单号合法性检查,订单号[%s]已存在", orderNo));
				return "false";
			}else{
				logger.info(String.format("订单号[%s]，还剩下货品条码为[%s]未缺货", orderNo,remaining));
			}
		}else{
			punishOrder = punishService.getPunishOrderByOrderNo(orderNo);
			if (punishOrder!=null && punishOrder.size()>0) {
				logger.info(String.format("订单号合法性检查,订单号[%s]已存在", orderNo));
				return "false";
			}
		}
		return "true";
	}
	
	/**
	 * 商家发货日期设置
	 * @param modelMap
	 * @param year
	 * @param isView true为只读
	 * @return
	 */
	@RequestMapping("to_shipmentDayList")
	public String toShipmentDayList(ModelMap modelMap,Integer year,String isView){
		
		Calendar cal = Calendar.getInstance();
		Integer curYear = cal.get(Calendar.YEAR);
		Map<String,List<ShipmentDaySetting>> map = new HashMap<String, List<ShipmentDaySetting>>();
		if(year == null || year < curYear ){
			year = curYear;
		}
		if(year > curYear + 2){
			year = year - 1;
		}
		for(int i = 1; i <= 12; i++){
			List<ShipmentDaySetting> list = shipmentDaySettingService.findShipmentDaySettingByYearAndMonth(year, i);
			String key = year.toString() + i;
			map.put(key, list);
		}
		modelMap.put("dateMap", map);
		modelMap.put("year", year);
		modelMap.put("now", cal.getTime());
		modelMap.put("isView", isView);
		return BASE_PATH + "shipment_day_list";
	}

	/**
	 * 设置为发货日/非发货日
	 * @param modelMap
	 * @param id
	 * @param isShipmentDay
	 * @return
	 */
	@ResponseBody
	@RequestMapping("save_shipmentDay")
	public String save_shipmentDay(HttpServletRequest request,ModelMap modelMap,String idStr,String isShipmentDayStr){
		DataSourceSwitcher.setMaster();
		Gson gson = new Gson();
		String[] ids = gson.fromJson(idStr, String[].class);
		String[] isShipmentDays = gson.fromJson(isShipmentDayStr, String[].class);
		if(ids != null){
			for(int i = 0;i < ids.length ;i++){
				String id = ids[i];
				String isShipmentDay = isShipmentDays[i];
				ShipmentDaySetting entity = shipmentDaySettingService.findShipmentDaySettingById(id);
				if(!StringUtils.equals(isShipmentDay, entity.getIsShipmentDay())){
					entity.setIsShipmentDay(isShipmentDay);
					entity.setUpdateTime(new Date());
					String operator = GetSessionUtil.getSystemUser(request).getUsername();
					shipmentDaySettingService.update(entity);
					String dayMsg = "非发货日";
					if(StringUtils.equals(isShipmentDay, "1")){
						dayMsg = "发货日";
					}
					logger.info(String.format("%s 把  %s 设置为  %s", operator,sdfDate.format(entity.getDate()),dayMsg ));
				}
			}
		}
		
		DataSourceSwitcher.clearDataSource();
		return "true";
	}
	
    /**
     * 工单列表查询
     * @param modelMap
     * @param id
     * @param isShipmentDay
     * @return
     */
    @RequestMapping("dialogList")
    public String dialogList(ModelMap model, QueryTraceSaleVo vo, com.yougou.ordercenter.common.Query query, HttpServletRequest request) {
        
        query.setPageSize(20);
        // 去掉OrderSubNo前后带的空格
        if (StringUtils.isNotEmpty(vo.getOrderSubNo())) {
            vo.setOrderSubNo(StringUtils.deleteWhitespace(vo.getOrderSubNo()));
        }else{
            vo.setOrderSubNo(null);
        }
        // 去掉OrderSubNo前后带的空格
        if (StringUtils.isNotEmpty(vo.getOrderTraceNo())) {
            vo.setOrderTraceNo(StringUtils.deleteWhitespace(vo.getOrderTraceNo()));
        }else{
            vo.setOrderTraceNo(null);
        }
        if(StringUtils.isEmpty(vo.getMerchantCode())){
            vo.setMerchantCode(null);
        }
        if(StringUtils.isEmpty(vo.getProblemId())){
            vo.setProblemId(null);
            vo.setSecondProblemId(null);
        }
        if (null != vo.getSecondProblemId() && vo.getSecondProblemId().equals("-1")) {
            vo.setSecondProblemId(null);
        }
        com.yougou.ordercenter.common.PageFinder<TraceSaleQueryResult> pageFinder = null;
        List<Problem> problem_list = null;
        List<Problem> secondeProblemList = null;
        try {
            // 默认设置查询起止时间
            if (StringUtils.isEmpty(vo.getStartTime())) {
                vo.setStartTime(DateUtil.format1(DateUtils.addDays(new Date(), -30)));
            }
            if (StringUtils.isEmpty(vo.getEndTime())) {
                vo.setEndTime(DateUtil.format1(new Date()));
            }
            // 查询问题类型的键值对
            problem_list = asmApiImpl.getMerchantProblemList();
            if(StringUtils.isNotEmpty(vo.getProblemId())){
            	secondeProblemList = asmApiImpl.getSecondProblemListByParentId(vo.getProblemId());
            }
            pageFinder = asmApiImpl.getTraceSaleQueryResults(vo, query);
        } catch (Exception e) {
            logger.error("查询工单列表时产生异常:", e);
        }
        model.addAttribute("problem", problem_list);
        model.addAttribute("problemList", secondeProblemList);
        model.addAttribute("vo", vo);
        model.addAttribute("pageFinder", pageFinder);
        model.addAttribute("omsHost", sysconfigproperties.getOmshost());
        return BASE_PATH + "merchants_dialog_list";
    }
    
    /**
     * 查询问题归属二级列表
     * @param modelMap
     * @param id
     * @param isShipmentDay
     * @return
     */
    @ResponseBody
    @RequestMapping("dialogList_SecondProblemList")
    public String getSecondProblemListByParentId(ModelMap model, QueryTraceSaleVo vo,HttpServletRequest request) {
        List<Problem> problemList = asmApiImpl.getSecondProblemListByParentId(vo.getProblemId());
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(problemList);
        jsonObj.put("problemList", jsonArray);
        return jsonObj.toString();

    }
    
    @RequestMapping("/viewTimeoutPunishLog")
    public String viewLog(ModelMap model,@RequestParam String merchantCode, Query query) throws Exception{
    	com.belle.infrastructure.orm.basedao.PageFinder<MerchantOperationLog> pageFinder = 
    			merchantOperationLogService.queryMerchantOperationLogByOperationType(merchantCode, 
    					OperationType.PUNISHORDER, query);
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("merchantCode",merchantCode);
		return BASE_PATH + "timeout_punish_log";
    }
    
    @ResponseBody
    @RequestMapping("/getOrderDetailByOrderNo")
    public String getOrderDetailByOrderNo(@RequestParam String orderNo){
    	// 该订单号是否在处罚订单上有重复。
    	//List<PunishOrder> punishOrder = punishService.getPunishOrderByOrderNo(orderNo);
    	//if(punishOrder==null || (punishOrder!=null&&punishOrder.size()==0)){
    		//如果为空，缺货再次到冗余表查询
    	List<PunishOrder> punishOrder = punishService.getPunishOutOfStockByOrderNo(orderNo);
    	//}
    	StringBuilder insideCodeSb = new StringBuilder();
    	if(punishOrder!=null){
	    	for(PunishOrder order : punishOrder){
	    		insideCodeSb.append(order.getInsideCode()+",");
			}
    	}
    	//List<OrderDetail4sub> details = 
    	//		orderForMerchantApi.findOrderDetailForOrderSub(null,orderNo,null,null);
    	OrderSub orderSub = orderApi.getOrderSubByOrderSubNo(orderNo, 1);
    	Gson gson = new Gson();
    	JsonObject object = null;
    	JsonArray array = new JsonArray();
    	for(OrderDetail4sub detail : orderSub.getOrderDetail4subs()){
    		if(!(insideCodeSb.toString().
    				contains(detail.getLevelCode()))){
    			//for(int i=0;i<detail.getCommodityNum();i++){
	    			object = new JsonObject();
	        		object.addProperty("orderNo", orderNo);
	        		object.addProperty("levelCode", detail.getLevelCode());
	        		object.addProperty("prodName",detail.getProdName());
	        		object.addProperty("prodTotalAmt", detail.getProdTotalAmt() + detail.getPostageCost());
	        		object.addProperty("commNum", detail.getCommodityNum());
	        		array.add(object);
    			//}
    		}
    	}
    	return gson.toJson(array);
    }
    
    /**
     * to_exportOutOfStock:导出缺货商品
     * @author li.n1 
     * @param punishOrderVo
     * @return 
     * @since JDK 1.6
     */
    @RequestMapping("/to_exportOutOfStock")
    public ModelAndView to_exportOutOfStock(ModelMap model,PunishOrderVo punishOrderVo){
    	model.put("punishOrderStatus", punishOrderVo.getPunishOrderStatus());
    	List<Map<String, Object>> list  = null;
    	if("2".equals(punishOrderVo.getPunishOrderStatus())){
    		//已审核
    		//缺货处罚
    		DecimalFormat df = new DecimalFormat("#.##");
    		Calendar c = Calendar.getInstance();
    		int month = c.get(Calendar.MONTH);
    		c.set(Calendar.MONTH, month-1);
    		if(punishOrderVo.getOrderTimeStart()==null||"".equals(punishOrderVo.getOrderTimeStart())){
    			c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
    			c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
    			c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
    			c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
    			punishOrderVo.setOrderTimeStart(DateUtil.parseDate(DateUtil.getDateTime(c.getTime()),"yyyy-MM-dd HH:mm:ss"));
    		}
    		if(punishOrderVo.getOrderTimeEnd()==null||"".equals(punishOrderVo.getOrderTimeEnd())){
    			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
    			c.set(Calendar.HOUR_OF_DAY, c.getActualMaximum(Calendar.HOUR_OF_DAY));
    			c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
    			c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND));
    			punishOrderVo.setOrderTimeEnd(DateUtil.parseDate(DateUtil.getDateTime(c.getTime()),"yyyy-MM-dd HH:mm:ss"));
    		}
    		list = punishService.getValidPunishOutOfStockList(punishOrderVo);
    		//算罚款金额
    		//商家编码-》缺货率
			Map<String,Double> queRate = new HashMap<String,Double>();
			//商家编码-》处罚比例
			Map<String,Double> punishRateMap = new HashMap<String,Double>();
			double que = 0.0;
			PunishRule punishRule = null;
    		for(Map<String, Object> map : list){
    			//罚款比例
    			int lackNum = 1;
				double punishRate = 0.0;
				if(queRate.containsKey(MapUtils.getString(map, "supplier_code"))){
					que = queRate.get(MapUtils.getString(map, "supplier_code"));
				}else{
					que = this.getOutStockRate(punishOrderVo.getOrderTimeStart(),
							punishOrderVo.getOrderTimeEnd(),
							MapUtils.getString(map, "supplier_code"));
					queRate.put(MapUtils.getString(map, "supplier_code"), que);
				}
				logger.info("商家编码{}，这段时间{}-{}，缺货率为：{}",
						new Object[]{MapUtils.getString(map, "supplier_code"),
						DateUtil.format(punishOrderVo.getOrderTimeStart(), "yyyy-MM-dd HH:mm:ss"),
						DateUtil.format(punishOrderVo.getOrderTimeEnd(), "yyyy-MM-dd HH:mm:ss"),
						que});
				if(map.get("punish_price")==null || 
						((Number)map.get("punish_price")).doubleValue()!=0.0){
					//人为设置罚款金额为0的不修改罚款金额
					//月缺货率：A% - B%，按商品成交价的C%处罚。
					//查看该商家设置的缺货处罚规则，如果未设置处罚规则，则不罚款；
					//设置了就查询月缺货率所在的范围
					if(punishRateMap.containsKey(MapUtils.getString(map, "supplier_code"))){
						punishRate = punishRateMap.get(MapUtils.getString(map, "supplier_code"));
					}else{
						punishRule = punishService.getPunishRuleByMerchantsCode(MapUtils.getString(map, "supplier_code"));
						if(punishRule!=null){//设置处罚规则
							List<StockPunishRuleDetail> detailList = punishService.getStockPunishRuleDetail(punishRule.getId());
							//罚款金额=商品成交金额*C%
							if(detailList!=null&&detailList.size()>0){
								for(StockPunishRuleDetail detail : detailList){
									if((que*100)>detail.getPunishRateBegin()&&
											(que*100)<=detail.getPunishRateEnd()){
										punishRate = detail.getPunishRule();
										break;
									}
								}
								punishRateMap.put(MapUtils.getString(map, "supplier_code"), punishRate);
							}
						}
					}
				}
				if(map.get("lack_num")!=null){
					lackNum = ((Number)map.get("lack_num")).intValue();
				}
				map.put("punish_price",df.format(((Number)map.get("totalamt")).doubleValue() * (punishRate*0.01) * lackNum));
    		}
    	}else{
    		//待审核
    		list = punishService.getPunishOutOfStockList(punishOrderVo);
    	}
    	model.put("list", list);
    	return new ModelAndView(new PunishOutOfStockOrderExcelView(), model);
    }
}
