package com.belle.yitiansystem.taobao.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.belle.yitiansystem.taobao.model.TaobaoBrand;

public interface TaobaoBrandMapper {
public void insertBrand(TaobaoBrand brand);
	
	public int selectBrandCount(@Param("params")Map<String,Object> param);
	
	public List<TaobaoBrand>selectBrandList (@Param("params") Map<String,Object>map,RowBounds rowBounds);
	
	public void updateBrand(TaobaoBrand brand);
	
	public void deleteBrand(@Param("bid") String bid);
	
	public TaobaoBrand selectBrandById(@Param("bid") String bid);
}
