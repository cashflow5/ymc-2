package com.yougou.kaidian.commodity.service;

import java.util.Date;
import java.util.List;

import com.yougou.kaidian.commodity.model.pojo.MerchantCommodityPics;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.pc.model.picture.MerchantPictureInfo;
import com.yougou.pc.model.prop.PropItem;

public interface IMerchantCommodityService {

	/**
	 * 保存商家商品图片信息
	 * 
	 * @param merchantCommodityPics
	 * @return boolean
	 */
	boolean insertMerchantCommodityPic(MerchantCommodityPics merchantCommodityPics);
	
	/**
	 * 删除商家商品图片信息(删除FTP图片)
	 * 
	 * @param ids
	 * @return boolean
	 * @throws Exception
	 */
	boolean deleteMerchantCommodityPicsWithinImage(String... ids) throws Exception;
	
	/**
	 * 删除商家商品图片信息(不删除FTP图片)
	 * 
	 * @param ids
	 * @return boolean
	 * @throws Exception
	 */
	boolean deleteMerchantCommodityPicsWithoutImage(String... ids) throws Exception;
	
	/**
	 * 删除商家所有商品图片信息(不删除FTP图片)
	 * 
	 * @param supplierCode
	 * @param merchantCode
	 * @return boolean
	 * @throws Exception
	 */
	boolean deleteMerchantCommodityAllPics(String supplierCode, String merchantCode) throws Exception;
	
	/**
	 * 以外部图片ID为条件删除商家商品图片信息(不删除FTP图片)
	 * 
	 * @param outerPicIds
	 * @return boolean
	 * @throws Exception
	 */
	boolean deleteMerchantCommodityPicsByOuterPicId(Long... outerPicIds) throws Exception;
	
	/**
	 * 按ID获取商家商品图片信息(永久)
	 * 
	 * @param id 商家图片ID
	 * @return MerchantCommodityPics
	 */
	MerchantCommodityPics getPermanentMerchantCommodityPics(String id);
	
	/**
	 * 按商家图片名称获取商家商品图片信息(永久)
	 * 
	 * @param innerPicName 商家图片名称
	 * @param merchantCode 商家编码
	 * @return MerchantCommodityPics
	 */
	MerchantCommodityPics getPermanentMerchantCommodityPics(String innerPicName, String merchantCode);
	
	/**
	 * 按ID获取商家商品图片信息(临时)
	 * 
	 * @param id 商家图片ID
	 * @return MerchantCommodityPics
	 */
	MerchantCommodityPics getTemporaryMerchantCommodityPics(String id);
	
	/**
	 * 按商家图片名称获取商家商品图片信息(临时)
	 * 
	 * @param innerPicName
	 * @param merchantCode
	 * @return MerchantCommodityPics
	 */
	MerchantCommodityPics getTemporaryMerchantCommodityPics(String innerPicName, String merchantCode);
	
	/**
	 * 分页查询商家图片信息
	 * 
	 * @param merchantCommodityPics
	 * @param start
	 * @param end
	 * @param query
	 * @return PageFinder
	 */
	PageFinder<MerchantPictureInfo> queryMerchantCommodityPics(MerchantCommodityPics merchantCommodityPics, Date start, Date end, Query query);
	
	/**
	 * 分页查询商家临时空间图片信息
	 * 
	 * @param merchantCommodityPics
	 * @param start
	 * @param end
	 * @param query
	 * @return PageFinder
	 */
	PageFinder<MerchantCommodityPics> queryMerchantTemporaryPics(MerchantCommodityPics merchantCommodityPics, Date start, Date end, String orderBy, Query query);
	
	/**
	 * 查询商家图片信息
	 * 
	 * @param supplierCode
	 * @param merchantCode
	 * @return List
	 * @throws Exception
	 */
	List<MerchantCommodityPics> queryMerchantCommodityPicsBySupplierCode(String supplierCode, String merchantCode) throws Exception;
		
	/**
	 * 获取商家入库类型
	 * 
	 * @param merchantCode
	 * @return Integer
	 * @throws Exception
	 */
	Integer getMerchantStorageType(String merchantCode) throws Exception;
	
	/**
	 * 按商品编号获取商品图片最低数量(单位：张)
	 * 
	 * @param catStructName
	 * @return int
	 * @throws Exception
	 */
	int getCommodityImageLeastNumbersByCommodityNo(String commodityNo) throws Exception;
	
	/**
	 * 获取商品上传图片状态
	 * 
	 * @param commodity
	 * @return
	 * @throws Exception
	 *             0 未上传 1部分上传 2已经上传
	 */
	int getCommodityImageUploadStatus(Commodity commodity) throws Exception;

	/**
	 * 按商品分类名称(一级、二级、三级)获取商品图片最低数量(单位：张)
	 * 
	 * @param catNames
	 * @return int
	 * @throws Exception
	 */
	int getCommodityImageLeastNumbersByCatNames(String[] catNames) throws Exception;
	
	/**
	 * 验证商品图片完整性
	 * 
	 * @param commodityNo
	 * @param supplierCode
	 * @param merchantCode
	 * @return boolean
	 * @throws Exception
	 */
	boolean checkCommodityPicsIntegrality(String commodityNo, String supplierCode, String merchantCode,String brandNo) throws Exception;
	
	/**
	 * 更新商品描述(使用已上传的商品图片)
	 * 
	 * @return boolean
	 */
	boolean updateCommodityDescriptionUseUploadedPics(String commodityNo, String merchantCode) throws Exception;
	
	/**
	 * 按商品编号获取商品分类名称(格式：一级分类名称、二级分类名称、三级分类名称)
	 * 
	 * @param commodityNo
	 * @return String[]
	 */
	String[] getCommodityCatNamesByCommodityNo(String merchantCode, String supplierCode,String brandNo) throws Exception;
	
	/**
	 * 按商品分类结构名称获取商品分类名称(格式：一级分类名称、二级分类名称、三级分类名称)
	 * 
	 * @param catStructName
	 * @return String[]
	 */
	String[] getCommodityCatNamesByCatStructName(String catStructName) throws Exception;

	boolean deleteMerchantCommodityPicsAndCommodityPics(Long outPicId, String innerPicName,String commodityNo, String merchantCode) throws Exception;
	
	/** 统一处理redis中未处理的商品描述字符串 */
	void commodityDescRedisOpt(String merchantCode) throws Exception;

	public int getLatestPicNumbers(String merchantCode, String supplierCode,String brandNo)throws Exception;

	public List<PropItem> getPropMsgByCatIdNew(String catId, boolean showSize);
	
	public String[] getCommodityCatNamesByCatID(String catID) throws Exception;
}
