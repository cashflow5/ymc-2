package com.yougou.kaidian.framework.beans;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

/**
 * Spring 返回字符串中文乱码处理 Bean
 * 
 * @author yang.mq
 *
 */
public class StringHttpMessageConverter extends org.springframework.http.converter.StringHttpMessageConverter {

	public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	
	@Override
	protected String readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException {
		MediaType contentType = inputMessage.getHeaders().getContentType();
		if (contentType.getCharSet() == null) {
			contentType = new MediaType(contentType.getType(), contentType.getSubtype(), UTF8_CHARSET);
			inputMessage.getHeaders().setContentType(contentType);
		}
		return super.readInternal(clazz, inputMessage);
	}

	@Override
	protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException {
		MediaType contentType = outputMessage.getHeaders().getContentType();
		if (contentType.getCharSet() == null) {
			contentType = new MediaType(contentType.getType(), contentType.getSubtype(), UTF8_CHARSET);
			outputMessage.getHeaders().setContentType(contentType);
		}
		super.writeInternal(s, outputMessage);
	}
}
