package com.yougou.kaidian.finance.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.belle.finance.biz.dubbo.IMerchantsRedeployPriceBillDubboService;
import com.belle.finance.merchants.redeployprice.model.vo.RedeployPriceVo;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.finance.service.IRedeployPriceService;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.commodityinfo.Commodity;

@Service
public class RedeployPriceServiceImpl implements IRedeployPriceService {
	
	@Resource
	private IMerchantsRedeployPriceBillDubboService merchantsRedeployPriceBillDubboService;
	
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;

	@Override
	public PageFinder<RedeployPriceVo> queryAllRedeployPrice(
			RedeployPriceVo redeploy, Query query) throws Exception {
		Integer count = merchantsRedeployPriceBillDubboService.queryRedeployPriceCount(redeploy);
		PageFinder<RedeployPriceVo> pageFinder = new PageFinder<RedeployPriceVo>(query.getPage(), query.getPageSize(), count);
		if (count != null && count.intValue() > 0) {
			List<RedeployPriceVo> data=merchantsRedeployPriceBillDubboService.queryRedeployPrice(redeploy, query.getOffset(),  query.getPageSize());
			//需要调用商品接口获取商品名称
			for (RedeployPriceVo redeployBack : data) {
				Commodity commodity = commodityBaseApiService.getCommodityByNo(redeployBack.getCommodityNo(), false, false, false);
				redeployBack.setCommodityName(null == commodity ? "" : commodity.getCommodityName());
			}
			pageFinder.setData(data);
		}
		
		return pageFinder;
	}
}
