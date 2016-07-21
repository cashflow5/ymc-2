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
import com.yougou.api.model.pojo.ApiFilter;
import com.yougou.api.service.IApiFilterService;
import com.yougou.api.util.StaticResourceResolver;

/**
 * API过滤器控制器
 * 
 * @author 杨梦清
 * 
 */
@Controller
@RequestMapping("/openapimgt/filter")
public class ApiFilterController {

	@Resource
	private IApiFilterService apiFilterService;

	@RequestMapping("/delete")
	public void deleteApiFilter(String identifier, HttpServletResponse response) throws Exception {
		try {
			apiFilterService.deleteApiFilterByIdentifier(identifier);
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}

	@RequestMapping("/saveorupdate")
	public void saveOrUpdateApiFilter(ApiFilter apiFilter, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SystemmgtUser systemmgtUser = (SystemmgtUser) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
			if (StringUtils.isBlank(apiFilter.getId())) {
				apiFilter.setId(null);
				apiFilter.setCreator(systemmgtUser.getLoginName());
				apiFilter.setCreated(new Date());
				apiFilterService.saveApiFilter(apiFilter);
			} else {
				apiFilter.setModifier(systemmgtUser.getLoginName());
				apiFilter.setModified(new Date());
				apiFilterService.updateApiFilter(apiFilter);
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
		List<Integer> usableOrderNoList = apiFilterService.getUsableOrderNoList(100);
		ApiFilter apiFilter = null;
		if (StringUtils.isNotBlank(identifier)) {
			apiFilter = apiFilterService.getApiFilterByIdentifier(identifier);
			usableOrderNoList.add(apiFilter.getOrderNo());
			Collections.sort(usableOrderNoList);
		}
		resultMap.put("classes", StaticResourceResolver.lookupFilterClasses());
		resultMap.put("apiFilter", apiFilter);
		resultMap.put("usableOrderNoList", usableOrderNoList);
		return new ModelAndView("yitiansystem/merchant/apimgt/view_filter", resultMap);
	}

	@RequestMapping("/prelist")
	public ModelAndView preListApiFilter() throws Exception {
		return new ModelAndView("yitiansystem/merchant/apimgt/list_filter");
	}

	@RequestMapping("/list")
	public ModelAndView listApiFilter(ApiFilter apiFilter, Query query) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DetachedCriteria criteria = DetachedCriteria.forClass(ApiFilter.class);
		criteria.addOrder(Order.asc("orderNo"));
		if (StringUtils.isNotBlank(apiFilter.getIdentifier())) {
			criteria.add(Restrictions.eq("identifier", apiFilter.getIdentifier()));
		}
		if (StringUtils.isNotBlank(apiFilter.getFilterClass())) {
			criteria.add(Restrictions.like("filterClass", apiFilter.getFilterClass(), MatchMode.ANYWHERE));
		}

		PageFinder<ApiFilter> pageFinder = apiFilterService.queryApiFilter(criteria, query);
		resultMap.put("pageFinder", pageFinder);
		resultMap.put("apiFilter", apiFilter);
		return new ModelAndView("yitiansystem/merchant/apimgt/list_filter", resultMap);
	}
}
