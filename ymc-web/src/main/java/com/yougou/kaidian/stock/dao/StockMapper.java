package com.yougou.kaidian.stock.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.stock.model.vo.InventoryForMerchantVo;

public interface StockMapper {

    /**
     * 查询仓库合作模式（优购，非优购）
     * 
     * @param supplierCode
     * @return
     */
    Map<String, Object> queryStorageModel(String supplierCode);

    /**
     * 查询商家安全库存数量
     * 
     * @param merchantCode
     * @return
     */
    Map<String, Object> querySafeStockQuantity(String merchantCode);

	/**
	 * 查询商家是否设置安全库存
	 * 
	 * @param merchantCode
	 * @return
	 */
	Integer querySafeStockQuantityCount(String merchantCode);

    /**
     * 更新商家安全库存数量
     * 
     * @param merchantCode
     * @param safeStockQuantity
     * @return
     */
    int updateSafeStockQuantity(@Param("merchantCode") String merchantCode, @Param("safeStockQuantity") Integer safeStockQuantity);

    /**
     * 插入商家安全库存数量
     * 
     * @param id
     * @param merchantCode
     * @param safeStockQuantity
     * @return
     */
    int insertSafeStockQuantity(@Param("id") String id, @Param("merchantCode") String merchantCode, @Param("safeStockQuantity") Integer safeStockQuantity);

    /**
     * 查询招商备份的原始库存数量
     * 
     * @param merchantCode
     * @param commodityCode
     * @return
     */
    Map<String, Object> queryInventoryForMerchant(@Param("merchantCode") String merchantCode, @Param("productNo") String productNo);

    /**
     * 更新招商备份的原始库存数量
     * 
     * @param id
     * @param merchantCode
     * @param commodityCode
     * @return
     */
    int updateInventoryForMerchant(@Param("merchantCode") String merchantCode, @Param("productNo") String productNo, @Param("safeStockQuantity") Integer safeStockQuantity);

    /**
     * 插入招商备份的原始库存数量
     * 
     * @param merchantCode
     * @param commodityCode
     * @param inventory
     * @return
     */
    int insertInventoryForMerchant(InventoryForMerchantVo vo);

	/** 
	 * getSafeStock:供管理员查询实际安全库存
	 * @author li.n1 
	 * @param merchantCode
	 * @param productNo
	 * @return 
	 * @since JDK 1.6 
	 */  
    List<Map<String, Object>> getSafeStock(@Param("merchantCode") String merchantCode,
    		@Param("productNo") String productNo,
    		@Param("safeStockQuantityGe") String safeStockQuantityGe,
			@Param("safeStockQuantityLe") String safeStockQuantityLe,
    		@Param("query") Query query);

	/** 
	 * getSafeStockCount:供管理员查询实际安全库存 
	 * @author li.n1 
	 * @param merchantCode
	 * @param productNo
	 * @param safeStockQuantity
	 * @param query
	 * @return 
	 * @since JDK 1.6 
	 */  
	int getSafeStockCount(@Param("merchantCode") String merchantCode,
			@Param("productNo") String productNo,
			@Param("safeStockQuantityGe") String safeStockQuantityGe,
			@Param("safeStockQuantityLe") String safeStockQuantityLe, 
			@Param("query") Query query);

	/** 
	 * @author li.n1 
	 * @param id 
	 * @since JDK 1.6 
	 */  
	void delSafeStock(@Param("id") String id);

	/** 
	 * @author li.n1 
	 * @param id 
	 * @param safeStockQuantity 
	 * @since JDK 1.6 
	 */  
	void modifySafeStock(@Param("id") String id,@Param("safeStockQuantity") Integer safeStockQuantity);
	
	/**
	 * 从商家主表查询仓库编码
	 * @param merchantCode
	 * @return 仓库编码（商家主表的同步数据）
	 */
	String queryWarehouseCodeByMerchantCode( @Param("merchantCode") String merchantCode );
}
