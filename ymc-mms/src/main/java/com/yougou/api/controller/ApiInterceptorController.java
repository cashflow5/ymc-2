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
import com.yougou.api.model.pojo.ApiInterceptor;
import com.yougou.api.service.IApiInterceptorService;
import com.yougou.api.util.StaticResourceResolver;

/**
 * API拦截器控制器
 * 
 * @author 杨梦清
 * 
 */
@Controller
@RequestMapping("/openapimgt/interceptor")
public class ApiInterceptorController {

	@Resource
	private IApiInterceptorService apiInterceptorService;
	
	@RequestMapping("/delete")
	public void deleteApiInterceptor(String identifier, HttpServletResponse response) throws Exception {
		try {
			apiInterceptorService.deleteApiInterceptorByIdentifier(identifier);
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/saveorupdate")
	public void saveOrUpdateApiInterceptor(ApiInterceptor apiInterceptor, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SystemmgtUser systemmgtUser = (SystemmgtUser) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
			if (StringUtils.isBlank(apiInterceptor.getId())) {
				apiInterceptor.setId(null);
				apiInterceptor.setCreator(systemmgtUser.getLoginName());
				apiInterceptor.setCreated(new Date());
				apiInterceptorService.saveApiInterceptor(apiInterceptor);
			} else {
				apiInterceptor.setModifier(systemmgtUser.getLoginName());
				apiInterceptor.setModified(new Date());
				apiInterceptorService.updateApiInterceptor(apiInterceptor);
			}
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/view")
	public ModelAndView viewApiInterceptor(String identifier) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(identifier)) {
			ApiInterceptor apiInterceptor = apiInterceptorService.getApiInterceptorByIdentifier(identifier);
			resultMap.put("apiInterceptor", apiInterceptor);
		}
		resultMap.put("classes", StaticResourceResolver.lookupInterceptorClasses());
		return new ModelAndView("/yitiansystem/merchant/apimgt/view_interceptor", resultMap);
	}
	
	@RequestMapping("/prelist")
	public ModelAndView preListApiInterceptor() {
		return new ModelAndView("yitiansystem/merchant/apimgt/list_interceptor");
	}
	
	@RequestMapping("/list")
	public ModelAndView listApiInterceptor(ApiInterceptor apiInterceptor, Query query) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DetachedCriteria criteria = DetachedCriteria.forClass(ApiInterceptor.class);
		criteria.addOrder(Order.desc("created"));
		if (StringUtils.isNotBlank(apiInterceptor.getIdentifier())) {
			criteria.add(Restrictions.eq("identifier", apiInterceptor.getIdentifier()));
		}
		if (StringUtils.isNotBlank(apiInterceptor.getInterceptorClass())) {
			criteria.add(Restrictions.like("interceptorClass", apiInterceptor.getInterceptorClass(), MatchMode.ANYWHERE));
		}
		
		PageFinder<ApiInterceptor> pageFinder = apiInterceptorService.queryApiInterceptor(criteria, query);
		resultMap.put("pageFinder", pageFinder);
		resultMap.put("apiInterceptor", apiInterceptor);
		return new ModelAndView("/yitiansystem/merchant/apimgt/list_interceptor", resultMap);
	}

}

