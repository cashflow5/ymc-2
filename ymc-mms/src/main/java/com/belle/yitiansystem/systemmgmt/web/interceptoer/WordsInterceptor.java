package com.belle.yitiansystem.systemmgmt.web.interceptoer;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.belle.infrastructure.spring.DataSourceSwitcher;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemConfig;
import com.belle.yitiansystem.systemmgmt.service.ISystemConfigService;


@Service
public class WordsInterceptor extends HandlerInterceptorAdapter {
	
	private static final String key = "config.system.keyword";
	private static List<String> banWords = new ArrayList<String>();  //禁用词  婊.{2}子   你是一个婊  子
	
	@Resource
	private ISystemConfigService systemConfigService;
	
	private void initBandWordsList() throws Exception{
		DataSourceSwitcher.setDataSourceSys();
		SystemConfig config = systemConfigService.getSystemConfigByKey(key);
		if(config != null){
			String values = config.getValue();
			String [] vals = values.split(",");
			for (String val : vals) {
				banWords.add(val);
			}
		}
		DataSourceSwitcher.clearDataSource();
	}
	
	
	
	 
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		
		
		if(banWords.size() == 0){
			initBandWordsList();
		}
		
		String servletPath = request.getServletPath().substring(1);
		if(servletPath.endsWith("error_wordcensor.sc") || (servletPath.indexOf("/systemconfig/") != -1)){
			return true;
		}
		request.removeAttribute("wordcensor");
		
		StringBuffer banWordsBuffer = new StringBuffer();
		
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String inputName = (String) e.nextElement();
			String inputValue = request.getParameter(inputName); // ***姨子****
			if (inputValue == null || inputValue.trim().equals("")) {
				continue;
			}
			// 用禁用词库去检查用户的输入
			for (String regex : banWords) {
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(inputValue);
				if (m.find()) {
					
					if(banWordsBuffer.indexOf(regex+",") == -1){
						banWordsBuffer.append(regex).append(",");
					}
					
					// 代表用户输入的数据有和正则表达式相匹配的数据
					// request.setAttribute("message", "文章中包含非法词汇，请文件用语！！！");
					// request.getRequestDispatcher("/message.jsp").forward(request,
					// response);
					// return;
				}
			}
		}
		
		if(banWordsBuffer.length() > 1){
			banWordsBuffer.deleteCharAt(banWordsBuffer.length()-1);
			request.setAttribute("wordcensor",banWordsBuffer.toString());
			request.getRequestDispatcher("/yitianmall/commodityshow/mallindex/error_wordcensor.sc").forward(request, response);
			return false;
		}
        
		return true;
	}
	

}
