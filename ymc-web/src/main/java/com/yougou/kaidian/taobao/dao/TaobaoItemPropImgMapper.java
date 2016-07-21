package com.yougou.kaidian.taobao.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.taobao.model.TaobaoItemPropImg;

public interface TaobaoItemPropImgMapper {

    public int deleteTaobaoItemPropImgByNumIid(@Param("numIid")Long numIid);

    public int insertTaobaoItemPropImgList(List<TaobaoItemPropImg> lstTaobaoItemPropImg);
    
    public List<TaobaoItemPropImg> queryTaobaoItemPropImgByNumIid(@Param("numIid")Long numIid);

}