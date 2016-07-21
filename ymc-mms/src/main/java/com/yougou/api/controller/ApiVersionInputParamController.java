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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.constant.Constant;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.yougou.api.model.pojo.ApiInputParamMetadata;
import com.yougou.api.model.pojo.ApiVersionInputParam;
import com.yougou.api.model.pojo.InputParam;
import com.yougou.api.service.IApiInputParamMetadataService;
import com.yougou.api.service.IApiInputParamService;
import com.yougou.api.service.IApiVersionService;
import com.yougou.api.util.StaticResourceResolver;

/**
 * API版本输入参数控制器
 * 
 * @author 杨梦清
 * 
 */
@Controller
@RequestMapping("/openapimgt/version/inputparam")
public class ApiVersionInputParamController {

	@Resource
	private IApiVersionService apiVersionService;
	
	@Resource
	private IApiInputParamService apiInputParamService;
	
	@Resource
	private IApiInputParamMetadataService apiInputParamMetadataService;
	
	@RequestMapping("/delete")
	public void deleteApiVersionInputParam(ApiVersionInputParam inputParam, HttpServletResponse response) throws Exception {
		try {
			apiInputParamService.deleteApiInputParamById(inputParam);
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}

	@RequestMapping("/saveorupdate")
	public void saveOrUpdateApiVersionInputParam(ApiVersionInputParam inputParam, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SystemmgtUser systemmgtUser = (SystemmgtUser) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
			if (StringUtils.isBlank(inputParam.getId())) {
				inputParam.setId(null);
				inputParam.setCreator(systemmgtUser.getLoginName());
				inputParam.setCreated(new Date());
				apiInputParamService.saveApiInputParam(inputParam);
			} else {
				inputParam.setModifier(systemmgtUser.getLoginName());
				inputParam.setModified(new Date());
				apiInputParamService.updateApiInputParam(inputParam);
			}
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/view")
	public ModelAndView viewApiVersionInputParam(ApiVersionInputParam inputParam) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Integer> usableOrderNoList = apiInputParamService.getUsableOrderNoList(inputParam, 100);
		InputParam another = inputParam;
		if (inputParam != null && StringUtils.isNotBlank(inputParam.getId())) {
			another = apiInputParamService.getApiInputParamById(inputParam);
			usableOrderNoList.add(another.getOrderNo());
			Collections.sort(usableOrderNoList);
		}
		resultMap.put("inputParam", another);
		resultMap.put("usableOrderNoList", usableOrderNoList);
		return new ModelAndView("yitiansystem/merchant/apimgt/view_input_param", resultMap);
	}
	
	/****** API版本输入参数元数据(START) ******/
	
	@RequestMapping("/metadata/view")
	public ModelAndView viewApiVersionInputParamMetadata(String refId, String metadataId) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Integer> usableOrderNoList = apiInputParamMetadataService.getUsableOrderNoList(refId, 100);
		ApiInputParamMetadata apiInputParamMetadata = null;
		if (StringUtils.isNotBlank(metadataId)) {
			apiInputParamMetadata = apiInputParamMetadataService.getApiInputParamMetadataById(metadataId);
			usableOrderNoList.add(apiInputParamMetadata.getOrderNo());
			Collections.sort(usableOrderNoList);
		} else {
			ApiVersionInputParam apiInputParam = new ApiVersionInputParam();
			apiInputParam.setApiVersion(apiVersionService.getApiVersionById(refId));
			apiInputParamMetadata = new ApiInputParamMetadata();
			apiInputParamMetadata.setInputParam(apiInputParam);
		}
		resultMap.put("apiInputParamMetadata", apiInputParamMetadata);
		resultMap.put("usableOrderNoList", usableOrderNoList);
		resultMap.put("classes", StaticResourceResolver.lookupValidatorClasses());
		return new ModelAndView("yitiansystem/merchant/apimgt/view_input_param_metadata", resultMap);
	}
	
	@RequestMapping("/metadata/saveorupdate")
	public void saveOrUpdateApiInputParamMetadata(ApiInputParamMetadata apiInputParamMetadata, String inputParamId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SystemmgtUser systemmgtUser = (SystemmgtUser) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
			if (StringUtils.isBlank(apiInputParamMetadata.getId())) {
				apiInputParamMetadata.setId(null);
				apiInputParamMetadata.setInputParam(new ApiVersionInputParam(inputParamId));
				apiInputParamMetadata.setCreator(systemmgtUser.getLoginName());
				apiInputParamMetadata.setCreated(new Date());
				apiInputParamMetadataService.saveApiInputParamMetadata(apiInputParamMetadata);
			} else {
				apiInputParamMetadata.setInputParam(new ApiVersionInputParam(inputParamId));
				apiInputParamMetadata.setModifier(systemmgtUser.getLoginName());
				apiInputParamMetadata.setModified(new Date());
				apiInputParamMetadataService.updateApiInputParamMetadata(apiInputParamMetadata);
			}
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/metadata/delete")
	public void deleteApiInputParamMetadata(ApiInputParamMetadata apiInputParamMetadata, HttpServletResponse response) throws Exception {
		try {
			apiInputParamMetadataService.deleteApiInputParamMetadataById(apiInputParamMetadata.getId());
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	/****** API版本输入参数元数据(END) ******/
}
