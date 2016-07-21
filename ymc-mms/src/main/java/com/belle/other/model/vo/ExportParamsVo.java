package com.belle.other.model.vo;

import java.util.Map;

public class ExportParamsVo {
	private Map<Object, Object> params;
	private String templatePath;

	public Map<Object, Object> getParams() {
		return params;
	}

	public void setParams(Map<Object, Object> params) {
		this.params = params;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

}
