package com.belle.infrastructure.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		arg0.getSession().removeAttribute("loginaccount_customer");
		arg0.getSession().removeAttribute("expiresTime");
		arg0.getSession().removeAttribute("alipay_token");
	}

}
