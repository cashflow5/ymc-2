package com.belle.yitiansystem.systemmgmt.component.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.belle.infrastructure.util.ReflectionUtils;
import com.belle.memcached.core.Cache;
import com.belle.yitiansystem.systemmgmt.component.IWordsFilterComponent;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemConfig;
import com.belle.yitiansystem.systemmgmt.service.ISystemConfigService;

@Component
public class WordsFilterComponentImpl implements IWordsFilterComponent {

	private static final String key = "config.system.keyword";
	private static final String filterKey ="system.keyword.filter";
	
	IMemcachedCache mc = Cache.getSingleCacheClient("brandclient1");
	
	@Resource
	private ISystemConfigService systemConfigService;
	
	@SuppressWarnings("unchecked")
	public List<String> loadKeyWordsToMc() throws Exception {
		List<String> banWords = new ArrayList<String>();
		Object obj = mc.get(filterKey);
		if(null == obj){
			SystemConfig config = systemConfigService.getSystemConfigByKey(key);
			if(config != null){
				String values = config.getValue();
				String [] vals = values.split(",");
				for (String val : vals) {
					banWords.add(val);
				}
			}
			mc.put(filterKey, banWords);
			return banWords;
		}else{
			return (List<String>) obj;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object filterInput(HttpServletRequest request,Object obj) throws Exception {
		List<String> banWords = this.loadKeyWordsToMc();
		if(banWords.size() == 0){
			return obj;
		}
		
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String inputName = (String) e.nextElement();
			String inputValue = request.getParameter(inputName); // 
			if (inputValue == null || inputValue.trim().equals("")) {
				continue;
			}
			// 用禁用词库去检查用户的输入
			for (String regex : banWords) {
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(inputValue);
				if (m.find()) {
					inputValue = m.replaceAll("****");
					ReflectionUtils.setFieldValueByFieldType(obj,inputName,inputValue);
				}
			}
		}
		return obj;
	}
	
	/**
	 *  非法输入过滤
	 * @param inputString 过滤数据
	 * @return
	 * @throws Exception 
	 */
	public String filterInputForString(String inputString) throws Exception{
		List<String> banWords = this.loadKeyWordsToMc();
		if(banWords.size() == 0){
			return inputString;
		}
		
		for (String regex : banWords) {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(inputString);
			if (m.find()) {
				inputString = m.replaceAll("****");
			}
		}
		
		return inputString;
	}
}
