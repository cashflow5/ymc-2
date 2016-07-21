package com.yougou.kaidian.finance.service;

import com.belle.finance.merchants.balancebill.model.vo.BalanceBillVo;
import com.belle.finance.merchants.recruit.model.vo.PromotionSettlementInfor;
import com.belle.finance.merchants.recruit.model.vo.PromotionSettlementVo;
import com.belle.finance.merchants.recruit.model.vo.RecruitDetailVo;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;

public interface IBalanceBillService {

	
	public  PageFinder<BalanceBillVo> queryAll(BalanceBillVo balanceBillVo,
			Query query,String startTime,String endTime)throws Exception;

	
	
	public BalanceBillVo queryBalanceBillById(String id)throws Exception;
	
	public PageFinder<RecruitDetailVo> queryRecruitDetailById(String id,RecruitDetailVo recruitDetail,Query query)throws Exception;
	
	public PageFinder<PromotionSettlementVo> querySettlementDetail (String id,String activeName,Query query) throws Exception;
	
	public PageFinder<PromotionSettlementInfor> queryPromotionInforDetail(PromotionSettlementInfor promotion, Query query) throws Exception;
	
	
}