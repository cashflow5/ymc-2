package com.yougou.kaidian.finance.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.belle.finance.merchants.redeployprice.model.vo.RedeployPriceVo;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.finance.service.IRedeployPriceService;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;

@Controller
@RequestMapping("finance/redeployprice")
public class RedeployPriceController {
	
	@Resource
	private IRedeployPriceService redeployPriceService;
	
	@RequestMapping("/queryAll")
	public ModelAndView queryAll(RedeployPriceVo redeployPrice,ModelMap map,HttpServletRequest request,Query query)throws Exception{
		query = query!= null ? query : new Query();
		
		redeployPrice.setSupplierCode(YmcThreadLocalHolder.getMerchantCode());
		PageFinder<RedeployPriceVo> pfRedeployPriceList = redeployPriceService.queryAllRedeployPrice(redeployPrice, query);
		map.addAttribute("pageFinder", pfRedeployPriceList);
		map.addAttribute("redeployPriceList", pfRedeployPriceList.getData());
		map.addAttribute("redeployPrice", redeployPrice);
		// 标识进入首页
		map.addAttribute("tagTab", "balance");
		return new ModelAndView("manage/finance/redeployprice",map);
	}
	
}
