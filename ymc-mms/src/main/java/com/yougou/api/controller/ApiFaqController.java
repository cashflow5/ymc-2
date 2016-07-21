package com.yougou.api.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.yougou.api.model.pojo.ApiFaq;
import com.yougou.api.service.IApiCategoryService;
import com.yougou.api.service.IApiFaqService;

/**
 * API常见问题控制器
 * 
 * @author yang.mq
 *
 */
@Controller
@RequestMapping("/openapimgt/api/faq")
public class ApiFaqController {

	@Resource
	private IApiFaqService apiFaqService;
	
	@Resource
	private IApiCategoryService apiCategoryService;
	
	@RequestMapping("/prelist")
	public ModelAndView preListApiFaq() throws Exception {
		return new ModelAndView("yitiansystem/merchant/apimgt/list_api_faq");
	}
	
	@RequestMapping("/list")
	public ModelAndView listApiFaq(ApiFaq apiFaq, Date fromCreated, Date toCreated, Query query) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DetachedCriteria criteria = DetachedCriteria.forClass(ApiFaq.class);
		criteria.addOrder(Order.desc("created"));
		if (StringUtils.isNotBlank(apiFaq.getSubject())) {
			criteria.add(Restrictions.like("subject", apiFaq.getSubject(), MatchMode.START));
		}
		if (StringUtils.isNotBlank(apiFaq.getCreator())) {
			criteria.add(Restrictions.eq("creator", apiFaq.getCreator()));
		}
		if (fromCreated != null) {
			criteria.add(Restrictions.ge("created", fromCreated));
		}
		if (toCreated != null) {
			criteria.add(Restrictions.le("created", toCreated));
		}
		PageFinder<ApiFaq> pageFinder = apiFaqService.queryApiFaq(criteria, query);
		resultMap.put("apiFaq", apiFaq);
		resultMap.put("fromCreated", fromCreated);
		resultMap.put("toCreated", toCreated);
		resultMap.put("pageFinder", pageFinder);
		return new ModelAndView("yitiansystem/merchant/apimgt/list_api_faq", resultMap);
	}
	
	@RequestMapping("/saveorupdate")
	public void saveOrUpdateApiFaq(ApiFaq apiFaq, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SystemmgtUser systemmgtUser = (SystemmgtUser) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
			if (StringUtils.isBlank(apiFaq.getId())) {
				apiFaq.setId(null);
				apiFaq.setCreator(systemmgtUser.getLoginName());
				apiFaq.setCreated(new Date());
				apiFaqService.saveApiFaq(apiFaq);
			} else {
				apiFaq.setModifier(systemmgtUser.getLoginName());
				apiFaq.setModified(new Date());
				apiFaqService.updateApiFaq(apiFaq);
			}
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/view")
	public ModelAndView viewApiFaq(ApiFaq apiFaq) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(apiFaq.getId())) {
			apiFaq = apiFaqService.getApiFaq(apiFaq.getId());
		}
		resultMap.put("apiFaq", apiFaq);
		resultMap.put("apiCategorys", apiCategoryService.queryAllApiCategory());
		return new ModelAndView("yitiansystem/merchant/apimgt/view_api_faq", resultMap);
	}
	
	@RequestMapping("/delete")
	public void deleteApiFaq(String id, HttpServletResponse response) throws Exception {
		try {
			apiFaqService.deleteApiFaq(id);
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
}
