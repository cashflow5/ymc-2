package com.yougou.api.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.yougou.api.beans.AppType;
import com.yougou.api.model.pojo.ApiCategory;
import com.yougou.api.service.IApiCategoryService;

/**
 * API分类控制器
 * 
 * @author yang.mq
 *
 */
@Controller
@RequestMapping("/openapimgt/category")
public class ApiCategoryController {
	
	@Resource
	private IApiCategoryService apiCategoryService;
	
	@RequestMapping("/saveorupdate")
	public void saveOrUpdateApiCategory(ApiCategory apiCategory, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SystemmgtUser systemmgtUser = (SystemmgtUser) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
			if (StringUtils.isBlank(apiCategory.getId())) {
				apiCategory.setId(null);
				apiCategory.setCreator(systemmgtUser.getLoginName());
				apiCategory.setCreated(new Date());
				apiCategoryService.saveApiCategory(apiCategory);
			} else {
				apiCategory.setModifier(systemmgtUser.getLoginName());
				apiCategory.setModified(new Date());
				apiCategoryService.updateApiCategory(apiCategory);
			}
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/delete")
	public void deleteApiCategory(String id, HttpServletResponse response) throws Exception {
		try {
			apiCategoryService.deleteApiCategory(id);
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}

	@RequestMapping("/view")
	public ModelAndView viewApiCategory(String id) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("appTypes", AppType.values());
		if (StringUtils.isNotBlank(id)) {
			ApiCategory apiCategory = apiCategoryService.getApiCategoryById(id);
			resultMap.put("apiCategory", apiCategory);
		}
		return new ModelAndView("/yitiansystem/merchant/apimgt/view_category", resultMap);
	}
	
	@RequestMapping("/prelist")
	public ModelAndView preListApiCategory() throws Exception {
		return new ModelAndView("/yitiansystem/merchant/apimgt/list_category");
	}
	
	@RequestMapping("/list")
	public ModelAndView listApiCategory(ApiCategory apiCategory, Query query) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DetachedCriteria criteria = DetachedCriteria.forClass(ApiCategory.class);
		criteria.addOrder(Order.desc("created"));
		if (StringUtils.isNotBlank(apiCategory.getCategoryName())) {
			criteria.add(Restrictions.like("categoryName", apiCategory.getCategoryName(), MatchMode.START));
		}
		if (StringUtils.isNotBlank(apiCategory.getCategoryCode())) {
			criteria.add(Restrictions.eq("categoryCode", apiCategory.getCategoryCode()));
		}
		
		PageFinder<ApiCategory> pageFinder = apiCategoryService.queryApiCategory(criteria, query);
		resultMap.put("pageFinder", pageFinder);
		resultMap.put("apiCategory", apiCategory);
		return new ModelAndView("/yitiansystem/merchant/apimgt/list_category", resultMap);
	}
}
