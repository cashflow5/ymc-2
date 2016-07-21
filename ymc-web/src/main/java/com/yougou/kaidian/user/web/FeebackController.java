package com.yougou.kaidian.user.web;

import java.util.Date;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.user.model.pojo.Feeback;
import com.yougou.kaidian.user.model.vo.FeebackVo;
import com.yougou.kaidian.user.service.FeebackService;

/**
 * 意见反馈
 * @author he.wc
 *
 */
@Controller
@RequestMapping("merchants/feeback")
public class FeebackController {

	@Resource
	private FeebackService feebackService;

	/**
	 * 跳转到登录首页
	 * 
	 * @author wang.m
	 * @param request
	 * @param loginAccount
	 * @param loginPassword
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/list")
	public String to_login(HttpServletRequest request,Query query,ModelMap modelMap) throws Exception {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		
		query.setPageSize(5);
		PageFinder<FeebackVo> pageFinder = feebackService.queryFeeback(merchantCode, query);
		modelMap.addAttribute("pageFinder", pageFinder);
		return "manage/merchants/feeback_list";
	}
	
	/**
	 * 新建意见反馈
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	public String saveFeeback(Feeback feeback, HttpServletRequest request) throws Exception{
		// 获得当前登录的商家
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String merchantName = YmcThreadLocalHolder.getMerchantName(); 
		if(StringUtils.isBlank(merchantCode)){
			throw new Exception("请先登录！商家编号不能为空！");
		}
		if(StringUtils.isBlank(merchantName)){
			throw new Exception("商家名称不能为空！");
		}
		
		feeback.setId(UUID.randomUUID().toString());
		feeback.setMerchantCode(merchantCode);
		feeback.setMerchantName(merchantName);
		feeback.setContent(feeback.getContent().replace("<script>", "").replace("</script>", ""));
		
		feeback.setCreateTime(new Date());
		feeback.setUpdateTime(new Date());
		feebackService.addFeeback(feeback);
		return "redirect:list.sc";
	}

}
