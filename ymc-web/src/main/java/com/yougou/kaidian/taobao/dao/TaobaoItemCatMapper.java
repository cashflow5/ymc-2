package com.yougou.kaidian.taobao.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.taobao.model.TaobaoItemCat;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemCat;

public interface TaobaoItemCatMapper {

	/**
	 * 新增淘宝类目
	 * @param taobaoItemcat
	 */
	public void insertTaobaoItemCat(TaobaoItemCat taobaoItemcat);
	
	/**
	 * 批量新增淘宝类目
	 * @param lstTaobaoItemcat
	 */
	public void insertTaobaoItemCatList(List<TaobaoItemCat> lstTaobaoItemcat);
	
	/**
	 * 根据淘宝cid获取淘宝类目信息
	 * @param cid
	 * @return
	 */
	public TaobaoItemCat getTaobaoItemCatByCid(@Param("cid")Long cid);
	
	/**
	 * 插入淘宝优购中间类目绑定数据
	 * @param taobaoYougouItemcat
	 */
	public void insertTaobaoYougouItemCat(TaobaoYougouItemCat taobaoYougouItemcat);

	/**
	 * 批量插入淘宝优购中间类目绑定数据
	 * @param taobaoYougouItemcat
	 */
	public void insertTaobaoYougouItemCatList(List<TaobaoYougouItemCat> lstTaobaoYougouItemcat);
	
	/**
	 * 根据商家编码、淘宝主账号nick_id和cid获取淘宝优购类目绑定数据
	 * @param merchantCode
	 * @param nickId
	 * @param cid
	 * @return
	 */
	public TaobaoYougouItemCat getTaobaoYougouItemCatByCid(@Param("merchantCode")String merchantCode, @Param("nickId") Long nickId, @Param("cid")Long cid);
	
	public TaobaoYougouItemCat getTaobaoYougouItemCatByCidTemplet(@Param("cid")Long cid);

	/**
	 * 根据id获取淘宝优购类目绑定数据
	 * @param id
	 * @return
	 */
	public TaobaoYougouItemCat getTaobaoYougouItemCatById(@Param("id") String id);

	/**
	 * 查询淘宝所有分类
	 * 
	 * @return
	 */
	public List<TaobaoItemCat> selectAllTaobaoItemCat();

	/**
	 * 查询淘宝优购分类数量
	 * 
	 * @param prams
	 * @return
	 */
	public int selectTaobaoYougouItemCatCount(
			@Param("params") Map<String, Object> prams);

	public List<TaobaoYougouItemCat> selectTaobaoYougouItemCatList(
			@Param("params") Map<String, Object> prams, RowBounds rowBounds);

	/**
	 * 查询所有淘宝优购分类
	 * 
	 * @param prams
	 * @return
	 */
	public List<TaobaoYougouItemCat> selectAllTaobaoYougouItemCatList(
			@Param("params") Map<String, Object> prams);

	/**
	 * 查询供应商对应的所有优购分类
	 * 
	 * @param merchantCode
	 * @return
	 */
	public List<String> selectAllYougouCatNoByMerchantCode(
			@Param("merchantCode") String merchantCode);

	/**
	 * 绑定分类属性，属性值
	 * 
	 * @param itemCat
	 */
	public void updateTaobaoYougouItemCat(TaobaoYougouItemCat itemCat);

	
	public void updateTaobaoYougouItemCatByCid(TaobaoYougouItemCat itemCat);
}
