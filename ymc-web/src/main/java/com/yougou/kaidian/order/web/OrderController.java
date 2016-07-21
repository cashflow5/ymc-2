package com.yougou.kaidian.order.web;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.component.logistics.api.IMemberLogisticsApi;
import com.yougou.component.logistics.vo.MemberLogisticsVo;
import com.yougou.kaidian.asm.model.OrderProductQuantityVo;
import com.yougou.kaidian.asm.service.IQualityService;
import com.yougou.kaidian.asm.vo.QualityVo;
import com.yougou.kaidian.commodity.convert.ExcelToDataModelConverter;
import com.yougou.kaidian.commodity.service.ICommodityService;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.commodity.pojo.Brand;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.constant.ErrorConstant;
import com.yougou.kaidian.common.util.CommonUtil;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.framework.base.BaseController;
import com.yougou.kaidian.framework.beans.CooperationModel;
import com.yougou.kaidian.framework.exception.YMCException;
import com.yougou.kaidian.framework.util.ExportXLSUtil;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.framework.wrapper.OrderDetail4subWrapper;
import com.yougou.kaidian.order.constant.OrderConstant;
import com.yougou.kaidian.order.enums.ResultEnums;
import com.yougou.kaidian.order.model.MerchantOrderExpand;
import com.yougou.kaidian.order.model.MerchantOrderPrintInputDto;
import com.yougou.kaidian.order.model.MerchantQueryOrderPrintOutputDto;
import com.yougou.kaidian.order.model.MerchantQueryOrderStockOutputDto;
import com.yougou.kaidian.order.model.OrderPunish;
import com.yougou.kaidian.order.model.OrderPunishRule;
import com.yougou.kaidian.order.model.OrderSub4Print;
import com.yougou.kaidian.order.model.OrderSubExpand;
import com.yougou.kaidian.order.model.SalesVO;
import com.yougou.kaidian.order.model.pojo.ReturnAndRejectionBean;
import com.yougou.kaidian.order.model.pojo.ReturnAndRejectionDetailBean;
import com.yougou.kaidian.order.service.IOrderForMerchantService;
import com.yougou.kaidian.order.service.IOrderPunishService;
import com.yougou.kaidian.order.service.IOrderService;
import com.yougou.kaidian.order.service.ISalesService;
import com.yougou.kaidian.order.util.OrderUtil;
import com.yougou.kaidian.user.constant.UserConstant;
import com.yougou.kaidian.user.enums.LogMenuEnum;
import com.yougou.kaidian.user.model.pojo.MerchantCenterOperationLog;
import com.yougou.kaidian.user.service.IMerchantCenterOperationLogService;
import com.yougou.kaidian.user.service.IMerchantUsers;
import com.yougou.member.exception.MemberException;
import com.yougou.merchant.api.supplier.service.ISupplierService;
import com.yougou.merchant.api.supplier.vo.RejectedAddressVo;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
import com.yougou.ordercenter.api.order.IOrderApi;
import com.yougou.ordercenter.api.order.IOrderForMerchantApi;
import com.yougou.ordercenter.common.OrderStatusEnum;
import com.yougou.ordercenter.common.SaleStatusEnum;
import com.yougou.ordercenter.model.order.OrderConsigneeInfo;
import com.yougou.ordercenter.model.order.OrderLog;
import com.yougou.ordercenter.model.order.OrderSub;
import com.yougou.ordercenter.vo.merchant.input.QueryOutOfStockInputDto;
import com.yougou.ordercenter.vo.merchant.input.QueryStockInputDto;
import com.yougou.ordercenter.vo.merchant.output.QueryOrderPickOutputDto;
import com.yougou.ordercenter.vo.merchant.output.QueryOrderPrintOutputDto;
import com.yougou.ordercenter.vo.merchant.output.QueryOutOfStockOutputDto;
import com.yougou.ordercenter.vo.order.ExceptionType;
import com.yougou.ordercenter.vo.order.OrderExceptionVo;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.pc.model.product.Product;
import com.yougou.pc.model.product.ProductCommodity;
import com.yougou.wms.wpi.inventory.service.IInventoryDomainService;
import com.yougou.wms.wpi.orderoutstore.domain.vo.OrderNoExpressCodeVo;
import com.yougou.wms.wpi.orderoutstore.domain.vo.OrderUpdateStatusVo;
import com.yougou.wms.wpi.orderoutstore.service.IOrderOutStoreDomainService;
import com.yougou.wms.wpi.warehouse.service.IWarehouseCacheService;
import com.yougou.yop.api.IMerchantApiOrderService;

/**
 * @directions:商家中心订单模块控制器
 * @author： daixiaowei
 * @create： 2012-3-9 下午12:00:57
 * @history：
 * @version:
 */
@Controller
@RequestMapping("order")
public class OrderController extends BaseController{

    private static final String BASE = "/manage/order/";

    @Resource
    private ISalesService salesService;
    @Resource
    private IMerchantUsers merchantUsers;
    @Resource
    private IQualityService iQualityService;
    @Resource
    private IOrderForMerchantApi orderForMerchantApi;
    @Resource
    private ICommodityBaseApiService commodityBaseApiService;
    @Resource
    private IOrderPunishService orderPunishService;
    @Resource
    private IOrderApi orderApi;
    @Resource
    private IMemberLogisticsApi logisticApi;
    @Resource
    private ISupplierService supplierService;
    @Resource
    private IOrderService orderService;
    @Resource
    private IQualityService qualityService;
    @Resource
    private IWarehouseCacheService warehouseCacheService;
    @Resource
    private IInventoryDomainService inventoryDomainService;
    @Resource
    private ICommodityService commodityService;
    @Resource
    private IMerchantCenterOperationLogService operationLogService;
    @Resource
    private IOrderOutStoreDomainService orderOutStoreDomainService;

    private static final String CONNECTION0 = "0"; // 连号
    /**
     * 日志对象
     */
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private IOrderForMerchantService orderForMerchantService;
    @Resource
    private IMerchantApiOrderService merchantOrderApi;

    /** 默认查询的订单天数间隔时间30天 **/
    private static final int QUERY_DATE_RANGE = 29;

    /** 数据绑定 */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateTimeFormat, true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    /**
     * 查询列表
     * 备货清单列表-功能已废弃
     * @param orderSubExpand
     * @param map
     * @param query
     * @return
     */
    @RequestMapping("/queryAll")
    public String queryAll(HttpServletRequest request,
                           OrderSubExpand orderSubExpand, ModelMap map, Query query)
            throws Exception {
        query = query == null ? new Query() : query;
        // 获得当前登录的商家
        Map<String, Object> unionUser = SessionUtil.getUnionUser(request);////
        if (unionUser == null) {
            throw new Exception("请先登录！");
        }
        String merchantCode =  MapUtils.getString(unionUser,"supplier_code");
        String warehouseCode = MapUtils.getString(unionUser, "warehouse_code");
        if (StringUtils.isBlank(merchantCode)) {
            throw new Exception("商家编号不能为空！");
        }
        if (StringUtils.isBlank(warehouseCode)) {
            throw new Exception("商家仓库编号不能为空！");
        }

        orderSubExpand.setMerchantCode(merchantCode);
        orderSubExpand.setSupplierCode(merchantCode);
        orderSubExpand.setWarehouseCode(warehouseCode);

        /**
         * 默认查询未导出 1:根据下单时间升序 2:根据 导出时间升序 下单时间升序 3:根据备货时间降序 下单时间升序 4:根据缺货时间降序
         * 下单时间降序 4:根据发货时间降序 下单时间降序
         */
        if (orderSubExpand.getTabNum() == null) {
            orderSubExpand.setTabNum(1);
        }

        QueryStockInputDto dto = new QueryStockInputDto();
        BeanUtils.copyProperties(orderSubExpand, dto);
        dto.setPageNo(query.getPage());
        dto.setPageSize(query.getPageSize());

        String isInputYougouWarehouse = MapUtils.getString(unionUser,
                "is_input_yougou_warehouse");

        // 不入优购库，优购发货 才有备货清单信息
        if (StringUtils.equals(isInputYougouWarehouse, "2")) {
            logger.info("调用订单系统备货清单接口-输入参数 {} " , dto);
            com.yougou.ordercenter.common.PageFinder<MerchantQueryOrderStockOutputDto> pageFinder = orderForMerchantService
                    .queryStockingList(dto);
            if (pageFinder != null && pageFinder.getData() != null
                    && pageFinder.getData().size() > 0) {
                logger.info("调用订单系统备货清单接口-返回结果 共{}条", pageFinder.getData().size());
                
                map.put("pageFinder", pageFinder);
            }
        }

        map.put("orderSubExpand", orderSubExpand);
        map.addAttribute("orderStatusMap", OrderUtil.getNewOrderStatus());
        map.put("tagTab", "deliver-all");// 标识进入订单清单
        return "manage/order/deliver-all";
    }

    /**
     * 查看商品信息
     *
     * @param model
     * @param orderid
     * @param OrderNo
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryGoodsInfo")
    public String queryGoodsInfo(HttpServletRequest request, String orderSubId)
            throws Exception {
        List<com.yougou.ordercenter.model.order.OrderDetail4sub> _orderDetails = orderService
                .getOrderDetail4sub(orderSubId,
                        YmcThreadLocalHolder.getMerchantCode());
        Map<String, String> map = new HashMap<String, String>();
        JSONArray arraylist = new JSONArray();
        if (CollectionUtils.isNotEmpty(_orderDetails)) {
            for (com.yougou.ordercenter.model.order.OrderDetail4sub _detail4sub : _orderDetails) {
                map.clear();
                Commodity _commodity = null;
                try {
                    _commodity = commodityBaseApiService
                            .getCommodityByNo(_detail4sub.getCommodityNo());
                } catch (Exception e) {
                    logger.error("获取商品:"+_detail4sub.getCommodityNo()+"异常.",e);
                }
                map.put("id", _detail4sub.getId());
                map.put("styleNo", _commodity.getStyleNo());
                map.put("specName", _detail4sub.getCommoditySpecificationStr()
                        .split(",")[0]);
                map.put("sizeName", _detail4sub.getCommoditySpecificationStr()
                        .split(",")[1]);
                map.put("prodName", _detail4sub.getProdName());
                map.put("activeName", _commodity.getStyleNo());
                map.put("prodNo", _detail4sub.getProdNo());
                map.put("levelCode", _detail4sub.getLevelCode());
                map.put("brandName", _commodity.getBrandName());
                map.put("commoditySpecificationStr",
                        _detail4sub.getCommoditySpecificationStr());
                map.put("commodityNum",
                        String.valueOf(_detail4sub.getCommodityNum()));
                arraylist.add(map);
            }
        }
        JSONObject object = new JSONObject();
        object.put("result", arraylist);
        String result = object.toString();
        return result;
    }

    /**
     * 置为缺货
     *
     * 订单id 多个用,号隔开
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateOutOfStock")
    public String updateOutOfStock(String orderSubNos, String orderSubId,
                                   String orderDetail4subIds, HttpServletRequest request)throws Exception {
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        String operateUser = YmcThreadLocalHolder.getOperater();

        if (StringUtils.isBlank(orderSubNos)||StringUtils.isBlank(orderSubId)||StringUtils.isBlank(orderDetail4subIds)) {
            return "noOrderSubNos";
        }
        OrderSub sub = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(orderSubNos,merchantCode);
        
        //判断订单状态是否允许缺货
        //12 订单已通知库房拣货  7 已申请修改订单  才可以操作缺货
        if (!(sub.getOrderStatus().intValue()==OrderStatusEnum.WAREHOUSE_NOTICED.getValue().intValue() 
        		|| sub.getOrderStatus().intValue()==OrderStatusEnum.MODIFY_APPLIED.getValue().intValue())) {
            return "errorStatus";
        }

        Map<String, Integer> mapThirdPartyCode = new HashMap<String, Integer>();
        // 李明注-原来的人写错,供应商款色编码实际上供应商货品条码. remark格式如下:仓库缺货<font color="black">供应商款色编码(TL31SL00460005);供应商款色编码(TL31SL00460004)</font>
        StringBuffer tempBuffer = new StringBuffer();
        List<com.yougou.ordercenter.model.order.OrderDetail4sub> detail4subList = orderService
                .getOrderDetail4sub(orderSubId, merchantCode);
        // List<OrderDetail4sub> detail4subList =
        // expandService.getOrderDetail4sub(orderSubId);
        List<String> orderDetailIdList=new ArrayList<String>();
        for (com.yougou.ordercenter.model.order.OrderDetail4sub detail4sub : detail4subList) {
            if (orderDetail4subIds.indexOf(detail4sub.getId()) >= 0) {
                if (tempBuffer.length() > 0) {
                    tempBuffer.append(";");
                } else {
                    tempBuffer.append("<font color=\"black\">");
                }
                Product _product = null;
                try {
                    _product = commodityBaseApiService.getProductByNo(
                            detail4sub.getProdNo(), true);
                } catch (Exception e) {
                    logger.error("获取货品:"+detail4sub.getProdNo()+"异常.", e);
                }
                if (_product != null) {
                    mapThirdPartyCode.put(_product.getInsideCode(), 0);
                }
                // 去掉原来的备注“商品款号+颜色+尺码”的格式 20140524
                tempBuffer
                        .append("供应商款色编码(")
                        .append(null == _product ? "" : _product
                                .getInsideCode()).append(")");
                orderDetailIdList.add(detail4sub.getId());
            }
        }
        tempBuffer.append("</font>");
        
        List<OrderExceptionVo> vos = new ArrayList<OrderExceptionVo>();
        OrderExceptionVo vo = new OrderExceptionVo();
        vo.setOperateUser(operateUser);
        vo.setOrderCode(orderSubNos);
        vo.setOrderDetailIds(orderDetailIdList);
        vo.setStatus(com.yougou.ordercenter.constant.OrderConstant.EXCEPTION_WAREHOUSE_ERROR);
        vo.setRemark(ExceptionType.HOUSE_LACK.getName()
		        + tempBuffer.toString());
        vos.add(vo);
        Map<String, String> failOrderSubMap = orderForMerchantApi.orderExceptionForMerchant(vos, merchantCode);
        // 置为缺货时，生成违规订单信息，把货品条码带上，插入数据库，用于审核需要
        if (MapUtils.isEmpty(failOrderSubMap)) {
                this.saveOrUpdateOrderPunish(orderSubNos,mapThirdPartyCode.keySet());
        }
        updateProductInventory(merchantCode, mapThirdPartyCode, 0);
        return MapUtils.isNotEmpty(failOrderSubMap) ? "fail" : "success";
    }

    /**
     * 保存和修改违规订单信息
     *
     * @param orderSubNo
     */
    private void saveOrUpdateOrderPunish(String orderSubNo,Set<String> thirdPartyCodes ) {
        Timestamp curDate = new Timestamp(new Date().getTime());
        List<OrderPunish> list = orderPunishService
                .queryOrderPunishList(orderSubNo);
        OrderSub orderSub = orderApi.getOrderSubByOrderSubNo(orderSubNo, 3);
        String merchantCode = orderSub.getOrderSubExpand().getMerchantCode();
        OrderPunishRule punishRule = orderPunishService
                .getOrderPunishRule(merchantCode);
        if (punishRule == null) {
            return;
        }
        if (list.isEmpty()) {
        	OrderPunish orderPunish = null;
        	for(String thirdPartyCode : thirdPartyCodes){
        		orderPunish = new OrderPunish();
                Double punishPrice;
                if (orderSub.getShipTime() != null) {
                    orderPunish.setShipmentTime(new Timestamp(orderSub
                            .getShipTime().getTime()));
                    orderPunish
                            .setShipmentStatus(OrderConstant.PUNISH_ORDER_SHIPMENT_YES);
                } else {
                    orderPunish
                            .setShipmentStatus(OrderConstant.PUNISH_ORDER_SHIPMENT_NO);
                }
                if (StringUtils.equals(punishRule.getStockPunishType(),
                        OrderConstant.STOCKPUNISHTYPE_MONEY)) {
                    punishPrice = punishRule.getStockPunishMoney() == null
                            ? 0
                            : punishRule.getStockPunishMoney();// 按固定值扣钱
                } else {
                    Double stockRate = punishRule.getStockPunishRate() == null
                            ? 0
                            : punishRule.getStockPunishRate();
                    punishPrice = stockRate * orderSub.getTotalPrice() / 100; // 按订单比率
                }
                orderPunish.setId(UUIDGenerator.getUUID());
                orderPunish.setPunishPrice(punishPrice);
                orderPunish.setMerchantCode(merchantCode);
                orderPunish.setOrderNo(orderSubNo);
                orderPunish.setThirdOrderNo(orderSub.getOutOrderId());
                orderPunish.setOrderPrice(orderSub.getTotalPrice());
                orderPunish.setOrderTime(new Timestamp(orderSub.getCreateTime()
                        .getTime()));
                orderPunish.setOrderSourceNo(orderSub.getOrderSourceNo());
                orderPunish.setOutShopName(orderSub.getOutShopName());
                orderPunish.setPunishType(OrderConstant.PUNISHTYPE_OUTSTOCK);
                orderPunish.setIsSettle(OrderConstant.ISSETTLE_NO);
                orderPunish
                        .setPunishOrderStatus(OrderConstant.PUNISHORDERSTATUS_NORMAL);
                orderPunish.setCreateTime(curDate);
                orderPunish.setUpdateTime(curDate);
                orderPunish.setInsideCode(thirdPartyCode);
                orderPunishService.saveOrderPunish(orderPunish);
                logger.info("保存缺货违规订单, 结果:{}",orderPunish);
        	}
        } else {
            String status = list.get(0).getPunishOrderStatus();
            // 如果该订单未审核，违规类型为超时效，则替换为缺货
            if (StringUtils.equals(status, OrderConstant.PUNISHTYPE_SHIPMENT)) {
                String id = list.get(0).getId();
                Double punishPrice;
                if(StringUtils.equals(punishRule.getStockPunishType(), OrderConstant.STOCKPUNISHTYPE_MONEY)){
                    punishPrice = punishRule.getStockPunishMoney() == null?0:punishRule.getStockPunishMoney();// 按固定值扣钱
                } else {
                    punishPrice = punishRule.getStockPunishRate() == null?0:punishRule.getStockPunishRate()
                            * orderSub.getTotalPrice() / 10; // 按订单比率
                }
                orderPunishService
                        .updateOrderPunsih(id,
                                OrderConstant.PUNISHTYPE_OUTSTOCK, curDate,
                                punishPrice);
                logger.info("修改[{}]订单为缺货违规订单, 处罚金额为{}" , orderSubNo , punishPrice);
            }

        }
    }

    /**
     * 批量更新具体供应商的SKU库存
     *
     * @param merchantCode
     *            供应商编码
     * @param mapThirdPartyCode
     *            SKU以及对应的库存数量Map<String,Integer>
     * @param updateType
     *            更新类型（0：全量，1：增量）
     */
    private void updateProductInventory(String merchantCode,
                                        Map<String, Integer> mapThirdPartyCode, int updateType) {
        String thirdPartyCode = null;
        int quantity = 0;
        if (mapThirdPartyCode != null && !mapThirdPartyCode.isEmpty()) {
            Iterator<String> it = mapThirdPartyCode.keySet().iterator();
            while (it.hasNext()) {
                thirdPartyCode = it.next();
                quantity = mapThirdPartyCode.get(thirdPartyCode);
                Map<String, Object> mapQueryParam = new HashMap<String, Object>();
                mapQueryParam.put("merchantCode", merchantCode);
                mapQueryParam.put("thirdPartyCode", thirdPartyCode);
                List<ProductCommodity> lstProductCommodity = commodityBaseApiService
                        .getProductCommodities(mapQueryParam, false);
                ProductCommodity productCommodity = null;
                if (lstProductCommodity == null
                        || lstProductCommodity.isEmpty()) {
                    logger.error("[ORDER_CONTROLLER]:merchantCode={} ,thirdPartyCode={}  is not exist SKU.",
                            merchantCode,thirdPartyCode );
                } else {
                    productCommodity = lstProductCommodity.get(0);
                }
                // 校验商家是否绑定虚拟仓库
                Map<String, ?> temporaryMap = warehouseCacheService
                        .getWarehouseByMerchantCode(merchantCode);
                if (MapUtils.isEmpty(temporaryMap)) {
                    logger.error("[ORDER_CONTROLLER]:merchantCode={} 不存在对应的虚拟仓库！" , merchantCode);
                }
                String warehouseCode = temporaryMap.keySet().iterator().next();
                updateType = NumberUtils.INTEGER_ONE.equals(updateType)
                        ? updateType
                        : NumberUtils.INTEGER_ZERO;
                temporaryMap = inventoryDomainService
                        .updateInventoryForMerchant(
                                productCommodity.getProductNo(), warehouseCode,
                                quantity, updateType);
                logger.error("[ORDER_CONTROLLER]:merchantCode={},thirdPartyCode={}  is update data."
                			 ,merchantCode , thirdPartyCode);
            }
        } else {
            logger.error("merchantCode={}  is not exist update data! ", merchantCode);
        }
    }
    /**
     * 置为备货
     *
     * @param orderSubNo
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateStocking")
    public String updateStocking(String orderSubNos, HttpServletRequest request)
            throws Exception {
        if (StringUtils.isBlank(orderSubNos)) {
            return "";
        }

        Map<String, Object> unionUser = SessionUtil.getUnionUser(request);////
        orderService.updateStocking(StringUtils.split(orderSubNos, ","),
                unionUser);
        return "success";
    }

    /**
     * 导出订单
     *
     * @param request
     * @param response
     * @param status
     * @throws Exception
     */
    @RequestMapping("/doExportOrder")
    public void doExportOrder(HttpServletRequest request,
                              HttpServletResponse response, String orderSubNos,
                              Integer exportType, OrderSubExpand orderSubExpand) throws Exception {
        // 获得当前登录的商家
        Map<String, Object> unionUser = SessionUtil.getUnionUser(request);////
        if (unionUser == null) {
            throw new RuntimeException("请先登录!");
        }

        if (StringUtils.isNotBlank(orderSubNos)) {
            orderSubNos = ("'"
                    + (orderSubNos.indexOf(',') == -1
                    ? orderSubNos
                    : StringUtils.join(orderSubNos.split(","), "','")) + "'");
        }

        orderSubExpand.setMerchantCode(MapUtils.getString(unionUser,
                "supplier_code"));
        // List<Object[]> objList =
        // expandService.queryAllOrderExport(orderSubNos, unionUser,
        // orderSubExpand);// 应该要加入商家的信息

        String subNoStr = StringUtils.strip(orderSubNos, "'");
        String[] subNoArry = StringUtils.splitByWholeSeparator(subNoStr, "','");
        String merchantCode =  MapUtils.getString(unionUser, "supplier_code");
        String warehouseCode = MapUtils.getString(unionUser, "warehouse_code");
        List<QueryOrderPickOutputDto> orderList = orderForMerchantService
                .queryPcikingList(merchantCode, warehouseCode,
                        Arrays.asList(subNoArry));
        if (CollectionUtils.isEmpty(orderList)) {
            throw new RuntimeException("没有数据可导出");
        }

        // 导出EXCEL
        String templatePath = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/template/order.xls");
        // 组装导出对象
        List<Object[]> objList = new ArrayList<Object[]>();
        Object[] orderPickObject = null;
        for (QueryOrderPickOutputDto orderPick : orderList) {
            orderPickObject = new Object[13];
            orderPickObject[0] = orderPick.getBackupNo();
            orderPickObject[1] = DateUtil2.getDateTime(orderPick.getCreateTime());
            orderPickObject[2] = orderPick.getOrderSubNo();
            orderPickObject[3] = orderPick.getProdName();
            orderPickObject[4] = orderPick.getSupplierCode();
            orderPickObject[5] = orderPick.getInsideCode();
            orderPickObject[6] = orderPick.getProdNo();
            orderPickObject[7] = orderPick.getThirdPartyCode();
            orderPickObject[8] = orderPick.getTotalPrice();
            orderPickObject[9] = orderPick.getBrandName();
            orderPickObject[10] = orderPick.getCommoditySpecificationStr();
            orderPickObject[11] = orderPick.getCommodityNum();
            orderPickObject[12] = DateUtil2.getDateTime(orderPick
                    .getExportedDate());
            objList.add(orderPickObject);
        }

        if (!orderService.doExportOrder(objList, templatePath, "order.xls",
                response, getExportOrderSheetName(exportType))) {
            throw new RuntimeException("导出数据异常");
        }

        // 将订单导出状态由未导出更新为已导出
        if (NumberUtils.INTEGER_ONE.equals(exportType)) {
            List<String> orderNoList = new ArrayList<String>();
            for (Object[] obj : objList) {
                orderNoList.add(obj[2].toString());
            }
            String loginName = YmcThreadLocalHolder.getOperater();
            if (!orderForMerchantApi.updateExport(
                    orderSubExpand.getMerchantCode(), orderNoList, new Date(),
                    loginName)) {
                throw new RuntimeException("更新导出数据异常");
            }
        }
    }

    /**
     * 获取导出备货清单Sheet名称
     *
     * @param exportType
     * @return String
     */
    private String getExportOrderSheetName(int exportType) {
        switch (exportType) {
            case 1 :
                return "捡货清单";
            case 2 :
                return "已导出订单清单";
            case 3 :
                return "交接清单";
            case 4 :
                return "缺货订单清单";
            default :
                return "";
        }
    }

    /***************************************** 销售 ***************************************************/
    /**
     * 查询销售列表
     *
     * @param orderSubExpand
     * @param map
     * @param query
     * @return
     */
    @RequestMapping("/queryAllSales")
    public String queryAllSales(HttpServletRequest request, ModelMap map,
                                Query query, SalesVO salesVO, String pageTag) throws Exception {
        query = query == null ? new Query() : query;
        // 获得当前登录的商家
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        if ( StringUtils.isNotBlank(merchantCode) ) {
            salesVO.setMerchantCode( merchantCode );
            if (StringUtils.isNotBlank(pageTag)) {
                // PageFinder<Map<String, Object>> pageFinder =
                // salesService.queryMerchantSalesList(salesVO, query);
                PageFinder<SalesVO> pageFinder = salesService
                        .queryMerchantProductSales(salesVO, query);
                map.put("pageFinder", pageFinder);
            }
        }
        map.put("salesVO", salesVO);
        map.put("tagTab", "deliver-all");// 标识进入订单清单
        map.put("pageTag", pageTag);// 标识页面是否是第一次进入
        return "manage/order/sales";
    }

    /**
     * 查询销售明细
     *
     * @param orderSubExpand
     * @param map
     * @param query
     * @return
     */
    @RequestMapping("/querySalesDetail")
    public String querySalesDetail(HttpServletRequest request, ModelMap map,
                                   Query query, SalesVO salesVO) throws Exception {
        query = query == null ? new Query() : query;
        if (salesVO != null) {
            if (StringUtils.isNotBlank(salesVO.getTimeStart())) {
                salesVO.setTimeStart(URLDecoder.decode(salesVO.getTimeStart(),
                        "UTF-8"));
            }
            if (StringUtils.isNotBlank(salesVO.getTimeEnd())) {
                salesVO.setTimeEnd(URLDecoder.decode(salesVO.getTimeEnd(),
                        "UTF-8"));
            }
            if (StringUtils.isNotBlank(salesVO.getShipTimeStart())) {
                salesVO.setShipTimeStart(URLDecoder.decode(
                        salesVO.getShipTimeStart(), "UTF-8"));
            }
            if (StringUtils.isNotBlank(salesVO.getShipTimeEnd())) {
                salesVO.setShipTimeEnd(URLDecoder.decode(
                        salesVO.getShipTimeEnd(), "UTF-8"));
            }
        }
        query = query == null ? new Query() : query;
        // 获得当前登录的商家
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        if (StringUtils.isNotBlank(merchantCode)) {
            salesVO.setMerchantCode( merchantCode );
            PageFinder<SalesVO> pageFinder = salesService.querySalesDetailByProductNo(salesVO, query);
            if (pageFinder.getData() != null && pageFinder.getData().size() > 0) {
                map.put("pageFinder", pageFinder);
            }
        }
        Commodity commodity=commodityBaseApiService.getCommodityByNo(salesVO.getNo());
        if(commodity!=null){
        	salesVO.setCommodityName(commodity.getCommodityName());
        }
        map.put("salesVO", salesVO);
        map.put("tagTab", "deliver-all");// 标识进入订单清单
        return "manage/order/sales-detail";
    }

    /**
     * 查询我的缺货商品
     * @param request
     * @param map
     * @param query
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/queryPunishOrderCommodityList")
    public String queryPunishOrderCommodityList(HttpServletRequest request,
                                                ModelMap map, Query query) throws UnsupportedEncodingException {
        Map<String, Object> params = this.builderParams(request, false);
        Map<String, Object> unionUser = SessionUtil.getUnionUser(request);////
        String merchantCode = MapUtils.getString(unionUser, "supplier_code");
        String warehouseCode = MapUtils.getString(unionUser, "warehouse_code");
        if(StringUtils.isBlank(warehouseCode)){
            throw new YMCException("未设置仓库编码", ErrorConstant.getErrorCode(ErrorConstant.MODULE_SYSTEM, ErrorConstant.E_0002));
        }
        QueryOutOfStockInputDto queryOutOfStockInputDto=new QueryOutOfStockInputDto();
        queryOutOfStockInputDto.setBackorderDateBegin(MapUtils.getString(params,"backorderDateBegin"));
        queryOutOfStockInputDto.setBackorderDateEnd(MapUtils.getString(params,"backorderDateEnd"));
        queryOutOfStockInputDto.setBrandNo(MapUtils.getString(params,"brandNo"));
        queryOutOfStockInputDto.setCommodityNo(MapUtils.getString(params,"commodityNo"));
        queryOutOfStockInputDto.setLevelCode(MapUtils.getString(params,"levelCode"));
        queryOutOfStockInputDto.setStyleNo(MapUtils.getString(params,"styleNo"));
        queryOutOfStockInputDto.setOrderSubNo(MapUtils.getString(params,"orderSubNo"));
        queryOutOfStockInputDto.setMerchant_warehouse_code(warehouseCode);
        queryOutOfStockInputDto.setMerchantCode(merchantCode);
        queryOutOfStockInputDto.setMerchant_code(merchantCode);
        queryOutOfStockInputDto.setPageNo(query.getPage());
        queryOutOfStockInputDto.setPageSize(query.getPageSize());
        /*com.yougou.ordercenter.common.PageFinder<QueryOutOfStockOutputDto> pageFinder=orderForMerchantApi.queryOutOfStockForPageFinder(queryOutOfStockInputDto);*/
        com.yougou.ordercenter.common.PageFinder<QueryOutOfStockOutputDto> pageFinder = 
        		orderPunishService.getPunishValidStockList(queryOutOfStockInputDto,query);
        //缺货处罚
        if(pageFinder!=null&&pageFinder.getData()!=null){
            String url="";
            for (QueryOutOfStockOutputDto  dto: pageFinder.getData()) {
                try {
                    url = commodityBaseApiService.getFullCommodityPageUrl(dto.getCommodityNo());
                } catch (Exception e) {
                    logger.error("通过商品接口获取单品页地址异常.", e);
                }
                //单品页链接
                dto.setCommodityURL(url);
                Commodity commodity = null;
                try {
                	commodity = commodityBaseApiService.getCommodityByNo(dto.getCommodityNo());
                } catch (Exception e) {
                    logger.error("通过商品接口获取商品信息", e);
                }
                if(commodity != null) {
                	dto.setPicSmall(commodity.getPicSmall());
                }
            }
        }
        List<Brand> lstBrand = commodityService.queryBrandList(merchantCode);
        map.put("lstBrand", lstBrand);
        map.put("pageFinder", pageFinder);
        map.put("orderPunish", params);
        return "manage/order/punish_order_commodity_list";
    }
    /**
     * 我的我的违规订单
     *
     * @param request
     * @param orderSubExpand
     * @param map
     * @param query
     * @return
     * @throws Exception
     */
    @RequestMapping("/queryPunishOrderList")
    public String queryPunishOrderList(HttpServletRequest request,
                                       OrderPunish orderPunish, ModelMap map, Query query)
            throws Exception {
        logger.info("orderPunish ={} " , orderPunish);
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        if ( StringUtils.isNotBlank(merchantCode) ) {
            orderPunish.setMerchantCode(merchantCode);
            PageFinder<OrderPunish> pageFinder = orderPunishService
                    .queryOrderPunishList(orderPunish, query);
            // 计算超出时长
            if (pageFinder != null && pageFinder.getData().size() > 0) {
                Map<String, Long> overTimeMap = new HashMap<String, Long>();
                Long hour;
                OrderPunishRule punishRule = orderPunishService
                        .getOrderPunishRule(merchantCode);
                for (OrderPunish entity : pageFinder.getData()) {
                    String key = entity.getOrderNo();
                    String shipmentStatus = entity.getShipmentStatus();
                    Date orderDate = new Date(entity.getOrderTime().getTime());
                    //防止shipmentStatus空指针
                    if ("1".equals(shipmentStatus)) {
                        Date shipmentDate = new Date(entity.getShipmentTime()
                                .getTime());
                        hour = (shipmentDate.getTime() - orderDate.getTime()) / 3600000;
                    } else {
                        hour = (new Date().getTime() - orderDate.getTime()) / 3600000;
                    }
                    overTimeMap.put(key, hour - punishRule.getShipmentHour());
                }
                map.put("overHour", overTimeMap);
            }

            map.put("pageFinder", pageFinder);
            map.put("orderPunish", orderPunish);
        }
        return "manage/order/punish_order_list";
    }

    /**
     * 导出违规订单明细
     *
     * @param orderSubNos
     * @throws Exception
     */
    @RequestMapping("/doExportPunishOrder")
    public ModelAndView doExportPunishOrder(String orderSubNos,
                                            HttpServletRequest request) throws Exception {

        if (!StringUtils.isBlank(orderSubNos)) {
            // 查询导出数据
            String[] orderSubNoArray = orderSubNos.split(",");
            List<OrderPunish> list = orderPunishService
                    .queryOrderPunishList(orderSubNoArray);
            Map<String, Long> overTimeMap = new HashMap<String, Long>();
            Long hour;
            Map<String, Object> model = new HashMap<String, Object>();
            String merchantCode = YmcThreadLocalHolder.getMerchantCode();
            OrderPunishRule punishRule = orderPunishService
                    .getOrderPunishRule(merchantCode);
            for (OrderPunish entity : list) {
                String key = entity.getOrderNo();
                String shipmentStatus = entity.getShipmentStatus();
                Date orderDate = new Date(entity.getOrderTime().getTime());
                if ("1".equals(shipmentStatus)) {
                    Date shipmentDate = new Date(entity.getShipmentTime()
                            .getTime());
                    hour = (shipmentDate.getTime() - orderDate.getTime()) / 3600000;
                } else {
                    hour = (new Date().getTime() - orderDate.getTime()) / 3600000;
                }
                overTimeMap.put(key, hour - punishRule.getShipmentHour());
            }
            model.put("list", list);
            model.put("hourMap", overTimeMap);
            return new ModelAndView(new PunishOrderExcelView(), model);
        } else {
            throw new Exception("请选择要导出的订单");
        }
    }

    /**
     * 导出全部的销售明细 create time 2012-7-9
     *
     * @throws Exception
     */
    @RequestMapping("/doExportAllSalesList")
    public void doExportAllSalesList(HttpServletRequest request,
                                     HttpServletResponse response, ModelMap map, Query query,
                                     SalesVO salesVO, String pageTag) throws Exception {
        query = query == null ? new Query() : query;
        // 获得当前登录的商家
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        if ( StringUtils.isNotBlank(merchantCode) ) {
            salesVO.setMerchantCode( merchantCode );
        }
        if (StringUtils.isNotBlank(pageTag)) {
            List<Object[]> lstMerchantSales = salesService
                    .queryMerchantSalesExport(salesVO);
            if (lstMerchantSales != null && !lstMerchantSales.isEmpty()) {
                String[] headers = {"下单时间", "发货时间", "订单号", "商品编码", "商品名称",
                        "商家款色编码", "市场价", "销售价", "商家货品条码", "货品条码", "商品规格",
                        "发货数量", "拒收退货", "售后退货", "优惠券方案", "优惠总金额", "货款金额",
                        "活动优惠金额", "优惠券金额", "礼品卡金额", "下单立减金额"};
                String[] heddenAndUpdateColumns = {ExportXLSUtil.NORMAL,
                        ExportXLSUtil.NORMAL, ExportXLSUtil.NORMAL,
                        ExportXLSUtil.NORMAL, ExportXLSUtil.NORMAL,
                        ExportXLSUtil.NORMAL, ExportXLSUtil.NORMAL,
                        ExportXLSUtil.NORMAL, ExportXLSUtil.NORMAL,
                        ExportXLSUtil.NORMAL, ExportXLSUtil.NORMAL,
                        ExportXLSUtil.NORMAL, ExportXLSUtil.NORMAL,
                        ExportXLSUtil.NORMAL, ExportXLSUtil.NORMAL,
                        ExportXLSUtil.NORMAL, ExportXLSUtil.NORMAL,
                        ExportXLSUtil.NORMAL, ExportXLSUtil.NORMAL,
                        ExportXLSUtil.NORMAL, ExportXLSUtil.NORMAL,
                        ExportXLSUtil.NORMAL, ExportXLSUtil.NORMAL,
                        ExportXLSUtil.NORMAL, ExportXLSUtil.NORMAL,
                        ExportXLSUtil.NORMAL};
                Boolean[] amounts = {false, false, false, false, false, false,
                        false, false, false, false, false, false, false, false,
                        false, false, false, false, false, false, false};

                String filename = NumberUtils.INTEGER_ONE.equals(SessionUtil
                        .getBrowingType(request)) ? URLEncoder.encode("销售明细",
                        "UTF-8") : new String("销售明细".getBytes("UTF-8"),
                        "ISO-8859-1");
                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.setHeader("Content-disposition",
                        "attachment; filename=" + filename + ".xls");
                try {
                    ExportXLSUtil.exportExcel(
                            Long.toString(System.currentTimeMillis()), headers,
                            lstMerchantSales, heddenAndUpdateColumns, amounts,
                            response.getOutputStream(), "yyyy-MM-dd");
                } finally {
                    IOUtils.closeQuietly(response.getOutputStream());
                }
            } else {
                throw new Exception("没有数据可导出");
            }
        }
    }
    
    @ResponseBody
    @RequestMapping(value="/queryAllSalesCount",method=RequestMethod.POST)
    public String queryAllSalesCount(HttpServletRequest request,SalesVO salesVO){
    	 String merchantCode = YmcThreadLocalHolder.getMerchantCode();
         if ( StringUtils.isNotBlank(merchantCode) ) {
        	 salesVO.setMerchantCode( merchantCode );
    	 }
    	Integer count = salesService.queryMerchantSalesCount(salesVO);
    	return count.toString();
    }
    

    /**
     * 导出销售明细
     *
     * @param request
     * @param response
     * @param status
     * @throws Exception
     */
    @RequestMapping("/doExportSalesDetail")
    public void doExportSalesDetail(HttpServletRequest request,
                                    HttpServletResponse response, SalesVO salesVO) throws Exception {
    	String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		if( StringUtils.isNotBlank(merchantCode) ){
            salesVO.setMerchantCode( merchantCode );
        }
        // 查询导出数据
        List<Object[]> lstDetailResult = salesService
                .queryMerchantSalesDetailExport(salesVO);
        if (lstDetailResult != null && lstDetailResult.size() > 0) {
            try {
                String fileRealPath = request.getSession().getServletContext()
                        .getRealPath("/WEB-INF/template/");
                // 模板路径
                String templatePath = fileRealPath + "/salesdetail.xls";
                boolean isHadRecord = orderService.doExportOrder(
                        lstDetailResult, templatePath, "salesdetail.xls",
                        response, "销售明细");
                if (!isHadRecord) {
                    throw new Exception("没有数据可导出");
                }
            } catch (Exception e) {
                throw new Exception("导出异常");
            }
        } else {
            throw new Exception("没有数据可导出");
        }
    }

    /********************************************************* 单据打印 *********************************************************/
    /**
     * 单据打印-未打印 页面
     */
    @RequestMapping("/toDocumentPrinting")
    public String toPrint(HttpServletRequest request,
                          OrderSubExpand orderSubExpand, ModelMap map, Query query)
            throws Exception {
        this.syncOrder(request);
        map = this.getPrintNewModel(map, orderSubExpand, query, request);
        //增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_READ,
        		UserConstant.MENU_DJDY, "未打印", "", "");
        return BASE + "billsPrint-1";
    }

    /**
     * 单据打印-已打印未发货 页面
     *
     * @param request
     * @param orderSubExpand
     * @param map
     * @param query
     * @param flag
     * @return
     * @throws Exception
     */
    @RequestMapping("/toPrintNotDelivery")
    public String toPrintNotDelivery(HttpServletRequest request,
                                     OrderSubExpand orderSubExpand, ModelMap map, Query query)
            throws Exception {
        map = this
                .getPrintNotDeliveryModel(map, orderSubExpand, query, request);
        //增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_READ,
        		UserConstant.MENU_DJDY, "已打印未发货", "", "");
        return BASE + "billsPrint-2";
    }

    /**
     * 单据打印-已发货 页面
     *
     * @param request
     * @param orderSubExpand
     * @param map
     * @param query
     * @param flag
     * @return
     * @throws Exception
     */
    @RequestMapping("/toPrintDelivery")
    public String toPrintDelivery(HttpServletRequest request,
                                  OrderSubExpand orderSubExpand, ModelMap map, Query query)
            throws Exception {
        map = this.getPrintDeliveryModel(map, orderSubExpand, query, request);
        //增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_READ,
        		UserConstant.MENU_DJDY, "已发货", "", "");
        return BASE + "billsPrint-3";
    }

    /**
     * 单据打印-缺货 页面
     *
     * @param request
     * @param orderSubExpand
     * @param map
     * @param query
     * @param flag
     * @return
     * @throws Exception
     */
    @RequestMapping("/toPrintOutstock")
    public String toPrintOutstock(HttpServletRequest request,
                                  OrderSubExpand orderSubExpand, ModelMap map, Query query)
            throws Exception {
        map = this.getPrintOutstockModel(map, orderSubExpand, query, request);
        //增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_READ,
        		UserConstant.MENU_DJDY, "缺货订单", "", "");
        return BASE + "billsPrint-4";
    }

    /**
     * 单据打印-全部 页面
     *
     * @param request
     * @param orderSubExpand
     * @param map
     * @param query
     * @param flag
     * @return
     * @throws Exception
     */
    @RequestMapping("/toPrintAll")
    public String toPrintAll(HttpServletRequest request,
                             OrderSubExpand orderSubExpand, ModelMap map, Query query)
            throws Exception {
        map = this.getPrintAllModel(map, orderSubExpand, query, request);
        map.addAttribute("orderStatusMap", OrderUtil.getNewOrderStatus());
        //增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_READ,
        		UserConstant.MENU_DJDY, "全部", "", "");
        return BASE + "billsPrint-all";
    }

    /********************************************************* 单据打印 (新) *********************************************************/
    /**
     * 单据打印(新)-未打印 页面
     *
     * @param request
     * @param orderSubExpand
     * @param map
     * @param query
     * @param flag
     * @return
     * @throws Exception
     */
    @RequestMapping("/toDocumentPrintingNew")
    public String toPrintNew(HttpServletRequest request,
                             OrderSubExpand orderSubExpand, ModelMap map, Query query)
            throws Exception {
        this.syncOrder(request);
        map = this.getPrintNewModel(map, orderSubExpand, query, request);
        
        //增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_READ,
        		UserConstant.MENU_DJDYX, "未打印", "", "");
        return BASE + "billsPrintNew-1";
    }

    /**
     * 单据打印(新)-已打印未发货 页面
     *
     * @param request
     * @param orderSubExpand
     * @param map
     * @param query
     * @param flag
     * @return
     * @throws Exception
     */
    @RequestMapping("/toPrintNewNotDelivery")
    public String toPrintNewNotDelivery(HttpServletRequest request,
                                        OrderSubExpand orderSubExpand, ModelMap map, Query query)
            throws Exception {
        map = this
                .getPrintNotDeliveryModel(map, orderSubExpand, query, request);
        
        //增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_READ,
        		UserConstant.MENU_DJDYX, "已打印未发货", "", "");
        return BASE + "billsPrintNew-2";
    }

    /**
     * 单据打印(新)-已发货 页面
     *
     * @param request
     * @param orderSubExpand
     * @param map
     * @param query
     * @param flag
     * @return
     * @throws Exception
     */
    @RequestMapping("/toPrintNewDelivery")
    public String toPrintNewDelivery(HttpServletRequest request,
                                     OrderSubExpand orderSubExpand, ModelMap map, Query query)
            throws Exception {
        map = this.getPrintDeliveryModel(map, orderSubExpand, query, request);
        //增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_READ,
        		UserConstant.MENU_DJDYX, "已发货", "", "");
        return BASE + "billsPrintNew-3";
    }

    /**
     * 单据打印(新)-缺货 页面
     *
     * @param request
     * @param orderSubExpand
     * @param map
     * @param query
     * @param flag
     * @return
     * @throws Exception
     */
    @RequestMapping("/toPrintNewOutstock")
    public String toPrintNewOutstock(HttpServletRequest request,
                                     OrderSubExpand orderSubExpand, ModelMap map, Query query)
            throws Exception {
        map = this.getPrintOutstockModel(map, orderSubExpand, query, request);
        //增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_READ,
        		UserConstant.MENU_DJDYX, "缺货订单", "", "");
        return BASE + "billsPrintNew-4";
    }

    /**
     * 单据打印(新)-全部 页面
     *
     * @param request
     * @param orderSubExpand
     * @param map
     * @param query
     * @param flag
     * @return
     * @throws Exception
     */
    @RequestMapping("/toPrintNewAll")
    public String toPrintNewAll(HttpServletRequest request,
                                OrderSubExpand orderSubExpand, ModelMap map, Query query)
            throws Exception {
        map = this.getPrintAllModel(map, orderSubExpand, query, request);
        map.addAttribute("orderStatusMap", OrderUtil.getNewOrderStatus());
        //增加日志
        saveOperationLog(request,UserConstant.OPERATION_TYPE_READ,
        		UserConstant.MENU_DJDYX, "全部", "", "");
        return BASE + "billsPrintNew-all";
    }

    /**
     * 单据打印-未打印 Model {订单基本状态:已确认,订单打印状态 :未打印,配送状态: 未发货-备货中,设置排序:下单时间正序 }
     *
     * @param map
     * @param orderSubExpand
     * @param query
     * @return
     * @throws Exception
     */
    private ModelMap getPrintNewModel(ModelMap map,
                                      OrderSubExpand orderSubExpand, Query query,
                                      HttpServletRequest request) throws Exception {

        // 设置查询订单基本状态 已确认
    	orderSubExpand.setOrderStatus(OrderStatusEnum.WAREHOUSE_NOTICED.getValue());
        // 设置查询订单打印状态 未打印
        orderSubExpand.setOrderPrintedStatus(OrderConstant.NOPRINT);
        // 配送状态 未发货 备货中
        orderSubExpand.setDeliveryStatus(OrderConstant.DELIVERY_PREPARE_REAl);
        // 设置排序
        orderSubExpand.setOrderBy(OrderConstant.ORDER_SORT_ORDERTIME_ASC);
        map = this.getDocumentPrintingModelMap(orderSubExpand, map, query,
                request);

        return map;
    }

    /**
     * 单据打印-已打印未发货 Model {订单基本状态:已确认,订单打印状态 :已打印,配送状态: 未发货-备货中,设置排序:下单时间正序 }
     *
     * @param map
     * @param orderSubExpand
     * @param query
     * @param request
     * @return
     * @throws Exception
     */
    private ModelMap getPrintNotDeliveryModel(ModelMap map,
                                              OrderSubExpand orderSubExpand, Query query,
                                              HttpServletRequest request) throws Exception {

        // 设置查询订单基本状态 已确认
    	orderSubExpand.setOrderStatus(OrderStatusEnum.WAREHOUSE_NOTICED.getValue());
        // 设置查询订单打印状态 已打印
        orderSubExpand.setOrderPrintedStatus(OrderConstant.YESPRINT);
        // 配送状态 未发货 备货中
        orderSubExpand.setDeliveryStatus(OrderConstant.DELIVERY_PREPARE_REAl);
        // 设置排序
        orderSubExpand.setOrderBy(OrderConstant.ORDER_SORT_ORDERTIME_ASC);
        map = this.getDocumentPrintingModelMap(orderSubExpand, map, query,
                request);
        return map;
    }

    /**
     * 单据打印-已发货 Model {订单基本状态:已确认,订单打印状态 : 已完成,配送状态:已发货,设置排序:下单时间倒序}
     *
     * @param map
     * @param orderSubExpand
     * @param query
     * @param request
     * @return
     * @throws Exception
     */
    private ModelMap getPrintDeliveryModel(ModelMap map,
                                           OrderSubExpand orderSubExpand, Query query,
                                           HttpServletRequest request) throws Exception {

        // 设置查询订单基本状态 已完成
    	orderSubExpand.setOrderStatus(OrderStatusEnum.DELIVERED.getValue());
        // 配送状态 已发货
        orderSubExpand.setDeliveryStatus(OrderConstant.DELIVERY_SEND);
        // 设置排序
        orderSubExpand.setOrderBy(OrderConstant.ORDER_SORT_SHIPMENTTIME_DESC);
        // 如果不是第一次进入该页面
        // if (orderSubExpand.getTabNum() != null && orderSubExpand.getTabNum()
        // == 1) {
        map = this.getDocumentPrintingModelMap(orderSubExpand, map, query,
                request);
        // }
        return map;
    }

    /**
     * 单据打印-缺货 Model {订单基本状态: 已挂起,设置挂起状态:缺货挂起,设置查询缺货时间：当前一个月内,设置排序:缺货时间倒序 }
     *
     * @param map
     * @param orderSubExpand
     * @param query
     * @param request
     * @return
     * @throws Exception
     */
    private ModelMap getPrintOutstockModel(ModelMap map,
                                           OrderSubExpand orderSubExpand, Query query,
                                           HttpServletRequest request) throws Exception {

        // 设置查询订单基本状态 已挂起
    	orderSubExpand.setOrderStatus(OrderStatusEnum.SERVICE_NOTICED.getValue());
        // 设置挂起状态 缺货挂起
        //orderSubExpand.setSuspendType(OrderConstant.SUSPEND_TYPE_LACK);;
        // 设置排序
        orderSubExpand
                .setOrderBy(OrderConstant.ORDER_SORT_OUTSTOCKTIME_DESC_ORDERTIME_DESC);
        // 设置查询缺货时间是当前一个月内的
        // int limitDay = 30;
        // if (StringUtils.isEmpty(orderSubExpand.getTimeStartOutStock())) {
        // orderSubExpand.setTimeStartOutStock(OrderUtil.getStartTime3(new
        // Date(), limitDay));
        // }
        // 如果不是第一次进入该页面
        // if (orderSubExpand.getTabNum() != null && orderSubExpand.getTabNum()
        // == 1) {
        map = this.getDocumentPrintingModelMap(orderSubExpand, map, query,
                request);
        // }

        return map;
    }

    /**
     * 单据打印-缺货 Model {设置排序:这个排序缺少 }
     *
     * @param map
     * @param orderSubExpand
     * @param query
     * @param request
     * @return
     * @throws Exception
     */
    private ModelMap getPrintAllModel(ModelMap map,
                                      OrderSubExpand orderSubExpand, Query query,
                                      HttpServletRequest request) throws Exception {

        // 设置排序
        orderSubExpand.setOrderBy(OrderConstant.ORDER_SORT_ORDERTIME_ASC);

        // 如果不是第一次进入该页面
        // if (orderSubExpand.getTabNum() != null && orderSubExpand.getTabNum()
        // == 1) {
        map = this.getDocumentPrintingModelMap(orderSubExpand, map, query,
                request);
        // }

        return map;
    }

    /**
     * 得单据打印ModelMap
     *
     * @param orderSubExpand
     * @param map
     * @param query
     * @return
     * @throws Exception
     */
    private ModelMap getDocumentPrintingModelMap(OrderSubExpand orderSubExpand,
                                                 ModelMap map, Query query, HttpServletRequest request)
            throws Exception {
        Map<String, Object> unionUser = SessionUtil.getUnionUser(request);////
        if (unionUser == null) {
            throw new Exception("请先登录！");
        }
        String merchantCode = MapUtils.getString(unionUser, "supplier_code");
        String warehouseCode = MapUtils.getString(unionUser, "warehouse_code");
        if (StringUtils.isBlank(merchantCode)) {
            throw new Exception("商家编号不能为空！");
        }
        if (StringUtils.isBlank(warehouseCode)) {
            throw new Exception("商家仓库编号不能为空！");
        }
        orderSubExpand.setSupplierCode(merchantCode);// 商家编码
        orderSubExpand.setMerchantCode(merchantCode);
        orderSubExpand.setWarehouseCode(warehouseCode);

        // 应订单组要求,当订单时间不为空时，要增加15天以内的下单时间查询条件。
        // 如果页面的下单时间不输入，默认查询15天以内的数据,但是如果订单号有输入则忽略下单时间（无论是否输入了下单时间）
        if (!StringUtils.isBlank(orderSubExpand.getTimeStart())
                && StringUtils.isBlank(orderSubExpand.getTimeEnd())
                && StringUtils.isBlank(orderSubExpand.getOrderSubNo())) {
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            Date startdate = format1.parse(orderSubExpand.getTimeStart());
            Date enddate = DateUtils.addDays(startdate, QUERY_DATE_RANGE);
            orderSubExpand.setTimeEnd(format1.format(enddate));
            // throw new Exception("当下单开始时间不为空，下单结束时间也不能为空！");
        } else if (StringUtils.isNotBlank(orderSubExpand.getTimeEnd())
                && StringUtils.isNotBlank(orderSubExpand.getOrderSubNo())) {
            // 如果同时填写了订单号和时间进行查询，则查询后，时间清空
            orderSubExpand.setTimeEnd(null);
        }
        if (StringUtils.isBlank(orderSubExpand.getTimeStart())
                && !StringUtils.isBlank(orderSubExpand.getTimeEnd())
                && StringUtils.isBlank(orderSubExpand.getOrderSubNo())) {
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            Date enddate = format1.parse(orderSubExpand.getTimeEnd());
            Date startdate = DateUtils.addDays(enddate, -QUERY_DATE_RANGE);
            orderSubExpand.setTimeStart(format1.format(startdate));
            // throw new Exception("当下单结束时间不为空，下单开始时间也不能为空！");
        } else if (StringUtils.isNotBlank(orderSubExpand.getTimeStart())
                && StringUtils.isNotBlank(orderSubExpand.getOrderSubNo())) {
            // 如果同时填写了订单号和时间进行查询，则查询后，时间清空
            orderSubExpand.setTimeStart(null);
        }
        if (!StringUtils.isBlank(orderSubExpand.getTimeStart())
                && !StringUtils.isBlank(orderSubExpand.getTimeEnd())) {
            Date timeStart = DateUtil2.getdate(orderSubExpand.getTimeStart());
            Date timeEnd = DateUtil2.getdate(orderSubExpand.getTimeEnd());
            long intervalMilli = timeEnd.getTime() - timeStart.getTime();
            int day = (int) (intervalMilli / (24 * 60 * 60 * 1000));
            if (day > QUERY_DATE_RANGE) {
                throw new Exception("下单时间隔必须小于等于30天");
            }
        }

        MerchantOrderPrintInputDto dto = new MerchantOrderPrintInputDto();

        BeanUtils.copyProperties(orderSubExpand, dto);
        // 如果页面的下单时间不输入，默认查询15天以内的数据
        if (StringUtils.isBlank(dto.getTimeStart())
                && StringUtils.isBlank(dto.getTimeEnd())
                && StringUtils.isBlank(dto.getOrderSubNo())) {
            Calendar calStart = Calendar.getInstance();
            calStart.add(Calendar.DAY_OF_YEAR, -QUERY_DATE_RANGE);
            dto.setTimeStart(DateUtil2.format1(calStart.getTime()));
            dto.setTimeEnd(DateUtil2.format1(new Date()));
        }
        // 加入客服申请拦截的订单
        if (null != orderSubExpand.getBaseStatus()
                && null != orderSubExpand.getDeliveryStatus()
                && OrderConstant.BASE_CONFIRM == orderSubExpand.getBaseStatus()
                && OrderConstant.DELIVERY_PREPARE_REAl == orderSubExpand
                .getDeliveryStatus()) {
            dto.setWithApplyInterruptForMerchant(Boolean.TRUE);
        }
        dto.setPageNo(query.getPage());
        dto.setPageSize(query.getPageSize());

        logger.info("调用订单系统单据打印接口-输入参数 {} " , dto);
        
        com.yougou.ordercenter.common.PageFinder<MerchantQueryOrderPrintOutputDto> pageFinder = orderForMerchantService
                .queryPrintList(dto);
        if (pageFinder != null && pageFinder.getData() != null
                && pageFinder.getData().size() > 0) {
        	//数据加密 li.j1 2015-5-25
        	for(MerchantQueryOrderPrintOutputDto order : pageFinder.getData()){  
    			String[] encryptResult = OrderUtil.encryptMobileAndAddressByDateAndStatus(order.getConsigneeAddress(), 
    					order.getConsigneeMobile(),order.getOrderStatus(), order.getCreateTime() );
    			order.setConsigneeAddress(encryptResult[0]);
    			order.setConsigneeMobile(encryptResult[1]);
        	}
        	
            Map<String, Object> interceptmap = new HashMap<String, Object>();
            for (QueryOrderPrintOutputDto data : pageFinder.getData()) {
                Object result = this.redisTemplate.opsForHash().get(
                        CacheConstant.C_ORDER_INTERCEPT_NO_KEY,
                        "orderSubNo_" + data.getOrderSubNo());
                interceptmap.put(
                        data.getOrderSubNo(),
                        orderForMerchantApi.getModifiedOrCanceledOrder(data
                                .getOrderSubNo()) != null && result == null
                                ? true
                                : false);
            }
            logger.info("调用订单系统单据打印接口-返回结果  {}条" , pageFinder.getData().size());
            map.put("pageFinder", pageFinder);
            map.put("orderintercept", interceptmap);
        }

        // 获取省市区第一级结果集数据
        List<Map<String, Object>> areaList = merchantUsers.getAreaList();
        map.put("orderSubExpand", orderSubExpand);
        map.addAttribute("areaList", areaList);
        map.put("tagTab", "deliver-all");// 标识进入订单清单
        return map;
    }

    /**
     * 前商家订单的同步处理
     *
     * @param request
     * @throws Exception
     */
    public void syncOrder(HttpServletRequest request) throws Exception {
        // 获得当前登录的商家
        Map<String, Object> unionUser = SessionUtil.getUnionUser(request);////
        if (MapUtils.isEmpty(unionUser)) {
            throw new Exception("请先登录！");
        }

        String merchantCode = MapUtils.getString(unionUser, "supplier_code");
        if (StringUtils.isBlank(merchantCode)) {
            throw new Exception("商家编号不能为空！");
        }

        try {
            // 商家编码
            /**************** 由于业务部门的要求 这里要加入对申请拦截和客服取消的订单处理 *************/
            orderForMerchantApi
                    .updateProcessInterceptAndCancelOrder(merchantCode);
            /**************** 由于业务部门的要求 这里要加入对申请拦截和客服取消的订单处理 *************/

            // 合作模式为“不入优购库，商家发货”才处理订单同步状态
            CooperationModel cooperationModel = CooperationModel
                    .forIdentifier(MapUtils.getInteger(unionUser,
                            "is_input_yougou_warehouse"));
            if (CooperationModel.NON_ENTER_YOUGOU_WAREHOUSE_MERCHANT_DELIVERY
                    .equals(cooperationModel)) {
                /**************** 由于业务部门的要求 这里要加入对当前商家订单的同步处理 *************/
                orderForMerchantApi.updateSynchronousOrder(merchantCode,
                        MapUtils.getString(unionUser, "supplier", ""));
                /**************** 由于业务部门的要求 这里要加入对当前商家订单的同步处理 *************/
            }
        } catch (Exception e) {
            logger.error( MessageFormat.format("商家：{0} | 同步商家订单异常.", merchantCode), e);
        }
    }

    /**
     * 跳转到单据打印订单详情页面
     */
    @RequestMapping("/toOrderDetails")
    public String toOrderDetails(String orderSubNo, HttpServletRequest request,
                                 ModelMap map, Query query) throws Exception {
        // 以上订单信息查询删除，现走接口查询
        OrderSub orderSub = orderApi.getOrderSubByOrderSubNo(orderSubNo, 2);
        if (null != orderSub) {
            map.put("expand", orderSub);
            MerchantOrderExpand  merchantExpand = orderForMerchantService.getMerchantOrderExpand(orderSubNo);
            if(null!=merchantExpand && null!=merchantExpand.getMarkNote() ){
            	 map.put("markNote",merchantExpand.getMarkNote().trim());
            }
            //信息加密
            OrderConsigneeInfo consigneeInfo = orderSub.getOrderConsigneeInfo();
            if(null != consigneeInfo){
            	String[] encryptResult = OrderUtil.encryptMobileAndAddressByDateAndStatus(consigneeInfo.getConsigneeAddress(),
            			consigneeInfo.getMobilePhone(), orderSub.getOrderStatus(),orderSub.getCreateTime());
            	consigneeInfo.setConsigneeAddress(encryptResult[0]);
            	consigneeInfo.setMobilePhone(encryptResult[1]);
            }
            
            map.put("orderConsigneeInfo", consigneeInfo);
            List<com.yougou.ordercenter.model.order.OrderDetail4sub> detailList = orderSub
                    .getOrderDetail4subs();
            if (CollectionUtils.isNotEmpty(detailList)) {
                for (com.yougou.ordercenter.model.order.OrderDetail4sub orderDetail4sub : detailList) {
                    orderDetail4sub.setTempCommodityUrl(commodityBaseApiService
                            .getFullCommodityPageUrl(orderDetail4sub
                                    .getCommodityNo()));
                }
            }
            map.put("detailList", detailList);
        }
        // 以上订单日志查询删除，现走接口查询
        List<OrderLog> log = orderApi.getOrderLogsByOrderSubNo(orderSubNo);
        map.put("log", log);
        // 订单发退货记录
        // 查询该订单的出库记录
//        List<Object[]> outdetail = new ArrayList<Object[]>();
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        Object[] outdetails = new Object[8];
        outdetails[0] = orderSub.getWarehouseCodeName();
        outdetails[1] = orderSub.getShipTime();
        outdetails[2] = merchantCode;
        outdetails[3] = orderSub.getExpressOrderId();
        outdetails[4] = orderSub.getActualPostage().toString();
        outdetails[5] = orderSub.getLogisticsName();
        outdetails[6] = orderSub.getLogisticsCode();
        outdetails[7] = orderSub.getBuyReductionPrefAmount();
//        outdetail.add(outdetails);
//        map.put("outdetail", outdetail);
        map.put("outdetails", outdetails);// add by LQ.
        // 订单售后
        QualityVo qualityVo = new QualityVo();
        qualityVo.setMerchantCode(merchantCode);
        qualityVo.setOrderNo(orderSubNo);
        List<OrderProductQuantityVo> asmInfoList = qualityService
                .queryOrderAsmInfo(qualityVo);
        List<ReturnAndRejectionBean> returnAndRejectionList = null;
        Date returnAndRejectionTime = null;
        if (asmInfoList != null && asmInfoList.size() > 0) {
            Map<String, ReturnAndRejectionBean> returnAndRejectionMap = new LinkedHashMap<String, ReturnAndRejectionBean>();
            ReturnAndRejectionBean bean = null;
            ReturnAndRejectionDetailBean bb = null;
            for (OrderProductQuantityVo main : asmInfoList) {
                bean = returnAndRejectionMap.get(main.getId());
                if (bean == null) {
                    bean = new ReturnAndRejectionBean();
                    bean.setApplyNo("123");
                    bean.setIndex(1);
                    bean.setExpressCharges(main.getExpress_charges());
                    bean.setExpressCode(main.getExpress_code());
                    bean.setExpressId("");
                    bean.setExpressName(main.getExpress_name());
                    bean.setId(main.getId());
                    bean.setOrderSubNo(main.getOrder_sub_no());
                    bean.setReturnAndRejectionDetailList(new ArrayList<ReturnAndRejectionDetailBean>());
                    returnAndRejectionMap.put(main.getId(), bean);
                }

                bb = new ReturnAndRejectionDetailBean();
                bb.setCommodityName(main.getCommodity_name());
                bb.setCommoditySize(main.getSize_name());
                Commodity commodity = commodityBaseApiService.getCommodityById(
                        main.getCommodity_id(), false, false, false);
                if (commodity != null) {
                    try {
                        bb.setProdUrl(commodityBaseApiService.getFullCommodityPageUrl(commodity
                                .getCommodityNo()));
                    } catch (Exception e) {
                        logger.error("通过cms接口获取单品页地址异常.", e);
                    }
                    bb.setPicUrl(commodity.getPicSmall());
                    bb.setSpecName(commodity.getColorName());
                    bb.setCommodityNo(commodity.getCommodityNo());
                }
                bb.setProductNo(main.getCommodity_code());
                bb.setDescription(main.getDescription());

                returnAndRejectionTime = main.getQa_date();
                bb.setQaDate(main.getQa_date());
                bb.setReason(main.getReason());
                bb.setResult(main.getIs_passed());
                bb.setType(main.getQuality_type());
                bb.setStatus(getApplyStatus(main.getShstatus()));
                bean.getReturnAndRejectionDetailList().add(bb);
            }
            returnAndRejectionList = new ArrayList<ReturnAndRejectionBean>();
            int index = 1;
            for (ReturnAndRejectionBean ra : returnAndRejectionMap.values()) {
                ra.setIndex(index++);
                returnAndRejectionList.add(ra);
            }
        }
        map.put("returnAndRejectionTime", returnAndRejectionTime);
        map.put("returnAndRejectionList", returnAndRejectionList);
        
        //增加日志
        String menuCode = request.getParameter("menuCode");
        String menuName = LogMenuEnum.getValue(menuCode);
        menuName = StringUtils.isBlank(menuName) ? "" : menuName;        
        saveOperationLog(request,UserConstant.OPERATION_TYPE_READ,
    			menuName, "查看订单详情", orderSubNo, "");
        return "manage_unless/order/order-detail";
    }

    private String getApplyStatus(String key) {
        if (key == null || "".equals(key)) {
            return "";
        }
        String name = "";
        try {
            name = SaleStatusEnum.valueOf(key).getName();
        } catch (Exception e) {
            logger.error("获取售后状态异常:", e);
        }
        return name;
    }
    /**
     * 物流跟踪信息 add by daixiaowei
     *
     * @param logisticsCompanyName
     * @param expressCode
     * @param logisticsCompanyCode
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectExpressLog")
    public String selectExpressLog(String expressCode,
                                   String logisticsCompanyCode, String orderSubNo) {
        // 将返回的json数据
        Map<String, Object> map = new HashMap<String, Object>();
        // 如果物流公司的名字和快递单号都不为空
        try {
            MemberLogisticsVo logisticsVo = logisticApi
                    .getMemberLogisticsByOrderSubNoAndSort(orderSubNo, "asc");
            if (null != logisticsVo) {
                map.put("data", logisticsVo.getData());
                map.put("logisticsTel", logisticsVo.getLogisticsTel());
                map.put("logisticsWeb", logisticsVo.getLogisticsWebsite());
            }
        } catch (MemberException e) {
            logger.error(
                    MessageFormat.format("订单号:{0}查询物流跟踪信息异常.", orderSubNo), e);
            map.put("error", orderSubNo + "物流跟踪信息查询异常!请重试");
        }

        return JSONObject.fromObject(map).toString();
    }

    /**
     * 导出单据打印 拣货清单
     *
     * @param request
     * @param response
     * @param orderSubId
     * @param orderSubExpand
     * @throws Exception
     */
    @RequestMapping("/doExportPrintOrder")
    public void doExportPrintOrder(HttpServletRequest request,
                                   HttpServletResponse response, String orderSubNos,
                                   OrderSubExpand orderSubExpand) throws Exception {
        getExportPrintOrder(request, response, orderSubNos, orderSubExpand,
                false);
    }
    /**
     * 导出单据打印 拣货清单(新)
     *
     * @param request
     * @param response
     * @param orderSubId
     * @param orderSubExpand
     * @throws Exception
     */
    @RequestMapping("/doExportPrintOrderNew")
    public void doExportPrintOrderNew(HttpServletRequest request,
                                      HttpServletResponse response, String orderSubNos,
                                      OrderSubExpand orderSubExpand) throws Exception {
        getExportPrintOrder(request, response, orderSubNos, orderSubExpand,
                true);
    }
    /**
     * 导出的公共方法
     *
     * @param request
     * @param response
     * @param orderSubId
     * @param orderSubExpand
     * @param isNewExport
     * @throws Exception
     */
    private void getExportPrintOrder(HttpServletRequest request,
                                     HttpServletResponse response, String orderSubNos,
                                     OrderSubExpand orderSubExpand, final boolean isNewExport)
            throws Exception {
        String[] orderSubNoArray = null;
        List<String> orderSubNoList = new ArrayList<String>();

        if (StringUtils.isBlank(orderSubNos)) {
            throw new Exception("请选择订单！");
        }

        Map<String, Object> unionUser = SessionUtil.getUnionUser(request);////
        if (unionUser == null) {
            throw new Exception("请先登录！");
        }
        String merchantCode = MapUtils.getString(unionUser, "supplier_code");
        String warehouseCode = MapUtils.getString(unionUser, "warehouse_code");
        if (StringUtils.isBlank(merchantCode)) {
            throw new Exception("商家编号不能为空！");
        }
        if (StringUtils.isBlank(warehouseCode)) {
            throw new Exception("商家仓库编号不能为空！");
        }

        // 查询导出数据
        orderSubNoArray = orderSubNos.split(",");
        for (int i = 0; i < orderSubNoArray.length; i++) {
            orderSubNoList.add(orderSubNoArray[i]);
        }

        logger.warn(MessageFormat.format(
                "调用订单系统拣货清单接口,输入参数:[商家编号:{0},仓库编号:{1},订单列表：{2}]", merchantCode,
                warehouseCode, orderSubNoList));
        List<QueryOrderPickOutputDto> orderPickList = orderForMerchantService
                .queryPcikingList(merchantCode, warehouseCode, orderSubNoList);
        if (CollectionUtils.isEmpty(orderPickList)) {
            throw new Exception("没有数据可导出");
        }
        try{        	
        	if (CollectionUtils.isNotEmpty(orderPickList)) {
        		//信息加密
        		for(QueryOrderPickOutputDto order : orderPickList){
        			String[] encryptResult = OrderUtil.encryptMobileAndAddressByDateAndStatus(order.getConsigneeAddress(),
        					order.getMobilePhone(), order.getOrderExportedStatus(), order.getCreateTime());
        			order.setConsigneeAddress(encryptResult[0]);
        			order.setMobilePhone(encryptResult[1]);
        		}
        	}
        }catch(Exception e){
        	logger.error("重要信息加密失败",e);
        }

        List<Object[]> objList = new ArrayList<Object[]>();

        Iterator<QueryOrderPickOutputDto> it = orderPickList.iterator();
        while (it.hasNext()) {
            QueryOrderPickOutputDto dto = (QueryOrderPickOutputDto) it.next();
            Object[] obj = new Object[20];
            obj[0] = DateUtil2.getDateTime(dto.getExportedDate());
            obj[1] = "已拣货";
            obj[2] = DateUtil2.getDateTime(dto.getCreateTime());
            obj[3] = dto.getOrderSubNo();

            if (isNewExport) {
                obj[4] = dto.getTotalPrice();
                obj[5] = dto.getDiscountAmount();
                obj[6] = dto.getUserName();
                obj[7] = dto.getMobilePhone();
                obj[8] = dto.getConsigneeArea();
                obj[9] = dto.getConsigneeAddress();
                obj[10] = dto.getZipCode();
                obj[11] = dto.getProdTotalAmt();
                obj[12] = dto.getMessage();
                obj[13] = dto.getProdName();
                obj[14] = dto.getSupplierCode();
                obj[15] = dto.getThirdPartyCode();
                obj[16] = dto.getInsideCode();
                obj[17] = dto.getBrandName();
                obj[18] = dto.getCommoditySpecificationStr();
                obj[19] = dto.getCommodityNum();
            } else {
                obj[4] = dto.getProdTotalAmt();
                obj[5] = dto.getMessage();
                obj[6] = dto.getProdName();
                obj[7] = dto.getSupplierCode();
                obj[8] = dto.getThirdPartyCode();
                obj[9] = dto.getInsideCode();
                obj[10] = dto.getBrandName();
                obj[11] = dto.getCommoditySpecificationStr();
                obj[12] = dto.getCommodityNum();
            }
            objList.add(obj);
        }

        // List<Object[]> objList =
        // expandService.queryPrintOrderExport(orderSubIds, unionUser,
        // isNewExport);// 应该要加入商家的信息
        //增加日志
        String menuName = UserConstant.MENU_DJDY;
        if(isNewExport){
        	menuName = UserConstant.MENU_DJDYX;
        }
        saveOperationLog(request,UserConstant.OPERATION_TYPE_OPERATE,
    			menuName, "导出拣货清单", "", objList.size()+"");

        if (objList != null && !objList.isEmpty()) {
            logger.error("拣货订单数量：{}" ,objList.size());
            try {
                String fileRealPath = request.getSession().getServletContext()
                        .getRealPath("/WEB-INF/template/");
                // 模板路径
                String templatePath = fileRealPath
                        + (isNewExport
                        ? "/print_order_new.xls"
                        : "/print_order.xls");
                String sheetName = "拣货清单";
                boolean isHadData = orderService.doExportOrder(objList,
                        templatePath, "print_order.xls", response, sheetName);
                if (!isHadData) {
                    throw new Exception("没有 数据可导出");
                } else {
                    // 状态置为导出 并记录备注
                    List<String> lstOrderSubNo = new ArrayList<String>();
                    for (Object[] obj : objList) {
                        if (obj != null && obj[3] != null) {
                            lstOrderSubNo.add(obj[3].toString());
                        }
                    }
                    // expandService.updateExport(lstOrderSubNo,
                    // DateUtil2.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
                    // unionUser);
                    //Date now = new Date();
                    orderForMerchantApi.updateExport(merchantCode,
                            lstOrderSubNo, new Date(),
                            unionUser.get("login_name").toString());
                    /*if(orderForMerchantApi.updateExport(merchantCode,
                            lstOrderSubNo, now,
                            unionUser.get("login_name").toString()))
                            {
                    	//解决延迟问题，修改招商库数据
                    	orderForMerchantService.updateExportStatus(merchantCode,lstOrderSubNo,now);
                    }else{
                    	logger.error("商家{}的账号{}，更新子订单{}的导出状态失败！",
                    			new Object[]{merchantCode,unionUser.get("login_name").toString(),
                    			StringUtils.join(lstOrderSubNo,",")});
                    }*/
                }
            } catch (Exception ex) {
                logger.error("导出订单失败！！",ex);
            }
        } else {
            throw new Exception("没有数据可导出");
        }
    }

    /**
     * 导出单据打印 缺货订单
     *
     * @param request
     * @param response
     * @param orderSubId
     * @param orderSubExpand
     * @throws Exception
     */
    @RequestMapping("/doExportOutStockOrder")
    public void doExportOutStockOrder(HttpServletRequest request,
                                      HttpServletResponse response, String orderSubNos) throws Exception {
        // 获得当前登录的商家
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        if (StringUtils.isBlank(merchantCode)) {
            throw new Exception("请先登录！");
        }
        if (ArrayUtils.isEmpty(orderSubNos.split(","))) {
            throw new Exception("没有数据可导出");
        }

        List<String> _orderSubNos = Arrays.asList(orderSubNos.split(","));
        MerchantOrderPrintInputDto dto = new MerchantOrderPrintInputDto();
        dto.setOrderSubNos(_orderSubNos);
        dto.setMerchantCode(merchantCode);
        dto.setWarehouseCode(SessionUtil.getWarehouseCodeFromSession(request));

        com.yougou.ordercenter.common.PageFinder<MerchantQueryOrderPrintOutputDto> pageFinder = orderForMerchantService
                .queryPrintList(dto);

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
            ExcelToDataModelConverter.createCell(row, cellIndex++,
                    DateUtil2.getDateTime(_dto.getCreateTime()));
            ExcelToDataModelConverter.createCell(row, cellIndex++,
                    ObjectUtils.toString(_dto.getOrderSubNo()));
            ExcelToDataModelConverter.createCell(row, cellIndex++,
                    ObjectUtils.toString(_dto.getTotalPrice()));
            ExcelToDataModelConverter.createCell(row, cellIndex++,
                    ObjectUtils.toString(_dto.getDiscountAmount()));
            ExcelToDataModelConverter.createCell(row, cellIndex++,
                    ObjectUtils.toString(_dto.getActualPostage()));
            ExcelToDataModelConverter.createCell(row, cellIndex++,
                    DateUtil2.getDateTime(_dto.getBackorderDate()));
            ExcelToDataModelConverter.createCell(row, cellIndex++,
                    _dto.getBaseStatusDesc());
        }

        OutputStream os = null;
        try {
            // 下载生成的模板
            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", MessageFormat.format(
                    "attachment; filename=out_of_orders_{0}.xlsx",
                    DateFormatUtils.format(new Date(), "yyyyMMdd")));
            os = response.getOutputStream();
            excel.write(os);
        } finally {
            IOUtils.closeQuietly(os);
        }
        logger.info("商家[{}]导出缺货订单数量：{} .", new Object[]{merchantCode,
                pageFinder.getData().size()});
        
        //增加日志
        String menuCode = request.getParameter("menuCode");
        String menuName = LogMenuEnum.getValue(menuCode);
        menuName = StringUtils.isBlank(menuName) ? "" : menuName;
        saveOperationLog(request,UserConstant.OPERATION_TYPE_OPERATE,
    			menuName, "导出缺货订单", "", pageFinder.getData().size()+"");
    }

    /********************************************************* 打包发货 *********************************************************/
    /**
     * 跳转到打包发货页面
     */
    @RequestMapping("/toPackageDelivery")
    public String toPackageDelivery(HttpServletRequest request, ModelMap map,
                                    Query query) {
        map.put("tagTab", "deliver-all");// 标识进入订单清单
        return "manage/order/send-order";
    }

    /**
     * 扫描发货订单号
     */
    @ResponseBody
    @RequestMapping("/scanningOrder")
    public String scanningOrder(HttpServletRequest request, String orderNo)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        com.yougou.ordercenter.model.order.OrderSub orderSub = null;

        try {
            if (StringUtils.isBlank(merchantCode)) {
                resultMap.put("msg", "获取不到商家编号,请重新登录!");
            } else if (StringUtils.isBlank(orderNo)
                    || (orderSub = orderForMerchantApi
                    .findOrderSubByOrderSubNoAndMerchantCode(orderNo,
                            merchantCode)) == null) {
                resultMap.put("msg", "您输入的订单号在系统中不存在!");
            } else if (StringUtils.isBlank(orderSub.getExpressOrderId())) {
                resultMap.put("msg", "该订单还没打印快递单!");
            } else if (orderSub.getBaseStatus() == com.yougou.ordercenter.constant.OrderConstant.BASE_SPECIAL_CANCEL) {
                resultMap.put("msg", "顾客要求取消该订单，请您停止发货!");
            } else if (orderSub.getBaseStatus() != com.yougou.ordercenter.constant.OrderConstant.BASE_CONFIRM
                    && orderSub.getBaseStatus() != com.yougou.ordercenter.constant.OrderConstant.BASE_UPDATE) {
                resultMap.put("msg", "该订单未确认或者已经打包发货了!");
            } else if (orderSub.getOrderSubExpand().getPrintShoppinglistCount() <= 0) {
                resultMap.put("msg", "该订单还没打印购物清单!");
            } else if (orderSub.getOrderSubExpand()
                    .getPrintLogisticslistCount() <= 0) {
                resultMap.put("msg", "该订单还没打印快递单!");
            } else if (CollectionUtils.isEmpty(orderSub.getOrderDetail4subs())) {
                resultMap.put("msg", "获取订单明细异常,请与我们招商部联系!");
            } else {
                if (orderSub.getBaseStatus() == com.yougou.ordercenter.constant.OrderConstant.BASE_UPDATE) {
                    if (orderForMerchantApi.getModifiedOrCanceledOrder(orderSub
                            .getOrderSubNo()) == null) {
                        // 已经同意客服的申请拦截，停止发货
                        resultMap.put("msg",
                                "顾客要求修改该订单信息，商家已经同意客服的申请拦截,请您停止发货!");
                        return JSONObject.fromObject(resultMap).toString();
                    } else {
                        // 尚未同意客服的申请拦截
                        resultMap.put("msg", "客服申请拦截");
                    }
                }
                List<OrderDetail4subWrapper> detail4subWrappers = new ArrayList<OrderDetail4subWrapper>();
                List<com.yougou.ordercenter.model.order.OrderDetail4sub> orderDetail4subs = orderSub
                        .getOrderDetail4subs();
                int prodCount = 0;
                for (com.yougou.ordercenter.model.order.OrderDetail4sub orderDetail4sub : orderDetail4subs) {
                    prodCount += orderDetail4sub.getCommodityNum();
                    //String thirdPartyCode = commodityBaseApiService.getProductByNo(orderDetail4sub.getProdNo()).getThirdPartyInsideCode();
                    OrderDetail4subWrapper orderDetail4subWrapper = new OrderDetail4subWrapper(
                            orderDetail4sub);
                    orderDetail4subWrapper.setThirdPartyCode(orderDetail4sub.getLevelCode());
                    detail4subWrappers.add(orderDetail4subWrapper);
                }

                Map<String, Object> orderSubInfoMap = new HashMap<String, Object>();
                orderSubInfoMap.put("orderSubNo", orderSub.getOrderSubNo());
                orderSubInfoMap.put("logisticsName",
                        orderSub.getLogisticsName());
                orderSubInfoMap.put("actualPostage",
                        orderSub.getActualPostage());
                orderSubInfoMap.put("expressOrderId",
                        orderSub.getExpressOrderId());
                orderSubInfoMap.put("prodCount", prodCount);
                resultMap.put("orderSub", orderSubInfoMap);
                resultMap.put("orderDetailslist", detail4subWrappers);
                resultMap.put("orderSubExpand", orderSub.getOrderSubExpand());
                resultMap.put("orderConsigneeInfo",
                        orderSub.getOrderConsigneeInfo());
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultMap.put("msg", "获取订单异常,请与我们招商部联系!");
        }

        return JSONObject.fromObject(resultMap).toString();
    }

    /**
     * 扫描货品条码
     */
    @ResponseBody
    @RequestMapping("/inspectGoods")
    public String inspectGoods(HttpServletRequest request, String orderNo,
                               String goodsCode) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        com.yougou.ordercenter.model.order.OrderSub orderSub = null;

        try {
            if (StringUtils.isBlank(merchantCode)) {
                resultMap.put("msg", "获取不到商家编号,请重新登录!");
            } else if (StringUtils.isBlank(orderNo)
                    || (orderSub = orderForMerchantApi
                    .findOrderSubByOrderSubNoAndMerchantCode(orderNo,
                            merchantCode)) == null) {
                resultMap.put("msg", "您输入的订单号在系统中不存在!");
            } else if (StringUtils.isBlank(goodsCode)) {
                resultMap.put("msg", "您输入的商品条码为空!");
            } else if (StringUtils.isBlank(orderSub.getExpressOrderId())) {
                resultMap.put("msg", "您还没录入快递单号!");
            } else if (orderSub.getBaseStatus() == com.yougou.ordercenter.constant.OrderConstant.BASE_SPECIAL_CANCEL) {
                resultMap.put("msg", "顾客要求取消该订单，请您停止发货!");
            }// else if (orderSub.getBaseStatus() ==
            // com.yougou.ordercenter.constant.OrderConstant.BASE_UPDATE) {
            // resultMap.put("msg", "顾客要求修改该订单信息，请您停止发货!");
            // }
            else if (CollectionUtils.isEmpty(orderSub.getOrderDetail4subs())) {
                resultMap.put("msg", "获取订单明细异常,请与我们招商部联系!");
            } else {
                if (orderSub.getBaseStatus() == com.yougou.ordercenter.constant.OrderConstant.BASE_UPDATE) {
                    if (orderForMerchantApi.getModifiedOrCanceledOrder(orderSub
                            .getOrderSubNo()) == null) {
                        // 已经同意客服的申请拦截，停止发货
                        resultMap.put("msg",
                                "顾客要求修改该订单信息，商家已经同意客服的申请拦截,请您停止发货!");
                        return JSONObject.fromObject(resultMap).toString();
                    } else {
                        // 尚未同意客服的申请拦截
                        resultMap.put("msg", "客服申请拦截");
                    }
                }
                List<com.yougou.ordercenter.model.order.OrderDetail4sub> orderDetail4subs = orderSub
                        .getOrderDetail4subs();
                for (com.yougou.ordercenter.model.order.OrderDetail4sub orderDetail4sub : orderDetail4subs) {
                    //String thirdPartyCode = commodityBaseApiService.getProductByNo(orderDetail4sub.getProdNo()).getThirdPartyInsideCode();
                    if (StringUtils.equals(goodsCode, orderDetail4sub.getLevelCode())) {
                        resultMap.put("thirdPartyCode", goodsCode);
                        break;
                    }
                }
                if (!resultMap.containsKey("thirdPartyCode")) {
                    resultMap.put("msg", "订单中没有商家货品条码为" + goodsCode + "的商品!");
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultMap.put("msg", "获取订单异常,请与我们招商部联系!");
        }

        return JSONObject.fromObject(resultMap).toString();
    }

    /**
     * 校验发货快递单
     */
    @ResponseBody
    @RequestMapping("/chexkExpress")
    public String chexkExpress(HttpServletRequest request, String orderNo,
                               String expressCode) throws Exception {
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        com.yougou.ordercenter.model.order.OrderSub orderSub = null;

        try {
            if (StringUtils.isBlank(merchantCode)) {
                return "获取不到商家编号,请重新登录!";
            } else if (StringUtils.isBlank(orderNo)
                    || (orderSub = orderForMerchantApi
                    .findOrderSubByOrderSubNoAndMerchantCode(orderNo,
                            merchantCode)) == null) {
                return "您输入的订单号在系统中不存在!";
            } else if (StringUtils.isBlank(expressCode)) {
                return "您输入的快递单号为空!";
            } else if (StringUtils.isBlank(orderSub.getExpressOrderId())) {
                return "您还没录入快递单号!";
            } else if (!StringUtils.equals(expressCode.trim(),
                    orderSub.getExpressOrderId())) {
            	//快递单号与打印的快递单号不一样时，考虑是否合并发货
            	//根据商家编码+快递单号查询订单
            	List<OrderSub> orderSubList = orderForMerchantApi.findOrderSubByMerchantCodeAndExpressOrderId(merchantCode,expressCode);
            	if (orderSubList != null && orderSubList.size() > 0) {
    				OrderSub order = orderSubList.get(0);
    				String expressInfo = this.getConsigneeInfoStr(order);
    				//收货人信息一致时
    				//判断发货时间是否在24小时之内
    				if (this.getConsigneeInfoStr(orderSub).equals(expressInfo)) {
    					Date date = order.getShipTime();
    					Date now = new Date();
    					long time = now.getTime()-date.getTime();
    					long h = time/(60*60*1000);
    					//System.out.println("相隔小时=="+h);
    					if(h>=24){
    						logger.warn("该快递单号已使用，合并发货时效不能超过24个小时！");
    						return "该快递单号已使用，合并发货时效不能超过24个小时！";
    					}else{
    						 boolean isSuccess = merchantOrderApi.orderOutStoreForMerchant(
    			                        merchantCode, orderNo, order.getLogisticsCode(),
    			                        order.getExpressOrderId(), now);
    			                logger.info(MessageFormat
    			                        .format("调用订单系统出库接口'{'输入参数:'{'订单号:{0},快递公司:{1},快递单号:{2}'}', 输出参数:{3}'}'",
    			                                orderNo, orderSub.getLogisticsCode(),
    			                                orderSub.getExpressOrderId(), isSuccess));
    			                try{
    			                	orderForMerchantService.updateOutStoreStatus(merchantCode,orderNo,now);
    			                }catch(Exception e){
    			                	logger.error(
    			                			MessageFormat.format("商家{0}发货订单{1}修改招商库订单状态失败：",new Object[]{merchantCode,orderNo})
    			                			,e);
    			                }
    			               return isSuccess ? "success" : "订单出库异常!";
    					}
    				}
    				/** 以合并之前的发货时间为准  
    				if(orderSub.getShipTime()!=null){
    					outShopDate = orderSub.getShipTime();
    				}*/
    			}
                return "验证失败,请检查您的快递单是否和此发货订单匹配!";
            } else if (orderSub.getBaseStatus() == com.yougou.ordercenter.constant.OrderConstant.BASE_SPECIAL_CANCEL) {
                return "顾客要求取消该订单，请您停止发货!";
            } else if (orderSub.getBaseStatus() == com.yougou.ordercenter.constant.OrderConstant.BASE_UPDATE
                    && orderForMerchantApi.getModifiedOrCanceledOrder(orderNo) == null) {
                return "顾客要求修改该订单信息，请您停止发货!";
            } else {
                // 新订单发货接口
            	Date now = new Date();
                boolean isSuccess = merchantOrderApi.orderOutStoreForMerchant(
                        merchantCode, orderNo, orderSub.getLogisticsCode(),
                        orderSub.getExpressOrderId(), now);
                logger.info(MessageFormat
                        .format("调用订单系统出库接口'{'输入参数:'{'订单号:{0},快递公司:{1},快递单号:{2}'}', 输出参数:{3}'}'",
                                orderNo, orderSub.getLogisticsCode(),
                                orderSub.getExpressOrderId(), isSuccess));
                try{
                	orderForMerchantService.updateOutStoreStatus(merchantCode,orderNo,now);
                }catch(Exception e){
                	logger.error(MessageFormat.format("商家{0}发货订单{1}修改招商库订单状态失败：",new Object[]{merchantCode,orderNo})
                	,e);
                }
                return isSuccess ? "success" : "订单出库异常!";
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return "订单出库异常,请与我们招商部联系!";
        }
    }

    /**
     * 快递单打印 选择物流公司 支持批量
     *
     * @param logisticsId
     *            物流公司id
     * @param orderNo订单号
     */
    @RequestMapping("/choosePrintExpressTemplate")
    public String choosePrintExpressTemplate(ModelMap map, String logisticsId,
                                             String orderNo, String orderNos, Integer orderCount,
                                             HttpServletRequest request) throws Exception {
        // 查询所有物流公司列表信息
        List<Map<String, Object>> logisticscompanList = merchantUsers
                .getLogisticscompanList();
        map.put("logisticscompanList", logisticscompanList);

        map.put("orderNo", orderNo);
        map.put("orderNos", orderNos);
        map.put("orderCount", orderCount);
        map.put("logisticsId", logisticsId);

        // 快递单模版信息
        Map<String, Object> template = null;
        String backGroundImage = "";
        // 根据物流公司Id获取快递单模版信息
        if (StringUtils.isNotBlank(logisticsId)) {
            template = merchantUsers
                    .getExpressTemplateByLogisticsId(logisticsId);
            if (null != template) {
                backGroundImage = "http://http://i1.ygimg.cn/pics/merchant/img/"
                        + template.get("back_bround_image").toString();
            } else {
                backGroundImage = null;
                map.put("error", "该物流公司快递单模版未设置！");
                return "manage_unless/order/print-express";
            }

            // 商家信息
            // 省市区
            String province = "";
            String city = "";
            String shipmentsArea = "";
            // 根据商家id查询商家发货地址信息
            Map<String, Object> consignmentadressMap = merchantUsers
                    .getMerchantConsignmentadress(request);
            if (consignmentadressMap != null && consignmentadressMap.size() > 0) {
                Object areaObject = consignmentadressMap.get("area");
                if (areaObject != null) {
                    String[] area = areaObject.toString().split("-");
                    province = area[0];
                    city = area[1];
                    shipmentsArea = area[2];
                }

                // 多个快递单html
                List<String> expressTemplateList = new ArrayList<String>();
                // 收货人信息
                String[] arrOrderNos = orderNo.split(",");
                for (int i = 0; i < arrOrderNos.length; i++) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    int consigneeYear = c.get(Calendar.YEAR);
                    int consigneeMonth = c.get(Calendar.MONTH) + 1;
                    int consigneeDay = c.get(Calendar.DAY_OF_MONTH);
                    Map<String, Object> orderMap = new HashMap<String, Object>();
                    if (StringUtils.isNotBlank(arrOrderNos[i])) {
                        // 根据订单号查询商家的收货人相关的信息
                        orderMap = merchantUsers.getOrderInfo(arrOrderNos[i],
                                request);
                    }
                    // 快递单html
                    String html = MapUtils.getString(template, "tbody", "");

                    html = html
                            .replace(
                                    "发货人姓名",
                                    consignmentadressMap
                                            .get("consignment_name") != null
                                            ? consignmentadressMap.get(
                                            "consignment_name")
                                            .toString() : "");
                    html = html.replace("订单号",
                            orderMap.get("order_sub_no") != null ? orderMap
                                    .get("order_sub_no").toString() : "");
                    html = html.replace("发货1级地区", province);
                    html = html.replace("发货2级地区", city);
                    html = html.replace("发货3级地区", shipmentsArea);
                    html = html.replace("发货人地址",
                            consignmentadressMap.get("adress") != null
                                    ? consignmentadressMap.get("adress")
                                    .toString() : "");
                    html = html.replace("发货人手机",
                            consignmentadressMap.get("phone") != null
                                    ? consignmentadressMap.get("phone")
                                    .toString() : "");
                    html = html.replace("发货人电话",
                            consignmentadressMap.get("tell") != null
                                    ? consignmentadressMap.get("tell")
                                    .toString() : "");
                    html = html.replace("发货人邮编", consignmentadressMap
                            .get("post_code") != null ? consignmentadressMap
                            .get("post_code").toString() : "");
                    html = html.replace(
                            "收货人姓名",
                            orderMap.get("user_name") != null ? orderMap.get(
                                    "user_name").toString() : "");
                    html = html.replace(
                            "收货人1级地区",
                            orderMap.get("province") != null ? orderMap.get(
                                    "province").toString() : "");
                    html = html.replace("收货人2级地区", orderMap.get("city") != null
                            ? orderMap.get("city").toString()
                            : "");
                    html = html.replace("收货人3级地区", orderMap.get("area") != null
                            ? orderMap.get("area").toString()
                            : "");
                    html = html.replace("收货人地址",
                            orderMap.get("consignee_address") != null
                                    ? orderMap.get("consignee_address")
                                    .toString() : "");
                    html = html.replace("收货人手机",
                            orderMap.get("mobile_phone") != null ? orderMap
                                    .get("mobile_phone").toString() : "");
                    html = html.replace("收货人电话",
                            orderMap.get("constact_phone") != null ? orderMap
                                    .get("constact_phone").toString() : "");
                    html = html.replace(
                            "收货人邮编",
                            orderMap.get("zip_code") != null ? orderMap.get(
                                    "zip_code").toString() : "");

                    html = html
                            .replace("当日日期-年", String.valueOf(consigneeYear));
                    html = html.replace("当日日期-月",
                            String.valueOf(consigneeMonth));
                    html = html.replace("当日日期-日", String.valueOf(consigneeDay));
                    html = html.replace("发货商家数量", orderMap
                            .get("product_send_quantity") != null ? orderMap
                            .get("product_send_quantity").toString() : "");
                    html = html.replace("订单来源",
                            MapUtils.getString(orderMap, "out_shop_name"));
                    html = html.replace(
                            "收款金额",
                            orderMap.get("total_price") != null ? orderMap.get(
                                    "total_price").toString() : "");
                    html = html.replace("订单备注", orderMap.get("message") != null
                            ? orderMap.get("message").toString()
                            : "");
                    if (template.containsKey("number")
                            && template.get("number").toString() != "") {
                        html = html.replace("对号", "√");
                    }
                    html = html.replace("×", "");// 去掉x
                    if (StringUtils.isNotBlank(backGroundImage)) {
                        html = "<div id='Box' style='position:relative;width:900px;height:550px;background-repeat:no-repeat;background-image:url("
                                + backGroundImage + ")'>" + html + "</div>";
                    } else {
                        html = "<div id='Box' style='position:relative;width:900px;height:550px;'>"
                                + html + "</div>";
                    }
                    expressTemplateList.add(html);
                }
                map.addAttribute("expressTemplateList", expressTemplateList);
            } else {
                map.put("error", "商家发货信息不完整！");
                return "manage_unless/order/print-express";
            }
        } else {
            map.put("error", "物流公司为空！");
            return "manage_unless/order/print-express";
        }
        return "manage_unless/order/print-express";
    }

    @RequestMapping("/toPrintExpressTemplateAjax.sc")
    public String choosePrintExpressTemplateAjax(ModelMap map, String orderNos,
                                                 HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(orderNos)) {
            map.put("error", "请选择需要打印的单据");
            return "manage_unless/order/print-expressNew";
        }
		map.put("logisticscompanList", merchantUsers.getLogisticscompanList());
        if(orderNos.endsWith(",")){
        	map.put("orderNos", orderNos.substring(0, orderNos.length()-1));
        }else{
        	map.put("orderNos", orderNos);
        }
        map.put("orderCount", orderNos.split(",").length);
        return "manage_unless/order/print-expressNew";
    }

    /**
     * 校验快递单是否已被使用过
     *
     * @param expressNos快递单号
     * @param orderNos订单号
     */
    private String checkExpressNosExist(String[] orderNos, String[] expressNos, String merchantCode) {
        String msg = "";
        for (int i = 0; i < expressNos.length; i++) {
            // 当前输入框内是否有重复的快递单号的校验
            if (i > 0) {
                for (int j = 0; j < i; j++) {
                    if (expressNos[j].equals(expressNos[i]) && !checkConsigneeInfoIsSame(orderNos[j], orderNos[i], merchantCode)) {
                        msg = expressNos[i] + "一个快递单号不允许对应多个订单,请重新录入!";
                        return msg;
                    }
                }
            }
            // 检验历史订单中是否有快递单号与当前快递单号重复
            msg = checkExpressNosExist(orderNos[i], expressNos[i], merchantCode);
            if (StringUtils.isNotEmpty(msg)) {
                return msg;
            }
        }
        return msg;
    }

    /**
     * 校验快递单是否已被使用过
     *
     * @param expressNo快递单号
     * @param orderNo订单号
     */
    private String checkExpressNosExist(String orderNo, String expressNo, String merchantCode) {
        String msg = "";
        List<OrderSub> orderSubs = orderForMerchantApi.findOrderSubByMerchantCodeAndExpressOrderId(merchantCode, expressNo);
        if (CollectionUtils.isNotEmpty(orderSubs)) {
            for (OrderSub orderSub : orderSubs) {
                if (!orderNo.equals(orderSub.getOrderSubNo())) {
                    OrderSub _orderSub = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(orderNo, merchantCode);
                    if (!getConsigneeInfoStr(orderSub).equals(getConsigneeInfoStr(_orderSub))) {
                        msg = expressNo + "快递单已经打印，不允许再次使用!";
                        break;
                    }
                }
            }
        }

        return msg;
    }

    /**
     * 比较2个订单的收货人信息是否一致
     *
     * @param orderSub
     * @return
     */
    private Boolean checkConsigneeInfoIsSame(String orderSubNo_1, String orderSubNo_2, String merchantCode) {
        OrderSub orderSub_1 = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(orderSubNo_1, merchantCode);
        OrderSub orderSub_2 = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(orderSubNo_2, merchantCode);
        return getConsigneeInfoStr(orderSub_1).equals(getConsigneeInfoStr(orderSub_2));
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

    @ResponseBody
    @RequestMapping("/getPrintExpressTemplateInfoAjax.sc")
    public String getPrintExpressTemplateInfoAjax(ModelMap map,
                                                  String expressNos, String logisticsId, String logisticsName,
                                                  String logisticsCode, String orderNos, String connection,
                                                  HttpServletRequest request) throws Exception {
        // 根据物流公司Id获取快递单模版信息
        JSONObject json = new JSONObject();
        if (StringUtils.isBlank(logisticsId)) {
            json.put("result", ResultEnums.FAIL.getResultMsg());
            json.put("msg", "物流公司为空！");
            return json.toString();
        }
        if (StringUtils.isBlank(orderNos)) {
            json.put("result", ResultEnums.FAIL.getResultMsg());
            json.put("msg", "单号信息不能为空");
            return json.toString();
        }
        if (StringUtils.isBlank(expressNos)) {
            json.put("result", ResultEnums.FAIL.getResultMsg());
            json.put("msg", "快递单号不能为空");
            return json.toString();
        }

        logisticsName = URLDecoder.decode(logisticsName, "UTF-8");
        if (expressNos != null)
            expressNos = URLDecoder.decode(expressNos, "UTF-8");
        if (expressNos != null)
            expressNos = URLDecoder.decode(expressNos, "UTF-8");

        // 获取商家编码
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();

        // 要关联的订单号

        String[] arrOrderNos = orderNos.split(",");
        String[] arrExpressNos = null;
        if (CommonUtil.IsChinese(expressNos)) {
            json.put("result", ResultEnums.FAIL.getResultMsg());
            json.put("msg", "快递单号不能是中文！");
            return json.toString();
        }
        String backGroundImage = "";
        Map<String, Object> template = merchantUsers.getExpressTemplateByLogisticsId(logisticsId);
        if (null != template) {
            backGroundImage = "http://img.yougou.com/pics/merchant/img/"
                    + template.get("back_bround_image").toString();
            json.put("backGroundImage", backGroundImage);
            if (String.valueOf(template.get("tbody")) != null
                    && String.valueOf(template.get("tbody")).contains("LODOP")) {
                json.put("backGroundImage", backGroundImage);
                json.put("result", ResultEnums.FAIL.getResultMsg());
                json.put("msg", "模板设置不完整！");
            }
        } else {
            json.put("result", ResultEnums.FAIL.getResultMsg());
            json.put("msg", "该物流公司快递单模版未设置！");
            return json.toString();
        }
        // 保存订单号，快递单号，快递公司编码信息，以便调用wms接口更新订单物流信息
        List<OrderNoExpressCodeVo> OrderNoExpressCodeList = new ArrayList<OrderNoExpressCodeVo>();
        if (arrOrderNos.length > 1) {
            if (CONNECTION0.equals(connection)) {// 连号
                if (expressNos.length() < 3) {
                    json.put("result", ResultEnums.FAIL.getResultMsg());
                    json.put("msg", "快递单号长度必须大于等于3！");
                    return json.toString();
                }
                if ( expressNos.split(",").length>1) {
                    json.put("result", ResultEnums.FAIL.getResultMsg());
                    json.put("msg", "连号模式下只准输入第一个快递单号！");
                    return json.toString();
                }
                String expressNum = expressNos
                        .substring(expressNos.length() - 3);
                arrExpressNos = new String[arrOrderNos.length];
                for (int i = 0; i < arrOrderNos.length; i++) {
                    arrExpressNos[i] = expressNos.substring(0, expressNos.length() - 3) + String.format("%03d", Integer.parseInt(expressNum) + i);
                }

            } else { // 非连号
                arrExpressNos = expressNos.split(",");
                if (arrExpressNos.length != arrOrderNos.length) {
                    json.put("result", ResultEnums.FAIL.getResultMsg());
                    json.put("msg", "录入的快递单个数与您选择的订单个数不匹配！");
                    return json.toString();
                }

            }
            String resultMsg = checkExpressNosExist(arrOrderNos, arrExpressNos, merchantCode);
            if (StringUtils.isNotEmpty(resultMsg)) {
                json.put("result", ResultEnums.FAIL.getResultMsg());
                json.put("msg", resultMsg);
                return json.toString();
            }

            for (int i = 0, length = arrExpressNos.length; i < length; i++) {
                String orderSubNo = arrOrderNos[i];
                boolean opt = orderForMerchantApi
                        .updateAssociateExpressCode(merchantCode,
                                orderSubNo, arrExpressNos[i],
                                logisticsName, logisticsCode);
                String infoMsg = (opt) ? "成功" : "失败";
                logger.info("订单{}写入快递单号{}. {} " , new Object[]{arrOrderNos[i] ,
                         arrExpressNos[i] , infoMsg});
                if(opt){
                	//解决可能的延迟，主动修改招商库的快递单打印次数
                	orderForMerchantService.updatePrintExpressSize(merchantCode, orderSubNo);
                }
                //保存订单号，快递单号，物流公司编码
                OrderNoExpressCodeVo oe = new OrderNoExpressCodeVo();
                oe.setExpressCode(arrExpressNos[i]);
                oe.setLogisticsCompanyCode(logisticsCode);
                oe.setOrderCode(orderSubNo);
                OrderNoExpressCodeList.add(oe);
            }
        } else {
            arrExpressNos = new String[1];
            if (expressNos.length() < 3) {
                json.put("result", ResultEnums.FAIL.getResultMsg());
                json.put("msg", "快递单号长度必须大于等于3！");
                return json.toString();
            }
            arrExpressNos[0] = expressNos;

            String resultMsg = checkExpressNosExist(orderNos, expressNos, merchantCode);
            if (StringUtils.isNotEmpty(resultMsg)) {
                json.put("result", ResultEnums.FAIL.getResultMsg());
                json.put("msg", resultMsg);
                return json.toString();
            }
            boolean opt = orderForMerchantApi.updateAssociateExpressCode(
                    merchantCode, orderNos, expressNos, logisticsName,
                    logisticsCode);
            logger.info("订单{}写入快递单号{}. {}" ,new Object[]{ orderNos , expressNos ,
                    ((opt) ? "成功" : "失败")});
            if(opt){
            	//解决可能的延迟，主动修改招商库的快递单打印次数
            	orderForMerchantService.updatePrintExpressSize(merchantCode, orderNos);
            }
            //保存订单号，快递单号，物流公司编码
            OrderNoExpressCodeVo oe = new OrderNoExpressCodeVo();
            oe.setExpressCode(expressNos);
            oe.setLogisticsCompanyCode(logisticsCode);
            oe.setOrderCode(orderNos);
            OrderNoExpressCodeList.add(oe);
        }
     
        //调用wms接口更新订单物流信息
        updateOrderLogisticInfoByWms(OrderNoExpressCodeList);

        // 商家信息
        // 省市区
        String province = "";
        String city = "";
        String shipmentsArea = "";
        // 根据商家id查询商家发货地址信息
        Map<String, Object> consignmentadressMap = merchantUsers
                .getMerchantConsignmentadress(request);
        if (consignmentadressMap == null || consignmentadressMap.size() == 0) {
            json.put("result", ResultEnums.FAIL.getResultMsg());
            json.put("msg", "请先设置发货地址！<br/><br/> <a onclick=\"location.href='/merchants/login/to_merchant_consignmentAdress_list.sc'\" style='text-decoration:underline;'>单击此处前往设置</a>");
            return json.toString();
        }
        Object areaObject = consignmentadressMap.get("area");
        if (areaObject != null) {
            String[] area = areaObject.toString().split("-");
            province = area[0];
            city = area[1];
            shipmentsArea = area[2];
        }
        json.put("province", province);
        json.put("city", city);
        json.put("shipmentsArea", shipmentsArea);
        json.put("sendInfo", consignmentadressMap);
        // 多个快递单信息
        List<String> expressTemplateList = new ArrayList<String>();
        // 收货人信息

        for (int i = 0; i < arrOrderNos.length; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            int consigneeYear = c.get(Calendar.YEAR);
            int consigneeMonth = c.get(Calendar.MONTH) + 1;
            int consigneeDay = c.get(Calendar.DAY_OF_MONTH);
            Map<String, Object> orderMap = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(arrOrderNos[i])) {
                // 根据订单号查询商家的收货人相关的信息
                orderMap = merchantUsers.getOrderInfo(arrOrderNos[i], request);
            }
            // 快递单html
            String html = MapUtils.getString(template, "tbody", "");

            html = html.replace("发货人姓名", consignmentadressMap
                    .get("consignment_name") != null ? consignmentadressMap
                    .get("consignment_name").toString() : "");
            html = html.replace("订单号", orderMap.get("order_sub_no") != null
                    ? orderMap.get("order_sub_no").toString()
                    : "");
            html = html.replace("发货1级地区", province);
            html = html.replace("发货2级地区", city);
            html = html.replace("发货3级地区", shipmentsArea);
            html = html.replace("发货人地址",
                    consignmentadressMap.get("adress") != null
                            ? consignmentadressMap.get("adress").toString()
                            : "");
            html = html.replace("发货人手机",
                    consignmentadressMap.get("phone") != null
                            ? consignmentadressMap.get("phone").toString()
                            : "");
            html = html.replace("发货人电话",
                    consignmentadressMap.get("tell") != null
                            ? consignmentadressMap.get("tell").toString()
                            : "");
            html = html.replace("发货人邮编",
                    consignmentadressMap.get("post_code") != null
                            ? consignmentadressMap.get("post_code").toString()
                            : "");
            html = html.replace("收货人姓名", orderMap.get("user_name") != null
                    ? orderMap.get("user_name").toString()
                    : "");
            html = html.replace("收货人1级地区", orderMap.get("province") != null
                    ? orderMap.get("province").toString()
                    : "");
            html = html.replace("收货人2级地区", orderMap.get("city") != null
                    ? orderMap.get("city").toString()
                    : "");
            html = html.replace("收货人3级地区", orderMap.get("area") != null
                    ? orderMap.get("area").toString()
                    : "");
            html = html.replace(
                    "收货人地址",
                    orderMap.get("consignee_address") != null ? orderMap.get(
                            "consignee_address").toString() : "");
            html = html.replace("收货人手机", orderMap.get("mobile_phone") != null
                    ? orderMap.get("mobile_phone").toString()
                    : "");
            html = html.replace("收货人电话", orderMap.get("constact_phone") != null
                    ? orderMap.get("constact_phone").toString()
                    : "");
            html = html.replace("收货人邮编", orderMap.get("zip_code") != null
                    ? orderMap.get("zip_code").toString()
                    : "");

            html = html.replace("当日日期-年", String.valueOf(consigneeYear));
            html = html.replace("当日日期-月", String.valueOf(consigneeMonth));
            html = html.replace("当日日期-日", String.valueOf(consigneeDay));
            html = html.replace("发货商家数量",
                    orderMap.get("product_send_quantity") != null ? orderMap
                            .get("product_send_quantity").toString() : "");
            html = html.replace("订单来源",
                    MapUtils.getString(orderMap, "out_shop_name"));
            html = html.replace("收款金额", orderMap.get("total_price") != null
                    ? orderMap.get("total_price").toString()
                    : "");
            html = html.replace("订单备注", orderMap.get("message") != null
                    ? orderMap.get("message").toString()
                    : "");
            if (template.containsKey("number")
                    && template.get("number").toString() != "") {
                html = html.replace("对号", "√");
            }
            html = html.replace("barcode", arrExpressNos[i]);
            html = html.replace("条码编号", arrExpressNos[i]);

            html = html.replaceAll("(.*?)src='(.*?)'", "$1src='"
                    + backGroundImage + "'");
            // 将LODOP.PRINT_INIT("快递单打印") 这一段截取掉，否则分页打印出不来;
            // 将图片背景截取掉，在页面中再拼接
            String[] htmls = html.split(";");
            StringBuilder htmlBud = new StringBuilder();
            for (String str : htmls) {
                if (str.contains("PRINT_INIT")) {// 去掉init
                    continue;
                }
                htmlBud.append(str).append(";");
            }
            expressTemplateList.add(htmlBud.toString());
        }
        json.put("result", ResultEnums.SUCCESS.getResultMsg());
        json.put("expressTemplateList", expressTemplateList);
        return json.toString();
    }
    
    /**
     * 调用wms接口更新订单物流信息
     * @param OrderNoExpressCodeList
     */
	private void updateOrderLogisticInfoByWms(
			List<OrderNoExpressCodeVo> OrderNoExpressCodeList) {
		try{
	        // 调用wms接口更新订单物流信息
	        List<OrderUpdateStatusVo> results= orderOutStoreDomainService.updateLogisticsCompany(OrderNoExpressCodeList);
	        // 循环返回结果，输出成功失败订单日志
	        if(results!=null && results.size()>0){
		        for(OrderUpdateStatusVo vo : results){
		        	if(vo.getStatus()!=null && vo.getStatus() ==1 ){
		        		logger.info("订单：{}调用wms orderOutStoreDomainService.updateLogisticsCompany 接口更新物流信息成功 ",vo.getOrderNo());
		        	}else{
		        		logger.error("订单：{}调用wms orderOutStoreDomainService.updateLogisticsCompany 接口更新物流信息失败！",vo.getOrderNo());
		        	}
		        }
	        }
        }catch(Exception e){
        	logger.error("调用wms orderOutStoreDomainService.updateLogisticsCompany 异常：",e);
        }
	}
    
    /**
     * 打印快递单 订单与快递单建立关联
     *
     * @param expressId
     * @param expressIdCount
     * @param expressIds
     * @param orderNo
     *            多个订单号 逗号隔开
     * @return
     */
    @ResponseBody
    @RequestMapping("/printExpressTemplate")
    public String printExpressTemplate(String expressId, String expressIdCount,
                                       String expressIds, String orderNo, String logisticsName,
                                       String logisticsCode, HttpServletRequest request) throws Exception {
        logisticsName = URLDecoder.decode(logisticsName, "UTF-8");
        if (expressId != null)
            expressId = URLDecoder.decode(expressId, "UTF-8");
        if (expressIds != null)
            expressIds = URLDecoder.decode(expressIds, "UTF-8");

        // 获取商家编码
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();

        // 要关联的订单号
        String[] orderNos = orderNo.split(",");
        if (StringUtils.isNotBlank(expressId)
                && StringUtils.isNotBlank(expressIdCount)) {
            if (CommonUtil.IsChinese(expressId)) {
                return "快递单号不能是中文！";
            }
            if (!CommonUtil.isNumeric(expressIdCount)) {
                return "张数必须为数字！";
            } else {
                if (orderNos.length != Integer.parseInt(expressIdCount)) {
                    return "您输入的快递单张数与您选择的订单号个数不匹配！";
                } else {
                    // 检验快递单号是否已经使用
                    String[] expressIdArray = expressId.split(",");
                    if (iQualityService.isHadExistsOutStoreRecord(Arrays
                            .asList(expressIdArray))) {
                        return "您输入的快递单号已存在发货信息，请重新输入快递单号！";
                    }
                    if (Integer.parseInt(expressIdCount) != 1) {
                        String expressNum = expressId.substring(expressId
                                .length() - 3);
                        if (!CommonUtil.isNumeric(expressNum)) {
                            return "您输入的快递单号不满足连号输入的条件！请选择输入全部快递单号！";
                        } else {
                            for (int i = 0; i < Integer
                                    .parseInt(expressIdCount); i++) {
                                String expressCode = expressId.substring(0,
                                        expressId.length() - 3)
                                        + String.format("%03d",
                                        Integer.parseInt(expressNum)
                                                + i);
                                String orderSubNo = orderNos[i];
                                // expandService.updateAssociateExpressCode(orderSubNo,
                                // expressCode, logisticsName, logisticsCode);
                                boolean opt = orderForMerchantApi
                                        .updateAssociateExpressCode(
                                                merchantCode, orderSubNo,
                                                expressCode, logisticsName,
                                                logisticsCode);
                                logger.info("订单{}写入快递单号{}. {}" ,new Object[]{ orderNos[i] 
                                        , expressCode  ,((opt) ? "成功" : "失败")});
                            }
                        }
                    } else {
                        // expandService.updateAssociateExpressCode(orderNo,
                        // expressId, logisticsName, logisticsCode);
                        boolean opt = orderForMerchantApi
                                .updateAssociateExpressCode(merchantCode,
                                        orderNo, expressId, logisticsName,
                                        logisticsCode);
                        logger.info("订单{}写入快递单号{}. {}" ,new Object[]{  orderNo , expressId, ((opt) ? "成功" : "失败")});
                    }
                }
            }
        } else if (StringUtils.isNotBlank(expressIds)) {
            if (CommonUtil.IsChinese(expressIds)) {
                return "快递单号不能是中文！";
            }
            String[] expressIdArray = expressIds.split(",");
            if (orderNos.length != expressIdArray.length) {
                return "您输入的快递单个数与您选择的订单号个数不匹配！";
            } else {
                // 检验快递单号是否已经使用
                if (iQualityService.isHadExistsOutStoreRecord(Arrays
                        .asList(expressIdArray))) {
                    return "您输入的快递单号已存在发货信息，请重新输入快递单号！";
                }
                logger.error("批量不连打订单号：{}快递单号：{} " , orderNo , expressIds);
                for (int i = 0; i < orderNos.length; i++) {
                    String expressCode = expressIdArray[i];
                    String orderSubNo = orderNos[i];
                    // expandService.updateAssociateExpressCode(orderSubNo,
                    // expressCode,logisticsName,logisticsCode);
                    boolean opt = orderForMerchantApi
                            .updateAssociateExpressCode(merchantCode,
                                    orderSubNo, expressCode, logisticsName,
                                    logisticsCode);
                    logger.info("订单{}写入快递单号{}. {}",new Object[]{ orderNos[i] , expressCode,
                            ((opt) ? "成功" : "失败")});
                }
            }
        } else {
            return "请输入快递单号！";
        }
        return "success";
    }

    /**
     * lodop打印获取打印信息
     *
     * @param modelMap
     * @param orderNos
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/toBatchPrintShoppingListTemplateAjax.sc")
    public String toBatchPrintShoppingListTemplateAjax(ModelMap modelMap,
                                                       String orderNos, HttpServletRequest request) throws Exception {
        JSONObject json = new JSONObject();
        try {
            if (StringUtils.isBlank(orderNos)) {
                json.put("result", ResultEnums.FAIL.getResultMsg());
                json.put("msg", "请选择需要打印的单据！");
            }
            // 获取商家编码
            String merchantCode = YmcThreadLocalHolder.getMerchantCode();
            if (StringUtils.isNotBlank(merchantCode)) {
                RejectedAddressVo merchantRejectedAddress = supplierService
                        .getSupplierRejectedAddress(merchantCode);
                json.put("merchantRejectedAddress", merchantRejectedAddress);
            }
            // 查询当前订单的购物清单信息
            List<String> _orderNos = Arrays.asList(orderNos.split(","));
            List<OrderSub4Print> orderSubs = new ArrayList<OrderSub4Print>();
            OrderSub4Print orderSub4Print = null;
			Map<String, String> hotLine = orderApi.getAllHotline();
			for (String _orderNo : _orderNos) {
				OrderSub _order = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(_orderNo, merchantCode);
				orderSub4Print = new OrderSub4Print();
				orderSub4Print.setDiscountAmount(_order.getDiscountAmount());
				orderSub4Print.setOrderPayTotalAmont(_order.getOrderPayTotalAmont());
				orderSub4Print.setOrderConsigneeInfo(_order.getOrderConsigneeInfo());
				orderSub4Print.setCreateTime(_order.getCreateTime());
				orderSub4Print.setOrderSubNo1(StringUtils.substringBefore(_order.getOrderSourceNo(), "-"));
				orderSub4Print.setOrderSubNo2(StringUtils.substringBeforeLast(_order.getOrderSourceNo(), "-"));
				orderSub4Print.setOrderSubNo3(_order.getOrderSourceNo());
				orderSub4Print.setHotLine(gethotline(hotLine, _order.getOrderSourceNo()));
				orderSub4Print.setOrderSourceNo(_order.getOrderSourceNo());
				orderSub4Print.setOrderSubNo(_order.getOrderSubNo());
				orderSub4Print.setProductTotalQuantity(_order.getProductTotalQuantity());
				orderSub4Print.setOrderDetail4subs(_order.getOrderDetail4subs());
				orderSubs.add(orderSub4Print);
			}
            // 更新购物清单打印次数
			boolean opt_Print = orderForMerchantApi.updateOrderSubPrintCount(merchantCode, _orderNos);
			if(opt_Print){
				//避免数据同步时的延迟，主动修改招商库的数据
            	orderForMerchantService.updatePrintShopSize(merchantCode, _orderNos);
			}
			logger.info("更新购物清单打印是否成功：{}",opt_Print);
            if (CollectionUtils.isNotEmpty(orderSubs)) {
                json.put("orderSubList", orderSubs);
            }
            json.put("orderNos", orderNos);
            json.put("result", ResultEnums.SUCCESS.getResultMsg());
            
        } catch (Exception e) {
            logger.error("获取打印信息时发生异常：", e);
            json.put("result", ResultEnums.FAIL.getResultMsg());
            json.put("msg", "获取打印信息失败！");
        }
        return json.toString();
    }
    
	private static String gethotline(Map<String, String> hotLine, String orderSourceNo) {
		if (hotLine.containsKey(orderSourceNo)) {
			return hotLine.get(orderSourceNo);
		} else {
			if ("YG".equals(StringUtils.substringBefore(orderSourceNo, "-"))) {
				return hotLine.get("YG-YGWSSC-YGWSSC");
			} else if ("TB".equals(StringUtils.substringBefore(orderSourceNo, "-"))) {
				return hotLine.get("TB-TBZXD-BL");
			} else if ("WBPT".equals(StringUtils.substringBefore(orderSourceNo, "-"))) {
				return hotLine.get("WBPT-DD-YG");
			}
		}
		return "";
	}
    /**
     * 购物清单打印
     *
     * @param orderIds
     *            订单id [多个英文逗号隔开]
     */
    @ResponseBody
    @RequestMapping("/doBatchPrintShoppingListTemplate")
    public String doBatchPrintShoppingListTemplate(ModelMap map,
                                                   ModelMap modelMap, String orderSubNos, HttpServletRequest request)
            throws Exception {
        // 获取商家编码
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();

        String[] orderSubNoArray = orderSubNos.split(",");

        if (orderSubNoArray == null)
            return "error";

        List<String> _orderNos = Arrays.asList(orderSubNoArray);

        boolean opt_Print = orderForMerchantApi.updateOrderSubPrintCount(
                merchantCode, _orderNos);

        logger.info(MessageFormat.format(
                "商家[{0}]打印购物清单(订单号：{1}), 操作{2}.",
                new Object[]{merchantCode, _orderNos,
                        Boolean.toString(opt_Print)}));

        return opt_Print ? "success" : "error";
    }

    /**
     * 店铺提醒(包括待发货订单数、缺货订单数、超时未发货订单数、库存提示(少于5)：待发货订单)
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/doShopRemind")
    public String doShopRemind(HttpServletRequest request) {
        // 获取商家编码
    	JSONObject json=new JSONObject();
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        SupplierVo _vo;
        Map<String, Integer> tips=null;
        try {
            _vo = supplierService.getSupplierByMerchantCode(merchantCode);
            Integer identifier = _vo.getIsInputYougouWarehouse();
            if(identifier==1){
    			tips = new HashMap<String, Integer>();
    			tips.put("waitSends", -1);
    			tips.put("stockOuts", -1);
    			tips.put("timeOutOrders", -1);
    			json.putAll(tips);
                return json.toString();
            }
        } catch (Exception e1) {
            logger.error( MessageFormat.format("query订单提醒相关信息异常.merchantCode:{0},异常信息:", merchantCode),e1);
			tips = new HashMap<String, Integer>();
			tips.put("waitSends", -1);
			tips.put("stockOuts", -1);
			tips.put("timeOutOrders", -1);
			json.putAll(tips);
            return json.toString();
        }
		try {
			tips = orderService.queryShopRemindList(merchantCode);
		} catch (Exception e) {
			tips = new HashMap<String, Integer>();
			tips.put("waitSends", 0);
			tips.put("stockOuts", 0);
			tips.put("timeOutOrders", 0);
			logger.error( MessageFormat.format(" 获取订单提醒异常.merchantCode:{0},异常信息:",merchantCode),e );
		}
        json.putAll(tips);
        return json.toString();
    }

    /**
     * 分析商家最近一周的销售情况
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/analyzeSellInfo")
    public String analyzeSellInfo(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        json.put("result", "false");
        // 获取商家编码
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        if (StringUtils.isBlank(merchantCode))
            return json.toString();

        Map<String, List<?>> list = orderService
                .analyzeSellByNeerWeek(merchantCode);

        json.put("list", list);
        json.put("result", "true");
        return json.toString();
    }
    
    /**
     * 保存某订单的商家备注
     * @param request
     * @param orderSubNo
     * @return
     */
    @ResponseBody
    @RequestMapping("/remarkOrder")
    public String remarkOrder(HttpServletRequest request,MerchantOrderExpand record) {
    	 JSONObject json = new JSONObject();
         json.put("result", "false");
    	// 获取商家编码
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        if (StringUtils.isBlank(merchantCode) || StringUtils.isBlank(record.getOrderSubId()) ){
        	 return json.toString();
        }
        //保存
        try {
        	record.setMerchantCode(merchantCode);
        	record.setUpdateTime( DateUtil2.getCurrentDateTime() );
        	orderForMerchantService.insertOrUpdateMerchantRemark(record);
		} catch (Exception e) {
			logger.error("订单模块：给商家("+merchantCode+")订单("+record.getOrderSubId()+")增加备注发生异常:",e );
			return json.toString();
		}
        json.put("result", "true");
        json.put("markColor", record.getMarkColor());
        json.put("markNote", record.getMarkNote());
        return json.toString();
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
						logger.error("商家中心-订单模块：记录操作日志报错！日志详情：{}",operationLog);
					}
					
				}
    			
    		});*/
	    	//增加日志       
			final MerchantCenterOperationLog operationLog = new MerchantCenterOperationLog(request,
				menuName,menuName+"-"+note,	operateNum ,operateOrder,operateType);
			Runnable run = new Runnable(){

				@Override
				public void run() {
					logger.warn("线程{},开始保存订单模块操作日志...", Thread.currentThread().getName());
					if(!(operationLogService.insertOperationLog(operationLog))){
						logger.error("商家中心-订单模块：记录操作日志报错！日志详情：{}",operationLog);
					}
					logger.warn("线程{},结束保存订单模块操作日志...", Thread.currentThread().getName());
				}
    			
    		};
    		Thread thread = new Thread(run);
    		thread.setName("saveOperationLog 商家中心-订单模块保存操作日志");
    		thread.start();
	}
    
    @ResponseBody
    @RequestMapping("getRemarkDetailListAjax")
   	public String getRemarkDetailListAjax(ModelMap modelMap,HttpServletRequest request,String orderNOs) throws Exception {
    	JSONObject jsonObject = new JSONObject();
    	if( StringUtils.isNotEmpty(orderNOs) ){
    		List<String> idList = new ArrayList<String>();
    		String[] ids = orderNOs.split(",");
    		for(int i=0;i<ids.length;i++){
    			idList.add( ids[i] );
    		}
    		List<MerchantOrderExpand> merchantOrderExpandList = new ArrayList<MerchantOrderExpand>();
			try {
				merchantOrderExpandList = orderForMerchantService.queryMerchantOrderExpandList(idList);
			} catch (Exception e) {
				logger.error("订单模块：给商家异步获取商家备注信息发生异常 ",e);
			}
    		if( null!=merchantOrderExpandList && merchantOrderExpandList.size()>0 ){
    			jsonObject.put("merchantOrderExpandList", merchantOrderExpandList);
    		}
    	}
    	return jsonObject.toString();
    }
}
