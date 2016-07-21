package com.yougou.kaidian.taobao.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.taobao.model.TaobaoItemExtend;
import com.yougou.kaidian.taobao.model.TaobaoItemExtendDto;


public interface TaobaoItemExtendMapper {
	public List<TaobaoItemExtendDto> selectTaobaoItemExtend(
			@Param("params") Map<String, Object> params, RowBounds rowBounds);

	public int selectTaobaoItemExtendCount(
			@Param("params") Map<String, Object> params);

	public int updateByPrimaryKeySelective(TaobaoItemExtend taobaoItemExtend);

	public TaobaoItemExtend getTaobaoItemExtendByExtendId(
			@Param("extendId") String extendId);
	 
	 
	 public void insertTaobaoItemExtend(TaobaoItemExtend taobaoItemExtend);
	 
	 /**
	  * 更具扩展ID删除
	  * @param extendId
	  * @param merchantCode
	  */
	 public void deleteByExtendId(@Param("extendId")String extendId,@Param("merchantCode")String merchantCode);


	 public int selectCountByNumIid(@Param("numIid")long numIid);
	 
	 public List<TaobaoItemExtend> selectByNumIid(@Param("numIid")long numIid);
	 
	 public List<TaobaoItemExtendDto> getTaobaoItemExtendByIds(@Param("merchantCode") String merchantCode,@Param("list") List<String> ids);
	 
	 public String getTaobaoItemExtendIdByCommodtyNo(@Param("merchantCode") String merchantCode,@Param("commodityNo") String commodityNo);
}
