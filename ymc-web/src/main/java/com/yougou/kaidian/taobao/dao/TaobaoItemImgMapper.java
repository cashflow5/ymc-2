package com.yougou.kaidian.taobao.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.taobao.model.TaobaoItemImg;

public interface TaobaoItemImgMapper {
	
	public int deleteTaobaoItemImgByNumIid(@Param("numIid") Long numIid);

	public int deleteTaobaoItemImgByExtendId(
			@Param("extendId") String extendId);

	public int insertTaobaoItemImgList(List<TaobaoItemImg> lstTaobaoItemImg);
	
	public List<TaobaoItemImg> queryTaobaoItemImgByNumIid(@Param("numIid")Long numIid);

	public List<TaobaoItemImg> queryTaobaoItemImgByExtendId(
			@Param("extendId") String extendId);

}