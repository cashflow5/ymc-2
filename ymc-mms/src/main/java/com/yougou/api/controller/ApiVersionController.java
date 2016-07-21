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
import com.yougou.api.model.pojo.ApiVersion;
import com.yougou.api.service.IApiVersionService;

/**
 * API版本控制器
 * 
 * @author 杨梦清
 * 
 */
@Controller
@RequestMapping("/openapimgt/version")
public class ApiVersionController {

	@Resource
	private IApiVersionService apiVersionService;

	@RequestMapping("/view")
	public ModelAndView viewApiVersion(String id) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ApiVersion apiVersion = null;
		if (StringUtils.isNotBlank(id)) {
			apiVersion = apiVersionService.getApiVersionById(id);
		}
		resultMap.put("apiVersion", apiVersion);
		return new ModelAndView("yitiansystem/merchant/apimgt/view_version", resultMap);
	}
	
	@RequestMapping("/view/inputparam")
	public ModelAndView viewApiInputParam(String id) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("apiVersion", apiVersionService.getApiVersionById(id));
		return new ModelAndView("yitiansystem/merchant/apimgt/view_version_input_param", resultMap);
	}

	@RequestMapping("/view/outputparam")
	public ModelAndView viewApiOutputParam(String id) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("apiVersion", apiVersionService.getApiVersionById(id));
		return new ModelAndView("yitiansystem/merchant/apimgt/view_version_output_param", resultMap);
	}
	
	@RequestMapping("/view/validator")
	public ModelAndView viewApiValidator(String id) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("apiVersion", apiVersionService.getApiVersionById(id));
		return new ModelAndView("yitiansystem/merchant/apimgt/view_version_validator", resultMap);
	}
	
	@RequestMapping("/view/interceptor")
	public ModelAndView viewApiInterceptor(String id) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("apiVersion", apiVersionService.getApiVersionById(id));
		return new ModelAndView("yitiansystem/merchant/apimgt/view_version_interceptor", resultMap);
	}
	
	@RequestMapping("/saveorupdate")
	public void saveOrUpdateApiVersion(ApiVersion apiVersion, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SystemmgtUser systemmgtUser = (SystemmgtUser) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
			if (StringUtils.isBlank(apiVersion.getId())) {
				ApiVersion another = apiVersionService.getApiVersionByNo(apiVersion.getVersionNo());
				if (another != null) {
					throw new UnsupportedOperationException("API版本号已经存在!");
				}
				apiVersion.setId(null);
				apiVersion.setCreator(systemmgtUser.getLoginName());
				apiVersion.setCreated(new Date());
				apiVersionService.saveApiVersion(apiVersion);
			} else {
				apiVersion.setModifier(systemmgtUser.getLoginName());
				apiVersion.setModified(new Date());
				apiVersionService.updateApiVersion(apiVersion);
			}
			response.getWriter().print(Boolean.TRUE);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/delete")
	public void deleteApiVersion(String id, HttpServletResponse response) throws Exception {
		try {
			apiVersionService.deleteApiVersionById(id);
			response.getWriter().print(Boolean.TRUE);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}

	@RequestMapping("/list")
	public ModelAndView listApiVersion() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("apiVersions", apiVersionService.queryAllApiVersion());
		return new ModelAndView("yitiansystem/merchant/apimgt/list_version", resultMap);
	}

}
