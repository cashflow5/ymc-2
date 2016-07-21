package com.yougou.kaidian.commodity.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.common.commodity.pojo.Brand;
import com.yougou.kaidian.common.commodity.pojo.Cat;

public interface CommodityPropertyMapper {
	/**
	 * <p>通过merchantCode查询商家授权分类</p>
	 * 
	 * @param merchantCode
	 * @return
	 */
	public List<Cat> querySupplierCatList(String merchantCode);
	
	/**
	 * <p>通过brandNo查询商家该品牌授权的分类</p>
	 * 
	 * @param merchantCode
	 * @param brandNo
	 * @return
	 */
	List<Cat> queryCategoryListByBrandNo(@Param("merchantCode")String merchantCode, @Param("brandNo")String brandNo);
	
	List<Cat> querySupplierCatListByStructName(@Param("merchantCode")String merchantCode, @Param("structName")String structName);
	
	/**
	 * <p>
	 * 	通过merchantCode获取brand_no列表</br>
	 * 	数据来源tbl_sp_brand.brand_no
	 * </p>
	 * 
	 * @param merchantCode
	 * @return
	 */
	public List<Brand> querySupplierBrandList(String merchantCode);
	
	/**
	 * queryCategoryListByBrandNoAndCate:查询 品牌下的分类是否有权限（授权）
	 * @author li.n1 
	 * @param merchantCode 商家编码
	 * @param brandNo 品牌
	 * @param catNo 分类
	 * @return 
	 * @since JDK 1.6
	 */
	public Map<String, Object> queryCategoryListByBrandNoAndCate(
			@Param("merchantCode") String merchantCode, 
			@Param("brandNo") String brandNo, 
			@Param("catNo") String catNo);
}
