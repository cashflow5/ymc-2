package com.yougou.api.model.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppkeyFlowWarnEmails {

	@Value("${appkey.flow.warn.dev.emails}") 
	private String emails;

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}
	
	
}
