package com.yougou.kaidian.asm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.kaidian.asm.service.IQualityService;
import com.yougou.kaidian.asm.vo.QualitySaveVo;
import com.yougou.kaidian.asm.vo.QualityVo;
import com.yougou.kaidian.asm.vo.RejectVo;
import com.yougou.kaidian.asm.vo.ReturnVo;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.order.model.AsmProduct;
import com.yougou.kaidian.order.service.IOrderForMerchantService;
import com.yougou.kaidian.stock.model.vo.ResultMsg;
import com.yougou.kaidian.stock.service.IWarehouseService;
import com.yougou.ordercenter.api.asm.IAsmApi;
import com.yougou.ordercenter.api.order.IOrderForMerchantApi;
import com.yougou.ordercenter.common.PageFinder;
import com.yougou.ordercenter.model.asm.SaleApplyBill;
import com.yougou.ordercenter.model.asm.SaleCancelGoodsInfo;
import com.yougou.ordercenter.model.order.OrderDetail4sub;
import com.yougou.ordercenter.model.order.OrderSub;
import com.yougou.ordercenter.vo.asm.QuerySaleVo;
import com.yougou.ordercenter.vo.asm.SaleApplyBillVo;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.wms.wpi.common.exception.WPIBussinessException;
import com.yougou.wms.wpi.rejectioninspection.service.IRejectionInspectionDomainService;
import com.yougou.wms.wpi.returnqaproduct.domain.vo.ReturnQaProductDetailDomainVo;
import com.yougou.wms.wpi.returnqaproduct.domain.vo.ReturnQaProductDomainVo;
import com.yougou.wms.wpi.returnqaproduct.service.IReturnQaProductDomainService;

/**
 * 新质检登记控制类
 * 
 * @author huang.tao
 * 
 */
@Controller
@RequestMapping("/quality")
public class QualityController {

    private final static Logger logger = LoggerFactory.getLogger(QualityController.class);

    private final static String QUALITY_REGISTER_URL = "/manage/asm/quality_register";

    @Resource
    private IQualityService iQualityService;

    @Resource
    private IOrderForMerchantApi orderForMerchantApi;

    @Resource
    private ICommodityBaseApiService commodityBaseApiService;

    @Resource
    private IAsmApi asmApiImpl;

    @Resource
    private IWarehouseService warehouseService;

    @Resource
    private IReturnQaProductDomainService returnQaProductDomainService;

    @Resource
    private IRejectionInspectionDomainService rejectionInspectionDomainService;
    @Resource
    private IOrderForMerchantService orderForMerchantService;

    /**
     * 跳转到新质检登记页
     * 
     * @author huang.tao
     * @param request
     * @return
     */
    @RequestMapping("/to_qualityRegister")
    public String toQualityRegister(ModelMap model, HttpServletRequest request) {
        if (logger.isDebugEnabled())
            logger.debug("quality register start.");

        return QUALITY_REGISTER_URL;
    }
    /**
     * 通过快递单号、订单号 查询质检信息
     * @param model
     * @param request
     * @param vo
     * @return
     */
    @RequestMapping("/to_newQueryOrderNoOrExpressNo")
    public  String toNewQueryOrderNoOrExpressNo(ModelMap model, HttpServletRequest request, QualityVo vo) {
    	Map<String, Object> loginUser = SessionUtil.getUnionUser(request);
        vo.setMerchantCode(MapUtils.getString(loginUser, "supplier_code"));
        vo.setKeyword(StringUtils.trim(vo.getKeyword()));
        vo.setOrderNo(StringUtils.trim(vo.getKeyword()));
        Map<String, Object> saleType = new HashMap<String, Object>();
        try {
             if (StringUtils.isBlank(vo.getOrderNo())) {
                 vo.setErrorMessage("请输入发货、寄回快递单号或订单号！");
                 vo.setOrderNo(null);
             } else {
            	 // 把str当做快递单号查询(有查询到记录、则认为是拒收)
            	 List<OrderSub> orderList = orderForMerchantApi.findOrderSubByMerchantCodeAndExpressOrderId(vo.getMerchantCode(), vo.getOrderNo());
            	// 判断是否为拒收
                 boolean isRejection =checkIsRejection(model, vo, loginUser, orderList);
                 // 判断是否有异常售后
                 boolean isAbnoramalSale = checkIsAbnoramalSale(model, vo, loginUser, orderList);
                 //订单详情信息
                 OrderSub order = new OrderSub();
                 // 判断是否为退换货
                 boolean isReturn=false;
               //是否完成质检登记
                 boolean isFinishQualityCheck=false;
                 //非拒收质检
                 if(!isRejection){
                	 //判断是否为退换货
                	 Map<String,Object> returnMap= checkIsReturn(isRejection, order, vo);
                	 isReturn=(Boolean)returnMap.get("isReturn");
                	 order=(OrderSub)returnMap.get("order");
             		 isFinishQualityCheck = checkisFinishQuality(order, orderList, model);
             		//是否完成质检登记 
             		 if(!isFinishQualityCheck){
             			  orderList = new ArrayList<OrderSub>();
             		      orderList.add(order);
             		 }
                 }
                 if(isFinishQualityCheck){
                 	vo.setErrorMessage("该订单内所有货品已经完成质检，请确认！");
                 }else if(isAbnoramalSale){
                 	vo.setErrorMessage("该订单已申请异常售后，无法录入质检！");
                 }else if (!isRejection && !isReturn) { // 非拒收也非退换货
                     vo.setErrorMessage("您录入的发货、寄回快递单号或订单号不存在，请重新录入！");
                 }else if(checkIsSend(isReturn, isRejection, vo, order)){//检验订单是否已发货
                	 setQualityInfo(model, orderList, order);
                 }
                 saleType.put("isReturn", String.valueOf(isReturn));
                 saleType.put("isRejection", String.valueOf(isRejection));
             }
        } catch (WPIBussinessException e) {
            vo.setErrorMessage(e.getMessage().split(" ")[1]);
            //logger.error(e.getMe/ssage().split(" ")[1]);
            vo.setErrorMessage("快递单号存在检登记信息或已完成拒收质检登记");
        } catch (Exception e) {
        	logger.error("系统内部异常, 请稍后再试.",e);
            throw new RuntimeException("系统内部异常, 请稍后再试.",e);
        }
        model.addAttribute("vo", vo);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("saleType", saleType);
        return QUALITY_REGISTER_URL;
    }
    /**
     *判断是否为拒收商品
     * @param model
     * @param vo
     */
    private boolean checkIsRejection(ModelMap model,QualityVo vo,Map<String, Object> loginUser,List<OrderSub> orderList)throws WPIBussinessException{
    	
    	 if (CollectionUtils.isNotEmpty(orderList)) {
             vo.setExpressNo(vo.getOrderNo());
             //校验快递单号能否进行拒收质检登记,虽然没有取返回结果，但是有用。已质检登记过的（不能再质检），会抛出异常供后面捕获，然后返回给前台页面
             rejectionInspectionDomainService.queryRejectionInspectionByExpressCode(vo.getOrderNo());
             model.addAttribute("commodityNum", getAllOrderCommodityNum(MapUtils.getString(loginUser, "supplier_code"),vo.getOrderNo()));
             return true;
         }
    	 return false;
    }
    /**
     *  判断是否售后异常
     * @param model
     * @param vo
     * @param loginUser
     * @param orderList
     * @throws WPIBussinessException
     */
    private boolean checkIsAbnoramalSale(ModelMap model,QualityVo vo,Map<String, Object> loginUser,List<OrderSub> orderList)throws Exception{
   	 if (CollectionUtils.isNotEmpty(orderList)) {
                //检验是否被登记过异常售后                    
				for (OrderSub order : orderList) {
					Map<String,String> paramMap = new HashMap<String,String>();
					paramMap.put("orderSubNo", order.getOrderSubNo());
					//订单是否有申请异常售后,订单系统接口 
					int abnoramalSaleCount = asmApiImpl.countAbnoramalSaleApplyBillByOrderSubNoAndProdNo(paramMap);
					if(abnoramalSaleCount > 0){
						return  true;
					}
				}
     }
   	 return false;
}
/**
 * 判断是否为退换货
 * @param isRejection
 * @param order
 * @param vo
 * @return
 * @throws Exception
 */
private Map<String,Object> checkIsReturn(boolean isRejection, OrderSub order,QualityVo vo)throws Exception{
	  Map<String,Object>  returnMap=new HashMap<String, Object>();
	  returnMap.put("isReturn", false);
	 if (!isRejection) {
		 //根据子订单号和商家编码获取订单详情信息
         order = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(vo.getOrderNo(), vo.getMerchantCode());
         returnMap.put("order", order);
         if (null == order) {
         	// 获取订单信息
         	com.yougou.ordercenter.common.Query _query = new com.yougou.ordercenter.common.Query();
            _query.setPage(1);
            _query.setPageSize(1);
            QuerySaleVo _vo = new QuerySaleVo();
            _vo.setMerchantCode(vo.getMerchantCode());
            _vo.setStatus("SALE_COMFIRM");//只查询已审核状态的售后申请单
            _vo.setExpressNo(vo.getOrderNo());
            PageFinder<SaleApplyBillVo> page = asmApiImpl.querySaleOrderByMerchantCode(_query,_vo);
            if(page!=null&&page.getData()!=null){
             	order = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(page.getData().get(0).getOrderSubNo(), vo.getMerchantCode());
             	vo.setOrderNo(order.getOrderSubNo());
            }
         }else{
        	 returnMap.put("isReturn", true);
         }
     }
	 return returnMap;
}
/**
 * 是否完成质检登记 
 * @param order
 * @param orderList
 * @param model
 * @return
 */
private boolean checkisFinishQuality(OrderSub order, List<OrderSub> orderList,ModelMap model){
	// 如果货品条码以及数量一致那么说明该单所有商品已经被质检
	if (iQualityService.checkHasQAByOrderNo(order)) {
		return true;                            
    } else {
        orderList = new ArrayList<OrderSub>();
        orderList.add(order);
        model.addAttribute("expressOrderId", orderList.get(0).getExpressOrderId());
        model.addAttribute("logisticsName", orderList.get(0).getLogisticsName());
    }
	return false;
}
/**
 * 检验订单是否已发货
 * @param isReturn
 * @param isRejection
 * @param vo
 * @param order
 * @return
 */
private boolean checkIsSend(boolean isReturn,boolean isRejection, QualityVo vo,OrderSub order){
	if(isReturn){// 退换货
		//检验订单是否已发货 
		if(!orderForMerchantApi.checkIsSendByOrderSubNoAndMerchantCode(order.getOrderSubNo(), vo.getMerchantCode())){
    		if (order.getDeliveryStatus() == 18) {
                vo.setErrorMessage("您输入的订单号已存在非作废的拒收质检，不能进行退换货质检！");
            } else if (order.getDeliveryStatus() != 16) {
                vo.setErrorMessage("您输入的订单号还没有发货信息！");
            }else if(iQualityService.checkHasQAByOrderNo(order)){
            	vo.setErrorMessage("该订单内所有货品已经完成质检，请确认！");
            }
    		return false;
    	}
	}else if(isRejection){//拒收
		 //校验商家扫描的快递单号对应的子订单是否已完成发货 
		 if(!orderForMerchantApi.checkIsSendByMerchantCodeAndExpressOrderId(vo.getMerchantCode(), vo.getOrderNo())){
     		vo.setErrorMessage("您输入的快递单号关联的订单还没有发货信息！");
     		return false;
		 }
	}
	return true;
}
/**
 * 设置质检商品信息
 * @param isReturn
 * @param isRejection
 * @return
 */
private void setQualityInfo(ModelMap model,List<OrderSub> orderList,OrderSub order){
    		// 顾客退换货说明(数据从售后申请中获取)
            List<Map<String, String>> returnInfos = new ArrayList<Map<String, String>>();
            model.addAttribute("returnInfos", returnInfos);
            // 组装订单发货信息
            model.addAttribute("orderDeliveryInfo", this.buildOrderDeliveryInfo(orderList));
            // 组装订单货品明细信息
            model.addAttribute("orderProductInfo", this.buildOrderProductInfos(orderList, returnInfos));
            model.addAttribute("expressInfos", this.iQualityService.getExpressInfo());
            model.addAttribute("orderNo", order.getOrderSubNo());
}

    /**
     * 通过快递单号、订单号查询
     * 
     * @param model
     * @param request
     * @param str
     * 用户录入的单号
     * @return
     */
    @RequestMapping("/to_queryOrderNoOrExpressNo")
    public String toQueryOrderNoOrExpressNo(ModelMap model, HttpServletRequest request, QualityVo vo) {
        Map<String, Object> loginUser = SessionUtil.getUnionUser(request); ////
        vo.setMerchantCode(MapUtils.getString(loginUser, "supplier_code"));
        vo.setKeyword(StringUtils.trim(vo.getKeyword()));
        vo.setOrderNo(StringUtils.trim(vo.getKeyword()));
        // 判断是否为拒收
        boolean isRejection = false;
        
        // 判断是否为退换货
        boolean isReturn = false;
		// 判断是否有异常售后
		boolean isAbnoramalSale = false;
		//是否完成质检登记
		boolean isFinishQualityCheck = false;
		
        try {
            if (StringUtils.isBlank(vo.getOrderNo())) {
                vo.setErrorMessage("请输入发货、寄回快递单号或订单号！");
                vo.setOrderNo(null);
            } else {
                // 把str当做快递单号查询(有查询到记录、则认为是拒收)
            	logger.warn("调用订单接口获取订单详情信息, 输入快递单号:{}商家编码:{}" , vo.getOrderNo(),vo.getMerchantCode());
                List<OrderSub> orderList = orderForMerchantApi.findOrderSubByMerchantCodeAndExpressOrderId(vo.getMerchantCode(), vo.getOrderNo());
                logger.warn("调用订单接口获取订单详情信息, 返回结果 共{}条" , orderList==null?"":orderList.size());
                	
                if (CollectionUtils.isNotEmpty(orderList)) {
                    isRejection = true;
                    vo.setExpressNo(vo.getOrderNo());
                    //校验快递单号能否进行拒收质检登记,虽然没有取返回结果，但是有用。已质检登记过的（不能再质检），会抛出异常供后面捕获，然后返回给前台页面
                    logger.warn("调用WMS接口判断是否已经质检, 输入参数订单号:{}" , vo.getOrderNo());
                    rejectionInspectionDomainService.queryRejectionInspectionByExpressCode(vo.getOrderNo());
                    
                    model.addAttribute("commodityNum", getAllOrderCommodityNum(MapUtils.getString(loginUser, "supplier_code"),vo.getOrderNo()));
                    
                    //检验是否被登记过异常售后                    
					for (OrderSub order : orderList) {
						Map<String,String> paramMap = new HashMap<String,String>();
						paramMap.put("orderSubNo", order.getOrderSubNo());
						//订单是否有申请异常售后,订单系统接口 li.j1 2015-05-21
						logger.warn("调用订单接口订单是否有申请异常售后, 输入参数参数订单号:{}" ,order.getOrderSubNo());
						int abnoramalSaleCount = asmApiImpl.countAbnoramalSaleApplyBillByOrderSubNoAndProdNo(paramMap);
						logger.warn("调用订单接口订单是否有申请异常售后, 输出异常售后数量:{}" ,abnoramalSaleCount);
						if(abnoramalSaleCount > 0){
							isAbnoramalSale = true;
						}
					}
                    
                }
                // 如果上面查询无结果、则将str当成订单号进行查询
                OrderSub order = null;
                if (!isRejection) {
                	logger.warn("调用订单接口获取订单详情信息, 输入快递单号:{}商家编码:{}" , vo.getOrderNo(),vo.getMerchantCode());
                    order = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(vo.getOrderNo(), vo.getMerchantCode());
                    if (null == order) {
                    	// 获取订单信息
                    	com.yougou.ordercenter.common.Query _query = new com.yougou.ordercenter.common.Query();
                        _query.setPage(1);
                        _query.setPageSize(1);
                        QuerySaleVo _vo = new QuerySaleVo();
                        _vo.setMerchantCode(vo.getMerchantCode());
                        _vo.setStatus("SALE_COMFIRM");//只查询已审核状态的售后申请单
                        _vo.setExpressNo(vo.getOrderNo());
                        logger.warn("调用售后申请单信息, 输入参数{}" , _vo.toString());
                        PageFinder<SaleApplyBillVo> page = asmApiImpl.querySaleOrderByMerchantCode(_query,_vo);
                        if(page!=null&&page.getData()!=null){
                        	order = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(page.getData().get(0).getOrderSubNo(), vo.getMerchantCode());
                        	vo.setOrderNo(order.getOrderSubNo());
                        }
                    }
                    if(null != order){
                    	isReturn = true;
                        // 如果货品条码以及数量一致那么说明该单所有商品已经被质检
    					if (iQualityService.checkHasQAByOrderNo(order)) {
    						isFinishQualityCheck = true;                            
                        } else {
                            orderList = new ArrayList<OrderSub>();
                            orderList.add(order);
                            model.addAttribute("expressOrderId", orderList.get(0).getExpressOrderId());
                            model.addAttribute("logisticsName", orderList.get(0).getLogisticsName());
                        }
                    }
                }
                
                if(isFinishQualityCheck){
                	vo.setErrorMessage("该订单内所有货品已经完成质检，请确认！");
                }else if(isAbnoramalSale){
                	vo.setErrorMessage("该订单已申请异常售后，无法录入质检！");
                }else if (!isRejection && !isReturn) { // 非拒收也非退换货
                    vo.setErrorMessage("您录入的发货、寄回快递单号或订单号不存在，请重新录入！");
                }else{
                	if(isReturn){// 退换货
                		logger.warn("检验订单是否已发货 , 输入订单号:{}商家编码:{}" , order.getOrderSubNo(),vo.getMerchantCode());
                		if(!orderForMerchantApi.checkIsSendByOrderSubNoAndMerchantCode(order.getOrderSubNo(), vo.getMerchantCode())){
                    		//OrderSub OrderSub = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(vo.getOrderNo(), vo.getMerchantCode());
                    		if (order.getDeliveryStatus() == 18) {
                                vo.setErrorMessage("您输入的订单号已存在非作废的拒收质检，不能进行退换货质检！");
                            } else if (order.getDeliveryStatus() != 16) {
                                vo.setErrorMessage("您输入的订单号还没有发货信息！");
                            }else if(iQualityService.checkHasQAByOrderNo(order)){
                            	vo.setErrorMessage("该订单内所有货品已经完成质检，请确认！");
                            }
                    	}else{
                    		// 顾客退换货说明(数据从售后申请中获取)
                            List<Map<String, String>> returnInfos = new ArrayList<Map<String, String>>();
                            model.addAttribute("returnInfos", returnInfos);
                            // 组装订单发货信息
                            model.addAttribute("orderDeliveryInfo", this.buildOrderDeliveryInfo(orderList));
                            // 组装订单货品明细信息
                            model.addAttribute("orderProductInfo", this.buildOrderProductInfos(orderList, returnInfos));
                            model.addAttribute("expressInfos", this.iQualityService.getExpressInfo());
                            model.addAttribute("orderNo", order.getOrderSubNo());
                    	}
                	}else if(isRejection){//拒收
                		logger.warn("检验订单是否已发货 , 输入订单号:{}商家编码:{}" , vo.getOrderNo(),vo.getMerchantCode());
                		 if(!orderForMerchantApi.checkIsSendByMerchantCodeAndExpressOrderId(vo.getMerchantCode(), vo.getOrderNo())){
                     		vo.setErrorMessage("您输入的快递单号关联的订单还没有发货信息！");
                		 }else{
                     	// 顾客退换货说明(数据从售后申请中获取)
                            List<Map<String, String>> returnInfos = new ArrayList<Map<String, String>>();
                            model.addAttribute("returnInfos", returnInfos);
                            // 组装订单发货信息
                            model.addAttribute("orderDeliveryInfo", this.buildOrderDeliveryInfo(orderList));
                            // 组装订单货品明细信息
                            model.addAttribute("orderProductInfo", this.buildOrderProductInfos(orderList, returnInfos));
                            model.addAttribute("expressInfos", this.iQualityService.getExpressInfo());
                     	}
                	}
				} 
            }
        } catch (WPIBussinessException e) {
            vo.setErrorMessage(e.getMessage().split(" ")[1]);
        } catch (Exception e) {
        	logger.error("系统内部异常, 请稍后再试.",e);
            throw new RuntimeException("系统内部异常, 请稍后再试.",e);
        }

        Map<String, Object> saleType = new HashMap<String, Object>();
        saleType.put("isReturn", String.valueOf(isReturn));
        saleType.put("isRejection", String.valueOf(isRejection));
        model.addAttribute("vo", vo);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("saleType", saleType);
        return QUALITY_REGISTER_URL;
    }

    /**
     * 获取拒收时所有订单中物品的件数
     * 
     * @param merchantCode
     * @param expressCode
     * @return
     */
    private Integer getAllOrderCommodityNum(String merchantCode,String expressCode) {
        
        int num = 0;
        logger.info("getAllOrderCommodityNum 调用订单接口根据快递单号和商家编码获取订单详情信息, 输入商家编码:{}快递单号:{}" , merchantCode,expressCode);
        List<OrderSub> orderSubList = orderForMerchantApi.findOrderSubByMerchantCodeAndExpressOrderId(merchantCode, expressCode);
        logger.info("getAllOrderCommodityNum 调用订单接口根据快递单号和商家编码获取订单详情信息, 输出订单信息{}条" , orderSubList==null?"":orderSubList.size());
        if (CollectionUtils.isNotEmpty(orderSubList)) {
            for (OrderSub orderSub : orderSubList) {
                for (OrderDetail4sub orderDetail4subs : orderSub.getOrderDetail4subs()) {
                    num = num + orderDetail4subs.getCommodityNum();
                }
            }
        }
        return num;
    }
    
    
    /**
     * 组装订单发货相关信息
     * 
     * @param orderList
     * @return
     */
    private Map<String, String> buildOrderDeliveryInfo(List<OrderSub> orderList) {
        if (CollectionUtils.isEmpty(orderList))
            return null;

        Map<String, String> orderDelivery = new HashMap<String, String>();
        OrderSub order = orderList.get(0);
        if (null == order)
            return null;

        // 订单发货时间, eg: 2013-09-01 10:02:30
        orderDelivery.put("shipTime", DateUtil2.getDateTime(order.getShipTime()));
        // 收货人姓名
        orderDelivery.put("userName", order.getOrderConsigneeInfo().getUserName());
        // 收货人手机
        orderDelivery.put("mobilePhone", order.getOrderConsigneeInfo().getMobilePhone());
        // 发货物流信息, eg:878748742741(圆通)
        orderDelivery.put("expressOrderId", order.getExpressOrderId() + (order.getLogisticsName() == null ? "" : "(" + order.getLogisticsName() + ")"));
        orderDelivery.put("expressName", order.getLogisticsName());
        return orderDelivery;
    }

    /**
     * 组装订单货品列表信息
     * 
     * @param orderList
     * @param changeRefunding
     *            顾客退换货说明
     * @return
     */
    private List<Map<String, String>> buildOrderProductInfos(List<OrderSub> orderList, List<Map<String, String>> changeRefunding) {
        if (CollectionUtils.isEmpty(orderList))
            return null;

        List<Map<String, String>> productInfos = new ArrayList<Map<String, String>>();
        Map<String, String> product = null;
        for (OrderSub order : orderList) {
            List<OrderDetail4sub> orderDetails = order.getOrderDetail4subs();
            for (OrderDetail4sub orderDetail4sub : orderDetails) {
                product = new HashMap<String, String>();
                product.put("orderSubNo", order.getOrderSubNo());
                product.put("commodityNum", orderDetail4sub.getCommodityNum().toString());
                product.put("picSmall", orderDetail4sub.getCommodityImage());
                // 商品名称以及对应的链接
                product.put("prodName", orderDetail4sub.getProdName());
                product.put("url", commodityBaseApiService.getFullCommodityPageUrl(orderDetail4sub.getCommodityNo()));
                // 第三方条码
                product.put("levelCode", orderDetail4sub.getLevelCode());
                // 款色编码要通过接口查询
                Commodity c = commodityBaseApiService.getCommodityByNo(orderDetail4sub.getCommodityNo(), false, false, false);
                // 款色编码
                product.put("supplierCode", null == c ? "" : c.getSupplierCode());
                product.put("prodNo", orderDetail4sub.getProdNo());
                // 申请单相关信息
                logger.info("调用售后接口根据订单号查询退换货申请单信息, 输入订单号:{}" , order.getOrderSubNo());
                List<SaleApplyBill> applyBills = asmApiImpl.querySaleApplyBillListByOrderSubNo(order.getOrderSubNo());
                logger.info("调用售后接口根据订单号查询退换货申请单信息, 输入记录:{}条" , applyBills==null?"":applyBills.size());
                SaleApplyBill applyBill = this.getSaleApplyBillByProduct(applyBills, orderDetail4sub.getProdNo());
                product.put("applyNo", null == applyBill ? "" : applyBill.getApplyNo());
                product.put("applyTime", null == applyBill ? "" : DateUtil2.getDateTime(applyBill.getCreateTime()));
                List<Map<String, String>> returnInfos = this.getChangeReturnInfos(applyBill, orderDetail4sub.getLevelCode());
                if (CollectionUtils.isNotEmpty(returnInfos))
                    changeRefunding.addAll(returnInfos);

                productInfos.add(product);
            }
        }

        return productInfos;
    }

    /**
     * <p>
     * 获取包含该条形码的申请单记录
     * </p>
     * <p>
     * 申请单的状态为已确认
     * </p>
     * 
     * @param saleApplyBills
     * @param productNo
     *            货品编码
     * @return
     */
    private SaleApplyBill getSaleApplyBillByProduct(List<SaleApplyBill> saleApplyBills, String productNo) {
        if (CollectionUtils.isEmpty(saleApplyBills)) {
            return null;
        }

        SaleApplyBill saleApplyBill = null;
        for (SaleApplyBill apply : saleApplyBills) {
            if (!"SALE_COMFIRM".equals(apply.getStatus()))
                continue;
            List<SaleCancelGoodsInfo> saleCancelGoodsInfo = apply.getGoodInfos();
            if (CollectionUtils.isEmpty(saleCancelGoodsInfo))
                continue;

            for (SaleCancelGoodsInfo goodsInfo : saleCancelGoodsInfo) {
                if (productNo.equals(goodsInfo.getProdCode())) {
                    saleApplyBill = apply;
                    break;
                }
            }

        }
        return saleApplyBill;
    }

    /**
     * 顾客退换货说明
     * 
     * @param applyBill
     * @return
     */
    private List<Map<String, String>> getChangeReturnInfos(SaleApplyBill applyBill, String levelCode) {
        if (null == applyBill || CollectionUtils.isEmpty(applyBill.getGoodInfos())) {
            return null;
        }

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> good = new HashMap<String, String>();
        good.put("levelCode", levelCode);
        good.put("saleType", "QUIT_GOODS".equals(applyBill.getSaleType()) ? "退货" : "换货");
        // 退换货备注
        good.put("remark", applyBill.getRemark());
        list.add(good);
        return list;
    }

    /**
     * 保存质检信息
     * 
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveResult")
    public String saveResult(ModelMap model, HttpServletRequest request, QualitySaveVo vo) throws Exception {
//        Map<String, Object> loginUser = SessionUtil.getUnionUser(request);
        Map<String, String> statusMap = new HashMap<String, String>();
        statusMap.put("status", "0");
        String merchantCode =YmcThreadLocalHolder.getMerchantCode();
        String warehouseCode = warehouseService.queryWarehouseCodeByMerchantCode(merchantCode);
        vo.setMerchantCode(merchantCode);
        vo.setWarehouseCode(warehouseCode);
        vo.setQaPerson("(商家)" + YmcThreadLocalHolder.getOperater() );
        ResultMsg msg = new ResultMsg();
        // 前台画面货品条码
 		if (StringUtils.isEmpty(vo.getInsideCode())) {
 			msg.setSuccess(false);
	        msg.setMsg("货品条码为空，不能保存。");
 		} else if ("true".equals(vo.getIsRejection())) {
        	//拒收
	        boolean result = iQualityService.saveQualityInfo(vo);
	        if (!result) {
	            msg.setMsg("保存质检信息失败，请重新登记质检信息！");
	        }else{
	        	msg.setMsg("保存质检信息成功！");
	        }
	        msg.setSuccess(result);
	    }else{
	    	//退换货
	    	Map<String, String> result = iQualityService.saveReturnQualityInfo(vo);
	    	if("true".equals(result.get("success"))){
	    		msg.setSuccess(true);
	    	}else{
	    		msg.setSuccess(false);
	    	}
	    	msg.setMsg(result.get("msg"));
	    	
	    }
 		return JSONObject.fromObject(msg).toString();
    }
    /**
     * 检验货品条码是否存在 售后质检是否已完成 是否正常收货
     * 
     * @author huang.qm
     * @param orderNo
     * @param thirdPartyCode
     * @return
     */
    @ResponseBody
    @RequestMapping("/newReturnCheckProduct")
    public String newReturnCheckProduct(ModelMap model, HttpServletRequest request, ReturnVo vo) throws Exception {
        Map<String, Object> statusMap = new HashMap<String, Object>();
       //   Map<String, Object> loginUser = SessionUtil.getUnionUser(request);
        if (StringUtils.isNotBlank(vo.getInsideCode())) {
            // 拿当前输入的实收货品条码到订单中未质检的货品条码中去匹配
            List<Map<String, String>> levelCodeList = new ArrayList<Map<String, String>>();
            String merchantCode = YmcThreadLocalHolder.getMerchantCode();
            logger.info("newReturnCheckProduct 调用订单接口根据订单号和商家编码获取订单详情信息, 输入商家编码:{}订单单号:{}" , merchantCode,vo.getOrderNo());
            OrderSub order = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode( vo.getOrderNo(),merchantCode );
            Map<String, Object> result = getNewNoQaCommodityList( order, merchantCode );
            @SuppressWarnings("unchecked")
			Map<String, Integer> diff = (Map<String, Integer>) result.get("diff");
            if (diff.containsKey(vo.getInsideCode())) {
                // 如果有相同的货品条码就加入list传到前台
                Map<String, String> map = new HashMap<String, String>();
                map.put("insideCode", vo.getInsideCode());
                levelCodeList.add(map);
                statusMap.put("status", "1");// 正确+未完成+正常收货
            }else {
                statusMap.put("status", "2");// 正确+未完成+非正常收货
            }
            // 如果没有任何匹配的，则把订单中全部未质检的货品条码传到前台
            if (0 == levelCodeList.size()) {
                for (String key : diff.keySet()) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("insideCode", diff.get(key).toString());
                    levelCodeList.add(map);
                }
            }
            statusMap.put("levelCodeList", levelCodeList);
            statusMap.put("diff", diff);
            statusMap.put("commodity_num_sum", result.get("commodity_num_sum"));
            //判断质检货品是否为异常售后
            @SuppressWarnings("unchecked")
            Map<String, Integer> expInsideCode = (Map<String, Integer>) result.get("expInsideCode");
            if (expInsideCode.containsKey(vo.getInsideCode())) {
            	statusMap.put("status", "3");
            }
            return JSONArray.fromObject(statusMap).toString();
        }
        statusMap.put("status", "0");
        return JSONArray.fromObject(statusMap).toString();// 不正确货品条码
    }
    /**
     * 检验货品条码是否存在 售后质检是否已完成 是否正常收货
     * 
     * @author huang.qm
     * @param orderNo
     * @param thirdPartyCode
     * @return
     */
    @ResponseBody
    @RequestMapping("/returnCheckProduct")
    public String returnCheckProduct(ModelMap model, HttpServletRequest request, ReturnVo vo) throws Exception {
        Map<String, Object> statusMap = new HashMap<String, Object>();
       //   Map<String, Object> loginUser = SessionUtil.getUnionUser(request);
        if (StringUtils.isNotBlank(vo.getInsideCode())) {
            // 拿当前输入的实收货品条码到订单中未质检的货品条码中去匹配
            List<Map<String, String>> levelCodeList = new ArrayList<Map<String, String>>();
            String merchantCode = YmcThreadLocalHolder.getMerchantCode();
            logger.info("returnCheckProduct 调用订单接口根据定单号和商家编码获取订单详情信息, 输入商家编码:{}订单单号:{}" , merchantCode,vo.getOrderNo());
            OrderSub order = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode( vo.getOrderNo(),merchantCode );
            Map<String, Object> result = getNoQaCommodityList( order, merchantCode );
            @SuppressWarnings("unchecked")
			Map<String, Integer> diff = (Map<String, Integer>) result.get("diff");
            if (diff.containsKey(vo.getInsideCode())) {
                // 如果有相同的货品条码就加入list传到前台
                Map<String, String> map = new HashMap<String, String>();
                map.put("insideCode", vo.getInsideCode());
                levelCodeList.add(map);
            }
            // 如果没有任何匹配的，则把订单中全部未质检的货品条码传到前台
            if (0 == levelCodeList.size()) {
                for (String key : diff.keySet()) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("insideCode", diff.get(key).toString());
                    levelCodeList.add(map);
                }
            }
            
            

            statusMap.put("levelCodeList", levelCodeList);
            statusMap.put("diff", diff);
            statusMap.put("commodity_num_sum", result.get("commodity_num_sum"));
            AsmProduct product =orderForMerchantService.getProductByLevelCode(order.getOrderSubNo(), vo.getInsideCode());
            if (null != product) {
                if (diff.containsKey(product.getOrderInsideCode())) {
                    statusMap.put("status", "1");// 正确+未完成+正常收货
                } else {
                    statusMap.put("status", "2");// 正确+未完成+非正常收货
                }
            }
            @SuppressWarnings("unchecked")
            Map<String, Integer> expInsideCode = (Map<String, Integer>) result.get("expInsideCode");
            if (null != expInsideCode && expInsideCode.containsKey(vo.getInsideCode())) {
            	statusMap.put("status", "3");
            }
            return JSONArray.fromObject(statusMap).toString();
        }
        statusMap.put("status", "0");
        return JSONArray.fromObject(statusMap).toString();// 不正确货品条码
    }
    
    
    
    /**
     * 获取未质检过的货品条码以及数量
     * @param order 订单
     * @param merchantCode  商家编码
     * @return
     * @throws Exception
     */
  public Map<String, Object> getNewNoQaCommodityList(OrderSub order,String merchantCode) {
    	Map<String, Object> result = new HashMap<String, Object>();
		if (null == order || StringUtils.isEmpty(order.getExpressOrderId())) {
			result.put("diff", new HashMap<String, Integer>());
			return result;
		}
		logger.warn("订单快递单号：{}",order.getExpressOrderId());
        //已经质检过的货品条码以及数量
        Map<String, Integer> hasQA = new HashMap<String, Integer>();
        //未质检的货品数量
        result.put("commodity_num_sum", getNoCommodityNum(hasQA, order, merchantCode));
        // 获取订单内所有货品条码以及数量
        Map<String, Integer> orderCommodityTotal = new HashMap<String, Integer>();
        //异常售后的货品条码及数量
        Map<String, Integer> abnoramalSale = new HashMap<String, Integer>();
        //获取订单商品数量和异常售后的货品条码及数量
		this.getCommodityAndAbnoramalNum(orderCommodityTotal, abnoramalSale, order);
		//未检
		Map<String, Integer> notYetQA = new HashMap<String, Integer>();
		for(String key : orderCommodityTotal.keySet()){
			if (!hasQA.containsKey(key)) {
				notYetQA.put(key, orderCommodityTotal.get(key));
            } else if (hasQA.containsKey(key)) {
            	notYetQA.put(key, orderCommodityTotal.get(key) - hasQA.get(key));
            }
		}
        //未检数量小于等于异常售后数量
		Map<String, Integer> expInsideCode = new HashMap<String, Integer>();
		for(String key : abnoramalSale.keySet()){
			if(abnoramalSale.get(key)>0 && notYetQA.get(key) <= abnoramalSale.get(key)){
				expInsideCode.put(key, 1);
			}
		}
		
		// 比较两个map里的货品条码以及数量,将差异写进diff
		Map<String, Integer> diff = new HashMap<String, Integer>();
		for(String key : orderCommodityTotal.keySet()){
			if (!hasQA.containsKey(key)) {
                diff.put(key, orderCommodityTotal.get(key)-abnoramalSale.get(key));
            } else if (hasQA.containsKey(key) && 
            		hasQA.get(key) < (orderCommodityTotal.get(key) - abnoramalSale.get(key))) {
                diff.put(key, orderCommodityTotal.get(key) - abnoramalSale.get(key) - hasQA.get(key));
            }
		}
		
        logger.warn("差异map的diff：{}",new Object[]{diff});
        result.put("diff", diff);
        result.put("expInsideCode", expInsideCode);
        return result;
    }
  /**
   * 获取未质检的货品数量
   * @param hasQA
   * @param commodityNumSum
   */
  private int getNoCommodityNum(Map<String, Integer> hasQA,OrderSub order,String merchantCode){
	  // 统计该订单发货快递单下所有订单的商品数量
      int commodityNumSum = getAllOrderCommodityNum(merchantCode, order.getExpressOrderId());
      //获取已质检过的货品数量按货品code分类存入map
	  for (ReturnQaProductDomainVo domainVo : returnQaProductDomainService.getReturnQaProductDomainByOrderNo(order.getOrderSubNo())) {
          if ("已确认".equals(domainVo.getReturnStatus().getLabel())) {
              for (ReturnQaProductDetailDomainVo domain : domainVo.getDetailsList()) {
                  commodityNumSum--;
                  if (null != hasQA.get(domain.getInsideCode())) {
                      hasQA.put(domain.getInsideCode(), hasQA.get(domain.getInsideCode()) + 1);
                  } else {
                      hasQA.put(domain.getInsideCode(), 1);
                  }
              }
          }
      }
	  return commodityNumSum;
  }
    /**
     *  获取订单商品数量和异常售后的货品条码及数量
     * @param orderCommodityTotal 快递单下所有订单的商品数量
     * @param abnoramalSale 异常售后的货品条码及数量
     */
    private void getCommodityAndAbnoramalNum( Map<String, Integer> orderCommodityTotal,Map<String, Integer> abnoramalSale,OrderSub order){
    	
    	for (OrderDetail4sub orderDetail4sub : order.getOrderDetail4subs()) {
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("orderSubNo", order.getOrderSubNo());
			paramMap.put("prodNo", orderDetail4sub.getProdNo());
			//订单是否有申请异常售后,订单系统接口 
			logger.info("getCommodityAndAbnoramalNum 调用售后接口根据传入的条件查询异常售后申请数量, 输入货品编码:{}订单单号:{}" , orderDetail4sub.getProdNo(),order.getOrderSubNo());
			int abnoramalSaleRegisterCount = asmApiImpl.countAbnoramalSaleApplyBillByOrderSubNoAndProdNo(paramMap);
			logger.warn("货品条码：{},商品编码：{},货品编号：{},异常售后：{}",
					new Object[]{orderDetail4sub.getLevelCode(),
					orderDetail4sub.getCommodityNo(),
					orderDetail4sub.getProdNo(),
					abnoramalSaleRegisterCount});
			//快递单下所有订单的商品数量
			if(abnoramalSale.containsKey(orderDetail4sub.getLevelCode())){
				abnoramalSale.put(orderDetail4sub.getLevelCode(),abnoramalSale.get(orderDetail4sub.getLevelCode()) + abnoramalSaleRegisterCount);
			}else{
				abnoramalSale.put(orderDetail4sub.getLevelCode(), abnoramalSaleRegisterCount);
			}
			//异常售后的货品条码及数量
			if(orderCommodityTotal.containsKey(orderDetail4sub.getLevelCode())){
				orderCommodityTotal.put(orderDetail4sub.getLevelCode(),orderCommodityTotal.get(orderDetail4sub.getLevelCode()) + orderDetail4sub.getCommodityNum());
			}else{
				orderCommodityTotal.put(orderDetail4sub.getLevelCode(), orderDetail4sub.getCommodityNum());
			}
			
		}
    }
    /**
     * 获取未质检过的货品条码以及数量
     * 
     * @param order
     *            订单
     * @param merchantCode
     *            商家编码
     * @return
     * @throws Exception
     */
    private Map<String, Object> getNoQaCommodityList(OrderSub order,String merchantCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		logger.warn("订单快递单号：{}",order.getExpressOrderId());
		if (null == order || StringUtils.isEmpty(order.getExpressOrderId())) {
			result.put("diff", new HashMap<String, Integer>());
			return result;
		}
        // 统计该订单发货快递单下所有订单的商品数量
        int commodityNumSum = getAllOrderCommodityNum(merchantCode, order.getExpressOrderId());
        // 获取已经质检过的货品条码以及数量
        Map<String, Integer> hasQA = new HashMap<String, Integer>();
        for (ReturnQaProductDomainVo domainVo : returnQaProductDomainService.getReturnQaProductDomainByOrderNo(order.getOrderSubNo())) {
            if ("已确认".equals(domainVo.getReturnStatus().getLabel())) {
                for (ReturnQaProductDetailDomainVo domain : domainVo.getDetailsList()) {
                    commodityNumSum--;
                    if (null != hasQA.get(domain.getInsideCode())) {
                        hasQA.put(domain.getInsideCode(), hasQA.get(domain.getInsideCode()) + 1);
                    } else {
                        hasQA.put(domain.getInsideCode(), 1);
                    }
                }
            }
        }
        result.put("commodity_num_sum", commodityNumSum);
        // 获取订单内所有货品条码以及数量
        Map<String, Integer> orderCommodityTotal = new HashMap<String, Integer>();
        //异常售后的货品条码及数量
        Map<String, Integer> abnoramalSale = new HashMap<String, Integer>();
		for (OrderDetail4sub orderDetail4sub : order.getOrderDetail4subs()) {
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("orderSubNo", order.getOrderSubNo());
			paramMap.put("prodNo", orderDetail4sub.getProdNo());
			//订单是否有申请异常售后,订单系统接口 li.j1 2015-05-21
			logger.info("getNoQaCommodityList 调用售后接口判断订单是否有申请异常售后, 输入货品编码:{}订单单号:{}" , orderDetail4sub.getProdNo(),order.getOrderSubNo());
			int abnoramalSaleRegisterCount = asmApiImpl.countAbnoramalSaleApplyBillByOrderSubNoAndProdNo(paramMap);
			
			logger.warn("货品条码：{},商品编码：{},货品编号：{},异常售后：{}",
					new Object[]{orderDetail4sub.getLevelCode(),
					orderDetail4sub.getCommodityNo(),
					orderDetail4sub.getProdNo(),
					abnoramalSaleRegisterCount});
			if(abnoramalSale.containsKey(orderDetail4sub.getLevelCode())){
				abnoramalSale.put(orderDetail4sub.getLevelCode(),abnoramalSale.get(orderDetail4sub.getLevelCode()) + abnoramalSaleRegisterCount);
			}else{
				abnoramalSale.put(orderDetail4sub.getLevelCode(), abnoramalSaleRegisterCount);
			}
			
			if(orderCommodityTotal.containsKey(orderDetail4sub.getLevelCode())){
				orderCommodityTotal.put(orderDetail4sub.getLevelCode(),orderCommodityTotal.get(orderDetail4sub.getLevelCode()) + orderDetail4sub.getCommodityNum());
			}else{
				orderCommodityTotal.put(orderDetail4sub.getLevelCode(), orderDetail4sub.getCommodityNum());
			}
			
		}
		//未检
		Map<String, Integer> notYetQA = new HashMap<String, Integer>();
		for(String key : orderCommodityTotal.keySet()){
			if (!hasQA.containsKey(key)) {
				notYetQA.put(key, orderCommodityTotal.get(key));
            } else if (hasQA.containsKey(key)) {
            	notYetQA.put(key, orderCommodityTotal.get(key) - hasQA.get(key));
            }
		}
		
        //未检数量小于等于异常售后数量
		Map<String, Integer> expInsideCode = new HashMap<String, Integer>();
		for(String key : abnoramalSale.keySet()){
			if(abnoramalSale.get(key)>0 && notYetQA.get(key) <= abnoramalSale.get(key)){
				expInsideCode.put(key, 1);
			}
		}
		
		// 比较两个map里的货品条码以及数量,将差异写进diff
		Map<String, Integer> diff = new HashMap<String, Integer>();
		for(String key : orderCommodityTotal.keySet()){
			if (!hasQA.containsKey(key)) {
                diff.put(key, orderCommodityTotal.get(key)-abnoramalSale.get(key));
            } else if (hasQA.containsKey(key) && 
            		hasQA.get(key) < (orderCommodityTotal.get(key) - abnoramalSale.get(key))) {
                diff.put(key, orderCommodityTotal.get(key) - abnoramalSale.get(key) - hasQA.get(key));
            }
		}
		
        logger.warn("差异map的diff：{}",new Object[]{diff});
        result.put("diff", diff);
        result.put("expInsideCode", expInsideCode);
        return result;
    }
    
    
    /**
     * 检验货品条码是否存在
     * 
     * @author huang.qm
     * 
     * 
     * 
     * @param thirdPartyCode
     * @return
     */
    @ResponseBody
    @RequestMapping("/rejectionCheckProduct")
    public String rejectionCheckProduct(ModelMap model, HttpServletRequest request, RejectVo vo) throws Exception {
        Map<String, Object> statusMap = new HashMap<String, Object>();
//        Map<String, Object> loginUser = SessionUtil.getUnionUser(request);
        String merchantCode =YmcThreadLocalHolder.getMerchantCode();

        if (StringUtils.isNotBlank(vo.getThirdPartyCode())) {
            // 拿当前输入的实收货品条码到关联的可能多个的订单的货品条码中去匹配
            List<Map<String, String>> orderList = new ArrayList<Map<String, String>>();
            logger.info("rejectionCheckProduct 调用订单接口根据快递单号和商家编码获取订单详情信息, 输入商家编码:{}快递单号:{}" , merchantCode,vo.getExpressOrderId());
            List<OrderSub> orderSubList = orderForMerchantApi.findOrderSubByMerchantCodeAndExpressOrderId(merchantCode, vo.getExpressOrderId());
            for (OrderSub order : orderSubList) {
            	 logger.info("rejectionCheckProduct 调用订单接口根据子订单号和商家编码获取订单详情信息, 输入商家编码:{}订单号:{}" , merchantCode,order.getOrderSubNo());
                OrderSub orderSub = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(order.getOrderSubNo(), YmcThreadLocalHolder.getMerchantCode());
                for (OrderDetail4sub orderDetail4sub : orderSub.getOrderDetail4subs()) {
                    if (vo.getThirdPartyCode().equals(orderDetail4sub.getLevelCode())) {
                        // 如果有相同的货品条码就吧订单号加入list传到前台
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("orderSub", order.getOrderSubNo());
                        orderList.add(map);
                    }
                }
            }

            // 如果没有任何匹配的，则把订单中的全部货品条码传到前台
            if (0 == orderList.size()) {
                for (OrderSub orderSub : orderSubList) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("orderSub", orderSub.getOrderSubNo());
                    orderList.add(map);
                }
            }
            statusMap.put("orderList", orderList);
            Map<String, Integer> diff = new HashMap<String, Integer>();
            logger.info("rejectionCheckProduct 调用订单接口根据快递单号和商家编码获取订单详情信息, 输入商家编码:{}快递单号:{}" , vo.getExpressOrderId());
            List<OrderSub> orderSubs = orderForMerchantApi.findOrderSubByMerchantCodeAndExpressOrderId(merchantCode, vo.getExpressOrderId());
            if (CollectionUtils.isNotEmpty(orderSubs)) {
                statusMap.put("status", "1");
                for (OrderSub orderSub : orderSubs) {
                    for (OrderDetail4sub orderDetail4sub : orderSub.getOrderDetail4subs()) {
                        if (diff.containsKey(orderDetail4sub.getLevelCode())) {
                            diff.put(orderDetail4sub.getLevelCode(), diff.get(orderDetail4sub.getLevelCode()) + orderDetail4sub.getCommodityNum());
                        } else {
                            diff.put(orderDetail4sub.getLevelCode(), orderDetail4sub.getCommodityNum());
                        }
                    }
                }
            } else {
                statusMap.put("status", "2");
            }

            statusMap.put("diff", diff);
            return JSONArray.fromObject(statusMap).toString();
        }
        statusMap.put("status", "0");
        return JSONArray.fromObject(statusMap).toString();
    }

    /**
     * 检验快递单号是否存在
     * 
     * @author mei.jf
     * @param RejectVo
     * @return
     */
    @ResponseBody
    @RequestMapping("/expressNoCheck")
    public String expressNoCheck(ModelMap model, HttpServletRequest request, RejectVo vo) throws Exception {
//        Map<String, Object> loginUser = SessionUtil.getUnionUser(request);
        String merchantCode =YmcThreadLocalHolder.getMerchantCode();
        vo.setMerchantCode(merchantCode);
        logger.info("expressNoCheck 调用订单接口根据快递单号和商家编码获取订单详情信息, 输入商家编码:{}快递单号:{}" , vo.getExpressOrderId());
        List<OrderSub> orderList = orderForMerchantApi.findOrderSubByMerchantCodeAndExpressOrderId(vo.getMerchantCode(), vo.getExpressOrderId());
        Map<String, Object> statusMap = new HashMap<String, Object>();
        if (null != orderList && orderList.size() > 0) {
            // 如果后来该值没有被改变，说明此次退换货的快递单号和以前的某个发货的快递单号重复了
            statusMap.put("status", "1");
            statusMap.put("message", "重复的发货快递单号，请重新输入！");
            for (OrderSub orderSub : orderList) {
                if (vo.getOrderSubNo().equals(orderSub.getOrderSubNo())) {
                    // 退换货的发货快递单号和原来的发货快递单号，说明是拿订单号来做拒收
                    statusMap.put("status", "0");
                    break;
                }
            }
        } else {
            List<String> orderSubNoList = returnQaProductDomainService.queryOrderSubNosByQualityExpressNo(vo.getExpressOrderId());
            if (null != orderSubNoList && orderSubNoList.size() > 0) {
                // 如果后来该值没有被改变，说明该快递单号在以前的退换货质检中已经使用过，现在是重复使用
                statusMap.put("status", "1");
                statusMap.put("message", "重复的退换货质检快递单号，请重新输入！");
                
                OrderSub orderSub_old = null;
                OrderSub orderSub = null;
                for (String orderSubNo : orderSubNoList) {
                    if (vo.getOrderSubNo().equals(orderSubNo)) {
                        // 说明该单可能之前做过部分质检，现允许继续质检
                        statusMap.put("status", "0");
                        break;
                    } else {
                    	
                        orderSub_old = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(orderSubNo, merchantCode);
                        orderSub = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(vo.getOrderSubNo(), merchantCode);
                        if (orderSub_old != null && orderSub != null && orderSub_old.getOrderConsigneeInfo() != null && orderSub.getOrderConsigneeInfo() != null
                                && orderSub_old.getOrderConsigneeInfo().getUserName().equals(orderSub.getOrderConsigneeInfo().getUserName())) {
                            // 说明同一收货人两单一起发回做退换货质检。
                            statusMap.put("status", "0");
                            break;
                        }
                    }
                }
            } else {
                // 说明该快递单号是新的，从未被使用过
                statusMap.put("status", "0");
            }
        }
        return JSONArray.fromObject(statusMap).toString();
    }
}
