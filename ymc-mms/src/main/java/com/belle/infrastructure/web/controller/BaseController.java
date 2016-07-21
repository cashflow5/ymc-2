package com.belle.infrastructure.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 *@author yhb
 * 
 * @version 创建时间：2011-4-7 下午08:08:43
 */
public class BaseController {
	
	/**
	 * 设置表单日期属性转换器
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	/**
	 * 绑定验证信息
	 * @param view
	 * @param result
	 */
	public void BinderValidaDate(ModelAndView view ,BindingResult ... results){
		for (BindingResult result : results) {
			List<FieldError> errList = result.getFieldErrors();
			view.getModel().put("validatorErrorList", errList);
		}
	}
	
	/**
	 * 绑定验证信息
	 * @param view
	 * @param result
	 */
	public void BinderValidaDate(ModelAndView view ,String ... results){
			
			List<FieldError> errList = (List<FieldError>) view.getModel().get("validatorErrorList");
			if(errList == null){
			    errList = new ArrayList<FieldError>();
			}
			
			
			for (String result : results) {
				FieldError error = new FieldError("", "", result);
				errList.add(error);
			}
			
			view.getModel().put("validatorErrorList", errList);
	}
	
	/**
	 * 绑定验证信息
	 * @param view
	 * @param result
	 */
	public void BinderValidaDate(ModelAndView view ,List<String> resultList){
			
			List<FieldError> errList = (List<FieldError>) view.getModel().get("validatorErrorList");
			if(errList == null){
			    errList = new ArrayList<FieldError>();
			}
			
			
			for (String result : resultList) {
				FieldError error = new FieldError("", "", result);
				errList.add(error);
			}
			
			view.getModel().put("validatorErrorList", errList);
	}
	public Map<String, Object> builderParams(HttpServletRequest request,
			boolean isDecode) throws UnsupportedEncodingException {
		Map params = request.getParameterMap();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (params != null && params.size() > 0) {
			for (Iterator iterator = params.entrySet().iterator(); iterator
					.hasNext();) {
				Entry p = (Entry) iterator.next();
				if (p.getValue() != null ) {
					String values[] = (String[]) p.getValue();
					// p.setValue(values[0]);
					if (isDecode) {
						resultMap.put(String.valueOf(p.getKey()), URLDecoder
								.decode(StringUtils.trimToEmpty(values[0]), "UTF-8"));
					} else {
						resultMap.put(String.valueOf(p.getKey()),
								values[0] == null ? null : values[0].trim());
					}

				}
			}
		}
		return resultMap;
	}
	
	
}
