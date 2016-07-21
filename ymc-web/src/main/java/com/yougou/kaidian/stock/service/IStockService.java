package com.yougou.kaidian.stock.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.stock.model.vo.InventoryHDQueryVO;
import com.yougou.kaidian.stock.model.vo.InventoryVo;
import com.yougou.kaidian.stock.model.vo.KeyValueVo;

/**
 * 库存重构 服务接口(新)
 * 
 * @author huang.tao
 * 
 */
public interface IStockService {

    /**
     * 批量更新库存
     * 
     * @param merchantCode
     *            商家编码
     * @param wareHouseCode
     *            仓库编码
     * @param paramList
     *            库存列表
     * @param progress
     *            进度条(100)
     * @return 错误列表
     * @throws Exception
     */
    List<Object> batchUpdateStock(String merchantCode, String wareHouseCode, List<Object[]> paramList, int progress) throws Exception;

    /**
     * 查询商家所有库存列表
     * 
     * @param merchantCode
     *            商家编码
     * @return
     * @throws Exception
     */
    List<Object[]> queryAllProductStocks(String merchantCode) throws Exception;

    /**
     * 获取年份
     */
    List<KeyValueVo> getYearsList();

    Map<String, Object> queryMerchantInventoryByThirdPartyCode(String merchantCode, String thirdPartyCode, String warehouseCode) throws Exception;

    /**
     * 库存查询（调用商品接口查询后再去分批取库存）
     * 
     * @param queryVO
     * @param query
     * @return
     */
    PageFinder<InventoryVo> queryInventoryNew(InventoryHDQueryVO queryVO, Query query);

    /**
     * 导出库存(调用商品接口、然后分批取库存)
     * 
     * @param queryVO
     * @return
     */
    List<InventoryVo> exportInventoryNew(InventoryHDQueryVO queryVO);

    /**
     * 更新商家设定的安全库存数量（存在就更新，不存在就插入）
     * 
     * @param merchantCode
     * @param safeStockQuantity
     * @return
     */
    Boolean updateSafeStockQuantityForSet(String merchantCode, String warehouseCode, Integer safeStockQuantity);

    /**
     * 根据商品No更新商家安全库存，并返回给wms应扣除的安全库存数
     * 
     * @param merchantCode
     * @param wareHouseCode
     * @param CommodityNo
     * @param safeStockQuantity
     * @return 给wms应扣除的安全库存数，出错的报错信息
     */
    public Map<Integer,String> updateSafeStockQuantity(String merchantCode, String wareHouseCode, String CommodityNo, Integer stockQuantity) throws Exception;
    
    /**
     * 根据商家编码获取设定的总安全库存数
     * 
     * @param merchantCode
     * @return 设定的总安全库存数 null：未设定；其他：设定值返回
     */
    public Integer getSafeStockQuantity(String merchantCode);
    
    /**
     * 根据货品编码获取该货品的实际安全库存数
     * 
     * @param merchantCode
     * @return 该货品的实际安全库存数 未设定也返回0
     */
    public Integer getSafeStockQuantityByProductNo(String merchantCode, String productNo);
    
    /**
     * 首页查询库存小于5
     * @param merchantCode
     * @param warehouseCode
     * @return
     * @throws Exception
     */
    public Set<String> getStockTips(String merchantCode, String warehouseCode)throws Exception;

	/** 
	 * updateInventoryForMerchant:修改库存统一入口
	 * 获取该货品的实际安全库存数，更新给wms的库存要减去该数目
	 * @author li.n1 
	 * @param merchantCode 商家编码
	 * @param warehouseCode 仓库编码
	 * @param commodityCode 货品编码
	 * @param quantity	总库存
	 * @return 
	 * @since JDK 1.6 
	 */  
	Map<String, Object> updateInventoryForMerchant(String merchantCode,
			String warehouseCode, String commodityCode, Integer quantity) throws Exception;

	/** 
	 * getSafeStock:供管理员查看实际安全库存 
	 * @author li.n1 
	 * @param productNo 货品编号
	 * @param merchantCode 商家编码
	 * @return 
	 * @since JDK 1.6 
	 */  
	PageFinder<Map<String, Object>> getSafeStock(String merchantCode,
			String productNo,
			String safeStockQuantityGe,
			String safeStockQuantityLe,
			Query query);

	/** 
	 * updateSafeStock:修改安全库存
	 * @author li.n1 
	 * @param id
	 * @param merchantCode 
	 * @return 
	 * @since JDK 1.6 
	 */  
	boolean updateSafeStock(String id, String merchantCode,Integer safeStockQuantity);

	/** 
	 * delSafeStock:删除安全库存（物理删除） 
	 * @author li.n1 
	 * @param id
	 * @return 
	 * @since JDK 1.6 
	 */  
	boolean delSafeStock(String[] id);
}
