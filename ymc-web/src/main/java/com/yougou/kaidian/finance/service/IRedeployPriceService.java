package com.yougou.kaidian.finance.service;

import com.belle.finance.merchants.redeployprice.model.vo.RedeployPriceVo;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;

public interface IRedeployPriceService {

	/**
	 * 分页查询该商家所有调价单
	 * @param redeployPriceVo
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public  PageFinder<RedeployPriceVo> queryAllRedeployPrice(
			RedeployPriceVo redeploy, Query query) throws Exception;

}