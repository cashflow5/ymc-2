package com.belle.yitiansystem.merchant.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.belle.yitiansystem.merchant.service.IMerchantApiInventoryService;
import com.yougou.api.exception.BusinessServiceException;
//import com.yougou.cms.api.ICMSApi;
import com.yougou.dto.input.QueryInventoryInputDto;
import com.yougou.dto.input.UpdateInventoryInputDto;
import com.yougou.dto.output.QueryInventoryOutputDto;
import com.yougou.dto.output.QueryInventoryOutputDto.Item;
import com.yougou.dto.output.UpdateInventoryOutputDto;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.product.ProductCommodity;
import com.yougou.wms.wpi.inventory.service.IInventoryDomainService;
import com.yougou.wms.wpi.warehouse.service.IWarehouseCacheService;

@Service
public class MerchantApiInventoryServiceImpl implements IMerchantApiInventoryService {
	
	@Resource
	private IWarehouseCacheService warehouseCacheService;
	
	@Resource
	private IInventoryDomainService inventoryDomainService;
	
	@Resource
	ICommodityBaseApiService commodityBaseApiService;
	
	private static final Logger logger = LoggerFactory.getLogger("openapiBusiness");

	
	@Override
	public Object updateMerchantInventory(UpdateInventoryInputDto dto) throws Exception {
//		Date modityDate = new Date();
//		Session session = null;
		UpdateInventoryOutputDto resultDto = null;
		
/*		try {
			session = SessionFactoryUtils.getSession(sessionFactory, true);
			StringBuilder sqlBuilder = new StringBuilder();
			
			sqlBuilder.setLength(0);
			sqlBuilder.append(" select ");
			sqlBuilder.append(" t2.id as commodity_id, t2.product_no as commodity_code ");
			sqlBuilder.append(" from ");
			sqlBuilder.append(" tbl_commodity_style t1 inner join tbl_commodity_product t2 on (t1.id = t2.commodity_id) ");
			sqlBuilder.append(" where ");
			sqlBuilder.append(" t1.delete_flag = ? and t1.merchant_code = ? and t2.delete_flag = ? and t2.third_party_code = ? ");
			SQLQuery sqlQuery = session.createSQLQuery(sqlBuilder.toString());
			sqlQuery.setParameter(0, NumberUtils.INTEGER_ONE);
			sqlQuery.setParameter(1, dto.getMerchant_code());
			sqlQuery.setParameter(2, NumberUtils.INTEGER_ONE);
			sqlQuery.setParameter(3, dto.getThird_party_code());
			sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			sqlQuery.addScalar("commodity_id", Hibernate.STRING);
			sqlQuery.addScalar("commodity_code", Hibernate.STRING);
			Map resultMap = (Map) sqlQuery.uniqueResult();
			if (MapUtils.isEmpty(resultMap)) {
				businessLogger.log("inventory.update",YOPBusinessCode.RUNTIME_ERROR,"商品无对应货品",dto.getMerchant_code());
				return new BusinessServiceException("您发布的商品无对应货品" + dto.getMerchant_code());
			}
			
			// 校验商家是否绑定虚拟仓库
			Map<String, ?> temporaryMap = warehouseCacheService.getWarehouseByMerchantCode(dto.getMerchant_code());
			if (MapUtils.isEmpty(temporaryMap)) {
				//throw new IllegalStateException("商家未绑定虚拟仓库.");
				logger.info("错误编码[501],接口[inventory.update],错误信息[未绑定虚拟仓库],商家编码[{}]",dto.getMerchant_code());
				return new BusinessServiceException("商家未绑定虚拟仓库.");
			}
			String warehouseCode = temporaryMap.keySet().iterator().next();
			
			Integer updateType = NumberUtils.INTEGER_ONE.equals(dto.getUpdate_type()) ? dto.getUpdate_type() : NumberUtils.INTEGER_ZERO;
			temporaryMap = inventoryDomainService.updateInventoryForMerchant(MapUtils.getString(resultMap, "commodity_code"), warehouseCode, dto.getQuantity(), updateType);
			
			resultDto = new UpdateInventoryOutputDto();
			resultDto.setThird_party_code(dto.getThird_party_code());
			resultDto.setModified((Date) temporaryMap.values().iterator().next());
		}  finally {
			SessionFactoryUtils.releaseSession(session, sessionFactory);
		}
*/
		Map<String, Object> mapQueryParam = new HashMap<String, Object>();
		mapQueryParam.put("merchantCode", dto.getMerchant_code());
		mapQueryParam.put("thirdPartyCode", dto.getThird_party_code());
		List<ProductCommodity> lstProductCommodity = commodityBaseApiService.getProductCommodities(mapQueryParam, false);
		ProductCommodity productCommodity = null;
		if (lstProductCommodity == null || lstProductCommodity.isEmpty()) {
			return new BusinessServiceException("您发布的商品无对应货品" + dto.getMerchant_code());
		} else {
			productCommodity = lstProductCommodity.get(0);
		}
			
		// 校验商家是否绑定虚拟仓库
		Map<String, ?> temporaryMap = warehouseCacheService.getWarehouseByMerchantCode(dto.getMerchant_code());
		if (MapUtils.isEmpty(temporaryMap)) {
			logger.info("商家未绑定虚拟仓库." + dto.getMerchant_code());
			return new BusinessServiceException("商家未绑定虚拟仓库.");
		}
		String warehouseCode = temporaryMap.keySet().iterator().next();
		
		Integer updateType = NumberUtils.INTEGER_ONE.equals(dto.getUpdate_type()) ? dto.getUpdate_type() : NumberUtils.INTEGER_ZERO;
		temporaryMap = inventoryDomainService.updateInventoryForMerchant(productCommodity.getProductNo(), warehouseCode, dto.getQuantity(), updateType);
		
		resultDto = new UpdateInventoryOutputDto();
		resultDto.setThird_party_code(dto.getThird_party_code());
		resultDto.setModified((Date) temporaryMap.values().iterator().next());
		return resultDto;
	}

	@Override
	public QueryInventoryOutputDto queryMerchantInventory(QueryInventoryInputDto dto) throws Exception {
/*		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" from ");
		sqlBuilder.append(" tbl_commodity_style t1 ");
		sqlBuilder.append(" inner join tbl_sp_supplier t2 on(t1.merchant_code = t2.supplier_code) ");
		sqlBuilder.append(" inner join tbl_wms_virtual_warehouse t3 on(t2.inventory_code = t3.virtual_warehouse_code) ");
		sqlBuilder.append(" inner join tbl_commodity_product t4 on(t1.id = t4.commodity_id) ");
		sqlBuilder.append(" left join tbl_wms_inventory t5 on(t3.id = t5.virtual_warehouse_id and t4.product_no = t5.commodity_code) ");
		sqlBuilder.append(" where ");
		sqlBuilder.append(" t1.merchant_code = ? and t1.delete_flag = ? and t4.delete_flag = ? ");
		
		// 定义 SQL 参数列表
		List<Serializable> sqlParameters = new ArrayList<Serializable>();
		sqlParameters.add(dto.getMerchant_code());
		sqlParameters.add(NumberUtils.INTEGER_ONE);
		sqlParameters.add(NumberUtils.INTEGER_ONE);
		
		if (StringUtils.isNotBlank(dto.getBrand_no())) {
			sqlBuilder.append(" and t1.brand_no = ? ");
			sqlParameters.add(dto.getBrand_no());
		}
		if (StringUtils.isNotBlank(dto.getCat_no())) {
			sqlBuilder.append(" and t1.cat_no = ? ");
			sqlParameters.add(dto.getCat_no());
		}
		if (StringUtils.isNotBlank(dto.getYears())) {
			sqlBuilder.append(" and t1.years = ? ");
			sqlParameters.add(dto.getYears());
		}
		if (StringUtils.isNotBlank(dto.getStyle_no())) {
			sqlBuilder.append(" and t1.style_no = ? ");
			sqlParameters.add(dto.getStyle_no());
		}
		if (StringUtils.isNotBlank(dto.getCommodity_status()) && !dto.getCommodity_status().equals("-1")) {
			if (dto.getCommodity_status().indexOf(",") == -1) {
				sqlBuilder.append(" and t1.commodity_status = ? ");
				sqlParameters.add(dto.getCommodity_status());
			} else {
				sqlBuilder.append(" and t1.commodity_status in(?) ");
				sqlParameters.add(dto.getCommodity_status());
			}
		}
		if (StringUtils.isNotBlank(dto.getOrder_distribution_side()) && !dto.getOrder_distribution_side().equals("-1")) {
			sqlBuilder.append(" and t1.order_distribution_side = ? ");
			sqlParameters.add(dto.getOrder_distribution_side());
		}
		if (StringUtils.isNotBlank(dto.getThird_party_code())) {
			sqlBuilder.append(" and t4.third_party_code = ? ");
			sqlParameters.add(dto.getThird_party_code());
		}
		if (StringUtils.isNotBlank(dto.getProduct_no())) {
			sqlBuilder.append(" and t4.product_no = ? ");
			sqlParameters.add(dto.getProduct_no());
		}
		
		Session session = null;
		QueryInventoryOutputDto outputDto = null;
		
		try {
			session = SessionFactoryUtils.getSession(sessionFactory, true);

			// 总记录数
			SQLQuery sqlQuery = session.createSQLQuery(" select count(*) " + sqlBuilder.toString());
			for (int i = 0; i < sqlParameters.size(); i++) {
				sqlQuery.setParameter(i, sqlParameters.get(i));
			}
			
			int totalCount = ((Number) sqlQuery.uniqueResult()).intValue();
			outputDto = new QueryInventoryOutputDto(dto.getPage_index(), dto.getPage_size(), totalCount);
			
			if (totalCount > 0) {
				// 反向拼接查询字段
				sqlBuilder.insert(0, " ifnull(t5.inventory_quantity, 0) as total_stock_quantity, t5.modity_date as modified ");
				sqlBuilder.insert(0, " t4.product_no, t4.third_party_code, ");
				sqlBuilder.insert(0, " t1.no as commodity_no, t1.style_no, t1.supplier_code, t1.commodity_name, t1.commodity_status, t1.sale_price, t1.public_price, t1.default_pic as commodity_image, ");
				sqlBuilder.insert(0, " select ");
				
				// 填充条件参数
				sqlQuery = session.createSQLQuery(sqlBuilder.toString());
				sqlQuery.setFirstResult(dto.getPage_index());
				sqlQuery.setMaxResults(dto.getPage_size());
				for (int i = 0; i < sqlParameters.size(); i++) {
					sqlQuery.setParameter(i, sqlParameters.get(i));
				}
				
				sqlQuery.addScalar("commodity_no", Hibernate.STRING);
				sqlQuery.addScalar("style_no", Hibernate.STRING);
				sqlQuery.addScalar("supplier_code", Hibernate.STRING);
				sqlQuery.addScalar("commodity_name", Hibernate.STRING);
				sqlQuery.addScalar("commodity_status", Hibernate.INTEGER);
				sqlQuery.addScalar("product_no", Hibernate.STRING);
				sqlQuery.addScalar("third_party_code", Hibernate.STRING);
				sqlQuery.addScalar("total_stock_quantity", Hibernate.INTEGER);
				sqlQuery.addScalar("modified", Hibernate.TIMESTAMP);
				sqlQuery.addScalar("sale_price", Hibernate.BIG_DECIMAL);
				sqlQuery.addScalar("public_price", Hibernate.BIG_DECIMAL);
				sqlQuery.addScalar("commodity_image", Hibernate.STRING);
				sqlQuery.setResultTransformer(new AliasToBeanResultTransformer(QueryInventoryOutputDto.Item.class) {
					@Override
					public Object transformTuple(Object[] tuple, String[] aliases) {
						try {
							Object object = super.transformTuple(tuple, aliases);
							String commodityNo = (String) BeanUtils.getProperty(object, "commodity_no");
							BeanUtils.setProperty(object, "commodity_items_page", OrderUtils.getCommodityPageUrl(commodityNo));
							return object;
						} catch (Exception e) {
							throw new HibernateException(e);
						}
					}
				});
				outputDto.setItems(sqlQuery.list());
			}
		}  finally {
			SessionFactoryUtils.releaseSession(session, sessionFactory);
		}
*/
		// 校验商家是否绑定虚拟仓库
		Map<String, String> temporaryMap = warehouseCacheService.getWarehouseByMerchantCode(dto.getMerchant_code());
		if (MapUtils.isEmpty(temporaryMap)) {
			logger.info("商家未绑定虚拟仓库." + dto.getMerchant_code());
			throw new BusinessServiceException("商家未绑定虚拟仓库.");
		}
		String warehouseCode = temporaryMap.keySet().iterator().next();
		QueryInventoryOutputDto outputDto = new QueryInventoryOutputDto(dto.getPage_index(), dto.getPage_size(), 0);
		Map<String, Object> mapQueryParam = new HashMap<String, Object>();
		mapQueryParam.put("merchantCode", dto.getMerchant_code());
		if (StringUtils.isNotBlank(dto.getBrand_no())) {
			mapQueryParam.put("brandNo", dto.getBrand_no());
		}
		if (StringUtils.isNotBlank(dto.getCat_no())) {
			mapQueryParam.put("cateNo", dto.getCat_no());
		}
		if (StringUtils.isNotBlank(dto.getYears())) {
			mapQueryParam.put("year", dto.getYears());
		}
		if (StringUtils.isNotBlank(dto.getStyle_no())) {
			mapQueryParam.put("styleNo", dto.getStyle_no());
		}
		if (StringUtils.isNotBlank(dto.getCommodity_status()) && !dto.getCommodity_status().equals("-1")) {
			mapQueryParam.put("commodityStatusList", dto.getCommodity_status().split(","));
		}
		if (StringUtils.isNotBlank(dto.getThird_party_code())) {
			mapQueryParam.put("thirdPartyCode", dto.getThird_party_code());
		}
		if (StringUtils.isNotBlank(dto.getProduct_no())) {
			mapQueryParam.put("productNo", dto.getProduct_no());
		}
		mapQueryParam.put("orderBy", "pro.product_no");
		List<ProductCommodity> lstProductCommodity = commodityBaseApiService.getProductCommodities(mapQueryParam, false, dto.getPage_index(), dto.getPage_size());
		if(lstProductCommodity != null && !lstProductCommodity.isEmpty()) {
			outputDto.setTotal_count(commodityBaseApiService.getProductCommoditiesCount(mapQueryParam));
			List<Item> lstItem = new ArrayList<Item>(lstProductCommodity.size());
			Item item = null;
			int saleCount = 0;
			for(ProductCommodity productCommodity : lstProductCommodity){
				item = new Item();
				Map<Integer, Date> mapResult = inventoryDomainService.querySalesInventroyForMerchant(productCommodity.getProductNo(), warehouseCode);
				saleCount = !MapUtils.isEmpty(mapResult)?mapResult.keySet().iterator().next():0;
				item.setCommodity_image(productCommodity.getDefaultPic());
				item.setCommodity_items_page(commodityBaseApiService.getCommodityPageUrlWithExtension(productCommodity.getCommodityNo()));
				item.setCommodity_name(productCommodity.getCommodityName());
				item.setCommodity_no(productCommodity.getCommodityNo());
				item.setCommodity_status(productCommodity.getCommodityStatus());
				item.setModified(mapResult.get(saleCount));
				item.setProduct_no(productCommodity.getProductNo());
				item.setPublic_price(BigDecimal.valueOf(productCommodity.getPublicPrice()));
				item.setSale_price(BigDecimal.valueOf(Double.valueOf(productCommodity.getSalePrice())));
				item.setStyle_no(productCommodity.getStyleNo());
				item.setSupplier_code(productCommodity.getSupplierCode());
				item.setThird_party_code(productCommodity.getThirdPartyCode());
				item.setTotal_stock_quantity(saleCount);
				lstItem.add(item);
			}
			if (lstProductCommodity != null && !lstProductCommodity.isEmpty()) {
				outputDto.setItems(lstItem);
			}
		}
		return outputDto;
	}
}
