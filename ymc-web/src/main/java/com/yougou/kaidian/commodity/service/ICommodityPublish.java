package com.yougou.kaidian.commodity.service;

import javax.servlet.http.HttpServletRequest;

import com.yougou.kaidian.commodity.model.vo.CommoditySubmitResultVo;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitVo;

public interface ICommodityPublish {
	
	CommoditySubmitResultVo insertCommodity(String merchantCode,  CommoditySubmitResultVo resultVo, 
			CommoditySubmitVo submitVo,HttpServletRequest request) throws Exception;

}
