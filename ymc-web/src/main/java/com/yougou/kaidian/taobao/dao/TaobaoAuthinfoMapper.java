package com.yougou.kaidian.taobao.dao;

import java.util.List;

import com.yougou.kaidian.taobao.model.TaobaoAuthinfo;
import com.yougou.kaidian.taobao.model.TaobaoAuthinfoDto;

public interface TaobaoAuthinfoMapper {
	TaobaoAuthinfo selectByTopVisitorID(String topVisitorId);

    int insertTaobaoAuthinfo(TaobaoAuthinfo record);

    int updateByPrimaryKeySelective(TaobaoAuthinfo record);
    
    String selectTopSecretByKey(String appkey);

	public List<TaobaoAuthinfoDto> selectAuthInfoByCatBandId(String catBandId);
}