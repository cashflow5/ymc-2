package com.yougou.common.web;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class CasConfigValues {
	
	
	private @Value("${cas.server.url}") String casServerUrl;
	private @Value("${cas.server.enabled}") String enabled;
	
	

	public String getCasServerUrl() {
		return casServerUrl;
	}

	public void setCasServerUrl(String casServerUrl) {
		this.casServerUrl = casServerUrl;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	
	

}
