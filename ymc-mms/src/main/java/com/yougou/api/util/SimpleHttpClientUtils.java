package com.yougou.api.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpStatus;

/**
 * 
 * @author 杨梦清
 * 
 */
public class SimpleHttpClientUtils {

	public static InputStream executeMethod(HttpMethod httpMethod) throws Exception {
		switch (new HttpClient().executeMethod(httpMethod)) {
		case HttpStatus.SC_OK:
			byte[] buffer = httpMethod.getResponseBody();
			httpMethod.releaseConnection();
			return new ByteArrayInputStream(buffer);
		case HttpStatus.SC_MOVED_PERMANENTLY:
		case HttpStatus.SC_MOVED_TEMPORARILY:
		case HttpStatus.SC_TEMPORARY_REDIRECT:
		case HttpStatus.SC_SEE_OTHER:
			Header header = httpMethod.getResponseHeader("location");
			if (header != null) {
				return executeMethod(new GetMethod(header.getValue()));
			}
		default:
			return null;
		}
	}
}
