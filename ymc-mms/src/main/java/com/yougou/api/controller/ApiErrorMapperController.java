package com.yougou.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.yougou.api.model.pojo.ApiError;
import com.yougou.api.model.pojo.ApiErrorMapper;
import com.yougou.api.service.IApiErrorMapperService;
import com.yougou.api.service.IApiErrorService;

/**
 * API错误码映射控制器
 * 
 * @author yang.mq
 *
 */
@Controller
@RequestMapping("/openapimgt/api/errormapper")
public class ApiErrorMapperController {

	@Resource
	private IApiErrorService apiErrorService;

	@Resource
	private IApiErrorMapperService apiErrorMapperService;

	@RequestMapping("/delete")
	public void deleteApiError(String id, HttpServletResponse response) throws Exception {
		try {
			apiErrorMapperService.deleteApiErrorMapper(id);
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}

	@RequestMapping("/saveorupdate")
	public void saveOrUpdateApiError(ApiErrorMapper apiErrorMapper, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			apiErrorMapper.setId(null);
			apiErrorMapperService.saveApiErrorMapper(apiErrorMapper);
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}

	@RequestMapping("/view")
	public ModelAndView viewApiErrorMapper(ApiErrorMapper apiErrorMapper) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DetachedCriteria criteria = DetachedCriteria.forClass(ApiError.class);
		criteria.setProjection(Projections.projectionList().add(Projections.property("id")).add(Projections.property("errorCode")));
		criteria.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		criteria.addOrder(Order.asc("orderNo"));
		PageFinder<ApiError> pageFinder = apiErrorService.queryApiError(criteria, new Query(Integer.MAX_VALUE));
		resultMap.put("pageFinder", pageFinder);
		resultMap.put("apiErrorMapper", apiErrorMapper);
		return new ModelAndView("yitiansystem/merchant/apimgt/view_error_mapper", resultMap);
	}
}
