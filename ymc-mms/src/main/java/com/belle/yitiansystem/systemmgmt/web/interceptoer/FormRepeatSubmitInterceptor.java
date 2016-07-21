package com.belle.yitiansystem.systemmgmt.web.interceptoer;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class FormRepeatSubmitInterceptor extends HandlerInterceptorAdapter {
	
	private String formKeyName = "PrivateFromSubmitKey";
	
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession();
		
		String oldKey  = (String)session.getAttribute(formKeyName); 
		String newKey = generateToken(session);
		if(oldKey == null || "".equals(oldKey)){
			session.setAttribute(formKeyName, newKey);
		}
		
		String formKey = (String)request.getParameter(formKeyName);
		
		if(formKey == null || "".equals(formKey)){
			return true;
		}
		
		
		if(oldKey.equals(formKey)){
			session.setAttribute(formKeyName, newKey);
			return true;
		}else{
			String repeatSubmitErrorUrl = "/yitianmall/commodityshow/mallindex/error_wordcensor.sc";
			response.sendRedirect(request.getContextPath()+repeatSubmitErrorUrl);
			return false;
		}
		
	}
	
	protected String generateToken(HttpSession session) {
		try {
			byte id[] = session.getId().getBytes();
			byte now[] = new Long(System.currentTimeMillis()).toString().getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(id);
			md.update(now);
			BigInteger a = new BigInteger(md.digest());
			return a.toString(16);
		} catch (IllegalStateException e) {
			return (null);
		} catch (NoSuchAlgorithmException e) {
			return (null);
		}
	}
	

}
