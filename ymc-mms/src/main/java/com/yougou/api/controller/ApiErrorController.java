package com.yougou.api.controller;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.yougou.api.model.pojo.ApiError;
import com.yougou.api.service.IApiErrorService;

/**
 * API错误码控制器
 * 
 * @author 杨梦清
 * 
 */
@Controller
@RequestMapping("/openapimgt/api/error")
public class ApiErrorController {

	@Resource
	private IApiErrorService apiErrorService;
	
	@RequestMapping("/prelist")
	public ModelAndView preListApiError() throws Exception {
		return new ModelAndView("/yitiansystem/merchant/apimgt/list_api_error");
	}
	
	@RequestMapping("/list")
	public ModelAndView listApiError(ApiError apiError, Query query) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DetachedCriteria criteria = DetachedCriteria.forClass(ApiError.class);
		criteria.addOrder(Order.desc("created"));
		if (StringUtils.isNotBlank(apiError.getErrorCode())) {
			criteria.add(Restrictions.like("errorCode", apiError.getErrorCode(), MatchMode.START));
		}
		PageFinder<ApiError> pageFinder = apiErrorService.queryApiError(criteria, query);
		resultMap.put("apiError", apiError);
		resultMap.put("pageFinder", pageFinder);
		return new ModelAndView("/yitiansystem/merchant/apimgt/list_api_error", resultMap);
	}
	
	@RequestMapping("/delete")
	public void deleteApiError(String id, HttpServletResponse response) throws Exception {
		try {
			apiErrorService.deleteApiErrorById(id);
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/saveorupdate")
	public void saveOrUpdateApiError(ApiError apiError, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SystemmgtUser systemmgtUser = (SystemmgtUser) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
			if (StringUtils.isBlank(apiError.getId())) {
				apiError.setId(null);
				apiError.setCreator(systemmgtUser.getLoginName());
				apiError.setCreated(new Date());
				apiErrorService.saveApiError(apiError);
			} else {
				apiError.setModifier(systemmgtUser.getLoginName());
				apiError.setModified(new Date());
				apiErrorService.updateApiError(apiError);
			}
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/view")
	public ModelAndView viewApiError(ApiError apiError) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Integer> usableOrderNoList = apiErrorService.getUsableOrderNoList(apiError, 100);
		ApiError another = apiError;
		if (StringUtils.isNotBlank(apiError.getId())) {
			another = apiErrorService.getApiErrorById(apiError.getId());
			usableOrderNoList.add(another.getOrderNo());
			Collections.sort(usableOrderNoList);
		}
		resultMap.put("apiError", another);
		resultMap.put("usableOrderNoList", usableOrderNoList);
		return new ModelAndView("yitiansystem/merchant/apimgt/view_error", resultMap);
	}
}

