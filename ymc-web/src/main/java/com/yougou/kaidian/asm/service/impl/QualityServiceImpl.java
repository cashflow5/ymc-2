package com.yougou.kaidian.asm.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.yougou.kaidian.asm.common.PinYinUtil;
import com.yougou.kaidian.asm.dao.QualityForOrderMapper;
import com.yougou.kaidian.asm.dao.QualityMapper;
import com.yougou.kaidian.asm.model.OrderProductQuantityVo;
import com.yougou.kaidian.asm.service.IQualityService;
import com.yougou.kaidian.asm.vo.AsmQcDetailVo;
import com.yougou.kaidian.asm.vo.AsmQcVo;
import com.yougou.kaidian.asm.vo.QualityNotPassQueryVo;
import com.yougou.kaidian.asm.vo.QualityNotPassResultVo;
import com.yougou.kaidian.asm.vo.QualitySaveVo;
import com.yougou.kaidian.asm.vo.QualityVo;
import com.yougou.kaidian.common.util.DateUtil;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.common.util.MathUtil;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.order.dao.MerchantOrderMapper;
import com.yougou.kaidian.order.model.AsmProduct;
import com.yougou.kaidian.user.util.UserUtil;
import com.yougou.ordercenter.api.asm.IAsmApi;
import com.yougou.ordercenter.api.order.IOrderForMerchantApi;
import com.yougou.ordercenter.model.asm.SaleApplyBill;
import com.yougou.ordercenter.model.asm.SaleCancelGoodsInfo;
import com.yougou.ordercenter.model.order.OrderDetail4sub;
import com.yougou.ordercenter.model.order.OrderSub;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.wms.wpi.common.exception.WPIBussinessException;
import com.yougou.wms.wpi.expresssoc.domain.ExpressSocDomain;
import com.yougou.wms.wpi.expresssoc.service.IExpressSocDomainService;
import com.yougou.wms.wpi.orderoutstore.service.IOrderOutStoreDomainService;
import com.yougou.wms.wpi.rejectioninspection.domain.RejectionInspectionDetailDomain;
import com.yougou.wms.wpi.rejectioninspection.domain.RejectionInspectionDomain;
import com.yougou.wms.wpi.rejectioninspection.service.IRejectionInspectionDomainService;
import com.yougou.wms.wpi.returnqaproduct.domain.vo.QualityQueryVo;
import com.yougou.wms.wpi.returnqaproduct.domain.vo.ReturnQaProductDetailDomainVo;
import com.yougou.wms.wpi.returnqaproduct.domain.vo.ReturnQaProductDomainVo;
import com.yougou.wms.wpi.returnqaproduct.domain.vo.ReturnQaProductDomainVo.ReturnExceptionType;
import com.yougou.wms.wpi.returnqaproduct.service.IReturnQaProductDetailDomainService;
import com.yougou.wms.wpi.returnqaproduct.service.IReturnQaProductDomainService;
import com.yougou.wms.wpi.warehouse.domain.WarehouseDomain;
import com.yougou.wms.wpi.warehouse.service.IWarehouseCacheService;

/**
 * 
 * @author huang.tao
 * 
 */
@Service
public class QualityServiceImpl extends SqlSessionDaoSupport implements IQualityService {

    private final Logger logger = LoggerFactory.getLogger(QualityServiceImpl.class);

    @Resource
    private QualityMapper qualityDao;
    
    @Resource
    private QualityForOrderMapper qualityForOrderMapper;

    @Resource
    private IRejectionInspectionDomainService rejectionInspectionDomainService;

    @Resource
    private IReturnQaProductDomainService returnQaProductDomainService;

    @Resource
    private IReturnQaProductDomainService returnQaApi;

    @Resource
    private IWarehouseCacheService warehouseCacheService;

    @Resource
    private IAsmApi asmApiImpl;

    @Resource
    private IOrderForMerchantApi orderForMerchantApi;

    @Resource
    private IExpressSocDomainService expressSocService;

    @Resource
    @Qualifier("returnQaProductDetailDomainService")
    private IReturnQaProductDetailDomainService returnQaDetailApi;

    @Resource
    private ICommodityBaseApiService commodityBaseApi;

    @Resource
    private IOrderOutStoreDomainService orderOutStoreDomainService;
    
    @Resource
    private MerchantOrderMapper merchantOrderMapper;
    
    @Resource 
    private RedisTemplate<String,Object> redisTemplate;
    

    @Override
    public PageFinder<Map<String, Object>> queryQualityListByVo(QualityQueryVo vo, Query query) {
        PageFinder<Map<String, Object>> pageFinder = null;
        RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<AsmQcVo> asmQcList = qualityDao.queryAsmQcListByVo(vo, rowBounds);
        String product_no = null;
        Commodity c = null;
        List<AsmQcDetailVo> asmQcDetailList = null;
        String cNo = null;
        for (AsmQcVo asmQc : asmQcList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("order_sub_no", asmQc.getOrderNo());
            map.put("user_name", asmQc.getUserName());
            map.put("mobile_phone", asmQc.getMobilePhone());
            // 去掉order_sub_no后面的空格
            asmQc.setOrderNo(StringUtils.deleteWhitespace(asmQc.getOrderNo()));
            asmQcDetailList = qualityDao.queryAsmQcDetailsByOrderNo(asmQc.getOrderNo());
            for (AsmQcDetailVo asmQcDetail : asmQcDetailList) {
                // 查询每条质检的商品信息
                product_no = asmQcDetail.getProdNo();
                cNo = StringUtils.substring(product_no, 0, product_no.length() - 3);
                c = commodityBaseApi.getCommodityByNo(cNo, false, true, false);
                asmQcDetail.setPicUrl(c.getPicSmall());
                asmQcDetail.setCommodityUrl(commodityBaseApi.getFullCommodityPageUrl(cNo));
            }
            map.put("asmQcDetail", asmQcDetailList);
            list.add(map);
        }
        int count = qualityDao.queryAsmQcListByVoCount(vo);
        pageFinder = new PageFinder<Map<String, Object>>(query.getPage(), query.getPageSize(), count, list);
        return pageFinder;
    }
    
    @Override
	public List<Map<String, Object>> queryQualityAllListByVo(QualityQueryVo vo) {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	List<AsmQcVo> asmQcList = qualityDao.queryAsmQcListByTime(vo);
    	for (AsmQcVo asmQc : asmQcList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("order_sub_no", asmQc.getOrderNo());
            map.put("user_name", asmQc.getUserName());
            map.put("mobile_phone", asmQc.getMobilePhone());
            // 去掉order_sub_no后面的空格
            asmQc.setOrderNo(StringUtils.deleteWhitespace(asmQc.getOrderNo()));
            List<AsmQcDetailVo> asmQcDetailList = qualityDao.queryAsmQcDetailsByOrderNo(asmQc.getOrderNo());
            map.put("asmQcDetail", asmQcDetailList);
            list.add(map);
    	}
		return list;
	}
    
    /**
     * batchQuery:分多次取所有数据 
     * @param size 一次取得数量
     * @param vo 
     * @author li.n1  
     * @since JDK 1.6
     */
    @Override
    public List<Map<String, Object>> batchQuery(QualityQueryVo vo,int size){
    	//总记录数
    	int count = qualityDao.queryAsmQcListByVoCount(vo);
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	//循环次数
    	int xunSize = (count%size)==0?(count/size):(count/size+1);
    	RowBounds rowBounds = null;
    	List<AsmQcVo> asmQcList = null;
    	Map<String, Object> map = null;
    	List<AsmQcDetailVo> asmQcDetailList = null;
    	for(int i=0;i<xunSize;i++){
    		rowBounds = new RowBounds((i*size), size);
    		asmQcList = qualityDao.queryAsmQcListByVo(vo, rowBounds);
    		for (AsmQcVo asmQc : asmQcList) {
                map = new HashMap<String, Object>();
                map.put("order_sub_no", asmQc.getOrderNo());
                map.put("user_name", asmQc.getUserName());
                map.put("mobile_phone", asmQc.getMobilePhone());
                // 去掉order_sub_no后面的空格
                asmQc.setOrderNo(StringUtils.deleteWhitespace(asmQc.getOrderNo()));
                asmQcDetailList = qualityDao.queryAsmQcDetailsByOrderNo(asmQc.getOrderNo());
                map.put("asmQcDetail", asmQcDetailList);
                list.add(map);
        	}
    	}
    	return list;
    }
    

    @Override
    public List<Map<String, Object>> queryQualityDetail(QualityQueryVo vo) {
        if (vo == null)
            return null;
        logger.info("查询质检明细|订单号：{}, 质检状态：{}, 质检类型：{}.", new Object[] { vo.getOrderSubNo(), vo.getStatusName(), vo.getQualityType() });
        if (StringUtils.isBlank(vo.getOrderSubNo()))
            return null;
        if (StringUtils.isBlank(vo.getStatusName()))
            return null;
        if ("拒收".equals(vo.getQualityType())) {
            return rejectionInspectionDomainService.queryQualityDetailFromInspection(vo);
        } else {
            return returnQaDetailApi.queryQualityDetailFromReturn((QualityQueryVo) vo);
        }
    }

    @Override
    public boolean saveQualityInfo(QualitySaveVo vo) throws Exception {
        return saveRejectionInspectionQuality(vo, true);
    }
    
    @Override
    public Map<String,String> saveReturnQualityInfo(QualitySaveVo vo) throws Exception {
        // 退换货
        return this.saveReturnQualityVoList(vo);
    }

    // 保存拒收对象
    // type: true:拿快递单号做拒收质检 false:拿订单号做拒收质检
    private boolean saveRejectionInspectionQuality(QualitySaveVo vo, boolean type) {
    	boolean isSuccess = false;
    	try {
            // 拒收 待确认
            RejectionInspectionDomain domain = this.convert2RejectionQualityVo(vo, type);
            if (rejectionInspectionDomainService.queryRejectionInspectionByExpressCode(vo.getOrderNo())) {
                if (!rejectionInspectionDomainService.addRejectionInspection(domain)) {
                    throw new IllegalStateException("拒收质检明细登记失败!");
                }
            }
            // 拒收 已确认
            isSuccess = rejectionInspectionDomainService.confirmRejectionInspection(domain.getId(), vo.getMerchantCode());
    	} catch (Exception ex) {
    		logger.error("拒收质检异常",ex);
    	}
        return isSuccess;
    }

    // 封装拒收质检对象
    // type: true:拿快递单号做拒收质检 false:拿订单号做拒收质检
    private RejectionInspectionDomain convert2RejectionQualityVo(QualitySaveVo vo, boolean type) throws Exception{
        RejectionInspectionDomain domain = new RejectionInspectionDomain();
        domain.setId(UUIDGenerator.getUUID());
        domain.setInspectionCode(System.currentTimeMillis() + "_" + MathUtil.buildRandom(3));
        // 拒收类型（0：非拆包拒收，1：拆包拒收) 商家中心默认为0
        domain.setRejectionType(NumberUtils.INTEGER_ZERO);
        domain.setCreator(vo.getQaPerson());
        domain.setCreateDate(new Date());
        domain.setWarehouseId(vo.getWarehouseCode());
        // 拒收默认
        domain.setExpressfee(0.0);
        domain.setExpressCode(type ? vo.getOrderNo() : StringUtils.trim(vo.getExpressNo()));
        domain.setExpressno(type ? vo.getOrderNo() : StringUtils.trim(vo.getExpressNo()));
        domain.setSTATUS(NumberUtils.INTEGER_ZERO);
        domain.setPaytype(NumberUtils.INTEGER_ZERO);

        List<RejectionInspectionDetailDomain> list = new ArrayList<RejectionInspectionDetailDomain>();
        RejectionInspectionDetailDomain detailDomain = null;
    	logger.error("insideCode:{}\tqadsc:{}\tqaToOrder:{}", new Object[] {vo.getInsideCode(), vo.getQadsc(), vo.getQaToOrder()});
        // 货品条码
        String[] insideCodes = vo.getInsideCode().split("####");
        String[] descs = vo.getQadsc().split("####");
        String[] qaToOrder = vo.getQaToOrder().split("####");
        AsmProduct product=null;
        for (int i = 0; i < insideCodes.length; i++) {
        	insideCodes[i]=StringUtils.trim(insideCodes[i]);
            detailDomain = new RejectionInspectionDetailDomain();
            detailDomain.setId(UUIDGenerator.getUUID());
            detailDomain.setRejectionId(domain.getId());
            detailDomain.setInspectionCode(domain.getInspectionCode());
            product=merchantOrderMapper.selectProductByLevelCode(vo.getOrderNo(), insideCodes[i]);
            detailDomain.setSupplierCode(product.getCommodityInsideCode());
            detailDomain.setCommodityId(product.getCommodityProductId());
            detailDomain.setCommodityCode(product.getProductNo());
            detailDomain.setGoodsName(product.getCommodityName());
            detailDomain.setSpecification(product.getSpecification());
            detailDomain.setInvitemno(product.getSupplierCode());
            detailDomain.setQuantity(NumberUtils.INTEGER_ONE);
            detailDomain.setOrderSubNo(type ? qaToOrder[i].trim() : vo.getOrderNo());
            detailDomain.setDescr(descs.length > i ? descs[i] : "");
            // 顾客归属 (顾客|仓库)
            detailDomain.setProblemDue(null);
            detailDomain.setProblemReason(null);
            detailDomain.setProblemType("非质量问题");
            detailDomain.setStorageType("GOOD");
            detailDomain.setUnits(null);
            list.add(detailDomain);
        }
        domain.setRejectionQcRegisterdetailDomainVo(list);
        return domain;
    }

    // 封装退换货质检对象
    private Map<String,String> saveReturnQualityVoList(QualitySaveVo vo) {
    	Map<String,String> result = new HashMap<String,String>();
    	//----------------------避免同时操作保存，出现重复质检start added by zhangfeng 2016-3-22 ------------------
    	String locked = (String) redisTemplate.opsForValue().get("saveReturnQuality:"+vo.getMerchantCode()+":"+vo.getOrderNo());
    	if("locked".equals(locked)){
    		result.put("success", "false");
			result.put("msg", "该订单由"+YmcThreadLocalHolder.getOperater()+"已经在录入质检，无法重复录入质检！");
			return result;
    	}else{
    		redisTemplate.opsForValue().set("saveReturnQuality:"+vo.getMerchantCode()+":"+vo.getOrderNo(),"locked",30, TimeUnit.SECONDS);
    	}
    	//----------------------避免同时操作保存，出现重复质检end  added by zhangfeng 2016-3-22 ------------------
        // 订单级别        
        String[] insideCodes = vo.getInsideCode().split("####");
        String[] descs = vo.getQadsc().split("####");
        String[] qaResults = vo.getIsPassed().split("####");
        String[] qaToOrder = vo.getQaToOrder().split("####");
        logger.warn("商家:{} 订单号:{} 质检信息,insideCodes:{}  descs:{}  qaResults:{}  qaToOrder:{}",new Object[]{vo.getMerchantCode(),vo.getOrderNo(),insideCodes,descs,qaResults,qaToOrder});
        
        OrderSub orderSub = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(vo.getOrderNo(), vo.getMerchantCode());
        logger.warn("商家:{} 订单号:{} 订单查询结果id:{}",new Object[]{vo.getMerchantCode(),vo.getOrderNo(),orderSub.getId()});
        // 如果用订单号做质检，但是快递单号和发货的快递单号一样，那么改走拒收的逻辑
        if (vo.getExpressEnt().split(",")[0].equals(orderSub.getLogisticsName()) && vo.getExpressNo().trim().equals(orderSub.getExpressOrderId())) {
            // 先校验是否做过退换货质检
            List<ReturnQaProductDomainVo> returnQaProductDomainVoList = returnQaProductDomainService.getReturnQaProductDomainByOrderNo(vo.getOrderNo()); 
            
            logger.warn("商家:{} 订单号:{} 根据订单号获取退换货质检信息,校验是否做过退换货质检(returnQaProductDomainService.getReturnQaProductDomainByOrderNo(vo.getOrderNo())),size：{}",new Object[]{vo.getMerchantCode(),vo.getOrderNo(),returnQaProductDomainVoList.size()});
            
            if (CollectionUtils.isEmpty(returnQaProductDomainVoList)) {
            	//先校验是否做过异常售后
				Map<String, Integer> abnoramalSaleRegisterCount = asmApiImpl.queryAbnoramalSaleRegisterCountByOrderSubNo(vo.getOrderNo());
				
				logger.warn("商家:{} 订单号:{} 异常售后情况,是否做过异常售后(IAsmApiImpl.queryAbnoramalSaleRegisterCountByOrderSubNo(vo.getOrderNo())),size：{}",new Object[]{vo.getMerchantCode(),vo.getOrderNo(),abnoramalSaleRegisterCount.size()});
				
				if (MapUtils.isNotEmpty(abnoramalSaleRegisterCount)) {
					//Map<String,String> result = new HashMap<String,String>();
					//操作失败，清除redis中锁定标识
					redisTemplate.delete("saveReturnQuality:"+vo.getMerchantCode()+":"+vo.getOrderNo());
					result.put("success", "false");
					result.put("msg", "该订单做过异常售后，无法录入质检！");
					return result;
				}
                // 转做拒收质检
				boolean success =saveRejectionInspectionQuality(vo, false);
				//Map<String,String> result = new HashMap<String,String>();
				if(success){
					result.put("success", "true");
					result.put("msg", "保存质检信息成功！");
				}else{
					//操作失败，清除redis中锁定标识
					redisTemplate.delete("saveReturnQuality:"+vo.getMerchantCode()+":"+vo.getOrderNo());
					result.put("success", "false");
					result.put("msg", "保存质检信息失败,请重试！");
				}
                return result;
            }
        }

        // 记录申请单号及个数
        List<SaleApplyBill> saleApplyBills = asmApiImpl.querySaleApplyBillListByOrderSubNo(vo.getOrderNo());
       
        logger.warn(" 商家:{} 订单号:{} 获取申请单号及个数：{}",new Object[]{vo.getMerchantCode(),vo.getOrderNo(),saleApplyBills.size()});
        
        //申请单处理状态 Map<申请单号, 待处理货品个数>
        Map<String, Integer> handleApplys = new HashMap<String, Integer>();
        if (CollectionUtils.isNotEmpty(saleApplyBills)) {
            for (SaleApplyBill apply : saleApplyBills) {
                if (!"SALE_COMFIRM".equals(apply.getStatus()) && !"PART_SALE_QC".equals(apply.getStatus()))
                    continue;
                List<SaleCancelGoodsInfo> goodsInfos = apply.getGoodInfos();
                if (CollectionUtils.isNotEmpty(goodsInfos) && !handleApplys.containsKey(apply.getApplyNo())) {
                    SaleCancelGoodsInfo goodsInfo = goodsInfos.get(0);
                    handleApplys.put(apply.getApplyNo(), goodsInfo.getCommodityNum());
                    logger.warn("商家:{} 订单号:{} 申请单号：{} 货品个数：{}",new Object[]{vo.getMerchantCode(),vo.getOrderNo(),apply.getApplyNo(),goodsInfo.getCommodityNum()});
                }
            }
        }
        
        // ---------------------质检保存前再次校验货品编码能否质检 start added by zhangfeng 2016-3-20-------------------
        result = checkInsideCode(vo, result, insideCodes, orderSub);
        if("false".equals(result.get("success"))){
        	//操作失败，清除redis中锁定标识
			redisTemplate.delete("saveReturnQuality:"+vo.getMerchantCode()+":"+vo.getOrderNo());
        	return result;
        }
        // ---------------------质检保存前再次校验货品编码能否质检 end -------------------
        
        //保存之前判断是否已经质检
		if (checkHasQAByOrderNo(orderSub)) {
			logger.warn(" 商家:{} 订单号:{} 已经质检：",new Object[]{vo.getMerchantCode(),vo.getOrderNo()});
			//Map<String,String> result = new HashMap<String,String>();
			//操作失败，清除redis中锁定标识
			redisTemplate.delete("saveReturnQuality:"+vo.getMerchantCode()+":"+vo.getOrderNo());
			result.put("success", "false");
			result.put("msg", "保存质检信息失败，该订单已经质检！");
			return result;
		}
        
        int successCount = 0;
        String failureInsideCodes = "";
        // 开始逐条构建质检主表
        // 物理仓库对象
        WarehouseDomain warehouse = warehouseCacheService.getWarehouse(vo.getWarehouseCode());
        logger.warn(" 商家:{} 订单号:{} 物理仓库信息 code {},name {}",new Object[]{vo.getMerchantCode(),vo.getOrderNo(),warehouse.getWarehouseCode(),warehouse.getWarehouseName()});
        AsmProduct _product=null;
        for (int i = 0; i < insideCodes.length; i++) {
        	// 实收质检货品信息
            AsmProduct product=merchantOrderMapper.selectProductByLevelCode(vo.getOrderNo(), insideCodes[i]);
			// 找不到要质检的条码对应的货品信息，防止空指针异常
			if (null == product) {
				failureInsideCodes += insideCodes[i] +" ";
				logger.warn("[RETRUN_QA_PRODUCT]质检时发生异常，找不到响应的货品条码 ---insideCodes[i]:"+insideCodes[i]+" vo.getMerchantCode():"+vo.getMerchantCode());
				break;
			}
            // 质检主表级别
            ReturnQaProductDomainVo domain = new ReturnQaProductDomainVo();
            domain.setId(UUIDGenerator.getUUID());
            domain.setQaDate(new Date());
            domain.setQaPerson(vo.getQaPerson());
            domain.setWarehouseCode(vo.getWarehouseCode());
            domain.setWarehouseId(warehouse == null ? "" : warehouse.getId());
            domain.setWarehouseName(warehouse == null ? "" : warehouse.getWarehouseName());
            // 快递
            domain.setExpressCharges(BigDecimal.valueOf(vo.getExpressCharges()));
            domain.setExpressCode(StringUtils.trim(vo.getExpressNo()));
            // 快递公司名称
            domain.setExpressName(vo.getExpressEnt().split(",")[0]);
            domain.setExpressId(vo.getExpressEnt().split(",")[1]);
            // 是否到付
            domain.setCashOnDelivery(vo.isPaytype() ? ReturnQaProductDomainVo.CashOnDelivery.YES : ReturnQaProductDomainVo.CashOnDelivery.NO);
            domain.setReturnStatus(ReturnQaProductDomainVo.ReturnStatus.WAIT_CONFIRM);
            // 判断是否异常完成
            domain.setIsException(ReturnExceptionType.valueOf(vo.getIsException()));

            // 质检明细级别
            List<ReturnQaProductDetailDomainVo> list = new ArrayList<ReturnQaProductDetailDomainVo>();
            ReturnQaProductDetailDomainVo detailDomain = new ReturnQaProductDetailDomainVo();
            detailDomain.setId(UUIDGenerator.getUUID());
            detailDomain.setReturnQaProductId(domain.getId());
            // 获取订单货品信息
            _product=merchantOrderMapper.selectProductByLevelCode(vo.getOrderNo(), qaToOrder[i]);
            logger.warn(" 商家:{} 订单号:{} 订单货品信息：{}",new Object[]{vo.getMerchantCode(),vo.getOrderNo(),qaToOrder[i]});
            //如果获取订单货品信息为null，则不关联申请单
            if (null != _product && null != _product.getProductNo()) {
                // 售后申请单
                List<SaleApplyBill> applyBills = asmApiImpl.getApplyNoByOrderSubNoAndProductNo(vo.getOrderNo(), _product.getProductNo());
                logger.warn(" 商家:{} 订单号:{} ：货品条码：{},申请单查询结果,size：{}",new Object[]{vo.getMerchantCode(),vo.getOrderNo(),_product.getProductNo(),applyBills.size()});
                // 获取handleApplys之后，开始关联申请单
                String applyNoStr="";
                for (SaleApplyBill saleApplyBill : applyBills) {
                    if (!saleApplyBill.getStatus().equals("SALE_COMFIRM") && !saleApplyBill.getStatus().equals("PART_SALE_QC")
                            && !handleApplys.containsKey(saleApplyBill.getApplyNo())) {
                    	applyNoStr=applyNoStr+"申请单:"+saleApplyBill.getApplyNo()+" Status:"+saleApplyBill.getStatus()+" 符合要求:no;\r\n";
                        continue;
                    }
                    applyNoStr=applyNoStr+" 申请单:"+saleApplyBill.getApplyNo()+" Status:"+saleApplyBill.getStatus()+" 符合要求:yes;\r\n";
                    // 售后类型
                    // 能关联到售后申请单的情况下
                    domain.setReturnType(saleApplyBill.getSaleType());
                    detailDomain.setApplyNo(saleApplyBill.getApplyNo());
                    detailDomain.setApplyDate(saleApplyBill.getCreateTime());
                    // 判断申请单是否已处理完成
                    Integer commodityNum = handleApplys.get(saleApplyBill.getApplyNo());
                    handleApplys.put(saleApplyBill.getApplyNo(), --commodityNum);
                    if (commodityNum == 0) {
                        handleApplys.remove(saleApplyBill.getApplyNo());
                    }
                    //设置寄回的物流信息
                    saleApplyBill.setLogisticsCompany(domain.getExpressName());
                    saleApplyBill.setExpressNo(domain.getExpressCode());
                    asmApiImpl.updateSaleApplyBillLogisticsByApplyNo(saleApplyBill);
                    break;
                }
                logger.warn("商家:{} 订单号:{} 货品条码:{} 申请单信息:{}",new Object[]{vo.getMerchantCode(),vo.getOrderNo(),_product.getProductNo(),applyNoStr});
            }

            // 订单信息
            detailDomain.setOrderSubNo(orderSub.getOrderSubNo());
            detailDomain.setConsignee(orderSub.getOrderConsigneeInfo().getUserName());// 收货人
            detailDomain.setConsigneeAddress(orderSub.getOrderConsigneeInfo().getConsigneeAddress());// 收货人地址
            detailDomain.setConsigneePhone(orderSub.getOrderConsigneeInfo().getMobilePhone());// 收货人手机
            detailDomain.setConsigneePostcode(orderSub.getOrderConsigneeInfo().getZipCode());// 收货人邮编
            detailDomain.setConsigneeTel(orderSub.getOrderConsigneeInfo().getConstactPhone());// 收货人电话
            detailDomain.setMember(orderSub.getOrderConsigneeInfo().getUserName());// 会员

            // 订单货品信息
            for (OrderDetail4sub orderDetail4sub : orderSub.getOrderDetail4subs()) {
                if (orderDetail4sub.getLevelCode().equals(qaToOrder[i])) {
                    // TODO
                    // detailDomain.setProductId(orderDetail4sub.getCommodityId());
                    detailDomain.setGoodsName(orderDetail4sub.getProdName());
                    detailDomain.setProductNo(orderDetail4sub.getProdNo());
                    detailDomain.setInsideCode(orderDetail4sub.getLevelCode());// 货品条码
                    detailDomain.setThirdPartyCode(StringUtils.substring(orderDetail4sub.getLevelCode(), 0, -2));// 款色编码
                    detailDomain.setQuantity(NumberUtils.INTEGER_ONE);
                    break;
                }
            }

            
            detailDomain.setQaProductId(product.getCommodityProductId());
            detailDomain.setQaGoodsName(product.getCommodityName());
            detailDomain.setQaInsideCode(product.getCommodityInsideCode());
            detailDomain.setQaProductNo(product.getProductNo());
            detailDomain.setQaQuantity(NumberUtils.INTEGER_ONE);
            detailDomain.setQaThirdPartyCode(StringUtils.substring(product.getSupplierCode(), 0, -2));// 款色编码StringUtils.substring(product.getThirdPartyInsideCode(), 0, -2)

            // 质检信息
            detailDomain.setQuestionType("GOOD");// 问题类型
            detailDomain.setQuestionCause("");
            detailDomain.setQuestionClassification("");
            detailDomain.setQuestionDescription("");
            detailDomain.setStorageType("GOOD");// 入库类型
            detailDomain.setQaDescription(descs.length > i ? descs[i] : "");
            // 待质检不通过的需求上线之后修正回来
            detailDomain.setIsPassed(qaResults[i]);//质检是否通过(1：质检通过；0：质检不通过。)
            //detailDomain.setIsPassed("1");
            logger.warn("[RETRUN_QA_PRODUCT]:orderSubNo="+detailDomain.getOrderSubNo()+",applyNo="+detailDomain.getApplyNo()+",applyDate="+(null!=detailDomain.getApplyDate()?DateUtil.getFormatByDate(detailDomain.getApplyDate()):"null")+",operateDate="+DateUtil.getFormatByDate(new Date()));
            list.add(detailDomain);
            domain.setDetailsList(list);
            try{
            	boolean saveSuccess = returnQaApi.returnQaProductForMerchantCode(domain, domain.getId(), vo.getMerchantCode());
            	if(saveSuccess){
            		successCount++;
            	}
            }catch(Exception e){
            	failureInsideCodes += insideCodes[i] +" ";
            	logger.error("调用WMS接口保存质检信息失败！",e);
            }
        }
        int failureCount = insideCodes.length - successCount;
       // Map<String,String> result = new HashMap<String,String>();
        if(successCount > 0){
        	String msg = "成功保存"+successCount+"条质检信息";
        	if(failureCount > 0){
        		msg += "，失败"+failureCount+"条，货品条码为"+failureInsideCodes;
        	}
        	result.put("success", "true");
        	result.put("msg", msg);
        }else{
        	//操作失败，清除redis中锁定标识
			redisTemplate.delete("saveReturnQuality:"+vo.getMerchantCode()+":"+vo.getOrderNo());
        	result.put("success", "false");
        	result.put("msg", "保存质检信息失败，可能已经存在质检信息！");
        }
		return result;
    }
    
    /**
     * 校验货品条码能否保存质检
     * added by zhangfeng 2016-3-20
     * @param vo
     * @param result
     * @param insideCodes
     * @param orderSub
     * @return
     */
	private Map<String, String> checkInsideCode(QualitySaveVo vo, Map<String, String> result,
			String[] insideCodes, OrderSub orderSub) {
		Map<String ,Object> resultMap = getDoNotQAInsideCode(orderSub, vo.getMerchantCode());
        //获取待质检数 <= 有效异常售后申请数 不能质检的货品编码
        List<String> doNotQAInsideCodes = (List<String>) resultMap.get("doNotQAInsideCodes");
        //校验保存登记质检的货品编码中是否有不能登记质检的: 
        StringBuffer sbf = new StringBuffer();
        for(String insideCoe : insideCodes){
        	if(doNotQAInsideCodes.contains(insideCoe)){
        		sbf.append(insideCoe).append(",");       		
        	}
        }
        if(sbf.length() > 0){
        	sbf.substring(0, sbf.length()-1);
        	result.put("success", "false");
			result.put("msg", "该订单中货品条码："+sbf.toString()+"，已申请了异常售后，无法质检！");
			return result;
        }
        //通过质检数量校验货品编码能否继续质检，diff{货品编码，可质检数量}
        Map<String,Integer> diff = (Map<String, Integer>) resultMap.get("diff");
        for(String insideCoe : insideCodes){
        	//可质检数量<=0 ，不能保存质检
        	if(diff.containsKey(insideCoe) && diff.get(insideCoe) <=0 ){
        		sbf.append(insideCoe).append(",");       		
        	}else if(!diff.containsKey(insideCoe)){
        		result.put("success", "false");
    			result.put("msg", "该订单货品条码："+insideCoe+"，已全部质检！");
    			return result;
        	}
        }
        if(sbf.length() > 0){
        	sbf.substring(0, sbf.length()-1);
        	result.put("success", "false");
			result.put("msg", "该订单中货品编码："+sbf.toString()+"，已全部质检！");
			return result;
        }
        return  result;
	}

    // 获取物流公司列表
    public List<Map<String, Object>> getExpressInfo() {  	
        List<Map<String, Object>> expressInfos = null;
        try {
            List<ExpressSocDomain> expresss = expressSocService.getExpressSocDomain();
            if (CollectionUtils.isNotEmpty(expresss)) {
                expressInfos = new ArrayList<Map<String, Object>>();
                Map<String, Object> map = null;
                for (ExpressSocDomain express : expresss) {
                    map = new HashMap<String, Object>();
                    map.put("id", express.getId());
                    map.put("express_no", express.getExpressNo());
                    map.put("express_name", express.getExpressName());
                    map.put("express_frtpinyin", PinYinUtil.getFirstSpell(express.getExpressName()));
                    expressInfos.add(map);
                 
                }
            }
        } catch (WPIBussinessException e) {
            throw new RuntimeException("系统内部异常, 请稍后再试.");
        }
        return expressInfos;
    }
    
    // 获取物流公司id和名称对应map
    public Map<String, String> getExpressInfoMap() {
        Map<String, String> expressInfos = new HashMap<String, String>();
        try {
            List<ExpressSocDomain> expresss = expressSocService.getExpressSocDomain();
            if (CollectionUtils.isNotEmpty(expresss)) {
                for (ExpressSocDomain express : expresss) {
                    expressInfos.put(express.getId(), express.getExpressName());
                }
            }
        } catch (WPIBussinessException e) {
            throw new RuntimeException("系统内部异常, 请稍后再试.");
        }
        return expressInfos;
    }

    /**
     * 判断是否存在发货信息
     * 
     * @param logisticsCode
     * @param lstExpressCode
     * @return
     */
    public boolean isHadExistsOutStoreRecord(String logisticsCode, List<String> lstExpressCode) {
        int count = orderOutStoreDomainService.getExistsOutStoreRecord(logisticsCode, lstExpressCode);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否存在发货信息(不指定物流编号)
     * 
     * @param lstExpressCode
     * @return
     */
    public boolean isHadExistsOutStoreRecord(List<String> lstExpressCode) {
        return isHadExistsOutStoreRecord(null, lstExpressCode);
    }
    
    /**
     * 查询质检不通过的后续流程
     * @param QualityNotPassQueryVo
     * @param Query
     * @return
     */
    public PageFinder<QualityNotPassResultVo> queryQualityNotPassListByVo(QualityNotPassQueryVo vo, Query query) {
        PageFinder<QualityNotPassResultVo> pageFinder = null;
        RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
        List<QualityNotPassResultVo> list = qualityDao.queryQualityNotPassListByVo(vo, rowBounds);

        // 获取快递公司名称
        Map<String, String> expressInfos = getExpressInfoMap();
        Date orderTime = null;
        String phone = "";
        Date compareTime = DateUtil2.addDate( DateUtil2.getCurrentDateTime(),-90 );
        for (QualityNotPassResultVo qualityNotPassResultVo : list) {
        	
        	  // 手机号加密 Add by LQ。
        	  orderTime = qualityNotPassResultVo.getQaDate();
              phone = qualityNotPassResultVo.getMobilePhone();
              if( orderTime.before( compareTime ) ){//加密
            	  qualityNotPassResultVo.setMobilePhone(UserUtil.encriptPhone(phone));
              }
        	
            qualityNotPassResultVo.setReturnLogisticsName(expressInfos.get(qualityNotPassResultVo.getReturnLogisticsCode()));
        }
        int count = qualityDao.queryQualityNotPassListByVoCount(vo);
        pageFinder = new PageFinder<QualityNotPassResultVo>(query.getPage(), query.getPageSize(), count, list);
        return pageFinder;
    }

	@Override
	public List<OrderProductQuantityVo> queryOrderAsmInfo(QualityVo qualityVo) {
		// TODO Auto-generated method stub
		return qualityForOrderMapper.queryOrderAsmInfo(qualityVo);
	}
	
    /**
     * 获取已经质检过的货品条码以及数量
     * 
     * @param orderNo
     * @return
     */
	private Map<String, Integer> getHasQAProductDomainByOrderNo(String orderNo) {
		// 获取已经质检过的货品条码以及数量
		Map<String, Integer> hasQA = new HashMap<String, Integer>();
		for (ReturnQaProductDomainVo domainVo : returnQaProductDomainService.getReturnQaProductDomainByOrderNo(orderNo)) {
			if ("已确认".equals(domainVo.getReturnStatus().getLabel())) {
				for (ReturnQaProductDetailDomainVo domain : domainVo.getDetailsList()) {
					if (null != hasQA.get(domain.getInsideCode())) {
						hasQA.put(domain.getInsideCode(), hasQA.get(domain.getInsideCode()) + 1);
					} else {
						hasQA.put(domain.getInsideCode(), 1);
					}
				}
			}
		}
		return hasQA;
	}
	
	/**
	 * 校验订单是否已经质检
	 * 
	 * @param orderSub
	 * @return
	 */
	public boolean checkHasQAByOrderNo(OrderSub orderSub) {
		Boolean same = true;
		// 获取已经质检过的货品条码以及数量
		Map<String, Integer> hasQA = getHasQAProductDomainByOrderNo(orderSub.getOrderSubNo());
		logger.info("保存质检信息调用wms接口获取到的已质检的信息：{}", hasQA);
		 //异常售后的货品条码及数量
        Map<String, Integer> abnoramalSale = new HashMap<String, Integer>();
		// 获取订单内所有货品条码以及数量
		Map<String, Integer> orderCommodityMap = new HashMap<String, Integer>();
		for (OrderDetail4sub orderDetail4sub : orderSub.getOrderDetail4subs()) {
			//如果一个订单，有相同货品条码进行累加
			if(orderCommodityMap.containsKey(orderDetail4sub.getLevelCode())){
				Integer	commodityNum=(orderCommodityMap.get(orderDetail4sub.getLevelCode()))+orderDetail4sub.getCommodityNum();
				orderCommodityMap.put(orderDetail4sub.getLevelCode(),commodityNum);
			}else{
				orderCommodityMap.put(orderDetail4sub.getLevelCode(), orderDetail4sub.getCommodityNum());
			}
			
			getNoQAMap(orderSub, abnoramalSale, orderDetail4sub);
		}
		// 比较两个map里的货品条码以及数量
		Iterator<String> it = orderCommodityMap.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			if (!hasQA.containsKey(key) || hasQA.get(key) < (orderCommodityMap.get(key) - abnoramalSale.get(key)  )  ) {
				same = false;
				break;
			}
		}
		return same;
	}
	
	/**
	 * 获取订单中每个货品的异常售后申请数量
	 * @param orderSub
	 * @param abnoramalSale
	 * @param orderDetail4sub
	 */
	private void getNoQAMap(OrderSub orderSub,
			Map<String, Integer> abnoramalSale, OrderDetail4sub orderDetail4sub) {
		//封装参数调用订单接口，获取订单内有效的异常售后货品信息
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("orderSubNo", orderSub.getOrderSubNo());
		paramMap.put("prodNo", orderDetail4sub.getProdNo());
		//订单是否有申请异常售后,订单系统接口 li.j1 2015-05-21
		logger.info("getNoQaCommodityList 调用售后接口判断订单是否有申请异常售后, 输入货品编码:{}订单单号:{}" , orderDetail4sub.getProdNo(),orderSub.getOrderSubNo());
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
	}
	
	/**
	 * 获取订单内不能质检的货品条码，可质检的货品条码及数量
	 * added by zhangfeng 2016-3-20
	 * @param order
	 * @param merchantCode
	 * @return
	 */
	 private Map<String ,Object> getDoNotQAInsideCode(OrderSub order,String merchantCode) {
		Map<String ,Object> result = new HashMap<String,Object>(); 
        // 获取已经质检过的货品条码以及数量
        Map<String, Integer> hasQA = getHasQAProductDomainByOrderNo(order.getOrderSubNo());

        //异常售后的货品条码及数量
        Map<String, Integer> abnoramalSale = new HashMap<String, Integer>();
        
        // 获取订单内所有货品条码以及数量
 		Map<String, Integer> orderCommodityMap = new HashMap<String, Integer>();
 		for (OrderDetail4sub orderDetail4sub : order.getOrderDetail4subs()) {
 			//如果一个订单，有相同货品条码进行累加
 			if(orderCommodityMap.containsKey(orderDetail4sub.getLevelCode())){
 				Integer	commodityNum=(orderCommodityMap.get(orderDetail4sub.getLevelCode()))+orderDetail4sub.getCommodityNum();
 				orderCommodityMap.put(orderDetail4sub.getLevelCode(),commodityNum);
 			}else{
 				orderCommodityMap.put(orderDetail4sub.getLevelCode(), orderDetail4sub.getCommodityNum());
 			}
 			
 			getNoQAMap(order, abnoramalSale, orderDetail4sub);
 		}
		
		//未检
		Map<String, Integer> notYetQA = new HashMap<String, Integer>();
		for(String key : orderCommodityMap.keySet()){
			if (!hasQA.containsKey(key)) {
				notYetQA.put(key, orderCommodityMap.get(key));
            } else if (hasQA.containsKey(key)) {
            	notYetQA.put(key, orderCommodityMap.get(key) - hasQA.get(key));
            }
		}
		
        //未检数量小于等于异常售后数量:即不能质检的货品
		List<String> doNotQAInsideCodes = new ArrayList<String>();
		for(String key : abnoramalSale.keySet()){
			if(abnoramalSale.get(key)>0 && notYetQA.get(key) <= abnoramalSale.get(key)){
				//expInsideCode.put(key, 1);
				doNotQAInsideCodes.add(key);
			}
		}
		
		// 比较两个map里的货品条码以及数量,将差异写进diff ,diff：{货品编码：可质检数量}
		Map<String, Integer> diff = new HashMap<String, Integer>();
		for(String key : orderCommodityMap.keySet()){
			if (!hasQA.containsKey(key)) {
                diff.put(key, orderCommodityMap.get(key)-abnoramalSale.get(key));
            } else if (hasQA.containsKey(key) && 
            		hasQA.get(key) < (orderCommodityMap.get(key) - abnoramalSale.get(key))) {
                diff.put(key, orderCommodityMap.get(key) - abnoramalSale.get(key) - hasQA.get(key));
            }
		}
		logger.info("getDoNotQAInsideCode方法，订单号：{}，商家编码：{}，不能质检的货品条码：{}",order.getOrderSubNo(),merchantCode,doNotQAInsideCodes);
		result.put("doNotQAInsideCodes", doNotQAInsideCodes);
		result.put("diff", diff);
        return result;
    }

}
