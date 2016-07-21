package com.yougou.api.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiCategory;
import com.yougou.api.service.IApiCategoryService;
import com.yougou.api.service.IApiService;

/**
 * API测试工具控制器
 * 
 * @author 杨梦清
 * 
 */
@Controller
@RequestMapping("/apitools")
public class ApiToolsController {

	@Resource
	private IApiCategoryService apiCategoryService;

	@Resource
	private IApiService apiService;
	
	@RequestMapping("/apitools")
	public ModelAndView preApiTools() throws Exception {
		return new ModelAndView("/yitiansystem/merchant/api_tools", Collections.<String, Object> emptyMap());
	}

	@ResponseBody
	@RequestMapping("/categorys")
	public String queryApiCategory() throws Exception {
		List<ApiCategory> apiCategorys = apiCategoryService.queryAllApiCategory();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "apiCategory", "apiVersion", "apiImplementor", "apiErrorMappers", "apiExamples", "apiInputParams", "apiOutputParams", "apiInterceptorMappers" });
		return JSONArray.fromObject(apiCategorys, jsonConfig).toString();
	}

	@ResponseBody
	@RequestMapping("/inputparams")
	public String queryApiInputParams(String apiId) throws Exception {
		Api api = apiService.getApiById(apiId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "api", "refer", "inputParam", "apiValidator" });
		return JSONArray.fromObject(api.getApiInputParams(), jsonConfig).toString();
	}
}
