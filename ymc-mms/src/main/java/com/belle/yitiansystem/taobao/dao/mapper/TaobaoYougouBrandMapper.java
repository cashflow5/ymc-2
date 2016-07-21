package com.belle.yitiansystem.taobao.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.belle.yitiansystem.taobao.model.TaobaoBrand;
import com.belle.yitiansystem.taobao.model.TaobaoYougouBrand;

public interface TaobaoYougouBrandMapper {
	public List<TaobaoBrand>selectTaobaoBrandList(@Param("taobaoBrandName") String taobaoBrandName);
	
	public List<TaobaoYougouBrand> selectTaoaoYougouBrandList(@Param("params") Map<String,Object>map,RowBounds rowBounds);
	
	public int selectTaoaoYougouBrandCount(@Param("params") Map<String,Object>map);
	
	public List<TaobaoYougouBrand> selectTaoaoYougouBrandList(@Param("params") Map<String,Object>map);
	
	public void insertTaobaoYougouBrandBatch(@Param("list") List<TaobaoYougouBrand> list);
	
	public void deleteBatch(@Param("list") List<String> list);
	
	
	public TaobaoYougouBrand selectTYBById(@Param("id") String id);
}
