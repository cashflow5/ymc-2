package com.yougou.api.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yougou.api.model.pojo.ApiKey;
import com.yougou.api.service.IApiKeyService;
import com.yougou.api.util.MD5Encryptor;

@Controller
public class AutoSignatureApiController {

	@Resource
	private IApiKeyService apiKeyService;

	@RequestMapping("/do_api_test")
	public void doGoYougouApiTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String redirectUrl = null;
		try {
			redirectUrl = buildRedirectUrl(request, response);
			response.sendRedirect(redirectUrl);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(e.getMessage());
		} finally {
			System.out.println(redirectUrl);
		}
	}
	
	@RequestMapping("/api_test_url")
	public void getRedirectUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String redirectUrl = null;
		try {
			redirectUrl = buildRedirectUrl(request, response);
			response.getWriter().print(redirectUrl);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(e.getMessage());
		} finally {
			System.out.println(redirectUrl);
		}
	}

	private String buildRedirectUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuilder qsBuilder = new StringBuilder();
		qsBuilder.append(request.getScheme());
		qsBuilder.append("://");
		qsBuilder.append(request.getServerName());
		qsBuilder.append(":");
		qsBuilder.append(request.getServerPort());
		qsBuilder.append(request.getContextPath());
		qsBuilder.append("/api.sc");

		Map parameterMap = new HashMap(request.getParameterMap());

		// create sign
		String appSecret = request.getParameter("app_secret");
		if ("自动分配".equals(appSecret)) {
			ApiKey apiKey = apiKeyService.randomSeekTestApiKey();
			if (apiKey == null) {
				throw new IllegalArgumentException("自动分配 app_key, app_secret 失败!");
			}
			appSecret = apiKey.getAppSecret();
			
			if (parameterMap.containsKey("app_key")) {
				parameterMap.put("app_key", new String[] { apiKey.getAppKey() });
			}
			if (parameterMap.containsKey("app_secret")) {
				parameterMap.put("app_secret", new String[] { apiKey.getAppSecret() });
			}
		}

		List<String> params = new ArrayList<String>();
		for (Iterator it = parameterMap.keySet().iterator(); it.hasNext();) {
			String key = it.next().toString();
			if (!"app_secret".equalsIgnoreCase(key)) {
				String[] values = (String[]) parameterMap.get(key);
				params.add(key + values[0]);
			}
		}

		Collections.sort(params);
		appSecret += StringUtils.join(params, "");
		String sign = MD5Encryptor.encrypt(appSecret);
		qsBuilder.append("?sign=").append(sign);

		// create param
		for (Iterator it = parameterMap.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			if (!"app_secret".equalsIgnoreCase(key)) {
				String[] values = (String[]) parameterMap.get(key);
				qsBuilder.append("&").append(key).append("=").append(URLEncoder.encode(values[0], "UTF-8"));
			}
		}
		
		return qsBuilder.toString();
	}
}
