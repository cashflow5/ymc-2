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
import com.yougou.api.model.pojo.ApiValidator;
import com.yougou.api.service.IApiValidatorService;
import com.yougou.api.util.StaticResourceResolver;

/**
 * API验证器控制器
 * 
 * @author 杨梦清
 * 
 */
@Controller
@RequestMapping("/openapimgt/validator")
public class ApiValidatorController {

	@Resource
	private IApiValidatorService apiValidatorService;
	
	@RequestMapping(value = "/delete")
	public void deleteApiValidator(String identifier, HttpServletResponse response) throws Exception {
		try {
			apiValidatorService.deleteApiValidatorByIdentifier(identifier);
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/saveorupdate")
	public void saveOrUpdateApiValidator(ApiValidator apiValidator, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SystemmgtUser systemmgtUser = (SystemmgtUser) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
			if (StringUtils.isBlank(apiValidator.getId())) {
				apiValidator.setId(null);
				apiValidator.setCreator(systemmgtUser.getLoginName());
				apiValidator.setCreated(new Date());
				apiValidatorService.saveApiValidator(apiValidator);
			} else {
				apiValidator.setModifier(systemmgtUser.getLoginName());
				apiValidator.setModified(new Date());
				apiValidatorService.updateApiValidator(apiValidator);
			}
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/view")
	public ModelAndView viewApiValidator(String identifier) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ApiValidator apiValidator = null;
		if (StringUtils.isNotBlank(identifier)) {
			apiValidator = apiValidatorService.getApiValidatorByIdentifier(identifier);
		} else {
			apiValidator = new ApiValidator();
			apiValidator.setIdentifier(apiValidatorService.getNextIdentifier());
		}
		resultMap.put("apiValidator", apiValidator);
		resultMap.put("classes", StaticResourceResolver.lookupValidatorClasses());
		return new ModelAndView("/yitiansystem/merchant/apimgt/view_validator", resultMap);
	}
	
	@RequestMapping("/prelist")
	public ModelAndView preListApiValidator() {
		return new ModelAndView("yitiansystem/merchant/apimgt/list_validator");
	}
	
	@RequestMapping("/list")
	public ModelAndView listApiValidator(ApiValidator apiValidator, Query query) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DetachedCriteria criteria = DetachedCriteria.forClass(ApiValidator.class);
		criteria.addOrder(Order.desc("created"));
		if (StringUtils.isNotBlank(apiValidator.getIdentifier())) {
			criteria.add(Restrictions.eq("identifier", apiValidator.getIdentifier()));
		}
		if (StringUtils.isNotBlank(apiValidator.getValidatorClass())) {
			criteria.add(Restrictions.like("validatorClass", apiValidator.getValidatorClass(), MatchMode.ANYWHERE));
		}
		
		PageFinder<ApiValidator> pageFinder = apiValidatorService.queryApiValidator(criteria, query);
		resultMap.put("pageFinder", pageFinder);
		resultMap.put("apiValidator", apiValidator);
		return new ModelAndView("/yitiansystem/merchant/apimgt/list_validator", resultMap);
	}
}

