package com.yougou.api.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.api.model.pojo.ApiVersionInterceptorMapper;
import com.yougou.api.model.pojo.InterceptorMapper;
import com.yougou.api.service.IApiInterceptorMapperService;
import com.yougou.api.util.StaticResourceResolver;

/**
 * API版本拦截器控制器
 * 
 * @author 杨梦清
 * 
 */
@Controller
@RequestMapping("/openapimgt/version/interceptor")
public class ApiVersionInterceptorMapperController {
	
	@Resource
	private IApiInterceptorMapperService apiInterceptorMapperService;

	@RequestMapping("/mapper/view")
	public ModelAndView viewInterceptorMapper(ApiVersionInterceptorMapper interceptorMapper) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Integer> usableOrderNoList = apiInterceptorMapperService.getUsableOrderNoList(interceptorMapper, 100);
		InterceptorMapper another = interceptorMapper;
		if (StringUtils.isNotBlank(interceptorMapper.getId())) {
			another = apiInterceptorMapperService.getInterceptorMapperById(interceptorMapper.getId());
			usableOrderNoList.add(another.getOrderNo());
			Collections.sort(usableOrderNoList);
		}
		resultMap.put("interceptorMapper", another);
		resultMap.put("usableOrderNoList", usableOrderNoList);
		resultMap.put("classes", StaticResourceResolver.lookupInterceptorClasses());
		return new ModelAndView("yitiansystem/merchant/apimgt/view_interceptor_mapper", resultMap);
	}
	
	@RequestMapping("/mapper/saveorupdate")
	public void saveOrUpdateInterceptorMapper(ApiVersionInterceptorMapper interceptorMapper, HttpServletResponse response) throws Exception {
		try {
			if (StringUtils.isBlank(interceptorMapper.getId())) {
				interceptorMapper.setId(null);
				apiInterceptorMapperService.saveInterceptorMapper(interceptorMapper);
			} else {
				apiInterceptorMapperService.updateInterceptorMapper(interceptorMapper);
			}
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/mapper/delete")
	public void deleteInterceptorMapper(ApiVersionInterceptorMapper interceptorMapper, HttpServletResponse response) throws Exception {
		try {
			apiInterceptorMapperService.deleteInterceptorMapperById(interceptorMapper.getId());
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
}

