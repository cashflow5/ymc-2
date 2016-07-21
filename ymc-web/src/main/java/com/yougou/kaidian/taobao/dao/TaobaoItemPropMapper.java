package com.yougou.kaidian.taobao.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.taobao.model.TaobaoItemCatProp;
import com.yougou.kaidian.taobao.model.TaobaoItemProp;
import com.yougou.kaidian.taobao.model.TaobaoItemPropValVO;

public interface TaobaoItemPropMapper {
	
	public void insertTaobaoItemPropList(List<TaobaoItemProp> lstTaobaoItemProp);
	
	public TaobaoItemProp getTaobaoItemProp(@Param("pid")Long pid);
	
	public int getTaobaoItemCatPropCount(TaobaoItemCatProp taobaoItemCatProp);
	
	public void insertTaobaoItemCatPropList(List<TaobaoItemCatProp> lstTaobaoItemCatProp);
	
	public List<TaobaoItemPropValVO> getTaobaoYougouItemPropNoByCid(@Param("merchantCode")String merchantCode,@Param("nickId")Long nickId, @Param("cid")Long cid);
	
	public String getTaobaoPidYougouSizeMapperingByCid(@Param("merchantCode")String merchantCode, @Param("cid")Long cid);
}
