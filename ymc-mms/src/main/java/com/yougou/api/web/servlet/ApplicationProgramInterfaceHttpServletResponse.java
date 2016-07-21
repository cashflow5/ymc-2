package com.yougou.api.web.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * HTTP 响应包装者
 * 
 * @author yang.mq
 *
 */
public class ApplicationProgramInterfaceHttpServletResponse extends HttpServletResponseWrapper {

	private final HttpServletResponse httpResponse;
	private ApplicationProgramInterfaceServletOutputStream outputStream;
	private PrintWriter writer;
	private boolean isGetOutputStream;
	private boolean isGetWriter;
	
	public ApplicationProgramInterfaceHttpServletResponse(ServletResponse response) {
		this((HttpServletResponse) response);
	}
	
	public ApplicationProgramInterfaceHttpServletResponse(HttpServletResponse response) {
		super(response);
		this.httpResponse = response;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (isGetWriter) {
			throw new IllegalStateException("getWriter() has already been called");
		}
		isGetOutputStream = true;
		return getApplicationProgramInterfaceServletOutputStream();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (isGetOutputStream) {
			throw new IllegalStateException("getOutputStream() has already been called");
		}
		isGetWriter = true;
		if (writer == null) {
			writer = new PrintWriter(new OutputStreamWriter(getApplicationProgramInterfaceServletOutputStream(), getCharacterEncoding()), true);
		}
		return writer;
	}
	
	@Override
	public void flushBuffer() throws IOException {
		if (isGetOutputStream) {
			outputStream.flush();
		} else if (isGetWriter) {
			writer.flush();
		}
	}
	
	/**
	 * 获取输出流
	 * 
	 * @return ApplicationProgramInterfaceServletOutputStream
	 * @throws IOException
	 */
	private ApplicationProgramInterfaceServletOutputStream getApplicationProgramInterfaceServletOutputStream() throws IOException {
		if (outputStream == null) {
			outputStream = new ApplicationProgramInterfaceServletOutputStream(getResponse().getOutputStream());
		}
		return outputStream;
	}
	
	/**
	 * 获取响应内容
	 * 
	 * @return String
	 * @throws IOException
	 */
	public String getResponseContent() throws IOException {
		if (isGetOutputStream) {
			return "ApplicationProgramInterfaceServletOutputStream";
		} else if (isGetWriter) {
			return new String(getApplicationProgramInterfaceServletOutputStream().toByteArray(), getCharacterEncoding());
		} else {
			return null;
		}
	}
}
