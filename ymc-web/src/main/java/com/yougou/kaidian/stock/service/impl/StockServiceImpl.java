/**
 * 
 */
package com.yougou.kaidian.stock.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.stock.dao.StockMapper;
import com.yougou.kaidian.stock.model.vo.InventoryForMerchantVo;
import com.yougou.kaidian.stock.model.vo.InventoryHDQueryVO;
import com.yougou.kaidian.stock.model.vo.InventoryVo;
import com.yougou.kaidian.stock.model.vo.KeyValueHelperVo;
import com.yougou.kaidian.stock.model.vo.KeyValueVo;
import com.yougou.kaidian.stock.service.IStockService;
import com.yougou.ordercenter.api.order.IOrderForMerchantApi;
import com.yougou.ordercenter.model.order.OrderDetail4sub;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.product.ProductCommodity;
import com.yougou.wms.wpi.common.exception.WPIBussinessException;
import com.yougou.wms.wpi.inventory.domain.vo.InventoryAssistVo;
import com.yougou.wms.wpi.inventory.service.IInventoryDomainService;
import com.yougou.wms.wpi.inventory.service.IInventoryForMerchantService;

/**
 * @author huang.tao
 * 
 */
@Service
public class StockServiceImpl implements IStockService {

    private final static Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Resource
    private IInventoryDomainService inventoryDomainService;
    @Resource
    private ICommodityMerchantApiService commodityMerchantApi;
    @Resource
    private ICommodityBaseApiService commodityBaseApiService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private IInventoryForMerchantService inventoryForMerchantService;
	@Resource
	private IOrderForMerchantApi orderForMerchantApi;
    @Resource
    private StockMapper stockMapper;

    @Override
    public List<Object> batchUpdateStock(String merchantCode, String wareHouseCode, List<Object[]> paramList, int progress) throws Exception {
        if (CollectionUtils.isEmpty(paramList))
            return null;

        // 通过商家编码查询所有条码列表(此接口由商品提供)
        List<Map<String, String>> proLists = commodityMerchantApi.getCommodityInfoByMerchantCode(merchantCode);
        progress += 10;
        this.redisTemplate.opsForHash().put(CacheConstant.C_STOCK_PROGRESS_BAR_KEY, merchantCode, progress);

        // 条码 - 货品信息
        Map<String, Map<String, String>> barCodeMap = new HashMap<String, Map<String, String>>();
        Map<String, String> pro_barCodeMap = new HashMap<String, String>();
        // 转换
        for (Map<String, String> map : proLists) {
            String thirdPartyCode = MapUtils.getString(map, "third_party_code");
            if (StringUtils.isNotBlank(thirdPartyCode)) {
                barCodeMap.put(thirdPartyCode, map);
                pro_barCodeMap.put(MapUtils.getString(map, "product_no"), thirdPartyCode);
            }
        }

        // 错误列表
        List<Object> errobjList = new ArrayList<Object>();
        Map<String, Integer> quantityMap = this.checkExtractStockData(paramList, errobjList, barCodeMap, merchantCode, progress);
        logger.info("商家：{} | 提取导入货品-库存列表({})：{}。", new Object[] { merchantCode, quantityMap.size(), quantityMap });

        if (MapUtils.isNotEmpty(quantityMap)) {
            Map<String, Integer> successMap = this.batchUpdateStock(merchantCode, wareHouseCode, quantityMap, errobjList, barCodeMap, pro_barCodeMap);
            logger.info("商家：{} | 成功导入货品-库存列表({})：{}。", new Object[] { merchantCode, successMap.size(), successMap });
        }

        /*
         * if (CollectionUtils.isNotEmpty(errobjList)) { //将错误列表存到缓存 try {
         * redisTemplate.opsForHash().put(CacheConstant.C_STOCK_ERROR_KEY,
         * merchantCode, errobjList); } catch (Exception e) {
         * logger.error(MessageFormat
         * .format("商家：{0} | 批量导入库存, 将异常数据集合放入redis异常。", merchantCode), e); } }
         */

        return errobjList;
    }

    /**
     * 校验并提取库存列表
     * 
     * @param paramList
     *            导入库存列表
     * @param errobjList
     *            错误列表
     * @param barCodeMap
     *            商家所有条码集合
     * @return 符合条件的库存集合 Map<货品编号, 库存>
     */
    private Map<String, Integer> checkExtractStockData(List<Object[]> paramList, List<Object> errobjList, Map<String, Map<String, String>> barCodeMap, String merchantCode,
            int progress) {
        Map<String, Integer> quantityMap = new HashMap<String, Integer>();

        // Excle顺序号,库存
        int seqNo = 1, qty = 0;
        Object[] obj = null;
        for (int i = 0; i < paramList.size(); i++) {
            Object[] lineObj = paramList.get(i);
            // 进度条
            this.storageProgressBar(merchantCode, progress, 25, paramList.size(), i);

            obj = new Object[4];
            seqNo++;

            StringBuffer sb = new StringBuffer();
            obj[0] = seqNo;
            obj[1] = lineObj[0];
            obj[2] = lineObj[3];

            // 商家货品编码校验
            if (this.isEmptyForObject(lineObj[0])) {
                sb.append("<商家货品条码为空>,");
            } else {
                if (!barCodeMap.keySet().contains(lineObj[0])) {
                    sb.append("<商家货品条码在系统中不存在>,");
                }
            }

            // 库存数据校验
            if (this.isEmptyForObject(lineObj[3])) {
                sb.append("<库存数据为空>,");
            } else if (!StringUtils.trimToEmpty(lineObj[3].toString()).matches("^-?\\d+$")) {
                sb.append("<库存数据必须为数字,并且必须为0或正整数>,");
            }
            if (StringUtils.isNotBlank(sb.toString())) {
                obj[3] = new StringBuffer().append("第").append(seqNo).append("行,").toString() + sb.toString();
                errobjList.add(obj);
                continue;
            }
            qty = Integer.parseInt(lineObj[3].toString().trim());
            Map<String, String> productMap = barCodeMap.get(lineObj[0]);
            quantityMap.put(productMap.get("product_no"), qty);
        }
        return quantityMap;
    }

    /**
     * 批量修改库存
     * 
     * @param merchantCode
     * @param wareHouseCode
     * @param quantityMap
     * @param errobjList
     * @param barCodeMap
     * @param proNo_barCodeMap
     *            货品编码和条码的关系
     * @return
     */
    private Map<String, Integer> batchUpdateStock(String merchantCode, String wareHouseCode, Map<String, Integer> quantityMap, List<Object> errobjList,
            Map<String, Map<String, String>> barCodeMap, Map<String, String> pro_barCodeMap) throws Exception {
        Map<String, Integer> successMap = new HashMap<String, Integer>();

        if (MapUtils.isNotEmpty(quantityMap)) {
            Set<String> key = quantityMap.keySet();
            String productNo = null;
            Object[] obj = null;
            int i = 0, progress = 55;
            for (Iterator<String> it = key.iterator(); it.hasNext();) {
                // 进度条
                this.storageProgressBar(merchantCode, progress, 45, key.size(), i);
                i++;
                obj = new Object[4];
                productNo = it.next();
                try {
                    // 获取该货品的实际安全库存数，更新给wms的库存要减去该数目
                    //Integer safeStockQuantity = updateSafeStockQuantity(merchantCode, wareHouseCode, productNo, quantityMap.get(productNo));
                    Map<Integer,String> safeStockQuantityMap = updateSafeStockQuantity(merchantCode, wareHouseCode, productNo, quantityMap.get(productNo));
                    Integer safeStockQuantity = (Integer)(safeStockQuantityMap.keySet().toArray()[0]);
                    if(null==safeStockQuantity){//库存小于  预占库存 加安全库存
                    	Integer preQuantity = 0; //预占库存
                        List<InventoryAssistVo> qtys = inventoryForMerchantService.queryProductInventory(Arrays.asList(productNo), wareHouseCode, 0);// 只更新正品库存
                        if (CollectionUtils.isNotEmpty(qtys)) {
                            preQuantity = qtys.get(0).getStoredNumber();
                        }
                        
                        Map<String, Object> result_all = stockMapper.querySafeStockQuantity(merchantCode);
                        Integer safeStock_all = Integer.valueOf(result_all.get("safe_stock_quantity").toString());
                        
                    	String erroMsg = "商家"+merchantCode+"修改货品编码"+productNo+"库存错误，总库存小于（预占库存{"+preQuantity+"}加安全库存{"+safeStock_all+"}";
                    	obj[0] = "";
                        obj[1] = MapUtils.getString(pro_barCodeMap, productNo, "");
                        obj[2] = MapUtils.getString(quantityMap, productNo, "");
                        obj[3] = erroMsg;
                        errobjList.add(obj);
                        logger.error(erroMsg);
                        continue;
                    }
                    Map<String, Date> resultMap = inventoryDomainService.updateInventoryForMerchant(productNo, wareHouseCode, quantityMap.get(productNo) - safeStockQuantity,
                            NumberUtils.INTEGER_ZERO);
                    if (MapUtils.isNotEmpty(resultMap) && resultMap.containsKey(productNo)) {
                        successMap.put(productNo, quantityMap.get(productNo));
                    }
                } catch (WPIBussinessException we) {
                    obj[0] = "";
                    obj[1] = MapUtils.getString(pro_barCodeMap, productNo, "");
                    obj[2] = MapUtils.getString(quantityMap, productNo, "");
                    obj[3] = we.getReturnMessage();
                    errobjList.add(obj);
                    logger.error("商家：{} | 货品条码：{}({})更新库存异常。| wms-returnCode: {}, wms-returnMessage: {}。",
                            new Object[] { merchantCode, obj[1], productNo, we.getReturnCode(), we.getReturnMessage() });
                }
            }
        }

        return successMap;
    }

    /**
     * 判断对象包含的字符串是否为空
     * 
     * @param o
     * @return
     */
    private boolean isEmptyForObject(Object o) {
        if (null == o)
            return true;

        if (StringUtils.isBlank(o.toString()))
            return true;

        return false;
    }

    @Override
    public List<Object[]> queryAllProductStocks(String merchantCode) throws Exception {
        List<Object[]> list = new ArrayList<Object[]>();
        // 通过商家编码查询所有条码列表(此接口由商品提供)
        List<Map<String, String>> proLists = commodityMerchantApi.getCommodityInfoByMerchantCode(merchantCode);

        Object[] obj = null;
        if (CollectionUtils.isNotEmpty(proLists)) {
            for (Map<String, String> map : proLists) {
                obj = new Object[3];
                obj[0] = map.get("third_party_code");
                obj[1] = map.get("commodity_name");
                obj[2] = "";
                list.add(obj);
            }
        }
        return list;
    }

    @Override
    public List<KeyValueVo> getYearsList() {
        List<Object[]> years = new ArrayList<Object[]>();
        int year = DateUtil2.getYear(new Date());
        for (int i = 0; i < 9; i++) {
            Object[] obj = { year + i, year + i };
            years.add(obj);
        }
        KeyValueHelperVo keyValueHelperVo = new KeyValueHelperVo(years);
        return keyValueHelperVo.getKeyValueList();
    }

    @Override
    public Map<String, Object> queryMerchantInventoryByThirdPartyCode(String merchantCode, String thirdPartyCode, String warehouseCode) throws Exception {
        Map<String, Object> mapQueryParam = new HashMap<String, Object>();
        mapQueryParam.put("merchantCode", merchantCode);
        mapQueryParam.put("thirdPartyCode", thirdPartyCode);
        List<ProductCommodity> lstProductCommodity = commodityBaseApiService.getProductCommodities(mapQueryParam, false);
        ProductCommodity productCommodity = null;
        if (lstProductCommodity == null || lstProductCommodity.isEmpty()) {
            logger.error("商家：{}| 修改库存, 条码：{}无对应货品.", new Object[] { merchantCode, thirdPartyCode });
        } else {
            productCommodity = lstProductCommodity.get(0);
        }

        Map<String, Object> resultMap = null;
        if (null != productCommodity) {
            resultMap = new HashMap<String, Object>();

            Map<Integer, Date> mapResult = inventoryDomainService.querySalesInventroyForMerchant(productCommodity.getProductNo(), warehouseCode);
            int saleCount = !MapUtils.isEmpty(mapResult) ? mapResult.keySet().iterator().next() : 0;

            resultMap.put("goods_name", productCommodity.getCommodityName());
            resultMap.put("commodity_code", productCommodity.getProductNo());
            resultMap.put("third_party_code", productCommodity.getThirdPartyCode());
            resultMap.put("specification", productCommodity.getColor() + "_" + productCommodity.getSize());
            resultMap.put("inventory_quantity", saleCount);
        }

        return resultMap;
    }

    @Override
    public PageFinder<InventoryVo> queryInventoryNew(InventoryHDQueryVO queryVo, Query query) {
		Boolean flag = StringUtils.isNotEmpty(queryVo.getStock());
		//组装查询条件
        Map<String, Object> params = assembleQueryCondition(queryVo);
        //查询总数据量
        Integer count = commodityBaseApiService.getProductCommoditiesCount(params);
        List<ProductCommodity> commoditys = new ArrayList<ProductCommodity>();
		if (flag) {
			Query query2 = new Query();
			query2.setPageSize(400);
			int pageCount = count / query2.getPageSize() + ((count % query2.getPageSize()) > 0 ? 1 : 0);
	        for (int i = 1; i <= pageCount; i++) {
	            query2.setPage(i);
	            List<ProductCommodity> commodity = commodityBaseApiService.getProductCommodities(params, false, query2.getPage(), query2.getPageSize());
	            commoditys.addAll(commodity);
	        }	
		} else {
			commoditys = commodityBaseApiService.getProductCommodities(params, false, query.getPage(), query.getPageSize());
		}

        // 查询库存相关信息
		Map<String, InventoryAssistVo> qtyMap = new HashMap<String, InventoryAssistVo>();
		getInverntoryQty(qtyMap, queryVo, commoditys);

        List<InventoryVo> data = new ArrayList<InventoryVo>();
        InventoryVo vo = null;
        Map<String, Object> safeStockQuantityMap = null;
        Integer safeStockQuantity = 0;
        InventoryAssistVo qty=null;
		for (ProductCommodity commodity : commoditys) {
			safeStockQuantity = 0;
			safeStockQuantityMap = stockMapper.queryInventoryForMerchant(queryVo.getMerchantCode(), commodity.getProductNo());
			if (MapUtils.isNotEmpty(safeStockQuantityMap)) {
				safeStockQuantity = Integer.valueOf(safeStockQuantityMap.get("safe_stock_quantity").toString());
			}
			qty = (InventoryAssistVo) MapUtils.getObject(qtyMap, commodity.getProductNo());
						
			if(flag && Integer.valueOf(queryVo.getStock()) > (null == qty ? 0 : qty.getInventoryQuantity() + safeStockQuantity)) {
				continue;
			}
			vo = new InventoryVo();
			vo.setGoodsName(commodity.getCommodityName());
			vo.setProductNo(commodity.getProductNo());
			vo.setCostPrice(commodity.getCostPrice());
			vo.setThirdPartyCode(commodity.getInsideCode());
			vo.setSupplierCode(commodity.getSupplierCode());
			vo.setCatStructName(commodity.getCatStructName());
			vo.setBrandName(commodity.getBrandName());
			vo.setSpecification(commodity.getColor() + "_" + commodity.getSize());
			vo.setUnit(commodity.getUnitName());
			vo.setPicSmall(commodity.getPicSmall());
			vo.setProdUrl(this.getFullCommodityPageUrl(commodity.getCommodityNo()));

			vo.setQuantity(null == qty ? 0 : qty.getInventoryQuantity() + safeStockQuantity);
			vo.setSaleQuantity(null == qty ? 0 : qty.getInventoryQuantity() - qty.getStoredNumber());
			vo.setPreQuantity(null == qty ? 0 : qty.getStoredNumber());
			vo.setQtyCost(null == qty ? 0.0 : (qty.getInventoryQuantity() + safeStockQuantity) * commodity.getCostPrice());
			data.add(vo);

		}
        PageFinder<InventoryVo> pageFinder = null;
        if(flag){
        	pageFinder = new PageFinder<InventoryVo>(query.getPage(), query.getPageSize(), data.size());
        	pageFinder.setData(data.subList((query.getPage()-1)*query.getPageSize(), Math.min(query.getPageSize()*query.getPage(),data.size())));
        }else{
        	
        	pageFinder = new PageFinder<InventoryVo>(query.getPage(), query.getPageSize(), count);
            pageFinder.setData(data);
        }

        return pageFinder;
    }

    /**
     * 获取库存信息
     * @param qtyMap
     * @param queryVo
     * @param commoditys
     */
	private void getInverntoryQty(Map<String, InventoryAssistVo> qtyMap,
			InventoryHDQueryVO queryVo,List<ProductCommodity> commoditys) {
        List<String> productNoList = new ArrayList<String>();
        for (ProductCommodity productCommodity : commoditys) {
            productNoList.add(productCommodity.getProductNo());
        }

        if (CollectionUtils.isNotEmpty(productNoList)) {
            try {
                List<InventoryAssistVo> qtys = null;
                if (StringUtils.isNotBlank(queryVo.getWarehouseCode())){
                	qtys = inventoryForMerchantService.queryProductInventory(productNoList, queryVo.getWarehouseCode(), 2 == queryVo.getSelectType() ? 1 : 0);
                } else {
                	qtys = inventoryForMerchantService.queryInvenotryByProduct(productNoList); // 非商家仓库暂时不统计残品库存
                }
                if (CollectionUtils.isNotEmpty(qtys)) {
                    for (InventoryAssistVo qtyVo : qtys) {
                        qtyMap.put(qtyVo.getProductNo(), qtyVo);
                    }
                }
            } catch (WPIBussinessException e) {
                logger.error("查询queryProductInventory发生异常.", e);
            }
        }
	}

	/**
	 * 组装查询条件
	 * @param queryVo
	 * @return
	 */
	private Map<String, Object> assembleQueryCondition(InventoryHDQueryVO queryVo) {
		String categoryNo = "";
        if (StringUtils.isNotEmpty(queryVo.getCategory3No()) && !"0".equals(queryVo.getCategory3No())) {
            categoryNo = queryVo.getCategory3No().substring(0, queryVo.getCategory3No().indexOf(";"));
        } else if (StringUtils.isNotEmpty(queryVo.getCategory2No()) && !"0".equals(queryVo.getCategory2No())) {
            categoryNo = queryVo.getCategory2No().substring(0, queryVo.getCategory2No().indexOf(";"));
        } else if (StringUtils.isNotEmpty(queryVo.getCategory1No()) && !"0".equals(queryVo.getCategory1No())) {
            categoryNo = queryVo.getCategory1No();
        }
        queryVo.setCategoryNo(categoryNo);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("merchantCode", queryVo.getMerchantCode());
        params.put("likeCommodityName", StringUtils.isBlank(queryVo.getGoodsName()) ? null : queryVo.getGoodsName());
        params.put("likeStructName", StringUtils.isBlank(queryVo.getCategoryNo()) ? null : queryVo.getCategoryNo());
        params.put("productNo", StringUtils.isBlank(queryVo.getProductNo()) ? null : queryVo.getProductNo());
        params.put("productNoList", StringUtils.isBlank(queryVo.getProductList()) ? null : Arrays.asList(queryVo.getProductList().split(",")));
        params.put("commodityNo", StringUtils.isBlank(queryVo.getCommodityCode()) ? null : queryVo.getCommodityCode());
        params.put("supplierCode", StringUtils.isBlank(queryVo.getSupplierCode()) ? null : queryVo.getSupplierCode());
        params.put("styleNo", StringUtils.isBlank(queryVo.getStyleNo()) ? null : queryVo.getStyleNo());
        params.put("insideCode", StringUtils.isBlank(queryVo.getInsideCode()) ? null : queryVo.getInsideCode());
        params.put("brandNo", StringUtils.isBlank(queryVo.getBrandNo()) ? null : queryVo.getBrandNo());
        params.put("year", StringUtils.isBlank(queryVo.getYear()) ? null : queryVo.getYear());
        if ("2".equals(queryVo.getCommodityStatus())) { // 在售(上架)
            params.put("commodityStatus", 2);
        } else if ("1".equals(queryVo.getCommodityStatus())) { // 不可销售状态
            List<Integer> status = new ArrayList<Integer>();
            status.add(1);
            status.add(3);
            status.add(4);
            status.add(5);
            status.add(6);
            status.add(11);
            status.add(12);
            status.add(13);
            status.add(14);
            status.add(15);
            params.put("commodityStatusList", status);
        }
		return params;
	}   

    private String getFullCommodityPageUrl(String commodityNo) {
        String url = "javascript:void(0)";
        if (StringUtils.isBlank(commodityNo))
            return url;

        try {
            url = commodityBaseApiService.getFullCommodityPageUrl(commodityNo);
        } catch (Exception e) {
            logger.error("通过cms接口获取单品页地址异常.", e);
        }

        return url;
    }

    /**
     * 库存导出
     */
    @Override
    public List<InventoryVo> exportInventoryNew(InventoryHDQueryVO queryVo) {
        Query query = new Query();
        query.setPageSize(400);
        Boolean flag = StringUtils.isNotEmpty(queryVo.getStock());
        Map<String, Object> params = assembleQueryCondition(queryVo);
        Integer count = commodityBaseApiService.getProductCommoditiesCount(params);
        List<ProductCommodity> commoditys = null;
        
        List<ProductCommodity> allCommoditys = new ArrayList<ProductCommodity>();
        int pageCount = count / query.getPageSize() + ((count % query.getPageSize()) > 0 ? 1 : 0);

        for (int i = 1; i <= pageCount; i++) {
            query.setPage(i);
            commoditys = commodityBaseApiService.getProductCommodities(params, false, query.getPage(), query.getPageSize());
            allCommoditys.addAll(commoditys);
        }		

        Map<String, InventoryAssistVo> qtyMap = new HashMap<String, InventoryAssistVo>();
    	// 查询库存数量相关信息
    	getInverntoryQty(qtyMap, queryVo, allCommoditys);

        List<InventoryVo> data = new ArrayList<InventoryVo>();
        InventoryVo vo = null;
        Map<String, Object> safeStockQuantityMap = null;
        Integer safeStockQuantity = 0;
        InventoryAssistVo qty=null;
        Map<String,String> categoryTemp = new HashMap<String,String>();
		for (ProductCommodity commodity : allCommoditys) {
			safeStockQuantity = 0;
			safeStockQuantityMap = stockMapper.queryInventoryForMerchant(queryVo.getMerchantCode(), commodity.getProductNo());
			if (MapUtils.isNotEmpty(safeStockQuantityMap)) {
				safeStockQuantity = Integer.valueOf(safeStockQuantityMap.get("safe_stock_quantity").toString());
			}
			qty = (InventoryAssistVo) MapUtils.getObject(qtyMap, commodity.getProductNo());
			if (flag && Integer.valueOf(queryVo.getStock()) > (null == qty ? 0 : qty.getInventoryQuantity() + safeStockQuantity)) {
				continue;
			}
			vo = new InventoryVo();
			vo.setGoodsName(commodity.getCommodityName());
			vo.setProductNo(commodity.getProductNo());
			vo.setCostPrice(commodity.getCostPrice());
			vo.setThirdPartyCode(commodity.getInsideCode());
			vo.setSupplierCode(commodity.getSupplierCode());
			String structCatName = "";
			//获取分类
			try {
				if(categoryTemp.containsKey(commodity.getCatStructName())){
					structCatName = categoryTemp.get(commodity.getCatStructName());
				}else{						
					Category category = commodityBaseApiService.getCategoryByStructName(commodity.getCatStructName());
					structCatName = null == category ? "未知" : category.getStructCatName();
					categoryTemp.put(commodity.getCatStructName(), structCatName);
				}
			} catch (Exception e) {
				logger.error("merchantCode: {} | export stock query Category[commodityBaseApiService.getCategoryByStructName] error.", queryVo.getMerchantCode());
			}
			vo.setCatStructName(structCatName);
			vo.setBrandName(commodity.getBrandName());
			vo.setSpecification(commodity.getColor() + "_" + commodity.getSize());
			vo.setUnit(commodity.getUnitName());

			vo.setQuantity(null == qty ? 0 : qty.getInventoryQuantity() + safeStockQuantity);
			vo.setSaleQuantity(null == qty ? 0 : qty.getInventoryQuantity() - qty.getStoredNumber());
			vo.setPreQuantity(null == qty ? 0 : qty.getStoredNumber());
			vo.setQtyCost(null == qty ? 0.0 : (qty.getInventoryQuantity() + safeStockQuantity) * commodity.getCostPrice());
			data.add(vo);

		}        
        return data;
    }

    /**
     * 处理进度信息
     * 
     * @param merchantCode
     *            商家编码
     * @param progress
     *            初始进度
     * @param share
     *            进度份额
     * @param count
     *            总数
     * @param current
     *            当前数
     */
    private void storageProgressBar(String merchantCode, int progress, int share, int count, int current) {
        // 进度条
        int _progress = getProgressNum(progress, share, count, current);
        if (progress != _progress) {
            this.redisTemplate.opsForHash().put(CacheConstant.C_STOCK_PROGRESS_BAR_KEY, merchantCode, _progress);
            progress = _progress;
        }
    }

    /**
     * 计算进度条
     * 
     * @param progress
     *            初始进度
     * @param share
     *            份额
     * @param count
     *            计算总数
     * @param current
     *            当前记录数
     * @return 进度
     */
    public static Integer getProgressNum(int progress, int share, int count, int current) {
        int f = (share * current * 100) / count;
        if (f > 100)
            return (progress + (f / 100));
        return progress;
    }

    /**
     * 更新商家设定的安全库存数量（存在就更新，不存在就插入）
     * 
     * @param merchantCode
     * @param safeStockQuantity
     * @return
     */
	public Boolean updateSafeStockQuantityForSet(String merchantCode, String warehouseCode, Integer safeStockQuantity) {
		int result = stockMapper.querySafeStockQuantityCount(merchantCode);
		if (result > 0) {
			stockMapper.updateSafeStockQuantity(merchantCode, safeStockQuantity);
		} else {
			stockMapper.insertSafeStockQuantity(UUIDGenerator.getUUID(), merchantCode, safeStockQuantity);
		}
		List<String> commodityNoList = batchUpdateSafeStockQuantity(safeStockQuantity, merchantCode, warehouseCode);
		if (CollectionUtils.isEmpty(commodityNoList)) {
			return true;
		}
		return false;
	}

    /**
     * 批量更新商家库存（减去安全库存），并保存实际安全库存
     * 
     * @param merchantCode
     * @param safeStockQuantity
     * @return 更新不成功的commodityNoList
     */
    public List<String> batchUpdateSafeStockQuantity(Integer newSafeStockQuantity, String merchantCode, String warehouseCode) {
        List<String> commodityNoList_failed = new ArrayList<String>();
        int XUNSIZE = 500;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("merchantCode", merchantCode);
        Integer count = commodityBaseApiService.getProductCommoditiesCount(params);
        //遍历次数
        int size = count%XUNSIZE==0?count/XUNSIZE:count/XUNSIZE+1; 
        for(int i=0;i<size;i++){
        	 List<ProductCommodity> commoditys = commodityBaseApiService.getProductCommodities(params, false , i+1, XUNSIZE);
             // 查询库存相关信息
             Map<String, InventoryAssistVo> qtyMap = new HashMap<String, InventoryAssistVo>();
             List<String> productNoList = new ArrayList<String>();
             for (ProductCommodity _obj : commoditys) {
                 productNoList.add(_obj.getProductNo());
             }

             if (CollectionUtils.isNotEmpty(productNoList)) {
                 try {
                     List<InventoryAssistVo> qtys = null;
                     if (StringUtils.isNotBlank(warehouseCode))
                         qtys = inventoryForMerchantService.queryProductInventory(productNoList, warehouseCode, 0);// 只更新正品库存
                     else
                         qtys = inventoryForMerchantService.queryInvenotryByProduct(productNoList); // 非商家仓库暂时不统计残品库存
                     if (CollectionUtils.isNotEmpty(qtys)) {
                         for (InventoryAssistVo qtyVo : qtys) {
                             qtyMap.put(qtyVo.getProductNo(), qtyVo);
                         }
                     }
                 } catch (WPIBussinessException e) {
                     logger.error("查询queryProductInventory发生异常.", e);
                 }
             }

             InventoryForMerchantVo inventoryForMerchantVo = null;
             Map<String, Object> result = null;
             int safeStockQuantity = 0;
             InventoryAssistVo qty = null;
             boolean executed_flag = true;
             for (ProductCommodity productCommodity : commoditys) {
                 safeStockQuantity = 0;
                 executed_flag = true;
                 // 查询原始库存
                 result = stockMapper.queryInventoryForMerchant(merchantCode, productCommodity.getProductNo());
                 qty = (InventoryAssistVo) MapUtils.getObject(qtyMap, productCommodity.getProductNo());
                 if (MapUtils.isEmpty(result)) {
                     if (null != qty && qty.getInventoryQuantity() > qty.getStoredNumber()) {
                         safeStockQuantity = Math.min(qty.getInventoryQuantity() - qty.getStoredNumber(), newSafeStockQuantity);
                     }
                     if (safeStockQuantity > 0) {
                         // 更新减去安全库存之后的wms库存
                         try {
                             inventoryDomainService.updateInventoryForMerchant(productCommodity.getProductNo(), warehouseCode, -safeStockQuantity, NumberUtils.INTEGER_ONE);
                         } catch (WPIBussinessException we) {
                             executed_flag = false;
                             commodityNoList_failed.add(productCommodity.getProductNo());
                             logger.error("商家：{} | 货品条码：{}更新库存异常。| wms-returnCode: {}, wms-returnMessage: {}。",
                                     new Object[] { merchantCode, productCommodity.getProductNo(), we.getReturnCode(), we.getReturnMessage() });
                         }
                     }
                     // 记录实际减去的安全库存
                     if (executed_flag) {
                         inventoryForMerchantVo = new InventoryForMerchantVo();
                         inventoryForMerchantVo.setId(UUIDGenerator.getUUID());
                         inventoryForMerchantVo.setProductNo(productCommodity.getProductNo());
                         inventoryForMerchantVo.setSafeStockQuantity(safeStockQuantity);
                         inventoryForMerchantVo.setWarehouseCode(warehouseCode);
                         inventoryForMerchantVo.setMerchantCode(merchantCode);
                         inventoryForMerchantVo.setCreateDate(new Date());
                         inventoryForMerchantVo.setModityDate(new Date());
                         if (stockMapper.insertInventoryForMerchant(inventoryForMerchantVo) <= 0) {
                             commodityNoList_failed.add(productCommodity.getProductNo());
                         }
                     }
                 } else {
                     Integer oldSafeStockQuantity = Integer.valueOf(result.get("safe_stock_quantity").toString());
                     if (null != qty && qty.getInventoryQuantity() + oldSafeStockQuantity > qty.getStoredNumber()) {
                         safeStockQuantity = Math.min(qty.getInventoryQuantity() + oldSafeStockQuantity - qty.getStoredNumber(), newSafeStockQuantity);
                     }
                     if (safeStockQuantity >= 0) {
                         // 更新减去安全库存之后的wms库存
                         try {
                             inventoryDomainService.updateInventoryForMerchant(productCommodity.getProductNo(), warehouseCode, oldSafeStockQuantity - safeStockQuantity,
                                     NumberUtils.INTEGER_ONE);
                         } catch (WPIBussinessException we) {
                             executed_flag = false;
                             commodityNoList_failed.add(productCommodity.getProductNo());
                             logger.error("商家：{} | 货品条码：{}更新库存异常。| wms-returnCode: {}, wms-returnMessage: {}。",
                                     new Object[] { merchantCode, productCommodity.getProductNo(), we.getReturnCode(), we.getReturnMessage() });
                         }
                     }
                     // 记录实际减去的安全库存
                     if (executed_flag && stockMapper.updateInventoryForMerchant(merchantCode, productCommodity.getProductNo(), safeStockQuantity) <= 0) {
                         commodityNoList_failed.add(productCommodity.getProductNo());
                     }
                 }
             }
        }
        return commodityNoList_failed;
    }
    
    /**
     * 根据商品No更新商家安全库存，并返回给wms应扣除的安全库存数
     * 
     * @param merchantCode
     * @param wareHouseCode
     * @param CommodityNo
     * @param safeStockQuantity
     * @return {给wms应扣除的安全库存数,出错报错信息}
     */
    public Map<Integer,String> updateSafeStockQuantity(String merchantCode, String wareHouseCode, 
    		String productNo, Integer stockQuantity) throws Exception {
    	Map<Integer,String> safeStockQuantityMap = new HashMap<Integer,String>();
        Integer safeStockQuantity = 0;
        Map<String, Object> result_all = stockMapper.querySafeStockQuantity(merchantCode);
        // 如果未设定就直接返回0
        if (MapUtils.isEmpty(result_all)) {
        	safeStockQuantityMap.put(0, null);
            return safeStockQuantityMap;
        }

        // 获取预占库存
        Integer preQuantity = 0;
        List<InventoryAssistVo> qtys = inventoryForMerchantService.queryProductInventory(Arrays.asList(productNo), wareHouseCode, 0);// 只更新正品库存
        if (CollectionUtils.isNotEmpty(qtys)) {
            preQuantity = qtys.get(0).getStoredNumber();
        }

        Map<String, Object> result_wms = stockMapper.queryInventoryForMerchant(merchantCode, productNo);
        if (MapUtils.isEmpty(result_wms)) {
            Integer safeStock_all = Integer.valueOf(result_all.get("safe_stock_quantity").toString());
            safeStockQuantity = stockQuantity - preQuantity - safeStock_all > 0 ? safeStock_all : Math.max(stockQuantity - preQuantity, 0);
            InventoryForMerchantVo inventoryForMerchantVo = new InventoryForMerchantVo();
            inventoryForMerchantVo.setId(UUIDGenerator.getUUID());
            inventoryForMerchantVo.setProductNo(productNo);
            inventoryForMerchantVo.setSafeStockQuantity(safeStockQuantity);
            inventoryForMerchantVo.setWarehouseCode(wareHouseCode);
            inventoryForMerchantVo.setMerchantCode(merchantCode);
            inventoryForMerchantVo.setCreateDate(new Date());
            inventoryForMerchantVo.setModityDate(new Date());
            stockMapper.insertInventoryForMerchant(inventoryForMerchantVo);
        } else {
            Integer safeStock_all = Integer.valueOf(result_all.get("safe_stock_quantity").toString());
            Integer safeStock_wms = Integer.valueOf(result_wms.get("safe_stock_quantity").toString());
            if(stockQuantity - preQuantity - safeStock_all>=0){
            	//safeStockQuantity = stockQuantity - preQuantity - safeStock_all > 0 ? safeStock_all : Math.max(stockQuantity - preQuantity, 0);
            	safeStockQuantity = safeStock_all;
            	 if (safeStock_wms != safeStockQuantity) {
                     stockMapper.updateInventoryForMerchant(merchantCode, productNo, safeStockQuantity);
                 }
            }else{
            	logger.error("商家{}修改货品编码{}的库存错误，总库存{}小于（预占库存{}加安全库存{}）", new Object[]{merchantCode,productNo,stockQuantity,preQuantity,safeStock_all});
            	safeStockQuantity = null;
            	safeStockQuantityMap.clear();
            	safeStockQuantityMap.put(null, String.format("总库存(%d)不得小于（预占库存(%d)加安全库存(%d)）",stockQuantity,preQuantity,safeStock_all));
            	//throw new Exception();
            }
        }
        if(safeStockQuantity!=null){
        	safeStockQuantityMap.clear();
        	safeStockQuantityMap.put(safeStockQuantity, null);
        }
        return safeStockQuantityMap;
    }

    /**
     * 根据商家编码获取设定的总安全库存数
     * 
     * @param merchantCode
     * @return 设定的总安全库存数 null：未设定；其他：设定值返回
     */
    public Integer getSafeStockQuantity(String merchantCode) {
        Map<String, Object> result = stockMapper.querySafeStockQuantity(merchantCode);
        if (MapUtils.isNotEmpty(result)) {
            return Integer.valueOf(result.get("safe_stock_quantity").toString());
        } else {
            return null;
        }
    }
    
    /**
     * 根据货品编码获取该货品的实际安全库存数
     * 
     * @param merchantCode
     * @return 该货品的实际安全库存数 未设定也返回0
     */
    public Integer getSafeStockQuantityByProductNo(String merchantCode,String productNo) {
        Map<String, Object> result = stockMapper.queryInventoryForMerchant(merchantCode, productNo);
        if (MapUtils.isNotEmpty(result)) {
            return Integer.valueOf(result.get("safe_stock_quantity").toString());
        } else {
            return 0;
        }
    }
    
    /**
     * 首页-查询库存小于5
     */
	public Set<String> getStockTips(String merchantCode, String warehouseCode)
			throws Exception {
		Set<String> stockTips = new HashSet<String>();
		try {
			stockTips = (Set<String>) redisTemplate.opsForHash().get("com.yougou.kaidian.order.shouye.stocktipsSet",merchantCode);
		} catch (Exception e) {
			logger.error("首页获取商品提示数据异常。", e);
		}
		if(stockTips!=null) {
			return stockTips;
		}else{
			stockTips = new HashSet<String>();
		}
		Set<String> waitSends = new HashSet<String>();
		// 缺货单=[], 待换货单=[]
		Map<String, List<String>> _tips = orderForMerchantApi
				.findOutOfStockAndWaitDeliveryOrdersByMerchantCode(merchantCode);
		if (MapUtils.isNotEmpty(_tips)) {
			waitSends.addAll((Collection<? extends String>) MapUtils.getObject(
					_tips, "待换货单"));
		}
		if (CollectionUtils.isNotEmpty(waitSends)) {
			// productNo, orderNos(多个订单逗号分隔)
			Set<String> _sets = new HashSet<String>();
			for (String _orderNo : waitSends) {
				List<OrderDetail4sub> details = orderForMerchantApi
						.findOrderDetailForOrderSubAll(merchantCode, _orderNo,
								null, null);
				if (CollectionUtils.isNotEmpty(details)) {
					for (OrderDetail4sub orderDetail4sub : details) {
						_sets.add(orderDetail4sub.getProdNo());
					}
				}
			}
			if (CollectionUtils.isNotEmpty(_sets)) {
				Map<String, Object[]> _qtyMaps=null;
				try {
					if(warehouseCode!=null){
						_qtyMaps = inventoryDomainService.querySalesInventroyBathForMerchant(new ArrayList<String>(_sets), warehouseCode);
					}
				} catch (WPIBussinessException e) {
					String message="";
					for(String str:_sets){
						message=message+str+",";
					}
					logger.error("inventoryDomainService.querySalesInventroyBathForMerchant调用异常,参数-productNo:{} warehouseCode:{}", new Object[]{message,warehouseCode});
					throw new WPIBussinessException("inventoryDomainService.querySalesInventroyBathForMerchant调用异常",e);
				}
				
				if (MapUtils.isNotEmpty(_qtyMaps)) {
					for (String proNo : _qtyMaps.keySet()) {
						Object[] _obj = _qtyMaps.get(proNo);
						if (ArrayUtils.isNotEmpty(_obj)) {
							Integer qty = (Integer) _obj[1];
							Integer safeStock = this
									.getSafeStockQuantityByProductNo(
											merchantCode, proNo);
							if (null == qty || qty + safeStock < 5) {
								stockTips.add(proNo);
							}
						}
					}
				}
			}
		}
		// 加入redisTemplate
		if(redisTemplate.hasKey("com.yougou.kaidian.order.shouye.stocktipsSet")){
			redisTemplate.opsForHash().put("com.yougou.kaidian.order.shouye.stocktipsSet", merchantCode,stockTips);
		}else{
			redisTemplate.opsForHash().put("com.yougou.kaidian.order.shouye.stocktipsSet", merchantCode,stockTips);
			redisTemplate.expire("com.yougou.kaidian.order.shouye.stocktipsSet", 5, TimeUnit.MINUTES);
		}
		return stockTips;
	}
	
	/** 
	 * @see com.yougou.kaidian.stock.service.IStockService#updateInventoryForMerchant(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer) 
	 */
	@Override
	public Map<String, Object> updateInventoryForMerchant(String merchantCode,
			String warehouseCode, String commodityCode, Integer quantity) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		//根据商品No更新商家安全库存，并返回给wms应扣除的安全库存数
		//Integer safeStockQuantity = updateSafeStockQuantity(merchantCode, warehouseCode, commodityCode, quantity);
		Map<Integer,String> safeStockQuantityMap = updateSafeStockQuantity(merchantCode, warehouseCode, commodityCode, quantity);
		Integer safeStockQuantity = (Integer)(safeStockQuantityMap.keySet().toArray()[0]);
		if(safeStockQuantity!=null){
			//更新库存(提供给商家用)
			result.putAll(inventoryDomainService.updateInventoryForMerchant(commodityCode, warehouseCode, quantity
                - safeStockQuantity, NumberUtils.INTEGER_ZERO));
			result.put("safeStockQuantity", safeStockQuantity);
		}else{
			result.put(null, safeStockQuantityMap.get(safeStockQuantity));
		}
		return result;
	}

	/** 
	 * @see com.yougou.kaidian.stock.service.IStockService#getSafeStock(java.lang.String, java.lang.String) 
	 */
	@Override
	public PageFinder<Map<String, Object>> getSafeStock(String merchantCode,
			String productNo,
			String safeStockQuantityGe, 
			String safeStockQuantityLe, 
			Query query) {
		List<Map<String, Object>> list = stockMapper.getSafeStock(merchantCode,productNo,
				safeStockQuantityGe,
				safeStockQuantityLe,
				query);
		int count = stockMapper.getSafeStockCount(merchantCode,productNo,
				safeStockQuantityGe,
				safeStockQuantityLe,
				query);
		return new PageFinder<Map<String, Object>>(query.getPage(), query.getPageSize(), count, list);
	}
	
	/** 
	 * @see com.yougou.kaidian.stock.service.IStockService#delSafeStock(java.lang.String) 
	 */
	@Override
	public boolean delSafeStock(String[] id) {
		boolean flag = false;
		try{
			for(String idstr : id){
				stockMapper.delSafeStock(idstr);
			}
			flag = true;
		}catch(Exception e){
			flag = false;
			logger.error("del安全库存时服务器发生错误", e);
		}
		return flag;
	}
	
	/** 
	 * @see com.yougou.kaidian.stock.service.IStockService#updateSafeStock(java.lang.String) 
	 */
	@Override
	public boolean updateSafeStock(String id, String merchantCode,Integer safeStockQuantity) {
		boolean flag = false;
		try{
			if(safeStockQuantity>getSafeStockQuantity(merchantCode)){
				logger.error("update安全库存时服务器发生错误，设置的实际安全库存{}大于商家设置的安全库存数量{}。", 
						new Object[]{safeStockQuantity,getSafeStockQuantity(merchantCode)});
			}else{
				stockMapper.modifySafeStock(id,safeStockQuantity);
				flag = true;
			}
		}catch(Exception e){
			logger.error("update安全库存时服务器发生错误", e);
		}
		return flag;
	}
	
}
