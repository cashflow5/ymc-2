package com.belle.yitiansystem.systemmgmt.component;

import javax.servlet.http.HttpServletRequest;

public interface IWordsFilterComponent {

	public Object filterInput(HttpServletRequest request,Object obj) throws Exception;
	
	public String filterInputForString(String inputString) throws Exception;
}
