package com.yougou.kaidian.taobao.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.commodity.pojo.Cat;
import com.yougou.kaidian.taobao.model.TaobaoItemCat;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemCatTemplet;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemPropTemplet;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemPropValueTemplet;

public interface TaobaoYougouItemCatTempletMapper {
	public List<TaobaoYougouItemCatTemplet> selectTaobaoYougouItemCatTempletByCats(
			@Param("cats") List<Cat> cats,
			@Param("params") Map<String, Object> params,
			@Param("query") Query query);

	public int selectTaobaoYougouItemCatTempletCountByCats(
			@Param("cats") List<Cat> cats,
			@Param("params") Map<String, Object> params);

	public TaobaoYougouItemCatTemplet selectTaobaoYougouItemCatTempletById(
			@Param("id") String id);

	public List<TaobaoItemCat> selectTaobaoItemCatList(
			@Param("params") Map<String, Object> params);

	public List<TaobaoYougouItemPropTemplet> selectTaobaoYougouItemCatPropTemplet(
			@Param("params") Map<String, Object> params);

	public List<TaobaoYougouItemPropValueTemplet> selectTaobaoYougouItemCatPropValueTempletByTaobaoPid(
			@Param("taobaoPid") String taobaoPid);

	public void copyItemPropTemplet2YougouItemPropByCid(
			@Param("merchant_code") String merchant_code,
			@Param("nick_id") Long nick_id, @Param("operater") String operater,
			@Param("operated") String operated,
			@Param("taobao_cid") Long taobao_cid);
	
	public void copyItemPropValueTemplet2YougouItemPropValueByCid(
			@Param("merchant_code") String merchant_code,
			@Param("nick_id") Long nick_id, @Param("operater") String operater,
			@Param("operated") String operated,
			@Param("taobao_cid") Long taobao_cid);
	
	public int selectTaobaoYougouItemPropValueByCid(
			@Param("merchant_code") String merchant_code,
			@Param("nick_id") Long nick_id, @Param("taobao_cid") Long taobao_cid);
}
