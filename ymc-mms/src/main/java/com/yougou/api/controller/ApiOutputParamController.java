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
import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiOutputParam;
import com.yougou.api.model.pojo.OutputParam;
import com.yougou.api.service.IApiOutputParamService;
import com.yougou.api.service.IApiService;

/**
 * API输出参数控制器
 * 
 * @author 杨梦清
 * 
 */
@Controller
@RequestMapping("/openapimgt/api/outputparam")
public class ApiOutputParamController {

	@Resource
	private IApiService apiService;
	
	@Resource
	private IApiOutputParamService apiOutputParamService;
	
	@RequestMapping("/delete")
	public void deleteApiOutputParam(ApiOutputParam outputParam, HttpServletResponse response) throws Exception {
		try {
			apiOutputParamService.deleteApiOutputParamById(outputParam);
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}

	@RequestMapping("/saveorupdate")
	public void saveOrUpdateApiFilter(ApiOutputParam outputParam, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Api api = outputParam.getApi();
			if (api == null || (api = apiService.getApiById(api.getId())) == null) {
				throw new IllegalArgumentException("无法匹配输出参数引用的API对象!");
			}
			SystemmgtUser systemmgtUser = (SystemmgtUser) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
			if (StringUtils.isBlank(outputParam.getId())) {
				outputParam.setId(null);
				outputParam.setApi(api);
				outputParam.setCreator(systemmgtUser.getLoginName());
				outputParam.setCreated(new Date());
				apiOutputParamService.saveApiOutputParam(outputParam);
			} else {
				outputParam.setModifier(systemmgtUser.getLoginName());
				outputParam.setModified(new Date());
				apiOutputParamService.updateApiOutputParam(outputParam);
			}
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}

	@RequestMapping("/view")
	public ModelAndView viewApiOutputParam(ApiOutputParam outputParam) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Integer> usableOrderNoList = apiOutputParamService.getUsableOrderNoList(outputParam, 100);
		OutputParam another = outputParam;
		if (outputParam != null && StringUtils.isNotBlank(outputParam.getId())) {
			another = apiOutputParamService.getApiOutputParamById(outputParam);
			usableOrderNoList.add(another.getOrderNo());
			Collections.sort(usableOrderNoList);
		}
		resultMap.put("outputParam", another);
		resultMap.put("usableOrderNoList", usableOrderNoList);
		return new ModelAndView("yitiansystem/merchant/apimgt/view_output_param", resultMap);
	}
}
