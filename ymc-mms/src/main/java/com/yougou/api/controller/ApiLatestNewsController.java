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
import com.yougou.api.model.pojo.ApiLatestNews;
import com.yougou.api.service.IApiLatestNewsService;

/**
 * API最新动态控制器
 * 
 * @author yang.mq
 *
 */
@Controller
@RequestMapping("/openapimgt/api/latestnews")
public class ApiLatestNewsController {

	@Resource
	private IApiLatestNewsService apiLatestNewsService;
	
	@RequestMapping("/prelist")
	public ModelAndView preListApiLatestNews() throws Exception {
		return new ModelAndView("yitiansystem/merchant/apimgt/list_api_latestnews");
	}
	
	@RequestMapping("/list")
	public ModelAndView listApiLatestNews(ApiLatestNews apiLatestNews, Date fromCreated, Date toCreated, Query query) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DetachedCriteria criteria = DetachedCriteria.forClass(ApiLatestNews.class);
		criteria.addOrder(Order.desc("created"));
		if (StringUtils.isNotBlank(apiLatestNews.getSubject())) {
			criteria.add(Restrictions.like("subject", apiLatestNews.getSubject(), MatchMode.START));
		}
		if (StringUtils.isNotBlank(apiLatestNews.getCreator())) {
			criteria.add(Restrictions.eq("creator", apiLatestNews.getCreator()));
		}
		if (fromCreated != null) {
			criteria.add(Restrictions.ge("created", fromCreated));
		}
		if (toCreated != null) {
			criteria.add(Restrictions.le("created", toCreated));
		}
		PageFinder<ApiLatestNews> pageFinder = apiLatestNewsService.queryApiLatestNews(criteria, query);
		resultMap.put("apiLatestNews", apiLatestNews);
		resultMap.put("fromCreated", fromCreated);
		resultMap.put("toCreated", toCreated);
		resultMap.put("pageFinder", pageFinder);
		return new ModelAndView("yitiansystem/merchant/apimgt/list_api_latestnews", resultMap);
	}
	
	@RequestMapping("/saveorupdate")
	public void saveOrUpdateApiLatestNews(ApiLatestNews apiLatestNews, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SystemmgtUser systemmgtUser = (SystemmgtUser) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
			if (StringUtils.isBlank(apiLatestNews.getId())) {
				apiLatestNews.setId(null);
				apiLatestNews.setCreator(systemmgtUser.getLoginName());
				apiLatestNews.setCreated(new Date());
				apiLatestNewsService.saveApiLatestNews(apiLatestNews);
			} else {
				apiLatestNews.setModifier(systemmgtUser.getLoginName());
				apiLatestNews.setModified(new Date());
				apiLatestNewsService.updateApiLatestNews(apiLatestNews);
			}
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/view")
	public ModelAndView viewApiLatestNews(ApiLatestNews apiLatestNews) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(apiLatestNews.getId())) {
			apiLatestNews = apiLatestNewsService.getApiLatestNews(apiLatestNews.getId());
		}
		resultMap.put("apiLatestNews", apiLatestNews);
		return new ModelAndView("yitiansystem/merchant/apimgt/view_api_latestnews", resultMap);
	}
	
	@RequestMapping("/delete")
	public void deleteApiLatestNews(String id, HttpServletResponse response) throws Exception {
		try {
			apiLatestNewsService.deleteApiLatestNews(id);
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
