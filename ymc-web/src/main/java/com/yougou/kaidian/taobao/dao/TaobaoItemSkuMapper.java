package com.yougou.kaidian.taobao.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.taobao.model.TaobaoItemSku;

public interface TaobaoItemSkuMapper {
	
	public int deleteTaobaoItemSkuByNumIid(@Param("numIid")Long numIid);

	public int insertTaobaoItemSkuList(List<TaobaoItemSku> lstTaobaoItemSku);
	
	public List<TaobaoItemSku> queryTaobaoItemSkuByNumIid(@Param("numIid")Long numIid);
	
	/**
	 * 删除SKUID
	 * @param skuId
	 * @return
	 */
	public int deleteTaobaoItemSkuBySkuid(@Param("skuId") Long skuId);

}