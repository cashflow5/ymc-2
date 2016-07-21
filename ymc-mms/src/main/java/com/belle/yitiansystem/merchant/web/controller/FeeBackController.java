package com.belle.yitiansystem.merchant.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.spring.DataSourceSwitcher;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.yitiansystem.merchant.constant.PunishConstant;
import com.belle.yitiansystem.merchant.model.pojo.Feeback;
import com.belle.yitiansystem.merchant.model.pojo.FeebackReply;
import com.belle.yitiansystem.merchant.model.vo.FeebackVo;
import com.belle.yitiansystem.merchant.service.FeebackService;

/**
 * 意见反馈
 * 
 * @author he.wc
 * 
 */
@Controller
@RequestMapping("/yitiansystem/merchants/businessorder")
public class FeeBackController {

	private final String BASE_PATH = "yitiansystem/merchants/feeback/";


	@Resource
	private FeebackService feebackService;

	// 数据绑定
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(PunishConstant.DATE_FORMAT_DATETIME);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateTimeFormat, true));
	}

	/**
	 * 跳转意见反馈列表
	 * 
	 * @param modelMap
	 * @param supplierId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("to_feebackList")
	public String to_punishList(ModelMap modelMap, FeebackVo feebackVo, Query query) throws Exception {
		PageFinder<Feeback> pageFinder = feebackService.queryFeebackList(feebackVo, query);
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("feebackVo", feebackVo);
		return BASE_PATH + "merchants_feeback_list";
	}

	/**
	 * 跳转到意见反馈回复页面
	 * 
	 * @param modelMap
	 * @param feebackVo
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("to_feebackReply")
	public String to_feebackReply(ModelMap modelMap, @RequestParam(value = "id", required = true) String id, Query query)
			throws Exception {
		feebackService.updateFeedbackIsRead(id);
		Feeback feeback = feebackService.getFeebackById(id);
		List<FeebackReply> replyList = feebackService.getFeebackReplyList(id);
		modelMap.addAttribute("feeback", feeback);
		modelMap.addAttribute("replyList", replyList);
		return BASE_PATH + "merchants_feeback_reply_input";
	}

	/**
	 * 新建回复信息
	 * 
	 * @param feebackId
	 * @param replyContent
	 * @return
	 */
	@RequestMapping("save_feebackReply")
	public String savefeebackReply(String feebackId, String replyContent, HttpServletRequest request,ModelMap modelMap) {

		String operator = GetSessionUtil.getSystemUser(request).getUsername();
		FeebackReply feebackReply = new FeebackReply();
		feebackReply.setFeebackId(feebackId);
		feebackReply.setReplyContent(replyContent);
		feebackReply.setReplyPerson(operator);
		feebackReply.setIsDeleted("0");
		feebackReply.setCreateTime(new Date());
		feebackReply.setUpdateTime(new Date());
		DataSourceSwitcher.setMaster();
		feebackService.saveFeebackReply(feebackReply);
		modelMap.addAttribute("message", "提交成功!");
		modelMap.addAttribute("refreshflag", "1");
		modelMap.addAttribute("methodName","/yitiansystem/merchants/businessorder/to_feebackList.sc");
		return "yitiansystem/merchants/merchants_message";
	}
	
	/**
	 * 删除优购回复
	 * @param id
	 * @return
	 */
	@RequestMapping("del_feebackReply")
	@ResponseBody
	public String delfeebackReply(String id) {
		DataSourceSwitcher.setMaster();
		feebackService.delFeebackReply(id);
		return "true";
	}
	
	
	/**
	 * 意见反馈excel导出
	 * @param request
	 * @param modelMap
	 * @param feebackVo
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("export_feeback")
	public ModelAndView exportFeeback(HttpServletRequest request,ModelMap modelMap,FeebackVo feebackVo,Query query) throws Exception{
		query.setPageSize(1000);
		PageFinder<Feeback> pageFinder = feebackService.queryFeebackList(feebackVo, query);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("feebackList", pageFinder.getData());
		return new ModelAndView(new FeebackExcelView(), model);
	}

}
