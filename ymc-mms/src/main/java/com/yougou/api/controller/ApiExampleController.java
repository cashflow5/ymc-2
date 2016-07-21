package com.yougou.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.constant.Constant;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.yougou.api.model.pojo.ApiExample;
import com.yougou.api.service.IApiExampleService;

/**
 * API示例控制器
 * 
 * @author 杨梦清
 * 
 */
@Controller
@RequestMapping("/openapimgt/api/example")
public class ApiExampleController {

	@Resource
	private IApiExampleService apiExampleService;
	
	@RequestMapping("/delete")
	public void deleteApiExample(String id, HttpServletResponse response) throws Exception {
		try {
			apiExampleService.deleteApiExampleById(id);
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/saveorupdate")
	public void saveOrUpdateApiExample(ApiExample apiExample, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SystemmgtUser systemmgtUser = (SystemmgtUser) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
			if (StringUtils.isBlank(apiExample.getId())) {
				apiExample.setId(null);
				apiExample.setCreator(systemmgtUser.getLoginName());
				apiExample.setCreated(new Date());
				apiExampleService.saveApiExample(apiExample);
			} else {
				apiExample.setModifier(systemmgtUser.getLoginName());
				apiExample.setModified(new Date());
				apiExampleService.updateApiExample(apiExample);
			}
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/view")
	public ModelAndView viewApiExample(ApiExample apiExample) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(apiExample.getId())) {
			apiExample = apiExampleService.getApiExampleById(apiExample.getId());
		}
		resultMap.put("apiExample", apiExample);
		return new ModelAndView("yitiansystem/merchant/apimgt/view_example", resultMap);
	}
}

