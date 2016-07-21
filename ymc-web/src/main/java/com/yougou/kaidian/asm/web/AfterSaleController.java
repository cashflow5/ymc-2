package com.yougou.kaidian.asm.web;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yougou.kaidian.asm.vo.OrderTraceSaleVo;
import com.yougou.kaidian.asm.vo.QueryAfterSaleVo;
import com.yougou.kaidian.common.util.DateUtil;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.user.constant.UserConstant;
import com.yougou.kaidian.user.model.pojo.MerchantCenterOperationLog;
import com.yougou.kaidian.user.service.IMerchantCenterOperationLogService;
import com.yougou.kaidian.user.util.UserUtil;
import com.yougou.ordercenter.api.asm.IAsmApi;
import com.yougou.ordercenter.api.order.IOrderApi;
import com.yougou.ordercenter.api.order.IOrderForMerchantApi;
import com.yougou.ordercenter.common.PageFinder;
import com.yougou.ordercenter.model.asm.Problem;
import com.yougou.ordercenter.model.asm.SaleApplyBill;
import com.yougou.ordercenter.model.asm.SaleCancelGoodsInfo;
import com.yougou.ordercenter.model.order.OrderConsigneeInfo;
import com.yougou.ordercenter.model.order.OrderDetail4sub;
import com.yougou.ordercenter.model.order.OrderLog;
import com.yougou.ordercenter.model.order.OrderSaleTrace;
import com.yougou.ordercenter.model.order.OrderSaleTraceProc;
import com.yougou.ordercenter.model.order.OrderSub;
import com.yougou.ordercenter.vo.asm.QuerySaleVo;
import com.yougou.ordercenter.vo.asm.QueryTraceSaleVo;
import com.yougou.ordercenter.vo.asm.SaleApplyBillVo;
import com.yougou.ordercenter.vo.asm.TraceSaleQueryResult;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.product.Product;
import com.yougou.wms.wpi.returnqaproduct.service.IReturnQaProductDetailDomainService;

/**
 * 
 * @author huang.tao
 * 
 */
@Controller
@RequestMapping("/afterSale")
public class AfterSaleController {
    @Resource
    @Qualifier("commodityBaseApiService")
    private ICommodityBaseApiService commodityBaseApiService;
  
    @Resource
    @Qualifier("returnQaProductDetailDomainService")
    private IReturnQaProductDetailDomainService returnQaDetailApi;
    @Resource
    private IOrderForMerchantApi orderForMerchantApi;
    final static String AFTER_SALE_DETAIL_URL = "/manage/asm/after_sale_detail";
    @Resource
    private IAsmApi asmApiImpl;
    @Resource
    private IOrderApi orderApi;
    /**
     * 日志对象
     */
    private final Logger logger = LoggerFactory.getLogger(AfterSaleController.class);
    @Resource
	private RedisTemplate<String, Object> redisTemplate;
    
    @Resource
    private IMerchantCenterOperationLogService operationLogService;
    /** 默认查询的订单天数为30天内 **/
    private static final int QUERY_DATE_RANGE = 30;
    /**
     * 售后单查询
     * 
     * @param model
     * @param vo
     * @param query
     * @param request
     * @return
     */
    @RequestMapping("/queryAfterSaleList")
    public String queryAfterSaleList(ModelMap model, QueryAfterSaleVo vo, Query query, HttpServletRequest request) {
        vo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
        model.addAttribute("vo", vo);
        model.addAttribute("pageFinder", null);
        model.addAttribute("isfirst", "true");
        return "/manage/asm/after_sale_list";
    }
    
    @SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping("/queryAfterSaleCount")
    public String queryAfterSaleCount(ModelMap model,HttpServletRequest request){
    	String merchantCode = YmcThreadLocalHolder.getMerchantCode();
    	JSONObject object = new JSONObject();
    	Map<String,Integer> map = null;
    	QuerySaleVo vo = new QuerySaleVo();
    	vo.setStatus("SALE_COMFIRM");
    	vo.setMerchantCode(merchantCode);
    	map = (Map<String,Integer>)redisTemplate.opsForValue().get(
				CacheConstant.C_AFTERSALE_REMIND_KEY + ":" +merchantCode);
    	if(MapUtils.isNotEmpty(map)){
    		object.putAll(map);
    		return object.toString();
    	}
    	Integer count = asmApiImpl.querySaleOrderCountByMerchantCode(vo);
    	if(count!=null){
    		map = new HashMap<String,Integer>();
    		map.put("asmCount", count);
    		// 加入redisTemplate
    		redisTemplate.opsForValue().set(
    				CacheConstant.C_AFTERSALE_REMIND_KEY + ":" +merchantCode, map);
    		redisTemplate.expire(CacheConstant.C_AFTERSALE_REMIND_KEY + ":" +merchantCode, 3, TimeUnit.MINUTES);
    		object.putAll(map);
    	}
    	return object.toString();
    }
    
    /**
     * queryAfterSale:待处理售后申请
     * @author li.n1 
     * @param model
     * @param vo
     * @param query
     * @param request
     * @return
     * @throws Exception 
     * @since JDK 1.6
     */
    @RequestMapping("/queryAfterSale")
    public String queryAfterSale(ModelMap model, QueryAfterSaleVo vo, 
    		Query query, HttpServletRequest request) throws Exception{
    	vo.setStatus("SALE_COMFIRM");
        return to_queryAfterSaleList(model,vo,query,request);
    }

    /**
     * 售后单查询
     * 
     * @param model
     * @param vo
     * @param query
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/to_queryAfterSaleList")
    public String to_queryAfterSaleList(ModelMap model, QueryAfterSaleVo vo, Query query, HttpServletRequest request) throws Exception {
        vo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
        // PageFinder<Map<String, Object>> pageFinder =
        // afterSaleService.queryAfterSaleByVo(vo, query);
        // 分页对象
        com.yougou.ordercenter.common.Query _query = new com.yougou.ordercenter.common.Query();
        _query.setPage(query.getPage());
        _query.setPageSize(query.getPageSize());
        QuerySaleVo _vo = new QuerySaleVo();
        _vo.setMerchantCode(vo.getMerchantCode());
        _vo.setOrderNo(vo.getOrderSubNo());
        _vo.setOutOrderId(vo.getOutOrderId());
        _vo.setUserName(vo.getConsignee());
        _vo.setMobilePhone(vo.getMobilePhone());
        _vo.setSaleType(vo.getSaleType());
        _vo.setStatus(vo.getStatus());
        _vo.setApplyNo(vo.getApplyNo());
        _vo.setExpressNo(vo.getExpressNo());
        if (StringUtils.isNotBlank(vo.getApplyTimeStart())) {
            _vo.setCreateTimeStart(vo.getApplyTimeStart() + " 00:00:00");
        }
        if (StringUtils.isNotBlank(vo.getApplyTimeEnd())) {
            _vo.setCreateTimeEnd(vo.getApplyTimeEnd() + " 23:59:59");
        }
        logger.info("to_queryAfterSaleList 获取售后信息，传入参数：{}", _vo.toString());	
        com.yougou.ordercenter.common.PageFinder<SaleApplyBillVo> pageFinder = asmApiImpl.querySaleOrderByMerchantCode(_query, _vo);
        logger.info("to_queryAfterSaleList 获取售后信息，输出数据{}条：", (pageFinder==null||pageFinder.getRowCount()<=0)?"":pageFinder.getData().size());
        OrderSub order = null;
        if (null != pageFinder && CollectionUtils.isNotEmpty(pageFinder.getData())) {
            for (SaleApplyBillVo saleApplyBillVo : pageFinder.getData()) {
            	logger.info("to_queryAfterSaleList 调用订单接口，根据子订单号和商家编码获取订单详情信息，传入参数订单号:{}商家编码:{}", saleApplyBillVo.getOrderSubNo(), vo.getMerchantCode());	
            	order = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(saleApplyBillVo.getOrderSubNo(), vo.getMerchantCode());
                saleApplyBillVo.setUserName(order.getOrderConsigneeInfo().getUserName());
                saleApplyBillVo.setOrderStatus(order.getOrderStatus());
            }
        }
        model.addAttribute("vo", vo);
        model.addAttribute("pageFinder", pageFinder);
        model.addAttribute("isfirst", "false");
        
        return "/manage/asm/after_sale_list";
    }

    @RequestMapping("/afterSaleDetail")
    public String afterSaleDetail(ModelMap model, String applyNo, HttpServletRequest request) {
        if (StringUtils.isBlank(applyNo)) {
            return AFTER_SALE_DETAIL_URL;
        }
        logger.info("afterSaleDetail 调用售后接口，根据申请单no退换货申请单信息，传入 申请单no:{}", applyNo);	
        SaleApplyBill applybill = asmApiImpl.querySaleApplyBillListByApplyNo(applyNo);
       
        List<SaleCancelGoodsInfo> goodsInfos = applybill == null ? null : applybill.getGoodInfos();
        // 申请退换货商品列表
        List<Map<String, Object>> returnProductList = new ArrayList<Map<String, Object>>();
        // 申请单信息
        Map<String, Object> saleApplyMap = new HashMap<String, Object>();
        Map<String, String> logistics = new HashMap<String, String>();
        if (null != applybill) {
            saleApplyMap.put("id", applybill.getId());
            saleApplyMap.put("apply_no", applybill.getApplyNo());
            saleApplyMap.put("status_name", this.getApplyStatusName(applybill.getStatus()));
            saleApplyMap.put("order_sub_no", applybill.getOrderNo());
            saleApplyMap.put("out_order_id", applybill.getOutOrderId());
            saleApplyMap.put("sale_type", this.getSaleType(applybill.getSaleType()));
            saleApplyMap.put("sale_reason", applybill.getSaleReason());
            saleApplyMap.put("remark", applybill.getRemark());
            saleApplyMap.put("createor", applybill.getCreateor());
            saleApplyMap.put("create_time", null == applybill.getCreateTime() ? "" : DateUtil.getFormatByDate(applybill.getCreateTime()));
            logistics.put("expressCode", applybill.getExpressNo());
            logistics.put("expressName", applybill.getLogisticsCompany());
        }

        // Map<String, Object> saleApplyMap =
        // afterSaleDao.queryAfterSaleByApplyNo(applyNo.trim());
        // 退换货质检明细
        // List<Map<String, Object>> returnQaDetailList =
        // afterSaleDao.queryReturnQaDetailsByApplyNo(applyNo.trim());
        logger.info("afterSaleDetail 根据申请单号查询质检，传入 申请单号:{}", applyNo);	
        List<Map<String, Object>> qaDetails = returnQaDetailApi.queryReturnQaDetailsByApplyNo(applyNo.trim());
        logger.info("afterSaleDetail 根据申请单号查询质检，输出记录数{}条", qaDetails==null?"":qaDetails.size());
        if (CollectionUtils.isNotEmpty(qaDetails) && null != qaDetails.get(0)) {
            //logistics.put("expressCode", MapUtils.getString(qaDetails.get(0), "express_code"));
            //logistics.put("expressName", MapUtils.getString(qaDetails.get(0), "express_name"));

            // 补充质检货品信息
            for (Map<String, Object> map : qaDetails) {
                String productNo = MapUtils.getString(map, "product_no");
                if (StringUtils.isNotBlank(productNo)) {
                    Product product = commodityBaseApiService.getProductByNo(productNo, true);
                    if (null == product)
                        continue;

                    map.put("specification", product != null ? product.getCommodity().getColorName() + "," + product.getSizeName() : "");
                    map.put("no", product != null ? product.getCommodity().getCommodityNo() : "");
                    String url = commodityBaseApiService.getFullCommodityPageUrl(product.getCommodity().getCommodityNo());
                    map.put("url", url);
                    map.put("picSmall", product.getCommodity().getPicSmall());
                }
            }
        }
        // 组装货品信息
        if (CollectionUtils.isNotEmpty(goodsInfos)) {
            Map<String, Object> _temp = null;
            for (SaleCancelGoodsInfo goodsInfo : goodsInfos) {
                String productNo = goodsInfo.getProdCode();
                if (StringUtils.isBlank(productNo))
                    continue;

                Product product = commodityBaseApiService.getProductByNo(productNo, true);
                if (null == product)
                    continue;

                _temp = new HashMap<String, Object>();
                _temp.put("prod_code", productNo);
                _temp.put("commodity_name", goodsInfo.getCommodityName());
                _temp.put("commodity_num", goodsInfo.getCommodityNum());
                _temp.put("sale_price", goodsInfo.getUnitPrice());
                _temp.put("specification", product.getCommodity().getColorName() + "," + product.getSizeName());
                _temp.put("supplier_code", product.getCommodity().getSupplierCode());
                _temp.put("no", product.getCommodity().getCommodityNo());
                _temp.put("url", this.getCommodityFullPageUrl(product.getCommodity().getCommodityNo()));
                _temp.put("prod_type", this.getCommodityType(product.getCommodity().getCommodityType()));
                _temp.put("picSmall", product.getCommodity().getPicSmall());
                returnProductList.add(_temp);
            }
        }

        // model.addAttribute("applybill", applybill);
        model.addAttribute("returnProductList", returnProductList);
        model.addAttribute("saleApplyMap", saleApplyMap);
        model.addAttribute("logistics", logistics);
        model.addAttribute("returnQaDetailList", qaDetails);

        return AFTER_SALE_DETAIL_URL;
    }

    @ResponseBody
    @RequestMapping("/checkApplyNoIsExist")
    public String checkApplyNoIsExist(String applyNo, HttpServletRequest request) {
        if (StringUtils.isBlank(applyNo))
            return Boolean.toString(false);

        // Map<String, Object> saleApplyMap =
        // afterSaleDao.queryAfterSaleByApplyNo(applyNo.trim());
        logger.info("checkApplyNoIsExist 调用售后接口，根据申请单no退换货申请单信息，传入 申请单no:{}", applyNo);	
        SaleApplyBill applybill = asmApiImpl.querySaleApplyBillListByApplyNo(applyNo.trim());
        if (null != applybill)
            return Boolean.toString(true);
        ;

        return Boolean.toString(false);
    }

    private String getCommodityFullPageUrl(String commodityNo) {
        String url = "#";
        if (StringUtils.isBlank(commodityNo))
            return url;

        try {
            url = commodityBaseApiService.getFullCommodityPageUrl(commodityNo);
        } catch (Exception e) {
        }

        return url;
    }

    /**
     * 获取商品类型(1商品，2赠品，3配件)
     * 
     * @param commodityType
     * @return
     */
    private String getCommodityType(String commodityType) {
        if (StringUtils.isBlank(commodityType))
            return "商品";

        if ("1".equals(commodityType))
            return "商品";
        else if ("2".equals(commodityType))
            return "赠品";
        else if ("3".equals(commodityType))
            return "配件";

        return "商品";
    }

    /**
     * 转换售后类型
     * 
     * @param saleType
     * @return
     */
    private String getSaleType(String saleType) {
        if (StringUtils.isBlank(saleType))
            return "未知";
        if ("QUIT_GOODS".equals(saleType))
            return "退货";
        else if ("TRADE_GOODS".equals(saleType))
            return "换货";
        else if ("REPAIR_GOODS".equals(saleType))
            return "维修";
        else if ("RETURN_GOODS".equals(saleType))
            return "退回";
        else if ("REISSUE_GOODS".equals(saleType))
            return "补发";
        else if ("REFUND_GOODS".equals(saleType))
            return "退款";

        return "";
    }

    /**
     * 转换申请单状态
     * 
     * @param status
     * @return
     */
    private String getApplyStatusName(String status) {
        if (StringUtils.isBlank(status))
            return "其他";
        if ("SALE_APPLY".equals(status))
            return "未审核";
        else if ("SALE_COMFIRM".equals(status))
            return "审核通过";
        else if ("PART_SALE_QC".equals(status))
            return "部分质检";
        else if ("SALE_REFUSE".equals(status))
            return "审核拒绝";
        else if ("SALE_EXCHANGE_GOODS".equals(status))
            return "已换货";
        else if ("SALE_NOT_GOODS".equals(status))
            return "未收到货";
        else if ("SALE_RECEIVE_GOODS".equals(status))
            return "收到退货";
        else if ("SALE_CALL_BACK".equals(status))
            return "打回";
        else if ("SALE_QC".equals(status))
            return "已质检";
        else if ("SALE_SEND_GOODS".equals(status))
            return "已发货";
        else if ("SALE_REFUND_APPLY".equals(status))
            return "退款申请中";
        else if ("SALE_REFUND_COMFIRM".equals(status))
            return "退款审核通过";
        else if ("SALE_REFUND_REFUSE".equals(status))
            return "退款拒绝";
        else if ("SALE_REFUND_YES".equals(status))
            return "已退款";
        else if ("SALE_SUPPLY_YES".equals(status))
            return "已补款";
        else if ("SALE_SUPPLY_FAIL".equals(status))
            return "补款拒绝";
        else if ("SALE_SUCCESS".equals(status))
            return "已完成";
        else if ("SALE_CANCEL".equals(status))
            return "已作废";
        else if ("SALE_NO_QC".equals(status))
            return "质检不通过";
        else if ("SALE_WAIT_REPAIR".equals(status))
            return "待维修";
        else if ("SALE_REPAIR_PROCESS".equals(status))
            return "维修中";
        else if ("SALE_REPAIR_FAILED_PROCESSED".equals(status))
            return "维修失败待处理";
        else if ("SALE_REPAIR_COMPLETE".equals(status))
            return "已维修待退回";
        else if ("SALE_REPAIR_WAITRETURN".equals(status))
            return "待退回";
        else if ("SALE_REPAIR_RETURN".equals(status))
            return "已退回";
        else if ("SALE_REPLACEMENT_GOODS".equals(status))
            return "已申请补发";
        return "";
    }

    /**
     * 异常售后查询
     * 
     * @param model
     * @param vo
     * @param query
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/queryExAfterSaleList")
    public String queryExAfterSaleList(ModelMap model, QuerySaleVo vo, com.yougou.ordercenter.common.Query query, HttpServletRequest request) throws Exception {
        vo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
        query.setPageSize(20);
        // 去掉OrderSubNo前后带的空格
        if (StringUtils.isNotEmpty(vo.getOrderNo())) {
            vo.setOrderNo(StringUtils.deleteWhitespace(vo.getOrderNo()));
        } else {
            vo.setOrderNo(null);
        }

        if (StringUtils.isEmpty(vo.getMerchantCode())) {
            vo.setMerchantCode(null);
        }
        // 默认设置查询起止时间
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(vo.getCreateTimeStart()) && StringUtils.isEmpty(vo.getCreateTimeEnd())) {
            vo.setCreateTimeStart(com.yougou.kaidian.common.util.DateUtil2.format1(DateUtils.addDays(new Date(), -30)) + " 00:00:00");
            vo.setCreateTimeEnd(com.yougou.kaidian.common.util.DateUtil2.format1(new Date()) + " 23:59:59");
        } else if (StringUtils.isEmpty(vo.getCreateTimeStart())) {
            Date createTimeEnd = format.parse(vo.getCreateTimeEnd());
            vo.setCreateTimeStart(com.yougou.kaidian.common.util.DateUtil2.format1(DateUtils.addDays(createTimeEnd, -30)) + " 00:00:00");
        } else if (StringUtils.isEmpty(vo.getCreateTimeEnd())) {
            Date createTimeStart = format.parse(vo.getCreateTimeStart());
            vo.setCreateTimeEnd(com.yougou.kaidian.common.util.DateUtil2.format1(DateUtils.addDays(createTimeStart, 30)) + " 23:59:59");
        }
        PageFinder<Map<String, Object>> pageFinder = null;
        SaleApplyBill saleApplyBill = null;
        OrderSub order = null;
        Date compareTime = DateUtil2.addDate( DateUtil2.getCurrentDateTime(),-90 );
        try {
        	logger.info("queryExAfterSaleList 调用售后接口，查询异常售后信息，传入 查询参数:{}", vo.toString());	
            pageFinder = asmApiImpl.getAbnormalSaleApplyList(query, vo);
            logger.info("queryExAfterSaleList 调用售后接口，查询异常售后信息，输出信息:{}条", (pageFinder==null||pageFinder.getRowCount()<=0)?"":pageFinder.getData().size());
            if (null != pageFinder && CollectionUtils.isNotEmpty(pageFinder.getData())) {
                for (Map<String, Object> map : pageFinder.getData()) {
                    saleApplyBill = asmApiImpl.querySaleApplyBillListByApplyNo(map.get("apply_no").toString());
                    order = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(map.get("order_sub_no").toString(), vo.getMerchantCode());
                    map.put("prodCode", saleApplyBill.getGoodInfos().get(0).getProdCode());
                    map.put("updateTime", DateFormatUtils.format(saleApplyBill.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
                    map.put("userName", order.getOrderConsigneeInfo().getUserName());
                    // 根据需要加密 Add by LQ.
                    Timestamp originalDate =(Timestamp) map.get("create_time");
                    if( originalDate.before( compareTime ) ){
                    	String phone = (String)map.get("mobile_phone");
                    	map.put("mobile_phone", UserUtil.encriptPhone(phone));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("查询异常售后列表时产生异常:", e);
        }
        model.addAttribute("vo", vo);
        model.addAttribute("pageFinder", pageFinder);
        
        // 记录操作日志
        MerchantCenterOperationLog log = null;
        String menu = UserConstant.MENU_SHDCX;
        String operationNotes = "异常售后查看列表";
        try {
        	log =  new MerchantCenterOperationLog(request,menu,operationNotes,"","",UserConstant.OPERATION_TYPE_READ);
			operationLogService.insertOperationLog(log);
		} catch (Exception e) {
			logger.error("菜单：{},{}时记录操作日志发生异常！",menu,operationNotes);
		}
        return "/manage/asm/ex_after_sale_list";
    }

    @RequestMapping("/ex_afterSaleDetail")
    public String ex_afterSaleDetail(ModelMap model, QuerySaleVo vo, HttpServletRequest request) {
    	logger.info("ex_afterSaleDetail 调用售后接口，根据申请单no退换货申请单信息，传入 查询参数:{}", vo.getApplyNo());	
        SaleApplyBill saleApplyBill = asmApiImpl.querySaleApplyBillListByApplyNo(vo.getApplyNo());
        logger.info("ex_afterSaleDetail 调用订单接口，通过订单号获取订单信息，传入 订单号:{}flag:{}", vo.getOrderNo(),4);
        OrderSub orderSub = orderApi.getOrderSubByOrderSubNo(vo.getOrderNo(), 4);
        Date originalDate = orderSub.getCreateTime();// 判断是否需要加密使用的日期 add by LQ.
        
        List<Map<String, Object>> goodList = new ArrayList<Map<String, Object>>();
        for (OrderDetail4sub orderDetail : orderSub.getOrderDetail4subs()) {
            if (orderDetail.getProdNo().equals(saleApplyBill.getGoodInfos().get(0).getProdCode())) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("prodNo", orderDetail.getProdNo());
                map.put("prodName", orderDetail.getProdName());
                map.put("commoditySpecificationStr", orderDetail.getCommoditySpecificationStr());
                map.put("levelCode", orderDetail.getLevelCode());
                map.put("prodTotalAmt", orderDetail.getProdTotalAmt());
                map.put("prodUnitPrice", orderDetail.getProdUnitPrice());
                map.put("commodityNum", saleApplyBill.getGoodInfos().get(0).getCommodityNum());
                map.put("buyNum", saleApplyBill.getGoodInfos().get(0).getBuyNum());
                map.put("url", commodityBaseApiService.getFullCommodityPageUrl(StringUtils.substring(orderDetail.getProdNo(), 0, orderDetail.getProdNo().length() - 3)));
                goodList.add(map);
            }
        }
		if (null != saleApplyBill && StringUtils.isNotBlank(saleApplyBill.getNewOrderNo())) {
			logger.warn("saleApplyBill.getNewOrderNo(): "+saleApplyBill.getNewOrderNo());
			OrderSub newOrderSub = orderApi.getOrderSubByOrderSubNo(saleApplyBill.getNewOrderNo(), 4);
			OrderConsigneeInfo orderFor = null;
			if( null!= newOrderSub && null!= newOrderSub.getOrderConsigneeInfo() ){
				orderFor = newOrderSub.getOrderConsigneeInfo();
				// 加密敏感信息：手机   地址   Add by LQ.
				if( null!=originalDate && UserUtil.checkEncryptOrNot(originalDate) ){
					
					String phone = orderFor.getMobilePhone();
					String consigneeAddr = orderFor.getConsigneeAddress();
					orderFor.setMobilePhone(UserUtil.encriptPhone(phone));
					orderFor.setConsigneeAddress(UserUtil.encriptAddress(consigneeAddr));
				}
				
			}
			
			model.addAttribute("newOrderNo", saleApplyBill.getNewOrderNo());
			model.addAttribute("newOrderSub", newOrderSub);
			model.addAttribute("newOrderConsigneeInfo", orderFor);// Amend by LQ.
			List<Map<String, Object>> newGoodList = new ArrayList<Map<String, Object>>();
			for (OrderDetail4sub orderDetail : newOrderSub.getOrderDetail4subs()) {
				if (orderDetail.getProdNo().equals(saleApplyBill.getGoodInfos().get(0).getProdCode())) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("prodNo", orderDetail.getProdNo());
					map.put("prodName", orderDetail.getProdName());
					map.put("commoditySpecificationStr", orderDetail.getCommoditySpecificationStr());
					map.put("levelCode", orderDetail.getLevelCode());
					map.put("prodTotalAmt", orderDetail.getProdTotalAmt());
					map.put("prodUnitPrice", orderDetail.getProdUnitPrice());
					map.put("commodityNum", orderDetail.getCommodityNum());
					map.put("url", commodityBaseApiService.getFullCommodityPageUrl(StringUtils.substring(orderDetail.getProdNo(), 0, orderDetail.getProdNo().length() - 3)));
					newGoodList.add(map);
				}
			}
			model.addAttribute("newGoodList", newGoodList);
		}
        
        List<OrderLog> log = orderApi.getOrderLogsByOrderSubNo(vo.getOrderNo());
        model.addAttribute("log", log);
        model.addAttribute("statusName", getApplyStatusName(saleApplyBill.getStatus()));
        model.addAttribute("orderSub", orderSub);
        model.addAttribute("orderConsigneeInfo", orderSub.getOrderConsigneeInfo());
        model.addAttribute("saleApplyBill", saleApplyBill);
        model.addAttribute("goodList", goodList);

        if (CollectionUtils.isNotEmpty(saleApplyBill.getAbnoramalBill())) {
            model.addAttribute("abnoramalBill", saleApplyBill.getAbnoramalBill().get(saleApplyBill.getAbnoramalBill().size() - 1));
        }
        
        // 记录操作日志
        MerchantCenterOperationLog logVO = null;
        String menu = UserConstant.MENU_SHDCX;
        String operationNotes = "异常售后查看详情.";
        String orderNO = "";
        if( null!=saleApplyBill && null!=saleApplyBill.getOrderNo() ){
           orderNO = saleApplyBill.getOrderNo();
        }
        try {
        	logVO =  new MerchantCenterOperationLog(request,menu,operationNotes,"",orderNO,UserConstant.OPERATION_TYPE_READ);
        	
			operationLogService.insertOperationLog(logVO);
		} catch (Exception e) {
			logger.error("菜单：{},{}时记录操作日志发生异常！",menu,operationNotes);
		}
        return "/manage/asm/ex_after_sale_detail";
    }
    
    /* ----------------------赔付管理 模块-------------------------------------*/
    /**
     * 处理赔付工单
     * @param model
     * @param vo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("reply_for_compensate")
    public String  replyForCompensate( ModelMap model,OrderSaleTraceProc orderSaleTraceProc, HttpServletRequest request){
      	//merchantCode 赋值
    	JSONObject result = new JSONObject();
    	String merchantCode = YmcThreadLocalHolder.getMerchantCode();
    	
    	OrderSaleTrace orderSaleTrace = null;
    	 
    	orderSaleTraceProc.setOperateUser(YmcThreadLocalHolder.getMerchantName());
    	orderSaleTraceProc.setOperateId(merchantCode);
    	orderSaleTraceProc.setCreateTime( DateUtil2.getCurrentDateTime() );
    	orderSaleTraceProc.setOperateType(2);//商家回复
    	
    	try {
    		logger.info("replyForCompensate 调用售后接口，根据工单ID查询工单详情，传入 查询参数:{}", orderSaleTraceProc.getOrderSaleTraceId());
    		orderSaleTrace = asmApiImpl.getOrderSaleTraceByOrderTraceId(orderSaleTraceProc.getOrderSaleTraceId());
    		Integer traceStatus = orderSaleTrace.getTraceStatus();
    		if ( traceStatus==UserConstant.TRACE_STATUS_NEW ){
    			asmApiImpl.replyOrderSaleTrace(orderSaleTraceProc);
    		}
    		result.put("resultCode", "200");
		} catch (Exception e) {
			logger.error( MessageFormat.format("商家({0})处理赔付工单,调用订单接口处理赔付工单发生异常。赔付工单编号：{1}", merchantCode,orderSaleTraceProc.getOrderTraceNo()), e);	
			result.put("resultCode", "500");
    		result.put("msg","调用订单接口处理赔付工单发生异常!");
		}
    	return result.toString();
    }
    /**
     * 进入处理赔付工单的详情页面
     * @param model
     * @param vo
     * @param request
     * @return
     */
    @RequestMapping("to_compensate_reply")
    public String  toCompensateReplyPage( ModelMap model, String id, HttpServletRequest request){
      	//merchantCode 赋值
    	String merchantCode = YmcThreadLocalHolder.getMerchantCode();
    	OrderSaleTrace orderSaleTrace = null;
    	OrderTraceSaleVo resultVo = null;
    	try {
    		logger.info("toCompensateReplyPage 调用售后接口，根据工单ID查询工单详情，传入 查询参数:{}", id);
    		 orderSaleTrace = asmApiImpl.getOrderSaleTraceByOrderTraceId(id);
    		// 重新包装vo
 	    	Date createTime = orderSaleTrace.getCreateTime();
 			resultVo = new OrderTraceSaleVo(createTime,UserConstant.TRACE_STATUS_NEW);
 			BeanUtils.copyProperties(orderSaleTrace, resultVo);
		} catch (Exception e) {
			
			logger.error( MessageFormat.format("商家({0})进入处理赔付工单页面,调用订单接口查询赔付工单详情发生异常。赔付工单ID：{1}", merchantCode,id), e);	
		}
    	model.put("vo", resultVo);
    	//拿到当前日期是否是周末，供前台使用，是否需要剩余时间实时刷新
    	if( DateUtil2.isWeekDay( new Date() )){
    		model.put("isWeekendFlag", "true" );
    	}else{
    		model.put("isWeekendFlag", "false" );
    	}
    	return "/manage/asm/compensate_reply";
    }
    
    /**
     * 查看赔付工单的详情页面
     * @param model
     * @param vo
     * @param request
     * @return
     */
    @RequestMapping("compensate_view")
    public String  compensateViewPage( ModelMap model, String id, HttpServletRequest request){
    	
    	 //merchantCode 赋值
    	 String merchantCode = YmcThreadLocalHolder.getMerchantCode();
    	 OrderSaleTrace orderSaleTrace = null;
    	try {
    		logger.info("compensateViewPage 调用售后接口，根据工单ID查询工单详情，传入 查询参数:{}", id);
    		 orderSaleTrace = asmApiImpl.getOrderSaleTraceByOrderTraceId(id);
		} catch (Exception e) {

			logger.error( MessageFormat.format("商家({0})查看赔付工单时,调用订单接口查询赔付工单详情发生异常。赔付工单ID：{1}", merchantCode,id), e);	
		}
    	orderSaleTrace.setCreatorName(YmcThreadLocalHolder.getMerchantName());//////
    	
    	model.put("vo", orderSaleTrace);
    
    	return "/manage/asm/compensate_detail";
    }
  
    
    /**
     * 进入赔付管理列表  （默认是全部列表）
     * @param model
     * @param vo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("batch_approve")
    public String  toPaymentList( ModelMap model,  HttpServletRequest request,String ids ){
    	String merchantCode = YmcThreadLocalHolder.getMerchantCode();
//    	vo.setMerchantCode(merchantCode);
    	try {// 若该工单已处理，则移除该id
    		List<String> idList = buildIdListFromString(ids);
    		if( null!=idList && 0<idList.size() ){
    			asmApiImpl.batchUpdateStatus( buildIdListFromString(ids) );
    		}
		} catch (Exception e) {
			
			logger.error( MessageFormat.format("商家({0})进入赔付管理列表,调用订单接口批量同意赔付发生异常。赔付工单编号：{1}", merchantCode,ids), e);	
			return "调用订单接口批量同意赔付发生异常";
		}
    	return "success";
    }
    
    private List<String> buildIdListFromString(String ids) throws Exception{// 若该工单已处理，则移除该id
    	List<String> list = null;
    	if( StringUtils.isNotEmpty(ids) ){
    		ids = ids.trim();
    		list =  new ArrayList<String>();
    		String[] idArray = ids.split(",");
    		for(int i=0;i<idArray.length;i++){
    			OrderSaleTrace orderSaleTrace = asmApiImpl.getOrderSaleTraceByOrderTraceId(idArray[i]);
        		Integer traceStatus = orderSaleTrace.getTraceStatus();
        		if ( traceStatus==UserConstant.TRACE_STATUS_NEW ){
        			list.add(idArray[i]);
        		}
    		}
    		if( 0==list.size() ){
    			list = null;
    		}
    	}
    	return list;
    }
    /**
     * 进入赔付管理列表  （待处理列表/全部列表）
     * @param model
     * @param vo
     * @param request
     * @return
     */
    @RequestMapping("compensate_list")
    public String  toCompensateList( ModelMap model, QueryTraceSaleVo vo,com.yougou.ordercenter.common.Query query, 
    							  HttpServletRequest request,String status ){
    	
    	resetTraceSaleVOStatus(vo,status);//重新设置与数据库字段匹配的状态
    	logger.info("toCompensateList 调用售后接口，根据上级问题名称查询问题归属，传入 查询参数:{}", "赔付工单");
    	List<Problem> problemList = asmApiImpl.getProblemListByParentName("赔付工单");
    	logger.info("toCompensateList 调用售后接口，根据上级问题名称查询问题归属，输出数据:{}条", problemList==null?"":problemList.size());
    	//merchantCode 赋值
    	String merchantCode = YmcThreadLocalHolder.getMerchantCode();
    	vo.setMerchantCode(merchantCode);
    	String startTime = vo.getStartTime();
    	if( UserConstant.TRACE_STATUS_NEW!=vo.getTraceStatus() ){
	    	if(  StringUtils.isEmpty(startTime) && (StringUtils.isEmpty(status) )
	    			&& StringUtils.isEmpty(vo.getOrderSubNo()) && StringUtils.isEmpty(vo.getOrderTraceNo())
	    			&& StringUtils.isEmpty(vo.getEndTime()) && StringUtils.isEmpty(vo.getSecondProblemId())
	    			){//非待处理的查询：默认查一个月的数据。
	    		vo.setStartTime( DateUtil2.getDateTime( DateUtil.addDay2Date( -QUERY_DATE_RANGE, new Date() ) ) );
	    	}
    	}
    	PageFinder<TraceSaleQueryResult> pageFinder = null;
    	PageFinder<OrderTraceSaleVo> resultPageFinder = null;
    	try {
    		logger.info("toCompensateList 调用售后接口，查询赔付工单列表，传入 查询参数:{}", vo.toString());
			pageFinder = asmApiImpl.getCompensateTraceSaleList(vo,query);
			logger.info("toCompensateList 调用售后接口，查询赔付工单列表，输出数据:{}条", (pageFinder==null||pageFinder.getRowCount()<=0)?"":pageFinder.getData().size());
			if(null!=pageFinder&&pageFinder.getRowCount()>0){
				List<OrderTraceSaleVo> resultList = new ArrayList<OrderTraceSaleVo> ();
				for(TraceSaleQueryResult result:pageFinder.getData()){
					Date createTime = result.getCreateTime();
					Integer traceStatus = result.getTraceStatus();
					OrderTraceSaleVo resultVo = new OrderTraceSaleVo(createTime,traceStatus);
					BeanUtils.copyProperties(result, resultVo);
					resultList.add(resultVo);
				}
				resultPageFinder = new PageFinder<OrderTraceSaleVo>(pageFinder.getPageNo(), pageFinder.getPageSize(), pageFinder.getRowCount(), resultList);
				resultPageFinder.setRowCount(pageFinder.getRowCount());
			}
		} catch (Exception e) {
			logger.error( MessageFormat.format("商家({0})进入赔付管理列表,调用订单接口查询数据发生异常。", merchantCode), e);	
		}
    	model.put("pageFinder", resultPageFinder);
    	model.put("problemList", problemList);
    	model.put("status", status);
    	model.put("vo", vo);
    	//拿到当前日期是否是周末，供前台使用，是否需要剩余时间实时刷新
    	if( DateUtil2.isWeekDay( new Date() )){
    		model.put("isWeekendFlag", "true" );
    	}else{
    		model.put("isWeekendFlag", "false" );
    	}
    	return "/manage/asm/compensate_list";
    }
    
    /**
     * 进入待处理赔付管理列表  (为了商家中心菜单url更简单而增加的)
     * @param model
     * @param vo
     * @param request
     * @return
     */
    @RequestMapping("compensate_handling_list")
    public String  toCompensateHandlingList( ModelMap model, QueryTraceSaleVo vo,com.yougou.ordercenter.common.Query query, 
    							  HttpServletRequest request){
    	
    	vo.setTraceStatus( UserConstant.TRACE_STATUS_NEW );
    	logger.info("toCompensateHandlingList 调用售后接口，根据上级问题名称查询问题归属，传入 查询参数:{}", "赔付工单");
    	List<Problem> problemList = asmApiImpl.getProblemListByParentName("赔付工单");
    	logger.info("toCompensateHandlingList 调用售后接口，根据上级问题名称查询问题归属，输出数据:{}条", problemList==null?"":problemList.size());
    	//merchantCode 赋值
    	String merchantCode = YmcThreadLocalHolder.getMerchantCode();
    	vo.setMerchantCode(merchantCode);
    	PageFinder<TraceSaleQueryResult> pageFinder = null;
    	PageFinder<OrderTraceSaleVo> resultPageFinder = null;
    	try {
    		logger.info("toCompensateList 调用售后接口，查询赔付工单列表，传入 查询参数:{}", vo.toString());
			pageFinder = asmApiImpl.getCompensateTraceSaleList(vo,query);
			logger.info("toCompensateList 调用售后接口，查询赔付工单列表，输出参数:{}条", (pageFinder==null||pageFinder.getRowCount()<=0)?"":pageFinder.getData().size());
			if(null!=pageFinder&&pageFinder.getRowCount()>0){
				List<OrderTraceSaleVo> resultList = new ArrayList<OrderTraceSaleVo> ();
				for(TraceSaleQueryResult result:pageFinder.getData()){
					Date createTime = result.getCreateTime();
					Integer traceStatus = result.getTraceStatus();
					OrderTraceSaleVo resultVo = new OrderTraceSaleVo(createTime,traceStatus);
					BeanUtils.copyProperties(result, resultVo);
					resultList.add(resultVo);
				}
				resultPageFinder = new PageFinder<OrderTraceSaleVo>(pageFinder.getPageNo(), pageFinder.getPageSize(), pageFinder.getRowCount(), resultList);
				resultPageFinder.setRowCount(pageFinder.getRowCount());
			}
		} catch (Exception e) {
			
			logger.error( MessageFormat.format("商家({0})进入待处理赔付管理列表,调用订单接口查询数据发生异常。", merchantCode), e);	
		}
    	//拿到当前日期是否是周末，供前台使用，是否需要剩余时间实时刷新
    	model.put("pageFinder", resultPageFinder);
    	model.put("problemList", problemList);
    	model.put("status", "0");
    	model.put("vo", vo);
    	if( DateUtil2.isWeekDay( new Date() )){
    		model.put("isWeekendFlag", "true" );
    	}else{
    		model.put("isWeekendFlag", "false" );
    	}
    	return "/manage/asm/compensate_list";
    }
    
    /**
     * 待处理赔付工单数目
     * @param model
     * @param vo
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping("compensate_count")
    public String  countCompensateList( ModelMap model ){
    	JSONObject object = new JSONObject();
    	Map<String,Integer> map = new HashMap<String,Integer>();
    	QueryTraceSaleVo vo = new QueryTraceSaleVo();
    	vo.setTraceStatus( UserConstant.TRACE_STATUS_NEW );
    	String merchantCode = YmcThreadLocalHolder.getMerchantCode();
    	vo.setMerchantCode(merchantCode);
    	PageFinder<TraceSaleQueryResult> pageFinder = null;
    	com.yougou.ordercenter.common.Query query = new com.yougou.ordercenter.common.Query();
    	int count = 0;
    	try {
			pageFinder = asmApiImpl.getCompensateTraceSaleList(vo,query);
			if( null!=pageFinder ){
				count = pageFinder.getRowCount();
			}
		} catch (Exception e) {
			logger.error( MessageFormat.format("商家({0})查询待处理赔付工单的数量,调用订单接口查询数据发生异常。", merchantCode), e);	
		}
    	map.put("compensate_count", count);
		object.putAll(map);
    	return object.toString();
    }
  
    // 赔付工单的状态由两个字段来表征（由订单组开发人员约定的）
   private void resetTraceSaleVOStatus(QueryTraceSaleVo vo,String status ){
	   if( StringUtils.isNotEmpty(status) ){
		   if( "0".equals(status) ){
			   vo.setTraceStatus(UserConstant.TRACE_STATUS_NEW);
		   }else if("1".equals(status)){
			   vo.setTraceStatus(UserConstant.TRACE_STATUS_APPEALING);
		   }else if("2".equals(status)){
			   vo.setOperateStatus(UserConstant.OPERATE_STATUS_APPROVE);
		   }else if("3".equals(status)){
			   vo.setOperateStatus(UserConstant.OPERATE_STATUS_APPEAL_SUCCESS);
		   }else if("4".equals(status)){
			   vo.setOperateStatus(UserConstant.OPERATE_STATUS_APPEAL_FAIL); 
		   }
	   }
   }
}
