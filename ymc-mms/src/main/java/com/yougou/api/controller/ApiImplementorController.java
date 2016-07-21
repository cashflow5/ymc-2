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
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.yougou.api.model.pojo.ApiImplementor;
import com.yougou.api.service.IApiImplementorService;
import com.yougou.api.util.StaticResourceResolver;

/**
 * API实现者控制器
 * 
 * @author 杨梦清
 * 
 */
@Controller
@RequestMapping("/openapimgt/implementor")
public class ApiImplementorController {
	
	@Resource
	private IApiImplementorService apiImplementorService;

	@RequestMapping("/delete")
	public void deleteApiImplementor(String identifier, HttpServletResponse response) throws Exception {
		try {
			apiImplementorService.deleteApiImplementorByIdentifier(identifier);
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/saveorupdate")
	public void saveOrUpdateApiFilter(ApiImplementor apiImplementor, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SystemmgtUser systemmgtUser = (SystemmgtUser) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
			if (StringUtils.isBlank(apiImplementor.getId())) {
				apiImplementor.setId(null);
				apiImplementor.setCreator(systemmgtUser.getLoginName());
				apiImplementor.setCreated(new Date());
				apiImplementorService.saveApiImplementor(apiImplementor);
			} else {
				apiImplementor.setModifier(systemmgtUser.getLoginName());
				apiImplementor.setModified(new Date());
				apiImplementorService.updateApiImplementor(apiImplementor);
			}
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/view")
	public ModelAndView viewApiFilter(String identifier) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ApiImplementor apiImplementor = null;
		if (StringUtils.isNotBlank(identifier)) {
			apiImplementor = apiImplementorService.getApiImplementorByIdentifier(identifier);
		}
		resultMap.put("classes", StaticResourceResolver.lookupImplementorClasses());
		resultMap.put("apiImplementor", apiImplementor);
		return new ModelAndView("yitiansystem/merchant/apimgt/view_implementor", resultMap);
	}
	
	@RequestMapping("/prelist")
	public ModelAndView preListApiFilter() throws Exception {
		return new ModelAndView("yitiansystem/merchant/apimgt/list_implementor");
	}
	
	@RequestMapping("/list")
	public ModelAndView listApiFilter(ApiImplementor apiImplementor, Query query) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DetachedCriteria criteria = DetachedCriteria.forClass(ApiImplementor.class);
		if (StringUtils.isNotBlank(apiImplementor.getIdentifier())) {
			criteria.add(Restrictions.eq("identifier", apiImplementor.getIdentifier()));
		}
		if (StringUtils.isNotBlank(apiImplementor.getIsSpringManagedInstance())) {
			criteria.add(Restrictions.eq("isSpringManagedInstance", apiImplementor.getIsSpringManagedInstance()));
		}
		if (StringUtils.isNotBlank(apiImplementor.getImplementorClass())) {
			criteria.add(Restrictions.like("implementorClass", apiImplementor.getImplementorClass(), MatchMode.ANYWHERE));
		}
		
		PageFinder<ApiImplementor> pageFinder = apiImplementorService.queryApiImplementor(criteria, query);
		resultMap.put("pageFinder", pageFinder);
		resultMap.put("apiImplementor", apiImplementor);
		return new ModelAndView("yitiansystem/merchant/apimgt/list_implementor", resultMap);
	}

}

