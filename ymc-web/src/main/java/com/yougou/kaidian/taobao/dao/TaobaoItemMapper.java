package com.yougou.kaidian.taobao.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.taobao.model.TaobaoItem;

public interface TaobaoItemMapper {
	public int deleteByPrimaryKey(@Param("numIid") Long numIid,@Param("merchantCode") String merchantCode);

    public int insertTaobaoItem(TaobaoItem taobaoItem);

    public TaobaoItem getTaobaoItemByNumIid(@Param("numIid") Long numIid);
    
    public TaobaoItem getTaobaoItemByExtendId(@Param("extendId") String extendId);
    
    public List<TaobaoItem> getTaobaoItem(@Param("params") Map<String,Object> params,RowBounds rowBounds);
    
    public int getTaobaoItemCount(@Param("params") Map<String,Object> params);

    public int updateByPrimaryKeySelective(TaobaoItem taobaoItem);
    
    public List<TaobaoItem> selectByIds(@Param("list") Set<String> ids,@Param("merchantCode") String merchantCode);
}