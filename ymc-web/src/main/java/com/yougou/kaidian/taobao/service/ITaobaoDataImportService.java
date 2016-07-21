package com.yougou.kaidian.taobao.service;

import java.util.Map;

import com.yougou.kaidian.taobao.exception.BusinessException;
import com.yougou.kaidian.taobao.vo.TaobaoCsvItemVO;
import com.yougou.kaidian.taobao.vo.TaobaoImportVo;


public interface ITaobaoDataImportService {

	/**
	 * 抓取淘宝基本数据（品牌、分类）导入优购mms_db
	 * @param appKey
	 * @param appSecret
	 * @param sessionKey
	 * @param merchantCode
	 * @param operater
	 * @param nickId
	 * @return
	 * @throws IllegalAccessException
	 */
	boolean importTaobaoBasicDataToYougou(String appKey, String appSecret, String sessionKey, String merchantCode, String operater, Long nickId) throws IllegalAccessException;

	/**
	 * 按照分类抓取淘宝商品数据导入优购mms_db
	 * @param appKey
	 * @param appSecret
	 * @param sessionKey
	 * @param merchantCode
	 * @param operater
	 * @param nickId
	 * @param cid
	 * @return
	 * @throws IllegalAccessException
	 */
	Map<String,Integer> importTaobaoOnSalaItemByCidToYougou(String appKey, String appSecret, String sessionKey, String merchantCode, String operater, Long nickId, Long cid) throws IllegalAccessException;

	/**
	 * 抓取淘宝全部商品数据导入优购mms_db
	 * @param appKey
	 * @param appSecret
	 * @param sessionKey
	 * @param merchantCode
	 * @param operater
	 * @param nickId
	 * @return
	 * @throws IllegalAccessException
	 */
	Map<String, Integer> importTaobaoAllOnSalaItemToYougou(String appKey, String appSecret, String sessionKey, String merchantCode, String operater, Long nickId) throws IllegalAccessException;
	
	/**
	 * 按照淘宝一级类目ID导入类目数据
	 * @param sessionKey
	 * @param merchantCode
	 * @param cid
	 * @return
	 * @throws IllegalAccessException
	 */
	String importTaobaoItemcatToYougouByParentCids(String sessionKey, String merchantCode, String operater, String cids) throws IllegalAccessException;
	
	/**
	 * csv导入商品数据
	 * @param items
	 * @param merchantCode
	 * @return
	 * @throws IllegalAccessException
	 */
	void importTaobaoItemData4CSV(String merchantCode,String operter,TaobaoImportVo importVo) throws IllegalAccessException;

	/**
	 * 根据模板初始化数据
	 * @param extendIdAndNumiidstr
	 * @param merchantCode
	 * @return
	 * @throws BusinessException
	 */
	Map<String, String> initDataFromTemplate(String extendIdAndNumiidstr,String merchantCode)throws BusinessException;
	
	/**
	 * 多线程处理
	 * @param tbitem
	 * @param errorList
	 * @param errorIndexList
	 * @return
	 * @throws IllegalAccessException
	 */
	void importTaobaoItemData4CSVForSimple( TaobaoCsvItemVO tbitem, 
			String merchantCode,TaobaoImportVo importVo)  ;
	
	void checkBrandAndCat(TaobaoImportVo importVo,String merchantCode);
}
