package com.yougou.kaidian.finance.web;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.belle.finance.merchants.balancebill.model.vo.BalanceBillVo;
import com.belle.finance.merchants.recruit.model.vo.PromotionSettlementInfor;
import com.belle.finance.merchants.recruit.model.vo.PromotionSettlementVo;
import com.belle.finance.merchants.recruit.model.vo.RecruitDetailVo;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.finance.service.IBalanceBillService;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.pc.api.ICommodityBaseApiService;

@Controller
@RequestMapping("finance/balancebill")
public class BalanceBillController {
	
	@Resource
	private IBalanceBillService balanceBillService;
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	
	@RequestMapping("/queryAll")
	public ModelAndView queryAll(BalanceBillVo balanceBill,ModelMap map,HttpServletRequest request,Query query,String startTime,String endTime)throws Exception{
		query = query!= null ? query : new Query();
		
		balanceBill.setSupplierCode(YmcThreadLocalHolder.getMerchantCode());
		PageFinder<BalanceBillVo> pfBalanceList = balanceBillService.queryAll(balanceBill, query,startTime,endTime);
		map.addAttribute("pageFinder", pfBalanceList);
		map.addAttribute("balanceBillList", pfBalanceList.getData());
		map.addAttribute("balanceBill", balanceBill);
		// 标识进入首页
		map.addAttribute("tagTab", "balance");
		map.addAttribute("startTime", startTime);
		map.addAttribute("endTime",endTime);
		return new ModelAndView("manage/finance/balance",map);
	}
	/**
	 * 结算单明细
	 * @param id
	 * @param recruitDetail
	 * @param map
	 * @param query
	 * @return
	 */
	@RequestMapping("/detail")
	public ModelAndView detail(String id,RecruitDetailVo recruitDetail,ModelMap map,Query query)throws Exception{
		
		BalanceBillVo balanceBill = null;
		PageFinder<RecruitDetailVo> pf = null;
		List<RecruitDetailVo>  recruitDetailList = new ArrayList<RecruitDetailVo>();
		if(StringUtils.isNotBlank(id)){
			
			balanceBill =  balanceBillService.queryBalanceBillById(id);
			pf = balanceBillService.queryRecruitDetailById(id, recruitDetail, query);
			recruitDetailList = pf.getData();
		}else{
			
			throw new Exception("ID为null");
		}
		if(recruitDetailList!=null){
			for(RecruitDetailVo vo:recruitDetailList){
				vo.setInsideCode(commodityBaseApiService.getProductByNo(vo.getProNo(),false).getInsideCode());
			}
		}
		map.addAttribute("id", id);
		map.addAttribute("balanceBill", balanceBill);
		map.addAttribute("recruitDetail", recruitDetail);
		map.addAttribute("recruitDetailList", recruitDetailList);
		map.addAttribute("pageFinder", pf);
		// 标识进入首页
		map.addAttribute("tagTab", "balance");
		return new ModelAndView("manage/finance/balance_detail",map);
	}
	/**
	 * 促销结算单明细
	 * @param id
	 * @param activeName
	 * @param map
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cx_detail")
	public ModelAndView cx_detail(String id,String activeName,ModelMap map,Query query) throws Exception{
		BalanceBillVo balanceBill = new BalanceBillVo();
		PageFinder<PromotionSettlementVo> pf = null;
		List<PromotionSettlementVo> psementList = new ArrayList<PromotionSettlementVo>();
		if(StringUtils.isNotBlank(id)){
			balanceBill = balanceBillService.queryBalanceBillById(id);
			
			pf = balanceBillService.querySettlementDetail(id, activeName, query);
			psementList = pf.getData();
		}else{
			throw new Exception("ID为null");
		}
		map.addAttribute("id", id);
		map.addAttribute("pageFinder", pf);
		map.addAttribute("balanceBill", balanceBill);
		map.addAttribute("psementList", psementList);
		map.addAttribute("activeName", activeName);
		// 标识进入首页
		map.addAttribute("tagTab", "balance");
		return new ModelAndView("manage/finance/cx_detail",map);
	}
	
	
	/**
	 * 出库促销商品明细
	 * @param activeName
	 * @param productNo
	 * @param productName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/q_detail")
	public ModelAndView q_detail (String activeName,String supplierCode,String supplierName,String proNo,String proName,ModelMap map,Query query) throws Exception{
		
		activeName = URLDecoder.decode(activeName, "UTF-8");
		supplierName = URLDecoder.decode(supplierName, "UTF-8");
		PageFinder<PromotionSettlementInfor> pf = null;
		List<PromotionSettlementInfor> psementInfoList = new ArrayList<PromotionSettlementInfor>();
		try {
			 PromotionSettlementInfor promotion = new PromotionSettlementInfor();
			 promotion.setActiveName(activeName);
			 promotion.setSupplierCode(supplierCode);
			 promotion.setProductNo(proNo);
			 promotion.setProductName(proName);
			 pf = balanceBillService.queryPromotionInforDetail(promotion, query);
			 psementInfoList = pf.getData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.addAttribute("proInforList", psementInfoList);
		map.addAttribute("pageFinder", pf);
		map.addAttribute("activeName", activeName);
		map.addAttribute("supplierCode", supplierCode);
		map.addAttribute("supplierName", supplierName);
		map.addAttribute("proNo", proNo);
		map.addAttribute("proName", proName);
		// 标识进入首页
		map.addAttribute("tagTab", "balance");
		return new ModelAndView("manage/finance/detail_infor",map);
	}
}
