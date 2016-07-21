package com.yougou.kaidian.commodity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.commodity.model.pojo.ImgJmsCountBean;

public interface MctImageJmsMapper {

	public List<ImgJmsCountBean> countImgJms(@Param("create_time_begin")String create_time_begin,@Param("create_time_end")String create_time_end);
}
