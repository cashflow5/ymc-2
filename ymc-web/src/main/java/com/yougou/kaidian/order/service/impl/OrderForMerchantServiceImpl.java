package com.yougou.kaidian.order.service.impl;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yougou.component.area.api.IAreaApi;
import com.yougou.component.area.model.Area;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.ordercenter.common.OrderStatusEnum;
import com.yougou.ordercenter.common.PageFinder;
import com.yougou.kaidian.order.dao.MerchantOrderMapper;
import com.yougou.kaidian.order.model.AsmProduct;
import com.yougou.kaidian.order.model.MerchantOrderExpand;
import com.yougou.kaidian.order.model.MerchantOrderPrintInputDto;
import com.yougou.kaidian.order.model.MerchantQueryOrderPrintOutputDto;
import com.yougou.kaidian.order.model.MerchantQueryOrderStockOutputDto;
import com.yougou.kaidian.order.service.IOrderForMerchantService;
import com.yougou.ordercenter.vo.merchant.input.QueryStockInputDto;
import com.yougou.ordercenter.vo.merchant.output.QueryOrderPickOutputDto;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityOrderApiService;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.pc.vo.commodity.SupplierInfoVo;
import com.yougou.tools.common.utils.DateUtil;
/**
 * 招商替代订单相关查询接口的实现
 * 
 * @author mei.jf
 * 
 */
@Service
public class OrderForMerchantServiceImpl implements IOrderForMerchantService {

    private static final Logger logger = LoggerFactory.getLogger(OrderForMerchantServiceImpl.class);

    @Resource
    private MerchantOrderMapper merchantOrderMapper;
    @Resource
    private ICommodityBaseApiService commodityBaseApiService;
    @Resource
    private IAreaApi areaApi;  
    @Autowired ICommodityOrderApiService commodityOrderApiService;
    
    @Override
    public PageFinder<MerchantQueryOrderPrintOutputDto> queryPrintList(MerchantOrderPrintInputDto dto) throws Exception {
    	
    	logger.info("订单打印清单参数:{}",dto);
    	
    	if (StringUtils.isEmpty(dto.getWarehouseCode()) || StringUtils.isEmpty(dto.getMerchantCode())) {
            logger.error("订单打印清单参数为空！WarehouseCode:{},MerchantCode-{}",dto.getWarehouseCode(),dto.getMerchantCode());
            throw new Exception("缺少必要参数！");
        }

        if (StringUtils.isNotEmpty(dto.getTimeStart()) && StringUtils.isNotEmpty(dto.getTimeEnd())) {
            // 限定查询订单的时间为14天内的订单
            Date startDate = DateUtil2.getdate(dto.getTimeStart());
            Date endDate = DateUtil2.getdate(dto.getTimeEnd());
            int day = daysBetween(startDate, endDate);
            if (day > 29) {
                logger.error("必要参数设置有误--下单开始时间和结束时间时间间隔过大！间隔天数:{}",day);
                throw new Exception("必要参数设置有误--下单开始时间和结束时间时间间隔过大！");
            }
        }

        try {
            if (StringUtils.isNotBlank(dto.getSuppliersColorModelsCode())) {
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("supplierCode", dto.getSuppliersColorModelsCode());
                try {
                    List<Commodity> commodities = commodityBaseApiService.getCommodityListByParams(paramMap);
                    if (commodities != null && commodities.size() > 0) {
                        dto.setCommodityNo(commodities.get(0).getCommodityNo());
                    }
                } catch (Exception e) {
                    logger.error("调用商品接口未找到数据！",e);
                    throw new Exception("根据供应商款色编码未找到数据！");
                }

            }
            
            int rowCount = 0;// Add by LQ.
            
            if (StringUtils.isNotBlank(dto.getMergerCargo()) && Integer.parseInt(dto.getMergerCargo()) == 1) { // 有此条件说明需要揪出可以合并发货的订单
            	
	            List<Map<String, String>> orderCountInfoList = merchantOrderMapper.queryCountOrderPrintList(dto);
	            List<OrderCountInfo> tempOrderCountInfoList = new ArrayList<OrderForMerchantServiceImpl.OrderCountInfo>();
	            List<String> orderSubIdList = new ArrayList<String>();
	            /**
	             * 把查询结果放置到orderCountInfo的list对象中
	             */
	            for (Map<String, String> map : orderCountInfoList) {
	                OrderCountInfo orderCountInfo = new OrderCountInfo();
	                orderCountInfo.setOrderSubId(map.get("orderSubId"));
	                orderCountInfo.setConsigneeInfo(map.get("consigneeInfo"));
	                tempOrderCountInfoList.add(orderCountInfo);
	            }

                /**
                 * 循环一下，标记那些发货信息相同
                 */
                for (OrderCountInfo orderCountInfo1 : tempOrderCountInfoList) {
                    for (OrderCountInfo orderCountInfo2 : tempOrderCountInfoList) {
                        if (orderCountInfo1.getConsigneeInfo() != null && orderCountInfo1.getOrderSubId() != null
                                && orderCountInfo1.getConsigneeInfo().equals(orderCountInfo2.getConsigneeInfo())
                                && !orderCountInfo1.getOrderSubId().equals(orderCountInfo2.getOrderSubId())) {
                            orderCountInfo1.setMergerCargo(true);
                        }
                    }
                }

                /**
                 * 再循环一下，取出发货信息相同的订单ordersubId
                 */
                for (OrderCountInfo orderCountInfo1 : tempOrderCountInfoList) {
                    if (orderCountInfo1.isMergerCargo()) {
                        orderSubIdList.add(orderCountInfo1.getOrderSubId());
                    }
                }
                rowCount = orderSubIdList.size();
                if (orderSubIdList != null && orderSubIdList.size() > 0) {
                    dto.setOrderSubIds(orderSubIdList);
                }
            } else {
            	rowCount =  merchantOrderMapper.queryCountOrderPrintNum(dto);
            }

            PageFinder<MerchantQueryOrderPrintOutputDto> pageFinder = queryDataOrderPrintList(dto, rowCount);
           
            return pageFinder;
            
        } catch (Exception e) {
            logger.error("查询打印订单错误：" , e);
            throw new Exception(e);
        }
    }
    
    
    private PageFinder<MerchantQueryOrderPrintOutputDto> queryDataOrderPrintList(MerchantOrderPrintInputDto dto,int totalCount) throws Exception {
        
        PageFinder<MerchantQueryOrderPrintOutputDto> pageFinder = null;
        if(dto.getIsPage() == false){ //不分页
            dto.setPageSize(totalCount);
            pageFinder = new PageFinder<MerchantQueryOrderPrintOutputDto>(dto.getPageNo(),totalCount);
        }else{
            pageFinder = new PageFinder<MerchantQueryOrderPrintOutputDto>(dto.getPageNo(),dto.getPageSize(),totalCount);
        }
        
        if( totalCount<1 ){// Add by LQ.
        	return pageFinder;
        }// Add by LQ.
        
        List<MerchantQueryOrderPrintOutputDto> datas = merchantOrderMapper.queryDataOrderPrintList(dto);
        if(datas != null){
            for(MerchantQueryOrderPrintOutputDto dto2 : datas){
                //dto2.setBaseStatusDesc(OrderVoConversion.getBaseStatusDisplay(dto2.getBaseStatus()));
                dto2.setBaseStatusDesc(OrderStatusEnum.getNameByValue(dto2.getOrderStatus()));
                if (StringUtils.isNotEmpty(dto2.getProvince())) {
                    if (areaApi.getAreaByNo(dto2.getProvince()) != null) {
                        dto2.setProvinceName(areaApi.getAreaByNo(dto2.getProvince()).getName());
                    }
                }
                if (StringUtils.isNotEmpty(dto2.getCity())) {
                    if (areaApi.getAreaByNo(dto2.getCity()) != null) {
                        dto2.setCityName(areaApi.getAreaByNo(dto2.getCity()).getName());
                    }
                }
                if (StringUtils.isNotEmpty(dto2.getArea())) {
                    if (areaApi.getAreaByNo(dto2.getArea()) != null) {
                        dto2.setAreaName(areaApi.getAreaByNo(dto2.getArea()).getName());
                    }
                }
            }
            pageFinder.setData(datas);
        } 
        return pageFinder;  
        
    }
    
    
    

    public static final int daysBetween(Date early, Date late) {

        java.util.Calendar calst = java.util.Calendar.getInstance();
        java.util.Calendar caled = java.util.Calendar.getInstance();
        calst.setTime(early);
        caled.setTime(late);
        // 设置时间为0时
        calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calst.set(java.util.Calendar.MINUTE, 0);
        calst.set(java.util.Calendar.SECOND, 0);
        caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
        caled.set(java.util.Calendar.MINUTE, 0);
        caled.set(java.util.Calendar.SECOND, 0);
        // 得到两个日期相差的天数
        int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;

        return days;
    }

    class OrderCountInfo {
        String orderSubId;
        String consigneeInfo;
        boolean MergerCargo = false;

        public String getOrderSubId() {
            return orderSubId;
        }

        public void setOrderSubId(String orderSubId) {
            this.orderSubId = orderSubId;
        }

        public String getConsigneeInfo() {
            return consigneeInfo;
        }

        public void setConsigneeInfo(String consigneeInfo) {
            this.consigneeInfo = consigneeInfo;
        }

        public boolean isMergerCargo() {
            return MergerCargo;
        }

        public void setMergerCargo(boolean mergerCargo) {
            MergerCargo = mergerCargo;
        }
    }

    @Override
    public PageFinder<MerchantQueryOrderStockOutputDto> queryStockingList(QueryStockInputDto dto) throws Exception {
        if (StringUtils.isEmpty(dto.getWarehouseCode()) || StringUtils.isEmpty(dto.getMerchantCode())) {
            logger.error("查询备货清单参数为空！");
            throw new Exception("缺少必要参数！");
        }
        try {
    		int page = dto.getPageNo() == 0 ? 1 : dto.getPageNo();
    		int  offset = (page - 1 )* dto.getPageSize();
            RowBounds rowBounds = new RowBounds(offset, dto.getPageSize());
            return new PageFinder(dto.getPageNo(),dto.getPageSize(),merchantOrderMapper.getCountOrderStock(dto),merchantOrderMapper.getDataOrderStock(dto,rowBounds));
        } catch (Exception e) {
            logger.error("查询备货清单错误：" , e);
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<QueryOrderPickOutputDto> queryPcikingList(String merchantCode, String warehouseCode, List<String> orderSubNos) throws Exception {
        if (StringUtils.isEmpty(merchantCode) || StringUtils.isEmpty(warehouseCode)) {
            logger.error("查询拣货清单参数为空");
            throw new Exception("缺少必要参数！");
        }
        try {
        	
            List<QueryOrderPickOutputDto> dtos = queryOrderPickingList(merchantCode, warehouseCode, orderSubNos);
            if (dtos != null && dtos.size() > 0) {
                for (QueryOrderPickOutputDto dto : dtos) {
                    String proviceName = "";
                    String cityName = "";
                    String areaName = "";
                    if (StringUtils.isNotBlank(dto.getProvince())) {
                        Area area = areaApi.getAreaByNo(dto.getProvince());
                        proviceName = area != null ? area.getName() : "";
                    }
                    if (StringUtils.isNotBlank(dto.getCity())) {
                        Area area = areaApi.getAreaByNo(dto.getCity());
                        cityName = area != null ? area.getName() : "";
                    }
                    if (StringUtils.isNotBlank(dto.getArea())) {
                        Area area = areaApi.getAreaByNo(dto.getArea());
                        areaName = area != null ? area.getName() : "";
                    }
                    dto.setConsigneeArea(proviceName + cityName + areaName);
                }
            }
            return dtos;
        } catch (Exception e) {
            logger.error("查询拣货清单错误：" , e);
            throw new Exception(e.getMessage());
        }
    }
    
    private List<QueryOrderPickOutputDto> queryOrderPickingList(
            String merchantCode, String warehouseCode, List<String> orderSubNos)
            throws Exception {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("merchantCode", merchantCode);
        parameterMap.put("warehouseCode",warehouseCode);
        parameterMap.put("orderSubNos", orderSubNos);
        List<QueryOrderPickOutputDto> orderPickOutputDtos = merchantOrderMapper.queryPickingOrderList(parameterMap);
        //获取不重复的list结果
        List<String> insideList = new ArrayList<String>();
        Set<String> set=new HashSet<String>(); 
        if(orderPickOutputDtos != null && orderPickOutputDtos.size() > 0){
            for(QueryOrderPickOutputDto dto : orderPickOutputDtos){
                set.add(dto.getInsideCode());
            }
        }
        
        insideList.addAll(set);
        
        if(insideList.size() > 0){
            List<SupplierInfoVo> supplierInfoVos =  commodityOrderApiService.getSupplierInfoVoList(insideList);
            Map<String, SupplierInfoVo> map = new HashMap<String, SupplierInfoVo>();
            if(supplierInfoVos != null && supplierInfoVos.size() > 0){
                for(SupplierInfoVo infoVo : supplierInfoVos){
                    map.put(infoVo.getThirdPartyCode(), infoVo);
                }
            }
            
            //if(map.size() > 0){
                for(QueryOrderPickOutputDto dto : orderPickOutputDtos){
                	try{ 
                		SupplierInfoVo supplierInfoVo = map.get(dto.getInsideCode());
                		if(supplierInfoVo!=null){
                			dto.setBrandName(supplierInfoVo.getBrandName());
                			dto.setSupplierCode(supplierInfoVo.getSupplierCode());
                			dto.setThirdPartyCode(dto.getInsideCode());
                		}else{
                			dto.setBrandName("该条码的商品信息无法找到，可能条码已更换");
                			dto.setSupplierCode("该条码的商品信息无法找到，可能条码已更换");
                			dto.setThirdPartyCode("该条码的商品信息无法找到，可能条码已更换");
                			logger.error("订单号为orderSubNo={};百丽条码insideCode={},导出拣货清单时，调用commodityOrderApiService接口未获取到商品的(品牌名称、供应商款色编码、商家货品条码)信息,可能货品条码已更换 ",
                					new Object[]{dto.getOrderSubNo(),dto.getInsideCode()});
                		}
                		
	                   /* dto.setBrandName(map.get(dto.getInsideCode()).getBrandName());
	                    dto.setThirdPartyCode(dto.getInsideCode());
	                    dto.setSupplierCode(map.get(dto.getInsideCode()).getSupplierCode());*/
                	}catch(Exception e ){
                		logger.error( MessageFormat.format("订单号为orderSubNo={0};百丽条码insideCode={1},导出拣货清单时，调用commodityOrderApiService接口未获取到商品的(品牌名称、供应商款色编码、商家货品条码)信息,可能货品条码已更换",
                				new Object[]{dto.getOrderSubNo(),dto.getInsideCode()}),e);
                	}
                }
            //}
        }
        
        return orderPickOutputDtos;
    }


	@Override
	public AsmProduct getProductByLevelCode(String order_sub_no,
			String level_code) {
		return merchantOrderMapper.selectProductByLevelCode(order_sub_no, level_code);
	}
	
	@Override
	public int insertOrUpdateMerchantRemark(MerchantOrderExpand vo) throws Exception{
		List<MerchantOrderExpand> list = merchantOrderMapper.selectByOrderSubNo(vo.getOrderSubId());
		if( null!=list && list.size()>0 ){
			//update
			String id = list.get(0).getId();
			vo.setId(id);
			return merchantOrderMapper.updateByPrimaryKeySelective(vo);
		}else{
			//insert
			vo.setId( UUIDGenerator.getUUID() );
			vo.setCreateTime( DateUtil2.getCurrentDateTime() );
			return merchantOrderMapper.insertSelective(vo);
		}
	}
	
	@Override
	public List<MerchantOrderExpand> queryMerchantOrderExpandList(List<String> orderNOs)throws Exception{
		List<MerchantOrderExpand> list = merchantOrderMapper.selectByOrders(orderNOs);
		return list;
	}
	
	
//	private Map<String,MerchantOrderExpand> constructRemarkKeyMap(List<String> orders){
//		Map<String,MerchantOrderExpand> map = new HashMap<String,MerchantOrderExpand>();
//		List<MerchantOrderExpand> list = merchantOrderMapper.selectByOrders(orders);
//		if( null!=list && 0<list.size()){
//			for(MerchantOrderExpand expand:list){
//				map.put(expand.getOrderSubId(), expand);
//			}
//		}
//		return map;
//	}
//	@Override
//	public void resetPageFinderForMerchantRemark(List<String> orders,PageFinder pageFinder)throws Exception{
//		Map<String,MerchantOrderExpand> constructMap = constructRemarkKeyMap(orders);
//		List<Object> voList =  pageFinder.getData();
////		List<MerchantQueryOrderPrintOutputDto> list = (List<MerchantQueryOrderPrintOutputDto>) pageFinder.getData();
//		for( int i=0;i<voList.size();i++ ){
//			Object vo = voList.get(i);
////			MerchantQueryOrderPrintOutputDto order = voList.get(i);
//			if( vo instanceof  MerchantQueryOrderPrintOutputDto ){
//				MerchantQueryOrderPrintOutputDto order  = (MerchantQueryOrderPrintOutputDto)vo;
//				//begin: 为了查得商家备注，构建所查结果pageFinder列表中的id拼接字符串
//				String orderId = order.getOrderSubNo();
//				MerchantOrderExpand expand = (MerchantOrderExpand)constructMap.get(orderId);
//				if(null!=expand){
//					order.setMarkColor( expand.getMarkColor() );
//					order.setMarkNote( expand.getMarkNote() );
//				}
//			}else if( vo instanceof  MerchantQueryOrderStockOutputDto ){
//				MerchantQueryOrderStockOutputDto order  = (MerchantQueryOrderStockOutputDto)vo;
//				//begin: 为了查得商家备注，构建所查结果pageFinder列表中的id拼接字符串
//				String orderId = order.getOrderSubNo();
//				MerchantOrderExpand expand = (MerchantOrderExpand)constructMap.get(orderId);
//				if(null!=expand){
//					order.setMarkColor( expand.getMarkColor() );
//					order.setMarkNote( expand.getMarkNote() );
//				}
//			}
//			
//		}
//	}
	// 查订单的商家备注信息
	@Override
	public MerchantOrderExpand getMerchantOrderExpand(String orderSubNo)throws Exception{
		List<MerchantOrderExpand> list = merchantOrderMapper.selectByOrderSubNo(orderSubNo);
		if( null!=list && 0<list.size()){
			return list.get(0);
		}else{
			return null;
		}
	}


	@Override
	public void updateExportStatus(String merchantCode,
			List<String> lstOrderSubNo, Date now) {
		merchantOrderMapper.updateExportStatus(merchantCode,lstOrderSubNo,now);
	}


	@Override
	public void updatePrintExpressSize(String merchantCode, String orderSubNo) {
		merchantOrderMapper.updatePrintExpressSize(merchantCode,orderSubNo);
	}


	@Override
	public void updatePrintShopSize(String merchantCode, List<String> _orderNos) {
		merchantOrderMapper.updatePrintShopSize(merchantCode,_orderNos);
	}


	@Override
	public void updateOutStoreStatus(String merchantCode, String orderSubNo,
			Date outShopDate) {
		merchantOrderMapper.updateOutStoreStatus(merchantCode,orderSubNo,outShopDate);
	}

}