package com.yougou.kaidian.taobao.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.taobao.model.TaobaoItemCatPropValue;
import com.yougou.kaidian.taobao.model.TaobaoItemCatPropValueDto;
import com.yougou.kaidian.taobao.model.TaobaoItemPropValVO;
import com.yougou.kaidian.taobao.model.TaobaoItemPropValue;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemPropValue;


public interface TaobaoItemPropValueMapper {
	
	public TaobaoItemPropValue getTaobaoItemPropValueByVid(@Param("vid") Long vid);
	
	public void insertTaobaoItemPropValueList(List<TaobaoItemPropValue> lstTaobaoItemPropValue);
	
	public TaobaoItemCatPropValue getTaobaoItemCatPropValueByVid(@Param("cid") Long cid, @Param("pid") Long pid, @Param("vid") Long vid);
	
	public void insertTaobaoItemCatPropValueList(List<TaobaoItemCatPropValue> lstTaobaoItemCatPropValue);
	
	/**
	 * 查询淘宝分类属性值
	 * 
	 * @param pid
	 * @param merchantCode TODO
	 * @param nickId TODO
	 * @return
	 */
	public List<TaobaoItemCatPropValueDto> selectTaobaoItemProValWidthYouItemProVal(
			@Param("pid") Long pid, @Param("cid") Long cid,
			@Param("merchantCode") String merchantCode,
			@Param("catBindId") String catBindId);

	/**
	 * 批量插入属性值
	 * 
	 * @param list
	 */
	public void insertTaobaoYougouItemPropValueBatch(
			List<TaobaoYougouItemPropValue> list);
	
	public List<TaobaoItemPropValVO> getTaobaoYougouItemPropValByCid(@Param("merchantCode")String merchantCode,@Param("nickId")Long nickId, @Param("cid")Long cid);

	/**
	 * 查询商家是否根据昵称、商家编码、淘宝cid、pid有对应的尺码绑定列表
	 * @param merchantCode
	 * @param nickId
	 * @param cid
	 * @param pid
	 * @return
	 */
	public List<TaobaoItemPropValVO> getTaobaoYougouItemPropValByCidPid(@Param("merchantCode")String merchantCode, @Param("nickId")Long nickId, @Param("cid")Long cid, @Param("pid")Long pid);
	
	public List<TaobaoItemPropValVO> getTaobaoYougouItemPropValByCidPidTemplet(@Param("cid")Long cid, @Param("pid")Long pid);
	
	/**
	 * 查询商家是否根据昵称、商家编码、淘宝cid、pid、vid有对应的尺码绑定
	 * @param merchantCode
	 * @param nickId
	 * @param cid
	 * @param pid
	 * @param vid
	 * @return
	 */
	public TaobaoItemPropValVO getTaobaoYougouItemPropValByCidPidVid(@Param("merchantCode")String merchantCode, @Param("nickId")Long nickId, @Param("cid")Long cid, @Param("pid")Long pid, @Param("vid")Long vid);
	/**
	 * 根据CID查询淘宝颜色分类属性值
	 * 
	 * @param cid
	 * @return
	 */
	public List<TaobaoItemCatPropValueDto> selectTaobaoItemProVal4Color(
			@Param("cid") Long cid);
}
