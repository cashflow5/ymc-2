package com.yougou.kaidian.finance.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.belle.finance.biz.dubbo.IMerchantsRedeployPriceBillDubboService;
import com.belle.finance.merchants.balancebill.model.vo.BalanceBillVo;
import com.belle.finance.merchants.recruit.model.vo.PromotionSettlementInfor;
import com.belle.finance.merchants.recruit.model.vo.PromotionSettlementVo;
import com.belle.finance.merchants.recruit.model.vo.RecruitDetailVo;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.finance.service.IBalanceBillService;

@Service
public class BalanceBillServiceImpl implements IBalanceBillService {
	
	@Resource
	private IMerchantsRedeployPriceBillDubboService merchantsRedeployPriceBillDubboService;
	
	@Override
	public PageFinder<BalanceBillVo> queryAll(BalanceBillVo balanceBillVo,
			Query query, String startTime, String endTime) throws Exception {
		Integer count=merchantsRedeployPriceBillDubboService.queryBalanceBillCount(balanceBillVo);
		PageFinder<BalanceBillVo> pageFinder = new PageFinder<BalanceBillVo>(query.getPage(), query.getPageSize(), count);

		if (count != null && count.intValue() > 0) {
			List<BalanceBillVo> balanceBillList=merchantsRedeployPriceBillDubboService.queryBalanceBill(balanceBillVo, query.getOffset(), query.getPageSize());
			pageFinder.setData(balanceBillList);
		}
		
		return pageFinder;
	}
	
	@Override
	public BalanceBillVo queryBalanceBillById(String id) throws Exception {
		return merchantsRedeployPriceBillDubboService.queryBalanceBillById(id);
	}
	
	@Override
	public PageFinder<RecruitDetailVo> queryRecruitDetailById(String id,
			RecruitDetailVo recruitDetail, Query query) throws Exception {
		recruitDetail.setBalanceOddId(id);
		Integer count = merchantsRedeployPriceBillDubboService.queryRecruitDetailByIdCount(recruitDetail);
		PageFinder<RecruitDetailVo> pageFinder = new PageFinder<RecruitDetailVo>(query.getPage(), query.getPageSize(), count);
		if (count != null && count.intValue() > 0) {
			List<RecruitDetailVo> data=merchantsRedeployPriceBillDubboService.queryRecruitDetailById(recruitDetail, query.getOffset(),  query.getPageSize());
			pageFinder.setData(data);
		}
		return pageFinder;
	}
	
	@Override
	public PageFinder<PromotionSettlementVo> querySettlementDetail(String id,
			String activeName, Query query) throws Exception {
		Integer count = merchantsRedeployPriceBillDubboService.querySettlementDetailCount(id, activeName);
		PageFinder<PromotionSettlementVo> pageFinder = new PageFinder<PromotionSettlementVo>(query.getPage(), query.getPageSize(), count);
		if (count != null && count.intValue() > 0) {
			List<PromotionSettlementVo> data = merchantsRedeployPriceBillDubboService.querySettlementDetail(id, activeName, query.getOffset(), query.getPageSize());
			pageFinder.setData(data);
		}
		return pageFinder;
	}
	
	
	@Override
	public PageFinder<PromotionSettlementInfor> queryPromotionInforDetail(PromotionSettlementInfor promotion, Query query) throws Exception {
		Integer count = merchantsRedeployPriceBillDubboService.queryPromotionInforDetailCount(promotion);
		PageFinder<PromotionSettlementInfor> pageFinder = new PageFinder<PromotionSettlementInfor>(query.getPage(), query.getPageSize(), count);
		if (count != null && count.intValue() > 0) {
			List<PromotionSettlementInfor> data = merchantsRedeployPriceBillDubboService.queryPromotionInforDetail(promotion, query.getOffset(), query.getPageSize());
			pageFinder.setData(data);
		}
		return pageFinder;
	}	
} 
