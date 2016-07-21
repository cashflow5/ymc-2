package com.yougou.kaidian.commodity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.commodity.model.pojo.MerchantCommodityPics;

public interface MerchantCommodityMapper {

	/**
	 * 保存商家商品图片信息
	 * 
	 * @param merchantCommodityPics
	 * @return Integer
	 */
	Integer insertMerchantCommodityPics(MerchantCommodityPics merchantCommodityPics);
	
	/**
	 * 删除商家商品图片信息
	 * 
	 * @param id
	 * @return Integer
	 */
	Integer deleteMerchantCommodityPics(@Param("ids")String... ids);
	
	/**
	 * 删除商家商品所有图片信息
	 * 
	 * @param supplierCode
	 * @param merchantCode
	 * @return Integer
	 */
	Integer deleteMerchantCommodityAllPics(@Param("supplierCode")String supplierCode, @Param("merchantCode")String merchantCode);
	
	/**
	 * 删除商家商品图片信息
	 * 
	 * @param outerPicIds
	 * @return Integer
	 */
	Integer deleteMerchantCommodityPicsByOuterPicId(@Param("outerPicIds")Long... outerPicIds);
	
	/**
	 * 获取商家永久商品图片信息(永久代表不能被删除)
	 * 
	 * @param id
	 * @return MerchantCommodityPics
	 */
	MerchantCommodityPics getPermanentMerchantCommodityPicsById(@Param("id")String id);
	
	/**
	 * 获取商家永久商品图片信息(永久代表不能被删除)
	 * 
	 * @param innerPicName
	 * @param merchantCode
	 * @return MerchantCommodityPics
	 */
	MerchantCommodityPics getPermanentMerchantCommodityPicsByName(@Param("innerPicName")String innerPicName, @Param("merchantCode")String merchantCode);
	
	/**
	 * 获取商家临时商品图片信息(临时代表可以被删除)
	 * 
	 * @param id
	 * @return MerchantCommodityPics
	 */
	MerchantCommodityPics getTemporaryMerchantCommodityPicsById(@Param("id")String id);
	
	/**
	 * 获取商家临时商品图片信息(临时代表可以被删除)
	 * 
	 * @param innerPicName
	 * @param merchantCode
	 * @return MerchantCommodityPics
	 */
	MerchantCommodityPics getTemporaryMerchantCommodityPicsByName(@Param("innerPicName")String innerPicName, @Param("merchantCode")String merchantCode);
	
	/**
	 * 按商家款色编码模糊查询商家商品图片
	 * 
	 * @param supplierCode
	 * @param merchantCode
	 * @return List
	 */
	List<MerchantCommodityPics> queryMerchantCommodityPicsBySupplierCode(@Param("supplierCode")String supplierCode, @Param("merchantCode")String merchantCode);
	
	/**
	 * 获取商家入库类型
	 * 
	 * @param merchantCode
	 * @return Integer
	 */
	Integer getMerchantStorageType(@Param("merchantCode")String merchantCode);
	
	List<MerchantCommodityPics> queryMerchantTemporaryPics(@Param("merchantCode")String merchantCode, @Param("innerPicName")String innerPicName, @Param("start")String start, @Param("end")String end, @Param("orderBy")String orderBy, RowBounds rowBounds);
	
	Integer queryMerchantTemporaryPicscount(@Param("merchantCode")String merchantCode, @Param("innerPicName")String innerPicName, @Param("start")String start, @Param("end")String end);
	
	List<MerchantCommodityPics> getMerchantTemporaryPicByPicName(@Param("merchantCode")String merchantCode, @Param("innerPicName")String innerPicName);
	
	Integer deleteMerchantTemporaryPicByPicName(@Param("merchantCode")String merchantCode, @Param("innerPicName")String innerPicName);
}
