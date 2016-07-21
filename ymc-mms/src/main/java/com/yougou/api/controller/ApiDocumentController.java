package com.yougou.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiCategory;
import com.yougou.api.service.IApiDocumentService;

/**
 * API文档中心控制器
 * 
 * @author yang.mq
 *
 */
@Controller
@RequestMapping("/apidoc")
public class ApiDocumentController {

	/** 所有分类列表页 **/
	private static final String LIST_CATEGORY_URI = "yitiansystem/merchant/apidoc/list_category";
	
	/** 分类下所有API **/
	private static final String LIST_CATEGORY_API_URI = "yitiansystem/merchant/apidoc/list_category_api";
	
	/** API详情页 **/
	private static final String VIEW_API_URI = "yitiansystem/merchant/apidoc/view_api";
	
	@Resource
	private IApiDocumentService apiDocumentService;
	
	@RequestMapping("/list_category")
	public ModelAndView listApiCategory() throws Exception {
		List<ApiCategory> categories = apiDocumentService.selectAllApiCategory();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("categories", categories);
		return new ModelAndView(LIST_CATEGORY_URI, resultMap);
	}
	
	@RequestMapping("/list_category_api")
	public ModelAndView listApiCategoryOwns(String categoryCode) throws Exception {
		List<Api> apis = apiDocumentService.selectApiByCategory(categoryCode);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if (CollectionUtils.isNotEmpty(apis)) {
			resultMap.put("category", apis.get(0).getApiCategory());
		}
		
		resultMap.put("apis", apis);
		return new ModelAndView(LIST_CATEGORY_API_URI, resultMap);
	}
	
	@RequestMapping("/view_api")
	public ModelAndView viewApi(String apiCode) throws Exception {
		Api api = apiDocumentService.selectApiByCode(apiCode);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("api", api);
		return new ModelAndView(VIEW_API_URI, resultMap);
	}
}
