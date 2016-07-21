package com.belle.yitiansystem.merchant.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.common.commodity.pojo.Brand;

public interface MerchantBrandMapper {

	/**
	 * 根据品牌查对应的商家编码
	 * 
	 * @param map
	 * @return
	 */
	public List<String> queryMerchantByBrands(Map<String, String> map);
	
	/**
	 * 根据品牌或者商家名称查对应的优购货品userId
	 * 
	 * @param map
	 * @return
	 */
	public List<String> queryMerchantByBrandsOrMerchantName(Map<String, String> map);
	/**
	 * 根据商家Id查询其所有二级分类目录信息（包括品牌编号）
	 * @param supplierId
	 * @return
	 */
	public List<Brand> querySecondLevelCatsBySupplierId(@Param("supplierId")String supplierId);
	
	
}
