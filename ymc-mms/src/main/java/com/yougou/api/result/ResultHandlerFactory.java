package com.yougou.api.result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ResultHandlerFactory {

	public static ResultHandler newInstance(HttpServletRequest request, HttpServletResponse response) {
		String format = request.getParameter("format");
		String method = request.getParameter("method");
		if ("json".equalsIgnoreCase(format)) {
			return new JsonResultHandler(response, method);
		} else {
			return new XmlResultHandler(response, method);
		}
	}
}
